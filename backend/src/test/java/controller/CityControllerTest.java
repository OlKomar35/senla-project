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
import org.senla.komar.spring.dto.CityDto;
import org.senla.komar.spring.exception.CityNotFoundException;
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
@ContextConfiguration(classes = {H2HibernateConfig.class, LiquibaseConfig.class, CityController.class})
@Transactional
public class CityControllerTest {

  @Autowired
  private Convertor convertor;
  @Autowired
  private CityController cityController;
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
  public void viewAllCities() throws Exception {
    mockMvc.perform(
            get("/cities")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
        )
        .andDo(print())
        .andExpect(status().isOk());

    assertEquals(cityController.viewAllCities(5, 1).size(), 5);
  }

  @Test
  @WithMockAdmin
  public void createCity() throws Exception {
    CityDto cityDto = MockTestData.buildCreateCityDto();

    mockMvc.perform(
            post("/cities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertor.convertDtoToJson(cityDto))
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
        )
        .andDo(print())
        .andExpect(status().isCreated());
  }

  @Test
  public void getCityById() throws Exception {
    CityDto cityDto = MockTestData.existCityDto();
    Long id = cityDto.getId();

    mockMvc.perform(
            get("/cities/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(cityDto.getId()))
        .andExpect(jsonPath("$.name").value(cityDto.getName()));
  }

  @Test(expected = CityNotFoundException.class)
  @WithMockAdmin
  public void deleteCityById() throws Exception {
    CityDto cityDto = MockTestData.existCityDto();
    Long id = cityDto.getId();

    mockMvc.perform(
            delete("/cities/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());

    assertNull(cityController.getCityById(id));
  }

  @Test
  @WithMockAdmin
  public void updateCityById() throws Exception {
    CityDto cityDto = MockTestData.buildUpdateCityDto();
    Long id = cityDto.getId();

    mockMvc.perform(
            put("/cities/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertor.convertDtoToJson(cityDto)))
        .andDo(print())
        .andExpect(status().isOk());
  }
}