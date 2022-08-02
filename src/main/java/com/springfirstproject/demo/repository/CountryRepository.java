package com.springfirstproject.demo.repository;

import com.springfirstproject.demo.beans.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Integer> {

}
