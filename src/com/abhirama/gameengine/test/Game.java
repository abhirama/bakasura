package com.abhirama.gameengine.test;

import com.abhirama.gameengine.Room;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: abhat
 * Date: 8/1/12
 * Time: 6:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class Game {
  public static void main(String[] args) {
    Room room = Room.createRoom();

    TestPlayer testPlayer0 = new TestPlayer(0);
    TestPlayer testPlayer1 = new TestPlayer(1);

    room.addPlayer(testPlayer0);
    room.addPlayer(testPlayer1);

    HitEvent hitEvent = new HitEvent();

    List<Integer> targetIds = new LinkedList<Integer>();
    targetIds.add(1);
    hitEvent.setTargetPlayerIds(targetIds);
    hitEvent.setOriginatorId(0);

    hitEvent.setTargetPlayerIds(targetIds);

    room.apply(hitEvent);

    System.out.println("Player 0 health:" + testPlayer0.getHealth());
    System.out.println("Player 1 health:" + testPlayer0.getHealth());
  }
}
