package org.example.model;

import lombok.ToString;

@ToString
public class NetworkUsage {
  public long userId;
  public long bytesConsumed;
  public long timestamp;
}
