package org.senla.komar.spring.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.senla.komar.spring.dto.AddressDto;
import org.senla.komar.spring.entity.Address;
import org.senla.komar.spring.exception.AddressNotFoundException;
import org.senla.komar.spring.mapper.AddressMapper;
import org.senla.komar.spring.mapper.AddressMapperImpl;
import org.senla.komar.spring.repository.AddressDao;
import org.senla.komar.spring.service.AddressService;
import org.senla.komar.spring.util.MockTestData;

@RunWith(MockitoJUnitRunner.class)
public class AddressServiceImplTest {

    @Mock
    private AddressDao addressDaoMock;
    @Spy
    private AddressMapper addressMapperSpy = new AddressMapperImpl();
    @InjectMocks
    private AddressServiceImpl addressService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        addressService = new AddressServiceImpl(addressDaoMock, addressMapperSpy);
    }

    @Test
    public void createAddress() {
        AddressDto addressDto = MockTestData.buildCreateAddressDto();
        Address address = addressMapperSpy.toAddress(addressDto);
        Mockito.doNothing().when(addressDaoMock).create(address);
        addressService.createAddress(addressDto);
        Mockito.verify(addressDaoMock).create(address);
    }

    @Test
    public void getAddressById() {
        AddressDto addressDto = MockTestData.existAddressDto();
        Address address = addressMapperSpy.toAddress(addressDto);
        Mockito.when(addressDaoMock.readById(address.getId())).thenReturn(address);
        AddressDto actualAddressDto = addressService.getAddressById(address.getId());
        Assert.assertEquals(addressDto, actualAddressDto);
    }

    @Test
    public void deleteById() {
        Address address = MockTestData.existAddress();
        Mockito.doNothing().when(addressDaoMock).deleteById(address.getId());
        addressService.deleteById(address.getId());
        Mockito.verify(addressDaoMock).deleteById(address.getId());
    }

    @Test
    public void updateById() {
        AddressDto addressDto = MockTestData.buildUpdateAddressDto();
        Address address = addressMapperSpy.toAddress(addressDto);
        Mockito.when(addressDaoMock.update(address.getId(), address)).thenReturn(address);
        addressService.updateById(addressDto.getId(),addressDto);
        addressDto.setId(addressDto.getId());
        Mockito.verify(addressDaoMock).update(address.getId(), address);
    }

    @Test
    public void getHouseNumberByAddressId() {
        Address address = MockTestData.existAddress();
        Mockito.when(addressDaoMock.getHouseNumberByAddressId(address.getId())).thenReturn(address.getHouseNumber());
        int actualHouseNumber = addressService.getHouseNumberByAddressId(address.getId());
        Mockito.verify(addressDaoMock).getHouseNumberByAddressId(address.getId());
        Assert.assertEquals(address.getHouseNumber(), actualHouseNumber);
    }

    @Test(expected = AddressNotFoundException.class)
    public void notFoundTest(){
        Long id = MockTestData.existHotel().getId();
        Mockito.when(addressDaoMock.readById(id)).thenReturn(null);
        addressService.getAddressById(id);
        Mockito.verify(addressDaoMock).readById(id);
    }
}