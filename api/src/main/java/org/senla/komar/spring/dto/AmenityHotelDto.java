package org.senla.komar.spring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AmenityHotelDto {
    private Integer id;

    @NotBlank(message = "Не введено название удобства")
    @Size(min = 2, max = 100)
    private String name;
}
