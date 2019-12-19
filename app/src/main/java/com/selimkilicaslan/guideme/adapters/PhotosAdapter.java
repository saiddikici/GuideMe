package com.selimkilicaslan.guideme.adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.selimkilicaslan.guideme.R;
import com.selimkilicaslan.guideme.classes.Chat;
import com.selimkilicaslan.guideme.classes.User;
import com.selimkilicaslan.guideme.ui.activities.ChatActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.MyViewHolder> {

    final int RESULT_LOAD_IMAGE = 1;

    private ArrayList<Bitmap> bitmapArrayList;
    private LayoutInflater inflater;
    private Context context;
    private String userID;

    public PhotosAdapter(Context context, String userID) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.userID = userID;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_photos, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Bitmap selectedBitmap = bitmapArrayList.get(position);
        holder.setData(selectedBitmap, position);

    }

    @Override
    public int getItemCount() {
        return bitmapArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView photoImageView;
        de.hdodenhof.circleimageview.CircleImageView closeButton;

        public MyViewHolder(View itemView) {
            super(itemView);

            photoImageView = itemView.findViewById(R.id.photoImageView);
            closeButton = itemView.findViewById(R.id.closeButton);

        }

        public void setData(final Bitmap photo, final int position) {
            if(position == 0) {
                closeButton.setVisibility(View.GONE);
            } else {
                closeButton.setVisibility(View.VISIBLE);
            }
            photoImageView.setImageBitmap(photo);
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    photoImageView.setImageBitmap(null);
                    closeButton.setVisibility(View.GONE);
                    bitmapArrayList.remove(position);
                    notifyDataSetChanged();
                }
            });
            photoImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                        Intent i = new Intent(
                                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                        ((Activity) context).startActivityForResult(i, RESULT_LOAD_IMAGE);
                    }
                }
            });

        }




        @Override
        public void onClick(View v) {

            int position = this.getAdapterPosition();

            //Intent intent = new Intent(context, ChatActivity.class);
            //intent.putExtra("chatID", bitmapArrayList.get(position).getChatID());
            //context.startActivity(intent);

        }


    }

}
