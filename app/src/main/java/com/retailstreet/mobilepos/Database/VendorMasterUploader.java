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
import com.retailstreet.mobilepos.Model.SyncResponse;
import com.retailstreet.mobilepos.Model.VendorSync;
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
import static com.retailstreet.mobilepos.Utils.Constants.DBNAME;

/**
 * Created by Mayank Choudhary on 11-06-2021.
 * mayankchoudhary00@gmail.com
 */
public class VendorMasterUploader extends Worker {


    private ArrayList<VendorSync> getVendorToSync;

    public VendorMasterUploader(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            Gson gson = new GsonBuilder().serializeNulls().create();
            getVendorToSync =getVendorSyncdata();

            String listString = gson.toJson(
                    getVendorToSync,
                    new TypeToken<ArrayList<VendorSync>>() {
                    }.getType());
            JSONArray jsonArray = new JSONArray(listString);
            Log.e("Vendor ", String.valueOf(jsonArray));
            if(jsonArray.length()>0 && getVendorToSync.size()>0){
                UploadRecord(jsonArray);
            }else{
                Log.e("Vendor","No Records found to Upload");
            }            return Result.success();

        } catch (Throwable throwable) {
            Log.e("Vendor", "Error Uploading Record ", throwable);
            return Result.failure();
        }
    }

    private void UploadRecord(JSONArray jsonArray) {
        ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonArray.toString());
        Call<SyncResponse> call = apiService.UploadVendorRecords(Constants.Authorization, body);
        call.enqueue(new Callback<SyncResponse>() {
            @Override
            public void onResponse(Call<SyncResponse> call, Response<SyncResponse> response) {
                try {
                    //  progressDialog.dismiss();
                    Log.d("Rc vendorcode:- ", String.valueOf(response.code()));
                    if (response.isSuccessful()) {
                        Log.d("Rc Response for :", response.body().getMessage() + "   " + response.body().getOutputValuesKeys());
                        List<String> items = Arrays.asList(response.body().getOutputValuesKeys().split("\\s*,\\s*"));
                        for (String i : items) {
                            Log.d("Rc OutputValuesKeys:- ", i);
                            Boolean val = updateintoVendor(i);
                            Log.d("Rc UpdateVend:- ", String.valueOf(val));
                        }
                        Log.d("Rc Response for :", "Vendor Record Synced Successfully");
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



    public ArrayList<VendorSync> getVendorSyncdata() {
        ArrayList<VendorSync> productlist = new ArrayList<>();
        try {
            SQLiteDatabase db = ApplicationContextProvider.getContext().openOrCreateDatabase(DBNAME,MODE_PRIVATE, null);
            Cursor productcursor = db.rawQuery(" select distinct a.DSTR_ID,a.VENDOR_GUID,a.DSTR_NM,a.VENDOR_CATEGORY,a.VENDOR_STREET," +
                    "a.ADD_1,a.CITY,a.DSTR_CNTCT_NM,a.MOBILE,a.EMAIL,a.GST,a.PAN," +
                    "a.ZIP,a.TELE,a.MASTERCOUNTRYID,a.DSTR_INV,a.VENDORSTATE,a.PAYMENTTERMS,a.VENDOR_STATUS,a.POS_USER from retail_str_dstr a where ISSYNCED='0' ", null);

            if (productcursor.moveToFirst()) {
                do {
                    VendorSync pm = new VendorSync();

                    // {"Field":"VENDOR_STATUS"},{"Field":"CREATEDON"} where ISSYNCED='0'
                    //String User_GUID =getGrouprUserGuid(Pos)
                    pm.setVENDORID(productcursor.getString(productcursor.getColumnIndex("DSTR_ID")));//DSTR_ID
                    pm.setVENDOR_GUID(productcursor.getString(productcursor.getColumnIndex("VENDOR_GUID")));
                    pm.setORG_GUID(getFromRetailStore("MASTERORG_GUID"));//MASTERORGID
                    pm.setSTORE_GUID( getFromRetailStore("STORE_GUID"));//STORE_ID//guid
                    pm.setVENDOR_NAME(productcursor.getString(productcursor.getColumnIndex("DSTR_NM")));//DSTR_NM
                    pm.setVENDOR_CATEGORY(productcursor.getString(productcursor.getColumnIndex("VENDOR_CATEGORY")));
                    pm.setVENDOR_STREET(productcursor.getString(productcursor.getColumnIndex("VENDOR_STREET")));
                    pm.setVENDOR_ADDRESS(productcursor.getString(productcursor.getColumnIndex("ADD_1")));//ADD_1
                    pm.setCITY(productcursor.getString(productcursor.getColumnIndex("CITY")));
                    pm.setCONTACT_PERSON(productcursor.getString(productcursor.getColumnIndex("DSTR_CNTCT_NM")));//DSTR_CNTCT_NM
                    pm.setMOBILE(productcursor.getString(productcursor.getColumnIndex("MOBILE")));
                    pm.setEMAIL(productcursor.getString(productcursor.getColumnIndex("EMAIL")));
                    pm.setGST(productcursor.getString(productcursor.getColumnIndex("GST")));
                    pm.setPAN(productcursor.getString(productcursor.getColumnIndex("PAN")));
                    pm.setCREATEDBYUSERID(productcursor.getString(productcursor.getColumnIndex("POS_USER")));//POS_USER
                    pm.setISACTIVE(productcursor.getString(productcursor.getColumnIndex("VENDOR_STATUS")));
                    pm.setPINCODE(productcursor.getString(productcursor.getColumnIndex("ZIP")));//ZIP
                    pm.setTELEPHONENO(productcursor.getString(productcursor.getColumnIndex("TELE")));//TELE
                    pm.setMASTERCOUNTRYID("109F3469-EEA5-47B8-8300-62B93854D36B");////
                    pm.setISINVENTORY(productcursor.getString(productcursor.getColumnIndex("DSTR_INV")));//DSTR_INV
                    pm.setVENDORSTATE(productcursor.getString(productcursor.getColumnIndex("VENDORSTATE")));
                    pm.setPAYMENTTERMS(productcursor.getString(productcursor.getColumnIndex("PAYMENTTERMS")));

                    productlist.add(pm);
                } while (productcursor.moveToNext());
            }

            db.close();
        } catch (IndexOutOfBoundsException cur) {
            cur.printStackTrace();
        }
        return productlist;
    }

    public Boolean updateintoVendor(String VendorGuid) {
        boolean returnval = true;
        SQLiteDatabase db = ApplicationContextProvider.getContext().openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
        ContentValues value = new ContentValues();
        value.put("ISSYNCED", "1");
        int sqlUpdateRetval = db.update("retail_str_dstr", value,
                " DSTR_ID = ? "
                , new String[]{String.format(VendorGuid)});

        if (sqlUpdateRetval < 1) {
            returnval = false;
        }
        db.close();
        return returnval;

    }



    private String getFromRetailStore( String column){
        String result= null;
        try {
            SQLiteDatabase mydb  = ApplicationContextProvider.getContext().openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
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
}
