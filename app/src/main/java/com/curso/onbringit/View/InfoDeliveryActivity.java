package com.curso.onbringit.View;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.curso.onbringit.DataBase.SqlConnection;
import com.curso.onbringit.DataBase.Sql_Query;
import com.curso.onbringit.Model.ApiRequest;
import com.curso.onbringit.R;

import java.util.ArrayList;

/**
 * Created by PC-PRAF on 29/7/2017.
 */

public class InfoDeliveryActivity extends AppCompatActivity {

    private TextView AddressSelected;
    private TextView DeliveryInformation;
    private ArrayList<String[]>Default_Address = new ArrayList<>();
    private EditText EntregarA;
    private EditText PhoneNumber;
    public static String Entrega;
    public static String phonerNumber;
    public static String Address;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addressdelivery);
        ToolbarConfiguration();

        SqlConnection connection = new SqlConnection(this.getApplicationContext() , "ORDENADB",null , 1);
        SQLiteDatabase sqLiteDatabase = connection.getReadableDatabase();

        Sql_Query query = new Sql_Query(sqLiteDatabase , null);

        this.Default_Address = query.GetAddressDefault();

        Entrega = "";
        phonerNumber = "";
        Address = "";

        EntregarA = (EditText) findViewById(R.id.ng_item_order_to);
        PhoneNumber = (EditText) findViewById(R.id.ng_order_phonenumber);

        DeliveryInformation = (TextView) findViewById(R.id.ng_DeliveryInformation);
        AddressSelected = (TextView) findViewById(R.id.ng_addressSelected);

        if(this.Default_Address.size()> 0){
            AddressSelected.setText(this.Default_Address.get(0)[0]);
            DeliveryInformation.setText(this.Default_Address.get(0)[1]);
        }else {
            AddressSelected.setText("");
            DeliveryInformation.setText("");
        }

        PhoneNumber.setText(query.getUsrPhone());
        LinearLayout LnBtnChangeAddress = (LinearLayout) findViewById(R.id.ng_ChangeAddress);
        LnBtnChangeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ChangeAddressActivity.class);
                intent.putExtra("Activity",1);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent.getStringExtra("AddressName")!= null){
            AddressSelected.setText(intent.getStringExtra("AddressName"));
            DeliveryInformation.setText(intent.getStringExtra("Address"));
            Address = intent.getStringExtra("AddressId");
        }
    }

    public void ToolbarConfiguration() {

        Toolbar toolbar = (Toolbar)findViewById(R.id.ng_Toolbar);
        setSupportActionBar(toolbar);

        ImageView ArrowBack = (ImageView)findViewById(R.id.ng_ArrowBack);
        RelativeLayout NextStep = (RelativeLayout) findViewById(R.id.ng_NextStep);
        TextView textView = (TextView)findViewById(R.id.ng_Header);
        textView.setText("Información de Entrega");
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

                if(!EntregarA.getText().toString().trim().equals("") &&
                   !PhoneNumber.getText().toString().trim().equals(""))
                {
                    ApiRequest request = new ApiRequest(view.getContext());
                    Entrega = EntregarA.getText().toString();
                    phonerNumber = PhoneNumber.getText().toString();
                    Address = Default_Address.get(0)[2].toString();
                    request.Send_Order();
                }else{
                    if(EntregarA.getText().toString().trim().equals("")){
                        Snackbar.make(view, "Tienes que decirnos la persona que va a recibir el pedido",Snackbar.LENGTH_LONG).show();
                    }else if(PhoneNumber.getText().toString().trim().equals("")){
                        Snackbar.make(view, "Tu número telefónico nos ayudara a mantener una comunicación contigo cuando estemos buscando tus ordenes.",Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

}
