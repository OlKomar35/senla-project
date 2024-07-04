package org.senla.komar.spring.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.senla.komar.spring.dto.AddressDto;
import org.senla.komar.spring.exception.AddressNotFoundException;
import org.senla.komar.spring.mapper.AddressMapper;
import org.senla.komar.spring.repository.AddressRepository;
import org.senla.komar.spring.service.AddressService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Override
    public void createAddress(AddressDto addressDto) {
        addressRepository.save(addressMapper.toAddress(addressDto));
    }

    @Override
    public AddressDto getAddressById(Long id) {
        return addressRepository.findById(id)
            .map(addressMapper::toDto)
            .orElseThrow(() -> new AddressNotFoundException("Не нашлось адреса с id=" + id));
    }

    @Override
    public List<AddressDto> getAllAddress(Integer limit,
                                          Integer page) {
        Pageable pageable = PageRequest.of(page-1, limit);
        List<AddressDto> addresses = addressRepository.findAll(pageable).stream()
                .map(addressMapper::toDto)
                .collect(Collectors.toList());

        if (!addresses.isEmpty()) {
            log.info("Список адресов из {} штук успешно получен", addresses.size());
        } else {
            log.debug("Получен пустой список адресов");
        }
        return addresses;
    }

    @Override
    public void deleteById(Long id) {
        addressRepository.deleteById(id);
        log.info("Удаление прошло успешно");
    }

    @Override
    public void updateById(Long id,
                           AddressDto newAddress) {
        newAddress.setId(id);
        addressRepository.save(addressMapper.toAddress(newAddress));
        log.info("Обновление прошло успешно");
    }

    @Override
    public int getHouseNumberByAddressId(Long id) {
        Integer houseNumber = addressRepository.getHouseNumberByAddressId(id);
        if (houseNumber == null) {
            log.debug("Номер дома не был найден");
        } else {
            log.info("Номер дома в адресе с id={} успешно найден", id);
        }
        return houseNumber;
    }

    @Override
    public List<AddressDto> getByCityName(String cityName,
                                          Integer limit,
                                          Integer page) {
        Pageable pageable = PageRequest.of(page-1, limit, Sort.by("city"));
        List<AddressDto> addresses = addressRepository.findAllByCityName(cityName,pageable)
                .stream()
                .map(addressMapper::toDto)
                .toList();

        if (!addresses.isEmpty() || addresses != null) {
            log.info("Список адресов из {} штук успешно получен", addresses.size());
        } else {
            log.debug("Получен пустой список адресов");
        }
        return addresses;
    }

    @Override
    public List<AddressDto> getByCityAndStreet(String cityName,
                                               String streetName,
                                               Integer limit,
                                               Integer page) {
        Pageable pageable = PageRequest.of(page-1, limit, Sort.by("city"));
        List<AddressDto> addresses = addressRepository
                .findAllByCityNameAndStreetName(cityName, streetName,pageable)
                .stream()
                .map(addressMapper::toDto)
                .toList();
        ;

        if (!addresses.isEmpty() || addresses != null) {
            log.info("Список адресов из {} штук успешно получен", addresses.size());
        } else {
            log.debug("Получен пустой список адресов");
        }
        return addresses;
    }
}
