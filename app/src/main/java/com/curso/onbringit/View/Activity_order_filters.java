package com.curso.onbringit.View;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.curso.onbringit.Adapters.Headings;
import com.curso.onbringit.Adapters.Stores;
import com.curso.onbringit.Model.ApiRequest;
import com.curso.onbringit.R;

/**
 * Created by PC-PRAF on 17/4/2018.
 */


public class Activity_order_filters extends AppCompatActivity {

    private static RecyclerView rvStores;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_filters);
        ToolbarConfiguration();

        RecyclerView rvHeading = (RecyclerView) findViewById(R.id.ng_rvheading);
        rvStores = (RecyclerView) findViewById(R.id.ng_rvStores);

        LinearLayoutManager linearLayout = new LinearLayoutManager(getApplicationContext(),  LinearLayoutManager.HORIZONTAL , false);
        LinearLayoutManager storeLayout = new LinearLayoutManager(getApplicationContext());

        Headings headings_list = new Headings(getApplicationContext());
        rvHeading.setLayoutManager(linearLayout);
        rvHeading.setAdapter(headings_list);

        Stores  stores_list = new Stores(getApplicationContext());
        rvStores.setLayoutManager(storeLayout);
        rvStores.setAdapter(stores_list);

        ApiRequest apiRequest = new ApiRequest(this.getApplicationContext());
        apiRequest.get_headings(headings_list);

        apiRequest.get_stores(stores_list , 0);
    }

    public void SearchStore(int heading_id , Context cntx){

        LinearLayoutManager storeLayout = new LinearLayoutManager(cntx);
        Stores  stores_list = new Stores(cntx);
        rvStores.setLayoutManager(storeLayout);
        rvStores.setAdapter(stores_list);
        ApiRequest apiRequest = new ApiRequest(cntx);
        apiRequest.get_stores(stores_list ,heading_id);

    }


    public void ToolbarConfiguration() {

        Toolbar toolbar = (Toolbar)findViewById(R.id.ng_Toolbar);
        setSupportActionBar(toolbar);
        ImageView ArrowBack = (ImageView)findViewById(R.id.ng_ArrowBack);
        TextView textView = (TextView)findViewById(R.id.ng_Header);
        textView.setText("Express Delivery");
        ArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

}
