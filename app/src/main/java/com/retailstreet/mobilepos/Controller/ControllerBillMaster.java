package com.retailstreet.mobilepos.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.retailstreet.mobilepos.Model.BillMaster;
import com.retailstreet.mobilepos.Model.BillMasterUpload;
import com.retailstreet.mobilepos.View.ApplicationContextProvider;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

public class ControllerBillMaster {

    String dbname = "MasterDB";
    private Context context;

    public ControllerBillMaster() {

        context = ApplicationContextProvider.getContext();
    }

    public void createBillMasterData(String BILLMASTERID, String BILLNO, String SALEDATETIME, String SALEDATE, String SALETIME, String MASTERCUSTOMERGUID, String MASTERSTOREGUID, String MASTERTERMINALGUID, String MASTERSHIFTGUID, String USER_GUID, String CUST_MOBILENO, String NETVALUE, String TAXVALUE, String TOTAL_AMOUNT, String DELIVERY_TYPE_GUID, String BILL_PRINT, String TOTAL_BILL_AMOUNT, String NO_OF_ITEMS, String BILLSTATUS, String ISSYNCED, String RECEIVED_CASH, String BALANCE_CASH, String ROUND_OFF, String NETDISCOUNT,String BillmasterrGuid, String ORDER_STATUS) {


        BillMaster billMaster = new BillMaster(BILLMASTERID, BILLNO, SALEDATETIME, SALEDATE, SALETIME, MASTERCUSTOMERGUID, MASTERSTOREGUID, MASTERTERMINALGUID, MASTERSHIFTGUID, USER_GUID, CUST_MOBILENO, NETVALUE, TAXVALUE, TOTAL_AMOUNT, DELIVERY_TYPE_GUID, BILL_PRINT, TOTAL_BILL_AMOUNT, NO_OF_ITEMS, BILLSTATUS, ISSYNCED, RECEIVED_CASH, BALANCE_CASH, ROUND_OFF, NETDISCOUNT,BillmasterrGuid, ORDER_STATUS);
        InsertBillMaster(billMaster);
    }

    public void InsertBillMaster(BillMaster billMaster) {
        if (billMaster == null) {
            return;
        }
        SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, MODE_PRIVATE, null);
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
        contentValues.put("BILLMASTERGUID", billMaster.getBILLMASTERGUID());
        contentValues.put("ORDER_STATUS", billMaster.getORDER_STATUS());
        myDataBase.insert("retail_str_sales_master", null, contentValues);
        myDataBase.close(); // Closing database connection
    }

    public ArrayList<BillMasterUpload> getBillMaster() {

        String ORGGUID = getORGID();

        ArrayList<BillMasterUpload> billMasters = new ArrayList<>();
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, MODE_PRIVATE, null);
            String query = "SELECT  * FROM retail_str_sales_master WHERE ISSYNCED = '0'";
            Cursor cursor = myDataBase.rawQuery(query, null);

            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

               BillMasterUpload billMasterUpload = new BillMasterUpload();
               billMasterUpload.setBID(cursor.getString(0));
               billMasterUpload.setSTORE_GUID(cursor.getString(6));
                billMasterUpload.setTERMINAL_GUID(cursor.getString(7));
                billMasterUpload.setSHIFTID(cursor.getString(8));
                billMasterUpload.setBILLNO(cursor.getString(1));
                billMasterUpload.setSALEDATE(cursor.getString(3));
                billMasterUpload.setSALETIME(cursor.getString(4));
                billMasterUpload.setUSER_GUID(cursor.getString(9));
                billMasterUpload.setCUST_MOBILENO(cursor.getString(10));
                billMasterUpload.setNETVALUE(cursor.getString(11));
                billMasterUpload.setTAXVALUE(cursor.getString(12));
                billMasterUpload.setTOTAL_AMOUNT(cursor.getString(13));
                billMasterUpload.setDEL_TYPE(cursor.getString(14));
                billMasterUpload.setBILL_PRINT(cursor.getString(15));
                billMasterUpload.setTOTAL_BILL_AMOUNT(cursor.getString(16));
                billMasterUpload.setNO_OF_ITEMS(cursor.getString(17));
                billMasterUpload.setBILL_STATUS(cursor.getString(18));
                billMasterUpload.setMASTERCUSTOMER_GUID(cursor.getString(5));
                billMasterUpload.setRECEIVED_CASH(cursor.getString(20));
                billMasterUpload.setBALANCE_CASH(cursor.getString(21));
                billMasterUpload.setROUND_OFF(cursor.getString(22));
                billMasterUpload.setNETDISCOUNT(cursor.getString(23));
                billMasterUpload.setORDER_STATUS(cursor.getString(cursor.getColumnIndex("ORDER_STATUS")));
               billMasterUpload.setORG_GUID(ORGGUID);

                billMasters.add(billMasterUpload);
            }

            cursor.close();
            myDataBase.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return billMasters;
    }

    public String getORGID(){

        try {
            SQLiteDatabase mydb = context.openOrCreateDatabase("MasterDB",MODE_PRIVATE,null);
            Cursor cursor = mydb.rawQuery("Select MASTERORG_GUID from retail_store",null);
            if(cursor.moveToFirst()) {

                String ORG = cursor.getString(0);
                Log.d("RetailORGGUID", "getORGID:Retrieved "+ORG);
                return ORG;
            }
            cursor.close();
            mydb.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return " ";
    }

    public boolean updateIsSync(String BillmasterGuid){
        try {
            Log.d("MasterSyncID", "updateIsSync:Received "+BillmasterGuid);
            SQLiteDatabase mydb = context.openOrCreateDatabase("MasterDB",MODE_PRIVATE,null);
            String strSQL = "UPDATE retail_str_sales_master SET ISSYNCED = '1' WHERE BILLMASTERID = '"+BillmasterGuid+"'";
            mydb.execSQL(strSQL);
            Log.d("MasterSyncNote", "updateIsSync: Successful ");
            mydb.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}






