package com.retailstreet.mobilepos.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.retailstreet.mobilepos.Controller.DBRetriever;
import com.retailstreet.mobilepos.Model.SyncResponse;
import com.retailstreet.mobilepos.Model.VendorPayDetailSync;
import com.retailstreet.mobilepos.Model.VendorPaymentMasterSync;
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

/**
 * Created by Mayank Choudhary on 04-05-2021.
 * mayankchoudhary00@gmail.com
 */
public class VendorPaymentUploader extends Worker {

    private ArrayList<VendorPaymentMasterSync> GetVendorPaymentMasterSync;
    private ArrayList<VendorPayDetailSync> GetVendorPayDetailSync;

    public VendorPaymentUploader(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            GetVendorPaymentMasterSync = getVendorPaymentMasterSync();
            JSONArray jsonArray = new JSONArray();
            for (VendorPaymentMasterSync prod : GetVendorPaymentMasterSync) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("VENDOR_PAYID", prod.getVENDOR_PAYID());
                    jsonObject.put("VENDOR_PAYGUID", prod.getVENDOR_PAYGUID());
                    jsonObject.put("MASTER_STOREGUID", prod.getMASTER_STOREGUID());
                    jsonObject.put("MASTER_VENDORGUID", prod.getMASTER_VENDORGUID());
                    jsonObject.put("INVOICENO", prod.getINVOICENO());
                    jsonObject.put("INVOICEDATE", prod.getINVOICEDATE());
                    jsonObject.put("INVOICEAMOUNT", prod.getINVOICEAMOUNT());
                    jsonObject.put("VENDORPAY_STATUS", "1");
                    jsonObject.put("CREATEDBYGUID", prod.getCREATEDBYGUID());
                    jsonObject.put("CREATEDON", prod.getCREATEDON());
                    jsonObject.put("UPDATEDBYGUID", prod.getUPDATEDBYGUID());
                    jsonObject.put("UPDATEDON", prod.getUPDATEDON());
                    jsonObject.put("DUEAMOUNT", prod.getDUEAMOUNT());
                    jsonObject.put("PAIDFOR", prod.getPAIDFOR());
                    jsonObject.put("TYPEOFINVOICE", prod.getTYPEOFINVOICE());
                    jsonObject.put("MASTER_TERMINAL_ID", prod.getMASTER_TERMINAL_ID());

                    GetVendorPayDetailSync = getVendorPayDetailSync(prod.getVENDOR_PAYGUID());
                    JSONArray jsonArray2 = new JSONArray();
                    for (VendorPayDetailSync prods : GetVendorPayDetailSync) {
                        JSONObject jsonObject2 = new JSONObject();
                        jsonObject2.put("VENDOR_PAYDETAIL_ID", prods.getVENDOR_PAYDETAIL_ID());
                        jsonObject2.put("VENDOR_PAYGUID", prods.getVENDOR_PAYGUID());
                        jsonObject2.put("BANK_GUID", prods.getBANK_GUID());
                        jsonObject2.put("AMOUNTPAID", prods.getAMOUNTPAID());
                        jsonObject2.put("PAYMENTDATE", prods.getPAYMENTDATE());
                        jsonObject2.put("PAYMODE", prods.getPAYMODE());
                        jsonObject2.put("CHEQUENO", prods.getCHEQUENO());
                        jsonObject2.put("CHEQUEDATE", prods.getCHEQUEDATE());

                        jsonArray2.put(jsonObject2);
                    }

                    jsonObject.put("ObjVendorReturnDetails", jsonArray2);
                    jsonArray.put(jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            Log.e("Rc Vendor Payment",jsonArray.toString());
            if(jsonArray.length()>0 &&GetVendorPaymentMasterSync.size()>0){
                UploadRecord(jsonArray);
            }else{
                Log.e("Vendor Payment ","No Records found to Upload");
            }
            return Result.success();
        } catch (Throwable throwable) {
            Log.e("Vendor Payment", "Error Uploading Record ", throwable);
            return Result.failure();
        }
    }

