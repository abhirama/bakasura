package com.abhirama.gameengine;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: abhat
 * Date: 8/1/12
 * Time: 6:03 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Event {
  protected Map data = new HashMap();
  protected List<Integer> targetPlayerIds = new LinkedList<Integer>();
  protected int originatorId;

  public Map getData() {
    return data;
  }

  public void setData(Map data) {
    this.data = data;
  }

  public void setTargetPlayerIds(List<Integer> targetPlayerIds) {
    this.targetPlayerIds = targetPlayerIds;
  }

  public List<Integer> getTargetPlayerIds() {
    return targetPlayerIds;
  }

  public int getOriginatorId() {
    return originatorId;
  }

  public void setOriginatorId(int originatorId) {
    this.originatorId = originatorId;
  }

  public abstract void apply(Player player);
}
