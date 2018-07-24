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

import com.curso.onbringit.Adapters.ProductsAdapter;
import com.curso.onbringit.Adapters.ThirdPartyApplications;
import com.curso.onbringit.Class.LayoutGrid;
import com.curso.onbringit.Model.ApiRequest;
import com.curso.onbringit.R;

public class ActivityProducts extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        ToolbarConfiguration();

        RecyclerView rvProducts = (RecyclerView)findViewById(R.id.ng_rvProducts);
        LayoutGrid layoutGrid = new LayoutGrid(getApplicationContext(),500);
        ProductsAdapter productsAdapter = new ProductsAdapter(this.getApplicationContext() , this);

        rvProducts.setAdapter(productsAdapter);
        rvProducts.setLayoutManager(layoutGrid);
        ApiRequest apiRequest = new ApiRequest(this.getApplicationContext());
        apiRequest.get_products(productsAdapter , getIntent().getIntExtra("store_id" , 0));

    }


    public void ToolbarConfiguration()
    {
        Toolbar toolbar = (Toolbar)findViewById(R.id.ng_Toolbar);
        setSupportActionBar(toolbar);
        ImageView ArrowBack = (ImageView)findViewById(R.id.ng_ArrowBack);
        RelativeLayout NextStep = (RelativeLayout) findViewById(R.id.ng_NextStep);
        TextView textView = (TextView)findViewById(R.id.ng_Header);
        textView.setText("Productos");
        NextStep.setVisibility(View.VISIBLE);
        ArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

}
