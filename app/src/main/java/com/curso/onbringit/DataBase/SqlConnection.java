package com.curso.onbringit.Models;

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
        sqLiteDatabase.execSQL(CreateDataBase());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DeleteDataBase());
        onCreate(sqLiteDatabase);
    }

    public String CreateDataBase(){

        String Entries ="";

        /* Create ADDRESS TABLE  */

        Entries =" CREATE TABLE "+ SchemaDB.TABLE_ADDRESS +" ( " +
            SchemaDB.ADDRESS_ID +" INTEGER PRIMARY KEY Autoincrement , " +
            SchemaDB.ADDRESS_NAME+" TEXT NOT NULL , "+
            SchemaDB.ADDRESS_+" TEXT NOT NULL , " +
            SchemaDB.ADDRESS_DEFAULT+" INTEGER NOT NULL )";

        return Entries;
    }

    public String DeleteDataBase(){

        String Entries="";

        Entries =" DROP TABLE IF EXISTS "+ SchemaDB.TABLE_ADDRESS;

        return Entries;
    }

    public static class SchemaDB {
        /* ADDRESS SQUEMA */

        public static final String TABLE_ADDRESS ="T_ADDRESS";
        public static final String ADDRESS_ID ="ADDRESS_ID";
        public static final String ADDRESS_NAME ="ADDRESS_NAME";
        public static final String ADDRESS_ ="ADDRESS";
        public static final String ADDRESS_DEFAULT ="ADDRESS_DEFAULT";
    }

}
