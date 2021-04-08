package com.retailstreet.mobilepos.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.retailstreet.mobilepos.Model.CustomerAddress;
import com.retailstreet.mobilepos.Model.CustomerMaster;
import com.retailstreet.mobilepos.Model.CustomerMasterUpload;
import com.retailstreet.mobilepos.Utils.IDGenerator;
import com.retailstreet.mobilepos.Utils.WorkManagerSync;
import com.retailstreet.mobilepos.View.ApplicationContextProvider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;
import static com.retailstreet.mobilepos.Utils.Constants.DBNAME;

public class ControllerCustomerMaster {

    Context context;

    String CUSTOMERTYPE;
    String CUSTOMERCATEGORY;
    String CUST_ID;
    String CUSTOMERGUID;
    String NAME;
    String E_MAIL;
    String GENDER;
    String AGE;
    String ISEMAILVALIDATED;
    String MOBILE_NO;
    String ISMOBILEVALIDATED;
    String SECONDARYEMAIL;
    String SECONDARYMOBILE;
    String CUSTOMERDISCOUNTPERCENTAGE;
    String LASTOTP;
    String OTPVALIDATEDDATETIME;
    String EMAILVALIDATEDDATETIME;
    String UPDATEDBY;
    String LAST_MODIFIED;
    String TOTALORDERS;
    String CREDIT_CUST;
    String REGISTEREDFROM;
    String REGISTEREDFROMSTOREID;
    String PANNO;
    String GSTNO;
    String CPASSWORD;
    String CUSTOMERSTATUS;
    String MASTER_CUSTOMER_TYPE;
    String MASTER_CUSTOMERCATEGORY;
    String ADVANCE_AMOUNT;
    String BALANCE_AMOUNT;
    String CUSTOMERSTOREKEY;
    String STORE_ID;
    String MASTER_CUSTOMERCATEGORYID;
    String PERCENTAGE;
    String CREATEDBY;
    String ISSYNCED;
    String CREDITLIMIT;



    String CUSTOMERADDRESSID;
    String MASTERCUSTOMERID;
    String ADDRESSTYPE;
    String CONTACTPERSONNAME;
    String ADDRESSLINE1;
    String ADDRESSLINE2;
    String STREET_AREA;
    String PINCODE;
    String CITY;
    String MASTERSTATEID;
    String ADDRESSSTATUS;
    String CREATEDDATETIME;


    public ControllerCustomerMaster() {
        context= ApplicationContextProvider.getContext();
    }

