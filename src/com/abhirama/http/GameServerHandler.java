/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

/**
 * Created by IntelliJ IDEA.
 * User: abhat
 * This software is provided under the "DO WHAT THE HECK YOU WANT TO DO WITH THIS LICENSE"
 */

package com.abhirama.http;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;
import org.jboss.netty.handler.codec.http.*;
import org.jboss.netty.util.CharsetUtil;

import java.util.*;
import java.util.Map.Entry;

import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.*;
import static org.jboss.netty.handler.codec.http.HttpHeaders.getHost;
import static org.jboss.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.OK;
import static org.jboss.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public abstract class GameServerHandler extends SimpleChannelUpstreamHandler {
  public static final String REQUEST_INFO_PROTOCOL_VERSION = "protocolVersion";
  public static final String REQUEST_INFO_HOSTNAME = "hostname";
  public static final String REQUEST_INFO_REQUEST_URI = "requestURI";

  //todo - set the access modifiers appropriately

  protected HttpRequest request;
   protected boolean readingChunks;
  /** Buffer that stores the response content */
  protected final StringBuilder buf = new StringBuilder();
  
  protected Set<Cookie> requestCookies = new HashSet<Cookie>();
  protected Map<String, String> requestHeaders = new HashMap<String, String>();
  protected Map<String, String> requestInfo = new HashMap<String, String>();
  protected Map<String, List<String>> requestParameters = new HashMap<String, List<String>>();
  protected String requestContent = "";

  protected Set<Cookie> responseCookies = new HashSet<Cookie>();
  protected Map<String, String> responseHeaders = new HashMap<String, String>();

  protected boolean keepAlive = true; //Keep alive by default

  public boolean getKeepAlive() {
    return keepAlive;
  }

  public void setKeepAlive(boolean keepAlive) {
    this.keepAlive = keepAlive;
  }

  private void addKeepAlive(HttpResponse response) {
    // Add 'Content-Length' header only for a keep-alive connection.
    response.setHeader(CONTENT_LENGTH, response.getContent().readableBytes());
    // Add keep alive header as per:
    // - http://www.w3.org/Protocols/HTTP/1.1/draft-ietf-http-v11-spec-01.html#Connection
    response.setHeader(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);

    if (isKeepAlive(request)) {
      // Add 'Content-Length' header only for a keep-alive connection.
      response.setHeader(CONTENT_LENGTH, response.getContent().readableBytes());
      // Add keep alive header as per:
      // - http://www.w3.org/Protocols/HTTP/1.1/draft-ietf-http-v11-spec-01.html#Connection
      response.setHeader(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
    }
  }
  
  private void populateRequestInfo() {
    this.requestInfo.put(REQUEST_INFO_HOSTNAME, getHost(request, "unknown"));
    //todo - use string formatting
    this.requestInfo.put(REQUEST_INFO_PROTOCOL_VERSION, request.getProtocolVersion().getMajorVersion() + "." + request.getProtocolVersion().getMinorVersion());
    this.requestInfo.put(REQUEST_INFO_REQUEST_URI,  request.getUri());
  }
  
  private void populateRequestHeaders() {
    for (Map.Entry<String, String> h: request.getHeaders()) {
      this.requestHeaders.put(h.getKey(), h.getValue());
    }
  }
  
  private void populateRequestCookies() {
    String cookieString = request.getHeader(COOKIE);
    if (cookieString != null) {
      CookieDecoder cookieDecoder = new CookieDecoder();
      Set<Cookie> cookies = cookieDecoder.decode(cookieString);
      this.requestCookies = cookies;
    }
  }
  
  private void populateRequestParameters() {
    QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.getUri());
    Map<String, List<String>> params = queryStringDecoder.getParameters();
    if (!params.isEmpty()) {
      for (Entry<String, List<String>> p: params.entrySet()) {
        String key = p.getKey();
        List<String> vals = p.getValue();
        this.requestParameters.put(key, vals);
      }
    }
  }

  protected void setResponseCookies(Set<Cookie> responseCookies) {
    this.responseCookies = responseCookies;
  }
  
  protected void setResponseHeaders(Map<String, String> responseHeaders) {
    this.responseHeaders = responseHeaders;  
  }
  
  //todo change this later
  protected void addToOp(String string) {
    this.buf.append(string);
  }
  
  protected void addCookies(HttpResponse response) {
    if (this.responseCookies.size() > 0) {
      for (Cookie responseCookie : this.responseCookies) {
        //Todo check the right way to do this
        CookieEncoder cookieEncoder = new CookieEncoder(true);
        cookieEncoder.addCookie(responseCookie);
        response.addHeader(SET_COOKIE, cookieEncoder.encode());
      }
    }
  }
  
  protected void addHeaders(HttpResponse response) {
    if (this.responseHeaders.size() > 0) {
      for (Map.Entry<String, String> entry : this.responseHeaders.entrySet()) {
        response.setHeader(entry.getKey(), entry.getValue());
      }
    }
  }

  @Override
  public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
    HttpRequest request = this.request = (HttpRequest) e.getMessage();
    
    this.populateRequestInfo();
    this.populateRequestHeaders();
    this.populateRequestCookies();
    this.populateRequestParameters();

    ChannelBuffer content = request.getContent();
    if (content.readable()) {
      this.requestContent = content.toString(CharsetUtil.UTF_8);
    }

    this.gameLogic(null);

    this.writeResponse(e);
  }
  
  public abstract Map gameLogic(Map data);

  private void writeResponse(MessageEvent e) throws InterruptedException {
    // Build the response object.
    HttpResponse response = new DefaultHttpResponse(HTTP_1_1, OK);
    response.setContent(ChannelBuffers.copiedBuffer(this.buf.toString(), CharsetUtil.UTF_8));

    //response.setHeader(CONTENT_TYPE, "text/plain; charset=UTF-8");
    this.addHeaders(response);

    boolean keepAlive = this.getKeepAlive() && isKeepAlive(request); //todo take this out later
    if (keepAlive) {
      this.addKeepAlive(response);
    }


    this.addCookies(response);

    // Write the response.
    ChannelFuture future = e.getChannel().write(response);

    // Close the non-keep-alive connection after the write operation is done.
    if (!keepAlive) {
      future.addListener(ChannelFutureListener.CLOSE);
    }
  }

  private static void send100Continue(MessageEvent e) {
    HttpResponse response = new DefaultHttpResponse(HTTP_1_1, CONTINUE);
    e.getChannel().write(response);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
      throws Exception {
    e.getCause().printStackTrace();
    e.getChannel().close();
  }
}

