package com.retailstreet.mobilepos.Controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ControllerCart extends SQLiteOpenHelper {
    Context context;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MasterDB";
    private static final String TABLE_NAME = "cart";
    Cursor cursor;
    SQLiteDatabase sqLiteDatabase;

   public ControllerCart(Context context){
       super(context, DATABASE_NAME, null, DATABASE_VERSION);
       this.context =context;
   }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor getCartCursor() {
        try {
             sqLiteDatabase = getReadableDatabase();
            if(cursor != null){
                cursor.close();
            }
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM cart", null);
            if (cursor.moveToFirst()) {
                return cursor;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public  void printCart(){
        Log.d("Printing Cart", "CartPrint called");
        SQLiteDatabase mydb = getReadableDatabase();
        String tableString = String.format("Table %s:\n", "cart");
        Cursor allRows  = mydb.rawQuery("SELECT * FROM " + "cart", null);
        if (allRows.moveToFirst() ){
            String[] columnNames = allRows.getColumnNames();
            do {
                for (String name: columnNames) {
                    tableString += String.format("%s: %s\n", name,
                            allRows.getString(allRows.getColumnIndex(name)));
                }
                tableString += "\n";

            } while (allRows.moveToNext());
        }
        Log.d("Cart", "printCart: "+tableString);
        allRows.close();
        mydb.close();
    }
}
