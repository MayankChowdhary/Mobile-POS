package com.retailstreet.mobilepos.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.labters.lottiealertdialoglibrary.DialogTypes;
import com.retailstreet.mobilepos.Controller.ControllerUserMaster;
import com.retailstreet.mobilepos.Controller.DBReadyCallback;
import com.retailstreet.mobilepos.Database.SQLiteDbBuilder;
import com.retailstreet.mobilepos.Database.SQLiteDbInspector;
import com.retailstreet.mobilepos.Model.LicenceModulePojo;
import com.retailstreet.mobilepos.R;
import com.retailstreet.mobilepos.Utils.ApiInterface;
import com.retailstreet.mobilepos.Utils.Constants;
import com.retailstreet.mobilepos.Utils.InstallationValidator;
import com.retailstreet.mobilepos.Utils.RetroSync;
import com.retailstreet.mobilepos.View.dialog.LoadingDialog;
import com.retailstreet.mobilepos.View.dialog.LottieAlertDialogs;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

public class LoginActivity extends AppCompatActivity implements DBReadyCallback {

    EditText userName;
    EditText passWord;
    EditText storeIdInput;
    EditText terminalIdInput;
    Button login;
    static String user_id;
    static String pass;
    static String store_id;
    static String terminal_id;
    LinearLayout installationLayout;
    int versionCode;
    int liscenceCheck = -1;

    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setBackgroundDrawable(null);


        versionCode=getVersionCode();

        if(versionCode == 0){
            Log.d("NewInstall", "onCreate: Invoked");
           clearAppData();
        }else if(versionCode!=getCurrentVersion()){
            Log.d("AppUpdateFound", "onCreate: Invoked");
                clearAppData();
        }

        if (!SQLiteDbInspector.doesDatabaseExist(LoginActivity.this, "MasterDB")) {
            new SQLiteDbBuilder(LoginActivity.this);
            Log.d("DatabaseCheck", "onCreate: Database Not exists! ");
        } else {
            SQLiteDbBuilder.dbOk = true;
            Log.d("DatabaseCheck", "onCreate: Database already exists! ");
        }

        userName = findViewById(R.id.email_input);
        passWord = findViewById(R.id.password_input);
        storeIdInput=findViewById(R.id.storeid_input);
        terminalIdInput=findViewById(R.id.terminal_input);
        login = findViewById(R.id.login_btn);
        installationLayout = findViewById(R.id.installation_layout);


        if (!SQLiteDbBuilder.dbOk) {

            installationLayout.setVisibility(View.VISIBLE);

        }else if(SQLiteDbBuilder.dbOk && !SQLiteDbInspector.isTableNotEmpty("group_user_master", getApplicationContext(), "MasterDB"))
        {
            installationLayout.setVisibility(View.VISIBLE);

        } else {
            installationLayout.setVisibility(View.GONE);
        }

            login.setOnClickListener(v -> {

            user_id = userName.getText().toString().trim();
            pass = passWord.getText().toString().trim();
            store_id = storeIdInput.getText().toString().trim();
            terminal_id = terminalIdInput.getText().toString().trim();

            if (SQLiteDbBuilder.dbOk && !SQLiteDbInspector.isTableNotEmpty("group_user_master", getApplicationContext(), "MasterDB")) {

                if (user_id.isEmpty() || pass.isEmpty() || store_id.isEmpty()|| terminal_id.isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Please fill-up all fields first!", Toast.LENGTH_LONG).show();

                }else {
                    new InstallationValidator().Check_Before_Download(store_id,user_id,pass,terminal_id,LoginActivity.this,LoginActivity.this);

                }

            }else if (user_id.isEmpty() || pass.isEmpty()) {
                Toast.makeText(getApplicationContext(), "UserName or Password cannot be Empty!!", Toast.LENGTH_LONG).show();
            } else {

                if (SQLiteDbInspector.isDbOk(getApplicationContext())) {
                    Log.d("TableisReady", "onCreate: Proceeding next..");
                    boolean result = new ControllerUserMaster(getApplicationContext()).validateUserPass(user_id, pass);

                    if (result && liscenceCheck==1) {
                        Toast.makeText(getApplicationContext(), "Login Successful!", Toast.LENGTH_LONG).show();
                        saveLogin(user_id, pass);
                        Intent salesIntent = new Intent(LoginActivity.this, MainDrawerActivity.class);
                        startActivity(salesIntent);
                        finish();

                    } else if(liscenceCheck==0){
                        Toast.makeText(this,"Liscence Check failed",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Username or Password Not Matched !", Toast.LENGTH_LONG).show();

                    }
                } else {

                    Toast.makeText(getApplicationContext(), "Database Not found", Toast.LENGTH_LONG).show();
                }
            }

        });

    }

