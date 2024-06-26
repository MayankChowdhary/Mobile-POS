package com.retailstreet.mobilepos.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.retailstreet.mobilepos.Model.StockMaster;
import com.retailstreet.mobilepos.Model.StockMasterSync;
import com.retailstreet.mobilepos.Model.StockRegister;
import com.retailstreet.mobilepos.Utils.IDGenerator;
import com.retailstreet.mobilepos.Utils.WorkManagerSync;
import com.retailstreet.mobilepos.View.ApplicationContextProvider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;
import static com.retailstreet.mobilepos.Utils.Constants.DBNAME;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */
public class ControllerStockMaster extends SQLiteOpenHelper {

    private  Context context;
    SQLiteDatabase db;
    Cursor cursor;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MasterDB";
    private static final String TABLE_NAME = "retail_str_stock_master";
    private ControllerStoreConfig config;
    private static boolean VENDOR_VISIBILITY = true;
    
        String STOCK_ID ;
        String STORE_GUID ;
        String ITEM_GUID ;
        String QTY ;
        String SALE_UOMID ;
        String UOM ;
        String BATCH_NO ;
        String BARCODE ;
        String P_PRICE ;
        String MRP ;
        String S_PRICE ;
        String INTERNET_PRICE ;
        String MIN_QUANTITY ;
        String MAX_QUANTITY ;
        String WHOLE_SPRICE ;
        String SPEC_PRICE ;
        String GENERIC_NAME ;
        String EXTERNALPRODUCTID ;
        String GST ;
        String SGST ;
        String CGST ;
        String IGST ;
        String CESS1 ;
        String CESS2 ;
        String EXP_DATE;
        String PROD_NM ;
        String ITEM_CODE ;
        String CREATED_BY ;
        String UPDATEDBY ;
        String CREATED_ON ;
        String UPDATEDON ;
        String SALESDISCOUNTBYPERCENTAGE ;
        String SALESDISCOUNTBYAMOUNT ;
        String GRN_GUID ;
        String GRNNO ;
        String VENDOR_GUID ;
        String VENDOR_NAME ;
        String ISSYNCED ;
        String GRNDETAILGUID;
        String MASTER_TERMINAL_ID;


    public ControllerStockMaster(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context =context;
        MASTER_TERMINAL_ID = getTerminal_ID();
        config = new ControllerStoreConfig();
        VENDOR_VISIBILITY =config.getVendorVisibility();

    }

    public void  InjectIntoStockMaster(String vendor_name, String vendor_guid, String userGuid, String itemCode, String prodName, String ExpDate, String cess1, String cess2, String gst, String sgst, String igst, String cgst, String extProdId, String genricName, String spec_price, String wholePrice, String minQty, String maxQty, String internetPrice, String s_price, String mrp, String p_price, String barcode, String batch_no, String uom, String itemGuid, String qty, String uomGuid){
        this.context =ApplicationContextProvider.getContext();
        MASTER_TERMINAL_ID = getTerminal_ID();
        STOCK_ID= IDGenerator.getTimeStamp();
        STORE_GUID = getFromRetailStore("STORE_GUID");
        ITEM_GUID = itemGuid;
        QTY = qty;
        SALE_UOMID  = uomGuid;
        UOM = uom;
        BATCH_NO = batch_no;
        BARCODE = barcode;
        P_PRICE = p_price;
        MRP = mrp;
        S_PRICE = s_price;
        INTERNET_PRICE  = internetPrice;
        MIN_QUANTITY = minQty;
        MAX_QUANTITY = maxQty;
        WHOLE_SPRICE = wholePrice;
        SPEC_PRICE = spec_price;
        GENERIC_NAME = " ";
        EXTERNALPRODUCTID = extProdId;
        GST = gst;
        SGST= sgst;
        IGST = igst;
        CGST = cgst;
        CESS1 =cess1;
        CESS2 = cess2;
        EXP_DATE = ExpDate;
        PROD_NM = prodName;
        ITEM_CODE = itemCode;
        CREATED_BY = userGuid;
        UPDATEDBY = userGuid;
        CREATED_ON = getStockDateAndTime();
        UPDATEDON = getStockDateAndTime();
        SALESDISCOUNTBYPERCENTAGE = "0.00";
        SALESDISCOUNTBYAMOUNT = "0.00";
        GRN_GUID = " ";
        GRNNO = " ";
        VENDOR_GUID = vendor_guid;
        VENDOR_NAME = vendor_name;
        ISSYNCED ="0";
        GRNDETAILGUID = " ";
        

        StockMaster stockMaster = new StockMaster(STOCK_ID, STORE_GUID, ITEM_GUID, QTY, SALE_UOMID, UOM, BATCH_NO, BARCODE, P_PRICE, MRP, S_PRICE, INTERNET_PRICE, MIN_QUANTITY, MAX_QUANTITY, WHOLE_SPRICE, SPEC_PRICE, GENERIC_NAME, EXTERNALPRODUCTID, GST, SGST, CGST, IGST, CESS1, CESS2, EXP_DATE, PROD_NM, ITEM_CODE, CREATED_BY, UPDATEDBY, CREATED_ON, UPDATEDON, SALESDISCOUNTBYPERCENTAGE, SALESDISCOUNTBYAMOUNT, GRN_GUID, GRNNO, VENDOR_GUID, VENDOR_NAME, ISSYNCED, GRNDETAILGUID, MASTER_TERMINAL_ID);
        InsertStockMaster(stockMaster);

    }

