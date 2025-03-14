package com.example.currencyexchange.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.currencyexchange.R;
import com.example.currencyexchange.controller.AuthController;

public class LoginActivity extends AppCompatActivity {
    private EditText editEmail, editPassword;
    private Button btnLogin, btnSignup;
    private AuthController authController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);
        authController = new AuthController();

        btnLogin.setOnClickListener(v -> {
            String email = editEmail.getText().toString().trim();
            String password = editPassword.getText().toString().trim();
            authController.loginUser(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(this,"Login failed.",Toast.LENGTH_SHORT).show();
                }
            });
        });

        btnSignup.setOnClickListener(v -> {
            String email = editEmail.getText().toString().trim();
            String password = editPassword.getText().toString().trim();
            authController.registerUser(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Toast.makeText(this,"Signup successful.",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,"Signup failed.",Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
