package com.abhirama.gameengine.tests.comet.server;

import com.abhirama.gameengine.Room;
import com.abhirama.gameengine.tests.TestPlayer;
import com.abhirama.gameengine.tests.stresstest.CustomGameServerHandler;
import com.abhirama.http.GameServer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: abhat
 * This software is provided under the "DO WHAT THE HECK YOU WANT TO DO WITH THIS LICENSE"
 */
public class Game {
  public static List<String> messages = new LinkedList<String>();

  public static void main(String[] args) {
    GameServer gameServer = new GameServer(8080, CometHandler.class);
    gameServer.run();
  }
}

