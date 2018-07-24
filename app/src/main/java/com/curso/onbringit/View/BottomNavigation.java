package com.curso.onbringit.View;

import android.app.ActivityManager;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.curso.onbringit.DataBase.Sql_Query;
import com.curso.onbringit.Fragment.ServicesActivity;
import com.curso.onbringit.Fragment.OrdersActivity;
import com.curso.onbringit.Fragment.SettingActivity;
import com.curso.onbringit.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.List;

/**
 * Created by PC-PRAF on 9/7/2017.
 */

public class BottomNavigation extends AppCompatActivity
{

    @Override
    protected void onStart() {
        super.onStart();

        SQLiteDatabase db = Sql_Query.GetConnection(this.getApplicationContext()).getReadableDatabase();
        Sql_Query sql_query = new Sql_Query(db,this.getApplicationContext());

        if(sql_query.FirstTime()){
            Intent intent = new Intent(getApplicationContext() , Activity_splash_screen.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(intent);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        ImageView imvArrowBack = (ImageView) findViewById(R.id.ng_ArrowBack);
        imvArrowBack.setVisibility(View.GONE);
        TextView txtHeader = (TextView) findViewById(R.id.ng_Header);
        RelativeLayout NextStep = (RelativeLayout) findViewById(R.id.ng_NextStep);
        txtHeader.setText("ORDENA");
        NextStep.setVisibility(View.GONE);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(100,0,0,0);
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        txtHeader.setLayoutParams(layoutParams);
        txtHeader.setTextSize(18);


        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

                if(tabId == R.id.nav_services )
                {
                    ServicesActivity servicesActivity = new ServicesActivity();
                    getSupportFragmentManager().beginTransaction().replace(R.id.ng_container,servicesActivity)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null)
                            .commit();
                } else if(tabId == R.id.nav_tracking )
                {
                    OrdersActivity trackingActivity = new OrdersActivity();
                    getSupportFragmentManager().beginTransaction().replace(R.id.ng_container, trackingActivity)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null)
                            .commit();
                }else if(tabId == R.id.nav_more )
                {
                    SettingActivity SettingActivity = new SettingActivity();
                    getSupportFragmentManager().beginTransaction().replace(R.id.ng_container,SettingActivity)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null)
                            .commit();
                }


            }
        });

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Intent intent2 = new Intent(getApplicationContext(),NgFinishMessage.class);
        if(intent.getIntExtra("Activity",0) == 1 )
        {
            intent2.putExtra("from",1);
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(intent2);
        } else if(intent.getIntExtra("Activity",0) == 2){
            intent2.putExtra("from",2);
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(intent2);
        }else if(intent.getIntExtra("Activity",0) == 3){
            intent2.putExtra("from",3);
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(intent2);
        }
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
