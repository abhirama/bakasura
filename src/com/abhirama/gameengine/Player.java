/**
 * Created by IntelliJ IDEA.
 * User: abhat
 * This software is provided under the "DO WHAT THE HECK YOU WANT TO DO WITH THIS LICENSE"
 */
package com.abhirama.gameengine;

public abstract class Player {
  private int id;

  public Player(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }
}