    public void  InjectIntoStockMasterFromPurcaheInvoice(String vendor_name, String vendor_guid, String userGuid, String itemCode, String prodName, String ExpDate, String cess1, String cess2, String gst, String sgst, String igst, String cgst, String extProdId, String genricName, String spec_price, String wholePrice, String minQty, String maxQty, String internetPrice, String s_price, String mrp, String p_price, String barcode, String batch_no, String uom, String itemGuid, String qty, String uomGuid,String grnDetailGuid,String grnguid,String grnno, String discByPer, String discByAmount){
        this.context =ApplicationContextProvider.getContext();
        MASTER_TERMINAL_ID= getTerminal_ID();
        STOCK_ID= IDGenerator.getTimeStamp();
        STORE_GUID = getFromRetailStore("STORE_GUID");
        ITEM_GUID = itemGuid;
        QTY = qty;
        SALE_UOMID  = uomGuid;
        UOM = uom;
        BATCH_NO = batch_no;
        BARCODE = barcode;
        P_PRICE = p_price;
        MRP = mrp;
        S_PRICE = s_price;
        INTERNET_PRICE  = internetPrice;
        MIN_QUANTITY = minQty;
        MAX_QUANTITY = maxQty;
        WHOLE_SPRICE = wholePrice;
        SPEC_PRICE = spec_price;
        GENERIC_NAME = " ";
        EXTERNALPRODUCTID = extProdId;
        GST = gst;
        SGST= sgst;
        IGST = igst;
        CGST = cgst;
        CESS1 =cess1;
        CESS2 = cess2;
        EXP_DATE = ExpDate;
        PROD_NM = prodName;
        ITEM_CODE = itemCode;
        CREATED_BY = userGuid;
        UPDATEDBY = userGuid;
        CREATED_ON = getStockDateAndTime();
        UPDATEDON = getStockDateAndTime();
        SALESDISCOUNTBYPERCENTAGE = discByPer;
        SALESDISCOUNTBYAMOUNT = discByAmount;
        GRN_GUID = grnguid ;
        GRNNO = grnno;
        VENDOR_GUID = vendor_guid;
        VENDOR_NAME = vendor_name;
        ISSYNCED ="0";
        GRNDETAILGUID = grnDetailGuid;

        StockMaster stockMaster = new StockMaster(STOCK_ID, STORE_GUID, ITEM_GUID, QTY, SALE_UOMID, UOM, BATCH_NO, BARCODE, P_PRICE, MRP, S_PRICE, INTERNET_PRICE, MIN_QUANTITY, MAX_QUANTITY, WHOLE_SPRICE, SPEC_PRICE, GENERIC_NAME, EXTERNALPRODUCTID, GST, SGST, CGST, IGST, CESS1, CESS2, EXP_DATE, PROD_NM, ITEM_CODE, CREATED_BY, UPDATEDBY, CREATED_ON, UPDATEDON, SALESDISCOUNTBYPERCENTAGE, SALESDISCOUNTBYAMOUNT, GRN_GUID, GRNNO, VENDOR_GUID, VENDOR_NAME, ISSYNCED, GRNDETAILGUID,MASTER_TERMINAL_ID);
        InsertStockMaster(stockMaster);

    }

