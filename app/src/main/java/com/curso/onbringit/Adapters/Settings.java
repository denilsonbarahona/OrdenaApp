package com.curso.onbringit.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.curso.onbringit.DataBase.Sql_Query;
import com.curso.onbringit.Model.ApiRequest;
import com.curso.onbringit.R;
import com.curso.onbringit.View.AboutActivity;
import com.curso.onbringit.View.ActivityCancelOrder;
import com.curso.onbringit.View.ActivityProfile;
import com.curso.onbringit.View.ActivityThirdPartyApplications;
import com.curso.onbringit.View.ScheduleListActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by PC-PRAF on 2/9/2017.
 */

public class Settings extends RecyclerView.Adapter<Settings.SettingsHolder> {


    private static ArrayList<String[]> SettingsNameArray;
    private Context context;
    private Activity activity;

    public Settings(Context cntx , Activity activity){
        this.activity = activity;
        context = cntx;
        SettingsNameArray = new ArrayList<>();
        SettingsNameArray.add(new String[]{"1","Servicios","1"});
        SettingsNameArray.add(new String[]{"2","Entregas Programados","0"});
        SettingsNameArray.add(new String[]{"3","Cancelación del Pedido","0"});
        SettingsNameArray.add(new String[]{"4","Cancelación de Articulos en Pedido","0"});
        SettingsNameArray.add(new String[]{"5","Cuenta","1"});
        SettingsNameArray.add(new String[]{"6","Perfil","0"});
        SettingsNameArray.add(new String[]{"7","Eliminar Cuenta","0"});
        SettingsNameArray.add(new String[]{"8","Información y Ayuda","1"});
        SettingsNameArray.add(new String[]{"9","Terminos y Privacidad","0"});
        SettingsNameArray.add(new String[]{"10","Preguntas Frecuentes","0"});
        SettingsNameArray.add(new String[]{"11","Enviar FeedBack","0"});
        SettingsNameArray.add(new String[]{"12","Reportar Error","0"});
        SettingsNameArray.add(new String[]{"13","Sobre ORDENA","0"});
        SettingsNameArray.add(new String[]{"14","Aplicaciones de Terceros","0"});
    }

