package com.retailstreet.mobilepos.Database;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.retailstreet.mobilepos.Controller.ApiInterface;
import com.retailstreet.mobilepos.Controller.ControllerBillDetail;
import com.retailstreet.mobilepos.Controller.ControllerBillMaster;
import com.retailstreet.mobilepos.Controller.ControllerBillPayDetail;
import com.retailstreet.mobilepos.Model.BillDetail;
import com.retailstreet.mobilepos.Model.BillMasterUpload;
import com.retailstreet.mobilepos.Model.BillPayDetail;
import com.retailstreet.mobilepos.Model.SyncResponse;
import com.retailstreet.mobilepos.Utils.Constants;
import com.retailstreet.mobilepos.Utils.RetroSync;
import com.retailstreet.mobilepos.View.ApplicationContextProvider;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TableDataUploader extends Worker {



        private ArrayList<BillMasterUpload> GetBillMasterToSync;
        private ArrayList<BillDetail> GetBillDetailToSync;
        private ArrayList<BillPayDetail> GetBillPaymentDetailToSync;

        public TableDataUploader(@NonNull Context context, @NonNull WorkerParameters workerParams) {
            super(context, workerParams);
        }

        @NonNull
        @Override
        public Result doWork() {
            try {
                Log.d("Sales Sync Started", "doWork: Invoked");
                //  Gson gson = new GsonBuilder().serializeNulls().create();
                GetBillMasterToSync = new ControllerBillMaster().getBillMaster();
                JSONArray jsonArray = new JSONArray();
                for (BillMasterUpload prod : GetBillMasterToSync) {
                    Log.e("RC mastersize:- ", String.valueOf(GetBillMasterToSync.size()));
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("BID", prod.getBID());
                    jsonObject.put("ORG_GUID", prod.getORG_GUID());
                    jsonObject.put("STORE_GUID", prod.getSTORE_GUID());
                    jsonObject.put("TERMINAL_GUID", prod.getTERMINAL_GUID());
                    jsonObject.put("SHIFTID", prod.getSHIFTID());
                    jsonObject.put("BILLNO", prod.getBILLNO());
                    jsonObject.put("SALEDATE", prod.getSALEDATE());
                    jsonObject.put("SALETIME", prod.getSALETIME());
                    jsonObject.put("USER_GUID", prod.getUSER_GUID());
                    jsonObject.put("CUST_MOBILENO", prod.getCUST_MOBILENO());
                    jsonObject.put("NETVALUE", prod.getNETVALUE());
                    jsonObject.put("TAXVALUE", prod.getTAXVALUE());
                    jsonObject.put("TOTAL_AMOUNT", prod.getTOTAL_AMOUNT());
                    jsonObject.put("DEL_TYPE", prod.getDEL_TYPE());
                    if(prod.getBILL_PRINT().matches("1"))
                        jsonObject.put("BILL_PRINT","Y");
                    else
                        jsonObject.put("BILL_PRINT","N");

                    jsonObject.put("TOTAL_BILL_AMOUNT", prod.getTOTAL_BILL_AMOUNT());
                    jsonObject.put("NO_OF_ITEMS", prod.getNO_OF_ITEMS());
                    jsonObject.put("BILL_STATUS", prod.getBILL_STATUS());
                    jsonObject.put("MASTERCUSTOMER_GUID", prod.getMASTERCUSTOMER_GUID());
                    jsonObject.put("RECEIVED_CASH", prod.getRECEIVED_CASH());
                    jsonObject.put("BALANCE_CASH", prod.getBALANCE_CASH());
                    jsonObject.put("ROUND_OFF", prod.getROUND_OFF());
                    jsonObject.put("NETDISCOUNT", prod.getNETDISCOUNT());


                    GetBillDetailToSync = new ControllerBillDetail().getBillDetails(prod.getBILLNO());
                    Log.e("RC detailsize:- ", String.valueOf(GetBillDetailToSync.size()));
                    JSONArray jsonArray2 = new JSONArray();
                    for (BillDetail prods : GetBillDetailToSync) {
                        JSONObject jsonObject2 = new JSONObject();
                        jsonObject2.put("BID", prod.getBID());
                        jsonObject2.put("BILLNO", prods.getBILLNO());
                        jsonObject2.put("CATEGORY_GUID", prods.getCATEGORY_GUID());
                        jsonObject2.put("SUBCAT_GUID", prods.getSUBCAT_GUID());
                        jsonObject2.put("ITEM_GUID", prods.getITEM_GUID());
                        jsonObject2.put("ITEM_CODE", prods.getITEM_CODE());
                        jsonObject2.put("QTY", prods.getQTY());
                        jsonObject2.put("UOM_GUID", prods.getUOM_GUID());
                        jsonObject2.put("BATCHNO", prods.getBATCHNO());
                        jsonObject2.put("COST_PRICE", prods.getCOST_PRICE());
                        jsonObject2.put("NETVALUE", prods.getNETVALUE());
                        jsonObject2.put("DISC_PERC", prods.getDISCOUNT_PERC());
                        jsonObject2.put("DISC_VALUE", prods.getDISCOUNT_VALUE());
                        jsonObject2.put("TOTVALUE", prods.getTOTALVALUE());
                        jsonObject2.put("MRP", prods.getMRP());
                        jsonObject2.put("HSN", prods.getHSN());
                        jsonObject2.put("CGSTPERCENTAGE", prods.getCGSTPERCENTAGE());
                        jsonObject2.put("SGSTPERCENTAGE", prods.getSGSTPERCENTAGE());
                        jsonObject2.put("IGSTPERCENTAGE", prods.getIGSTPERCENTAGE());
                        jsonObject2.put("ADDITIONALPERCENTAGE", (prods.getADDITIONALPERCENTAGE()));
                        jsonObject2.put("CGST", prods.getCGST());
                        jsonObject2.put("SGST", prods.getSGST());
                        jsonObject2.put("IGST",prods.getIGST());
                        jsonObject2.put("ADDITIONALCESS",prods.getADDITIONALCESS());
                        jsonObject2.put("TOTALGST",prods.getTOTALGST());
                        jsonObject2.put("BILL_DETAIL_STATUS",prods.getBILLDETAILSTATUS());
                        jsonArray2.put(jsonObject2);
                    }
                    jsonObject.put("ObjBillDetails", jsonArray2);
                    // jsonArray.put(jsonObject);
                    GetBillPaymentDetailToSync = new ControllerBillPayDetail().getBillPayDetails(prod.getBID());
                    JSONArray jsonArray3 = new JSONArray();
                    for (BillPayDetail paydetail : GetBillPaymentDetailToSync) {
                        Log.e("RC paydetailsize:- ", String.valueOf(GetBillPaymentDetailToSync.size()));

                        JSONObject jsonObject3 = new JSONObject();
                        jsonObject3.put("BID", prod.getBID());
                        jsonObject3.put("STORE_GUID", prod.getSTORE_GUID());
                        jsonObject3.put("TERMINAL_GUID", prod.getTERMINAL_GUID());
                        jsonObject3.put("BILLNO", prod.getBILLNO());
                        jsonObject3.put("MASTERPAYMODEGUID", paydetail.getMASTERPAYMODEGUID());
                        jsonObject3.put("PAYAMOUNT", paydetail.getPAYAMOUNT());
                        jsonObject3.put("TRANSACTIONNUMBER", paydetail.getTRANSACTIONNUMBER());
                        jsonObject3.put("ADDITIONALPARAM1", paydetail.getADDITIONALPARAM1());
                        jsonObject3.put("ADDITIONALPARAM2", paydetail.getADDITIONALPARAM2());
                        jsonObject3.put("ADDITIONALPARAM3", paydetail.getADDITIONALPARAM3());
                        jsonObject3.put("BILLPAYDETAIL_STATUS", paydetail.getBILLPAYDETAIL_STATUS());
                        jsonArray3.put(jsonObject3);
                    }
                    jsonObject.put("ObjBillPaymentDetails", jsonArray3);
                    jsonArray.put(jsonObject);
                }
                Log.e("Rc Sales",jsonArray.toString());
                Log.e("Rc Sales length", String.valueOf(jsonArray.length()));
                if(jsonArray.length()>0 && GetBillMasterToSync.size()>0){
                    UploadRecord(jsonArray);
                }else{
                    Log.e("Rc Sales","No Records to Upload");
                }
                return Result.success();
            } catch (Throwable throwable) {
                Log.e("Sales", "Error Uploading Record ", throwable);
                return Result.failure();
            }
        }

        private void UploadRecord(JSONArray jsonArray) {
            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonArray.toString());
            Call<SyncResponse> call = apiService.UploadSaleRecords(Constants.Authorization, body);
            call.enqueue(new Callback<SyncResponse>() {
                @Override
                public void onResponse(Call<SyncResponse> call, Response<SyncResponse> response) {
                    try {
                        //  progressDialog.dismiss();
                        Log.d("Rc sales:- ", String.valueOf(response.code()));
                        Log.d("PrintingResponseBody", "onResponse: "+response.body());
                        if (response.isSuccessful()) {
                            Log.d("Rc Response for sales :", response.body().getMessage() + "   " + response.body().getOutputValuesKeys());
                            List<String> items = Arrays.asList(response.body().getOutputValuesKeys().split("\\s*,\\s*"));
                            for (String i : items) {
                                Log.d("Rc OutputValuesKeys:- ","Key:"+ i);
                                Boolean val = new ControllerBillMaster().updateIsSync(i);
                                Log.d("Rc Updatesaledata:- ", String.valueOf(val));

                                if(!i.isEmpty()){
                                    Toast.makeText(ApplicationContextProvider.getContext(),"Data Successfully Synced!",Toast.LENGTH_LONG).show();

                                }else {
                                    Toast.makeText(ApplicationContextProvider.getContext(),"Sync Failed!",Toast.LENGTH_LONG).show();

                                }

                            }
                            Log.d("Rc Response for :", "Sales Record Synced Successfully");

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<SyncResponse> call, Throwable t) {
                    //  progressDialog.dismiss();
                    Log.e("In sales Error", t.getMessage());
                }
            });
        }
    }

