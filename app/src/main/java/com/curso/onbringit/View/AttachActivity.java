package com.curso.onbringit.View;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.curso.onbringit.Adapters.ImagesFolders;
import com.curso.onbringit.Class.LayoutGrid;
import com.curso.onbringit.R;

/**
 * Created by PC-PRAF on 9/7/2017.
 **/

public class AttachActivity extends AppCompatActivity
{

    RecyclerView recyclerView;
    private Boolean grantedPermission = false;
    private static int READ_STORAGE = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attach);
        ToolbarConfiguration();
        GrantPermissionStorage();
    }

    private void GettingListFolders(){

        recyclerView = (RecyclerView)findViewById(R.id.ng_rvFolderImage);
        LayoutGrid layoutGrid = new LayoutGrid(getApplicationContext(),500);
        recyclerView.setLayoutManager(layoutGrid);

        ImagesFolders imagesFolders = new ImagesFolders(getApplicationContext() ,
                getIntent().getStringExtra("item_position") ,
                getIntent().getIntExtra("activity",0));
        recyclerView.setAdapter(imagesFolders);
    }

    public void ToolbarConfiguration() {

        Toolbar toolbar = (Toolbar)findViewById(R.id.ng_Toolbar);
        setSupportActionBar(toolbar);

        ImageView ArrowBack = (ImageView)findViewById(R.id.ng_ArrowBack);
        RelativeLayout NextStep = (RelativeLayout) findViewById(R.id.ng_NextStep);
        TextView textView = (TextView)findViewById(R.id.ng_Header);
        textView.setText("Adjuntar Imagen");
        NextStep.setVisibility(View.GONE);
        ArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void GrantPermissionStorage(){

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||

            ContextCompat.checkSelfPermission(this ,
               Manifest.permission.READ_EXTERNAL_STORAGE)
               != PackageManager.PERMISSION_GRANTED  ) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                ActivityCompat.shouldShowRequestPermissionRationale(this ,
                   Manifest.permission.READ_EXTERNAL_STORAGE ) ) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE ,
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        READ_STORAGE);
            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE ,
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        READ_STORAGE);
            }

        }else {
            grantedPermission = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 3: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        grantedPermission = true;
                } else {
                    GrantPermissionStorage();
                }
            }
        }
        return;
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(grantedPermission){
            GettingListFolders();
        }

    }
}
