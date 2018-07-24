package com.curso.onbringit.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.curso.onbringit.Model.ApiRequest;
import com.curso.onbringit.R;
import com.curso.onbringit.View.ActivityItemInOrder;
import com.curso.onbringit.View.Activity_Cancel_Item;
import com.curso.onbringit.View.TrackingActivity;

import java.util.ArrayList;

/**
 * Created by PC-PRAF on 30/8/2017.
 */

public class Orders extends RecyclerView.Adapter<Orders.OrdersHolder> {

    ArrayList<String[]> Orders = new ArrayList<>();
    private int Accion;
    private Activity activity;


    public Orders(int accion , Activity activity){
        this.Accion = accion;
        this.activity = activity;
    }

    public void AddOrders(ArrayList<String[]> Orders){
        this.Orders = Orders;
        this.notifyDataSetChanged();
    }

    public void RefreshData(int position){
        this.Orders.remove(position);
        this.notifyDataSetChanged();
    }

    @Override
    public Orders.OrdersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ng_order_item, parent , false);
        OrdersHolder holder = new OrdersHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Orders.OrdersHolder holder, final int position) {

        holder.ng_OrderNumber.setText(this.Orders.get(position)[7]);

        if(this.Orders.get(position)[6]=="1"){
            holder.ng_OrderDate.setText("AYER");
        }else if(this.Orders.get(position)[6]=="0"){
            holder.ng_OrderDate.setText("HOY");
        }else{
            holder.ng_OrderDate.setText(this.Orders.get(position)[1]);
        }


        holder.ng_OrderAddress.setText(this.Orders.get(position)[2]);
        holder.ng_OrderState.setText(this.Orders.get(position)[3]);
        holder.ng_show_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Accion == 2){
                    Intent intent = new Intent(view.getContext() , ActivityItemInOrder.class);
                    intent.putExtra("order_id" , Orders.get(position)[0]);
                    view.getContext().startActivity(intent);
                }else if(Accion == 3){
                    Intent intent = new Intent(view.getContext() , Activity_Cancel_Item.class);
                    intent.putExtra("order_id" , Orders.get(position)[0]);
                    view.getContext().startActivity(intent);
                }
            }
        });
        holder.ng_tracking_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Accion == 1){
                    Intent intent = new Intent(view.getContext() , TrackingActivity.class);
                    intent.putExtra("order_id",Orders.get(position)[4]);
                    intent.putExtra("state_id",Orders.get(position)[5]);
                    view.getContext().startActivity(intent);
                }else if(Accion == 2){
                    Display display = activity.getWindowManager().getDefaultDisplay();
                    int mwidth = display.getWidth();
                    int mheight = display.getHeight();

                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    View v = LayoutInflater.from(view.getContext()).inflate(R.layout.ng_popup_cancel_order ,null);

                    builder.setView(v);
                    final AlertDialog dialog = builder.create();
                    dialog.show();
                    WindowManager.LayoutParams LP = new WindowManager.LayoutParams();
                    LP.copyFrom(dialog.getWindow().getAttributes());
                    LP.width  = (int)(( mwidth / 2 ) * 1.8);
                    LP.height = (int)(( mheight/2 ) * 0.9 );

                    TextView ng_CancelPop = (TextView) v.findViewById(R.id.ng_cancelpop);
                    TextView ng_Selected = (TextView) v.findViewById(R.id.ng_schedule_selected);
                    final TextView ng_ErrorMessage = (TextView) v.findViewById(R.id.ng_error_message);
                    ng_ErrorMessage.setVisibility(View.GONE);
                    ng_CancelPop.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });

                    ng_Selected.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ApiRequest api = new ApiRequest(view.getContext());
                            api.cancel_order(Orders.get(position)[0] , position , ng_ErrorMessage ,dialog , com.curso.onbringit.Adapters.Orders.this);
                        }
                    });

                    dialog.getWindow().setAttributes(LP);
                }
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
        LinearLayout ng_show_items;

        public OrdersHolder(View itemView)
        {
            super(itemView);
            this.ng_OrderNumber = (TextView) itemView.findViewById(R.id.ng_order_number);
            this.ng_OrderDate = (TextView) itemView.findViewById(R.id.ng_order_date);
            this.ng_OrderAddress = (TextView) itemView.findViewById(R.id.ng_order_address);
            this.ng_OrderState = (TextView) itemView.findViewById(R.id.ng_order_state);
            this.ng_tracking_item = (LinearLayout) itemView.findViewById(R.id.ng_tracing_item);
            this.ng_show_items = (LinearLayout) itemView.findViewById(R.id.ng_ShowItems);
        }
    }
}
