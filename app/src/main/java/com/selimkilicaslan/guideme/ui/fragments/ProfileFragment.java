package com.selimkilicaslan.guideme.ui.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.selimkilicaslan.guideme.R;
import com.selimkilicaslan.guideme.classes.MyFragment;
import com.selimkilicaslan.guideme.classes.User;
import com.selimkilicaslan.guideme.ui.activities.DatePickerActivity;
import com.selimkilicaslan.guideme.ui.activities.GeneralInfoActivity;
import com.selimkilicaslan.guideme.ui.activities.LanguagesActivity;
import com.selimkilicaslan.guideme.ui.activities.ServicesActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ProfileFragment extends MyFragment {

    private LinearLayout generalInfoLinearLayout;
    private LinearLayout availableDatesLinearLayout;
    private LinearLayout contactIdLinearLayout;
    private LinearLayout cityLinearLayout;
    private LinearLayout servicesLinearLayout;
    private LinearLayout languagesLinearLayout;

    private View root;
    private ImageView generalInfoImageView;
    private ImageView availableDatesImageView;
    private ImageView cityImageView;
    private ImageView servicesImageView;
    private ImageView languagesImageView;

    private TextView cityTextView;
    private TextView availableDatesTextView;
    private TextView servicesTextView;
    private TextView languagesTextView;

    private LocationManager locationManager;
    private LocationListener locationListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);


        root = inflater.inflate(R.layout.fragment_profile, container, false);

        generalInfoLinearLayout = root.findViewById(R.id.generalInfoLinearLayout);
        availableDatesLinearLayout = root.findViewById(R.id.availableDatesLinearLayout);
        contactIdLinearLayout = root.findViewById(R.id.contactIdLinearLayout);
        cityLinearLayout = root.findViewById(R.id.cityLinearLayout);
        servicesLinearLayout = root.findViewById(R.id.servicesLinearLayout);
        languagesLinearLayout = root.findViewById(R.id.languagesLinearLayout);

        availableDatesImageView = root.findViewById(R.id.availableDatesImageView);
        cityImageView = root.findViewById(R.id.cityImageView);
        servicesImageView = root.findViewById(R.id.servicesImageView);
        languagesImageView = root.findViewById(R.id.languagesImageView);

        availableDatesTextView = root.findViewById(R.id.availableDatesTextView);
        cityTextView = root.findViewById(R.id.cityTextView);
        servicesTextView = root.findViewById(R.id.servicesTextView);
        languagesTextView = root.findViewById(R.id.languagesTextView);


        generalInfoLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generalInfoOnclick(v);
            }
        });

        availableDatesLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                availableDatesOnClick(v);
            }
        });

        contactIdLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactIdOnClick(v);
            }
        });

        cityLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationOnClick(v);
            }
        });

        servicesLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                servicesOnClick(view);
            }
        });

        languagesLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                languagesOnClick(view);
            }
        });

        try {
            final DocumentReference userRef = mDatabase.collection("users").document(mUser.getUid());
            userRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot snapshot,
                                    @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e);
                        return;
                    }

                    if (snapshot != null && snapshot.exists()) {
                        Log.d(TAG, "Current data: " + snapshot.getData());
                        User user = snapshot.toObject(User.class);
                        if(user.getAvailableDates() != null){
                            availableDatesImageView.setImageResource(R.drawable.ic_check_circle_green_24dp);
                            availableDatesTextView.setText("");

                        } else {
                            availableDatesImageView.setImageResource(R.drawable.ic_error_orange_24dp);
                            availableDatesTextView.setText("Missing");
                        }
                        if(user.getCity() != null) {
                            cityImageView.setImageResource(R.drawable.ic_check_circle_green_24dp);
                            cityTextView.setText(user.getCity());
                        } else {
                            cityImageView.setImageResource(R.drawable.ic_error_orange_24dp);
                            cityTextView.setText("");
                        }
                        if(user.getServicesOffered() != null) {
                            servicesImageView.setImageResource(R.drawable.ic_check_circle_green_24dp);
                            servicesTextView.setText("");
                        } else {
                            servicesImageView.setImageResource(R.drawable.ic_error_orange_24dp);
                            servicesTextView.setText("Missing");
                        }
                        if(user.getLanguages() != null) {
                            languagesImageView.setImageResource(R.drawable.ic_check_circle_green_24dp);
                            languagesTextView.setText("");
                        } else {
                            languagesImageView.setImageResource(R.drawable.ic_error_orange_24dp);
                            languagesTextView.setText("Missing");
                        }
                    } else {
                        Log.d(TAG, "Current data: null");
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }



        return root;
    }

    private void servicesOnClick(View view) {
        Intent intent = new Intent(getActivity().getApplicationContext(), ServicesActivity.class);
        startActivity(intent);
    }

    private void generalInfoOnclick(View view) {
        Intent intent = new Intent(root.getContext(), GeneralInfoActivity.class);
        startActivity(intent);
    }

    private void availableDatesOnClick(View view) {
        Intent intent = new Intent(root.getContext(), DatePickerActivity.class);
        startActivity(intent);
    }

    private void contactIdOnClick(View view) {
    }

    private void languagesOnClick(View view) {
        Intent intent = new Intent(getActivity().getApplicationContext(), LanguagesActivity.class);
        startActivity(intent);
    }

    private void locationOnClick(View view) {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                updateLocation(location);
                locationManager.removeUpdates(locationListener);
                locationManager = null;

            }
            @Override
            public void onProviderEnabled(String s) {}
            @Override
            public void onProviderDisabled(String s) {}
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

        };


        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else {

            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (location != null) {
                updateLocation(location);
            }
        }

    }

    private void updateLocation(Location location) {

        Geocoder geocoder = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());

        try {

            List<Address> locationList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if(locationList.size() > 0) {
                DocumentReference reference = mDatabase.collection("users").document(mUser.getUid());
                Address add = locationList.get(0);
                if (add.getAdminArea() != null && add.getCountryName() != null) {
                    reference.update("city", add.getAdminArea());
                    reference.update("country", add.getCountryName());
                }
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Can't retrieve location", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }







    public void requestLocation()
    {
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, locationListener);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                requestLocation();

            }
        }
    }

}
