package com.example.currencyexchange.controller;

import android.content.Context;
import android.database.Cursor;

import com.example.currencyexchange.model.Conversion;
import com.example.currencyexchange.model.DatabaseHelper;

public class HistoryController {
    public DatabaseHelper db;

    public HistoryController(Context context){
        db = new DatabaseHelper(context);
    }

    public void addConversion(Conversion conversion){
        db.insertConversion(conversion);
    }

    public Cursor getConversions(){
        return db.getAllConversions();
    }
}
