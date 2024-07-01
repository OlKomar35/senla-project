package org.senla.komar.spring.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import  org.senla.komar.spring.enums.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingDtoFullInfo {

    private Long id;

    @NotNull
    private GuestDto guest;

    @NotNull
    private RoomDto room;

    @NotNull
    private BookingStatus bookingStatus;

    @Positive
    @DecimalMin(value = "0.00", inclusive = true, message = "Значение должно быть не меньше 0")
    private BigDecimal cost;

    @Positive
    @Min(1)
    private int countGuests;

    @FutureOrPresent
    private Date checkInDate;

    @FutureOrPresent
    private Date checkOutDate;

    @FutureOrPresent
    private Date arrivalTime;

    @NotNull
    private PaymentStatus paymentStatus;

    @NotNull
    private TypePayment typePayment;

    @NotNull
    private TypeFood typeFood;

    private List<FacilityDto> facilities;

    private String comment;
}
