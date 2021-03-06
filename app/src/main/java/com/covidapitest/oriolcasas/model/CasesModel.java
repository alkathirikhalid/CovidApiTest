package com.covidapitest.oriolcasas.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Root class for the response of cases service which contains a map with the country and its data
 */
public class CasesModel {
    private Map<String, CountryData> countriesData;

    public Map<String, CountryData> getCountriesData() {
        if (countriesData == null)
            countriesData = new HashMap<>(); // If it's null return empty
        return countriesData;
    }

    public void setCountriesData(Map<String, CountryData> countriesData) {
        this.countriesData = countriesData;
    }
}
