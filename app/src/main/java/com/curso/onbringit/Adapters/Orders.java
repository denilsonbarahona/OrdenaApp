package com.curso.onbringit.Adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.curso.onbringit.Fragment.OrdersActivity;
import com.curso.onbringit.Model.ApiRequest;
import com.curso.onbringit.R;
import com.curso.onbringit.View.TrackingActivity;

import java.util.ArrayList;

/**
 * Created by PC-PRAF on 30/8/2017.
 */

public class Orders extends RecyclerView.Adapter<Orders.OrdersHolder> {

    ArrayList<String[]> Orders = new ArrayList<>();


    public void AddOrders(ArrayList<String[]> Orders){
        this.Orders = Orders;
        this.notifyDataSetChanged();
    }

    @Override
    public Orders.OrdersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ng_track_item, parent , false);
        OrdersHolder holder = new OrdersHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Orders.OrdersHolder holder, final int position) {

        holder.ng_OrderNumber.setText(this.Orders.get(position)[0]);
        holder.ng_OrderDate.setText(this.Orders.get(position)[1]);
        holder.ng_OrderAddress.setText(this.Orders.get(position)[2]);
        holder.ng_OrderState.setText(this.Orders.get(position)[3]);
        holder.ng_tracking_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext() , TrackingActivity.class);
                intent.putExtra("order_id",Orders.get(position)[4]);
                intent.putExtra("state_id",Orders.get(position)[5]);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.Orders.size();
    }

    public class OrdersHolder extends RecyclerView.ViewHolder {

        TextView ng_OrderNumber;
        TextView ng_OrderDate;
        TextView ng_OrderAddress;
        TextView ng_OrderState;
        LinearLayout ng_tracking_item;

        public OrdersHolder(View itemView)
        {
            super(itemView);
            this.ng_OrderNumber = (TextView) itemView.findViewById(R.id.ng_order_number);
            this.ng_OrderDate = (TextView) itemView.findViewById(R.id.ng_order_date);
            this.ng_OrderAddress = (TextView) itemView.findViewById(R.id.ng_order_address);
            this.ng_OrderState = (TextView) itemView.findViewById(R.id.ng_order_state);
            this.ng_tracking_item = (LinearLayout) itemView.findViewById(R.id.ng_tracing_item);
        }
    }
}
