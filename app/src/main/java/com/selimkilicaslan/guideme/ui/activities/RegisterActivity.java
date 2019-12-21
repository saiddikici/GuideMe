package com.selimkilicaslan.guideme.ui.activities;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.selimkilicaslan.guideme.R;
import com.selimkilicaslan.guideme.classes.ImageHandler;
import com.selimkilicaslan.guideme.classes.MyAppCompatActivity;
import com.selimkilicaslan.guideme.classes.User;
import com.selimkilicaslan.guideme.types.Gender;
import com.selimkilicaslan.guideme.types.UserType;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class RegisterActivity extends MyAppCompatActivity {

    final int RESULT_LOAD_IMAGE = 1;

    EditText nameEditText, emailEditText, passwordEditText, phoneEditText;
    Button registerButton;
    RadioGroup radioGroup;
    RadioButton touristRadioButton, guideRadioButton;
    de.hdodenhof.circleimageview.CircleImageView profileImageView;

    Spinner genderSpinner;
    String Filename;

    User newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.currentPasswordEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        registerButton = findViewById(R.id.saveProfileChangesButton);
        genderSpinner = findViewById(R.id.genderSpinner);
        radioGroup = findViewById(R.id.radioGroup);
        touristRadioButton = findViewById(R.id.touristRadioButton);
        guideRadioButton = findViewById(R.id.guideRadioButton);
        profileImageView = findViewById(R.id.generalInfoProfileImageView);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Genders, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);


    }

    //TODO profile picture url oluşmamasını kontrol et
    //TODO otentikeyşından dönen hataları sınıflandır
    public void registerButtonOnClick(View view) {

        registerButton.setClickable(false);

        if (!nameEditText.getText().toString().equals("") &&
            !emailEditText.getText().toString().equals("") &&
            !phoneEditText.getText().toString().equals("")){

            final String name, email, password, phone;
            final UserType userType;
            final Gender gender;
            name = nameEditText.getText().toString();
            email = emailEditText.getText().toString();
            password = passwordEditText.getText().toString();
            phone = phoneEditText.getText().toString();
            if (genderSpinner.getSelectedItemPosition() == 0)
                gender = Gender.MALE;
            else gender = Gender.FEMALE;

            if (radioGroup.getCheckedRadioButtonId() == touristRadioButton.getId()) userType = UserType.TOURIST;
            else userType = UserType.GUIDE;

            newUser = new User();
            newUser.setEmail(email);
            newUser.setGender(gender);
            newUser.setPhoneNumber(phone);
            newUser.setUsername(name);
            newUser.setUserType(userType);

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                final FirebaseUser user = mAuth.getCurrentUser();

                                StorageReference storageRef = mStorage.getReference();
                                StorageReference imagesRef = storageRef.child("images");
                                StorageReference userImagesRef = imagesRef.child(user.getUid());
                                newUser.setUserID(user.getUid());

                                addUserToDatabase();

                                String imageUUID = UUID.randomUUID().toString();
                                String imageName = imageUUID + ".jpg";
                                final StorageReference newImageRef = userImagesRef.child(imageName);

                                Bitmap bitmap = ((BitmapDrawable) profileImageView.getDrawable()).getBitmap();
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                byte[] data = baos.toByteArray();

                                UploadTask uploadTask = newImageRef.putBytes(data);
                                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                    @Override
                                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                        if (!task.isSuccessful()) {
                                            throw task.getException();
                                        }
                                        return newImageRef.getDownloadUrl();
                                    }
                                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        if (task.isSuccessful()) {
                                            Uri downloadUri = task.getResult();
                                            newUser.setProfilePictureURL(downloadUri.toString());
                                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                    .setDisplayName(name)
                                                    .setPhotoUri(downloadUri)
                                                    .build();
                                            user.updateProfile(profileUpdates)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(getApplicationContext(), "User successfully created!", Toast.LENGTH_SHORT).show();
                                                                finish();
                                                            }
                                                        }
                                                    });

                                        } else {
                                            Toast.makeText(getApplicationContext(), "Error! Cannot upload photo", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });

                            } else {
                                registerButton.setClickable(true);
                                Toast.makeText(RegisterActivity.this, "Cannot create user!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
        else {
            Toast.makeText(getApplicationContext(), "Please fill all of the fields!", Toast.LENGTH_SHORT).show();
            registerButton.setClickable(true);
        }

    }

    public void addUserToDatabase(){
        mDatabase.collection("users")
                .document(newUser.getUserID())
                .set(newUser)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Creating user...", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
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
                Filename = picturePath.substring(picturePath.lastIndexOf("/") + 1);
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
