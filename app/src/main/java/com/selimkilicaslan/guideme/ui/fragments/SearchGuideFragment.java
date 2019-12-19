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

        mDatabase.collection("users")
                .whereEqualTo("userType", UserType.GUIDE)
                .whereEqualTo("validGuide", true)
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

        guideSearchAdapter = new GuideSearchAdapter(root.getContext(), guides);
        guidesRecyclerView.setAdapter(guideSearchAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        guidesRecyclerView.setLayoutManager(linearLayoutManager);

        return root;
    }
}
