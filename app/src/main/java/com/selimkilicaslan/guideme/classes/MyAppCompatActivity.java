package com.selimkilicaslan.guideme.classes;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.storage.FirebaseStorage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MyAppCompatActivity extends AppCompatActivity {
    public FirebaseAuth mAuth;
    public FirebaseStorage mStorage;
    public FirebaseUser mUser;
    public FirebaseFirestore mDatabase;
    public FirebaseFunctions mFunctions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseFirestore.getInstance();
        mFunctions = FirebaseFunctions.getInstance();
    }
}
