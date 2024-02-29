package org.example.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
  private static final ObjectMapper MAPPER = new ObjectMapper();

  public static <T> T fromJson(String json, Class<? extends T> cls) {
    try {
      return MAPPER.readValue(json, cls);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Json conversion error for " + json);
    }
  }

  public static <T> String toJson(T t) {
    try {
      return MAPPER.writeValueAsString(t);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Json conversion error for " + t);
    }
  }
}
