package com.example.currencyexchange.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.currencyexchange.R;

public class CompoundInterestActivity extends AppCompatActivity {

    private EditText editPrincipal, editRate, editPeriods;
    private Button btnCalculate;
    private TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compound_interest);

        editPrincipal = findViewById(R.id.editPrincipal);
        editRate = findViewById(R.id.editRate);
        editPeriods = findViewById(R.id.editPeriods);
        btnCalculate = findViewById(R.id.btnCalculate);
        textResult = findViewById(R.id.textResult);

        Button btnBack = findViewById(R.id.btnBackToMain);
        btnBack.setOnClickListener(v -> {
            finish(); // fecha a activity atual e volta para a anterior (MainActivity)
        });


        btnCalculate.setOnClickListener(v -> {
            String principalStr = editPrincipal.getText().toString();
            String rateStr = editRate.getText().toString();
            String periodsStr = editPeriods.getText().toString();

            if (principalStr.isEmpty() || rateStr.isEmpty() || periodsStr.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double principal = Double.parseDouble(principalStr);
                double rate = Double.parseDouble(rateStr) / 100.0;
                int periods = Integer.parseInt(periodsStr);

                double result = principal * Math.pow(1 + rate, periods);
                textResult.setText(String.format("Final Amount: %.2f", result));
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
