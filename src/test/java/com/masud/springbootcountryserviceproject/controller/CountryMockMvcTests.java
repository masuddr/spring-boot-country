package com.masud.springbootcountryserviceproject.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.masud.springbootcountryserviceproject.entity.Country;
import com.masud.springbootcountryserviceproject.service.CountryService;
import com.masud.springbootcountryserviceproject.service.CountryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.ArrayList;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ComponentScan("com.masud.springbootcountryserviceproject")
@AutoConfigureMockMvc
@SpringBootTest(classes = CountryMockMvcTests.class)
 class CountryMockMvcTests {


    MockMvc mockMvc;

    List<Country> myCountries = new ArrayList<>();

    Country country;

    @Mock
    CountryServiceImpl countryService;

    @InjectMocks
    CountryController countryController;


    @BeforeEach
    public void setUp()
    {
        mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();
        myCountries.add(Country.builder().id(1L).countryName("Turkey").countryCapital("Ankara").build());
        myCountries.add(Country.builder().id(2L).countryName("Netherlands").countryCapital("Amsterdam").build());
        myCountries.add(Country.builder().id(3L).countryName("Spain").countryCapital("Madrid").build());

        country = Country.builder().id(1L).countryName("UK").countryCapital("London").build();
    }

    @Test
    void test_allCountries() throws Exception {
        when(countryService.getAllCountries()).thenReturn(myCountries);

        mockMvc.perform(get("/api/countries")).andExpect(status().isFound())
                .andDo(print());

    }


    @Test
    void test_countryByID() throws Exception
    {
        Long countryID = 1L;
        when(countryService.getCountryById(countryID)).thenReturn(country);

        mockMvc.perform(get("/api/countries/{id}",countryID))
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath(".id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("UK"))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("London"))
                .andDo(print());
    }

    @Test
    void test_addCountry() throws Exception
    {
        when(countryService.addCountry(country)).thenReturn(country);

        ObjectMapper mapper = new ObjectMapper();
        String jsonBody = mapper.writeValueAsString(country);

        mockMvc.perform(post("/api/countries").content(jsonBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("UK"))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("London"))
                .andDo(print());
    }


    @Test
    void test_updateCountry() throws Exception
    {
        Long countryID = 1L;
        country.setCountryCapital("Birmingham");
        when(countryService.getCountryById(countryID)).thenReturn(country);
        when(countryService.updateCountry(countryID,country)).thenReturn(country);

        ObjectMapper mapper = new ObjectMapper();
        String jsonBody = mapper.writeValueAsString(country);

        mockMvc.perform(put("/api/countries/{id}",countryID).content(jsonBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("UK"))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Birmingham"))
                .andDo(print());
    }

    @Test
    void test_deleteCountry() throws Exception
    {
        Long countryID = 1L;

        when(countryService.getCountryById(countryID)).thenReturn(country);
        when(countryService.deleteCountry(countryID)).thenReturn(country);



        mockMvc.perform(delete("/api/countries/{id}",countryID))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("UK"))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("London"))
                .andDo(print());
    }




}
