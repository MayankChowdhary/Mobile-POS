package com.retailstreet.mobilepos.Database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.retailstreet.mobilepos.Utils.JSONParserSync;
import com.retailstreet.mobilepos.View.ApplicationContextProvider;
import com.retailstreet.mobilepos.View.dialog.LoadingDialog;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


@SuppressWarnings("deprecation")
public class SQLiteDbBuilder {
    private final Context context;

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
    private final ArrayList<String> shift_trans;
    private final ArrayList<String> shift_trans_pk;
    private final ArrayList<String> master_category;
    private final ArrayList<String> master_category_pk;
    private final ArrayList<String> master_subcategory;
    private final ArrayList<String> master_subcategory_pk;
    private final ArrayList<String> hsn_master;
    private final ArrayList<String> hsn_master_pk;
    private final ArrayList<String> retail_str_dstr;
    private final ArrayList<String> retail_str_dstr_pk;
    private final ArrayList<String> master_uom;
    private final ArrayList<String> master_uom_pk;
    private final ArrayList<String> master_customer_type;
    private final ArrayList<String> master_customer_type_pk;
    private final ArrayList<String> masterState;
    private final ArrayList<String> masterState_pk;
    private final ArrayList<String> bank_details;
    private final ArrayList<String> bank_details_pk;
    private final ArrayList<String> store_configuration;
    private final ArrayList<String> store_configuration_pk;
    private final ArrayList<String> retail_store_cust_reject;
    private final ArrayList<String> retail_store_cust_reject_pk;
    private final ArrayList<String> retail_credit_cust;
    private final ArrayList<String> retail_credit_cust_pk;
    private final ArrayList<String> customerReturnDetail;
    private final ArrayList<String> customerReturnDetail_pk;
    private final ArrayList<String> customerReturnMaster;
    private final ArrayList<String> customerReturnMaster_pk;
    private final ArrayList<String> customerLedger;
    private final ArrayList<String> customerLedger_pk;
    private final ArrayList<String> retail_credit_bill_details;
    private final ArrayList<String> stock_register;
    private final ArrayList<String> stock_register_pk;



   static String dbname = "MasterDB";
    public static boolean dbOk = false;
    LoadingDialog loadingDialog;


