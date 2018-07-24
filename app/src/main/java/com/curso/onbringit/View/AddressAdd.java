package com.curso.onbringit.View;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.curso.onbringit.DataBase.Sql_Query;
import com.curso.onbringit.Model.ApiRequest;
import com.curso.onbringit.R;

/**
 * Created by PC-PRAF on 30/7/2017.
 */

public class AddressAdd extends AppCompatActivity
{

    private EditText AddressName;
    private EditText AddressAdded;
    private Sql_Query query;
    private CheckBox AddressDefault;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addressadd);
        ToolbarConfiguration();

        SQLiteDatabase db = Sql_Query.GetConnection(this.getApplicationContext()).getReadableDatabase();

        AddressName = (EditText)findViewById(R.id.ng_AddressNameAdded);
        AddressAdded = (EditText) findViewById(R.id.ng_AddressAdded);
        AddressDefault = (CheckBox) findViewById(R.id.ng_default_address);

        if(getIntent().getIntExtra("first_log",2) == 1){
            AddressDefault.setVisibility(View.GONE);
        }else{
            AddressDefault.setVisibility(View.VISIBLE);
        }

        this.query =  new Sql_Query(db,this.getApplicationContext());
    }



    public void ToolbarConfiguration()
    {
        Toolbar toolbar = (Toolbar)findViewById(R.id.ng_Toolbar);
        setSupportActionBar(toolbar);
        ImageView ArrowBack = (ImageView)findViewById(R.id.ng_ArrowBack);
        RelativeLayout NextStep = (RelativeLayout) findViewById(R.id.ng_NextStep);
        TextView textView = (TextView)findViewById(R.id.ng_Header);
        textView.setText("Nueva Dirección");
        NextStep.setVisibility(View.VISIBLE);
        ArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        NextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                if(!AddressAdded.getText().toString().equals("")  && !AddressName.getText().toString().equals(""))
                {
                   if(getIntent().getIntExtra("first_log",0)==1){

                       query.SaveProfile(getIntent().getStringExtra("name") , getIntent().getStringExtra("phone") ,
                               getIntent().getStringExtra("email"));

                       String usuario_email = query.getUsuarioEmail();

                       ApiRequest apiRequest = new ApiRequest(view.getContext());

                       if( apiRequest.get_user_api_id(  usuario_email , getIntent().getStringExtra("name") ,
                                                    getIntent().getStringExtra("phone") ,
                                                    getIntent().getStringExtra("image_encode") ,
                                                    getIntent().getIntExtra("image_profile_status",0) )
                               )
                       {
                           apiRequest.add_address(AddressName.getText().toString() ,
                                                  AddressAdded.getText().toString() ,
                                                 (AddressDefault.isChecked() || getIntent().getIntExtra("first_log",0) == 1 )?1:0
                                   );
                       }


                       Intent intent = new Intent(view.getContext(),BottomNavigation.class);
                       view.getContext().startActivity(intent);
                   }else {
                       ApiRequest apiRequest = new ApiRequest(view.getContext());
                       if(      apiRequest.add_address(AddressName.getText().toString() ,
                                   AddressAdded.getText().toString() ,
                                   (AddressDefault.isChecked() || getIntent().getIntExtra("first_log",0) == 1 )?1:0
                                )
                         ){
                               Intent intent = new Intent(view.getContext(),ChangeAddressActivity.class);
                               intent.putExtra("Added",1);
                               view.getContext().startActivity(intent);
                          }


                   }
                }else {
                    Snackbar.make (view , "Tienes que registrar una dirección donde te realizaremos las entregas", Snackbar.LENGTH_LONG ).show();
                }
            }
        });
    }

}
