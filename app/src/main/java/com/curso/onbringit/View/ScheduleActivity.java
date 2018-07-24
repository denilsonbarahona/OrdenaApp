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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.curso.onbringit.Adapters.ItemsInDelivery;
import com.curso.onbringit.R;

/**
 * Created by PC-PRAF on 12/8/2017.
 */

public class ScheduleActivity extends AppCompatActivity {

    ItemsInDelivery itemsInDelivery;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        ToolbarConfiguration();

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.ng_itemAddedDelivery);
        itemsInDelivery = new ItemsInDelivery(1 , 2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getApplicationContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(itemsInDelivery);

        final EditText itemName = (EditText) findViewById(R.id.ng_ItemName);
        final EditText itemQuantity =(EditText) findViewById(R.id.ng_ItemQuantity);
        final EditText itemDeliveryNotes = (EditText) findViewById(R.id.ng_DeliveryNotes);
        final EditText itemAddresPick = (EditText) findViewById(R.id.ng_itemAddresPick);


        Button ng_AddItem = (Button)findViewById(R.id.ng_btn_add_item);
        ng_AddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(itemsInDelivery.getItemCount() < 10)
                {
                    if(!itemName.getText().toString().trim().equals("")
                            && !itemAddresPick.getText().toString().toString().equals(""))
                    {
                        itemsInDelivery.AddNewItem(itemName.getText().toString() ,
                                itemQuantity.getText().toString() ,
                                itemDeliveryNotes.getText().toString(),itemAddresPick.getText().toString());

                        itemName.setText("");
                        itemQuantity.setText("");
                        itemDeliveryNotes.setText("");
                        itemAddresPick.setText("");

                    }

                    else if (itemName.getText().toString().trim().equals(""))
                    {
                        Snackbar.make(view, "Tienes que decirnos que te traemos.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        itemName.requestFocus();

                    }else if(itemAddresPick.getText().toString().toString().equals("")){

                        Snackbar.make(view, "No se te olvide decirnos donde lo buscamos por tí.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        itemAddresPick.requestFocus();
                    }

                }else
                {
                    Snackbar.make(view, "Solo se puede hacer una orden de 10 artículos.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }


    public void ToolbarConfiguration() {

        Toolbar toolbar = (Toolbar)findViewById(R.id.ng_Toolbar);
        setSupportActionBar(toolbar);
        ImageView ArrowBack = (ImageView)findViewById(R.id.ng_ArrowBack);
        TextView textView = (TextView)findViewById(R.id.ng_Header);
        RelativeLayout NextStep = (RelativeLayout) findViewById(R.id.ng_NextStep);
        textView.setText("Entregas Programadas");
        NextStep.setVisibility(View.VISIBLE);
        ArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        NextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(itemsInDelivery.getItemCount() < 10 && itemsInDelivery.getItemCount()>0){
                    Intent intent = new Intent(view.getContext(),AddScheduleActivity.class);
                    view.getContext().startActivity(intent);
                }else{
                    Snackbar.make(view, "Solo se puede hacer una orden de hasta 10 artículos.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        itemsInDelivery.RefreshItems();
    }
}
