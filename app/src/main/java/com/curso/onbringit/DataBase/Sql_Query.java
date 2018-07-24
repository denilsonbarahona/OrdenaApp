package com.curso.onbringit.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.curso.onbringit.Model.ApiRequest;

import java.security.PublicKey;
import java.util.ArrayList;

/**
 * Created by PC-PRAF on 17/9/2017.
 */

public class Sql_Query {

    SQLiteDatabase db;
    Context Contex;


    public Sql_Query(SQLiteDatabase SQLiteDatabase , Context context){
        this.Contex = context;
        this.db = SQLiteDatabase;
    }

    public static SqlConnection GetConnection(Context context){
        SqlConnection connection = new SqlConnection( context, "ORDENADB",null , 1);
        return  connection;
    }

    public ArrayList<String[]> GetAddressDefault(){
        ArrayList<String[]> AddressDefault = new ArrayList<>();

        String[] Projection = {
                SqlConnection.SchemaDB.ADDRESS_NAME ,
                SqlConnection.SchemaDB.ADDRESS_     ,
                SqlConnection.SchemaDB.ADDRESS_API_ID
        };

        String selection = SqlConnection.SchemaDB.ADDRESS_DEFAULT +" = ? ";
        String [] args = {"1"};
        Cursor cursor = this.db.query(
                        SqlConnection.SchemaDB.TABLE_ADDRESS ,
                        Projection ,
                        selection  ,
                        args       ,
                        null       ,
                        null       ,
                        null
                );

        if(cursor.moveToFirst()){
            do{
               AddressDefault.add(new String[]{
                       cursor.getString(cursor.getColumnIndex(SqlConnection.SchemaDB.ADDRESS_NAME)) ,
                       cursor.getString(cursor.getColumnIndex(SqlConnection.SchemaDB.ADDRESS_))     ,
                       cursor.getString(cursor.getColumnIndex(SqlConnection.SchemaDB.ADDRESS_API_ID))
               });
            }while (cursor.moveToNext());
        }

        return AddressDefault;
    }

    public long SaveAddress(String Name , String Address , int default_ , int Address_api_id){

        if(default_ != 0)
        {
            ContentValues UpdateValue = new ContentValues();
            UpdateValue.put(SqlConnection.SchemaDB.ADDRESS_DEFAULT , 0);
            String selection = SqlConnection.SchemaDB.ADDRESS_DEFAULT+" = ? ";
            String[] Args = {"1"};

            this.db.update(SqlConnection.SchemaDB.TABLE_ADDRESS ,
                                       UpdateValue ,
                                       selection   ,
                                       Args
                    );
        }

        ContentValues values = new ContentValues();
        values.put(SqlConnection.SchemaDB.ADDRESS_NAME,Name.toUpperCase());
        values.put(SqlConnection.SchemaDB.ADDRESS_,Address.toUpperCase());
        values.put(SqlConnection.SchemaDB.ADDRESS_API_ID,Address_api_id);
        values.put(SqlConnection.SchemaDB.ADDRESS_DEFAULT,default_);

        long result = this.db.insert(SqlConnection.SchemaDB.TABLE_ADDRESS , null, values);

        return result;
    }

    public long SaveProfile(String Name , String PhoneNumber , String Email){

        ContentValues values = new ContentValues();
        values.put(SqlConnection.SchemaDB.USER_NAME  , Name);
        values.put(SqlConnection.SchemaDB.USER_EMAIL , Email);
        values.put(SqlConnection.SchemaDB.USER_PHONE , PhoneNumber);
        values.put(SqlConnection.SchemaDB.USER_LOG   , 1 );
        values.put(SqlConnection.SchemaDB.USER_FIRSTTIME , 1);

        long result = this.db.insert(SqlConnection.SchemaDB.TABLE_USERS , null , values);

        return result;
    }

