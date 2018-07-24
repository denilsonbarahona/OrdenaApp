package com.curso.onbringit.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.curso.onbringit.R;
import com.curso.onbringit.View.EditScheduleActivity;

import java.util.ArrayList;

/**
 * Created by PC-PRAF on 3/9/2017.
 */

public class OrderSchedule extends RecyclerView.Adapter<OrderSchedule.ScheduleHolder>
{
    private Context context;
    private ArrayList<String[]> ScheduleOrders;

    public OrderSchedule(Context cntx)
    {
        this.context = cntx;
        this.ScheduleOrders = new ArrayList<>();
    }

    public void AddScheduleOrders(ArrayList<String[]> Schedule){
        this.ScheduleOrders = Schedule;
        this.notifyDataSetChanged();
    }

    @Override
    public OrderSchedule.ScheduleHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ng_schedule_item, parent , false);
        ScheduleHolder holder = new ScheduleHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(OrderSchedule.ScheduleHolder holder, final int position)
    {
        holder.OrderNumber.setText("Order Número # "+this.ScheduleOrders.get(position)[0]);
       // holder.OrderAddress.setText(this.ScheduleOrders.get(position)[1]);
        holder.DeliveryAddress.setText(this.ScheduleOrders.get(position)[1]);
        holder.NextDelivery.setText(this.ScheduleOrders.get(position)[2]);
        holder.ItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditScheduleActivity.class);
                intent.putExtra("OrdenNumber",ScheduleOrders.get(position)[0]);
                intent.putExtra("OrdenId",ScheduleOrders.get(position)[3]);
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.ScheduleOrders.size();
    }

    public class ScheduleHolder extends RecyclerView.ViewHolder {


        public TextView  OrderNumber;
        public TextView  DeliveryAddress;
        public TextView  NextDelivery;
        public LinearLayout ItemLayout;

        public ScheduleHolder(View itemView)
        {
            super(itemView);
            this.OrderNumber = (TextView) itemView.findViewById(R.id.ng_order_number);
            this.DeliveryAddress = (TextView) itemView.findViewById(R.id.ng_delivery_address);
            this.NextDelivery = (TextView) itemView.findViewById(R.id.ng_nextDelivery);
            this.ItemLayout = (LinearLayout) itemView.findViewById(R.id.ng_tracing_item);
        }
    }
}