    @Override
    public Settings.SettingsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ng_setting_item,parent,false);
        SettingsHolder holder = new SettingsHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final Settings.SettingsHolder holder, final int position) {

        holder.layout_app_version.setVisibility(View.GONE);
        if(SettingsNameArray.get(position)[2]=="1"){
            holder.layout_setting_name.setVisibility(View.GONE);
            holder.LayoutHeader.setVisibility(View.VISIBLE);
            holder.headerText.setText(SettingsNameArray.get(position)[1]);
            holder.Item_separator.setVisibility(View.GONE);
        }else{
            holder.layout_setting_name.setVisibility(View.VISIBLE);
            holder.LayoutHeader.setVisibility(View.GONE);
            holder.SettingName.setText(SettingsNameArray.get(position)[1]);
            holder.Item_separator.setVisibility(View.VISIBLE);
        }
        if(SettingsNameArray.size()-1 == position){
            holder.layout_app_version.setVisibility(View.VISIBLE);
        }

        holder.layout_setting_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                switch (SettingsNameArray.get(position)[0])
                {
                    case "2":
                        intent = new Intent(context , ScheduleListActivity.class);
                        context.startActivity(intent);
                        break;

                    case "3":
                        intent = new Intent(context , ActivityCancelOrder.class);
                        intent.putExtra("actividad",1);
                        context.startActivity(intent);
                        break;
                    case "4":
                        intent = new Intent(context , ActivityCancelOrder.class);
                        intent.putExtra("actividad",2);
                        context.startActivity(intent);
                        break;
                    case "6":
                        intent = new Intent(context , ActivityProfile.class);
                        context.startActivity(intent);
                        break;
                    case "7":
                        delete_account();
                        break;
                    case "11":
                        Intent mailClient = new Intent(Intent.ACTION_SEND);
                        mailClient.setType("text/plain");
                        mailClient.putExtra(Intent.EXTRA_EMAIL,new String[]{"supportappordena@gmail.com"});
                        mailClient.putExtra(Intent.EXTRA_SUBJECT,"FeedBack Report");
                        mailClient.putExtra(Intent.EXTRA_TEXT,"Muchas Gracias por tomarte el tiempo para escribirnos. Por favor comparte tu feedback aqui: ");
                        context.startActivity(mailClient);
                        break;
                    case "12":
                        Intent BugReport = new Intent(Intent.ACTION_SEND);
                        BugReport.setType("text/plain");
                        BugReport.putExtra(Intent.EXTRA_EMAIL,new String[]{"supportappordena@gmail.com"});
                        BugReport.putExtra(Intent.EXTRA_SUBJECT,"Bug Report");
                        BugReport.putExtra(Intent.EXTRA_TEXT,"Muchas Gracias por tomarte el tiempo para escribirnos. Por favor comparte tu feedback aqui: ");
                        context.startActivity(BugReport);
                        break;
                    case "13":
                        intent = new Intent(context , AboutActivity.class);
                        context.startActivity(intent);
                        break;
                    case "14":
                        intent = new Intent(context , ActivityThirdPartyApplications.class);
                        context.startActivity(intent);
                        break;
                }
            }
        });
    }


    public void delete_account(){
        Display display = activity.getWindowManager().getDefaultDisplay();
        int mwidth = display.getWidth();
        int mheight = display.getHeight();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View v = LayoutInflater.from(context).inflate(R.layout.ng_popup_delete_account ,null);

        builder.setView(v);
        final AlertDialog dialog = builder.create();
        dialog.show();
        WindowManager.LayoutParams LP = new WindowManager.LayoutParams();
        LP.copyFrom(dialog.getWindow().getAttributes());
        LP.width  = (int)(( mwidth/2) * 2);
        LP.height = (int)(( mheight/2) * 0.9);

        TextView ng_CancelPop = (TextView) v.findViewById(R.id.ng_cancelpop);
        TextView ng_Selected = (TextView) v.findViewById(R.id.ng_schedule_selected);

        ng_CancelPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        ng_Selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = Sql_Query.GetConnection(view.getContext()).getReadableDatabase();
                Sql_Query sql_query = new Sql_Query(db,view.getContext());
                ArrayList<String[]> UserProfile = sql_query.GetUserProfile();
                ApiRequest api = new ApiRequest(view.getContext());

                String user_params = "{'user_email':'"+UserProfile.get(0)[2]+"' , 'user_api_id':'"+UserProfile.get(0)[5]+"' }";

                try {
                    JSONObject jsonObject = new JSONObject(user_params);
                    api.deleteprofile(jsonObject , sql_query, dialog);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        dialog.getWindow().setAttributes(LP);

    }


    @Override
    public int getItemCount() {
        return SettingsNameArray.size();
    }

    public class SettingsHolder extends RecyclerView.ViewHolder {

        public TextView SettingName;
        public LinearLayout LayoutHeader;
        public LinearLayout layout_setting_name;
        public TextView headerText;
        public ImageView Item_separator;
        public LinearLayout layout_app_version;

        public SettingsHolder(View itemView) {
            super(itemView);

            this.SettingName = (TextView) itemView.findViewById(R.id.ng_SettingName);
            this.LayoutHeader = (LinearLayout) itemView.findViewById(R.id.ng_lyheader);
            this.layout_setting_name = (LinearLayout) itemView.findViewById(R.id.ng_lysetting_name);
            this.headerText = (TextView) itemView.findViewById(R.id.ng_header_text_setting);
            this.Item_separator = (ImageView) itemView.findViewById(R.id.ng_Item_separator);
            this.layout_app_version = (LinearLayout) itemView.findViewById(R.id.ng_layout_app_version);
        }
    }
}
