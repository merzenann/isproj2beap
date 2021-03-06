package com.example.merzensumagaysay.thesis2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.merzensumagaysay.thesis2.model.Crud;
import com.example.merzensumagaysay.thesis2.R;


import java.util.List;


public class AlertAdapter extends RecyclerView.Adapter<AlertAdapter.ViewHolder> {


    public static List<Crud> alertitems;
    private Context context;

    public AlertAdapter(List<Crud> alertitems, Context context) {
        this.alertitems = alertitems;
        this.context = context;

    }

    @Override
    public AlertAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new AlertAdapter.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final AlertAdapter.ViewHolder holder, final int position) {
        final Crud listItem = alertitems.get(position);

        holder.txtCalamityName.setText(listItem.getCalamityName());
        holder.txtDescription.setText(listItem.getDescription());
        holder.imageView.setImageResource(listItem.image);
    }


    @Override
    public int getItemCount() {
        return alertitems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtCalamityName;
        public TextView txtDescription;
        public ImageView imageView;


        public ViewHolder(View itemView) {
            super(itemView);

            txtCalamityName = itemView.findViewById(R.id.txtCalamityName);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            imageView = itemView.findViewById(R.id.imgCalamity);

        }
    }
}
