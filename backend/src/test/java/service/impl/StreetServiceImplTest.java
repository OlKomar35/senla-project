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
import org.senla.komar.spring.dto.StreetDto;
import org.senla.komar.spring.entity.Street;
import org.senla.komar.spring.exception.StreetNotFoundException;
import org.senla.komar.spring.mapper.StreetMapper;
import org.senla.komar.spring.mapper.StreetMapperImpl;
import org.senla.komar.spring.repository.StreetDao;
import org.senla.komar.spring.service.StreetService;
import org.senla.komar.spring.util.MockTestData;

import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class StreetServiceImplTest {
    @Mock
    private StreetDao streetDaoMock;
    @Spy
    private StreetMapper streetMapperSpy = new StreetMapperImpl();
    @InjectMocks
    private StreetServiceImpl streetService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        streetService = new StreetServiceImpl(streetDaoMock, streetMapperSpy);
    }

    @Test
    public void createStreet() {
        StreetDto streetDto = MockTestData.buildCreateStreetDto();
        Street street = streetMapperSpy.toStreet(streetDto);
        Mockito.doNothing().when(streetDaoMock).create(street);
        streetService.createStreet(streetDto);
        Mockito.verify(streetDaoMock).create(street);
    }

    @Test
    public void getStreetById() {
        StreetDto streetDto = MockTestData.existStreetDto();
        Street street = streetMapperSpy.toStreet(streetDto);
        Mockito.when(streetDaoMock.readById(street.getId())).thenReturn(street);
        StreetDto actualStreetDto = streetService.getStreetById(street.getId());
        Assert.assertEquals(streetDto, actualStreetDto);
    }

    @Test
    public void deleteStreetById() {
        Street street = MockTestData.existStreet();
        Mockito.doNothing().when(streetDaoMock).deleteById(street.getId());
        streetService.deleteStreetById(street.getId());
        Mockito.verify(streetDaoMock).deleteById(street.getId());
    }

    @Test
    public void updateStreetById() {
        StreetDto streetDto = MockTestData.buildUpdateStreetDto();
        Street street = streetMapperSpy.toStreet(streetDto);
        Mockito.when(streetDaoMock.update(street.getId(), street)).thenReturn(street);
        streetService.updateStreetById(streetDto.getId(),streetDto);
        streetDto.setId(street.getId());
        Mockito.verify(streetDaoMock).update(street.getId(),street);
    }

    @Test(expected = StreetNotFoundException.class)
    public void notFoundTest(){
        Long id = MockTestData.existStreet().getId();
        Mockito.when(streetDaoMock.readById(id)).thenReturn(null);
        streetService.getStreetById(id);
        Mockito.verify(streetDaoMock).readById(id);
    }
}