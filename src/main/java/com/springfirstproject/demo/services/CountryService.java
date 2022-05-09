package com.springfirstproject.demo.services;

import com.springfirstproject.demo.beans.Country;
import com.springfirstproject.demo.controllers.AddResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Component
public class CountryService {
    static HashMap<Integer, Country> countryIdMap;

    public CountryService() {
        countryIdMap = new HashMap<Integer, Country>();
        Country indCountry= new Country(1, "india", "delhi");
        Country usaCountry= new Country(2, "USA", "London");
        Country franceCountry= new Country(3, "France", "Parice");

        countryIdMap.put(1,indCountry);
        countryIdMap.put(2,usaCountry);
        countryIdMap.put(3,franceCountry);
    }
    public List getAllCountries(){
        List countries = new ArrayList<>(countryIdMap.values());
        return countries;
    }

    public Country getCountryByID(int id){
        Country country= countryIdMap.get(id);
        return country;
    }
    public  Country getCountryByName(String countryName){
        Country country =null;
        for (int i : countryIdMap.keySet()){
            if (countryIdMap.get(i).getName().equals(countryName)){
                country = countryIdMap.get(i);
            }
        }
        return country;
    }
    public Country addCountry(Country country){
        country.setId(getMaxId());
        countryIdMap.put(country.getId(), country);
        return country;
    }
    public static  int getMaxId(){
        int max=0;
        for (int id:countryIdMap.keySet()){
            if(max<=id){
                max=id;
            }
        }
        return max+1;
    }
    public Country updateCountry(Country country){
        if(country.getId()>0) {
            countryIdMap.put(country.getId(), country);
        }
        return country;
    }
    public AddResponse deleteCountry(int id){
        countryIdMap.remove(id);
        AddResponse res =new AddResponse();
        res.setMsg("This Record has been deleted...");
        res.setId(id);
        return res;
    }
}
