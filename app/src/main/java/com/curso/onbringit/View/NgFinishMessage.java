package com.curso.onbringit.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.curso.onbringit.R;

/**
 * Created by PC-PRAF on 7/8/2017.
 */

public class NgFinishMessage extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ng_finish_order);

        TextView ngMessage = (TextView) findViewById(R.id.ng_message);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ng_close);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if(getIntent().getIntExtra("from",0)== 1){
            ngMessage.setText("YAY!!. La entrega de su efectivo se realizara dentro de 30 minutos.");
        } else if(getIntent().getIntExtra("from",0)== 2){
            ngMessage.setText("YAY!!. El pago de su orden se realizará al momento de la entrega. Podrás pagar con efectivo o targeta.");
        }else if(getIntent().getIntExtra("from",0)==3){
            ngMessage.setText("Estamos aplicando el cambio para cuando hagamos tu proxima entrega programada.");
        }

    }
}
