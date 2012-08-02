package com.abhirama.gameengine.tests.testOneAgainstOne;

import com.abhirama.gameengine.Room;
import com.abhirama.http.GameServerHandler;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: abhat
 * Date: 8/2/12
 * Time: 7:36 PM
 * This software is provided under the "DO WHAT THE HECK YOU WANT TO DO WITH THIS LICENSE"
 */
public class OneAgainstOneGameServerHandler extends GameServerHandler {
  @Override
  public Map gameLogic(Map data) {
    //todo hack to get the data now
    data = this.requestParameters;

    this.setKeepAlive(true);
    if (this.isCreateRoomCommand(data)) {
      Room room = Room.createRoom();
      TestPlayer testPlayer = new TestPlayer(this.getPlayerId(data));
      room.addPlayer(testPlayer);
      this.addToOp("Room " + room.getId() + " created by player " + testPlayer.getId());
    }

    return null;
  }

  public String getCommand(Map data) {
    if (data.containsKey(GameProtocol.COMMAND)) {
      return (String)((List)data.get(GameProtocol.COMMAND)).get(0);
    }
    
    return "";
  }

  public boolean isCreateRoomCommand(Map data) {
    return this.getCommand(data).equals(GameProtocol.CREATE_ROOM_COMMAND);
  }
  
  public int getPlayerId(Map<String, List<String>> data) {
    return Integer.parseInt(data.get(GameProtocol.ORIGIN_PLAYER_ID).get(0));
  }
      
}
