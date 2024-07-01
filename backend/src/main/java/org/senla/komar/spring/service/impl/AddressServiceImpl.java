package org.senla.komar.spring.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.senla.komar.spring.dto.AddressDto;
import org.senla.komar.spring.exception.AddressNotFoundException;
import org.senla.komar.spring.mapper.AddressMapper;
import org.senla.komar.spring.repository.AddressDao;
import org.senla.komar.spring.service.AddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressDao addressDAO;
    private final AddressMapper addressMapper;

    @Override
    public void createAddress(AddressDto addressDto) {
        addressDAO.create(addressMapper.toAddress(addressDto));
    }

    @Override
    public AddressDto getAddressById(Long id) {
        AddressDto address = addressMapper.toDto(addressDAO.readById(id));
        if (address == null) {
            throw new AddressNotFoundException("Не нашлось адреса с id=" + id);
        }
        return address;
    }

    @Override
    public List<AddressDto> getAllAddress(Integer limit,
                                          Integer page) {
        List<AddressDto> addresses = addressDAO.getAll(limit, page).stream()
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
        addressDAO.deleteById(id);
        log.info("Удаление прошло успешно");
    }

    @Override
    public void updateById(Long id,
                           AddressDto newAddress) {
        newAddress.setId(id);
        addressDAO.update(id,addressMapper.toAddress(newAddress));
        log.info("Обновление прошло успешно");
    }

    @Override
    public int getHouseNumberByAddressId(Long id) {
        Integer houseNumber = addressDAO.getHouseNumberByAddressId(id);
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
        List<AddressDto> addresses = addressDAO.getByCityName(cityName, limit, page)
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
        List<AddressDto> addresses = addressDAO
                .getByCityAndStreet(cityName, streetName, limit, page)
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
