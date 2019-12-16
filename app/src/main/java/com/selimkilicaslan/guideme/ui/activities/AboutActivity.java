package com.selimkilicaslan.guideme.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.selimkilicaslan.guideme.R;
import com.selimkilicaslan.guideme.classes.MyAppCompatActivity;

public class AboutActivity extends MyAppCompatActivity {

    EditText mottoEditText, aboutEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        mottoEditText = findViewById(R.id.mottoEditText);
        aboutEditText = findViewById(R.id.aboutEditText);
    }

    public void updateButtonClick(View view) {

        DocumentReference reference = mDatabase.collection("users").document(mUser.getUid());
        reference.update("quote", mottoEditText.getText().toString());
        reference.update("about", aboutEditText.getText().toString());
        Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
        finish();

    }
}
