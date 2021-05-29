package com.covidapitest.oriolcasas.presenter;

import com.covidapitest.oriolcasas.model.CountryHistory;

/**
 * The interface which declares the functions needed from a presenter and a view class
 * to request and show a country's confirmed cases
 */
public interface HistoryPresenter {

    /**
     * The functions a view needs to show the retrieved data
     */
    interface View {
        void onHistoryReceived(CountryHistory countryHistory);
        void showError(String message);
    }

    void setView(View view);
    void requestHistoryData(String country);
}
