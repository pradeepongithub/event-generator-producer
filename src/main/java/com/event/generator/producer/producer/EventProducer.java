package com.event.generator.producer.producer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.kafka.core.KafkaTemplate;
import com.event.generator.producer.model.Employee;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Slf4j
public class EventProducer {

    private static final String CRON_EXP ="0/30 * * * * ?";

    private static final String EVENT_NAME = "card_swipe";

    private static final ObjectWriter EVENT_MESSAGE_WRITER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .setSerializationInclusion(JsonInclude.Include.NON_NULL).writerFor(Employee.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final String topic;

    public EventProducer(KafkaTemplate<String, String> kafkaTemplate, @Value("${kafka.topic}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Scheduled(cron = CRON_EXP)
    public void publishEvent() {
        String key = UUID.randomUUID().toString();
        Employee employeeEventData = Employee.builder().name("xxxxx").dept("OSS").id(key).time(LocalDateTime.now()).build();

        try {
            String payload = EVENT_MESSAGE_WRITER.writeValueAsString(employeeEventData);
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, payload);
            record.headers().add("eventType", EVENT_NAME.getBytes());
            log.info("sending payload {} to topic {}", payload, topic);

            kafkaTemplate.send(record).completable();

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to send the message for event : " + EVENT_NAME + " on topic for key: " + key + " and the error is " + e.getMessage());
        }
    }

}