package com.retailstreet.mobilepos.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.retailstreet.mobilepos.Utils.StringWithTag;
import com.retailstreet.mobilepos.View.ApplicationContextProvider;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;


public class BillGenerator {
    Context context;
     HashMap<String, String> orderList = new HashMap<>();
    String itemGuid;
    String customerID;

    String billDetailId ;
    String billMasterId;
    String billNumber ;
    String CATEGORY_GUID;
    String SUBCAT_GUID;
    String ITEM_CODE;
    String QTY;
    String UOM_GUID;
    String BATCHNO;
    String COST_PRICE;
    String NETVALUE;
    String DISCOUNT_PERC;
    String DISCOUNT_VALUE;
    String TOTALVALUE;
    String MRP;
    String BILLDETAILSTATUS;
    String HSN;
    String CGSTPERCENTAGE;
    String SGSTPERCENTAGE;
    String IGSTPERCENTAGE;
    String ADDITIONALPERCENTAGE;
    String CGST;
    String SGST;
    String IGST;
    String ADDITIONALCESS;
    String TOTALGST;
    
    

        String SALEDATETIME ;
        String SALEDATE ;
        String SALETIME;
        String MASTERCUSTOMERGUID = " " ;
        String MASTERSTOREGUID ;
        String MASTERTERMINALGUID ;
        String MASTERSHIFTGUID ;
        String USER_GUID ;
        String CUST_MOBILENO ;
        String NETVALUEM="0.00" ;
        String TAXVALUE ="0.00" ;
        String TOTAL_AMOUNT = "0.00" ;
        String DELIVERY_TYPE_GUID = " " ;
        String BILL_PRINT ;
        String TOTAL_BILL_AMOUNT ;
        String NO_OF_ITEMS ;
        String BILLSTATUS ;
        String ISSYNCED ;
        String RECEIVED_CASH ;
        String BALANCE_CASH;
        String ROUND_OFF ;
        String NETDISCOUNT="0.00";


        String BILLPAYDETAILID;
        String BILLMASTERID;
        String MASTERPAYMODEGUID ;
        String PAYAMOUNT ;
        String TRANSACTIONNUMBER ;
        String ADDITIONALPARAM1 ;
        String ADDITIONALPARAM2 ;
        String ADDITIONALPARAM3 ;
        String BILLPAYDETAIL_STATUS ;

        public  BillGenerator(){
            context = ApplicationContextProvider.getContext();
        }

    public BillGenerator( String customerID, String receivedCash,String balanceCash, String deliveryTypeGuid, String MasterPayModeGuid) {
        context = ApplicationContextProvider.getContext();
        initMap();
        context = ApplicationContextProvider.getContext();
        this.customerID = customerID;
        NO_OF_ITEMS = String.valueOf(orderList.size());
        billNumber = getBillNumber();
        billMasterId = getTimeStamp();
        BILLPAYDETAILID = getTimeStamp();
        RECEIVED_CASH = receivedCash;
        BALANCE_CASH = balanceCash;
        DELIVERY_TYPE_GUID = deliveryTypeGuid;
        this.MASTERPAYMODEGUID = MasterPayModeGuid;



        for (String key:orderList.keySet()) {
            String orderId = key;
            String count = Objects.requireNonNull(orderList.get(key));
            GenerateBillDetails(orderId,count,customerID);
        }
       GenerateBillMaster(customerID);
        GenerateBillPayDetail();


    }

