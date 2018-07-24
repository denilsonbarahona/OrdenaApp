package com.curso.onbringit.View;


import android.content.Intent;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.curso.onbringit.R;
import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by PC-PRAF on 26/11/2017.
 */

public class ActivitySignUp extends AppCompatActivity {

    private ImageView AddImage;
    private int VisibleDeleteImageProfile = -1;
    private TextView TxtName ;
    private TextView TxtPhone;
    private TextView TxtEmail;
    private int imageProfile = 0;
    private String image_encode="";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ToolbarConfiguration();

        AddImage = (ImageView)findViewById(R.id.ng_profile);
        TxtName = (TextView) findViewById(R.id.ng_nameprofile);
        TxtPhone = (TextView) findViewById(R.id.ng_phoneprofile);
        TxtEmail = (TextView) findViewById(R.id.ng_emailprofile);

        FrameLayout attach_profile = (FrameLayout) findViewById(R.id.ng_attache_profile_image);
        attach_profile.setOnClickListener(new View.OnClickListener() {
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
                        intent.putExtra("activity",5);
                        v.getContext().startActivity(intent);
                        dialog.dismiss();
                    }
                });

                DeleteProfileImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AddImage.setImageDrawable(v.getContext().getResources().getDrawable(R.drawable.ic_profile));
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

                AddImage.setImageDrawable(roundedBitmapDrawable);
                VisibleDeleteImageProfile = 1;

                this.imageProfile = 1;
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

            AddImage.setImageDrawable(roundedBitmapDrawable);
            VisibleDeleteImageProfile = 1;

            this.imageProfile = 1;
        }
    }



    public void ToolbarConfiguration() {

        Toolbar toolbar = (Toolbar)findViewById(R.id.ng_Toolbar);
        setSupportActionBar(toolbar);
        ImageView ArrowBack = (ImageView)findViewById(R.id.ng_ArrowBack);
        TextView textView = (TextView)findViewById(R.id.ng_Header);
        RelativeLayout NextStep = (RelativeLayout) findViewById(R.id.ng_NextStep);
        TextView NextText = (TextView) findViewById(R.id.ng_NextText);

        ArrowBack.setVisibility(View.VISIBLE);
        ArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        textView.setText("ORDENA");

        NextStep.setVisibility(View.VISIBLE);
        NextText.setText("CONTINUAR");
        NextStep.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                if(!TxtName.getText().toString().equals("")  &&
                   !TxtPhone.getText().toString().equals("") &&
                   !TxtPhone.getText().toString().equals("") )
                {
                    if(Patterns.EMAIL_ADDRESS.matcher(TxtEmail.getText().toString()).matches())
                    {
                        if(TxtPhone.getText().length()==8){
                            Intent intent = new Intent(view.getContext() , AddressAdd.class);
                            intent.putExtra("name" ,  TxtName.getText().toString() );
                            intent.putExtra("phone",  TxtPhone.getText().toString() );
                            intent.putExtra("email",  TxtEmail.getText().toString() );
                            intent.putExtra("first_log", 1);
                            intent.putExtra("image_profile_status",imageProfile);
                            intent.putExtra("image_encode",image_encode);
                            view.getContext().startActivity(intent);
                        }else{
                            Toast.makeText(view.getContext(),"Tienes que ingresar un número de teléfono valido",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(view.getContext(),"Tienes que ingresar tu Email",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
}
