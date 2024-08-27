package org.senla.komar.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.senla.komar.spring.service.TemplateService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Olga Komar
 * <p>
 * Created at 27.08.2024
 */
@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {

  @Value("${rest-template.path}")
  private String pathRestTemplate;

  private final RestTemplate restTemplate;

  @Override
  public String getMessageType() {
    ResponseEntity<String> response = restTemplate.getForEntity(pathRestTemplate + "/message-types",
        String.class);
    return response.getBody();
  }

  @Override
  public String getDeliveryChannel() {
    ResponseEntity<String> response = restTemplate.getForEntity(pathRestTemplate + "/delivery-channels",
        String.class);
    return response.getBody();
  }
}