    public void GenerateBillDetails( String orderid , String count, String custId) {
            billDetailId = getTimeStamp();
            itemGuid = getFromStockMaster(orderid,"ITEM_GUID");
            CATEGORY_GUID = getFromProductMaster(itemGuid,"CATEGORY_GUID");
            SUBCAT_GUID = getFromProductMaster(itemGuid,"SUBCATEGORY_ID");
            ITEM_CODE = getFromProductMaster(itemGuid,"ITEM_CODE");
            QTY = count;
            UOM_GUID = getFromProductMaster(itemGuid,"UOM_GUID");
            BATCHNO = getFromStockMaster(orderid,"BATCH_NO");
            COST_PRICE = getFromStockMaster(orderid,"P_PRICE");
            DISCOUNT_PERC = getFromStockMaster(orderid,"SALESDISCOUNTBYPERCENTAGE");
            DISCOUNT_VALUE = getDiscountValue(orderid);
            if(!DISCOUNT_VALUE.isEmpty())
            NETDISCOUNT = String.valueOf(Double.parseDouble(DISCOUNT_VALUE)+ Double.parseDouble(NETDISCOUNT));
            SGST = getSGST(orderid);
            CGST = getCGST(orderid);
            NETVALUE = getNetValue(CGST,SGST,orderid);
            TOTALVALUE = getTOTALVALUE(orderid);
            MRP = getFromStockMaster(orderid,"MRP");
            BILLDETAILSTATUS = "1";
            HSN = getFromProductMaster(itemGuid,"HSN");
            CGSTPERCENTAGE = getFromStockMaster(orderid,"CGST");
            SGSTPERCENTAGE = getFromStockMaster(orderid,"SGST");
            IGSTPERCENTAGE = getFromStockMaster(orderid,"IGST");
            IGST= getFromStockMaster(orderid,"IGST");
            ADDITIONALPERCENTAGE ="0";
            ADDITIONALCESS = "0.00";
            TOTALGST = String.valueOf(Double.parseDouble(SGST) + Double.parseDouble(CGST));
            if(!NETVALUE.isEmpty())
            NETVALUEM =  String.valueOf(Double.parseDouble(NETVALUE) + Double.parseDouble(NETVALUEM));
            if(SGST.isEmpty()) {
                SGST = "0.00";
            }
            if(CGST.isEmpty()){
                CGST="0.00";
            }

            TAXVALUE = String.valueOf( Double.parseDouble(SGST)+ Double.parseDouble(CGST) + Double.parseDouble(TAXVALUE));
            if(TOTALVALUE.isEmpty())
                TOTALVALUE = "0.00";
            TOTAL_AMOUNT = String.valueOf( Double.parseDouble(TOTALVALUE) +Double.parseDouble(TOTAL_AMOUNT));

            new ControllerBillDetail().createBillDetailData( billDetailId,  billMasterId,  billNumber,  CATEGORY_GUID,  SUBCAT_GUID,  itemGuid,  ITEM_CODE,  QTY,  UOM_GUID,  BATCHNO,  COST_PRICE,  NETVALUE,  DISCOUNT_PERC,  DISCOUNT_VALUE,  TOTALVALUE,  MRP,  BILLDETAILSTATUS,  HSN,  CGSTPERCENTAGE,  SGSTPERCENTAGE,  IGSTPERCENTAGE,  ADDITIONALPERCENTAGE,  CGST,  SGST,  IGST,  ADDITIONALCESS,  TOTALGST);
    }

    public void GenerateBillMaster( String custId) {

        SALEDATETIME = getSaleDateAndTime();
        SALEDATE = getSaleDate();
        SALETIME = getSaleTime();
        if(custId!=null && !custId.isEmpty())
        MASTERCUSTOMERGUID = getFromMasterCustomer(custId,"CUSTOMERGUID");
        MASTERSTOREGUID = getFromRetailStore("STORE_GUID");
        MASTERTERMINALGUID = getFromTerminalConfig(MASTERSTOREGUID,"TERMINAL_GUID");
        MASTERSHIFTGUID = getFromMasterShift(MASTERSTOREGUID,"SHIFTGUID");
        USER_GUID  = getFromGroupUserMaster("USER_GUID");
        CUST_MOBILENO = getFromMasterCustomer(custId,"MOBILE_NO");
        BILL_PRINT ="1";
        TOTAL_BILL_AMOUNT = TOTAL_AMOUNT;
        BILLSTATUS ="1";
        ISSYNCED = "0";
        ROUND_OFF ="0.00";

        new ControllerBillMaster().createBillMasterData(billMasterId, billNumber, SALEDATETIME, SALEDATE, SALETIME, MASTERCUSTOMERGUID, MASTERSTOREGUID, MASTERTERMINALGUID, MASTERSHIFTGUID, USER_GUID, CUST_MOBILENO, NETVALUE, TAXVALUE, TOTAL_AMOUNT, DELIVERY_TYPE_GUID, BILL_PRINT, TOTAL_BILL_AMOUNT, NO_OF_ITEMS, BILLSTATUS, ISSYNCED, RECEIVED_CASH, BALANCE_CASH, ROUND_OFF, NETDISCOUNT);

    }

