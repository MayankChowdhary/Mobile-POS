package com.retailstreet.mobilepos.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.retailstreet.mobilepos.Model.ShiftTrans;
import com.retailstreet.mobilepos.Utils.StringWithTag;
import com.retailstreet.mobilepos.View.ApplicationContextProvider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static android.content.Context.MODE_PRIVATE;

public class ControllerShiftTrans {

    Context context;
    String dbname= "MasterDB";

        String username;
        String SHIFT_TRANS_ID ;
        String SHIFT_TRANSACTIONGUID;
        String ORG_GUID;
        String STORE_GUID ;
        String SHIFT_GUID ;
        String SHIFT_DATE;
        String START_TIME ;
        String END_TIME ;
        String IS_SHIFT_STARTED ;
        String IS_SHIFT_ENDED ;
        String USER_GUID ;
        String CASH_OPENED ;
        String CASH_CLOSED ;
        String ISACTIVE ;
        String ISSYNCED;


    public ControllerShiftTrans(){
        context= ApplicationContextProvider.getContext();

    }

    public  ControllerShiftTrans(String PosDate, String Username, String shiftGuid,String startCash){
        context= ApplicationContextProvider.getContext();
        this.username=Username;
        SHIFT_TRANS_ID =  getTimeStamp();
        SHIFT_TRANSACTIONGUID = getUUID();
        ORG_GUID =  getFromRetailStore("MASTERORG_GUID");
        STORE_GUID = getFromRetailStore("STORE_GUID");;
        SHIFT_GUID  = shiftGuid ;
        SHIFT_DATE  = PosDate;
        START_TIME = getCurrentTime();
         END_TIME ="00:00:00";
        IS_SHIFT_STARTED = "Y";
         IS_SHIFT_ENDED ="N";
        USER_GUID =  getFromGroupUserMaster("USER_GUID");
        CASH_OPENED = startCash;
        CASH_CLOSED = "0.00";
        ISACTIVE = "Y";
        ISSYNCED ="0";
        
        ShiftTrans shiftTrans = new ShiftTrans(  SHIFT_TRANS_ID  , SHIFT_TRANSACTIONGUID , ORG_GUID , STORE_GUID  , SHIFT_GUID  , SHIFT_DATE , START_TIME  , END_TIME  , IS_SHIFT_STARTED  , IS_SHIFT_ENDED  , USER_GUID  , CASH_OPENED  , CASH_CLOSED  , ISACTIVE  , ISSYNCED );

        insertIntoShiftTrans(shiftTrans);
    }

