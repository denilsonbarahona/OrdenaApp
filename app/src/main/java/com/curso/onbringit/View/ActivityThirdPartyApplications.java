package com.curso.onbringit.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.curso.onbringit.Adapters.ThirdPartyApplications;
import com.curso.onbringit.Model.ApiRequest;
import com.curso.onbringit.R;

/**
 * Created by PC-PRAF on 3/12/2017.
 */

public class ActivityThirdPartyApplications extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_party_applications);
        ToolbarConfiguration();

        RecyclerView rvApplications = (RecyclerView)findViewById(R.id.ng_rvThird_party);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        ThirdPartyApplications ApplicationsAdapter = new ThirdPartyApplications();

        rvApplications.setAdapter(ApplicationsAdapter);
        rvApplications.setLayoutManager(linearLayoutManager);
    }



    public void ToolbarConfiguration()
    {
        Toolbar toolbar = (Toolbar)findViewById(R.id.ng_Toolbar);
        setSupportActionBar(toolbar);
        ImageView ArrowBack = (ImageView)findViewById(R.id.ng_ArrowBack);
        RelativeLayout NextStep = (RelativeLayout) findViewById(R.id.ng_NextStep);
        TextView textView = (TextView)findViewById(R.id.ng_Header);
        textView.setText("Aplicaciones de Terceros");
        NextStep.setVisibility(View.GONE);
        ArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

}
