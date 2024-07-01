package org.senla.komar.spring.service;

import java.util.List;
import org.senla.komar.spring.dto.AddressDto;

public interface AddressService {
    void createAddress(AddressDto addressDto);

    AddressDto getAddressById(Long id);

    List<AddressDto> getAllAddress(Integer limit, Integer page);

    void deleteById(Long id);

    void updateById(Long id, AddressDto newAddress);

    int getHouseNumberByAddressId(Long id);

    List<AddressDto> getByCityName(String cityName, Integer limit, Integer page);

    List<AddressDto> getByCityAndStreet(String cityName, String streetName, Integer limit, Integer page);
}
