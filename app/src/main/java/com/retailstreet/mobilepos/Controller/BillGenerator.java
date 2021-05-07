package com.retailstreet.mobilepos.Controller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.util.Log;

import com.retailstreet.mobilepos.Model.CreditBillDetails;
import com.retailstreet.mobilepos.Model.StockRegister;
import com.retailstreet.mobilepos.Utils.IDGenerator;
import com.retailstreet.mobilepos.Utils.WorkManagerSync;
import com.retailstreet.mobilepos.View.ApplicationContextProvider;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;
import static com.retailstreet.mobilepos.Utils.Constants.DBNAME;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

public class BillGenerator {
    Context context;
    private static Set<String> smsSetFromPrefs = new HashSet<>();
     HashMap<String, String> orderList = new HashMap<>();
    String itemGuid;

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
        String BILLMASTERGUID;


        String BILLPAYDETAILID;
        String BILLMASTERID;
        String MASTERPAYMODEGUID ;
        String PAYAMOUNT ;
        String TRANSACTIONNUMBER ;
        String ADDITIONALPARAM1 ;
        String ADDITIONALPARAM2 ;
        String ADDITIONALPARAM3 ;
        String BILLPAYDETAIL_STATUS ;


    String STORE_GUID;
    String CUSTOMERMOBILENO;
    String CUSTOMERGUID;
    String BILLNO;
    String BILLDATE;
    String ITEM_GUID;
    String ITEM_NAME;
    String SELLINGPRICE;


        public  BillGenerator(){
            context = ApplicationContextProvider.getContext();
        }

