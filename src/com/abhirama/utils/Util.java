package com.abhirama.utils;

/**
 * Created by IntelliJ IDEA.
 * User: abhat
 * Date: 8/2/12
 * Time: 2:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class Util {
  public static int getRandomInt(int min, int max) {
    return min + (int)(Math.random() * ((max - min) + 1));
  }
}
