package com.curso.onbringit.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.curso.onbringit.Adapters.Orders;
import com.curso.onbringit.Model.ApiRequest;
import com.curso.onbringit.R;

/**
 * Created by PC-PRAF on 11/11/2017.
 */

public class ActivityCancelOrder extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_order);
        ToolbarConfiguration();

        ApiRequest apiRequest = new ApiRequest(this.getApplicationContext());
        RecyclerView rv_OrderCanceled = (RecyclerView) findViewById(R.id.rv_CancelOrderList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getApplicationContext());

        if(getIntent().getIntExtra("actividad",0)==1){

            Orders orders = new Orders(2 , this);
            rv_OrderCanceled.setAdapter(orders);
            rv_OrderCanceled.setLayoutManager(linearLayoutManager);
            apiRequest.get_Orders(orders , 2);

        } else if(getIntent().getIntExtra("actividad",0)==2){

            Orders orders = new Orders(3 , this);
            rv_OrderCanceled.setAdapter(orders);
            rv_OrderCanceled.setLayoutManager(linearLayoutManager);
            apiRequest.get_Orders(orders, 3);
        }
    }

    public void ToolbarConfiguration() {

        Toolbar toolbar = (Toolbar)findViewById(R.id.ng_Toolbar);
        setSupportActionBar(toolbar);
        ImageView ArrowBack = (ImageView)findViewById(R.id.ng_ArrowBack);
        TextView textView = (TextView)findViewById(R.id.ng_Header);
        RelativeLayout NextStep = (RelativeLayout) findViewById(R.id.ng_NextStep);
        if(getIntent().getIntExtra("actividad",0)==1){
            textView.setText("Cancelación del Pedido");
        }else if(getIntent().getIntExtra("actividad",0)==2){
            textView.setText("Cancelación Articulos");
        }

        NextStep.setVisibility(View.GONE);
        ArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

}
