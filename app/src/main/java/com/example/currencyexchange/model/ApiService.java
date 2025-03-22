package com.example.currencyexchange.model;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class ApiService {

    public interface VolleyCallback {
        void onSuccess(JSONObject response);
        void onError(Exception e);
    }

    public static void fetchConversionRate(Context context, String currencyPair, final VolleyCallback callback) {
        String apiKey = "d5dfa79cd0f24252af308280ec816d15";
        String baseCurrency = currencyPair.split("-")[0];
        String targetCurrency = currencyPair.split("-")[1];

        // ✅ Bloqueia se não for USD como base
        if (!baseCurrency.equals("USD")) {
            callback.onError(new Exception("Free plan only supports USD as base currency."));
            return;
        }

        // ✅ Remove 'base=USD' e usa padrão da API (USD já é o base por padrão)
        String url = "https://api.currencyfreaks.com/latest?apikey=" + apiKey +
                "&symbols=" + targetCurrency;

        Log.d("ApiService", "URL: " + url);

        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                callback::onSuccess,
                error -> {
                    Log.e("ApiService", "Volley error: " + error.toString());
                    callback.onError(error);
                });

        queue.add(request);
    }
}
