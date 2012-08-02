/**
 * Created by IntelliJ IDEA.
 * User: abhat
 * This software is provided under the "DO WHAT THE HECK YOU WANT TO DO WITH THIS LICENSE"
 */
package com.abhirama.gameengine.tests;

import com.abhirama.gameengine.Player;

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

  public String toString() {
    return String.format("Player{Id - %d, Health - %d", this.getId(), this.getHealth());
  }
}
