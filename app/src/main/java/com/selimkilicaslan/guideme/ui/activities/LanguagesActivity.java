package com.selimkilicaslan.guideme.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.selimkilicaslan.guideme.R;
import com.selimkilicaslan.guideme.adapters.LanguagesAdapter;
import com.selimkilicaslan.guideme.classes.LanguageOffered;
import com.selimkilicaslan.guideme.classes.MyAppCompatActivity;
import com.selimkilicaslan.guideme.classes.User;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LanguagesActivity extends MyAppCompatActivity {

    ArrayList<LanguageOffered> languageOffered;
    LanguagesAdapter languagesAdapter;

    RecyclerView languagesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_languages);



        languagesRecyclerView = findViewById(R.id.languagesRecyclerView);

        languageOffered = new ArrayList<>();
        languageOffered.add(new LanguageOffered("Turkish", false));
        languageOffered.add(new LanguageOffered("English", false));
        languageOffered.add(new LanguageOffered("German", false));
        languageOffered.add(new LanguageOffered("French", false));
        languageOffered.add(new LanguageOffered("Spanish", false));
        languageOffered.add(new LanguageOffered("Portuguese", false));
        languageOffered.add(new LanguageOffered("Arabic", false));
        languageOffered.add(new LanguageOffered("Chinese", false));
        languageOffered.add(new LanguageOffered("Japanese", false));

        DocumentReference docRef = mDatabase.collection("users").document(mUser.getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                try {
                    User user = documentSnapshot.toObject(User.class);
                    for(int i = 0; i < languageOffered.size(); i++){
                        for (LanguageOffered language : user.getLanguages()) {
                            if(languageOffered.get(i).getLanguage().equals(language.getLanguage())){
                                languageOffered.get(i).setOffered(language.getOffered());
                            }
                        }
                    }
                    languagesAdapter.notifyDataSetChanged();
                } catch (Exception ex) {

                }
            }
        });

        languagesAdapter = new LanguagesAdapter(getApplicationContext(), languageOffered);
        languagesRecyclerView.setAdapter(languagesAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        languagesRecyclerView.setLayoutManager(linearLayoutManager);

    }

    public void updateButtonClick(View view) {
        languageOffered = languagesAdapter.getItems();
        ArrayList<LanguageOffered> nonEmptyLangs = new ArrayList<>();
        for(LanguageOffered lang : languageOffered){
            if(lang.getOffered()){
                nonEmptyLangs.add(lang);
            }
        }
        if(nonEmptyLangs.size() == 0) nonEmptyLangs = null;

        DocumentReference reference = mDatabase.collection("users").document(mUser.getUid());
        reference.update("languages", nonEmptyLangs);
        Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
        finish();
    }
}
