package com.retailstreet.mobilepos.View;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.labters.lottiealertdialoglibrary.DialogTypes;
import com.retailstreet.mobilepos.R;
import com.retailstreet.mobilepos.Utils.WorkManagerSync;
import com.retailstreet.mobilepos.View.ExpandableNavigation.ExpandableListAdapter;
import com.retailstreet.mobilepos.View.ExpandableNavigation.MenuModel;
import com.retailstreet.mobilepos.View.dialog.LottieAlertDialogs;
import com.retailstreet.mobilepos.View.ui.Home.HomeFragmentDirections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MainDrawerActivity extends AppCompatActivity  {

    private AppBarConfiguration mAppBarConfiguration;
    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_drawer_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().setBackgroundDrawable(null);
        //Thread.setDefaultUncaughtExceptionHandler(new CrashHandler(this));
        expandableListView = findViewById(R.id.expandableListView);
        prepareMenuData();
        populateExpandableList();

         drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_sales, R.id.nav_dayopen, R.id.nav_dayclose, R.id.nav_products, R.id.nav_customer, R.id.nav_customer_update, R.id.nav_sales_refund, R.id.nav_credit_pay, R.id.nav_home, R.id.nav_sales_report,R.id.nav_vendor_update,R.id.nav_stock_update,R.id.nav_purchase_Invoice,R.id.nav_vendor_Payment,R.id.nav_vi_payment_fragment)
                .setOpenableLayout(drawer)
                .build();


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        navigationView.setNavigationItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();
            //it's possible to do more actions on several items, if there is a large amount of items I prefer switch(){case} instead of if()
            if (id == R.id.nav_logout) {


                return true;

            }

            //This is for maintaining the behavior of the Navigation view
            NavigationUI.onNavDestinationSelected(menuItem, navController);
            //This is for closing the drawer after acting on it
            drawer.closeDrawer(GravityCompat.START);
            return true;
        });

    }

    private void prepareMenuData() {

        MenuModel menuModel = new MenuModel("Reception", true, false, R.drawable.reception); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }


        menuModel = new MenuModel("Billing", true, true,  R.drawable.billing);
        headerList.add(menuModel);
        List<MenuModel> childModelsList = new ArrayList<>();
        MenuModel childModel = new MenuModel("Sales", false, false, R.drawable.product);
        childModelsList.add(childModel);

        childModel = new MenuModel("Sales Return", false, false, R.drawable.returning);
        childModelsList.add(childModel);


        if (menuModel.hasChildren) {
            Log.d("API123","here");
            childList.put(menuModel, childModelsList);
        }


        menuModel = new MenuModel("Inventory", true, true,  R.drawable.inventory);
        headerList.add(menuModel);
        childModelsList = new ArrayList<>();
        childModel = new MenuModel("Add Product", false, false, R.drawable.add_product);
        childModelsList.add(childModel);
        childModel = new MenuModel("Stock Update", false, false, R.drawable.stockupdate);
        childModelsList.add(childModel);
        childModel = new MenuModel("Purchase Invoice", false, false, R.drawable.invoice);
        childModelsList.add(childModel);
        childModel = new MenuModel("Petty Cash", false, false, R.drawable.petty_cash);
        childModelsList.add(childModel);
        childModel = new MenuModel("Vendor Payment", false, false, R.drawable.vendor_payment);
        childModelsList.add(childModel);
        childModel = new MenuModel("Vendor Details", false, false, R.drawable.vendor);
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            Log.d("API123","here");
            childList.put(menuModel, childModelsList);
        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("Credit Management", true, true,  R.drawable.creditmanagement); //Menu of Python Tutorials
        headerList.add(menuModel);
        childModel = new MenuModel("Credit Pay", false, false, R.drawable.credit);
        childModelsList.add(childModel);

        childModel = new MenuModel("Customer Addition", false, false, R.drawable.customer);
        childModelsList.add(childModel);
        childModel = new MenuModel("Customer Update", false, false, R.drawable.customer_update);
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("Cashier", true, true,  R.drawable.cashier); //Menu of Python Tutorials
        headerList.add(menuModel);
        childModel = new MenuModel("Day Open", false, false, R.drawable.open);
        childModelsList.add(childModel);

        childModel = new MenuModel("Day Close", false, false, R.drawable.close);
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("Reports", true, true, R.drawable.reports); //Menu of Python Tutorials
        headerList.add(menuModel);
        childModel = new MenuModel("Sales Reports", false, false,  R.drawable.sales_report);
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }


         menuModel = new MenuModel("Logout", true, false,  R.drawable.logout); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

    }

    private void populateExpandableList() {

            expandableListAdapter = new ExpandableListAdapter(this, headerList, childList);
            expandableListView.setAdapter(expandableListAdapter);

            expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                    if (headerList.get(groupPosition).isGroup) {
                        if (!headerList.get(groupPosition).hasChildren) {
                            /*NavController navController = Navigation.findNavController(MainDrawerActivity.this, R.id.nav_host_fragment);
                            navController.popBackStack();*/
                            if(groupPosition==0){
                                Navigation.findNavController(MainDrawerActivity.this,R.id.nav_host_fragment).navigate(R.id.nav_home);
                                drawer.closeDrawer(GravityCompat.START);
                            } if(groupPosition==6){
                                LottieAlertDialogs alertDialog = new LottieAlertDialogs.Builder(MainDrawerActivity.this, DialogTypes.TYPE_WARNING)
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
                                            lottieAlertDialog.dismiss();
                                            finish();

                                        })
                                        .setNegativeListener(Dialog::dismiss)
                                        .build();
                                alertDialog.setCancelable(true);
                                alertDialog.show();
                            }
                        }
                    }

                    return false;
                }
            });

            expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                    if (childList.get(headerList.get(groupPosition)) != null) {
                        MenuModel model = Objects.requireNonNull(childList.get(headerList.get(groupPosition))).get(childPosition);

                        Navigation.findNavController(MainDrawerActivity.this,R.id.nav_host_fragment).popBackStack();
                        Navigation.findNavController(MainDrawerActivity.this,R.id.nav_host_fragment).navigate(R.id.nav_home);

                        if (groupPosition==1 && childPosition==0) {
                            Navigation.findNavController(MainDrawerActivity.this,R.id.nav_host_fragment).navigate(R.id.action_nav_home_to_nav_sales);
                            drawer.closeDrawer(GravityCompat.START);
                        }else if (groupPosition==1 && childPosition==1) {
                            Navigation.findNavController(MainDrawerActivity.this,R.id.nav_host_fragment).navigate(R.id.action_nav_home_to_nav_sales_refund);
                            drawer.closeDrawer(GravityCompat.START);
                        }else if (groupPosition==2 && childPosition==0) {
                            Navigation.findNavController(MainDrawerActivity.this,R.id.nav_host_fragment).navigate(R.id.action_nav_home_to_nav_products);
                            drawer.closeDrawer(GravityCompat.START);
                        }else if (groupPosition==3 && childPosition==0) {
                            Navigation.findNavController(MainDrawerActivity.this,R.id.nav_host_fragment).navigate(R.id.action_nav_home_to_nav_credit_pay);
                            drawer.closeDrawer(GravityCompat.START);
                        }else if (groupPosition==3 && childPosition==1) {
                            Navigation.findNavController(MainDrawerActivity.this,R.id.nav_host_fragment).navigate(R.id.action_nav_home_to_nav_customer);
                            drawer.closeDrawer(GravityCompat.START);
                        }else if (groupPosition==3 && childPosition==2) {
                            Navigation.findNavController(MainDrawerActivity.this,R.id.nav_host_fragment).navigate(R.id.action_nav_home_to_nav_customer_update);
                            drawer.closeDrawer(GravityCompat.START);
                        }else if (groupPosition==4 && childPosition==0) {
                            Navigation.findNavController(MainDrawerActivity.this,R.id.nav_host_fragment).navigate(R.id.action_nav_home_to_nav_dayopen);
                            drawer.closeDrawer(GravityCompat.START);
                        }else if (groupPosition==4 && childPosition==1) {
                            Navigation.findNavController(MainDrawerActivity.this,R.id.nav_host_fragment).navigate(R.id.action_nav_home_to_nav_dayclose);
                            drawer.closeDrawer(GravityCompat.START);
                        }else if (groupPosition==5 && childPosition==0) {
                            HomeFragmentDirections.ActionNavHomeToNavSalesReport actionNavSalesReport = HomeFragmentDirections.actionNavHomeToNavSalesReport("");
                            Navigation.findNavController(MainDrawerActivity.this,R.id.nav_host_fragment).navigate(actionNavSalesReport);
                            drawer.closeDrawer(GravityCompat.START);
                        }else if (groupPosition==2 && childPosition==5) {
                            Navigation.findNavController(MainDrawerActivity.this,R.id.nav_host_fragment).navigate(R.id.action_nav_home_to_nav_vendor_update);
                            drawer.closeDrawer(GravityCompat.START);
                        }else if (groupPosition==2 && childPosition==1) {
                            Navigation.findNavController(MainDrawerActivity.this,R.id.nav_host_fragment).navigate(R.id.action_nav_home_to_stockUpdateFragment);
                            drawer.closeDrawer(GravityCompat.START);
                        }else if (groupPosition==2 && childPosition==2) {
                        Navigation.findNavController(MainDrawerActivity.this, R.id.nav_host_fragment).navigate(R.id.action_nav_home_to_purchaseInvoiceFragment);
                        drawer.closeDrawer(GravityCompat.START);
                    } else if (groupPosition==2 && childPosition==3) {
                        Navigation.findNavController(MainDrawerActivity.this, R.id.nav_host_fragment).navigate(R.id.action_nav_home_to_vi_payment_fragment);
                        drawer.closeDrawer(GravityCompat.START);
                    }else if (groupPosition==2 && childPosition==4) {
                            Navigation.findNavController(MainDrawerActivity.this,R.id.nav_host_fragment).navigate(R.id.action_nav_home_to_vendorPaymentFragment);
                            drawer.closeDrawer(GravityCompat.START);
                        }

                    }

                    return false;
                }
            });



    setNavigationString();
        try {
            new WorkManagerSync(0);
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


        try {
            SQLiteDatabase mydb = openOrCreateDatabase("MasterDB",MODE_PRIVATE,null);
            Cursor cursor = mydb.rawQuery("Select STR_NM, E_MAIL from retail_store",null);
            if(cursor.moveToFirst()){

                title = cursor.getString(0).toUpperCase();
                subtitle= cursor.getString(1).toLowerCase();

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