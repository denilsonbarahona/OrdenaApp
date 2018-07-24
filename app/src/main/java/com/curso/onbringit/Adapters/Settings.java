package com.curso.onbringit.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.curso.onbringit.R;
import com.curso.onbringit.View.AboutActivity;
import com.curso.onbringit.View.ScheduleListActivity;

import java.util.ArrayList;

/**
 * Created by PC-PRAF on 2/9/2017.
 */

public class Settings extends RecyclerView.Adapter<Settings.SettingsHolder> {


    private static ArrayList<String[]> SettingsNameArray;
    private Context context;

    public Settings(Context cntx){

        context = cntx;
        SettingsNameArray = new ArrayList<>();
        SettingsNameArray.add(new String[]{"1","Servicios","1"});
        SettingsNameArray.add(new String[]{"2","Entregas Programados","0"});
        SettingsNameArray.add(new String[]{"3","Cancelación del Pedido","0"});
        SettingsNameArray.add(new String[]{"4","Cancelación de Articulos en Pedido","0"});
        SettingsNameArray.add(new String[]{"5","Cuenta","1"});
        SettingsNameArray.add(new String[]{"6","Perfil","0"});
        SettingsNameArray.add(new String[]{"7","Eliminar Cuenta","0"});
        SettingsNameArray.add(new String[]{"8","Cerrar Sesión","0"});
        SettingsNameArray.add(new String[]{"9","Información y Ayuda","1"});
        SettingsNameArray.add(new String[]{"10","Terminos y Privacidad","0"});
        SettingsNameArray.add(new String[]{"11","Preguntas Frecuentes","0"});
        SettingsNameArray.add(new String[]{"12","Enviar FeedBack","0"});
        SettingsNameArray.add(new String[]{"13","Reportar Error","0"});
        SettingsNameArray.add(new String[]{"14","Sobre ORDENA","0"});
        SettingsNameArray.add(new String[]{"16","Aplicaciones de Terceros","0"});
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
                switch (SettingsNameArray.get(position)[0]){
                    case "14":
                           intent = new Intent(context , AboutActivity.class);
                            context.startActivity(intent);
                            break;
                    case "2":
                        intent = new Intent(context , ScheduleListActivity.class);
                        context.startActivity(intent);
                        break;
                }
            }
        });
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
