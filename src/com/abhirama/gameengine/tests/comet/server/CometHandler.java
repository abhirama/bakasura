package com.abhirama.gameengine.tests.comet.server;

import com.abhirama.http.GameServerHandler;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: abhat
 * This software is provided under the "DO WHAT THE HECK YOU WANT TO DO WITH THIS LICENSE"
 */
public class CometHandler extends GameServerHandler {
  @Override
  public Map gameLogic(Map data) {
    System.out.println(this.requestParameters.toString());
    return null;
  }
}
