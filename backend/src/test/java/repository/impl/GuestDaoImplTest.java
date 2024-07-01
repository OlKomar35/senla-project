package org.senla.komar.spring.repository.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.senla.komar.spring.config.LiquibaseConfig;
import org.senla.komar.spring.entity.Guest;
import org.senla.komar.spring.repository.GuestDao;
import org.senla.komar.spring.config.H2HibernateConfig;
import org.senla.komar.spring.util.MockTestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {H2HibernateConfig.class, LiquibaseConfig.class, GuestDaoImpl.class})
@Transactional
@WebAppConfiguration
public class GuestDaoImplTest {

    @Autowired
    GuestDao guestDao;

    @Test
    public void create() {
        Guest expected = MockTestData.buildCreateGuest();
        guestDao.create(expected);
        Guest actual = guestDao.readById(expected.getId());
        assertEquals(expected,actual);
    }

    @Test
    public void readById() {
        Guest expected = MockTestData.existGuest();
        Guest actual = guestDao.readById(expected.getId());
        assertEquals(expected,actual);
    }

    @Test
    public void update() {
        Guest expected = MockTestData.buildUpdateGuest();
        guestDao.update(expected.getId(), expected);
        Guest actual = guestDao.readById(expected.getId());
        assertEquals(expected,actual);
    }

    @Test
    public void deleteById() {
        Guest expected = MockTestData.existGuest();
        guestDao.deleteById(expected.getId());
        assertNull(guestDao.readById(expected.getId()));
    }

    @Test
    public void getGuestByPhoneNumber() {
        Guest expected = MockTestData.existGuest();
        Guest actual = guestDao.getGuestByPhoneNumber(expected.getPerson().getPhoneNumber());
        assertEquals(expected, actual);
    }

    @Test
    public void getGuestsBySurname() {
        Guest expected = MockTestData.existGuest();
        Guest actual = guestDao
                .getGuestsBySurname(expected.getPerson().getSurname(),1,1)
                .stream()
                .filter(g -> g.getId().equals(expected.getId()))
                .findFirst().get();
        assertEquals(expected, actual);
    }
}