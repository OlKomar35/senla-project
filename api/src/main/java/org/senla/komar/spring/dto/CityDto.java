package org.senla.komar.spring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CityDto {
    private Long id;

    @NotBlank(message = "Название города  не может быть пустым")
    @Size(min = 2, max = 25, message = "Не верная длина названия города")
    private String name;
}
