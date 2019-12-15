package com.selimkilicaslan.guideme.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.selimkilicaslan.guideme.R;
import com.selimkilicaslan.guideme.classes.User;
import com.selimkilicaslan.guideme.types.UserType;

public class GeneralInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_info);

        User user = new User("said", "said@said", "asdf", "1234", UserType.GUIDE, "https://i.ibb.co/4j109Mv/taksim-dayi.png");
    }

    public void saveButtonOnClick(View view) {
    }

    public void profileImageOnClick(View view) {
    }
}
