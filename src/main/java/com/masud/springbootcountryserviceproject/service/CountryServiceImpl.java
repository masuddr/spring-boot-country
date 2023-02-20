package com.masud.springbootcountryserviceproject.service;

import com.masud.springbootcountryserviceproject.entity.Country;
import com.masud.springbootcountryserviceproject.exception.CountryNotFoundException;
import com.masud.springbootcountryserviceproject.exception.NoDataFoundException;
import com.masud.springbootcountryserviceproject.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class CountryServiceImpl implements CountryService{


    private final CountryRepository countryRepository;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }




    @Override
    public List<Country> getAllCountries() {

        List<Country> countries = countryRepository.findAll();

        if (countries.isEmpty()) {

            throw new NoDataFoundException();
        }

        return countries;
    }

    @Override
    public Country getCountryById(Long id) {
        return countryRepository.findById(id).orElseThrow(() -> new CountryNotFoundException(id));
    }

    @Override
    public Country addCountry(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public Country updateCountry(Long id, Country country) {
        Country countryFromDb = countryRepository.findById(id).orElseThrow(() -> new CountryNotFoundException(id));


            if(country.getCountryName() != null)
            {
                countryFromDb.setCountryName(country.getCountryName());
            }

            if(country.getCountryCapital() != null)
            {
                countryFromDb.setCountryCapital(country.getCountryCapital());
            }

            countryRepository.save(countryFromDb);

        return countryFromDb;

    }

    @Override
    public Country deleteCountry(Long id) {
        Country countryFromDb = countryRepository.findById(id).orElseThrow(() -> new CountryNotFoundException(id));

            countryRepository.delete(countryFromDb);

        return countryFromDb;
    }
}
