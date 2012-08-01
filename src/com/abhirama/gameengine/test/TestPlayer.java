package com.abhirama.gameengine.test;

import com.abhirama.gameengine.Event;
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

  @Override
  public void apply(Event event) {
    if (event instanceof HitEvent) {
      apply((HitEvent)event);
    }
  }

  public void apply(HitEvent hitEvent) {
    if (hitEvent.getOriginatorId() == 0) {
      this.health = this.health - 10;
    }
  }

  public int getHealth() {
    return health;
  }

  public void setHealth(int health) {
    this.health = health;
  }
}
