package org.senla.komar.spring.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.senla.komar.spring.dto.FacilityDto;
import org.senla.komar.spring.service.FacilityService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/facilities")
@RequiredArgsConstructor
@Validated
public class FacilityController {
    private final FacilityService facilityService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('HOTEL_INFORMATION')")
    public void createFacility(@Valid @RequestBody FacilityDto facilityDto) {
        facilityService.createFacility(facilityDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FacilityDto> viewAll(@RequestParam(value = "limit", defaultValue = "5")
                                     @Positive
                                     @Min(1)
                                     @Max(50)
                                     Integer limit,
                                     @RequestParam(value = "page", defaultValue = "1")
                                     @Positive
                                     @Min(1)
                                     Integer page) {
        return facilityService.getAll(limit, page);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FacilityDto getFacilityById(@PathVariable("id") @Positive @Min(1) Integer id) {
        return facilityService.getFacilityById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('HOTEL_INFORMATION')")
    public void deleteFacilityById(@PathVariable("id") @Positive @Min(1) Integer id) {
        facilityService.deleteById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('HOTEL_INFORMATION')")
    public void updateFacilityById(@PathVariable("id") @Positive @Min(1) Integer id,
                                   @Valid @RequestBody FacilityDto newFacility) {
        facilityService.updateFacilityById(id, newFacility);
    }
}
