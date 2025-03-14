package com.example.currencyexchange.model;

public class Conversion {
    public String originalCurrency, targetCurrency, timestamp;
    public double originalAmount, convertedAmount;

    public Conversion(String originalCurrency, String targetCurrency, double originalAmount, double convertedAmount, String timestamp) {
        this.originalCurrency = originalCurrency;
        this.targetCurrency = targetCurrency;
        this.originalAmount = originalAmount;
        this.convertedAmount = convertedAmount;
        this.timestamp = timestamp;
    }
}
