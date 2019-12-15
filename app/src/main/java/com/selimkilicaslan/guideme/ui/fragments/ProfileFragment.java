package com.selimkilicaslan.guideme.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.selimkilicaslan.guideme.R;
import com.selimkilicaslan.guideme.ui.activities.GeneralInfoActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ProfileFragment extends Fragment {

    View root;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout generalInfoLinearLayout;
        LinearLayout localHostInfoLinearLayout;
        LinearLayout contactIdLinearLayout;

        root = inflater.inflate(R.layout.fragment_profile, container, false);

        generalInfoLinearLayout = root.findViewById(R.id.generalInfoLinearLayout);
        localHostInfoLinearLayout = root.findViewById(R.id.localHostInfoLinearLayout);
        contactIdLinearLayout = root.findViewById(R.id.contactIdLinearLayout);

        generalInfoLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generalInfoOnclick(v);
            }
        });

        localHostInfoLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localHostInfoOnClick(v);
            }
        });

        contactIdLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactIdOnClick(v);
            }
        });

        return root;
    }
    public void generalInfoOnclick(View view) {
        Intent intent = new Intent(root.getContext(), GeneralInfoActivity.class);
        startActivity(intent);
    }

    public void localHostInfoOnClick(View view) {
    }

    public void contactIdOnClick(View view) {
    }
}
