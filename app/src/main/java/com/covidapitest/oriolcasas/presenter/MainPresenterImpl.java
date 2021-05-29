package com.covidapitest.oriolcasas.presenter;

import android.content.Context;

import com.covidapitest.oriolcasas.model.CasesModel;
import com.covidapitest.oriolcasas.repository.CasesRepository;
import com.covidapitest.oriolcasas.repository.PetitionCallback;

/**
 * The {@link MainPresenter} implementation. It will do the requests and send the response to the view.
 */
public class MainPresenterImpl implements MainPresenter {
    private View view;
    private CasesRepository repository;

    /**
     * The constructor which needs a context for the repository
     * @param context Needed by the repository to get strings for the errors.
     */
    public MainPresenterImpl(Context context) {
        repository = new CasesRepository(new PetitionCallback() {
            @Override
            public void onSuccess(Object response) {
                if (response instanceof CasesModel)
                    view.onDataRetrieved((CasesModel) response);
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
     */
    @Override
    public void requestData() {
        repository.makeRequest();
    }
}
