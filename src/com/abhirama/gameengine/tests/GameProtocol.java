package com.abhirama.gameengine.tests;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: abhat
 * Date: 8/2/12
 * Time: 7:28 PM
 * This software is provided under the "DO WHAT THE HECK YOU WANT TO DO WITH THIS LICENSE"
 */
public class GameProtocol {
  public static final String COMMAND = "command";
  public static final String CREATE_ROOM_COMMAND = "createRoom";
  public static final String PLAYER_ID = "playerId";
  
  public static final String JOIN_ROOM_COMMAND = "joinRoom";
  public static final String ROOM_ID = "roomId";
  
  public static final String ATTACK_COMMAND = "attack";
  public static final String ORIGINATOR_ID = "originatorId";
  public static final String TARGET_IDS = "targetIds";

  public static final String HEALTH = "health";
  
  public static final String ORIGINATOR = "originator";
  public static final String TARGET = "target";
  
  public static final String CALLBACK = "callback";
  public static final String MESSAGE = "message";
  
  public static final String ACTION = "action";
  
  public static final String SEND_MESSAGE_ACTION = "sendMessage";
  public static final String RECEIVE_MESSAGE_ACTION = "receiveMessage";

  public static String getCallBack(Map<String, List<String>> data) {
    return data.get(GameProtocol.CALLBACK).get(0);
  }

  public static String getMessage(Map<String, List<String>> data) {
    return data.get(GameProtocol.MESSAGE).get(0);
  }

  public static final boolean isSendMessage(Map<String, List<String>> data) {
    return SEND_MESSAGE_ACTION.equals(getAction(data));
  }

  public static final boolean isReceiveMessage(Map<String, List<String>> data) {
    return RECEIVE_MESSAGE_ACTION.equals(getAction(data));
  }

  public static String getAction(Map<String, List<String>> data) {
    return data.get(GameProtocol.ACTION).get(0);
  }

  public static boolean isCreateRoomCommand(Map data) {
    return getCommand(data).equals(GameProtocol.CREATE_ROOM_COMMAND);
  }

  public static boolean isJoinRoomCommand(Map<String, List<String>> data) {
    return getCommand(data).equals(GameProtocol.JOIN_ROOM_COMMAND);
  }
  
  public static boolean isAttackCommand(Map<String, List<String>> data) {
    return getCommand(data).equals(GameProtocol.ATTACK_COMMAND);
  }
  
  public static int getId(Map<String, List<String>> data, String identifier) {
    return Integer.parseInt(data.get(identifier).get(0));
  }

  public static int getPlayerId(Map<String, List<String>> data) {
    return getId(data, GameProtocol.PLAYER_ID);
  }

  public static int getRoomId(Map<String, List<String>> data) {
    return getId(data, GameProtocol.ROOM_ID);
  }
  
  public static int getOriginatorId(Map<String, List<String>> data) {
    return getId(data, GameProtocol.ORIGINATOR_ID);
  }
  
  public static int getTargetId(Map<String, List<String>> data) {
    return getId(data, GameProtocol.TARGET_IDS);
  }

  public static String getCommand(Map data) {
    if (data.containsKey(GameProtocol.COMMAND)) {
      return (String)((List)data.get(GameProtocol.COMMAND)).get(0);
    }

    return "";
  }
}
