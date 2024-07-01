package org.senla.komar.spring.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.senla.komar.spring.dto.AddressDto;
import org.senla.komar.spring.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * Класс AddressController представляет контроллер для управления операциями, связанными с адресами.
 * Он является компонентом архитектуры RESTful API и использует аннотации Spring для определения маршрутов и обработчиков запросов.
 *
 * @author Olga Komar
 * @version 1.0
 * @since 1.0
 */

@Log4j2
@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
@Validated
@Tag(name = "Класс AddressController ", description = "Представляет контроллер для управления операциями, связанными с адресами")
public class AddressController {

    /**
     * Сервис, отвечающий за бизнес-логику, связанную с адресами.
     */
    private final AddressService addressService;

    /**
     * Обработчик GET-запроса для получения списка всех адресов.
     *
     * @param limit количество адресов на одной странице (по умолчанию 5).
     * @param page  номер страницы (по умолчанию 1).
     * @return список адресов в формате AddressDto.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Обработчик GET-запроса для получения списка всех адресов")
    public List<AddressDto> viewAllAddresses(@RequestParam(value = "limit", defaultValue = "5")
                                             @Positive
                                             @Min(1)
                                             @Max(50)
                                             Integer limit,
                                             @RequestParam(value = "page", defaultValue = "1")
                                             @Positive
                                             @Min(1)
                                             Integer page) {
        log.info("Получение списка всех адресов с лимитом {} и номером страницы {}", limit, page);
        return addressService.getAllAddress(limit, page);
    }

    /**
     * Обработчик POST-запроса для создания нового адреса.
     *
     * @param addressDto объект AddressDto, содержащий данные нового адреса.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('HOTEL_INFORMATION')")
    @Operation(description = "Обработчик POST-запроса для создания нового адреса")
    public void createAddress(@Valid @RequestBody AddressDto addressDto) {
        log.info("Создание нового адреса: {}", addressDto);
        addressService.createAddress(addressDto);
    }

    /**
     * Обработчик GET-запроса для получения адреса по его идентификатору.
     *
     * @param id идентификатор адреса.
     * @return объект AddressDto, представляющий найденный адрес.
     */
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Обработчик GET-запроса для получения адреса по его идентификатору")
    public AddressDto getAddressById(@PathVariable("id") @Positive @Min(1) Long id) {
        log.info("Получение адреса по идентификатору: {}", id);
        return addressService.getAddressById(id);
    }

    /**
     * Обработчик DELETE-запроса для удаления адреса по его идентификатору.
     *
     * @param id идентификатор адреса.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('HOTEL_INFORMATION')")
    @Operation(description = "Обработчик DELETE-запроса для удаления адреса по его идентификатору")
    public void deleteAddressById(@PathVariable("id") @Positive @Min(1) Long id) {
        log.info("Удаление адреса по идентификатору: {}", id);
        addressService.deleteById(id);
    }

    /**
     * Обработчик PUT-запроса для обновления адреса по его идентификатору.
     *
     * @param id         идентификатор адреса, который требуется обновить.
     * @param newAddress объект AddressDto с обновленными данными адреса.
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('HOTEL_INFORMATION')")
    @Validated
    @Operation(description = "Обработчик PUT-запроса для обновления адреса по его идентификатору")
    public void updateAddressById(@PathVariable("id") @Positive @Min(1) Long id,
                                  @Valid @RequestBody AddressDto newAddress) {
        log.info("Обновление адреса по идентификатору: {}, новые данные: {}", id, newAddress);
        addressService.updateById(id, newAddress);
    }

    /**
     * Обработчик GET-запроса для получения номера дома по идентификатору адреса.
     *
     * @param id идентификатор адреса.
     * @return номер дома.
     */
    @GetMapping("/house/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Обработчик GET-запроса для получения номера дома по идентификатору адреса")
    public int getHouseNumberByAddressId(@PathVariable("id") @Positive @Min(1) Long id) {
        log.info("Получение номера дома по идентификатору адреса: {}", id);
        return addressService.getHouseNumberByAddressId(id);
    }

    /**
     * Обработчик GET-запроса для получения списка адресов по названию города.
     *
     * @param name  название города.
     * @param limit количество адресов на одной странице (по умолчанию 5).
     * @param page  номер страницы (по умолчанию 1).
     * @return список адресов в формате AddressDto.
     */
    @GetMapping("/city/{name}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Обработчик GET-запроса для получения списка адресов по названию города")
    public List<AddressDto> getAddressesByCity(@PathVariable("name") @NotBlank @Size(min = 2, max = 25) String name,
                                               @RequestParam(value = "limit", defaultValue = "5")
                                               @Positive
                                               @Min(1)
                                               @Max(50)
                                               Integer limit,
                                               @RequestParam(value = "page", defaultValue = "1")
                                               @Positive
                                               @Min(1)
                                               Integer page) {
        log.info("Получение всех адресов в городе {}", name);
        return addressService.getByCityName(name, limit, page);
    }

    /**
     * Обработчик GET-запроса для получения списка адресов по названию города и названию улицы.
     *
     * @param cityName   название города.
     * @param streetName название улицы.
     * @param limit      количество адресов на одной странице (по умолчанию 5).
     * @param page       номер страницы (по умолчанию 1).
     * @return список адресов в формате AddressDto.
     */
    @GetMapping("/city-street")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Обработчик GET-запроса для получения списка адресов по названию города и названию улицы")
    public List<AddressDto> getAddressesByCityAndStreet(@RequestParam("city")
                                                        @NotBlank @Size(min = 2, max = 25) String cityName,
                                                        @RequestParam("street")
                                                        @NotBlank @Size(min = 2, max = 100) String streetName,
                                                        @RequestParam(value = "limit", defaultValue = "5")
                                                        @Positive
                                                        @Min(1)
                                                        @Max(50)
                                                        Integer limit,
                                                        @RequestParam(value = "page", defaultValue = "1")
                                                        @Positive
                                                        @Min(1)
                                                        Integer page) {
        log.info("Получение всех адресов в городе {} на улице {}", cityName, streetName);
        return addressService.getByCityAndStreet(cityName, streetName, limit, page);
    }
}
