package org.senla.komar.spring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttractionDto {
    private Long id;

    @NotBlank(message = "Не введено название достопримечательности")
    @Size(min = 2, max = 150)
    private String name;

    @NotNull
    private AddressDto address;
}