    public ArrayList<String[]> GetAddressSaved(){

        ArrayList<String[]> AddressSaved = new ArrayList<>();
        String[] projection = {
                SqlConnection.SchemaDB.ADDRESS_ID ,
                SqlConnection.SchemaDB.ADDRESS_NAME ,
                SqlConnection.SchemaDB.ADDRESS_ ,
                SqlConnection.SchemaDB.ADDRESS_DEFAULT ,
                SqlConnection.SchemaDB.ADDRESS_API_ID
        };

        Cursor cursor = db.query(SqlConnection.SchemaDB.TABLE_ADDRESS ,
                                 projection ,
                                 null ,
                                 null ,
                                 null ,
                                 null ,
                                 SqlConnection.SchemaDB.ADDRESS_DEFAULT+" DESC "
                );
        if(cursor.moveToFirst()){
            do{
                AddressSaved.add(new String[]{
                        cursor.getString(cursor.getColumnIndex(SqlConnection.SchemaDB.ADDRESS_NAME))    ,
                        cursor.getString(cursor.getColumnIndex(SqlConnection.SchemaDB.ADDRESS_))        ,
                        cursor.getString(cursor.getColumnIndex(SqlConnection.SchemaDB.ADDRESS_DEFAULT)) ,
                        cursor.getString(cursor.getColumnIndex(SqlConnection.SchemaDB.ADDRESS_API_ID))
                });
            }while(cursor.moveToNext());
        }

        return AddressSaved;
    }

    public String UrlImageProfile(){
        String profile="";
        String[] projection = {
                SqlConnection.SchemaDB.USER_IMAGE_PROFILE
        };
        Cursor cursor = db.query(SqlConnection.SchemaDB.TABLE_USERS ,
                                 projection , null       ,
                                 null       , null       ,
                                 null       , null
                );
        if(cursor.moveToFirst()){
            do{
                profile = cursor.getString(cursor.getColumnIndex(SqlConnection.SchemaDB.USER_IMAGE_PROFILE));
            }while(cursor.moveToNext());
        }
        return profile;
    }

    public Boolean FirstTime() {
        Boolean IsFirstTime = Boolean.FALSE;
        String[] projection = {
                SqlConnection.SchemaDB.USER_FIRSTTIME
        };
        Cursor cursor = db.query(SqlConnection.SchemaDB.TABLE_USERS ,
                projection , null       ,
                null       , null       ,
                null       , null
        );
        if(cursor.moveToFirst()){
            do{
                IsFirstTime = (cursor.getInt(cursor.getColumnIndex(SqlConnection.SchemaDB.USER_FIRSTTIME))== 0)?true:false;
            }while(cursor.moveToNext());
        }else {
            IsFirstTime = true;
        }
        return IsFirstTime;
    }

    public void SaveUserApiID(int usuario_api_id , int hasImageProfile , String token , int updateToken){

        ApiRequest apiRequest = new ApiRequest(null);
        String image_profile =(hasImageProfile ==1)?apiRequest.getUrl()+"perfil_image/"+String.valueOf(usuario_api_id)
                                                   :apiRequest.getUrl()+"perfil_image/default";

        ContentValues UpdateValues = new ContentValues();
        if(updateToken == 0){
            UpdateValues.put(SqlConnection.SchemaDB.USER_API_ID , usuario_api_id);
            UpdateValues.put(SqlConnection.SchemaDB.USER_IMAGE_PROFILE , image_profile);
        }
        UpdateValues.put(SqlConnection.SchemaDB.USER_TOKEN , token);
        this.db.update( SqlConnection.SchemaDB.TABLE_USERS ,
                        UpdateValues ,
                        null         ,
                        null
                );
    }

    public void DeleteUser(int usuario_id){

        this.db.delete(SqlConnection.SchemaDB.TABLE_USERS,
                       SqlConnection.SchemaDB.USER_ID+" = "+String.valueOf(usuario_id),
                       null
                );
    }

    public String GetApiToken(){
        String Token="";
        String[] project = {
                SqlConnection.SchemaDB.USER_TOKEN
        };
        Cursor cursor = db.query(SqlConnection.SchemaDB.TABLE_USERS ,
                                project , null ,
                                null    , null ,
                                null    , null
                );
        if(cursor.moveToFirst()){
            do{
                Token = cursor.getString(cursor.getColumnIndex(SqlConnection.SchemaDB.USER_TOKEN));
            }while (cursor.moveToNext());
        }
        return  Token;
    }

