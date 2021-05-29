package com.covidapitest.oriolcasas.views;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.covidapitest.oriolcasas.R;
import com.covidapitest.oriolcasas.model.CountryData;
import com.covidapitest.oriolcasas.model.CountryHistory;
import com.covidapitest.oriolcasas.presenter.HistoryPresenter;
import com.covidapitest.oriolcasas.presenter.HistoryPresenterImpl;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The fragment which shows a country details and a chart with the history of its confirmed cases.
 */
public class CountryDetailFragment extends Fragment implements HistoryPresenter.View {
    private static final DecimalFormat DOUBLE_DECIMALS = new DecimalFormat("#.00");

    private String countryName;
    private CountryData countryData;
    private TextView title, active, deaths, lastUpdate, active100k, deaths100k, error;
    private AnyChartView chartView;
    private CircularProgressIndicator progressIndicator;

    /**
     * Static constructor. This way we can ensure the params are available in the new instance.
     * @param country Name of the country.
     * @param countryData  The data of the selected country.
     * @return The created fragment which contains the country and its data.
     */
    public static CountryDetailFragment newInstance(String country, CountryData countryData) {
        CountryDetailFragment fragment = new CountryDetailFragment();
        fragment.setCountryName(country);
        fragment.setCountryData(countryData);
        return fragment;
    }

    private void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    private void setCountryData(CountryData countryData) {
        this.countryData = countryData;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_country_detail, container, false);
        bindViews(layout);
        setData();
        setPresenter();

        return layout;
    }

    private void bindViews(View view) {
        title = view.findViewById(R.id.country_title);
        active = view.findViewById(R.id.country_active_cases);
        deaths = view.findViewById(R.id.country_deaths);
        lastUpdate = view.findViewById(R.id.country_last_update);
        active100k = view.findViewById(R.id.country_active_100k);
        deaths100k = view.findViewById(R.id.country_deaths_100k);
        chartView = view.findViewById(R.id.country_history_chart);
        error = view.findViewById(R.id.country_history_chart_error);
        progressIndicator = view.findViewById(R.id.country_history_progress_indicator);
    }

    private void setData() {
        title.setText(countryName);
        active.setText(String.valueOf(countryData.getActiveCases()));
        deaths.setText(String.valueOf(countryData.getNumberDeaths()));
        lastUpdate.setText(countryData.getLastUpdate());
        active100k.setText(DOUBLE_DECIMALS.format(countryData.getActivePer100k()));
        deaths100k.setText(DOUBLE_DECIMALS.format(countryData.getDeathsPer100k()));
    }

    private void setPresenter() {
        HistoryPresenterImpl presenter = new HistoryPresenterImpl(getContext());
        presenter.setView(this);
        presenter.requestHistoryData(countryName);
    }

    @Override
    public void onHistoryReceived(CountryHistory countryHistory) {
        // The response is converted to a List which is recognized by AnyChart
        List<DataEntry> seriesData = new ArrayList<>();
        for (Pair<String, Integer> pair : countryHistory.getDates()) {
            seriesData.add(new ValueDataEntry(pair.first, pair.second));
        }

        // The dates come from most actual to oldest date, we need to reverse them
        Collections.reverse(seriesData);

        // The data will be represented using a line chart
        Cartesian cartesian = AnyChart.line();
        cartesian.title(getString(R.string.country_chart_title, countryName));
        cartesian.padding("5dp");
        cartesian.data(seriesData);

        chartView.setChart(cartesian);
        chartView.setVisibility(View.VISIBLE);
        progressIndicator.hide();
    }

    @Override
    public void showError(String message) {
        error.setText(message);
        if (getActivity() != null) {
            error.setTextColor(getResources().getColor(R.color.design_default_color_error, getActivity().getTheme()));
        }
        error.setVisibility(View.VISIBLE);
        progressIndicator.hide();
    }
}
