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
public class BookingDtoShortInfo {

    private Long id;

    @NotNull
    private GuestDto guest;

    @NotNull
    private RoomDto room;
}
