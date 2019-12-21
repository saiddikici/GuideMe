package com.selimkilicaslan.guideme.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.selimkilicaslan.guideme.R;
import com.selimkilicaslan.guideme.classes.MyFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class GuideHomeFragment extends MyFragment {

    View upcomingView;

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
        Fragment newFragment = new MatchesFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack if needed
        transaction.replace(R.id.nav_host_fragment, newFragment);
        transaction.addToBackStack(null);

// Commit the transaction
        transaction.commit();
    }
}
