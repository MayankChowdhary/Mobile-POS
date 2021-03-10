package com.retailstreet.mobilepos.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.retailstreet.mobilepos.Model.BillDetail;
import com.retailstreet.mobilepos.Model.BillMaster;
import com.retailstreet.mobilepos.View.ApplicationContextProvider;
import com.retailstreet.mobilepos.View.LoadingDialog;

import java.util.List;

public class ControllerBillMaster {

    String dbname = "MasterDB";
    SQLiteDatabase myDataBase;
    private Context context;

    public ControllerBillMaster() {

        context = ApplicationContextProvider.getContext();
    }

    public void createBillMasterData(String BILLMASTERID, String BILLNO, String SALEDATETIME, String SALEDATE, String SALETIME, String MASTERCUSTOMERGUID, String MASTERSTOREGUID, String MASTERTERMINALGUID, String MASTERSHIFTGUID, String USER_GUID, String CUST_MOBILENO, String NETVALUE, String TAXVALUE, String TOTAL_AMOUNT, String DELIVERY_TYPE_GUID, String BILL_PRINT, String TOTAL_BILL_AMOUNT, String NO_OF_ITEMS, String BILLSTATUS, String ISSYNCED, String RECEIVED_CASH, String BALANCE_CASH, String ROUND_OFF, String NETDISCOUNT) {
        BillMaster billMaster = new BillMaster(BILLMASTERID, BILLNO, SALEDATETIME, SALEDATE, SALETIME, MASTERCUSTOMERGUID, MASTERSTOREGUID, MASTERTERMINALGUID, MASTERSHIFTGUID, USER_GUID, CUST_MOBILENO, NETVALUE, TAXVALUE, TOTAL_AMOUNT, DELIVERY_TYPE_GUID, BILL_PRINT, TOTAL_BILL_AMOUNT, NO_OF_ITEMS, BILLSTATUS, ISSYNCED, RECEIVED_CASH, BALANCE_CASH, ROUND_OFF, NETDISCOUNT);
        InsertBillMaster(billMaster);
    }

    public void InsertBillMaster(BillMaster billMaster) {
        if (billMaster == null) {
            return;
        }
        myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
            ContentValues contentValues = new ContentValues();
            contentValues.put("BILLMASTERID", billMaster.getBILLMASTERID());
            contentValues.put("BILLNO", billMaster.getBILLNO());
            contentValues.put("SALEDATETIME", billMaster.getSALEDATETIME());
            contentValues.put("SALEDATE", billMaster.getSALEDATE());
            contentValues.put("SALETIME", billMaster.getSALETIME());
            contentValues.put("MASTERCUSTOMERGUID", billMaster.getMASTERCUSTOMERGUID());
            contentValues.put("MASTERSTOREGUID", billMaster.getMASTERSTOREGUID());
            contentValues.put("MASTERTERMINALGUID", billMaster.getMASTERTERMINALGUID());
            contentValues.put("MASTERSHIFTGUID", billMaster.getMASTERSHIFTGUID());
            contentValues.put("USER_GUID", billMaster.getUSER_GUID());
            contentValues.put("CUST_MOBILENO", billMaster.getCUST_MOBILENO());
            contentValues.put("NETVALUE", billMaster.getNETVALUE());
            contentValues.put("TAXVALUE", billMaster.getTAXVALUE());
            contentValues.put("TOTAL_AMOUNT", billMaster.getTOTAL_AMOUNT());
            contentValues.put("DELIVERY_TYPE_GUID", billMaster.getDELIVERY_TYPE_GUID());
            contentValues.put("BILL_PRINT", billMaster.getBILL_PRINT());
            contentValues.put("TOTAL_BILL_AMOUNT", billMaster.getTOTAL_BILL_AMOUNT());
            contentValues.put("NO_OF_ITEMS", billMaster.getNO_OF_ITEMS());
            contentValues.put("BILLSTATUS", billMaster.getBILLSTATUS());
            contentValues.put("ISSYNCED", billMaster.getISSYNCED());
            contentValues.put("RECEIVED_CASH", billMaster.getRECEIVED_CASH());
            contentValues.put("BALANCE_CASH", billMaster.getBALANCE_CASH());
            contentValues.put("ROUND_OFF", billMaster.getROUND_OFF());
            contentValues.put("NETDISCOUNT", billMaster.getNETDISCOUNT());
            myDataBase.insert("retail_str_sales_master", null, contentValues);
             myDataBase.close(); // Closing database connection
        }
    }





