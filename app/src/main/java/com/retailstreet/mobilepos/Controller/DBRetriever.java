package com.retailstreet.mobilepos.Controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.retailstreet.mobilepos.View.ApplicationContextProvider;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Mayank Choudhary on 23-06-2021.
 * mayankchoudhary00@gmail.com
 */
public class DBRetriever {

        static Context context= ApplicationContextProvider.getContext();

    public DBRetriever(){
        context= ApplicationContextProvider.getContext();
    }

    public static String getTerminal_ID(){
        String result= null;
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            result = "";
            String selectQuery = "SELECT MASTER_TERMINAL_ID FROM terminal_configuration";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {

                result= cursor.getString(0);
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getTerminal_ID: "+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

}
