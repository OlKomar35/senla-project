package org.senla.komar.spring.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.senla.komar.spring.dto.AuthPersonDto;
import org.senla.komar.spring.dto.FeedbackDto;
import org.senla.komar.spring.service.FeedbackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feedbacks")
@RequiredArgsConstructor
@Validated
public class FeedbackController {
    private final FeedbackService feedbackService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FeedbackDto> viewAllFeedback(@RequestParam(value = "limit", defaultValue = "5")
                                             @Positive
                                             @Min(1)
                                             @Max(50)
                                             Integer limit,
                                             @RequestParam(value = "page", defaultValue = "1")
                                             @Positive
                                             @Min(1)
                                             Integer page) {
        return feedbackService.getAllFeedback(limit, page);
    }

    @PostMapping("/hotel/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('BOOKING')")
    public void createHotelFeedback(@Valid @RequestBody FeedbackDto feedbackDto,
                                    @PathVariable("id") Long id) {
        feedbackService.createHotelFeedback(feedbackDto, id);
    }

    @PostMapping("/guest/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createGuestFeedback(@Valid @RequestBody FeedbackDto feedbackDto,
                                    @PathVariable("id") Long id) {
        feedbackService.createGuestFeedback(feedbackDto, id);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getFeedbackById(@AuthenticationPrincipal AuthPersonDto authPersonDto,
                                             @PathVariable("id") @Positive @Min(1) Long id) {
        return feedbackService.getFeedbackById(authPersonDto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteFeedbackById(@PathVariable("id") Long id) {
        feedbackService.deleteById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateFeedbackById(@PathVariable("id") Long id, @RequestBody FeedbackDto feedbackDto) {
        feedbackService.updateById(id, feedbackDto);
    }
}
