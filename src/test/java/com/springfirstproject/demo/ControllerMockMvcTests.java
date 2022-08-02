package com.springfirstproject.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springfirstproject.demo.beans.Country;
import com.springfirstproject.demo.controllers.CountryControllers;
import com.springfirstproject.demo.services.CountryService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {ControllerMockMvcTests.class})
@ComponentScan(basePackages = "com.springfirstproject.demo")
@AutoConfigureMockMvc
@ContextConfiguration
public class ControllerMockMvcTests {
    @Autowired
    MockMvc mockMvc;
    @Mock
    CountryService countryService;
    @InjectMocks
    CountryControllers countryControllers;
    List<Country> myCountries;
    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(countryControllers).build();
    }
    @Test
    @Order(1)
    public void test_getAllCountries() throws Exception {
        List<Country> myCountries = new ArrayList<Country>();
        myCountries.add(new Country(1, "india", "delhi"));
        myCountries.add(new Country(2, "USA", "London"));
        myCountries.add(new Country(3, "France", "Paris"));
        when(countryService.getAllCountries()).thenReturn(myCountries);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/getcountries"))
                .andExpect(status().isFound()).andDo(print());
    }
    @Test
    @Order(2)
    public void test_getCountryById() throws Exception {
        List<Country> myCountries = new ArrayList<Country>();
        Country country = new Country(1, "india", "delhi");
        int countryId=1;
        when(countryService.getCountryByID(countryId)).thenReturn(country);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/getcountries/{id}",countryId))
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath(".id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath(".name").value("india"))
                .andExpect(MockMvcResultMatchers.jsonPath(".capital").value("delhi"))
                .andDo(print());
    }
    @Test
    @Order(3)
    public void test_getCountryByName() throws Exception {
        List<Country> myCountries = new ArrayList<Country>();
        Country country = new Country(1, "india", "delhi");
        String countryName="india";
        when(countryService.getCountryByName(countryName)).thenReturn(country);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/getcountry/countryname").param("name","india"))
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath(".id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath(".name").value("india"))
                .andExpect(MockMvcResultMatchers.jsonPath(".capital").value("delhi"))
                .andDo(print());
    }
    @Test
    @Order(4)
    public void test_addCountry() throws Exception {
        Country country = new Country(1, "india", "delhi");
        when(countryService.addCountry(country)).thenReturn(country);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(country);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/addcountry")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }
    @Test
    @Order(5)
    public void test_updateCountry() throws Exception {
        Country country = new Country(1, "india", "delhi");
        int countryId=1;
        when(countryService.getCountryByID(countryId)).thenReturn(country);
        when(countryService.updateCountry(country)).thenReturn(country);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(country);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/updatecountry/{id}",countryId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".name").value("india"))
                .andExpect(MockMvcResultMatchers.jsonPath(".capital").value("delhi"))
                .andDo(print());
    }

    @Test
    @Order(6)
    public void test_deleteCountry() throws Exception {
        Country country = new Country(1, "india", "delhi");
        int countryId=1;
        when(countryService.getCountryByID(countryId)).thenReturn(country);
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/deletecountry/{id}",countryId))
                .andExpect(status().isOk())
                .andDo(print());

    }


}
