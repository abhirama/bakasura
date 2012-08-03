package com.abhirama.gameengine.tests.comet.server;

import com.abhirama.gameengine.tests.GameProtocol;
import com.abhirama.http.GameServerHandler;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: abhat
 * This software is provided under the "DO WHAT THE HECK YOU WANT TO DO WITH THIS LICENSE"
 */
public class CometHandler extends GameServerHandler {
  @Override
  public Map gameLogic(Map data) {
    Map<String, List<String>> params = this.requestParameters;

    System.out.println("Callback is:" + GameProtocol.getCallBack(params));
    System.out.println("Message is:" + GameProtocol.getMessage(params));

    return null;
  }
}
