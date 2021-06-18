package com.retailstreet.mobilepos.Database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.retailstreet.mobilepos.Controller.DBReadyCallback;
import com.retailstreet.mobilepos.Model.BankDetails;
import com.retailstreet.mobilepos.Model.BillDetail;
import com.retailstreet.mobilepos.Model.BillMaster;
import com.retailstreet.mobilepos.Model.BillPayDetail;
import com.retailstreet.mobilepos.Model.CreditBillDetails;
import com.retailstreet.mobilepos.Model.CustomerAddress;
import com.retailstreet.mobilepos.Model.CustomerCredit;
import com.retailstreet.mobilepos.Model.CustomerLedger;
import com.retailstreet.mobilepos.Model.CustomerMaster;
import com.retailstreet.mobilepos.Model.CustomerReject;
import com.retailstreet.mobilepos.Model.CustomerReturnDetails;
import com.retailstreet.mobilepos.Model.CustomerReturnMaster;
import com.retailstreet.mobilepos.Model.DeliveryTypeMaster;
import com.retailstreet.mobilepos.Model.GRNDetails;
import com.retailstreet.mobilepos.Model.GRNMaster;
import com.retailstreet.mobilepos.Model.GroupUserMaster;
import com.retailstreet.mobilepos.Model.HSNMaster;
import com.retailstreet.mobilepos.Model.MasterCategory;
import com.retailstreet.mobilepos.Model.MasterCustomerType;
import com.retailstreet.mobilepos.Model.MasterState;
import com.retailstreet.mobilepos.Model.MasterSubcategory;
import com.retailstreet.mobilepos.Model.MasterUOM;
import com.retailstreet.mobilepos.Model.PaymentModeMaster;
import com.retailstreet.mobilepos.Model.ProductMaster;
import com.retailstreet.mobilepos.Model.RetailStore;
import com.retailstreet.mobilepos.Model.ShiftMaster;
import com.retailstreet.mobilepos.Model.ShiftTrans;
import com.retailstreet.mobilepos.Model.StockMaster;
import com.retailstreet.mobilepos.Model.StockRegister;
import com.retailstreet.mobilepos.Model.StoreConfiguration;
import com.retailstreet.mobilepos.Model.StoreParameter;
import com.retailstreet.mobilepos.Model.TerminalConfiguration;
import com.retailstreet.mobilepos.Model.TerminalUserAllocation;
import com.retailstreet.mobilepos.Model.VendorDetailReturn;
import com.retailstreet.mobilepos.Model.VendorMaster;
import com.retailstreet.mobilepos.Model.VendorMasterReturn;
import com.retailstreet.mobilepos.Model.VendorPayDetail;
import com.retailstreet.mobilepos.Model.VendorPayMaster;
import com.retailstreet.mobilepos.Model.VendorRejectReason;
import com.retailstreet.mobilepos.Utils.ApiInterface;
import com.retailstreet.mobilepos.Utils.CallbackWithRetry;
import com.retailstreet.mobilepos.Utils.Constants;
import com.retailstreet.mobilepos.Utils.RetroSync;
import com.retailstreet.mobilepos.View.dialog.ProgressBarDialog;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Response;


/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */


public class TableDataInjector {
    static ExecutorService injectExecutor = Executors.newSingleThreadExecutor();
    Activity activity;
    String dbname = "MasterDB";
    private Context context;
    String baseUrl = "http://99retailstreet.com:8080/";
    String storeId;
    String terminal_id;
    private static DBReadyCallback dbReadyCallback;
    private List<GroupUserMaster> groupUserMasterList = null;
    private List<CustomerMaster> retailCustList = null;
    private List<ProductMaster> productMasterList = null;
    private List<StockMaster> stockMasterList = null;
    private List<BillDetail> billDetailList = null;
    private List<BillMaster> billMasterList = null;
    private List<RetailStore> retailStoreList = null;
    private List<TerminalUserAllocation> terminalUserAllocations = null;
    private List<TerminalConfiguration> terminalConfigurations = null;
    private List<ShiftMaster> shiftMasters = null;
    private List<DeliveryTypeMaster> deliveryTypeMasters = null;
    private List<PaymentModeMaster> paymentModeMasters = null;
    private List<BillPayDetail> billPayDetailList = null;
    private List<ShiftTrans> shiftTransList = null;
    private List<MasterCategory> masterCategoryList = null;
    private List<MasterSubcategory> masterSubcategoryList = null;
    private List<HSNMaster> hsnMasterList = null;
    private List<VendorMaster> vendorMasterList = null;
    private List<MasterUOM> masterUOMList = null;
    private List<MasterCustomerType> masterCustomerTypeList = null;
    private List<CustomerAddress> customerAddressList = null;
    private List<MasterState> masterStateList = null;
    private List<BankDetails> bankDetailsList = null;
    private List<StoreConfiguration> storeConfigurationList = null;
    private List<CustomerReturnMaster> customerReturnMasterList = null;
    private List<CustomerReturnDetails> customerReturnDetailsList = null;
    private List<CustomerReject> customerRejectList = null;
    private List<CustomerCredit> customerCreditList = null;
    private List<CustomerLedger> customerLedgerList = null;
    private List<CreditBillDetails> creditBillDetails = null;
    private List<StockRegister> stockRegisterList = null;
    private List<GRNDetails> grnDetailsList = null;
    private List<GRNMaster> grnMasterList = null;
    private List<VendorPayDetail> vendorPayDetailList = null;
    private List<VendorPayMaster> vendorPayMasterList = null;
    private List<VendorRejectReason> vendorRejectReasonList = null;
    private List<VendorDetailReturn> vendorDetailReturnList = null;
    private List<VendorMasterReturn> vendorMasterReturnList = null;
    private List<StoreParameter> storeParameterList = null;

   // private static LoadingDialog loadingDialog;

    private static ProgressBarDialog progressBarDialog;

    public static int status =0;
    public static final int tableConstant=38;

    public TableDataInjector(Activity context, String storeid,DBReadyCallback callback, String terminal_id) {

        activity = context;
        this.context = context.getBaseContext();
        this.storeId = storeid;
        this.terminal_id=terminal_id;
        dbReadyCallback=callback;
        status=0;
        progressBarDialog=new ProgressBarDialog(activity);
        progressBarDialog.show();
        injectAll();

    }


    public void injectAll(){

        getUserMasterList();
        getRetailCustList();
        getProductMasterList();
        getStockMasterList();
        getBillDetails();
        getBillMaster();
        getRetailStore();
        getTerminalUserAlloc();
        getTerminalConfig();
        getShiftMaster();
        getDeliveryType();
        getPaymentMode();
        getBillPayDetail();
        getShiftTransactions();
        getMasterCategory();
        getMasterSubCategory();
        getHsnMaster();
        getVendorMaster();
        getMasterUom();
        getMasterCustomerType();
        getCustomerAddress();
        getMasterState();
        getBankMaster();
        getStoreConfig();
        getCustomerReturnMaster();
        getCustomerReturnDetails();
        getCustomerReject();
        getCustomerCredit();
        getCustomerLedger();
        getCreditBillDetails();
        getStockRegister();
        getGrnDetails();
        getGrnMasters();
        getVendorPayDetails();
        getVendorPayMaster();
        getVendorRejectReason();
        getVendorDetailReturn();
        getVendorMasterReturn();
        getStoreParameter();

    }



