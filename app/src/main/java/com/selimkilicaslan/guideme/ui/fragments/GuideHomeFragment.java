package com.selimkilicaslan.guideme.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.selimkilicaslan.guideme.R;
import com.selimkilicaslan.guideme.classes.MyFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class GuideHomeFragment extends MyFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_guide_home, container, false);



        return root;
    }
}
