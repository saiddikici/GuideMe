package com.selimkilicaslan.guideme.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.selimkilicaslan.guideme.R;
import com.selimkilicaslan.guideme.classes.ImageHandler;
import com.selimkilicaslan.guideme.classes.MyAppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import androidx.annotation.NonNull;
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
    private ArrayList<String> photoPaths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        photoList = new ArrayList<>(8);
        photoPaths = new ArrayList<>(8);

        profilePicture = findViewById(R.id.profilePicture);
        picture1 = findViewById(R.id.picture1);
        picture2 = findViewById(R.id.picture2);
        picture3 = findViewById(R.id.picture3);
        picture4 = findViewById(R.id.picture4);
        picture5 = findViewById(R.id.picture5);
        picture6 = findViewById(R.id.picture6);
        picture7 = findViewById(R.id.picture7);
        picture8 = findViewById(R.id.picture8);

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
                photoPaths.add(picturePath.substring(picturePath.lastIndexOf("/") + 1));
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
        ImageView picture = view.findViewById(R.id.photoImageView);
    }

    private void updateUI(){

        ImageView photo1 = picture1.findViewById(R.id.photoImageView);
        ImageView photo2 = picture2.findViewById(R.id.photoImageView);
        ImageView photo3 = picture3.findViewById(R.id.photoImageView);
        ImageView photo4 = picture4.findViewById(R.id.photoImageView);
        ImageView photo5 = picture5.findViewById(R.id.photoImageView);
        ImageView photo6 = picture6.findViewById(R.id.photoImageView);
        ImageView photo7 = picture7.findViewById(R.id.photoImageView);
        ImageView photo8 = picture8.findViewById(R.id.photoImageView);

        if(photoList.get(0) != null){
            photo1.setImageBitmap(photoList.get(0));
        } else photo1.setImageBitmap(null);
        if(photoList.get(1) != null){
            photo2.setImageBitmap(photoList.get(1));
        } else photo2.setImageBitmap(null);
        if(photoList.get(2) != null){
            photo3.setImageBitmap(photoList.get(2));
        } else photo3.setImageBitmap(null);
        if(photoList.get(3) != null){
            photo4.setImageBitmap(photoList.get(3));
        } else photo4.setImageBitmap(null);
        if(photoList.get(4) != null){
            photo5.setImageBitmap(photoList.get(4));
        } else photo5.setImageBitmap(null);
        if(photoList.get(5) != null){
            photo6.setImageBitmap(photoList.get(5));
        } else photo6.setImageBitmap(null);
        if(photoList.get(6) != null){
            photo7.setImageBitmap(photoList.get(6));
        } else photo7.setImageBitmap(null);
        if(photoList.get(7) != null){
            photo8.setImageBitmap(photoList.get(7));
        } else photo8.setImageBitmap(null);
    }

}
