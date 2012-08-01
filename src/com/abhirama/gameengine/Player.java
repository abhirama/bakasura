package com.abhirama.gameengine;

/**
 * Created by IntelliJ IDEA.
 * User: abhat
 * Date: 8/1/12
 * Time: 4:19 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Player {
  private int id;

  public Player(int id) {
    this.id = id;
  }

  public abstract void apply(Event event);
}
