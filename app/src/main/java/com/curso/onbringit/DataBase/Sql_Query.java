package com.curso.onbringit.Models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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


    public ArrayList<String[]> GetAddressSaved(){

        ArrayList<String[]> AddressSaved = new ArrayList<>();

        String[] projection = {
                SqlConnection.SchemaDB.ADDRESS_ID ,
                SqlConnection.SchemaDB.ADDRESS_NAME ,
                SqlConnection.SchemaDB.ADDRESS_ ,
                SqlConnection.SchemaDB.ADDRESS_DEFAULT
        };

        Cursor cursor = db.query(SqlConnection.SchemaDB.TABLE_ADDRESS ,
                                 projection ,
                                 null ,
                                 null ,
                                 null ,
                                 null ,
                                 SqlConnection.SchemaDB.ADDRESS_DEFAULT+" DESC "
                );

        return AddressSaved;
    }


}
