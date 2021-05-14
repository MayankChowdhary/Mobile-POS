package com.retailstreet.mobilepos.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.retailstreet.mobilepos.Model.CustomerLedger;
import com.retailstreet.mobilepos.Utils.IDGenerator;
import com.retailstreet.mobilepos.Utils.WorkManagerSync;
import com.retailstreet.mobilepos.View.ApplicationContextProvider;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.retailstreet.mobilepos.Utils.Constants.DBNAME;


/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

public class ControllerCreditPay {

    Context context;
    String GRANDTOTAL;
    String RECEIVEAMOUNT;
    String DUEAMOUNT;

    String CUSTLEDGERID;
    String CUSTOMERGUID;
    String STORE_GUID;
    String USER_GUID;
    String ACTIONDATE;
   //String GRANDTOTAL;
    String CREDITAMOUNT;
    String DEBITAMOUNT;
    String BALANCEAMOUNT;
    String BILLNO;
    String ISSYNCED;
    String MASTERPAYMODEGUID;
    String ADDITIONALPARAM1;
    String ADDITIONALPARAM2;
    String ADDITIONALPARAM3;
    String ADDITIONALPARAM4;
    String ADDITIONALPARAM5;
    String ADDITIONALPARAM6;



    public ControllerCreditPay( ) {

        context = ApplicationContextProvider.getContext();

    }