    public void GenerateBillPayDetail(){
        BILLMASTERID=  billMasterId;
        PAYAMOUNT = TOTAL_AMOUNT;
        TRANSACTIONNUMBER = billNumber;
        ADDITIONALPARAM1 ="0";
        ADDITIONALPARAM2 = "0";
        ADDITIONALPARAM3 = "0";
        BILLPAYDETAIL_STATUS = "1";

        new ControllerBillPayDetail().createBillPayDetail( BILLPAYDETAILID,  BILLMASTERID,  MASTERPAYMODEGUID,  PAYAMOUNT,  TRANSACTIONNUMBER,  ADDITIONALPARAM1,  ADDITIONALPARAM2,  ADDITIONALPARAM3,  BILLPAYDETAIL_STATUS);
    }



    public String getTimeStamp() {
        Date date = new Date();
        //This method returns the time in millis
        long timeMilli = date.getTime();
        return String.valueOf(timeMilli);
    }

    private String getBillNumber() {

        String selectQuery = "SELECT BILLNO FROM retail_str_sales_detail";
        SQLiteDatabase db = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);          //Opens database in writable mode.
        Cursor cursor = db.rawQuery(selectQuery, null);
        int billInt = 1;
        if (cursor.moveToLast()) {

            String billno = cursor.getString(0);
            Log.d("BillNumRetrieved", "getBillNumber: String: "+billno);
            billInt = Integer.parseInt(billno);
            billInt = billInt+1;
            Log.d("BillNumRetrieved", "getBillNumber: IncrementedValue: "+billInt);
        }

