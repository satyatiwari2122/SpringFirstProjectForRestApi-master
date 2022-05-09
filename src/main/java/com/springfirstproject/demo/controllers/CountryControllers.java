package com.springfirstproject.demo.controllers;

import com.springfirstproject.demo.beans.Country;
import com.springfirstproject.demo.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CountryControllers {
    @Autowired
    CountryService countryService;
    @GetMapping("/getcountries")
    public List getCountries(){

        return countryService.getAllCountries();
    }
    @GetMapping("/getcountries/{id}")
    public Country getCountries(@PathVariable(value = "id") int id){
        return countryService.getCountryByID(id);
    }
    @GetMapping("/getcountry/countryname")
    public Country getCountries(@RequestParam(value = "name") String name){
        return countryService.getCountryByName(name);
    }
    @PostMapping("/addcountry")
    public Country addCountry(@RequestBody Country country){
        return countryService.addCountry(country);
    }
    @PutMapping("/updatecountry")
    public Country updateCountry(@RequestBody Country country){
        return countryService.updateCountry(country);
    }
    @DeleteMapping("/deletecountry/{id}")
    public AddResponse deleteCountry(@PathVariable (value = "id") int id){
        return countryService.deleteCountry(id);
    }
}
