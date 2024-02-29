package org.example;

import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.example.model.NetworkUsage;
import org.example.model.NetworkUsageDeserializer;

import static org.apache.flink.table.api.Expressions.$;

public class DataProcessor {

    public void job() {
        final StreamExecutionEnvironment env =
                StreamExecutionEnvironment.getExecutionEnvironment();
        env.setRuntimeMode(RuntimeExecutionMode.STREAMING);

        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);

        KafkaSource<NetworkUsage> networkUsageSource =
                KafkaSource.<NetworkUsage>builder()
                        .setBootstrapServers("kafka:9092")
                        .setTopics("network_usage")
                        .setStartingOffsets(OffsetsInitializer.earliest())
                        .setDeserializer(KafkaRecordDeserializationSchema.valueOnly(NetworkUsageDeserializer.class))
                        .build();

        DataStream<NetworkUsage> networkUsageStream =
                env.fromSource(networkUsageSource, WatermarkStrategy.noWatermarks(), "network_usage");

        Table results =
                tEnv.fromDataStream(networkUsageStream)
                        .groupBy($("userId"))
                        .select(
                                $("userId"),
                                $("bytesConsumed").sum().as("totalUsage"),
                                $("timestamp").max().as("lastActive"));

        results.execute().print();
    }
}
