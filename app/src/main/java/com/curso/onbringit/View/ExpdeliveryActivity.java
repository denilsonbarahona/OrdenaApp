package com.curso.onbringit.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.curso.onbringit.Adapters.ItemsInDelivery;
import com.curso.onbringit.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class ExpdeliveryActivity extends AppCompatActivity
{
    ItemsInDelivery itemsInDelivery;
    private static  int option_amount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expdelivery);

        ToolbarConfiguration();

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.ng_itemAddedDelivery);
        itemsInDelivery = new ItemsInDelivery(1 , 1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getApplicationContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(itemsInDelivery);

        final EditText itemName = (EditText) findViewById(R.id.ng_ItemName);
 //       final EditText itemQuantity =(EditText) findViewById(R.id.ng_ItemQuantity);
        final MaterialSpinner itemQuantity =(MaterialSpinner) findViewById(R.id.ng_ItemQuantity_spinner);
        final EditText itemDeliveryNotes = (EditText) findViewById(R.id.ng_DeliveryNotes);
        final EditText itemAddresPick = (EditText) findViewById(R.id.ng_itemAddresPick);
//        final MaterialSpinner itemAddresPick = (MaterialSpinner) findViewById(R.id.ng_ItemQuantity_spinner);

        itemQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Display display = getWindowManager().getDefaultDisplay();
                int mwidth = display.getWidth();
                int mheight = display.getHeight();

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                View v = LayoutInflater.from(view.getContext()).inflate(R.layout.ng_popup_amount ,null);

                final RadioButton ng_rbPlate = (RadioButton) v.findViewById(R.id.ng_amount_plate);
                final RadioButton ng_rbKg = (RadioButton) v.findViewById(R.id.ng_amount_kg);
                final RadioButton ng_rbPound = (RadioButton) v.findViewById(R.id.ng_amount_pound);
                final RadioButton ng_rbOthers = (RadioButton) v.findViewById(R.id.ng_amount_other);
                /*---------------------------------------------------------------------------------*/
                final EditText ng_rbPlateText = (EditText) v.findViewById(R.id.ng_amount_plate_text);
                final EditText ng_rbKgText = (EditText) v.findViewById(R.id.ng_amount_kg_text);
                final EditText ng_rbPoundText = (EditText) v.findViewById(R.id.ng_amount_pount_text);
                final EditText ng_OthersText = (EditText) v.findViewById(R.id.ng_amount_other_text);

                ng_rbPlate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(ng_rbPlate.isChecked()){
                            ng_rbOthers.setChecked(false);
                            ng_rbKg.setChecked(false);
                            ng_rbPound.setChecked(false);

                            ng_OthersText.setEnabled(false);
                            ng_OthersText.setText("");
                            ng_rbPlateText.setEnabled(true);
                            ng_rbPlateText.setText("");
                            ng_rbKgText.setEnabled(false);
                            ng_rbKgText.setText("");
                            ng_rbPoundText.setEnabled(false);
                            ng_rbPoundText.setText("");
                            ExpdeliveryActivity.option_amount = 1;
                        }
                    }
                });

                ng_rbKg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(ng_rbKg.isChecked()){
                            ng_rbOthers.setChecked(false);
                            ng_rbPlate.setChecked(false);
                            ng_rbPound.setChecked(false);

                            ng_OthersText.setEnabled(false);
                            ng_OthersText.setText("");
                            ng_rbPlateText.setEnabled(false);
                            ng_rbPlateText.setText("");
                            ng_rbKgText.setEnabled(true);
                            ng_rbKgText.setText("");
                            ng_rbPoundText.setEnabled(false);
                            ng_rbPoundText.setText("");
                            ExpdeliveryActivity.option_amount = 2;
                        }
                    }
                });


                ng_rbPound.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(ng_rbPound.isChecked()){
                            ng_rbOthers.setChecked(false);
                            ng_rbPlate.setChecked(false);
                            ng_rbKg.setChecked(false);
                            ng_OthersText.setEnabled(false);
                            ng_OthersText.setText("");
                            ng_rbPlateText.setEnabled(false);
                            ng_rbPlateText.setText("");
                            ng_rbKgText.setEnabled(false);
                            ng_rbKgText.setText("");
                            ng_rbPoundText.setEnabled(true);
                            ng_rbPoundText.setText("");

                            ExpdeliveryActivity.option_amount = 3;
                        }
                    }
                });

                ng_rbOthers.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(ng_rbOthers.isChecked()){
                            ng_OthersText.setEnabled(true);
                            ng_OthersText.setText("");
                            ng_rbPlateText.setEnabled(false);
                            ng_rbPlateText.setText("");
                            ng_rbKgText.setEnabled(false);
                            ng_rbKgText.setText("");
                            ng_rbPoundText.setEnabled(false);
                            ng_rbPoundText.setText("");

                            ng_rbPlate.setChecked(false);
                            ng_rbKg.setChecked(false);
                            ng_rbPound.setChecked(false);
                            ExpdeliveryActivity.option_amount = 4;
                        }
                    }
                });

                builder.setView(v);
                final AlertDialog dialog = builder.create();
                dialog.show();

                WindowManager.LayoutParams LP = new WindowManager.LayoutParams();
                LP.copyFrom(dialog.getWindow().getAttributes());
                LP.width  = (int)(( mwidth / 2 ) * 1.9);
                LP.height = (int)(( mheight /2 ) * 1.5);

                TextView ng_CancelPop = (TextView) v.findViewById(R.id.ng_cancelpop);
                TextView ng_Selected = (TextView) v.findViewById(R.id.ng_schedule_selected);


                ng_Selected.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (ExpdeliveryActivity.option_amount){
                            case 1: itemQuantity.setText(ng_rbPlateText.getText().toString()+" Platos ");
                                    break;
                            case 2: itemQuantity.setText(ng_rbKgText.getText().toString()+" Kg ");
                                break;
                            case 3: itemQuantity.setText(ng_rbPoundText.getText().toString()+" Lb ");
                                break;
                            case 4: itemQuantity.setText(ng_OthersText.getText().toString());
                                break;

                        }
                        ExpdeliveryActivity.option_amount = 0;
                        dialog.cancel();
                    }
                });

                ng_CancelPop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });

                dialog.getWindow().setAttributes(LP);
                itemQuantity.collapse();
            }
        });


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

    @Override
    protected void onRestart() {
        super.onRestart();
        itemsInDelivery.RefreshItems();
    }

    public void ToolbarConfiguration() {

        Toolbar toolbar = (Toolbar)findViewById(R.id.ng_Toolbar);
        setSupportActionBar(toolbar);
        ImageView ArrowBack = (ImageView)findViewById(R.id.ng_ArrowBack);
        TextView textView = (TextView)findViewById(R.id.ng_Header);
        RelativeLayout NextStep = (RelativeLayout) findViewById(R.id.ng_NextStep);

        if(getIntent().getIntExtra("process" , 0) == 3){
            textView.setText("Nuevo Artículo");
        }else{
            textView.setText("Express delivery");
        }


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

                if(getIntent().getIntExtra("process" , 0)==3){
                    Intent intent = new Intent(view.getContext(),ItemScheduleActivity.class);
                    intent.putExtra("Added",1);
                    view.getContext().startActivity(intent);
                }else{
                    if(itemsInDelivery.getItemCount() < 10 && itemsInDelivery.getItemCount()>0 ){
                        Intent intent = new Intent(view.getContext(),InfoDeliveryActivity.class);
                        view.getContext().startActivity(intent);
                    }else{
                        Snackbar.make(view, "Solo se puede hacer una orden de hasta 10 artículos.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
            }
        });

    }
}
