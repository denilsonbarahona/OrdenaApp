package com.curso.onbringit.Model.ApiExecute;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

import com.curso.onbringit.Adapters.Headings;
import com.curso.onbringit.DataBase.Sql_Query;
import com.curso.onbringit.Model.ApiSchemaResponse;
import com.curso.onbringit.Model.ApiServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by PC-PRAF on 17/4/2018.
 */

public class headings_executes extends AsyncTask<Integer , Void , Boolean> {

    private Context context;
    private ApiServices services;
    private Headings headingsApp;

    public headings_executes(Context cntx , ApiServices serv , Headings headings){
        this.context = cntx;
        this.services = serv;
        this.headingsApp = headings;
    }

    @Override
    protected Boolean doInBackground(Integer... integers) {
        Boolean response = false;
        switch (integers[0]){
            case 1: response = GetHeadings();
                break;
        }
        return response;
    }


    private boolean GetHeadings(){
        boolean response;

        SQLiteDatabase db = Sql_Query.GetConnection(context).getReadableDatabase();
        Sql_Query sql_query = new Sql_Query(db,context);
        String Token = sql_query.GetApiToken();
        ArrayList<String[]> Headings = new ArrayList<>();

        Call<ApiSchemaResponse.listar_rubros> item_headings = services.listar_rubros (Token);
        try {
            Response response_items = item_headings.execute();
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
                    response = GetHeadings();
                }else{
                    Toast.makeText(context,"Error al cargar la información",Toast.LENGTH_LONG).show();
                    response = false;
                }
            }else{
                if(response_items.code() == 200){
                    ApiSchemaResponse.listar_rubros respuesta = (ApiSchemaResponse.listar_rubros) response_items.body();
                    for(int l = 0 ; l < respuesta.getmensaje().size(); l++){
                        Headings.add(new String[]{
                                String.valueOf( respuesta.getmensaje().get(l).getRubro_Codigo() ) ,
                                respuesta.getmensaje().get(l).getRubro_Nombre()
                        });
                    }
                    headingsApp.PushHeadings(Headings);
                    response = true;
                }else{
                    Toast.makeText(context,"Error al listar los rubros",Toast.LENGTH_LONG).show();
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
