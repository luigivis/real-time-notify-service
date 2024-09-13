package com.luigivismara.modeldomain.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service to produce messages to Kafka topics with optional support for custom headers, keys, and correlation IDs.
 *
 * <p>
 * This service provides methods to send messages asynchronously to Kafka topics, including methods for adding headers
 * and specifying a key and correlation ID.
 * </p>
 *
 * <p><b>Usage Examples:</b></p>
 * <pre>
 * {@code
 * KafkaProducerService kafkaProducerService = new KafkaProducerService(kafkaTemplate);
 *
 * // Sending a simple message
 * kafkaProducerService.sendMessage("user-topic", userObject);
 *
 * // Sending a message with key and additional headers
 * List<Header> headers = kafkaProducerService.createHeaders("custom-header", "value1");
 * kafkaProducerService.sendMessageWithHeaders("user-topic", "user-key", userObject, "correlation-123", headers);
 *
 * // Sending a message with automatically generated correlation ID
 * kafkaProducerService.sendMessageWithHeaders("user-topic", "user-key", userObject);
 * }
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * Sends a message asynchronously to the specified Kafka topic.
     *
     * @param topic   the Kafka topic to which the message will be sent
     * @param message the message to send
     */
    @Async("kafka")
    public void sendMessage(String topic, Object message) {
        log.info("Sending message {} to topic {}", message, topic);
        kafkaTemplate.send(topic, message);
    }

    /**
     * Sends a message asynchronously to the specified Kafka topic, along with a key, correlation ID, and additional headers.
     *
     * @param topic             the Kafka topic to which the message will be sent
     * @param key               the key to associate with the message (can be used for partitioning)
     * @param message           the message to send
     * @param correlationId     the correlation ID (can be used for tracking requests)
     * @param additionalHeaders additional headers to add to the message
     */
    @Async("kafka")
    public void sendMessageWithHeaders(final String topic, final String key, final Object message, String correlationId, final List<Header> additionalHeaders) {
        if (correlationId == null || correlationId.isEmpty()) {
            correlationId = UUID.randomUUID().toString();
        }

        final var headers = new ArrayList<Header>(additionalHeaders);
        headers.add(new RecordHeader("correlation_id", correlationId.getBytes(StandardCharsets.UTF_8)));
        final var record = new ProducerRecord<>(topic, null, key, message, headers);

        log.info("Sending message with key {} and headers to topic {}: {}", key, topic, headers);
        kafkaTemplate.send(record);
    }

    /**
     * Sends a message asynchronously to the specified Kafka topic with a key and automatically generated correlation ID.
     *
     * @param topic   the Kafka topic to which the message will be sent
     * @param key     the key to associate with the message (can be used for partitioning)
     * @param message the message to send
     */
    @Async("kafka")
    public void sendMessageWithHeaders(final String topic, final String key, final Object message) {
        sendMessageWithHeaders(topic, key, message, null, new ArrayList<>());
    }

    /**
     * Sends a message asynchronously to the specified Kafka topic with headers but no key.
     *
     * @param topic   the Kafka topic to which the message will be sent
     * @param message the message to send
     * @param headers the list of headers to attach to the message
     */
    @Async("kafka")
    public void sendMessageWithHeaders(String topic, Object message, List<Header> headers) {
        String correlationId = UUID.randomUUID().toString();
        headers.add(new RecordHeader("correlation_id", correlationId.getBytes(StandardCharsets.UTF_8)));

        var record = new ProducerRecord<String, Object>(topic, null, null, message, headers);

        log.info("Sending message {} with headers no key to topic {}: {}", message, topic, headers);
        kafkaTemplate.send(record);
    }

    /**
     * Sends a message asynchronously to the specified Kafka topic with a key but no headers.
     *
     * @param topic   the Kafka topic to which the message will be sent
     * @param key     the key to associate with the message
     * @param message the message to send
     */
    @Async("kafka")
    public void sendMessageWithKey(final String topic, final String key, final Object message) {
        final var record = new ProducerRecord<String, Object>(topic, key, message);

        log.info("Sending message with key {} to topic {}", key, topic);
        kafkaTemplate.send(record);
    }

    /**
     * Creates a list of Kafka headers with a single key-value pair.
     *
     * @param key   the header key
     * @param value the header value
     * @return a list of Kafka headers
     */
    public List<Header> createHeaders(final String key, final String value) {
        final var headers = new ArrayList<Header>();
        headers.add(new RecordHeader(key, value.getBytes(StandardCharsets.UTF_8)));
        return headers;
    }

    /**
     * Creates a list of Kafka headers from an array of custom header objects.
     *
     * @param headers an array of {@link Headers} objects representing the key-value pairs
     * @return a list of Kafka headers
     */
    public List<Header> createHeaders(Headers... headers) {
        return Arrays.stream(headers)
                .map(h -> new RecordHeader(h.getKey(), h.getValue().getBytes(StandardCharsets.UTF_8)))
                .collect(Collectors.toList());
    }

    /**
     * A class representing a key-value pair for Kafka headers.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Headers {
        private String key;
        private String value;

        /**
         * Factory method to create a new {@code Headers} object.
         *
         * @param key   the header key
         * @param value the header value
         * @return a new {@code Headers} object
         */
        public static Headers of(final String key, final String value) {
            return new Headers(key, value);
        }
    }
}
