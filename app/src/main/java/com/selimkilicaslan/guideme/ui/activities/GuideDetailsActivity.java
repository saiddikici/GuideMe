package com.selimkilicaslan.guideme.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.selimkilicaslan.guideme.R;
import com.selimkilicaslan.guideme.adapters.SliderAdapter;
import com.selimkilicaslan.guideme.classes.Chat;
import com.selimkilicaslan.guideme.classes.LanguageOffered;
import com.selimkilicaslan.guideme.classes.Match;
import com.selimkilicaslan.guideme.classes.Message;
import com.selimkilicaslan.guideme.classes.MyAppCompatActivity;
import com.selimkilicaslan.guideme.classes.ServiceOffered;
import com.selimkilicaslan.guideme.classes.User;
import com.selimkilicaslan.guideme.types.MatchStatus;
import com.smarteist.autoimageslider.SliderView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

public class GuideDetailsActivity extends MyAppCompatActivity {

    private User guide;
    private String guideID;

    private TextView quoteTextView;
    private TextView aboutTextView;
    private TextView languageTextView;
    private TextView servicesTextView;
    private TextView placesTextView;

    private String selectedDate;
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_details);

        guideID = getIntent().getStringExtra("guideID");
        if (guideID == null || guideID.equals("")) finish();

        selectedDate = getIntent().getStringExtra("selectedDate");
        if (selectedDate == null || selectedDate.equals("")) finish();

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

    public void onMatchButtonClick(View view) {

        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(selectedDate);
        } catch (ParseException ex) {
            ex.printStackTrace();
            Toast.makeText(getApplicationContext(), "Date format error", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(GuideDetailsActivity.this);
        builder.setTitle("Guide reservation");
        builder.setMessage(String.format("Do you want to make a reservation for ₺%d on %s", guide.getPricePerDay(), selectedDate));
        builder.setNegativeButton("No", null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                makeReservation();
            }
        });
        builder.show();
    }

    private void makeReservation() {

        String matchID = UUID.randomUUID().toString();
        final DocumentReference guideRef = mDatabase.collection("users").document(guideID);
        final DocumentReference touristRef = mDatabase.collection("users").document(mUser.getUid());
        Match newMatch = new Match();
        newMatch.setMatchID(matchID);
        newMatch.setGuide(guideID);
        newMatch.setGuideReference(guideRef);
        newMatch.setTourist(mUser.getUid());
        newMatch.setTouristReference(touristRef);
        newMatch.setPrice(guide.getPricePerDay());
        newMatch.setDate(date);
        newMatch.setStatus(MatchStatus.PLANNED);
        final DocumentReference matchRef = mDatabase.collection("matches").document(matchID);
        matchRef.set(newMatch);
        guideRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                guide = documentSnapshot.toObject(User.class);
                if (guide != null) {
                    List<DocumentReference> docRefs = new ArrayList<>();
                    if(guide.getMatchReferences() != null) {
                        docRefs.addAll(guide.getMatchReferences());
                    }
                    docRefs.add(matchRef);
                    List<Date> dates = new ArrayList<>();
                    if(guide.getPlannedDates() != null){
                        dates.addAll(guide.getPlannedDates());
                    }
                    dates.add(date);
                    guide.setMatchReferences(docRefs);
                    guide.setPlannedDates(dates);
                    guideRef.set(guide);
                }
            }
        });
        touristRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                if (user != null) {
                    List<DocumentReference> docRefs = new ArrayList<>();
                    if(user.getMatchReferences() != null) {
                        docRefs.addAll(user.getMatchReferences());
                    }
                    docRefs.add(matchRef);
                    List<Date> dates = new ArrayList<>();
                    if(user.getPlannedDates() != null) {
                        dates.addAll(user.getPlannedDates());
                    }
                    dates.add(date);
                    user.setMatchReferences(docRefs);
                    user.setPlannedDates(dates);
                    touristRef.set(user);
                }
            }
        });
        Toast.makeText(getApplicationContext(), "Reservation completed", Toast.LENGTH_SHORT).show();
    }
}
