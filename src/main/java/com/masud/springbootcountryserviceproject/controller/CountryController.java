package com.masud.springbootcountryserviceproject.controller;

import com.masud.springbootcountryserviceproject.entity.Country;
import com.masud.springbootcountryserviceproject.exception.CountryNotFoundException;
import com.masud.springbootcountryserviceproject.exception.NoDataFoundException;
import com.masud.springbootcountryserviceproject.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/countries")
public class CountryController {


    private final CountryService countryService;


    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public ResponseEntity<List<Country>> getCountries()
    {
        try {
            return new ResponseEntity<>(countryService.getAllCountries(), HttpStatus.FOUND);
        }catch (NoDataFoundException exc)
        {
            throw new NoDataFoundException();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Country> getCountryById(@PathVariable Long id)
    {
        try {
            return new ResponseEntity<>(countryService.getCountryById(id), HttpStatus.FOUND);
        }
        catch (CountryNotFoundException exc) {
            throw new CountryNotFoundException(id);
        }
    }



    @PostMapping
    public ResponseEntity<Country> addCountry(@RequestBody Country newCountry)
    {
        Country country = countryService.addCountry(newCountry);
        return new ResponseEntity<>(country,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable Long id, @RequestBody Country country)
    {
        try {
            return new ResponseEntity<>(countryService.updateCountry(id,country), HttpStatus.OK);
        }
        catch (CountryNotFoundException exc) {
            throw new CountryNotFoundException(id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Country> deleteCountry(@PathVariable Long id)
    {
        try {
            return new ResponseEntity<>(countryService.deleteCountry(id), HttpStatus.OK);
        }
        catch (CountryNotFoundException exc) {
            throw new CountryNotFoundException(id);
        }


    }
}
