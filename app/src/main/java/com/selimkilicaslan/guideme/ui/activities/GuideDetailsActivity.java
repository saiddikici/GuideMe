package com.selimkilicaslan.guideme.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.selimkilicaslan.guideme.R;
import com.selimkilicaslan.guideme.adapters.SliderAdapter;
import com.smarteist.autoimageslider.SliderView;

public class GuideDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_details);

        SliderView imageSlider = findViewById(R.id.imageSlider);
        imageSlider.setSliderAdapter(new SliderAdapter(getApplicationContext()));
    }
}
