package com.curso.onbringit.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.curso.onbringit.Adapters.ImagesInFolder;
import com.curso.onbringit.Adapters.ItemsInDelivery;
import com.curso.onbringit.Class.LayoutGrid;
import com.curso.onbringit.R;

import java.io.File;

/**
 * Created by PC-PRAF on 22/7/2017.
 */

public class ImageInFolder extends AppCompatActivity
{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageinfolder);
        ToolbarConfiguration(getIntent().getStringExtra("FolderName").toString());

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.ng_rvImagenInFolder);
        LayoutGrid layoutGrid = new LayoutGrid(getApplicationContext(),500);
        recyclerView.setLayoutManager(layoutGrid);
        File file = new File(getIntent().getStringExtra("parent_file")).getParentFile();

        ImagesInFolder imageInFolder = new ImagesInFolder(getApplicationContext(),
                file.getAbsolutePath(),
                getIntent().getStringExtra("item_position") ,
                getIntent().getIntExtra("activity",0));

        recyclerView.setAdapter(imageInFolder);

    }

    public void ToolbarConfiguration(String FolderName) {

        Toolbar toolbar = (Toolbar)findViewById(R.id.ng_Toolbar);
        setSupportActionBar(toolbar);

        ImageView ArrowBack = (ImageView)findViewById(R.id.ng_ArrowBack);
        RelativeLayout NextStep = (RelativeLayout) findViewById(R.id.ng_NextStep);
        TextView textView = (TextView)findViewById(R.id.ng_Header);
        textView.setText(FolderName);
        if(getIntent().getIntExtra("activity",0)== 5 || getIntent().getIntExtra("activity",0)==6){
            NextStep.setVisibility(View.GONE);
        }else{
            NextStep.setVisibility(View.VISIBLE);
        }

        ArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for( int x = 0 ; x<ItemsInDelivery.AttachInItem.size(); x++){
                    if(ItemsInDelivery.AttachInItem.get(x).get(0)==getIntent().getStringExtra("item_position")) {
                       ItemsInDelivery.AttachInItem.remove(x);
                    }
                }
                onBackPressed();
            }
        });

        NextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( getIntent().getIntExtra("activity",0)== 1){
                    Intent intent = new Intent(view.getContext() , ExpdeliveryActivity.class);
                    view.getContext().startActivity(intent);

                }else  if( getIntent().getIntExtra("activity",0)== 2){
                    Intent intent = new Intent(view.getContext() , ScheduleActivity.class);
                    view.getContext().startActivity(intent);
                }else if( getIntent().getIntExtra("activity",0)== 4){
                    Intent intent = new Intent(view.getContext() , ItemScheduleActivity.class);
                    view.getContext().startActivity(intent);
                }

            }
        });

    }

}
