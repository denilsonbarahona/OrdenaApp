package com.curso.onbringit.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.curso.onbringit.Model.ApiRequest;
import com.curso.onbringit.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

/**
 * Created by PC-PRAF on 3/9/2017.
 */

public class EditScheduleActivity extends AppCompatActivity
{

    private MaterialSpinner ng_scheduleSpinner;
    private EditText ng_delivery_to;
    private EditText ng_phone_number;
    private String ScheduleSelected = "";
    private Boolean OwnSchedule = false;
    public static int ScheduleOption = 0;
    public static int NumberDaysOwn;
    private TextView ng_Address;
    private TextView ng_infodelivery;
    public static int AddressApiID;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_schedule);
        ToolbarConfiguration();

        ng_scheduleSpinner = (MaterialSpinner)findViewById(R.id.ng_schedule_spinner);
        ng_delivery_to = (EditText) findViewById(R.id.ng_item_order_to);
        ng_phone_number = (EditText) findViewById(R.id.ng_order_phonenumber);
        ng_Address = (TextView)findViewById(R.id.ng_address);
        ng_infodelivery = (TextView)findViewById(R.id.ng_DeliveryInformation);

        LinearLayout ng_ChangeAddress = (LinearLayout) findViewById(R.id.ng_ChangeAddress);
        ng_ChangeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ChangeAddressActivity.class);
                intent.putExtra("Activity",4);
                view.getContext().startActivity(intent);
            }
        });



        ApiRequest apiRequest = new ApiRequest(this.getApplicationContext());
        apiRequest.get_schedule_info(  ng_delivery_to  ,ng_phone_number , ng_scheduleSpinner , ng_Address ,
                                       ng_infodelivery ,getIntent().getStringExtra("OrdenId")  );

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
                            ng_weeks.setEnabled(false);
                            ScheduleOption = 1;
                            EditScheduleActivity.this.ScheduleSelected = ng_rbDaily.getText().toString();
                            EditScheduleActivity.this.OwnSchedule = false;
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
                            ng_weeks.setEnabled(false);
                            ScheduleOption = 2;
                            EditScheduleActivity.this.ScheduleSelected = ng_rbWeekly.getText().toString();
                            EditScheduleActivity.this.OwnSchedule = false;
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
                            ng_weeks.setEnabled(false);
                            ScheduleOption = 3;
                            EditScheduleActivity.this.ScheduleSelected = ng_rbMonthly.getText().toString();
                            EditScheduleActivity.this.OwnSchedule = false;
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
                            ScheduleOption = 4;
                            EditScheduleActivity.this.OwnSchedule = true;
                        }else{
                            ng_weeks.setEnabled(false);
                            EditScheduleActivity.this.OwnSchedule = false;
                        }
                    }
                });


                builder.setView(v);
                final AlertDialog dialog = builder.create();
                dialog.show();

                WindowManager.LayoutParams LP = new WindowManager.LayoutParams();
                LP.copyFrom(dialog.getWindow().getAttributes());
                LP.width  = (int)(( mwidth / 2 ) * 1.8);
                LP.height = (int)(( mheight /2 ) * 1.2 );

                TextView ng_CancelPop = (TextView) v.findViewById(R.id.ng_cancelpop);
                TextView ng_Selected = (TextView) v.findViewById(R.id.ng_schedule_selected);

                ng_Selected.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(ScheduleOption!=0){
                            if(EditScheduleActivity.this.OwnSchedule){
                                if(ng_weeks.getText().toString().trim().length()>0){
                                    ng_scheduleSpinner.setText("Cada "+ng_weeks.getText().toString() +" Semanas");
                                    NumberDaysOwn = Integer.parseInt( ng_weeks.getText().toString());
                                    dialog.cancel();
                                }else{
                                    Toast.makeText(getApplicationContext(),"Indique la cantidad de semanas",Toast.LENGTH_LONG).show();
                                }
                            }else{
                                ng_scheduleSpinner.setText(EditScheduleActivity.this.ScheduleSelected);
                                dialog.cancel();
                            }
                            EditScheduleActivity.this.OwnSchedule=false;
                        }else{
                            Toast.makeText(getApplicationContext(),"seleccione una opción",Toast.LENGTH_LONG).show();
                        }
                    }
                });

                ng_CancelPop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });

                dialog.getWindow().setAttributes(LP);
                ng_scheduleSpinner.collapse();
            }
        });
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent.getStringExtra("AddressName")!= null){
            ng_Address.setText(intent.getStringExtra("AddressName"));
            ng_infodelivery.setText(intent.getStringExtra("Address"));
            AddressApiID = Integer.parseInt(intent.getStringExtra("AddressId"));
        }
    }

    public void ToolbarConfiguration() {

        Toolbar toolbar = (Toolbar)findViewById(R.id.ng_Toolbar);
        setSupportActionBar(toolbar);
        ImageView ArrowBack = (ImageView)findViewById(R.id.ng_ArrowBack);
        TextView textView = (TextView)findViewById(R.id.ng_Header);
        RelativeLayout NextStep = (RelativeLayout) findViewById(R.id.ng_NextStep);
        textView.setText("Orden Número # "+getIntent().getStringExtra("OrdenNumber"));
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

            int daysSchedule = 0;
            switch (ScheduleOption){
                case 1: daysSchedule = ScheduleOption;
                    break;
                case 2: daysSchedule = 7;
                    break;
                case 3: daysSchedule = 30;
                    break;
                case 4: daysSchedule = NumberDaysOwn *7;
                    break;

            }

            String deliveryInformation = " { \"schedule\": \"" + String.valueOf(daysSchedule)+"\" , "      +
                                         "   \"delivery_to\": \""+ ng_delivery_to.getText().toString()+"\" ,"+
                                         "   \"phone_to\": \""+ ng_phone_number.getText().toString()+"\" ,"   +
                                         "   \"AddressID\":  \""+ String.valueOf(AddressApiID)+"\" }";

            Intent intent = new Intent(view.getContext() , ItemScheduleActivity.class);
            intent.putExtra("OrdenNumber",getIntent().getStringExtra("OrdenNumber"));
            intent.putExtra("OrdenId" , getIntent().getStringExtra("OrdenId"));
            intent.putExtra("info_delivery" , deliveryInformation);
            view.getContext().startActivity(intent);
            }
        });

    }
}
