package com.covidapitest.oriolcasas.utils;

/**
 * An enum which declares the accepted type of sorting by the countries list.
 */
public enum SortingType {
    NONE, // Used as a default. In fact, it will sort by countries' name.
    ACTIVE,
    DEATHS,
    ACTIVE100K,
    DEATHS100K
}
