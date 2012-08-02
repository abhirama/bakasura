package com.abhirama.gameengine.stresstest;

import com.abhirama.gameengine.Player;

/**
 * Created by IntelliJ IDEA.
 * User: abhat
 * Date: 8/1/12
 * Time: 6:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestPlayer extends Player {
  private int health = 100;

  public TestPlayer(int id) {
    super(id);
  }

  public int getHealth() {
    return health;
  }

  public void setHealth(int health) {
    this.health = health;
  }
}
