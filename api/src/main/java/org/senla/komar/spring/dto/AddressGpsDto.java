package org.senla.komar.spring.dto;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressGpsDto {

    private Long id;
    @Positive
    private BigDecimal latitude;
    @Positive
    private BigDecimal longitude;
}
