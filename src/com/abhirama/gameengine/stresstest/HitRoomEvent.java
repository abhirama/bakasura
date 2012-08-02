package com.abhirama.gameengine.stresstest;

import com.abhirama.gameengine.Player;
import com.abhirama.gameengine.Room;
import com.abhirama.gameengine.RoomEvent;
import com.abhirama.utils.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: abhat
 * Date: 8/1/12
 * Time: 8:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class HitRoomEvent implements RoomEvent {
  @Override
  public Map execute(Data data, Room room) {
    System.out.println("Executing hit room event:" + room.getPlayers().size());
    List<Player> players = room.getPlayers();

    TestPlayer player0 = (TestPlayer)players.get(Util.getRandomInt(0, 9));
    TestPlayer player1 = (TestPlayer)players.get(Util.getRandomInt(0, 9));

    player1.setHealth(90);

    return new HashMap();
  }
}
