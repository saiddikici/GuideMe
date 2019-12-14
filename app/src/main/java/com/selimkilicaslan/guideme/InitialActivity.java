package com.selimkilicaslan.guideme;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

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
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    public void registerButtonOnClick(View view) {
        Intent intent = new Intent(getApplicationContext(), InboxActivity.class);
        startActivity(intent);
    }
}
