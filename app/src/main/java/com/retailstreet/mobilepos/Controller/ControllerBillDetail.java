package com.retailstreet.mobilepos.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.CpuUsageInfo;
import android.util.Log;

import com.retailstreet.mobilepos.Model.BillDetail;
import com.retailstreet.mobilepos.View.ApplicationContextProvider;
import com.retailstreet.mobilepos.View.LoadingDialog;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ControllerBillDetail {

    String dbname = "MasterDB";
    private Context context;

    public ControllerBillDetail(){
        
        context = ApplicationContextProvider.getContext();
    }

    public void createBillDetailData(String BILLDETAILID, String BILLMASTERID, String BILLNO, String CATEGORY_GUID, String SUBCAT_GUID, String ITEM_GUID, String ITEM_CODE, String QTY, String UOM_GUID, String BATCHNO, String COST_PRICE, String NETVALUE, String DISCOUNT_PERC, String DISCOUNT_VALUE, String TOTALVALUE, String MRP, String BILLDETAILSTATUS, String HSN, String CGSTPERCENTAGE, String SGSTPERCENTAGE, String IGSTPERCENTAGE, String ADDITIONALPERCENTAGE, String CGST, String SGST, String IGST, String ADDITIONALCESS, String TOTALGST){
        BillDetail billDetail = new BillDetail( BILLDETAILID,  BILLMASTERID,  BILLNO,  CATEGORY_GUID,  SUBCAT_GUID,  ITEM_GUID,  ITEM_CODE,  QTY,  UOM_GUID,  BATCHNO,  COST_PRICE,  NETVALUE,  DISCOUNT_PERC,  DISCOUNT_VALUE,  TOTALVALUE,  MRP,  BILLDETAILSTATUS,  HSN,  CGSTPERCENTAGE,  SGSTPERCENTAGE,  IGSTPERCENTAGE,  ADDITIONALPERCENTAGE,  CGST,  SGST,  IGST,  ADDITIONALCESS,  TOTALGST );
        insertBillDetails(billDetail);
    }

    public void insertBillDetails(BillDetail billDetail) {
        if (billDetail == null) {
            return;
        }
        try {
            SQLiteDatabase  myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
            ContentValues contentValues = new ContentValues();
            contentValues.put("BILLDETAILID", billDetail.getBILLDETAILID());
            contentValues.put("BILLMASTERID", billDetail.getBILLMASTERID());
            contentValues.put("BILLNO", billDetail.getBILLNO());
            contentValues.put("CATEGORY_GUID", billDetail.getCATEGORY_GUID());
            contentValues.put("SUBCAT_GUID", billDetail.getSUBCAT_GUID());
            contentValues.put("ITEM_GUID", billDetail.getITEM_GUID());
            contentValues.put("ITEM_CODE", billDetail.getITEM_CODE());
            contentValues.put("QTY", billDetail.getQTY());
            contentValues.put("UOM_GUID", billDetail.getUOM_GUID());
            contentValues.put("BATCHNO", billDetail.getBATCHNO());
            contentValues.put("COST_PRICE", billDetail.getCOST_PRICE());
            contentValues.put("NETVALUE", billDetail.getNETVALUE());
            contentValues.put("DISCOUNT_PERC", billDetail.getDISCOUNT_PERC());
            contentValues.put("DISCOUNT_VALUE", billDetail.getDISCOUNT_VALUE());
            contentValues.put("TOTALVALUE", billDetail.getTOTALVALUE());
            contentValues.put("MRP", billDetail.getMRP());
            contentValues.put("BILLDETAILSTATUS", billDetail.getBILLDETAILSTATUS());
            contentValues.put("HSN", billDetail.getHSN());
            contentValues.put("CGSTPERCENTAGE", billDetail.getCGSTPERCENTAGE());
            contentValues.put("SGSTPERCENTAGE", billDetail.getSGSTPERCENTAGE());
            contentValues.put("IGSTPERCENTAGE", billDetail.getIGSTPERCENTAGE());
            contentValues.put("ADDITIONALPERCENTAGE", billDetail.getADDITIONALPERCENTAGE());
            contentValues.put("CGST", billDetail.getCGST());
            contentValues.put("SGST", billDetail.getSGST());
            contentValues.put("IGST", billDetail.getIGST());
            contentValues.put("ADDITIONALCESS", billDetail.getADDITIONALCESS());
            contentValues.put("TOTALGST", billDetail.getTOTALGST());
            myDataBase.insert("retail_str_sales_detail", null, contentValues);
            myDataBase.close(); // Closing database connection

            Log.d("Bill Details", "insertBillDetails: Successful! ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }




