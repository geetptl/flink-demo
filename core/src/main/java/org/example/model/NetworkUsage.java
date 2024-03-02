package org.example.model;

import lombok.ToString;

@ToString
public class NetworkUsage extends KafkaMessage {
  public long userId;
  public long bytesConsumed;
  public long timestamp;

  @Override
  public String topic() {
    return "network_usage";
  }
}
