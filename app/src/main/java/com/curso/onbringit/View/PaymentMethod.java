package com.curso.onbringit.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.curso.onbringit.Fragment.ServicesActivity;
import com.curso.onbringit.R;

/**
 * Created by PC-PRAF on 5/8/2017.
 */

public class PaymentMethod extends AppCompatActivity
{
    RadioButton CashRadio,
                CreditCarRadio,
                PayPalRadio;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        ToolbarConfiguration();

        CashRadio = (RadioButton)findViewById(R.id.ng_Cash_Radio);
        CreditCarRadio = (RadioButton)findViewById(R.id.ng_CreditCard_Radio);
        PayPalRadio = (RadioButton)findViewById(R.id.ng_PayPal_Radio);

        LinearLayout CashLayout = (LinearLayout) findViewById(R.id.ng_Cash_Ly);
        LinearLayout CreditCard = (LinearLayout) findViewById(R.id.ng_CreditCard_Ly);
        LinearLayout PayPalLayout = (LinearLayout) findViewById(R.id.ng_PayPal_Ly);
        final LinearLayout CardInformation = (LinearLayout) findViewById(R.id.ng_ly_PayInformation);

        CashRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CreditCarRadio.setChecked(false);
                PayPalRadio.setChecked(false);
                if(CashRadio.isSelected())
                {
                    CashRadio.setChecked(false);
                    CardInformation.setVisibility(View.VISIBLE);
                }else{
                    CashRadio.setChecked(true);
                    CardInformation.setVisibility(View.GONE);
                }

            }
        });

        CreditCarRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CashRadio.setChecked(false);
                PayPalRadio.setChecked(false);
                if(CreditCarRadio.isSelected())
                {
                    CreditCarRadio.setChecked(false);
                }else{
                    CreditCarRadio.setChecked(true);
                    CardInformation.setVisibility(View.VISIBLE);
                }
            }
        });

        PayPalRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CashRadio.setChecked(false);
                CreditCarRadio.setChecked(false);
                if(PayPalRadio.isSelected())
                {
                    PayPalRadio.setChecked(false);
                }else{
                    PayPalRadio.setChecked(true);
                    CardInformation.setVisibility(View.VISIBLE);
                }
            }
        });

    }


    public void ToolbarConfiguration() {

        Toolbar toolbar = (Toolbar)findViewById(R.id.ng_Toolbar);
        setSupportActionBar(toolbar);

        ImageView ArrowBack = (ImageView)findViewById(R.id.ng_ArrowBack);
        RelativeLayout NextStep = (RelativeLayout) findViewById(R.id.ng_NextStep);
        TextView NextText = (TextView) findViewById(R.id.ng_NextText);
        TextView textView = (TextView)findViewById(R.id.ng_Header);
        textView.setText("Métodos de Pago");
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
                Intent intent = new Intent(view.getContext(),BottomNavigation.class);
                view.getContext().startActivity(intent);
            }
        });

    }
}
