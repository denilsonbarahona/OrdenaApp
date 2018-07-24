package com.curso.onbringit.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.curso.onbringit.Adapters.ItemsInDelivery;
import com.curso.onbringit.Model.ApiRequest;
import com.curso.onbringit.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by PC-PRAF on 3/9/2017.
 */

public class ItemScheduleActivity extends AppCompatActivity
{
    ItemsInDelivery itemsInDelivery;
    ApiRequest api;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_schedule);

        ToolbarConfiguration();

        itemsInDelivery = new ItemsInDelivery(2 , 4);
        FloatingActionButton ng_floating = (FloatingActionButton) findViewById(R.id.ng_floatingButton);

        ng_floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext() , ExpdeliveryActivity.class);
                intent.putExtra("process" , 3 );
                view.getContext().startActivity(intent);
            }
        });

        Get_Item_In_Order();
    }


    private void Get_Item_In_Order(){

        recyclerView = (RecyclerView)findViewById(R.id.ng_rvItemSchedule);
        linearLayoutManager = new LinearLayoutManager(this.getApplicationContext());
        recyclerView.setAdapter(itemsInDelivery);
        recyclerView.setLayoutManager(linearLayoutManager);
        api = new ApiRequest(this.getApplicationContext());
        api.get_item_schedule(itemsInDelivery , getIntent().getStringExtra("OrdenId"));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent.getIntExtra("Added",0)==1){
            Get_Item_In_Order();
            if(ItemsInDelivery.ItemsInDelivery.size() == 0){
                itemsInDelivery.AddItemsArray(ItemsInDelivery.ItemsInDelivery);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(ItemsInDelivery.ItemsInDelivery.size()==0){
            Get_Item_In_Order();
        }
    }

    public void ToolbarConfiguration() {

        Toolbar toolbar = (Toolbar)findViewById(R.id.ng_Toolbar);
        setSupportActionBar(toolbar);
        ImageView ArrowBack = (ImageView)findViewById(R.id.ng_ArrowBack);
        TextView textView = (TextView)findViewById(R.id.ng_Header);
        RelativeLayout NextStep = (RelativeLayout) findViewById(R.id.ng_NextStep);
        TextView NextText = (TextView) findViewById(R.id.ng_NextText);

        textView.setText("Orden Número # "+getIntent().getStringExtra("OrdenNumber"));
        NextStep.setVisibility(View.VISIBLE);
        NextText.setText("FINALIZAR");
        ArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        NextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JSONObject ItemJSON = null;
                JSONObject DeliveryJSON = null;
                try {
                    ItemJSON = new JSONObject(itemsInDelivery.GetItemInDelivery());
                    DeliveryJSON = new JSONObject(getIntent().getStringExtra("info_delivery"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                api.update_schedule_order(  getIntent().getStringExtra("OrdenId") ,  ItemJSON ,  DeliveryJSON);
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        itemsInDelivery.RefreshItems();
    }
}
