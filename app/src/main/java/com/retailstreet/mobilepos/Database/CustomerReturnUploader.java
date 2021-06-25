package com.retailstreet.mobilepos.Database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.retailstreet.mobilepos.Controller.ControllerCustomerReturn;
import com.retailstreet.mobilepos.Model.CustomerReturnDetails;
import com.retailstreet.mobilepos.Model.CustomerReturnMaster;
import com.retailstreet.mobilepos.Model.SyncResponse;
import com.retailstreet.mobilepos.Utils.ApiInterface;
import com.retailstreet.mobilepos.Utils.Constants;
import com.retailstreet.mobilepos.Utils.RetroSync;

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

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

public class CustomerReturnUploader extends Worker {

    private ArrayList<CustomerReturnMaster> GetReturnMasterToSync;
    private ArrayList<CustomerReturnDetails> GetReturnDetailToSync;
    ControllerCustomerReturn customerReturn;

    public CustomerReturnUploader(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        customerReturn = new ControllerCustomerReturn();

    }

    @NonNull
    @Override
    public Result doWork() {
        try {

            Gson gson = new GsonBuilder().serializeNulls().create();
            GetReturnMasterToSync = customerReturn.getSalesReturnMasterdata();
            JSONArray jsonArray = new JSONArray();
            for (CustomerReturnMaster prod : GetReturnMasterToSync) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("CUSTOMER_RETURNS_MASTERID", prod.getCUSTOMER_RETURNS_MASTERID());
                    jsonObject.put("CUSTOMERRETURNGUID", prod.getCUSTOMERRETURNGUID());
                    jsonObject.put("MASTERREASONGUID", prod.getREASONGUID());
                    jsonObject.put("MASTERSTOREGUID", prod.getMASTERSTOREID());
                    jsonObject.put("MASTERCUSTOMERGUID", prod.getCUSTOMERGUID());
                    jsonObject.put("BILLNO", prod.getBILLNO());
                    jsonObject.put("SALESDATE", prod.getSALESDATE());
                    jsonObject.put("REASONDETAILS", prod.getREASONDETAILS());
                    jsonObject.put("RETURN_DATE", prod.getRETURN_DATE());
                    jsonObject.put("ISPARTIALRETURN", prod.getISPARTIALRETURN());
                    jsonObject.put("AMOUNTREFUNDED", prod.getAMOUNTREFUNDED());
                    jsonObject.put("REPLACEMENTBILLNO", prod.getREPLACEMENTBILLNO());
                    jsonObject.put("CREDITNOTENUMBER", prod.getCREDITNOTENUMBER());
                    jsonObject.put("CUSTOMER_RETURNS_STATUS", prod.getCUSTOMER_RETURNS_STATUS());
                    jsonObject.put("CREATEDBYGUID", prod.getCREATEDBYGUID());
                    jsonObject.put("CREATEDON", prod.getCREATEDON());
                    jsonObject.put("MASTER_TERMINAL_ID", prod.getMASTER_TERMINAL_ID());


                    GetReturnDetailToSync = customerReturn.getSalereturnDetailsSyncdata(prod.getCUSTOMERRETURNGUID());
                    JSONArray jsonArray2 = new JSONArray();
                    for (CustomerReturnDetails prods : GetReturnDetailToSync) {
                        JSONObject jsonObject2 = new JSONObject();
                        jsonObject2.put("CUSTOMER_RETURNS_MASTER_GUID", prods.getCUSTOMER_RETURNS_MASTER_ID());
                        jsonObject2.put("MASTER_PRODUCT_ITEM_GUID", prods.getMASTER_PRODUCT_ITEM_ID());
                        jsonObject2.put("BATCHNO", prods.getBATCHNO());
                        jsonObject2.put("RETURNQUANTITY", prods.getRETURNQUANTITY());
                        jsonObject2.put("EXPIRYDATE", prods.getEXPIRYDATE());
                        jsonObject2.put("CUSTOMER_RETURN_DETAIL_STATUS", prods.getCUSTOMER_RETURN_DETAIL_STATUS());
                        jsonArray2.put(jsonObject2);
                    }

                    jsonObject.put("ObjCustomerReturnDetails", jsonArray2);
                    jsonArray.put(jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            Log.e("SalesReturnMaster ",jsonArray.toString());
            if(jsonArray.length()>0 && GetReturnMasterToSync.size()>0){
                UploadRecord(jsonArray);
            }else{
                Log.e("SalesReturnMaster ","No Records found to Upload");
            }
            return Result.success();
        } catch (Throwable throwable) {
            Log.e("SalesReturnMaster", "Error Uploading Record ", throwable);
            return Result.failure();
        }
    }

    private void UploadRecord(JSONArray jsonArray) {
        ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonArray.toString());
        Call<SyncResponse> call = apiService.UploadSalesReturnRecords(Constants.Authorization, body);
        call.enqueue(new Callback<SyncResponse>() {
            @Override
            public void onResponse(Call<SyncResponse> call, Response<SyncResponse> response) {
                try {
                    Log.d("SalesReturn ", " code:- "+response.code());
                    if (response.isSuccessful()) {
                        Log.e("SalesReturn","Response for :"+response.body().getMessage() + "   " + response.body().getOutputValuesKeys());
                        List<String> items = Arrays.asList(response.body().getOutputValuesKeys().split("\\s*,\\s*"));
                        for (String i : items) {
                            Log.d("Output Return ",i);
                            Boolean val = customerReturn.updateintoreturnmaster(i);
                            Log.e("UpdateSalesReturn:- ",val+"");
                            Log.d("Response for :", "SalesReturn Record Synced Successfully");

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SyncResponse> call, Throwable t) {
                //  progressDialog.dismiss();
                Log.e("In SalesReturn Error", t.getMessage());
            }
        });
    }


}
