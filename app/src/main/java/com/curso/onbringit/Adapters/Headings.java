package com.curso.onbringit.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.curso.onbringit.Model.Providers;
import com.curso.onbringit.R;
import com.curso.onbringit.View.Activity_order_filters;

import java.util.ArrayList;

/**
 * Created by PC-PRAF on 17/4/2018.
 */

public class Headings extends RecyclerView.Adapter<Headings.HeadingsHolder> {

    private Context context;
    private ArrayList<String[]> Headings= new ArrayList<>();
    private String items_seleccted="0";

    public Headings(Context cntx){
        this.context = cntx;
    }

    public void PushHeadings(ArrayList<String[]> heading){
        this.Headings = heading;
    }

    @Override
    public HeadingsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ng_heading_item, parent , false);
        HeadingsHolder headingsHolder = new HeadingsHolder(view);
        return headingsHolder;
    }

    @Override
    public void onBindViewHolder(final HeadingsHolder holder, final int position) {

        if(items_seleccted == Headings.get(position)[0]){
            holder.ng_heading.setTextColor(Color.WHITE);
            GradientDrawable drawable = (GradientDrawable) holder.ng_heading.getBackground();
            drawable.setColor(this.context.getResources().getColor(R.color.colorPrimary));
        }else{
            holder.ng_heading.setTextColor(this.context.getResources().getColor(R.color.colorPrimary));
            GradientDrawable drawable = (GradientDrawable) holder.ng_heading.getBackground();
            drawable.setColor(this.context.getResources().getColor(R.color.select_list));
        }

        holder.ng_heading.setText(Headings.get(position)[1]);
        holder.ng_heading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items_seleccted = Headings.get(position)[0];
                notifyDataSetChanged();
                Activity_order_filters filter = new Activity_order_filters();
                filter.SearchStore(Integer.valueOf(Headings.get(position)[0]) , view.getContext());
            }
        });

    }

    @Override
    public int getItemCount() {
        return Headings.size();
    }

    public class HeadingsHolder extends RecyclerView.ViewHolder {
        TextView ng_heading;

        public HeadingsHolder(View itemView) {
            super(itemView);
            ng_heading = (TextView) itemView.findViewById(R.id.ng_heading);

        }
    }


}
