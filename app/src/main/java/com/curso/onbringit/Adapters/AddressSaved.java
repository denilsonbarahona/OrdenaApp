package com.curso.onbringit.Adapters;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.curso.onbringit.DataBase.Sql_Query;
import com.curso.onbringit.R;
import com.curso.onbringit.View.ATMMovil;
import com.curso.onbringit.View.AddScheduleActivity;
import com.curso.onbringit.View.EditScheduleActivity;
import com.curso.onbringit.View.InfoDeliveryActivity;

import java.util.ArrayList;

/**
 * Created by PC-PRAF on 30/7/2017.
 */

public class AddressSaved extends RecyclerView.Adapter<AddressSaved.AddressHolder> {

    public ArrayList<String[]> Address = new ArrayList<>();
    Integer Activity;
    Sql_Query query;

    public AddressSaved (Integer Activity , Sql_Query query /*SQLiteDatabase sqLiteDatabase*/)
    {
        this.query = query; //new Sql_Query(sqLiteDatabase , null);
        this.Activity = Activity;
        this.Address =  query.GetAddressSaved();
    }

    public void RefreshItems(){
        this.Address =  query.GetAddressSaved();
        notifyDataSetChanged();
    }

    @Override
    public AddressHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ng_address_item , parent, false);
        AddressHolder holder = new AddressHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AddressHolder holder, final int position)
    {
        holder.AddressName.setText(Address.get(position)[0]);
        holder.Address.setText(Address.get(position)[1]);
        holder.AddressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Activity==1){
                    Intent intent = new Intent(view.getContext(), InfoDeliveryActivity.class);
                    intent.putExtra("AddressName",Address.get(position)[0]);
                    intent.putExtra("Address",Address.get(position)[1]);
                    intent.putExtra("AddressId",Address.get(position)[3]);
                    view.getContext().startActivity(intent);
                }else if(Activity==2){
                    Intent intent = new Intent(view.getContext(), ATMMovil.class);
                    intent.putExtra("AddressName",Address.get(position)[0]);
                    intent.putExtra("Address",Address.get(position)[1]);
                    intent.putExtra("AddressId",Address.get(position)[3]);
                    view.getContext().startActivity(intent);
                }else if(Activity==3){
                    Intent intent = new Intent(view.getContext(), AddScheduleActivity.class);
                    intent.putExtra("AddressName",Address.get(position)[0]);
                    intent.putExtra("Address",Address.get(position)[1]);
                    intent.putExtra("AddressId",Address.get(position)[3]);
                    view.getContext().startActivity(intent);
                }else{
                    Intent intent = new Intent(view.getContext(), EditScheduleActivity.class);
                    intent.putExtra("AddressName",Address.get(position)[0]);
                    intent.putExtra("Address",Address.get(position)[1]);
                    intent.putExtra("AddressId",Address.get(position)[3]);
                    view.getContext().startActivity(intent);
                }
            }
        });
        holder.AddressLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(view.getContext(),"Options",Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return Address.size();
    }

    public class AddressHolder extends RecyclerView.ViewHolder
    {
        TextView AddressName;
        TextView Address;
        LinearLayout AddressLayout;

        public AddressHolder(View itemView)
        {
            super(itemView);
            this.AddressName = (TextView)itemView.findViewById(R.id.ng_AddressName);
            this.Address = (TextView)itemView.findViewById(R.id.ng_Address);
            this.AddressLayout = (LinearLayout)itemView.findViewById(R.id.ng_LyAddress);
        }
    }
}
