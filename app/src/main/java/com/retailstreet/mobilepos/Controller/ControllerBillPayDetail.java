package com.retailstreet.mobilepos.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.retailstreet.mobilepos.Model.BillPayDetail;
import com.retailstreet.mobilepos.View.ApplicationContextProvider;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class ControllerBillPayDetail {

    String dbname = "MasterDB";
    SQLiteDatabase myDataBase;
    private Context context;

    public ControllerBillPayDetail(){

        context = ApplicationContextProvider.getContext();
    }

    public void createBillPayDetail(String BILLPAYDETAILID, String BILLMASTERID, String MASTERPAYMODEGUID, String PAYAMOUNT, String TRANSACTIONNUMBER, String ADDITIONALPARAM1, String ADDITIONALPARAM2, String ADDITIONALPARAM3, String BILLPAYDETAIL_STATUS){
        BillPayDetail billPayDetail = new BillPayDetail(  BILLPAYDETAILID,  BILLMASTERID,  MASTERPAYMODEGUID,  PAYAMOUNT,  TRANSACTIONNUMBER,  ADDITIONALPARAM1,  ADDITIONALPARAM2,  ADDITIONALPARAM3,  BILLPAYDETAIL_STATUS);
        insertBillPayDetails(billPayDetail);
    }

    public void insertBillPayDetails(BillPayDetail billPayDetail) {
        if (billPayDetail == null) {
            return;
        }
        myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
        ContentValues contentValues = new ContentValues();
        contentValues.put("BILLPAYDETAILID", billPayDetail.getBILLPAYDETAILID());
        contentValues.put("BILLMASTERID", billPayDetail.getBILLMASTERID());
        contentValues.put("MASTERPAYMODEGUID", billPayDetail.getMASTERPAYMODEGUID());
        contentValues.put("PAYAMOUNT", billPayDetail.getPAYAMOUNT());
        contentValues.put("TRANSACTIONNUMBER", billPayDetail.getTRANSACTIONNUMBER());
        contentValues.put("ADDITIONALPARAM1", billPayDetail.getADDITIONALPARAM1());
        contentValues.put("ADDITIONALPARAM2", billPayDetail.getADDITIONALPARAM2());
        contentValues.put("ADDITIONALPARAM3", billPayDetail.getADDITIONALPARAM3());
        contentValues.put("BILLPAYDETAIL_STATUS", billPayDetail.getBILLPAYDETAIL_STATUS());

        myDataBase.insert("billpaydetail ", null, contentValues);
        myDataBase.close(); // Closing database connection

        Log.d("Bill Pay Details", "insertBillDetails: Successful! ");
    }

    public ArrayList<BillPayDetail> getBillPayDetails(String billMasterID){

        ArrayList<BillPayDetail> billPayDetailArrayList = new ArrayList<>();
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, MODE_PRIVATE, null);
            String query = "SELECT  * FROM billpaydetail where BILLMASTERID = '"+billMasterID+"'";
            Cursor cursor = myDataBase.rawQuery(query, null);
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                BillPayDetail  billPayDetail = new BillPayDetail();
                billPayDetail.setBILLPAYDETAILID(cursor.getString(0));
                billPayDetail.setBILLMASTERID(cursor.getString(1));
                billPayDetail.setMASTERPAYMODEGUID(cursor.getString(2));
                billPayDetail.setPAYAMOUNT(cursor.getString(3));
                billPayDetail.setTRANSACTIONNUMBER(cursor.getString(4));
                billPayDetail.setADDITIONALPARAM1(cursor.getString(5));
                billPayDetail.setADDITIONALPARAM2(cursor.getString(6));
                billPayDetail.setADDITIONALPARAM3(cursor.getString(7));
                billPayDetail.setBILLPAYDETAIL_STATUS(cursor.getString(8));
                billPayDetailArrayList.add(billPayDetail);
            }
            cursor.close();
            myDataBase.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return billPayDetailArrayList;
    }
}

