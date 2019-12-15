package com.selimkilicaslan.guideme.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.selimkilicaslan.guideme.R;
import com.selimkilicaslan.guideme.adapters.GuideSearchAdapter;
import com.selimkilicaslan.guideme.adapters.InboxAdapter;
import com.selimkilicaslan.guideme.classes.Chat;
import com.selimkilicaslan.guideme.classes.Guide;
import com.selimkilicaslan.guideme.classes.Message;
import com.selimkilicaslan.guideme.classes.User;
import com.selimkilicaslan.guideme.types.UserType;

import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchGuideFragment extends Fragment {

    RecyclerView guidesRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_search_guide, container, false);

        //if(mUser != null){
        //    Toast.makeText(root.getContext(), mUser.getDisplayName(), Toast.LENGTH_SHORT).show();
        //}

        guidesRecyclerView = root.findViewById(R.id.guidesRecyclerView);

        ArrayList<Guide> guides = new ArrayList<>();

        Guide kubra = new Guide();
        kubra.setName("Kübra");
        kubra.setGender("Female");
        kubra.setCity("Istanbul");
        kubra.setCountry("Turkey");
        kubra.setQuote("This is an inspirational quote...");
        kubra.setPricePerHour(1);
        kubra.setReviewCount(1);
        kubra.setRating(1);
        kubra.setProfilePictureURL("https://img.freepik.com/free-photo/beautiful-girl-stands-near-walll-with-leaves_8353-5378.jpg?size=626&ext=jpg");
        guides.add(kubra);

        Guide enes = new Guide();
        enes.setName("Selim Enes");
        enes.setGender("Male");
        enes.setCity("Istanbul");
        enes.setCountry("Turkey");
        enes.setQuote("This is an inspirational quote...");
        enes.setPricePerHour(22);
        enes.setReviewCount(56);
        enes.setRating(5);
        enes.setProfilePictureURL("https://pbs.twimg.com/profile_images/586131436392046592/YdkXfQah_400x400.jpg");
        guides.add(enes);

        Guide said = new Guide();
        said.setName("Said Dikici");
        said.setGender("Male");
        said.setCity("Istanbul");
        said.setCountry("Turkey");
        said.setQuote("This is an inspirational quote...");
        said.setPricePerHour(1);
        said.setReviewCount(1);
        said.setRating(1);
        said.setProfilePictureURL("https://i.ibb.co/4j109Mv/taksim-dayi.png");
        guides.add(said);

        GuideSearchAdapter guideSearchAdapter = new GuideSearchAdapter(root.getContext(), guides);
        guidesRecyclerView.setAdapter(guideSearchAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        guidesRecyclerView.setLayoutManager(linearLayoutManager);

        return root;
    }
}
