package com.curso.onbringit.Adapters;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.curso.onbringit.R;

import java.util.ArrayList;

/**
 * Created by PC-PRAF on 3/12/2017.
 */

public class ThirdPartyApplications extends RecyclerView.Adapter<ThirdPartyApplications.Holder> {

    private ArrayList<String[]> Applications;

    public ThirdPartyApplications(){
        this.Applications = new ArrayList<>();

        Applications.add(new String[]{ "BottomBar" ,  "https://github.com/roughike/BottomBar" });
        Applications.add(new String[]{ "Glide" ,  "https://github.com/bumptech/glide" });
        Applications.add(new String[]{ "Material Spinner" ,  "https://github.com/jaredrummler/MaterialSpinner" });
        Applications.add(new String[]{ "Retrofit" ,  "https://github.com/square/retrofit" });
        Applications.add(new String[]{ "Gson Converter" ,  "https://github.com/square/retrofit/tree/master/retrofit-converters/gson" });
        Applications.add(new String[]{ "AutoFormatEditText" ,  "https://github.com/aldoKelvianto/AutoFormatEditText" });
        Applications.add(new String[]{ "Icons8" ,  "https://icons8.com" });
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ng_application_item,parent,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position)
    {
        holder.application_name.setText(this.Applications.get(position)[0]);
        holder.application_ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW , Uri.parse(Applications.get(position)[1]));
                view.getContext().startActivity(browserIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.Applications.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView application_name;
        LinearLayout application_ly;

        public Holder(View itemView) {
            super(itemView);
            application_name = (TextView)itemView.findViewById(R.id.ng_ApplicationName);
            application_ly = (LinearLayout)itemView.findViewById(R.id.ng_application_item);
        }
    }
}
