package com.retailstreet.mobilepos.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.retailstreet.mobilepos.Model.GRNDetails;
import com.retailstreet.mobilepos.Model.GRNMaster;
import com.retailstreet.mobilepos.Model.StockRegister;
import com.retailstreet.mobilepos.Utils.IDGenerator;
import com.retailstreet.mobilepos.Utils.WorkManagerSync;
import com.retailstreet.mobilepos.View.ApplicationContextProvider;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;
import static com.retailstreet.mobilepos.Utils.Constants.DBNAME;
/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */
public class ControllerPurchaseInvoice {
    Context context;
    HashMap<String, String> purchseLIst = new HashMap<>();
    String GRNDETAILID;
    String GRN_QTY;
    String BATCHNO;
    String EXP_DATE;
    String PUR_PRICE;
    String TAX_AMOUNT;
    String GRN_VALUE;
    String MRP;
    String ISFREEGOODS;
    String FREE_QUANTITY;
    String PURCHASEDISCOUNTPERCENTAGE;
    String PURCHASEDISCOUNTBYAMOUNT;
    String GRN_GUID;
    String ITEM_GUID;
    String STORE_GUID;
    String UOM_GUID;
    String GRNDETAILGUID;


    String GRNNO;
    String GRNDate;
    String GRANDAMOUNT;
    String INVOICENO;
    String INVOICEDATE;
    String INVOICEDISCOUNT;
    String GRNPRINT;
    String GRNRECON;
    String GRN_STATUS;
    String CREATEDBY;
    String GRNTYPE;
    String USER_GUID;
    String VENDOR_GUID;
    String ISSYNCED;


    String REGISTERGUID;
    String MASTERORG_GUID;
    // String STORE_GUID;
    // String VENDOR_GUID;
    String LINETYPE;
    String TRANSACTIONTYPE;
    String TRANSACTIONNUMBER;
    String TRANSACTIONDATE;
    // String ITEM_GUID;
    // String UOM_GUID;
    String QUANTITY;
    // String BATCHNO;
    String BARCODE;
    String SALESPRICE;
    String WHOLESALEPRICE;
    String INTERNETPRICE;
    String SPECIALPRICE;
    // String ISSYNCED;
    String MASTER_TERMINAL_ID;



    public ControllerPurchaseInvoice() {
        context = ApplicationContextProvider.getContext();
        MASTER_TERMINAL_ID = getTerminal_ID();

    }

