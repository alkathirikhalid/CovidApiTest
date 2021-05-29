package com.covidapitest.oriolcasas.utils;

import com.covidapitest.oriolcasas.model.CountryData;

import java.util.Comparator;
import java.util.Map;

/**
 * It contains every comparator needed to sort the countries list.
 */
public class Comparators {

    /**
     * It sorts the countries' list alphabetically.
     */
    public static class CountriesComparator implements Comparator<Map.Entry<String, CountryData>> {

        @Override
        public int compare(Map.Entry<String, CountryData> o1, Map.Entry<String, CountryData> o2) {
            return o1.getKey().compareToIgnoreCase(o2.getKey()); // String comparison to return the countries in alphabetical order
        }
    }

    /**
     * Base class which retrieve the specific countries data to be compared.
     */
    private static abstract class CasesComparator implements Comparator<Map.Entry<String, CountryData>> {

        // Depending on the type of comparison different parameters from CountryData are compared
        protected abstract int compare(CountryData first, CountryData second);

        @Override
        public int compare(Map.Entry<String, CountryData> o1, Map.Entry<String, CountryData> o2) {
            CountryData first = o1.getValue();
            CountryData second = o2.getValue();
            return compare(first, second);
        }
    }

    /**
     * The countries are sorted by the active cases in descending order.
     */
    public static class ActiveCasesComparator extends CasesComparator {

        @Override
        protected int compare(CountryData first, CountryData second) {
            return second.getActiveCases() - first.getActiveCases(); // Second - First for the descending order
        }
    }

    /**
     * The countries are sorted by the death cases in descending order.
     */
    public static class DeathsCasesComparator extends CasesComparator {

        @Override
        protected int compare(CountryData first, CountryData second) {
            return second.getNumberDeaths() - first.getNumberDeaths(); // Second - First for the descending order
        }
    }

    /**
     * The countries are sorted by the active cases for 100k inhabitants in descending order.
     */
    public static class ActiveCases100kComparator extends CasesComparator {

        @Override
        protected int compare(CountryData first, CountryData second) {
            return Double.valueOf(second.getActivePer100k() - first.getActivePer100k()).intValue(); // Second - First for the descending order
        }
    }

    /**
     * The countries are sorted by the death cases for 100k inhabitants in descending order.
     */
    public static class DeathsCases100kComparator extends CasesComparator {

        @Override
        protected int compare(CountryData first, CountryData second) {
            return Double.valueOf(second.getDeathsPer100k() - first.getDeathsPer100k()).intValue(); // Second - First for the descending order
        }
    }
}