        cursor.close();
        db.close();
        return String.valueOf(billInt);
    }

    private String getFromProductMaster( String itemGuid, String column) {

        SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
        String result = "";
        String selectQuery = "SELECT "+column+" FROM retail_store_prod_com where ITEM_GUID ='"+itemGuid+"'";
        Cursor cursor = mydb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

                result = cursor.getString(0);
        }
        cursor.close();
        Log.d("DataRetrieved", "getFromProductMaster: "+column+":"+result);
        mydb.close();
        return result;
    }


    private String getFromStockMaster( String stockid,String column) {
        SQLiteDatabase  mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
        String result="";
        String selectQuery = "SELECT "+column+" FROM retail_str_stock_master where STOCK_ID ="+stockid;
        Cursor cursor = mydb.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {

                result= cursor.getString(0);
        }
        cursor.close();
        mydb.close();
        Log.d("DataRetrieved", "getFromStockMaster: "+column+": "+result);
        return result;
    }

    public  String getNetValue( String cgst, String sgst,String stock_id){
        SQLiteDatabase  mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
        double gstD = Double.parseDouble(cgst)+Double.parseDouble(sgst);
        String query = "select S_PRICE, count from cart WHERE STOCK_ID = "+stock_id;
        Cursor result = mydb.rawQuery( query, null );

        if(result==null)
            return "0";

        double total = 0.0; // Your default if none is found
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            double itemprice = Double.parseDouble(result.getString(0));
             itemprice = itemprice-((itemprice*gstD)/100);
            int itemcount= Integer.parseInt(result.getString(1));
            total += itemprice*itemcount;
        }
        result.close();
        mydb.close();
        return String.valueOf(total);
    }

    public    String getSGST(String stockID){
        SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
        String query = "select SGST,S_PRICE, count from cart WHERE STOCK_ID = "+stockID;
        Cursor result = mydb.rawQuery( query, null );

        if(result==null)
            return "0";

        double total = 0.0; // Your default if none is found
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {

            double itemSgst = Double.parseDouble(result.getString(0));
            double itemPrice = Double.parseDouble(result.getString(1));
            double sgstForone= ((itemSgst*itemPrice)/100);
            int itemcount= Integer.parseInt(result.getString(2));
             total = sgstForone*itemcount;

        }
        result.close();
        mydb.close();
        return String.valueOf(total);
    }

    public  String getCGST( String stockID){
        SQLiteDatabase  mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
        String query = "select CGST,S_PRICE, count from cart WHERE STOCK_ID = "+stockID;
        Cursor result = mydb.rawQuery( query, null );

        if(result==null)
            return "0";

        double total = 0.0; // Your default if none is found
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {

            double itemSgst = Double.parseDouble(result.getString(0));
            double itemPrice = Double.parseDouble(result.getString(1));
            double sgstForone= ((itemSgst*itemPrice)/100);
            int itemcount= Integer.parseInt(result.getString(2));
            total = sgstForone*itemcount;

        }
        result.close();
        mydb.close();
        return String.valueOf(total);

    }

    private String getTOTALVALUE(String stockID){
        SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
        String query = "select S_PRICE, count from cart WHERE STOCK_ID = "+stockID;
        Cursor result = mydb.rawQuery( query, null );

        if(result==null)
            return "0";

        double total = 0.0; // Your default if none is found
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            double itemprice = Double.parseDouble(result.getString(0));
            int itemcount= Integer.parseInt(result.getString(1));
            total += itemprice*itemcount;
        }
        result.close();
        mydb.close();
        return String.valueOf(total);

    }

    private String getSaleDateAndTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
       return formatter.format(date);
    }

    private String getSaleDate(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy",Locale.getDefault());
        String strDate = formatter.format(date);
        return  strDate;
    }

    private String getSaleTime(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss",Locale.getDefault());
        String strTime = formatter.format(date);
        return  strTime;
    }

    private String getFromMasterCustomer(String custId, String column){
        SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
        String result="";
        String selectQuery = "SELECT "+column+" FROM retail_cust where CUST_ID ='"+custId+"'";
        Cursor cursor = mydb.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {

            result= cursor.getString(0);
        }
        cursor.close();
        mydb.close();
        Log.d("DataRetrieved", "getFromCustomerMaster: "+result);
        return result;

    }
    private String getFromMasterShift(String storeGuid, String column){
        SQLiteDatabase  mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
        String result="";
        String selectQuery = "SELECT "+column+" FROM master_shift where STORE_GUID ='"+storeGuid+"'";
        Cursor cursor = mydb.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {

            result= cursor.getString(0);
        }
        cursor.close();
        mydb.close();
        Log.d("DataRetrieved", "getFromMasterShift: "+result);
        return result;

    }

    private String getFromRetailStore( String column){
        SQLiteDatabase  mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
        String result="";
        String selectQuery = "SELECT "+column+" FROM retail_store ";
        Cursor cursor = mydb.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {

            result= cursor.getString(0);
        }
        cursor.close();
        mydb.close();
        Log.d("DataRetrieved", "getFromRetailStore: "+result);
        return result;

    }

    private String getFromTerminalConfig(String storeId, String column){
        SQLiteDatabase  mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
        String result="";
        String selectQuery = "SELECT "+column+" FROM terminal_configuration where STORE_GUID ='"+storeId+"'";
        Cursor cursor = mydb.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {

            result= cursor.getString(0);
        }
        cursor.close();
        mydb.close();
        Log.d("DataRetrieved", "getFromCustomerMaster: "+result);
        return result;

    }
    private String getFromGroupUserMaster(String column){
        SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
        String username;
        SharedPreferences sh = context.getSharedPreferences("com.retailstreet.mobilepos", MODE_PRIVATE);
        username = sh.getString("username", "");

        String result="";
        String selectQuery = "SELECT "+column+" FROM group_user_master where USER_NAME ="+username;
        Cursor cursor = mydb.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {

            result= cursor.getString(0);
        }
        cursor.close();
        mydb.close();
        Log.d("DataRetrieved", "getFromUserMaster: "+result);
        return result;

    }

    public String getDiscountValue(String stockID){
        SQLiteDatabase  mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
        String query = "select SALESDISCOUNTBYAMOUNT, count from cart WHERE STOCK_ID = "+stockID;
        Cursor result = mydb.rawQuery( query, null );

        if(result==null)
            return "0";

        double total = 0.0; // Your default if none is found
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            double discount = Double.parseDouble(result.getString(0));
            int itemcount= Integer.parseInt(result.getString(1));
            total += discount*itemcount;
        }
        result.close();
        mydb.close();
        return String.valueOf(total);

    }

    private void initMap(){
        try {
            orderList.clear();
            SQLiteDatabase   mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            Cursor cursor  = mydb.rawQuery("SELECT * FROM cart", null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    String id = cursor.getString(0);
                    String cnt = cursor.getString(2);
                    orderList.put(id,cnt);
                    cursor.moveToNext();
                }
            }
            mydb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
