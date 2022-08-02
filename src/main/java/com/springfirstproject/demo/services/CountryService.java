package com.springfirstproject.demo.services;

import com.springfirstproject.demo.beans.Country;
import com.springfirstproject.demo.controllers.AddResponse;
import com.springfirstproject.demo.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
@Service
public class CountryService {
    @Autowired
    CountryRepository countryRepository;
    public List<Country> getAllCountries(){
        return countryRepository.findAll();
    }

    public Country getCountryByID(int id) {

        return countryRepository.findById(id).get();
    }
    public  Country getCountryByName(String countryName){

       List<Country> countries = countryRepository.findAll();
       Country country= null;
       for (Country con:countries){
            if (con.getName().equalsIgnoreCase(countryName)){
                country=con;
            }
       }
       return country;
    }
    public Country addCountry(Country country){
        country.setId(getMaxId());
        countryRepository.save(country);
        return country;

    }
    public   int getMaxId(){
        return countryRepository.findAll().size()+1;

    }
    public Country updateCountry(Country country){
        countryRepository.save(country);
        return country;

    }
    public AddResponse deleteCountry(int id){
        countryRepository.deleteById(id);
        AddResponse response = new AddResponse();
        response.setMsg("Country with this ID is deleted");
        response.setId(id);
        return response;
    }
}
