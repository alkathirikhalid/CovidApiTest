package com.covidapitest.oriolcasas.presenter;

import com.covidapitest.oriolcasas.model.CasesModel;

/**
 * The interface which declares the functions needed from a presenter and a view class
 * to request and show all countries and their data.
 */
public interface MainPresenter {

    /**
     * The functions a view needs to show the retrieved data
     */
    interface View {
        void onDataRetrieved(CasesModel data);
        void showError(String message);
    }

    void setView(View view);
    void requestData();
}
