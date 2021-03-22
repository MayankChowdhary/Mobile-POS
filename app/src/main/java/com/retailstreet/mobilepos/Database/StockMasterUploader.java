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
import com.retailstreet.mobilepos.Controller.ControllerStockMaster;
import com.retailstreet.mobilepos.Model.StockMasterSync;
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

public class StockMasterUploader extends Worker {
    private ArrayList<StockMasterSync> getStockToSync;
    private ControllerStockMaster controllerStockMaster;

    public StockMasterUploader(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        controllerStockMaster = new ControllerStockMaster(ApplicationContextProvider.getContext());
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            Gson gson = new GsonBuilder().serializeNulls().create();
            getStockToSync = controllerStockMaster.getStockSyncdata();
            String listString = gson.toJson(
                    getStockToSync,
                    new TypeToken<ArrayList<StockMasterSync>>() {
                    }.getType());
            JSONArray jsonArray = new JSONArray(listString);
            Log.e("RC jsonArray stock", String.valueOf(jsonArray));
            if(jsonArray.length()>0 && getStockToSync.size()>0){
                UploadProductRecords(jsonArray);
            }else{
                Log.e("stock ","No Records found to Upload");
            }
            return Result.success();

        } catch (Throwable throwable) {
            Log.e("Stock", "Error Uploading Record ", throwable);
            return Result.failure();
        }
    }

    private void UploadProductRecords(JSONArray jsonArray) {
        ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonArray.toString());
        Call<SyncResponse> call = apiService.UploadStockRecords(Constants.Authorization, body);
        call.enqueue(new Callback<SyncResponse>() {
            @Override
            public void onResponse(Call<SyncResponse> call, Response<SyncResponse> response) {
                try {
                    Log.d("Rc Stock code:- ", String.valueOf(response.code()));
                    if (response.isSuccessful()) {
                        Log.d("Rc Response for :", response.body().getMessage() + "   " + response.body().getOutputValuesKeys());
                        List<String> items = Arrays.asList(response.body().getOutputValuesKeys().split("\\s*,\\s*"));
                        for (String i : items) {
                            Log.d("Rc OutputValuesKeys:- ", i);
                            Boolean val = controllerStockMaster.updateintoStock(i);
                            Log.d("Rc UpdateStock:- ", String.valueOf(val));


                            if(!i.isEmpty()){
                                Toast.makeText(ApplicationContextProvider.getContext(),"Stock Successfully Synced!",Toast.LENGTH_LONG).show();

                            }else {
                                Toast.makeText(ApplicationContextProvider.getContext(),"Stock Sync Failed!",Toast.LENGTH_LONG).show();

                            }


                        }
                        Log.d("Rc Response for :", "StockRecord Synced Successfully");
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
