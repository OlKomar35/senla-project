package org.senla.komar.spring.repository.impl;

import jakarta.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.senla.komar.spring.config.LiquibaseConfig;
import org.senla.komar.spring.entity.Address;
import org.senla.komar.spring.repository.AddressDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.senla.komar.spring.config.H2HibernateConfig;
import org.senla.komar.spring.util.MockTestData;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {H2HibernateConfig.class, LiquibaseConfig.class, AddressDaoImpl.class})
@Transactional
@WebAppConfiguration
public class AddressDaoImplTest{

    @Autowired
    private AddressDao addressDao;



    @Test
    public void create() {
        Address expected = MockTestData.buildCreateAddress();
        addressDao.create(expected);
        Address actual = addressDao.readById(expected.getId());
        assertEquals(expected,actual);
    }

    @Test
    public void readById() {
        Address expected = MockTestData.existAddress();
        Address actual = addressDao.readById(expected.getId());
        assertEquals(expected,actual);
    }

    @Test
    public void update() {
        Address expected = MockTestData.buildUpdateAddress();
        addressDao.update(expected.getId(), expected);
        Address actual = addressDao.readById(expected.getId());
        assertEquals(expected,actual);
    }

    @Test
    public void deleteById() {
        Address expected = MockTestData.existAddress();
        addressDao.deleteById(expected.getId());
        assertNull(addressDao.readById(expected.getId()));
    }

    @Test
    public void getHouseNumberByAddressId() {
        Address address= MockTestData.existAddress();
        int houseNumberExpected = address.getHouseNumber();
        int houseNumberActual = addressDao.getHouseNumberByAddressId(address.getId());
        assertEquals(houseNumberExpected,houseNumberActual);
    }

}