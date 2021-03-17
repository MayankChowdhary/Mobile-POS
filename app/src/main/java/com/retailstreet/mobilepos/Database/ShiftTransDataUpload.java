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
import com.retailstreet.mobilepos.Controller.ApiInterface;
import com.retailstreet.mobilepos.Controller.ControllerShiftTrans;
import com.retailstreet.mobilepos.Model.ShiftTrans;
import com.retailstreet.mobilepos.Model.ShiftTransUpload;
import com.retailstreet.mobilepos.Model.SyncResponse;
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

public class ShiftTransDataUpload extends Worker {


        private ArrayList<ShiftTransUpload> getShift_Trans_sync;
        private ControllerShiftTrans controllerShiftTrans;

        public ShiftTransDataUpload(@NonNull Context context, @NonNull WorkerParameters workerParams) {
            super(context, workerParams);
        }
        @NonNull
        @Override
        public Result doWork() {
            try {
                Gson gson = new GsonBuilder().serializeNulls().create();
               controllerShiftTrans =new ControllerShiftTrans();
                getShift_Trans_sync = controllerShiftTrans.get_ShiftTrans_syncdata();

                String listString = gson.toJson(
                        getShift_Trans_sync,
                        new TypeToken<ArrayList<ShiftTransUpload>>() {
                        }.getType());
                JSONArray jsonArray = new JSONArray(listString);
                Log.e("RC jsonArray ShiftTrans", String.valueOf(jsonArray));

                if(jsonArray.length()>0 &&getShift_Trans_sync.size()>0){
                    UploadShifttransRecord(jsonArray);
                }else{
                    Log.e("ShiftTrans ","No Records found to Upload");
                }
                return Result.success();

            } catch (Throwable throwable) {
                Log.e("ShiftTrans", "Error Uploading Record ", throwable);
                return Result.failure();
            }
        }
        private void UploadShifttransRecord(JSONArray jsonArray) {
            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonArray.toString());
            Call<SyncResponse> call = apiService.UploadShifttransRecords(Constants.Authorization, body);
            call.enqueue(new Callback<SyncResponse>() {
                @Override
                public void onResponse(Call<SyncResponse> call, Response<SyncResponse> response) {
                    try {
                        Log.d("Rc ShiftTrans:- ", String.valueOf(response.code()));
                        if (response.isSuccessful()) {
                            Log.d("Rc Response for :", response.body().getMessage() + "   " + response.body().getOutputValuesKeys());
                            List<String> items = Arrays.asList(response.body().getOutputValuesKeys().split("\\s*,\\s*"));
                            for (String i : items) {
                                Log.d("Rc OutputValuesKeys:- ", i);
                                Boolean val = controllerShiftTrans.updateintoShiftTrans(i);
                                Log.d("Rc ShiftTrans:-", String.valueOf(val));

                                if(!i.isEmpty()){
                                    Toast.makeText(ApplicationContextProvider.getContext(),"Shift Successfully Synced!",Toast.LENGTH_LONG).show();

                                }else {
                                    Toast.makeText(ApplicationContextProvider.getContext(),"Shift Sync Failed!",Toast.LENGTH_LONG).show();

                                }
                            }

                            Log.d("Rc Response for :", "ShiftTrans Record Synced Successfully");
                        }else {

                            Toast.makeText(ApplicationContextProvider.getContext(),"Shift Sync Failed!",Toast.LENGTH_LONG).show();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<SyncResponse> call, Throwable t) {
                    Log.e("In ShiftTrans Error", t.getMessage());
                }
            });

        }

    }

