package com.retailstreet.mobilepos.Controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.retailstreet.mobilepos.View.ApplicationContextProvider;

public class ControllerCart {
    Context context;
    static SQLiteDatabase mydb;
   public ControllerCart(Context context){
       this.context = context;
       mydb = ApplicationContextProvider.getContext().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null);
   }

    public Cursor getCartCursor() {
        Cursor cursor = mydb.rawQuery("SELECT * FROM cart", null);
        if (cursor.moveToFirst()) {
            return cursor;
        } else {
            return null;
        }
    }
    public static void printCart(){
        Log.d("Printing Cart", "CartPrint called");
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
    }
}
