package org.senla.komar.spring.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.senla.komar.spring.annotation.WithMockAdmin;
import org.senla.komar.spring.config.H2HibernateConfig;
import org.senla.komar.spring.config.LiquibaseConfig;
import org.senla.komar.spring.dto.PersonDto;
import org.senla.komar.spring.exception.PersonNotFoundException;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {H2HibernateConfig.class, LiquibaseConfig.class, PersonController.class})
@Transactional
public class PersonControllerTest {

    @Autowired
    private Convertor convertor;
    @Autowired
    private PersonController personController;
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
    public void createPerson() throws Exception {
        PersonDto personDto = MockTestData.buildCreatePersonDto();

        mockMvc.perform(
                        post("/people")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertor.convertDtoToJson(personDto))
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockAdmin
    public void viewAllPeople() throws Exception {
        mockMvc.perform(
                        get("/people")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(status().isOk());

        List<PersonDto> personDtoList = personController.viewAllPeople(2,1);
        assertEquals(personDtoList.size(), 2);
    }

    @Test
    @WithMockAdmin
    public void getPersonById() throws Exception {
        PersonDto personDto = MockTestData.existPersonDto();
        Long id = personDto.getId();

        mockMvc.perform(
                        get("/people/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(personDto.getId()))
                .andExpect(jsonPath("$.surname").value(personDto.getSurname()))
                .andExpect(jsonPath("$.firstname").value(personDto.getFirstname()))
                .andExpect(jsonPath("$.middleName").value(personDto.getMiddleName()))
                .andExpect(jsonPath("$.passportSeries").value(personDto.getPassportSeries()))
                .andExpect(jsonPath("$.passportNumber").value(personDto.getPassportNumber()))
                .andExpect(jsonPath("$.phoneNumber").value(personDto.getPhoneNumber()))
                .andExpect(jsonPath("$.email").value(personDto.getEmail()));
    }

    @Test(expected = PersonNotFoundException.class)
    @WithMockAdmin
    public void deletePersonById() throws Exception {
        PersonDto personDto = MockTestData.existPersonDto();
        Long id = personDto.getId();

        mockMvc.perform(
                        delete("/people/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        assertNull(personController.getPersonById(id));
    }

    @Test
    @WithMockAdmin
    public void updatePersonById() throws Exception {
        PersonDto personDto = MockTestData.buildUpdatePersonDto();
        Long id = personDto.getId();

        mockMvc.perform(
                        put("/people/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertor.convertDtoToJson(personDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockAdmin
    public void getPersonByPassportDataEntityGraph() throws Exception {
        PersonDto personDto = MockTestData.existPersonDto();

        mockMvc.perform(
                        get("/people/passport")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .param("passportSeries", personDto.getPassportSeries())
                                .param("passportNumber", String.valueOf(personDto.getPassportNumber())))
                .andDo(print())
                .andExpect(status().isOk());

        assertEquals(personController.getPersonByPassportDataEntityGraph(
                personDto.getPassportSeries(), personDto.getPassportNumber()), personDto);
    }
}