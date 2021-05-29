package com.covidapitest.oriolcasas.utils;

import android.util.Pair;

import com.covidapitest.oriolcasas.model.CasesModel;
import com.covidapitest.oriolcasas.model.CountryData;
import com.covidapitest.oriolcasas.model.CountryHistory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Utils class to parse the different responses.
 */
public class JsonParser {
    private static final String JSON_ROOT = "All";
    private static final String JSON_CONFIRMED = "confirmed";
    private static final String JSON_RECOVERED = "recovered";
    private static final String JSON_DEATHS = "deaths";
    private static final String JSON_POPULATION = "population";
    private static final String JSON_LAST_UPDATE = "updated";
    private static final String JSON_DATES = "dates";

    private static final float POPULATION_100K = 100000f;

    /**
     * Parses the json from cases' response.
     * @param response The json in a string.
     * @return the {@link CasesModel} after parsing the json.
     * @throws JSONException If the json can't be parsed an error is thrown.
     */
    public static CasesModel parseCasesResponse(String response) throws JSONException {
        JSONObject data = new JSONObject(response);
        CasesModel model = new CasesModel();
        Map<String, CountryData> countryDataMap = new HashMap<>();
        Iterator<?> keys = data.keys(); //The keys are the name of the countries
        String key;
        while (keys.hasNext()) {
            key = (String) keys.next();
            JSONObject countryJson = data.getJSONObject(key).getJSONObject(JSON_ROOT);
            countryDataMap.put(key, parseCountryData(countryJson));
        }
        model.setCountriesData(countryDataMap);
        return model;
    }

    /**
     * Parses the {@link CountryData} from an specific <code>JSONObject</code>.
     * @param jsonObject The json with the needed data to populate the country data.
     * @return The model after parsing the needed values from the json.
     * @throws JSONException If the json can't be parsed an error is thrown.
     */
    public static CountryData parseCountryData(JSONObject jsonObject) throws JSONException {
        // -1 is used for the user to see a value isn't valid as in this case any if these numbers should be negative
        int active;
        int confirmed = -1;
        int recovered = -1;
        int deaths = -1;
        int population = -1;

        // Parsing every param if it's present in the JSON
        CountryData countryData = new CountryData();
        if (jsonObject.has(JSON_CONFIRMED)) {
            confirmed = jsonObject.getInt(JSON_CONFIRMED);
        }
        if (jsonObject.has(JSON_RECOVERED)) {
            recovered = jsonObject.getInt(JSON_RECOVERED);
        }
        if (jsonObject.has(JSON_DEATHS)) {
            deaths = jsonObject.getInt(JSON_DEATHS);
        }
        if (jsonObject.has(JSON_POPULATION)) {
            population = jsonObject.getInt(JSON_POPULATION);
        }
        if (jsonObject.has(JSON_LAST_UPDATE)) {
            countryData.setLastUpdate(jsonObject.getString(JSON_LAST_UPDATE));
        }

        // Active cases calculation
        active = confirmed - recovered - deaths;

        // Parameters are set to the class
        countryData.setActiveCases(active);
        countryData.setNumberDeaths(deaths);
        countryData.setActivePer100k(active / (population / POPULATION_100K));
        countryData.setDeathsPer100k(deaths / (population / POPULATION_100K));

        return countryData;
    }

    /**
     * Parses the json from history's response.
     * @param response The json returned by the api.
     * @return The model of {@link CountryHistory} after parsing the needed values from the json.
     * @throws JSONException If the json can't be parsed an error is thrown.
     */
    public static CountryHistory parseHistoryResponse(String response) throws JSONException {
        JSONObject data = new JSONObject(response).getJSONObject(JSON_ROOT).getJSONObject(JSON_DATES);
        CountryHistory countryHistory = new CountryHistory();
        List<Pair<String, Integer>> dates = new ArrayList<>();
        Iterator<?> keys = data.keys(); // The keys are the dates
        String key;
        while (keys.hasNext()) {
            key = (String) keys.next();
            dates.add(new Pair<>(key, data.getInt(key)));
        }
        countryHistory.setDates(dates);
        return countryHistory;
    }
}