    public BillGenerator( String customerID, String receivedCash,String balanceCash, String deliveryTypeGuid, HashMap<String , String[]> payModeData, String additionDisc, String redeemNumber,double advanceAmount) {
        context = ApplicationContextProvider.getContext();
        GetSetFromPrefs();
        initMap();
        context = ApplicationContextProvider.getContext();
        NO_OF_ITEMS = String.valueOf(orderList.size());
        billNumber = getBillNumber();
        billMasterId = getTimeStamp();
        BILLMASTERGUID = IDGenerator.getUUID();
        RECEIVED_CASH = receivedCash;
        BALANCE_CASH=balanceCash;
        DELIVERY_TYPE_GUID = deliveryTypeGuid;
        STORE_GUID  = MASTERSTOREGUID = getFromRetailStore("STORE_GUID");
        CUSTOMERMOBILENO = CUST_MOBILENO = getFromMasterCustomer(customerID,"MOBILE_NO");
        BILLDATE =  SALEDATETIME = getSaleDateAndTime();
        CUSTOMERGUID = customerID;
         BILLNO = billNumber;


        for (String key:orderList.keySet()) {
            String count = Objects.requireNonNull(orderList.get(key));
            Boolean isCreditBill =  payModeData.containsKey("RX");
            GenerateBillDetails(key,count,isCreditBill);
            setNewQuantity(key);
        }


       GenerateBillMaster(customerID, additionDisc);
        GenerateBillPayDetail(payModeData);
        if(advanceAmount>0){
            new ControllerCreditPay().updateAdvanceSettlement(String.valueOf(advanceAmount),CUSTOMERGUID,BILLNO);
        }

        if(redeemNumber!=null && !redeemNumber.trim().isEmpty()){

                updateRetunMaster(redeemNumber,billNumber);
        }
        smsSetFromPrefs.add(billNumber);
        SaveSetsInPrefs();

        try {
            new WorkManagerSync(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

            try {
                new WorkManagerSync(6);
            } catch (Exception e) {
                e.printStackTrace();
            }
        try {
            new WorkManagerSync(4);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            new WorkManagerSync(10);
        } catch (Exception e) {
            e.printStackTrace();
        }

        }


    public void GenerateBillDetails( String orderid , String count, Boolean isCreditBill) {
            billDetailId = getTimeStamp();
            itemGuid = getFromStockMaster(orderid,"ITEM_GUID");
            CATEGORY_GUID = getFromProductMaster(itemGuid,"CATEGORY_GUID");
            SUBCAT_GUID = getFromProductMaster(itemGuid,"SUB_CATEGORYGUID");
            ITEM_CODE = getFromProductMaster(itemGuid,"ITEM_CODE");
            QTY = count+".0000";
            UOM_GUID = getFromProductMaster(itemGuid,"UOM_GUID");
            BATCHNO = getFromStockMaster(orderid,"BATCH_NO");
            COST_PRICE = getFromStockMaster(orderid,"P_PRICE");
            CGSTPERCENTAGE = getFromProductMaster(itemGuid,"CGST");
            SGSTPERCENTAGE = getFromProductMaster(itemGuid,"SGST");
            DISCOUNT_PERC = getFromStockMaster(orderid,"SALESDISCOUNTBYPERCENTAGE");
            DISCOUNT_VALUE = getDiscountValue(orderid);
            if(!DISCOUNT_VALUE.isEmpty())
            NETDISCOUNT = String.valueOf(Double.parseDouble(DISCOUNT_VALUE)+ Double.parseDouble(NETDISCOUNT));
            SGST = getSGST(orderid,SGSTPERCENTAGE);
            CGST = getCGST(orderid,CGSTPERCENTAGE);
            NETVALUE = getNetValue(CGST,SGST,orderid);
            TOTALVALUE = getTOTALVALUE(orderid);
            MRP = getFromStockMaster(orderid,"MRP");
            BILLDETAILSTATUS = "1";
            HSN = getFromProductMaster(itemGuid,"HSN");
            IGSTPERCENTAGE = "0.00";
            IGST= "0.00";
            ADDITIONALPERCENTAGE ="0";
            ADDITIONALCESS = "0.00";
            TOTALGST = String.valueOf(Double.parseDouble(SGST) + Double.parseDouble(CGST));
            if(!NETVALUE.isEmpty()) {
                Log.d("NETvalueCheck", "GenerateBillDetails: NetValue: "+NETVALUE);
                NETVALUEM = String.valueOf(Double.parseDouble(NETVALUE) + Double.parseDouble(NETVALUEM));
                Log.d("NetValueMCalculated", "GenerateBillDetails: "+NETVALUEM);
            }

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
    
            if(isCreditBill){

                try {
                    ITEM_GUID = itemGuid;
                    SELLINGPRICE = String.valueOf(Double.parseDouble(NETVALUE) / Double.parseDouble(QTY));
                    ITEM_NAME = getFromStockMaster(orderid,"PROD_NM");
                    Log.d("CreditInsert", "GenerateCreditBillDetails: ");
                    CreditBillDetails creditBillDetails = new CreditBillDetails( STORE_GUID,  CUSTOMERMOBILENO,  CUSTOMERGUID,  BILLNO,  BILLDATE,  ITEM_GUID,  ITEM_NAME,  MRP,  SELLINGPRICE,  QTY,  TOTALVALUE,  TOTALGST,  CGST,  SGST,  IGST,  DISCOUNT_PERC, DISCOUNT_VALUE);
                    InsertCreditBillDetails(creditBillDetails);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
       String  REGISTERGUID = IDGenerator.getUUID();
       String  MASTERORG_GUID = getFromRetailStore("MASTERORG_GUID");
       String VENDOR_GUID = getFromStockMaster(orderid,"VENDOR_GUID");
       String LINETYPE = "DEBIT";
       String TRANSACTIONTYPE = "SALE";
       TRANSACTIONNUMBER = billNumber;
       String TRANSACTIONDATE = BILLDATE;
       String BARCODE = getFromStockMaster(orderid,"BARCODE");
       String SALESPRICE = TOTALVALUE ;
       String  WHOLESALEPRICE = getFromStockMaster(orderid, "WHOLE_SPRICE");
       String INTERNETPRICE = getFromStockMaster(orderid, "INTERNET_PRICE");
       String SPECIALPRICE = getFromStockMaster(orderid, "SPEC_PRICE");
        ISSYNCED ="0";
        StockRegister stockRegister = new StockRegister(REGISTERGUID, MASTERORG_GUID, STORE_GUID, VENDOR_GUID, LINETYPE, TRANSACTIONTYPE, TRANSACTIONNUMBER, TRANSACTIONDATE, ITEM_GUID, UOM_GUID, QTY, BATCHNO, BARCODE, SALESPRICE, WHOLESALEPRICE, INTERNETPRICE, SPECIALPRICE, ISSYNCED);
        InsertStockRegister(stockRegister);

    }

    public void InsertStockRegister(StockRegister prod) {
        try {

            SQLiteDatabase myDataBase = context.openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE, null);
            ContentValues contentValues = new ContentValues();
            contentValues.put("REGISTERGUID", prod.getREGISTERGUID());
            contentValues.put("MASTERORG_GUID", prod.getMASTERORG_GUID());
            contentValues.put("STORE_GUID", prod.getSTORE_GUID());
            contentValues.put("VENDOR_GUID", prod.getVENDOR_GUID());
            contentValues.put("LINETYPE", prod.getLINETYPE());
            contentValues.put("TRANSACTIONTYPE", prod.getTRANSACTIONTYPE());
            contentValues.put("TRANSACTIONNUMBER", prod.getTRANSACTIONNUMBER());
            contentValues.put("TRANSACTIONDATE", prod.getTRANSACTIONDATE());
            contentValues.put("ITEM_GUID", prod.getITEM_GUID());
            contentValues.put("UOM_GUID", prod.getUOM_GUID());
            contentValues.put("QUANTITY", prod.getQUANTITY());
            contentValues.put("BATCHNO", prod.getBATCHNO());
            contentValues.put("BARCODE", prod.getBARCODE());
            contentValues.put("SALESPRICE", prod.getSALESPRICE());
            contentValues.put("WHOLESALEPRICE", prod.getWHOLESALEPRICE());
            contentValues.put("INTERNETPRICE", prod.getINTERNETPRICE());
            contentValues.put("SPECIALPRICE", prod.getSPECIALPRICE());
            contentValues.put("ISSYNCED", prod.getISSYNCED());
            myDataBase.insert("stock_register", null, contentValues);

            myDataBase.close();
            Log.d("Insertion Successful", "StockRegisters: ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void GenerateBillMaster( String custId, String additionDiscount) {

        try {
            SALEDATE = getSaleDate();
            SALETIME = getSaleTime();
            if(custId!=null && !custId.isEmpty())
            MASTERCUSTOMERGUID = custId;
            MASTERTERMINALGUID = getFromTerminalConfig(MASTERSTOREGUID,"TERMINALCONFIG_GUID");
            MASTERSHIFTGUID = getFromMasterShift(MASTERSTOREGUID,"SHIFTGUID");
            USER_GUID  = getFromGroupUserMaster("USER_GUID");
            BILL_PRINT ="1";
            TOTAL_AMOUNT = String.valueOf( Double.parseDouble(TOTAL_AMOUNT)-Double.parseDouble(additionDiscount));
            TOTAL_BILL_AMOUNT = TOTAL_AMOUNT;
            BILLSTATUS ="1";
            ISSYNCED = "0";
            ROUND_OFF ="0.00";
            NETDISCOUNT = String.valueOf(Double.parseDouble(NETDISCOUNT)+Double.parseDouble(additionDiscount));

            new ControllerBillMaster().createBillMasterData(billMasterId, billNumber, SALEDATETIME, SALEDATE, SALETIME, MASTERCUSTOMERGUID, MASTERSTOREGUID, MASTERTERMINALGUID, MASTERSHIFTGUID, USER_GUID, CUST_MOBILENO, NETVALUEM, TAXVALUE, TOTAL_AMOUNT, DELIVERY_TYPE_GUID, BILL_PRINT, TOTAL_BILL_AMOUNT, NO_OF_ITEMS, BILLSTATUS, ISSYNCED, RECEIVED_CASH, BALANCE_CASH, ROUND_OFF, NETDISCOUNT,BILLMASTERGUID);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    public void GenerateBillPayDetail(HashMap<String , String[]> payModeData){
        BILLMASTERID=  billMasterId;
        TRANSACTIONNUMBER = billNumber;
        BILLPAYDETAIL_STATUS = "1";
        ADDITIONALPARAM1 = "0";
        ADDITIONALPARAM2 = "0";
        ADDITIONALPARAM3 = "0";


        for (Map.Entry<String,String[]> entry : payModeData.entrySet()) {
            String[] data = entry.getValue();
            Log.d("AddingBillPay", "GenerateBillPayDetail: "+entry.getKey());

            if(data[1]!=null && !data[1].trim().isEmpty()) {
                TRANSACTIONNUMBER = data[1];
            }else {
                TRANSACTIONNUMBER = billNumber;
            }

            MASTERPAYMODEGUID=data[0];
            PAYAMOUNT = data[2];

            if(!data[3].trim().isEmpty()) {
                ADDITIONALPARAM1 = data[3];
            }else {
                ADDITIONALPARAM1 = "0";
            }

            if(!data[4].trim().isEmpty()) {
                ADDITIONALPARAM2 = data[4];
            }else {
                ADDITIONALPARAM2 = "0";
            }

            if(!data[5].trim().isEmpty()) {
                ADDITIONALPARAM3 = data[5];
            }else {
                ADDITIONALPARAM3 = "0";
            }

            if(entry.getKey().equals("RX")){
                String currentLImit = getCreditDetails(CUSTOMERGUID);
                String newLimit = String.valueOf(Double.parseDouble(currentLImit) - Double.parseDouble(PAYAMOUNT));
                updateCreditCust(newLimit,CUSTOMERGUID);
                new ControllerCreditPay().updateCreditCustomer(PAYAMOUNT,CUSTOMERGUID);
                new ControllerCreditPay().updateCustLedger(PAYAMOUNT,CUSTOMERGUID,BILLNO);
            }
            BILLPAYDETAILID = getTimeStamp();

            if(!entry.getKey().equals("RX"))
            new ControllerBillPayDetail().createBillPayDetail( BILLPAYDETAILID,  BILLMASTERID,  MASTERPAYMODEGUID,  PAYAMOUNT,  TRANSACTIONNUMBER,  ADDITIONALPARAM1,  ADDITIONALPARAM2,  ADDITIONALPARAM3,  BILLPAYDETAIL_STATUS);

        }

    }

    public void InsertCreditBillDetails(CreditBillDetails prod) {

        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE, null);
            myDataBase.delete("retail_credit_bill_details", null, null);
                ContentValues contentValues = new ContentValues();
                contentValues.put("STORE_GUID", prod.getSTORE_GUID());
                contentValues.put("CUSTOMERMOBILENO", prod.getCUSTOMERMOBILENO());
                contentValues.put("CUSTOMERGUID", prod.getCUSTOMERGUID());
                contentValues.put("BILLNO", prod.getBILLNO());
                contentValues.put("BILLDATE", prod.getBILLDATE());
                contentValues.put("ITEM_GUID", prod.getITEM_GUID());
                contentValues.put("ITEM_NAME", prod.getITEM_NAME());
                contentValues.put("MRP", prod.getMRP());
                contentValues.put("SELLINGPRICE", prod.getSELLINGPRICE());
                contentValues.put("QTY", prod.getQTY());
                contentValues.put("TOTALVALUE", prod.getTOTALVALUE());
                contentValues.put("TOTALGST", prod.getTOTALGST());
                contentValues.put("CGST", prod.getCGST());
                contentValues.put("SGST", prod.getSGST());
                contentValues.put("IGST", prod.getIGST());
                contentValues.put("DISCOUNT_PERC", prod.getDISCOUNT_PERC());
                contentValues.put("DISCOUNT_VALUE", prod.getDISCOUNT_VALUE());

                myDataBase.insert("retail_credit_bill_details", null, contentValues);
            myDataBase.close();
            Log.d("Insertion Successful", "CreditBillDetails: ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getTimeStamp() {
        Date date = new Date();
        //This method returns the time in millis
        long timeMilli = date.getTime();
        return String.valueOf(timeMilli);
    }

    private String getBillNumber() {

        int count= 0;
        try {
            SQLiteDatabase db = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);          //Opens database in writable mode.
            String query = "SELECT COUNT(BILLNO) from retail_str_sales_master";
            //long numRows = DatabaseUtils.queryNumEntries(db, "retail_str_sales_master");
            Cursor cursor = db.rawQuery(query,null);
            if(cursor.moveToFirst()){

                count= cursor.getInt(0);
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(count+1);
    }

    private String getFromProductMaster( String itemGuid, String column) {

        String result = null;
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            result = "";
            String selectQuery = "SELECT "+column+" FROM retail_store_prod_com where ITEM_GUID ='"+itemGuid+"'";
            Cursor cursor = mydb.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {

                    result = cursor.getString(0);
            }
            cursor.close();
            Log.d("DataRetrieved", "getFromProductMaster: "+column+":"+result);
            mydb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    private String getFromStockMaster( String stockid,String column) {
        String result= null;
        try {
            SQLiteDatabase  mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            result = "";
            String selectQuery = "SELECT "+column+" FROM retail_str_stock_master where STOCK_ID ="+stockid;
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {

                    result= cursor.getString(0);
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getFromStockMaster: "+column+": "+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public  String getNetValue( String cgst, String sgst,String stock_id){
        double total = 0; // Your default if none is found
        try {
            SQLiteDatabase  mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            double gstD = Double.parseDouble(cgst)+Double.parseDouble(sgst);
            String query = "select S_PRICE, count from cart WHERE STOCK_ID = "+stock_id;
            Cursor result = mydb.rawQuery( query, null );

            Log.d("GSTReceived", "getNetValue: "+gstD);

            if(result==null)
                return "0";

            total = 0.0;
            for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
                double itemprice = Double.parseDouble(result.getString(0));
                Log.d("PriceReceived", "getNetValue: "+itemprice);
                 itemprice = itemprice-(gstD);
                Log.d("NetPriceOfAnItem", "getNetValue: "+itemprice);
                int itemcount= Integer.parseInt(result.getString(1));
                total += itemprice*itemcount;
                Log.d("NetPriceOfAllCount", "getNetValue: "+total);
            }
            result.close();
            mydb.close();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return String.valueOf(total);
    }

    public    String getSGST(String stockID,String Sgst){
        Log.d("SGST % Received", "getSGST: "+Sgst);
        double total = 0; // Your default if none is found
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String query = "select SGST,S_PRICE, count from cart WHERE STOCK_ID = "+stockID;
            Cursor result = mydb.rawQuery( query, null );

            if(result==null)
                return "0";

            total = 0.0;
            for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {

                double itemSgst = Double.parseDouble(Sgst);
                double itemPrice = Double.parseDouble(result.getString(1));
                double sgstForone= ((itemSgst*itemPrice)/100);
                int itemcount= Integer.parseInt(result.getString(2));
                 total = sgstForone*itemcount;

            }
            result.close();
            mydb.close();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return String.valueOf(total);
    }

    public  String getCGST( String stockID, String cgst){

        Log.d("CGST % Received", "getSGST: "+cgst);
        double total = 0; // Your default if none is found
        try {
            SQLiteDatabase  mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String query = "select CGST,S_PRICE, count from cart WHERE STOCK_ID = "+stockID;
            Cursor result = mydb.rawQuery( query, null );
            if(result==null)
                return "0";

            total = 0.0;
            for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {

                double itemCgst = Double.parseDouble(cgst);
                double itemPrice = Double.parseDouble(result.getString(1));
                double sgstForone= ((itemCgst*itemPrice)/100);
                int itemcount= Integer.parseInt(result.getString(2));
                total = sgstForone*itemcount;

            }
            result.close();
            mydb.close();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return String.valueOf(total);

    }

    private String getTOTALVALUE(String stockID){
        double total = 0; // Your default if none is found
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String query = "select S_PRICE, count from cart WHERE STOCK_ID = "+stockID;
            Cursor result = mydb.rawQuery( query, null );

            if(result==null)
                return "0";

            total = 0.0;
            for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
                double itemprice = Double.parseDouble(result.getString(0));
                int itemcount= Integer.parseInt(result.getString(1));
                total += itemprice*itemcount;
            }
            result.close();
            mydb.close();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return String.valueOf(total);

    }

    private String getSaleDateAndTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
       return formatter.format(date);
    }

    private String getSaleDate(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
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
        String result= null;
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            result = "";
            String selectQuery = "SELECT "+column+" FROM retail_cust where CUSTOMERGUID ='"+custId+"'";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {

                result= cursor.getString(0);
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getFromCustomerMaster: "+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }
    private String getFromMasterShift(String storeGuid, String column){
        String result= null;
        try {
            SQLiteDatabase  mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            result = "";
            String selectQuery = "SELECT "+column+" FROM master_shift where STORE_GUID ='"+storeGuid+"'";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {

                result= cursor.getString(0);
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getFromMasterShift: "+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    private String getFromRetailStore( String column){
        String result= null;
        try {
            SQLiteDatabase  mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            result = "";
            String selectQuery = "SELECT "+column+" FROM retail_store ";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {

                result= cursor.getString(0);
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getFromRetailStore: "+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    private String getFromTerminalConfig(String storeId, String column){
        String result= null;
        try {
            SQLiteDatabase  mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            result = "";
            String selectQuery = "SELECT "+column+" FROM terminal_configuration where STORE_GUID ='"+storeId+"'";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {

                result= cursor.getString(0);
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getFromCustomerMaster: "+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }
    private String getFromGroupUserMaster(String column){
        String result= null;
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String username;
            SharedPreferences sh = context.getSharedPreferences("com.retailstreet.mobilepos", MODE_PRIVATE);
            username = sh.getString("username", "");

            result = "";
            String selectQuery = "SELECT "+column+" FROM group_user_master where USER_NAME ='"+username+"'";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {

                result= cursor.getString(0);
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getFromUserMaster: "+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    public String getDiscountValue(String stockID){
        double total = 0; // Your default if none is found
        try {
            SQLiteDatabase  mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String query = "select SALESDISCOUNTBYAMOUNT, count from cart WHERE STOCK_ID = '"+stockID+"'";
            Cursor result = mydb.rawQuery( query, null );

            if(result==null)
                return "0";

            total = 0.0;
            for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
                double discount = Double.parseDouble(result.getString(0));
                int itemcount= Integer.parseInt(result.getString(1));
                total += discount*itemcount;
            }
            result.close();
            mydb.close();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
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
            cursor.close();
            mydb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setNewQuantity(String StockId) {
        try {
            String newQty = "";
            SQLiteDatabase mydb = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            Cursor cursor = mydb.rawQuery("SELECT count, QTY FROM cart where STOCK_ID ='" + StockId + "'", null);
            if (cursor.moveToFirst()) {
                String cnt = cursor.getString(0);
                String qty = cursor.getString(1);
                newQty = String.valueOf(Double.parseDouble(qty) - Double.parseDouble(cnt));

            }
            cursor.close();
            mydb.close();
            UpdateQuantity(newQty, StockId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void UpdateQuantity(String quantity, String stockID){
        try {

            String query = "Update retail_str_stock_master Set QTY = '"+quantity+"', ISSYNCED = '0' where STOCK_ID = '"+stockID+"'";
            SQLiteDatabase   db  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            db.execSQL(query);
            db.close();
            Log.d("UpdateStockQty", "UpdateQuantity: Successful"+quantity);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    public void GetSetFromPrefs()
    {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        smsSetFromPrefs = sharedPref.getStringSet("smsSync", new HashSet<String>() );
    }

    @SuppressLint("ApplySharedPref")
    public void SaveSetsInPrefs()
    {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putStringSet("smsSync", smsSetFromPrefs);
        editor.commit();
    }

    public void updateCreditCust(String newLimit, String custGuid){
        try {

            String query = "Update retail_cust Set CREDITLIMIT = '"+newLimit+"'  where CUSTOMERGUID = '"+custGuid+"'";
            SQLiteDatabase db  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            db.execSQL(query);
            db.close();
            Log.d("UpdateStockQty", "UpdateQuantity: Successful"+custGuid);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateRetunMaster(String redeemNum, String billNum){
        try {

            String query = "Update customerReturnMaster Set REPLACEMENTBILLNO = '"+billNum+"'  where CREDITNOTENUMBER = '"+redeemNum+"'";
            SQLiteDatabase db  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            db.execSQL(query);
            db.close();
            Log.d("UpdateStockQty", "UpdateQuantity: Successful"+redeemNum);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private String  getCreditDetails(String custId){
        String creditLimit="0.00";
        SQLiteDatabase mydb = context.openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE,null);
        Cursor cursor = mydb.rawQuery("select CREDITLIMIT from retail_cust where CUSTOMERGUID = '"+custId+"'",null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                creditLimit = cursor.getString(0);

                Log.d("CreditLimitRetrieved", "getCreditDetails: "+creditLimit);

                cursor.moveToNext();
            }
        }
        cursor.close();
        mydb.close();

        return creditLimit;
    }
}
