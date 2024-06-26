package com.retailstreet.mobilepos.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.retailstreet.mobilepos.View.ApplicationContextProvider;

import java.io.File;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class SQLiteDbInspector {

    /**
     * Created by Mayank Choudhary on 07-05-2021.
     * mayankchoudhary00@gmail.com
     */

    /*
     *
     * This Method is used to print names of All the tables
     * and its Schema details contains in Database.
     *
     */
     public static void PrintTableSchema(Context context, String dbname) {

        Log.d("TableSchema", "printTableNames: Printing Table Schema");

        SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
        Cursor c = myDataBase.rawQuery(
                "SELECT name FROM sqlite_master WHERE type='table'AND name!='android_metadata' order by name", null);
        ArrayList<String[]> result = new ArrayList<String[]>();
        int i = 0;
        result.add(c.getColumnNames());
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String[] temp = new String[c.getColumnCount()];
            for (i = 0; i < temp.length; i++) {
                temp[i] = c.getString(i);
                System.out.println("TABLE - " + temp[i]);


                Cursor c1 = myDataBase.rawQuery(
                        "SELECT * FROM " + temp[i], null);
                c1.moveToFirst();
                String[] COLUMNS = c1.getColumnNames();
                for (int j = 0; j < COLUMNS.length; j++) {
                    c1.move(j);
                    System.out.println("    COLUMN - " + COLUMNS[j]);
                }
            }
            result.add(temp);
        }
         c.close();
         myDataBase.close();
        //PrintTableData(myDataBase,"retail_str_stock_master");
    }
    public static void PrintTableData(String dbname, String tableName) {
        SQLiteDatabase db = ApplicationContextProvider.getContext().openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
        Log.d(TAG, "getTableAsString called");
        String tableString = String.format("Table %s:\n", tableName);
        Cursor allRows  = db.rawQuery("SELECT * FROM " + tableName, null);
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
        allRows.close();
        db.close();
        Log.d("PrintingTable", "TableName: "+tableName+" : " +tableString);
    }


    public static boolean isDbOk(Context context){

        return doesDatabaseExist(context, "MasterDB") && isTableNotEmpty("group_user_master", context, "MasterDB");

     }

    public static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

    public  static boolean isTableNotEmpty(String tableName, Context context, String dbname){

        boolean isEmpty= false;
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
            String count = "SELECT * FROM "+tableName;
            Cursor mcursor = myDataBase.rawQuery(count, null);
            isEmpty = mcursor.moveToFirst();
            mcursor.close();
            myDataBase.close();
        } catch (Exception e) {
            e.printStackTrace();
            isEmpty = false;
        }
        return isEmpty;


    }

}
