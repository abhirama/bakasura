package com.abhirama.gameengine.tests.testOneAgainstOne;

import com.abhirama.http.GameServer;

/**
 * Created by IntelliJ IDEA.
 * User: abhat
 * Date: 8/2/12
 * Time: 7:53 PM
 * This software is provided under the "DO WHAT THE HECK YOU WANT TO DO WITH THIS LICENSE"
 */
public class Game {
  public static void main(String[] args) {
    GameServer gameServer = new GameServer(8080, OneAgainstOneGameServerHandler.class);
    gameServer.run();
  }
}