    public ControllerPurchaseInvoice(String Invoicedate, String grandTotal, String invoiceNo, String vendorGuid,String vendorName) {
        context = ApplicationContextProvider.getContext();
        MASTER_TERMINAL_ID=getTerminal_ID();
        GRN_GUID = IDGenerator.getUUID();
        GRNNO = IDGenerator.getTimeStamp();
        INVOICEDISCOUNT = "0.00";
        PURCHASEDISCOUNTBYAMOUNT = "0.00";
        STORE_GUID = getFromRetailStore("STORE_GUID");
        VENDOR_GUID = vendorGuid;
        GRNDate = getGrnDateAndTime();

        initMap();

        for (String key : purchseLIst.keySet()) {
            String count = Objects.requireNonNull(purchseLIst.get(key));
            generateGRNDetails(key, count,vendorName);
            generateStockRegister(key, count);

        }


        generateGRNMaster(Invoicedate, grandTotal, invoiceNo);

        try {
            new WorkManagerSync(9);
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




    private void generateGRNDetails(String itemGuid, String totalCount,String vendorname) {

        try {
            GRNDETAILID = IDGenerator.getTimeStamp();
            GRNDETAILGUID = IDGenerator.getUUID();
            GRN_QTY = getFromTempPurchase(itemGuid, "QTY");
            BATCHNO = getFromStockMaster(itemGuid, "BATCH_NO");
            EXP_DATE = getFromTempPurchase(itemGuid, "EXP_DATE");
            PUR_PRICE = getFromTempPurchase(itemGuid, "P_PRICE");
            TAX_AMOUNT = getFromTempPurchase(itemGuid, "TAX");
            GRN_VALUE = String.valueOf(Double.parseDouble(PUR_PRICE) * Double.parseDouble(getFromTempPurchase(itemGuid, "QTY")));
            MRP = getFromTempPurchase(itemGuid, "MRP");
            ISFREEGOODS = "0";
            FREE_QUANTITY = getFromTempPurchase(itemGuid, "FQTY");
            if (Double.parseDouble(FREE_QUANTITY) > 0) {
                ISFREEGOODS = "1";
            }

            PURCHASEDISCOUNTBYAMOUNT = String.valueOf(Double.parseDouble(getFromTempPurchase(itemGuid, "DISC")));
            INVOICEDISCOUNT = String.valueOf(Double.parseDouble(INVOICEDISCOUNT) + Double.parseDouble(PURCHASEDISCOUNTBYAMOUNT));
            PURCHASEDISCOUNTPERCENTAGE = new DecimalFormat("###.##").format((100 * Double.parseDouble(PURCHASEDISCOUNTBYAMOUNT)) / Double.parseDouble(GRN_VALUE));
            ITEM_GUID = itemGuid;

            UOM_GUID = getFromProductMaster(itemGuid, "UOM_GUID");

            GRNDetails grnDetails = new GRNDetails(GRNDETAILID, GRN_QTY, BATCHNO, EXP_DATE, PUR_PRICE, TAX_AMOUNT, GRN_VALUE, MRP, ISFREEGOODS, FREE_QUANTITY, PURCHASEDISCOUNTPERCENTAGE, PURCHASEDISCOUNTBYAMOUNT, GRN_GUID, ITEM_GUID, STORE_GUID, UOM_GUID, GRNDETAILGUID,MASTER_TERMINAL_ID);
            InsertGrnDetails(grnDetails);
            generateStockMaster(itemGuid,totalCount,vendorname,GRNDETAILGUID);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void generateGRNMaster(String Invoicedate, String GrandToatl, String invoiceNo) {
        try {
            INVOICEDATE = Invoicedate + " 00:00:00.0";
            GRANDAMOUNT = GrandToatl;
            INVOICENO = invoiceNo;
            GRNPRINT = "N";
            GRNRECON = "Y";
            GRN_STATUS = "1";
            CREATEDBY = getFromGroupUserMaster("USER_NAME");
            GRNTYPE = "MOB";
            USER_GUID = getFromGroupUserMaster("USER_GUID");
            ISSYNCED = "0";

            GRNMaster grnMaster = new GRNMaster(GRN_GUID, GRNNO, GRNDate, GRANDAMOUNT, INVOICENO, INVOICEDATE, INVOICEDISCOUNT, GRNPRINT, GRNRECON, GRN_STATUS, CREATEDBY, GRNTYPE, USER_GUID, VENDOR_GUID, STORE_GUID, ISSYNCED,MASTER_TERMINAL_ID);
            InsertGrnMasters(grnMaster);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateStockRegister(String itemGuid, String totalCount) {
        try {
            REGISTERGUID = IDGenerator.getUUID();
            MASTERORG_GUID = getFromRetailStore("MASTERORG_GUID");
            LINETYPE = "CREDIT";
            TRANSACTIONTYPE = "GRN";
            TRANSACTIONNUMBER = GRN_GUID;
            TRANSACTIONDATE = GRNDate;
            ITEM_GUID = itemGuid;
            UOM_GUID = getFromProductMaster(itemGuid, "UOM_GUID");
            QUANTITY = totalCount;
            BATCHNO = getFromStockMaster(itemGuid, "BATCH_NO");
            BARCODE = getFromTempPurchase(itemGuid, "BARCODE");
            SALESPRICE = getFromTempPurchase(itemGuid, "S_PRICE");
            WHOLESALEPRICE = getFromStockMaster(itemGuid, "WHOLE_SPRICE");
            INTERNETPRICE = getFromStockMaster(itemGuid, "INTERNET_PRICE");
            SPECIALPRICE = getFromStockMaster(itemGuid, "SPEC_PRICE");
            ISSYNCED = "0";

            StockRegister stockRegister = new StockRegister(REGISTERGUID, MASTERORG_GUID, STORE_GUID, VENDOR_GUID, LINETYPE, TRANSACTIONTYPE, TRANSACTIONNUMBER, TRANSACTIONDATE, ITEM_GUID, UOM_GUID, QUANTITY, BATCHNO, BARCODE, SALESPRICE, WHOLESALEPRICE, INTERNETPRICE, SPECIALPRICE, ISSYNCED,MASTER_TERMINAL_ID);
            InsertStockRegister(stockRegister);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateStockMaster(String itemGuid, String totalCount,String vendor_name,String grnDetailGuid) {

        try {
            BARCODE = getFromTempPurchase(itemGuid, "BARCODE");
            MRP = getFromTempPurchase(itemGuid, "MRP");
            String QTY = totalCount;
            String  SALE_UOMID  = getFromProductMaster(itemGuid,"UOM_GUID");
            String  UOM = getFromProductMaster(itemGuid,"UOM");
            String  BATCH_NO = getFromStockMaster(itemGuid,"BATCH_NO");
            String DiscAmount = getFromTempPurchase(itemGuid,"DISC");
            String  P_PRICE = getFromTempPurchase(itemGuid, "P_PRICE");
            String  S_PRICE = getFromTempPurchase(itemGuid, "MRP");
            String DiscByPer = new DecimalFormat("#.##").format((Double.parseDouble(DiscAmount)/ Double.parseDouble(S_PRICE))*100);

            String  INTERNET_PRICE  = getFromStockMaster(itemGuid,"INTERNET_PRICE");
            if(INTERNET_PRICE.trim().isEmpty()){
                INTERNET_PRICE = S_PRICE;
            }
            String MIN_QUANTITY = getFromStockMaster(itemGuid,"MIN_QUANTITY");
            if(MIN_QUANTITY.trim().isEmpty()){
                MIN_QUANTITY = "0.00";
            }
            String MAX_QUANTITY = getFromStockMaster(itemGuid,"MAX_QUANTITY");
            if(MAX_QUANTITY.trim().isEmpty()){
                MAX_QUANTITY = "0.00";
            }

            String WHOLE_SPRICE = getFromStockMaster(itemGuid,"WHOLE_SPRICE");

            if(WHOLE_SPRICE.trim().isEmpty()){
                WHOLE_SPRICE = S_PRICE;
            }
            String SPEC_PRICE = getFromStockMaster(itemGuid,"SPEC_PRICE");
            if(SPEC_PRICE.trim().isEmpty()){
                SPEC_PRICE = S_PRICE;
            }
            String GENERIC_NAME = getFromStockMaster(itemGuid,"GENERIC_NAME");
            String  EXTERNALPRODUCTID = getFromTempPurchase(itemGuid, "EXTERNALPRODUCTID");
            String  GST =  getFromProductMaster(itemGuid,"GST");
            String SGST= getFromProductMaster(itemGuid,"SGST");
            String IGST = getFromProductMaster(itemGuid,"IGST");
            String CGST = getFromProductMaster(itemGuid,"CGST");

            String  CESS1 =getFromProductMaster(itemGuid,"CESS1");
            String  CESS2 = getFromProductMaster(itemGuid,"CESS2");
            EXP_DATE = getFromTempPurchase(itemGuid, "EXP_DATE");
            String  PROD_NM = getFromTempPurchase(itemGuid, "PROD_NM");
            String  ITEM_CODE = getFromProductMaster(itemGuid,"ITEM_CODE");
            String USER_GUID= getFromGroupUserMaster("USER_GUID");
            ISSYNCED ="0";
            GRNDETAILGUID = grnDetailGuid;

            String stockID = isNewStockItem(itemGuid,VENDOR_GUID,BARCODE,MRP,PROD_NM).trim();

            if(stockID.isEmpty())
            {
                new ControllerStockMaster(context).InjectIntoStockMasterFromPurcaheInvoice(vendor_name, VENDOR_GUID, USER_GUID, ITEM_CODE, PROD_NM,EXP_DATE, CESS1, CESS2, GST, SGST, IGST, CGST, EXTERNALPRODUCTID, GENERIC_NAME, SPEC_PRICE, WHOLE_SPRICE,MIN_QUANTITY, MAX_QUANTITY, INTERNET_PRICE, S_PRICE, MRP, P_PRICE,BARCODE, BATCH_NO, UOM, itemGuid, QTY, SALE_UOMID,GRNDETAILGUID,GRN_GUID,GRNNO,DiscByPer,DiscAmount);
            }else{
                String oldQty = getFromStockMaster1(stockID,"QTY" );
                String newQty = String.valueOf(Double.parseDouble(oldQty)+Double.parseDouble(QTY));
                new ControllerStockMaster(context).updateStockMasterFromPurchaseInvoice(stockID,VENDOR_GUID,vendor_name,PROD_NM,EXTERNALPRODUCTID,BARCODE,EXP_DATE,MRP,S_PRICE,P_PRICE,newQty,CGST,SGST,USER_GUID,GRNDETAILGUID,GRN_GUID,GRNNO,DiscByPer,DiscAmount);
            }
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

    public void InsertGrnMasters(GRNMaster prod) {

        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE, null);
            ContentValues contentValues = new ContentValues();
            contentValues.put("GRN_GUID", prod.getGRN_GUID());
            contentValues.put("GRNNO", prod.getGRNNO());
            contentValues.put("GRNDate", prod.getGRNDate());
            contentValues.put("GRANDAMOUNT", prod.getGRANDAMOUNT());
            contentValues.put("INVOICENO", prod.getINVOICENO());
            contentValues.put("INVOICEDATE", prod.getINVOICEDATE());
            contentValues.put("INVOICEDISCOUNT", prod.getINVOICEDISCOUNT());
            contentValues.put("GRNPRINT", prod.getGRNPRINT());
            contentValues.put("GRNRECON", prod.getGRNRECON());
            contentValues.put("GRN_STATUS", prod.getGRN_STATUS());
            contentValues.put("CREATEDBY", prod.getCREATEDON());
            contentValues.put("GRNTYPE", prod.getGRNTYPE());
            contentValues.put("USER_GUID", prod.getUSER_GUID());
            contentValues.put("VENDOR_GUID", prod.getVENDOR_GUID());
            contentValues.put("STORE_GUID", prod.getSTORE_GUID());
            contentValues.put("ISSYNCED", prod.getISSYNCED());
            contentValues.put("MASTER_TERMINAL_ID", prod.getMASTER_TERMINAL_ID());
            myDataBase.insert("retail_str_grn_master", null, contentValues);

            myDataBase.close();

            Log.d("Insertion Successful", "GRNMaster: ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getFromGroupUserMaster(String column) {
        String result = null;
        try {
            SQLiteDatabase mydb = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String username;
            SharedPreferences sh = context.getSharedPreferences("com.retailstreet.mobilepos", MODE_PRIVATE);
            username = sh.getString("username", "");

            result = "";
            String selectQuery = "SELECT " + column + " FROM group_user_master where USER_NAME ='" + username + "'";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {

                result = cursor.getString(0);
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getFromUserMaster: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    private String getGrnDateAndTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return formatter.format(date);
    }


    public void InsertGrnDetails(GRNDetails prod) {
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE, null);
            ContentValues contentValues = new ContentValues();
            contentValues.put("GRNDETAILID", prod.getGRNDETAILID());
            contentValues.put("GRN_QTY", prod.getGRN_QTY());
            contentValues.put("BATCHNO", prod.getBATCHNO());
            contentValues.put("EXP_DATE", prod.getEXP_DATE());
            contentValues.put("PUR_PRICE", prod.getPUR_PRICE());
            contentValues.put("TAX_AMOUNT", prod.getTAX_AMOUNT());
            contentValues.put("GRN_VALUE", prod.getGRN_VALUE());
            contentValues.put("MRP", prod.getMRP());
            contentValues.put("ISFREEGOODS", prod.getISFREEGOODS());
            contentValues.put("FREE_QUANTITY", prod.getFREE_QUANTITY());
            contentValues.put("PURCHASEDISCOUNTPERCENTAGE", prod.getPURCHASEDISCOUNTPERCENTAGE());
            contentValues.put("PURCHASEDISCOUNTBYAMOUNT", prod.getPURCHASEDISCOUNTBYAMOUNT());
            contentValues.put("GRN_GUID", prod.getGRN_GUID());
            contentValues.put("ITEM_GUID", prod.getITEM_GUID());
            contentValues.put("STORE_GUID", prod.getSTORE_GUID());
            contentValues.put("UOM_GUID", prod.getUOM_GUID());
            contentValues.put("GRNDETAILGUID", prod.getGRNDETAILGUID());
            contentValues.put("MASTER_TERMINAL_ID", prod.getMASTER_TERMINAL_ID());
            myDataBase.insert("retail_str_grn_detail", null, contentValues);
            myDataBase.close();

            Log.d("Insertion Successful", "GRNDetails: ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String getFromProductMaster(String itemGuid, String column) {

        String result = null;
        try {
            SQLiteDatabase mydb = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            result = "";
            String selectQuery = "SELECT " + column + " FROM retail_store_prod_com where ITEM_GUID ='" + itemGuid + "'";
            Cursor cursor = mydb.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {

                result = cursor.getString(0);
            }
            cursor.close();
            Log.d("DataRetrieved", "getFromProductMaster: " + column + ":" + result);
            mydb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    private String getFromRetailStore(String column) {
        String result = null;
        try {
            SQLiteDatabase mydb = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            result = "";
            String selectQuery = "SELECT " + column + " FROM retail_store ";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {

                result = cursor.getString(0);
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getFromRetailStore: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    private String getFromStockMaster(String itemGuid, String column) {
        String result = "";
        try {
            SQLiteDatabase mydb = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String selectQuery = "SELECT " + column + " FROM retail_str_stock_master where ITEM_GUID = '" + itemGuid +"'";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {

                result = cursor.getString(0);
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getFromStockMaster: " + column + ": " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private String getFromStockMaster1(String stockid, String column) {
        String result = "";
        try {
            SQLiteDatabase mydb = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String selectQuery = "SELECT " + column + " FROM retail_str_stock_master where STOCK_ID = '"+stockid+"'";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {

                result = cursor.getString(0);
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getFromStockMaster: " + column + ": " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private String getFromTempPurchase(String itemGuid, String column) {
        String result = null;
        try {
            SQLiteDatabase mydb = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            result = "";
            String selectQuery = "SELECT " + column + " FROM tmp_purchase_invoice where ITEM_GUID ='" + itemGuid + "'";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {

                result = cursor.getString(0);
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getFromTemPurchase: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }



    private void initMap() {
        try {
            purchseLIst.clear();
            SQLiteDatabase mydb = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            Cursor cursor = mydb.rawQuery("SELECT ITEM_GUID,QTY,FQTY FROM tmp_purchase_invoice", null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    String id = cursor.getString(0);
                    String cnt = String.valueOf(cursor.getInt(1) + cursor.getInt(2));
                    purchseLIst.put(id, cnt);
                    cursor.moveToNext();
                }
            }
            cursor.close();
            mydb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String  isNewStockItem(String guid,String VENDOR, String BARCODE, String MRP, String PROD) {
        String isNewItem = "";

        try {
            SQLiteDatabase mydb = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            Cursor cursor = mydb.rawQuery("SELECT VENDOR_NAME,BARCODE,MRP,PROD_NM,STOCK_ID FROM retail_str_stock_master WHERE ITEM_GUID= '" + guid + "'", null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    String vendor = cursor.getString(0);
                    String barcode = cursor.getString(1);
                    String mrp = cursor.getString(2);
                    String prod = cursor.getString(3);
                    String stockid = cursor.getString(4);

                    Log.d("PrintingStockMatcher", "Fetched: Vendor: "+vendor+" Barcode: "+barcode+" MRP: "+mrp+" Prod: "+prod);

                    if(VENDOR.equals(vendor) && BARCODE.equals(barcode) && MRP.equals(mrp) && PROD.equals(prod))
                    {
                        Log.d("StockMatcher", "StockMatched!: ");
                        isNewItem=stockid;
                    }

                    cursor.moveToNext();
                }
            }
            cursor.close();
            mydb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isNewItem;
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
