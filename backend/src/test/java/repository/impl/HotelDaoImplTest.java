package org.senla.komar.spring.repository.impl;

import jakarta.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.senla.komar.spring.config.LiquibaseConfig;
import org.senla.komar.spring.entity.Hotel;
import org.senla.komar.spring.config.H2HibernateConfig;
import org.senla.komar.spring.repository.HotelDao;
import org.senla.komar.spring.util.MockTestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {H2HibernateConfig.class, LiquibaseConfig.class, HotelDaoImpl.class})
@Transactional
@WebAppConfiguration
public class HotelDaoImplTest {

    @Autowired
    private HotelDao hotelDao;

    @Test
    public void create() {
        Hotel expected = MockTestData.buildCreateHotel();
        hotelDao.create(expected);
        Hotel actual = hotelDao.readById(expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void readById() {
        Hotel expected = MockTestData.existHotel();
        Hotel actual = hotelDao.readById(expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void update() {
        Hotel expected = MockTestData.buildUpdateHotel();
        hotelDao.update(expected.getId(), expected);
        Hotel actual = hotelDao.readById(expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void deleteById() {
        Hotel expected = MockTestData.existHotel();
        hotelDao.deleteById(expected.getId());
        assertNull(hotelDao.readById(expected.getId()));
    }

//    @Test
//    public void getHotelsByCity() {
//        List<Hotel> expected = MockTestData.hotelsList();
//        List<Hotel> actual = hotelDao.getHotelsByCity("Гродно");
//        assertEquals(expected, actual);
//    }
}