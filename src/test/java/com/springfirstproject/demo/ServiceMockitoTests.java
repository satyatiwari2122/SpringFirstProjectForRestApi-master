package com.springfirstproject.demo;

import com.springfirstproject.demo.beans.Country;
import com.springfirstproject.demo.repository.CountryRepository;
import com.springfirstproject.demo.services.CountryService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {ServiceMockitoTests.class})
public class ServiceMockitoTests {
    @Mock
    CountryRepository countryRepository;
    @InjectMocks
    CountryService countryService;
    List<Country> countries;
    @Test
    @Order(1)
    public void test_getAllCountries(){
        List<Country> myCountries = new ArrayList<Country>();
        myCountries.add(new Country(1, "india", "delhi"));
        myCountries.add(new Country(2, "USA", "London"));
        myCountries.add(new Country(3, "France", "Paris"));
        //Mocking
        when(countryRepository.findAll()).thenReturn(myCountries);
        assertEquals(3, countryService.getAllCountries().size());
    }
    @Test
    @Order(2)
    public void test_getCountryByID() {
        List<Country> myCountries = new ArrayList<Country>();
        myCountries.add(new Country(1, "india", "delhi"));
        myCountries.add(new Country(2, "USA", "London"));
        //Mocking
        Country countryById = null;
        int countryId=2;
        for (Country con : myCountries){
            if (con.getId()==countryId){
                countryById=con;
            }
        }
        when(countryRepository.findById(countryId)).thenReturn(Optional.ofNullable(countryById));//mocking
        assertEquals(countryId, countryService.getCountryByID(countryId).getId());
    }
    @Test
    @Order(3)
    public void test_getCountryByName() {
        List<Country> myCountries = new ArrayList<Country>();
        myCountries.add(new Country(1, "India", "Delhi"));
        myCountries.add(new Country(2, "USA", "London"));
        //Mocking
        String countryName="India";
        when(countryRepository.findAll()).thenReturn(myCountries);
        assertEquals(countryName, countryService.getCountryByName(countryName).getName());
    }
    @Test
    @Order(4)
    public void test_addCountry() {
        Country country= new Country(3, "France", "Paris");

        when(countryRepository.save(country)).thenReturn(country);
        assertEquals(country, countryService.addCountry(country));
    }
    @Test
    @Order(5)
    public void test_updateCountry() {
        Country country= new Country(3, "France", "Paris");

        when(countryRepository.save(country)).thenReturn(country);
        assertEquals(country, countryService.updateCountry(country));
    }
    @Test
    @Order(6)
    public void test_deleteCountry() {
        Country country= new Country(3, "France", "Paris");

        countryService.deleteCountry(country.getId());
        verify(countryRepository, times(1)).deleteById(country.getId());
    }


}
