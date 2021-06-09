package com.retailstreet.mobilepos.Utils;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.labters.lottiealertdialoglibrary.DialogTypes;
import com.retailstreet.mobilepos.View.dialog.LottieAlertDialogs;

import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Mayank Choudhary on 09-06-2021.
 * mayankchoudhary00@gmail.com
 */
public class ConnectionInspector {

    Context context;
    Fragment fragment;
    LottieAlertDialogs progressDialog;
    Handler handler = new Handler(Looper. getMainLooper());

    public ConnectionInspector(Context context, Fragment fragment){

        this.context = context;
        this.fragment = fragment;
    }

    public  void  inspect(){

        if(!isNetConnected()){
            showErrorDialog();
        }else {
            handler. post(() -> {
            Toast.makeText(context,"Internet Check Success!", Toast.LENGTH_SHORT).show();
            });
        }
    }


    public boolean isNetOn() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
        public boolean isNetConnected() {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if( cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()){
                Log.d("Internet is Enabled", "isNetConnected: Invoked!");
                try {
                    InetAddress ipAddr = InetAddress.getByName("www.google.com");
                    //You can replace it with your name
                    Log.d("Internet is Working", "isNetConnected: Invoked! ="+ipAddr);
                    return !ipAddr.equals("");
                } catch (Exception e) {
                    Log.d("Internet is Not Working", "isNetConnected: Invoked!");
                    e.printStackTrace();
                    return false;
                }

            }else {
                Log.d("Internet is NOT Enabled", "isNetConnected: Invoked!");
            }

            return false;
    }


    public  void showErrorDialog(){

        handler. post(() -> {
            progressDialog= new LottieAlertDialogs.Builder(context, DialogTypes.TYPE_ERROR)
                    .setTitle("Connection Error!")
                    .setDescription("Please Connect to Internet First.")
                    .setPositiveText("RETRY")
                    .setNegativeText("EXIT")
                    .setPositiveButtonColor(Color.parseColor("#297999"))
                    .setPositiveTextColor(Color.parseColor("#ffffff"))
                    .setNegativeButtonColor(Color.parseColor("#297999"))
                    .setNegativeTextColor(Color.parseColor("#ffffff"))
                    .setPositiveListener(dialog -> {
                        dialog.dismiss();
                        ExecutorService service = Executors.newSingleThreadExecutor();
                        service.submit(this::inspect);
                    })
                    .setNegativeListener(dialog -> {
                        dialog.dismiss();
                        NavHostFragment.findNavController(fragment).navigateUp();
                    })
                    .build();
            progressDialog.setCancelable(false);
            progressDialog.show();
            });
    }
}
