package com.selimkilicaslan.guideme.ui.activities;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.selimkilicaslan.guideme.R;
import com.selimkilicaslan.guideme.classes.ImageHandler;
import com.selimkilicaslan.guideme.classes.MyAppCompatActivity;
import com.selimkilicaslan.guideme.classes.User;

import java.io.File;
import java.io.IOException;

import io.opencensus.tags.Tag;

public class GeneralInfoActivity extends MyAppCompatActivity {
    final int RESULT_LOAD_IMAGE = 1;

    ImageView generalInfoProfileImageView;
    TextView emailTextView;
    EditText nameEditText;
    EditText phoneEditText;
    EditText currentPasswordEditText;
    EditText newPasswordEditText;
    EditText confirmNewPasswordEditText;
    Button saveProfileChangesButton;

    String filePath;

    User user;

    UserProfileChangeRequest profileUpdates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_info);


        generalInfoProfileImageView = findViewById(R.id.generalInfoProfileImageView);
        emailTextView = findViewById(R.id.emailTextView);
        nameEditText = findViewById(R.id.nameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        currentPasswordEditText = findViewById(R.id.currentPasswordEditText);
        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        confirmNewPasswordEditText = findViewById(R.id.confirmNewPasswordEditText);
        saveProfileChangesButton = findViewById(R.id.saveProfileChangesButton);


        DocumentReference docRef = mDatabase.collection("users").document(mUser.getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(User.class);

                /*nameEditText.setText(user.getUsername());
                emailTextView.setText(user.getEmail());
                phoneEditText.setText(user.getPhoneNumber());*/
            }
        });

        Glide.with(generalInfoProfileImageView)
                .load(mUser.getPhotoUrl())
                .into(generalInfoProfileImageView);
        nameEditText.setText(mUser.getDisplayName());
        emailTextView.setText(mUser.getEmail());
        phoneEditText.setText(mUser.getPhoneNumber());
    }

    //TODO şifreleri kontrol et
    //TODO email güncellemeyi şeyet
    //TODO Profil resmi güncellemeyi kontrol et
    public void saveButtonOnClick(View view) {
        profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(nameEditText.getText().toString())
                .build();
        mUser.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.d("GuideMe", "User Profile Updated");
                            Toast.makeText(GeneralInfoActivity.this, "User Profile Updated", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Log.d("GuideMe", "Error");
                            Toast.makeText(GeneralInfoActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        AuthCredential credential = EmailAuthProvider.getCredential(emailTextView.getText().toString(), currentPasswordEditText.getText().toString());
        mUser.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            mUser.updatePassword(newPasswordEditText.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Log.d("GuideMe", "Password Updated");
                                        Toast.makeText(GeneralInfoActivity.this, "Password Updated", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Log.d("GuideMe", "Error");
                                        Toast.makeText(GeneralInfoActivity.this, "Error, password not updated.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Log.d("GuideMe", "Auth failed");
                            Toast.makeText(GeneralInfoActivity.this, "Auth failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void profileImageOnClick(View view) {
        String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE};

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(galleryPermissions, 2);

        } else {
            Intent i = new Intent(
                    Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(i, RESULT_LOAD_IMAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            Bitmap bitmap;
            try {
                bitmap = ImageHandler.handleSamplingAndRotationBitmap(getApplicationContext(), Uri.fromFile(new File(picturePath)));
                filePath = picturePath.substring(picturePath.lastIndexOf("/") + 1);
            } catch (IOException e) {
                bitmap = null;
                e.printStackTrace();
            }
            ImageView imageButton = findViewById(R.id.generalInfoProfileImageView);
            imageButton.setImageBitmap(bitmap);
            cursor.close();

        }
    }
}
