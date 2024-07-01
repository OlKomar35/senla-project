package org.senla.komar.spring.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TypeHotel {

    MOTEL("Motel"),
    HOSTEL("Hostel"),
    ONE_STARS("1*"),
    TWO_STARS("2*"),
    THREE_STARS("3*"),
    FOUR_STARS("4*"),
    FIVE_STARS("5*"),
    APART_HOTEL( "Apart hotel");

    private final String name;

}
