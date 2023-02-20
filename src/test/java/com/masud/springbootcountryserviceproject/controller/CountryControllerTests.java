package com.masud.springbootcountryserviceproject.controller;

import com.masud.springbootcountryserviceproject.entity.Country;
import com.masud.springbootcountryserviceproject.exception.CountryNotFoundException;
import com.masud.springbootcountryserviceproject.exception.NoDataFoundException;
import com.masud.springbootcountryserviceproject.service.CountryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = CountryControllerTests.class)

  class CountryControllerTests {


    @Mock
    CountryServiceImpl countryService;

    @InjectMocks
    CountryController countryController;


    List<Country> myCountries = new ArrayList<>();

    Country country;

    @BeforeEach
    public void setUp() {
        System.out.println("Init");
        myCountries.add(Country.builder().id(1L).countryName("Turkey").countryCapital("Ankara").build());
        myCountries.add(Country.builder().id(2L).countryName("Netherlands").countryCapital("Amsterdam").build());
        myCountries.add(Country.builder().id(3L).countryName("Spain").countryCapital("Madrid").build());

        country = Country.builder().id(1L).countryName("UK").countryCapital("London").build();
    }


    @Test
    void should_return_allCountries()
    {
        when(countryService.getAllCountries()).thenReturn(myCountries);

        ResponseEntity<List<Country>> response = countryController.getCountries();

        assertEquals(HttpStatus.FOUND,response.getStatusCode());
        assertEquals(3,response.getBody().size());
    }

    @Test
    void should_return_country_byID() {
        Long countryID = 1L;
        when(countryService.getCountryById(countryID)).thenReturn(country);
        ResponseEntity<Country> response = countryController.getCountryById(2L);

        assertEquals(HttpStatus.FOUND, response.getStatusCode());

    }


    @Test
    void should_add_country() {

        when(countryService.addCountry(country)).thenReturn(country);
        ResponseEntity<Country> response = countryController.addCountry(country);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(country.getCountryName(),response.getBody().getCountryName());
    }

    @Test
    void should_update_country() {
        Long countryID = 1L;
        when(countryService.getCountryById(countryID)).thenReturn(country);
        when(countryService.updateCountry(countryID,country)).thenReturn(country);
        ResponseEntity<Country> response = countryController.updateCountry(countryID,country);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(country.getCountryName(),response.getBody().getCountryName());
        assertEquals(countryID,response.getBody().getId());
    }

    @Test
    void should_delete_country() {
        Long countryID = 1L;
        when(countryService.deleteCountry(countryID)).thenReturn(country);
        ResponseEntity<Country> response = countryController.deleteCountry(countryID);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(country.getCountryName(),response.getBody().getCountryName());
    }




}
