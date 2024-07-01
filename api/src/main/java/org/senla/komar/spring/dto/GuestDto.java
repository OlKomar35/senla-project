package org.senla.komar.spring.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestDto {
    private Long id;

    @NotNull
    private PersonDto person;

    List<FeedbackDto> feedbacks;
    private Double rank;
}
