package org.senla.komar.spring.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ServiceProvidedDto {
    private Long id;
    private Long bookingId;
    private Long employeeId;
    private int facilityId;
    private boolean status;
}
