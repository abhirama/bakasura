/**
 * Created by IntelliJ IDEA.
 * User: abhat
 * This software is provided under the "DO WHAT THE HECK YOU WANT TO DO WITH THIS LICENSE"
 */
package com.abhirama.gameengine.tests.stresstest;

import com.abhirama.gameengine.Room;
import com.abhirama.http.GameServerHandler;
import com.abhirama.utils.Util;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CustomGameServerHandler extends GameServerHandler {

  @Override
  public Map gameLogic(Map data) {
    //Simulate a memcache fetch
    try {
      TimeUnit.MILLISECONDS.sleep(2);
    } catch (InterruptedException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
    Room room = Room.getRoom(Util.getRandomInt(1, 1000));

    HitEvent hitEvent = new HitEvent();
    HitRoomEvent hitRoomEvent = new HitRoomEvent();

    room.setRoomEvent(hitRoomEvent);

    room.executeRoomEvent(hitEvent);
    //Simulate a memcache fetch
    try {
      TimeUnit.MILLISECONDS.sleep(2);
    } catch (InterruptedException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }

    this.addToOp("I am inside custom game server handler" + this.requestParameters.toString());

    return null;
  }
}
