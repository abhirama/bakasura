package com.abhirama.gameengine.tests.testOneAgainstOne;

import com.abhirama.gameengine.Room;
import com.abhirama.gameengine.tests.GameProtocol;
import com.abhirama.gameengine.tests.PlayerStore;
import com.abhirama.gameengine.tests.TestPlayer;
import com.abhirama.http.GameServerHandler;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.*;

import java.util.HashMap;
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

    this.setKeepAlive(false);
    
    Map<String, String> responseHeaders = new HashMap<String, String>();
    responseHeaders.put(CONTENT_TYPE, "text/plain; charset=UTF-8");

    this.setResponseHeaders(responseHeaders);
    
    //http://localhost:8080/?command=createRoom&playerId=1
    if (GameProtocol.isCreateRoomCommand(data)) {
      Room room = Room.createRoom();
      int playerId = GameProtocol.getPlayerId(data);
      TestPlayer testPlayer = new TestPlayer(playerId);

      PlayerStore.add(playerId, testPlayer);

      room.addPlayer(testPlayer);
      this.addToOp("Room " + room.getId() + " created by player " + testPlayer.getId());
    }

    //http://localhost:8080/?command=joinRoom&roomId=1001&playerId=2
    if (GameProtocol.isJoinRoomCommand(data)) {
      int roomId = GameProtocol.getRoomId(data);
      int playerId = GameProtocol.getPlayerId(data);
      
      Room room = Room.getRoom(roomId);

      TestPlayer testPlayer = new TestPlayer(playerId);

      PlayerStore.add(playerId, testPlayer);
      room.addPlayer(testPlayer);
      this.addToOp("Player " + playerId + " joined room " + room.getId() + ". Currently room has " + room.getPlayers().toString());
    }

    //http://localhost:8080/?command=attack&roomId=1001&originatorId=1&targetIds=2
    if (GameProtocol.isAttackCommand(data)) {
      int roomId = GameProtocol.getRoomId(data);
      Room room = Room.getRoom(roomId);
      
      int originatorId = GameProtocol.getOriginatorId(data);
      TestPlayer originator = (TestPlayer)PlayerStore.get(originatorId);
      
      int targetId = GameProtocol.getTargetId(data);
      TestPlayer target = ((TestPlayer) PlayerStore.get(targetId));

      target.setHealth(90);

      this.addToOp(originator + " hit " + target + " in " + room);
    }

    return null;
  }

}
