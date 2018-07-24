package com.curso.onbringit.View;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.curso.onbringit.Adapters.AddressSaved;
import com.curso.onbringit.DataBase.SqlConnection;
import com.curso.onbringit.DataBase.Sql_Query;
import com.curso.onbringit.R;

public class ChangeAddressActivity extends AppCompatActivity {

    private AddressSaved addressSaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeaddress);

        ToolbarConfiguration();

        SQLiteDatabase db = Sql_Query.GetConnection(this.getApplicationContext()).getReadableDatabase();
        Sql_Query sql_query = new Sql_Query(db,this.getApplicationContext());


        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.ng_rvAddressSaved);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getApplicationContext());
        addressSaved = new AddressSaved(getIntent().getIntExtra("Activity",0) , sql_query );
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(addressSaved);


        FloatingActionButton floatingActionButton = (FloatingActionButton)findViewById(R.id.ng_floatingButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext() , AddressAdd.class);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent.getIntExtra("Added",0)==1){
            addressSaved.RefreshItems();
        }
    }

    public void ToolbarConfiguration()
    {
        Toolbar toolbar = (Toolbar)findViewById(R.id.ng_Toolbar);
        setSupportActionBar(toolbar);
        ImageView ArrowBack = (ImageView)findViewById(R.id.ng_ArrowBack);
        RelativeLayout NextStep = (RelativeLayout) findViewById(R.id.ng_NextStep);
        TextView textView = (TextView)findViewById(R.id.ng_Header);
        textView.setText("Cambiar Dirección");
        NextStep.setVisibility(View.GONE);
        ArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

}
