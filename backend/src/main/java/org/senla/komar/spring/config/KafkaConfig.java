package org.senla.komar.spring.config;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.senla.komar.spring.entity.Booking;
import org.senla.komar.spring.event.MessageSentEvent;
import org.springframework.beans.factory.annotation.Autowired;
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
public class KafkaConfig {

  final Environment environment;

  public KafkaConfig(Environment environment) {
    this.environment = environment;
  }

  public Map<String, Object> producerConfigs(){
    Map<String, Object> configs = new HashMap<>();
    configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,environment.getProperty("spring.kafka.producer.bootstrap-servers"));
    configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,environment.getProperty("spring.kafka.producer.key-serializer"));
    configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,environment.getProperty("spring.kafka.producer.value-serializer"));
    configs.put(ProducerConfig.ACKS_CONFIG,environment.getProperty("spring.kafka.producer.acks"));

    return configs;
  }

  @Bean
  ProducerFactory<String, MessageSentEvent> producerFactory(){
    return new DefaultKafkaProducerFactory<>(producerConfigs());
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
