package org.senla.komar.spring.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.senla.komar.spring.dto.AuthPersonDto;
import org.senla.komar.spring.dto.FeedbackDto;
import org.senla.komar.spring.dto.GuestDto;
import org.senla.komar.spring.service.GuestService;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/guests")
@RequiredArgsConstructor
@Validated
public class GuestController {
    private final GuestService guestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createGuest(@Valid @RequestBody GuestDto guestDto) {
        guestService.createGuest(guestDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('USER')")
    public List<GuestDto> viewAllGuests(@RequestParam(value = "limit", defaultValue = "5")
                                        @Positive
                                        @Min(1)
                                        @Max(50)
                                        Integer limit,
                                        @RequestParam(value = "page", defaultValue = "1")
                                        @Positive
                                        @Min(1)
                                        Integer page) {
        return guestService.getAll(limit, page);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GuestDto getGuestById(@AuthenticationPrincipal AuthPersonDto authPersonDto,
                                 @PathVariable("id") @Positive @Min(1) Long id) {
        return guestService.getGuestById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('USER')")
    public void deleteGuestById(@PathVariable("id") @Positive @Min(1) Long id) {
        guestService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateGuestById(@AuthenticationPrincipal AuthPersonDto authPersonDto,
                                @PathVariable("id") @Positive @Min(1) Long id,
                                @Valid @RequestBody GuestDto newGuest) {
        guestService.updateGuestById(id, newGuest);
    }

    @GetMapping("/phoneNumber/{phoneNumber}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('USER')")
    public GuestDto getGuestByPhoneNumber(@PathVariable("phoneNumber")
                                          @NotBlank @Size(min = 7, max = 30)
                                          String phoneNumber) {
        return guestService.getGuestByPhoneNumber(phoneNumber);
    }

    @GetMapping("/surname/{surname}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('USER')")
    public List<GuestDto> getGuestsBySurname(@PathVariable("surname") @NotBlank @Size(min = 2, max = 35) String surname,
                                             @RequestParam(value = "limit", defaultValue = "5")
                                             @Positive
                                             @Min(1)
                                             @Max(50) Integer limit,
                                             @RequestParam(value = "page", defaultValue = "1")
                                             @Positive
                                             @Min(1)
                                             Integer page) {
        return guestService.getGuestsBySurname(surname, limit, page);
    }

    @GetMapping("/rank/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('USER')")
    public BigDecimal getRankById(@PathVariable("id") @Positive @Min(1) Long id) {
        return guestService.getRankById(id);
    }

    @GetMapping("/feedbacks/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<FeedbackDto> getFeedbacksById(@AuthenticationPrincipal AuthPersonDto authPersonDto,
                                              @PathVariable("id") @Positive @Min(1) Long id) {
        return guestService.getFeedbacksById(id);
    }
}
