package org.senla.komar.spring.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TypePayment {
    CASH,
    CASHLESS,
    DEPOSIT,
    ONLINE,
    PARTIAL,
    CORPORATE
}
