package com.selimkilicaslan.guideme.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.selimkilicaslan.guideme.R;
import com.selimkilicaslan.guideme.classes.Guide;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class GuideSearchAdapter extends RecyclerView.Adapter<GuideSearchAdapter.MyViewHolder> {

    ArrayList<Guide> guideArrayList;
    LayoutInflater inflater;

    public GuideSearchAdapter(Context context, ArrayList<Guide> guideArrayList) {
        inflater = LayoutInflater.from(context);
        this.guideArrayList = guideArrayList;
    }


    @Override
    public GuideSearchAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.guides_list_item, parent, false);
        GuideSearchAdapter.MyViewHolder holder = new GuideSearchAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(GuideSearchAdapter.MyViewHolder holder, int position) {
        Guide selectedGuide = guideArrayList.get(position);
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

        public void setData(Guide guide, int position) {

            this.guideNameTextView.setText(guide.getName());
            this.guidePlaceTextView.setText(guide.getCity() + ", " + guide.getCountry());
            this.guidePriceTextView.setText(guide.getPricePerHour() + "₺");
            this.quoteTextView.setText(guide.getQuote());
            this.reviewCountTextView.setText(String.valueOf(guide.getReviewCount()));
            this.reviewRatingBar.setRating(guide.getRating());
            Glide.with(this.guideImageView)
                    .load(guide.getProfilePictureURL())
                    .into(this.guideImageView);

        }


        @Override
        public void onClick(View v) {


        }
    }
}
