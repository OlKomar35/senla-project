package org.senla.komar.spring.service;

import org.senla.komar.spring.dto.AddressDto;
import org.senla.komar.spring.dto.AuthPersonDto;
import org.senla.komar.spring.dto.FeedbackDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FeedbackService {
     void createFeedback(FeedbackDto feedbackDto);
    ResponseEntity<?> getFeedbackById(AuthPersonDto authPersonDto, Long id);
    List<FeedbackDto> getAllFeedback(Integer limit, Integer page);
    void deleteById(Long id);
    void updateById(Long id, FeedbackDto newFeedback);
    void createHotelFeedback(FeedbackDto feedbackDto, Long id);
    void createGuestFeedback(FeedbackDto feedbackDto, Long id);

}
