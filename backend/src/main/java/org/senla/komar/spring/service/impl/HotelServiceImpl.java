package org.senla.komar.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.senla.komar.spring.dto.FeedbackDto;
import org.senla.komar.spring.dto.HotelDtoFullInfo;
import org.senla.komar.spring.dto.HotelDtoShortInfo;
import org.senla.komar.spring.exception.HotelNotFoundException;
import org.senla.komar.spring.mapper.FeedbackMapper;
import org.senla.komar.spring.mapper.HotelFullInfoMapper;
import org.senla.komar.spring.mapper.HotelShortInfoMapper;
import org.senla.komar.spring.repository.HotelRepository;
import org.senla.komar.spring.service.HotelService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

  private final HotelRepository hotelRepository;
  private final HotelShortInfoMapper hotelShortInfoMapper;
  private final HotelFullInfoMapper hotelFullInfoMapper;
  private final FeedbackMapper feedbackMapper;

  @Override
  public void createHotel(HotelDtoFullInfo hotel) {
    hotelRepository.save(hotelFullInfoMapper.toHotel(hotel));
  }

  @Override
  public HotelDtoFullInfo getHotelById(Long id) {
    return hotelRepository.findById(id)
        .map(hotelFullInfoMapper::toDto)
        .orElseThrow(() -> new HotelNotFoundException("Не удалось получить отель с id= [" + id + "]"));

  }

  @Override
  public List<HotelDtoFullInfo> getAllHotel() {

    List<HotelDtoFullInfo> hotels = hotelRepository.findAll()
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
    hotelRepository.deleteById(id);
  }

  @Override
  public List<HotelDtoFullInfo> getHotelByCity(String cityName) {
    List<HotelDtoFullInfo> hotels = hotelRepository.findAllByAddressCity(cityName, Pageable.unpaged())
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
    return hotelRepository.findById(id)
        .map(hotelShortInfoMapper::toDto)
        .orElseThrow(() -> new HotelNotFoundException("Не удалось получить отель с id= [" + id + "]"));
  }

  @Override
  public void updateById(Long id, HotelDtoFullInfo newHotel) {
    newHotel.setId(id);
    hotelRepository.save(hotelFullInfoMapper.toHotel(newHotel));
  }

  @Override
  public List<FeedbackDto> getFeedbacksByHotelId(Long id) {
    return hotelRepository.getFeedbacksById(id)
        .stream()
        .map(feedbackMapper::toDto).toList();
  }

  @Override
  public String getRankById(Long id) {
    BigDecimal averageScore = hotelRepository.getRankById(id);

    DecimalFormat decimalFormat = new DecimalFormat("#.#");
    decimalFormat.setMaximumFractionDigits(1);
    return (decimalFormat.format(averageScore));
  }

  @Override
  public List<HotelDtoFullInfo> getHotelsByAmenities(List<Integer> amenityHotelsId,
      Integer limit,
      Integer page) {
    Pageable pageable = PageRequest.of(page - 1, limit);
    return hotelRepository.findAllByAmenitiesIn(amenityHotelsId, pageable)
        .map(hotelFullInfoMapper::toDto)
        .toList();

  }

}
