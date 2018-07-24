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

import com.curso.onbringit.Adapters.ItemsCancel;
import com.curso.onbringit.Model.ApiRequest;
import com.curso.onbringit.R;

public class Activity_Cancel_Item extends AppCompatActivity
{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_item);
        ToolbarConfiguration();

        RecyclerView ng_rvCancelItem = (RecyclerView) findViewById(R.id.ng_rvCancelItem);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getApplicationContext());
        ItemsCancel itemsCancel = new ItemsCancel(this);

        ng_rvCancelItem.setLayoutManager(linearLayout);
        ng_rvCancelItem.setAdapter(itemsCancel);

        ApiRequest apiRequest = new ApiRequest(this.getApplicationContext());
        apiRequest.show_cancel_item( itemsCancel , getIntent().getStringExtra("order_id"));
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
