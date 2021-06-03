package com.retailstreet.mobilepos.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.retailstreet.mobilepos.Model.GRNDetailsSync;
import com.retailstreet.mobilepos.Model.GRNMasterSync;
import com.retailstreet.mobilepos.Model.SyncResponse;
import com.retailstreet.mobilepos.Utils.ApiInterface;
import com.retailstreet.mobilepos.Utils.Constants;
import com.retailstreet.mobilepos.Utils.RetroSync;
import com.retailstreet.mobilepos.View.ApplicationContextProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.retailstreet.mobilepos.Utils.Constants.DBNAME;

/**
 * Created by Mayank Choudhary on 29-04-2021.
 * mayankchoudhary00@gmail.com
 */
public class GRNUploader extends Worker {

    private ArrayList<GRNMasterSync> GetGRNMasterToSync;
    private ArrayList<GRNDetailsSync> GetGRNDetailToSync;

    public GRNUploader(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            GetGRNMasterToSync = getGRNMasterSyncdata();
            JSONArray jsonArray = new JSONArray();
            for (GRNMasterSync prod : GetGRNMasterToSync) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("GRNID", "0");
                    jsonObject.put("GRN_GUID", prod.getGRN_GUID());
                    jsonObject.put("ORG_GUID", prod.getORG_GUID());
                    jsonObject.put("STORE_GUID", prod.getSTORE_GUID());
                    jsonObject.put("VENDOR_GUID", prod.getVENDOR_GUID());
                    jsonObject.put("GRNNO", prod.getGRNNO());
                    jsonObject.put("GRNDATE", prod.getGRNDate());
                    jsonObject.put("INVNO", prod.getINVOICENO());
                    jsonObject.put("INVDATE", prod.getINVOICEDATE());
                    jsonObject.put("GRNTYPE", prod.getGRNTYPE());
                    jsonObject.put("GRANDAMOUNT", prod.getGRANDAMOUNT());
                    jsonObject.put("INVOICEDISCOUNT", prod.getINVOICEDISCOUNT());
                    jsonObject.put("USERGUID", getFromGroupUserMaster("USER_GUID"));



                    GetGRNDetailToSync = getGRNDetailsSyncdata(prod.getGRN_GUID());
                    Log.e("RC detailsize:- ",jsonObject.toString());
                    JSONArray jsonArray2 = new JSONArray();
                    for (GRNDetailsSync prods : GetGRNDetailToSync) {
                        JSONObject jsonObject2 = new JSONObject();
                        jsonObject2.put("GRNID", prods.getGRNID());
                        jsonObject2.put("GRNNO", prods.getGRNNO());
                        jsonObject2.put("CAT_GUID", prods.getCAT_GUID());
                        jsonObject2.put("ITEM_GUID", prods.getITEM_GUID());
                        jsonObject2.put("GRN_QTY", prods.getGRN_QTY());
                        jsonObject2.put("UOM_GUID", prods.getUOM_GUID());
                        if(prods.getBATCHNO()==(null)){
                            jsonObject2.put("BATCHNO", "0");
                        }
                        else{
                            jsonObject2.put("BATCHNO", prods.getBATCHNO());
                        }
                        jsonObject2.put("EXP_DATE", prods.getEXP_DATE());
                        jsonObject2.put("PUR_PRICE", prods.getPUR_PRICE());
                        jsonObject2.put("TAX_AMOUNT", prods.getTAX_AMOUNT());
                        jsonObject2.put("GRN_VALUE", prods.getGRN_VALUE());
                        jsonObject2.put("MRP", prods.getMRP());
                        jsonObject2.put("ISFREEGOODS", prods.getISFREEGOODS());
                        jsonObject2.put("FREE_QUANTITY", prods.getFREE_QUANTITY());
                        //   jsonObject2.put("TOTAL_GRN_QUANTITY", Double.parseDouble(prods.getTOTAL_GRN_QUANTITY()));
                        jsonObject2.put("TOTAL_GRN_QUANTITY", ((Float.parseFloat(prods.getGRN_QTY()))+(Float.parseFloat(prods.getFREE_QUANTITY()))));
                        jsonObject2.put("PURCHASEDISCOUNTPERCENTAGE", prods.getPURCHASEDISCOUNTPERCENTAGE());
                        jsonObject2.put("PURCHASEDISCOUNTBYAMOUNT", prods.getPURCHASEDISCOUNTBYAMOUNT());
                        jsonObject2.put("GRN_DETAIL_STATUS",prods.getGRN_DETAIL_STATUS());//null
                        jsonArray2.put(jsonObject2);
                    }

                    jsonObject.put("ObjBillDetails", jsonArray2);
                    jsonArray.put(jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            Log.e("Rc ValGRN",jsonArray.toString());
            if(jsonArray.length()>0 && GetGRNMasterToSync.size()>0){
                UploadRecord(jsonArray);
            }else{
                Log.e("GRN ","No Records found to Upload");
            }
            return Result.success();
        } catch (Throwable throwable) {
            Log.e("GRN", "Error Uploading Record ", throwable);
            return Result.failure();
        }
    }

