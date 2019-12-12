package com.selimkilicaslan.guideme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class InitialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
    }

    public void loginButtonOnClick(View view) {
        Intent intent = new Intent(getApplicationContext(), ProfileSettingsActivity.class);
        startActivity(intent);
    }

    public void registerButtonOnClick(View view) {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }
}
