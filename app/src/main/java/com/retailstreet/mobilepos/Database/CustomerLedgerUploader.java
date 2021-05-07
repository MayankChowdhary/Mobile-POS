package com.retailstreet.mobilepos.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.retailstreet.mobilepos.Model.CustomerLedgerUpload;
import com.retailstreet.mobilepos.Model.SyncResponse;
import com.retailstreet.mobilepos.Utils.ApiInterface;
import com.retailstreet.mobilepos.Utils.Constants;
import com.retailstreet.mobilepos.Utils.RetroSync;
import com.retailstreet.mobilepos.View.ApplicationContextProvider;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

public class CustomerLedgerUploader extends Worker {
    private ArrayList<CustomerLedgerUpload> getCustomer_lagger_synv;

    public CustomerLedgerUploader(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }
    @NonNull
    @Override
    public Result doWork() {
        try {
            Gson gson = new GsonBuilder().serializeNulls().create();
            getCustomer_lagger_synv =getCustomerlaggersyncdata();

            String listString = gson.toJson(
                    getCustomer_lagger_synv,
                    new TypeToken<ArrayList<CustomerLedgerUpload>>() {
                    }.getType());
            JSONArray jsonArray = new JSONArray(listString);
            Log.e("RC jsonArray lagger", String.valueOf(jsonArray));
            if(jsonArray.length()>0 &&getCustomer_lagger_synv.size()>0){
                UploadCustomerladgerRecord(jsonArray);
            }else{
                Log.e("Customer ledger ","No Records found to Upload");
            }
            return Result.success();

        } catch (Throwable throwable) {
            Log.e("RetailStore", "Error Uploading Record ", throwable);
            return Result.failure();
        }
    }
    private void UploadCustomerladgerRecord(JSONArray jsonArray) {
        ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonArray.toString());
        Call<SyncResponse> call = apiService.UploadcustomerladgersRecords(Constants.Authorization, body);
        call.enqueue(new Callback<SyncResponse>() {
            @Override
            public void onResponse(Call<SyncResponse> call, Response<SyncResponse> response) {
                try {
                    //  progressDialog.dismiss();
                    Log.d("Rc lagger:- ", String.valueOf(response.code()));
                    if (response.isSuccessful()) {
                        Log.d("Rc Response for :", response.body().getMessage() + "   " + response.body().getOutputValuesKeys());
                        List<String> items = Arrays.asList(response.body().getOutputValuesKeys().split("\\s*,\\s*"));
                        for (String i : items) {
                            Log.d("Rc OutputValuesKeys:- ", i);
                            Boolean val = updateintoCustomerladger(i);
                            Log.d("Rc lagger:-", String.valueOf(val));
                        }
                        Log.d("Rc Response for :", "Record Synced Successfully");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SyncResponse> call, Throwable t) {
                //  progressDialog.dismiss();
                Log.e("In Error", t.getMessage());
            }
        });

    }


    public ArrayList<CustomerLedgerUpload> getCustomerlaggersyncdata() {
        ArrayList<CustomerLedgerUpload> productlist = new ArrayList<CustomerLedgerUpload>();
        try {
            SQLiteDatabase db  = ApplicationContextProvider.getContext().openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            Cursor productcursor = db.rawQuery(" select * from customerLedger where ISSYNCED ='0' ", null);
            if (productcursor.moveToFirst()) {
                do {
                    CustomerLedgerUpload pm = new CustomerLedgerUpload();

                    pm.setLEDGERKEY(productcursor.getString(productcursor.getColumnIndex("CUSTLEDGERID")));
                    pm.setCUSTOMERGUID(productcursor.getString(productcursor.getColumnIndex("CUSTOMERGUID")));
                    pm.setSTORE_GUID(productcursor.getString(productcursor.getColumnIndex("STORE_GUID")));
                    pm.setUSER_GUID(productcursor.getString(productcursor.getColumnIndex("USER_GUID")));
                    pm.setACTIONDATE(productcursor.getString(productcursor.getColumnIndex("ACTIONDATE")));
                    pm.setGRANDTOTAL(productcursor.getString(productcursor.getColumnIndex("GRANDTOTAL")));
                    pm.setCREDITAMOUNT(productcursor.getString(productcursor.getColumnIndex("CREDITAMOUNT")));
                    pm.setDEBITAMOUNT(productcursor.getString(productcursor.getColumnIndex("DEBITAMOUNT")));
                    pm.setBALANCEAMOUNT(productcursor.getString(productcursor.getColumnIndex("BALANCEAMOUNT")));
                    pm.setBILLNO(productcursor.getString(productcursor.getColumnIndex("BILLNO")));
                    pm.setMASTERPAYMODEGUID(productcursor.getString(productcursor.getColumnIndex("MASTERPAYMODEGUID")));
                    pm.setADDITIONALPARAM1(productcursor.getString(productcursor.getColumnIndex("ADDITIONALPARAM1")));
                    pm.setADDITIONALPARAM2(productcursor.getString(productcursor.getColumnIndex("ADDITIONALPARAM2")));
                    pm.setADDITIONALPARAM3(productcursor.getString(productcursor.getColumnIndex("ADDITIONALPARAM3")));
                    pm.setADDITIONALPARAM4(productcursor.getString(productcursor.getColumnIndex("ADDITIONALPARAM4")));
                    pm.setADDITIONALPARAM5(productcursor.getString(productcursor.getColumnIndex("ADDITIONALPARAM5")));
                    pm.setADDITIONALPARAM6(productcursor.getString(productcursor.getColumnIndex("ADDITIONALPARAM6")));

                    productlist.add(pm);
                } while (productcursor.moveToNext());
            }

            db.close();
        } catch (IndexOutOfBoundsException cur) {
            cur.printStackTrace();
        }
        return productlist;
    }
    public Boolean updateintoCustomerladger(String billno) {
        boolean returnval = true;
        SQLiteDatabase db  = ApplicationContextProvider.getContext().openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
        ContentValues value = new ContentValues();
        value.put("ISSYNCED", "1");
        int sqlUpdateRetval = db.update("customerLedger", value,
                "CUSTLEDGERID = ? "
                , new String[]{String.format(billno)});

        if (sqlUpdateRetval < 1) {
            returnval = false;
        }
        db.close();
        return returnval;
    }

}
