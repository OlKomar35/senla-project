package org.senla.komar.spring.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.senla.komar.spring.annotation.WithMockAdmin;
import org.senla.komar.spring.config.H2HibernateConfig;
import org.senla.komar.spring.config.LiquibaseConfig;
import org.senla.komar.spring.dto.GuestDto;
import org.senla.komar.spring.dto.PersonDto;
import org.senla.komar.spring.exception.GuestNotFoundException;
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
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {H2HibernateConfig.class, LiquibaseConfig.class, GuestController.class})
@Transactional
public class GuestControllerTest {

    @Autowired
    private Convertor convertor;
    @Autowired
    private GuestController guestController;
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
    @WithMockAdmin
    public void createGuest() throws Exception {
        GuestDto guestDto = MockTestData.buildCreateGuestDto();

        mockMvc.perform(
                        post("/guests")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertor.convertDtoToJson(guestDto))
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockAdmin
    public void viewAllGuests() throws Exception {
        mockMvc.perform(
                        get("/guests")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(status().isOk());

        List<GuestDto> guestDtoList = guestController.viewAllGuests(2,1);
        assertEquals(guestDtoList.size(), 2);
    }

    @Test
    @WithMockAdmin
    public void getGuestById() throws Exception {
        GuestDto guestDto = MockTestData.existGuestDto();
        Long id = guestDto.getId();

        mockMvc.perform(
                        get("/guests/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(guestDto.getId()))
                .andExpect(jsonPath("$.person").value(guestDto.getPerson()));
    }

    @Test(expected = GuestNotFoundException.class)
    @WithMockAdmin
    public void deleteGuestById() throws Exception {
        GuestDto guestDto = MockTestData.existGuestDto();
        Long id = guestDto.getId();

        mockMvc.perform(
                        delete("/guests/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        //assertNull(guestController.getGuestById(id));
    }

    @Test
    @WithMockAdmin
    public void updateGuestById() throws Exception {
        GuestDto guestDto = MockTestData.buildUpdateGuestDto();
        Long id = guestDto.getId();

        mockMvc.perform(
                        put("/guests/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertor.convertDtoToJson(guestDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockAdmin
    public void getGuestByPhoneNumber() throws Exception {
        GuestDto guestDto = MockTestData.existGuestDto();

        mockMvc.perform(
                        get("/guests/phoneNumber/{phoneNumber}", guestDto.getPerson().getPhoneNumber())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(status().isOk());

        assertEquals(guestController.getGuestByPhoneNumber(guestDto.getPerson().getPhoneNumber()), guestDto);
    }

    @Test
    @WithMockAdmin
    public void getGuestsBySurname() throws Exception {
        GuestDto guestDto = MockTestData.existGuestDto();

        mockMvc.perform(
                        get("/guests/surname/{surname}", guestDto.getPerson().getSurname()).contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(status().isOk());

        assertEquals(guestController.getGuestsBySurname(guestDto.getPerson().getSurname(),1,1).getFirst(), guestDto);
        assertEquals(guestController.getGuestsBySurname(guestDto.getPerson().getSurname(),1,1).size(), 1);
    }
}