package com.retailstreet.mobilepos.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.retailstreet.mobilepos.Model.SyncResponse;
import com.retailstreet.mobilepos.Model.VendorDetailReturnSync;
import com.retailstreet.mobilepos.Model.VendorReturnMasterSync;
import com.retailstreet.mobilepos.Utils.ApiInterface;
import com.retailstreet.mobilepos.Utils.Constants;
import com.retailstreet.mobilepos.Utils.RetroSync;
import com.retailstreet.mobilepos.View.ApplicationContextProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
 * Created by Mayank Choudhary on 11-05-2021.
 * mayankchoudhary00@gmail.com
 */
public class VendorReturnUploader extends Worker {
    private ArrayList<VendorReturnMasterSync> GetVendor_return_master_sync;
    private ArrayList<VendorDetailReturnSync> GetVendor_return_detail_sync;

    public VendorReturnUploader(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            GetVendor_return_master_sync = getVendor_return_master_sync();

            JSONArray jsonArray = new JSONArray();
            for (VendorReturnMasterSync prod : GetVendor_return_master_sync) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("VENDOR_RETURN_MASTERID", prod.getVENDOR_RETURN_MASTERID());
                    jsonObject.put("VENDOR_RETURNGUID", prod.getVENDOR_RETURNGUID());
                    jsonObject.put("MASTER_STORE_GUID", prod.getMASTER_STORE_GUID());
                    jsonObject.put("MASTER_VENDOR_GUID", prod.getMASTER_VENDOR_GUID());
                    jsonObject.put("REASON", prod.getREASON());
                    jsonObject.put("RETURN_DATE", prod.getRETURN_DATE());
                    jsonObject.put("GRNNO", prod.getGRNNO());
                    jsonObject.put("GRNDATE", prod.getGRNDATE());
                    jsonObject.put("INVOICENO", prod.getINVOICENO());
                    jsonObject.put("INVOICEDATE", prod.getINVOICEDATE());
                    jsonObject.put("VENDOR_RETURN_STATUS", prod.getVENDOR_RETURN_STATUS());
                    jsonObject.put("CREATEDBYGUID", prod.getCREATEDBYGUID());
                    jsonObject.put("CREATEDON", prod.getCREATEDON());
                    jsonObject.put("UPDATEDBYGUID", prod.getUPDATEDBYGUID());
                    jsonObject.put("UPDATEDON", prod.getUPDATEDON());
                    jsonObject.put("MASTER_TERMINAL_ID", prod.getMASTER_TERMINAL_ID());


                    // jsonObject.put("ISSYNCED", "0");
                    GetVendor_return_detail_sync = getVendor_return_detail_sync(prod.getVENDOR_RETURNGUID());
                    JSONArray jsonArray2 = new JSONArray();
                    for (VendorDetailReturnSync prods : GetVendor_return_detail_sync) {
                        JSONObject jsonObject2 = new JSONObject();
                        jsonObject2.put("VENDOR_RETURN_MASTER_GUID", prods.getVENDOR_RETURN_MASTER_GUID());
                        jsonObject2.put("MASTER_PRODUCT_ITEM_GUID", prods.getMASTER_PRODUCT_ITEM_GUID());
                        jsonObject2.put("BATCHNO", prods.getBATCHNO());
                        jsonObject2.put("QTY", prods.getQTY());
                        jsonObject2.put("MASTER_UOM_GUID", prods.getMASTER_UOM_GUID());
                        jsonArray2.put(jsonObject2);
                    }

                    jsonObject.put("ObjVendorReturnDetails", jsonArray2);
                    jsonArray.put(jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            Log.e("Vendor return",jsonArray.toString());
            if(jsonArray.length()>0 &&GetVendor_return_master_sync.size()>0){
                UploadRecord(jsonArray);
            }else{
                Log.e("Vendor return","No Records found to Upload");
            }
            return Result.success();
        } catch (Throwable throwable) {
            Log.e("Vendor return", "Error Uploading Record ", throwable);
            return Result.failure();
        }
    }