    public void getUserMasterList() {
        try {
            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            Call<List<GroupUserMaster>> call1 = apiService.loadGroupUserMaster(Constants.Authorization, storeId,terminal_id);
            call1.enqueue(new CallbackWithRetry<List<GroupUserMaster>>() {
                @Override
                public void onResponse(Call<List<GroupUserMaster>> call, Response<List<GroupUserMaster>> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try {
                                groupUserMasterList = response.body();
                                injectExecutor.submit(() -> InsertDataGroupUserMaster(groupUserMasterList));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("InsertionEmpty", "GroupUserMaster: ");
                            Toast.makeText(context, "Please Insert Valid Store ID!", Toast.LENGTH_LONG).show();
                            interruptTask();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<GroupUserMaster>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                      super.onFailure(call,t);
                     //cancelDialog();
                    //Toast.makeText(ApplicationContextProvider.getContext(),"Network Error Download Failed !",Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getBillMaster() {
        try {
            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            Call<List<BillMaster>> call = apiService.loadBillMaster(Constants.Authorization, storeId);

            call.enqueue(new CallbackWithRetry<List<BillMaster>>() {
                @Override
                public void onResponse(Call<List<BillMaster>> call, Response<List<BillMaster>> response) {


                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            billMasterList = response.body();
                            Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                            try{
                                injectExecutor.submit(() -> InsertBillMaster(billMasterList));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("InsertionEmpty", "BillMAster: ");
                            status++;
                            if (status == tableConstant) {
                                finishTask();
                            } else {
                                updateProgress(status);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<BillMaster>> call, Throwable t) {
                    super.onFailure(call,t);
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }
    public void getRetailCustList() {
        try {
            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            Call<List<CustomerMaster>> call = apiService.loadMasterCustomer(Constants.Authorization, storeId);
            call.enqueue(new CallbackWithRetry<List<CustomerMaster>>() {
                @Override
                public void onResponse(Call<List<CustomerMaster>> call, Response<List<CustomerMaster>> response) {

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try{
                                retailCustList = response.body();
                                Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                                injectExecutor.submit(() -> InsertRetailCust(retailCustList));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("InsertionEmpty", "CustomerMaster: ");
                            status++;
                            if (status == tableConstant) {
                                finishTask();
                            } else {
                                updateProgress(status);
                            }
                        }
                    }


                }

                @Override
                public void onFailure(Call<List<CustomerMaster>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                    super.onFailure(call,t);
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getProductMasterList() {
        try {
            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            Call<List<ProductMaster>> call = apiService.loadProductMaster(Constants.Authorization, storeId);
            call.enqueue(new CallbackWithRetry<List<ProductMaster>>() {
                @Override
                public void onResponse(Call<List<ProductMaster>> call, Response<List<ProductMaster>> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try{
                                productMasterList = response.body();
                                Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                                injectExecutor.submit(() -> InsertProductMaster(productMasterList));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("InsertionEmpty", "ProductMaster: ");
                            status++;
                            if (status == tableConstant) {
                                finishTask();
                            } else {
                                updateProgress(status);
                            }
                        }
                    }


                }

                @Override
                public void onFailure(Call<List<ProductMaster>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                    super.onFailure(call,t);
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getStockMasterList() {
        try {

            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            Call<List<StockMaster>> call = apiService.loadStockMaster(Constants.Authorization, storeId);
            call.enqueue(new CallbackWithRetry<List<StockMaster>>() {
                @Override
                public void onResponse(Call<List<StockMaster>> call, Response<List<StockMaster>> response) {

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try{
                                stockMasterList = response.body();
                                Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                                injectExecutor.submit(() -> InsertStockMaster(stockMasterList));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("InsertionEmpty", "StockMaster: ");
                            status++;
                            if (status == tableConstant) {
                                finishTask();
                            } else {
                                updateProgress(status);
                            }
                        }
                    }

                }

                @Override
                public void onFailure(Call<List<StockMaster>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                    super.onFailure(call,t);
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getBillDetails() {
        try {
            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            Call<List<BillDetail>> call = apiService.loadBillDetail(Constants.Authorization, storeId);
            call.enqueue(new CallbackWithRetry<List<BillDetail>>() {
                @Override
                public void onResponse(Call<List<BillDetail>> call, Response<List<BillDetail>> response) {

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try{
                                billDetailList = response.body();
                                Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                                injectExecutor.submit(() -> InsertBillDetails(billDetailList));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("InsertionEmpty", "BillDetail: ");
                            status++;
                            if (status == tableConstant) {
                                finishTask();
                            } else {
                                updateProgress(status);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<BillDetail>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                    super.onFailure(call,t);
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getRetailStore() {
        try {
            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            Call<List<RetailStore>> call = apiService.loadRetailStore(Constants.Authorization, storeId);
            call.enqueue(new CallbackWithRetry<List<RetailStore>>() {
                @Override
                public void onResponse(Call<List<RetailStore>> call, Response<List<RetailStore>> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try{
                                retailStoreList = response.body();
                                Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                                injectExecutor.submit(() -> InsertRetailStore(retailStoreList));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("InsertionEmpty", "RetailStore: ");
                            Toast.makeText(context, "Please Insert Valid Store ID!", Toast.LENGTH_LONG).show();
                            interruptTask();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<RetailStore>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                    super.onFailure(call,t);
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getTerminalUserAlloc() {
        try {
            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            Call<List<TerminalUserAllocation>> call = apiService.loadTerminalUserAllocationterminal(Constants.Authorization, storeId,terminal_id);
            call.enqueue(new CallbackWithRetry<List<TerminalUserAllocation>>() {
                @Override
                public void onResponse(@NonNull Call<List<TerminalUserAllocation>> call, Response<List<TerminalUserAllocation>> response) {


                    if (response.isSuccessful()) {
                            if (response.code() == 200) {
                                try{
                                    terminalUserAllocations = response.body();
                                    Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                                    injectExecutor.submit(() -> InsertTerminalUserAlloc(terminalUserAllocations));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.d("InsertionEmpty", "TerminalUserAllocation: ");
                                status++;
                                if (status == tableConstant) {
                                    finishTask();
                                } else {
                                    updateProgress(status);
                                }
                            }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<TerminalUserAllocation>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                    super.onFailure(call,t);
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getTerminalConfig() {
        try {
            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            Call<List<TerminalConfiguration>> call = apiService.loadTerminalConfiguration(Constants.Authorization, storeId,terminal_id);
            call.enqueue(new CallbackWithRetry<List<TerminalConfiguration>>() {
                @Override
                public void onResponse(Call<List<TerminalConfiguration>> call, Response<List<TerminalConfiguration>> response) {

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try{
                                terminalConfigurations = response.body();
                                Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                                injectExecutor.submit(() -> InsertTerminalConnfig(terminalConfigurations));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("InsertionEmpty", "TerminalUserAllocation: ");
                            status++;
                            if (status == tableConstant) {
                                finishTask();
                            } else {
                                updateProgress(status);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<TerminalConfiguration>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                    super.onFailure(call,t);
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getShiftMaster() {
        try {
            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            Call<List<ShiftMaster>> call = apiService.loadShiftMaster(Constants.Authorization, storeId,terminal_id);
            call.enqueue(new CallbackWithRetry<List<ShiftMaster>>() {
                @Override
                public void onResponse(Call<List<ShiftMaster>> call, Response<List<ShiftMaster>> response) {

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try{
                                shiftMasters = response.body();
                                Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                                injectExecutor.submit(() ->  InsertShiftMaster(shiftMasters));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("InsertionEmpty", "ShiftMaster: ");
                            status++;
                            if (status == tableConstant) {
                                finishTask();
                            } else {
                                updateProgress(status);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<ShiftMaster>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                    super.onFailure(call,t);
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getDeliveryType() {
        try {
            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            Call<List<DeliveryTypeMaster>> call = apiService.loadMasterDeliveryType(Constants.Authorization, storeId);

            call.enqueue(new CallbackWithRetry<List<DeliveryTypeMaster>>() {
                @Override
                public void onResponse(Call<List<DeliveryTypeMaster>> call, Response<List<DeliveryTypeMaster>> response) {

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try{
                                deliveryTypeMasters = response.body();
                                Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                                injectExecutor.submit(() -> InsertDeliveryType(deliveryTypeMasters));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("InsertionEmpty", "DeliveryTypeMaster: ");
                            status++;
                            if (status == tableConstant) {
                                finishTask();
                            } else {
                                updateProgress(status);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<DeliveryTypeMaster>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                    super.onFailure(call,t);
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getPaymentMode() {
        try {
            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            Call<List<PaymentModeMaster>> call = apiService.loadMasterPayMode(Constants.Authorization, storeId);
            call.enqueue(new CallbackWithRetry<List<PaymentModeMaster>>() {
                @Override
                public void onResponse(Call<List<PaymentModeMaster>> call, Response<List<PaymentModeMaster>> response) {


                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try{
                                paymentModeMasters = response.body();
                                Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                                injectExecutor.submit(() -> InsertPaymentModeMaster(paymentModeMasters));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("InsertionEmpty", "PaymentModeMaster: ");
                            status++;
                            if (status == tableConstant) {
                                finishTask();
                            } else {
                                updateProgress(status);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<PaymentModeMaster>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                    super.onFailure(call,t);
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getBillPayDetail() {
        try {
            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            Call<List<BillPayDetail>> call = apiService.loadBillPayDetail(Constants.Authorization, storeId);
            call.enqueue(new CallbackWithRetry<List<BillPayDetail>>() {
                @Override
                public void onResponse(Call<List<BillPayDetail>> call, Response<List<BillPayDetail>> response) {


                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try{
                                billPayDetailList = response.body();
                                Log.i("autolog", "RetrievedTabaleData" + response.body().toString());

                                injectExecutor.submit(() -> InsertBillPayDetail(billPayDetailList));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("InsertionEmpty", "BillPayDetail: ");
                            status++;
                            if (status == tableConstant) {
                                finishTask();
                            } else {
                                updateProgress(status);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<BillPayDetail>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                    super.onFailure(call,t);
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getShiftTransactions() {
        try {
            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            Call<List<ShiftTrans>> call = apiService.loadShifttrans(Constants.Authorization, storeId,terminal_id);
            call.enqueue(new CallbackWithRetry<List<ShiftTrans>>() {
                @Override
                public void onResponse(Call<List<ShiftTrans>> call, Response<List<ShiftTrans>> response) {


                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try{
                                shiftTransList = response.body();
                                Log.i("autolog", "RetrievedTabaleData" + response.body().toString());

                                injectExecutor.submit(() -> InsertShiftTrans(shiftTransList));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("InsertionEmpty", "ShiftTrans: ");
                            status++;
                            if (status == tableConstant) {
                                finishTask();
                            } else {
                                updateProgress(status);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<ShiftTrans>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                    super.onFailure(call,t);
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getMasterCategory() {
        try {
            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            Call<List<MasterCategory>> call = apiService.loadMasterCategory(Constants.Authorization, storeId);
            call.enqueue(new CallbackWithRetry<List<MasterCategory>>() {
                @Override
                public void onResponse(Call<List<MasterCategory>> call, Response<List<MasterCategory>> response) {

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try{
                                masterCategoryList = response.body();
                                Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                                injectExecutor.submit(() -> InsertMasterCategory(masterCategoryList));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("InsertionEmpty", "MasterCategory: ");
                            status++;
                            if (status == tableConstant) {
                                finishTask();
                            } else {
                                updateProgress(status);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<MasterCategory>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                    super.onFailure(call,t);
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getMasterSubCategory() {
        try {
            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            Call<List<MasterSubcategory>> call = apiService.loadMasterSubCategory(Constants.Authorization, storeId);
            call.enqueue(new CallbackWithRetry<List<MasterSubcategory>>() {
                @Override
                public void onResponse(Call<List<MasterSubcategory>> call, Response<List<MasterSubcategory>> response) {

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try{
                                masterSubcategoryList = response.body();
                                Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                                injectExecutor.submit(() -> InsertMasterSubCategory(masterSubcategoryList));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("InsertionEmpty", "MasterSubcategory: ");
                            status++;
                            if (status == tableConstant) {
                                finishTask();
                            } else {
                                updateProgress(status);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<MasterSubcategory>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                    super.onFailure(call,t);
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }


    public void getHsnMaster() {
        try {
            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            Call<List<HSNMaster>> call = apiService.loadHsnMaster(Constants.Authorization, storeId);
            call.enqueue(new CallbackWithRetry<List<HSNMaster>>() {
                @Override
                public void onResponse(Call<List<HSNMaster>> call, Response<List<HSNMaster>> response) {

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try{
                                hsnMasterList = response.body();
                                Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                                injectExecutor.submit(() -> InsertHsnMaster(hsnMasterList));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("InsertionEmpty", "HSNMaster: ");
                            status++;
                            if (status == tableConstant) {
                                finishTask();
                            } else {
                                updateProgress(status);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<HSNMaster>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                    super.onFailure(call,t);
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }


    public void getVendorMaster() {
        try {
            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            Call<List<VendorMaster>> call = apiService.loadVendorMaster(Constants.Authorization, storeId);
            call.enqueue(new CallbackWithRetry<List<VendorMaster>>() {
                @Override
                public void onResponse(Call<List<VendorMaster>> call, Response<List<VendorMaster>> response) {

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try{
                                vendorMasterList = response.body();
                                Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                                injectExecutor.submit(() -> InsertVendorMaster(vendorMasterList));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("InsertionEmpty", "VendorMaster: ");
                            status++;
                            if (status == tableConstant) {
                                finishTask();
                            } else {
                                updateProgress(status);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<VendorMaster>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                    super.onFailure(call,t);
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }


    public void getMasterUom() {
        try {
            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            Call<List<MasterUOM>> call = apiService.loadUomMaster(Constants.Authorization, storeId);
            call.enqueue(new CallbackWithRetry<List<MasterUOM>>() {
                @Override
                public void onResponse(Call<List<MasterUOM>> call, Response<List<MasterUOM>> response) {

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try{
                                masterUOMList = response.body();
                                Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                                injectExecutor.submit(() -> InsertMasterUom(masterUOMList));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("InsertionEmpty", "MasterUOM: ");
                            status++;
                            if (status == tableConstant) {
                                finishTask();
                            } else {
                                updateProgress(status);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<MasterUOM>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                    super.onFailure(call,t);
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getMasterCustomerType() {
        try {
            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            Call<List<MasterCustomerType>> call = apiService.loadMasterCustomerType(Constants.Authorization, storeId);
            call.enqueue(new CallbackWithRetry<List<MasterCustomerType>>() {
                @Override
                public void onResponse(Call<List<MasterCustomerType>> call, Response<List<MasterCustomerType>> response) {

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try{
                                masterCustomerTypeList = response.body();
                                Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                                injectExecutor.submit(() -> InsertCustomerType(masterCustomerTypeList));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("InsertionEmpty", "MasterCustomerType: ");
                            status++;
                            if (status == tableConstant) {
                                finishTask();
                            } else {
                                updateProgress(status);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<MasterCustomerType>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                    super.onFailure(call,t);
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getCustomerAddress() {
        try {
            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            Call<List<CustomerAddress>> call = apiService.loadMasterCustomerAddress(Constants.Authorization, storeId);
            call.enqueue(new CallbackWithRetry<List<CustomerAddress>>() {
                @Override
                public void onResponse(Call<List<CustomerAddress>> call, Response<List<CustomerAddress>> response) {

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try{
                                customerAddressList = response.body();
                                Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                                injectExecutor.submit(() -> InsertCustomerAddress(customerAddressList));


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("InsertionEmpty", "CustomerAddress: ");
                            status++;
                            if (status == tableConstant) {
                                finishTask();
                            } else {
                                updateProgress(status);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<CustomerAddress>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                    super.onFailure(call,t);
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }


    public void getMasterState() {
        try {

            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            Call<List<MasterState>> call = apiService.loadmasterstate(Constants.Authorization, storeId);

            call.enqueue(new CallbackWithRetry<List<MasterState>>() {
                @Override
                public void onResponse(Call<List<MasterState>> call, Response<List<MasterState>> response) {

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try{
                                masterStateList = response.body();
                                Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                                injectExecutor.submit(() -> InsertMasterState(masterStateList));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("InsertionEmpty", "MasterState: ");
                            status++;
                            if (status == tableConstant) {
                                finishTask();
                            } else {
                                updateProgress(status);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<MasterState>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                    super.onFailure(call,t);
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getBankMaster() {
        try {
            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            Call<List<BankDetails>> call = apiService.loadBankMaster(Constants.Authorization, storeId);

            call.enqueue(new CallbackWithRetry<List<BankDetails>>() {
                @Override
                public void onResponse(Call<List<BankDetails>> call, Response<List<BankDetails>> response) {


                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try{
                                bankDetailsList= response.body();
                                Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                                injectExecutor.submit(() -> InsertBankMaster(bankDetailsList));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("InsertionEmpty", "BankDetails: ");
                            status++;
                            if (status == tableConstant) {
                                finishTask();
                            } else {
                                updateProgress(status);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<BankDetails>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                    super.onFailure(call,t);
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getStoreConfig() {
        try {
            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            Call<List<StoreConfiguration>> call = apiService.loadStoreConfiguration(Constants.Authorization, storeId);
            call.enqueue(new CallbackWithRetry<List<StoreConfiguration>>() {
                @Override
                public void onResponse(Call<List<StoreConfiguration>> call, Response<List<StoreConfiguration>> response) {

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try{
                                storeConfigurationList= response.body();
                                Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                                injectExecutor.submit(() -> InsertStoreConfig(storeConfigurationList));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("InsertionEmpty", "StoreConfiguration: ");
                            status++;
                            if (status == tableConstant) {
                                finishTask();
                            } else {
                                updateProgress(status);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<StoreConfiguration>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                    super.onFailure(call,t);
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }


    }
    public void getCustomerReturnMaster() {
        try {
            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            Call<List<CustomerReturnMaster>> call = apiService.loadCustomerReturnMaster(Constants.Authorization, storeId);
            call.enqueue(new CallbackWithRetry<List<CustomerReturnMaster>>() {
                @Override
                public void onResponse(Call<List<CustomerReturnMaster>> call, Response<List<CustomerReturnMaster>> response) {


                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try{
                                customerReturnMasterList = response.body();
                                Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                                injectExecutor.submit(() -> InsertCustomerReturnMaster(customerReturnMasterList));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("InsertionEmpty", "CustomerReturnMaster: ");
                            status++;
                            if (status == tableConstant) {
                                finishTask();
                            } else {
                                updateProgress(status);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<CustomerReturnMaster>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                    super.onFailure(call,t);
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }


    public void getCustomerReturnDetails() {
        try {
            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            Call<List<CustomerReturnDetails>> call = apiService.loadCustomerReturnDetail(Constants.Authorization, storeId);
            call.enqueue(new CallbackWithRetry<List<CustomerReturnDetails>>() {
                @Override
                public void onResponse(Call<List<CustomerReturnDetails>> call, Response<List<CustomerReturnDetails>> response) {


                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try{
                                customerReturnDetailsList = response.body();
                                Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                                injectExecutor.submit(() -> InsertCustomerReturnDetails(customerReturnDetailsList));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("InsertionEmpty", "CustomerReturnDetails: ");
                            status++;
                            if (status == tableConstant) {
                                finishTask();
                            } else {
                                updateProgress(status);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<CustomerReturnDetails>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                    super.onFailure(call,t);
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getCustomerReject() {
        try {
            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            Call<List<CustomerReject>> call = apiService.loadRetailStoreCustReject(Constants.Authorization, storeId);
            call.enqueue(new CallbackWithRetry<List<CustomerReject>>() {
                @Override
                public void onResponse(Call<List<CustomerReject>> call, Response<List<CustomerReject>> response) {

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try{
                                customerRejectList= response.body();
                                Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                                injectExecutor.submit(() -> InsertCustomerReject(customerRejectList));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("InsertionEmpty", "CustomerReject: ");
                            status++;
                            if (status == tableConstant) {
                                finishTask();
                            } else {
                                updateProgress(status);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<CustomerReject>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                    super.onFailure(call,t);
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }


    public void getCustomerCredit() {
        try {
            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            Call<List<CustomerCredit>> call = apiService.loadRetailCreditCust(Constants.Authorization, storeId);
            call.enqueue(new CallbackWithRetry<List<CustomerCredit>>() {
                @Override
                public void onResponse(Call<List<CustomerCredit>> call, Response<List<CustomerCredit>> response) {

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try{
                                customerCreditList= response.body();
                                Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                                injectExecutor.submit(() -> InsertCustomerCredit(customerCreditList));


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("InsertionEmpty", "CustomerCredit: ");
                            status++;
                            if (status == tableConstant) {
                                finishTask();
                            } else {
                                updateProgress(status);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<CustomerCredit>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                    super.onFailure(call,t);
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getCreditBillDetails() {
        try {
            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            Call<List<CreditBillDetails>> call = apiService.loadRetailCreditBillDetails(Constants.Authorization, storeId);
            call.enqueue(new CallbackWithRetry<List<CreditBillDetails>>() {
                @Override
                public void onResponse(Call<List<CreditBillDetails>> call, Response<List<CreditBillDetails>> response) {

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try{
                                creditBillDetails= response.body();
                                Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                                injectExecutor.submit(() -> InsertCreditBillDetails(creditBillDetails));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("InsertionEmpty", "CreditBillDetails: ");
                            status++;
                            if (status == tableConstant) {
                                finishTask();
                            } else {
                                updateProgress(status);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<CreditBillDetails>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                    super.onFailure(call,t);
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getCustomerLedger() {
        try {
            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            Call<List<CustomerLedger>> call = apiService.loadCustomerLedger(Constants.Authorization, storeId);
            call.enqueue(new CallbackWithRetry<List<CustomerLedger>>() {
                @Override
                public void onResponse(Call<List<CustomerLedger>> call, Response<List<CustomerLedger>> response) {

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try{
                                customerLedgerList= response.body();
                                Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                                injectExecutor.submit(() -> InsertCustomerLedger(customerLedgerList));


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("InsertionEmpty", "CustomerLedger: ");
                            status++;
                            if (status == tableConstant) {
                                finishTask();
                            } else {
                                updateProgress(status);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<CustomerLedger>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                    super.onFailure(call,t);
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getStockRegister() {
        try {
            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            Call<List<StockRegister>> call = apiService.loadStockRegister(Constants.Authorization, storeId);
            call.enqueue(new CallbackWithRetry<List<StockRegister>>() {
                @Override
                public void onResponse(Call<List<StockRegister>> call, Response<List<StockRegister>> response) {

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try{
                                stockRegisterList= response.body();
                                Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                                injectExecutor.submit(() -> InsertStockRegister(stockRegisterList));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("InsertionEmpty", "StockRegister: ");
                            status++;
                            if (status == tableConstant) {
                                finishTask();
                            } else {
                                updateProgress(status);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<StockRegister>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                    super.onFailure(call,t);
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }
    public void getGrnDetails() {
        try {
            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            Call<List<GRNDetails>> call = apiService.loadGrnDetail(Constants.Authorization, storeId);
            call.enqueue(new CallbackWithRetry<List<GRNDetails>>() {
                @Override
                public void onResponse(Call<List<GRNDetails>> call, Response<List<GRNDetails>> response) {


                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try{
                                grnDetailsList= response.body();
                                Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                                injectExecutor.submit(() -> InsertGrnDetails(grnDetailsList));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("InsertionEmpty", "GRNDetails: ");
                            status++;
                            if (status == tableConstant) {
                                finishTask();
                            } else {
                                updateProgress(status);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<GRNDetails>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                    super.onFailure(call,t);
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }


    public void getGrnMasters() {
        try {
            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            Call<List<GRNMaster>> call = apiService.loadGrnMaster(Constants.Authorization, storeId);

            call.enqueue(new CallbackWithRetry<List<GRNMaster>>() {
                @Override
                public void onResponse(Call<List<GRNMaster>> call, Response<List<GRNMaster>> response) {


                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try{
                                grnMasterList= response.body();
                                Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                                injectExecutor.submit(() -> InsertGrnMasters(grnMasterList));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("InsertionEmpty", "GRNMaster: ");
                            status++;
                            if (status == tableConstant) {
                                finishTask();
                            } else {
                                updateProgress(status);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<GRNMaster>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                    super.onFailure(call,t);
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getVendorPayDetails() {
        try {
            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            Call<List<VendorPayDetail>> call = apiService.loadVendorPayDetail(Constants.Authorization, storeId);
            call.enqueue(new CallbackWithRetry<List<VendorPayDetail>>() {
                @Override
                public void onResponse(Call<List<VendorPayDetail>> call, Response<List<VendorPayDetail>> response) {


                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try{
                                vendorPayDetailList= response.body();
                                Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                                injectExecutor.submit(() -> InsertVendorPayDetails(vendorPayDetailList));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("InsertionEmpty", "VendorPayDetail: ");
                            status++;
                            if (status == tableConstant) {
                                finishTask();
                            } else {
                                updateProgress(status);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<VendorPayDetail>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                    super.onFailure(call,t);
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getVendorPayMaster() {
        try {
            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            Call<List<VendorPayMaster>> call = apiService.loadVendorPayMaster(Constants.Authorization, storeId);
            call.enqueue(new CallbackWithRetry<List<VendorPayMaster>>() {
                @Override
                public void onResponse(Call<List<VendorPayMaster>> call, Response<List<VendorPayMaster>> response) {

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try{
                                vendorPayMasterList= response.body();
                                Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                                injectExecutor.submit(() -> InsertVendorPayMaster(vendorPayMasterList));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("InsertionEmpty", "VendorPayMaster: ");
                            status++;
                            if (status == tableConstant) {
                                finishTask();
                            } else {
                                updateProgress(status);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<VendorPayMaster>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                    super.onFailure(call,t);
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getVendorRejectReason() {
        try {
            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            Call<List<VendorRejectReason>> call = apiService.loadRetailStoreVendReject(Constants.Authorization, storeId);
            call.enqueue(new CallbackWithRetry<List<VendorRejectReason>>() {
                @Override
                public void onResponse(Call<List<VendorRejectReason>> call, Response<List<VendorRejectReason>> response) {

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try{
                                vendorRejectReasonList= response.body();
                                Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                                injectExecutor.submit(() -> InsertVendorReject(vendorRejectReasonList));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("InsertionEmpty", "VendorRejectReason: ");
                            status++;
                            if (status == tableConstant) {
                                finishTask();
                            } else {
                                updateProgress(status);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<VendorRejectReason>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                    super.onFailure(call,t);
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getVendorDetailReturn() {
        try {
            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            Call<List<VendorDetailReturn>> call = apiService.loadVendorReturnDetail(Constants.Authorization, storeId);
            call.enqueue(new CallbackWithRetry<List<VendorDetailReturn>>() {
                @Override
                public void onResponse(Call<List<VendorDetailReturn>> call, Response<List<VendorDetailReturn>> response) {

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try{
                                vendorDetailReturnList= response.body();
                                Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                                injectExecutor.submit(() -> InsertVendorDetailReturn(vendorDetailReturnList));


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("InsertionEmpty", "VendorDetailReturn: ");
                            status++;
                            if (status == tableConstant) {
                                finishTask();
                            } else {
                                updateProgress(status);
                            }
                        }
                    }

                }
                @Override
                public void onFailure(Call<List<VendorDetailReturn>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                    super.onFailure(call,t);
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getVendorMasterReturn() {
        try {
            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            Call<List<VendorMasterReturn>> call = apiService.loadVendorReturnMaster(Constants.Authorization, storeId);
            call.enqueue(new CallbackWithRetry<List<VendorMasterReturn>>() {
                @Override
                public void onResponse(Call<List<VendorMasterReturn>> call, Response<List<VendorMasterReturn>> response) {


                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try{
                                vendorMasterReturnList= response.body();
                                Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                                injectExecutor.submit(() -> InsertVendorMasterReturn(vendorMasterReturnList));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("InsertionEmpty", "VendorMasterReturn: ");
                            status++;
                            if (status == tableConstant) {
                                finishTask();
                            } else {
                                updateProgress(status);
                            }
                        }
                    }

                }
                @Override
                public void onFailure(Call<List<VendorMasterReturn>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                    super.onFailure(call,t);
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getStoreParameter() {
        try {
            ApiInterface apiService = RetroSync.getSyncBase().create(ApiInterface.class);
            Call<List<StoreParameter>> call = apiService.loadStoreParameter(Constants.Authorization, storeId);
            call.enqueue(new CallbackWithRetry<List<StoreParameter>>() {
                @Override
                public void onResponse(Call<List<StoreParameter>> call, Response<List<StoreParameter>> response) {


                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try{
                                storeParameterList= response.body();
                                Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                                injectExecutor.submit(() -> InsertStoreParameter(storeParameterList));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("InsertionEmpty", "StoreParameter: ");
                            status++;
                            if (status == tableConstant) {
                                finishTask();
                            } else {
                                updateProgress(status);
                            }
                        }
                    }

                }
                @Override
                public void onFailure(Call<List<StoreParameter>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                    super.onFailure(call,t);
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }


    public void InsertStoreParameter(List<StoreParameter> list) {
        if (list == null) {
            return;
        }
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
            myDataBase.delete("Store_Parameters", null, null);

            for (StoreParameter prod : list) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("STORE_ID", prod.getSTORE_ID());
                contentValues.put("STORE_GUID", prod.getSTORE_GUID());
                contentValues.put("Vendoraddition", prod.getVendoraddition());
                contentValues.put("Productaddition", prod.getProductaddition());
                contentValues.put("StockEntry", prod.getStockEntry());
                contentValues.put("Stockadjustments", prod.getStockadjustments());
                contentValues.put("Vendorpayment", prod.getVendorpayment());
                contentValues.put("Vendorreturns", prod.getVendorreturns());
                contentValues.put("Creditsales", prod.getCreditsales());
                contentValues.put("Estimates", prod.getEstimates());
                contentValues.put("Systemsetting", prod.getSystemsetting());
                contentValues.put("Storeaddress", prod.getStoreaddress());
                contentValues.put("Reports", prod.getReports());
                contentValues.put("Printersetting", prod.getPrintersetting());
                contentValues.put("Additionalparam1", prod.getAdditionalparam1());
                contentValues.put("Looseitem", prod.getLooseitem());
                contentValues.put("GST_Price", prod.getGST_Price());
                contentValues.put("Cash_drawer", prod.getCash_drawer());
                contentValues.put("Printer", prod.getPrinter());
                contentValues.put("Printer_brand", prod.getPrinter_brand());

                myDataBase.insert("Store_Parameters", null, contentValues);
            }

            myDataBase.close();
            status+=1;
            if(status==tableConstant){
                finishTask();
            }else {
                updateProgress(status);
            }
            Log.d("Insertion Successful", "Store_Parameters: "+status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void InsertVendorMasterReturn(List<VendorMasterReturn> list) {
        if (list == null) {
            return;
        }
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
            myDataBase.delete("retail_str_vendor_master_return", null, null);
            for (VendorMasterReturn prod : list) {

                ContentValues contentValues = new ContentValues();
                contentValues.put("VENDOR_RETURN_MASTERID", prod.getVENDOR_RETURN_MASTERID());
                contentValues.put("VENDOR_RETURNGUID", prod.getVENDOR_RETURNGUID());
                contentValues.put("VENDOR_GUID", prod.getVENDOR_GUID());
                contentValues.put("STORE_GUID", prod.getSTORE_GUID());
                contentValues.put("INVOICENO", prod.getINVOICENO());
                contentValues.put("INVOICEDATE", prod.getINVOICEDATE());
                contentValues.put("RETURN_DATE", prod.getRETURN_DATE());
                contentValues.put("REASON", prod.getREASON());
                contentValues.put("GRNNO", prod.getGRNNO());
                contentValues.put("GRNDATE", prod.getGRNDATE());
                contentValues.put("CREATEDBY", prod.getCREATEDBY());
                contentValues.put("UPDATEDBY", prod.getUPDATEDBY());
                contentValues.put("CREATEDON", prod.getCREATEDON());
                contentValues.put("UPDATEDON", prod.getUPDATEDON());
                contentValues.put("ISSYNCED", prod.getISSYNCED());

                myDataBase.insert("retail_str_vendor_master_return", null, contentValues);
            }

            myDataBase.close();
            status+=1;
            if(status==tableConstant){
                finishTask();
            }else {
                updateProgress(status);
            }
            Log.d("Insertion Successful", "retail_str_vendor_master_return: "+status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void InsertVendorDetailReturn(List<VendorDetailReturn> list) {
        if (list == null) {
            return;
        }
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
            myDataBase.delete("retail_str_vendor_detail_return", null, null);
            for (VendorDetailReturn prod : list) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("VENDOR_RETURN_DETAILID", prod.getVENDOR_RETURN_DETAILID());
                contentValues.put("VENDOR_RETURNGUID", prod.getVENDOR_RETURNGUID());
                contentValues.put("ITEM_GUID", prod.getITEM_GUID());
                contentValues.put("BATCHNO", prod.getBATCHNO());
                contentValues.put("QTY", prod.getQTY());
                contentValues.put("UOM_GUID", prod.getUOM_GUID());
                myDataBase.insert("retail_str_vendor_detail_return", null, contentValues);
            }
            myDataBase.close();
            status+=1;
            if(status==tableConstant){
                finishTask();
            }else {
                updateProgress(status);
            }
            Log.d("Insertion Successful", "retail_str_vendor_detail_return: "+status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void InsertVendorReject(List<VendorRejectReason> list) {
        if (list == null) {
            return;
        }
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
            myDataBase.delete("retail_store_vend_reject", null, null);
            for (VendorRejectReason prod : list) {

                ContentValues contentValues = new ContentValues();
                contentValues.put("ID", prod.getID());
                contentValues.put("REASONFOR", prod.getREASONFOR());
                contentValues.put("ACTIVE", prod.getACTIVE());
                contentValues.put("REASONGUID", prod.getREASONGUID());
                contentValues.put("STORE_ID", prod.getSTORE_ID());
                contentValues.put("LAST_MODIFIED", prod.getLAST_MODIFIED());
                contentValues.put("REASON_FOR_REJECTION", prod.getREASON_FOR_REJECTION());
                contentValues.put("CREATEDBY", prod.getCREATEDBY());
                myDataBase.insert("retail_store_vend_reject", null, contentValues);

            }

            myDataBase.close();
            status+=1;
            if(status==tableConstant){
                finishTask();
            }else {
                updateProgress(status);
            }
            Log.d("Insertion Successful", "retail_store_vend_reject: "+status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void InsertVendorPayMaster(List<VendorPayMaster> list) {
        if (list == null) {
            return;
        }
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
            myDataBase.delete("VendorPayMaster", null, null);
            for (VendorPayMaster prod : list) {

                ContentValues contentValues = new ContentValues();
                contentValues.put("VENDOR_PAYID", prod.getVENDOR_PAYID());
                contentValues.put("VENDOR_PAYGUID", prod.getVENDOR_PAYGUID());
                contentValues.put("VENDOR_GUID", prod.getVENDOR_GUID());
                contentValues.put("STORE_GUID", prod.getSTORE_GUID());
                contentValues.put("INVOICENO", prod.getINVOICENO());
                contentValues.put("INVOICEDATE", prod.getINVOICEDATE());
                contentValues.put("INVOICEAMOUNT", prod.getINVOICEAMOUNT());
                contentValues.put("CREATEDBY", prod.getCREATEDBY());
                contentValues.put("UPDATEDBY", prod.getUPDATEDBY());
                contentValues.put("CREATEDON", prod.getCREATEDON());
                contentValues.put("UPDATEDON", prod.getUPDATEDON());
                contentValues.put("DUEAMOUNT", prod.getDUEAMOUNT());
                contentValues.put("PAIDFOR", prod.getPAIDFOR());
                contentValues.put("TYPEOFINVOICE", prod.getTYPEOFINVOICE());
                contentValues.put("ISYNCED", prod.getISYNCED());
                contentValues.put("VENDORPAY_STATUS", prod.getVENDORPAY_STATUS());

                myDataBase.insert("VendorPayMaster", null, contentValues);

            }

            myDataBase.close();
            status+=1;
            if(status==tableConstant){
                finishTask();
            }else {
                updateProgress(status);
            }
            Log.d("Insertion Successful", "VendorPayMaster: "+status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void InsertVendorPayDetails(List<VendorPayDetail> list) {
        if (list == null) {
            return;
        }
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
            myDataBase.delete("VendorPayDetail", null, null);
            for (VendorPayDetail prod : list) {

                ContentValues contentValues = new ContentValues();
                contentValues.put("VENDOR_PAYDETAIL_ID", prod.getVENDOR_PAYDETAIL_ID());
                contentValues.put("VENDOR_PAYGUID", prod.getVENDOR_PAYGUID());
                contentValues.put("BANK_GUID", prod.getBANK_GUID());
                contentValues.put("AMOUNTPAID", prod.getAMOUNTPAID());
                contentValues.put("PAYMENTDATE", prod.getPAYMENTDATE());
                contentValues.put("PAYMODE", prod.getPAYMODE());
                contentValues.put("CHEQUENO", prod.getCHEQUENO());
                contentValues.put("CHEQUEDATE", prod.getCHEQUEDATE());
                myDataBase.insert("VendorPayDetail", null, contentValues);

            }

            myDataBase.close();
            status+=1;
            if(status==tableConstant){
                finishTask();
            }else {
                updateProgress(status);
            }
            Log.d("Insertion Successful", "VendorPayDetail: "+status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void InsertGrnMasters(List<GRNMaster> list) {
        if (list == null) {
            return;
        }
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
            myDataBase.delete("retail_str_grn_master", null, null);
            for (GRNMaster prod : list) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("GRN_GUID", prod.getGRN_GUID());
                contentValues.put("GRNNO", prod.getGRNNO());
                contentValues.put("GRNDate", prod.getGRNDate());
                contentValues.put("GRANDAMOUNT", prod.getGRANDAMOUNT());
                contentValues.put("INVOICENO", prod.getINVOICENO());
                contentValues.put("INVOICEDATE", prod.getINVOICEDATE());
                contentValues.put("INVOICEDISCOUNT", prod.getINVOICEDISCOUNT());
                contentValues.put("GRNPRINT", prod.getGRNPRINT());
                contentValues.put("GRNRECON", prod.getGRNRECON());
                contentValues.put("GRN_STATUS", prod.getGRN_STATUS());
                contentValues.put("CREATEDBY", prod.getCREATEDON());
                contentValues.put("GRNTYPE", prod.getGRNTYPE());
                contentValues.put("USER_GUID", prod.getUSER_GUID());
                contentValues.put("VENDOR_GUID", prod.getVENDOR_GUID());
                contentValues.put("STORE_GUID", prod.getSTORE_GUID());
                contentValues.put("ISSYNCED", prod.getISSYNCED());
                myDataBase.insert("retail_str_grn_master", null, contentValues);

            }

            myDataBase.close();
            status+=1;
            if(status==tableConstant){
                finishTask();
            }else {
                updateProgress(status);
            }
            Log.d("Insertion Successful", "GRNMaster: "+status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void InsertGrnDetails(List<GRNDetails> list) {
        if (list == null) {
            return;
        }
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
            myDataBase.delete("retail_str_grn_detail", null, null);
            for (GRNDetails prod : list) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("GRNDETAILID", prod.getGRNDETAILID());
                contentValues.put("GRN_QTY", prod.getGRN_QTY());
                contentValues.put("BATCHNO", prod.getBATCHNO());
                contentValues.put("EXP_DATE", prod.getEXP_DATE());
                contentValues.put("PUR_PRICE", prod.getPUR_PRICE());
                contentValues.put("TAX_AMOUNT", prod.getTAX_AMOUNT());
                contentValues.put("GRN_VALUE", prod.getGRN_VALUE());
                contentValues.put("MRP", prod.getMRP());
                contentValues.put("ISFREEGOODS", prod.getISFREEGOODS());
                contentValues.put("FREE_QUANTITY", prod.getFREE_QUANTITY());
                contentValues.put("PURCHASEDISCOUNTPERCENTAGE", prod.getPURCHASEDISCOUNTPERCENTAGE());
                contentValues.put("PURCHASEDISCOUNTBYAMOUNT", prod.getPURCHASEDISCOUNTBYAMOUNT());
                contentValues.put("GRN_GUID", prod.getGRN_GUID());
                contentValues.put("ITEM_GUID", prod.getITEM_GUID());
                contentValues.put("STORE_GUID", prod.getSTORE_GUID());
                contentValues.put("UOM_GUID", prod.getUOM_GUID());
                contentValues.put("GRNDETAILGUID", prod.getGRNDETAILGUID());
                myDataBase.insert("retail_str_grn_detail", null, contentValues);

            }

            myDataBase.close();
            status+=1;
            if(status==tableConstant){
                finishTask();
            }else {
                updateProgress(status);
            }
            Log.d("Insertion Successful", "GRNDetails: "+status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void InsertStockRegister(List<StockRegister> list) {
        if (list == null) {
            return;
        }
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
            myDataBase.delete("stock_register", null, null);
            for (StockRegister prod : list) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("REGISTERGUID", prod.getREGISTERGUID());
                contentValues.put("MASTERORG_GUID", prod.getMASTERORG_GUID());
                contentValues.put("STORE_GUID", prod.getSTORE_GUID());
                contentValues.put("VENDOR_GUID", prod.getVENDOR_GUID());
                contentValues.put("LINETYPE", prod.getLINETYPE());
                contentValues.put("TRANSACTIONTYPE", prod.getTRANSACTIONTYPE());
                contentValues.put("TRANSACTIONNUMBER", prod.getTRANSACTIONNUMBER());
                contentValues.put("TRANSACTIONDATE", prod.getTRANSACTIONDATE());
                contentValues.put("ITEM_GUID", prod.getITEM_GUID());
                contentValues.put("UOM_GUID", prod.getUOM_GUID());
                contentValues.put("QUANTITY", prod.getQUANTITY());
                contentValues.put("BATCHNO", prod.getBATCHNO());
                contentValues.put("BARCODE", prod.getBARCODE());
                contentValues.put("SALESPRICE", prod.getSALESPRICE());
                contentValues.put("WHOLESALEPRICE", prod.getWHOLESALEPRICE());
                contentValues.put("INTERNETPRICE", prod.getINTERNETPRICE());
                contentValues.put("SPECIALPRICE", prod.getSPECIALPRICE());
                contentValues.put("ISSYNCED", prod.getISSYNCED());
                myDataBase.insert("stock_register", null, contentValues);

            }

            myDataBase.close();
            status+=1;
            if(status==tableConstant){
                finishTask();
            }else {
                updateProgress(status);
            }
            Log.d("Insertion Successful", "StockRegisters: "+status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void InsertCreditBillDetails(List<CreditBillDetails> list) {
        if (list == null) {
            return;
        }
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
            myDataBase.delete("retail_credit_bill_details", null, null);
            for (CreditBillDetails prod : list) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("STORE_GUID", prod.getSTORE_GUID());
                contentValues.put("CUSTOMERMOBILENO", prod.getCUSTOMERMOBILENO());
                contentValues.put("CUSTOMERGUID", prod.getCUSTOMERGUID());
                contentValues.put("BILLNO", prod.getBILLNO());
                contentValues.put("BILLDATE", prod.getBILLDATE());
                contentValues.put("ITEM_GUID", prod.getITEM_GUID());
                contentValues.put("ITEM_NAME", prod.getITEM_NAME());
                contentValues.put("MRP", prod.getMRP());
                contentValues.put("SELLINGPRICE", prod.getSELLINGPRICE());
                contentValues.put("QTY", prod.getQTY());
                contentValues.put("TOTALVALUE", prod.getTOTALVALUE());
                contentValues.put("TOTALGST", prod.getTOTALGST());
                contentValues.put("CGST", prod.getCGST());
                contentValues.put("SGST", prod.getSGST());
                contentValues.put("IGST", prod.getIGST());
                contentValues.put("DISCOUNT_PERC", prod.getDISCOUNT_PERC());
                contentValues.put("DISCOUNT_VALUE", prod.getDISCOUNT_VALUE());

                myDataBase.insert("retail_credit_bill_details", null, contentValues);

            }

            myDataBase.close();
            status+=1;
            if(status==tableConstant){
                finishTask();
            }else {
                updateProgress(status);
            }
            Log.d("Insertion Successful", "CreditBillDetails: "+status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void InsertCustomerLedger(List<CustomerLedger> list) {
        if (list == null) {
            return;
        }
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
            myDataBase.delete("customerLedger", null, null);
            for (CustomerLedger prod : list) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("CUSTLEDGERID", prod.getCUSTLEDGERID());
                contentValues.put("CUSTOMERGUID", prod.getCUSTOMERGUID());
                contentValues.put("STORE_GUID", prod.getSTORE_GUID());
                contentValues.put("USER_GUID", prod.getUSER_GUID());
                contentValues.put("ACTIONDATE", prod.getACTIONDATE());
                contentValues.put("GRANDTOTAL", prod.getGRANDTOTAL());
                contentValues.put("CREDITAMOUNT", prod.getCREDITAMOUNT());
                contentValues.put("DEBITAMOUNT", prod.getDEBITAMOUNT());
                contentValues.put("BALANCEAMOUNT", prod.getBALANCEAMOUNT());
                contentValues.put("BILLNO", prod.getBILLNO());
                contentValues.put("ISSYNCED", prod.getISSYNCED());
                contentValues.put("MASTERPAYMODEGUID", prod.getMASTERPAYMODEGUID());
                contentValues.put("ADDITIONALPARAM1", prod.getADDITIONALPARAM1());
                contentValues.put("ADDITIONALPARAM2", prod.getADDITIONALPARAM2());
                contentValues.put("ADDITIONALPARAM3", prod.getADDITIONALPARAM3());
                contentValues.put("ADDITIONALPARAM4", prod.getADDITIONALPARAM4());
                contentValues.put("ADDITIONALPARAM5", prod.getADDITIONALPARAM5());
                contentValues.put("ADDITIONALPARAM6", prod.getADDITIONALPARAM6());

                myDataBase.insert("customerLedger", null, contentValues);

            }

            myDataBase.close();
            status+=1;
            if(status==tableConstant){
                finishTask();
            }else {
                updateProgress(status);
            }
            Log.d("Insertion Successful", "CustomerLedger: "+status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void InsertCustomerCredit(List<CustomerCredit> list) {
        if (list == null) {
            return;
        }
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
            myDataBase.delete("retail_credit_cust", null, null);
            for (CustomerCredit prod : list) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("PAYMENTID", prod.getPAYMENTID());
                contentValues.put("STORE_GUID", prod.getSTORE_GUID());
                contentValues.put("CUSTOMERNAME", prod.getCUSTOMERNAME());
                contentValues.put("CUSTOMERMOBILENO", prod.getCUSTOMERMOBILENO());
                contentValues.put("GRANDTOTAL", prod.getGRANDTOTAL());
                contentValues.put("CUSTOMERSTATUS", prod.getCUSTOMERSTATUS());
                contentValues.put("RECEIVEAMOUNT", prod.getRECEIVEAMOUNT());
                contentValues.put("DUEAMOUNT", prod.getDUEAMOUNT());
                contentValues.put("CUSTOMERGUID", prod.getCUSTOMERGUID());
                contentValues.put("TOTALGST", prod.getTOTALGST());
                myDataBase.insert("retail_credit_cust", null, contentValues);

            }

            myDataBase.close();
            status+=1;
            if(status==tableConstant){
                finishTask();
            }else {
                updateProgress(status);
            }
            Log.d("Insertion Successful", "Insert_retail_credit_cust: "+status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void InsertCustomerReject(List<CustomerReject> list) {
        if (list == null) {
            return;
        }
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
            myDataBase.delete("retail_store_cust_reject", null, null);
            for (CustomerReject prod : list) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("ID", prod.getID());
                contentValues.put("REASONGUID", prod.getREASONGUID());
                contentValues.put("REASONFOR", prod.getREASONFOR());
                contentValues.put("REASON_FOR_REJECTION", prod.getREASON_FOR_REJECTION());
                contentValues.put("ACTIVE", prod.getACTIVE());
                contentValues.put("STORE_ID", prod.getSTORE_ID());
                contentValues.put("CREATEDBY", prod.getCREATEDBY());
                contentValues.put("LAST_MODIFIED", prod.getLAST_MODIFIED());
                myDataBase.insert("retail_store_cust_reject", null, contentValues);

            }

            myDataBase.close();
            status+=1;
            if(status==tableConstant){
                finishTask();
            }else {
                updateProgress(status);
            }
            Log.d("Insertion Successful", "Insertretail_store_cust_reject: "+status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void InsertCustomerReturnDetails(List<CustomerReturnDetails> list) {
        if (list == null) {
            return;
        }
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
            myDataBase.delete("customerReturnDetail", null, null);
            for (CustomerReturnDetails prod : list) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("CUSTOMER_RETURNS_DETAILID", prod.getCUSTOMER_RETURNS_DETAILID());
                contentValues.put("CUSTOMER_RETURNS_MASTER_ID", prod.getCUSTOMER_RETURNS_MASTER_ID());
                contentValues.put("MASTER_PRODUCT_ITEM_ID", prod.getMASTER_PRODUCT_ITEM_ID());
                contentValues.put("BATCHNO", prod.getBATCHNO());
                contentValues.put("RETURNQUANTITY", prod.getRETURNQUANTITY());
                contentValues.put("EXPIRYDATE", prod.getEXPIRYDATE());
                contentValues.put("CUSTOMER_RETURN_DETAIL_STATUS", prod.getCUSTOMER_RETURN_DETAIL_STATUS());
                myDataBase.insert("customerReturnDetail", null, contentValues);
            }

            myDataBase.close();
            status+=1;
            if(status==tableConstant){
                finishTask();
            }else {
                updateProgress(status);
            }
            Log.d("Insertion Successful", "InsertSalesReturnMaster: "+status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void InsertCustomerReturnMaster(List<CustomerReturnMaster> list) {
        if (list == null) {
            return;
        }
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
            myDataBase.delete("customerReturnMaster", null, null);
            for (CustomerReturnMaster prod : list) {
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
            }

            myDataBase.close();
            status+=1;
            if(status==tableConstant){
                finishTask();
            }else {
                updateProgress(status);
            }
            Log.d("Insertion Successful", "InsertcustomerReturnMaster: "+status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void InsertStoreConfig(List<StoreConfiguration> list) {
        if (list == null) {
            return;
        }
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
            myDataBase.delete("store_configuration", null, null);
            for (StoreConfiguration prod : list) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("STORE_CONFIGKEY", prod.getSTORE_CONFIGKEY());
                contentValues.put("STORECONFIG_GUID", prod.getSTORECONFIG_GUID());
                contentValues.put("STORE_ID", prod.getSTORE_ID());
                contentValues.put("MRP_VISIBILITY", prod.getMRP_VISIBILITY());
                contentValues.put("SECONDARY_TELEPHONE_VISIBILITY", prod.getSECONDARY_TELEPHONE_VISIBILITY());
                contentValues.put("FOOTER_VISIBILITY", prod.getFOOTER_VISIBILITY());
                contentValues.put("INVOICE_DISCOUNT_VISIBILITY", prod.getINVOICE_DISCOUNT_VISIBILITY());
                contentValues.put("NOOFBILLCOPIES", prod.getNOOFBILLCOPIES());
                contentValues.put("CREDIT_BILLCOPIES", prod.getCREDIT_BILLCOPIES());
                contentValues.put("RETURN_BILLCOPIES", prod.getRETURN_BILLCOPIES());
                contentValues.put("MARGIN_VISIBILITY", prod.getMARGIN_VISIBILITY());
                contentValues.put("BILL_DISCOUNT", prod.getBILL_DISCOUNT());
                contentValues.put("FREE_QTY_VISIBILITY", prod.getFREE_QTY_VISIBILITY());
                contentValues.put("INVOICE_BILL_PRINT", prod.getINVOICE_BILL_PRINT());
                contentValues.put("SALE_PRINT_BILL", prod.getSALE_PRINT_BILL());
                contentValues.put("EXP_DATE_VISIBILITY", prod.getEXP_DATE_VISIBILITY());
                contentValues.put("BARCODE_VISIBILITY", prod.getBARCODE_VISIBILITY());
                contentValues.put("SALES_REPORT_PASSWORD", prod.getSALES_REPORT_PASSWORD());
                contentValues.put("FINANCIAL_REPORT_PASSWORD", prod.getFINANCIAL_REPORT_PASSWORD());
                contentValues.put("DISPLAY_TOTALBILLVALE", prod.getDISPLAY_TOTALBILLVALE());
                contentValues.put("DISPLAY_NETBILLPAYABLE", prod.getDISPLAY_NETBILLPAYABLE());
                contentValues.put("DISPLAY_AMOUNTRECEIVED", prod.getDISPLAY_AMOUNTRECEIVED());
                contentValues.put("DISPLAY_AMOUNTPAIDBACK", prod.getDISPLAY_AMOUNTPAIDBACK());
                contentValues.put("DISPLAY_SALESRETURN", prod.getDISPLAY_SALESRETURN());
                contentValues.put("HOLD_INVOICEDAYS", prod.getHOLD_INVOICEDAYS());
                contentValues.put("HOLD_SALESDAYS", prod.getHOLD_SALESDAYS());
                contentValues.put("ROUNDOFF", prod.getROUNDOFF());
                contentValues.put("SALESDATAARCHIVEDAYS", prod.getSALESDATAARCHIVEDAYS());
                contentValues.put("STORE_CONFIGSTATUS", prod.getSTORE_CONFIGSTATUS());
                contentValues.put("NO_OF_SHIFTS", prod.getNO_OF_SHIFTS());
                contentValues.put("CREATEDBY", prod.getCREATEDBY());
                contentValues.put("CREATEDON", prod.getCREATEDON());
                contentValues.put("OTP_CHECK", prod.getOTP_CHECK());
                contentValues.put("CARD_MACHINE", prod.getCARD_MACHINE());
                contentValues.put("HUTCH_PRINT", prod.getHUTCH_PRINT());
                contentValues.put("MOBITEL_PRINT", prod.getMOBITEL_PRINT());
                contentValues.put("DIALOG_PRINT", prod.getDIALOG_PRINT());
                contentValues.put("MRP_DECIMAL", prod.getMRP_DECIMAL());
                contentValues.put("PURCHASE_PRICE_DECIMAL", prod.getPURCHASE_PRICE_DECIMAL());
                contentValues.put("SALES_PRICE_DECIMAL", prod.getSALES_PRICE_DECIMAL());
                contentValues.put("SCREEN_COLOR", prod.getSCREEN_COLOR());
                contentValues.put("DISCOUNT", prod.getDISCOUNT());
                contentValues.put("NEGATIVESTOCK", prod.getNEGATIVESTOCK());
                contentValues.put("EXPIRYDATELIMIT", prod.getEXPIRYDATELIMIT());
                contentValues.put("TEXTSIZE", prod.getTEXTSIZE());
                contentValues.put("SALESDATA", prod.getSALESDATA());
                contentValues.put("BARCODEPRINT", prod.getBARCODEPRINT());
                contentValues.put("PAPERSIZE", prod.getPAPERSIZE());
                contentValues.put("HEADERSPIN", prod.getHEADERSPIN());
                contentValues.put("STORE_FONT", prod.getSTORE_FONT());
                contentValues.put("STORE_SIZE", prod.getSTORE_SIZE());
                contentValues.put("STORE_ADFONT", prod.getSTORE_ADFONT());
                contentValues.put("STORE_ADSIZE", prod.getSTORE_ADSIZE());
                contentValues.put("TELEPHONEFONT", prod.getTELEPHONEFONT());
                contentValues.put("TELEPHONESIZE", prod.getTELEPHONESIZE());
                contentValues.put("TELEPHONEALIGN", prod.getTELEPHONEALIGN());
                contentValues.put("STORE_ALIGN", prod.getSTORE_ALIGN());
                contentValues.put("STORE_ADALIGN", prod.getSTORE_ADALIGN());
                contentValues.put("TOTAL_TAX_VISIBLE", prod.getTOTAL_TAX_VISIBLE());
                contentValues.put("TAX_VISIBLE", prod.getTAX_VISIBLE());
                contentValues.put("ISSYNCED", prod.getISSYNCED());
                contentValues.put("RATE_APPEARANCE", prod.getRATE_APPEARANCE());
                contentValues.put("DISCOUNT_APPEARANCE", prod.getDISCOUNT_APPEARANCE());
                contentValues.put("ADDITIONAL_EXP1", prod.getADDITIONAL_EXP1());
                contentValues.put("ADDITIONAL_EXP2", prod.getADDITIONAL_EXP2());
                contentValues.put("ADDITIONAL_EXP3", prod.getADDITIONAL_EXP3());
                contentValues.put("ADDITIONAL_EXP4", prod.getADDITIONAL_EXP4());
                contentValues.put("ADDITIONAL_EXP5", prod.getADDITIONAL_EXP5());
                contentValues.put("ADDITIONAL_EXP6", prod.getADDITIONAL_EXP6());
                contentValues.put("ADDITIONAL_EXP7", prod.getADDITIONAL_EXP7());
                contentValues.put("ADDITIONAL_EXP8", prod.getADDITIONAL_EXP8());
                contentValues.put("ADDITIONAL_EXP9", prod.getADDITIONAL_EXP9());
                contentValues.put("ADDITIONAL_EXP10", prod.getADDITIONAL_EXP10());
                contentValues.put("MULTI_CURRENCY", prod.getMULTI_CURRENCY());
                contentValues.put("QUICK_PAY", prod.getQUICK_PAY());
                contentValues.put("MULTI_PAYMENT_MODE", prod.getMULTI_PAYMENT_MODE());

                myDataBase.insert("store_configuration", null, contentValues);

            }
            myDataBase.close();
            status+=1;
            if(status==tableConstant){
                finishTask();
            }else {
                updateProgress(status);
            }
            Log.d("Insertion Successful", "InsertStoreConfiguration: "+status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void InsertBankMaster(List<BankDetails> list) {
        if (list == null) {
            return;
        }
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
            myDataBase.delete("bank_details", null, null);
            for (BankDetails prod : list) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("BANK_ID", prod.getBANK_ID());
                contentValues.put("BANK_GUID", prod.getBANK_GUID());
                contentValues.put("BANK_NAME", prod.getBANK_NAME());
                contentValues.put("BANK_ADDRESS", prod.getBANK_ADDRESS());
                contentValues.put("BANK_LOCATION", prod.getBANK_LOCATION());
                contentValues.put("BANK_CITY", prod.getBANK_CITY());
                contentValues.put("COUNTRY_CODE", prod.getCOUNTRY_CODE());
                contentValues.put("BANK_STATUS", prod.getBANK_STATUS());
                contentValues.put("STORE_ID", prod.getSTORE_ID());
                contentValues.put("POS_USER", prod.getPOS_USER());
                contentValues.put("LAST_MODIFIED", prod.getLAST_MODIFIED());
                contentValues.put("STORE_GUID", prod.getSTORE_GUID());
                myDataBase.insert("bank_details", null, contentValues);

            }

            myDataBase.close();
            status+=1;
            if(status==tableConstant){
                finishTask();
            }else {
                updateProgress(status);
            }
            Log.d("Insertion Successful", "InsertmasterState: "+status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void InsertMasterState(List<MasterState> list) {
        if (list == null) {
            return;
        }
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
            myDataBase.delete("masterState", null, null);
            for (MasterState prod : list) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("STATE_GUID", prod.getSTATE_GUID());
                contentValues.put("STATE", prod.getSTATE());
                contentValues.put("ISSYNCED", prod.getISSYNCED());
                myDataBase.insert("masterState", null, contentValues);
            }

            myDataBase.close();
            status+=1;
            if(status==tableConstant){
                finishTask();
            }else {
                updateProgress(status);
            }
            Log.d("Insertion Successful", "InsertmasterState: "+status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






    public void InsertCustomerAddress(List<CustomerAddress> list) {
        if (list == null) {
            return;
        }
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
            myDataBase.delete("retail_cust_address", null, null);
            for (CustomerAddress prod : list) {
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
            }

            myDataBase.close();
            status+=1;
            if(status==tableConstant){
                finishTask();
            }else {
                updateProgress(status);
            }
            Log.d("Insertion Successful", "InsertMasterAddress: "+status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void InsertCustomerType(List<MasterCustomerType> list) {
        if (list == null) {
            return;
        }
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
            myDataBase.delete("master_customer_type", null, null);
            for (MasterCustomerType prod : list) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("MASTER_CUSTOMERTYPEID", prod.getMASTER_CUSTOMERTYPEID());
                contentValues.put("CUSTOMERTYPE", prod.getCUSTOMERTYPE());
                contentValues.put("MASTER_CUSTOMERTYPESTATUS", prod.getMASTER_CUSTOMERTYPESTATUS());
                myDataBase.insert("master_customer_type", null, contentValues);
            }

            myDataBase.close();
            status+=1;
            if(status==tableConstant){
                finishTask();
            }else {
                updateProgress(status);
            }
            Log.d("Insertion Successful", "InsertMasterCustomerType: "+status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public void InsertMasterUom(List<MasterUOM> list) {
        if (list == null) {
            return;
        }
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
            myDataBase.delete("master_uom", null, null);
            for (MasterUOM prod : list) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("ISACTIVE", prod.getISACTIVE());
                contentValues.put("UoM", prod.getUoM());
                contentValues.put("UOM_GUID", prod.getUOM_GUID());
                contentValues.put("UoMID", prod.getUoMID());
                myDataBase.insert("master_uom", null, contentValues);

            }

            myDataBase.close();
            status+=1;
            if(status==tableConstant){
                finishTask();
            }else {
                updateProgress(status);
            }
            Log.d("Insertion Successful", "InsertMasterUom: "+status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void InsertVendorMaster(List<VendorMaster> list) {
        if (list == null) {
            return;
        }
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
            myDataBase.delete("retail_str_dstr", null, null);
            for (VendorMaster prod : list) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("DSTR_ID", prod.getDSTR_ID());
                contentValues.put("VENDOR_GUID", prod.getVENDOR_GUID());
                contentValues.put("MASTERORGID", prod.getMASTERORGID());
                contentValues.put("STORE_ID", prod.getSTORE_ID());
                contentValues.put("VENDOR_CATEGORY", prod.getVENDOR_CATEGORY());
                contentValues.put("DSTR_NM", prod.getDSTR_NM());
                contentValues.put("VENDOR_STREET", prod.getVENDOR_STREET());
                contentValues.put("ADD_1", prod.getADD_1());
                contentValues.put("CITY", prod.getCITY());
                contentValues.put("DSTR_CNTCT_NM", prod.getDSTR_CNTCT_NM());
                contentValues.put("MOBILE", prod.getMOBILE());
                contentValues.put("EMAIL", prod.getEMAIL());
                contentValues.put("GST", prod.getGST());
                contentValues.put("PAN", prod.getPAN());
                contentValues.put("ZIP", prod.getZIP());
                contentValues.put("TELE", prod.getTELE());
                contentValues.put("VENDOR_STATUS", prod.getVENDOR_STATUS());
                contentValues.put("POS_USER", prod.getPOS_USER());
                contentValues.put("CREATEDON", prod.getCREATEDON());
                contentValues.put("MASTERCOUNTRYID", prod.getMASTERCOUNTRYID());
                contentValues.put("DSTR_INV", prod.getDSTR_INV());
                contentValues.put("VENDORSTATE", prod.getVENDORSTATE());
                contentValues.put("PAYMENTTERMS", prod.getPAYMENTTERMS());
                contentValues.put("ISSYNCED", prod.getISSYNCED());
                myDataBase.insert("retail_str_dstr", null, contentValues);

            }

            myDataBase.close();
            status+=1;
            if(status==tableConstant){
                finishTask();
            }else {
                updateProgress(status);
            }
            Log.d("Insertion Successful", "InsertVendorMater: "+status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void InsertHsnMaster(List<HSNMaster> list) {
        if (list == null) {
            return;
        }
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
            myDataBase.delete("hsn_master", null, null);
            for (HSNMaster prod : list) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("HSN_ID", prod.getHSN_ID());
                contentValues.put("HSN", prod.getHSN());
                contentValues.put("GST", prod.getGST());
                contentValues.put("SGST", prod.getSGST());
                contentValues.put("CGST", prod.getCGST());
                contentValues.put("IGST", prod.getIGST());
                contentValues.put("CESS", prod.getCESS());
                contentValues.put("HSN_STATUS", prod.getHSN_STATUS());
                myDataBase.insert("hsn_master", null, contentValues);

            }

            myDataBase.close();
            status+=1;
            if(status==tableConstant){
                finishTask();
            }else {
                updateProgress(status);
            }
            Log.d("Insertion Successful", "InsertHsnMaster: "+status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void InsertMasterSubCategory(List<MasterSubcategory> list) {
        if (list == null) {
            return;
        }
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
            myDataBase.delete("master_subcategory", null, null);
            for (MasterSubcategory prod : list) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("SUB_CATEGORYID", prod.getSUB_CATEGORYID());
                contentValues.put("SUB_CATEGORYGUID", prod.getSUB_CATEGORYGUID());
                contentValues.put("SUBCATEGORY_DESCRIPTION", prod.getSUBCATEGORY_DESCRIPTION());
                contentValues.put("CATEGORY_GUID", prod.getCATEGORY_GUID());
                myDataBase.insert("master_subcategory", null, contentValues);

            }

            myDataBase.close();
            status+=1;
            if(status==tableConstant){
                finishTask();
            }else {
                updateProgress(status);
            }
            Log.d("Insertion Successful", "InsertMaster_subcategory: "+status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void InsertMasterCategory(List<MasterCategory> list) {
        if (list == null) {
            return;
        }
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
            myDataBase.delete("master_category", null, null);
            for (MasterCategory prod : list) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("CATEGORYID", prod.getCATEGORYID());
                contentValues.put("CATEGORY_GUID", prod.getCATEGORY_GUID());
                contentValues.put("CATEGORY", prod.getCATEGORY());
                myDataBase.insert("master_category", null, contentValues);

                // myDataBase.close(); // Closing database connection
            }

            myDataBase.close();
            status+=1;
            if(status==tableConstant){
                finishTask();
            }else {
                updateProgress(status);
            }
            Log.d("Insertion Successful", "InsertMaster_category: "+status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void InsertShiftTrans(List<ShiftTrans> list) {
        if (list == null) {
            return;
        }
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
            myDataBase.delete("shift_trans", null, null);
            for (ShiftTrans prod : list) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("SHIFT_TRANS_ID", prod.getSHIFT_TRANS_ID());
                contentValues.put("SHIFT_TRANSACTIONGUID", prod.getSHIFT_TRANSACTIONGUID());
                contentValues.put("ORG_GUID", prod.getORG_GUID());
                contentValues.put("STORE_GUID", prod.getSTORE_GUID());
                contentValues.put("SHIFT_GUID", prod.getSHIFT_GUID());
                contentValues.put("SHIFT_DATE", prod.getSHIFT_DATE());
                contentValues.put("START_TIME", prod.getSTART_TIME());
                contentValues.put("END_TIME", prod.getEND_TIME());
                contentValues.put("IS_SHIFT_STARTED", prod.getIS_SHIFT_STARTED());
                contentValues.put("IS_SHIFT_ENDED", prod.getIS_SHIFT_ENDED());
                contentValues.put("USER_GUID", prod.getUSER_GUID());
                contentValues.put("CASH_OPENED", prod.getCASH_OPENED());
                contentValues.put("CASH_CLOSED", prod.getCASH_CLOSED());
                contentValues.put("ISACTIVE", prod.getISACTIVE());
                contentValues.put("ISSYNCED", prod.getISSYNCED());
                myDataBase.insert("shift_trans", null, contentValues);

                // myDataBase.close(); // Closing database connection
            }

            myDataBase.close();
            status+=1;
            if(status==tableConstant){
                finishTask();
            }else {
                updateProgress(status);
            }
            Log.d("Insertion Successful", "InsertShiftTrans: "+status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void InsertBillPayDetail(List<BillPayDetail> list) {
        if (list == null) {
            return;
        }
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
            myDataBase.delete("billpaydetail", null, null);
            for (BillPayDetail prod : list) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("BILLPAYDETAILID", prod.getBILLPAYDETAILID());
                contentValues.put("BILLMASTERID", prod.getBILLMASTERID());
                contentValues.put("MASTERPAYMODEGUID", prod.getMASTERPAYMODEGUID());
                contentValues.put("PAYAMOUNT", prod.getPAYAMOUNT());
                contentValues.put("TRANSACTIONNUMBER", prod.getTRANSACTIONNUMBER());
                contentValues.put("ADDITIONALPARAM1", prod.getADDITIONALPARAM1());
                contentValues.put("ADDITIONALPARAM2", prod.getADDITIONALPARAM2());
                contentValues.put("ADDITIONALPARAM3", prod.getADDITIONALPARAM3());
                contentValues.put("BILLPAYDETAIL_STATUS", prod.getBILLPAYDETAIL_STATUS());
                myDataBase.insert("billpaydetail", null, contentValues);

                // myDataBase.close(); // Closing database connection
            }

            myDataBase.close();
            status+=1;
            if(status==tableConstant){
                finishTask();
            }else {
                updateProgress(status);
            }
            Log.d("Insertion Successful", "InsertBillPayDetail: "+status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void InsertPaymentModeMaster(List<PaymentModeMaster> list) {
        if (list == null) {
            return;
        }
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
            myDataBase.delete("masterpaymode", null, null);
            for (PaymentModeMaster prod : list) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("PAYMODE_GUID", prod.getPAYMODE_GUID());
                contentValues.put("PAYMODE", prod.getPAYMODE());
                contentValues.put("LEGEND", prod.getLEGEND());
                myDataBase.insert("masterpaymode", null, contentValues);

                // myDataBase.close(); // Closing database connection
            }
            myDataBase.close();
            status+=1;
            if(status==tableConstant){
                finishTask();
            }else {
                updateProgress(status);
            }
            Log.d("Insertion Successful", "InsertPaymentModeMaster: "+status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void InsertDeliveryType(List<DeliveryTypeMaster> list) {
        if (list == null) {
            return;
        }
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
            myDataBase.delete("masterdeliverytype", null, null);
            for (DeliveryTypeMaster prod : list) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("DELIVERYTYPE_GUID", prod.getDELIVERYTYPE_GUID());
                contentValues.put("DELIVERYTYPE", prod.getDELIVERYTYPE());
                contentValues.put("LEGEND", prod.getLEGEND());
                myDataBase.insert("masterdeliverytype", null, contentValues);

                // myDataBase.close(); // Closing database connection
            }
            myDataBase.close();
            status+=1;
            if(status==tableConstant){
                finishTask();
            }else {
                updateProgress(status);
            }
            Log.d("Insertion Successful", "InsertDeliveryType: "+status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void InsertShiftMaster(List<ShiftMaster> list) {
        if (list == null) {
            return;
        }
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
            myDataBase.delete("master_shift", null, null);
            for (ShiftMaster prod : list) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("SHIFTGUID", prod.getSHIFTGUID());
                contentValues.put("SHIFT_DESCRIPTION", prod.getSHIFT_DESCRIPTION());
                contentValues.put("START_TIME", prod.getSTART_TIME());
                contentValues.put("END_TIME", prod.getEND_TIME());
                contentValues.put("MASTERORG_GUID", prod.getMASTERORG_GUID());
                contentValues.put("STORE_GUID", prod.getSTORE_GUID());
                myDataBase.insert("master_shift", null, contentValues);

                // myDataBase.close(); // Closing database connection
            }
            myDataBase.close();
            status+=1;
            if(status==tableConstant){
                finishTask();
            }else {
                updateProgress(status);
            }
            Log.d("Insertion Successful", "InsertShiftMaster: "+status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void InsertTerminalConnfig(List<TerminalConfiguration> list) {
        if (list == null) {
            return;
        }
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
            myDataBase.delete("terminal_configuration", null, null);
            for (TerminalConfiguration prod : list) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("STORE_GUID", prod.getSTOREGUID());
                contentValues.put("TERMINALCONFIG_GUID", prod.getTERMINALCONFIGGUID());
                contentValues.put("TERMINAL_GUID", prod.getTERMINALGUID());
                contentValues.put("TERMINAL_NAME", prod.getTERMINALNAME());
                myDataBase.insert("terminal_configuration", null, contentValues);

                // myDataBase.close(); // Closing database connection
            }
            myDataBase.close();
            status+=1;
            if(status==tableConstant){
                finishTask();
            }else {
                updateProgress(status);
            }

            Log.d("Insertion Successful", "InsertTerminalConnfig: "+status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void InsertTerminalUserAlloc(List<TerminalUserAllocation> list) {
        if (list == null) {
            return;
        }
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
            myDataBase.delete("terminal_user_allocation", null, null);
            for (TerminalUserAllocation prod : list) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("USER_GUID", prod.getUSERGUID());
                contentValues.put("TERMINAL_GUID", prod.getTERMINALGUID());
                contentValues.put("STORE_GUID", prod.getSTOREGUID());
                contentValues.put("TERMINAL_USER_ALLOCATION_ID", prod.getTERMINAL_USER_ALLOCATION_ID());

                myDataBase.insert("terminal_user_allocation", null, contentValues);


                // myDataBase.close(); // Closing database connection
            }
            myDataBase.close();
            status+=1;
            if(status==tableConstant){
                finishTask();
            }else {
                updateProgress(status);
            }
            Log.d("Insertion Successful", "InsertTerminalUserAlloc: "+status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void InsertRetailStore(List<RetailStore> list) {
        if (list == null) {
            return;
        }
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
            myDataBase.delete("retail_store", null, null);
            for (RetailStore prod : list) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("BUSINESSTYPE", prod.getBUSINESSTYPE());
                contentValues.put("MASTERORG_GUID", prod.getMASTERORGGUID());
                contentValues.put("STORE_ID", prod.getSTOREID());
                contentValues.put("STATUS", prod.getSTATUS());
                contentValues.put("STORE_GUID", prod.getSTOREGUID());
                contentValues.put("STORE_NUMBER", prod.getSTORENUMBER());
                contentValues.put("ORGID", prod.getORGID());
                contentValues.put("ENTID", prod.getENTID());
                contentValues.put("STR_NM", prod.getSTRNM());
                contentValues.put("ADD_1", prod.getADD1());
                contentValues.put("CTY", prod.getCTY());
                contentValues.put("StoreStateID", prod.getStoreStateID());
                contentValues.put("STR_CTR", prod.getSTRCTR());
                contentValues.put("StoreTaxID", prod.getStoreTaxID());
                contentValues.put("E_MAIL", prod.getEMAIL());
                contentValues.put("TELE", prod.getTELE());
                contentValues.put("NoOfRegisters", prod.getNoOfRegisters());
                contentValues.put("FOOTER", prod.getFOOTER());
                contentValues.put("Store_Street", prod.getStoreStreet());
                contentValues.put("GSTIN_NUMBER", prod.getGSTINNUMBER());
                contentValues.put("ZIP", prod.getZIP());
                contentValues.put("PANCARD_NUMBER", prod.getPANCARDNUMBER());
                contentValues.put("SALESPERSON_ID", prod.getSALESPERSONID());
                contentValues.put("POS_USER", prod.getISSYNCED());
                contentValues.put("CREATION_DATE", prod.getPOSUSER());
                contentValues.put("LastUpdatedBy", prod.getLastUpdatedBy());
                contentValues.put("LastUpdatedOn", prod.getLastUpdatedOn());
                contentValues.put("FLAG", prod.getFLAG());
                contentValues.put("STORE_SECONDARYEMAIL", prod.getSTORESECONDARYEMAIL());
                contentValues.put("TELE_1", prod.getTELE1());
                contentValues.put("STR_CNTCT_NM", prod.getSTRCNTCTNM());
                contentValues.put("STORE_STATE", prod.getSTORESTATE());
                contentValues.put("IS_STOREENABLEDFORECOMMERCE", prod.getISSTOREENABLEDFORECOMMERCE());
                contentValues.put("ECOMMERCESTOREID", prod.getECOMMERCESTOREID());
                contentValues.put("FSSAINUMBER", prod.getFSSAINUMBER());
                contentValues.put("VERSIONINDENTITY", prod.getVERSIONINDENTITY());
                contentValues.put("ASSEMBLYINFO", prod.getASSEMBLYINFO());
                contentValues.put("VERSION_NAME", prod.getVERSIONNAME());
                contentValues.put("VERSION_BUILD", prod.getVERSIONBUILD());
                contentValues.put("DESCRIPTION", prod.getDESCRIPTION());
                contentValues.put("CULTUREINFO", prod.getCULTUREINFO());
                contentValues.put("ISSYNCED", prod.getISSYNCED());
                contentValues.put("ISINTRAIL", prod.getISINTRAIL());
                contentValues.put("CREDIT_NOTE_VALIDITY", prod.getCREDIT_NOTE_VALIDITY());
                contentValues.put("RETURN_FOOTER", prod.getRETURN_FOOTER());

                myDataBase.insert("retail_store", null, contentValues);

               // SQLiteDbInspector.PrintTableData(myDataBase, "retail_store");
                // myDataBase.close(); // Closing database connection
            }
            myDataBase.close();
            status+=1;
            if(status==tableConstant){
                finishTask();
            }else {
                updateProgress(status);
            }
            Log.d("Insertion Successful", "InsertRetailStore: "+status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void InsertBillMaster(List<BillMaster> list) {
        if (list == null) {
            return;
        }
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
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
                contentValues.put("BILLMASTERGUID", prod.getBILLMASTERGUID());


                myDataBase.insert("retail_str_sales_master", null, contentValues);

                // myDataBase.close(); // Closing database connection
            }
            myDataBase.close();
            status+=1;
            if(status==tableConstant){
                finishTask();
            }else {
                updateProgress(status);
            }
            Log.d("Insertion Successful", "InsertBillMaster: "+status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void InsertBillDetails(List<BillDetail> list) {
        if (list == null) {
            return;
        }
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
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


                // myDataBase.close(); // Closing database connection
            }
            myDataBase.close();
            status+=1;
            if(status==tableConstant){
                finishTask();
            }else {
                updateProgress(status);
            }
            Log.d("Insertion Successful", "InsertBillDetails: "+status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void InsertStockMaster(List<StockMaster> list) {
        if (list == null) {
            return;
        }
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
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


               // SQLiteDbInspector.PrintTableData(myDataBase, "retail_str_stock_master");


                    // myDataBase.close(); // Closing database connection
            }
            myDataBase.close();
            status+=1;
            if(status==tableConstant){
                finishTask();
            }else {
                updateProgress(status);
            }
            Log.d("Insertion Successful", "InsertStockMaster: "+status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void InsertProductMaster(List<ProductMaster> list) {
        if (list == null) {
            return;
        }
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
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
                contentValues.put("ISPRODUCTRETURNABLE",prod.getISPRODUCTRETURNABLE());
                contentValues.put("ISLOOSEITEM",prod.getISLOOSEITEM());
                contentValues.put("ADDITIONALPARAM1",prod.getADDITIONALPARAM1());
                contentValues.put("ADDITIONALPARAM2",prod.getADDITIONALPARAM2());
                contentValues.put("ADDITIONALPARAM3",prod.getADDITIONALPARAM3());

                myDataBase.insert("retail_store_prod_com", null, contentValues);


                // myDataBase.close(); // Closing database connection
            }
            myDataBase.close();
            status+=1;
            if(status==tableConstant){
                finishTask();
            }else {
                updateProgress(status);
            }
            Log.d("Insertion Successful", "InsertProductMaster: "+status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void InsertDataGroupUserMaster(List<GroupUserMaster> list) {
        if (list == null) {
            return;
        }
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
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

               // myDataBase.close(); // Closing database connection
            }
            myDataBase.close();
            status+=1;
            if(status==tableConstant){
                finishTask();
            }else {
                updateProgress(status);
            }
            Log.d("Insertion Successful", "InsertDataGroupUserMaster: "+status);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void InsertRetailCust(List<CustomerMaster> list) {
        if (list == null) {
            return;
        }
        try {
            SQLiteDatabase myDataBase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
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
                contentValues.put("CREDITLIMIT", prod.getCREDITLIMIT());


                myDataBase.insert("retail_cust", null, contentValues);

                //myDataBase.close(); // Closing database connection
            }

            myDataBase.close();
            status+=1;
            if(status==tableConstant){
               finishTask();
            }else {
                updateProgress(status);
            }
            Log.d("Insertion Successful", "InsertRetailCust: "+status);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }






    public static void  finishTask(){
        new Handler(Looper.getMainLooper()).post(() -> {
            progressBarDialog.cancel();
            dbReadyCallback.onDBReady();
        });
    }
    public static void  interruptTask(){
        new Handler(Looper.getMainLooper()).post(() -> {
          /* progressBarDialog.cancel();

            if(!injectExecutor.isShutdown())
                injectExecutor.shutdown();*/

        });
    }


    public static void updateProgress(int currentStatus){

        new Handler(Looper.getMainLooper()).post(() -> {
            int progress = (currentStatus*100)/tableConstant;
            progressBarDialog.updateProgress(progress);
            Log.d("Printing Progress", "updateProgress: "+progress);
        });
    }

    public static void  cancelDialog(){
        new Handler(Looper.getMainLooper()).post(() -> {
            progressBarDialog.cancel();
        });

    }
}


