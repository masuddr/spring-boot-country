package com.masud.springbootcountryserviceproject.repository;

import com.masud.springbootcountryserviceproject.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CountryRepository extends JpaRepository<Country, Long> {


    public Country findByCountryName(String name);
}
