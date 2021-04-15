package com.retailstreet.mobilepos.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.retailstreet.mobilepos.Model.CustomerReturnDetails;
import com.retailstreet.mobilepos.Model.CustomerReturnMaster;
import com.retailstreet.mobilepos.Model.SalesReturnManage;
import com.retailstreet.mobilepos.Utils.IDGenerator;
import com.retailstreet.mobilepos.Utils.WorkManagerSync;
import com.retailstreet.mobilepos.View.ApplicationContextProvider;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class ControllerCustomerReturn {

    Context context;
    HashMap<String, String> returnList = new HashMap<>();

    String CUSTOMER_RETURNS_DETAILID;
    String CUSTOMER_RETURNS_MASTER_ID;
    String MASTER_PRODUCT_ITEM_ID;
    String BATCHNO;
    String RETURNQUANTITY;
    String EXPIRYDATE;
    String CUSTOMER_RETURN_DETAIL_STATUS;


    String CUSTOMERRETURNGUID;
    String REASONGUID;
    String MASTERSTOREID;
    String CUSTOMERGUID;
    String BILLNO;
    String SALESDATE;
    String REASONDETAILS;
    String RETURN_DATE;
    String ISPARTIALRETURN;
    String AMOUNTREFUNDED;
    String REPLACEMENTBILLNO;
    String CUSTOMER_RETURNS_STATUS;
    String CREATEDBYGUID;
    String CREATEDON;
    String ISSYNCED;
    String CREDITNOTENUMBER;


    public ControllerCustomerReturn(){
        context = ApplicationContextProvider.getContext();

    }

    public ControllerCustomerReturn(String reasonGuid, String custGuid, String reasonDetail, String totalAmount, String creditNoteNum){
        context = ApplicationContextProvider.getContext();
        initMap();
        CUSTOMERRETURNGUID= IDGenerator.getUUID();

        for (String key:returnList.keySet()) {
            String num = Objects.requireNonNull(returnList.get(key));
            GenerateSalesReturnDetails(key,num);
            UpdateQuantity(num,key);
        }

        GenerateReturnMaster("",reasonGuid,custGuid,reasonDetail,totalAmount,creditNoteNum);


        try {
            new WorkManagerSync(7);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ControllerCustomerReturn(String billno, String reasonGuid, String custGuid, String reasonDetail, String totalAmount, String creditNoteNum){
        context = ApplicationContextProvider.getContext();
        initMap2();
        CUSTOMERRETURNGUID = IDGenerator.getUUID();

        for (String key:returnList.keySet()) {
            String num = Objects.requireNonNull(returnList.get(key));
            String stockId = getFromStockMaster2(key,"STOCK_ID");
            GenerateSalesReturnDetails(stockId,num);
            UpdateQuantity(num,stockId);
        }

        GenerateReturnMaster(billno,reasonGuid,custGuid,reasonDetail,totalAmount,creditNoteNum);

        try {
            new WorkManagerSync(7);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            new WorkManagerSync(4);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void GenerateSalesReturnDetails(String key,String count){

        CUSTOMER_RETURNS_DETAILID = IDGenerator.getTimeStamp();
        MASTER_PRODUCT_ITEM_ID = getFromStockMaster(key,"ITEM_GUID");
        BATCHNO = getFromStockMaster(key,"BATCH_NO");
        RETURNQUANTITY = count;
        EXPIRYDATE = getFromStockMaster(key,"EXP_DATE");
        CUSTOMER_RETURN_DETAIL_STATUS="1";
        CustomerReturnDetails customerReturnDetails = new CustomerReturnDetails( CUSTOMER_RETURNS_DETAILID,  CUSTOMERRETURNGUID,  MASTER_PRODUCT_ITEM_ID,  BATCHNO,  RETURNQUANTITY,  EXPIRYDATE, CUSTOMER_RETURN_DETAIL_STATUS);
        InsertCustomerReturnDetails(customerReturnDetails);
    }

    private void GenerateReturnMaster(String billno, String reasonGuid, String custGuid, String reason,String amount, String creditNoteNum){
        CUSTOMER_RETURNS_MASTER_ID= IDGenerator.getTimeStamp();
        REASONGUID = reasonGuid;
        MASTERSTOREID = getFromRetailStore("STORE_GUID");
        CUSTOMERGUID = custGuid;
        BILLNO = billno;
        SALESDATE = getCurrentDateAndTime();
        REASONDETAILS = reason;
        RETURN_DATE = getCurrentDateAndTime();
        ISPARTIALRETURN = "1";
        AMOUNTREFUNDED = amount;
        REPLACEMENTBILLNO = "";
        CUSTOMER_RETURNS_STATUS = "1";
        CREATEDBYGUID = getFromGroupUserMaster("USER_GUID");
        CREATEDON = getCurrentDateAndTime();
        ISSYNCED = "0";
        CREDITNOTENUMBER = creditNoteNum;

      CustomerReturnMaster customerReturnMaster = new CustomerReturnMaster( CUSTOMER_RETURNS_MASTER_ID, CUSTOMERRETURNGUID,  REASONGUID,  MASTERSTOREID,  CUSTOMERGUID,  BILLNO,  SALESDATE,  REASONDETAILS,  RETURN_DATE,  ISPARTIALRETURN,  AMOUNTREFUNDED,  REPLACEMENTBILLNO,  CUSTOMER_RETURNS_STATUS,  CREATEDBYGUID,  CREATEDON,  ISSYNCED,  CREDITNOTENUMBER);
        InsertCustomerReturnMaster(customerReturnMaster);
    }



    public void InsertCustomerReturnDetails(CustomerReturnDetails prod) {
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null);
                ContentValues contentValues = new ContentValues();
                contentValues.put("CUSTOMER_RETURNS_DETAILID", prod.getCUSTOMER_RETURNS_DETAILID());
                contentValues.put("CUSTOMER_RETURNS_MASTER_ID", prod.getCUSTOMER_RETURNS_MASTER_ID());
                contentValues.put("MASTER_PRODUCT_ITEM_ID", prod.getMASTER_PRODUCT_ITEM_ID());
                contentValues.put("BATCHNO", prod.getBATCHNO());
                contentValues.put("RETURNQUANTITY", prod.getRETURNQUANTITY());
                contentValues.put("EXPIRYDATE", prod.getEXPIRYDATE());
                contentValues.put("CUSTOMER_RETURN_DETAIL_STATUS", prod.getCUSTOMER_RETURN_DETAIL_STATUS());
                myDataBase.insert("customerReturnDetail", null, contentValues);

            myDataBase.close();
            Log.d("Insertion Successful", "InsertSalesReturnMaster: ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void InsertCustomerReturnMaster(CustomerReturnMaster prod) {
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null);
                ContentValues contentValues = new ContentValues();
                contentValues.put("CUSTOMER_RETURNS_MASTERID", prod.getCUSTOMER_RETURNS_MASTERID());
                contentValues.put("CUSTOMERRETURNGUID", prod.getCUSTOMERRETURNGUID());
                contentValues.put("REASONGUID", prod.getREASONGUID());
                contentValues.put("MASTERSTOREID", prod.getMASTERSTOREID());
                contentValues.put("CUSTOMERGUID", prod.getCUSTOMERGUID());
                contentValues.put("BILLNO", prod.getBILLNO());
                contentValues.put("SALESDATE", prod.getSALESDATE());
                contentValues.put("REASONDETAILS", prod.getREASONDETAILS());
                contentValues.put("RETURN_DATE", prod.getRETURN_DATE());
                contentValues.put("ISPARTIALRETURN", prod.getISPARTIALRETURN());
                contentValues.put("AMOUNTREFUNDED", prod.getAMOUNTREFUNDED());
                contentValues.put("REPLACEMENTBILLNO", prod.getREPLACEMENTBILLNO());
                contentValues.put("CUSTOMER_RETURNS_STATUS", prod.getCUSTOMER_RETURNS_STATUS());
                contentValues.put("CREATEDBYGUID", prod.getCREATEDBYGUID());
                contentValues.put("CREATEDON", prod.getCREATEDON());
                contentValues.put("ISSYNCED", prod.getISSYNCED());
                contentValues.put("CREDITNOTENUMBER", prod.getCREDITNOTENUMBER());

                myDataBase.insert("customerReturnMaster", null, contentValues);
            myDataBase.close();
            Log.d("Insertion Successful", "InsertcustomerReturnMaster: ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Cursor getSalesReturnCursor(String billno) {
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            mydb.execSQL("DROP TABLE IF EXISTS tmp_sales_return");
            mydb.execSQL("CREATE TABLE IF NOT EXISTS tmp_sales_return AS SELECT pm.PROD_NM, pm.BARCODE, pm.UOM, bd.QTY, bd.MRP, bd.TOTALVALUE,  bd.DISCOUNT_PERC, bd.ITEM_GUID, pm.ISPRODUCTRETURNABLE  FROM retail_store_prod_com pm INNER JOIN retail_str_sales_detail bd ON bd.ITEM_GUID = pm.ITEM_GUID WHERE bd.BILLNO = '"+billno+"'");
            mydb.execSQL("ALTER TABLE tmp_sales_return ADD COLUMN MAXQTY INTEGER DEFAULT 0");
            mydb.execSQL("DELETE FROM tmp_sales_return WHERE ISPRODUCTRETURNABLE= 'NO'");
            mydb.execSQL("UPDATE tmp_sales_return SET MAXQTY = QTY, MRP = TOTALVALUE/QTY ");

            Cursor cursor = mydb.rawQuery("SELECT * from tmp_sales_return",null);
            if (cursor.moveToFirst()) {
                Log.d("SalesReturnCursor", "getSalesReturnCursor: "+cursor.getString(0));
                return cursor;
            } else {
                Toast.makeText(context,"Bill Number Or Returnable Item Not Found!",Toast.LENGTH_LONG).show();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Cursor refreshSalesReturnCursor() {
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            Cursor cursor = mydb.rawQuery("SELECT * from tmp_sales_return",null);
            if (cursor.moveToFirst()) {
                Log.d("SalesReturnCursor", "getSalesReturnCursor: "+cursor.getString(0));
                return cursor;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public Cursor refreshNoBillCursor() {
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            Cursor cursor = mydb.rawQuery("SELECT * from tmp_sr_no_bill",null);
            if (cursor.moveToFirst()) {
                Log.d("SalesReturnCursor", "getSalesReturnCursor: "+cursor.getString(0));
                return cursor;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private String getFromStockMaster( String stockid,String column) {
        String result= null;
        try {
            SQLiteDatabase  mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            result = "";
            String selectQuery = "SELECT "+column+" FROM retail_str_stock_master where STOCK_ID ='"+stockid+"'";
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

    private String getFromStockMaster2( String itemGuid,String column) {
        String result= null;
        Log.d("DataInserted", "getFromStockMaster:"+itemGuid);
        try {

            SQLiteDatabase  mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            result = "";
            String selectQuery = "SELECT "+column+" FROM retail_str_stock_master where ITEM_GUID ='"+itemGuid+"'";
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


    private void initMap(){
        try {
            returnList.clear();
            SQLiteDatabase   mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            Cursor cursor  = mydb.rawQuery("SELECT * FROM tmp_sr_no_bill", null);
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

    private void initMap2(){
        try {
            returnList.clear();
            SQLiteDatabase   mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            Cursor cursor  = mydb.rawQuery("SELECT * FROM tmp_sales_return", null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    String id = cursor.getString(7);
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

    public void UpdateQuantity(String quantity, String stockID){
        try {

            String CurrentQty= getFromStockMaster(stockID,"QTY");
            CurrentQty = String.valueOf(Double.parseDouble(CurrentQty)+Double.parseDouble(quantity));
            String query = "Update retail_str_stock_master Set QTY = '"+CurrentQty+"', ISSYNCED = '0'  where STOCK_ID = '"+stockID+"'";
            SQLiteDatabase   db  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            db.execSQL(query);
            db.close();
            Log.d("UpdateStockQty", "UpdateQuantity: Successful"+quantity);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public ArrayList<CustomerReturnMaster> getSalesReturnMasterdata() {
        ArrayList<CustomerReturnMaster> productlist = new ArrayList<>();
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            Cursor productcursor = mydb.rawQuery(" select * from customerReturnMaster " +
                    "where ISSYNCED='0' ", null);
            if (productcursor.moveToFirst()) {
                do {
                    CustomerReturnMaster pm = new CustomerReturnMaster();

                    pm.setCUSTOMER_RETURNS_MASTERID(productcursor.getString(productcursor.getColumnIndex("CUSTOMER_RETURNS_MASTERID")));
                    pm.setCUSTOMERRETURNGUID(productcursor.getString(productcursor.getColumnIndex("CUSTOMERRETURNGUID")));
                    pm.setREASONGUID(productcursor.getString(productcursor.getColumnIndex("REASONGUID")));//
                    pm.setMASTERSTOREID(productcursor.getString(productcursor.getColumnIndex("MASTERSTOREID")));//
                    pm.setCUSTOMERGUID(productcursor.getString(productcursor.getColumnIndex("CUSTOMERGUID")));
                    pm.setBILLNO(productcursor.getString(productcursor.getColumnIndex("BILLNO")));
                    pm.setSALESDATE(productcursor.getString(productcursor.getColumnIndex("SALESDATE")));
                    pm.setREASONDETAILS(productcursor.getString(productcursor.getColumnIndex("REASONDETAILS")));
                    pm.setRETURN_DATE(productcursor.getString(productcursor.getColumnIndex("RETURN_DATE")));
                    pm.setISPARTIALRETURN(productcursor.getString(productcursor.getColumnIndex("ISPARTIALRETURN")));
                    pm.setAMOUNTREFUNDED(productcursor.getString(productcursor.getColumnIndex("AMOUNTREFUNDED")));
                    pm.setREPLACEMENTBILLNO(productcursor.getString(productcursor.getColumnIndex("REPLACEMENTBILLNO")));
                    pm.setCREDITNOTENUMBER(productcursor.getString(productcursor.getColumnIndex("CREDITNOTENUMBER")));
                    pm.setCUSTOMER_RETURNS_STATUS(productcursor.getString(productcursor.getColumnIndex("CUSTOMER_RETURNS_STATUS")));
                    pm.setCREATEDBYGUID(productcursor.getString(productcursor.getColumnIndex("CREATEDBYGUID")));
                    pm.setCREATEDON(productcursor.getString(productcursor.getColumnIndex("CREATEDON")));
                    productlist.add(pm);
                } while (productcursor.moveToNext());
            }

            mydb.close();
        } catch (IndexOutOfBoundsException cur) {
            cur.printStackTrace();
        }
        return productlist;
    }


    public ArrayList<CustomerReturnDetails> getSalereturnDetailsSyncdata(String masterid) {
        ArrayList<CustomerReturnDetails> productlist = new ArrayList<CustomerReturnDetails>();
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            Cursor productcursor = mydb.rawQuery(" select * from customerReturnDetail  where CUSTOMER_RETURNS_MASTER_ID='" + masterid + "' ", null);
            if (productcursor.moveToFirst()) {
                do {
                    CustomerReturnDetails pm = new CustomerReturnDetails();
                    pm.setCUSTOMER_RETURNS_DETAILID(productcursor.getString(productcursor.getColumnIndex("CUSTOMER_RETURNS_DETAILID")));
                    pm.setCUSTOMER_RETURNS_MASTER_ID(productcursor.getString(productcursor.getColumnIndex("CUSTOMER_RETURNS_MASTER_ID")));
                    pm.setMASTER_PRODUCT_ITEM_ID(productcursor.getString(productcursor.getColumnIndex("MASTER_PRODUCT_ITEM_ID")));
                    if (productcursor.getString(productcursor.getColumnIndex("BATCHNO")).endsWith("\n")) {
                        pm.setBATCHNO(productcursor.getString(productcursor.getColumnIndex("BATCHNO")).replaceAll("[\n\r]$", ""));
                    } else {
                        pm.setBATCHNO(productcursor.getString(productcursor.getColumnIndex("BATCHNO")));
                    }
                    pm.setRETURNQUANTITY(productcursor.getString(productcursor.getColumnIndex("RETURNQUANTITY")));
                    //pm.setEXPIRYDATE(productcursor.getString(productcursor.getColumnIndex("EXPIRYDATE")));


                    if (productcursor.getString(productcursor.getColumnIndex("EXPIRYDATE")) == null
                            || (productcursor.getString(productcursor.getColumnIndex("EXPIRYDATE")).matches("")) ||
                            productcursor.getString(productcursor.getColumnIndex("EXPIRYDATE")).isEmpty()) {
                        pm.setEXPIRYDATE("");
                    } else {
                        pm.setEXPIRYDATE(productcursor.getString(productcursor.getColumnIndex("EXPIRYDATE")));
                    }
                    pm.setCUSTOMER_RETURN_DETAIL_STATUS(productcursor.getString(productcursor.getColumnIndex("CUSTOMER_RETURN_DETAIL_STATUS")));
                    productlist.add(pm);
                } while (productcursor.moveToNext());
            }

            mydb.close();
        } catch (IndexOutOfBoundsException cur) {
            cur.printStackTrace();
        }
        return productlist;

    }


    public Boolean updateintoreturnmaster(String CUSTOMER_RETURNS_MASTERID) {
        boolean returnval = true;
        SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
        ContentValues value = new ContentValues();
        value.put("ISSYNCED", "1");
        int sqlUpdateRetval = mydb.update("customerReturnMaster", value,
                " CUSTOMERRETURNGUID = ? "
                , new String[]{String.format(CUSTOMER_RETURNS_MASTERID)});

        if (sqlUpdateRetval < 1) {
            returnval = false;
        }
        mydb.close();
        return returnval;
    }

    public void SalesReturn_UpdateorDelete(ArrayList<SalesReturnManage> list, String billno) {
        SQLiteDatabase db  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
        ContentValues values = new ContentValues();
        DecimalFormat f = new DecimalFormat("##.00");
        float DetailGST, TotalMasterGST = 0.0f, TotalMasterDiscount = 0.0f,
                MasterTotalValue = 0.0f, MasterBalance = 0.0f, Detail_return_total = 0.0f;
        for (SalesReturnManage prod : list) {
            Float remaingqty = prod.getSalestockqty() - prod.getSalereturn_qty();
            DetailGST = prod.getGST();
            float oneItemGST = DetailGST / prod.getSalestockqty();
            float newDetailGST = oneItemGST * remaingqty;
            Float oneitemdisc = Float.parseFloat(prod.getSalediscoumt()) / prod.getSalestockqty();

            TotalMasterGST += newDetailGST;
            TotalMasterDiscount += oneitemdisc * remaingqty;
            if (prod.getSalereturn_qty() == prod.getSalestockqty()) {
                Detail_return_total += prod.getSalereturn_total();
                db.delete("retail_str_sales_detail", "ITEM_GUID = ?  and " + "BILLNO " +
                        " = ?  ", new String[]{prod.getSaleProdid(), billno});
            } else {
            /*Float remaingqty = prod.getSalestockqty() - prod.getSalereturn_qty();
            DetailGST = prod.getGST();
            float oneItemGST = DetailGST / prod.getSalestockqty();
            float newDetailGST = oneItemGST * remaingqty;
            Float oneitemdisc= Float.parseFloat(prod.getSalediscoumt())/prod.getSalestockqty();

            TotalMasterGST +=newDetailGST;
            TotalMasterDiscount +=oneitemdisc*remaingqty;
            MasterTotalValue =(prod.getSaletotal()-prod.getSalereturn_total());
            MasterBalance= prod.getBALANCE_CASH()-MasterTotalValue;
            Log.e("Rc Doing ", "Cal "+TotalMasterGST+"  "+TotalMasterDiscount+"  "+MasterTotalValue+"  "+MasterBalance);
            */
                values.put("QTY", prod.getSalestockqty() - prod.getSalereturn_qty());
                Log.d("R TV & NV", String.valueOf((prod.getSalereturn_sprice() * remaingqty)) + "  " + ((prod.getSalereturn_sprice() * remaingqty) - newDetailGST));
                Detail_return_total += prod.getSalereturn_total();
                values.put("TOTALVALUE", prod.getSalereturn_sprice() * remaingqty);
                values.put("TOTALGST", newDetailGST);
                values.put("CGST", f.format(newDetailGST / 2));
                values.put("SGST", f.format(newDetailGST / 2));
                values.put("DISCOUNT_VALUE", f.format(oneitemdisc * remaingqty));
                values.put("NETVALUE", f.format((prod.getSalereturn_sprice() * remaingqty) - newDetailGST));

                db.update("retail_str_sales_detail", values, "ITEM_GUID = ?  and " + "BILLNO " +
                        " = ?  ", new String[]{prod.getSaleProdid(), billno});
                values.clear();
            }
            MasterTotalValue = (prod.getSaletotal() - Detail_return_total);
            MasterBalance = prod.getBALANCE_CASH() - MasterTotalValue;
            Log.e("Rc GST,DISC,TA,BAL ", "Cal " + TotalMasterGST + "  " + TotalMasterDiscount + "  " + MasterTotalValue + "  " + MasterBalance);

        }

        values.put("NETDISCOUNT", f.format(TotalMasterDiscount));
        values.put("TAXVALUE", f.format(TotalMasterGST));
        values.put("TOTAL_AMOUNT", f.format(MasterTotalValue));
        values.put("TOTAL_BILL_AMOUNT", f.format(MasterTotalValue));
        values.put("NETVALUE ", f.format(MasterTotalValue - TotalMasterGST));
        values.put("BALANCE_CASH ", f.format(MasterBalance));//
        Log.e("Balance cash ", f.format(MasterBalance));


        db.update("retail_str_sales_master", values, "BILLNO " +
                " = ?  ", new String[]{billno});

    }

}