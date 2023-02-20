package com.masud.springbootcountryserviceproject.service;


import com.masud.springbootcountryserviceproject.entity.Country;
import com.masud.springbootcountryserviceproject.exception.NoDataFoundException;
import com.masud.springbootcountryserviceproject.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = CountryServiceTests.class)
class CountryServiceTests {

    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private CountryServiceImpl countryService;

    List<Country> myCountries = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        System.out.println("Init");
        myCountries.add(Country.builder().id(1L).countryName("Turkey").countryCapital("Ankara").build());
        myCountries.add(Country.builder().id(2L).countryName("Netherlands").countryCapital("Amsterdam").build());
        myCountries.add(Country.builder().id(3L).countryName("Spain").countryCapital("Madrid").build());
    }


    @Test
    void should_return_allCountries() {

        when(countryRepository.findAll()).thenReturn(myCountries);

        int countrySize = countryService.getAllCountries().size();
        assertEquals(3, countrySize);
    }

    @Test
    void should_return_empty_countries() {

        when(countryRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(NoDataFoundException.class,
                () -> {
                    countryService.getAllCountries();
                });
    }

    @Test
    void should_return_country_byID() {

        when(countryRepository.findById(2L)).thenReturn(Optional.ofNullable(Country.builder().id(2L).countryName("Netherlands").countryCapital("Amsterdam").build()));
        Long expectedID = 2L;
        Long countryID = 2L;

        assertEquals(expectedID, countryService.getCountryById(countryID).getId());
    }

    @Test
    void should_return_country_byName() {

        when(countryRepository.findById(2L)).thenReturn(Optional.ofNullable(Country.builder().id(2L).countryName("Netherlands").countryCapital("Amsterdam").build()));
        String expectedID = "Netherlands";
        Long countryID = 2L;


        assertEquals(expectedID, countryService.getCountryById(countryID).getCountryName());
    }

    @Test
    void should_fail_on_return_countryByName() {

        when(countryRepository.findById(2L)).thenReturn(Optional.ofNullable(Country.builder().id(2L).countryName("Spain").countryCapital("Madrid").build()));
        String expectedID = "Netherlands";
        Long countryID = 2L;


        assertNotEquals(expectedID, countryService.getCountryById(countryID).getCountryName());
    }

    @Test
    void should_add_country() {

        Country country = Optional.ofNullable(Country.builder().id(2L).countryName("Spain").countryCapital("Madrid").build()).get();
        when(countryRepository.save(country)).thenReturn(country);

        assertEquals(country, countryService.addCountry(country));
    }

    @Test
    void should_update_country() {
        when(countryRepository.findById(2L)).thenReturn(Optional.ofNullable(Country.builder().id(2L).countryName("Spain").countryCapital("Madrid").build()));
        Country country = Optional.ofNullable(Country.builder().id(2L).countryName("Spain").countryCapital("Madrid").build()).get();
        when(countryRepository.save(country)).thenReturn(country);

        assertEquals(country, countryService.updateCountry(2L, country));
    }

    @Test
    void should_delete_country() {
        when(countryRepository.findById(2L)).thenReturn(Optional.ofNullable(Country.builder().id(2L).countryName("Spain").countryCapital("Madrid").build()));
        Country country = Optional.ofNullable(Country.builder().id(2L).countryName("Spain").countryCapital("Madrid").build()).get();
        when(countryRepository.save(country)).thenReturn(country);

        assertEquals(country, countryService.deleteCountry(2L));
    }


}
