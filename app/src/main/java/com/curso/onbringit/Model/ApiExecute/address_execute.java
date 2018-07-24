package com.curso.onbringit.Model.ApiExecute;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

import com.curso.onbringit.DataBase.Sql_Query;
import com.curso.onbringit.Model.ApiRequest;
import com.curso.onbringit.Model.ApiSchemaResponse;
import com.curso.onbringit.Model.ApiServices;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by PC-PRAF on 24/12/2017.
 */

public class address_execute extends AsyncTask<Integer , Void , Boolean> {

    private Context context;
    private ApiServices Service;
    private String AddressName;
    private String AddressDescription;
    private int    AddressId;
    private int     default_;

    public address_execute(Context contx , ApiServices serv , String  AddressName ,
                           String AddressDescription , int AddressId , int default_ ){

        this.context = contx;
        this.Service = serv;
        this.AddressName = AddressName;
        this.AddressDescription = AddressDescription;
        this.AddressId = AddressId;
        this.default_ = default_;
    }

    @Override
    protected Boolean doInBackground(Integer... integers) {

        boolean response = false;
        switch (integers[0]){
            case 1:
                    response = add_address();
                    break;
        }

        return response;
    }

    public boolean add_address()
    {
        boolean salida;
        SQLiteDatabase db = Sql_Query.GetConnection(context).getReadableDatabase();
        Sql_Query sql_query = new Sql_Query(db,context);
        String Token = sql_query.GetApiToken();

        String AddressParams = "{'address_info':['"+this.AddressName+"','"+this.AddressDescription+"']}";
        try {
            JSONObject jsonObject = new JSONObject(AddressParams);
            Call<ApiSchemaResponse.conexion_escalar_api> address_request = this.Service.nueva_direccion(jsonObject , Token);
            Response address_response = address_request.execute();
            ApiSchemaResponse.conexion_escalar_api response = (ApiSchemaResponse.conexion_escalar_api) address_response.body();
            if(address_response.code()==200){
                sql_query.SaveAddress(AddressName ,AddressDescription , default_ , response.getMensaje());
                salida = true;
            }else{
                if(address_response.code()==401)
                {
                    user_excutes user_excutes = new user_excutes(context , Service);
                    boolean user_request = user_excutes.executeOnExecutor(THREAD_POOL_EXECUTOR , 1).get();
                    if(user_request){
                        salida = add_address();
                    }else{
                        salida = false;
                    }
                }else{

                    Toast.makeText(context , "Error de servidor, No se pudo registrar la dirección" , Toast.LENGTH_LONG).show();
                    salida = false;
                }
            }

        } catch (Exception e) {
            Toast.makeText(context , e.getMessage() , Toast.LENGTH_LONG).show();
            salida = false;
        }

        return  salida;
    }

}
