package com.retailstreet.mobilepos.Controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ControllerStockMaster extends SQLiteOpenHelper {

    private final Context context;
    SQLiteDatabase db;
    Cursor cursor;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MasterDB";
    private static final String TABLE_NAME = "retail_str_stock_master";

    public ControllerStockMaster(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context =context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      /*  String CREATION_TABLE = "CREATE TABLE StockMaster ( "
                + "EMPLOYEE_GUID INTEGER PRIMARY KEY, " + "ISACTIVE TEXT, "
                + "PASSWORD TEXT, "+ "ROLE_GUID TEXT, " + "ROLE_ID TEXT," + "ROLE_NAME TEXT,"+ "USER_GUID TEXT,"+ "USER_NAME TEXT,"+ "ISSYNCED TEXT )";

        db.execSQL(CREATION_TABLE);

       */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      /*  // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db); */
    }



    public Cursor getStockMasterCursor() {
        try {
            if(cursor != null){
                cursor.close();
            }
            String query = "SELECT  * FROM " + TABLE_NAME;
            db = getReadableDatabase();          //Opens database in writable mode.
            cursor = db.rawQuery(query, null);
           if(cursor.moveToFirst()){
               return cursor;
           }else {
               return null;
           }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cursor;
    }
    public Cursor searchStockMasterCursor(String pattern, String column) {
        try {
            if(cursor != null){
                cursor.close();
            }
            String query = "SELECT  * FROM " + TABLE_NAME+" WHERE "+ column+" LIKE "+"'%"+pattern+"%'";
            db = getReadableDatabase();          //Opens database in writable mode.
            cursor = db.rawQuery(query, null);
            if(cursor.moveToFirst()){
                return cursor;
            }else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cursor;
    }


    
}