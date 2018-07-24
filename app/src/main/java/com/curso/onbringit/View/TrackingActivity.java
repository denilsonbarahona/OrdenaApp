package com.curso.onbringit.View;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.curso.onbringit.R;

/**
 * Created by PC-PRAF on 1/9/2017.
 */

public class TrackingActivity extends AppCompatActivity
{

    /* imageView Dots*/

    ImageView FirstDot;
    ImageView SecondDot;
    ImageView ThirdDot;
    ImageView FourthDot;
    ImageView FifthDot;

    /* ImageView Icon */

    ImageView FirstIcon;
    ImageView SecondIcon;
    ImageView ThirdIcon;
    ImageView FourthIcon;
    ImageView FifthIcon;

    /* TextView Name */

    TextView FirstName;
    TextView SecondName;
    TextView ThirdName;
    TextView FourthName;
    TextView FifthName;

    /* TextView Description */


    TextView FirstDescription;
    TextView SecondDescription;
    TextView ThirdDescription;
    TextView FourthDescription;
    TextView FifthDescription;

    /* Linear Progress */

    LinearLayout FirstProgress;
    LinearLayout SecondProgress;
    LinearLayout ThirdProgress;
    LinearLayout FourthProgress;
    LinearLayout FifthProgress;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);
        ToolbarConfiguration();


        /* Dots */

        FirstDot = (ImageView) findViewById(R.id.ng_first_state_dot);
        SecondDot = (ImageView) findViewById(R.id.ng_second_state_dot);
        ThirdDot = (ImageView) findViewById(R.id.ng_third_state_dot);
        FourthDot = (ImageView) findViewById(R.id.ng_fourth_state_dot);
        FifthDot = (ImageView) findViewById(R.id.ng_fifth_state_dot);

        /* icon states */

        FirstIcon = (ImageView) findViewById(R.id.ng_first_state_image);
        SecondIcon = (ImageView) findViewById(R.id.ng_second_state_image);
        ThirdIcon = (ImageView) findViewById(R.id.ng_third_state_image);
        FourthIcon = (ImageView) findViewById(R.id.ng_fourth_state_image);
        FifthIcon = (ImageView) findViewById(R.id.ng_fifth_state_image);

        /* Name State */

        FirstName = (TextView) findViewById(R.id.ng_first_state_name);
        SecondName = (TextView) findViewById(R.id.ng_second_state_name);
        ThirdName = (TextView) findViewById(R.id.ng_third_state_name);
        FourthName = (TextView) findViewById(R.id.ng_fourth_state_name);
        FifthName = (TextView) findViewById(R.id.ng_fifth_state_name);

        /* Description State */

        FirstDescription = (TextView) findViewById(R.id.ng_first_state_description);
        SecondDescription = (TextView) findViewById(R.id.ng_second_state_description);
        ThirdDescription = (TextView) findViewById(R.id.ng_third_state_description);
        FourthDescription = (TextView) findViewById(R.id.ng_fourth_state_description);
        FifthDescription = (TextView) findViewById(R.id.ng_fifth_state_description);

        /* Progress Line */

        FirstProgress = (LinearLayout) findViewById(R.id.ng_first_state_process);
        SecondProgress = (LinearLayout) findViewById(R.id.ng_second_state_process);
        ThirdProgress = (LinearLayout) findViewById(R.id.ng_third_state_process);
        FourthProgress = (LinearLayout) findViewById(R.id.ng_fourth_state_process);

        ShowOrderCurrentState();
    }


    public void ShowOrderCurrentState(){

       switch (getIntent().getStringExtra("state_id")){
           case "1":
                    FirstDot.setBackgroundResource(R.drawable.ng_status_dot_active);
                    FirstIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent), PorterDuff.Mode.SRC_IN);
                    FirstName.setTextColor(getResources().getColor(R.color.colorAccent));
                    FirstDescription.setTextColor(getResources().getColor(R.color.colorAccent));

                    SecondIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.disable), PorterDuff.Mode.SRC_IN);
                    ThirdIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.disable), PorterDuff.Mode.SRC_IN);
                    FourthIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.disable), PorterDuff.Mode.SRC_IN);
                    FifthIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.disable), PorterDuff.Mode.SRC_IN);

               break;
           case "2":
                    FirstDot.setBackgroundResource(R.drawable.ng_status_dot_done);
                    FirstIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.primary_text), PorterDuff.Mode.SRC_IN);
                    FirstName.setTextColor(getResources().getColor(R.color.primary_text));
                    FirstDescription.setTextColor(getResources().getColor(R.color.primary_text));
                    FirstProgress.setBackgroundColor(getResources().getColor(R.color.endprocess));

                    SecondDot.setBackgroundResource(R.drawable.ng_status_dot_active);
                    SecondIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent), PorterDuff.Mode.SRC_IN);
                    SecondName.setTextColor(getResources().getColor(R.color.colorAccent));
                    SecondDescription.setTextColor(getResources().getColor(R.color.colorAccent));

                    ThirdIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.disable), PorterDuff.Mode.SRC_IN);
                    FourthIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.disable), PorterDuff.Mode.SRC_IN);
                    FifthIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.disable), PorterDuff.Mode.SRC_IN);

               break;

           case "3":
               FirstDot.setBackgroundResource(R.drawable.ng_status_dot_done);
               FirstIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.primary_text), PorterDuff.Mode.SRC_IN);
               FirstName.setTextColor(getResources().getColor(R.color.primary_text));
               FirstDescription.setTextColor(getResources().getColor(R.color.primary_text));
               FirstProgress.setBackgroundColor(getResources().getColor(R.color.endprocess));

               SecondDot.setBackgroundResource(R.drawable.ng_status_dot_done);
               SecondIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.primary_text), PorterDuff.Mode.SRC_IN);
               SecondName.setTextColor(getResources().getColor(R.color.primary_text));
               SecondDescription.setTextColor(getResources().getColor(R.color.primary_text));
               SecondProgress.setBackgroundColor(getResources().getColor(R.color.primary_text));

               ThirdDot.setBackgroundResource(R.drawable.ng_status_dot_active);
               ThirdIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent), PorterDuff.Mode.SRC_IN);
               ThirdName.setTextColor(getResources().getColor(R.color.colorAccent));
               ThirdDescription.setTextColor(getResources().getColor(R.color.colorAccent));

               FourthIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.disable), PorterDuff.Mode.SRC_IN);
               FifthIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.disable), PorterDuff.Mode.SRC_IN);

               break;

           case "4":
               FirstDot.setBackgroundResource(R.drawable.ng_status_dot_done);
               FifthIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.primary_text), PorterDuff.Mode.SRC_IN);
               FirstName.setTextColor(getResources().getColor(R.color.primary_text));
               FirstDescription.setTextColor(getResources().getColor(R.color.primary_text));
               FirstProgress.setBackgroundColor(getResources().getColor(R.color.endprocess));

               SecondDot.setBackgroundResource(R.drawable.ng_status_dot_done);
               FifthIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.primary_text), PorterDuff.Mode.SRC_IN);
               SecondName.setTextColor(getResources().getColor(R.color.primary_text));
               SecondDescription.setTextColor(getResources().getColor(R.color.primary_text));
               SecondProgress.setBackgroundColor(getResources().getColor(R.color.endprocess));

               ThirdDot.setBackgroundResource(R.drawable.ng_status_dot_done);
               FifthIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.primary_text), PorterDuff.Mode.SRC_IN);
               ThirdName.setTextColor(getResources().getColor(R.color.primary_text));
               ThirdDescription.setTextColor(getResources().getColor(R.color.primary_text));
               ThirdProgress.setBackgroundColor(getResources().getColor(R.color.endprocess));

               FourthDot.setBackgroundResource(R.drawable.ng_status_dot_active);
               FourthIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent), PorterDuff.Mode.SRC_IN);
               FourthName.setTextColor(getResources().getColor(R.color.colorAccent));
               FourthDescription.setTextColor(getResources().getColor(R.color.colorAccent));

               FifthIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.disable), PorterDuff.Mode.SRC_IN);

               break;

           case "5":
               FirstDot.setBackgroundResource(R.drawable.ng_status_dot_done);
               FirstIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.primary_text), android.graphics.PorterDuff.Mode.MULTIPLY);
               FirstName.setTextColor(getResources().getColor(R.color.primary_text));
               FirstDescription.setTextColor(getResources().getColor(R.color.primary_text));
               FirstProgress.setBackgroundColor(getResources().getColor(R.color.endprocess));

               SecondDot.setBackgroundResource(R.drawable.ng_status_dot_done);
               SecondIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.primary_text), PorterDuff.Mode.SRC_IN);
               SecondName.setTextColor(getResources().getColor(R.color.primary_text));
               SecondDescription.setTextColor(getResources().getColor(R.color.primary_text));
               SecondProgress.setBackgroundColor(getResources().getColor(R.color.endprocess));

               ThirdDot.setBackgroundResource(R.drawable.ng_status_dot_done);
               ThirdIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.primary_text), PorterDuff.Mode.SRC_IN);
               ThirdName.setTextColor(getResources().getColor(R.color.primary_text));
               ThirdDescription.setTextColor(getResources().getColor(R.color.primary_text));
               ThirdProgress.setBackgroundColor(getResources().getColor(R.color.endprocess));

               FourthDot.setBackgroundResource(R.drawable.ng_status_dot_done);
               FourthIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.primary_text), PorterDuff.Mode.SRC_IN);
               FourthName.setTextColor(getResources().getColor(R.color.primary_text));
               FourthDescription.setTextColor(getResources().getColor(R.color.primary_text));
               FourthProgress.setBackgroundColor(getResources().getColor(R.color.endprocess));

               FifthDot.setBackgroundResource(R.drawable.ng_status_dot_done);
               FifthIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.primary_text), PorterDuff.Mode.SRC_IN);
               FifthName.setTextColor(getResources().getColor(R.color.primary_text));
               FifthDescription.setTextColor(getResources().getColor(R.color.primary_text));

               break;

           case "6":
               FirstDot.setBackgroundResource(R.drawable.ng_status_dot_done);
               FirstIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.primary_text), android.graphics.PorterDuff.Mode.MULTIPLY);
               FirstName.setTextColor(getResources().getColor(R.color.primary_text));
               FirstDescription.setTextColor(getResources().getColor(R.color.primary_text));
               FirstProgress.setBackgroundColor(getResources().getColor(R.color.endprocess));

               SecondDot.setBackgroundResource(R.drawable.ng_status_dot_done);
               SecondIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.primary_text), android.graphics.PorterDuff.Mode.MULTIPLY);
               SecondName.setTextColor(getResources().getColor(R.color.primary_text));
               SecondDescription.setTextColor(getResources().getColor(R.color.primary_text));
               SecondProgress.setBackgroundColor(getResources().getColor(R.color.endprocess));

               ThirdDot.setBackgroundResource(R.drawable.ng_status_dot_done);
               ThirdIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.primary_text), android.graphics.PorterDuff.Mode.MULTIPLY);
               ThirdName.setTextColor(getResources().getColor(R.color.primary_text));
               ThirdDescription.setTextColor(getResources().getColor(R.color.primary_text));
               ThirdProgress.setBackgroundColor(getResources().getColor(R.color.endprocess));

               FourthDot.setBackgroundResource(R.drawable.ng_status_dot_done);
               FourthIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.primary_text), android.graphics.PorterDuff.Mode.MULTIPLY);
               FourthName.setTextColor(getResources().getColor(R.color.primary_text));
               FourthDescription.setTextColor(getResources().getColor(R.color.primary_text));
               FourthProgress.setBackgroundColor(getResources().getColor(R.color.endprocess));

               FifthDot.setBackgroundResource(R.drawable.ng_status_dot_done);
               FifthIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.primary_text), android.graphics.PorterDuff.Mode.MULTIPLY);
               FifthIcon.setImageResource(R.drawable.ic_cancel);
               FifthName.setTextColor(getResources().getColor(R.color.primary_text));
               FifthName.setText("Orden Cancelada");
               FifthDescription.setTextColor(getResources().getColor(R.color.primary_text));
               FifthDescription.setText("La orden fue cancelada");

               break;
       }


    }


    public void ToolbarConfiguration() {

        Toolbar toolbar = (Toolbar)findViewById(R.id.ng_Toolbar);
        setSupportActionBar(toolbar);
        ImageView ArrowBack = (ImageView)findViewById(R.id.ng_ArrowBack);
        TextView textView = (TextView)findViewById(R.id.ng_Header);
        RelativeLayout NextStep = (RelativeLayout) findViewById(R.id.ng_NextStep);
        textView.setText("Rastrear Pedido");
        NextStep.setVisibility(View.GONE);
        ArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

}
