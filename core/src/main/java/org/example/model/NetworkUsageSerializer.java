package org.example.model;

import org.apache.kafka.common.serialization.Serializer;
import org.example.util.JsonUtils;

public class NetworkUsageSerializer implements Serializer<NetworkUsage> {
    @Override
    public byte[] serialize(String s, NetworkUsage networkUsage) {
        String json = JsonUtils.toJson(networkUsage);
        return json.getBytes();
    }
}
