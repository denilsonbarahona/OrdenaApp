package com.curso.onbringit.Model.ApiExecute;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

import com.curso.onbringit.Adapters.ItemsCancel;
import com.curso.onbringit.DataBase.Sql_Query;
import com.curso.onbringit.Model.ApiSchemaResponse;
import com.curso.onbringit.Model.ApiServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by PC-PRAF on 13/1/2018.
 */

public class items_executes extends AsyncTask<Integer , Void , Boolean> {

    private Context context;
    private ApiServices Service;
    private int product_id;
    private String Order_id;
    private ItemsCancel itemsInOrder;


    public items_executes(Context contx , ApiServices Sev , String Order_id , ItemsCancel itemsInOrder){
        this.context = contx;
        this.Service = Sev;
        this.Order_id = Order_id;
        this.itemsInOrder = itemsInOrder;
    }


    public items_executes(Context contx , ApiServices Sev , int product_id){
        this.context = contx;
        this.Service = Sev;
        this.product_id = product_id;
    }

    @Override
    protected Boolean doInBackground(Integer... integers) {
        Boolean response = false;
        switch (integers[0]){
            case 1: response = show_cancel_item();
                break;
            case 2: response = cancel_item();
                break;
        }
        return response;    }

    private boolean show_cancel_item(){

        boolean response;
        SQLiteDatabase db = Sql_Query.GetConnection(context).getReadableDatabase();
        Sql_Query sql_query = new Sql_Query(db,context);
        String Token = sql_query.GetApiToken();

        ArrayList<String[]> Array_ListItems = new ArrayList<>();
        Call<ApiSchemaResponse.listar_item_orden> listar_items = Service.listar_item_orden(this.Order_id,Token);

        try {
            Response list_response = listar_items.execute();
            if(list_response.code() == 401){

                user_excutes user_excutes = new user_excutes(context , Service);
                boolean user_request = false;
                try {
                    user_request = user_excutes.executeOnExecutor(THREAD_POOL_EXECUTOR , 1).get();
                } catch (InterruptedException e) {
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                    response = false;
                } catch (ExecutionException e) {
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                    response = false;
                }
                if(user_request){
                    response = show_cancel_item();
                }else{
                    Toast.makeText(context,"Error al cargar la información",Toast.LENGTH_LONG).show();
                    response = false;
                }
            }else{
                if(list_response.code() == 200){
                    ApiSchemaResponse.listar_item_orden list_items = (ApiSchemaResponse.listar_item_orden) list_response.body();
                    for(int i = 0; i < list_items.getMensaje().size(); i++){
                        Array_ListItems.add(new String[]{
                                list_items.getMensaje().get(i).getNombre()        ,
                                list_items.getMensaje().get(i).getCantidad()      ,
                                list_items.getMensaje().get(i).getNota()          ,
                                list_items.getMensaje().get(i).getLugar_compra()  ,
                                String.valueOf( list_items.getMensaje().get(i).getAdjuntos() ) ,
                                "Estado: "+list_items.getMensaje().get(i).getEstado_d()        ,
                                String.valueOf( list_items.getMensaje().get(i).getId() )

                        });
                    }
                    response = true;
                    itemsInOrder.AddItems(Array_ListItems);
                }else{
                    Toast.makeText(context,"Error al listar los productos de la orden",Toast.LENGTH_LONG).show();
                    response = false;
                }
            }
        } catch (IOException e) {

            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            response = false;
        }
        return  response;
    }

    private boolean cancel_item(){

        boolean response;

        SQLiteDatabase db = Sql_Query.GetConnection(context).getReadableDatabase();
        Sql_Query sql_query = new Sql_Query(db,context);
        String Token = sql_query.GetApiToken();

        Call<ApiSchemaResponse.cancelar_producto> cancelar_producto = Service.cancelar_producto(this.product_id , Token);
        try {
            Response cancel_item_response = cancelar_producto.execute();
            if(cancel_item_response.code() == 401){

                user_excutes user_excutes = new user_excutes(context , Service);
                boolean user_request = false;
                try {
                    user_request = user_excutes.executeOnExecutor(THREAD_POOL_EXECUTOR , 1).get();
                } catch (InterruptedException e) {
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                    response = false;
                } catch (ExecutionException e) {
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                    response = false;
                }
                if(user_request){
                    response = cancel_item();
                }else{
                    Toast.makeText(context,"Error no se pudo cancelar el producto",Toast.LENGTH_LONG).show();
                    response = false;
                }
            }else{
                if(cancel_item_response.code()==200){
                    ApiSchemaResponse.cancelar_producto respuesta = (ApiSchemaResponse.cancelar_producto) cancel_item_response.body();
                    if(respuesta.getStatus()==200){
                        response = true;
                    }else{
                        response = false;
                    }
                }else {
                    response = false;
                }
            }
        } catch (IOException e) {
            response = false;
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
        }

        return  response;
    }

}
