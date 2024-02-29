package org.example;

import java.util.Random;

public class Throttler {
  public static final Random RANDOM = new Random();

  public void throttle() throws InterruptedException {
    Thread.sleep(RANDOM.nextLong(250, 750) * 2);
  }
}
