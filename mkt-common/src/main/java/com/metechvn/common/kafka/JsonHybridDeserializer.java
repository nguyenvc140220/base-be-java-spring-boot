package com.metechvn.common.kafka;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

@SuppressWarnings("ALL")
public class JsonHybridDeserializer<T> implements Deserializer<T> {

    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final TypeReference<T> convertType = new TypeReference<T>() {
    };

    @Override
    public void configure(Map<String, ?> props, boolean isKey) {
    }

    @Override
    public T deserialize(String topic, byte[] bytes) {
        if (bytes == null)
            return null;

        T data;
        try {
            data = OBJECT_MAPPER.readValue(bytes, convertType);
        } catch (IOException ignored) {
            try {
                return OBJECT_MAPPER.readValue(new String(bytes), convertType);
            } catch (Exception e) {
                throw new SerializationException(e);
            }
        }

        return data;
    }

    @Override
    public void close() {
    }
}