package org.senla.komar.spring.repository.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.senla.komar.spring.config.LiquibaseConfig;
import org.senla.komar.spring.entity.Street;
import org.senla.komar.spring.entity.Street;
import org.senla.komar.spring.config.H2HibernateConfig;
import org.senla.komar.spring.repository.StreetDao;
import org.senla.komar.spring.util.MockTestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {H2HibernateConfig.class, LiquibaseConfig.class, StreetDaoImpl.class})
@Transactional
@WebAppConfiguration
public class StreetDaoImplTest {

    @Autowired
    private StreetDao streetDao;

    @Test
    public void create() {
        Street expected = MockTestData.buildCreateStreet();
        streetDao.create(expected);
        Street actual = streetDao.readById(expected.getId());
        assertEquals(expected,actual);
    }

    @Test
    public void readById() {
        Street expected = MockTestData.existStreet();
        Street actual = streetDao.readById(expected.getId());
        assertEquals(expected,actual);
    }

    @Test
    public void update() {
        Street expected = MockTestData.buildUpdateStreet();
        streetDao.update(expected.getId(), expected);
        Street actual = streetDao.readById(expected.getId());
        assertEquals(expected,actual);
    }

    @Test
    public void deleteById() {
        Street expected = MockTestData.existStreet();
        streetDao.deleteById(expected.getId());
        assertNull(streetDao.readById(expected.getId()));
    }
}