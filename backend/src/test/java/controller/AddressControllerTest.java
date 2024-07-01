package org.senla.komar.spring.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.senla.komar.spring.annotation.WithMockAdmin;
import org.senla.komar.spring.config.H2HibernateConfig;
import org.senla.komar.spring.config.LiquibaseConfig;
import org.senla.komar.spring.config.WebConfig;
import org.senla.komar.spring.dto.AddressDto;
import org.senla.komar.spring.exception.AddressNotFoundException;
import org.senla.komar.spring.util.MockTestData;
import org.senla.komar.spring.utils.Convertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class,H2HibernateConfig.class, LiquibaseConfig.class, AddressController.class})
@Transactional
public class AddressControllerTest {

    @Autowired
    private Convertor convertor;
    @Autowired
    private AddressController addressController;
    @Autowired
    private WebApplicationContext applicationContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void viewAllAddresses() throws Exception {
        mockMvc.perform(
                        get("/addresses")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding(StandardCharsets.UTF_8)
                )
                .andDo(print())
                .andExpect(status().isOk());

        assertEquals(addressController.viewAllAddresses(3,1).size(), 3);
    }

    @Test
    @WithMockAdmin
    public void createAddress() throws Exception {
        AddressDto addressDto = MockTestData.buildCreateAddressDto();

        mockMvc.perform(
                        post("/addresses")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertor.convertDtoToJson(addressDto))
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding(StandardCharsets.UTF_8)
                )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void getAddressById() throws Exception {
        AddressDto addressDto = MockTestData.existAddressDto();
        Long id = addressDto.getId();

        mockMvc.perform(
                        get("/addresses/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(addressDto.getId()))
                .andExpect(jsonPath("$.city").value(addressDto.getCity()))
                .andExpect(jsonPath("$.street").value(addressDto.getStreet()))
                .andExpect(jsonPath("$.houseNumber").value(addressDto.getHouseNumber()));
    }

    @Test(expected = AddressNotFoundException.class)
    @WithMockAdmin
    public void deleteAddressById() throws Exception {
        AddressDto addressDto = MockTestData.existAddressDto();
        Long id = addressDto.getId();

        mockMvc.perform(
                        delete("/addresses/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        assertNull(addressController.getAddressById(id));
    }

    @Test
    @WithMockAdmin
    public void updateAddressById() throws Exception {
        AddressDto addressDto = MockTestData.buildUpdateAddressDto();
        Long id = addressDto.getId();

        mockMvc.perform(
                        put("/addresses/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertor.convertDtoToJson(addressDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getHouseNumberByAddressId() throws Exception {
        AddressDto addressDto = MockTestData.existAddressDto();
        Long id = addressDto.getId();

        mockMvc.perform(
                        get("/addresses/house/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        assertEquals(addressController.getHouseNumberByAddressId(id), addressDto.getHouseNumber());
    }
}