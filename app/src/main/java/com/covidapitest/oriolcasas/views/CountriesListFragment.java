package com.covidapitest.oriolcasas.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.covidapitest.oriolcasas.MainActivity;
import com.covidapitest.oriolcasas.R;
import com.covidapitest.oriolcasas.model.CasesModel;
import com.covidapitest.oriolcasas.model.CountryData;
import com.covidapitest.oriolcasas.utils.Comparators;
import com.covidapitest.oriolcasas.utils.SortingType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The fragment which shows the countries' list and sorts them.
 */
public class CountriesListFragment extends Fragment implements CountryClickListener {
    private CasesModel casesModel;
    private CountriesListAdapter adapter;
    private RecyclerView countriesListView;

    /**
     * Static constructor. This way we can ensure the params are available in the new instance.
     * @param casesModel List of countries and their data.
     * @return The created fragment which contains the list.
     */
    public static CountriesListFragment newInstance(CasesModel casesModel) {
        CountriesListFragment fragment = new CountriesListFragment();
        fragment.setCasesModel(casesModel);
        return fragment;
    }

    private void setCasesModel(CasesModel casesModel) {
        this.casesModel = casesModel;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_countries_list, container, false);
        bindViews(layout);
        setData();
        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            ((MainActivity) getActivity()).setMenuVisibility(true);
        }
    }

    private void bindViews(View view) {
        countriesListView = view.findViewById(R.id.countries_list);
    }

    private void setData() {
        adapter = new CountriesListAdapter(casesModel, this);
        countriesListView.setAdapter(adapter);
        countriesListView.setLayoutManager(new LinearLayoutManager(getContext()));
        // By default they are ordered by countries' name
        sortList(SortingType.NONE);
    }

    public void sortList(SortingType sortingType) {
        switch (sortingType) {
            case NONE:
                casesModel.setCountriesData(sortedMap(new Comparators.CountriesComparator()));
                break;
            case ACTIVE:
                casesModel.setCountriesData(sortedMap(new Comparators.ActiveCasesComparator()));
                break;
            case DEATHS:
                casesModel.setCountriesData(sortedMap(new Comparators.DeathsCasesComparator()));
                break;
            case ACTIVE100K:
                casesModel.setCountriesData(sortedMap(new Comparators.ActiveCases100kComparator()));
                break;
            case DEATHS100K:
                casesModel.setCountriesData(sortedMap(new Comparators.DeathsCases100kComparator()));
                break;
            default:
                break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCountryClickedListener(String country) {
        if (getActivity() != null && getActivity() instanceof MainActivity) {
            CountryDetailFragment fragment = CountryDetailFragment.newInstance(country, casesModel.getCountriesData().get(country));
            ((MainActivity) getActivity()).navigateToFragment(fragment);
        }
    }

    /**
     * A new map is created using the requested sorting.
     * @param comparator How are the countries sorted.
     * @return The list sorted.
     */
    private Map<String, CountryData> sortedMap(Comparator<Map.Entry<String, CountryData>> comparator) {
        // The Map is converted to an ArrayList containing every entry of the map
        List<Map.Entry<String, CountryData>> list = new ArrayList<>(casesModel.getCountriesData().entrySet());
        // These entries are sorted using the chosen comparator
        list.sort(comparator);

        // Once every entry is sorted the ArrayList is converted back to a Map
        Map<String, CountryData> result = new LinkedHashMap<>();
        for (Map.Entry<String, CountryData> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
}
