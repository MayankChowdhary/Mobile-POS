package com.retailstreet.mobilepos.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.retailstreet.mobilepos.Model.VendorPayDetail;
import com.retailstreet.mobilepos.Model.VendorPayMaster;
import com.retailstreet.mobilepos.Utils.IDGenerator;
import com.retailstreet.mobilepos.Utils.WorkManagerSync;
import com.retailstreet.mobilepos.View.ApplicationContextProvider;

import static android.content.Context.MODE_PRIVATE;
import static com.retailstreet.mobilepos.Utils.Constants.DBNAME;

/**
 * Created by Mayank Choudhary on 04-05-2021.
 * mayankchoudhary00@gmail.com
 */


public class ControllerVendorPay {

    Context context;

    String VENDOR_PAYDETAIL_ID;
    String VENDOR_PAYGUID;
    String BANK_GUID;
    String AMOUNTPAID;
    String PAYMENTDATE;
    String PAYMODE;
    String CHEQUENO;
    String CHEQUEDATE;


    String VENDOR_PAYID;
    String VENDOR_GUID;
    String STORE_GUID;
    String INVOICENO;
    String INVOICEDATE;
    String INVOICEAMOUNT;
    String CREATEDBY;
    String UPDATEDBY;
    String CREATEDON;
    String UPDATEDON;
    String DUEAMOUNT;
    String PAIDFOR;
    String TYPEOFINVOICE;
    String ISYNCED;
    String VENDORPAY_STATUS;

    String MASTER_TERMINAL_ID;

    public ControllerVendorPay(){

        context = ApplicationContextProvider.getContext();
        MASTER_TERMINAL_ID = getTerminal_ID();

    }

