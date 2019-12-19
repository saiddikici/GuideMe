package com.selimkilicaslan.guideme.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.selimkilicaslan.guideme.R;
import com.selimkilicaslan.guideme.classes.Chat;
import com.selimkilicaslan.guideme.classes.Message;
import com.selimkilicaslan.guideme.classes.User;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private Chat chat;
    private LayoutInflater inflater;
    private String userID;
    private Bitmap senderImage;
    private Bitmap receiverImage;

    public ChatAdapter(final Context context, Chat chat, String userID, Bitmap senderImage, Bitmap receiverImage) {
        inflater = LayoutInflater.from(context);
        this.chat = chat;
        this.userID = userID;
        this.senderImage = senderImage;
        this.receiverImage = receiverImage;
    }


    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_message, parent, false);
        ChatAdapter.MyViewHolder holder = new ChatAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ChatAdapter.MyViewHolder holder, int position) {
        Message selectedMessage = chat.getMessages().get(position);
        holder.setData(selectedMessage, position);

    }

    @Override
    public int getItemCount() {
        return chat.getMessages().size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView chatContentTextView;
        de.hdodenhof.circleimageview.CircleImageView senderProfilePicture, receiverProfilePicture;
        EditText messageInputEditText;


        public MyViewHolder(View itemView) {
            super(itemView);
            chatContentTextView = itemView.findViewById(R.id.chatContentTextView);
            receiverProfilePicture = itemView.findViewById(R.id.receiverProfilePicture);
            senderProfilePicture = itemView.findViewById(R.id.senderProfilePicture);
            messageInputEditText = itemView.findViewById(R.id.messageInputEditText);

        }

        public void setData(Message message, int position) {

            this.chatContentTextView.setText(message.getMessageContent());
            if (message.getSender().equals(userID)){

                senderProfilePicture.setImageBitmap(senderImage);
                receiverProfilePicture.setVisibility(View.GONE);
            }
            else {
                receiverProfilePicture.setImageBitmap(receiverImage);
                senderProfilePicture.setVisibility(View.GONE);
            }

        }


        @Override
        public void onClick(View v) {


        }


    }

}
