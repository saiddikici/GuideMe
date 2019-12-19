package com.selimkilicaslan.guideme.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import androidx.recyclerview.widget.RecyclerView;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.MyViewHolder> {
    private ArrayList<Chat> chatArrayList;
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
        View view = inflater.inflate(R.layout.list_item_chat, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Chat selectedChat = chatArrayList.get(position);
        holder.setData(selectedChat, position);

    }

    @Override
    public int getItemCount() {
        return chatArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView cNameTextView, chatTextView;
        de.hdodenhof.circleimageview.CircleImageView chatProfilePicture;

        public MyViewHolder(View itemView) {
            super(itemView);

            cNameTextView = itemView.findViewById(R.id.cNameTextView);
            chatTextView = itemView.findViewById(R.id.chatTextView);
            chatProfilePicture = itemView.findViewById(R.id.chatProfilePicture);

        }

        public void setData(final Chat chat, int position) {

            DocumentReference reference;

            if(userID.equals(chat.getFirstUser())) {
                reference = chat.getSecondUserReference();
            } else if(userID.equals(chat.getSecondUser())) {
                reference = chat.getFirstUserReference();
            } else {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                return;
            }

            reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful() && task.getResult() != null) {
                        User user = task.getResult().toObject(User.class);
                        if(user != null) {
                            cNameTextView.setText(user.getUsername());
                            chatTextView.setText(chat.getMessages().get(chat.getMessages().size() - 1).getMessageContent());
                            Glide.with(context)
                                    .asBitmap()
                                    .load(user.getProfilePictureURL())
                                    .into(new CustomTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                            chatProfilePicture.setImageBitmap(resource);
                                        }

                                        @Override
                                        public void onLoadCleared(@Nullable Drawable placeholder) {

                                        }
                                    });
                        }
                    } else {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            /*Glide.with(chatProfilePicture)
                    .load(chat.getFirstUser().getProfilePictureURL())
                    .into(chatProfilePicture);*/




        }


        @Override
        public void onClick(View v) {

            int position = this.getAdapterPosition();

            Intent intent = new Intent(context, ChatActivity.class);
            intent.putExtra("chatID", chatArrayList.get(position).getChatID());
            context.startActivity(intent);

        }


    }

}
