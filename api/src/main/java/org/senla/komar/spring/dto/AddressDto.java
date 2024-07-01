package org.senla.komar.spring.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {
    private Long id;

    @NotNull
    private CityDto city;

    @NotNull
    private StreetDto street;

    @NotNull
    @Min(1)
    private int houseNumber;
}
