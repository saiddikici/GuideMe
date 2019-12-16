package com.selimkilicaslan.guideme.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.selimkilicaslan.guideme.R;
import com.selimkilicaslan.guideme.adapters.GuideSearchAdapter;
import com.selimkilicaslan.guideme.classes.MyFragment;
import com.selimkilicaslan.guideme.classes.User;
import com.selimkilicaslan.guideme.types.Gender;
import com.selimkilicaslan.guideme.types.UserType;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchGuideFragment extends MyFragment {

    ArrayList<User> guides;

    RecyclerView guidesRecyclerView;
    GuideSearchAdapter guideSearchAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_search_guide, container, false);

        //if(mUser != null){
        //    Toast.makeText(root.getContext(), mUser.getDisplayName(), Toast.LENGTH_SHORT).show();
        //}

        guidesRecyclerView = root.findViewById(R.id.guidesRecyclerView);

        guides = new ArrayList<>();

        mDatabase.collection("users").whereEqualTo("userType", UserType.GUIDE)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){

                            for(QueryDocumentSnapshot document : task.getResult()){
                                User guide = document.toObject(User.class);
                                guides.add(guide);
                            }
                            guideSearchAdapter.notifyDataSetChanged();

                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "Guide search failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        /*User kubra = new User();
        kubra.setUsername("Kübra");
        kubra.setGender(Gender.FEMALE);
        kubra.setCity("Istanbul");
        kubra.setCountry("Turkey");
        kubra.setQuote("This is an inspirational quote...");
        kubra.setPricePerDay(1);
        kubra.setReviewCount(1);
        kubra.setRating(1);
        kubra.setProfilePictureURL("https://img.freepik.com/free-photo/beautiful-girl-stands-near-walll-with-leaves_8353-5378.jpg?size=626&ext=jpg");
        guides.add(kubra);

        User enes = new User();
        enes.setUsername("Selim Enes");
        enes.setGender(Gender.MALE);
        enes.setCity("Istanbul");
        enes.setCountry("Turkey");
        enes.setQuote("This is an inspirational quote...");
        enes.setPricePerDay(22);
        enes.setReviewCount(56);
        enes.setRating(5);
        enes.setProfilePictureURL("https://pbs.twimg.com/profile_images/586131436392046592/YdkXfQah_400x400.jpg");
        guides.add(enes);

        User said = new User();
        said.setUsername("Said Dikici");
        said.setGender(Gender.MALE);
        said.setCity("Istanbul");
        said.setCountry("Turkey");
        said.setQuote("This is an inspirational quote...");
        said.setPricePerDay(1);
        said.setReviewCount(1);
        said.setRating(1);
        said.setProfilePictureURL("https://i.ibb.co/4j109Mv/taksim-dayi.png");
        guides.add(said);

         */

        guideSearchAdapter = new GuideSearchAdapter(root.getContext(), guides);
        guidesRecyclerView.setAdapter(guideSearchAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        guidesRecyclerView.setLayoutManager(linearLayoutManager);

        return root;
    }
}
