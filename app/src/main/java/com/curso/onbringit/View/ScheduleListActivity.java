package com.curso.onbringit.View;

import android.content.Intent;
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

import com.curso.onbringit.Adapters.OrderSchedule;
import com.curso.onbringit.R;

/**
 * Created by PC-PRAF on 3/9/2017.
 */

public class EditScheduleActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_schedule);

        RecyclerView rvScheduleList = (RecyclerView) findViewById(R.id.rv_ScheduleList);
        OrderSchedule ScheduleOrders = new OrderSchedule(this.getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getApplicationContext());

        rvScheduleList.setAdapter(ScheduleOrders);
        rvScheduleList.setLayoutManager(linearLayoutManager);

        ToolbarConfiguration();
    }


    public void ToolbarConfiguration() {

        Toolbar toolbar = (Toolbar)findViewById(R.id.ng_Toolbar);
        setSupportActionBar(toolbar);
        ImageView ArrowBack = (ImageView)findViewById(R.id.ng_ArrowBack);
        TextView textView = (TextView)findViewById(R.id.ng_Header);
        RelativeLayout NextStep = (RelativeLayout) findViewById(R.id.ng_NextStep);
        textView.setText("Entregas Programadas");
        NextStep.setVisibility(View.GONE);
        ArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}
