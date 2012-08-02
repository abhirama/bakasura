package com.abhirama.gameengine.tests.testOneAgainstOne;

import com.abhirama.gameengine.Player;

/**
 * Created by IntelliJ IDEA.
 * User: abhat
 * Date: 8/2/12
 * Time: 7:34 PM
 * This software is provided under the "DO WHAT THE HECK YOU WANT TO DO WITH THIS LICENSE"
 */
public class TestPlayer extends Player {
  private int health = 1000;

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
