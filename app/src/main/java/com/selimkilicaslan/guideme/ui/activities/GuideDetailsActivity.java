package com.selimkilicaslan.guideme.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.selimkilicaslan.guideme.R;
import com.selimkilicaslan.guideme.adapters.SliderAdapter;
import com.selimkilicaslan.guideme.classes.Chat;
import com.selimkilicaslan.guideme.classes.LanguageOffered;
import com.selimkilicaslan.guideme.classes.Message;
import com.selimkilicaslan.guideme.classes.MyAppCompatActivity;
import com.selimkilicaslan.guideme.classes.ServiceOffered;
import com.selimkilicaslan.guideme.classes.User;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.UUID;

import androidx.annotation.NonNull;

public class GuideDetailsActivity extends MyAppCompatActivity {

    private User guide;
    private String guideID;

    private TextView quoteTextView;
    private TextView aboutTextView;
    private TextView languageTextView;
    private TextView servicesTextView;
    private TextView placesTextView;

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
        placesTextView = findViewById(R.id.placesTextView);

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

                placesTextView.setText(guide.getPlacesCovered());
            }
        });


    }

    public void onContactButtonClick(View view) {

        mDatabase.collection("conversations")
                .whereEqualTo("firstUser", mUser.getUid())
                .whereEqualTo("secondUser", guideID)
                .limit(1)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful() && task.getResult() != null && task.getResult().size() > 0){
                            for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                Chat chat = documentSnapshot.toObject(Chat.class);
                                String chatID = chat.getChatID();
                                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                                intent.putExtra("chatID", chatID);
                                startActivity(intent);
                            }
                        } else {
                            mDatabase.collection("conversations")
                                    .whereEqualTo("firstUser", guideID)
                                    .whereEqualTo("secondUser", mUser.getUid())
                                    .limit(1)
                                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful() && task.getResult() != null && task.getResult().size() > 0){
                                        for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                            Chat chat = documentSnapshot.toObject(Chat.class);
                                            String chatID = chat.getChatID();
                                            Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                                            intent.putExtra("chatID", chatID);
                                            startActivity(intent);
                                        }
                                    } else {

                                        String chatID = UUID.randomUUID().toString();
                                        DocumentReference firstRef = mDatabase.collection("users").document(mUser.getUid());
                                        DocumentReference secondRef = mDatabase.collection("users").document(guideID);
                                        Chat newChat = new Chat(mUser.getUid(), guideID, chatID, firstRef, secondRef, new ArrayList<Message>());

                                        DocumentReference chatRef = mDatabase.collection("conversations").document(chatID);
                                        chatRef.set(newChat);

                                        firstRef.update("conversationIDs", FieldValue.arrayUnion(chatRef));
                                        secondRef.update("conversationIDs", FieldValue.arrayUnion(chatRef));

                                        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                                        intent.putExtra("chatID", chatID);
                                        startActivity(intent);

                                    }
                                }
                            });
                        }
                    }
                });


    }
}