    public String getUsuarioEmail() {
        String UsuarioEmail="";
        String[] project = {
                SqlConnection.SchemaDB.USER_EMAIL
        };
        Cursor cursor = db.query(SqlConnection.SchemaDB.TABLE_USERS ,
                    project , null ,
                    null    , null ,
                    null    , null
                );
        if(cursor.moveToFirst()){
            do{
                UsuarioEmail = cursor.getString(cursor.getColumnIndex(SqlConnection.SchemaDB.USER_EMAIL));
            }while(cursor.moveToNext());
        }
        return  UsuarioEmail;
    }

    public ArrayList<String[]> GetUserProfile(){

        ArrayList<String[]> UserProfile = new ArrayList<>();
        String[] projection = {
            SqlConnection.SchemaDB.USER_IMAGE_PROFILE ,
            SqlConnection.SchemaDB.USER_NAME          ,
            SqlConnection.SchemaDB.USER_EMAIL         ,
            SqlConnection.SchemaDB.USER_PHONE         ,
            SqlConnection.SchemaDB.USER_ID            ,
            SqlConnection.SchemaDB.USER_API_ID        ,
            SqlConnection.SchemaDB.USER_TOKEN
        };

        Cursor cursor = db.query(SqlConnection.SchemaDB.TABLE_USERS ,
                                 projection ,
                                 null       ,
                                 null       ,
                                 null       ,
                                 null       ,
                                 null
        );

        if(cursor.moveToFirst()){
            do{
               UserProfile.add(new String[]{
                       cursor.getString(cursor.getColumnIndex(SqlConnection.SchemaDB.USER_IMAGE_PROFILE)),
                       cursor.getString(cursor.getColumnIndex(SqlConnection.SchemaDB.USER_NAME))         ,
                       cursor.getString(cursor.getColumnIndex(SqlConnection.SchemaDB.USER_EMAIL))        ,
                       cursor.getString(cursor.getColumnIndex(SqlConnection.SchemaDB.USER_PHONE))        ,
                       cursor.getString(cursor.getColumnIndex(SqlConnection.SchemaDB.USER_ID))           ,
                       cursor.getString(cursor.getColumnIndex(SqlConnection.SchemaDB.USER_API_ID))       ,
                       cursor.getString(cursor.getColumnIndex(SqlConnection.SchemaDB.USER_TOKEN))

               });
            }while(cursor.moveToNext());
        }

        return  UserProfile;
    }

    public void UpdateUserInformation(String UserName ,String Email , String Phone , int changePhone , int user_api_id)
    {
        ApiRequest apiRequest = new ApiRequest(null);

        ContentValues UpdateValues = new ContentValues();
        UpdateValues.put(SqlConnection.SchemaDB.USER_NAME , UserName);
        UpdateValues.put(SqlConnection.SchemaDB.USER_EMAIL , Email);
        UpdateValues.put(SqlConnection.SchemaDB.USER_PHONE , Phone);
        if(changePhone == 1)
        {
            UpdateValues.put(SqlConnection.SchemaDB.USER_IMAGE_PROFILE , apiRequest.getUrl()+"perfil_image/"+String.valueOf(user_api_id));
        }
        this.db.update(SqlConnection.SchemaDB.TABLE_USERS ,
                       UpdateValues ,
                       null         ,
                       null
                );
    }

    public void deleteAccount(){
        this.db.delete(SqlConnection.SchemaDB.TABLE_USERS,null,null);
        this.db.delete(SqlConnection.SchemaDB.TABLE_ADDRESS,null,null);
    }

    public String getUsrPhone(){
        String Phone="";
        String[] project = {
                SqlConnection.SchemaDB.USER_PHONE
        };
        Cursor cursor = db.query(SqlConnection.SchemaDB.TABLE_USERS ,
                project , null ,
                null    , null ,
                null    , null
        );
        if(cursor.moveToFirst()){
            do{
                Phone = cursor.getString(cursor.getColumnIndex(SqlConnection.SchemaDB.USER_PHONE));
            }while(cursor.moveToNext());
        }
        return  Phone;
    }
}
