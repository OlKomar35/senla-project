package org.senla.komar.spring.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TypeRoom {

    SINGLE_ROOM,
    DOUBLE_ROOM,
    TWIN_ROOM,
    FAMILY_ROOM,
    SUITE,
    APARTMENT,
    STUDIO,
    LUXURY

}
