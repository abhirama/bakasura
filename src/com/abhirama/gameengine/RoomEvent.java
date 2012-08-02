/**
 * Created by IntelliJ IDEA.
 * User: abhat
 * This software is provided under the "DO WHAT THE HECK YOU WANT TO DO WITH THIS LICENSE"
 */
package com.abhirama.gameengine;

import com.abhirama.gameengine.tests.stresstest.Data;

import java.util.Map;

public interface RoomEvent {
  public Map execute(Data data, Room room);
}
