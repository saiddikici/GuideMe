package com.selimkilicaslan.guideme;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.MyViewHolder> {
    ArrayList<Chat> chatArrayList;
    LayoutInflater inflater;
    Context context;

    public InboxAdapter(Context context, ArrayList<Chat> chatArrayList) {
        inflater = LayoutInflater.from(context);
        this.chatArrayList = chatArrayList;
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.chat_list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
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

            this.cNameTextView.setText(chat.getReceiver().getUsername());
            this.chatTextView.setText(chat.getMessages().get(chat.getMessages().size() - 1).getMessageContent());
            /*Glide.with(chatProfilePicture)
                    .load(chat.getReceiver().getProfilePictureURL())
                    .into(chatProfilePicture);*/

            Glide.with(context)
                    .asBitmap()
                    .load(chat.getReceiver().getProfilePictureURL())
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            chatProfilePicture.setImageBitmap(resource);
                            chat.getReceiver().setBmpProfilePicture(resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });


        }


        @Override
        public void onClick(View v) {


        }


    }

}
