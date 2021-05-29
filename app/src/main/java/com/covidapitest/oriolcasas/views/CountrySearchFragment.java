package com.covidapitest.oriolcasas.views;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.covidapitest.oriolcasas.MainActivity;
import com.covidapitest.oriolcasas.R;
import com.covidapitest.oriolcasas.model.CasesModel;

import java.util.List;

/**
 * The fragment which filters the countries by their name if the user wants to search for an specific one.
 */
public class CountrySearchFragment extends Fragment {

    private List<String> countries;
    private CasesModel casesModel;

    private TextView countryText;
    private ListView countriesList;

    /**
     * Static constructor. This way we can ensure the params are available in the new instance.
     * @param countries List with every country available.
     * @param casesModel The data of the countries needed by the details fragment.
     * @return The created fragment which contains the country and its data.
     */
    public static CountrySearchFragment newInstance(List<String> countries, CasesModel casesModel) {
        CountrySearchFragment fragment = new CountrySearchFragment();
        fragment.setCountries(countries);
        fragment.setCasesModel(casesModel);
        return fragment;
    }

    private void setCountries(List<String> countries) {
        this.countries = countries;
    }

    private void setCasesModel(CasesModel casesModel) {
        this.casesModel = casesModel;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_country_search, container, false);
        bindViews(layout);
        setData();
        return layout;
    }

    private void bindViews(View view) {
        countryText = view.findViewById(R.id.country_search_edittext);
        countriesList = view.findViewById(R.id.country_search_list);
    }

    private void setData() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.country_search_item, countries);
        countriesList.setAdapter(adapter);
        countriesList.setTextFilterEnabled(false); // The reason is specified in the onTextChanged
        countriesList.setOnItemClickListener((parent, view, position, id) -> {
            if (getActivity() != null && getActivity() instanceof MainActivity) {
                // The string is retrieved from the view as the position does not correspond with the position in the array of countries
                String selectedCountry = ((TextView) view).getText().toString();
                CountryDetailFragment fragment = CountryDetailFragment.newInstance(selectedCountry, casesModel.getCountriesData().get(selectedCountry));
                ((MainActivity) getActivity()).navigateToFragment(fragment);
            }
        });

        // Everytime the edit text changes its content, the countriesList will be filtered
        countryText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString()); // Used this way to avoid the grey box of setTextFilterEnabled and setFilterText
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
