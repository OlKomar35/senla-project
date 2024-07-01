package org.senla.komar.spring.service;

import java.util.List;
import org.senla.komar.spring.dto.FeedbackDto;
import org.senla.komar.spring.dto.HotelDtoFullInfo;
import org.senla.komar.spring.dto.HotelDtoShortInfo;

public interface HotelService {
    void createHotel(HotelDtoFullInfo hotel);

    HotelDtoFullInfo getHotelById(Long id);

    List<HotelDtoFullInfo> getAllHotel();

    void deleteById(Long id);

    List<HotelDtoFullInfo> getHotelByCity(String cityName);

    HotelDtoShortInfo getHotelShortInfoById(Long id);

    void updateById(Long id, HotelDtoFullInfo newHotel);

    List<FeedbackDto> getFeedbacksByHotelId(Long id);

    String getRankById(Long id);
}
