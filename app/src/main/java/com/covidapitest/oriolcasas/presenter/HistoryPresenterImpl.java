package com.covidapitest.oriolcasas.presenter;

import android.content.Context;

import com.covidapitest.oriolcasas.model.CountryHistory;
import com.covidapitest.oriolcasas.repository.HistoryRepository;
import com.covidapitest.oriolcasas.repository.PetitionCallback;

/**
 * The {@link HistoryPresenter} implementation. It will do the requests and send the response to the view.
 */
public class HistoryPresenterImpl implements HistoryPresenter {
    private View view;
    private HistoryRepository historyRepository;

    /**
     * The constructor which needs a context for the repository
     * @param context Needed by the repository to get strings for the errors.
     */
    public HistoryPresenterImpl(Context context) {
        historyRepository = new HistoryRepository(new PetitionCallback() {
            @Override
            public void onSuccess(Object result) {
                if (result instanceof CountryHistory)
                    view.onHistoryReceived((CountryHistory) result);
            }

            @Override
            public void onError(String message) {
                view.showError(message);
            }
        }, context);
    }

    /**
     * Setting the view.
     * @param view The view which will be showing the response.
     */
    @Override
    public void setView(View view) {
        this.view = view;
    }

    /**
     * Makes the petition to the repository.
     * @param country Which country we want to know the confirmed cases.
     */
    @Override
    public void requestHistoryData(String country) {
        historyRepository.makeRequest(country);
    }
}