    private void UploadRecord(JSONArray jsonArray) {
        ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonArray.toString());
        Call<SyncResponse> call = apiService.UploadGRNRecords(Constants.Authorization, body);
        call.enqueue(new Callback<SyncResponse>() {
            @Override
            public void onResponse(Call<SyncResponse> call, Response<SyncResponse> response) {
                try {
                    Log.d("Rc GRN code:- ", String.valueOf(response.code()));
                    if (response.isSuccessful()) {
                        Log.e("Rc Response for :",response.body().getMessage() + "   " + response.body().getOutputValuesKeys());
                        List<String> items = Arrays.asList(response.body().getOutputValuesKeys().split("\\s*,\\s*"));
                        for (String i : items) {
                            Log.d("Output GRN ",i);
                            Boolean val = updateintogrnMaster(i);
                            // Boolean val2 = dbHelper.updateintogrnDetail(i);
                            Log.e("Rc UpdateGRN:- ",val+"");
                            Log.d("Rc Response for :", "Record Synced Successfully");

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SyncResponse> call, Throwable t) {
                //  progressDialog.dismiss();
                Log.e("In Error", t.getMessage());
            }
        });
    }


    public ArrayList<GRNDetailsSync> getGRNDetailsSyncdata(String grnno) {
        ArrayList<GRNDetailsSync> productlist = new ArrayList<GRNDetailsSync>();
        try {
            SQLiteDatabase db = ApplicationContextProvider.getContext().openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE, null);
                //,SPEC_PRICE,WHOLE_SPRICE,CGST,SGST  //these 4 columns are not found
            Cursor productcursor = db.rawQuery(" select distinct a.GRNDETAILID,a.ITEM_GUID,a.GRN_QTY,a.UOM_GUID,a.BATCHNO,a.EXP_DATE" +
                    ",a.PUR_PRICE,a.TAX_AMOUNT,a.GRN_VALUE,a.MRP,a.ISFREEGOODS,a.FREE_QUANTITY,a.PURCHASEDISCOUNTPERCENTAGE," +
                    "a.PURCHASEDISCOUNTBYAMOUNT,a.GRNDETAILGUID,b.GRNNO,c.CATEGORY_GUID from retail_str_grn_detail a LEFT JOIN retail_str_grn_master b ON a.GRN_GUID =b.GRN_GUID LEFT JOIN retail_store_prod_com c ON a.ITEM_GUID=c.ITEM_GUID where a.GRN_GUID='" + grnno + "' ", null);
            if (productcursor.moveToFirst()) {
                do {//GRNNO,CAT_GUID,GRN_DETAIL_STATUS
                    GRNDetailsSync pm = new GRNDetailsSync();
                    pm.setGRNID(productcursor.getString(productcursor.getColumnIndex("GRNDETAILID")));
                    pm.setGRNNO(productcursor.getString(productcursor.getColumnIndex("GRNNO")));//Pending
                    pm.setCAT_GUID(productcursor.getString(productcursor.getColumnIndex("CATEGORY_GUID")));//Pending
                    pm.setITEM_GUID(productcursor.getString(productcursor.getColumnIndex("ITEM_GUID")));
                    pm.setGRN_QTY(productcursor.getString(productcursor.getColumnIndex("GRN_QTY")));
                    pm.setUOM_GUID(productcursor.getString(productcursor.getColumnIndex("UOM_GUID")));
                    pm.setBATCHNO(productcursor.getString(productcursor.getColumnIndex("BATCHNO")));
                    pm.setEXP_DATE(productcursor.getString(productcursor.getColumnIndex("EXP_DATE")));
                    pm.setPUR_PRICE(productcursor.getString(productcursor.getColumnIndex("PUR_PRICE")));
                    pm.setTAX_AMOUNT(productcursor.getString(productcursor.getColumnIndex("TAX_AMOUNT")));
                    pm.setGRN_VALUE(productcursor.getString(productcursor.getColumnIndex("GRN_VALUE")));
                    pm.setMRP(productcursor.getString(productcursor.getColumnIndex("MRP")));
                    pm.setISFREEGOODS(productcursor.getString(productcursor.getColumnIndex("ISFREEGOODS")));
                    pm.setFREE_QUANTITY(productcursor.getString(productcursor.getColumnIndex("FREE_QUANTITY")));
                    pm.setPURCHASEDISCOUNTPERCENTAGE(productcursor.getString(productcursor.getColumnIndex("PURCHASEDISCOUNTPERCENTAGE")));
                    pm.setPURCHASEDISCOUNTBYAMOUNT(productcursor.getString(productcursor.getColumnIndex("PURCHASEDISCOUNTBYAMOUNT")));
                    pm.setGRN_DETAIL_STATUS("1");//pending
                    pm.setGRNDETAILGUID(productcursor.getString(productcursor.getColumnIndex("GRNDETAILGUID")));

                    productlist.add(pm);
                } while (productcursor.moveToNext());
            }

            db.close();
        } catch (IndexOutOfBoundsException cur) {
            cur.printStackTrace();
        }
        return productlist;
    }

    public ArrayList<GRNMasterSync> getGRNMasterSyncdata() {
        ArrayList<GRNMasterSync> productlist = new ArrayList<>();
        try {
            SQLiteDatabase db = ApplicationContextProvider.getContext().openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE, null);
            Cursor productcursor = db.rawQuery(" select distinct a.GRN_GUID,a.VENDOR_GUID,a.GRNNO" +
                    ",a.GRNDate,a.INVOICENO,a.INVOICEDATE,a.GRNTYPE,a.GRANDAMOUNT,a.INVOICEDISCOUNT,b.MASTERORG_GUID,b.STORE_GUID from retail_str_grn_master a LEFT JOIN retail_store b ON a.STORE_GUID=b.STORE_GUID " +
                    "where a.ISSYNCED='0' ", null);
            if (productcursor.moveToFirst()) {
                do {
                    GRNMasterSync pm = new GRNMasterSync();

                    pm.setGRN_GUID(productcursor.getString(productcursor.getColumnIndex("GRN_GUID")));
                    pm.setORG_GUID(productcursor.getString(productcursor.getColumnIndex("MASTERORG_GUID")));//from store
                    pm.setSTORE_GUID(productcursor.getString(productcursor.getColumnIndex("STORE_GUID")));//from store
                    pm.setVENDOR_GUID(productcursor.getString(productcursor.getColumnIndex("VENDOR_GUID")));
                    pm.setGRNNO(productcursor.getString(productcursor.getColumnIndex("GRNNO")));
                    pm.setGRNDate(productcursor.getString(productcursor.getColumnIndex("GRNDate")));
                    pm.setINVOICENO(productcursor.getString(productcursor.getColumnIndex("INVOICENO")));
                    pm.setINVOICEDATE(productcursor.getString(productcursor.getColumnIndex("INVOICEDATE")));
                    pm.setGRNTYPE(productcursor.getString(productcursor.getColumnIndex("GRNTYPE")));
                    pm.setGRANDAMOUNT(productcursor.getString(productcursor.getColumnIndex("GRANDAMOUNT")));
                    pm.setINVOICEDISCOUNT(productcursor.getString(productcursor.getColumnIndex("INVOICEDISCOUNT")));
                    productlist.add(pm);
                } while (productcursor.moveToNext());
            }

            db.close();
        } catch (IndexOutOfBoundsException cur) {
            cur.printStackTrace();
        }
        return productlist;
    }

    public Boolean updateintogrnMaster(String VendorGuid) {
        boolean returnval = true;
        SQLiteDatabase db = ApplicationContextProvider.getContext().openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE, null);
        ContentValues value = new ContentValues();
        value.put("ISSYNCED", "1");
        int sqlUpdateRetval = db.update("retail_str_grn_master", value,
                " GRN_GUID = ? "
                , new String[]{String.format(VendorGuid)});

        if (sqlUpdateRetval < 1) {
            returnval = false;
        }
        db.close();
        return returnval;
    }

    private String getFromGroupUserMaster(String column){
        String result= null;
        try {
            SQLiteDatabase mydb  = ApplicationContextProvider.getContext().openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String username;
            SharedPreferences sh = ApplicationContextProvider.getContext().getSharedPreferences("com.retailstreet.mobilepos", MODE_PRIVATE);
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
}
