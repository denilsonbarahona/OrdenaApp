package com.curso.onbringit.Model.ApiExecute;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

import com.curso.onbringit.DataBase.Sql_Query;
import com.curso.onbringit.Model.ApiRequest;
import com.curso.onbringit.Model.ApiSchemaResponse;
import com.curso.onbringit.Model.ApiServices;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by PC-PRAF on 24/12/2017.
 */

public class user_excutes extends AsyncTask<Integer , Void , Boolean> {

    private Context context;
    private ApiServices Service;
    private String Email;
    private String Nombre;
    private String Telefono;
    private int hasImageProfile;
    public static  int user_api_id;
    private JSONObject Datos;
    private String image_profile;


    public user_excutes(Context conxt , ApiServices Serv){
        context = conxt;
        Service = Serv;
    }

    public user_excutes(Context conxt , ApiServices Serv , String Email , String Nombre , String Telefono , int hasImageProfile){
        context = conxt;
        Service = Serv;
        this.Email = Email;
        this.Nombre = Nombre;
        this.Telefono = Telefono;
        this.hasImageProfile = hasImageProfile;
    }

    public user_excutes(Context conxt , ApiServices Serv , JSONObject datos){
        this.context = conxt;
        this.Service = Serv;
        this.Datos = datos;
    }

    public user_excutes(Context conxt , ApiServices Serv , String image_profile , int user_api){
        context = conxt;
        Service = Serv;
        this.image_profile = image_profile;
        user_excutes.user_api_id = user_api;
    }




    @Override
    protected Boolean doInBackground(Integer... integers) {
        Boolean response = false;
        switch (integers[0]){
            case 1: response = obtener_token();
                    break;
            case 2: response = obtener_usuario_api_id();
                    break;
            case 3: response = update_user_profile();
                    break;
            case 4: response = upload_image();
                    break;
            case 5: response = delete_profile();
                    break;
        }
        return response;
    }


    public boolean obtener_token(){

        boolean salida;

        SQLiteDatabase db = Sql_Query.GetConnection(context).getReadableDatabase();
        Sql_Query sql_query = new Sql_Query(db,context);

        Call<ApiSchemaResponse.obtener_usuario_api_id> Token = Service.obtener_token(sql_query.getUsuarioEmail());
        try {
            ApiSchemaResponse.obtener_usuario_api_id respuesta = Token.execute().body();

            if(respuesta.getStatus() == 200){
                sql_query.SaveUserApiID(respuesta.getMensaje().get(0).getUsuario_id() , 0 , respuesta.getMensaje().get(0).getToken() ,1);
                salida = true;
            }else{
                salida = false;
            }
        } catch (IOException e) {
            salida = false;
        }

        return  salida;
    }

    public boolean obtener_usuario_api_id(){

        boolean salida;
        Call<ApiSchemaResponse.obtener_usuario_api_id> usuario_api_id = Service.obtener_usuario_api_id(this.Email, this.Nombre , this.Telefono);
        try {
            ApiSchemaResponse.obtener_usuario_api_id response = usuario_api_id.execute().body();
            if(response.getStatus() == 200){
                SQLiteDatabase db = Sql_Query.GetConnection(context).getReadableDatabase();
                Sql_Query sql_query = new Sql_Query(db,context);
                sql_query.SaveUserApiID(response.getMensaje().get(0).getUsuario_id() , hasImageProfile ,
                        response.getMensaje().get(0).getToken() , 0);

                user_api_id = response.getMensaje().get(0).getUsuario_id();
                salida = true;
            }else{
                Toast.makeText(context , "Error de servidor , no se pudo registrar el usuario" , Toast.LENGTH_LONG).show();
                salida = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            salida = false;
        }


        return salida;
    }

    public boolean update_user_profile(){
        boolean response;

        SQLiteDatabase db = Sql_Query.GetConnection(context).getReadableDatabase();
        Sql_Query sql_query = new Sql_Query(db,context);
        String Token = sql_query.GetApiToken();

        Call<ApiSchemaResponse.conexion_escalar_api> update_profile = Service.actualizar_perfil(Datos , Token);

        try {
            Response profile_response = update_profile.execute();
            if(profile_response.code()==401){
                user_excutes user_excutes = new user_excutes(context , Service);
                boolean user_request = false;
                try {
                    user_request = user_excutes.executeOnExecutor(THREAD_POOL_EXECUTOR , 1).get();
                } catch (InterruptedException e) {
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                } catch (ExecutionException e) {
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                }
                if(user_request){
                    response = update_user_profile();
                }else{
                    Toast.makeText(context,"Error, no se puede actualizar la información del perfil",Toast.LENGTH_LONG).show();
                    response = false;
                }
            }else{
                if(profile_response.code()==200){
                    response = true;
                }else{
                    Toast.makeText(context,"Error, no se puede actualizar la información del perfil",Toast.LENGTH_LONG).show();
                    response = false;
                }
            }

        } catch (IOException e) {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            response = false;
        }

        return  response;
    }

    public boolean upload_image(){
        boolean response;

        SQLiteDatabase db = Sql_Query.GetConnection(context).getReadableDatabase();
        Sql_Query sql_query = new Sql_Query(db,context);
        String Token = sql_query.GetApiToken();

        Call<ApiSchemaResponse.subir_imagen> subir_imagen = Service.subir_imagen_perfil(image_profile , user_api_id  ,Token);

        try {
            Response upload_response = subir_imagen.execute();
            if(upload_response.code() == 401){
                user_excutes user_excutes = new user_excutes(context , Service);
                boolean user_request = false;
                try {
                    user_request = user_excutes.executeOnExecutor(THREAD_POOL_EXECUTOR , 1).get();
                } catch (InterruptedException e) {
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                } catch (ExecutionException e) {
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                }
                if(user_request){
                    response = upload_image();
                }else{
                    Toast.makeText(context,"Error, no se puede cargar la imagen del perfil",Toast.LENGTH_LONG).show();
                    response = false;
                }
            }else{
                if(upload_response.code() == 200){
                    response = true;
                }else{
                    Toast.makeText(context,"Error, no se puede cargar la imagen del perfil",Toast.LENGTH_LONG).show();
                    response = false;
                }
            }
        } catch (IOException e) {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            response = false;
        }

        return  response;
    }

    public boolean delete_profile(){
        boolean response;

        SQLiteDatabase db = Sql_Query.GetConnection(context).getReadableDatabase();
        Sql_Query sql_query = new Sql_Query(db,context);
        String Token = sql_query.GetApiToken();
        Call<ApiSchemaResponse.conexion_escalar_api> delete_account = Service.eliminar_perfil(Datos , Token);

        try {
            Response delete_response = delete_account.execute();
            if(delete_response.code()==401){

                user_excutes user_excutes = new user_excutes(context , Service);
                boolean user_request = false;
                try {
                    user_request = user_excutes.executeOnExecutor(THREAD_POOL_EXECUTOR , 1).get();
                } catch (InterruptedException e) {
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                } catch (ExecutionException e) {
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                }
                if(user_request){
                    response = delete_profile();
                }else{
                    Toast.makeText(context,"Error, no se puede eliminar el perfil de ordena",Toast.LENGTH_LONG).show();
                    response = false;
                }
            }else{
                if(delete_response.code()== 200){
                    response = true;
                }else{
                    Toast.makeText(context,"Error, no se puede eliminar el perfil de ordena",Toast.LENGTH_LONG).show();
                    response = false;
                }
            }
        } catch (IOException e) {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            response = false;
        }
        return  response;
    }

}
