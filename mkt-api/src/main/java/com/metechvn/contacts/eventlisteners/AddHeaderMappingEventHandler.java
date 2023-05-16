package com.metechvn.contacts.eventlisteners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.metechvn.contacts.commands.AddHeaderMappingCommand;
import com.metechvn.contacts.events.AddHeaderMappingEvent;
import com.metechvn.tenancy.TenantIdentifierResolver;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
public class AddHeaderMappingEventHandler implements ApplicationListener<AddHeaderMappingEvent> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final TenantIdentifierResolver currentTenantProvider;

    @Override
    public void onApplicationEvent(AddHeaderMappingEvent event) {
        if (!(event.getSource() != null && event.getSource() instanceof AddHeaderMappingCommand de)) return;
        de.getHeaders().put("tenant", currentTenantProvider.resolveCurrentTenantIdentifier());
        try {
            kafkaTemplate
                    .send("MKT.BE.ImportExcelRequest", de.getHeaders())
                    .get();
            log.debug("Sent entity {} created to kafka", objectMapper.writeValueAsString(de.getHeaders()));
        } catch (InterruptedException | ExecutionException | JsonProcessingException e) {
            log.error("Cannot send message to kafka. Trace {}", e.getMessage());
        }
    }
}