    private void UploadRecord(JSONArray jsonArray) {
        ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonArray.toString());
        Call<SyncResponse> call = apiService.UploadvendorpaymentRecords(Constants.Authorization, body);
        call.enqueue(new Callback<SyncResponse>() {
            @Override
            public void onResponse(Call<SyncResponse> call, Response<SyncResponse> response) {
                try {
                    Log.d("Rc VEndorpay code:- ", String.valueOf(response.code()));
                    if (response.isSuccessful()) {
                        Log.e("Rc Response for :",response.body().getMessage() + "   " + response.body().getOutputValuesKeys());
                        List<String> items = Arrays.asList(response.body().getOutputValuesKeys().split("\\s*,\\s*"));
                        for (String i : items) {
                            Log.d("Output VEndorpay ",i);
                            Boolean val = updateintovendpaymentMaster(i);
                            // Boolean val2 = dbHelper.updateintogrnDetail(i);
                            Log.e("Rc UpdateVEndorpay:- ",val+"");
                            Log.d("Rc Response for :", "Record Synced Successfully");

                        }
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



    public ArrayList<VendorPaymentMasterSync> getVendorPaymentMasterSync() {
        ArrayList<VendorPaymentMasterSync> productlist = new ArrayList<VendorPaymentMasterSync>();
        try {
            SQLiteDatabase   db  = ApplicationContextProvider.getContext().openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            Cursor productcursor = db.rawQuery("select VENDOR_PAYID,VENDOR_PAYGUID,STORE_GUID,VENDOR_GUID,INVOICENO,INVOICEDATE,INVOICEAMOUNT,VENDORPAY_STATUS," +
                    "CREATEDBY,CREATEDON,UPDATEDBY,UPDATEDON,DUEAMOUNT,PAIDFOR,TYPEOFINVOICE  from VendorPayMaster where ISYNCED='0'  ", null);
            if (productcursor.moveToFirst()) {
                do {
                    VendorPaymentMasterSync pm = new VendorPaymentMasterSync();
                    pm.setVENDOR_PAYID(productcursor.getString(productcursor.getColumnIndex("VENDOR_PAYID")));
                    pm.setVENDOR_PAYGUID(productcursor.getString(productcursor.getColumnIndex("VENDOR_PAYGUID")));
                    pm.setMASTER_STOREGUID(productcursor.getString(productcursor.getColumnIndex("STORE_GUID")));
                    pm.setMASTER_VENDORGUID(productcursor.getString(productcursor.getColumnIndex("VENDOR_GUID")));
                    pm.setINVOICENO(productcursor.getString(productcursor.getColumnIndex("INVOICENO")));
                    pm.setINVOICEDATE(productcursor.getString(productcursor.getColumnIndex("INVOICEDATE")));
                    pm.setINVOICEAMOUNT(productcursor.getString(productcursor.getColumnIndex("INVOICEAMOUNT")));
                    pm.setVENDORPAY_STATUS(productcursor.getString(productcursor.getColumnIndex("VENDORPAY_STATUS")));
                    pm.setCREATEDBYGUID(productcursor.getString(productcursor.getColumnIndex("CREATEDBY")));
                    pm.setCREATEDON(productcursor.getString(productcursor.getColumnIndex("CREATEDON")));
                    pm.setUPDATEDBYGUID(productcursor.getString(productcursor.getColumnIndex("UPDATEDBY")));
                    pm.setUPDATEDON(productcursor.getString(productcursor.getColumnIndex("UPDATEDON")));
                    pm.setDUEAMOUNT(productcursor.getString(productcursor.getColumnIndex("DUEAMOUNT")));
                    pm.setPAIDFOR(productcursor.getString(productcursor.getColumnIndex("PAIDFOR")));
                    pm.setTYPEOFINVOICE(productcursor.getString(productcursor.getColumnIndex("TYPEOFINVOICE")));
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

    public ArrayList<VendorPayDetailSync> getVendorPayDetailSync(String billno) {
        ArrayList<VendorPayDetailSync> productlist = new ArrayList<VendorPayDetailSync>();
        try {
            SQLiteDatabase   db  = ApplicationContextProvider.getContext().openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            Cursor productcursor = db.rawQuery(" select VENDOR_PAYDETAIL_ID,VENDOR_PAYGUID,BANK_GUID,AMOUNTPAID,PAYMENTDATE," +
                    "PAYMODE,CHEQUENO,CHEQUEDATE from VendorPayDetail  where VENDOR_PAYGUID ='" + billno + "' ", null);
            if (productcursor.moveToFirst()) {
                do {
                    VendorPayDetailSync pm = new VendorPayDetailSync();
                    pm.setVENDOR_PAYDETAIL_ID(productcursor.getString(productcursor.getColumnIndex("VENDOR_PAYDETAIL_ID")));
                    pm.setVENDOR_PAYGUID(productcursor.getString(productcursor.getColumnIndex("VENDOR_PAYGUID")));
                    pm.setBANK_GUID(productcursor.getString(productcursor.getColumnIndex("BANK_GUID")));
                    pm.setAMOUNTPAID(productcursor.getString(productcursor.getColumnIndex("AMOUNTPAID")));
                    pm.setPAYMENTDATE(productcursor.getString(productcursor.getColumnIndex("PAYMENTDATE")));
                    pm.setPAYMODE(productcursor.getString(productcursor.getColumnIndex("PAYMODE")));
                    pm.setCHEQUENO(productcursor.getString(productcursor.getColumnIndex("CHEQUENO")));
                    pm.setCHEQUEDATE(productcursor.getString(productcursor.getColumnIndex("CHEQUEDATE")));


                    productlist.add(pm);
                } while (productcursor.moveToNext());
            }

            db.close();
        } catch (IndexOutOfBoundsException cur) {
            cur.printStackTrace();
        }
        return productlist;
    }


    public Boolean updateintovendpaymentMaster(String VendorGuid) {
        boolean returnval = true;
        SQLiteDatabase   db  = ApplicationContextProvider.getContext().openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
        ContentValues value = new ContentValues();
        value.put("ISYNCED", "1");
        int sqlUpdateRetval = db.update("VendorPayMaster", value,
                " VENDOR_PAYID = ? "
                , new String[]{String.format(VendorGuid)});

        if (sqlUpdateRetval < 1) {
            returnval = false;
        }
        db.close();
        return returnval;
    }

}
