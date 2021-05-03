package com.retailstreet.mobilepos.Utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.retailstreet.mobilepos.Controller.DBReadyCallback;
import com.retailstreet.mobilepos.Database.TableDataInjector;
import com.retailstreet.mobilepos.Model.DownloadcheckPojo;
import com.retailstreet.mobilepos.View.ApplicationContextProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InstallationValidator  {



    public void Check_Before_Download(final String StoreID, String UserNm, final String Password, String Terminal, DBReadyCallback activity, Context context) {
        ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
        JSONObject jsonObject2 = new JSONObject();
        try {
            jsonObject2.put("Store_Number",StoreID);
            jsonObject2.put("Username", UserNm);
            jsonObject2.put("Password", Password);
            jsonObject2.put("Terminal", Terminal);
            Log.e("Log",jsonObject2.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonObject2.toString());
        Call<DownloadcheckPojo> call1 = apiService.Check_Before_Download_Data(Constants.Authorization, body);
        call1.enqueue(new Callback<DownloadcheckPojo>() {
            @Override
            public void onResponse(Call<DownloadcheckPojo> call, Response<DownloadcheckPojo> response) {
                if(response.isSuccessful()){
                    Log.e("RC Messsagee",response.body().getMessage());
                    if (response.code()==200){
                        new TableDataInjector(context, StoreID,activity);

                        /*Data data = new Data.Builder()
                                .putString(DownLoadDataWorker.store_ID,StoreID)
                                .putString(DownLoadDataWorker.passWord,Password)
                                .build();
                        Constraints myConstraints = new Constraints.Builder()
                                .setRequiredNetworkType(NetworkType.CONNECTED)
                                .build();

                        final OneTimeWorkRequest workRequest =
                                new OneTimeWorkRequest.Builder(DownLoadDataWorker.class)
                                        .setInputData(data)
                                        .setConstraints(myConstraints)
                                        .addTag("Onetime")
                                        .build();
                        WorkManager.getInstance().enqueue(workRequest);
                        WorkManager.getInstance().getWorkInfoByIdLiveData(workRequest.getId())
                                .observe(Activity_Installation.this, new Observer<WorkInfo>() {
                                    @Override
                                    public void onChanged(@Nullable WorkInfo workInfo) {
                                        if( workInfo.getState().name().matches("SUCCEEDED"))
                                            //  startActivity(new Intent(Activity_Installation.this,SplashScreenActivity.class));
                                            startActivity(new Intent(Activity_Installation.this,login.class));

                                    }
                                });*/
                    }
                }
                else {
                    new TableDataInjector(context, StoreID,activity);
                    if(response.code()==417) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<DownloadcheckPojo>() {
                        }.getType();
                        DownloadcheckPojo errorResponse = gson.fromJson(response.errorBody().charStream(), type);
                        Log.e("Rc message", errorResponse.getMessage());
                        if(errorResponse.getMessage().equalsIgnoreCase("User Provided Info is Wrong, Pl Recheck"))
                            Toast.makeText(ApplicationContextProvider.getContext(), "User Provided Info is Wrong, Pl Recheck", Toast.LENGTH_SHORT).show();
                        else if(errorResponse.getMessage().equalsIgnoreCase("Already Registered")) {
                           //new TableDataInjector(context, StoreID,activity);
                            Toast.makeText(ApplicationContextProvider.getContext(), "Already Registered", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(ApplicationContextProvider.getContext(), "Unknown Error", Toast.LENGTH_SHORT).show();
                    }

                }
            }


            @Override
            public void onFailure(Call<DownloadcheckPojo> call, Throwable t) {
                Log.e("RC Response error",t.toString());
                t.printStackTrace();
            }
        });
    }




}
