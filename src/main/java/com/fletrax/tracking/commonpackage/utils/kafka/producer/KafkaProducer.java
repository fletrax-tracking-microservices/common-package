package com.fletrax.tracking.commonpackage.utils.kafka.producer;

import com.fletrax.tracking.commonpackage.events.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplateCommon;

    public <T extends Event> void sendEvent(T event, String topic) {
        log.info("Sending event to {}: {}", topic, event);
        Message<T> message = MessageBuilder.withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .build();
        kafkaTemplateCommon.send(message);
    }

    public void sendMessage(String topic, String key, Object message) {
        log.info("Sending message to {}: {}", topic, message);
        kafkaTemplateCommon.send(topic, key, message);
    }
}
