package org.senla.komar.spring.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.senla.komar.spring.enums.TypeRoom;

@Data
@NoArgsConstructor
public class RoomDto {
    private Long id;
    @NotNull
    private HotelDtoFullInfo hotel;

    @NotNull
    @Min(1)
    private int number;

    @NotNull
    @Min(0)
    private int floor;

    @NotNull
    private TypeRoom typeRoom;

    @DecimalMin(value = "0.00", message = "Значение должно быть не меньше 0")
    private BigDecimal pricePerNight;

    @NotNull
    private boolean availability;
}