    public void InsertStockMaster(StockMaster prod) {
        try {
                 db = getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("STOCK_ID", prod.getSTOCK_ID());
                values.put("STORE_GUID",prod.getSTORE_GUID());
                values.put("ITEM_GUID", prod.getITEM_GUID());
                values.put("QTY", prod.getQTY());
                values.put("SALE_UOMID",prod.getSALE_UOMID());
                values.put("UOM", prod.getUOM());
                values.put("BATCH_NO",prod.getBATCH_NO());
                values.put("BARCODE", prod.getBARCODE());
                values.put("P_PRICE", prod.getP_PRICE());
                values.put("MRP", prod.getMRP());
                values.put("S_PRICE", prod.getS_PRICE());
                values.put("INTERNET_PRICE",prod.getINTERNET_PRICE());
                values.put("MIN_QUANTITY",prod.getMIN_QUANTITY());
                values.put("MAX_QUANTITY",prod.getMAX_QUANTITY());
                values.put("WHOLE_SPRICE", prod.getWHOLE_SPRICE());
                values.put("SPEC_PRICE", prod.getSPEC_PRICE());
                values.put("GENERIC_NAME",prod.getGENERIC_NAME());
                values.put("EXTERNALPRODUCTID",prod.getEXTERNALPRODUCTID());
                values.put("GST",prod.getGST());
                values.put("CGST", prod.getCGST());
                values.put("SGST", prod.getSGST());
                values.put("IGST",prod.getIGST());
                values.put("CESS1",prod.getCESS1());
                values.put("CESS2",prod.getCESS2());
                values.put("EXP_DATE",prod.getEXP_DATE());
                values.put("PROD_NM", prod.getPROD_NM());
                values.put("ITEM_CODE",prod.getITEM_CODE());
                values.put("CREATED_BY",prod.getCREATED_BY());
                values.put("CREATED_ON",prod.getCREATED_ON());
                values.put("UPDATEDBY",prod.getUPDATEDBY());
                values.put("UPDATEDON",prod.getUPDATEDON());
                values.put("SALESDISCOUNTBYPERCENTAGE",prod.getSALESDISCOUNTBYPERCENTAGE());
                values.put("SALESDISCOUNTBYAMOUNT", prod.getSALESDISCOUNTBYAMOUNT());
                values.put("GRNNO", prod.getGRNNO());
                values.put("GRN_GUID", prod.getGRN_GUID());
                values.put("VENDOR_NAME", prod.getVENDOR_NAME());
                values.put("VENDOR_GUID",prod.getVENDOR_GUID());
                values.put("ISSYNCED", prod.getISSYNCED());
                values.put("GRNDETAILGUID",prod.getGRNDETAILGUID());
                values.put("MASTER_TERMINAL_ID", prod.getMASTER_TERMINAL_ID());
                db.insert("retail_str_stock_master", null, values);
                db.close();
            Log.d("Insertion Successful", "InsertStockMaster: ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateStockMaster(String stockid,String  EXTERNALPRODUCTID,String  BARCODE,String  EXP_DATE,String S_PRICE,String P_PRICE,String QTY, String MRP, String discByPer){
        try{
            db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("EXTERNALPRODUCTID", EXTERNALPRODUCTID);
            contentValues.put("BARCODE", BARCODE);
            contentValues.put("EXP_DATE", EXP_DATE);
            contentValues.put("S_PRICE", S_PRICE);
            contentValues.put("P_PRICE", P_PRICE);
            contentValues.put("QTY", QTY);
            contentValues.put("MRP", MRP);
            contentValues.put("SALESDISCOUNTBYPERCENTAGE", discByPer);
            contentValues.put("ISSYNCED", "0");


            String where = "STOCK_ID=?";
            String[] whereArgs = new String[] {String.valueOf(stockid)};
            db.update("retail_str_stock_master", contentValues, where, whereArgs);
            db.close();
            Log.d("Updation Successful", "UpdateStockMaster: ");
            try {
                new WorkManagerSync(4);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    public void generateStockRegister(String orderid , String count){

        String  REGISTERGUID = IDGenerator.getUUID();
        String  MASTERORG_GUID = getFromRetailStore("MASTERORG_GUID");
        String VENDOR_GUID = getFromStockMaster(orderid,"VENDOR_GUID");
        String LINETYPE = "CREDIT";
        QTY = count;
        ITEM_GUID = getFromStockMaster(orderid,"ITEM_GUID");
        String BATCHNO = getFromStockMaster(orderid,"BATCH_NO");
        String UOM_GUID = getFromProductMaster(ITEM_GUID,"UOM_GUID");
        STORE_GUID  = getFromRetailStore("STORE_GUID");
        String TRANSACTIONTYPE = "STOCK ADJUSTMENT";
        String TRANSACTIONNUMBER = IDGenerator.getTimeStamp();
        String TRANSACTIONDATE = getStockDateAndTime();
        String BARCODE = getFromStockMaster(orderid,"BARCODE");
        String SALESPRICE = getFromStockMaster(orderid, "S_PRICE");
        String  WHOLESALEPRICE = getFromStockMaster(orderid, "WHOLE_SPRICE");
        String INTERNETPRICE = getFromStockMaster(orderid, "INTERNET_PRICE");
        String SPECIALPRICE = getFromStockMaster(orderid, "SPEC_PRICE");
        ISSYNCED ="0";
        StockRegister stockRegister = new StockRegister(REGISTERGUID, MASTERORG_GUID, STORE_GUID, VENDOR_GUID, LINETYPE, TRANSACTIONTYPE, TRANSACTIONNUMBER, TRANSACTIONDATE, ITEM_GUID, UOM_GUID, QTY, BATCHNO, BARCODE, SALESPRICE, WHOLESALEPRICE, INTERNETPRICE, SPECIALPRICE, ISSYNCED,MASTER_TERMINAL_ID);
        InsertStockRegister(stockRegister);
        try {
            new WorkManagerSync(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void updateStockMasterFromPurchaseInvoice(String stockid,String VENDOR_GUID,String VENDOR_NAME, String  PROD_NM,String  EXTERNALPRODUCTID,String  BARCODE,String  EXP_DATE,String  MRP,String S_PRICE,String P_PRICE,String QTY,String CGST,String SGST,String USER_GUID,String GrndetailGuid, String grnGuid,String grnno,String discByPer, String discByAmount){
        try{

            db = getWritableDatabase();
            UPDATEDON = getStockDateAndTime();
            ContentValues contentValues = new ContentValues();
            contentValues.put("VENDOR_GUID", VENDOR_GUID);
            contentValues.put("VENDOR_NAME", VENDOR_NAME);
            contentValues.put("PROD_NM", PROD_NM);
            contentValues.put("EXTERNALPRODUCTID", EXTERNALPRODUCTID);
            contentValues.put("BARCODE", BARCODE);
            contentValues.put("EXP_DATE", EXP_DATE);
            contentValues.put("MRP", MRP);
            contentValues.put("S_PRICE", S_PRICE);
            contentValues.put("P_PRICE", P_PRICE);
            contentValues.put("QTY", QTY);
            contentValues.put("CGST", CGST);
            contentValues.put("SGST", SGST);
            contentValues.put("GRNNO", grnno);
            contentValues.put("GRN_GUID", grnGuid);
            contentValues.put("UPDATEDBY",USER_GUID);
            contentValues.put("UPDATEDON",UPDATEDON);
            contentValues.put("GRNDETAILGUID",GrndetailGuid);
            contentValues.put("SALESDISCOUNTBYPERCENTAGE",discByPer);
            contentValues.put("SALESDISCOUNTBYAMOUNT", discByAmount);
            contentValues.put("ISSYNCED", "0");

            String where = "STOCK_ID=?";
            String[] whereArgs = new String[] {String.valueOf(stockid)};
            db.update("retail_str_stock_master", contentValues, where, whereArgs);
            db.close();
            Log.d("Updation Successful", "UpdateStockMaster: ");

            try {
                new WorkManagerSync(4);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      /*  String CREATION_TABLE = "CREATE TABLE StockMaster ( "
                + "EMPLOYEE_GUID INTEGER PRIMARY KEY, " + "ISACTIVE TEXT, "
                + "PASSWORD TEXT, "+ "ROLE_GUID TEXT, " + "ROLE_ID TEXT," + "ROLE_NAME TEXT,"+ "USER_GUID TEXT,"+ "USER_NAME TEXT,"+ "ISSYNCED TEXT )";

        db.execSQL(CREATION_TABLE);

       */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      /*  // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db); */
    }

    
    


    public Cursor getStockMasterCursor() {
        try {
            if(cursor != null && !cursor.isClosed()){
                cursor.close();
            }
            String query;

            if(VENDOR_VISIBILITY){
                query = "SELECT  * FROM " + TABLE_NAME;
            }else {
                query = "SELECT STOCK_ID, STORE_GUID,  ITEM_GUID,  TOTAL(QTY),  SALE_UOMID,  UOM,  BATCH_NO,  BARCODE,  P_PRICE,  MRP,  S_PRICE,  INTERNET_PRICE,  MIN_QUANTITY,  MAX_QUANTITY,  WHOLE_SPRICE,  SPEC_PRICE,  GENERIC_NAME,  EXTERNALPRODUCTID,  GST,  SGST,  CGST,  IGST,  CESS1,  CESS2,  EXP_DATE,  PROD_NM,  ITEM_CODE,  CREATED_BY,  UPDATEDBY,  CREATED_ON,  UPDATEDON,  SALESDISCOUNTBYPERCENTAGE,  SALESDISCOUNTBYAMOUNT,  GRN_GUID,  GRNNO,  VENDOR_GUID,  VENDOR_NAME,  GRNDETAILGUID,  ISSYNCED,  MASTER_TERMINAL_ID FROM " + TABLE_NAME+" GROUP BY ITEM_GUID,MRP";
            }

            db = getReadableDatabase();          //Opens database in writable mode.
            cursor = db.rawQuery(query, null);
           if(cursor.moveToFirst()){
               return cursor;
           }else {
               return null;
           }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cursor;
    }
    public Cursor searchStockMasterCursor(String pattern, String column) {
        try {
            if(cursor != null){
                cursor.close();
            }
            String query;

            if(VENDOR_VISIBILITY){
                query = "SELECT  * FROM " + TABLE_NAME+" WHERE "+ column+" LIKE "+"'%"+pattern+"%'";
            }else {
                query = "SELECT  STOCK_ID, STORE_GUID,  ITEM_GUID,  TOTAL(QTY),  SALE_UOMID,  UOM,  BATCH_NO,  BARCODE,  P_PRICE,  MRP,  S_PRICE,  INTERNET_PRICE,  MIN_QUANTITY,  MAX_QUANTITY,  WHOLE_SPRICE,  SPEC_PRICE,  GENERIC_NAME,  EXTERNALPRODUCTID,  GST,  SGST,  CGST,  IGST,  CESS1,  CESS2,  EXP_DATE,  PROD_NM,  ITEM_CODE,  CREATED_BY,  UPDATEDBY,  CREATED_ON,  UPDATEDON,  SALESDISCOUNTBYPERCENTAGE,  SALESDISCOUNTBYAMOUNT,  GRN_GUID,  GRNNO,  VENDOR_GUID,  VENDOR_NAME,  GRNDETAILGUID,  ISSYNCED,  MASTER_TERMINAL_ID FROM " + TABLE_NAME+" WHERE "+ column+" LIKE "+"'%"+pattern+"%' GROUP BY ITEM_GUID,MRP";
            }


            db = getReadableDatabase();          //Opens database in writable mode.
            cursor = db.rawQuery(query, null);
            if(cursor.moveToFirst()){
                return cursor;
            }else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cursor;
    }

    private String getStockDateAndTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return formatter.format(date);
    }

    private String getFromStockMaster( String stockid,String column) {
        String result= null;
        try {
            SQLiteDatabase  mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            result = "";
            String selectQuery = "SELECT "+column+" FROM retail_str_stock_master where STOCK_ID = '"+stockid+"'";
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


    public ArrayList<StockMasterSync> getStockSyncdata() {
        ArrayList<StockMasterSync> productlist = new ArrayList<StockMasterSync>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor productcursor = db.rawQuery(" select distinct a.STOCK_ID,b.SUB_CATEGORYGUID,b.CATEGORY_GUID,a.STORE_GUID,a.ITEM_GUID,a.ITEM_CODE,a.PROD_NM,a.SALE_UOMID," +
                    "a.BATCH_NO,a.QTY,a.P_PRICE,a.MRP,a.S_PRICE,a.INTERNET_PRICE,a.BARCODE,a.CREATED_BY,a.CREATED_ON,a.UPDATEDBY,a.UPDATEDON," +
                    "a.MIN_QUANTITY,a.MAX_QUANTITY,a.WHOLE_SPRICE,a.SPEC_PRICE,a.EXP_DATE,a.SALESDISCOUNTBYPERCENTAGE,a.SALESDISCOUNTBYAMOUNT," +
                    "a.VENDOR_GUID,a.GRNDETAILGUID,a.GRN_GUID from retail_str_stock_master a LEFT JOIN retail_store_prod_com b ON a.ITEM_GUID = b.ITEM_GUID where a.ISSYNCED = '0' ", null);

            if (productcursor.moveToFirst()) {
                do {
                    StockMasterSync pm = new StockMasterSync();

                    pm.setSTOCKID(productcursor.getString(productcursor.getColumnIndex("STOCK_ID")));
                    pm.setORG_GUID(getFromRetailStore("MASTERORG_GUID"));
                    pm.setSTORE_GUID(productcursor.getString(productcursor.getColumnIndex("STORE_GUID")));
                    pm.setCATEGORY_GUID(productcursor.getString(productcursor.getColumnIndex("CATEGORY_GUID")));
                    pm.setSUBCATEGORY_GUID(productcursor.getString(productcursor.getColumnIndex("SUB_CATEGORYGUID")));
                    pm.setITEM_GUID(productcursor.getString(productcursor.getColumnIndex("ITEM_GUID")));
                    pm.setITEM_CODE(productcursor.getString(productcursor.getColumnIndex("ITEM_CODE")));
                    pm.setITEM_NAME(productcursor.getString(productcursor.getColumnIndex("PROD_NM")));
                    pm.setPUR_UOM_GUID(productcursor.getString(productcursor.getColumnIndex("SALE_UOMID")));
                    pm.setSALE_UOM_GUID(productcursor.getString(productcursor.getColumnIndex("SALE_UOMID")));
                    if (productcursor.getString(productcursor.getColumnIndex("BATCH_NO")) == null || productcursor.getString(productcursor.getColumnIndex("BATCH_NO")).isEmpty())
                        pm.setBATCHNO("");
                    else
                        pm.setBATCHNO(productcursor.getString(productcursor.getColumnIndex("BATCH_NO")));
                    pm.setQTY(productcursor.getString(productcursor.getColumnIndex("QTY")));
                    pm.setCOST_PRICE(productcursor.getString(productcursor.getColumnIndex("P_PRICE")));
                    pm.setMRP(productcursor.getString(productcursor.getColumnIndex("MRP")));
                    pm.setSALEPRICE(productcursor.getString(productcursor.getColumnIndex("S_PRICE")));
                    if (productcursor.getString(productcursor.getColumnIndex("INTERNET_PRICE")) == null
                            || (productcursor.getString(productcursor.getColumnIndex("INTERNET_PRICE")).matches("")) ||
                            productcursor.getString(productcursor.getColumnIndex("INTERNET_PRICE")).isEmpty()) {
                        pm.setINTERNETPRICE("0.00");
                    } else {
                        pm.setINTERNETPRICE(productcursor.getString(productcursor.getColumnIndex("INTERNET_PRICE")));
                    }

                    if (productcursor.getString(productcursor.getColumnIndex("BARCODE")).endsWith("\n")) {
                        int barcodelength = productcursor.getString(productcursor.getColumnIndex("BARCODE")).length();
                        // if(barcodelength>1)
                        pm.setBARCODE(productcursor.getString(productcursor.getColumnIndex("BARCODE")).replaceAll("[\n\r]$", ""));
                        //else
                        //    pm.setBARCODE(productcursor.getString(productcursor.getColumnIndex("BARCODE")));
                    } else {
                        pm.setBARCODE(productcursor.getString(productcursor.getColumnIndex("BARCODE")));
                    }

                    pm.setCREATEDBYGUID(productcursor.getString(productcursor.getColumnIndex("CREATED_BY")));
                    pm.setCREATEDON(productcursor.getString(productcursor.getColumnIndex("CREATED_ON")));
                    pm.setUPDATEDBYGUID(productcursor.getString(productcursor.getColumnIndex("UPDATEDBY")));
                    pm.setUPDATEDON(productcursor.getString(productcursor.getColumnIndex("UPDATEDON")));
                    if (productcursor.getString(productcursor.getColumnIndex("MIN_QUANTITY")) == null ||
                            productcursor.getString(productcursor.getColumnIndex("MIN_QUANTITY")).isEmpty() ||
                            productcursor.getString(productcursor.getColumnIndex("MIN_QUANTITY")).matches("")) {
                        pm.setMIN_QUANTITY("0");
                    } else {
                        pm.setMIN_QUANTITY(productcursor.getString(productcursor.getColumnIndex("MIN_QUANTITY")));
                    }
                    if (productcursor.getString(productcursor.getColumnIndex("MAX_QUANTITY")) == null ||
                            productcursor.getString(productcursor.getColumnIndex("MAX_QUANTITY")).isEmpty() ||
                            productcursor.getString(productcursor.getColumnIndex("MAX_QUANTITY")).matches("")) {
                        pm.setMAX_QUANTITY("0");
                    } else {
                        pm.setMAX_QUANTITY(productcursor.getString(productcursor.getColumnIndex("MAX_QUANTITY")));
                    }
                    String val = productcursor.getString(productcursor.getColumnIndex("WHOLE_SPRICE"));
                    if (val.matches("")) {
                        pm.setWHOLESALEPRICE("0.00");
                    } else {
                        pm.setWHOLESALEPRICE(productcursor.getString(productcursor.getColumnIndex("WHOLE_SPRICE")));
                    }

                    if (productcursor.getString(productcursor.getColumnIndex("SPEC_PRICE")) == null || (productcursor.getString(productcursor.getColumnIndex("SPEC_PRICE")).matches(""))) {
                        pm.setSPECIALPRICE("0.00");
                    } else {
                        pm.setSPECIALPRICE(productcursor.getString(productcursor.getColumnIndex("SPEC_PRICE")));
                    }
                    pm.setEXPIRYDATE(productcursor.getString(productcursor.getColumnIndex("EXP_DATE")));
                    pm.setSALESDISCOUNTBYPERCENTAGE(productcursor.getString(productcursor.getColumnIndex("SALESDISCOUNTBYPERCENTAGE")));
                    pm.setSALESDISCOUNTBYAMOUNT(productcursor.getString(productcursor.getColumnIndex("SALESDISCOUNTBYAMOUNT")));
                    pm.setMASTER_VENDOR_GUID(productcursor.getString(productcursor.getColumnIndex("VENDOR_GUID")));

                    pm.setGRN_DETAIL_GUID("");
                    pm.setGRN_MASTER_GUID("");
                    pm.setMASTER_TERMINAL_ID(DBRetriever.getTerminal_ID());
                    productlist.add(pm);
                } while (productcursor.moveToNext());
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


    public Boolean updateintoStock(String ITEM_GUID) {
        boolean returnval = true;
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues value = new ContentValues();
        value.put("ISSYNCED", "1");
        int sqlUpdateRetval = db.update("retail_str_stock_master", value,
                " STOCK_ID = ? "
                , new String[]{String.format(ITEM_GUID)});

        if (sqlUpdateRetval < 1) {
            returnval = false;
        }
        db.close();
        return returnval;
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

    private String getTerminal_ID(){
        String result= null;
        try {
            SQLiteDatabase  mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            result = "";
            String selectQuery = "SELECT MASTER_TERMINAL_ID FROM terminal_configuration";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {

                result= cursor.getString(0);
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getTerminal_ID: "+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

}