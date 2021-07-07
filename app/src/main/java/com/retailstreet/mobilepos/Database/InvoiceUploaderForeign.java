package com.retailstreet.mobilepos.Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.retailstreet.mobilepos.Controller.InvoiceGenerator;
import com.retailstreet.mobilepos.Model.BillDetail;
import com.retailstreet.mobilepos.Model.BillMasterUpload;
import com.retailstreet.mobilepos.Model.BillPayInvoice;
import com.retailstreet.mobilepos.Model.SMS_SYNC;
import com.retailstreet.mobilepos.Utils.ApiInterface;
import com.retailstreet.mobilepos.Utils.RetroSync;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

public class InvoiceUploaderForeign extends Worker {
        Context context;
        private  ArrayList<BillMasterUpload> GetBillMasterToSync = new ArrayList<>();
        private  ArrayList<BillDetail> GetBillDetailToSync = new ArrayList<>();
        private  ArrayList<BillPayInvoice> GetBillPaymentDetailToSync = new ArrayList<>();
        public static String Bill_No = null;
        private static Set<String> smsSetFromPrefs = new HashSet<>();
        InvoiceGenerator invoiceGenerator;

        public InvoiceUploaderForeign(@NonNull Context context, @NonNull WorkerParameters workerParams) {
            super(context, workerParams);
            this.context  = context;
        }

