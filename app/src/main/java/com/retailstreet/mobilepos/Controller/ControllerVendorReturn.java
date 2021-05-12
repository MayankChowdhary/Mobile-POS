package com.retailstreet.mobilepos.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.retailstreet.mobilepos.Model.StockRegister;
import com.retailstreet.mobilepos.Model.VendorDetailReturn;
import com.retailstreet.mobilepos.Model.VendorMasterReturn;
import com.retailstreet.mobilepos.Utils.IDGenerator;
import com.retailstreet.mobilepos.Utils.WorkManagerSync;
import com.retailstreet.mobilepos.View.ApplicationContextProvider;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;
import static com.retailstreet.mobilepos.Database.TableDataInjector.status;
import static com.retailstreet.mobilepos.Utils.Constants.DBNAME;

/**
 * Created by Mayank Choudhary on 06-05-2021.
 * mayankchoudhary00@gmail.com
 */


public class ControllerVendorReturn {

    Context context;
    HashMap<String, String> returnList = new HashMap<>();

    String VENDOR_RETURN_DETAILID;
    String VENDOR_RETURNGUID;
    String ITEM_GUID;
    String BATCHNO;
    String QTY;
    String UOM_GUID;


    String VENDOR_RETURN_MASTERID;
    String VENDOR_GUID;
    String STORE_GUID;
    String INVOICENO;
    String INVOICEDATE;
    String RETURN_DATE;
    String REASON;
    String GRNNO;
    String GRNDATE;
    String CREATEDBY;
    String UPDATEDBY;
    String CREATEDON;
    String UPDATEDON;
    String ISSYNCED;
    String GRN_GUID;

    String REGISTERGUID;
    String MASTERORG_GUID;
    //String STORE_GUID;
    //String VENDOR_GUID;
    String LINETYPE;
    String TRANSACTIONTYPE;
    String TRANSACTIONNUMBER;
    String TRANSACTIONDATE;
   // String ITEM_GUID;
   // String UOM_GUID;
    String QUANTITY;
    //String BATCHNO;
    String BARCODE;
    String SALESPRICE;
    String WHOLESALEPRICE;
    String INTERNETPRICE;
    String SPECIALPRICE;
   // String ISSYNCED;


    public ControllerVendorReturn(){
     context = ApplicationContextProvider.getContext();
    }


