package com.retailstreet.mobilepos.Controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.retailstreet.mobilepos.Model.BillDetail;
import com.retailstreet.mobilepos.Model.BillMasterUpload;
import com.retailstreet.mobilepos.Model.BillPayInvoice;
import com.retailstreet.mobilepos.View.ApplicationContextProvider;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class InvoiceGenerator {
    Context context;

    public InvoiceGenerator() {
        this.context = ApplicationContextProvider.getContext();
    }


    public ArrayList<BillMasterUpload> getBillMasterSyncdataforSMS(String BILLNO) {
        ArrayList<BillMasterUpload> productlist = new ArrayList<BillMasterUpload>();
        try {
            SQLiteDatabase db = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            Cursor productcursor = db.rawQuery(" select BILLMASTERID,MASTERTERMINALGUID,MASTERSHIFTGUID," +
                    "BILLNO,SALEDATE,SALETIME,USER_GUID,CUST_MOBILENO,NETVALUE," +
                    " TAXVALUE,TOTAL_AMOUNT,DELIVERY_TYPE_GUID,BILL_PRINT,TOTAL_BILL_AMOUNT,NO_OF_ITEMS," +
                    "MASTERCUSTOMERGUID,RECEIVED_CASH,BALANCE_CASH,ROUND_OFF,NETDISCOUNT" +
                    " from retail_str_sales_master " +
                    "where BILLNO='" + BILLNO + "' ", null);
            if (productcursor.moveToFirst()) {
                do {
                    BillMasterUpload pm = new BillMasterUpload();

                    pm.setBID(productcursor.getString(productcursor.getColumnIndex("BILLMASTERID")));
                    pm.setTERMINAL_GUID(productcursor.getString(productcursor.getColumnIndex("MASTERTERMINALGUID")));
                    pm.setSHIFTID(productcursor.getString(productcursor.getColumnIndex("MASTERSHIFTGUID")));
                    pm.setBILLNO(productcursor.getString(productcursor.getColumnIndex("BILLNO")));
                    pm.setSALEDATE(productcursor.getString(productcursor.getColumnIndex("SALEDATE")));
                    pm.setSALETIME(productcursor.getString(productcursor.getColumnIndex("SALETIME")));
                    pm.setUSER_GUID(productcursor.getString(productcursor.getColumnIndex("USER_GUID")));
                    pm.setCUST_MOBILENO(productcursor.getString(productcursor.getColumnIndex("CUST_MOBILENO")));
                    pm.setNETVALUE(productcursor.getString(productcursor.getColumnIndex("NETVALUE")));
                    pm.setTAXVALUE(productcursor.getString(productcursor.getColumnIndex("TAXVALUE")));
                    pm.setTOTAL_AMOUNT(productcursor.getString(productcursor.getColumnIndex("TOTAL_AMOUNT")));
                    pm.setDEL_TYPE(productcursor.getString(productcursor.getColumnIndex("DELIVERY_TYPE_GUID")));
                    pm.setBILL_PRINT(productcursor.getString(productcursor.getColumnIndex("BILL_PRINT")));
                    pm.setTOTAL_BILL_AMOUNT(productcursor.getString(productcursor.getColumnIndex("TOTAL_BILL_AMOUNT")));
                    pm.setNO_OF_ITEMS(productcursor.getString(productcursor.getColumnIndex("NO_OF_ITEMS")));
                    pm.setMASTERCUSTOMER_GUID(productcursor.getString(productcursor.getColumnIndex("MASTERCUSTOMERGUID")));
                    pm.setRECEIVED_CASH(productcursor.getString(productcursor.getColumnIndex("RECEIVED_CASH")));
                    pm.setBALANCE_CASH(productcursor.getString(productcursor.getColumnIndex("BALANCE_CASH")));
                    pm.setROUND_OFF(productcursor.getString(productcursor.getColumnIndex("ROUND_OFF")));
                    pm.setNETDISCOUNT(productcursor.getString(productcursor.getColumnIndex("NETDISCOUNT")));
                    productlist.add(pm);
                } while (productcursor.moveToNext());
            }

            db.close();
        } catch (IndexOutOfBoundsException cur) {
            cur.printStackTrace();
        }
        return productlist;
    }

    public ArrayList<BillDetail> getBillDetailsSyncdata(String billno) {
        ArrayList<BillDetail> productlist = new ArrayList<BillDetail>();
        try {
            SQLiteDatabase db = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
                        //,SPEC_PRICE,WHOLE_SPRICE,CGST,SGST  //these 4 columns are not found
            Cursor productcursor = db.rawQuery(" select distinct BILLMASTERID,BILLNO,CATEGORY_GUID,SUBCAT_GUID,ITEM_GUID,ITEM_CODE," +
                    "QTY,UOM_GUID,BATCHNO,COST_PRICE,NETVALUE,DISCOUNT_PERC,DISCOUNT_VALUE,TOTALVALUE,MRP," +
                    "HSN,CGSTPERCENTAGE,SGSTPERCENTAGE,IGSTPERCENTAGE,ADDITIONALPERCENTAGE,CGST," +
                    "SGST,IGST,ADDITIONALCESS,TOTALGST from retail_str_sales_detail where BILLNO='" + billno + "' ", null);
            if (productcursor.moveToFirst()) {
                do {//GRNNO,CAT_GUID,GRN_DETAIL_STATUS
                    BillDetail pm = new BillDetail();//PROMO_GUID,BOGO_ITEM_GUID,BOGO_QTY,BOGO_UOM_GUID,
                    pm.setBILLMASTERID(productcursor.getString(productcursor.getColumnIndex("BILLMASTERID")));
                    pm.setBILLNO(productcursor.getString(productcursor.getColumnIndex("BILLNO")));
                    pm.setCATEGORY_GUID(productcursor.getString(productcursor.getColumnIndex("CATEGORY_GUID")));
                    pm.setSUBCAT_GUID(productcursor.getString(productcursor.getColumnIndex("SUBCAT_GUID")));
                    pm.setITEM_GUID(productcursor.getString(productcursor.getColumnIndex("ITEM_GUID")));
                    pm.setITEM_CODE(productcursor.getString(productcursor.getColumnIndex("ITEM_CODE")));
                    pm.setQTY(productcursor.getString(productcursor.getColumnIndex("QTY")));
                    pm.setUOM_GUID(productcursor.getString(productcursor.getColumnIndex("UOM_GUID")));
                    pm.setBATCHNO(productcursor.getString(productcursor.getColumnIndex("BATCHNO")));
                    pm.setCOST_PRICE(productcursor.getString(productcursor.getColumnIndex("COST_PRICE")));
                    pm.setNETVALUE(productcursor.getString(productcursor.getColumnIndex("NETVALUE")));
                    pm.setDISCOUNT_PERC(productcursor.getString(productcursor.getColumnIndex("DISCOUNT_PERC")));
                    pm.setDISCOUNT_VALUE(productcursor.getString(productcursor.getColumnIndex("DISCOUNT_VALUE")));
                    pm.setTOTALVALUE(productcursor.getString(productcursor.getColumnIndex("TOTALVALUE")));
                    pm.setMRP(productcursor.getString(productcursor.getColumnIndex("MRP")));
                    pm.setHSN(productcursor.getString(productcursor.getColumnIndex("HSN")));
                    pm.setCGSTPERCENTAGE(productcursor.getString(productcursor.getColumnIndex("CGSTPERCENTAGE")));
                    pm.setSGSTPERCENTAGE(productcursor.getString(productcursor.getColumnIndex("SGSTPERCENTAGE")));
                    pm.setIGSTPERCENTAGE(productcursor.getString(productcursor.getColumnIndex("IGSTPERCENTAGE")));
                    pm.setADDITIONALPERCENTAGE(productcursor.getString(productcursor.getColumnIndex("ADDITIONALPERCENTAGE")));//null
                    pm.setCGST(productcursor.getString(productcursor.getColumnIndex("CGST")));
                    pm.setSGST(productcursor.getString(productcursor.getColumnIndex("SGST")));
                    pm.setIGST(productcursor.getString(productcursor.getColumnIndex("IGST")));
                    pm.setADDITIONALCESS(productcursor.getString(productcursor.getColumnIndex("ADDITIONALCESS")));//null
                    pm.setTOTALGST(productcursor.getString(productcursor.getColumnIndex("TOTALGST")));

                    productlist.add(pm);
                } while (productcursor.moveToNext());
            }

            db.close();
        } catch (IndexOutOfBoundsException cur) {
            cur.printStackTrace();
        }
        return productlist;
    }

    public ArrayList<BillPayInvoice> getBillPaymentDetails(String billno) {
        ArrayList<BillPayInvoice> productlist = new ArrayList<BillPayInvoice>();
        billno = getFromSalesMaster(billno,"BILLMASTERID");
        try {
            SQLiteDatabase db = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            Cursor productcursor = db.rawQuery(" select a.BILLMASTERID,b.MASTERTERMINALGUID,b.BILLNO,a.MASTERPAYMODEGUID,a.PAYAMOUNT,a.TRANSACTIONNUMBER," +
                    "a.ADDITIONALPARAM1,a.ADDITIONALPARAM2,a.ADDITIONALPARAM3 from billpaydetail a LEFT JOIN retail_str_sales_master b ON a.BILLMASTERID =b.BILLMASTERID  where a.BILLMASTERID='" + billno + "'", null);
            if (productcursor.moveToFirst()) {//BILLPAYDETAILID
                do {
                    BillPayInvoice pm = new BillPayInvoice();
                    pm.setBILLMASTERID(productcursor.getString(productcursor.getColumnIndex("BILLMASTERID")));
                    pm.setSTORE_GUID(getFromRetailStore("STORE_GUID"));
                    pm.setTERMINAL_GUID(productcursor.getString(productcursor.getColumnIndex("MASTERTERMINALGUID")));
                    pm.setBILLNO(productcursor.getString(productcursor.getColumnIndex("BILLNO")));
                    pm.setMASTERPAYMODEGUID(productcursor.getString(productcursor.getColumnIndex("MASTERPAYMODEGUID")));
                    pm.setPAYAMOUNT(productcursor.getString(productcursor.getColumnIndex("PAYAMOUNT")));
                    pm.setTRANSACTIONNUMBER(productcursor.getString(productcursor.getColumnIndex("TRANSACTIONNUMBER")));
                    pm.setADDITIONALPARAM1(productcursor.getString(productcursor.getColumnIndex("ADDITIONALPARAM1")));
                    pm.setADDITIONALPARAM2(productcursor.getString(productcursor.getColumnIndex("ADDITIONALPARAM2")));
                    pm.setADDITIONALPARAM3(productcursor.getString(productcursor.getColumnIndex("ADDITIONALPARAM3")));
                    productlist.add(pm);
                    Log.d("PayModeData", "getBillPaymentDetails: GUID "+productcursor.getString(productcursor.getColumnIndex("MASTERPAYMODEGUID")));
                    Log.d("PayModeData", "getBillPaymentDetails: PAYMAMOUNT"+productcursor.getString(productcursor.getColumnIndex("PAYAMOUNT")));

                } while (productcursor.moveToNext());
            }else {

                Log.d("No Bill Data Found", "getBillPaymentDetails: Size= "+productlist.size());
            }






            db.close();
        } catch (IndexOutOfBoundsException cur) {
            cur.printStackTrace();
        }
        return productlist;
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

    private String getFromSalesMaster( String billno, String column) {

        String result = null;
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            result = "";
            String selectQuery = "SELECT "+column+" FROM retail_str_sales_master where BILLNO ='"+billno+"'";
            Cursor cursor = mydb.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {

                result = cursor.getString(0);
            }
            cursor.close();
            Log.d("DataRetrieved", "getFromSalesMaster: "+column+":"+result);
            mydb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}
