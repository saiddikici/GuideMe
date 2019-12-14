package com.selimkilicaslan.guideme.ui.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.selimkilicaslan.guideme.R;

public class InitialActivity extends MyAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(fUser != null){
            Intent intent = new Intent(getApplicationContext(), SearchGuideActivity.class);
            startActivity(intent);
        }
        setContentView(R.layout.activity_initial);



    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void loginButtonOnClick(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void registerButtonOnClick(View view) {
        Intent intent = new Intent(getApplicationContext(), InboxActivity.class);
        startActivity(intent);
    }
}
