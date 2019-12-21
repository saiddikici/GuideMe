package com.selimkilicaslan.guideme.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.R.drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.selimkilicaslan.guideme.R;
import com.selimkilicaslan.guideme.classes.Chat;
import com.selimkilicaslan.guideme.classes.Match;
import com.selimkilicaslan.guideme.classes.User;
import com.selimkilicaslan.guideme.types.MatchStatus;
import com.selimkilicaslan.guideme.ui.activities.ChatActivity;

import java.util.ArrayList;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.MyViewHolder> {
    private ArrayList<Match> matchArrayList;
    private LayoutInflater inflater;
    private Context context;
    private String userID;

    public MatchesAdapter(Context context, ArrayList<Match> matchesArrayList, String userID) {
        inflater = LayoutInflater.from(context);
        this.matchArrayList = matchesArrayList;
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
        Match selectedMatch = matchArrayList.get(position);
        holder.setData(selectedMatch, position);

    }

    @Override
    public int getItemCount() {
        return matchArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mNameTextView, dateTextView;
        de.hdodenhof.circleimageview.CircleImageView matchProfilePicture;
        ImageView statusImageView;

        public MyViewHolder(View itemView) {
            super(itemView);

            mNameTextView = itemView.findViewById(R.id.mNameTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            matchProfilePicture = itemView.findViewById(R.id.matchProfilePicture);
            statusImageView = itemView.findViewById(R.id.statusImageView);

        }

        public void setData(final Match match, int position) {

            DocumentReference reference;

            if(userID.equals(match.getGuideReference())) {
                reference = match.getTouristReference();
            } else if(userID.equals(match.getTouristReference())) {
                reference = match.getGuideReference();
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
                            //cNameTextView.setText(user.getUsername());
                            //chatTextView.setText(chat.getMessages().get(chat.getMessages().size() - 1).getMessageContent());
                            if (match.getStatus().equals(MatchStatus.PLANNED)){
                                mNameTextView.setText("Matched - " + user.getUsername());
                            }
                            else if(match.getStatus().equals(MatchStatus.WAITING)){
                                mNameTextView.setText(user.getUsername() + " sent you a request");
                                matchProfilePicture.setImageResource(R.drawable.ic_error_orange_24dp);
                            }
                            dateTextView.setText(match.getDate().toString());
                            Glide.with(context)
                                    .asBitmap()
                                    .load(user.getProfilePictureURL())
                                    .into(new CustomTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                            matchProfilePicture.setImageBitmap(resource);
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




        }


        @Override
        public void onClick(View v) {

            int position = this.getAdapterPosition();

            /*Intent intent = new Intent(context, ChatActivity.class);
            intent.putExtra("chatID", chatArrayList.get(position).getChatID());
            context.startActivity(intent);*/

        }


    }

}
