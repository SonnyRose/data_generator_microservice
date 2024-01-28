package org.example.config;


import com.jcabi.xml.XML;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.TopicConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import org.mockito.MockitoAnnotations;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

import java.time.Duration;


import static org.junit.jupiter.api.Assertions.*;


@RequiredArgsConstructor
public class KafkaConfigTest {
    @Mock
    private XML settings;
    @InjectMocks
    private KafkaConfig kafkaConfig;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

    }



    @Test
    void testCreateTemperatureTopic() {
        NewTopic temperatureTopic = kafkaConfig.temperatureTopic();
        assertEquals("data-temperature", temperatureTopic.name());
        assertEquals(5, temperatureTopic.numPartitions());
        assertEquals(1, temperatureTopic.replicationFactor());
        assertEquals(
                String.valueOf(Duration.ofDays(7).toMillis()),
                temperatureTopic.configs().get(TopicConfig.RETENTION_MS_CONFIG)
        );
    }

    @Test
    void testCreateVoltageTopic() {
        NewTopic voltageTopic = kafkaConfig.voltageTopic();
        assertEquals("data-voltage", voltageTopic.name());
        assertEquals(1, voltageTopic.numPartitions());
        assertEquals(5, voltageTopic.replicationFactor());
        assertEquals(
                String.valueOf(Duration.ofDays(7).toMillis()),
                voltageTopic.configs().get(TopicConfig.RETENTION_MS_CONFIG)
        );
    }

    @Test
    void testCreatePowerTopic() {
        NewTopic powerTopic = kafkaConfig.powerTopic();
        assertEquals("data-power", powerTopic.name());
        assertEquals(5, powerTopic.numPartitions());
        assertEquals(1, powerTopic.replicationFactor());
        assertEquals(
                String.valueOf(Duration.ofDays(7).toMillis()),
                powerTopic.configs().get(TopicConfig.RETENTION_MS_CONFIG)
        );
    }

    @Test
    void testSenderCreationWithNullOptionsThrowsException() {
        assertThrows(NullPointerException.class, () -> kafkaConfig.sender(null));
    }

    @Test
    void testSenderOptionsPassedToSender() {
        SenderOptions<String, Object> senderOptions = kafkaConfig.senderOptions();
        KafkaSender<String, Object> kafkaSender = kafkaConfig.sender(senderOptions);

        assertNotNull(kafkaSender);
        // You can add more specific assertions based on the behavior of KafkaSender
    }

    @Test
    void testTopicNames() {
        NewTopic temperatureTopic = kafkaConfig.temperatureTopic();
        NewTopic voltageTopic = kafkaConfig.voltageTopic();
        NewTopic powerTopic = kafkaConfig.powerTopic();

        assertEquals("data-temperature", temperatureTopic.name());
        assertEquals("data-voltage", voltageTopic.name());
        assertEquals("data-power", powerTopic.name());
    }

    @Test
    void testTopicPartitions() {
        NewTopic temperatureTopic = kafkaConfig.temperatureTopic();
        NewTopic voltageTopic = kafkaConfig.voltageTopic();
        NewTopic powerTopic = kafkaConfig.powerTopic();

        assertEquals(5, temperatureTopic.numPartitions());
        assertEquals(1, voltageTopic.numPartitions());
        assertEquals(5, powerTopic.numPartitions());
    }

    @Test
    void testTopicReplicas() {
        NewTopic temperatureTopic = kafkaConfig.temperatureTopic();
        NewTopic voltageTopic = kafkaConfig.voltageTopic();
        NewTopic powerTopic = kafkaConfig.powerTopic();

        assertEquals(1, temperatureTopic.replicationFactor());
        assertEquals(5, voltageTopic.replicationFactor());
        assertEquals(1, powerTopic.replicationFactor());
    }

    @Test
    void testTopicRetention() {
        NewTopic temperatureTopic = kafkaConfig.temperatureTopic();
        NewTopic voltageTopic = kafkaConfig.voltageTopic();
        NewTopic powerTopic = kafkaConfig.powerTopic();

        // Assuming you have a method to retrieve the retention in milliseconds
        String expectedRetention = String.valueOf(Duration.ofDays(7).toMillis());

        assertEquals(expectedRetention, temperatureTopic.configs().get(TopicConfig.RETENTION_MS_CONFIG));
        assertEquals(expectedRetention, voltageTopic.configs().get(TopicConfig.RETENTION_MS_CONFIG));
        assertEquals(expectedRetention, powerTopic.configs().get(TopicConfig.RETENTION_MS_CONFIG));
    }

    @Test
    void testKeySerializerConfiguration() {
        // Ensure that senderOptions() method is invoked before accessing the key serializer
        SenderOptions<String, Object> senderOptions = kafkaConfig.senderOptions();

        // Set the key serializer in SenderOptions (use your actual serializer class)
        senderOptions = senderOptions.producerProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "com.example.CustomKeySerializer");

        // Retrieve the key serializer class from the KafkaConfig instance
        String actualKeySerializer = (String) senderOptions.producerProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG);

        // Validate that the key serializer class matches the expected value
        assertEquals("com.example.CustomKeySerializer", actualKeySerializer);
    }

    @Test
    void testValueSerializerConfiguration() {
        // Ensure that senderOptions() method is invoked before accessing the value serializer
        SenderOptions<String, Object> senderOptions = kafkaConfig.senderOptions();

        // Set the value serializer in SenderOptions (use your actual serializer class)
        senderOptions = senderOptions.producerProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "com.example.CustomValueSerializer");

        // Retrieve the value serializer class from the KafkaConfig instance
        String actualValueSerializer = (String) senderOptions.producerProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG);

        // Validate that the value serializer class matches the expected value
        assertEquals("com.example.CustomValueSerializer", actualValueSerializer);
    }
}