    public ControllerVendorReturn(String vendorGuid, String invoiceNum, String reason){
        context = ApplicationContextProvider.getContext();
        initMap();
        VENDOR_RETURNGUID = IDGenerator.getUUID();

        String lastKey="";

        for (String key:returnList.keySet()) {
            lastKey = key;
            String qty = Objects.requireNonNull(returnList.get(key));
            String itemGuid = getFrom_tmp_vendor_return(key,"ITEM_GUID");
            String stockId = getFromStockMaster(itemGuid,"STOCK_ID");
            genearteVendorDetailReturn(key, itemGuid);
            UpdateQuantity(qty,stockId );
            generateStockRegister(stockId,getLineTotalItemsAmount(qty,key),vendorGuid,qty);
        }

        GRN_GUID = getFrom_tmp_vendor_return(lastKey,"GRN_GUID");
        genearteVendorMasterReturn(vendorGuid,invoiceNum,reason);
        UpdateGrnAmount(getOveallTotalAmount(),GRN_GUID);

        try {
            new WorkManagerSync(12);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            new WorkManagerSync(4);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            new WorkManagerSync(9);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ControllerVendorReturn(String vendorGuid , String reason) {
        context = ApplicationContextProvider.getContext();
        initMap2();
        VENDOR_RETURNGUID = IDGenerator.getUUID();

        for (String key : returnList.keySet()) {
            String  num = returnList.get(key);
            genearteVendorDetailReturn2(key);
            generateStockRegister(key,getTotalAmountNoGRN(key),vendorGuid,num);
           UpdateQuantity(num, key);
        }

        genearteVendorMasterReturn2(vendorGuid,reason);

        try {
            new WorkManagerSync(12);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            new WorkManagerSync(4);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        public void genearteVendorDetailReturn(String key,String itemGuid){
        VENDOR_RETURN_DETAILID = IDGenerator.getTimeStamp();
        ITEM_GUID = itemGuid;
        BATCHNO = getFromStockMaster(ITEM_GUID,"BATCH_NO");
        QTY = getFrom_tmp_vendor_return(key,"GRN_QTY");
        UOM_GUID = getFrom_tmp_vendor_return(key,"UOM_GUID");
        VendorDetailReturn vendorDetailReturn = new VendorDetailReturn( VENDOR_RETURN_DETAILID,  VENDOR_RETURNGUID,  ITEM_GUID,  BATCHNO,  QTY,  UOM_GUID);
        InsertVendorDetailReturn(vendorDetailReturn);
    }

    public void genearteVendorDetailReturn2(String key){
        VENDOR_RETURN_DETAILID = IDGenerator.getTimeStamp();
        ITEM_GUID = getFromStockMasterByID(key,"ITEM_GUID");
        BATCHNO = getFromStockMasterByID(key,"BATCH_NO");
        QTY = getFrom_tmp_vr_no_grn(key,"QTY");
        UOM_GUID = getFromProductMaster(ITEM_GUID,"UOM_GUID");
        VendorDetailReturn vendorDetailReturn = new VendorDetailReturn( VENDOR_RETURN_DETAILID,  VENDOR_RETURNGUID,  ITEM_GUID,  BATCHNO,  QTY,  UOM_GUID);
        InsertVendorDetailReturn(vendorDetailReturn);
    }

    public void generateStockRegister(String orderid, String totalAmount, String vendorGuid, String quantity){

        REGISTERGUID = IDGenerator.getUUID();
        MASTERORG_GUID = getFromRetailStore("MASTERORG_GUID");
        VENDOR_GUID =vendorGuid;
        ITEM_GUID = getFromStockMasterByID(orderid,"ITEM_GUID");
        STORE_GUID   = getFromRetailStore("STORE_GUID");
        UOM_GUID = getFromProductMaster(ITEM_GUID,"UOM_GUID");
        BATCHNO = getFromStockMasterByID(orderid,"BATCH_NO");
        LINETYPE = "DEBIT";
        QTY = quantity;
         TRANSACTIONTYPE = "VENDOR RETURN";
        TRANSACTIONNUMBER = VENDOR_RETURNGUID;
         TRANSACTIONDATE = getCurrentDateAndTime();
         BARCODE = getFromStockMasterByID(orderid,"BARCODE");
         //SALESPRICE = getFromStockMasterByID(orderid, "S_PRICE");
        SALESPRICE = totalAmount;
          WHOLESALEPRICE = getFromStockMasterByID(orderid, "WHOLE_SPRICE");
         INTERNETPRICE = getFromStockMasterByID(orderid, "INTERNET_PRICE");
         SPECIALPRICE = getFromStockMasterByID(orderid, "SPEC_PRICE");
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

    public void genearteVendorMasterReturn(String vendorGuid, String invoiceNum, String reason){
         VENDOR_RETURN_MASTERID = IDGenerator.getTimeStamp();
         VENDOR_GUID = vendorGuid;
         STORE_GUID = getFromRetailStore("STORE_GUID");
         INVOICENO  = invoiceNum;
         INVOICEDATE = getFromGRNMaster(GRN_GUID ,"INVOICEDATE");
         RETURN_DATE = getCurrentDateAndTime();
         REASON = reason;
         GRNNO = getFromGRNMaster(GRN_GUID,"GRNNO");
         GRNDATE =  getFromGRNMaster(GRN_GUID,"GRNDate");
         CREATEDBY =  getFromGroupUserMaster("USER_GUID");
         UPDATEDBY = getFromGroupUserMaster("USER_GUID");;
         CREATEDON = getCurrentDateAndTime();
         UPDATEDON = getCurrentDateAndTime();
         ISSYNCED = "0";

        VendorMasterReturn vendorMasterReturn = new VendorMasterReturn(VENDOR_RETURN_MASTERID, VENDOR_RETURNGUID, VENDOR_GUID, STORE_GUID, INVOICENO, INVOICEDATE, RETURN_DATE, REASON, GRNNO, GRNDATE, CREATEDBY, UPDATEDBY, CREATEDON, UPDATEDON, ISSYNCED);
        InsertVendorMasterReturn(vendorMasterReturn);
    }

    public void genearteVendorMasterReturn2(String vendorGuid, String reason){
        VENDOR_RETURN_MASTERID = IDGenerator.getTimeStamp();
        VENDOR_GUID = vendorGuid;
        STORE_GUID = getFromRetailStore("STORE_GUID");
        INVOICENO  = "";
        INVOICEDATE = "1990-01-01 00:00:00";
        RETURN_DATE = getCurrentDateAndTime();
        REASON = reason;
        GRNNO = "";
        GRNDATE =  "1990-01-01 00:00:00";
        CREATEDBY =  getFromGroupUserMaster("USER_GUID");
        UPDATEDBY = getFromGroupUserMaster("USER_GUID");;
        CREATEDON = getCurrentDateAndTime();
        UPDATEDON = getCurrentDateAndTime();
        ISSYNCED = "0";

        VendorMasterReturn vendorMasterReturn = new VendorMasterReturn(VENDOR_RETURN_MASTERID, VENDOR_RETURNGUID, VENDOR_GUID, STORE_GUID, INVOICENO, INVOICEDATE, RETURN_DATE, REASON, GRNNO, GRNDATE, CREATEDBY, UPDATEDBY, CREATEDON, UPDATEDON, ISSYNCED);
        InsertVendorMasterReturn(vendorMasterReturn);
    }

    public void InsertVendorMasterReturn(VendorMasterReturn prod) {
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE, null);

                ContentValues contentValues = new ContentValues();
                contentValues.put("VENDOR_RETURN_MASTERID", prod.getVENDOR_RETURN_MASTERID());
                contentValues.put("VENDOR_RETURNGUID", prod.getVENDOR_RETURNGUID());
                contentValues.put("VENDOR_GUID", prod.getVENDOR_GUID());
                contentValues.put("STORE_GUID", prod.getSTORE_GUID());
                contentValues.put("INVOICENO", prod.getINVOICENO());
                contentValues.put("INVOICEDATE", prod.getINVOICEDATE());
                contentValues.put("RETURN_DATE", prod.getRETURN_DATE());
                contentValues.put("REASON", prod.getREASON());
                contentValues.put("GRNNO", prod.getGRNNO());
                contentValues.put("GRNDATE", prod.getGRNDATE());
                contentValues.put("CREATEDBY", prod.getCREATEDBY());
                contentValues.put("UPDATEDBY", prod.getUPDATEDBY());
                contentValues.put("CREATEDON", prod.getCREATEDON());
                contentValues.put("UPDATEDON", prod.getUPDATEDON());
                contentValues.put("ISSYNCED", prod.getISSYNCED());

                myDataBase.insert("retail_str_vendor_master_return", null, contentValues);

            myDataBase.close();

            Log.d("Insertion Successful", "retail_str_vendor_master_return: "+status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void InsertVendorDetailReturn(VendorDetailReturn prod) {
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE, null);
                ContentValues contentValues = new ContentValues();
                contentValues.put("VENDOR_RETURN_DETAILID", prod.getVENDOR_RETURN_DETAILID());
                contentValues.put("VENDOR_RETURNGUID", prod.getVENDOR_RETURNGUID());
                contentValues.put("ITEM_GUID", prod.getITEM_GUID());
                contentValues.put("BATCHNO", prod.getBATCHNO());
                contentValues.put("QTY", prod.getQTY());
                contentValues.put("UOM_GUID", prod.getUOM_GUID());
                myDataBase.insert("retail_str_vendor_detail_return", null, contentValues);
            myDataBase.close();
            Log.d("Insertion Successful", "retail_str_vendor_detail_return: "+status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Cursor getVendorReturnCursor(String grnGuid) {
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            mydb.execSQL("DROP TABLE IF EXISTS tmp_vendor_return");
            mydb.execSQL("CREATE TABLE IF NOT EXISTS tmp_vendor_return AS SELECT pm.PROD_NM, pm.BARCODE, pm.UOM, gd.GRN_QTY,gd.FREE_QUANTITY, gd.PUR_PRICE, gd.GRN_VALUE, gd.EXP_DATE, gd.GRN_GUID, gd.GRNDETAILID, gd.ITEM_GUID,pm.UOM_GUID, gd.PURCHASEDISCOUNTBYAMOUNT FROM retail_store_prod_com pm INNER JOIN retail_str_grn_detail gd ON gd.ITEM_GUID = pm.ITEM_GUID WHERE gd.GRN_GUID = '"+ grnGuid +"'");
            mydb.execSQL("ALTER TABLE tmp_vendor_return ADD COLUMN MAXQTY INTEGER DEFAULT 0");
            mydb.execSQL("ALTER TABLE tmp_vendor_return ADD COLUMN TOTAL REAL DEFAULT 0.0");
            mydb.execSQL("UPDATE tmp_vendor_return SET TOTAL = (CAST(PUR_PRICE AS REAL)* CAST(GRN_QTY AS REAL))- CAST(PURCHASEDISCOUNTBYAMOUNT AS REAL)");
            mydb.execSQL("UPDATE tmp_vendor_return SET GRN_QTY = CAST(GRN_QTY AS REAL)+CAST(FREE_QUANTITY AS REAL)");
            mydb.execSQL("UPDATE tmp_vendor_return SET MAXQTY = CAST(GRN_QTY AS INTEGER)");

            Cursor cursor = mydb.rawQuery("SELECT * from tmp_vendor_return",null);
            if (cursor.moveToFirst()) {
                Log.d("VendorReturnCursor", "getVendorReturnCursor: "+cursor.getString(0));
                return cursor;
            } else {
               // Toast.makeText(context,"Bill Number Or Returnable Item Not Found!",Toast.LENGTH_LONG).show();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Cursor refreshVendorReturnCursor() {
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            Cursor cursor = mydb.rawQuery("SELECT * from tmp_vendor_return",null);
            if (cursor.moveToFirst()) {
                Log.d("VendorReturnCursor", "getVendorReturnCursor: "+cursor.getString(0));
                return cursor;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Cursor refreshNoGrnCursor() {
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            Cursor cursor = mydb.rawQuery("SELECT * from tmp_vr_no_grn",null);
            if (cursor.moveToFirst()) {
                Log.d("VendorReturnCursor", "getVendorReturnCursor: "+cursor.getString(0));
                return cursor;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private void initMap(){
        try {
            returnList.clear();
            SQLiteDatabase   mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            Cursor cursor  = mydb.rawQuery("SELECT * FROM tmp_vendor_return", null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    String id = cursor.getString(9);
                    String cnt = cursor.getString(3);
                    returnList.put(id,cnt);
                    cursor.moveToNext();
                }
            }
            cursor.close();
            mydb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initMap2(){
        try {
            returnList.clear();
            SQLiteDatabase   mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            Cursor cursor  = mydb.rawQuery("SELECT * FROM tmp_vr_no_grn", null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    String id = cursor.getString(0);
                    String cnt = cursor.getString(3);
                    returnList.put(id,cnt);
                    cursor.moveToNext();
                }
            }
            cursor.close();
            mydb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getFrom_tmp_vr_no_grn(String primary, String column) {
        String result = null;
        try {
            SQLiteDatabase mydb = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            result = "";
            String selectQuery = "SELECT " + column + " FROM tmp_vr_no_grn where STOCK_ID ='" + primary + "'";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                result = cursor.getString(0);
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getFrom_tmp_vendor_return: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    private String getFrom_tmp_vendor_return(String primary, String column) {
        String result = null;
        try {
            SQLiteDatabase mydb = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            result = "";
            String selectQuery = "SELECT " + column + " FROM tmp_vendor_return where GRNDETAILID ='" + primary + "'";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {

                result = cursor.getString(0);
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getFrom_tmp_vendor_return: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    private String getFromStockMaster( String itemGuid,String column) {
        String result= null;
        try {
            SQLiteDatabase  mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            result = "";
            String selectQuery = "SELECT "+column+" FROM retail_str_stock_master where ITEM_GUID = '"+itemGuid+"'";
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

    private String getFromStockMasterByID( String stockId,String column) {
        String result= null;
        try {
            SQLiteDatabase  mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            result = "";
            String selectQuery = "SELECT "+column+" FROM retail_str_stock_master where STOCK_ID = '"+stockId+"'";
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

    private String getFromGRNMaster( String grnGuid,String column) {
        String result= null;
        try {
            SQLiteDatabase  mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            result = "";
            String selectQuery = "SELECT "+column+" FROM retail_str_grn_master where GRN_GUID = '"+grnGuid+"'";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {

                result= cursor.getString(0);
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getGrnMaster: "+column+": "+result);
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

    private String getCurrentDateAndTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return formatter.format(date);
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

    public void UpdateQuantity(String quantity, String stockID){
        try {

            String CurrentQty= getFromStockMasterByID(stockID,"QTY");
            CurrentQty = String.valueOf(Double.parseDouble(CurrentQty)+Double.parseDouble(quantity));
            String query = "Update retail_str_stock_master Set QTY = '"+CurrentQty+"', ISSYNCED = '0'  where STOCK_ID = '"+stockID+"'";
            SQLiteDatabase   db  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            db.execSQL(query);
            db.close();
            Log.d("UpdateStockQty", "UpdateQuantity: Successful"+quantity);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public String getSingleItemAmount(String grnDetailID){

        String result="";
        String GRNQty="";
        try {
            SQLiteDatabase mydb = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            result = "";
            String selectQuery1 = "SELECT GRN_QTY FROM retail_str_grn_detail where GRNDETAILID = '" + grnDetailID + "'";
            Cursor cursor = mydb.rawQuery(selectQuery1, null);

            if(cursor.moveToFirst()){

                GRNQty= cursor.getString(0);
                Log.d("GRN QTY Received", "getSingleItemAmount: "+GRNQty);
            }

            cursor.close();

            String selectQuery = "SELECT  TOTAL FROM tmp_vendor_return where GRNDETAILID = '" + grnDetailID + "'";
             cursor = mydb.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {

                result = cursor.getString(0);
                Log.d("LineTotalRetrieved", "getSingleItemAmount: "+result);
                result = String.valueOf (Double.parseDouble(result)/Double.parseDouble(GRNQty));
                Log.d("ItemAmountClacd", "getSingleItemAmount: "+result);
            }

            cursor.close();
            mydb.close();
            Log.d("RetrnedSingleItemAmount", "getSingleItemAmount: "+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private String getLineTotalItemsAmount(String noOfItems, String grnDetailID){

        String result="";
        Double GRNQty=0.0;
        Double itemNumber= Double.parseDouble(noOfItems);
        Double singleItemAmount = Double.parseDouble(getSingleItemAmount(grnDetailID));
        try {
            SQLiteDatabase mydb = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String selectQuery1 = "SELECT GRN_QTY FROM retail_str_grn_detail where GRNDETAILID = '" + grnDetailID + "'";
            Cursor cursor = mydb.rawQuery(selectQuery1, null);

            if (cursor.moveToFirst()) {

                GRNQty = Double.parseDouble(cursor.getString(0));
                Log.d("GRN QTY Received", "getTotalItemsAmount: " + GRNQty);
            }

            if( itemNumber <= GRNQty){

                result = String.valueOf(itemNumber*singleItemAmount);
                Log.d("GRN QTY Final1 Calc", "getTotalItemsAmount: " + result);

            }else {

                result = String.valueOf(GRNQty*singleItemAmount);
                Log.d("GRN QTY Final2 Calc", "getTotalItemsAmount: " + result);
            }

            cursor.close();
        }catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private String getTotalAmountNoGRN( String id){
        String total="0.0";
        try {
            SQLiteDatabase   mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            Cursor cursor  = mydb.rawQuery("SELECT TOTAL FROM tmp_vr_no_grn WHERE STOCK_ID = '"+id+"'", null);
            if (cursor.moveToFirst()) {
                     total = cursor.getString(0);
            }
            cursor.close();
            mydb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return String.valueOf(total);

    }

    private String getOveallTotalAmount(){

        Double result=0.0;

        try {
            SQLiteDatabase mydb = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String selectQuery1 = "SELECT GRNDETAILID, GRN_QTY FROM tmp_vendor_return";
            Cursor cursor = mydb.rawQuery(selectQuery1, null);

            if (cursor.moveToFirst()) {


                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                    String id = cursor.getString(0);
                    double GRNQty = Double.parseDouble(cursor.getString(1));
                    Log.d("GRN QTY Received", "getOveallTotalAmount: " + GRNQty);
                    Double lineTotal = Double.parseDouble(getLineTotalItemsAmount(String.valueOf(GRNQty),id));
                    result += lineTotal;
                    Log.d("GRN QTY GrandCalc", "getOveallTotalAmount: " + GRNQty);

                }
            }

            cursor.close();
        }catch (Exception e) {
            e.printStackTrace();
        }

        return String.valueOf(result);
    }


    public void UpdateGrnAmount(String deduction, String grnGuid){
        try {

            Log.d("UpdateGrnAmount", "GrandAmountReceived: "+deduction);
            String CurrentQty= getFromGRNMaster(GRN_GUID ,"GRANDAMOUNT");
            Log.d("UpdateGrnAmount", "GrandAmountRetrieved: OLD"+CurrentQty);
            CurrentQty = String.valueOf(Double.parseDouble(CurrentQty)- Double.parseDouble(deduction));
            String query = "Update retail_str_grn_master Set GRANDAMOUNT = '"+CurrentQty+"', ISSYNCED = '0'  where GRN_GUID = '"+grnGuid+"'";
            SQLiteDatabase   db  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            db.execSQL(query);
            db.close();
            Log.d("UpdateGRNAmount", "UpdateQuantity: Successful"+CurrentQty);

        } catch (Exception e) {

            e.printStackTrace();
        }

    }
}

