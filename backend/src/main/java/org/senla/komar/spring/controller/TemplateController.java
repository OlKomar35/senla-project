package org.senla.komar.spring.controller;

import lombok.RequiredArgsConstructor;
import org.senla.komar.spring.service.TemplateService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Olga Komar
 * <p>
 * Created at 26.08.2024
 */
@RestController
@RequestMapping("/templates")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
public class TemplateController {

  private final TemplateService templateService;

  @GetMapping("/message-types")
  public String getMessageType() {
   return templateService.getMessageType();
  }
  @GetMapping("/delivery-channels")
  public String getDeliveryChannel() {
    return templateService.getDeliveryChannel();
  }

}
