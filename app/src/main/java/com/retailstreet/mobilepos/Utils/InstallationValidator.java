package com.retailstreet.mobilepos.Utils;

import android.app.Activity;
import android.util.Log;

import com.retailstreet.mobilepos.Controller.DBReadyCallback;
import com.retailstreet.mobilepos.Database.TableDataInjector;
import com.retailstreet.mobilepos.Model.DownloadcheckPojo;
import com.retailstreet.mobilepos.View.dialog.LoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

public class InstallationValidator  {

    public void Check_Before_Download(final String StoreID, String UserNm, final String Password, String Terminal, DBReadyCallback callback, Activity activity) {
        ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
        final LoadingDialog[] loadingDialog = {new LoadingDialog()};
        loadingDialog[0].showDialog(activity, "Please Wait!", "Validating Download...");

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
                        loadingDialog[0].cancelDialog();
                        loadingDialog[0] =null;
                        new TableDataInjector(activity, StoreID,callback,Terminal);

                    }
                }
                else {
                    loadingDialog[0].cancelDialog();
                    loadingDialog[0] =null;
                    new TableDataInjector(activity, StoreID,callback,Terminal);
                    /*if(response.code()==417) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<DownloadcheckPojo>() {
                        }.getType();
                        DownloadcheckPojo errorResponse = gson.fromJson(response.errorBody().charStream(), type);
                        Log.e("Rc message", errorResponse.getMessage());
                        if(errorResponse.getMessage().equalsIgnoreCase("User Provided Info is Wrong, Pl Recheck"))
                            Toast.makeText(ApplicationContextProvider.getContext(), "User Provided Info is Wrong, Pl Recheck", Toast.LENGTH_SHORT).show();
                        else if(errorResponse.getMessage().equalsIgnoreCase("Already Registered")) {

                            Toast.makeText(ApplicationContextProvider.getContext(), "Already Registered", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(ApplicationContextProvider.getContext(), "Unknown Error", Toast.LENGTH_SHORT).show();
                    }*/

                }
            }


            @Override
            public void onFailure(Call<DownloadcheckPojo> call, Throwable t) {
                loadingDialog[0].cancelDialog();
                loadingDialog[0] =null;
                Log.e("RC Response error",t.toString());
                t.printStackTrace();
            }
        });
    }



}

