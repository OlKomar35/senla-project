package org.senla.komar.spring.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.senla.komar.spring.dto.AttractionDto;
import org.senla.komar.spring.dto.HotelDtoShortInfo;
import org.senla.komar.spring.service.AttractionService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Класс контроллера, предоставляющий REST API для управления достопримечательностями.
 *
 * <p>Данный контроллер обеспечивает создание, получение, обновление и удаление
 * достопримечательностей, а также получение списка достопримечательностей
 * по различным критериям.
 * </p>
 *
 * @author Olga Komar
 * @version 1.0
 * @since 1.0
 */

@Log4j2
@RestController
@RequestMapping("/attractions")
@RequiredArgsConstructor
@Tag(name = "Класс AttractionController ", description = "Представляет контроллер для управления операциями, связанными с достопримечательностями")
@Validated
public class AttractionController {
    /**
     * Сервис достопримечательностей.
     */
    private final AttractionService attractionService;

    /**
     * Обработчик POST-запроса для создания новой достопримечательности.
     *
     * @param attractionDto объект AttractionDto, содержащий информацию о достопримечательности
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "Обработчик POST-запроса для создания новой достопримечательности")
    public void createAttraction(@Valid @RequestBody AttractionDto attractionDto) {
        log.info("Создание новой достопримечательности: {}", attractionDto);
        attractionService.createAttraction(attractionDto);
    }

    /**
     * Обработчик GET-запроса для получения достопримечательность по ее идентификатору.
     *
     * @param id идентификатор достопримечательности
     * @return объект AttractionDto, содержащий информацию о достопримечательности
     */
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AttractionDto getAttractionById(@PathVariable("id") @Positive @Min(1) Long id) {
        log.info("Получение достопримечательности по идентификатору: {}", id);
        return attractionService.getAttractionById(id);
    }

    /**
     * Обработчик DELETE-запроса для удаления достопримечательности по ее идентификатору.
     *
     * @param id идентификатор достопримечательности
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAttractionById(@PathVariable("id") @Positive @Min(1) Long id) {
        log.info("Удаление достопримечательности по идентификатору: {}", id);
        attractionService.deleteById(id);
    }

    /**
     * Обработчик PUT-запроса для обновления достопримечательности по ее идентификатору.
     *
     * @param id            идентификатор достопримечательности
     * @param newAttraction объект AttractionDto с обновленной информацией о достопримечательности
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public void updateAttractionById(@PathVariable("id") @Positive @Min(1) Long id,
                                     @Valid @RequestBody AttractionDto newAttraction) {
        log.info("Обновление достопримечательности по идентификатору: {}, новые данные: {}", id, newAttraction);
        attractionService.updateById(id, newAttraction);
    }


    /**
     * Обработчик GET-запроса для получения списка достопримечательностей в конкретном городе.
     *
     * @param name  название города
     * @param limit ограничение на количество возвращаемых достопримечательностей (по умолчанию 5)
     * @param page  номер страницы результатов (по умолчанию 1)
     * @return список объектов Dto, содержащих информацию о достопримечательностях
     */
    @GetMapping("/city/{name}")
    @ResponseStatus(HttpStatus.OK)
    public List<AttractionDto> getAttractionsByCity(@PathVariable("name") String name,
                                                    @RequestParam(value = "limit", defaultValue = "5") Integer limit,
                                                    @RequestParam(value = "page", defaultValue = "1") Integer page) {
        log.info("Получение всех достопримечательностей в городе: {}" , name);
        return attractionService.getAttractionsByCity(name, limit, page);
    }

    /**
     * Обработчик GET-запроса для получения списка достопримечательностей в конкретном городе и на конкретной улице.
     *
     * @param cityName   название города
     * @param streetName название улицы
     * @param limit      ограничение на количество возвращаемых достопримечательностей (по умолчанию 5)
     * @param page       номер страницы результатов (по умолчанию 1)
     * @return список объектов Dto, содержащих информацию о достопримечательностях
     */
    @GetMapping("/city-street")
    @ResponseStatus(HttpStatus.OK)
    public List<AttractionDto> getAttractionsByCityAndStreet(@RequestParam("city")
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
        log.info("Получение всех достопримечательностей в городе {} на улице {}" , cityName, streetName);
        return attractionService.getAttractionsByCityAndStreet(cityName, streetName, limit, page);
    }
}
