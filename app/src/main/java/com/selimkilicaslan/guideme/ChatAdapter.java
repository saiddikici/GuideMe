package com.selimkilicaslan.guideme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    Chat chat;
    LayoutInflater inflater;

    public ChatAdapter(Context context, ArrayList<Chat> chatArrayList) {
        inflater = LayoutInflater.from(context);
        this.chatArrayList = chatArrayList;
    }


    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.chat_list_item, parent, false);
        ChatAdapter.MyViewHolder holder = new ChatAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ChatAdapter.MyViewHolder holder, int position) {
        Chat selectedChat = chatArrayList.get(position);
        holder.setData(selectedChat, position);

    }

    @Override
    public int getItemCount() {
        return chatArrayList.size();
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

        public void setData(Chat chat, int position) {

            this.chatContentTextView.setText(chat.getMessages().get(position));
            Glide.with(receiverProfilePicture)
                    .load(chat.getProfilePictureURL())
                    .into(receiverProfilePicture);


        }


        @Override
        public void onClick(View v) {


        }


    }

}
