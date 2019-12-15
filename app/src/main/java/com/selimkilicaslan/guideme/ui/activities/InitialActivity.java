package com.selimkilicaslan.guideme.ui.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.selimkilicaslan.guideme.R;
import com.selimkilicaslan.guideme.classes.MyAppCompatActivity;

public class InitialActivity extends MyAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(mUser != null){

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        setContentView(R.layout.activity_initial);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void loginButtonOnClick(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    public void registerButtonOnClick(View view) {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }
}
