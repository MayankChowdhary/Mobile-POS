package com.retailstreet.mobilepos.Controller;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.retailstreet.mobilepos.Model.GroupUserMaster;

public class ControllerUserMaster extends SQLiteOpenHelper {

    private Context context;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MasterDB";
    private static final String TABLE_NAME = "group_user_master";
    private static final String KEY_EMPLOYEE_GUID = "EMPLOYEE_GUID";
    private static final String KEY_ISACTIVE = "ISACTIVE";
    private static final String KEY_PASSWORD = "PASSWORD";
    private static final String KEY_ROLE_GUID = "ROLE_GUID";
    private static final String KEY_ROLE_ID = "ROLE_ID";
    private static final String KEY_ROLE_NAME = "ROLE_NAME";
    private static final String KEY_USER_GUID = "USER_GUID";
    private static final String KEY_USER_NAME = "USER_NAME";
    private static final String KEY_ISSYNCED = "ISSYNCED";
    private static final String[] COLUMNS = {KEY_EMPLOYEE_GUID, KEY_ISACTIVE, KEY_PASSWORD, KEY_ROLE_GUID,KEY_ROLE_ID,KEY_ROLE_NAME,KEY_USER_GUID,KEY_USER_NAME, KEY_ISSYNCED};

    public ControllerUserMaster(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context =context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      /*  String CREATION_TABLE = "CREATE TABLE GroupUserMaster ( "
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

    public void deleteOne(GroupUserMaster groupUserMaster) {
        // Get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "EMPLOYEE_GUID = ?", new String[]{String.valueOf(groupUserMaster.getEMPLOYEEGUID())});
        db.close();
    }

    public GroupUserMaster getUSERMASTER(int EMPLOYEE_GUID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, // a. table
                COLUMNS, // b. column names
                " EMPLOYEE_GUID = ?", // c. selections
                new String[]{String.valueOf(EMPLOYEE_GUID)}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        if (cursor != null)
            cursor.moveToFirst();

        GroupUserMaster groupUserMaster = new GroupUserMaster();
        groupUserMaster.setEMPLOYEEGUID(cursor.getString(0));
        groupUserMaster.setISACTIVE(cursor.getString(1));
        groupUserMaster.setPASSWORD(cursor.getString(2));
        groupUserMaster.setROLEGUID(cursor.getString(3));
        groupUserMaster.setROLEID(cursor.getString(4));
        groupUserMaster.setROLENAME(cursor.getString(5));
        groupUserMaster.setUSERGUID(cursor.getString(6));
        groupUserMaster.setUSERNAME(cursor.getString(7));
        groupUserMaster.setISSYNCED(cursor.getString(8));



        return groupUserMaster;
    }

    public boolean validateUserPass(String Username , String password){

        return checkPassword(getUserMasterData(Username),password);
    }

    public GroupUserMaster getUserMasterData(String UserName) {
        SQLiteDatabase db = context.openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null);
        Cursor cursor = db.query(TABLE_NAME, // a. table
                COLUMNS, // b. column names
                " USER_NAME = ?", // c. selections
                new String[]{String.valueOf(UserName)}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit


        if( cursor != null && cursor.moveToFirst() ) {

            GroupUserMaster groupUserMaster = new GroupUserMaster();
            groupUserMaster.setEMPLOYEEGUID(cursor.getString(0));
            groupUserMaster.setISACTIVE(cursor.getString(1));
            groupUserMaster.setPASSWORD(cursor.getString(2));
            groupUserMaster.setROLEGUID(cursor.getString(3));
            groupUserMaster.setROLEID(cursor.getString(4));
            groupUserMaster.setROLENAME(cursor.getString(5));
            groupUserMaster.setUSERGUID(cursor.getString(6));
            groupUserMaster.setUSERNAME(cursor.getString(7));
            groupUserMaster.setISSYNCED(cursor.getString(8));

            Log.d("CursorRetrievedFor", "getUSERNamePass: " + cursor.getString(7));
            Log.d("CursorRetrievedFor", "getUSERNamePass: " + cursor.getString(2));
            cursor.close();
            return groupUserMaster;
        }else
            cursor.close();
            return null;

    }

    public boolean checkPassword(GroupUserMaster groupUserMaster, String password){

        if(groupUserMaster==null) {
            Log.e("CheckPass", "checkPassword: ReceivedNullObject" );
            return false;
        }

            return password.equals(groupUserMaster.getPASSWORD());
        }



    public List<GroupUserMaster> allUserMaster() {

        List<GroupUserMaster> groupUserMasters = new LinkedList<GroupUserMaster>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        GroupUserMaster groupUserMaster = null;

        if (cursor.moveToFirst()) {
            do {
                groupUserMaster = new GroupUserMaster();
                groupUserMaster.setEMPLOYEEGUID(cursor.getString(0));
                groupUserMaster.setISACTIVE(cursor.getString(1));
                groupUserMaster.setPASSWORD(cursor.getString(2));
                groupUserMaster.setROLEGUID(cursor.getString(3));
                groupUserMaster.setROLEID(cursor.getString(4));
                groupUserMaster.setROLENAME(cursor.getString(5));
                groupUserMaster.setUSERGUID(cursor.getString(6));
                groupUserMaster.setUSERNAME(cursor.getString(7));
                groupUserMaster.setISSYNCED(cursor.getString(8));
                groupUserMasters.add(groupUserMaster);
            } while (cursor.moveToNext());
        }

        return groupUserMasters;
    }

    public void addUserMaster(GroupUserMaster groupUserMaster) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EMPLOYEE_GUID, groupUserMaster.getEMPLOYEEGUID());
        values.put(KEY_ISACTIVE, groupUserMaster.getISACTIVE());
        values.put(KEY_PASSWORD, groupUserMaster.getPASSWORD());
        values.put(KEY_ROLE_GUID, groupUserMaster.getROLEGUID());
        values.put(KEY_ROLE_ID, groupUserMaster.getROLEID());
        values.put(KEY_ROLE_NAME, groupUserMaster.getROLENAME());
        values.put(KEY_USER_GUID, groupUserMaster.getUSERGUID());
        values.put(KEY_USER_NAME, groupUserMaster.getUSERNAME());
        values.put(KEY_ISSYNCED, groupUserMaster.getISSYNCED());
        // insert
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public int updateUserMaster(GroupUserMaster groupUserMaster) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ISACTIVE, groupUserMaster.getISACTIVE());
        values.put(KEY_PASSWORD, groupUserMaster.getPASSWORD());
        values.put(KEY_ROLE_GUID, groupUserMaster.getROLEGUID());
        values.put(KEY_ROLE_ID, groupUserMaster.getROLEID());
        values.put(KEY_ROLE_NAME, groupUserMaster.getROLENAME());
        values.put(KEY_USER_GUID, groupUserMaster.getUSERGUID());
        values.put(KEY_USER_NAME, groupUserMaster.getUSERNAME());
        values.put(KEY_ISSYNCED, groupUserMaster.getISSYNCED());

        int i = db.update(TABLE_NAME, // table
                values, // column/value
                "EMPLOYEE_GUID = ?", // selections
                new String[]{String.valueOf(groupUserMaster.getEMPLOYEEGUID())});

        db.close();

        return i;
    }

}