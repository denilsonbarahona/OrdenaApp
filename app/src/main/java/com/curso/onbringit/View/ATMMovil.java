package com.curso.onbringit.View;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aldoapps.autoformatedittext.AutoFormatEditText;
import com.curso.onbringit.DataBase.SqlConnection;
import com.curso.onbringit.DataBase.Sql_Query;
import com.curso.onbringit.Model.ApiRequest;
import com.curso.onbringit.R;

import java.util.ArrayList;

/**
 * Created by PC-PRAF on 6/8/2017.
 */

public class ATMMovil extends AppCompatActivity {

    private TextView AddressSelected;
    private TextView DeliveryInformation;
    private ArrayList<String[]> Default_Address = new ArrayList<>();
    private AutoFormatEditText MoneyATM;
    private EditText deliTo;
    private EditText NumberPhone;

    public static String moneyAtm;
    public static String DeliveryTo;
    public static String DeliveryAddres;
    public static String phoneNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atm_movil);
        ToolbarConfiguration();

        SqlConnection connection = new SqlConnection(this.getApplicationContext() , "ORDENADB",null , 1);
        SQLiteDatabase sqLiteDatabase = connection.getReadableDatabase();

        Sql_Query query = new Sql_Query(sqLiteDatabase , null);

        this.Default_Address = query.GetAddressDefault();

        moneyAtm ="";
        DeliveryTo ="";
        DeliveryAddres ="";
        phoneNumber ="";

        deliTo = (EditText) findViewById(R.id.ng_ATM_order_to);
        MoneyATM = (AutoFormatEditText) findViewById(R.id.ng_ATM_Quantity);
        NumberPhone = (EditText) findViewById(R.id.ng_ATM_phonenumber);


        MoneyATM.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if(MoneyATM.getText().length() > 0){
                    if( Double.parseDouble ( MoneyATM.getText().toString().replace(",",""))< 1000 ||
                            Double.parseDouble  ( MoneyATM.getText().toString().replace(",",""))>8000){
                        Toast.makeText (ATMMovil.this,"Solo podemos hacer entrega desde L 1,000.00 hasta L 8,000.00" , Toast.LENGTH_LONG).show();
                        MoneyATM.setText("");
                    }
                }
            }
        });


        DeliveryInformation = (TextView) findViewById(R.id.ng_ATM_DeliveryInformation);
        AddressSelected = (TextView) findViewById(R.id.ng_addressSelected);

        if(this.Default_Address.size()>0){
            AddressSelected.setText(this.Default_Address.get(0)[0]);
            DeliveryInformation.setText(this.Default_Address.get(0)[1]);
        }else{
            AddressSelected.setText("");
            DeliveryInformation.setText("");
        }

        LinearLayout ChangeAddres = (LinearLayout) findViewById(R.id.ng_ATM_ChangeAddress);
        ChangeAddres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ChangeAddressActivity.class);
                intent.putExtra("Activity",2);
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
            DeliveryAddres = intent.getStringExtra("AddressId");
        }
    }

    public void ToolbarConfiguration() {

        Toolbar toolbar = (Toolbar)findViewById(R.id.ng_Toolbar);
        setSupportActionBar(toolbar);
        ImageView ArrowBack = (ImageView)findViewById(R.id.ng_ArrowBack);
        TextView textView = (TextView)findViewById(R.id.ng_Header);
        RelativeLayout NextStep = (RelativeLayout) findViewById(R.id.ng_NextStep);
        TextView NextText = (TextView) findViewById(R.id.ng_NextText);

        textView.setText("ATM Movil");
        NextStep.setVisibility(View.VISIBLE);
        NextText.setText("FINALIZAR");
        ArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        NextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                moneyAtm = MoneyATM.getText().toString();
                DeliveryTo = deliTo.getText().toString();
                phoneNumber = NumberPhone.getText().toString();
                DeliveryAddres = Default_Address.get(0)[2].toString();

                ApiRequest request = new ApiRequest(view.getContext());
                request.Send_AtmOrder();

            }
        });
    }
}
