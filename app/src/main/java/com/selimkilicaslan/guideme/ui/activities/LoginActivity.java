package com.selimkilicaslan.guideme.ui.activities;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.selimkilicaslan.guideme.R;
import com.selimkilicaslan.guideme.classes.MyAppCompatActivity;

public class LoginActivity extends MyAppCompatActivity {

    EditText emailEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.currentPasswordEditText);
    }

    public void loginButtonOnClick(View view) {
        if(!emailEditText.getText().toString().equals("") && !passwordEditText.getText().toString().equals("")){
            String email, password;

            email = emailEditText.getText().toString();
            password = passwordEditText.getText().toString();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseInstanceId.getInstance().getInstanceId()
                                        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                                if (!task.isSuccessful()) {
                                                    return;
                                                }
                                                String token = task.getResult().getToken();
                                                mUser = mAuth.getCurrentUser();
                                                DocumentReference ref = mDatabase.collection("users").document(mUser.getUid());
                                                ref.update("token", token);
                                                Log.d("FIREBASETOKEN", token);

                                            }
                                        });

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);

                            } else {
                                Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
        else {
            Toast.makeText(getApplicationContext(), "Email or password cannot be empty!", Toast.LENGTH_SHORT).show();
        }


    }
}
