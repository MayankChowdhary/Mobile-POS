package com.retailstreet.mobilepos.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.retailstreet.mobilepos.Model.ProductMaster;
import com.retailstreet.mobilepos.Model.ProductMasterSync;
import com.retailstreet.mobilepos.Utils.IDGenerator;
import com.retailstreet.mobilepos.Utils.WorkManagerSync;
import com.retailstreet.mobilepos.View.ApplicationContextProvider;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.retailstreet.mobilepos.Utils.Constants.DBNAME;

public class ControllerProductMaster {

    Context context;

    String ACTIVE;
    String BARCODE;
    String CATEGORY;
    String CATEGORY_GUID;
    String CESS1;
    String CESS2;
    String CGST;
    String EXTERNALPRODUCTID;
    String GENERIC_NAME;
    String GST;
    String HSN;
    String IGST;
    String ITEM_CODE;
    String ITEM_GUID;
    String Item_Type;
    String MASTERBRAND;
    String MASTERCATEGORY_id;
    String POS_USER;
    String PRINT_NAME;
    String PROD_ID;
    String PROD_NM;
    String PRODUCTRELEVANCE;
    String SGST;
    String STORE_ID;
    String STORE_NUMBER;
    String SUB_CATEGORYGUID;
    String SUBCATEGORY_DESCRIPTION;
    String SUBCATEGORY_ID;
    String UOM;
    String UOM_GUID;
    String UoMID;
    String ISSYNCED;
    String ISPRODUCTRETURNABLE;
    String ISLOOSEITEM;
    String ADDITIONALPARAM1;
    String ADDITIONALPARAM2;
    String ADDITIONALPARAM3;

    String userGuid;

    public  ControllerProductMaster(){
        context = ApplicationContextProvider.getContext();

    }

    public ControllerProductMaster(String qty, String batch_no, String internetPrice, String sPrice, String mrp,String minQty, String MaxQty, String wholePrice, String specPrice, String expDate, String vendor_nm, String vendorGuid,String prodName, String brandName,String p_price, String category, String subCategory, String barcode, String categoryGuid, String cess1, String cess2, String cgst, String extProID, String hsn, String igst, String sgst, String subCategoryGuid, String uom, String uomGuid, String isProdReturn, String isLoose){

        context = ApplicationContextProvider.getContext();
        ACTIVE="1";
        BARCODE= barcode;
        CATEGORY = category;
        CATEGORY_GUID = categoryGuid;
        CESS1 = cess1;
        CESS2 = cess2;
        CGST = cgst;
        SGST = sgst;
        GST = String.valueOf(Double.parseDouble(cgst)+ Double.parseDouble(sgst));
        EXTERNALPRODUCTID = extProID;
        GENERIC_NAME = " ";
        HSN = hsn;
        IGST = igst;
        ITEM_CODE = extProID;
        Item_Type = "Local";
        MASTERBRAND = brandName;
        ITEM_GUID = IDGenerator.getUUID();
        MASTERCATEGORY_id = getFromCategoryMaster(categoryGuid,"CATEGORYID");
        POS_USER = getFromGroupUserMaster("USER_GUID");
        PRINT_NAME = " ";
        PROD_ID = IDGenerator.getTimeStamp();
        PROD_NM = prodName;
        PRODUCTRELEVANCE =" ";
        STORE_ID = getFromRetailStore("STORE_ID");
        STORE_NUMBER = getFromRetailStore("STORE_NUMBER");
        SUB_CATEGORYGUID= subCategoryGuid;
        SUBCATEGORY_DESCRIPTION = subCategory;
        SUBCATEGORY_ID =getFromSubCategoryMaster(subCategoryGuid,"SUB_CATEGORYID");
        UOM = uom;
        UOM_GUID = uomGuid;
        UoMID = getFromUomMaster(uomGuid,"UoMID");
        ISSYNCED = "0";
        userGuid= getFromGroupUserMaster("USER_GUID");
        ISPRODUCTRETURNABLE = isProdReturn;
        ISLOOSEITEM = isLoose;
        ADDITIONALPARAM1 = "YES";
        ADDITIONALPARAM2 ="YES";
        ADDITIONALPARAM3 = "YES";

        ProductMaster productMaster= new ProductMaster( ACTIVE,  BARCODE,  CATEGORY,  CATEGORY_GUID,  CESS1,  CESS2,  CGST,  EXTERNALPRODUCTID,  GENERIC_NAME,  GST,  HSN,  IGST,  ITEM_CODE,  ITEM_GUID,  Item_Type,  MASTERBRAND,  MASTERCATEGORY_id,  POS_USER,  PRINT_NAME,  PROD_ID,  PROD_NM,  PRODUCTRELEVANCE,  SGST,  STORE_ID,  STORE_NUMBER,  SUB_CATEGORYGUID,  SUBCATEGORY_DESCRIPTION,  SUBCATEGORY_ID,  UOM,  UOM_GUID,  UoMID,ISSYNCED, ISPRODUCTRETURNABLE, ISLOOSEITEM, ADDITIONALPARAM1, ADDITIONALPARAM2, ADDITIONALPARAM3) ;
        InsertProductMaster(productMaster);
        new ControllerStockMaster(context).InjectIntoStockMaster(vendor_nm,vendorGuid,  userGuid,  ITEM_CODE, PROD_NM, expDate, CESS1, CESS2, GST, SGST, IGST, CGST, EXTERNALPRODUCTID, GENERIC_NAME, specPrice, wholePrice,  minQty,  MaxQty, internetPrice, sPrice, mrp, p_price, BARCODE, batch_no,  UOM, ITEM_GUID, qty, UOM_GUID);

        try {
            new WorkManagerSync(3);
        } catch (Exception e) {
            e.printStackTrace();
        }

        }

