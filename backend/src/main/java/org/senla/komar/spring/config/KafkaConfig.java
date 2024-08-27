package org.senla.komar.spring.config;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.senla.komar.spring.entity.Booking;
import org.senla.komar.spring.event.MessageSentEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.apache.kafka.clients.admin.NewTopic;


/**
 * @author Olga Komar
 * <p>
 * Created at 02.08.2024
 */
@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

  private final KafkaProperties kafkaProperties;

  @Bean
  ProducerFactory<String, MessageSentEvent> producerFactory(){
    Map<String, Object> configs = kafkaProperties.buildProducerProperties(null);

    return new DefaultKafkaProducerFactory<>(configs);
  }

  @Bean
  KafkaTemplate<String, MessageSentEvent> kafkaTemplate(){
    return new KafkaTemplate<String, MessageSentEvent>(producerFactory());
  }

  @Bean
  NewTopic createTopic(){
    return TopicBuilder.name("message-send-topic")
        .partitions(3)
        .replicas(3)
        .configs(Map.of("min.insync.replicas", "2"))
        .build();
  }

}
