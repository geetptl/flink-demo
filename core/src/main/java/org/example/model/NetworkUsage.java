package org.example.model;

public class NetworkUsage {
  public long userId;
  public long bytesConsumed;
  public long timestamp;

  @Override
  public String toString() {
    return "NetworkUsage{"
        + "userId="
        + userId
        + ", bytesConsumed="
        + bytesConsumed
        + ", timestamp="
        + timestamp
        + '}';
  }
}
