package com.metechvn.common.kafka;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;

import java.util.HashMap;
import java.util.Map;

public class SerdeFactory {

    private SerdeFactory() {
    }

    public static <T> Serde<T> serdeFor(Class<T> clazz, boolean isKey) {
        Map<String, Object> serdeProps = new HashMap<>();
        serdeProps.put("serializedClass", clazz);

        Serializer<T> ser = new JsonPojoSerializer<>();
        ser.configure(serdeProps, isKey);

        Deserializer<T> de = new JsonHybridDeserializer<>();
        de.configure(serdeProps, isKey);

        return Serdes.serdeFrom(ser, de);
    }

}