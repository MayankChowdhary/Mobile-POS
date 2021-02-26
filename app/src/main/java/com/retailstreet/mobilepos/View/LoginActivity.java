package com.retailstreet.mobilepos.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.retailstreet.mobilepos.Controller.ControllerUserMaster;
import com.retailstreet.mobilepos.Controller.DBReadyCallback;
import com.retailstreet.mobilepos.Database.SQLiteDbBuilder;
import com.retailstreet.mobilepos.Database.SQLiteDbInspector;
import com.retailstreet.mobilepos.Database.TableDataInjector;
import com.retailstreet.mobilepos.R;


public class LoginActivity extends AppCompatActivity implements DBReadyCallback {

    EditText userName;
    EditText passWord;
    ImageButton login;
    static String user_id;
    static String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        if (!SQLiteDbInspector.doesDatabaseExist(getApplicationContext(), "MasterDB")) {
            LoadingDialog.showDialog(LoginActivity.this, "Please Wait", "Preparing Database...");
            new SQLiteDbBuilder(ApplicationContextProvider.getContext());
            Log.d("DatabaseCheck", "onCreate: Database Not exists! ");
        } else {
            SQLiteDbBuilder.dbOk = true;
            Log.d("DatabaseCheck", "onCreate: Database already exists! ");
        }

        userName = findViewById(R.id.email_input);
        passWord = findViewById(R.id.password_input);
        login = findViewById(R.id.login_btn);

        login.setOnClickListener(v -> {

            user_id = userName.getText().toString().trim();
            pass = passWord.getText().toString().trim();

            if (user_id.isEmpty() || pass.isEmpty()) {
                Toast.makeText(getApplicationContext(), "UserName or Password cannot be Empty!!", Toast.LENGTH_LONG).show();
            } else {

                if (SQLiteDbBuilder.dbOk && !SQLiteDbInspector.isTableNotEmpty("group_user_master", getApplicationContext(), "MasterDB")) {
                    LoadingDialog.showDialog(LoginActivity.this, "Please Wait", "Downloading Database...");
                    Log.d("TableNotYetCreated", "onCreate: Creating Table..");
                    new TableDataInjector(ApplicationContextProvider.getContext(), user_id, LoginActivity.this);

                } else if (SQLiteDbInspector.isDbOk(getApplicationContext())) {
                    Log.d("TableisReady", "onCreate: Proceeding next..");
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

    @Override
    protected void onResume() {
        super.onResume();

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





