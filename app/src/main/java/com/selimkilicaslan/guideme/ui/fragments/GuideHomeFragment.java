package com.selimkilicaslan.guideme.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.selimkilicaslan.guideme.R;
import com.selimkilicaslan.guideme.classes.MyFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

public class GuideHomeFragment extends MyFragment {

    private View upcomingView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_guide_home, container, false);

        upcomingView = root.findViewById(R.id.upcomingView);
        upcomingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUpcomingViewClick(v);
            }
        });

        return root;
    }

    private void onUpcomingViewClick(View v) {

        NavDirections action =
                GuideHomeFragmentDirections
                        .actionNavigationGuideHomeToNavigationMatches();
        Navigation.findNavController(v).navigate(action);
    }
}
