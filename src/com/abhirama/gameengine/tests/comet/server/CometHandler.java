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
    //this.setKeepAlive(false);
    this.clearOp();

    Map<String, List<String>> params = this.requestParameters;

    if (GameProtocol.isSendMessage(params)) {
      String message = GameProtocol.getMessage(params);
      Game.messages.add(message);
    } else {
      String callBack = GameProtocol.getCallBack(params);
      String op = String.format("%s('%s')", callBack, "");
      if (!Game.messages.isEmpty()) {
        op = String.format("%s('%s')", callBack, Game.messages.get(0));
        Game.messages.remove(0);
      }
      this.addToOp(op);
    }
    return null;
  }
}
