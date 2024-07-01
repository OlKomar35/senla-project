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
public class AmenityRoomDto {
    private Integer id;

    @NotBlank(message = "Не введено название удобства")
    @Size(min = 2, max = 100)
    private String name;
}
