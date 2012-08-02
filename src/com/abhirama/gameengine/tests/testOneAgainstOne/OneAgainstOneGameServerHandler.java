package com.abhirama.gameengine.tests.testOneAgainstOne;

import com.abhirama.gameengine.Room;
import com.abhirama.gameengine.tests.GameProtocol;
import com.abhirama.gameengine.tests.PlayerStore;
import com.abhirama.gameengine.tests.TestPlayer;
import com.abhirama.http.GameServerHandler;
import flexjson.JSONSerializer;

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
    responseHeaders.put(CONTENT_TYPE, "application/json");

    this.setResponseHeaders(responseHeaders);
    
    //http://localhost:8080/?command=createRoom&playerId=1
    if (GameProtocol.isCreateRoomCommand(data)) {
      Room room = Room.createRoom();
      int playerId = GameProtocol.getPlayerId(data);
      TestPlayer testPlayer = new TestPlayer(playerId);

      PlayerStore.add(playerId, testPlayer);

      room.addPlayer(testPlayer);

      Map map = new HashMap();
      map.put(GameProtocol.ROOM_ID, room.getId());
      map.put(GameProtocol.PLAYER_ID, testPlayer.getId());
      
      this.addToOp(map);
    }

    //http://localhost:8080/?command=joinRoom&roomId=1001&playerId=2
    if (GameProtocol.isJoinRoomCommand(data)) {
      int roomId = GameProtocol.getRoomId(data);
      int playerId = GameProtocol.getPlayerId(data);
      
      Room room = Room.getRoom(roomId);

      TestPlayer testPlayer = new TestPlayer(playerId);

      PlayerStore.add(playerId, testPlayer);
      room.addPlayer(testPlayer);

      Map map = new HashMap();
      map.put(GameProtocol.ROOM_ID, room.getId());
      map.put(GameProtocol.PLAYER_ID, testPlayer.getId());
      map.put("roomState", room.getPlayers());

      this.addToOp(map);
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
      
      Map map = new HashMap();

      Map map0 = new HashMap();
      map0.put(GameProtocol.PLAYER_ID, originator.getId());
      map0.put(GameProtocol.HEALTH, originator.getHealth());
      
      map.put(GameProtocol.ORIGINATOR_ID, map0);
      
      Map map1 = new HashMap();
      map1.put(GameProtocol.PLAYER_ID, target.getId());
      map1.put(GameProtocol.HEALTH, target.getHealth());
      
      map.put(GameProtocol.TARGET_IDS, map1);
      map.put(GameProtocol.ROOM_ID, room.getId());

      this.addToOp(map);
    }

    return null;
  }
  
  private void addToOp(Map map) {
    JSONSerializer serializer = new JSONSerializer();
    String op = serializer.exclude("*.class").deepSerialize(map);
    this.addToOp(op);
  }

}
