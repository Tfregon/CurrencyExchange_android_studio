package com.example.currencyexchange.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, "currency_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE conversions(id INTEGER PRIMARY KEY AUTOINCREMENT, original_currency TEXT, target_currency TEXT, original_amount REAL, converted_amount REAL, timestamp TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS conversions");
        onCreate(db);
    }

    public void insertConversion(Conversion conversion){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("original_currency", conversion.originalCurrency);
        values.put("target_currency", conversion.targetCurrency);
        values.put("original_amount", conversion.originalAmount);
        values.put("converted_amount", conversion.convertedAmount);
        values.put("timestamp", conversion.timestamp);
        db.insert("conversions", null, values);
    }

    public Cursor getAllConversions(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM conversions ORDER BY id DESC", null);
    }
}
