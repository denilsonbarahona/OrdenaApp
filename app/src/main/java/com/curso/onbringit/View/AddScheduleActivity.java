package com.curso.onbringit.View;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import com.curso.onbringit.Class.DateDialog;
import com.curso.onbringit.DataBase.SqlConnection;
import com.curso.onbringit.DataBase.Sql_Query;
import com.curso.onbringit.Model.ApiRequest;
import com.curso.onbringit.R;
import com.jaredrummler.materialspinner.MaterialSpinner;
import java.util.ArrayList;

/**
 * Created by PC-PRAF on 12/8/2017.
 */

public class AddScheduleActivity extends AppCompatActivity {

    private TextView AddressSelected;
    private TextView DeliveryInformation;
    private String ScheduleSelected="";
    private Boolean OwnSchedule=false;
    private MaterialSpinner ng_daySpinner;
    private MaterialSpinner ng_scheduleSpinner;
    private MaterialSpinner ng_HourSpinner;
    private EditText ng_delivery_to;
    private EditText ng_phone_number;
    private ArrayList<String[]> Default_Address = new ArrayList<>();
    public static String dateFirtsDelivery;
    public static String TimeFirtsDelivery;
    public static int ScheduleOption;
    public static String DeliveryTo;
    public static String phoneNumber;
    public static String Address;
    public static int NumberDaysOwn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
        ToolbarConfiguration();

        ng_scheduleSpinner = (MaterialSpinner ) findViewById(R.id.ng_schedule_spinner);
        ng_daySpinner = (MaterialSpinner) findViewById(R.id.ng_dayspinner);
        ng_HourSpinner = (MaterialSpinner) findViewById(R.id.ng_hourSpinner);

        SqlConnection connection = new SqlConnection(this.getApplicationContext() , "ORDENADB",null , 1);
        SQLiteDatabase sqLiteDatabase = connection.getReadableDatabase();

        Sql_Query query = new Sql_Query(sqLiteDatabase , null);

        this.Default_Address = query.GetAddressDefault();

        AddressSelected = (TextView) findViewById(R.id.ng_AddressName);
        DeliveryInformation = (TextView) findViewById(R.id.ng_DeliveryInformation);

        ng_delivery_to = (EditText) findViewById(R.id.ng_item_order_to);
        ng_phone_number = (EditText) findViewById(R.id.ng_order_phonenumber);

        if(this.Default_Address.size()>0){
            AddressSelected.setText(this.Default_Address.get(0)[0]);
            DeliveryInformation.setText(this.Default_Address.get(0)[1]);
        }else{
            AddressSelected.setText("");
            DeliveryInformation.setText("");
        }

        LinearLayout ng_ChangeAddress = (LinearLayout) findViewById(R.id.ng_ChangeAddress);
        ng_ChangeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ChangeAddressActivity.class);
                intent.putExtra("Activity",3);
                view.getContext().startActivity(intent);
            }
        });

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
                            AddScheduleActivity.this.ScheduleSelected = ng_rbDaily.getText().toString();
                            AddScheduleActivity.this.OwnSchedule = false;
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
                            AddScheduleActivity.this.ScheduleSelected = ng_rbWeekly.getText().toString();
                            AddScheduleActivity.this.OwnSchedule = false;
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
                            AddScheduleActivity.this.ScheduleSelected = ng_rbMonthly.getText().toString();
                            AddScheduleActivity.this.OwnSchedule = false;
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
                            AddScheduleActivity.this.OwnSchedule = true;
                        }else{
                            ng_weeks.setEnabled(false);
                            AddScheduleActivity.this.OwnSchedule = false;
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
                        if(AddScheduleActivity.this.OwnSchedule){
                            ng_scheduleSpinner.setText("Cada "+ng_weeks.getText().toString() +" Semanas");
                            NumberDaysOwn = Integer.parseInt( ng_weeks.getText().toString());
                        }else{
                            ng_scheduleSpinner.setText(AddScheduleActivity.this.ScheduleSelected);
                        }
                        AddScheduleActivity.this.OwnSchedule=false;
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
                ng_scheduleSpinner.collapse();

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

                dateFirtsDelivery = ng_daySpinner.getText().toString();
                TimeFirtsDelivery = ng_HourSpinner.getText().toString();
                DeliveryTo  = ng_delivery_to.getText().toString();
                phoneNumber = ng_phone_number.getText().toString();
                Address = Default_Address.get(0)[2].toString();

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

                ApiRequest request = new ApiRequest(view.getContext());
                request.Send_ScheduleOrden(daysSchedule);

            }
        });

    }
}

