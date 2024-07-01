package org.senla.komar.spring.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.senla.komar.spring.dto.PersonDto;
import org.senla.komar.spring.service.impl.PersonServiceImpl;
import org.senla.komar.spring.validation.annotation.RussianCapitalLetters;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/people")
@RequiredArgsConstructor
@Validated
@PreAuthorize("hasAuthority('HOTEL_INFORMATION')")
public class PersonController {
    private final PersonServiceImpl personServiceImpl;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createPerson(@Valid @RequestBody PersonDto personDto) {
        personServiceImpl.createPerson(personDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PersonDto> viewAllPeople(@RequestParam(value = "limit", defaultValue = "5")
                                         @Positive
                                         @Min(1)
                                         @Max(50)
                                         Integer limit,

                                         @RequestParam(value = "page", defaultValue = "1")
                                         @Positive
                                         @Min(1)
                                         Integer page) {
        return personServiceImpl.getAll(limit, page);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PersonDto getPersonById(@PathVariable("id") @Positive @Min(1) Long id) {
        return personServiceImpl.getPersonById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePersonById(@PathVariable("id") @Positive @Min(1) Long id) {
        personServiceImpl.deleteById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updatePersonById(@PathVariable("id") @Positive @Min(1) Long id,
                                 @Valid @RequestBody PersonDto newPerson) {
        personServiceImpl.updatePersonById(id, newPerson);
    }

    @GetMapping("/passport")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PersonDto> getPersonByPassportDataEntityGraph(@RequestParam(value = "passportSeries")
                                                                        @NotBlank
                                                                        @Size(min = 2, max = 5)
                                                                        @RussianCapitalLetters
                                                                        String passportSeries,

                                                                        @RequestParam(value ="passportNumber")
                                                                        @NotNull
                                                                        @Positive
                                                                        @Min(1000000)
                                                                        @Max(9999999)
                                                                        int passportNumber) {
        return ResponseEntity.ok(personServiceImpl.getPersonByPassportDataEntityGraph(passportSeries, passportNumber));
    }
}
