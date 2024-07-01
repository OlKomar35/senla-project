package org.senla.komar.spring.repository.impl;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.senla.komar.spring.config.LiquibaseConfig;
import org.senla.komar.spring.dto.CityDto;
import org.senla.komar.spring.entity.City;
import org.senla.komar.spring.repository.CityDao;
import org.senla.komar.spring.config.H2HibernateConfig;
import org.senla.komar.spring.util.MockTestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {H2HibernateConfig.class, LiquibaseConfig.class, CityDaoImpl.class})
@Transactional
@WebAppConfiguration

public class CityDaoImplTest {

    @Autowired
    private CityDao cityDao;

    private Validator validator;

    @Before
    public void beforeAll() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    public void create() {
        CityDto expected = MockTestData.buildCreateCityDto();
        Set<ConstraintViolation<CityDto>> violations = validator.validate(expected);
        assertTrue(violations.isEmpty());
        cityDao.create(MockTestData.buildCreateCity());
        City actual = cityDao.readById(expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void readById() {
        City expected = MockTestData.existCity();
        City actual = cityDao.readById(expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void update() {
        City expected = MockTestData.buildUpdateCity();
        Set<ConstraintViolation<City>> violations = validator.validate(expected);
        assertTrue(violations.isEmpty());
        cityDao.update(expected.getId(), expected);
        City actual = cityDao.readById(expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void deleteById() {
        City expected = MockTestData.existCity();
        cityDao.deleteById(expected.getId());
        assertNull(cityDao.readById(expected.getId()));
    }

}