        @NonNull
        @Override
        public Result doWork() {
            try {

                GetSetFromPrefs();
                Bill_No = getInputData().getString("BillNo");
                Log.d("BillNoReceived", "doWork: "+Bill_No);
                invoiceGenerator= new InvoiceGenerator();
                this.GetBillDetailToSync = invoiceGenerator.getBillDetailsSyncdata(Bill_No);
                this.GetBillMasterToSync = invoiceGenerator.getBillMasterSyncdataforSMS(Bill_No);
                this.GetBillPaymentDetailToSync = invoiceGenerator.getBillPaymentDetails(Bill_No);


                JSONArray jsonArray = new JSONArray();
                for (BillMasterUpload prod : GetBillMasterToSync) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("BillType","Sales Bill for Invoice");
                    jsonObject.put("STORENAME", getFromRetailStore("STR_NM"));
                    jsonObject.put("STOREADDRESS", getFromRetailStore("ADD_1"));
                    jsonObject.put("STORECITY", getFromRetailStore("CTY"));
                    jsonObject.put("STOREMOBILENO", getFromRetailStore("TELE"));
                    jsonObject.put("VAT_NUMBER",getFromRetailStore("GSTIN_NUMBER"));//changed
                    jsonObject.put("CUSTOMERNAME", getFromMasterCustomer( prod.getCUST_MOBILENO(),"NAME"));
                    jsonObject.put("CUSTOMERMOBILENO", prod.getCUST_MOBILENO());
                    jsonObject.put("CASHIERNAME", getFromGroupUserMaster("USER_NAME",prod.getUSER_GUID()));
                    jsonObject.put("MACHINENUMBER", getFromTerminalConfig(getFromRetailStore("STORE_GUID"),"TERMINAL_NAME"));
                    jsonObject.put("BILLNO", prod.getBILLNO());
                    jsonObject.put("TOTAL_AMOUNT", prod.getTOTAL_AMOUNT());
                    jsonObject.put("SALEDATE", prod.getSALEDATE());
                    jsonObject.put("SALETIME", prod.getSALETIME());


                    Log.e("detailsize for sms:- ", String.valueOf(GetBillDetailToSync.size()));
                    JSONArray jsonArray2 = new JSONArray();
                    for (BillDetail prods : GetBillDetailToSync) {
                        JSONObject jsonObject2 = new JSONObject();
                        jsonObject2.put("ITEMNAME", getFromProductMaster(prods.getITEM_GUID(),"PROD_NM"));
                        jsonObject2.put("SPRICE", prods.getCOST_PRICE());
                        jsonObject2.put("QTY", prods.getQTY());
                        jsonObject2.put("TOTALVALUE", prods.getTOTALVALUE());
                        jsonObject2.put("VATPERCENTAGE", prods.getIGSTPERCENTAGE());
                        jsonObject2.put("VAT",prods.getIGST());
                        jsonObject2.put("TOTALVAT",prods.getTOTALGST());
                        jsonArray2.put(jsonObject2);
                    }
                    jsonObject.put("ObjBillDetails", jsonArray2);
                 //   GetBillPaymentDetailToSync = dbHelper.getBillPaymentDetails(prod.getBID());
                    JSONArray jsonArray3 = new JSONArray();
                    for (BillPayInvoice paydetail : GetBillPaymentDetailToSync) {
                        Log.e("RC paydetailsize:- ", String.valueOf(GetBillPaymentDetailToSync.size()));
                        JSONObject jsonObject3 = new JSONObject();
                        jsonObject3.put("PAYMODE",getFromPayModeMaster( paydetail.getMASTERPAYMODEGUID(),"PAYMODE"));
                        jsonObject3.put("PAYAMOUNT", paydetail.getPAYAMOUNT());
                        jsonArray3.put(jsonObject3);
                    }
                    jsonObject.put("ObjBillPaymentDetails", jsonArray3);
                    jsonArray.put(jsonObject);
                }
                Log.e("Rc SalesSMS length", String.valueOf(jsonArray.toString()));
                if(jsonArray.length()>0 && GetBillMasterToSync.size()>0){
                    UploadRecord(jsonArray);
                }else{
                    Log.e("Rc SalesSMS","No Records to Upload");
                }
                return Result.success();
            } catch (Throwable throwable) {
                Log.e("SalesSMS", "Error Uploading Record ", throwable);
                return Result.failure();
            }
        }

        private void UploadRecord(JSONArray jsonArray) {
            ApiInterface apiService = RetroSync.getClient().create(ApiInterface.class);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonArray.toString());
            Call<SMS_SYNC> call = apiService.UploadSaleRecordsSMS(body);
            call.enqueue(new Callback<SMS_SYNC>() {
                @Override
                public void onResponse(Call<SMS_SYNC> call, Response<SMS_SYNC> response) {
                    try {
                        //  progressDialog.dismiss();
                        Log.d("Rc salesSMS:- ", response.raw().toString());
                        if (response.isSuccessful()) {
                            Log.d("Rc Response for sales :", "SMS " + (response.body() != null ? response.body().getData() : null));
                            smsSetFromPrefs.remove(Bill_No);
                            Log.d("SMSNoRemoved", "onResponse: " + Bill_No);
                            SaveSetsInPrefs();
                            Toast.makeText(context, "SMS Successfully Synced!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<SMS_SYNC> call, Throwable t) {
                    //  progressDialog.dismiss();
                    Log.e("In sales sms Error", t.getMessage());
                }
            });
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


    private String getFromMasterCustomer(String mobile, String column){
        String result= null;
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            result = "";
            String selectQuery = "SELECT "+column+" FROM retail_cust where MOBILE_NO ='"+mobile+"'";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {

                result= cursor.getString(0);
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getFromCustomerMaster: "+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }
    private String getFromGroupUserMaster(String column, String guid){
        String result= null;
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            result = "";
            String selectQuery = "SELECT "+column+" FROM group_user_master where USER_GUID ='"+guid+"'";
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
    private String getFromTerminalConfig(String storeId, String column){
        String result= null;
        try {
            SQLiteDatabase  mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            result = "";
            String selectQuery = "SELECT "+column+" FROM terminal_configuration where STORE_GUID ='"+storeId+"'";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {

                result= cursor.getString(0);
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getFromCustomerMaster: "+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    private String getFromProductMaster( String itemGuid, String column) {

        String result = null;
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            result = "";
            String selectQuery = "SELECT "+column+" FROM retail_store_prod_com where ITEM_GUID ='"+itemGuid+"'";
            Cursor cursor = mydb.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {

                result = cursor.getString(0);
            }
            cursor.close();
            Log.d("DataRetrieved", "getFromProductMaster: "+column+":"+result);
            mydb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private String getFromPayModeMaster( String paymodeGuid, String column) {

        String result = null;
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            result = "";
            String selectQuery = "SELECT "+column+" FROM masterpaymode where PAYMODE_GUID ='"+paymodeGuid+"'";
            Cursor cursor = mydb.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {

                result = cursor.getString(0);
            }
            cursor.close();
            Log.d("DataRetrieved", "getFromPAyModeMaster: "+column+":"+result);
            mydb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void GetSetFromPrefs()
    {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        smsSetFromPrefs = sharedPref.getStringSet("smsSync", new HashSet<String>() );
    }

    @SuppressLint("ApplySharedPref")
    public void SaveSetsInPrefs()
    {

        if(smsSetFromPrefs.isEmpty()){
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.remove("smsSync");
            editor.commit();
        }else {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putStringSet("smsSync", smsSetFromPrefs);
            editor.commit();

            for (String data : smsSetFromPrefs) {

                Log.d("Saved Sms Keys", "SaveSetsInPrefs: " + data);
            }
        }
    }

}






