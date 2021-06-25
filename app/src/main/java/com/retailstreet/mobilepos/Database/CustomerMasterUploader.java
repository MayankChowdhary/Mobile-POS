package com.retailstreet.mobilepos.Database;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.retailstreet.mobilepos.Controller.ControllerCustomerMaster;
import com.retailstreet.mobilepos.Model.CustomerAddress;
import com.retailstreet.mobilepos.Model.CustomerMasterUpload;
import com.retailstreet.mobilepos.Model.SyncResponse;
import com.retailstreet.mobilepos.Utils.ApiInterface;
import com.retailstreet.mobilepos.Utils.Constants;
import com.retailstreet.mobilepos.Utils.RetroSync;
import com.retailstreet.mobilepos.View.ApplicationContextProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

public class CustomerMasterUploader extends Worker {
        private ArrayList<CustomerMasterUpload> GetCustomerMasterToSync;
        private ArrayList<CustomerAddress> GetCustomerDetailToSync;
        private ControllerCustomerMaster controllerCustomerMaster;

        public CustomerMasterUploader(@NonNull Context context, @NonNull WorkerParameters workerParams) {
            super(context, workerParams);
            controllerCustomerMaster = new ControllerCustomerMaster();
        }

        @NonNull
        @Override
        public Result doWork() {
            try{
                Gson gson = new GsonBuilder().serializeNulls().create();
                GetCustomerMasterToSync = controllerCustomerMaster.getCustomerMasterSyncdata();
                String listString = gson.toJson(
                        GetCustomerMasterToSync,
                        new TypeToken<ArrayList<CustomerMasterUpload>>() {
                        }.getType());

                JSONArray jsonArray = new JSONArray();
                for (CustomerMasterUpload prod : GetCustomerMasterToSync) {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("CUSTOMERGUID", prod.getCUSTOMERGUID());
                        jsonObject.put("NAME", prod.getNAME());
                        jsonObject.put("E_MAIL", prod.getE_MAIL());
                        jsonObject.put("GENDER", prod.getGENDER());
                        jsonObject.put("AGE", prod.getAGE());
                        jsonObject.put("MOBILE_NO", prod.getMOBILE_NO());
                        jsonObject.put("SECONDARYEMAIL", prod.getSECONDARYEMAIL());
                        jsonObject.put("SECONDARYMOBILE", prod.getSECONDARYMOBILE());
                        jsonObject.put("CUSTOMERDISCOUNTPERCENTAGE", prod.getCUSTOMERDISCOUNTPERCENTAGE());
                        jsonObject.put("UPDATEDBYGUID", prod.getUPDATEDBY());
                        jsonObject.put("CREDIT_CUST", prod.getCREDIT_CUST());
                        jsonObject.put("REGISTEREDFROM", prod.getREGISTEREDFROM());
                        jsonObject.put("REGISTEREDFROMSTOREGUID", prod.getREGISTEREDFROMSTOREGUID());
                        jsonObject.put("PANNO", prod.getPANNO());
                        jsonObject.put("GSTNO", prod.getGSTNO());
                        jsonObject.put("CUSTOMERSTATUS", prod.getCUSTOMERSTATUS());
                        jsonObject.put("MASTER_CUSTOMER_TYPE", prod.getMASTER_CUSTOMER_TYPE());
                        jsonObject.put("MASTER_CUSTOMERCATEGORY", prod.getMASTER_CUSTOMERCATEGORY());
                        jsonObject.put("ADVANCE_AMOUNT", prod.getADVANCE_AMOUNT());
                        jsonObject.put("BALANCE_AMOUNT", prod.getBALANCE_AMOUNT());
                        jsonObject.put("CUSTOMERID", prod.getCUSTOMERID());
                        jsonObject.put("CREATEDBYGUID", prod.getCREATEDBY());
                        jsonObject.put("CREDITLIMIT", prod.getCREDITLIMIT());
                        jsonObject.put("MASTER_TERMINAL_ID", prod.getMASTER_TERMINAL_ID());

                        GetCustomerDetailToSync = controllerCustomerMaster.getCustomerDetailsSyncdata(prod.getCUSTOMERGUID());
                        Log.e("RC CustomerMaster :- ",jsonObject.toString());
                        JSONArray jsonArray2 = new JSONArray();
                        for (CustomerAddress prods : GetCustomerDetailToSync) {
                            JSONObject jsonObject2 = new JSONObject();
                            jsonObject2.put("CUSTOMERADDRESSID", prods.getCUSTOMERADDRESSID());
                            jsonObject2.put("MASTERCUSTOMERGUID", prods.getMASTERCUSTOMERID());
                            jsonObject2.put("ADDRESSTYPE", prods.getADDRESSTYPE());
                            jsonObject2.put("CONTACTPERSONNAME", prods.getCONTACTPERSONNAME());
                            jsonObject2.put("ADDRESSLINE1", prods.getADDRESSLINE1());
                            jsonObject2.put("ADDRESSLINE2", prods.getADDRESSLINE2());
                            jsonObject2.put("STREET_AREA", prods.getSTREET_AREA());
                            jsonObject2.put("PINCODE", prods.getPINCODE());
                            jsonObject2.put("CITY", prods.getCITY());
                            jsonObject2.put("MASTERSTATEGUID", prods.getMASTERSTATEID());
                            jsonObject2.put("ADDRESSSTATUS", prods.getADDRESSSTATUS());
                            jsonArray2.put(jsonObject2);
                        }
                        jsonObject.put("ObjAddressDetails", jsonArray2);
                        jsonArray.put(jsonObject);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.e("Rc Customer ",jsonArray.toString());
                if(jsonArray.length()>0 && GetCustomerMasterToSync.size()>0){
                    UploadCustomerRecord(jsonArray);
                }else{
                    Log.e("Customer ","No Records found to Upload");
                }
                return Result.success();
            } catch (Throwable throwable) {
                Log.e("Customer ", "Error Uploading Record ", throwable);
                return Result.failure();
            }
        }
        private void UploadCustomerRecord(JSONArray jsonArray) {
            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonArray.toString());
            Call<SyncResponse> call = apiService.UploadCustomerRecords(Constants.Authorization, body);
            call.enqueue(new Callback<SyncResponse>() {
                @Override
                public void onResponse(Call<SyncResponse> call, Response<SyncResponse> response) {
                    try {
                        Log.d("Rc Customer code:- ", String.valueOf(response.code()));
                        if (response.isSuccessful()) {
                            Log.e("Rc Response for :",response.body().getMessage() + "   " + response.body().getOutputValuesKeys());
                            String[] items = response.body().getOutputValuesKeys().split("\\s*,\\s*");
                            for (String i : items) {
                                Log.d("Output Customer ",i);
                                Boolean val = controllerCustomerMaster.updateintocustomer(i);
                                // Boolean val2 = dbHelper.updateintogrnDetail(i);
                                //Timber.tag("Rc UpdateCustomer:- ").d(val+"");
                                if(!i.isEmpty()){
                                    Toast.makeText(ApplicationContextProvider.getContext(),"Customer Successfully Synced!",Toast.LENGTH_LONG).show();

                                }else {
                                    Toast.makeText(ApplicationContextProvider.getContext(),"Customer Sync Failed!",Toast.LENGTH_LONG).show();

                                }
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
}
