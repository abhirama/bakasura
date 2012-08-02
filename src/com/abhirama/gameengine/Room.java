/**
 * Created by IntelliJ IDEA.
 * User: abhat
 * This software is provided under the "DO WHAT THE HECK YOU WANT TO DO WITH THIS LICENSE"
 */
package com.abhirama.gameengine;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


public class Room {
  private static AtomicInteger roomId = new AtomicInteger();

  private static Map<Integer, Room> registeredRooms = new ConcurrentHashMap<Integer, Room>();

  private List<Player> players = new LinkedList<Player>();
  
  private int id;

  private Room() {
  }
  
  public static Room createRoom() {
    Room room = new Room();
    int id = roomId.incrementAndGet();
    room.setId(id);
    registeredRooms.put(id, room);
    return room;
  }

  public static Room getRoom(int roomId) {
    return registeredRooms.get(roomId);
  }
  
  public void addPlayer(Player player) {
    this.players.add(player);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public List<Player> getPlayers() {
    return players;
  }

  public void setPlayers(List<Player> players) {
    this.players = players;
  }
}
