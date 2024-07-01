package org.senla.komar.spring.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.senla.komar.spring.dto.StreetDto;
import org.senla.komar.spring.service.StreetService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/streets")
@RequiredArgsConstructor
@Validated
public class StreetController {
    private final StreetService streetService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StreetDto> viewAllStreets(@RequestParam(value = "limit", defaultValue = "5")
                                          @Positive
                                          @Min(1)
                                          @Max(50)
                                          Integer limit,
                                          @RequestParam(value = "page", defaultValue = "1")
                                          @Positive
                                          @Min(1)
                                          Integer page) {
        return streetService.getAllStreets(limit, page);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void createStreet(@Valid @RequestBody StreetDto streetDto) {
        streetService.createStreet(streetDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StreetDto getStreetById(@PathVariable("id") @Positive @Min(1) Long id) {
        return streetService.getStreetById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteStreetById(@PathVariable("id") @Positive @Min(1) Long id) {
        streetService.deleteStreetById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateStreetById(@PathVariable("id") @Positive @Min(1)Long id,
                                 @Valid @RequestBody StreetDto newStreet) {
        streetService.updateStreetById(id, newStreet);
    }
}
