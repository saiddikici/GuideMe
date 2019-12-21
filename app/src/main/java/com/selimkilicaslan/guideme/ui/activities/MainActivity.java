package com.selimkilicaslan.guideme.ui.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.selimkilicaslan.guideme.R;
import com.selimkilicaslan.guideme.classes.MyAppCompatActivity;
import com.selimkilicaslan.guideme.classes.User;
import com.selimkilicaslan.guideme.types.UserType;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends MyAppCompatActivity {

    BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Log.d("TOKEN", FirebaseInstanceId.getInstance().getToken());
        final ProgressDialog dialog = ProgressDialog.show(MainActivity.this, "",
                "Loading. Please wait...", true);
        navView = findViewById(R.id.nav_view);

        final AppCompatActivity appCompatActivity = this;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        DocumentReference reference = mDatabase.collection("users").document(mUser.getUid());
        reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                AppBarConfiguration appBarConfiguration;
                User user = documentSnapshot.toObject(User.class);
                NavController navController = Navigation.findNavController(appCompatActivity, R.id.nav_host_fragment);
                if(user != null){
                    if(user.getUserType() == UserType.TOURIST){
                        appBarConfiguration = new AppBarConfiguration.Builder(
                                R.id.navigation_search, R.id.navigation_inbox, R.id.navigation_profile)
                                .build();
                        navView.inflateMenu(R.menu.menu_bottom_nav_tourist);
                        navController.setGraph(R.navigation.mobile_navigation_tourist);


                    } else {
                        appBarConfiguration = new AppBarConfiguration.Builder(
                                R.id.navigation_guide_home, R.id.navigation_inbox, R.id.navigation_profile)
                                .build();
                        navView.inflateMenu(R.menu.menu_bottom_nav_guide);
                        navController.setGraph(R.navigation.mobile_navigation_guide);
                    }
                    NavigationUI.setupActionBarWithNavController(appCompatActivity, navController, appBarConfiguration);
                    NavigationUI.setupWithNavController(navView, navController);
                    dialog.dismiss();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.actionLogout:
                DocumentReference ref = mDatabase.collection("users").document(mUser.getUid());
                ref.update("token", "").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mAuth.signOut();
                        Toast.makeText(getApplicationContext(), "Logged out", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                break;

        }


        return true;
    }

    @Override
    public void onBackPressed() {
        if(navView.getSelectedItemId() == R.id.navigation_search || navView.getSelectedItemId() == R.id.navigation_guide_home) {

        } else {
        }
        super.onBackPressed();
    }


}
