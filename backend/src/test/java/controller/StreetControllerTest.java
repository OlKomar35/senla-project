package org.senla.komar.spring.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.senla.komar.spring.annotation.WithMockAdmin;
import org.senla.komar.spring.config.H2HibernateConfig;
import org.senla.komar.spring.config.LiquibaseConfig;
import org.senla.komar.spring.dto.StreetDto;
import org.senla.komar.spring.exception.StreetNotFoundException;
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


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {H2HibernateConfig.class, LiquibaseConfig.class, StreetController.class})
@Transactional
public class StreetControllerTest {

    @Autowired
    private Convertor convertor;
    @Autowired
    private StreetController streetController;

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
    public void viewAllStreets() throws Exception {
        mockMvc.perform(
          get("/streets")
              .contentType(MediaType.APPLICATION_JSON)
              .accept(MediaType.APPLICATION_JSON)
              .characterEncoding(StandardCharsets.UTF_8)
        )
            .andDo(print())
            .andExpect(status().isOk());
        List<StreetDto> streetDtoList = streetController.viewAllStreets(5,1);
        assertEquals(streetDtoList.size(),5);
    }
    @Test
    @WithMockAdmin
    public void createStreet() throws Exception{
        StreetDto streetDto = MockTestData.buildCreateStreetDto();

        mockMvc.perform(
            post("/streets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertor.convertDtoToJson(streetDto))
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
        )
            .andDo(print())
            .andExpect(status().isCreated());
    }

    @Test
    public void getStreetById() throws Exception{
        StreetDto streetDto = MockTestData.existStreetDto();
        Long id = streetDto.getId();

        mockMvc.perform(
            get("/streets/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(streetDto.getId()))
            .andExpect(jsonPath("$.name").value(streetDto.getName()));
    }

    @Test(expected = StreetNotFoundException.class)
    @WithMockAdmin
    public void deleteStreetById() throws Exception{
        StreetDto streetDto = MockTestData.existStreetDto();
        Long id = streetDto.getId();

        mockMvc.perform(
            delete("/streets/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().isOk());

        assertNull(streetController.getStreetById(id));
    }

    @Test
    @WithMockAdmin
    public void updateStreetById() throws Exception{
        StreetDto streetDto = MockTestData.buildUpdateStreetDto();
        Long id = streetDto.getId();

        mockMvc.perform(
                put("/streets/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(convertor.convertDtoToJson(streetDto))
            )
            .andDo(print())
            .andExpect(status().isOk());
    }
}