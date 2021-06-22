package com.retailstreet.mobilepos.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.retailstreet.mobilepos.Model.ShiftTrans;
import com.retailstreet.mobilepos.Model.ShiftTransUpload;
import com.retailstreet.mobilepos.Utils.IDGenerator;
import com.retailstreet.mobilepos.Utils.StringWithTag;
import com.retailstreet.mobilepos.Utils.WorkManagerSync;
import com.retailstreet.mobilepos.View.ApplicationContextProvider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;
/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */
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

    String MASTER_TERMINAL_ID;

    public ControllerShiftTrans(){
        context= ApplicationContextProvider.getContext();
        MASTER_TERMINAL_ID = getTerminal_ID();

    }

    public  ControllerShiftTrans(String PosDate, String Username, String shiftGuid,String startCash){
        context= ApplicationContextProvider.getContext();
        MASTER_TERMINAL_ID = getTerminal_ID();
        this.username=Username;
        SHIFT_TRANS_ID =  IDGenerator.getTimeStamp();
        SHIFT_TRANSACTIONGUID = IDGenerator.getUUID();
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
        
        ShiftTrans shiftTrans = new ShiftTrans(  SHIFT_TRANS_ID  , SHIFT_TRANSACTIONGUID , ORG_GUID , STORE_GUID  , SHIFT_GUID  , SHIFT_DATE , START_TIME  , END_TIME  , IS_SHIFT_STARTED  , IS_SHIFT_ENDED  , USER_GUID  , CASH_OPENED  , CASH_CLOSED  , ISACTIVE  , ISSYNCED,MASTER_TERMINAL_ID );

        insertIntoShiftTrans(shiftTrans);

    }


    public  void closeDay( String endCash){
        try {
            String shiftTransID = geShiftTransID();
            Log.d("ShiftTransID", "closeDay: Received "+shiftTransID);
            END_TIME = getCurrentTime();
            CASH_CLOSED = endCash;
            SQLiteDatabase  mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String strSQL = "UPDATE shift_trans SET END_TIME = '"+END_TIME+"'"+", ISACTIVE = 'N', IS_SHIFT_ENDED = 'Y', CASH_CLOSED = '"+CASH_CLOSED+"'"+", ISSYNCED = '0' WHERE SHIFT_TRANS_ID = '"+shiftTransID+"'";
            mydb.execSQL(strSQL);
            mydb.close();
            Log.d("SessionClosed", "closeSessions: Invoked ");
            try {
                new WorkManagerSync(2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
            try {
                new WorkManagerSync(2);
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    public String getShiftTime() {
        String result= null;
        String time=null;
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
                time= cursor.getString(6);
                Log.d("Printing Received Date", "geShiftSession: "+result);
                result = getFormatedDate(result);
                if(result==null)
                    return null;

                if(result.equals(date)){
                    Log.d("DateMatched", "geShiftSession: "+result);
                    return time;
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
        return time;
    }


    public String geShiftTransID() {
        String result= null;
        String userGuid= getFromGroupUserMaster("USER_GUID");
        try {
            SQLiteDatabase  mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String selectQuery = "SELECT * FROM shift_trans where USER_GUID ='"+userGuid+"'" +"AND ISACTIVE = 'Y' ";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToLast()) {

                result = cursor.getString(0);
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getFromShiftMaster: "+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private String getDate(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
        String strDate = formatter.format(date);
        return  strDate;
    }
    private String getFormatedDate(String date) {

        try {
            Log.d("FormatterReceved", "getFormatedDate: "+date);

            SimpleDateFormat formatter=new SimpleDateFormat( "yyyy-MM-dd",Locale.getDefault());
            Date date1=formatter.parse(date);
            String formatted = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).format(date1);
            Log.d("DateAfterParsed", "getFormatedDate: "+formatted);
            return formatted;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<ShiftTransUpload> get_ShiftTrans_syncdata() {
        ArrayList<ShiftTransUpload> productlist = new ArrayList<>();
        try {
            SQLiteDatabase db = ApplicationContextProvider.getContext().openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            Cursor productcursor = db.rawQuery(" select * from shift_trans where ISSYNCED ='0' ", null);
            if (productcursor.moveToFirst()) {
                do {
                    ShiftTransUpload pm = new ShiftTransUpload();
                    pm.setSHIFT_TRANS_ID(productcursor.getString(productcursor.getColumnIndex("SHIFT_TRANS_ID")));
                    pm.setSHIFT_TRANSACTIONGUID(productcursor.getString(productcursor.getColumnIndex("SHIFT_TRANSACTIONGUID")));
                    pm.setORG_GUID(productcursor.getString(productcursor.getColumnIndex("ORG_GUID")));
                    pm.setSTORE_GUID(productcursor.getString(productcursor.getColumnIndex("STORE_GUID")));
                    pm.setUSER_GUID(productcursor.getString(productcursor.getColumnIndex("USER_GUID")));
                    pm.setSHIFT_GUID(productcursor.getString(productcursor.getColumnIndex("SHIFT_GUID")));
                    pm.setSHIFT_DATE(productcursor.getString(productcursor.getColumnIndex("SHIFT_DATE")));
                    pm.setSTART_TIME(productcursor.getString(productcursor.getColumnIndex("START_TIME")));
                    pm.setEND_TIME(productcursor.getString(productcursor.getColumnIndex("END_TIME")));
                    pm.setIS_SHIFT_STARTED(productcursor.getString(productcursor.getColumnIndex("IS_SHIFT_STARTED")));
                    pm.setIS_SHIFT_ENDED(productcursor.getString(productcursor.getColumnIndex("IS_SHIFT_ENDED")));
                    pm.setCASH_OPENED(productcursor.getString(productcursor.getColumnIndex("CASH_OPENED")));
                    pm.setCASH_CLOSED(productcursor.getString(productcursor.getColumnIndex("CASH_CLOSED")));
                    pm.setISACTIVE(productcursor.getString(productcursor.getColumnIndex("ISACTIVE")));
                    productlist.add(pm);
                } while (productcursor.moveToNext());
            }
            productcursor.close();
            db.close();
        } catch (IndexOutOfBoundsException cur) {
            cur.printStackTrace();
        }
        return productlist;
    }

    public Boolean updateintoShiftTrans(String SHIFT_TRANS_ID) {

        boolean returnval = true;
        SQLiteDatabase db = ApplicationContextProvider.getContext().openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
        ContentValues value = new ContentValues();
        value.put("ISSYNCED", "1");
        int sqlUpdateRetval = db.update("shift_trans", value,
                "SHIFT_TRANS_ID = ? "
                , new String[]{SHIFT_TRANS_ID});

        if (sqlUpdateRetval < 1) {
            returnval = false;
            Log.d("ShiftSyncNote", "updateIsSync: Faild ");
        }else {
            Log.d("ShiftSyncNote", "updateIsSync: Successful ");
        }
        db.close();
        return returnval;
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
