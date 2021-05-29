package com.covidapitest.oriolcasas.repository;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.covidapitest.oriolcasas.R;
import com.covidapitest.oriolcasas.model.CasesModel;
import com.covidapitest.oriolcasas.utils.Constants;
import com.covidapitest.oriolcasas.utils.JsonParser;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The class which will do the petitions to the cases service.
 */
public class CasesRepository {
    /**
     * The service's URL
     */
    private static final String REQUEST_URL = "https://covid-api.mmediagroup.fr/v1/cases";

    /**
     * Instead of the deprecated AsyncTask, the executor is used to do the background tasks
     */
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    /**
     * Once the response is ready to be returned the executor calls the handler.
     * The handler post is run on the UIThread which is where the response should go.
     */
    private final Handler handler = new Handler(Looper.getMainLooper());

    private PetitionCallback callback;
    private Context context;
    private int responseCode;
    private CasesModel casesModel;

    /**
     * These two params are needed to return the response.
     * @param callback It returns the response or the error message.
     * @param context Needed to retrieve the error strings.
     */
    public CasesRepository(PetitionCallback callback, Context context) {
        this.callback = callback;
        this.context = context;
    }

    /**
     * The function which calls the service and parses the response.
     */
    public void makeRequest() {
        executor.execute(() -> {
            // If the casesModel is present in the memory, we can avoid to make the petition again.
            if (casesModel == null) {
                Log.d(Constants.TAG, REQUEST_URL);
                try {
                    URL url = new URL(REQUEST_URL);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod(Constants.REQUEST_METHOD);
                    connection.setConnectTimeout(Constants.TIMEOUT);
                    responseCode = connection.getResponseCode();
                    // Only a 200 OK is valid for this api so the response is only read in this case
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        StringBuilder responseBuilder = new StringBuilder();
                        String responseLine;
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        while ((responseLine = bufferedReader.readLine()) != null) {
                            responseBuilder.append(responseLine);
                        }
                        bufferedReader.close(); // The stream must be closed
                        Log.d(Constants.TAG, responseBuilder.toString());
                        handler.post(() -> {
                            try {
                                casesModel = JsonParser.parseCasesResponse(responseBuilder.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                                callback.onError(context.getString(R.string.http_parsing_error));
                            }
                            callback.onSuccess(casesModel);
                        });
                    } else {
                        handler.post(() -> callback.onError(context.getString(R.string.http_error)));
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    handler.post(() -> callback.onError(context.getString(R.string.http_request_bad_request)));
                } catch (IOException e) {
                    e.printStackTrace();
                    handler.post(() -> callback.onError(context.getString(R.string.http_request_io_exception)));
                }
            } else {
                handler.post(() -> callback.onSuccess(casesModel));
            }
        });
    }
}
