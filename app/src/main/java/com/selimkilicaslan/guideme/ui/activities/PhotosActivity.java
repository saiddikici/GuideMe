package com.selimkilicaslan.guideme.ui.activities;

import android.os.Bundle;

import com.selimkilicaslan.guideme.R;
import com.selimkilicaslan.guideme.classes.MyAppCompatActivity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PhotosActivity extends MyAppCompatActivity {

    private RecyclerView photosRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        photosRecyclerView = findViewById(R.id.photosRecyclerView);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        photosRecyclerView.setLayoutManager(layoutManager);
        photosRecyclerView.setAdapter(null);

    }
}
