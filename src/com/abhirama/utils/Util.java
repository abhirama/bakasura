/**
 * Created by IntelliJ IDEA.
 * User: abhat
 * This software is provided under the "DO WHAT THE HECK YOU WANT TO DO WITH THIS LICENSE"
 */
package com.abhirama.utils;

public class Util {
  public static int getRandomInt(int min, int max) {
    return min + (int)(Math.random() * ((max - min) + 1));
  }
}
