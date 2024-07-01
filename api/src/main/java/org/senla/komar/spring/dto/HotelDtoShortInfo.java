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
public class HotelDtoShortInfo {
    private Long id;

    @NotBlank(message = "Название гостиницы не может быть пустым")
    @Size(min = 2, max = 100, message = "Не верный размер")
    private String name;

    @NotNull
    private AddressDto address;
}
