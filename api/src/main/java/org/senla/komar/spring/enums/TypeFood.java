package org.senla.komar.spring.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TypeFood {
    RO("room only", "без питания"),

    BB("bed and breakfast", "завтрак включен"),

    HB("half board", "полупансион"),

    FB("full board", "полный пансион"),

    AI("all inclusive", "все включено");

    private final String name;
    private final String description;
}
