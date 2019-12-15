package com.selimkilicaslan.guideme.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.selimkilicaslan.guideme.R;
import com.selimkilicaslan.guideme.classes.LanguageOffered;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class LanguagesAdapter extends RecyclerView.Adapter<LanguagesAdapter.MyViewHolder> {
    ArrayList<LanguageOffered> languageArrayList;
    LayoutInflater inflater;
    Context context;

    public LanguagesAdapter(Context context, ArrayList<LanguageOffered> languageArrayList) {
        inflater = LayoutInflater.from(context);
        this.languageArrayList = languageArrayList;
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
        LanguageOffered selectedLanguage = languageArrayList.get(position);
        holder.setData(selectedLanguage, position);
    }

    @Override
    public int getItemCount() { return languageArrayList.size(); }

    public ArrayList<LanguageOffered> getItems() { return languageArrayList; }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Switch isOfferedSwitch;

        public MyViewHolder(View itemView) {
            super(itemView);

            isOfferedSwitch = itemView.findViewById(R.id.isOfferedSwitch);
            isOfferedSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    int position = getAdapterPosition();
                    languageArrayList.get(position).setOffered(b);
                }
            });

        }

        public void setData(final LanguageOffered languageOffered, int position) {

            this.isOfferedSwitch.setText(languageOffered.getLanguage());
            this.isOfferedSwitch.setChecked(languageOffered.getOffered());

        }


        @Override
        public void onClick(View v) {


        }


    }

}
