package com.curso.onbringit.Model.ApiExecute;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;

import com.curso.onbringit.Adapters.ItemsInDelivery;
import com.curso.onbringit.DataBase.Sql_Query;
import com.curso.onbringit.Model.ApiSchemaResponse;
import com.curso.onbringit.Model.ApiServices;
import com.curso.onbringit.View.InfoDeliveryActivity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import retrofit2.Call;

/**
 * Created by PC-PRAF on 24/12/2017.
 */

public class pedidos_executes extends AsyncTask<Integer , Void , Boolean> {

    private Context context;
    private ApiServices Service;

    public pedidos_executes(Context contx , ApiServices Serv){
        this.context = contx;
        this.Service = Serv;
    }

    @Override
    protected Boolean doInBackground(Integer... integers) {

        switch (integers[0]){
            case 1:
                    break;
        }

        return null;
    }

    private boolean ExpressDelivery(){
        boolean salida = false;
        SQLiteDatabase db = Sql_Query.GetConnection(context).getReadableDatabase();
        Sql_Query sql_query = new Sql_Query(db,context);
        String Token = sql_query.GetApiToken();


        ArrayList<String[]> OrderList = ItemsInDelivery.ItemsInDelivery;
        String Images ="{ 'images': [";
        for(int x = 0; x<ItemsInDelivery.AttachInItem.size() ; x++){
            for(int y = 1;y<ItemsInDelivery.AttachInItem.get(x).size();y++)
            {
                if(y == 1){
                    for(int z = 0;z<OrderList.size();z++)
                    {
                        if(z == Integer.parseInt(ItemsInDelivery.AttachInItem.get(x).get(0))){
                            Images+=" { 'producto': '"+OrderList.get(z)[0]+"' , 'imagenes':[ ";
                        }
                    }
                }

                Bitmap bitmap = BitmapFactory.decodeFile(ItemsInDelivery.AttachInItem.get(x).get(y));
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                byte[] bytes = byteArrayOutputStream.toByteArray();
                Images+="'"+ Base64.encodeToString(bytes , Base64.DEFAULT)+"'";

                if(y+1 < ItemsInDelivery.AttachInItem.get(x).size()){
                    Images+=",";
                }
            }
            Images+=" ] } ";
            if(x+1 < ItemsInDelivery.AttachInItem.size()){
                Images+=",";
            }
        }

        Images+=" ]}";

        String jsonInfoOrder = "{ datos: [[ "+ InfoDeliveryActivity.Address+" , " +
                InfoDeliveryActivity.Entrega+" , "+
                InfoDeliveryActivity.phonerNumber+"]]";

        String jsonOrderList = new Gson().toJson(OrderList);

        try {
            JSONObject jsonAttchedList = new JSONObject(Images);
            Call<ApiSchemaResponse.Pedido_Response> Send_Order = this.Service.enviar_pedido(jsonOrderList   ,
                                                                                            jsonAttchedList ,
                                                                                            jsonInfoOrder   ,
                                                                                            Token);
            ApiSchemaResponse.Pedido_Response respuesta = Send_Order.execute().body();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return salida;
    }
}
