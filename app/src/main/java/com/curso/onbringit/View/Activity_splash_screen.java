package com.curso.onbringit.View;

import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.curso.onbringit.R;

import java.util.List;

/**
 * Created by PC-PRAF on 14/1/2018.
 */

public class Activity_splash_screen extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        TextView terms = (TextView)findViewById(R.id.ng_terms_condition);
        terms.setPaintFlags(terms.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);


        Button ng_accept = (Button) findViewById(R.id.ng_accept_terms);
        ng_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), ActivitySignUp.class);
                view.getContext().startActivity(intent);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.AppTask> appTasks = activityManager.getAppTasks();
        for (ActivityManager.AppTask task : appTasks) {
            task.finishAndRemoveTask();
        }
    }


}
