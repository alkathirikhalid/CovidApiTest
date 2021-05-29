package com.covidapitest.oriolcasas.model;

import android.util.Pair;

import androidx.annotation.Nullable;

import java.util.List;

/**
 * Contains a list with the date and the registered cases of a country
 */
public class CountryHistory {
    private List<Pair<String, Integer>> dates;

    public List<Pair<String, Integer>> getDates() {
        return dates;
    }

    public void setDates(List<Pair<String, Integer>> dates) {
        this.dates = dates;
    }
}
