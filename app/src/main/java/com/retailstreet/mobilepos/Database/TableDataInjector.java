package com.retailstreet.mobilepos.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
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
import com.retailstreet.mobilepos.Model.TerminalConfiguration;
import com.retailstreet.mobilepos.Model.TerminalUserAllocation;
import com.retailstreet.mobilepos.Model.VendorDetailReturn;
import com.retailstreet.mobilepos.Model.VendorMaster;
import com.retailstreet.mobilepos.Model.VendorMasterReturn;
import com.retailstreet.mobilepos.Model.VendorPayDetail;
import com.retailstreet.mobilepos.Model.VendorPayMaster;
import com.retailstreet.mobilepos.Model.VendorRejectReason;
import com.retailstreet.mobilepos.Utils.ApiInterface;
import com.retailstreet.mobilepos.View.ApplicationContextProvider;
import com.retailstreet.mobilepos.View.dialog.LoadingDialog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */


public class TableDataInjector {
    String dbname = "MasterDB";
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

    private LoadingDialog loadingDialog;

    public static int status =0;
    private final int tableConstant=38;

    public TableDataInjector(Context context, String storeid,DBReadyCallback callback) {

        this.context = context;
        this.storeId = storeid;
        dbReadyCallback=callback;
        status=0;
        loadingDialog=  new LoadingDialog();
        loadingDialog.showDialog(context, "Please Wait!", "Downloading Database...");

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

    }

