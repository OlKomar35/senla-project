package org.senla.komar.spring.controller;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.senla.komar.spring.dto.*;
import org.senla.komar.spring.entity.Hotel;
import org.senla.komar.spring.service.HotelService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
@Validated
public class HotelController {

  private final HotelService hotelService;

  @GetMapping("/short/{id}")
  @ResponseStatus(HttpStatus.OK)
  public HotelDtoShortInfo getShortInfoById(@PathVariable("id") Long id) {
    return hotelService.getHotelShortInfoById(id);
  }

  @GetMapping("/all/{id}")
  @ResponseStatus(HttpStatus.OK)
  public HotelDtoFullInfo getAllInfoAboutHotel(@PathVariable("id") Long id) {
    return hotelService.getHotelById(id);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<HotelDtoFullInfo> viewAllHotels() {
    return hotelService.getAllHotel().stream().toList();
  }

  @GetMapping("/amenities")
  public List<HotelDtoFullInfo> getHotelsByAmenities(
      @RequestParam(required = true, value = "amenities") List<Integer> amenityHotelsId,
      @RequestParam(value = "limit", defaultValue = "5")
      @Min(1)
      @Max(50)
      Integer limit,
      @RequestParam(value = "page", defaultValue = "1")
      @Min(1)
      Integer page) {
    return hotelService.getHotelsByAmenities(amenityHotelsId, limit, page);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_MANAGER') ")
  public void createHotel(@RequestBody HotelDtoFullInfo hotelDtoFullInfo) {
    hotelService.createHotel(hotelDtoFullInfo);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_MANAGER')")
  public void deleteHotelById(@PathVariable("id") Long id) {
    hotelService.deleteById(id);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_MANAGER')")
  public void updateHotelById(@PathVariable("id") Long id, @RequestBody HotelDtoFullInfo newHotel) {
    hotelService.updateById(id, newHotel);
  }

  @GetMapping("/city/{city}")
  @ResponseStatus(HttpStatus.OK)
  public List<HotelDtoFullInfo> getHotelByCity(@PathVariable(value = "city") String cityName) {
    return hotelService.getHotelByCity(cityName).stream().toList();
  }

  @GetMapping("/feedbacks/{id}")
  @ResponseStatus(HttpStatus.OK)
  public List<FeedbackDto> getFeedbacksByHotelId(@PathVariable("id") @Positive @Min(1) Long id) {
    return hotelService.getFeedbacksByHotelId(id);
  }

  @GetMapping("/rank/{id}")
  @ResponseStatus(HttpStatus.OK)
  public String getRankById(@PathVariable("id") @Positive @Min(1) Long id) {
    return hotelService.getRankById(id);
  }

}
