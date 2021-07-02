package com.retailstreet.mobilepos.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.retailstreet.mobilepos.Controller.ControllerStoreConfig;
import com.retailstreet.mobilepos.Controller.ControllerStoreParams;
import com.retailstreet.mobilepos.R;
import com.retailstreet.mobilepos.Utils.CrashHandler;
import com.retailstreet.mobilepos.Utils.Vibration;
import com.retailstreet.mobilepos.Utils.WorkManagerSync;
import com.retailstreet.mobilepos.View.ExpandableNavigation.ExpandableListAdapter;
import com.retailstreet.mobilepos.View.ExpandableNavigation.MenuModel;
import com.retailstreet.mobilepos.View.dialog.LottieAlertDialogs;
import com.retailstreet.mobilepos.View.ui.Home.HomeFragmentDirections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

public class MainDrawerActivity extends AppCompatActivity  {

    private AppBarConfiguration mAppBarConfiguration;
    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();
    DrawerLayout drawer;

    String LOCK_PASSWORD;
    boolean SUMMERY_UNLOCKED = true;
    boolean SALES_REPORT_NOT_BANNED = true;
    boolean IS_RETURN_UNLOCKED = true;
    boolean IS_VENDOR_ADDITION = true;
    boolean IS_PRODUCT_ADDITION = true;
    boolean IS_STOCK_ENTRY = true;
    boolean IS_STOCK_ADJUSTMENT = true;
    boolean IS_VENDOR_PAY = true;
    boolean IS_VENDOR_RETURNS = true;
    boolean ALL_REPORTS_VISIBILITY = true;

    ControllerStoreConfig config = new ControllerStoreConfig();
    ControllerStoreParams params = new ControllerStoreParams();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_drawer_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().setBackgroundDrawable(null);
        Thread.setDefaultUncaughtExceptionHandler(new CrashHandler(this));
        expandableListView = findViewById(R.id.expandableListView);
        prepareMenuData();
        populateExpandableList();
        LOCK_PASSWORD = config.getLockPassword();
        SUMMERY_UNLOCKED = config.getSummeryLock();
        SALES_REPORT_NOT_BANNED = config.getSalesReportBanned();
        IS_RETURN_UNLOCKED = config.getSalesReturnLock();

        IS_VENDOR_ADDITION = params.getIsVendorAddition();
         IS_PRODUCT_ADDITION = params.getIsProductAddition();
         IS_STOCK_ENTRY = params.getIsStockEntry();
         IS_STOCK_ADJUSTMENT = params.getIsStockAdjust();
         IS_VENDOR_PAY = params.getVendorPayment();
         IS_VENDOR_RETURNS = params.getVendorreturns();
         ALL_REPORTS_VISIBILITY = params.getAllReportsVisibility();

         drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_sales, R.id.nav_dayopen, R.id.nav_dayclose, R.id.nav_products, R.id.nav_customer, R.id.nav_customer_update, R.id.nav_sales_refund, R.id.nav_credit_pay, R.id.nav_home, R.id.nav_sales_report,R.id.nav_vendor_update,R.id.nav_stock_update,R.id.nav_purchase_Invoice,R.id.nav_vendor_Payment,R.id.nav_vi_payment_fragment,R.id.nav_vendor_return,R.id.nav_vendor_reports,R.id.nav_sales_return_report,R.id.nav_vendor_addition,R.id.nav_vendor_pay_report,R.id.nav_summery)
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

        MenuModel menuModel = new MenuModel("Store Info", true, false, R.drawable.reception); //Menu of Android Tutorial. No sub menus
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
        childModel = new MenuModel("Add Vendor", false, false, R.drawable.vendor);
        childModelsList.add(childModel);
        childModel = new MenuModel("Stock Update", false, false, R.drawable.stockupdate);
        childModelsList.add(childModel);
        childModel = new MenuModel("Purchase Invoice", false, false, R.drawable.invoice);
        childModelsList.add(childModel);
        childModel = new MenuModel("Petty Cash", false, false, R.drawable.petty_cash);
        childModelsList.add(childModel);
        childModel = new MenuModel("Vendor Payment", false, false, R.drawable.vendor_payment);
        childModelsList.add(childModel);
        childModel = new MenuModel("Vendor Return", false, false, R.drawable.vendor_return);
        childModelsList.add(childModel);
        childModel = new MenuModel("Vendor Details", false, false, R.drawable.vendor_details);
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
        childModel = new MenuModel("Sales Report", false, false,  R.drawable.sales_report);
        childModelsList.add(childModel);
        childModel = new MenuModel("Vendor Report", false, false,  R.drawable.vendor_report);
        childModelsList.add(childModel);
        childModel = new MenuModel("Sales Return Report", false, false,  R.drawable.sr_report);
        childModelsList.add(childModel);
        childModel = new MenuModel("Vendor Pay Report", false, false,  R.drawable.vendor_pay_report);
        childModelsList.add(childModel);
        childModel = new MenuModel("All Summery", false, false,  R.drawable.summery);
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

            expandableListView.setOnGroupClickListener((parent, v, groupPosition, id) -> {

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
            });

            expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {

                if (childList.get(headerList.get(groupPosition)) != null) {
                    //MenuModel model = Objects.requireNonNull(childList.get(headerList.get(groupPosition))).get(childPosition);

                    Navigation.findNavController(MainDrawerActivity.this,R.id.nav_host_fragment).popBackStack();
                    Navigation.findNavController(MainDrawerActivity.this,R.id.nav_host_fragment).navigate(R.id.nav_home);

                    if (groupPosition==1 && childPosition==0) {
                        Navigation.findNavController(MainDrawerActivity.this,R.id.nav_host_fragment).navigate(R.id.action_nav_home_to_nav_sales);
                        drawer.closeDrawer(GravityCompat.START);
                    }else if (groupPosition==1 && childPosition==1) {
                        if(IS_RETURN_UNLOCKED || LOCK_PASSWORD.isEmpty()) {
                            Navigation.findNavController(MainDrawerActivity.this,R.id.nav_host_fragment).navigate(R.id.action_nav_home_to_nav_sales_refund);
                            drawer.closeDrawer(GravityCompat.START);
                        }else {

                            showPasswordDialog("Sales Return",2);
                        }

                    }else if (groupPosition==2 && childPosition==0) {
                        if(IS_PRODUCT_ADDITION){
                            Navigation.findNavController(MainDrawerActivity.this,R.id.nav_host_fragment).navigate(R.id.action_nav_home_to_nav_products);
                            drawer.closeDrawer(GravityCompat.START);
                        }else {
                            Vibration.Companion.vibrate(400);
                            Toast.makeText(getApplicationContext(),"Permission denied!",Toast.LENGTH_LONG).show();
                        }
                    }else if (groupPosition==2 && childPosition==1) {
                        if(IS_VENDOR_ADDITION){
                            Navigation.findNavController(MainDrawerActivity.this,R.id.nav_host_fragment).navigate(R.id.action_nav_home_to_nav_vendor_addition);
                            drawer.closeDrawer(GravityCompat.START);
                        }else {
                            Vibration.Companion.vibrate(400);
                            Toast.makeText(getApplicationContext(),"Permission denied!",Toast.LENGTH_LONG).show();
                        }

                    } else if (groupPosition==3 && childPosition==0) {
                        Navigation.findNavController(MainDrawerActivity.this,R.id.nav_host_fragment).navigate(R.id.action_nav_home_to_nav_credit_pay);
                        drawer.closeDrawer(GravityCompat.START);
                    }else if (groupPosition==3 && childPosition==1) {
                        Navigation.findNavController(MainDrawerActivity.this,R.id.nav_host_fragment).navigate(R.id.action_nav_home_to_nav_customer);
                        drawer.closeDrawer(GravityCompat.START);
                    }else if (groupPosition==3 && childPosition==2) {

                        HomeFragmentDirections.ActionNavHomeToNavCustomerUpdate actionCustomerUpdate = HomeFragmentDirections.actionNavHomeToNavCustomerUpdate("");
                        Navigation.findNavController(MainDrawerActivity.this,R.id.nav_host_fragment).navigate(actionCustomerUpdate);
                        drawer.closeDrawer(GravityCompat.START);
                    }else if (groupPosition==4 && childPosition==0) {
                        Navigation.findNavController(MainDrawerActivity.this,R.id.nav_host_fragment).navigate(R.id.action_nav_home_to_nav_dayopen);
                        drawer.closeDrawer(GravityCompat.START);
                    }else if (groupPosition==4 && childPosition==1) {
                        Navigation.findNavController(MainDrawerActivity.this,R.id.nav_host_fragment).navigate(R.id.action_nav_home_to_nav_dayclose);
                        drawer.closeDrawer(GravityCompat.START);
                    }else if (groupPosition==5 && childPosition==0) {

                        if(SALES_REPORT_NOT_BANNED && ALL_REPORTS_VISIBILITY) {
                            if (LOCK_PASSWORD == null || LOCK_PASSWORD.isEmpty()) {
                                HomeFragmentDirections.ActionNavHomeToNavSalesReport actionNavSalesReport = HomeFragmentDirections.actionNavHomeToNavSalesReport("");
                                Navigation.findNavController(MainDrawerActivity.this, R.id.nav_host_fragment).navigate(actionNavSalesReport);
                                drawer.closeDrawer(GravityCompat.START);
                            } else {

                                showPasswordDialog("Sales Report", 0);
                            }
                        }else {
                            Vibration.Companion.vibrate(400);
                            Toast.makeText(getApplicationContext(),"Permission denied!",Toast.LENGTH_LONG).show();
                        }
                    }else if (groupPosition==5 && childPosition==1) {
                        if(ALL_REPORTS_VISIBILITY) {
                            Navigation.findNavController(MainDrawerActivity.this, R.id.nav_host_fragment).navigate(R.id.action_nav_home_to_nav_vendor_reports);
                            drawer.closeDrawer(GravityCompat.START);
                        }else {
                            Vibration.Companion.vibrate(400);
                            Toast.makeText(getApplicationContext(),"Permission denied!",Toast.LENGTH_LONG).show();
                        }
                    }else if (groupPosition==5 && childPosition==2) {
                        if(ALL_REPORTS_VISIBILITY) {
                            Navigation.findNavController(MainDrawerActivity.this, R.id.nav_host_fragment).navigate(R.id.action_nav_home_to_salesReturnReport);
                            drawer.closeDrawer(GravityCompat.START);
                        }else {
                            Vibration.Companion.vibrate(400);
                            Toast.makeText(getApplicationContext(),"Permission denied!",Toast.LENGTH_LONG).show();
                     }
                    }else if (groupPosition==5 && childPosition==3) {
                        if(ALL_REPORTS_VISIBILITY) {
                            Navigation.findNavController(MainDrawerActivity.this, R.id.nav_host_fragment).navigate(R.id.action_nav_home_to_nav_vendor_pay_report);
                            drawer.closeDrawer(GravityCompat.START);
                        }else {
                            Vibration.Companion.vibrate(400);
                            Toast.makeText(getApplicationContext(),"Permission denied!",Toast.LENGTH_LONG).show();
                        }
                    } else if (groupPosition==5 && childPosition==4) {
                        if(SUMMERY_UNLOCKED || LOCK_PASSWORD.isEmpty()) {
                            Navigation.findNavController(MainDrawerActivity.this, R.id.nav_host_fragment).navigate(R.id.action_nav_home_to_summeryFragment);
                            drawer.closeDrawer(GravityCompat.START);
                        }else {

                            showPasswordDialog("All Summery",1);
                        }
                    } else if (groupPosition==2 && childPosition==7) {
                        Navigation.findNavController(MainDrawerActivity.this,R.id.nav_host_fragment).navigate(R.id.action_nav_home_to_nav_vendor_update);
                        drawer.closeDrawer(GravityCompat.START);
                    }else if (groupPosition==2 && childPosition==2) {
                        if(IS_STOCK_ADJUSTMENT){
                            HomeFragmentDirections.ActionNavHomeToStockUpdateFragment actionNavStockUpdate = HomeFragmentDirections.actionNavHomeToStockUpdateFragment("");
                            Navigation.findNavController(MainDrawerActivity.this,R.id.nav_host_fragment).navigate(actionNavStockUpdate);
                            drawer.closeDrawer(GravityCompat.START);
                        }else {
                            Vibration.Companion.vibrate(400);
                            Toast.makeText(getApplicationContext(),"Permission denied!",Toast.LENGTH_LONG).show();
                        }

                    }else if (groupPosition==2 && childPosition==3) {
                        if(IS_STOCK_ENTRY){
                            Navigation.findNavController(MainDrawerActivity.this, R.id.nav_host_fragment).navigate(R.id.action_nav_home_to_purchaseInvoiceFragment);
                            drawer.closeDrawer(GravityCompat.START);
                        }else {
                            Vibration.Companion.vibrate(400);
                            Toast.makeText(getApplicationContext(),"Permission denied!",Toast.LENGTH_LONG).show();
                        }
                } else if (groupPosition==2 && childPosition==4) {
                    Navigation.findNavController(MainDrawerActivity.this, R.id.nav_host_fragment).navigate(R.id.action_nav_home_to_vi_payment_fragment);
                    drawer.closeDrawer(GravityCompat.START);
                }else if (groupPosition==2 && childPosition==5) {
                        if(IS_VENDOR_PAY){
                            Navigation.findNavController(MainDrawerActivity.this,R.id.nav_host_fragment).navigate(R.id.action_nav_home_to_vendorPaymentFragment);
                            drawer.closeDrawer(GravityCompat.START);
                        }else {
                            Vibration.Companion.vibrate(400);
                            Toast.makeText(getApplicationContext(),"Permission denied!",Toast.LENGTH_LONG).show();
                        }

                    }else if (groupPosition==2 && childPosition==6) {
                        if(IS_VENDOR_RETURNS){
                            Navigation.findNavController(MainDrawerActivity.this,R.id.nav_host_fragment).navigate(R.id.action_nav_home_to_nav_vendor_return);
                            drawer.closeDrawer(GravityCompat.START);
                        }else {
                            Vibration.Companion.vibrate(400);
                            Toast.makeText(getApplicationContext(),"Permission denied!",Toast.LENGTH_LONG).show();
                        }
                    }
                }

                return false;
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
            cursor.close();
        } catch (Exception e) {

            e.printStackTrace();
        }
        NavigationView navigationView = findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = hView.findViewById(R.id.nav_title);
        nav_user.setText(title);
        nav_user.setSelected(true);
        TextView nav_subuser = hView.findViewById(R.id.nav_subtitle);
        nav_subuser.setText(subtitle);
        nav_subuser.setSelected(true);
    }


    private void showPasswordDialog(String message, int screenIndex){
        AlertDialog.Builder alert = new AlertDialog.Builder(MainDrawerActivity.this);
        alert.setTitle("Enter Password");
        alert.setMessage("           "+message);
        alert.setIcon(android.R.drawable.ic_lock_lock);
        final EditText input = new EditText(MainDrawerActivity.this);
        input.setGravity(Gravity.CENTER);
        input.setTextSize(20);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        input.setTypeface(input.getTypeface(), Typeface.BOLD);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        alert.setView(input);
        input.requestFocus();
        alert.setCancelable(false);
        alert.setPositiveButton("Ok", (dialog, whichButton) -> {
        });
        alert.setNegativeButton("Cancel", (dialog, whichButton) -> {
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        });
        final AlertDialog dialog = alert.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v1 -> {
            String value = input.getText().toString().trim();
            if (value.equals(LOCK_PASSWORD)) {
                switch (screenIndex) {
                    case 0:
                        HomeFragmentDirections.ActionNavHomeToNavSalesReport actionNavSalesReport = HomeFragmentDirections.actionNavHomeToNavSalesReport("");
                        Navigation.findNavController(MainDrawerActivity.this, R.id.nav_host_fragment).navigate(actionNavSalesReport);
                        break;
                    case 1:
                        Navigation.findNavController(MainDrawerActivity.this, R.id.nav_host_fragment).navigate(R.id.action_nav_home_to_summeryFragment);
                        break;
                    case 2:
                        Navigation.findNavController(MainDrawerActivity.this, R.id.nav_host_fragment).navigate(R.id.action_nav_home_to_nav_sales_refund);
                        break;
                }

                drawer.closeDrawer(GravityCompat.START);
                Toast.makeText(getApplicationContext(), "Permission Granted !", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            } else {

                Vibration.Companion.vibrate(300);
                Toast.makeText(getApplicationContext(), "Invalid Password !", Toast.LENGTH_SHORT).show();
            }
        });

    }
}