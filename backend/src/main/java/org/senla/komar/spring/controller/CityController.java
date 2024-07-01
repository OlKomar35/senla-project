package org.senla.komar.spring.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.senla.komar.spring.dto.CityDto;
import org.senla.komar.spring.service.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Класс представляет контроллер для управления операциями, связанными с городами.
 * Он является компонентом архитектуры RESTful API и использует аннотации Spring для обработчиков запросов.
 *
 * @author Olga Komar
 * @version 1.0
 * @since 1.0
 */

@RestController
@RequestMapping("/cities")
@RequiredArgsConstructor
@Validated
public class CityController {

    /**
     * Сервис, отвечающий за бизнес-логику, связанную с городами.
     */
    private final CityService cityService;

    /**
     * Обработчик GET-запроса для получения списка всех городов.
     *
     * @param limit количество городов на одной странице (по умолчанию 5).
     * @param page  номер страницы (по умолчанию 1).
     * @return список городов в формате CityDto.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CityDto> viewAllCities(@RequestParam(value = "limit", defaultValue = "5")
                                       @Positive
                                       @Min(1)
                                       @Max(50)
                                       Integer limit,
                                       @RequestParam(value = "page", defaultValue = "1")
                                       @Positive
                                       @Min(1)
                                       Integer page) {
        return cityService.getAllCities(limit, page);
    }

    /**
     * Обработчик POST-запроса для создания нового города.
     *
     * @param cityDto объект CityDto, содержащий данные нового города.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void createCity(@Valid @RequestBody CityDto cityDto) {
        cityService.createCity(cityDto);
    }

    /**
     * Обработчик GET-запроса для получения города по его идентификатору.
     *
     * @param id идентификатор города.
     * @return объект CityDto, представляющий найденный город.
     */
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CityDto getCityById(@PathVariable("id") @Positive @Min(1) Long id) {
        return cityService.getCityById(id);
    }

    /**
     * Обработчик DELETE-запроса для удаления города по его идентификатору.
     *
     * @param id идентификатор города.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteCityById(@PathVariable("id") @Positive @Min(1) Long id) {
        cityService.deleteById(id);
    }

    /**
     * Обработчик PUT-запроса для обновления города по его идентификатору.
     *
     * @param id      идентификатор города, который требуется обновить.
     * @param newCity объект CityDto с обновленными данными города.
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateCityById(@PathVariable("id") @Positive @Min(1) Long id,
                               @Valid @RequestBody CityDto newCity) {
        cityService.updateById(id, newCity);
    }
}
