package com.abhirama.gameengine;

import com.abhirama.gameengine.stresstest.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by IntelliJ IDEA.
 * User: abhat
 * Date: 8/1/12
 * Time: 4:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class Room {
  private static AtomicInteger roomId = new AtomicInteger();

  private static Map<Integer, Room> registeredRooms = new ConcurrentHashMap<Integer, Room>();

  private List<Player> players = new LinkedList<Player>();
  
  private int id;

  private RoomEvent roomEvent;

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

  public void executeRoomEvent(Data data) {
    this.getRoomEvent().execute(data, this);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public RoomEvent getRoomEvent() {
    return roomEvent;
  }

  public void setRoomEvent(RoomEvent roomEvent) {
    this.roomEvent = roomEvent;
  }

  public List<Player> getPlayers() {
    return players;
  }

  public void setPlayers(List<Player> players) {
    this.players = players;
  }
}
