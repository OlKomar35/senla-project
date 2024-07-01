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
import org.senla.komar.spring.dto.CityDto;
import org.senla.komar.spring.entity.City;
import org.senla.komar.spring.exception.CityNotFoundException;
import org.senla.komar.spring.mapper.CityMapper;
import org.senla.komar.spring.mapper.CityMapperImpl;
import org.senla.komar.spring.repository.CityDao;
import org.senla.komar.spring.util.MockTestData;

@RunWith(MockitoJUnitRunner.class)
public class CityServiceImplTest {

    @Mock
    private CityDao cityDaoMock;
    @Spy
    private CityMapper cityMapperSpy = new CityMapperImpl();
    @InjectMocks
    private CityServiceImpl cityService;
    
    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        cityService = new CityServiceImpl(cityDaoMock, cityMapperSpy);
    }
    
    @Test
    public void createCity() {
        CityDto cityDto = MockTestData.buildCreateCityDto();
        City city = cityMapperSpy.toCity(cityDto);
        Mockito.doNothing().when(cityDaoMock).create(city);
        cityService.createCity(cityDto);
        Mockito.verify(cityDaoMock).create(city);
    }

    @Test
    public void getCityById() {
        CityDto cityDto = MockTestData.existCityDto();
        City city = cityMapperSpy.toCity(cityDto);
        Mockito.when(cityDaoMock.readById(city.getId())).thenReturn(city);
        CityDto actualCityDto = cityService.getCityById(city.getId());
        Assert.assertEquals(cityDto, actualCityDto);
    }

    @Test
    public void deleteById() {
        City city = MockTestData.existCity();
        Mockito.doNothing().when(cityDaoMock).deleteById(city.getId());
        cityService.deleteById(city.getId());
        Mockito.verify(cityDaoMock).deleteById(city.getId());
    }

    @Test
    public void updateById() {
        CityDto cityDto = MockTestData.buildUpdateCityDto();
        City city = cityMapperSpy.toCity(cityDto);
        Mockito.when(cityDaoMock.update(city.getId(), city)).thenReturn(city);
        cityService.updateById(cityDto.getId(),cityDto);
        cityDto.setId(city.getId());
        Mockito.verify(cityDaoMock).update(city.getId(), city);
    }

    @Test(expected = CityNotFoundException.class)
    public void notFoundTest(){
        Long id = MockTestData.existCity().getId();
        Mockito.when(cityDaoMock.readById(id)).thenReturn(null);
        cityService.getCityById(id);
        Mockito.verify(cityDaoMock).readById(id);
    }
}