package com.curso.onbringit.Class;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.jaredrummler.materialspinner.MaterialSpinner;

/**
 * Created by PC-PRAF on 13/8/2017.
 */


public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private MaterialSpinner DateSpinner;
    private MaterialSpinner HourSpinner;
    private int Type=0;


    public void SetSpinnerDate(View Date , int type){
        this.DateSpinner = (MaterialSpinner) Date;
        this.Type = type;
    }

    public void SetSpinnerHour(View Hour , int type){
        this.HourSpinner = (MaterialSpinner) Hour;
        this.Type = type;
    }


    private Dialog CreateDialogDate(){

        Calendar calendar = Calendar.getInstance();
        int Year = calendar.YEAR;
        int Month = calendar.MONTH;
        int Day = calendar.DAY_OF_MONTH;

        DatePickerDialog dialog = new DatePickerDialog(getActivity(),this,Year,Month,Day);
        dialog.getDatePicker().setMinDate(new Date().getTime());

        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 30);
        dt = c.getTime();
        dialog.getDatePicker().setMaxDate(dt.getTime());

        return  dialog;
    }

    private Dialog CreateDialogTime(){

        Calendar calendar = Calendar.getInstance();
        int Hour = calendar.HOUR;
        int Minutes = calendar.MINUTE;

        TimePickerDialog dialog = new TimePickerDialog(getActivity(),this,Hour,Minutes,true);


        return  dialog;
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return  (this.Type==1)?CreateDialogDate():CreateDialogTime();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        DateSpinner.setText(day+"/"+(month+1)+"/"+year);
    }


    @Override
    public void onTimeSet(TimePicker timePicker, int Hour, int Minutes) {
        HourSpinner.setText(Hour+" : "+Minutes);
    }
}
