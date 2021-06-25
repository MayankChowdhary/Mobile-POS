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
import com.retailstreet.mobilepos.Controller.DBRetriever;
import com.retailstreet.mobilepos.Model.StockRegisterSync;
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
import static com.retailstreet.mobilepos.Utils.Constants.DBNAME;

/**
 * Created by Mayank Choudhary on 29-04-2021.
 * mayankchoudhary00@gmail.com
 */
public class StockRegisterUploader extends Worker {

    private ArrayList<StockRegisterSync> getInventoryLedgerToSync;

    public StockRegisterUploader(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }


    @NonNull
    @Override
    public Result doWork() {
        try {
            Gson gson = new GsonBuilder().serializeNulls().create();
            getInventoryLedgerToSync = getInventoryLedgerSyncdata();
            String listString = gson.toJson(
                    getInventoryLedgerToSync,
                    new TypeToken<ArrayList<StockRegisterSync>>() {
                    }.getType());
            JSONArray jsonArray = new JSONArray(listString);
            Log.e("RC jsonArray InvLedger", String.valueOf(jsonArray));
            if(jsonArray.length()>0 && getInventoryLedgerToSync.size()>0){
                UploadInvLedgerRecords(jsonArray);
            }else{
                Log.e("InvLedger ","No Records found to Upload");
            }
            return Result.success();

        } catch (Throwable throwable) {
            Log.e("InvLedger", "Error Uploading Record ", throwable);
            return Result.failure();
        }
    }

    private void UploadInvLedgerRecords(JSONArray jsonArray) {
        ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonArray.toString());
        Call<SyncResponse> call = apiService.UploadInventoryLedgerRecords(Constants.Authorization, body);
        call.enqueue(new Callback<SyncResponse>() {
            @Override
            public void onResponse(Call<SyncResponse> call, Response<SyncResponse> response) {
                try {
                    Log.d("InvLedger code:- ", String.valueOf(response.code()));
                    if (response.isSuccessful()) {
                        List<String> items = Arrays.asList(response.body().getOutputValuesKeys().split("\\s*,\\s*"));
                        for (String i : items) {
                            Log.d(" OutputValuesKeys:- ", i);
                            Boolean val = updateintoInv_Ledger(i);
                            Log.d(" UpdateInvLedger:- ", String.valueOf(val));

                        }
                        Log.d("Response for :", "InvLedgerRecord Synced Successfully");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SyncResponse> call, Throwable t) {
                //  progressDialog.dismiss();
                Log.e("InvLedger Error", t.getMessage());
            }
        });

    }


    public ArrayList<StockRegisterSync> getInventoryLedgerSyncdata() {
        ArrayList<StockRegisterSync> productlist = new ArrayList<StockRegisterSync>();
        try {
            SQLiteDatabase db = ApplicationContextProvider.getContext().openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE, null);
            Cursor productcursor = db.rawQuery("select distinct a.REGISTERGUID,a.MASTERORG_GUID,a.STORE_GUID,a.VENDOR_GUID,a.LINETYPE,a.TRANSACTIONTYPE,a.TRANSACTIONNUMBER,a.TRANSACTIONDATE," +
                    "a.ITEM_GUID,a.UOM_GUID,a.QUANTITY,a.BATCHNO,a.BARCODE,a.SALESPRICE,a.WHOLESALEPRICE,a.INTERNETPRICE,a.SPECIALPRICE from stock_register a where a.ISSYNCED = '0' ", null);

            if (productcursor.moveToFirst()) {
                do {
                    StockRegisterSync pm = new StockRegisterSync();

                    pm.setREGISTERGUID(productcursor.getString(productcursor.getColumnIndex("REGISTERGUID")));
                    pm.setMASTERORG_GUID(getFromRetailStore("MASTERORG_GUID"));
                    pm.setSTORE_GUID(productcursor.getString(productcursor.getColumnIndex("STORE_GUID")));
                    pm.setVENDOR_GUID(productcursor.getString(productcursor.getColumnIndex("VENDOR_GUID")));
                    pm.setLINETYPE(productcursor.getString(productcursor.getColumnIndex("LINETYPE")));
                    pm.setTRANSACTIONTYPE(productcursor.getString(productcursor.getColumnIndex("TRANSACTIONTYPE")));
                    pm.setTRANSACTIONNUMBER(productcursor.getString(productcursor.getColumnIndex("TRANSACTIONNUMBER")));
                    pm.setTRANSACTIONDATE(productcursor.getString(productcursor.getColumnIndex("TRANSACTIONDATE")));
                    pm.setITEM_GUID(productcursor.getString(productcursor.getColumnIndex("ITEM_GUID")));
                    pm.setUOM_GUID(productcursor.getString(productcursor.getColumnIndex("UOM_GUID")));
                    pm.setBATCHNO(productcursor.getString(productcursor.getColumnIndex("BATCHNO")));
                    pm.setQUANTITY(productcursor.getString(productcursor.getColumnIndex("QUANTITY")));
                    if (productcursor.getString(productcursor.getColumnIndex("BARCODE")).endsWith("\n")) {
                        pm.setBarcode(productcursor.getString(productcursor.getColumnIndex("BARCODE")).replaceAll("[\n\r]$", ""));
                    } else {
                        pm.setBarcode(productcursor.getString(productcursor.getColumnIndex("BARCODE")));
                    }
                    pm.setSALESPRICE(productcursor.getString(productcursor.getColumnIndex("SALESPRICE")));
                    pm.setWHOLESALEPRICE(productcursor.getString(productcursor.getColumnIndex("WHOLESALEPRICE")));
                    pm.setINTERNETPRICE(productcursor.getString(productcursor.getColumnIndex("INTERNETPRICE")));
                    pm.setSPECIALPRICE(productcursor.getString(productcursor.getColumnIndex("SPECIALPRICE")));
                    pm.setMASTER_TERMINAL_ID(DBRetriever.getTerminal_ID());
                    productlist.add(pm);
                } while (productcursor.moveToNext());
            }

            db.close();
        } catch (IndexOutOfBoundsException cur) {
            cur.printStackTrace();
        }
        return productlist;
    }


    public Boolean updateintoInv_Ledger(String ITEM_GUID) {
        boolean returnval = true;
        SQLiteDatabase db = ApplicationContextProvider.getContext().openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE, null);

        ContentValues value = new ContentValues();
        value.put("ISSYNCED", "1");
        int sqlUpdateRetval = db.update("stock_register", value,
                " REGISTERGUID = ? "
                , new String[]{String.format(ITEM_GUID)});

        if (sqlUpdateRetval < 1) {
            returnval = false;
        }
        db.close();
        return returnval;
    }

    private String getFromRetailStore( String column){
        String result= null;
        try {
            SQLiteDatabase  mydb  = ApplicationContextProvider.getContext().openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
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
