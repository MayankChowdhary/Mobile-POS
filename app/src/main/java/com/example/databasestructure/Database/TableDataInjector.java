package com.example.databasestructure.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.databasestructure.Controller.ApiInterface;
import com.example.databasestructure.Controller.DBReadyCallback;
import com.example.databasestructure.Model.BillDetail;
import com.example.databasestructure.Model.BillMaster;
import com.example.databasestructure.Model.GroupUserMaster;
import com.example.databasestructure.Model.CustomerMaster;
import com.example.databasestructure.Model.ProductMaster;
import com.example.databasestructure.Model.StockMaster;
import com.example.databasestructure.View.ApplicationContextProvider;
import com.example.databasestructure.View.LoadingDialog;

import java.util.List;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TableDataInjector {
    String dbname = "MasterDB";
    SQLiteDatabase myDataBase;
    private Context context;
    String baseUrl = "http://99retailstreet.com:8080/";
    String storeId;
    private DBReadyCallback dbReadyCallback;
    private List<GroupUserMaster> groupUserMasterList = null;
    private List<CustomerMaster> retailCustList = null;
    private List<ProductMaster> productMasterList = null;
    private List<StockMaster> stockMasterList = null;
    private List<BillDetail> billDetailList = null;
    private List<BillMaster> billMasterList = null;

    public static int status =0;

    public TableDataInjector(Context context, String storeid,DBReadyCallback callback) {

        this.context = context;
        this.storeId = storeid;
        dbReadyCallback=callback;

        getUserMasterList();
        getRetailCustList();
        getProductMasterList();
        getStockMasterList();
        getBillDetails();
        getBillMaster();
    }

    private Retrofit getRetroInstance(String url) {
        try {
            Retrofit retrofit = null;
            Log.i("autolog", "retrofit");

            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Log.i("autolog", "build();");
            }
            return retrofit;
        } catch (Exception e) {
            Log.i("autolog", "Exception");
            return null;
        }

    }

    public void getUserMasterList() {
        try {
            Retrofit retrofit = getRetroInstance(baseUrl);
            ApiInterface service = retrofit.create(ApiInterface.class);
            Call<List<GroupUserMaster>> call = service.getGroupUserMaster(generateTableUrl("GroupUserMaster",storeId));
            call.enqueue(new Callback<List<GroupUserMaster>>() {
                @Override
                public void onResponse(Call<List<GroupUserMaster>> call, Response<List<GroupUserMaster>> response) {
                    groupUserMasterList = response.body();
                    Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                    InsertDataGroupUserMaster(groupUserMasterList);
                }

                @Override
                public void onFailure(Call<List<GroupUserMaster>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                    LoadingDialog.cancelDialog();
                    Toast.makeText(ApplicationContextProvider.getContext(),"Network Error Download Failed !",Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getBillMaster() {
        try {
            Retrofit retrofit = getRetroInstance(baseUrl);
            ApiInterface service = retrofit.create(ApiInterface.class);
            Call<List<BillMaster>> call = service.getBill_Mater(generateTableUrl("BillMaster",storeId));
            call.enqueue(new Callback<List<BillMaster>>() {
                @Override
                public void onResponse(Call<List<BillMaster>> call, Response<List<BillMaster>> response) {
                    billMasterList = response.body();
                    Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                    InsertBillMaster(billMasterList);
                }

                @Override
                public void onFailure(Call<List<BillMaster>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                    LoadingDialog.cancelDialog();
                    Toast.makeText(ApplicationContextProvider.getContext(),"Network Error Download Failed !",Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }
    public void getRetailCustList() {
        try {
            Retrofit retrofit = getRetroInstance(baseUrl);
            ApiInterface service = retrofit.create(ApiInterface.class);
            Call<List<CustomerMaster>> call = service.getRetail_Cust(generateTableUrl("MasterCustomer",storeId));
            call.enqueue(new Callback<List<CustomerMaster>>() {
                @Override
                public void onResponse(Call<List<CustomerMaster>> call, Response<List<CustomerMaster>> response) {
                    retailCustList = response.body();
                    Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                    InsertRetailCust(retailCustList);
                }

                @Override
                public void onFailure(Call<List<CustomerMaster>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getProductMasterList() {
        try {
            Retrofit retrofit = getRetroInstance(baseUrl);
            ApiInterface service = retrofit.create(ApiInterface.class);
            Call<List<ProductMaster>> call = service.getProduct_MAster(generateTableUrl("ProductMaster",storeId));
            call.enqueue(new Callback<List<ProductMaster>>() {
                @Override
                public void onResponse(Call<List<ProductMaster>> call, Response<List<ProductMaster>> response) {
                    productMasterList = response.body();
                    Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                    InsertProductMaster(productMasterList);
                }

                @Override
                public void onFailure(Call<List<ProductMaster>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getStockMasterList() {
        try {
            Retrofit retrofit = getRetroInstance(baseUrl);
            ApiInterface service = retrofit.create(ApiInterface.class);
            Call<List<StockMaster>> call = service.getStock_Master(generateTableUrl("StockMaster",storeId));
            call.enqueue(new Callback<List<StockMaster>>() {
                @Override
                public void onResponse(Call<List<StockMaster>> call, Response<List<StockMaster>> response) {
                    stockMasterList = response.body();
                    Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                    InsertStockMaster(stockMasterList);
                }

                @Override
                public void onFailure(Call<List<StockMaster>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                    LoadingDialog.cancelDialog();
                    Toast.makeText(ApplicationContextProvider.getContext(),"Network Error Download Failed !",Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getBillDetails() {
        try {
            Retrofit retrofit = getRetroInstance(baseUrl);
            ApiInterface service = retrofit.create(ApiInterface.class);
            Call<List<BillDetail>> call = service.getBill_Details(generateTableUrl("BillDetail",storeId));
            call.enqueue(new Callback<List<BillDetail>>() {
                @Override
                public void onResponse(Call<List<BillDetail>> call, Response<List<BillDetail>> response) {
                    billDetailList = response.body();
                    Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                    InsertBillDetails(billDetailList);
                }

                @Override
                public void onFailure(Call<List<BillDetail>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void InsertBillMaster(List<BillMaster> list) {
        if (list == null) {
            return;
        }
        myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
        myDataBase.delete("retail_str_sales_master", null, null);
        for (BillMaster prod : list) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("BILLMASTERID", prod.getBILLMASTERID());
            contentValues.put("BILLNO", prod.getBILLNO());
            contentValues.put("SALEDATETIME", prod.getSALEDATETIME());
            contentValues.put("SALEDATE", prod.getSALEDATE());
            contentValues.put("SALETIME", prod.getSALETIME());
            contentValues.put("MASTERCUSTOMERGUID", prod.getMASTERCUSTOMERGUID());
            contentValues.put("MASTERSTOREGUID", prod.getMASTERSTOREGUID());
            contentValues.put("MASTERTERMINALGUID", prod.getMASTERTERMINALGUID());
            contentValues.put("MASTERSHIFTGUID", prod.getMASTERSHIFTGUID());
            contentValues.put("USER_GUID", prod.getUSER_GUID());
            contentValues.put("CUST_MOBILENO", prod.getCUST_MOBILENO());
            contentValues.put("NETVALUE", prod.getNETVALUE());
            contentValues.put("TAXVALUE", prod.getTAXVALUE());
            contentValues.put("TOTAL_AMOUNT", prod.getTOTAL_AMOUNT());
            contentValues.put("DELIVERY_TYPE_GUID", prod.getDELIVERY_TYPE_GUID());
            contentValues.put("BILL_PRINT", prod.getBILL_PRINT());
            contentValues.put("TOTAL_BILL_AMOUNT", prod.getTOTAL_BILL_AMOUNT());
            contentValues.put("NO_OF_ITEMS", prod.getNO_OF_ITEMS());
            contentValues.put("BILLSTATUS", prod.getBILLSTATUS());
            contentValues.put("ISSYNCED", prod.getISSYNCED());
            contentValues.put("RECEIVED_CASH", prod.getRECEIVED_CASH());
            contentValues.put("BALANCE_CASH", prod.getBALANCE_CASH());
            contentValues.put("ROUND_OFF", prod.getROUND_OFF());
            contentValues.put("NETDISCOUNT", prod.getNETDISCOUNT());


            myDataBase.insert("retail_str_sales_master", null, contentValues);
            status+=1;
            if(status==6){
                LoadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
            }
            // myDataBase.close(); // Closing database connection
        }
    }

    public void InsertBillDetails(List<BillDetail> list) {
        if (list == null) {
            return;
        }
        myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
        myDataBase.delete("retail_str_sales_detail ", null, null);

        for (BillDetail prod : list) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("BILLDETAILID", prod.getBILLDETAILID());
            contentValues.put("BILLMASTERID", prod.getBILLMASTERID());
            contentValues.put("BILLNO", prod.getBILLNO());
            contentValues.put("CATEGORY_GUID", prod.getCATEGORY_GUID());
            contentValues.put("SUBCAT_GUID", prod.getSUBCAT_GUID());
            contentValues.put("ITEM_GUID", prod.getITEM_GUID());
            contentValues.put("ITEM_CODE", prod.getITEM_CODE());
            contentValues.put("QTY", prod.getQTY());
            contentValues.put("UOM_GUID", prod.getUOM_GUID());
            contentValues.put("BATCHNO", prod.getBATCHNO());
            contentValues.put("COST_PRICE", prod.getCOST_PRICE());
            contentValues.put("NETVALUE", prod.getNETVALUE());
            contentValues.put("DISCOUNT_PERC", prod.getDISCOUNT_PERC());
            contentValues.put("DISCOUNT_VALUE", prod.getDISCOUNT_VALUE());
            contentValues.put("TOTALVALUE", prod.getTOTALVALUE());
            contentValues.put("MRP", prod.getMRP());
            contentValues.put("BILLDETAILSTATUS", prod.getBILLDETAILSTATUS());
            contentValues.put("HSN", prod.getHSN());
            contentValues.put("CGSTPERCENTAGE", prod.getCGSTPERCENTAGE());
            contentValues.put("SGSTPERCENTAGE", prod.getSGSTPERCENTAGE());
            contentValues.put("IGSTPERCENTAGE", prod.getIGSTPERCENTAGE());
            contentValues.put("ADDITIONALPERCENTAGE", prod.getADDITIONALPERCENTAGE());
            contentValues.put("CGST", prod.getCGST());
            contentValues.put("SGST", prod.getSGST());
            contentValues.put("IGST", prod.getIGST());
            contentValues.put("ADDITIONALCESS", prod.getADDITIONALCESS());
            contentValues.put("TOTALGST", prod.getTOTALGST());

            myDataBase.insert("retail_str_sales_detail ", null, contentValues);
            status+=1;
            if(status==6){
                LoadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
            }
            // myDataBase.close(); // Closing database connection
        }
    }
    public void InsertStockMaster(List<StockMaster> list) {
        if (list == null) {
            return;
        }
        myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
        myDataBase.delete("retail_str_stock_master", null, null);
        for (StockMaster prod : list) {
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


            myDataBase.insert("retail_str_stock_master", null, values);
            status+=1;
            if(status==6){
                LoadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
            }
                // myDataBase.close(); // Closing database connection
        }
    }

    public void InsertProductMaster(List<ProductMaster> list) {
        if (list == null) {
            return;
        }
        myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
        myDataBase.delete("retail_store_prod_com", null, null);
        for (ProductMaster prod : list) {
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

            myDataBase.insert("retail_store_prod_com", null, contentValues);
            status+=1;
            if(status==6){
                LoadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
            }
            // myDataBase.close(); // Closing database connection
        }
    }


    public void InsertDataGroupUserMaster(List<GroupUserMaster> list) {
        if (list == null) {
            return;
        }
        myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
        myDataBase.delete("group_user_master", null, null);
        for (GroupUserMaster prod : list) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("PASSWORD", prod.getPASSWORD());
            contentValues.put("ROLE_ID", prod.getROLEID());
            contentValues.put("ROLE_NAME", prod.getROLENAME());
            contentValues.put("USER_NAME", prod.getUSERNAME());
            contentValues.put("ISACTIVE", prod.getISACTIVE());
            contentValues.put("EMPLOYEE_GUID", prod.getEMPLOYEEGUID());
            contentValues.put("ROLE_GUID", prod.getROLEGUID());
            contentValues.put("USER_GUID", prod.getUSERGUID());
            contentValues.put("ISSYNCED", prod.getISSYNCED());

            myDataBase.insert("group_user_master", null, contentValues);
            status+=1;
            SQLiteDbInspector.PrintTableData(myDataBase, "group_user_master");
            if(status==6){
                LoadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
            }
           // myDataBase.close(); // Closing database connection
        }
    }

    public void InsertRetailCust(List<CustomerMaster> list) {
        myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
        if (list == null) {
            return;
        }
        myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
        myDataBase.delete("retail_cust", null, null);
        for (CustomerMaster prod : list) {
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


            myDataBase.insert("retail_cust", null, contentValues);
            status+=1;
            if(status==6){
                LoadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
            }
            //myDataBase.close(); // Closing database connection
        }
    }

    private String generateTableUrl(String tablename , String storeid){

        String defaultstoreid= "9747690001";

        if(storeid.isEmpty() || storeid.equals(""))
            storeid = defaultstoreid;

        switch (tablename){

            case "GroupUserMaster" :
                return "ApiTest/GroupUserMaster?STORE_ID="+storeid;


            case "MasterCustomer":
                return "ApiTest/MasterCustomer?STORE_ID="+storeid;


            case "ProductMaster":
                return "ApiTest/ProductMaster?STORE_ID="+storeid;

            case "StockMaster":
                return "ApiTest/StockMaster?STORE_ID="+storeid;


            case "BillDetail":
                return "ApiTest/BillDetail?STORE_ID="+storeid;


            case "BillMaster":
                return "ApiTest/BillMaster?STORE_ID="+storeid;

            default:
                return "";
        }

    }
}
