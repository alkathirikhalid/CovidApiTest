package com.covidapitest.oriolcasas.model;

import androidx.annotation.Nullable;

/**
 * The class which contains a country's data to show
 */
public class CountryData {
    private int activeCases;
    private int numberDeaths;
    private String lastUpdate;
    private Double activePer100k;
    private Double deathsPer100k;

    public int getActiveCases() {
        return activeCases;
    }

    public void setActiveCases(int activeCases) {
        this.activeCases = activeCases;
    }

    public int getNumberDeaths() {
        return numberDeaths;
    }

    public void setNumberDeaths(int numberDeaths) {
        this.numberDeaths = numberDeaths;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public double getActivePer100k() {
        return activePer100k;
    }

    public void setActivePer100k(double activePer100k) {
        this.activePer100k = activePer100k;
    }

    public double getDeathsPer100k() {
        return deathsPer100k;
    }

    public void setDeathsPer100k(double deathsPer100k) {
        this.deathsPer100k = deathsPer100k;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof CountryData) {
            CountryData comparison = (CountryData) obj;
            if (getActiveCases() != comparison.getActiveCases())
                return false;
            if (getNumberDeaths() != comparison.getNumberDeaths())
                return false;
            if (!getLastUpdate().equals(comparison.getLastUpdate()))
                return false;
            if (getActivePer100k() != comparison.getActivePer100k())
                return false;
            if (getDeathsPer100k() != comparison.getDeathsPer100k())
                return false;
            return true;
        } else {
            return false;
        }
    }
}
