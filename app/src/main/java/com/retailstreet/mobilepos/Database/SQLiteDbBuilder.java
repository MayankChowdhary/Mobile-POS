package com.retailstreet.mobilepos.Database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.retailstreet.mobilepos.Utils.JSONParserSync;
import com.retailstreet.mobilepos.View.LoadingDialog;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


@SuppressWarnings("deprecation")
public class SQLiteDbBuilder {
    private final Context context;
    String JSON_STRING;

    /* Arrays to store Table field names retrieved from JSON Structure*/
    private final ArrayList<String> user_master;
    private final ArrayList<String> user_master_pk;
    private final ArrayList<String> retail_cust;
    private final ArrayList<String> retail_cust_pk;
    private final ArrayList<String> retail_store_prod_com;
    private final ArrayList<String> retail_store_prod_com_pk;
    private final ArrayList<String> retail_str_stock_master;
    private final ArrayList<String> retail_str_stock_master_pk;
    private final ArrayList<String> retail_str_sales_detail;
    private final ArrayList<String> retail_str_sales_detail_pk;
    private final ArrayList<String> retail_str_sales_master;
    private final ArrayList<String> retail_str_sales_master_pk;
    private final ArrayList<String> retail_str_sales_master_return;
    private final ArrayList<String> retail_str_sales_master_return_pk;
    private final ArrayList<String> retail_str_sales_detail_return;
    private final ArrayList<String> retail_str_sales_detail_return_pk;
    private final ArrayList<String> cartList;
    private final ArrayList<String> cartList_Pk;
    private final ArrayList<String> retail_store;
    private final ArrayList<String> retail_store_pk;
    private final ArrayList<String> retail_cust_address;
    private final ArrayList<String> retail_cust_address_pk;
    private final ArrayList<String> terminal_user_allocation;
    private final ArrayList<String> terminal_user_allocation_pk;
    private final ArrayList<String> terminal_configuration;
    private final ArrayList<String> terminal_configuration_pk;
    private final ArrayList<String> master_shift;
    private final ArrayList<String> master_shift_pk;
    private final ArrayList<String> masterdeliverytype;
    private final ArrayList<String> masterdeliverytype_pk;
    private final ArrayList<String> masterpaymode;
    private final ArrayList<String> masterpaymode_pk;
    private final ArrayList<String> billpaydetail;
    private final ArrayList<String> billpaydetail_pk;


   static String dbname = "MasterDB";
    public static boolean dbOk = false;


    public SQLiteDbBuilder(Context context) {
        this.context = context;

        user_master = new ArrayList<>();
        user_master_pk = new ArrayList<>();
        retail_cust = new ArrayList<>();
        retail_cust_pk = new ArrayList<>();
        retail_store_prod_com = new ArrayList<>();
        retail_store_prod_com_pk = new ArrayList<>();
        retail_str_stock_master= new ArrayList<>();
        retail_str_stock_master_pk= new ArrayList<>();
        retail_str_sales_detail= new ArrayList<>();
        retail_str_sales_detail_pk= new ArrayList<>();
        retail_str_sales_master= new ArrayList<>();
        retail_str_sales_master_pk= new ArrayList<>();
        retail_str_sales_master_return= new ArrayList<>();
        retail_str_sales_master_return_pk= new ArrayList<>();
        retail_str_sales_detail_return= new ArrayList<>();
        retail_str_sales_detail_return_pk= new ArrayList<>();
        retail_cust_address= new ArrayList<>();
        retail_cust_address_pk= new ArrayList<>();
        retail_store = new ArrayList<>();
        retail_store_pk = new ArrayList<>();
        terminal_user_allocation = new ArrayList<>();
        terminal_user_allocation_pk = new ArrayList<>();
        terminal_configuration = new ArrayList<>();
        terminal_configuration_pk = new ArrayList<>();
        master_shift = new ArrayList<>();
        master_shift_pk = new ArrayList<>();
        masterdeliverytype = new ArrayList<>();
        masterdeliverytype_pk = new ArrayList<>();
        masterpaymode = new ArrayList<>();
        masterpaymode_pk = new ArrayList<>();
        billpaydetail = new ArrayList<>();
        billpaydetail_pk = new ArrayList<>();
        cartList= new ArrayList<>(Arrays.asList("STOCK_ID","PROD_NM","count","MRP","S_PRICE","SALESDISCOUNTBYAMOUNT","GST","SGST","CGST","QTY" ));
        cartList_Pk= new ArrayList<>(Collections.singletonList("STOCK_ID"));
        getJSON();

    }


