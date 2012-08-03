package com.abhirama.gameengine.tests.comet.server;

import com.abhirama.http.GameServer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: abhat
 * This software is provided under the "DO WHAT THE HECK YOU WANT TO DO WITH THIS LICENSE"
 */
public class Game {
  public static List<String> messages = new CopyOnWriteArrayList<String>();

  public static void main(String[] args) {
    GameServer gameServer = new GameServer(8080, CometHandler.class);
    gameServer.run();
  }
}

