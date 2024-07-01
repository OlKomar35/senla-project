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
import org.senla.komar.spring.dto.PersonDto;
import org.senla.komar.spring.entity.Person;
import org.senla.komar.spring.exception.PersonNotFoundException;
import org.senla.komar.spring.mapper.PersonMapper;
import org.senla.komar.spring.mapper.PersonMapperImpl;
import org.senla.komar.spring.repository.PersonDao;
import org.senla.komar.spring.service.PersonService;
import org.senla.komar.spring.util.MockTestData;

@RunWith(MockitoJUnitRunner.class)
public class PersonServiceImplTest {
    @Mock
    private PersonDao personDaoMock;
    @Spy
    private PersonMapper personMapperSpy = new PersonMapperImpl();
    @InjectMocks
    private PersonServiceImpl personService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        personService = new PersonServiceImpl(personDaoMock, personMapperSpy);
    }

    @Test
    public void createPerson() {
        PersonDto personDto = MockTestData.buildCreatePersonDto();
        Person person = personMapperSpy.toPerson(personDto);
        Mockito.doNothing().when(personDaoMock).create(person);
        personService.createPerson(personDto);
        Mockito.verify(personDaoMock).create(person);
    }

    @Test
    public void getPersonById() {
        PersonDto personDto = MockTestData.existPersonDto();
        Person person = personMapperSpy.toPerson(personDto);
        Mockito.when(personDaoMock.readById(person.getId())).thenReturn(person);
        PersonDto actualPersonDto = personService.getPersonById(person.getId());
        Assert.assertEquals(personDto, actualPersonDto);
    }

    @Test
    public void deleteById() {
        Person person = MockTestData.existPerson();
        Mockito.doNothing().when(personDaoMock).deleteById(person.getId());
        personService.deleteById(person.getId());
        Mockito.verify(personDaoMock).deleteById(person.getId());
    }

    @Test
    public void updatePersonById() {
        PersonDto personDto = MockTestData.buildUpdatePersonDto();
        Person person = personMapperSpy.toPerson(personDto);
        Mockito.when(personDaoMock.update(person.getId(), person)).thenReturn(person);
        personService.updatePersonById(personDto.getId(),personDto);
        personDto.setId(person.getId());
        Mockito.verify(personDaoMock).update(person.getId(), person);
    }

    @Test
    public void getPersonByPassportData() {
        PersonDto personDto = MockTestData.existPersonDto();
        Person person = personMapperSpy.toPerson(personDto);
        Mockito.when(personDaoMock.getPersonByPassportData(person.getPassportSeries(), person.getPassportNumber()))
                .thenReturn(person);
        PersonDto actualPersonDto = personService
                .getPersonByPassportData(person.getPassportSeries(), person.getPassportNumber());
        Mockito.verify(personDaoMock).getPersonByPassportData(personDto.getPassportSeries(), person.getPassportNumber());
        Assert.assertEquals(personDto, actualPersonDto);
    }

    @Test
    public void getPersonByPassportDataEntityGraph() {
        PersonDto personDto = MockTestData.existPersonDto();
        Person person = personMapperSpy.toPerson(personDto);
        Mockito.when(personDaoMock.getPersonByPassportData(person.getPassportSeries(), person.getPassportNumber()))
                .thenReturn(person);
        PersonDto actualPersonDto = personService
                .getPersonByPassportData(person.getPassportSeries(), person.getPassportNumber());
        Mockito.verify(personDaoMock).getPersonByPassportData(personDto.getPassportSeries(), person.getPassportNumber());
        Assert.assertEquals(personDto, actualPersonDto);
    }

    @Test(expected = PersonNotFoundException.class)
    public void notFoundTest(){
        Long id = MockTestData.existPerson().getId();
        Mockito.when(personDaoMock.readById(id)).thenReturn(null);
        personService.getPersonById(id);
        Mockito.verify(personDaoMock).readById(id);
    }
}