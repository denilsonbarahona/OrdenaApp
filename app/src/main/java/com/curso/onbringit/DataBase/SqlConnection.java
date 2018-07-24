package com.curso.onbringit.DataBase;

import android.content.Context;
import android.database.sqlite.*;
/**
 * Created by PC-PRAF on 17/9/2017.
 */

public class SqlConnection  extends SQLiteOpenHelper  {

    public SqlConnection(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        CreateDataBase(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        DeleteDataBase(sqLiteDatabase);
        onCreate(sqLiteDatabase);
    }

    public void CreateDataBase(SQLiteDatabase sqLiteDatabase){

        /* Create ADDRESS TABLE  */

        String Entries_Adress =" CREATE TABLE "+ SchemaDB.TABLE_ADDRESS +" ( " +
            SchemaDB.ADDRESS_ID +" INTEGER PRIMARY KEY Autoincrement , " +
            SchemaDB.ADDRESS_NAME+" TEXT NOT NULL , "+
            SchemaDB.ADDRESS_+" TEXT NOT NULL , "+
            SchemaDB.ADDRESS_API_ID+" INTEGER NOT NULL , "+
            SchemaDB.ADDRESS_DEFAULT+" INTEGER NOT NULL )  ";

        sqLiteDatabase.execSQL(Entries_Adress);
        /* Create USER TABLE */

        String Entries_User = " CREATE TABLE "+ SchemaDB.TABLE_USERS+" ( " +
            SchemaDB.USER_ID +"    INTEGER PRIMARY KEY  Autoincrement , " +
            SchemaDB.USER_API_ID+" INTEGER NULL , "+
            SchemaDB.USER_EMAIL+"  TEXT NULL    , "+
            SchemaDB.USER_PHONE+"  INTEGER NULL , "+
            SchemaDB.USER_NAME+"   TEXT  NULL   , "+
            SchemaDB.USER_IMAGE_PROFILE+" TEXT NULL , "+
            SchemaDB.USER_LOG+" INTEGER NULL        , "+
            SchemaDB.USER_TOKEN+" TEXT NULL         , "+
            SchemaDB.USER_FIRSTTIME+" INTEGER NOT NULL ); ";

        sqLiteDatabase.execSQL(Entries_User);
    }

    public void DeleteDataBase(SQLiteDatabase sqLiteDatabase)
    {
        String Entries_Address =" DROP TABLE IF EXISTS "+ SchemaDB.TABLE_ADDRESS;
        String Entries_User =" DROP TABLE IF EXISTS "+ SchemaDB.TABLE_USERS;

        sqLiteDatabase.execSQL(Entries_Address);
        sqLiteDatabase.execSQL(Entries_User);
    }

    public static class SchemaDB
    {
        /* ADDRESS SQUEMA */
        public static final String TABLE_ADDRESS ="T_ADDRESS";
        public static final String ADDRESS_ID ="ADDRESS_ID";
        public static final String ADDRESS_NAME ="ADDRESS_NAME";
        public static final String ADDRESS_ ="ADDRESS";
        public static final String ADDRESS_API_ID ="ADDRESS_API_ID";
        public static final String ADDRESS_DEFAULT ="ADDRESS_DEFAULT";

        /* USER SQUEMA */
        public static final String TABLE_USERS ="T_USERS";
        public static final String USER_ID ="USER_ID";
        public static final String USER_API_ID ="USER_API_ID";
        public static final String USER_EMAIL ="USER_EMAIL";
        public static final String USER_PHONE ="USER_PHONE";
        public static final String USER_NAME ="USER_NAME";
        public static final String USER_IMAGE_PROFILE ="USER_IMAGE_PROFILE";
        public static final String USER_LOG ="USER_LOG";
        public static final String USER_FIRSTTIME ="USER_FIRSTTIME";
        public static final String USER_TOKEN ="USER_TOKEN";
    }

}
