package com.selimkilicaslan.guideme.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.selimkilicaslan.guideme.R;
import com.selimkilicaslan.guideme.classes.ServiceOffered;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class ServicesOfferedAdapter extends RecyclerView.Adapter<ServicesOfferedAdapter.MyViewHolder> {
    ArrayList<ServiceOffered> serviceArrayList;
    LayoutInflater inflater;
    Context context;

    public ServicesOfferedAdapter(Context context, ArrayList<ServiceOffered> serviceArrayList) {
        inflater = LayoutInflater.from(context);
        this.serviceArrayList = serviceArrayList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_offer, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ServiceOffered selectedService = serviceArrayList.get(position);
        holder.setData(selectedService, position);
    }

    @Override
    public int getItemCount() { return serviceArrayList.size(); }

    public ArrayList<ServiceOffered> getItems() { return serviceArrayList; }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Switch isOfferedSwitch;

        public MyViewHolder(View itemView) {
            super(itemView);

            isOfferedSwitch = itemView.findViewById(R.id.isOfferedSwitch);
            isOfferedSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    int position = getAdapterPosition();
                    serviceArrayList.get(position).setOffered(b);
                }
            });

        }

        public void setData(final ServiceOffered serviceOffered, int position) {

            this.isOfferedSwitch.setText(serviceOffered.getServiceName());
            this.isOfferedSwitch.setChecked(serviceOffered.getOffered());

        }


        @Override
        public void onClick(View v) {


        }


    }

}
