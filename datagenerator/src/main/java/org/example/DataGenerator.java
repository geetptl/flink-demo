package org.example;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.example.model.NetworkUsage;
import org.example.model.NetworkUsageSerializer;
import org.example.model.NetworkUsageSupplier;

public class DataGenerator implements Runnable {
  private static Properties getProperties() {
    final Properties props = new Properties();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
    props.put(ProducerConfig.ACKS_CONFIG, "all");
    props.put(ProducerConfig.RETRIES_CONFIG, 0);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, NetworkUsageSerializer.class);
    return props;
  }

  private boolean isRunning = true;

  @Override
  public void run() {

    KafkaProducer<Long, NetworkUsage> producer = new KafkaProducer<>(getProperties());
    NetworkUsageSupplier nuSupplier = new NetworkUsageSupplier();
    Throttler throttler = new Throttler();

    NetworkUsageSerializer dummy = new NetworkUsageSerializer();

    int count = 0;

    while (isRunning && count < 500) {
      long millis = System.currentTimeMillis();

      NetworkUsage networkUsage = nuSupplier.get();
      ProducerRecord<Long, NetworkUsage> record =
          new ProducerRecord<>(networkUsage.topic(), null, millis, networkUsage.userId, networkUsage);
      producer.send(record);

      System.out.println("Produced record : " + new String(dummy.serialize("", networkUsage)));

      count++;

      try {
        throttler.throttle();
      } catch (InterruptedException e) {
        isRunning = false;
      }
    }

    System.out.printf("Closing producer after %d records%n", count);

    producer.close();
  }
}