    private Retrofit getRetroInstance(String url) {
        try {
            Retrofit retrofit = null;
            Log.i("autolog", "retrofit");

            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Log.i("autolog", "build();");
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
                    GroupUserMaster master = groupUserMasterList.get(0);
                    String checkData = master.getUSERNAME();
                    if(checkData ==null || checkData.isEmpty()){
                       Toast.makeText(context,"Please Insert Valid Store ID!",Toast.LENGTH_LONG).show();
                             loadingDialog.cancelDialog();

                    }else {
                        InsertDataGroupUserMaster(groupUserMasterList);
                    }
                }

                @Override
                public void onFailure(Call<List<GroupUserMaster>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                     loadingDialog.cancelDialog();
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
                     loadingDialog.cancelDialog();
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
                     loadingDialog.cancelDialog();
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

    public void getRetailStore() {
        try {
            Retrofit retrofit = getRetroInstance(baseUrl);
            ApiInterface service = retrofit.create(ApiInterface.class);
            Call<List<RetailStore>> call = service.getRetail_store(generateTableUrl("retail_store",storeId));
            call.enqueue(new Callback<List<RetailStore>>() {
                @Override
                public void onResponse(Call<List<RetailStore>> call, Response<List<RetailStore>> response) {
                    retailStoreList = response.body();
                    Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                  InsertRetailStore(retailStoreList);
                }

                @Override
                public void onFailure(Call<List<RetailStore>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getTerminalUserAlloc() {
        try {
            Retrofit retrofit = getRetroInstance(baseUrl);
            ApiInterface service = retrofit.create(ApiInterface.class);
            Call<List<TerminalUserAllocation>> call = service.getTerminalUser_Alloc(generateTableUrl("terminal_user_allocation",storeId));
            call.enqueue(new Callback<List<TerminalUserAllocation>>() {
                @Override
                public void onResponse(@NonNull Call<List<TerminalUserAllocation>> call, Response<List<TerminalUserAllocation>> response) {
                    terminalUserAllocations = response.body();
                    Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                   InsertTerminalUserAlloc(terminalUserAllocations);
                }

                @Override
                public void onFailure(@NonNull Call<List<TerminalUserAllocation>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getTerminalConfig() {
        try {
            Retrofit retrofit = getRetroInstance(baseUrl);
            ApiInterface service = retrofit.create(ApiInterface.class);
            Call<List<TerminalConfiguration>> call = service.getTerminal_Config(generateTableUrl("terminal_configuration",storeId));
            call.enqueue(new Callback<List<TerminalConfiguration>>() {
                @Override
                public void onResponse(Call<List<TerminalConfiguration>> call, Response<List<TerminalConfiguration>> response) {
                    terminalConfigurations = response.body();
                    Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                   InsertTerminalConnfig(terminalConfigurations);
                }

                @Override
                public void onFailure(Call<List<TerminalConfiguration>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getShiftMaster() {
        try {
            Retrofit retrofit = getRetroInstance(baseUrl);
            ApiInterface service = retrofit.create(ApiInterface.class);
            Call<List<ShiftMaster>> call = service.getShift_Master(generateTableUrl("master_shift",storeId));
            call.enqueue(new Callback<List<ShiftMaster>>() {
                @Override
                public void onResponse(Call<List<ShiftMaster>> call, Response<List<ShiftMaster>> response) {
                    shiftMasters = response.body();
                    Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                    InsertShiftMaster(shiftMasters);
                }

                @Override
                public void onFailure(Call<List<ShiftMaster>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getDeliveryType() {
        try {
            Retrofit retrofit = getRetroInstance(baseUrl);
            ApiInterface service = retrofit.create(ApiInterface.class);
            Call<List<DeliveryTypeMaster>> call = service.getDelivery_Type(generateTableUrl("masterdeliverytype",storeId));
            call.enqueue(new Callback<List<DeliveryTypeMaster>>() {
                @Override
                public void onResponse(Call<List<DeliveryTypeMaster>> call, Response<List<DeliveryTypeMaster>> response) {
                    deliveryTypeMasters = response.body();
                    Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                    InsertDeliveryType(deliveryTypeMasters);
                }

                @Override
                public void onFailure(Call<List<DeliveryTypeMaster>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getPaymentMode() {
        try {
            Retrofit retrofit = getRetroInstance(baseUrl);
            ApiInterface service = retrofit.create(ApiInterface.class);
            Call<List<PaymentModeMaster>> call = service.getPayemtMode_Master(generateTableUrl("masterpaymode",storeId));
            call.enqueue(new Callback<List<PaymentModeMaster>>() {
                @Override
                public void onResponse(Call<List<PaymentModeMaster>> call, Response<List<PaymentModeMaster>> response) {
                    paymentModeMasters = response.body();
                    Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                    InsertPaymentModeMaster(paymentModeMasters);
                }

                @Override
                public void onFailure(Call<List<PaymentModeMaster>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getBillPayDetail() {
        try {
            Retrofit retrofit = getRetroInstance(baseUrl);
            ApiInterface service = retrofit.create(ApiInterface.class);
            Call<List<BillPayDetail>> call = service.getBillPay_Detail(generateTableUrl("billpaydetail",storeId));
            call.enqueue(new Callback<List<BillPayDetail>>() {
                @Override
                public void onResponse(Call<List<BillPayDetail>> call, Response<List<BillPayDetail>> response) {
                    billPayDetailList = response.body();
                    Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                    InsertBillPayDetail(billPayDetailList);
                }

                @Override
                public void onFailure(Call<List<BillPayDetail>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getShiftTransactions() {
        try {
            Retrofit retrofit = getRetroInstance(baseUrl);
            ApiInterface service = retrofit.create(ApiInterface.class);
            Call<List<ShiftTrans>> call = service.getShiftTrans(generateTableUrl("shift_trans",storeId));
            call.enqueue(new Callback<List<ShiftTrans>>() {
                @Override
                public void onResponse(Call<List<ShiftTrans>> call, Response<List<ShiftTrans>> response) {
                    shiftTransList = response.body();
                    Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                    InsertShiftTrans(shiftTransList);
                }

                @Override
                public void onFailure(Call<List<ShiftTrans>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getMasterCategory() {
        try {
            Retrofit retrofit = getRetroInstance(baseUrl);
            ApiInterface service = retrofit.create(ApiInterface.class);
            Call<List<MasterCategory>> call = service.getMasterCategory(generateTableUrl("master_category",storeId));
            call.enqueue(new Callback<List<MasterCategory>>() {
                @Override
                public void onResponse(Call<List<MasterCategory>> call, Response<List<MasterCategory>> response) {
                    masterCategoryList = response.body();
                    Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                    InsertMasterCategory(masterCategoryList);
                }

                @Override
                public void onFailure(Call<List<MasterCategory>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getMasterSubCategory() {
        try {
            Retrofit retrofit = getRetroInstance(baseUrl);
            ApiInterface service = retrofit.create(ApiInterface.class);
            Call<List<MasterSubcategory>> call = service.getMasterSubCategory(generateTableUrl("master_subcategory",storeId));
            call.enqueue(new Callback<List<MasterSubcategory>>() {
                @Override
                public void onResponse(Call<List<MasterSubcategory>> call, Response<List<MasterSubcategory>> response) {
                    masterSubcategoryList = response.body();
                    Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                    InsertMasterSubCategory(masterSubcategoryList);
                }

                @Override
                public void onFailure(Call<List<MasterSubcategory>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }


    public void getHsnMaster() {
        try {
            Retrofit retrofit = getRetroInstance(baseUrl);
            ApiInterface service = retrofit.create(ApiInterface.class);
            Call<List<HSNMaster>> call = service.getHsnMaster(generateTableUrl("hsn_master",storeId));
            call.enqueue(new Callback<List<HSNMaster>>() {
                @Override
                public void onResponse(Call<List<HSNMaster>> call, Response<List<HSNMaster>> response) {
                    hsnMasterList = response.body();
                    Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                    InsertHsnMaster(hsnMasterList);
                }

                @Override
                public void onFailure(Call<List<HSNMaster>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }


    public void getVendorMaster() {
        try {
            Retrofit retrofit = getRetroInstance(baseUrl);
            ApiInterface service = retrofit.create(ApiInterface.class);
            Call<List<VendorMaster>> call = service.getVendorMaster(generateTableUrl("retail_str_dstr",storeId));
            call.enqueue(new Callback<List<VendorMaster>>() {
                @Override
                public void onResponse(Call<List<VendorMaster>> call, Response<List<VendorMaster>> response) {
                    vendorMasterList = response.body();
                    Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                    InsertVendorMaster(vendorMasterList);
                }

                @Override
                public void onFailure(Call<List<VendorMaster>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }


    public void getMasterUom() {
        try {
            Retrofit retrofit = getRetroInstance(baseUrl);
            ApiInterface service = retrofit.create(ApiInterface.class);
            Call<List<MasterUOM>> call = service.getMasterUom(generateTableUrl("master_uom",storeId));
            call.enqueue(new Callback<List<MasterUOM>>() {
                @Override
                public void onResponse(Call<List<MasterUOM>> call, Response<List<MasterUOM>> response) {
                    masterUOMList = response.body();
                    Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                    InsertMasterUom(masterUOMList);
                }

                @Override
                public void onFailure(Call<List<MasterUOM>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getMasterCustomerType() {
        try {
            Retrofit retrofit = getRetroInstance(baseUrl);
            assert retrofit != null;
            ApiInterface service = retrofit.create(ApiInterface.class);
            Call<List<MasterCustomerType>> call = service.getCustomerType(generateTableUrl("master_customer_type",storeId));
            call.enqueue(new Callback<List<MasterCustomerType>>() {
                @Override
                public void onResponse(Call<List<MasterCustomerType>> call, Response<List<MasterCustomerType>> response) {
                    masterCustomerTypeList = response.body();
                    Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                    InsertCustomerType(masterCustomerTypeList);
                }

                @Override
                public void onFailure(Call<List<MasterCustomerType>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getCustomerAddress() {
        try {
            Retrofit retrofit = getRetroInstance(baseUrl);
            assert retrofit != null;
            ApiInterface service = retrofit.create(ApiInterface.class);
            Call<List<CustomerAddress>> call = service.getCustomerAddress(generateTableUrl("retail_cust_address",storeId));
            call.enqueue(new Callback<List<CustomerAddress>>() {
                @Override
                public void onResponse(Call<List<CustomerAddress>> call, Response<List<CustomerAddress>> response) {
                    customerAddressList = response.body();
                    Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                    InsertCustomerAddress(customerAddressList);
                }

                @Override
                public void onFailure(Call<List<CustomerAddress>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }


    public void getMasterState() {
        try {
            Retrofit retrofit = getRetroInstance(baseUrl);
            assert retrofit != null;
            ApiInterface service = retrofit.create(ApiInterface.class);
            Call<List<MasterState>> call = service.getMasterState(generateTableUrl("masterState",storeId));
            call.enqueue(new Callback<List<MasterState>>() {
                @Override
                public void onResponse(Call<List<MasterState>> call, Response<List<MasterState>> response) {
                    masterStateList = response.body();
                    Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                    InsertMasterState(masterStateList);
                }

                @Override
                public void onFailure(Call<List<MasterState>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getBankMaster() {
        try {
            Retrofit retrofit = getRetroInstance(baseUrl);
            assert retrofit != null;
            ApiInterface service = retrofit.create(ApiInterface.class);
            Call<List<BankDetails>> call = service.getBankDetails(generateTableUrl("bank_details",storeId));
            call.enqueue(new Callback<List<BankDetails>>() {
                @Override
                public void onResponse(Call<List<BankDetails>> call, Response<List<BankDetails>> response) {
                     bankDetailsList= response.body();
                    Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                    InsertBankMaster(bankDetailsList);
                }

                @Override
                public void onFailure(Call<List<BankDetails>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getStoreConfig() {
        try {
            Retrofit retrofit = getRetroInstance(baseUrl);
            assert retrofit != null;
            ApiInterface service = retrofit.create(ApiInterface.class);
            Call<List<StoreConfiguration>> call = service.getStoreConfiguration(generateTableUrl("store_configuration",storeId));
            call.enqueue(new Callback<List<StoreConfiguration>>() {
                @Override
                public void onResponse(Call<List<StoreConfiguration>> call, Response<List<StoreConfiguration>> response) {
                    storeConfigurationList= response.body();
                    Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                    InsertStoreConfig(storeConfigurationList);
                }

                @Override
                public void onFailure(Call<List<StoreConfiguration>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }


    }
    public void getCustomerReturnMaster() {
        try {
            Retrofit retrofit = getRetroInstance(baseUrl);
            assert retrofit != null;
            ApiInterface service = retrofit.create(ApiInterface.class);
            Call<List<CustomerReturnMaster>> call = service.getCustomerReturnMaster(generateTableUrl("customerReturnMaster",storeId));
            call.enqueue(new Callback<List<CustomerReturnMaster>>() {
                @Override
                public void onResponse(Call<List<CustomerReturnMaster>> call, Response<List<CustomerReturnMaster>> response) {
                    customerReturnMasterList = response.body();
                    Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                    InsertCustomerReturnMaster(customerReturnMasterList);
                }

                @Override
                public void onFailure(Call<List<CustomerReturnMaster>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }


    public void getCustomerReturnDetails() {
        try {
            Retrofit retrofit = getRetroInstance(baseUrl);
            assert retrofit != null;
            ApiInterface service = retrofit.create(ApiInterface.class);
            Call<List<CustomerReturnDetails>> call = service.getCustomerReturnDetails(generateTableUrl("customerReturnDetail",storeId));
            call.enqueue(new Callback<List<CustomerReturnDetails>>() {
                @Override
                public void onResponse(Call<List<CustomerReturnDetails>> call, Response<List<CustomerReturnDetails>> response) {
                    customerReturnDetailsList = response.body();
                    Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                    InsertCustomerReturnDetails(customerReturnDetailsList);
                }

                @Override
                public void onFailure(Call<List<CustomerReturnDetails>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getCustomerReject() {
        try {
            Retrofit retrofit = getRetroInstance(baseUrl);
            assert retrofit != null;
            ApiInterface service = retrofit.create(ApiInterface.class);
            Call<List<CustomerReject>> call = service.getCustomerReject(generateTableUrl("retail_store_cust_reject",storeId));
            call.enqueue(new Callback<List<CustomerReject>>() {
                @Override
                public void onResponse(Call<List<CustomerReject>> call, Response<List<CustomerReject>> response) {
                    customerRejectList= response.body();
                    Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                    InsertCustomerReject(customerRejectList);
                }

                @Override
                public void onFailure(Call<List<CustomerReject>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }


    public void getCustomerCredit() {
        try {
            Retrofit retrofit = getRetroInstance(baseUrl);
            assert retrofit != null;
            ApiInterface service = retrofit.create(ApiInterface.class);
            Call<List<CustomerCredit>> call = service.getCustomerCredit(generateTableUrl("retail_credit_cust",storeId));
            call.enqueue(new Callback<List<CustomerCredit>>() {
                @Override
                public void onResponse(Call<List<CustomerCredit>> call, Response<List<CustomerCredit>> response) {
                    customerCreditList= response.body();
                    Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                    InsertCustomerCredit(customerCreditList);
                }

                @Override
                public void onFailure(Call<List<CustomerCredit>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getCreditBillDetails() {
        try {
            Retrofit retrofit = getRetroInstance(baseUrl);
            assert retrofit != null;
            ApiInterface service = retrofit.create(ApiInterface.class);
            Call<List<CreditBillDetails>> call = service.getCreditBillDetails(generateTableUrl("retail_credit_bill_details",storeId));
            call.enqueue(new Callback<List<CreditBillDetails>>() {
                @Override
                public void onResponse(Call<List<CreditBillDetails>> call, Response<List<CreditBillDetails>> response) {
                    creditBillDetails= response.body();
                    Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                    InsertCreditBillDetails(creditBillDetails);
                }

                @Override
                public void onFailure(Call<List<CreditBillDetails>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getCustomerLedger() {
        try {
            Retrofit retrofit = getRetroInstance(baseUrl);
            assert retrofit != null;
            ApiInterface service = retrofit.create(ApiInterface.class);
            Call<List<CustomerLedger>> call = service.getCustomerLedger(generateTableUrl("customerLedger",storeId));
            call.enqueue(new Callback<List<CustomerLedger>>() {
                @Override
                public void onResponse(Call<List<CustomerLedger>> call, Response<List<CustomerLedger>> response) {
                    customerLedgerList= response.body();
                    Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                    InsertCustomerLedger(customerLedgerList);
                }

                @Override
                public void onFailure(Call<List<CustomerLedger>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getStockRegister() {
        try {
            Retrofit retrofit = getRetroInstance(baseUrl);
            assert retrofit != null;
            ApiInterface service = retrofit.create(ApiInterface.class);
            Call<List<StockRegister>> call = service.getStockRegister(generateTableUrl("stock_register",storeId));
            call.enqueue(new Callback<List<StockRegister>>() {
                @Override
                public void onResponse(Call<List<StockRegister>> call, Response<List<StockRegister>> response) {
                    stockRegisterList= response.body();
                    Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                    InsertStockRegister(stockRegisterList);
                }

                @Override
                public void onFailure(Call<List<StockRegister>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }
    public void getGrnDetails() {
        try {
            Retrofit retrofit = getRetroInstance(baseUrl);
            assert retrofit != null;
            ApiInterface service = retrofit.create(ApiInterface.class);
            Call<List<GRNDetails>> call = service.getGrnDetails(generateTableUrl("retail_str_grn_detail",storeId));
            call.enqueue(new Callback<List<GRNDetails>>() {
                @Override
                public void onResponse(Call<List<GRNDetails>> call, Response<List<GRNDetails>> response) {
                    grnDetailsList= response.body();
                    Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                    InsertGrnDetails(grnDetailsList);
                }

                @Override
                public void onFailure(Call<List<GRNDetails>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }


    public void getGrnMasters() {
        try {
            Retrofit retrofit = getRetroInstance(baseUrl);
            assert retrofit != null;
            ApiInterface service = retrofit.create(ApiInterface.class);
            Call<List<GRNMaster>> call = service.getGrnMaster(generateTableUrl("retail_str_grn_master",storeId));
            call.enqueue(new Callback<List<GRNMaster>>() {
                @Override
                public void onResponse(Call<List<GRNMaster>> call, Response<List<GRNMaster>> response) {
                    grnMasterList= response.body();
                    Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                    InsertGrnMasters(grnMasterList);
                }

                @Override
                public void onFailure(Call<List<GRNMaster>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getVendorPayDetails() {
        try {
            Retrofit retrofit = getRetroInstance(baseUrl);
            assert retrofit != null;
            ApiInterface service = retrofit.create(ApiInterface.class);
            Call<List<VendorPayDetail>> call = service.getVendorPayDetail(generateTableUrl("VendorPayDetail",storeId));
            call.enqueue(new Callback<List<VendorPayDetail>>() {
                @Override
                public void onResponse(Call<List<VendorPayDetail>> call, Response<List<VendorPayDetail>> response) {
                    vendorPayDetailList= response.body();
                    Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                  InsertVendorPayDetails(vendorPayDetailList);
                }

                @Override
                public void onFailure(Call<List<VendorPayDetail>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getVendorPayMaster() {
        try {
            Retrofit retrofit = getRetroInstance(baseUrl);
            assert retrofit != null;
            ApiInterface service = retrofit.create(ApiInterface.class);
            Call<List<VendorPayMaster>> call = service.getVendorPayMaster(generateTableUrl("VendorPayMaster",storeId));
            call.enqueue(new Callback<List<VendorPayMaster>>() {
                @Override
                public void onResponse(Call<List<VendorPayMaster>> call, Response<List<VendorPayMaster>> response) {
                    vendorPayMasterList= response.body();
                    Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                    InsertVendorPayMaster(vendorPayMasterList);
                }

                @Override
                public void onFailure(Call<List<VendorPayMaster>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getVendorRejectReason() {
        try {
            Retrofit retrofit = getRetroInstance(baseUrl);
            assert retrofit != null;
            ApiInterface service = retrofit.create(ApiInterface.class);
            Call<List<VendorRejectReason>> call = service.getVendorRejectReason(generateTableUrl("retail_store_vend_reject",storeId));
            call.enqueue(new Callback<List<VendorRejectReason>>() {
                @Override
                public void onResponse(Call<List<VendorRejectReason>> call, Response<List<VendorRejectReason>> response) {
                    vendorRejectReasonList= response.body();
                    Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                    InsertVendorReject(vendorRejectReasonList);
                }

                @Override
                public void onFailure(Call<List<VendorRejectReason>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getVendorDetailReturn() {
        try {
            Retrofit retrofit = getRetroInstance(baseUrl);
            assert retrofit != null;
            ApiInterface service = retrofit.create(ApiInterface.class);
            Call<List<VendorDetailReturn>> call = service.getVendorDetailReturn(generateTableUrl("retail_str_vendor_detail_return",storeId));
            call.enqueue(new Callback<List<VendorDetailReturn>>() {
                @Override
                public void onResponse(Call<List<VendorDetailReturn>> call, Response<List<VendorDetailReturn>> response) {
                    vendorDetailReturnList= response.body();
                    Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                    InsertVendorDetailReturn(vendorDetailReturnList);
                }
                @Override
                public void onFailure(Call<List<VendorDetailReturn>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
        }
    }

    public void getVendorMasterReturn() {
        try {
            Retrofit retrofit = getRetroInstance(baseUrl);
            assert retrofit != null;
            ApiInterface service = retrofit.create(ApiInterface.class);
            Call<List<VendorMasterReturn>> call = service.getVendorMasterReturn(generateTableUrl("retail_str_vendor_master_return",storeId));
            call.enqueue(new Callback<List<VendorMasterReturn>>() {
                @Override
                public void onResponse(Call<List<VendorMasterReturn>> call, Response<List<VendorMasterReturn>> response) {
                    vendorMasterReturnList= response.body();
                    Log.i("autolog", "RetrievedTabaleData" + response.body().toString());
                    InsertVendorMasterReturn(vendorMasterReturnList);
                }
                @Override
                public void onFailure(Call<List<VendorMasterReturn>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("autolog", "Exception");
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
                loadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
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
                loadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
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
                loadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
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
                loadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
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
                loadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
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
                loadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
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
                loadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
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
                loadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
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
                loadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
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
                loadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
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
                loadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
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
                loadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
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
                loadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
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
                loadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
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

                myDataBase.insert("store_configuration", null, contentValues);

            }
            myDataBase.close();
            status+=1;
            if(status==tableConstant){
                loadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
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
                loadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
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
                loadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
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
                loadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
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
                loadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
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
                loadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
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
                loadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
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
                loadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
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
                loadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
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
                loadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
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
                 loadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
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
                 loadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
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
                 loadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
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
                 loadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
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
                 loadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
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
                 loadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
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
                 loadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
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

                myDataBase.insert("retail_store", null, contentValues);

               // SQLiteDbInspector.PrintTableData(myDataBase, "retail_store");
                // myDataBase.close(); // Closing database connection
            }
            myDataBase.close();
            status+=1;
            if(status==tableConstant){
                 loadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
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
                 loadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
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
                 loadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
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
                 loadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
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
                 loadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
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
                 loadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
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
                 loadingDialog.cancelDialog();
                dbReadyCallback.onDBReady();
            }
            Log.d("Insertion Successful", "InsertRetailCust: "+status);
        } catch (Exception e) {
            e.printStackTrace();
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

            case "retail_store":
                return "ApiTest/TestData?STORE_ID="+storeid;

            case "terminal_user_allocation":
                return "ApiTest/TerminalUserAllocation?STORE_ID="+storeid;

            case "terminal_configuration":
                return "ApiTest/TerminalConfiguration?STORE_ID="+storeid;

            case "master_shift":
                return "ApiTest/ShiftMaster?STORE_ID="+storeid;

            case "masterdeliverytype":
                return "ApiTest/MasterDeliveryType?STORE_ID="+storeid;

            case "masterpaymode":
                return "ApiTest/MasterPayMode?STORE_ID="+storeid;

            case "billpaydetail":
                return "ApiTest/BillPayDetail?STORE_ID="+storeid;

            case "shift_trans":
                return "ApiTest/ShiftTransactions?STORE_ID="+storeid;

            case "master_category":
                return "ApiTest/MasterCategory?STORE_ID="+storeid;

            case "master_subcategory":
                return "ApiTest/MasterSubCategory?STORE_ID="+storeid;

            case "hsn_master":
                return "ApiTest/HsnMaster?STORE_ID="+storeid;

            case "retail_str_dstr":
                return "ApiTest/VendorMaster?STORE_ID="+storeid;

            case "master_uom":
                return "ApiTest/UomMaster?STORE_ID="+storeid;

            case "master_customer_type":
                return "ApiTest/MasterCustomerType?STORE_ID"+storeid;

            case "retail_cust_address":
                return "ApiTest/MasterCustomerAddress?STORE_ID="+storeid;

            case "masterState":
                return "ApiTest/MasterState?STORE_ID="+storeid;

            case "bank_details":
                return "ApiTest/BankMaster?STORE_ID="+storeid;

            case "store_configuration":
                return "ApiTest/StoreConfiguration?STORE_ID="+storeid;

            case "customerReturnDetail":
                return "ApiTest/CustomerReturnDetail?STORE_ID="+storeid;

            case "customerReturnMaster":
                return "ApiTest/CustomerReturnMaster?STORE_ID="+storeid;

            case "retail_store_cust_reject":
                return "ApiTest/RetailStoreCustReject?STORE_ID="+storeid;

            case "retail_credit_cust":
                return "ApiTest/RetailCreditCust?STORE_ID="+storeid;

            case "customerLedger":
                return "ApiTest/CustomerLedger?STORE_ID="+storeid;

            case "retail_credit_bill_details":
                return "ApiTest/RetailCreditBillDetails?STORE_ID="+storeid;

            case "stock_register":
                return "ApiTest/StockRegister?STORE_ID="+storeid;

            case "retail_str_grn_detail":
                return "ApiTest/GrnDetail?STORE_ID="+storeid;

            case "retail_str_grn_master":
                return "ApiTest/GrnMaster?STORE_ID="+storeid;

            case "VendorPayMaster":
                return "ApiTest/VendorPayMaster?STORE_ID="+storeid;

            case "VendorPayDetail":
                return "ApiTest/VendorPayDetail?STORE_ID="+storeid;

            case "retail_store_vend_reject":
                return "ApiTest/RetailStoreVendReject?STORE_ID="+storeid;

            case "retail_str_vendor_detail_return":
                return "ApiTest/VendorReturnDetail?STORE_ID="+storeid;

            case "retail_str_vendor_master_return":
                return "ApiTest/VendorReturnMaster?STORE_ID="+storeid;

            default:
                return "";
        }

    }
}