    private void getJSON() {
        @SuppressWarnings("deprecation")
        class GetJSON extends AsyncTask<Void, Void, String> {
            //ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // loading = ProgressDialog.show(com.example.stock_inventory.Activity_Installation.this, "Fetching Data", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                //loading.dismiss();
                JSON_STRING = result;
                JSONToArray(result);
            }

            @Override
            protected String doInBackground(Void... params) {
                JSONParserSync jsonParserSync = new JSONParserSync();
                String structure = jsonParserSync.sendGetRequest("http://www.99retailstreet.com:8080/ApiTest/TestStructure");
                Log.d("BackgroundDone", "doInBackground: " + structure);
                return structure;


            }
        }

        GetJSON gj = new GetJSON();
        gj.execute();

    }


    public void JSONToArray(String JSON_Input) {
        try {

            JSONObject jsonObject = new JSONObject(JSON_Input);

            JSONArray userMasterJSON = jsonObject.getJSONArray("group_user_master");
            for (int i = 0; i < userMasterJSON.length(); i++) {
                JSONObject obj = (JSONObject) userMasterJSON.get(i);
                String id = obj.getString("Field");
                user_master.add(id);
                //Log.d("group_user_master", "id:" + id);
            }
            JSONArray userMaster_PK_JSON = jsonObject.getJSONArray("group_user_master_pk");
            for (int i = 0; i < userMaster_PK_JSON.length(); i++) {
                JSONObject obj = (JSONObject) userMaster_PK_JSON.get(i);
                String constraint= obj.getString("Constraint");
                user_master_pk.add(constraint);
              //  Log.d("group_user_master_pk", "constraint:" + constraint);
            }

            JSONArray retailCustJSON = jsonObject.getJSONArray("retail_cust");
            for (int i = 0; i < retailCustJSON.length(); i++) {
                JSONObject obj = (JSONObject) retailCustJSON.get(i);
                String id = obj.getString("Field");
                retail_cust.add(id);
               // Log.d("Retail_Cust", "id:" + id);
            }

            JSONArray retailcust_PK_JSON = jsonObject.getJSONArray("retail_cust_pk");
            for (int i = 0; i < retailcust_PK_JSON.length(); i++) {
                JSONObject obj = (JSONObject) retailcust_PK_JSON.get(i);
                String constraint = obj.getString("Constraint");
                retail_cust_pk.add(constraint);
               // Log.d("retail_cust_pk", "constraint:" + constraint+"  Array:"+retail_cust_pk.toString());
            }

            JSONArray retail_store_prod = jsonObject.getJSONArray("retail_store_prod_com");
            for (int i = 0; i < retail_store_prod.length(); i++) {
                JSONObject obj = (JSONObject) retail_store_prod.get(i);
               String id = obj.getString("Field");
                retail_store_prod_com.add(id);
              //  Log.d("retail_store_prod_com", "id:" + id);

            }

            JSONArray retail_store_prod_pk = jsonObject.getJSONArray("retail_store_prod_com_pk");
            for (int i = 0; i < retail_store_prod_pk.length(); i++) {
                JSONObject obj = (JSONObject) retail_store_prod_pk.get(i);
                String constraint = obj.getString("Constraint");
                retail_store_prod_com_pk.add(constraint);
               // Log.d("retail_store_prod_pk", "constraint:" + constraint);
            }

            JSONArray stock_master_JSON = jsonObject.getJSONArray("retail_str_stock_master");
            for (int i = 0; i < stock_master_JSON.length(); i++) {
                JSONObject obj = (JSONObject) stock_master_JSON.get(i);
                String id = obj.getString("Field");
                retail_str_stock_master.add(id);
              //  Log.d("retail_str_stock_master", "id:" + id);

            }

            JSONArray stock_master_pk_JSON = jsonObject.getJSONArray("retail_str_stock_master_pk");
            for (int i = 0; i < stock_master_pk_JSON.length(); i++) {
                JSONObject obj = (JSONObject) stock_master_pk_JSON.get(i);
                String constraint = obj.getString("Constraint");
                retail_str_stock_master_pk.add(constraint);
                //Log.d("retail_stock_master_pk", "constraint:" + constraint);
            }


            JSONArray retail_sales_detail_JSON = jsonObject.getJSONArray("retail_str_sales_detail");
            for (int i = 0; i < retail_sales_detail_JSON.length(); i++) {
                JSONObject obj = (JSONObject) retail_sales_detail_JSON.get(i);
                String id = obj.getString("Field");
                retail_str_sales_detail.add(id);
              //  Log.d("retail_str_sales_detail", "id:" + id);

            }

            JSONArray retail_sales_detail_pk_JSON = jsonObject.getJSONArray("retail_str_sales_detail_pk");
            for (int i = 0; i < retail_sales_detail_pk_JSON.length(); i++) {
                JSONObject obj = (JSONObject) retail_sales_detail_pk_JSON.get(i);
                String constraint = obj.getString("Constraint");
                retail_str_sales_detail_pk.add(constraint);
                //Log.d("retail_sales_detail_pk", "constraint:" + constraint);
            }

            JSONArray retail_sales_master_JSON = jsonObject.getJSONArray("retail_str_sales_master");
            for (int i = 0; i < retail_sales_master_JSON.length(); i++) {
                JSONObject obj = (JSONObject) retail_sales_master_JSON.get(i);
                String id = obj.getString("Field");
                retail_str_sales_master.add(id);
               // Log.d("retail_str_sales_master", "id:" + id);

            }

            JSONArray retail_sales_master_pk_JSON = jsonObject.getJSONArray("retail_str_sales_master_pk");
            for (int i = 0; i < retail_sales_master_pk_JSON.length(); i++) {
                JSONObject obj = (JSONObject) retail_sales_master_pk_JSON.get(i);
                String constraint = obj.getString("Constraint");
                retail_str_sales_master_pk.add(constraint);
               // Log.d("retail_sales_master_pk", "constraint:" + constraint);
            }

            JSONArray retail_sales_master_return_JSON = jsonObject.getJSONArray("retail_str_sales_master_return");
            for (int i = 0; i < retail_sales_master_return_JSON.length(); i++) {
                JSONObject obj = (JSONObject) retail_sales_master_return_JSON.get(i);
                String id = obj.getString("Field");
                retail_str_sales_master_return.add(id);
                //Log.d("sales_master_return", "id:" + id);

            }

            JSONArray retail_sales_master_return_pk_JSON = jsonObject.getJSONArray("retail_str_sales_master_return_pk");
            for (int i = 0; i < retail_sales_master_return_pk_JSON.length(); i++) {
                JSONObject obj = (JSONObject) retail_sales_master_return_pk_JSON.get(i);
                String constraint = obj.getString("Constraint");
                retail_str_sales_master_return_pk.add(constraint);
              //  Log.d("sales_master_return_pk", "constraint:" + constraint);
            }

            JSONArray sales_detail_return_JSON = jsonObject.getJSONArray("retail_str_sales_detail_return");
            for (int i = 0; i < sales_detail_return_JSON.length(); i++) {
                JSONObject obj = (JSONObject) sales_detail_return_JSON.get(i);
                String id = obj.getString("Field");
                retail_str_sales_detail_return.add(id);
                //Log.d("sales_detail_return", "id:" + id);

            }

            JSONArray sales_detail_return_pk_JSON = jsonObject.getJSONArray("retail_str_sales_detail_return_pk");
            for (int i = 0; i < sales_detail_return_pk_JSON.length(); i++) {
                JSONObject obj = (JSONObject) sales_detail_return_pk_JSON.get(i);
                String constraint = obj.getString("Constraint");
                retail_str_sales_detail_return_pk.add(constraint);
                //Log.d("sales_detail_return_pk", "constraint:" + constraint);
            }


            JSONArray retailStore_JSON = jsonObject.getJSONArray("retail_store");
            for (int i = 0; i < retailStore_JSON.length(); i++) {
                JSONObject obj = (JSONObject) retailStore_JSON.get(i);
                String id = obj.getString("Field");
                retail_store.add(id);
               // Log.d("retail_store", "id:" + id);

            }

            JSONArray retailStore_pk_JSON = jsonObject.getJSONArray("retail_store_pk");
            for (int i = 0; i < retailStore_pk_JSON.length(); i++) {
                JSONObject obj = (JSONObject) retailStore_pk_JSON.get(i);
                String constraint = obj.getString("Constraint");
                retail_store_pk.add(constraint);
                //Log.d("retail_store_pk", "constraint:" + constraint);
            }
            JSONArray custAddr_JSON = jsonObject.getJSONArray("retail_cust_address");
            for (int i = 0; i < custAddr_JSON.length(); i++) {
                JSONObject obj = (JSONObject) custAddr_JSON.get(i);
                String id = obj.getString("Field");
                retail_cust_address.add(id);
                // Log.d("retail_store", "id:" + id);

            }

            JSONArray custAddr_pk_JSON = jsonObject.getJSONArray("retail_cust_address_pk");
            for (int i = 0; i < custAddr_pk_JSON.length(); i++) {
                JSONObject obj = (JSONObject) custAddr_pk_JSON.get(i);
                String constraint = obj.getString("Constraint");
                retail_cust_address_pk.add(constraint);
                //Log.d("retail_store_pk", "constraint:" + constraint);
            }

            JSONArray terminalUserAlloc_JSON = jsonObject.getJSONArray("terminal_user_allocation");
            for (int i = 0; i < terminalUserAlloc_JSON.length(); i++) {
                JSONObject obj = (JSONObject) terminalUserAlloc_JSON.get(i);
                String id = obj.getString("Field");
                terminal_user_allocation.add(id);
                // Log.d("retail_store", "id:" + id);

            }

            JSONArray terminalUserAlloc_pk_JSON = jsonObject.getJSONArray("terminal_user_allocation_pk");
            for (int i = 0; i < terminalUserAlloc_pk_JSON.length(); i++) {
                JSONObject obj = (JSONObject) terminalUserAlloc_pk_JSON.get(i);
                String constraint = obj.getString("Constraint");
                terminal_user_allocation_pk.add(constraint);
                //Log.d("retail_store_pk", "constraint:" + constraint);
            }

            JSONArray terminalConfig_JSON = jsonObject.getJSONArray("terminal_configuration");
            for (int i = 0; i < terminalConfig_JSON.length(); i++) {
                JSONObject obj = (JSONObject) terminalConfig_JSON.get(i);
                String id = obj.getString("Field");
                terminal_configuration.add(id);
                // Log.d("retail_store", "id:" + id);

            }

            JSONArray terminalConfig_pk_JSON = jsonObject.getJSONArray("terminal_configuration_pk");
            for (int i = 0; i < terminalConfig_pk_JSON.length(); i++) {
                JSONObject obj = (JSONObject) terminalConfig_pk_JSON.get(i);
                String constraint = obj.getString("Constraint");
                terminal_configuration_pk.add(constraint);
                //Log.d("retail_store_pk", "constraint:" + constraint);
            }

            JSONArray masterShift_JSON = jsonObject.getJSONArray("master_shift");
            for (int i = 0; i < masterShift_JSON.length(); i++) {
                JSONObject obj = (JSONObject) masterShift_JSON.get(i);
                String id = obj.getString("Field");
                master_shift.add(id);
                // Log.d("retail_store", "id:" + id);

            }

            JSONArray masterShift_pk_JSON = jsonObject.getJSONArray("master_shift_pk");
            for (int i = 0; i < masterShift_pk_JSON.length(); i++) {
                JSONObject obj = (JSONObject) masterShift_pk_JSON.get(i);
                String constraint = obj.getString("Constraint");
                master_shift_pk.add(constraint);
                //Log.d("retail_store_pk", "constraint:" + constraint);
            }

            JSONArray masterDelivery_JSON = jsonObject.getJSONArray("masterdeliverytype");
            for (int i = 0; i < masterDelivery_JSON.length(); i++) {
                JSONObject obj = (JSONObject) masterDelivery_JSON.get(i);
                String id = obj.getString("Field");
                masterdeliverytype.add(id);
                // Log.d("retail_store", "id:" + id);

            }

            JSONArray masterDelivery_pk_JSON = jsonObject.getJSONArray("masterdeliverytype_pk");
            for (int i = 0; i < masterDelivery_pk_JSON.length(); i++) {
                JSONObject obj = (JSONObject) masterDelivery_pk_JSON.get(i);
                String constraint = obj.getString("Constraint");
                masterdeliverytype_pk.add(constraint);
                //Log.d("retail_store_pk", "constraint:" + constraint);
            }

            JSONArray masterPayMode_JSON = jsonObject.getJSONArray("masterpaymode");
            for (int i = 0; i < masterPayMode_JSON.length(); i++) {
                JSONObject obj = (JSONObject) masterPayMode_JSON.get(i);
                String id = obj.getString("Field");
                masterpaymode.add(id);
                // Log.d("retail_store", "id:" + id);

            }

            JSONArray masterPayMode_pk_JSON = jsonObject.getJSONArray("masterpaymode_pk");
            for (int i = 0; i < masterPayMode_pk_JSON.length(); i++) {
                JSONObject obj = (JSONObject) masterPayMode_pk_JSON.get(i);
                String constraint = obj.getString("Constraint");
                masterpaymode_pk.add(constraint);
                //Log.d("retail_store_pk", "constraint:" + constraint);
            }

            JSONArray bilPayDetail_JSON = jsonObject.getJSONArray("billpaydetail");
            for (int i = 0; i < bilPayDetail_JSON.length(); i++) {
                JSONObject obj = (JSONObject) bilPayDetail_JSON.get(i);
                String id = obj.getString("Field");
                billpaydetail.add(id);
                // Log.d("retail_store", "id:" + id);

            }

            JSONArray bilPayDetail_pk_JSON = jsonObject.getJSONArray("billpaydetail_pk");
            for (int i = 0; i < bilPayDetail_pk_JSON.length(); i++) {
                JSONObject obj = (JSONObject) bilPayDetail_pk_JSON.get(i);
                String constraint = obj.getString("Constraint");
                billpaydetail_pk.add(constraint);
                //Log.d("retail_store_pk", "constraint:" + constraint);
            }


            createDynamicDatabase(context, "group_user_master", user_master, user_master_pk);
            createDynamicDatabase(context, "retail_cust", retail_cust, retail_cust_pk);
            createDynamicDatabase(context, "retail_store_prod_com", retail_store_prod_com, retail_store_prod_com_pk);
            createDynamicDatabase(context, "retail_str_stock_master", retail_str_stock_master, retail_str_stock_master_pk);
            createDynamicDatabase(context, "retail_str_sales_detail", retail_str_sales_detail, retail_str_sales_detail_pk);
            createDynamicDatabase(context, "retail_str_sales_master", retail_str_sales_master, retail_str_sales_master_pk);
            createDynamicDatabase(context, "retail_str_sales_master_return", retail_str_sales_master_return, retail_str_sales_master_return_pk);
            createDynamicDatabase(context, "retail_str_sales_detail_return", retail_str_sales_detail_return, retail_str_sales_detail_return_pk);
            createDynamicDatabase(context, "cart", cartList, cartList_Pk);
            createDynamicDatabase(context, "retail_store", retail_store, retail_store_pk);
            createDynamicDatabase(context, "retail_cust_address", retail_cust_address, retail_cust_address_pk);
            createDynamicDatabase(context, "terminal_user_allocation", terminal_user_allocation, terminal_user_allocation_pk);
            createDynamicDatabase(context, "terminal_configuration", terminal_configuration, terminal_configuration_pk);
            createDynamicDatabase(context, "master_shift", master_shift, master_shift_pk);
            createDynamicDatabase(context, "masterdeliverytype", masterdeliverytype, masterdeliverytype_pk);
            createDynamicDatabase(context, "masterpaymode", masterpaymode, masterpaymode_pk);
            createDynamicDatabase(context, "billpaydetail", billpaydetail, billpaydetail_pk);

            dbOk=true;
            LoadingDialog.cancelDialog();
            Toast.makeText(context, "Tables Created", Toast.LENGTH_LONG).show();
             SQLiteDbInspector.PrintTableSchema(context,dbname);

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }


        //////////////////////////////////////////////////////////////////////////////////////


    }

    public void createDynamicDatabase(Context context, String tableName, ArrayList<String> title, ArrayList<String> constraint) {
       // Log.i("INSIDE LoginDatabase", "****creatLoginDatabase*****"+tableName+constraint.toString());
      //  Log.d("PrintingTableReceived", "createDynamicDatabase: "+title.toString());
        try {
            int i;
            String querryString;
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname,Context.MODE_PRIVATE, null);          //Opens database in writable mode.
            //myDataBase.execSQL("PRAGMA key ='Anaconda'");          //Opens database in writable mode.

            //Opens database in writable mode.
            querryString = title.get(0) + " NVARCHAR(30),";
            Log.d("**createDynamicDatabase", "in oncreate");
            for (i = 1; i < title.size() - 1; i++) {
                querryString += title.get(i);
                querryString += " NVARCHAR(30) ";
                querryString += ",";
            }
            String constraintString = constraint.toString().replace("[", "(").replace("]", ")");
            querryString += title.get(i) + " NVARCHAR(30) ";
            querryString = "CREATE TABLE IF NOT EXISTS " + tableName + "(" + querryString + ", CONSTRAINT store_pk PRIMARY KEY " + constraintString + " );";
          //  System.out.println("Create Table Stmt : " + querryString);
            myDataBase.execSQL(querryString);
            myDataBase.close();

        } catch (SQLException ex) {
            Log.i("xyz CreateDB Exception ", ex.getMessage());
        }
    }
}
