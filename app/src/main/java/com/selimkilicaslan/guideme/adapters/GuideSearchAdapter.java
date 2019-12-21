package com.selimkilicaslan.guideme.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.selimkilicaslan.guideme.R;
import com.selimkilicaslan.guideme.classes.User;
import com.selimkilicaslan.guideme.ui.activities.GuideDetailsActivity;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

import static com.selimkilicaslan.guideme.ui.fragments.SearchGuideFragment.dateText;

public class GuideSearchAdapter extends RecyclerView.Adapter<GuideSearchAdapter.MyViewHolder> {

    ArrayList<User> guideArrayList;
    LayoutInflater inflater;
    Context context;

    public GuideSearchAdapter(Context context, ArrayList<User> guideArrayList) {
        inflater = LayoutInflater.from(context);
        this.guideArrayList = guideArrayList;
        this.context = context;
    }


    @Override
    public GuideSearchAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_guides, parent, false);
        GuideSearchAdapter.MyViewHolder holder = new GuideSearchAdapter.MyViewHolder(view);
        view.setOnClickListener(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(GuideSearchAdapter.MyViewHolder holder, int position) {
        User selectedGuide = guideArrayList.get(position);
        holder.setData(selectedGuide, position);

    }

    @Override
    public int getItemCount() {
        return guideArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView guideNameTextView, guidePlaceTextView, guidePriceTextView, quoteTextView, reviewCountTextView;
        RatingBar reviewRatingBar;
        ImageView guideImageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            guideNameTextView = itemView.findViewById(R.id.guideNameTextView);
            guidePlaceTextView = itemView.findViewById(R.id.guidePlaceTextView);
            guidePriceTextView = itemView.findViewById(R.id.guidePriceTextView);
            quoteTextView = itemView.findViewById(R.id.quoteTextView);
            reviewCountTextView = itemView.findViewById(R.id.reviewCountTextView);
            reviewRatingBar = itemView.findViewById(R.id.reviewRatingBar);
            guideImageView = itemView.findViewById(R.id.guideImageView);

        }

        public void setData(User guide, int position) {

            this.guideNameTextView.setText(guide.getUsername());
            this.guidePlaceTextView.setText(guide.getCity() + ", " + guide.getCountry());
            this.guidePriceTextView.setText(guide.getPricePerDay() + "₺");
            this.quoteTextView.setText(guide.getQuote());
            this.reviewCountTextView.setText(String.valueOf(guide.getReviewCount()));
            this.reviewRatingBar.setRating(guide.getRating());
            Glide.with(this.guideImageView)
                    .load(guide.getProfilePictureURL())
                    .into(this.guideImageView);

        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            String selectedGuideID = guideArrayList.get(position).getUserID();
            Intent intent = new Intent(context, GuideDetailsActivity.class);
            intent.putExtra("guideID", selectedGuideID);
            intent.putExtra("selectedDate", dateText);
            context.startActivity(intent);
        }
    }
}
