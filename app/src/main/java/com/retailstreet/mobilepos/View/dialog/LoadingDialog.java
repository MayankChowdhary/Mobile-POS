package com.retailstreet.mobilepos.View.dialog;

import android.content.Context;

import com.labters.lottiealertdialoglibrary.DialogTypes;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */


public class LoadingDialog {

     LottieAlertDialogs progressDialog;


    public  void showDialog(Context context,String message, String title){
    progressDialog= new LottieAlertDialogs.Builder(context, DialogTypes.TYPE_LOADING)
                .setTitle(title)
                .setDescription(message)
                .build();
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    public void cancelDialog(){

        progressDialog.dismiss();
    }
}
