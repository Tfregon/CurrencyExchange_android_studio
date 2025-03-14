package com.example.currencyexchange.model;

import android.content.Context;

import org.json.JSONObject;
import android.content.Context;
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

    public static void fetchConversionRate(Context context, String currencyPair, final VolleyCallback callback){
        String url = "https://economia.awesomeapi.com.br/json/last/" + currencyPair;
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> callback.onSuccess(response),
                error -> callback.onError(error));

        queue.add(request);
    }
}
