package com.masud.springbootcountryserviceproject.service;

import com.masud.springbootcountryserviceproject.entity.Country;


import java.util.List;



public interface CountryService {


    public List<Country> getAllCountries();

    public Country getCountryById(Long id);

    public Country addCountry(Country country);

    public Country updateCountry(Long id, Country country);

    public Country deleteCountry(Long id);
}
