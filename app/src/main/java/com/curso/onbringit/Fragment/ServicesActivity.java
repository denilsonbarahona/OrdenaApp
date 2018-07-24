package com.curso.onbringit.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.curso.onbringit.R;
import com.curso.onbringit.View.ATMMovil;
import com.curso.onbringit.View.Activity_order_filters;
import com.curso.onbringit.View.ExpdeliveryActivity;
import com.curso.onbringit.View.ScheduleActivity;

/**
 * Created by PC-PRAF on 9/7/2017.
 */

public class ServicesActivity extends android.support.v4.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View RootView = inflater.inflate(R.layout.activity_services,container,false);


        Button btnServicesExpress = (Button)RootView.findViewById(R.id.ng_btn_express_services);
        Button btnATMMovil = (Button)RootView.findViewById(R.id.ng_btn_atm_services);
        Button btnSchedule = (Button)RootView.findViewById(R.id.ng_btn_schedule_services);

        btnServicesExpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Activity_order_filters.class );
                view.getContext().startActivity(intent);
            }
        });

        btnATMMovil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ATMMovil.class);
                view.getContext().startActivity(intent);
            }
        });

        btnSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ScheduleActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        return RootView;

    }
}
