package org.example.model;

import org.apache.kafka.common.serialization.Deserializer;
import org.example.util.JsonUtils;

public class NetworkUsageDeserializer implements Deserializer<NetworkUsage> {
    @Override
    public NetworkUsage deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }
        String json = new String(data);
        return JsonUtils.fromJson(json, NetworkUsage.class);
    }
}
