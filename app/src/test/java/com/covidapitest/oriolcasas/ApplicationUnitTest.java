package com.covidapitest.oriolcasas;

import android.util.Pair;

import com.covidapitest.oriolcasas.model.CasesModel;
import com.covidapitest.oriolcasas.model.CountryData;
import com.covidapitest.oriolcasas.model.CountryHistory;
import com.covidapitest.oriolcasas.utils.Comparators;
import com.covidapitest.oriolcasas.utils.JsonParser;
import com.covidapitest.oriolcasas.views.CountriesListAdapter;
import com.google.common.collect.Maps;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ApplicationUnitTest {

    //---- COMPARATORS ----//

    @Test
    public void testAlphabeticalComparator() {
        Map<String, CountryData> dummyData = new HashMap<>();
        List<String> expectedResult = new ArrayList<>();

        dummyData.put("C", null);
        dummyData.put("E", null);
        dummyData.put("A", null);
        dummyData.put("Z", null);
        dummyData.put("T", null);
        dummyData.put("O", null);
        dummyData.put("B", null);

        expectedResult.add("A");
        expectedResult.add("B");
        expectedResult.add("C");
        expectedResult.add("E");
        expectedResult.add("O");
        expectedResult.add("T");
        expectedResult.add("Z");

        List<Map.Entry<String, CountryData>> dummyList = new ArrayList<>(dummyData.entrySet());
        dummyList.sort(new Comparators.CountriesComparator());

        List<String> sortedDummyKeys = new ArrayList<>();
        for (Map.Entry<String, CountryData> entry : dummyList) {
            sortedDummyKeys.add(entry.getKey());
        }

        assertEquals(expectedResult, sortedDummyKeys);
    }

    @Test
    public void testActiveCasesComparator() {
        Map<String, CountryData> dummyData = new HashMap<>();
        Map<String, CountryData> expectedResult = new HashMap<>();

        CountryData dummy1 = new CountryData();
        dummy1.setActiveCases(1);
        CountryData dummy2 = new CountryData();
        dummy2.setActiveCases(29);
        CountryData dummy3 = new CountryData();
        dummy3.setActiveCases(10);
        CountryData dummy4 = new CountryData();
        dummy4.setActiveCases(20);
        CountryData dummy5 = new CountryData();
        dummy5.setActiveCases(22);
        CountryData dummy6 = new CountryData();
        dummy6.setActiveCases(30);
        dummyData.put("C", dummy1);
        dummyData.put("E", dummy2);
        dummyData.put("A", dummy3);
        dummyData.put("Z", dummy4);
        dummyData.put("T", dummy5);
        dummyData.put("O", dummy6);

        expectedResult.put("O", dummy6);
        expectedResult.put("E", dummy2);
        expectedResult.put("T", dummy5);
        expectedResult.put("Z", dummy4);
        expectedResult.put("A", dummy3);
        expectedResult.put("C", dummy1);

        List<Map.Entry<String, CountryData>> list = new ArrayList<>(dummyData.entrySet());
        list.sort(new Comparators.ActiveCasesComparator());
        Map<String, CountryData> sortedDummy = new LinkedHashMap<>();
        for (Map.Entry<String, CountryData> entry : list) {
            sortedDummy.put(entry.getKey(), entry.getValue());
        }

        assertTrue(Maps.difference(expectedResult, sortedDummy).areEqual());
    }

    @Test
    public void testDeathCasesComparator() {
        Map<String, CountryData> dummyData = new HashMap<>();
        Map<String, CountryData> expectedResult = new HashMap<>();

        CountryData dummy1 = new CountryData();
        dummy1.setNumberDeaths(1);
        CountryData dummy2 = new CountryData();
        dummy2.setNumberDeaths(29);
        CountryData dummy3 = new CountryData();
        dummy3.setNumberDeaths(10);
        CountryData dummy4 = new CountryData();
        dummy4.setNumberDeaths(20);
        CountryData dummy5 = new CountryData();
        dummy5.setNumberDeaths(22);
        CountryData dummy6 = new CountryData();
        dummy6.setNumberDeaths(30);
        dummyData.put("C", dummy1);
        dummyData.put("E", dummy2);
        dummyData.put("A", dummy3);
        dummyData.put("Z", dummy4);
        dummyData.put("T", dummy5);
        dummyData.put("O", dummy6);

        expectedResult.put("O", dummy6);
        expectedResult.put("E", dummy2);
        expectedResult.put("T", dummy5);
        expectedResult.put("Z", dummy4);
        expectedResult.put("A", dummy3);
        expectedResult.put("C", dummy1);

        List<Map.Entry<String, CountryData>> list = new ArrayList<>(dummyData.entrySet());
        list.sort(new Comparators.DeathsCasesComparator());
        Map<String, CountryData> sortedDummy = new LinkedHashMap<>();
        for (Map.Entry<String, CountryData> entry : list) {
            sortedDummy.put(entry.getKey(), entry.getValue());
        }

        assertTrue(Maps.difference(expectedResult, sortedDummy).areEqual());
    }

    @Test
    public void testActiveCases100kComparator() {
        Map<String, CountryData> dummyData = new HashMap<>();
        Map<String, CountryData> expectedResult = new HashMap<>();

        CountryData dummy1 = new CountryData();
        dummy1.setActivePer100k(1.2);
        CountryData dummy2 = new CountryData();
        dummy2.setActivePer100k(2.0);
        CountryData dummy3 = new CountryData();
        dummy3.setActivePer100k(1.3);
        CountryData dummy4 = new CountryData();
        dummy4.setActivePer100k(1.5);
        CountryData dummy5 = new CountryData();
        dummy5.setActivePer100k(1.9);
        CountryData dummy6 = new CountryData();
        dummy6.setActivePer100k(2.3);
        dummyData.put("C", dummy1);
        dummyData.put("E", dummy2);
        dummyData.put("A", dummy3);
        dummyData.put("Z", dummy4);
        dummyData.put("T", dummy5);
        dummyData.put("O", dummy6);

        expectedResult.put("O", dummy6);
        expectedResult.put("E", dummy2);
        expectedResult.put("T", dummy5);
        expectedResult.put("Z", dummy4);
        expectedResult.put("A", dummy3);
        expectedResult.put("C", dummy1);

        List<Map.Entry<String, CountryData>> list = new ArrayList<>(dummyData.entrySet());
        list.sort(new Comparators.ActiveCases100kComparator());
        Map<String, CountryData> sortedDummy = new LinkedHashMap<>();
        for (Map.Entry<String, CountryData> entry : list) {
            sortedDummy.put(entry.getKey(), entry.getValue());
        }

        assertTrue(Maps.difference(expectedResult, sortedDummy).areEqual());
    }

    @Test
    public void testDeathCases100kComparator() {
        Map<String, CountryData> dummyData = new HashMap<>();
        Map<String, CountryData> expectedResult = new HashMap<>();

        CountryData dummy1 = new CountryData();
        dummy1.setDeathsPer100k(1.2);
        CountryData dummy2 = new CountryData();
        dummy2.setDeathsPer100k(2.0);
        CountryData dummy3 = new CountryData();
        dummy3.setDeathsPer100k(1.3);
        CountryData dummy4 = new CountryData();
        dummy4.setDeathsPer100k(1.5);
        CountryData dummy5 = new CountryData();
        dummy5.setDeathsPer100k(1.9);
        CountryData dummy6 = new CountryData();
        dummy6.setDeathsPer100k(2.3);
        dummyData.put("C", dummy1);
        dummyData.put("E", dummy2);
        dummyData.put("A", dummy3);
        dummyData.put("Z", dummy4);
        dummyData.put("T", dummy5);
        dummyData.put("O", dummy6);

        expectedResult.put("O", dummy6);
        expectedResult.put("E", dummy2);
        expectedResult.put("T", dummy5);
        expectedResult.put("Z", dummy4);
        expectedResult.put("A", dummy3);
        expectedResult.put("C", dummy1);

        List<Map.Entry<String, CountryData>> list = new ArrayList<>(dummyData.entrySet());
        list.sort(new Comparators.DeathsCases100kComparator());
        Map<String, CountryData> sortedDummy = new LinkedHashMap<>();
        for (Map.Entry<String, CountryData> entry : list) {
            sortedDummy.put(entry.getKey(), entry.getValue());
        }

        assertTrue(Maps.difference(expectedResult, sortedDummy).areEqual());
    }

    //---- JSON PARSERS ----//

    @Test
    public void testCasesModelParser() throws IOException, JSONException {
        Map<String, CountryData> expectedMap = new HashMap<>();

        CountryData data1 = new CountryData();
        data1.setActiveCases(9130);
        data1.setNumberDeaths(2881);
        data1.setLastUpdate("2021/05/28 16:20:47+00");
        data1.setActivePer100k(9130 / (35530081 / 100000f));
        data1.setDeathsPer100k(2881 / (35530081 / 100000f));
        expectedMap.put("Afghanistan", data1);

        CountryData data2 = new CountryData();
        data2.setActiveCases(775);
        data2.setNumberDeaths(2447);
        data2.setLastUpdate("2021/05/28 16:20:47+00");
        data2.setActivePer100k(775 / (2930187 / 100000f));
        data2.setDeathsPer100k(2447 / (2930187 / 100000f));
        expectedMap.put("Albania", data2);

        CountryData data3 = new CountryData();
        data3.setActiveCases(35438);
        data3.setNumberDeaths(3448);
        data3.setLastUpdate("2021/05/28 16:20:47+00");
        data3.setActivePer100k(35438 / (41318142 / 100000f));
        data3.setDeathsPer100k(3448 / (41318142 / 100000f));
        expectedMap.put("Algeria", data3);

        CountryData data4 = new CountryData();
        data4.setActiveCases(150);
        data4.setNumberDeaths(127);
        data4.setLastUpdate("2021/05/28 16:20:47+00");
        data4.setActivePer100k(150 / (76965 / 100000f));
        data4.setDeathsPer100k(127 / (76965 / 100000f));
        expectedMap.put("Andorra", data4);

        StringBuilder jsonString = new StringBuilder();
        String line;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/CasesModelJson")));
        while ((line = bufferedReader.readLine()) != null) {
            jsonString.append(line);
        }
        bufferedReader.close();

        assertTrue(Maps.difference(expectedMap, JsonParser.parseCasesResponse(jsonString.toString()).getCountriesData()).areEqual());
    }

    @Test
    public void testCountryDataParser() throws IOException, JSONException {
        CountryData expectedData = new CountryData();
        expectedData.setActiveCases(150);
        expectedData.setNumberDeaths(127);
        expectedData.setLastUpdate("2021/05/28 16:20:47+00");
        expectedData.setActivePer100k(150 / (76965 / 100000f));
        expectedData.setDeathsPer100k(127 / (76965 / 100000f));

        StringBuilder jsonString = new StringBuilder();
        String line;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/CountryDataJson")));
        while ((line = bufferedReader.readLine()) != null) {
            jsonString.append(line);
        }
        bufferedReader.close();

        assertEquals(expectedData, JsonParser.parseCountryData(new JSONObject(jsonString.toString())));
    }

    @Test
    public void testHistoryResponseParser() throws IOException, JSONException {
        List<Pair<String, Integer>> expectedList = new ArrayList<>();
        expectedList.add(new Pair<>("2020-12-25", 29580));
        expectedList.add(new Pair<>("2020-12-24", 29330));
        expectedList.add(new Pair<>("2020-12-23", 28909));
        expectedList.add(new Pair<>("2020-12-22", 28096));
        expectedList.add(new Pair<>("2020-12-21", 27110));

        StringBuilder jsonString = new StringBuilder();
        String line;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/HistoryDataJson")));
        while ((line = bufferedReader.readLine()) != null) {
            jsonString.append(line);
        }
        bufferedReader.close();

        CountryHistory fromJson = JsonParser.parseHistoryResponse(jsonString.toString());

        assertEquals(expectedList.size(), fromJson.getDates().size());
        int i = 0;
        for (Pair<String, Integer> entry : expectedList) {
            Pair<String, Integer> jsonEntry = fromJson.getDates().get(i);
            assertEquals(entry.first, jsonEntry.first);
            assertEquals(entry.second, jsonEntry.second);
            i++;
        }
    }

    //---- ADAPTER ----//

    @Test
    public void testCountriesListAdapter_nullObjects() {
        CountriesListAdapter countriesListAdapter = new CountriesListAdapter(null, null);

        assertEquals(0, countriesListAdapter.getItemCount());
    }

    @Test
    public void testCountriesListAdapter_emptyCases() {
        CasesModel casesModel = new CasesModel();
        casesModel.setCountriesData(null);

        CountriesListAdapter countriesListAdapter = new CountriesListAdapter(casesModel, null);

        assertEquals(0, countriesListAdapter.getItemCount());
    }

    @Test
    public void testCountriesListAdapter_filledCases() {
        CasesModel casesModel = new CasesModel();
        Map<String, CountryData> dummyData = new HashMap<>();

        CountryData dummy1 = new CountryData();
        dummy1.setActiveCases(1);
        CountryData dummy2 = new CountryData();
        dummy2.setActiveCases(29);
        CountryData dummy3 = new CountryData();
        dummy3.setActiveCases(10);
        CountryData dummy4 = new CountryData();
        dummy4.setActiveCases(20);
        CountryData dummy5 = new CountryData();
        dummy5.setActiveCases(22);
        CountryData dummy6 = new CountryData();
        dummy6.setActiveCases(30);
        dummyData.put("C", dummy1);
        dummyData.put("E", dummy2);
        dummyData.put("A", dummy3);
        dummyData.put("Z", dummy4);
        dummyData.put("T", dummy5);
        dummyData.put("O", dummy6);
        casesModel.setCountriesData(dummyData);

        CountriesListAdapter countriesListAdapter = new CountriesListAdapter(casesModel, null);

        assertEquals(6, countriesListAdapter.getItemCount());
    }
}
