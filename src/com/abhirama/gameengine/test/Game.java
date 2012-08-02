package com.abhirama.gameengine.test;

import com.abhirama.gameengine.Room;
import com.abhirama.utils.Util;
import com.sun.xml.internal.messaging.saaj.util.TeeInputStream;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: abhat
 * Date: 8/1/12
 * Time: 6:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class Game {
  public static void init() {
/*    Room room = Room.createRoom();

    TestPlayer testPlayer0 = new TestPlayer(0);
    TestPlayer testPlayer1 = new TestPlayer(1);

    room.addPlayer(testPlayer0);
    room.addPlayer(testPlayer1);

    HitEvent hitEvent = new HitEvent();
    HitRoomEvent hitRoomEvent = new HitRoomEvent();

    room.setRoomEvent(hitRoomEvent);

    room.executeRoomEvent(hitEvent);

    System.out.println("Player 0 health:" + testPlayer0.getHealth());
    System.out.println("Player 1 health:" + testPlayer1.getHealth());*/

    ArrayList<Room> rooms = new ArrayList<Room>();

    for (int i = 0; i < 1000; ++i) {
      Room room = Room.createRoom();
      rooms.add(room);
    }

    int count = 0;
    for (Room room : rooms) {
      for (int i = 0; i < 100; ++i) {
        TestPlayer testPlayer = new TestPlayer(count++);
        room.addPlayer(testPlayer);
      }
    }
  }
}
