package org.example.model;

import java.util.Random;
import java.util.function.Supplier;

public class NetworkUsageSupplier implements Supplier<NetworkUsage> {

  private static final Random RANDOM = new Random();

  @Override
  public NetworkUsage get() {
    NetworkUsage networkUsage = new NetworkUsage();
    networkUsage.userId = RANDOM.nextLong(1L, 10L);
    networkUsage.bytesConsumed = RANDOM.nextLong(160L, 240L);
    networkUsage.timestamp = System.currentTimeMillis();
    return networkUsage;
  }
}
