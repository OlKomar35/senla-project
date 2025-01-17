package org.senla.komar.spring.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttractionGpsDto {
    private Long id;

    @NotNull
    private AddressGpsDto addressGpsDto;
}
