package com.retailstreet.mobilepos.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.labters.lottiealertdialoglibrary.DialogTypes;
import com.retailstreet.mobilepos.R;
import com.google.android.material.navigation.NavigationView;
import com.retailstreet.mobilepos.Utils.WorkManagerSync;
import com.retailstreet.mobilepos.View.dialog.LottieAlertDialogs;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainDrawerActivity extends AppCompatActivity  {

    private AppBarConfiguration mAppBarConfiguration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_drawer_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().setBackgroundDrawable(null);

        DrawerLayout  drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
               R.id.nav_sales)
                .setOpenableLayout(drawer)
                .build();


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController,mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(menuItem -> {
            int id=menuItem.getItemId();
            //it's possible to do more actions on several items, if there is a large amount of items I prefer switch(){case} instead of if()
            if (id==R.id.nav_logout){

                LottieAlertDialogs alertDialog= new LottieAlertDialogs.Builder(MainDrawerActivity.this, DialogTypes.TYPE_WARNING)
                        .setTitle("Logout")
                        .setDescription("Confirm Logout?")
                        .setPositiveText("Yes")
                        .setPositiveButtonColor(Color.parseColor("#297999"))
                        .setPositiveTextColor(Color.parseColor("#ffffff"))
                        .setNegativeText("No")
                        .setNegativeButtonColor(Color.parseColor("#297999"))
                        .setNegativeTextColor(Color.parseColor("#ffffff"))
                        .setPositiveListener(lottieAlertDialog -> {
                            SharedPreferences sharedPreferences = getSharedPreferences("com.retailstreet.mobilepos", MODE_PRIVATE);
                            SharedPreferences.Editor myEdit = sharedPreferences.edit();
                            myEdit.remove("username");
                            myEdit.remove("password");
                            myEdit.apply();
                            Intent loginIntent = new Intent(MainDrawerActivity.this, LoginActivity.class);
                            startActivity(loginIntent);
                            finish();
                            lottieAlertDialog.dismiss();
                        })
                        .setNegativeListener(Dialog::dismiss)
                        .build();
                alertDialog.setCancelable(true);
                alertDialog.show();
                return true;

            } if (id==R.id.nav_cart) {
                Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.action_nav_sales_to_nav_cart);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }if(id==R.id.nav_sales){
                Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.nav_sales);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
            //This is for maintaining the behavior of the Navigation view
            NavigationUI.onNavDestinationSelected(menuItem,navController);
            //This is for closing the drawer after acting on it
            drawer.closeDrawer(GravityCompat.START);
            return true;
        });

    setNavigationString();
        try {
            new WorkManagerSync();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tool_menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private  void setNavigationString(){

        String title = "Mobile: Point Of Sales";
        String subtitle = "androidstudio@android.com";

        SharedPreferences sh = getSharedPreferences("com.retailstreet.mobilepos", MODE_PRIVATE);
        String  user_id = sh.getString("username", "");

        try {
            SQLiteDatabase mydb = openOrCreateDatabase("MasterDB",MODE_PRIVATE,null);
            Cursor cursor = mydb.rawQuery("Select STR_NM, E_MAIL from retail_store",null);
            if(cursor.moveToFirst()){

                title = cursor.getString(0);
                subtitle= cursor.getString(1);

            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        NavigationView navigationView = findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.nav_title);
        nav_user.setText(title);
        TextView nav_subuser = (TextView)hView.findViewById(R.id.nav_subtitle);
        nav_subuser.setText(subtitle);


    }


}