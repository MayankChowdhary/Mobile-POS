package com.retailstreet.mobilepos.Controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.retailstreet.mobilepos.View.ApplicationContextProvider;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Mayank Choudhary on 03-06-2021.
 * mayankchoudhary00@gmail.com
 */
public class ControllerStoreConfig {

    Context context;

    public ControllerStoreConfig() {

        context= ApplicationContextProvider.getContext();
    }

    public boolean getIsIndia(){

        boolean isIndia = false;
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String selectQuery = "SELECT STR_CTR FROM retail_store";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                String result= cursor.getString(0);
                if(result.trim().equals("91")){
                    isIndia = true;
                }
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getIsIndia: "+ isIndia);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isIndia;

    }

    public  boolean getMRPVisibility(){
        boolean isVisible = true;
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String selectQuery = "SELECT MRP_VISIBILITY FROM store_configuration";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                String result= cursor.getString(0);
                if(result.trim().equals("N")|| result.trim().equals("0")){
                    isVisible = false;
                }
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getMRPVisibility: "+isVisible);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isVisible;
    }


    public  boolean getInvoiceDiscVis(){
        boolean isVisible = true;
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String selectQuery = "SELECT INVOICE_DISCOUNT_VISIBILITY FROM store_configuration";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                String result= cursor.getString(0);
                if(result.trim().equals("N")|| result.trim().equals("0")){
                    isVisible = false;
                }
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getMRPVisibility: "+isVisible);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isVisible;
    }

    public  boolean getInvoiceFreeVis(){
        boolean isVisible = true;
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String selectQuery = "SELECT FREE_QTY_VISIBILITY FROM store_configuration";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                String result= cursor.getString(0);
                if(result.trim().equals("N")|| result.trim().equals("0")){
                    isVisible = false;
                }
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getMRPVisibility: "+isVisible);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isVisible;
    }

    public  boolean getInvoiceExpiryVis(){
        boolean isVisible = true;
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String selectQuery = "SELECT EXP_DATE_VISIBILITY FROM store_configuration";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                String result= cursor.getString(0);
                if(result.trim().equals("N")|| result.trim().equals("0")){
                    isVisible = false;
                }
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getMRPVisibility: "+isVisible);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isVisible;
    }
    public  boolean getInvoiceBarcodeVis(){
        boolean isVisible = true;
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String selectQuery = "SELECT BARCODE_VISIBILITY FROM store_configuration";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                String result= cursor.getString(0);
                if(result.trim().equals("N")|| result.trim().equals("0")){
                    isVisible = false;
                }
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getMRPVisibility: "+isVisible);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isVisible;
    }

    public  String getLockPassword(){
        String  pwd = "";
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String selectQuery = "SELECT SALES_REPORT_PASSWORD FROM store_configuration";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                pwd = cursor.getString(0);

            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getMRPVisibility: "+pwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pwd;
    }

    public  boolean getCardMachine(){
        boolean isVisible = true;
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String selectQuery = "SELECT CARD_MACHINE FROM store_configuration";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                String result= cursor.getString(0);
                if(result.trim().equals("N")|| result.trim().equals("0")){
                    isVisible = false;
                }
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getCardMachine: "+isVisible);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isVisible;
    }

    public  boolean getSalesReportBanned(){
        boolean isNotBanned = true;
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String selectQuery = "SELECT ADDITIONAL_EXP9 FROM store_configuration";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                String result= cursor.getString(0);
                if(result.trim().equals("N")|| result.trim().equals("0")){
                    isNotBanned = false;
                }
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getCardMachine: "+ isNotBanned);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isNotBanned;
    }

    public  boolean getSummeryLock(){
        boolean isUnlocked = true;
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String selectQuery = "SELECT ADDITIONAL_EXP10 FROM store_configuration";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                String result= cursor.getString(0);
                if(result.trim().equals("N")|| result.trim().equals("0")){
                    isUnlocked = false;
                }
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getCardMachine: "+ isUnlocked);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUnlocked;
    }

    public  boolean getSalesReturnLock(){
        boolean isUnlocked = true;
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String selectQuery = "SELECT ADDITIONAL_EXP10 FROM store_configuration";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                String result= cursor.getString(0);
                if(result.trim().equals("N")|| result.trim().equals("0")){
                    isUnlocked = false;
                }
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getCardMachine: "+ isUnlocked);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUnlocked;
    }

}
