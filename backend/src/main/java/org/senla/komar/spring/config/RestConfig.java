package org.senla.komar.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Olga Komar
 * <p>
 * Created at 26.08.2024
 */
@Configuration
public class RestConfig {
  @Bean
  public RestTemplate restTemplate(){
    return new RestTemplate();
  }
}