    public ControllerCustomerMaster( String stateGuid, String city, String pin, String street, String add1, String add2, String addType, String creditLimit, String balance, String advance, String custCatid, String custTypeguid, String PAN, String GST, String custCredittype, String mobile, String gender, String custType, String custCategory, String custName , String email, String age){
        context= ApplicationContextProvider.getContext();
        CUSTOMERTYPE = custType;
        CUSTOMERCATEGORY = custCategory;
        CUST_ID = IDGenerator.getTimeStamp();
        CUSTOMERGUID = IDGenerator.getUUID();
        NAME = custName;
        E_MAIL = email;
        GENDER =gender;
        AGE = age;
        ISEMAILVALIDATED = "0";
        MOBILE_NO = mobile;
        ISMOBILEVALIDATED = "0";
        SECONDARYEMAIL = " ";
        SECONDARYMOBILE = " ";
        CUSTOMERDISCOUNTPERCENTAGE = "0.00";
        LASTOTP = " ";
        OTPVALIDATEDDATETIME =getDateAndTime();
        EMAILVALIDATEDDATETIME = getDateAndTime();
        UPDATEDBY = getFromGroupUserMaster("USER_GUID");
        LAST_MODIFIED = getDateAndTime();
        TOTALORDERS = "0";
        CREDIT_CUST = custCredittype;
        REGISTEREDFROM = "MOBILEPOS";
        STORE_ID = getFromRetailStore("STORE_ID");
        REGISTEREDFROMSTOREID =  STORE_ID;
        PANNO = PAN;
        GSTNO = GST;
        CPASSWORD = " ";
        CUSTOMERSTATUS ="1";
        MASTER_CUSTOMER_TYPE = custTypeguid;
        MASTER_CUSTOMERCATEGORY = custCatid;
        ADVANCE_AMOUNT = advance;
        BALANCE_AMOUNT =  balance;
        CUSTOMERSTOREKEY = " ";
        MASTER_CUSTOMERCATEGORYID = custCatid;
        PERCENTAGE = "0.00";
        CREATEDBY = getFromGroupUserMaster("USER_GUID");
        ISSYNCED ="0";
        CREDITLIMIT =  creditLimit;

        CUSTOMERADDRESSID = IDGenerator.getTimeStamp();
        MASTERCUSTOMERID = CUSTOMERGUID;
        ADDRESSTYPE = addType;
        CONTACTPERSONNAME = NAME;
        ADDRESSLINE1 = add1;
        ADDRESSLINE2 = add2;
        STREET_AREA = street;
        PINCODE = pin;
        CITY = city;
        MASTERSTATEID = stateGuid;
        ADDRESSSTATUS = "1";
        CREATEDDATETIME = getDateAndTime();


        CustomerMaster customerMaster = new CustomerMaster(  CUSTOMERTYPE,  CUSTOMERCATEGORY,  CUST_ID,  CUSTOMERGUID,  NAME,  E_MAIL,  GENDER,  AGE,  ISEMAILVALIDATED,  MOBILE_NO,  ISMOBILEVALIDATED,  SECONDARYEMAIL,  SECONDARYMOBILE,  CUSTOMERDISCOUNTPERCENTAGE,  LASTOTP,  OTPVALIDATEDDATETIME,  EMAILVALIDATEDDATETIME,  UPDATEDBY,  LAST_MODIFIED,  TOTALORDERS,  CREDIT_CUST,  REGISTEREDFROM,  REGISTEREDFROMSTOREID,  PANNO,  GSTNO,  CPASSWORD,  CUSTOMERSTATUS,  MASTER_CUSTOMER_TYPE,  MASTER_CUSTOMERCATEGORY,  ADVANCE_AMOUNT,  BALANCE_AMOUNT,  CUSTOMERSTOREKEY,  STORE_ID,  MASTER_CUSTOMERCATEGORYID,  PERCENTAGE,  CREATEDBY,  ISSYNCED,  CREDITLIMIT);
        CustomerAddress customerAddress = new CustomerAddress( CUSTOMERADDRESSID,  MASTERCUSTOMERID,  ADDRESSTYPE,  CONTACTPERSONNAME,  ADDRESSLINE1,  ADDRESSLINE2,  STREET_AREA,  PINCODE,  CITY,  MASTERSTATEID,  ADDRESSSTATUS,  CREATEDBY,  CREATEDDATETIME);
        InsertRetailCust(customerMaster);
        InsertCustomerAddress(customerAddress);

        try {
            new WorkManagerSync(5);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ControllerCustomerMaster(String custId, String mobile, String name,String email,String pan, String gst,String custType, String custTypeGuid,String custguId, String creditLimit, String advance ) {
        context= ApplicationContextProvider.getContext();
      updateCustomerMaster(custId,mobile,name,email,pan,gst,custType,custTypeGuid, creditLimit,advance);
            try {
                new WorkManagerSync(5);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

        public void InsertRetailCust(CustomerMaster prod) {
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE, null);
                ContentValues contentValues = new ContentValues();
                contentValues.put("CUSTOMERTYPE", prod.getCUSTOMERTYPE());
                contentValues.put("CUSTOMERCATEGORY", prod.getCUSTOMERCATEGORY());
                contentValues.put("CUST_ID", prod.getCUST_ID());
                contentValues.put("CUSTOMERGUID", prod.getCUSTOMERGUID());
                contentValues.put("NAME", prod.getNAME());
                contentValues.put("E_MAIL", prod.getE_MAIL());
                contentValues.put("GENDER", prod.getGENDER());
                contentValues.put("AGE", prod.getAGE());
                contentValues.put("ISEMAILVALIDATED", prod.getISEMAILVALIDATED());
                contentValues.put("MOBILE_NO", prod.getMOBILE_NO());
                contentValues.put("ISMOBILEVALIDATED", prod.getISMOBILEVALIDATED());
                contentValues.put("SECONDARYEMAIL", prod.getSECONDARYEMAIL());
                contentValues.put("SECONDARYMOBILE", prod.getSECONDARYMOBILE());
                contentValues.put("CUSTOMERDISCOUNTPERCENTAGE", prod.getCUSTOMERDISCOUNTPERCENTAGE());
                contentValues.put("LASTOTP", prod.getLASTOTP());
                contentValues.put("OTPVALIDATEDDATETIME", prod.getOTPVALIDATEDDATETIME());
                contentValues.put("EMAILVALIDATEDDATETIME", prod.getEMAILVALIDATEDDATETIME());
                contentValues.put("UPDATEDBY", prod.getUPDATEDBY());
                contentValues.put("LAST_MODIFIED", prod.getLAST_MODIFIED());
                contentValues.put("TOTALORDERS", prod.getTOTALORDERS());
                contentValues.put("CREDIT_CUST", prod.getCREDIT_CUST());
                contentValues.put("REGISTEREDFROM", prod.getREGISTEREDFROM());
                contentValues.put("REGISTEREDFROMSTOREID", prod.getREGISTEREDFROMSTOREID());
                contentValues.put("PANNO", prod.getPANNO());
                contentValues.put("GSTNO", prod.getGSTNO());
                contentValues.put("CPASSWORD", prod.getCPASSWORD());
                contentValues.put("CUSTOMERSTATUS", prod.getCUSTOMERSTATUS());
                contentValues.put("MASTER_CUSTOMER_TYPE", prod.getMASTER_CUSTOMER_TYPE());
                contentValues.put("MASTER_CUSTOMERCATEGORY", prod.getMASTER_CUSTOMERCATEGORY());
                contentValues.put("ADVANCE_AMOUNT", prod.getADVANCE_AMOUNT());
                contentValues.put("BALANCE_AMOUNT", prod.getBALANCE_AMOUNT());
                contentValues.put("CUSTOMERSTOREKEY", prod.getCUSTOMERSTOREKEY());
                contentValues.put("STORE_ID", prod.getSTORE_ID());
                contentValues.put("MASTER_CUSTOMERCATEGORYID", prod.getMASTER_CUSTOMERCATEGORYID());
                contentValues.put("PERCENTAGE", prod.getPERCENTAGE());
                contentValues.put("CREATEDBY", prod.getCREATEDBY());
                contentValues.put("ISSYNCED", prod.getISSYNCED());
                contentValues.put("CREDITLIMIT", prod.getCREDITLIMIT());
                myDataBase.insert("retail_cust", null, contentValues);

            myDataBase.close();
            Log.d("Insertion Successful", "InsertRetailCust: ");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void InsertCustomerAddress(CustomerAddress prod) {
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE, null);
                ContentValues contentValues = new ContentValues();
                contentValues.put("CUSTOMERADDRESSID", prod.getCUSTOMERADDRESSID());
                contentValues.put("MASTERCUSTOMERID", prod.getMASTERCUSTOMERID());
                contentValues.put("ADDRESSTYPE", prod.getADDRESSTYPE());
                contentValues.put("CONTACTPERSONNAME", prod.getCONTACTPERSONNAME());
                contentValues.put("ADDRESSLINE1", prod.getADDRESSLINE1());
                contentValues.put("ADDRESSLINE2", prod.getADDRESSLINE2());
                contentValues.put("STREET_AREA", prod.getSTREET_AREA());
                contentValues.put("PINCODE", prod.getPINCODE());
                contentValues.put("CITY", prod.getCITY());
                contentValues.put("MASTERSTATEID", prod.getMASTERSTATEID());
                contentValues.put("ADDRESSSTATUS", prod.getADDRESSSTATUS());
                contentValues.put("CREATEDBY", prod.getCREATEDBY());
                contentValues.put("CREATEDDATETIME", prod.getCREATEDDATETIME());
                myDataBase.insert("retail_cust_address", null, contentValues);

            myDataBase.close();
            Log.d("Insertion Successful", "InsertMasterAddress: ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCustomerMaster(String custId, String mobile, String name,String email,String pan, String gst,String custType, String custTypeGuid, String creditLimit, String advance){
        try{
        SQLiteDatabase myDataBase = context.openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE, null);
        ContentValues contentValues = new ContentValues();
        contentValues.put("MOBILE_NO", mobile);
        contentValues.put("NAME", name);
        contentValues.put("E_MAIL", email);
        contentValues.put("PANNO", pan);
        contentValues.put("GSTNO", gst);
        contentValues.put("CUSTOMERTYPE", custType);
        contentValues.put("MASTER_CUSTOMER_TYPE", custTypeGuid);
        contentValues.put("CREDITLIMIT", creditLimit);
        contentValues.put("ADVANCE_AMOUNT", advance);
        contentValues.put("ISSYNCED", "0");

        String where = "CUST_ID=?";
        String[] whereArgs = new String[] {String.valueOf(custId)};
        myDataBase.update("retail_cust", contentValues, where, whereArgs);
        myDataBase.close();
            Log.d("Updation Successful", "UpdateMasterCustomer: ");
        }
        catch (Exception e){
           e.printStackTrace();
        }
    }

    public void updateAddressMaster(String custguId,String add1, String add2, String street){
        try{
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE, null);
            ContentValues contentValues = new ContentValues();
            contentValues.put("ADDRESSLINE1", add1);
            contentValues.put("ADDRESSLINE2", add2);
            contentValues.put("STREET_AREA", street);
            String where = "MASTERCUSTOMERID=?";
            String[] whereArgs = new String[] {String.valueOf(custguId)};
            myDataBase.update("retail_cust_address", contentValues, where, whereArgs);
            myDataBase.close();
            Log.d("Updation Successful", "UpdateMasterAddress: ");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    private String getDateAndTime(){
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

    public ArrayList<CustomerAddress> getCustomerDetailsSyncdata(String customerguid) {
        ArrayList<CustomerAddress> productlist = new ArrayList<CustomerAddress>();
        try {
            SQLiteDatabase db  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            Cursor productcursor = db.rawQuery(" select * from retail_cust_address where MASTERCUSTOMERID='"+ customerguid +"'", null);
            if (productcursor.moveToFirst()) {
                do {

                    CustomerAddress pm = new CustomerAddress();
                    pm.setCUSTOMERADDRESSID(productcursor.getString(productcursor.getColumnIndex("CUSTOMERADDRESSID")));
                    pm.setMASTERCUSTOMERID(productcursor.getString(productcursor.getColumnIndex("MASTERCUSTOMERID")));//MASTERCUSTOMERGUID
                    pm.setADDRESSTYPE(productcursor.getString(productcursor.getColumnIndex("ADDRESSTYPE")));
                    pm.setCONTACTPERSONNAME(productcursor.getString(productcursor.getColumnIndex("CONTACTPERSONNAME")));
                    pm.setADDRESSLINE1(productcursor.getString(productcursor.getColumnIndex("ADDRESSLINE1")));
                    pm.setADDRESSLINE2(productcursor.getString(productcursor.getColumnIndex("ADDRESSLINE2")));
                    pm.setSTREET_AREA(productcursor.getString(productcursor.getColumnIndex("STREET_AREA")));
                    pm.setPINCODE(productcursor.getString(productcursor.getColumnIndex("PINCODE")));
                    pm.setCITY(productcursor.getString(productcursor.getColumnIndex("CITY")));
                    pm.setMASTERSTATEID(productcursor.getString(productcursor.getColumnIndex("MASTERSTATEID")));//MASTERSTATEGUID
                    pm.setADDRESSSTATUS(productcursor.getString(productcursor.getColumnIndex("ADDRESSSTATUS")));
                    pm.setCREATEDBY(productcursor.getString(productcursor.getColumnIndex("CREATEDBY")));
                    pm.setCREATEDDATETIME(productcursor.getString(productcursor.getColumnIndex("CREATEDDATETIME")));

                    productlist.add(pm);
                } while (productcursor.moveToNext());
            }

            db.close();
        } catch (IndexOutOfBoundsException cur) {
            cur.printStackTrace();
        }
        return productlist;
    }

    public ArrayList<CustomerMasterUpload> getCustomerMasterSyncdata() {
        ArrayList<CustomerMasterUpload> productlist = new ArrayList<CustomerMasterUpload>();
        try {
            SQLiteDatabase db  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            Cursor productcursor = db.rawQuery(" select * from retail_cust where ISSYNCED = '0' ", null);
            if (productcursor.moveToFirst()) {
                do {

                    CustomerMasterUpload pm = new CustomerMasterUpload();
                    pm.setCUSTOMERGUID(productcursor.getString(productcursor.getColumnIndex("CUSTOMERGUID")));
                    pm.setNAME(productcursor.getString(productcursor.getColumnIndex("NAME")));
                    pm.setE_MAIL(productcursor.getString(productcursor.getColumnIndex("E_MAIL")));
                    pm.setGENDER(productcursor.getString(productcursor.getColumnIndex("GENDER")));
                    pm.setAGE(productcursor.getString(productcursor.getColumnIndex("AGE")));
                    pm.setMOBILE_NO(productcursor.getString(productcursor.getColumnIndex("MOBILE_NO")));
                    pm.setSECONDARYEMAIL(productcursor.getString(productcursor.getColumnIndex("SECONDARYEMAIL")));
                    pm.setSECONDARYMOBILE(productcursor.getString(productcursor.getColumnIndex("SECONDARYMOBILE")));
                    pm.setCUSTOMERDISCOUNTPERCENTAGE(productcursor.getString(productcursor.getColumnIndex("CUSTOMERDISCOUNTPERCENTAGE")));
                    pm.setUPDATEDBY(productcursor.getString(productcursor.getColumnIndex("UPDATEDBY")));
                    pm.setCREDIT_CUST(productcursor.getString(productcursor.getColumnIndex("CREDIT_CUST")));
                    pm.setREGISTEREDFROM("MOBILEPOS");
                    pm.setREGISTEREDFROMSTOREGUID(getFromRetailStore("STORE_GUID"));//REGISTEREDFROMSTOREID
                    pm.setPANNO(productcursor.getString(productcursor.getColumnIndex("PANNO")));
                    pm.setGSTNO(productcursor.getString(productcursor.getColumnIndex("GSTNO")));
                    pm.setCUSTOMERSTATUS(productcursor.getString(productcursor.getColumnIndex("CUSTOMERSTATUS")));
                    pm.setMASTER_CUSTOMER_TYPE(productcursor.getString(productcursor.getColumnIndex("CUSTOMERTYPE")));////MASTER_CUSTOMER_TYPEGUID
                    pm.setMASTER_CUSTOMERCATEGORY(productcursor.getString(productcursor.getColumnIndex("CUSTOMERCATEGORY")));////MASTER_CUSTOMERCATEGORYGUID
                    pm.setADVANCE_AMOUNT(productcursor.getString(productcursor.getColumnIndex("ADVANCE_AMOUNT")));
                    pm.setBALANCE_AMOUNT(productcursor.getString(productcursor.getColumnIndex("BALANCE_AMOUNT")));
                    pm.setCUSTOMERID(productcursor.getString(productcursor.getColumnIndex("CUST_ID")));
                    pm.setCREATEDBY(productcursor.getString(productcursor.getColumnIndex("CREATEDBY")));
                    pm.setCREDITLIMIT(productcursor.getString(productcursor.getColumnIndex("CREDITLIMIT")));

                    productlist.add(pm);
                } while (productcursor.moveToNext());
            }

            db.close();
        } catch (IndexOutOfBoundsException cur) {
            cur.printStackTrace();
        }
        return productlist;

    }

    public Boolean updateintocustomer(String custid) {
        boolean returnval = true;
        SQLiteDatabase db  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
        ContentValues value = new ContentValues();
        value.put("ISSYNCED", "1");
        int sqlUpdateRetval = db.update("retail_cust", value, " CUST_ID = ? ", new String[]{String.format(custid)});

        if (sqlUpdateRetval < 1) {
            returnval = false;
        }
        db.close();
        return returnval;
    }
}


