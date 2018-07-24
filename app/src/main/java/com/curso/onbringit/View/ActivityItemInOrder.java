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

import com.curso.onbringit.Adapters.ItemsInOrder;
import com.curso.onbringit.Model.ApiRequest;
import com.curso.onbringit.R;

/**
 * Created by PC-PRAF on 16/11/2017.
 */

public class ActivityItemInOrder extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_in_list);
        ToolbarConfiguration();

        RecyclerView rv_ItemInList = (RecyclerView) findViewById(R.id.ng_rvItemsInOrder);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getApplicationContext());
        ItemsInOrder itemsInOrder = new ItemsInOrder();

        rv_ItemInList.setLayoutManager(linearLayout);
        rv_ItemInList.setAdapter(itemsInOrder);

        ApiRequest apiRequest = new ApiRequest(this.getApplicationContext());
        apiRequest.show_order_item( itemsInOrder , getIntent().getStringExtra("order_id"));

    }


    public void ToolbarConfiguration() {

        Toolbar toolbar = (Toolbar)findViewById(R.id.ng_Toolbar);
        setSupportActionBar(toolbar);
        ImageView ArrowBack = (ImageView)findViewById(R.id.ng_ArrowBack);
        TextView textView = (TextView)findViewById(R.id.ng_Header);
        RelativeLayout NextStep = (RelativeLayout) findViewById(R.id.ng_NextStep);
        textView.setText("Articulos de la orden");
        NextStep.setVisibility(View.GONE);
        ArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}