    public SQLiteDbBuilder(Context context) {
        this.context = context;
        loadingDialog=  new LoadingDialog();

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
        shift_trans = new ArrayList<>();
        shift_trans_pk = new ArrayList<>();
        master_category = new ArrayList<>();
        master_category_pk= new ArrayList<>();
        master_subcategory =  new ArrayList<>();
        master_subcategory_pk = new ArrayList<>();
        hsn_master = new ArrayList<>();
        hsn_master_pk =  new ArrayList<>();
        retail_str_dstr = new ArrayList<>();
        retail_str_dstr_pk =  new ArrayList<>();
        master_uom= new ArrayList<>();
        master_uom_pk =  new ArrayList<>();
        master_customer_type =  new ArrayList<>();
        master_customer_type_pk =  new ArrayList<>();
        masterState =  new ArrayList<>();
        masterState_pk=  new ArrayList<>();
        bank_details =  new ArrayList<>();
        bank_details_pk=  new ArrayList<>();
        store_configuration=  new ArrayList<>();
        store_configuration_pk =  new ArrayList<>();
        retail_store_cust_reject=  new ArrayList<>();
        retail_store_cust_reject_pk =  new ArrayList<>();
        retail_credit_cust=  new ArrayList<>();
        retail_credit_cust_pk =  new ArrayList<>();
        customerReturnDetail=  new ArrayList<>();
        customerReturnDetail_pk =  new ArrayList<>();
        customerReturnMaster =  new ArrayList<>();
        customerReturnMaster_pk =  new ArrayList<>();
        customerLedger =  new ArrayList<>();
        customerLedger_pk =  new ArrayList<>();
        retail_credit_bill_details =  new ArrayList<>();
        stock_register  =  new ArrayList<>();
        stock_register_pk  =  new ArrayList<>();



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
                loadingDialog.showDialog(context, "Please Wait!", "Preparing Database...");

            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                if(result!=null && !result.isEmpty()) {
                    Toast.makeText(context, "Tables Created", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(ApplicationContextProvider.getContext(), "Network Error Download Failed !", Toast.LENGTH_LONG).show();
                }
                    loadingDialog.cancelDialog();
                    loadingDialog=null;

            }

            @Override
            protected String doInBackground(Void... params) {
                JSONParserSync jsonParserSync = new JSONParserSync();
                String structure = jsonParserSync.sendGetRequest("http://www.99retailstreet.com:8080/ApiTest/TestStructure");
                Log.d("BackgroundDone", "doInBackground: " + structure);

                if(structure!=null && !structure.isEmpty())
                JSONToArray(structure);

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


            JSONArray shift_trans_json = jsonObject.getJSONArray("shift_trans");
            for (int i = 0; i < shift_trans_json.length(); i++) {
                JSONObject obj = (JSONObject) shift_trans_json.get(i);
                String id = obj.getString("Field");
                shift_trans.add(id);
                // Log.d("retail_store", "id:" + id);

            }

            JSONArray shift_trans_pk_json = jsonObject.getJSONArray("shift_trans_pk");
            for (int i = 0; i < shift_trans_pk_json.length(); i++) {
                JSONObject obj = (JSONObject) shift_trans_pk_json.get(i);
                String constraint = obj.getString("Constraint");
                shift_trans_pk.add(constraint);
                //Log.d("retail_store_pk", "constraint:" + constraint);
            }

            JSONArray master_category_json = jsonObject.getJSONArray("master_category");
            for (int i = 0; i < master_category_json.length(); i++) {
                JSONObject obj = (JSONObject) master_category_json.get(i);
                String id = obj.getString("Field");
                master_category.add(id);
                // Log.d("retail_store", "id:" + id);

            }

            JSONArray master_category_pk_json = jsonObject.getJSONArray("master_category_pk");
            for (int i = 0; i < master_category_pk_json.length(); i++) {
                JSONObject obj = (JSONObject) master_category_pk_json.get(i);
                String constraint = obj.getString("Constraint");
                master_category_pk.add(constraint);
                //Log.d("retail_store_pk", "constraint:" + constraint);
            }

            JSONArray master_subcategory_json = jsonObject.getJSONArray("master_subcategory");
            for (int i = 0; i < master_subcategory_json.length(); i++) {
                JSONObject obj = (JSONObject) master_subcategory_json.get(i);
                String id = obj.getString("Field");
                master_subcategory.add(id);
                // Log.d("retail_store", "id:" + id);

            }

            JSONArray master_subcategory_pk_json = jsonObject.getJSONArray("master_subcategory_pk");
            for (int i = 0; i < master_subcategory_pk_json.length(); i++) {
                JSONObject obj = (JSONObject) master_subcategory_pk_json.get(i);
                String constraint = obj.getString("Constraint");
                master_subcategory_pk.add(constraint);
                //Log.d("retail_store_pk", "constraint:" + constraint);
            }
            JSONArray hsn_master_json = jsonObject.getJSONArray("hsn_master");
            for (int i = 0; i < hsn_master_json.length(); i++) {
                JSONObject obj = (JSONObject) hsn_master_json.get(i);
                String id = obj.getString("Field");
                hsn_master.add(id);
                // Log.d("retail_store", "id:" + id);

            }

            JSONArray hsn_master_pk_json = jsonObject.getJSONArray("hsn_master_pk");
            for (int i = 0; i < hsn_master_pk_json.length(); i++) {
                JSONObject obj = (JSONObject) hsn_master_pk_json.get(i);
                String constraint = obj.getString("Constraint");
                hsn_master_pk.add(constraint);
                //Log.d("retail_store_pk", "constraint:" + constraint);
            }

            JSONArray retail_str_dstr_json = jsonObject.getJSONArray("retail_str_dstr");
            for (int i = 0; i < retail_str_dstr_json.length(); i++) {
                JSONObject obj = (JSONObject) retail_str_dstr_json.get(i);
                String id = obj.getString("Field");
                retail_str_dstr.add(id);
                // Log.d("retail_store", "id:" + id);

            }

            JSONArray retail_str_dstr_pk_json = jsonObject.getJSONArray("retail_str_dstr_pk");
            for (int i = 0; i < retail_str_dstr_pk_json.length(); i++) {
                JSONObject obj = (JSONObject) retail_str_dstr_pk_json.get(i);
                String constraint = obj.getString("Constraint");
                retail_str_dstr_pk.add(constraint);
                //Log.d("retail_store_pk", "constraint:" + constraint);
            }

            JSONArray master_uom_json = jsonObject.getJSONArray("master_uom");
            for (int i = 0; i < master_uom_json.length(); i++) {
                JSONObject obj = (JSONObject) master_uom_json.get(i);
                String id = obj.getString("Field");
                master_uom.add(id);
                // Log.d("retail_store", "id:" + id);

            }

            JSONArray master_uom_pk_json = jsonObject.getJSONArray("master_uom_pk");
            for (int i = 0; i < master_uom_pk_json.length(); i++) {
                JSONObject obj = (JSONObject) master_uom_pk_json.get(i);
                String constraint = obj.getString("Constraint");
                master_uom_pk.add(constraint);
                //Log.d("retail_store_pk", "constraint:" + constraint);
            }

            JSONArray customer_type_json = jsonObject.getJSONArray("master_customer_type");
            for (int i = 0; i < customer_type_json.length(); i++) {
                JSONObject obj = (JSONObject) customer_type_json.get(i);
                String id = obj.getString("Field");
                master_customer_type.add(id);
                // Log.d("retail_store", "id:" + id);

            }

            JSONArray customer_type_pk_json = jsonObject.getJSONArray("master_customer_type_pk");
            for (int i = 0; i < customer_type_pk_json.length(); i++) {
                JSONObject obj = (JSONObject) customer_type_pk_json.get(i);
                String constraint = obj.getString("Constraint");
                master_customer_type_pk.add(constraint);
                //Log.d("retail_store_pk", "constraint:" + constraint);
            }

            JSONArray masterState_json = jsonObject.getJSONArray("masterState");
            for (int i = 0; i < masterState_json.length(); i++) {
                JSONObject obj = (JSONObject) masterState_json.get(i);
                String id = obj.getString("Field");
                masterState.add(id);
                // Log.d("retail_store", "id:" + id);

            }

            JSONArray masterState_pk_json = jsonObject.getJSONArray("masterState_pk");
            for (int i = 0; i < masterState_pk_json.length(); i++) {
                JSONObject obj = (JSONObject) masterState_pk_json.get(i);
                String constraint = obj.getString("Constraint");
                masterState_pk.add(constraint);
                //Log.d("retail_store_pk", "constraint:" + constraint);
            }
            JSONArray bankDetails_json = jsonObject.getJSONArray("bank_details");
            for (int i = 0; i < bankDetails_json.length(); i++) {
                JSONObject obj = (JSONObject) bankDetails_json.get(i);
                String id = obj.getString("Field");
                bank_details.add(id);
                // Log.d("retail_store", "id:" + id);

            }

            JSONArray bank_details_pk_json = jsonObject.getJSONArray("bank_details_pk");
            for (int i = 0; i < bank_details_pk_json.length(); i++) {
                JSONObject obj = (JSONObject) bank_details_pk_json.get(i);
                String constraint = obj.getString("Constraint");
                bank_details_pk.add(constraint);
                //Log.d("retail_store_pk", "constraint:" + constraint);
            }

            JSONArray store_configuration_json = jsonObject.getJSONArray("store_configuration");
            for (int i = 0; i < store_configuration_json.length(); i++) {
                JSONObject obj = (JSONObject) store_configuration_json.get(i);
                String id = obj.getString("Field");
                store_configuration.add(id);
                // Log.d("retail_store", "id:" + id);

            }

            JSONArray store_configuration_pk_json = jsonObject.getJSONArray("store_configuration_pk");
            for (int i = 0; i < store_configuration_pk_json.length(); i++) {
                JSONObject obj = (JSONObject) store_configuration_pk_json.get(i);
                String constraint = obj.getString("Constraint");
                store_configuration_pk.add(constraint);
                //Log.d("retail_store_pk", "constraint:" + constraint);
            }


            JSONArray retail_store_cust_reject_json = jsonObject.getJSONArray("retail_store_cust_reject");
            for (int i = 0; i < retail_store_cust_reject_json.length(); i++) {
                JSONObject obj = (JSONObject) retail_store_cust_reject_json.get(i);
                String id = obj.getString("Field");
                retail_store_cust_reject.add(id);
                // Log.d("retail_store", "id:" + id);

            }

            JSONArray retail_store_cust_reject_pk_json = jsonObject.getJSONArray("retail_store_cust_reject_pk");
            for (int i = 0; i < retail_store_cust_reject_pk_json.length(); i++) {
                JSONObject obj = (JSONObject) retail_store_cust_reject_pk_json.get(i);
                String constraint = obj.getString("Constraint");
                retail_store_cust_reject_pk.add(constraint);
                //Log.d("retail_store_pk", "constraint:" + constraint);
            }

            JSONArray retail_credit_cust_json = jsonObject.getJSONArray("retail_credit_cust");
            for (int i = 0; i < retail_credit_cust_json.length(); i++) {
                JSONObject obj = (JSONObject) retail_credit_cust_json.get(i);
                String id = obj.getString("Field");
                retail_credit_cust.add(id);
                // Log.d("retail_store", "id:" + id);

            }

            JSONArray retail_credit_cust_pk_json = jsonObject.getJSONArray("retail_credit_cust_pk");
            for (int i = 0; i < retail_credit_cust_pk_json.length(); i++) {
                JSONObject obj = (JSONObject) retail_credit_cust_pk_json.get(i);
                String constraint = obj.getString("Constraint");
                retail_credit_cust_pk.add(constraint);
                //Log.d("retail_store_pk", "constraint:" + constraint);
            }


            JSONArray customerReturnDetail_json = jsonObject.getJSONArray("customerReturnDetail");
            for (int i = 0; i < customerReturnDetail_json.length(); i++) {
                JSONObject obj = (JSONObject) customerReturnDetail_json.get(i);
                String id = obj.getString("Field");
                customerReturnDetail.add(id);
                // Log.d("retail_store", "id:" + id);

            }

            JSONArray customerReturnDetail_pk_json = jsonObject.getJSONArray("customerReturnDetail_pk");
            for (int i = 0; i < customerReturnDetail_pk_json.length(); i++) {
                JSONObject obj = (JSONObject) customerReturnDetail_pk_json.get(i);
                String constraint = obj.getString("Constraint");
                customerReturnDetail_pk.add(constraint);
                //Log.d("retail_store_pk", "constraint:" + constraint);
            }


            JSONArray customerReturnMaster_json = jsonObject.getJSONArray("customerReturnMaster");
            for (int i = 0; i < customerReturnMaster_json.length(); i++) {
                JSONObject obj = (JSONObject) customerReturnMaster_json.get(i);
                String id = obj.getString("Field");
                customerReturnMaster.add(id);
                // Log.d("retail_store", "id:" + id);

            }

            JSONArray customerReturnMaster_pk_json = jsonObject.getJSONArray("customerReturnMaster_pk");
            for (int i = 0; i < customerReturnMaster_pk_json.length(); i++) {
                JSONObject obj = (JSONObject) customerReturnMaster_pk_json.get(i);
                String constraint = obj.getString("Constraint");
                customerReturnMaster_pk.add(constraint);
                //Log.d("retail_store_pk", "constraint:" + constraint);
            }

            JSONArray customerLedger_json = jsonObject.getJSONArray("customerLedger");
            for (int i = 0; i < customerLedger_json.length(); i++) {
                JSONObject obj = (JSONObject) customerLedger_json.get(i);
                String id = obj.getString("Field");
                customerLedger.add(id);
                // Log.d("retail_store", "id:" + id);

            }

            JSONArray customerLedger_pk_json = jsonObject.getJSONArray("customerLedger_pk");
            for (int i = 0; i < customerLedger_pk_json.length(); i++) {
                JSONObject obj = (JSONObject) customerLedger_pk_json.get(i);
                String constraint = obj.getString("Constraint");
                customerLedger_pk.add(constraint);
                //Log.d("retail_store_pk", "constraint:" + constraint);
            }

            JSONArray retailCreditBillDetails_JSON = jsonObject.getJSONArray("retail_credit_bill_details");
            for (int i = 0; i < retailCreditBillDetails_JSON.length(); i++) {
                JSONObject obj = (JSONObject) retailCreditBillDetails_JSON.get(i);
                String id = obj.getString("Field");
                retail_credit_bill_details.add(id);
                // Log.d("retail_store", "id:" + id);

            }

            JSONArray stock_register_json = jsonObject.getJSONArray("stock_register");
            for (int i = 0; i < stock_register_json.length(); i++) {
                JSONObject obj = (JSONObject) stock_register_json.get(i);
                String id = obj.getString("Field");
                stock_register.add(id);
                // Log.d("retail_store", "id:" + id);

            }

            JSONArray stock_register_pk_JSON = jsonObject.getJSONArray("stock_register_pk");
            for (int i = 0; i < stock_register_pk_JSON.length(); i++) {
                JSONObject obj = (JSONObject) stock_register_pk_JSON.get(i);
                String constraint = obj.getString("Constraint");
                stock_register_pk.add(constraint);
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
            createDynamicDatabase(context, "shift_trans", shift_trans, shift_trans_pk);
            createDynamicDatabase(context, "master_category", master_category, master_category_pk);
            createDynamicDatabase(context, "master_subcategory", master_subcategory, master_subcategory_pk);
            createDynamicDatabase(context, "master_subcategory", master_subcategory, master_subcategory_pk);
            createDynamicDatabase(context, "hsn_master", hsn_master, hsn_master_pk);
            createDynamicDatabase(context, "retail_str_dstr", retail_str_dstr, retail_str_dstr_pk);
            createDynamicDatabase(context, "master_uom", master_uom, master_uom_pk);
            createDynamicDatabase(context, "master_customer_type", master_customer_type, master_customer_type_pk);
            createDynamicDatabase(context, "masterState", masterState, masterState_pk);
            createDynamicDatabase(context, "bank_details", bank_details, bank_details_pk);
            createDynamicDatabase(context, "store_configuration", store_configuration, store_configuration_pk);
            createDynamicDatabase(context, "retail_store_cust_reject", retail_store_cust_reject, retail_store_cust_reject_pk);
            createDynamicDatabase(context, "retail_credit_cust", retail_credit_cust, retail_credit_cust_pk);
            createDynamicDatabase(context, "customerReturnDetail", customerReturnDetail, customerReturnDetail_pk);
            createDynamicDatabase(context, "customerReturnMaster", customerReturnMaster, customerReturnMaster_pk);
            createDynamicDatabase(context, "customerLedger", customerLedger, customerLedger_pk);
            createDynamicDatabaseWithoutKey(context, "retail_credit_bill_details", retail_credit_bill_details);
            createDynamicDatabase(context, "stock_register", stock_register,stock_register_pk);

            dbOk=true;
            //loadingDialog.cancelDialog();
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

    public void createDynamicDatabaseWithoutKey(Context context, String tableName, ArrayList<String> title) {
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
            querryString += title.get(i) + " NVARCHAR(30) ";
            querryString = "CREATE TABLE IF NOT EXISTS " + tableName + "(" + querryString + " );";
            //  System.out.println("Create Table Stmt : " + querryString);
            myDataBase.execSQL(querryString);
            myDataBase.close();

        } catch (SQLException ex) {
            Log.i("xyz CreateDB Exception ", ex.getMessage());
        }
    }
}
