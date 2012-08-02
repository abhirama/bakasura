package com.abhirama.gameengine.stresstest;

import com.abhirama.gameengine.Room;
import com.abhirama.http.GameServerHandler;
import com.abhirama.utils.Util;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: abhat
 * Date: 8/2/12
 * Time: 5:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class CustomGameServerHandler extends GameServerHandler {

  @Override
  public Map gameLogic(Map data) {
    Room room = Room.getRoom(Util.getRandomInt(1, 1000));

    HitEvent hitEvent = new HitEvent();
    HitRoomEvent hitRoomEvent = new HitRoomEvent();

    room.setRoomEvent(hitRoomEvent);

    room.executeRoomEvent(hitEvent);

    this.addToOp("I am inside custom game server handler" + this.requestParameters.toString());

    return null;
  }
}
