package com.selimkilicaslan.guideme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    Chat chat;
    LayoutInflater inflater;

    public ChatAdapter(Context context, Chat chat) {
        inflater = LayoutInflater.from(context);
        this.chat = chat;
    }


    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.chat_list_item, parent, false);
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

        public MyViewHolder(View itemView) {
            super(itemView);
            chatContentTextView = itemView.findViewById(R.id.cNameTextView);
            receiverProfilePicture = itemView.findViewById(R.id.receiverProfilePicture);
            senderProfilePicture = itemView.findViewById(R.id.senderProfilePicture);

        }

        public void setData(Message message, int position) {

            this.chatContentTextView.setText(message.getMessageContent());
            if (message.getSent()){
                Glide.with(senderProfilePicture)
                        .load(message.getUser().getProfilePictureURL())
                        .into(senderProfilePicture);
            }
            else {
                Glide.with(receiverProfilePicture)
                        .load(message.getUser().getProfilePictureURL())
                        .into(receiverProfilePicture);
            }

        }


        @Override
        public void onClick(View v) {


        }


    }

}