    @Override
    public void onDBReady() {
        boolean result = new ControllerUserMaster(getApplicationContext()).validateUserPass(user_id, pass);
        if (result) {
            Toast.makeText(getApplicationContext(), "Login Successful!", Toast.LENGTH_LONG).show();
            saveLogin(user_id, pass);
            Intent salesIntent = new Intent(LoginActivity.this, MainDrawerActivity.class);
            startActivity(salesIntent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Username or Password Not Matched !", Toast.LENGTH_LONG).show();

        }
    }

    private void saveLogin(String username, String password) {

        SharedPreferences sharedPreferences = getSharedPreferences("com.retailstreet.mobilepos", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("username", username);
        myEdit.putString("password", password);
        myEdit.apply();
    }
    private int getVersionCode(){
        SharedPreferences sharedPreferences = getSharedPreferences("com.retailstreet.mobilepos", MODE_PRIVATE);
        return   sharedPreferences.getInt("version", 0);

    }

    private void setVersionCode(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = LoginActivity.this.getPackageManager()
                    .getPackageInfo(LoginActivity.this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        assert packageInfo != null;
        int myVersion = packageInfo.versionCode;

        SharedPreferences sharedPreferences = getSharedPreferences("com.retailstreet.mobilepos", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putInt("version", myVersion);
        myEdit.apply();

    }

    private  int getCurrentVersion(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = LoginActivity.this.getPackageManager()
                    .getPackageInfo(LoginActivity.this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        assert packageInfo != null;
        return packageInfo.versionCode;
    }
    private void clearAppData(){
        Log.d("ClearAppData", "clearAppData:Invoked ");
        try {
            ApplicationContextProvider.getContext().deleteDatabase("MasterDB");
            SharedPreferences sharedPreferences = getSharedPreferences("com.retailstreet.mobilepos", MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.clear();
            myEdit.commit();
            setVersionCode();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*Log.d("ClearAppData", "clearAppData:Invoked ");
            ((ActivityManager)LoginActivity.this.getSystemService(ACTIVITY_SERVICE))
                    .clearApplicationUserData(); // note: it has a return value!*/
    }


    @Override
    protected void onResume() {
        super.onResume();
        String storeguid = getStoreGUID();
        if(storeguid!=null & !storeguid.trim().isEmpty()) {
            loadingDialog = new LoadingDialog();
            loadingDialog.showDialog(LoginActivity.this, "Please Wait!", "Checking Licence...");
            DailyCheck_Store_Licence(getStoreGUID());
        }
    }


    public void DailyCheck_Store_Licence(String StoreGUID) {
        ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
        Call<LicenceModulePojo> call1 = apiService.Store_Licence_Checked(Constants.Authorization, StoreGUID);
        call1.enqueue(new Callback<LicenceModulePojo>() {
            @Override
            public void onResponse(Call<LicenceModulePojo> call, Response<LicenceModulePojo> response) {
                try {
                    if(response.isSuccessful()){
                        Log.e("RC Response",response.body().toString());
                        if (response.code()==200){
                            Log.d("LicenceCheckSuccessful","Calling Activity Login");
                            String ActiveStatus =response.body().getActiveStatus();
                          //  PersistenceManager.saveLicense(SplashScreenActivity.this,response.body().getLicenseStatus());
                            String Severity =response.body().getSeverity();
                            if( ActiveStatus.equalsIgnoreCase("True") && Severity.equalsIgnoreCase("SEVERITY-S4")){

                                liscenceCheck =1;
                                loadingDialog.cancelDialog();
                                doLogin();
                            }
                            else {
                                liscenceCheck=0;
                                loadingDialog.cancelDialog();
                              LottieAlertDialogs  progressDialog= new LottieAlertDialogs.Builder(LoginActivity.this, DialogTypes.TYPE_ERROR)
                                        .setTitle("Licence check failed!")
                                        .setDescription("Contact Administrator")
                                        .build();
                                progressDialog.setCancelable(false);
                                progressDialog.show();
                            }
                        }
                    } else {
                        liscenceCheck=0;
                        loadingDialog.cancelDialog();
                        LottieAlertDialogs  progressDialog= new LottieAlertDialogs.Builder(LoginActivity.this, DialogTypes.TYPE_ERROR)
                                .setTitle("Licence check failed!")
                                .setDescription("Unknown Error")
                                .build();
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<LicenceModulePojo> call, Throwable t) {
                Log.e("RC Response error",t.toString());
                liscenceCheck =0;
                loadingDialog.cancelDialog();
                LottieAlertDialogs  progressDialog= new LottieAlertDialogs.Builder(LoginActivity.this, DialogTypes.TYPE_ERROR)
                        .setTitle("Licence check failed!")
                        .setDescription("Check your Internet connectivity...")
                        .build();
                progressDialog.setCancelable(false);
                progressDialog.show();
                t.printStackTrace();
            }
        });
    }


    private String getStoreGUID(){
        String storeGuid="";

        try {
            SQLiteDatabase mydb = openOrCreateDatabase("MasterDB",MODE_PRIVATE,null);
            Cursor cursor = mydb.rawQuery("Select STORE_GUID from retail_store",null);
            if(cursor.moveToFirst()){

                storeGuid = cursor.getString(0);

            }
        } catch (Exception e) {
            storeGuid="";
            e.printStackTrace();
        }

        Log.d("StoreID retrieved", "getStoreGUID: OnLoginCheck "+storeGuid);
        return  storeGuid;
    }

        private void doLogin() {
            SharedPreferences sh = getSharedPreferences("com.retailstreet.mobilepos", MODE_PRIVATE);
            user_id = sh.getString("username", "");
            pass = sh.getString("password", "");


            if (!user_id.isEmpty() && !pass.isEmpty()) {
                if (SQLiteDbInspector.isDbOk(getApplicationContext())) {
                    Log.d("TableisReady", "AutoLogin: Proceeding next..");
                    boolean result = new ControllerUserMaster(getApplicationContext()).validateUserPass(user_id, pass);
                    if (result) {
                        Toast.makeText(getApplicationContext(), "Auto Login Successful!", Toast.LENGTH_LONG).show();
                        Intent salesIntent = new Intent(LoginActivity.this, MainDrawerActivity.class);
                        startActivity(salesIntent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Auto Login Failed !", Toast.LENGTH_LONG).show();

                    }
                }

            }
        }
}