    public void updateCreditCustomer(String creditTaken, String custGuid ) {

        context = ApplicationContextProvider.getContext();

        try {
            String currentCreditTotal = getFromCreditCust(custGuid,"GRANDTOTAL");
            String newTotal = String.valueOf(Double.parseDouble(currentCreditTotal)+Double.parseDouble(creditTaken));
            UpdateCreditCust2(newTotal,custGuid);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


    }

    public  void updateCustLedger( String creditTaken, String custGuid, String billnum){

        ADDITIONALPARAM1 = "";
        ADDITIONALPARAM2 = "";
        ADDITIONALPARAM3 = "";
        ISSYNCED = "0";
        BILLNO = billnum;
        CUSTOMERGUID = custGuid;
        STORE_GUID = getFromRetailStore("STORE_GUID");
        USER_GUID = getFromGroupUserMaster("USER_GUID");
        ACTIONDATE = getDateAndTime();
        GRANDTOTAL = creditTaken;
        DEBITAMOUNT = GRANDTOTAL;
        BALANCEAMOUNT = GRANDTOTAL;
        ADDITIONALPARAM4 = "";
        ADDITIONALPARAM5 = "";
        ADDITIONALPARAM6 = "";
        CUSTLEDGERID = IDGenerator.getTimeStamp();
        ADDITIONALPARAM1 = BILLNO;
        MASTERPAYMODEGUID="23FD7DBD-2386-423A-A0FB-8C97477BBEA7";
        RECEIVEAMOUNT = creditTaken;
        CREDITAMOUNT = "0";


        CustomerLedger customerLedger = new CustomerLedger( CUSTLEDGERID,  CUSTOMERGUID,  STORE_GUID,  USER_GUID,  ACTIONDATE,  GRANDTOTAL,  CREDITAMOUNT,  DEBITAMOUNT,  BALANCEAMOUNT,  BILLNO,  ISSYNCED,  MASTERPAYMODEGUID,  ADDITIONALPARAM1,  ADDITIONALPARAM2,  ADDITIONALPARAM3,  ADDITIONALPARAM4,  ADDITIONALPARAM5,  ADDITIONALPARAM6);
        InsertCustomerLedger(customerLedger);
        try {
            new WorkManagerSync(8);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public  void updateCustAdditionLedger(  String custGuid, String advance){

        ADDITIONALPARAM1 = "";
        ADDITIONALPARAM2 = advance;
        ADDITIONALPARAM3 = "";
        ISSYNCED = "0";
        BILLNO = " ";
        CUSTOMERGUID = custGuid;
        STORE_GUID = getFromRetailStore("STORE_GUID");
        USER_GUID = getFromGroupUserMaster("USER_GUID");
        ACTIONDATE = getDateAndTime();
        GRANDTOTAL = "0";
        DEBITAMOUNT = "0";
        BALANCEAMOUNT = "0";
        ADDITIONALPARAM4 = "";
        ADDITIONALPARAM5 = "";
        ADDITIONALPARAM6 = "";
        CUSTLEDGERID = IDGenerator.getTimeStamp();
        ADDITIONALPARAM1 = "";
        MASTERPAYMODEGUID="";
        RECEIVEAMOUNT = "0";
        CREDITAMOUNT = "0";

        CustomerLedger customerLedger = new CustomerLedger( CUSTLEDGERID,  CUSTOMERGUID,  STORE_GUID,  USER_GUID,  ACTIONDATE,  GRANDTOTAL,  CREDITAMOUNT,  DEBITAMOUNT,  BALANCEAMOUNT,  BILLNO,  ISSYNCED,  MASTERPAYMODEGUID,  ADDITIONALPARAM1,  ADDITIONALPARAM2,  ADDITIONALPARAM3,  ADDITIONALPARAM4,  ADDITIONALPARAM5,  ADDITIONALPARAM6);
        InsertCustomerLedger(customerLedger);
        try {
            new WorkManagerSync(8);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public  void updateAdvanceSettlement( String advance, String custGuid, String billnum) {

        ADDITIONALPARAM1 = "";
        ADDITIONALPARAM2 = "";
        ADDITIONALPARAM3 = "";
        ISSYNCED = "0";
        BILLNO = billnum;
        CUSTOMERGUID = custGuid;
        STORE_GUID = getFromRetailStore("STORE_GUID");
        USER_GUID = getFromGroupUserMaster("USER_GUID");
        ACTIONDATE = getDateAndTime();
        GRANDTOTAL = "0";
        DEBITAMOUNT = "0";
        BALANCEAMOUNT = "0";
        ADDITIONALPARAM4 = "";
        ADDITIONALPARAM5 = "";
        ADDITIONALPARAM6 = advance;
        CUSTLEDGERID = IDGenerator.getTimeStamp();
        ADDITIONALPARAM1 = "";
        MASTERPAYMODEGUID="";
        RECEIVEAMOUNT = "0";
        CREDITAMOUNT = "0";

        CustomerLedger customerLedger = new CustomerLedger( CUSTLEDGERID,  CUSTOMERGUID,  STORE_GUID,  USER_GUID,  ACTIONDATE,  GRANDTOTAL,  CREDITAMOUNT,  DEBITAMOUNT,  BALANCEAMOUNT,  BILLNO,  ISSYNCED,  MASTERPAYMODEGUID,  ADDITIONALPARAM1,  ADDITIONALPARAM2,  ADDITIONALPARAM3,  ADDITIONALPARAM4,  ADDITIONALPARAM5,  ADDITIONALPARAM6);
        InsertCustomerLedger(customerLedger);
        try {
            new WorkManagerSync(8);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ControllerCreditPay(String custId, String amount){
        context = ApplicationContextProvider.getContext();
        ADDITIONALPARAM1 = "0";
        ADDITIONALPARAM2 = "0";
        ADDITIONALPARAM3 = "0";
        ISSYNCED = "0";
        BILLNO = getBillNumber();
        CUSTOMERGUID = custId;
        STORE_GUID = getFromRetailStore("STORE_GUID");
        USER_GUID = getFromGroupUserMaster("USER_GUID");
        ACTIONDATE = getDateAndTime();
        DEBITAMOUNT = "0.00";
        BALANCEAMOUNT = "0.00";
        ADDITIONALPARAM4 = "";
        ADDITIONALPARAM5 = "";
        ADDITIONALPARAM6 = "";
        CUSTLEDGERID = IDGenerator.getTimeStamp();
        ADDITIONALPARAM1 = BILLNO;
        MASTERPAYMODEGUID="E123BBBE-A000-4617-AD49-9B5AF2275F43";
        RECEIVEAMOUNT = amount;
        CREDITAMOUNT = RECEIVEAMOUNT;
        GRANDTOTAL = getFromCreditCust(custId,"GRANDTOTAL");
        DUEAMOUNT = String.valueOf(Math.abs(Double.parseDouble(GRANDTOTAL)) - Double.parseDouble(RECEIVEAMOUNT));
        GRANDTOTAL = DUEAMOUNT;
        UpdateCreditCust(GRANDTOTAL,custId,RECEIVEAMOUNT,DUEAMOUNT);
        CustomerLedger customerLedger = new CustomerLedger( CUSTLEDGERID,  CUSTOMERGUID,  STORE_GUID,  USER_GUID,  ACTIONDATE,  GRANDTOTAL,  CREDITAMOUNT,  DEBITAMOUNT,  BALANCEAMOUNT,  BILLNO,  ISSYNCED,  MASTERPAYMODEGUID,  ADDITIONALPARAM1,  ADDITIONALPARAM2,  ADDITIONALPARAM3,  ADDITIONALPARAM4,  ADDITIONALPARAM5,  ADDITIONALPARAM6);
        InsertCustomerLedger(customerLedger);

        try {
            new WorkManagerSync(8);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ControllerCreditPay( String custGuid , HashMap<String , String[]> payModeData) {
        context = ApplicationContextProvider.getContext();
        ADDITIONALPARAM1 = "0";
        ADDITIONALPARAM2 = "0";
        ADDITIONALPARAM3 = "0";

        ISSYNCED = "0";
        BILLNO = getBillNumber();
        CUSTOMERGUID = custGuid;
        STORE_GUID = getFromRetailStore("STORE_GUID");
        USER_GUID = getFromGroupUserMaster("USER_GUID");
        ACTIONDATE = getDateAndTime();
        DEBITAMOUNT = "0.00";
        BALANCEAMOUNT = "0.00";
        ADDITIONALPARAM4 = "";
        ADDITIONALPARAM5 = "";
        ADDITIONALPARAM6 = "";


        for (Map.Entry<String,String[]> entry : payModeData.entrySet()) {
            String[] data = entry.getValue();
            CUSTLEDGERID = IDGenerator.getTimeStamp();
            Log.d("AddingBillPay", "GenerateBillPayDetail: "+entry.getKey());

            if(data[1]!=null && !data[1].trim().isEmpty()) {
                ADDITIONALPARAM1 = data[1];
            }else {
                ADDITIONALPARAM1 = BILLNO;
            }

            MASTERPAYMODEGUID=data[0];
            RECEIVEAMOUNT = data[2];
            CREDITAMOUNT = RECEIVEAMOUNT;

            if(!data[3].trim().isEmpty()) {
                ADDITIONALPARAM1 = data[3];
            }else {
                ADDITIONALPARAM1 = "0";
            }

            if(!data[4].trim().isEmpty()) {
                ADDITIONALPARAM2 = data[4];
            }else {
                ADDITIONALPARAM2 = "0";
            }

            if(!data[5].trim().isEmpty()) {
                ADDITIONALPARAM3 = data[5];
            }else {
                ADDITIONALPARAM3 = "0";
            }

            GRANDTOTAL = getFromCreditCust(custGuid,"GRANDTOTAL");
            DUEAMOUNT = String.valueOf(Math.abs(Double.parseDouble(GRANDTOTAL)) - Double.parseDouble(RECEIVEAMOUNT));
            GRANDTOTAL = DUEAMOUNT;

            UpdateCreditCust(GRANDTOTAL,custGuid,RECEIVEAMOUNT,DUEAMOUNT);
            CustomerLedger customerLedger = new CustomerLedger( CUSTLEDGERID,  CUSTOMERGUID,  STORE_GUID,  USER_GUID,  ACTIONDATE,  GRANDTOTAL,  CREDITAMOUNT,  DEBITAMOUNT,  BALANCEAMOUNT,  BILLNO,  ISSYNCED,  MASTERPAYMODEGUID,  ADDITIONALPARAM1,  ADDITIONALPARAM2,  ADDITIONALPARAM3,  ADDITIONALPARAM4,  ADDITIONALPARAM5,  ADDITIONALPARAM6);
            InsertCustomerLedger(customerLedger);
        }

        try {
            new WorkManagerSync(8);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void InsertCustomerLedger(CustomerLedger prod) {
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE, null);
                ContentValues contentValues = new ContentValues();
                contentValues.put("CUSTLEDGERID", prod.getCUSTLEDGERID());
                contentValues.put("CUSTOMERGUID", prod.getCUSTOMERGUID());
                contentValues.put("STORE_GUID", prod.getSTORE_GUID());
                contentValues.put("USER_GUID", prod.getUSER_GUID());
                contentValues.put("ACTIONDATE", prod.getACTIONDATE());
                contentValues.put("GRANDTOTAL", prod.getGRANDTOTAL());
                contentValues.put("CREDITAMOUNT", prod.getCREDITAMOUNT());
                contentValues.put("DEBITAMOUNT", prod.getDEBITAMOUNT());
                contentValues.put("BALANCEAMOUNT", prod.getBALANCEAMOUNT());
                contentValues.put("BILLNO", prod.getBILLNO());
                contentValues.put("ISSYNCED", prod.getISSYNCED());
                contentValues.put("MASTERPAYMODEGUID", prod.getMASTERPAYMODEGUID());
                contentValues.put("ADDITIONALPARAM1", prod.getADDITIONALPARAM1());
                contentValues.put("ADDITIONALPARAM2", prod.getADDITIONALPARAM2());
                contentValues.put("ADDITIONALPARAM3", prod.getADDITIONALPARAM3());
                contentValues.put("ADDITIONALPARAM4", prod.getADDITIONALPARAM4());
                contentValues.put("ADDITIONALPARAM5", prod.getADDITIONALPARAM5());
                contentValues.put("ADDITIONALPARAM6", prod.getADDITIONALPARAM6());
                myDataBase.insert("customerLedger", null, contentValues);
                
            Log.d("Insertion Successful", "CustomerLedger: ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void UpdateCreditCust(String grand, String custGuid, String received, String dueAmount){
        try {

            String query = "Update retail_credit_cust Set GRANDTOTAL = '"+grand+"' , RECEIVEAMOUNT = '"+received+"', DUEAMOUNT = '"+dueAmount+"'  where CUSTOMERGUID = '"+custGuid+"'";
            SQLiteDatabase db  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            db.execSQL(query);
            db.close();
            Log.d("UpdateStockQty", "UpdateQuantity: Successful"+custGuid);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void UpdateCreditCust2(String grand, String custGuid){
        try {

            String query = "Update retail_credit_cust Set GRANDTOTAL = '"+grand+"'  where CUSTOMERGUID = '"+custGuid+"'";
            SQLiteDatabase db  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            db.execSQL(query);
            db.close();
            Log.d("UpdateStockQty", "UpdateQuantity: Successful"+custGuid);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    private String getFromCreditCust( String custGuid,String column) {
        String result= "0.00";
        try {
            SQLiteDatabase  mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            result = "";
            String selectQuery = "SELECT "+column+" FROM retail_credit_cust where CUSTOMERGUID ='"+custGuid+"'";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {

                result= cursor.getString(0);
            }
            cursor.close();
            mydb.close();
            if(result==null || result.isEmpty())
                result = "0.00";
            Log.d("DataRetrieved", "getFromStockMaster: "+column+": "+result);
        } catch (Exception e) {
            result = "0.00";
            e.printStackTrace();
        }
        return result;
    }


    private String getBillNumber() {

        int count= 0;
        try {
            SQLiteDatabase db = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);          //Opens database in writable mode.
            String query = "SELECT COUNT(BILLNO) from retail_str_sales_master";
            //long numRows = DatabaseUtils.queryNumEntries(db, "retail_str_sales_master");
            Cursor cursor = db.rawQuery(query,null);
            if(cursor.moveToFirst()){

                count= cursor.getInt(0);
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(count);
    }

    private String getFromRetailStore( String column){
        String result= null;
        try {
            SQLiteDatabase  mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
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

    private String getDateAndTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return formatter.format(date);
    }


}


