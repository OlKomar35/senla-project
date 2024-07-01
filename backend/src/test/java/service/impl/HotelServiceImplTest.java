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
import org.senla.komar.spring.dto.HotelDtoFullInfo;
import org.senla.komar.spring.dto.HotelDtoShortInfo;
import org.senla.komar.spring.entity.Hotel;
import org.senla.komar.spring.exception.HotelNotFoundException;
import org.senla.komar.spring.mapper.*;
import org.senla.komar.spring.repository.HotelDao;
import org.senla.komar.spring.service.HotelService;
import org.senla.komar.spring.util.MockTestData;

@RunWith(MockitoJUnitRunner.class)
public class HotelServiceImplTest {

    @Mock
    private HotelDao hotelDaoMock;
    @Spy
    private HotelShortInfoMapper hotelShortInfoMapperSpy = new HotelShortInfoMapperImpl();
    @Spy
    private HotelFullInfoMapper hotelFullInfoMapperSpy = new HotelFullInfoMapperImpl();
    @Spy
    private FeedbackMapper feedbackMapper = new FeedbackMapperImpl();
    @InjectMocks
    private HotelServiceImpl hotelService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        hotelService = new HotelServiceImpl(hotelDaoMock,
                hotelShortInfoMapperSpy,
                hotelFullInfoMapperSpy,
                feedbackMapper);
    }

    @Test
    public void createHotel() {
        HotelDtoFullInfo hotelDto = MockTestData.buildCreateHotelFullDto();
        Hotel hotel = hotelFullInfoMapperSpy.toHotel(hotelDto);
        Mockito.doNothing().when(hotelDaoMock).create(hotel);
        hotelService.createHotel(hotelDto);
        Mockito.verify(hotelDaoMock).create(hotel);
    }

    @Test
    public void getHotelById() {
        Hotel hotel = MockTestData.existHotel();
        HotelDtoFullInfo hotelDto = hotelFullInfoMapperSpy.toDto(hotel);
        Mockito.when(hotelDaoMock.readById(hotel.getId())).thenReturn(hotel);
        HotelDtoFullInfo actualHotelDto = hotelService.getHotelById(hotel.getId());
        Assert.assertEquals(hotelDto, actualHotelDto);
    }

    @Test
    public void deleteById() {
        Hotel hotel = MockTestData.existHotel();
        Mockito.doNothing().when(hotelDaoMock).deleteById(hotel.getId());
        hotelService.deleteById(hotel.getId());
        Mockito.verify(hotelDaoMock).deleteById(hotel.getId());
    }


    @Test
    public void getHotelShortInfoById() {
        Hotel hotel = MockTestData.existHotel();
        HotelDtoShortInfo hotelDto = hotelShortInfoMapperSpy.toDto(hotel);
        Mockito.when(hotelDaoMock.readById(hotel.getId())).thenReturn(hotel);
        HotelDtoShortInfo actualHotelDto = hotelService.getHotelShortInfoById(hotel.getId());
        Assert.assertEquals(hotelDto, actualHotelDto);
    }

    @Test(expected = HotelNotFoundException.class)
    public void notFoundTest() {
        Long id = MockTestData.existHotel().getId();
        Mockito.when(hotelDaoMock.readById(id)).thenReturn(null);
        hotelService.getHotelById(id);
        Mockito.verify(hotelDaoMock).readById(id);
    }
}