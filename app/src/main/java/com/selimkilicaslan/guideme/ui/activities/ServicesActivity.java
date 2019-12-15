package com.selimkilicaslan.guideme.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.selimkilicaslan.guideme.R;
import com.selimkilicaslan.guideme.adapters.ServicesOfferedAdapter;
import com.selimkilicaslan.guideme.classes.MyAppCompatActivity;
import com.selimkilicaslan.guideme.classes.ServiceOffered;
import com.selimkilicaslan.guideme.classes.User;

import java.util.ArrayList;

public class ServicesActivity extends MyAppCompatActivity {

    ArrayList<ServiceOffered> servicesOffered;
    ServicesOfferedAdapter servicesOfferedAdapter;

    RecyclerView servicesRecyclerView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        servicesRecyclerView = findViewById(R.id.servicesRecyclerView);

        servicesOffered = new ArrayList<>();
        servicesOffered.add(new ServiceOffered("Car", false));
        servicesOffered.add(new ServiceOffered("Motorcycle", false));
        servicesOffered.add(new ServiceOffered("Accommodation", false));
        servicesOffered.add(new ServiceOffered("Airport pickup", false));
        servicesOffered.add(new ServiceOffered("Bus station pickup", false));
        servicesOffered.add(new ServiceOffered("Train station pickup", false));
        servicesOffered.add(new ServiceOffered("Ferry dock pickup", false));

        DocumentReference docRef = mDatabase.collection("users").document(mUser.getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                try {
                    User user = documentSnapshot.toObject(User.class);
                    for(int i = 0; i < servicesOffered.size(); i++){
                        for (ServiceOffered service : user.getServicesOffered()) {
                            if(servicesOffered.get(i).getServiceName().equals(service.getServiceName())){
                                servicesOffered.get(i).setOffered(service.getOffered());
                            }
                        }
                    }
                    servicesOfferedAdapter.notifyDataSetChanged();
                } catch (Exception ex) {

                }
            }
        });

        servicesOfferedAdapter = new ServicesOfferedAdapter(getApplicationContext(), servicesOffered);
        servicesRecyclerView.setAdapter(servicesOfferedAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        servicesRecyclerView.setLayoutManager(linearLayoutManager);
    }

    public void updateButtonClick(View view) {
        servicesOffered = servicesOfferedAdapter.getItems();
        DocumentReference reference = mDatabase.collection("users").document(mUser.getUid());
        reference.update("servicesOffered", servicesOffered);
        Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
        finish();
    }
}