    public ControllerVendorPay(String payId, String bankGuid, String amountPaid, String payMode, String chequeNo, String chequeDate, String vendorGuid, String paidFor,String typeOfInvoice){
        context = ApplicationContextProvider.getContext();
        VENDOR_PAYGUID = IDGenerator.getUUID();
        MASTER_TERMINAL_ID = getTerminal_ID();

        generateVendorPayDetail(bankGuid,amountPaid,payMode, chequeNo,chequeDate);
        generateVendorPayMaster(payId,vendorGuid,amountPaid,paidFor,typeOfInvoice);


        try {
            new WorkManagerSync(11);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateVendorPayDetail(String bankGuid,String amount, String payMode, String chequeNum,String chequeDate) {
        VENDOR_PAYDETAIL_ID = IDGenerator.getTimeStamp();
        BANK_GUID = bankGuid;
        AMOUNTPAID = amount;
        PAYMENTDATE = IDGenerator.getDateAndTime();
        PAYMODE = payMode;
        CHEQUENO = chequeNum;
        CHEQUEDATE = chequeDate;

        VendorPayDetail vendorPayDetail = new VendorPayDetail(VENDOR_PAYDETAIL_ID, VENDOR_PAYGUID, BANK_GUID, AMOUNTPAID, PAYMENTDATE, PAYMODE, CHEQUENO, CHEQUEDATE);
        InsertVendorPayDetails(vendorPayDetail);


    }


    private void generateVendorPayMaster(String payId, String vendorGuid, String invoiceAmount, String paidFor,String typeOfInvoice){
        VENDOR_PAYID = payId;
        VENDOR_GUID = vendorGuid;
        STORE_GUID = getFromRetailStore("STORE_GUID");
        INVOICENO = "0";
        INVOICEDATE = IDGenerator.getDateAndTime();
        INVOICEAMOUNT = invoiceAmount;
        CREATEDBY = getFromGroupUserMaster("USER_GUID");
        UPDATEDBY = CREATEDBY;
        CREATEDON = UPDATEDON = INVOICEDATE;
        DUEAMOUNT= "0";
        PAIDFOR = paidFor;
        TYPEOFINVOICE = typeOfInvoice;
        ISYNCED = "0";
        VENDORPAY_STATUS = "1";

        VendorPayMaster vendorPayMaster = new VendorPayMaster( VENDOR_PAYGUID,  VENDOR_PAYID,  VENDOR_GUID,  STORE_GUID,  INVOICENO,  INVOICEDATE,  INVOICEAMOUNT,  CREATEDBY,  UPDATEDBY,  CREATEDON,  UPDATEDON,  DUEAMOUNT,  PAIDFOR,  TYPEOFINVOICE,  ISYNCED,  VENDORPAY_STATUS, MASTER_TERMINAL_ID);
        InsertVendorPayMaster(vendorPayMaster);
    }


    public void InsertVendorPayDetails(VendorPayDetail prod) {
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE, null);

                ContentValues contentValues = new ContentValues();
                contentValues.put("VENDOR_PAYDETAIL_ID", prod.getVENDOR_PAYDETAIL_ID());
                contentValues.put("VENDOR_PAYGUID", prod.getVENDOR_PAYGUID());
                contentValues.put("BANK_GUID", prod.getBANK_GUID());
                contentValues.put("AMOUNTPAID", prod.getAMOUNTPAID());
                contentValues.put("PAYMENTDATE", prod.getPAYMENTDATE());
                contentValues.put("PAYMODE", prod.getPAYMODE());
                contentValues.put("CHEQUENO", prod.getCHEQUENO());
                contentValues.put("CHEQUEDATE", prod.getCHEQUEDATE());
                myDataBase.insert("VendorPayDetail", null, contentValues);
                
            myDataBase.close();
            Log.d("Insertion Successful", "VendorPayDetail: ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void InsertVendorPayMaster(VendorPayMaster prod) {

        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE, null);

                ContentValues contentValues = new ContentValues();
                contentValues.put("VENDOR_PAYID", prod.getVENDOR_PAYID());
                contentValues.put("VENDOR_PAYGUID", prod.getVENDOR_PAYGUID());
                contentValues.put("VENDOR_GUID", prod.getVENDOR_GUID());
                contentValues.put("STORE_GUID", prod.getSTORE_GUID());
                contentValues.put("INVOICENO", prod.getINVOICENO());
                contentValues.put("INVOICEDATE", prod.getINVOICEDATE());
                contentValues.put("INVOICEAMOUNT", prod.getINVOICEAMOUNT());
                contentValues.put("CREATEDBY", prod.getCREATEDBY());
                contentValues.put("UPDATEDBY", prod.getUPDATEDBY());
                contentValues.put("CREATEDON", prod.getCREATEDON());
                contentValues.put("UPDATEDON", prod.getUPDATEDON());
                contentValues.put("DUEAMOUNT", prod.getDUEAMOUNT());
                contentValues.put("PAIDFOR", prod.getPAIDFOR());
                contentValues.put("TYPEOFINVOICE", prod.getTYPEOFINVOICE());
                contentValues.put("ISYNCED", prod.getISYNCED());
                contentValues.put("VENDORPAY_STATUS", prod.getVENDORPAY_STATUS());
                contentValues.put("MASTER_TERMINAL_ID", prod.getMASTER_TERMINAL_ID());
                myDataBase.insert("VendorPayMaster", null, contentValues);


            myDataBase.close();
            Log.d("Insertion Successful", "VendorPayMaster: ");
        } catch (Exception e) {
            e.printStackTrace();
        }
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


    public void updateGRNMaster(String dueAmount, String vendorGuid,String invoiceNo){
        try {

            String query = "Update retail_str_grn_master Set GRANDAMOUNT = '"+dueAmount+"',ISSYNCED = '0'   where VENDOR_GUID = '"+vendorGuid+"'"+" AND INVOICENO = '"+invoiceNo+"'";
            SQLiteDatabase db  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            db.execSQL(query);
            db.close();
            Log.d("UpdateGRNMASTER", "UpdateGRNGrandTOTAL: Successful"+invoiceNo);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            new WorkManagerSync(9);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private String getTerminal_ID(){
        String result= null;
        try {
            SQLiteDatabase  mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
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
