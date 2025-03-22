package com.example.currencyexchange.controller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.currencyexchange.model.ApiService;

import org.json.JSONObject;

public class ConversionController {

    public interface ConversionResult {
        void onSuccess(double convertedValue);
        void onFailure(Exception e);
    }

    public void performConversion(Context context, String from, String to, double amount, ConversionResult callback) {
        // ✅ Limita o plano gratuito: apenas base USD
        if (!from.equals("USD")) {
            Toast.makeText(context, "Free plan only allows conversions from USD.", Toast.LENGTH_LONG).show();
            callback.onFailure(new Exception("Base must be USD."));
            return;
        }

        String currencyPair = from + "-" + to;

        ApiService.fetchConversionRate(context, currencyPair, new ApiService.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    Log.d("ConversionController", "Response: " + response.toString());

                    JSONObject rates = response.getJSONObject("rates");
                    String rateStr = rates.getString(to);
                    double rate = Double.parseDouble(rateStr);
                    double convertedValue = amount * rate;

                    callback.onSuccess(convertedValue);
                } catch (Exception e) {
                    Log.e("ConversionController", "JSON parsing error: " + e.getMessage());
                    callback.onFailure(e);
                }
            }

            @Override
            public void onError(Exception e) {
                Log.e("ConversionController", "API error: " + e.getMessage());
                callback.onFailure(e);
            }
        });
    }
}
