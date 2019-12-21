package com.selimkilicaslan.guideme.ui.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.selimkilicaslan.guideme.R;
import com.selimkilicaslan.guideme.adapters.GuideSearchAdapter;
import com.selimkilicaslan.guideme.classes.MyFragment;
import com.selimkilicaslan.guideme.classes.User;
import com.selimkilicaslan.guideme.types.UserType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchGuideFragment extends MyFragment {

    private ArrayList<User> guides;

    private RecyclerView guidesRecyclerView;
    private GuideSearchAdapter guideSearchAdapter;

    private EditText cityEditText, dateEditText;
    private Date selectedDate;
    private Button searchButton;

    public static String dateText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_search_guide, container, false);


        guidesRecyclerView = root.findViewById(R.id.guidesRecyclerView);
        cityEditText = root.findViewById(R.id.cityEditText);
        dateEditText = root.findViewById(R.id.dateEditText);
        searchButton = root.findViewById(R.id.searchButton);

        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSearchButtonClick(view);
            }
        });

        guides = new ArrayList<>();



        guideSearchAdapter = new GuideSearchAdapter(root.getContext(), guides);
        guidesRecyclerView.setAdapter(guideSearchAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        guidesRecyclerView.setLayoutManager(linearLayoutManager);

        return root;
    }

    private void onSearchButtonClick(View view) {
        hideKeyboard(getActivity());
        String city = cityEditText.getText().toString().trim();
        String date = dateEditText.getText().toString().trim();
        if(!city.equals("") && !date.equals("")){
            Toast.makeText(getContext(), "Searching guides...", Toast.LENGTH_SHORT).show();
            mDatabase.collection("users")
                    .whereEqualTo("userType", UserType.GUIDE)
                    .whereEqualTo("validGuide", true)
                    .whereArrayContains("availableDates", selectedDate)
                    .whereEqualTo("city", city)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                guides.clear();
                                if(task.getResult() != null && task.getResult().size() > 0) {
                                    for(QueryDocumentSnapshot document : task.getResult()){
                                        User guide = document.toObject(User.class);
                                        guides.add(guide);
                                    }
                                    guideSearchAdapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(getContext(), "We couldn't find any guides", Toast.LENGTH_LONG).show();
                                }

                            } else {
                                Toast.makeText(getActivity().getApplicationContext(), "Guide search failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(getContext(), "Please select a city and a date", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDateDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        final DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month++;
                        dateText = dayOfMonth + "/" + month + "/" + year;
                        try {
                            selectedDate = new SimpleDateFormat("dd/MM/yyyy").parse(dateText);
                            dateEditText.setText(dateText);
                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        }
                    }
                }, year, month, day);
        datePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, "Choose", datePickerDialog);
        datePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Cancel", datePickerDialog);
        datePickerDialog.show();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
