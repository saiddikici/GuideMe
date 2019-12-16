package com.selimkilicaslan.guideme.ui.activities;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.selimkilicaslan.guideme.R;
import com.selimkilicaslan.guideme.adapters.SliderAdapter;
import com.selimkilicaslan.guideme.classes.LanguageOffered;
import com.selimkilicaslan.guideme.classes.MyAppCompatActivity;
import com.selimkilicaslan.guideme.classes.ServiceOffered;
import com.selimkilicaslan.guideme.classes.User;
import com.smarteist.autoimageslider.SliderView;

public class GuideDetailsActivity extends MyAppCompatActivity {

    private User guide;
    private String guideID;

    private TextView quoteTextView;
    private TextView aboutTextView;
    private TextView languageTextView;
    private TextView servicesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_details);

        guideID = getIntent().getStringExtra("guideID");
        if (guideID == null || guideID.equals("")) finish();

        quoteTextView = findViewById(R.id.quoteTextView);
        aboutTextView = findViewById(R.id.aboutTextView);
        languageTextView = findViewById(R.id.languageTextView);
        servicesTextView = findViewById(R.id.servicesTextView);

        DocumentReference reference = mDatabase.collection("users").document(guideID);
        reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                guide = documentSnapshot.toObject(User.class);
                SliderView imageSlider = findViewById(R.id.imageSlider);
                imageSlider.setSliderAdapter(new SliderAdapter(getApplicationContext(), guide));

                quoteTextView.setText(guide.getQuote());
                aboutTextView.setText(guide.getAbout());
                String languages = "";
                for (LanguageOffered lang : guide.getLanguages()){
                    languages += lang.getLanguage() + "\n";
                }
                languageTextView.setText(languages);
                String services = "";
                for (ServiceOffered serviceOffered : guide.getServicesOffered()){
                    if(serviceOffered.getOffered()) {
                        services += serviceOffered.getServiceName() + "\n";
                    }
                }
                if(services.equals("")) services = "No extra services";
                servicesTextView.setText(services);
            }
        });


    }
}
