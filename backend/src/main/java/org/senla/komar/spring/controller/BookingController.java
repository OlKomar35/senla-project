package org.senla.komar.spring.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.senla.komar.spring.dto.AuthPersonDto;
import org.senla.komar.spring.dto.BookingDtoFullInfo;
import org.senla.komar.spring.enums.BookingStatus;
import org.senla.komar.spring.service.BookingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Этот класс представляет собой контроллер для операций, связанных с бронированием.
 * Он обрабатывает различные HTTP-запросы, связанные с бронированием, такие как получение, создание, обновление и удаление бронирований.
 *
 * @author Olga Komar
 * @version 1.0
 */

@Log4j2
@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('BOOKING')")
@Validated
public class  BookingController {

    private final BookingService bookingService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BookingDtoFullInfo> viewAllBookings(@RequestParam(value = "limit", defaultValue = "5")
                                                    @Positive
                                                    @Min(1)
                                                    @Max(50)
                                                    Integer limit,
                                                    @RequestParam(value = "page", defaultValue = "1")
                                                    @Positive
                                                    @Min(1)
                                                    Integer page) {
        return bookingService.getAllBooking(limit, page);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createBooking(@Valid @RequestBody BookingDtoFullInfo bookingDtoFullInfo) {
        bookingService.createBooking(bookingDtoFullInfo);
    }

    @GetMapping("/{id}")
    public BookingDtoFullInfo getBookingById(@AuthenticationPrincipal AuthPersonDto authPersonDto,
                                            @PathVariable("id") @Positive @Min(1) Long id) {
        return bookingService.getBookingById(authPersonDto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBookingById(@PathVariable("id") @Positive @Min(1) Long id) {
        bookingService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateBookingById(@PathVariable("id") @Positive @Min(1) Long id,
                                  @Valid @RequestBody BookingDtoFullInfo newBooking) {
        bookingService.updateById(id, newBooking);
    }

    @GetMapping("/date-in")
    @ResponseStatus(HttpStatus.OK)
    public List<BookingDtoFullInfo> viewAllBookingsByDateIn(@RequestParam("date")
                                                            @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                                                            @RequestParam(value = "limit", defaultValue = "5")
                                                            @Positive
                                                            @Min(1)
                                                            @Max(50)
                                                            Integer limit,
                                                            @RequestParam(value = "page", defaultValue = "1")
                                                            @Positive
                                                            @Min(1)
                                                            Integer page) {
        return bookingService.getAllBookingsByDateIn(date, limit, page);
    }

    @GetMapping("/date-out/{date}")
    @ResponseStatus(HttpStatus.OK)
    public List<BookingDtoFullInfo> viewAllBookingsByDateOut(@PathVariable("date")
                                                             @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                                                             @RequestParam(value = "limit", defaultValue = "5")
                                                             @Positive
                                                             @Min(1)
                                                             @Max(50)
                                                             Integer limit,
                                                             @RequestParam(value = "page", defaultValue = "1")
                                                             @Positive
                                                             @Min(1)
                                                             Integer page) {
        return bookingService.getAllBookingsByDateOut(date, limit, page);
    }


    @GetMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public List<BookingDtoFullInfo> getAllBookingsByStatus(@RequestParam(value = "status", defaultValue = "NEW")
                                                           @NotNull BookingStatus status,
                                                           @RequestParam(value = "limit", defaultValue = "5")
                                                           @Positive
                                                           @Min(1)
                                                           @Max(50)
                                                           Integer limit,
                                                           @RequestParam(value = "page", defaultValue = "1")
                                                           @Positive
                                                           @Min(1)
                                                           Integer page) {

        return bookingService.getAllBookingsByStatus(status, limit, page);
    }

    @GetMapping("/facilities/{id}")
    public ResponseEntity<?> getAllFacilityByBookingId(@AuthenticationPrincipal AuthPersonDto authPersonDto,
                                                       @PathVariable("id") @Positive @Min(1) Long id) {
        return bookingService.getAllFacilityByBookingId(authPersonDto, id);
    }

}
