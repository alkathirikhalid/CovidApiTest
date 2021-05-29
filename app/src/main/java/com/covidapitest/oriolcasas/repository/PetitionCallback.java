package com.covidapitest.oriolcasas.repository;

/**
 * This interface indicates which functions can the repositories call to return the results.
 */
public interface PetitionCallback {
    /**
     * The function called when the api returns a response.
     * @param result Object which contains the response.
     */
    void onSuccess(Object result);

    /**
     * The function called when there has been an error during the api call or parsing the response.
     * @param message Indicates the error that has happened.
     */
    void onError(String message);
}