    private void UploadRecord(JSONArray jsonArray) {
        ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonArray.toString());
        Call<SyncResponse> call = apiService.UploadVendor_Return_Records(Constants.Authorization, body);
        call.enqueue(new Callback<SyncResponse>() {
            @Override
            public void onResponse(Call<SyncResponse> call, Response<SyncResponse> response) {
                try {
                    Log.d("Rc Vendorreturn code:- ", String.valueOf(response.code()));
                    if (response.isSuccessful()) {
                        Log.e("Rc Response for :",response.body().getMessage() + "   " + response.body().getOutputValuesKeys());
                        List<String> items = Arrays.asList(response.body().getOutputValuesKeys().split("\\s*,\\s*"));
                        for (String i : items) {
                            Log.d("Output Vendorreturn ",i);
                            Boolean val = updateintovendorreturn(i);
                            // Boolean val2 = dbHelper.updateintogrnDetail(i);
                            Log.e("Rc UpdateVendreturn:- ",val+"");
                            Log.d("Rc Response for :", "Vendorreturn Record Synced Successfully");

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SyncResponse> call, Throwable t) {
                //  progressDialog.dismiss();
                Log.e("Vendorreturn Error", t.getMessage());
            }
        });
    }


    public ArrayList<VendorReturnMasterSync> getVendor_return_master_sync() {
        ArrayList<VendorReturnMasterSync> productlist = new ArrayList<VendorReturnMasterSync>();
        try {
            SQLiteDatabase db = ApplicationContextProvider.getContext().openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE, null);
            Cursor productcursor = db.rawQuery(" select * from retail_str_vendor_master_return where ISSYNCED='0' ", null);
            if (productcursor.moveToFirst()) {
                do {
                    VendorReturnMasterSync pm = new VendorReturnMasterSync();
                    pm.setVENDOR_RETURN_MASTERID(productcursor.getString(productcursor.getColumnIndex("VENDOR_RETURN_MASTERID")));
                    pm.setVENDOR_RETURNGUID(productcursor.getString(productcursor.getColumnIndex("VENDOR_RETURNGUID")));
                    pm.setMASTER_STORE_GUID(getFromRetailStore("STORE_GUID"));
                    pm.setMASTER_VENDOR_GUID(productcursor.getString(productcursor.getColumnIndex("VENDOR_GUID")));
                    pm.setREASON(productcursor.getString(productcursor.getColumnIndex("REASON")));
                    pm.setRETURN_DATE(productcursor.getString(productcursor.getColumnIndex("RETURN_DATE")));
                    pm.setGRNNO(productcursor.getString(productcursor.getColumnIndex("GRNNO")));
                    pm.setGRNDATE(productcursor.getString(productcursor.getColumnIndex("GRNDATE")));
                    pm.setINVOICENO(productcursor.getString(productcursor.getColumnIndex("INVOICENO")));
                    pm.setINVOICEDATE(productcursor.getString(productcursor.getColumnIndex("INVOICEDATE")));
                    pm.setVENDOR_RETURN_STATUS("1");
                    pm.setCREATEDBYGUID(productcursor.getString(productcursor.getColumnIndex("CREATEDBY")));
                    pm.setCREATEDON(productcursor.getString(productcursor.getColumnIndex("CREATEDON")));
                    pm.setUPDATEDBYGUID(productcursor.getString(productcursor.getColumnIndex("UPDATEDBY")));
                    pm.setUPDATEDON(productcursor.getString(productcursor.getColumnIndex("UPDATEDON")));
                    pm.setMASTER_TERMINAL_ID(getTerminal_ID());
                    productlist.add(pm);
                } while (productcursor.moveToNext());
            }

            db.close();
        } catch (IndexOutOfBoundsException cur) {
            cur.printStackTrace();
        }
        return productlist;
    }


    public ArrayList<VendorDetailReturnSync> getVendor_return_detail_sync(String billno) {
        ArrayList<VendorDetailReturnSync> productlist = new ArrayList<>();
        try {
            SQLiteDatabase db = ApplicationContextProvider.getContext().openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE, null);
            Cursor productcursor = db.rawQuery(" select b.VENDOR_RETURNGUID,b.ITEM_GUID," +
                    "b.BATCHNO,b.QTY,b.UOM_GUID   from  retail_str_vendor_detail_return b  where b.VENDOR_RETURNGUID='" + billno + "' ", null);
            if (productcursor.moveToFirst()) {

                do {
                    VendorDetailReturnSync pm = new VendorDetailReturnSync();
                    pm.setVENDOR_RETURN_MASTER_GUID(productcursor.getString(productcursor.getColumnIndex("VENDOR_RETURNGUID")));
                    pm.setMASTER_PRODUCT_ITEM_GUID(productcursor.getString(productcursor.getColumnIndex("ITEM_GUID")));
                    pm.setBATCHNO(productcursor.getString(productcursor.getColumnIndex("BATCHNO")));
                    pm.setQTY(productcursor.getString(productcursor.getColumnIndex("QTY")));
                    pm.setMASTER_UOM_GUID(productcursor.getString(productcursor.getColumnIndex("UOM_GUID")));
                    productlist.add(pm);
                } while (productcursor.moveToNext());
            }

            db.close();
        } catch (IndexOutOfBoundsException cur) {
            cur.printStackTrace();
        }
        return productlist;
    }

    public Boolean updateintovendorreturn(String VendorGuid) {
        boolean returnval = true;
        SQLiteDatabase db = ApplicationContextProvider.getContext().openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE, null);
        ContentValues value = new ContentValues();
        value.put("ISSYNCED", "1");
        int sqlUpdateRetval = db.update("retail_str_vendor_master_return", value,
                " VENDOR_RETURN_MASTERID = ? "
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

    private String getTerminal_ID(){
        String result= null;
        try {
            SQLiteDatabase  mydb  = ApplicationContextProvider.getContext().openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
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