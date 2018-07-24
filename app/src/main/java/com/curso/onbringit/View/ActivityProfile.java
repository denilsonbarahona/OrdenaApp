package com.curso.onbringit.View;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.curso.onbringit.DataBase.Sql_Query;
import com.curso.onbringit.Model.ApiRequest;
import com.curso.onbringit.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by PC-PRAF on 26/11/2017.
 */

public class ActivityProfile extends AppCompatActivity
{
    private ImageView imageProfile;
    private EditText  UserName;
    private EditText  UserPhone;
    private EditText  UserEmail;
    private ImageView AddImage;
    private int VisibleDeleteImageProfile = -1;
    private String image_encode="";
    private int ChangeimageProfile = 0;
    private int User_Api_Id = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ToolbarConfiguration();

        SQLiteDatabase db = Sql_Query.GetConnection(this.getApplicationContext()).getReadableDatabase();
        Sql_Query sql_query = new Sql_Query(db,this.getApplicationContext());

        ArrayList<String[]> UserProfile;
        UserProfile = sql_query.GetUserProfile();

        this.imageProfile = (ImageView) findViewById(R.id.ng_image_profile);
        this.UserName = (EditText) findViewById(R.id.ng_nameprofile);
        this.UserPhone = (EditText) findViewById(R.id.ng_phoneprofile);
        this.UserEmail = (EditText) findViewById(R.id.ng_emailprofile);
        this.AddImage = (ImageView) findViewById(R.id.ng_add_image_profile);
        this.User_Api_Id =Integer.valueOf( UserProfile.get(0)[5] );

        Glide.with(this).load(UserProfile.get(0)[0]+"/"+UserProfile.get(0)[6]).asBitmap()
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .skipMemoryCache(true)
                        .into(new BitmapImageViewTarget(this.imageProfile){
            @Override
            protected void setResource(Bitmap resource) {
                super.setResource(resource);
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageProfile.setImageDrawable(circularBitmapDrawable);
            }
        });

        this.UserName.setText(UserProfile.get(0)[1]);
        this.UserEmail.setText(UserProfile.get(0)[2]);
        this.UserPhone.setText(UserProfile.get(0)[3]);
        this.AddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                View view = getLayoutInflater().inflate(R.layout.ng_popup_photo, null);

                TextView OpenCamera = (TextView) view.findViewById(R.id.camera_up);
                TextView OpenGallery = (TextView) view.findViewById(R.id.gallery_up);
                TextView DeleteProfileImage = (TextView) view.findViewById(R.id.delete_up);

                if(VisibleDeleteImageProfile == 1){
                    DeleteProfileImage.setVisibility(View.VISIBLE);
                }

                builder.setView(view);
                final AlertDialog dialog = builder.create();
                dialog.show();

                OpenGallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext() , AttachActivity.class);
                        intent.putExtra("activity",6);
                        v.getContext().startActivity(intent);
                        dialog.dismiss();
                    }
                });

                DeleteProfileImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageProfile.setImageDrawable(v.getContext().getResources().getDrawable(R.drawable.ic_profile));
                        dialog.dismiss();
                        VisibleDeleteImageProfile = -1;
                    }
                });
                OpenCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent IntentOpenCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if(IntentOpenCamera.resolveActivity(getPackageManager())!= null){
                            startActivityForResult(IntentOpenCamera , 1);
                            dialog.dismiss();
                        }
                    }
                });

            }
        });
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if(intent.getStringExtra("image_uri")!=null){
            try{
                File image = new File(intent.getStringExtra("image_uri"));
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                Bitmap imageBitmap = BitmapFactory.decodeFile(image.getAbsolutePath(),bmOptions);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                image_encode = Base64.encodeToString(byteArray , Base64.DEFAULT);

                Bitmap b = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                Bitmap bitmap = Bitmap.createScaledBitmap(b, AddImage.getHeight(), AddImage.getWidth(), false);

                RoundedBitmapDrawable roundedBitmapDrawable = null;
                roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(),bitmap);
                roundedBitmapDrawable.setCornerRadius(bitmap.getHeight());

                imageProfile.setImageDrawable(roundedBitmapDrawable);
                VisibleDeleteImageProfile = 1;

                this.ChangeimageProfile = 1;
            }catch (Exception e){

            }
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            image_encode = Base64.encodeToString(byteArray , Base64.DEFAULT);

            Bitmap b = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            Bitmap bitmap = Bitmap.createScaledBitmap(b, AddImage.getHeight(), AddImage.getWidth(), false);

            RoundedBitmapDrawable roundedBitmapDrawable = null;
            roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(),bitmap);
            roundedBitmapDrawable.setCornerRadius(bitmap.getHeight());

            imageProfile.setImageDrawable(roundedBitmapDrawable);
            VisibleDeleteImageProfile = 1;

            this.ChangeimageProfile = 1;
        }
    }

    public void ToolbarConfiguration() {

        Toolbar toolbar = (Toolbar)findViewById(R.id.ng_Toolbar);
        setSupportActionBar(toolbar);
        ImageView ArrowBack = (ImageView)findViewById(R.id.ng_ArrowBack);
        TextView textView = (TextView)findViewById(R.id.ng_Header);
        RelativeLayout NextStep = (RelativeLayout) findViewById(R.id.ng_NextStep);
        TextView NextText = (TextView) findViewById(R.id.ng_NextText);

        textView.setText("Perfil");
        NextStep.setVisibility(View.VISIBLE);
        NextText.setText("FINALIZAR");
        ArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        NextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!UserName.getText().toString().equals("")  &&
                        !UserPhone.getText().toString().equals("") &&
                        !UserEmail.getText().toString().equals("") )
                {
                    if (Patterns.EMAIL_ADDRESS.matcher(UserEmail.getText().toString()).matches()) {
                        ApiRequest apiRequest = new ApiRequest(view.getContext());
                        String user_profile ="{ 'photo_updated':'"+String.valueOf(ChangeimageProfile)+"' , "
                                             +" 'photo':'"+image_encode+"' , "
                                             +" 'user_name':'"+UserName.getText().toString()+"' , "
                                             +" 'user_phone':'"+UserPhone.getText().toString()+"', "
                                             +" 'user_email':'"+UserEmail.getText().toString()+"',"
                                             +" 'user_api_id':'"+String.valueOf(User_Api_Id)+"' }";

                        try {
                            JSONObject obj = new JSONObject(user_profile);

                            SQLiteDatabase db = Sql_Query.GetConnection(view.getContext()).getReadableDatabase();
                            Sql_Query sql_query = new Sql_Query(db,view.getContext());

                            sql_query.UpdateUserInformation(UserName.getText().toString(),
                                    UserEmail.getText().toString(),
                                    UserPhone.getText().toString(),
                                    ChangeimageProfile ,
                                    User_Api_Id);

                            apiRequest.update_user_profile(obj);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        });
    }



}
