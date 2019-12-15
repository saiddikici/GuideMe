package com.selimkilicaslan.guideme.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.firestore.DocumentReference;
import com.selimkilicaslan.guideme.R;
import com.selimkilicaslan.guideme.classes.MyAppCompatActivity;
import com.squareup.timessquare.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DatePickerActivity extends MyAppCompatActivity {

    CalendarPickerView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);
        calendarView = findViewById(R.id.calendarView);

        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
        Date today = new Date();

        calendarView.init(today, nextYear.getTime())
                .inMode(CalendarPickerView.SelectionMode.RANGE);
    }

    public void confirmDatesButtonOnClick(View view) {
        List<Date> dates = calendarView.getSelectedDates();
        DocumentReference reference = mDatabase.collection("users").document(mUser.getUid());
        reference.update("availableDates", dates);
        finish();
    }
}