    public  void insertIntoShiftTrans(ShiftTrans shiftTrans){

        try {
            if (shiftTrans == null) {
                return;
            }
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, MODE_PRIVATE, null);
            ContentValues contentValues = new ContentValues();
            contentValues.put("SHIFT_TRANS_ID", shiftTrans.getSHIFT_TRANS_ID());
            contentValues.put("SHIFT_TRANSACTIONGUID", shiftTrans.getSHIFT_TRANSACTIONGUID());
            contentValues.put("ORG_GUID", shiftTrans.getORG_GUID());
            contentValues.put("STORE_GUID", shiftTrans.getSTORE_GUID());
            contentValues.put("SHIFT_GUID", shiftTrans.getSHIFT_GUID());
            contentValues.put("SHIFT_DATE", shiftTrans.getSHIFT_DATE());
            contentValues.put("START_TIME", shiftTrans.getSTART_TIME());
            contentValues.put("END_TIME", shiftTrans.getEND_TIME());
            contentValues.put("IS_SHIFT_STARTED", shiftTrans.getIS_SHIFT_STARTED());
            contentValues.put("IS_SHIFT_ENDED", shiftTrans.getIS_SHIFT_ENDED());
            contentValues.put("USER_GUID", shiftTrans.getUSER_GUID());
            contentValues.put("CASH_OPENED", shiftTrans.getCASH_OPENED());
            contentValues.put("CASH_CLOSED", shiftTrans.getCASH_CLOSED());
            contentValues.put("ISACTIVE", shiftTrans.getISACTIVE());
            contentValues.put("ISSYNCED", shiftTrans.getISSYNCED());

            myDataBase.insert("shift_trans", null, contentValues);
            myDataBase.close(); // Closing database connection
            Log.d("DataSuccessfulInserted!", "insertIntoShiftTrans: ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  List<StringWithTag> getShiftOptions(){
        List<StringWithTag> list = new ArrayList<>();
        //list.add(new StringWithTag("No Delivery Type", " "));
        SQLiteDatabase mydb = context.openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE,null);
        Cursor cursor = mydb.rawQuery("select SHIFTGUID, SHIFT_DESCRIPTION from master_shift",null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String id = cursor.getString(1);
                String name = cursor.getString(0);
                list.add(new StringWithTag(id, name));
                cursor.moveToNext();
            }
        }
        cursor.close();
        mydb.close();
        return  list;

    }

    public  List<String> getUsernameOptions(){
        List<String> list = new ArrayList<>();
        //list.add(new StringWithTag("No Delivery Type", " "));
        SQLiteDatabase mydb = context.openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE,null);
        Cursor cursor = mydb.rawQuery("select USER_NAME from group_user_master",null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String id = cursor.getString(0);
                list.add((id));
                cursor.moveToNext();
            }
        }
        cursor.close();
        mydb.close();
        return  list;

    }

    public String getUUID() {
        UUID uuid = UUID.randomUUID();
        System.out.println("UUID=" + uuid.toString());
        return uuid.toString();
    }

    public String getTimeStamp() {
        Date date = new Date();
        //This method returns the time in millis
        long timeMilli = date.getTime();
        return String.valueOf(timeMilli);
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

    private String getFromShiftMaster( String shiftGuid ,String column) {
        String result= null;
        try {
            SQLiteDatabase  mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            result = "";
            String selectQuery = "SELECT "+column+" FROM master_shift where SHIFTGUID ="+shiftGuid;
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {

                result= cursor.getString(0);
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getFromShiftMaster: "+column+": "+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private String getCurrentTime(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss", Locale.getDefault());
        String strTime = formatter.format(date);
        return  strTime;
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

    public String geShiftSession() {
        String result= null;
        String date=getDate();
        Log.d("DateGenerated", "geShiftSession: "+date);

        String userGuid= getFromGroupUserMaster("USER_GUID");
        try {
            SQLiteDatabase  mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            result = "";

            String selectQuery = "SELECT * FROM shift_trans where USER_GUID ='"+userGuid+"'" +"AND ISACTIVE = 'Y' ";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToLast()) {

                result= cursor.getString(5);
                Log.d("Printing Received Date", "geShiftSession: "+result);
                result = getFormatedDate(result);
                if(result==null)
                    return null;

                if(result.equals(date)){
                    Log.d("DateMatched", "geShiftSession: "+result);
                    return result;
                }else {
                    return null;
                }

            }else {
                result ="";
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getFromShiftMaster: "+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void closeSessions(){
        try {
            String userGuid = getFromGroupUserMaster("USER_GUID");
            SQLiteDatabase  mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String strSQL = "UPDATE shift_trans SET ISACTIVE = 'N' WHERE USER_GUID = '"+userGuid+"'";
            mydb.execSQL(strSQL);
            mydb.close();
            Log.d("SessionClosed", "closeSessions: Invoked ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getDate(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault());
        String strDate = formatter.format(date);
        return  strDate;
    }
    private String getFormatedDate(String date) {

        try {
            Log.d("FormatterReceved", "getFormatedDate: "+date);

            SimpleDateFormat formatter=new SimpleDateFormat( "dd-MM-yyyy",Locale.getDefault());
            Date date1=formatter.parse(date);
            String formatted = new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault()).format(date1);
            Log.d("DateAfterParsed", "getFormatedDate: "+formatted);
            return formatted;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