    public void InsertProductMaster(ProductMaster prod) {
        try {

            SQLiteDatabase myDataBase = context.openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE, null);
                ContentValues contentValues = new ContentValues();
                contentValues.put("BARCODE",prod.getBARCODE());
                contentValues.put("ACTIVE",prod.getACTIVE());
                contentValues.put("CESS1",prod.getCESS1());
                contentValues.put("CESS2",prod.getCESS2());
                contentValues.put("CGST",prod.getCGST());
                contentValues.put("CATEGORY",prod.getCATEGORY());
                contentValues.put("EXTERNALPRODUCTID",prod.getEXTERNALPRODUCTID());
                contentValues.put("CATEGORY_GUID",prod.getCATEGORY_GUID());
                contentValues.put("GST",prod.getGST());
                contentValues.put("IGST",prod.getIGST());
                contentValues.put("GENERIC_NAME",prod.getGENERIC_NAME());
                contentValues.put("ITEM_GUID",prod.getITEM_GUID());
                contentValues.put("HSN",prod.getHSN());
                contentValues.put("ITEM_CODE",prod.getITEM_CODE());
                contentValues.put("Item_Type",prod.getItem_Type());
                contentValues.put("MASTERBRAND",prod.getMASTERBRAND());
                contentValues.put("MASTERCATEGORY_id",prod.getMASTERCATEGORY_id());
                contentValues.put("SGST",prod.getSGST());
                contentValues.put("POS_USER",prod.getPOS_USER());
                contentValues.put("PROD_ID",prod.getPROD_ID());
                contentValues.put("PROD_NM",prod.getPROD_NM());
                contentValues.put("PRODUCTRELEVANCE",prod.getPRODUCTRELEVANCE());
                contentValues.put("PRINT_NAME",prod.getPRINT_NAME());
                contentValues.put("STORE_ID",prod.getSTORE_ID());
                contentValues.put("STORE_NUMBER",prod.getSTORE_NUMBER());
                contentValues.put("SUB_CATEGORYGUID",prod.getSUB_CATEGORYGUID());
                contentValues.put("SUBCATEGORY_DESCRIPTION",prod.getSUBCATEGORY_DESCRIPTION());
                contentValues.put("SUBCATEGORY_ID",prod.getSUBCATEGORY_ID());
                contentValues.put("UOM",prod.getUOM());
                contentValues.put("UOM_GUID",prod.getUOM_GUID());
                contentValues.put("UoMID",prod.getUoMID());
                contentValues.put("ISSYNCED",prod.getISSYNCED());
                contentValues.put("ISPRODUCTRETURNABLE",prod.getISPRODUCTRETURNABLE());
                contentValues.put("ISLOOSEITEM",prod.getISLOOSEITEM());
                contentValues.put("ADDITIONALPARAM1",prod.getADDITIONALPARAM1());
                contentValues.put("ADDITIONALPARAM2",prod.getADDITIONALPARAM2());
                contentValues.put("ADDITIONALPARAM3",prod.getADDITIONALPARAM3());
                myDataBase.insert("retail_store_prod_com", null, contentValues);
            myDataBase.close();
            Log.d("Insertion Successful", "InsertProductMaster: ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private String getFromCategoryMaster( String catGuid, String column) {

        String result = " ";
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            result = "";
            String selectQuery = "SELECT "+column+" FROM master_category where CATEGORY_GUID ='"+catGuid+"'";
            Cursor cursor = mydb.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {

                result = cursor.getString(0);
            }
            cursor.close();
            Log.d("DataRetrieved", "getFromCategoryMaster: "+column+":"+result);
            mydb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private String getCurrentUseName(){
        SharedPreferences sh = context.getSharedPreferences("com.retailstreet.mobilepos", MODE_PRIVATE);
        String username = sh.getString("username", "");
        return username;
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

    private String getFromSubCategoryMaster( String subCatGuid, String column) {

        String result = " ";
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            result = "";
            String selectQuery = "SELECT "+column+" FROM master_subcategory where SUB_CATEGORYGUID ='"+subCatGuid+"'";
            Cursor cursor = mydb.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {

                result = cursor.getString(0);
            }
            cursor.close();
            Log.d("DataRetrieved", "getSubCategoryMaster: "+column+":"+result);
            mydb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private String getFromUomMaster( String uomGuid, String column) {

        String result = " ";
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            result = "";
            String selectQuery = "SELECT "+column+" FROM master_uom where UOM_GUID ='"+uomGuid+"'";
            Cursor cursor = mydb.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {

                result = cursor.getString(0);
            }
            cursor.close();
            Log.d("DataRetrieved", "getUomMaster: "+column+":"+result);
            mydb.close();
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

    public ArrayList<ProductMasterSync> getProductSyncdata() {
        ArrayList<ProductMasterSync> productlist = new ArrayList<ProductMasterSync>();
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            Cursor productcursor = mydb.rawQuery(" select a.PROD_ID,a.CATEGORY_GUID,a.SUB_CATEGORYGUID,a.ITEM_GUID," +
                    "a.ITEM_CODE,a.PROD_NM,a.PRINT_NAME,a.BARCODE,a.HSN,a.GST,a.ACTIVE,a.STORE_ID,a.POS_USER," +
                    "a.PRODUCTRELEVANCE,a.GENERIC_NAME,a.EXTERNALPRODUCTID,a.MASTERBRAND,b.UOM_GUID, a.ISPRODUCTRETURNABLE, a.ISLOOSEITEM, a.ADDITIONALPARAM1, a.ADDITIONALPARAM2, a.ADDITIONALPARAM3 from retail_store_prod_com a LEFt JOIN master_uom b ON a.UoMID=b.UoMID where ISSYNCED = '0'  ", null);
            if (productcursor.moveToFirst()) {
                do {

                    ProductMasterSync pm = new ProductMasterSync();
                    pm.setITEMID(productcursor.getString(productcursor.getColumnIndex("PROD_ID")));
                    pm.setCATEGORY_GUID(productcursor.getString(productcursor.getColumnIndex("CATEGORY_GUID")));
                    pm.setSUBCATEGORY_GUID(productcursor.getString(productcursor.getColumnIndex("SUB_CATEGORYGUID")));
                    pm.setITEM_GUID(productcursor.getString(productcursor.getColumnIndex("ITEM_GUID")));
                    pm.setITEM_CODE(productcursor.getString(productcursor.getColumnIndex("ITEM_CODE")));
                    pm.setITEM_NAME(productcursor.getString(productcursor.getColumnIndex("PROD_NM")));
                    pm.setPRINT_NAME(productcursor.getString(productcursor.getColumnIndex("PRINT_NAME")));

                    if (productcursor.getString(productcursor.getColumnIndex("BARCODE")).endsWith("\n")) {
                        int barcodelength = productcursor.getString(productcursor.getColumnIndex("BARCODE")).length();
                        if (barcodelength > 1)
                            pm.setBARCODE(productcursor.getString(productcursor.getColumnIndex("BARCODE")).replaceAll("[\n\r]$", ""));
                        else
                            pm.setBARCODE(productcursor.getString(productcursor.getColumnIndex("BARCODE")));
                    } else {
                        pm.setBARCODE(productcursor.getString(productcursor.getColumnIndex("BARCODE")));
                    }

                    pm.setHSN(productcursor.getString(productcursor.getColumnIndex("HSN")));
                    pm.setGST(productcursor.getString(productcursor.getColumnIndex("GST")));
                    pm.setUOMID(productcursor.getString(productcursor.getColumnIndex("UOM_GUID")));
                    // pm.setISACTIVE(productcursor.getString(productcursor.getColumnIndex("ACTIVE")));
                    pm.setSTORE_GUID(getFromRetailStore("STORE_GUID"));
                    pm.setUSER_GUID(productcursor.getString(productcursor.getColumnIndex("POS_USER")));//need to pass userguid
                    pm.setPRODUCTRELEVANCE(productcursor.getString(productcursor.getColumnIndex("PRODUCTRELEVANCE")));
                    pm.setGENERICNAME(productcursor.getString(productcursor.getColumnIndex("GENERIC_NAME")));
                    pm.setEXTERNALPRODUCTID(productcursor.getString(productcursor.getColumnIndex("EXTERNALPRODUCTID")));
                    pm.setMASTERBRAND(productcursor.getString(productcursor.getColumnIndex("MASTERBRAND")));
                    pm.setISACTIVE("1");
                    pm.setISPRODUCTRETURNABLE(productcursor.getString(productcursor.getColumnIndex("ISPRODUCTRETURNABLE")));
                    pm.setISLOOSEITEM(productcursor.getString(productcursor.getColumnIndex("ISLOOSEITEM")));
                    pm.setADDITIONALPARAM1(productcursor.getString(productcursor.getColumnIndex("ADDITIONALPARAM1")));
                    pm.setADDITIONALPARAM2(productcursor.getString(productcursor.getColumnIndex("ADDITIONALPARAM2")));
                    pm.setADDITIONALPARAM3(productcursor.getString(productcursor.getColumnIndex("ADDITIONALPARAM3")));
                    productlist.add(pm);
                } while (productcursor.moveToNext());
            }

            productcursor.close();
            mydb.close();
        } catch (IndexOutOfBoundsException cur) {
            cur.printStackTrace();
        }
        return productlist;
    }

    public Boolean updateintoProduct(String ITEM_GUID) {
        boolean returnval = true;
        SQLiteDatabase db  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
        ContentValues value = new ContentValues();
        value.put("ISSYNCED", "1");
        int sqlUpdateRetval = db.update("retail_store_prod_com", value,
                " PROD_ID = ? "
                , new String[]{String.format(ITEM_GUID)});

        if (sqlUpdateRetval < 1) {
            returnval = false;
        }
        db.close();

        return returnval;
    }


}
