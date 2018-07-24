package com.curso.onbringit.Model.ApiExecute;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

import com.curso.onbringit.Adapters.ProductsAdapter;
import com.curso.onbringit.Adapters.Stores;
import com.curso.onbringit.DataBase.Sql_Query;
import com.curso.onbringit.Model.ApiRequest;
import com.curso.onbringit.Model.ApiSchemaResponse;
import com.curso.onbringit.Model.ApiServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Response;

public class products_executes  extends AsyncTask<Integer , Void , Boolean> {

    private Context context;
    private ApiServices services;
    private ProductsAdapter products;
    private int store_id;

    public products_executes(Context cntx , ApiServices serv , int store_id , ProductsAdapter products){
        this.context = cntx;
        this.services = serv;
        this.store_id = store_id;
        this.products = products;
    }

    @Override
    protected Boolean doInBackground(Integer... integers) {
        Boolean response = false;
        switch (integers[0]){
            case 1: response = GetProducts();
                break;
        }
        return response;
    }



    private boolean GetProducts(){
        boolean response;

        SQLiteDatabase db = Sql_Query.GetConnection(context).getReadableDatabase();
        Sql_Query sql_query = new Sql_Query(db,context);
        String Token = sql_query.GetApiToken();
        ArrayList<String[]> Products = new ArrayList<>();

        Call<ApiSchemaResponse.listar_productos> item_products = services.listar_productos(Token , this.store_id);
        try {
            Response response_items = item_products.execute();
            if(response_items.code() == 401)
            {
                user_excutes user_excutes = new user_excutes(context , services);
                boolean user_request = false;
                try {
                    user_request = user_excutes.executeOnExecutor(THREAD_POOL_EXECUTOR , 1).get();
                } catch (InterruptedException e) {
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                } catch (ExecutionException e) {
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                }

                if(user_request){
                    response = GetProducts();
                }else{
                    Toast.makeText(context,"Error al cargar la información",Toast.LENGTH_LONG).show();
                    response = false;
                }
            }else{
                if(response_items.code() == 200){
                    ApiSchemaResponse.listar_productos respuesta = (ApiSchemaResponse.listar_productos) response_items.body();
                    for(int l = 0 ; l < respuesta.getmensaje().size(); l++){
                        Products.add(new String[]{
                                String.valueOf( respuesta.getmensaje().get(l).getProducto_Codigo() ) ,
                                respuesta.getmensaje().get(l).getProducto_Nombre() ,
                                String.valueOf( respuesta.getmensaje().get(l).getProducto_precio() ) +" - " +
                                    respuesta.getmensaje().get(l).getProducto_medida() ,
                                respuesta.getmensaje().get(l).getProducto_Imagen()
                        });
                    }
                     products.PushProducts(Products);
                    response = true;
                }else{
                    Toast.makeText(context,"Error al listar los locales",Toast.LENGTH_LONG).show();
                    response = false;
                }
            }
        } catch (IOException e) {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            response = false;
        }

        return response;
    }


}
