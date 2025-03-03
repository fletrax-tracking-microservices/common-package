package com.fletrax.tracking.commonpackage.configuration.kafka.producer;

import com.fletrax.tracking.commonpackage.utils.kafka.producer.KafkaProducer;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaProducerConfig {

    // @Value("${spring.kafka.consumer.bootstrap-servers}")
    // private String bootstrapServers;

    @Bean
    public ProducerFactory<String, Object> producerFactoryCommon() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka-1:9090,kafka-2:9093,kafka-3:9095");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                org.apache.kafka.common.serialization.StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                org.springframework.kafka.support.serializer.JsonSerializer.class);
        configProps.put(ProducerConfig.ACKS_CONFIG, "all"); // Ensure durability
        configProps.put(ProducerConfig.RETRIES_CONFIG, Integer.MAX_VALUE);
        configProps.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplateCommon() {
        return new KafkaTemplate<>(producerFactoryCommon());
    }

    @Bean
    public KafkaProducer getKafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        return new KafkaProducer(kafkaTemplate);
    }
}
