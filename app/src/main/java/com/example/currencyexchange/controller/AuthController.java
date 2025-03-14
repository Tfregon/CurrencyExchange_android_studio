package com.example.currencyexchange.controller;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthController {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public Task<AuthResult> registerUser(String email, String password){
        return mAuth.createUserWithEmailAndPassword(email,password);
    }

    public Task<AuthResult> loginUser(String email, String password){
        return mAuth.signInWithEmailAndPassword(email,password);
    }

    public boolean isUserLoggedIn(){
        return mAuth.getCurrentUser() != null;
    }

    public void logoutUser(){
        mAuth.signOut();
    }
}

