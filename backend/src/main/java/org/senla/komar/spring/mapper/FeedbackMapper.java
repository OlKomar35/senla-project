package org.senla.komar.spring.mapper;

import org.mapstruct.Mapper;
import org.senla.komar.spring.dto.CityDto;
import org.senla.komar.spring.dto.FeedbackDto;
import org.senla.komar.spring.entity.City;
import org.senla.komar.spring.entity.Feedback;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {
    Feedback toFeedback(FeedbackDto feedbackDto);
    FeedbackDto toDto(Feedback feedback);

}
