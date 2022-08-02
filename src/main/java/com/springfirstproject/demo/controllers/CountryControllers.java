package com.springfirstproject.demo.controllers;

import com.springfirstproject.demo.beans.Country;
import com.springfirstproject.demo.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
public class CountryControllers {
    @Autowired
    CountryService countryService;
    @GetMapping("/getcountries")
    public ResponseEntity<List <Country>> getCountries(){
        try {
            List<Country> countries = countryService.getAllCountries();
            return new ResponseEntity<List<Country>>(countries, HttpStatus.FOUND);
        }catch (Exception exception){
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/getcountries/{id}")
    public ResponseEntity<Country> getCountryById(@PathVariable(value = "id") int id)
    {
        Country country = countryService.getCountryByID(id);
        try {
            return new ResponseEntity<Country>(country, HttpStatus.FOUND);
        }catch (Exception exception){
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/getcountry/countryname")
    public ResponseEntity<Country> getCountryByName(@RequestParam(value = "name") String name){
        try {
            Country country = countryService.getCountryByName(name);
            return new ResponseEntity<Country>(country, HttpStatus.FOUND);
        }catch (Exception exception){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/addcountry")
    public ResponseEntity<Country> addCountry(@RequestBody Country country){
        try {
            country = countryService.addCountry(country);
            return  new ResponseEntity<Country>(country, HttpStatus.CREATED);
        }catch (NoSuchElementException noSuchElementException){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
    @PutMapping("/updatecountry/{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable(value = "id") int id, @RequestBody Country country){
        try {
            Country existedCountry = countryService.getCountryByID(id);
            existedCountry.setName(country.getName());
            existedCountry.setCapital(country.getCapital());
            countryService.updateCountry(existedCountry);
            return new ResponseEntity<Country>(existedCountry,HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
    @DeleteMapping("/deletecountry/{id}")
    public ResponseEntity<Country> deleteCountry(@PathVariable (value = "id") int id){
        Country country =null;
        try {
            country= countryService.getCountryByID(id);
            countryService.deleteCountry(id);
        }catch(NoSuchElementException noSuchElementException){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(country,HttpStatus.OK);
    }
}
