package com.retailstreet.mobilepos.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.retailstreet.mobilepos.View.ApplicationContextProvider;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Mayank Choudhary on 31-05-2021.
 * mayankchoudhary00@gmail.com
 */

public class ControllerSummery {

    Context context;
    String userGuid="";
    Date date ;

    public ControllerSummery() {

        context= ApplicationContextProvider.getContext();
        userGuid =  getFromGroupUserMaster("USER_GUID");
        long millis = System.currentTimeMillis();
        date = new Date(millis);
        Log.d("DatePrinting", "getOpeningBalance: "+date.toString());

    }

    public String getOpeningBalance(String startDate, String endDate){
        double result= 0.00;
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String selectQuery;

            if(startDate.trim().isEmpty() || endDate.trim().isEmpty()) {
                 selectQuery = "SELECT CASH_OPENED FROM shift_trans Where SHIFT_DATE LIKE '" + date + "%' AND USER_GUID = '"+userGuid+"'";
            }else {
                selectQuery = "SELECT CASH_OPENED FROM shift_trans Where SHIFT_DATE BETWEEN '" + startDate + "' AND '"+ endDate + "' AND USER_GUID = '"+userGuid+"'";
            }
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    result += Double.parseDouble(cursor.getString(0));
                    cursor.moveToNext();
                }

            }
            cursor.close();
            mydb.close();
            Log.d("DataCalculated", "getOpeningBalance: "+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  getFormattedValue(result);

    }

    public String getTotalBillCount(String startDate, String endDate){
        double result= 0.00;
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String selectQuery;

            if(startDate.trim().isEmpty() || endDate.trim().isEmpty()) {
                selectQuery = "SELECT * FROM retail_str_sales_master Where SALEDATE LIKE '" + date + "%'";
            }else {
                selectQuery = "SELECT * FROM retail_str_sales_master Where SALEDATE BETWEEN '" + startDate + "' AND '"+ endDate + "'";
            }
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                    result = cursor.getCount();
            }
            cursor.close();
            mydb.close();
            Log.d("DataCalculated", "getOpeningBalance: "+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  getFormattedValue(result);

    }

    public String getTotalBillCancelled(String startDate, String endDate){
        double result= 0.00;

        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String selectQuery;

            if(startDate.trim().isEmpty() || endDate.trim().isEmpty()) {
                selectQuery = "SELECT * FROM customerReturnMaster Where RETURN_DATE LIKE '" + date + "%'";
            }else {
                selectQuery = "SELECT * FROM customerReturnMaster Where RETURN_DATE BETWEEN '" + startDate + "' AND '"+ endDate + "'";
            }
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                result = cursor.getCount();
            }
            cursor.close();
            mydb.close();
            Log.d("DataCalculated", "getOpeningBalance: "+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  getFormattedValue(result);

    }


    private String getFormattedValue(Double value){

        return String.format(Locale.getDefault(),"%.2f",value);
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


    public String getSalesReturnCash(String startDate, String endDate){
        double result= 0.00;
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String selectQuery;

            if(startDate.trim().isEmpty() || endDate.trim().isEmpty()) {
                selectQuery = "SELECT TOTAL(AMOUNTREFUNDED)   FROM customerReturnMaster Where RETURN_DATE LIKE '" + date + "%' AND CREDITNOTENUMBER = ''";
            }else {
                selectQuery = "SELECT TOTAL(AMOUNTREFUNDED)  FROM customerReturnMaster Where RETURN_DATE BETWEEN '" + startDate + "' AND '"+ endDate + "' AND CREDITNOTENUMBER = ''";
            }
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                    result = Double.parseDouble(cursor.getString(0));
                }
            cursor.close();
            mydb.close();
            Log.d("DataCalculated", "getOpeningBalance: "+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  getFormattedValue(result);

    }

    public String getSalesReturnCreditNote(String startDate, String endDate){
        double result= 0.00;
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String selectQuery;

            if(startDate.trim().isEmpty() || endDate.trim().isEmpty()) {
                selectQuery = "SELECT TOTAL(AMOUNTREFUNDED)   FROM customerReturnMaster Where RETURN_DATE LIKE '" + date + "%' AND CREDITNOTENUMBER NOT LIKE ''";
            }else {
                selectQuery = "SELECT TOTAL(AMOUNTREFUNDED)  FROM customerReturnMaster Where RETURN_DATE BETWEEN '" + startDate + "' AND '"+ endDate + "' AND CREDITNOTENUMBER NOT LIKE ''";
            }
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                result = Double.parseDouble(cursor.getString(0));
            }
            cursor.close();
            mydb.close();
            Log.d("DataCalculated", "getOpeningBalance: "+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  getFormattedValue(result);

    }

    public String getTotalCreditPayment(String startDate, String endDate){
        double result= 0.00;
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String selectQuery;

            if(startDate.trim().isEmpty() || endDate.trim().isEmpty()) {
                selectQuery = "SELECT TOTAL(CREDITAMOUNT)   FROM customerLedger Where ACTIONDATE LIKE '" + date + "%' ";
            }else {
                selectQuery = "SELECT TOTAL(CREDITAMOUNT)  FROM customerLedger Where ACTIONDATE BETWEEN '" + startDate + "' AND '"+ endDate + "'";
            }
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                result = Double.parseDouble(cursor.getString(0));
            }
            cursor.close();
            mydb.close();
            Log.d("DataCalculated", "getOpeningBalance: "+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  getFormattedValue(result);

    }

    public String getCreditPaymentCash(String startDate, String endDate){
        double result= 0.00;
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String selectQuery;

            if(startDate.trim().isEmpty() || endDate.trim().isEmpty()) {
                selectQuery = "SELECT TOTAL(CREDITAMOUNT)   FROM customerLedger Where ACTIONDATE LIKE '" + date + "%' AND MASTERPAYMODEGUID = 'E123BBBE-A000-4617-AD49-9B5AF2275F43'";
            }else {
                selectQuery = "SELECT TOTAL(CREDITAMOUNT)  FROM customerLedger Where ACTIONDATE BETWEEN '" + startDate + "' AND '"+ endDate + "' AND MASTERPAYMODEGUID = 'E123BBBE-A000-4617-AD49-9B5AF2275F43'";
            }
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                result = Double.parseDouble(cursor.getString(0));
            }
            cursor.close();
            mydb.close();
            Log.d("DataCalculated", "getOpeningBalance: "+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  getFormattedValue(result);

    }

    public String getTotalDailyExpenses(String startDate, String endDate, String payMode, String payType){
        double result= 0.00;
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String selectQuery;

            if(startDate.trim().isEmpty() || endDate.trim().isEmpty()) {
                selectQuery = "SELECT TOTAL(a.INVOICEAMOUNT) FROM VendorPayMaster a LEFT JOIN VendorPayDetail b ON a.VENDOR_PAYGUID = b.VENDOR_PAYGUID where a.CREATEDON LIKE '" + date + "%' AND a.TYPEOFINVOICE = '"+ payType+"' and b.PAYMODE='"+payMode+"'";
            }else {
                selectQuery = "SELECT TOTAL(a.INVOICEAMOUNT) FROM VendorPayMaster a LEFT JOIN VendorPayDetail b ON a.VENDOR_PAYGUID = b.VENDOR_PAYGUID Where strftime('%Y-%m-%d', CREATEDON) BETWEEN '" + startDate + "' AND '"+ endDate + "' AND a.TYPEOFINVOICE = '"+ payType+"' and b.PAYMODE='"+payMode+"'";
            }
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                result = Double.parseDouble(cursor.getString(0));
            }
            cursor.close();
            mydb.close();
            Log.d("DataCalculated", "getOpeningBalance: "+result);
        } catch (Exception e) {

            e.printStackTrace();

        }
        return  getFormattedValue(result);

    }



    public double getSumOfSaleByType(String startDate, String endDate, String masterPayId){
        double result= 0.00;
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String selectQuery;

            if(startDate.trim().isEmpty() || endDate.trim().isEmpty()) {
                selectQuery = "SELECT TOTAL(a.PAYAMOUNT) FROM billpaydetail a LEFT JOIN retail_str_sales_master b ON a.BILLMASTERID=b.BILLMASTERID where b.SALEDATE LIKE '" + date + "%' and a.MASTERPAYMODEGUID='" + masterPayId + "'";
            }else {
                selectQuery = "SELECT TOTAL(a.PAYAMOUNT) FROM billpaydetail a LEFT JOIN retail_str_sales_master b ON a.BILLMASTERID=b.BILLMASTERID Where strftime('%Y-%m-%d', SALEDATE) BETWEEN '" + startDate + "' AND '"+ endDate + "' AND a.MASTERPAYMODEGUID='" + masterPayId + "'";
            }
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                Log.d("Printing Value TOTAL", "getSumOfSaleByType: "+cursor.getString(0));
                result = Double.parseDouble(cursor.getString(0));
            }
            cursor.close();
            mydb.close();
            Log.d("DataCalculated", "getOpeningBalance: "+result);
        } catch (Exception e) {

            e.printStackTrace();

        }
        return  result;

    }

    public double getDailyDebitpayment(String startDate, String endDate){
        double result= 0.00;
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String selectQuery;

            if(startDate.trim().isEmpty() || endDate.trim().isEmpty()) {
                selectQuery = "SELECT TOTAL(DEBITAMOUNT) FROM customerLedger where strftime('%Y-%m-%d',ACTIONDATE) ='" + date +  "'";
            }else {
                selectQuery = "SELECT TOTAL(DEBITAMOUNT)  FROM customerLedger  Where strftime('%Y-%m-%d', ACTIONDATE) BETWEEN '" + startDate + "' AND '"+ endDate + "'";
            }
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                result = Double.parseDouble(cursor.getString(0));
            }
            cursor.close();
            mydb.close();
            Log.d("DataCalculated", "getOpeningBalance: "+result);
        } catch (Exception e) {

            e.printStackTrace();

        }
        return  result;

    }


    public double getDailyAdvanceadjust(String startDate, String endDate){
        double result= 0.00;
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String selectQuery;

            if(startDate.trim().isEmpty() || endDate.trim().isEmpty()) {
                selectQuery = "SELECT TOTAL(ADDITIONALPARAM6)  FROM customerLedger  where strftime('%Y-%m-%d',ACTIONDATE) ='" + date +  "'";
            }else {
                selectQuery = "SELECT TOTAL(ADDITIONALPARAM6)  FROM customerLedger  Where strftime('%Y-%m-%d', ACTIONDATE) BETWEEN '" + startDate + "' AND '"+ endDate + "'";
            }
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                result = Double.parseDouble(cursor.getString(0));
            }
            cursor.close();
            mydb.close();
            Log.d("DataCalculated", "getOpeningBalance: "+result);
        } catch (Exception e) {

            e.printStackTrace();

        }
        return  result;

    }

       public double getDailyAdvancepaymentpaymentReceived(String startDate, String endDate){
        double result= 0.00;
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String selectQuery;

            if(startDate.trim().isEmpty() || endDate.trim().isEmpty()) {
                selectQuery = "SELECT TOTAL(ADDITIONALPARAM2)  FROM customerLedger where strftime('%Y-%m-%d',ACTIONDATE) ='" + date +  "'";
            }else {
                selectQuery = "SELECT TOTAL(ADDITIONALPARAM2)  FROM customerLedger Where strftime('%Y-%m-%d',ACTIONDATE) BETWEEN '" + startDate + "' AND '"+ endDate + "'";
            }
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                result = Double.parseDouble(cursor.getString(0));
            }
            cursor.close();
            mydb.close();
            Log.d("DataCalculated", "getOpeningBalance: "+result);
        } catch (Exception e) {

            e.printStackTrace();

        }
        return  result;

    }





    public String getTotalSaleOfDay(String startDate, String endDate){

        double totalSum = 0.00;
        double cash = getSumOfSaleByType(startDate,endDate, "E123BBBE-A000-4617-AD49-9B5AF2275F43");
        double card = getSumOfSaleByType(startDate,endDate,  "ADB4763E-1BD4-4589-A460-5F369DB42C31");
        double creditnote =getSumOfSaleByType(startDate,endDate, "03857C6B-E4C0-4E20-9318-48499911929D");
        double paytm = getSumOfSaleByType(startDate,endDate, "6114C394-A511-48A1-B811-EC6FD6A8C7D9");
        double googlepay = getSumOfSaleByType(startDate,endDate, "7EF5165C-7D97-4203-A9FF-67AB1B51E6A5");
        double whatsapppay = getSumOfSaleByType(startDate,endDate, "8C97DDDE-B6BF-4C12-BE88-CF78801689B3");
        double amazon = getSumOfSaleByType(startDate,endDate, "5A15705D-89C9-471F-A443-71F8CD749BF5");
        double bhimpay = getSumOfSaleByType(startDate,endDate, "D8031C2F-1F00-4191-8294-37E4E787D4FA");
        double cheque = getSumOfSaleByType(startDate,endDate, "DCF64C1F-2706-4063-AAD1-83430C0090B1");
        double credit = getDailyDebitpayment( startDate,  endDate);
        double advance = getDailyAdvanceadjust( startDate,  endDate);

        totalSum = cash+card+creditnote+paytm+googlepay+whatsapppay+amazon+bhimpay+cheque+credit+advance;
        return  getFormattedValue(totalSum);
    }

    public String getTotalOnlinePayment(String startDate, String endDate){

        double totalSum = 0.00;
        double paytm = getSumOfSaleByType(startDate,endDate, "6114C394-A511-48A1-B811-EC6FD6A8C7D9");
        double googlepay = getSumOfSaleByType(startDate,endDate, "7EF5165C-7D97-4203-A9FF-67AB1B51E6A5");
        double whatsapppay = getSumOfSaleByType(startDate,endDate, "8C97DDDE-B6BF-4C12-BE88-CF78801689B3");
        double amazon = getSumOfSaleByType(startDate,endDate, "5A15705D-89C9-471F-A443-71F8CD749BF5");
        double bhimpay = getSumOfSaleByType(startDate,endDate, "D8031C2F-1F00-4191-8294-37E4E787D4FA");

        totalSum = paytm+googlepay+whatsapppay+amazon+bhimpay;
        return  getFormattedValue(totalSum);
    }



    public String getAllCreditCustomer() {
        String result= null;
        try {
            SQLiteDatabase  mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            result = "";
            String selectQuery = "select TOTAL(GRANDTOTAL) from retail_credit_cust ";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {

                result= cursor.getString(0);
            }
            cursor.close();
            mydb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public String getAllvendorpayment() {
        String result= null;
        try {
            SQLiteDatabase  mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            result = "";
            String selectQuery = "select TOTAL(GRANDAMOUNT) from retail_str_grn_master ";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {

                result= cursor.getString(0);
            }
            cursor.close();
            mydb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getAllCreditCustomerAdvance() {
        String result= null;
        try {
            SQLiteDatabase  mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            result = "";
            String selectQuery = "select TOTAL(ADVANCE_AMOUNT) from retail_cust";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {

                result= cursor.getString(0);
            }
            cursor.close();
            mydb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public double getMonthlySellReport(String masterpayguid) {
        double result= 0.00;
        Calendar theEnd = Calendar.getInstance();
        Calendar theStart = (Calendar) theEnd.clone();
        theStart.add(Calendar.DAY_OF_MONTH, -30);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
        String start = dateFormat.format(theStart.getTime());
        String end = dateFormat.format(theEnd.getTime());
        try {
            SQLiteDatabase  mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String selectQuery = "SELECT TOTAL(a.PAYAMOUNT) FROM billpaydetail a LEFT JOIN retail_str_sales_master b ON a.BILLMASTERID=b.BILLMASTERID where b.SALEDATE between '" + start + "' AND '" + end + "' and a.MASTERPAYMODEGUID='" + masterpayguid + "'";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {

                result= Double.parseDouble(cursor.getString(0));
            }
            cursor.close();
            mydb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public double getMonthlyDebitReport(String days) {
        double result= 0.00;
        try {
            SQLiteDatabase  mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String selectQuery = "SELECT TOTAL(DEBITAMOUNT)  FROM customerLedger where strftime('%Y-%m-%d',ACTIONDATE) > date('now','"+days+" days') ";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {

                result= Double.parseDouble(cursor.getString(0));
            }
            cursor.close();
            mydb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public double getMonthlyAdvanceAdjustReport(String days) {
        double result= 0.00;
        try {
            SQLiteDatabase  mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String selectQuery = "SELECT  TOTAL(ADDITIONALPARAM6)  FROM customerLedger  where strftime('%Y-%m-%d',ACTIONDATE) > date('now','"+days+" days') ";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {

                result= Double.parseDouble(cursor.getString(0));
            }
            cursor.close();
            mydb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }



    public String getMonthlyTotal(){
        double total=0.00;
        double mcash = getMonthlySellReport("E123BBBE-A000-4617-AD49-9B5AF2275F43");
        double mpaytm = getMonthlySellReport("6114C394-A511-48A1-B811-EC6FD6A8C7D9");
        double mgooglepay = getMonthlySellReport("7EF5165C-7D97-4203-A9FF-67AB1B51E6A5");
        double mwhatsapppay = getMonthlySellReport("8C97DDDE-B6BF-4C12-BE88-CF78801689B3");
        double mamazon = getMonthlySellReport("5A15705D-89C9-471F-A443-71F8CD749BF5");
        double mbhimpay = getMonthlySellReport("D8031C2F-1F00-4191-8294-37E4E787D4FA");
        double mcard = getMonthlySellReport("ADB4763E-1BD4-4589-A460-5F369DB42C31");
        double mcreditnote = getMonthlySellReport("03857C6B-E4C0-4E20-9318-48499911929D");
        double mcheque = getMonthlySellReport("DCF64C1F-2706-4063-AAD1-83430C0090B1");
        double mcreditsale  = getMonthlyDebitReport("-30");
        double advanceadj = getMonthlyAdvanceAdjustReport("-30");
        total = mcash + mcard + mpaytm + mbhimpay + mgooglepay + mamazon + mwhatsapppay+mcreditnote+mcreditsale+mcheque+advanceadj;
        return getFormattedValue(total);
    }
}

