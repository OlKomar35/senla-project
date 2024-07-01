package org.senla.komar.spring.repository.impl;

import jakarta.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.senla.komar.spring.config.LiquibaseConfig;
import org.senla.komar.spring.entity.Person;
import org.senla.komar.spring.config.H2HibernateConfig;
import org.senla.komar.spring.repository.PersonDao;
import org.senla.komar.spring.util.MockTestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {H2HibernateConfig.class, LiquibaseConfig.class, PersonDaoImpl.class})
@Transactional
@WebAppConfiguration
public class PersonDaoImplTest {
    
    @Autowired
    private PersonDao personDao;
    
    @Test
    public void create() {
        Person expected = MockTestData.buildCreatePerson();
        personDao.create(expected);
        Person actual = personDao.readById(expected.getId());
        assertEquals(expected,actual);
    }

    @Test
    public void readById() {
        Person expected = MockTestData.existPerson();
        Person actual = personDao.readById(expected.getId());
        assertEquals(expected,actual);
    }

    @Test
    public void update() {
        Person expected = MockTestData.buildUpdatePerson();
        personDao.update(expected.getId(), expected);
        Person actual = personDao.readById(expected.getId());
        assertEquals(expected,actual);
    }

    @Test
    public void deleteById() {
        Person expected = MockTestData.existPerson();
        personDao.deleteById(expected.getId());
        assertNull(personDao.readById(expected.getId()));
    }

    @Test
    public void getPersonByPassportData() {
        Person expected = MockTestData.existPerson();
        Person actual = personDao.getPersonByPassportData(expected.getPassportSeries(), expected.getPassportNumber());
        assertEquals(expected, actual);
    }

    @Test
    public void getPersonByPassportDataEntityGraph() {
        Person expected = MockTestData.existPerson();
        Person actual = personDao.getPersonByPassportData(expected.getPassportSeries(), expected.getPassportNumber());
        assertEquals(expected, actual);
    }
}