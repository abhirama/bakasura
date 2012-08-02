package com.abhirama.gameengine;

import com.abhirama.gameengine.stresstest.Data;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: abhat
 * Date: 8/1/12
 * Time: 7:19 PM
 * To change this template use File | Settings | File Templates.
 */
public interface RoomEvent {
  public Map execute(Data data, Room room);
}
