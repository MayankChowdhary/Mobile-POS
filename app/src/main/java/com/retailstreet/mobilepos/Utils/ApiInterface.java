package com.retailstreet.mobilepos.Utils;


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
import com.retailstreet.mobilepos.Model.GroupUserMaster;
import com.retailstreet.mobilepos.Model.HSNMaster;
import com.retailstreet.mobilepos.Model.InvoiceSyncResponse;
import com.retailstreet.mobilepos.Model.MasterCategory;
import com.retailstreet.mobilepos.Model.MasterCustomerType;
import com.retailstreet.mobilepos.Model.MasterState;
import com.retailstreet.mobilepos.Model.MasterSubcategory;
import com.retailstreet.mobilepos.Model.MasterUOM;
import com.retailstreet.mobilepos.Model.PaymentModeMaster;
import com.retailstreet.mobilepos.Model.ProductMaster;
import com.retailstreet.mobilepos.Model.RetailStore;
import com.retailstreet.mobilepos.Model.SalesRetunMaster;
import com.retailstreet.mobilepos.Model.SalesReturnDetails;
import com.retailstreet.mobilepos.Model.ShiftMaster;
import com.retailstreet.mobilepos.Model.ShiftTrans;
import com.retailstreet.mobilepos.Model.StockMaster;
import com.retailstreet.mobilepos.Model.StockRegister;
import com.retailstreet.mobilepos.Model.StoreConfiguration;
import com.retailstreet.mobilepos.Model.SyncResponse;
import com.retailstreet.mobilepos.Model.TerminalConfiguration;
import com.retailstreet.mobilepos.Model.TerminalUserAllocation;
import com.retailstreet.mobilepos.Model.VendorMaster;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;


public interface ApiInterface {

    @GET
    Call<List<GroupUserMaster>> getGroupUserMaster(@Url String url);

    @GET
    Call<List<CustomerMaster>> getRetail_Cust(@Url String url);

    @GET
    Call<List<ProductMaster>> getProduct_MAster(@Url String url);

    @GET
    Call<List<StockMaster>> getStock_Master(@Url String url);

    @GET
    Call<List<BillDetail>> getBill_Details(@Url String url);

    @GET
    Call<List<BillMaster>> getBill_Mater(@Url String url);

    @GET
    Call<List<RetailStore>> getRetail_store(@Url String url);

    @GET
    Call<List<TerminalUserAllocation>> getTerminalUser_Alloc(@Url String url);

    @GET
    Call<List<TerminalConfiguration>> getTerminal_Config(@Url String url);

    @GET
    Call<List<ShiftMaster>> getShift_Master(@Url String url);

    @GET
    Call<List<DeliveryTypeMaster>> getDelivery_Type(@Url String url);

    @GET
    Call<List<PaymentModeMaster>> getPayemtMode_Master(@Url String url);

    @GET
    Call<List<BillPayDetail>> getBillPay_Detail(@Url String url);

    @GET
    Call<List<ShiftTrans>> getShiftTrans(@Url String url);

    @GET
    Call<List<MasterCategory>> getMasterCategory(@Url String url);

    @GET
    Call<List<MasterSubcategory>> getMasterSubCategory(@Url String url);

    @GET
    Call<List<HSNMaster>> getHsnMaster(@Url String url);

    @GET
    Call<List<MasterUOM>> getMasterUom(@Url String url);

    @GET
    Call<List<VendorMaster>> getVendorMaster(@Url String url);

    @GET
    Call<List<CustomerAddress>> getCustomerAddress(@Url String url);

    @GET
    Call<List<MasterState>> getMasterState(@Url String url);

    @GET
    Call<List<MasterCustomerType>> getCustomerType(@Url String url);

    @GET
    Call<List<BankDetails>> getBankDetails(@Url String url);

    @GET
    Call<List<StoreConfiguration>> getStoreConfiguration(@Url String url);

    @GET
    Call<List<SalesRetunMaster>> getSalesReturnMaster(@Url String url);

    @GET
    Call<List<SalesReturnDetails>> getSalesReturnDetails(@Url String url);

    @GET
    Call<List<CustomerReject>> getCustomerReject(@Url String url);

    @GET
    Call<List<CustomerCredit>> getCustomerCredit(@Url String url);

    @GET
    Call<List<CustomerReturnMaster>> getCustomerReturnMaster(@Url String url);

    @GET
    Call<List<CustomerReturnDetails>> getCustomerReturnDetails(@Url String url);

    @GET
    Call<List<CustomerLedger>> getCustomerLedger(@Url String url);

    @GET
    Call<List<CreditBillDetails>> getCreditBillDetails(@Url String url);

    @GET
    Call<List<StockRegister>> getStockRegister(@Url String url);



    //****************************Upload API*****************************

   @POST("/APIManagers/api/PullPOSRegualarSync/PullBillHeaderDetails")
   Call<SyncResponse> UploadSaleRecords(@Header("Authorization")String Authorization, @Body RequestBody body);


    @POST("/APIManagers/api/PullPOSRegualarSync/PushShiftTransactions")
    Call<SyncResponse> UploadShifttransRecords(@Header("Authorization")String Authorization, @Body RequestBody body);

    @POST("/APIManagers/api/PullPOSRegualarSync/PullLocalProductDetails")
    Call<SyncResponse> UploadproductRecords(@Header("Authorization")String Authorization, @Body RequestBody body);

    @POST("/APIManagers/api/PullPOSRegualarSync/PullStockonHandDetails")
    Call<SyncResponse> UploadStockRecords(@Header("Authorization")String Authorization, @Body RequestBody body);

    @POST("/APIManagers/api/PullPOSRegualarSync/PushCustomerMasterDetails")
    Call<SyncResponse> UploadCustomerRecords(@Header("Authorization")String Authorization, @Body RequestBody body);

    @POST("/ApiTest/Invoice")
    Call<List<InvoiceSyncResponse>> UploadSaleRecordsSMS(@Body RequestBody body);

    @POST("/APIManagers/api/PullPOSRegualarSync/PushCustomerReturns")
    Call<SyncResponse> UploadSalesReturnRecords(@Header("Authorization")String Authorization, @Body RequestBody body);

    @POST("/APIManagers/api/PullPOSRegualarSync/PushCustomerLedger")
    Call<SyncResponse> UploadcustomerladgersRecords(@Header("Authorization")String Authorization, @Body RequestBody body);



}
