package com.retailstreet.mobilepos.Controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.retailstreet.mobilepos.View.ApplicationContextProvider;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Mayank Choudhary on 14-06-2021.
 * mayankchoudhary00@gmail.com
 */
public class ControllerStoreParams {

    Context context;

    public ControllerStoreParams() {

        context= ApplicationContextProvider.getContext();
    }


    public boolean getIsVendorAddition(){

        boolean isEnabled = true;
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String selectQuery = "SELECT Vendoraddition FROM Store_Parameters";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                String result= cursor.getString(0);
                if(result.trim().equals("N")|| result.trim().equals("0")){
                    isEnabled = false;
                }
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getIsVendorAddition: "+ isEnabled);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isEnabled;

    }


    public boolean getIsProductAddition(){
        boolean isEnabled = true;
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String selectQuery = "SELECT Productaddition FROM Store_Parameters";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                String result= cursor.getString(0);
                if(result.trim().equals("N")|| result.trim().equals("0")){
                    isEnabled = false;
                }
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getIsProductAddition: "+ isEnabled);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isEnabled;

    }

    public boolean getIsStockEntry(){

        boolean isEnabled = true;
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String selectQuery = "SELECT StockEntry FROM Store_Parameters";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                String result= cursor.getString(0);
                if(result.trim().equals("N")|| result.trim().equals("0")){
                    isEnabled = false;
                }
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getIsStockEntry: "+ isEnabled);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isEnabled;

    }

    public boolean getIsStockAdjust(){
        boolean isEnabled = true;
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String selectQuery = "SELECT Stockadjustments FROM Store_Parameters";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                String result= cursor.getString(0);
                if(result.trim().equals("N")|| result.trim().equals("0")){
                    isEnabled = false;
                }
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getIsStockAdjust: "+ isEnabled);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isEnabled;

    }


    public boolean getVendorPayment(){
        boolean isEnabled = true;
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String selectQuery = "SELECT Vendorpayment FROM Store_Parameters";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                String result= cursor.getString(0);
                if(result.trim().equals("N")|| result.trim().equals("0")){
                    isEnabled = false;
                }
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getVendorPayment: "+ isEnabled);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isEnabled;
    }

    public boolean getVendorreturns(){
        boolean isEnabled = true;
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String selectQuery = "SELECT Vendorreturns FROM Store_Parameters";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                String result= cursor.getString(0);
                if(result.trim().equals("N")|| result.trim().equals("0")){
                    isEnabled = false;
                }
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getVendorreturns: "+ isEnabled);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isEnabled;

    }


    public boolean getCreditSale(){
        boolean isEnabled = true;
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String selectQuery = "SELECT Creditsales FROM Store_Parameters";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                String result= cursor.getString(0);
                if(result.trim().equals("N")|| result.trim().equals("0")){
                    isEnabled = false;
                }
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getVendorreturns: "+ isEnabled);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isEnabled;

    }

    public boolean getAllReportsVisibility(){
        boolean isEnabled = true;
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String selectQuery = "SELECT Reports FROM Store_Parameters";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                String result= cursor.getString(0);
                if(result.trim().equals("N")|| result.trim().equals("0")){
                    isEnabled = false;
                }
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getVendorreturns: "+ isEnabled);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isEnabled;

    }

    public boolean getIsGSTInclude(){
        boolean isEnabled = true;
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String selectQuery = "SELECT GST_Price FROM Store_Parameters";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                String result= cursor.getString(0);
                if(result.trim().equalsIgnoreCase("Exclude")){
                    isEnabled = false;
                }
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getIsGSTInclude: "+ isEnabled);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isEnabled;

    }


}
