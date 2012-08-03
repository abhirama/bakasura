package com.abhirama.gameengine.tests.comet.server;

import com.abhirama.gameengine.Room;
import com.abhirama.gameengine.tests.TestPlayer;
import com.abhirama.gameengine.tests.stresstest.CustomGameServerHandler;
import com.abhirama.http.GameServer;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: abhat
 * This software is provided under the "DO WHAT THE HECK YOU WANT TO DO WITH THIS LICENSE"
 */
public class Game {
  public static void init() {
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

  public static void main(String[] args) {
    Game.init();
    GameServer gameServer = new GameServer(8080, CometHandler.class);
    gameServer.run();
  }
}

