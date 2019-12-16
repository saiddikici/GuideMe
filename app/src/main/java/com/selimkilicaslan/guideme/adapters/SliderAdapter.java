package com.selimkilicaslan.guideme.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.selimkilicaslan.guideme.R;
import com.selimkilicaslan.guideme.classes.User;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.bumptech.glide.Glide;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterVH> {

    private Context context;

    private User user;

    public SliderAdapter(Context context, User user) {
        this.context = context;
        this.user = user;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_layout_image, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {

        if(position == 0){
            viewHolder.sliderNameTextView.setText(user.getUsername());
            String place = user.getCity() + ", " + user.getCountry();
            viewHolder.sliderPlaceTextView.setText(place);
            String price = "₺" + user.getPricePerDay() + "/Day";
            viewHolder.guidePriceTextView.setText(price);
            viewHolder.guidePriceTextView.setVisibility(View.VISIBLE);
            Glide.with(viewHolder.itemView)
                    .load(user.getProfilePictureURL())
                    .into(viewHolder.imageViewBackground);
        } else {
            Glide.with(viewHolder.itemView)
                    .load(user.getPictureURLs().get(position - 1))
                    .into(viewHolder.imageViewBackground);
        }

    }

    @Override
    public int getCount() {
        if(user.getPictureURLs() != null) {
            return  user.getPictureURLs().size() + 1;
        }
        return 1;
    }

    public class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        TextView sliderNameTextView;
        TextView sliderPlaceTextView;
        TextView guidePriceTextView;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.sliderImageView);
            sliderNameTextView = itemView.findViewById(R.id.sliderNameTextView);
            sliderPlaceTextView = itemView.findViewById(R.id.sliderPlaceTextView);
            guidePriceTextView = itemView.findViewById(R.id.guidePriceTextView);
            this.itemView = itemView;
        }
    }
}
