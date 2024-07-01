package org.senla.komar.spring.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacilityDto {
    private Integer id;

    @NotBlank(message = "Не введено название услуги")
    @Size(min = 2, max = 100)
    private String name;

    @NotNull(message = "Не указана стоимость услуги")
    @DecimalMin(value = "0.00", inclusive = true, message = "Значение должно быть не меньше 0")
    private BigDecimal cost;
}
