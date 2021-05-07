package com.retailstreet.mobilepos.Controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.retailstreet.mobilepos.View.ApplicationContextProvider;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Mayank Choudhary on 06-05-2021.
 * mayankchoudhary00@gmail.com
 */
public class ControllerVendorReturn {

    Context context;
    public ControllerVendorReturn(){
     context = ApplicationContextProvider.getContext();
    }

    public Cursor getVendorReturnCursor(String grnGuid) {
        try {
            SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            mydb.execSQL("DROP TABLE IF EXISTS tmp_vendor_return");
            mydb.execSQL("CREATE TABLE IF NOT EXISTS tmp_vendor_return AS SELECT pm.PROD_NM, pm.BARCODE, pm.UOM, gd.GRN_QTY,gd.FREE_QUANTITY, gd.PUR_PRICE, gd.GRN_VALUE, gd.EXP_DATE, gd.GRN_GUID, gd.GRNDETAILID  FROM retail_store_prod_com pm INNER JOIN retail_str_grn_detail gd ON gd.ITEM_GUID = pm.ITEM_GUID WHERE gd.GRN_GUID = '"+ grnGuid +"'");
            mydb.execSQL("ALTER TABLE tmp_vendor_return ADD COLUMN MAXQTY INTEGER DEFAULT 0");
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
}
