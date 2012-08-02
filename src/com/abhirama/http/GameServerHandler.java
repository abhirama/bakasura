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
package com.abhirama.http;

import static org.jboss.netty.handler.codec.http.HttpHeaders.*;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.*;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.*;
import static org.jboss.netty.handler.codec.http.HttpVersion.*;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import com.abhirama.gameengine.Room;
import com.abhirama.gameengine.test.HitEvent;
import com.abhirama.gameengine.test.HitRoomEvent;
import com.abhirama.utils.Util;
import com.sun.org.apache.xpath.internal.axes.HasPositionalPredChecker;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.codec.http.Cookie;
import org.jboss.netty.handler.codec.http.CookieDecoder;
import org.jboss.netty.handler.codec.http.CookieEncoder;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpChunk;
import org.jboss.netty.handler.codec.http.HttpChunkTrailer;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.QueryStringDecoder;
import org.jboss.netty.util.CharsetUtil;

public abstract class GameServerHandler extends SimpleChannelUpstreamHandler {
  public static final String REQUEST_INFO_PROTOCOL_VERSION = "protocolVersion";
  public static final String REQUEST_INFO_HOSTNAME = "hostname";
  public static final String REQUEST_INFO_REQUEST_URI = "requestURI";
  
  private HttpRequest request;
  private boolean readingChunks;
  /** Buffer that stores the response content */
  private final StringBuilder buf = new StringBuilder();
  
  private Set<Cookie> requestCookies = new HashSet<Cookie>();
  private Map<String, String> requestHeaders = new HashMap<String, String>();
  private Map<String, String> requestInfo = new HashMap<String, String>();
  private Map<String, List<String>> requestParameters = new HashMap<String, List<String>>();
  private String requestContent = "";

  private Set<Cookie> responseCookies = new HashSet<Cookie>();
  private Map<String, String> responseHeaders = new HashMap<String, String>();
  
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
    TimeUnit.MILLISECONDS.sleep(2);
    TimeUnit.MILLISECONDS.sleep(2);

    // Decide whether to close the connection or not.
    boolean keepAlive = isKeepAlive(request);
    keepAlive = false;

    if (keepAlive) {
      System.out.println("Keeping alive");
    }

    // Build the response object.
    HttpResponse response = new DefaultHttpResponse(HTTP_1_1, OK);
    response.setContent(ChannelBuffers.copiedBuffer(buf.toString(), CharsetUtil.UTF_8));
    response.setHeader(CONTENT_TYPE, "text/plain; charset=UTF-8");

    if (keepAlive) {
      // Add 'Content-Length' header only for a keep-alive connection.
      response.setHeader(CONTENT_LENGTH, response.getContent().readableBytes());
      // Add keep alive header as per:
      // - http://www.w3.org/Protocols/HTTP/1.1/draft-ietf-http-v11-spec-01.html#Connection
      response.setHeader(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
    }

    // Encode the cookie.
    String cookieString = request.getHeader(COOKIE);
    if (cookieString != null) {
      CookieDecoder cookieDecoder = new CookieDecoder();
      Set<Cookie> cookies = cookieDecoder.decode(cookieString);
      if (!cookies.isEmpty()) {
        // Reset the cookies if necessary.
        CookieEncoder cookieEncoder = new CookieEncoder(true);
        for (Cookie cookie : cookies) {
          cookieEncoder.addCookie(cookie);
          response.addHeader(SET_COOKIE, cookieEncoder.encode());
        }
      }
    } else {
      // Browser sent no cookie.  Add some.
      CookieEncoder cookieEncoder = new CookieEncoder(true);
      cookieEncoder.addCookie("key1", "value1");
      response.addHeader(SET_COOKIE, cookieEncoder.encode());
      cookieEncoder.addCookie("key2", "value2");
      response.addHeader(SET_COOKIE, cookieEncoder.encode());
    }

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

