package com.example.currencyexchange.controller;

import android.content.Context;

import com.example.currencyexchange.model.ApiService;

import org.json.JSONException;
import org.json.JSONObject;

public class ConversionController {

    public interface ConversionResult {
        void onSuccess(double convertedValue);
        void onFailure(Exception e);
    }

    public void performConversion(Context context, String from, String to, double amount, ConversionResult callback){
        ApiService.fetchConversionRate(context, from + "-" + to, new ApiService.VolleyCallback(){
            @Override
            public void onSuccess(JSONObject response){
                try {
                    JSONObject pair = response.getJSONObject(from + to);
                    double rate = pair.getDouble("bid");
                    double result = amount * rate;
                    callback.onSuccess(result);
                } catch (JSONException e) {
                    callback.onFailure(e);
                }
            }

            @Override
            public void onError(Exception e) {
                callback.onFailure(e);
            }
        });
    }
}

