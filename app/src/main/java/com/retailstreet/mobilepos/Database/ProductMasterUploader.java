package com.retailstreet.mobilepos.Database;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.retailstreet.mobilepos.Controller.ControllerProductMaster;
import com.retailstreet.mobilepos.Model.ProductMasterSync;
import com.retailstreet.mobilepos.Model.SyncResponse;
import com.retailstreet.mobilepos.Utils.ApiInterface;
import com.retailstreet.mobilepos.Utils.Constants;
import com.retailstreet.mobilepos.Utils.RetroSync;
import com.retailstreet.mobilepos.Utils.WorkManagerSync;
import com.retailstreet.mobilepos.View.ApplicationContextProvider;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductMasterUploader extends Worker {
        private ArrayList<ProductMasterSync> getProductToSync;
        private ControllerProductMaster controllerProductMaster;

        public ProductMasterUploader(@NonNull Context context, @NonNull WorkerParameters workerParams) {
            super(context, workerParams);
            controllerProductMaster = new ControllerProductMaster();

            Log.d("ProductMasterSync", "ProductMasterUploader: Invoked ");

        }

        @NonNull
        @Override
        public Result doWork() {
            try {
                Gson gson = new Gson();
                getProductToSync =controllerProductMaster.getProductSyncdata();
                String listString = gson.toJson(
                        getProductToSync,
                        new TypeToken<ArrayList<ProductMasterSync>>() {
                        }.getType());
                JSONArray jsonArray = new JSONArray(listString);
                Log.e("RC jsonArray Product", String.valueOf(jsonArray));
                if(jsonArray.length()>0 && getProductToSync.size()>0){
                    UploadProductRecords(jsonArray);
                }else{
                    Log.e("Product ","No Records found to Upload");
                }
                return Result.success();

            } catch (Throwable throwable) {
                Log.e("Product ", "Error Uploading Record ", throwable);
                return Result.failure();
            }
        }

        private void UploadProductRecords(JSONArray jsonArray) {
            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonArray.toString());
            Call<SyncResponse> call = apiService.UploadproductRecords(Constants.Authorization, body);


            call.enqueue(new Callback<SyncResponse>() {
                @Override
                public void onResponse(Call<SyncResponse> call, Response<SyncResponse> response) {
                    try { Log.d("Rc Prod code:- ", String.valueOf(response.code()));
                        if (response.isSuccessful()) {
                            Log.d("Rc Response for local :", response.body().getMessage() + "   " + response.body().getOutputValuesKeys());
                            List<String> items = Arrays.asList(response.body().getOutputValuesKeys().split("\\s*,\\s*"));
                            for (String i : items) {
                                Log.d("Rc OutputValuesKeys:- ", i);
                                Boolean val = controllerProductMaster.updateintoProduct(i);
                                Log.d("Rc UpdateLocalProd:- ", String.valueOf(val));

                                if(!i.isEmpty()){
                                    Toast.makeText(ApplicationContextProvider.getContext(),"Product Successfully Synced!",Toast.LENGTH_LONG).show();

                                }else {
                                    Toast.makeText(ApplicationContextProvider.getContext(),"Product Sync Failed!",Toast.LENGTH_LONG).show();

                                }

                            }
                            Log.d("Rc Response for :", "Record Synced Successfully");

                            try {
                                new WorkManagerSync(4);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else{
                            Log.d("Rc Prod code:- ", String.valueOf(response.raw().body()));
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

