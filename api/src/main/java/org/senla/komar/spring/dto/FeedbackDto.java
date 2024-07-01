package org.senla.komar.spring.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class FeedbackDto {
    private Long id;

    private String description;

    @Positive
    @NotNull(message = "Не поставлена оценка")
    @Min(0)
    @Max(10)
    private BigDecimal score;
}
