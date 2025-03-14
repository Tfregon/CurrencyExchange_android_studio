package com.example.currencyexchange.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.currencyexchange.R;
import com.example.currencyexchange.controller.AuthController;
import com.example.currencyexchange.controller.ConversionController;
import com.example.currencyexchange.controller.HistoryController;
import com.example.currencyexchange.model.Conversion;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Spinner spinnerFrom, spinnerTo;
    private EditText inputAmount;
    private Button btnConvert, btnHistory, btnLogout;
    private TextView txtResult;
    private AuthController authController;
    private ConversionController conversionController;
    private HistoryController historyController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        inputAmount = findViewById(R.id.inputAmount);
        btnConvert = findViewById(R.id.btnConvert);
        btnHistory = findViewById(R.id.btnHistory);
        btnLogout = findViewById(R.id.btnLogout);
        txtResult = findViewById(R.id.txtResult);

        authController = new AuthController();
        conversionController = new ConversionController();
        historyController = new HistoryController(this);

        // Redireciona para login caso não esteja logado
        if(!authController.isUserLoggedIn()){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        // Preenche os spinners com moedas fixas
        String[] currencies = {"USD", "BRL", "EUR", "CAD", "GBP"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currencies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

        btnConvert.setOnClickListener(v -> {
            String from = spinnerFrom.getSelectedItem().toString();
            String to = spinnerTo.getSelectedItem().toString();
            String amountText = inputAmount.getText().toString();

            if(amountText.isEmpty()){
                Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show();
                return;
            }

            double amount = Double.parseDouble(amountText);

            conversionController.performConversion(this, from, to, amount, new ConversionController.ConversionResult(){
                @Override
                public void onSuccess(double convertedValue){
                    txtResult.setText(String.format("%.2f",convertedValue));
                    historyController.addConversion(new Conversion(from, to, amount, convertedValue, new Date().toString()));
                }
                @Override
                public void onFailure(Exception e){
                    Toast.makeText(MainActivity.this,"Conversion Error!",Toast.LENGTH_SHORT).show();
                }
            });
        });

        btnHistory.setOnClickListener(v -> {
            startActivity(new Intent(this, HistoryActivity.class));
        });

        btnLogout.setOnClickListener(v -> {
            authController.logoutUser();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}
