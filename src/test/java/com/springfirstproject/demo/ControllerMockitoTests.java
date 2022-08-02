package com.springfirstproject.demo;

import com.springfirstproject.demo.beans.Country;
import com.springfirstproject.demo.controllers.CountryControllers;
import com.springfirstproject.demo.services.CountryService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {ControllerMockitoTests.class})
public class ControllerMockitoTests {
    @Mock
    CountryService countryService;
    @InjectMocks
    CountryControllers countryControllers;

    List <Country> myCountries;
    Country country;

    @Test
    @Order(1)
    public void test_getCountries(){
        List<Country> myCountries = new ArrayList<Country>();
        myCountries.add(new Country(1, "india", "delhi"));
        myCountries.add(new Country(2, "USA", "London"));
        myCountries.add(new Country(3, "France", "Paris"));
        when(countryService.getAllCountries()).thenReturn(myCountries);
        ResponseEntity<List<Country>> response = countryControllers.getCountries();
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertEquals(3, response.getBody().size());
    }
    @Test
    @Order(2)
    public void test_getCountryById(){
        Country country =new Country(1, "india", "delhi");
        int countryId=1;
        when(countryService.getCountryByID(countryId)).thenReturn(country);
        ResponseEntity<Country> response = countryControllers.getCountryById(countryId);
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertEquals(countryId, response.getBody().getId());
    }
    @Test
    @Order(3)
    public void test_getCountryByName(){
        Country country =new Country(2, "india", "delhi");
        String  countryName="india";
        when(countryService.getCountryByName(countryName)).thenReturn(country);
        ResponseEntity<Country> response = countryControllers.getCountryByName(countryName);
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertEquals(countryName, response.getBody().getName());
    }
    @Test
    @Order(4)
    public void test_addCountry(){
        Country country = new Country(2, "india", "delhi");
        when(countryService.addCountry(country)).thenReturn(country);
        ResponseEntity<Country> response = countryControllers.addCountry(country);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(country, response.getBody());
    }
    @Test
    @Order(5)
    public void test_updateCountry(){
        Country country = new Country(1, "US", "NewYork");
        int countryId =1;
        when(countryService.getCountryByID(countryId)).thenReturn(country);
        when(countryService.updateCountry(country)).thenReturn(country);
        ResponseEntity<Country> response = countryControllers.updateCountry(countryId, country);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getId());
        assertEquals("US", response.getBody().getName());
        assertEquals("NewYork", response.getBody().getCapital());
    }
    @Test
    @Order(6)
    public void test_deleteCountry(){
        Country country = new Country(1, "US", "NewYork");
        int countryId =1;
        when(countryService.getCountryByID(countryId)).thenReturn(country);
        ResponseEntity<Country> response = countryControllers.deleteCountry(countryId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
