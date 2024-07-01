package org.senla.komar.spring.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Перечисление {@code BookingStatus} представляет возможные статусы бронирования.
 * Каждый статус бронирования имеет свое имя и описание.
 *
 * @author Olga Komar
 * @since 1.0
 */
@Getter
@RequiredArgsConstructor
public enum BookingStatus {
    NEW("new", " бронирование - только что созданное"),
    PENDING("pending", " бронирование - находится в обработке"),
    WAIT_LIST("wait list", "бронирование - в листе ожидания"),
    CONFIRMED("confirmed", "бронирование - подтвержденно"),
    CANCELLED("cancelled", " бронирование - отменнено"),
    MODIFIED("modified", " бронирование - измененно"),
    COMPLETED("completed", "бронирование - законченно");

    private final String name;
    private final String description;

}
