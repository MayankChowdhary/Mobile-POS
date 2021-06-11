package com.retailstreet.mobilepos.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.retailstreet.mobilepos.Model.VendorMaster;
import com.retailstreet.mobilepos.Utils.IDGenerator;
import com.retailstreet.mobilepos.Utils.WorkManagerSync;
import com.retailstreet.mobilepos.View.ApplicationContextProvider;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;
import static com.retailstreet.mobilepos.Utils.Constants.DBNAME;

/**
 * Created by Mayank Choudhary on 10-06-2021.
 * mayankchoudhary00@gmail.com
 */
    public class ControllerVendorMaster {

        Context context = ApplicationContextProvider.getContext();

        private  String DSTR_ID;
        private  String VENDOR_GUID;
        private  String MASTERORGID;
        private  String STORE_ID;
        private  String VENDOR_CATEGORY;
        private  String DSTR_NM;
        private  String VENDOR_STREET;
        private  String ADD_1;
        private  String CITY;
        private  String DSTR_CNTCT_NM;
        private  String MOBILE;
        private  String EMAIL;
        private  String GST;
        private  String PAN;
        private  String ZIP;
        private  String TELE;
        private  String VENDOR_STATUS;
        private  String POS_USER;
        private  String CREATEDON;
        private  String MASTERCOUNTRYID;
        private  String DSTR_INV;
        private  String VENDORSTATE;
        private  String PAYMENTTERMS;
        private  String ISSYNCED;


    public  ControllerVendorMaster(){

    }

    public  ControllerVendorMaster(String name, String addr,String city, String mob, String email, String gst, String pan, String zip, String tele,String inventory, String state, String payterm){
        DSTR_ID = IDGenerator.getTimeStamp();
        VENDOR_GUID = IDGenerator.getUUID();
        MASTERORGID = getFromRetailStore("MASTERORG_GUID");
        STORE_ID = getFromRetailStore("STORE_GUID");
        VENDOR_CATEGORY = "";
        DSTR_NM = name;
        VENDOR_STREET = "";
        ADD_1 = addr;
        CITY = city;
        DSTR_CNTCT_NM = name;
        MOBILE = mob;
        EMAIL = email;
        GST = gst;
        PAN = pan;
        ZIP = zip;
        TELE = tele;
        VENDOR_STATUS = "1";
        POS_USER = getFromGroupUserMaster("USER_GUID");
        CREATEDON = getSaleDateAndTime();
        MASTERCOUNTRYID = getFromRetailStore("STR_CTR");
        DSTR_INV = inventory;
        VENDORSTATE = state;
        PAYMENTTERMS = payterm;
        ISSYNCED = "0";

       VendorMaster vendorMaster= new VendorMaster( DSTR_ID,  VENDOR_GUID,  MASTERORGID,  STORE_ID,  VENDOR_CATEGORY,  DSTR_NM,  VENDOR_STREET,  ADD_1,  CITY,  DSTR_CNTCT_NM,  MOBILE,  EMAIL,  GST,  PAN,  ZIP,  TELE,  VENDOR_STATUS,  POS_USER,  CREATEDON,  MASTERCOUNTRYID,  DSTR_INV,  VENDORSTATE,  PAYMENTTERMS,  ISSYNCED);
       InsertVendorMaster(vendorMaster);

        try {
            new WorkManagerSync(13);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void InsertVendorMaster(VendorMaster prod) {

        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE, null);
                ContentValues contentValues = new ContentValues();
                contentValues.put("DSTR_ID", prod.getDSTR_ID());
                contentValues.put("VENDOR_GUID", prod.getVENDOR_GUID());
                contentValues.put("MASTERORGID", prod.getMASTERORGID());
                contentValues.put("STORE_ID", prod.getSTORE_ID());
                contentValues.put("VENDOR_CATEGORY", prod.getVENDOR_CATEGORY());
                contentValues.put("DSTR_NM", prod.getDSTR_NM());
                contentValues.put("VENDOR_STREET", prod.getVENDOR_STREET());
                contentValues.put("ADD_1", prod.getADD_1());
                contentValues.put("CITY", prod.getCITY());
                contentValues.put("DSTR_CNTCT_NM", prod.getDSTR_CNTCT_NM());
                contentValues.put("MOBILE", prod.getMOBILE());
                contentValues.put("EMAIL", prod.getEMAIL());
                contentValues.put("GST", prod.getGST());
                contentValues.put("PAN", prod.getPAN());
                contentValues.put("ZIP", prod.getZIP());
                contentValues.put("TELE", prod.getTELE());
                contentValues.put("VENDOR_STATUS", prod.getVENDOR_STATUS());
                contentValues.put("POS_USER", prod.getPOS_USER());
                contentValues.put("CREATEDON", prod.getCREATEDON());
                contentValues.put("MASTERCOUNTRYID", prod.getMASTERCOUNTRYID());
                contentValues.put("DSTR_INV", prod.getDSTR_INV());
                contentValues.put("VENDORSTATE", prod.getVENDORSTATE());
                contentValues.put("PAYMENTTERMS", prod.getPAYMENTTERMS());
                contentValues.put("ISSYNCED", prod.getISSYNCED());
                myDataBase.insert("retail_str_dstr", null, contentValues);

            Log.d("Insertion Successful", "InsertVendorMater: ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    

    private String getFromGroupUserMaster(String column){
        String result= null;
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String username;
            SharedPreferences sh = context.getSharedPreferences("com.retailstreet.mobilepos", MODE_PRIVATE);
            username = sh.getString("username", "");

            result = "";
            String selectQuery = "SELECT "+column+" FROM group_user_master where USER_NAME ='"+username+"'";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {

                result= cursor.getString(0);
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getFromUserMaster: "+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }







    private String getFromRetailStore( String column){
        String result= null;
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            result = "";
            String selectQuery = "SELECT "+column+" FROM retail_store ";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {

                result= cursor.getString(0);
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getFromRetailStore: "+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    private String getSaleDateAndTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return formatter.format(date);
    }


    }
