package com.selimkilicaslan.guideme;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MyAppCompatActivity extends AppCompatActivity {
    public FirebaseAuth mAuth;
    public FirebaseStorage storage;
    public FirebaseUser fUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        fUser = mAuth.getCurrentUser();
    }
}
