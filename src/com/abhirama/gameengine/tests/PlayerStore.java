/**
 * Created by IntelliJ IDEA.
 * User: abhat
 * This software is provided under the "DO WHAT THE HECK YOU WANT TO DO WITH THIS LICENSE"
 */
package com.abhirama.gameengine.tests;

import com.abhirama.gameengine.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerStore {
  private static Map<Integer, Player> players = new HashMap<Integer, Player>();
  
  public static void add(int playerId, Player player) {
    players.put(playerId, player);
  }
  
  public static Player get(int playerId) {
    return players.get(playerId);
  }
  
}
