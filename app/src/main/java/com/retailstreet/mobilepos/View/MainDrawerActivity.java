package com.retailstreet.mobilepos.View;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.retailstreet.mobilepos.Database.SQLiteDbInspector;
import com.retailstreet.mobilepos.R;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Currency;

public class MainDrawerActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_drawer_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().setBackgroundDrawable(null);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_sales, R.id.nav_cart, R.id.nav_checkout, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            int id=menuItem.getItemId();
            //it's possible to do more actions on several items, if there is a large amount of items I prefer switch(){case} instead of if()
            if (id==R.id.nav_logout){

                new AlertDialog.Builder(MainDrawerActivity.this)
                        .setTitle("Logout")
                        .setMessage("Confirm Logout?")
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> {

                            SharedPreferences sharedPreferences = getSharedPreferences("com.retailstreet.mobilepos", MODE_PRIVATE);
                            SharedPreferences.Editor myEdit = sharedPreferences.edit();
                            myEdit.remove("username");
                            myEdit.remove("password");
                            myEdit.apply();
                            Intent loginIntent = new Intent(MainDrawerActivity.this, LoginActivity.class);
                            startActivity(loginIntent);
                            finish();
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

                return true;

            } if (id==R.id.nav_cart) {
                Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.action_nav_sales_to_nav_cart);
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


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tool_menu, menu);
       /* Drawable cartdrawable = menu.findItem(R.id.appCart).getIcon();
        cartdrawable = DrawableCompat.wrap(cartdrawable);
        DrawableCompat.setTint(cartdrawable, ContextCompat.getColor(this,R.color.white));
        menu.findItem(R.id.appCart).setIcon(cartdrawable);*/
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

        SQLiteDatabase mydb = openOrCreateDatabase("MasterDB",MODE_PRIVATE,null);
        Cursor cursor = mydb.rawQuery("Select DESCRIPTION, E_MAIL from retail_store where STORE_NUMBER = "+user_id,null);
        if(cursor.moveToFirst()){

            title = cursor.getString(0);
            subtitle= cursor.getString(1);

        }
        NavigationView navigationView = findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.nav_title);
        nav_user.setText(title);
        TextView nav_subuser = (TextView)hView.findViewById(R.id.nav_subtitle);
        nav_subuser.setText(subtitle);


    }

}