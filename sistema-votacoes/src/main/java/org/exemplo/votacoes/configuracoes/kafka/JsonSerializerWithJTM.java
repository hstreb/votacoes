package org.exemplo.votacoes.configuracoes.kafka;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class JsonSerializerWithJTM<T> extends JsonSerializer<T> {
    public JsonSerializerWithJTM() {
        super();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
}
