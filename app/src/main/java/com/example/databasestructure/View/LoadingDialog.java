package com.example.databasestructure.View;

import android.app.ProgressDialog;
import android.content.Context;

public class LoadingDialog {

    static ProgressDialog progressDialog;


    public static void showDialog(Context context,String message, String title){
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message); // Setting Message
        progressDialog.setTitle(title); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);

    }

    public static void cancelDialog(){

        progressDialog.dismiss();
    }
}
