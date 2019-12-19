package com.selimkilicaslan.guideme.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.selimkilicaslan.guideme.R;
import com.selimkilicaslan.guideme.classes.ImageHandler;
import com.selimkilicaslan.guideme.classes.MyAppCompatActivity;
import com.selimkilicaslan.guideme.classes.User;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class PhotosActivity extends MyAppCompatActivity {

    final int RESULT_LOAD_IMAGE = 1;

    private View profilePicture;
    private View picture1;
    private View picture2;
    private View picture3;
    private View picture4;
    private View picture5;
    private View picture6;
    private View picture7;
    private View picture8;

    private ArrayList<Bitmap> photoList;
    private ArrayList<String> photoURLs;

    private int currentCount = 0;
    private int initialCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        photoList = new ArrayList<>(8);
        photoURLs = new ArrayList<>(8);

        profilePicture = findViewById(R.id.profilePicture);
        picture1 = findViewById(R.id.picture1);
        picture2 = findViewById(R.id.picture2);
        picture3 = findViewById(R.id.picture3);
        picture4 = findViewById(R.id.picture4);
        picture5 = findViewById(R.id.picture5);
        picture6 = findViewById(R.id.picture6);
        picture7 = findViewById(R.id.picture7);
        picture8 = findViewById(R.id.picture8);

        picture1.setTag(0);
        picture2.setTag(1);
        picture3.setTag(2);
        picture4.setTag(3);
        picture5.setTag(4);
        picture6.setTag(5);
        picture7.setTag(6);
        picture8.setTag(7);


        Glide.with(profilePicture)
                .load(mUser.getPhotoUrl())
                .into((ImageView) profilePicture.findViewById(R.id.photoImageView));

        for (int i = 0; i < 8; i++){
            getViewFromIndex(i).setVisibility(View.GONE);
        }

        DocumentReference userRef = mDatabase.collection("users").document(mUser.getUid());
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                final User user = documentSnapshot.toObject(User.class);

                if (user != null) {
                    if(user.getPictureURLs() != null){

                        currentCount = user.getPictureURLs().size();
                        initialCount = currentCount;
                        for(int i = 0; i < user.getPictureURLs().size(); i++){
                            final int index = i;
                            View picture = getViewFromIndex(i);
                            if(picture != null){
                                final ImageView imageView = picture.findViewById(R.id.photoImageView);
                                Glide.with(getApplicationContext())
                                        .asBitmap()
                                        .load((user.getPictureURLs().get(i)))
                                        .into(new CustomTarget<Bitmap>() {
                                            @Override
                                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                                imageView.setImageBitmap(resource);
                                                photoList.add(index, resource);
                                                photoURLs.add(user.getPictureURLs().get(index));
                                                updateUI();
                                            }

                                            @Override
                                            public void onLoadCleared(@Nullable Drawable placeholder) {

                                            }
                                        });
                            }
                        }
                    }
                    for (int i = 0; i < 8; i++){
                        getViewFromIndex(i).setVisibility(View.VISIBLE);
                    }
                    updateUI();
                }
            }
        });


        setListeners(picture1);
        setListeners(picture2);
        setListeners(picture3);
        setListeners(picture4);
        setListeners(picture5);
        setListeners(picture6);
        setListeners(picture7);
        setListeners(picture8);

        profilePicture.findViewById(R.id.closeButton).setVisibility(View.GONE);

    }

    private void setListeners(final View view) {
        view.findViewById(R.id.photoImageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPictureClick(view);
            }
        });
        view.findViewById(R.id.closeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCloseButtonClick(view);
            }
        });
    }

    public void onPictureClick(View view){

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
                //photoURLs.add(picturePath.substring(picturePath.lastIndexOf("/") + 1));
                photoList.add(bitmap);
                updateUI();
            } catch (IOException e) {
                bitmap = null;
                e.printStackTrace();
            }
            cursor.close();

        }
    }

    public void onCloseButtonClick(View view){

        int pos = (int) view.getTag();
        photoList.remove(pos);
        if(pos < currentCount) {
            photoURLs.remove(pos);
            currentCount--;
        }
        updateUI();

    }

    private void updateUI(){


        for(int i = 0; i < 8; i++){
            View view = getViewFromIndex(i);
            ImageView photo = view.findViewById(R.id.photoImageView);
            de.hdodenhof.circleimageview.CircleImageView button = view.findViewById(R.id.closeButton);

            if(i < photoList.size()){
                photo.setImageBitmap(photoList.get(i));
                button.setVisibility(View.VISIBLE);
                photo.setClickable(false);
            } else {
                photo.setImageResource(R.drawable.ic_add_circle_gray_24dp);
                button.setVisibility(View.GONE);
                photo.setClickable(true);
            }

        }
    }

    public void onUpdateButtonClick(View view) {

        StorageReference storageRef = mStorage.getReference();
        StorageReference imagesRef = storageRef.child("images");
        StorageReference userImagesRef = imagesRef.child(mUser.getUid());
        if(photoList.size() == currentCount && currentCount == initialCount) {
            Toast.makeText(getApplicationContext(), "No changes", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(getApplicationContext(), "Updating...", Toast.LENGTH_SHORT).show();
        if(photoList.size() == 0){

            DocumentReference userRef = mDatabase.collection("users").document(mUser.getUid());
            userRef.update("pictureURLs", null);
            Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
            currentCount = photoList.size();
            initialCount = currentCount;

        } else {

            if(photoList.size() == currentCount && currentCount < initialCount){
                DocumentReference userRef = mDatabase.collection("users").document(mUser.getUid());
                userRef.update("pictureURLs", photoURLs);
                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                currentCount = photoList.size();
                initialCount = currentCount;
            } else {
                for(int i = currentCount; i < photoList.size(); i++){


                    String imageUUID = UUID.randomUUID().toString();
                    String imageName = imageUUID + ".jpg";

                    final int currentPos = i;
                    final StorageReference newImageRef = userImagesRef.child(imageName);

                    Bitmap bitmap = photoList.get(i);
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
                                photoURLs.add(downloadUri.toString());
                                if(currentPos == photoList.size() - 1){
                                    DocumentReference userRef = mDatabase.collection("users").document(mUser.getUid());
                                    userRef.update("pictureURLs", photoURLs);
                                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                                    currentCount = photoList.size();
                                    initialCount = currentCount;
                                }
                            }
                        }
                    });

                }
            }

        }


    }

    private View getViewFromIndex(int index){
        if ((int)picture1.getTag() == index) return picture1;
        if ((int)picture2.getTag() == index) return picture2;
        if ((int)picture3.getTag() == index) return picture3;
        if ((int)picture4.getTag() == index) return picture4;
        if ((int)picture5.getTag() == index) return picture5;
        if ((int)picture6.getTag() == index) return picture6;
        if ((int)picture7.getTag() == index) return picture7;
        if ((int)picture8.getTag() == index) return picture8;
        return null;
    }
}
