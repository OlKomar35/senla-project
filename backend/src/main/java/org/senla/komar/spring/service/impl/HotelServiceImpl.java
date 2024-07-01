package org.senla.komar.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.senla.komar.spring.dto.FeedbackDto;
import org.senla.komar.spring.dto.HotelDtoFullInfo;
import org.senla.komar.spring.dto.HotelDtoShortInfo;
import org.senla.komar.spring.exception.HotelNotFoundException;
import org.senla.komar.spring.mapper.FeedbackMapper;
import org.senla.komar.spring.mapper.HotelFullInfoMapper;
import org.senla.komar.spring.mapper.HotelShortInfoMapper;
import org.senla.komar.spring.repository.HotelDao;
import org.senla.komar.spring.service.HotelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelDao hotelDao;
    private final HotelShortInfoMapper hotelShortInfoMapper;
    private final HotelFullInfoMapper hotelFullInfoMapper;
    private final FeedbackMapper feedbackMapper;

    @Override
    public void createHotel(HotelDtoFullInfo hotel) {
        hotelDao.create(hotelFullInfoMapper.toHotel(hotel));
    }

    @Override
    public HotelDtoFullInfo getHotelById(Long id) {
        HotelDtoFullInfo hotel = hotelFullInfoMapper.toDto(hotelDao.readById(id));
        if (hotel == null) {
            throw new HotelNotFoundException("Не удалось получить отель с id= [" + id + "]");
        }
        return hotel;
    }

    @Override
    public List<HotelDtoFullInfo> getAllHotel() {
        List<HotelDtoFullInfo> hotels = hotelDao.getAll()
                .stream()
                .map(hotelFullInfoMapper::toDto)
                .collect(Collectors.toList());
        if (hotels.isEmpty()) {
            throw new HotelNotFoundException("Не удалось получить ни одного отеля");
        }
        return hotels;
    }

    @Override
    public void deleteById(Long id) {
        hotelDao.deleteById(id);
    }

    @Override
    public List<HotelDtoFullInfo> getHotelByCity(String cityName) {
        List<HotelDtoFullInfo> hotels = hotelDao.getHotelsByCity(cityName)
                .stream()
                .map(hotelFullInfoMapper::toDto)
                .collect(Collectors.toList());
        if (hotels.isEmpty()) {
            throw new HotelNotFoundException("Не удалось получить ни один отель в городе " + cityName);
        }
        return hotels;
    }


    @Override
    public HotelDtoShortInfo getHotelShortInfoById(Long id) {
        HotelDtoShortInfo hotel = hotelShortInfoMapper.toDto(hotelDao.readById(id));
        if (hotel == null) {
            throw new HotelNotFoundException("Не удалось получить отель с id= [" + id + "]");
        }
        return hotel;
    }

    @Override
    public void updateById(Long id, HotelDtoFullInfo newHotel) {
        newHotel.setId(id);
        hotelDao.update(id,hotelFullInfoMapper.toHotel(newHotel));
    }

    @Override
    public List<FeedbackDto> getFeedbacksByHotelId(Long id) {
        return hotelDao.getFeedbacksById(id)
                .stream()
                .map(feedbackMapper::toDto).toList();
    }

    @Override
    public String getRankById(Long id) {
        BigDecimal averageScore = hotelDao.getRankById(id);

        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        decimalFormat.setMaximumFractionDigits(1);
        return (decimalFormat.format(averageScore));
    }

}
