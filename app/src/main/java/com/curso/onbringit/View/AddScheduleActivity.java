package com.curso.onbringit.View;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.curso.onbringit.Class.DateDialog;
import com.curso.onbringit.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

/**
 * Created by PC-PRAF on 12/8/2017.
 */

public class AddSchedureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
        ToolbarConfiguration();

        MaterialSpinner ng_scheduleSpinner = (MaterialSpinner ) findViewById(R.id.ng_schedule_spinner);

        final MaterialSpinner ng_daySpinner = (MaterialSpinner) findViewById(R.id.ng_dayspinner);
        final MaterialSpinner ng_HourSpinner = (MaterialSpinner) findViewById(R.id.ng_hourSpinner);

        ng_scheduleSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Display display = getWindowManager().getDefaultDisplay();
                int mwidth = display.getWidth();
                int mheight = display.getHeight();

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                View v = LayoutInflater.from(view.getContext()).inflate(R.layout.ng_popup_schedule ,null);

                final RadioButton ng_rbDaily = (RadioButton) v.findViewById(R.id.ng_schedule_daily);
                final RadioButton ng_rbWeekly = (RadioButton) v.findViewById(R.id.ng_schedule_weekly);
                final RadioButton ng_rbMonthly = (RadioButton) v.findViewById(R.id.ng_schedule_monthly);
                final RadioButton ng_rbOthers = (RadioButton) v.findViewById(R.id.ng_schedule_other);
                final EditText ng_weeks = (EditText) v.findViewById(R.id.ng_own_schedule);


                ng_rbDaily.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(ng_rbDaily.isChecked()){
                            ng_rbOthers.setChecked(false);
                            ng_rbWeekly.setChecked(false);
                            ng_rbMonthly.setChecked(false);
                            ng_weeks.setEnabled(true);
                        }
                    }
                });

                ng_rbWeekly.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(ng_rbWeekly.isChecked()){
                            ng_rbOthers.setChecked(false);
                            ng_rbMonthly.setChecked(false);
                            ng_rbDaily.setChecked(false);
                            ng_weeks.setEnabled(true);
                        }
                    }
                });


                ng_rbMonthly.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(ng_rbMonthly.isChecked()){
                            ng_rbOthers.setChecked(false);
                            ng_rbDaily.setChecked(false);
                            ng_rbWeekly.setChecked(false);
                            ng_weeks.setEnabled(true);
                        }
                    }
                });

                ng_rbOthers.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(ng_rbOthers.isChecked()){
                            ng_weeks.setEnabled(true);
                            ng_rbDaily.setSelected(false);
                            ng_rbDaily.setChecked(false);
                            ng_rbWeekly.setSelected(false);
                            ng_rbWeekly.setChecked(false);
                            ng_rbMonthly.setSelected(false);
                            ng_rbMonthly.setChecked(false);
                        }else{
                            ng_weeks.setEnabled(false);
                        }
                    }
                });

                builder.setView(v);
                AlertDialog dialog = builder.create();
                dialog.show();

                WindowManager.LayoutParams LP = new WindowManager.LayoutParams();
                LP.copyFrom(dialog.getWindow().getAttributes());
                LP.width  = (int)(( mwidth / 2 ) * 1.6);
                LP.height = (int)(( mheight /2 ) * 1 );


                dialog.getWindow().setAttributes(LP);

            }
        });

        ng_daySpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateDialog dialog = new DateDialog();
                dialog.SetSpinnerDate(view,1);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                dialog.show(fragmentTransaction,"DatePicker");
                ng_daySpinner.collapse();
            }
        });

        ng_HourSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateDialog dialog = new DateDialog();
                dialog.SetSpinnerHour(view,2);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                dialog.show(fragmentTransaction,"DatePicker");
                ng_HourSpinner.collapse();
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
                Intent intent = new Intent(view.getContext(),PaymentMethod.class);
                view.getContext().startActivity(intent);
            }
        });

    }
}

