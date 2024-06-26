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
import com.retailstreet.mobilepos.Model.DownloadcheckPojo;
import com.retailstreet.mobilepos.Model.GRNDetails;
import com.retailstreet.mobilepos.Model.GRNMaster;
import com.retailstreet.mobilepos.Model.GroupUserMaster;
import com.retailstreet.mobilepos.Model.HSNMaster;
import com.retailstreet.mobilepos.Model.LicenceModulePojo;
import com.retailstreet.mobilepos.Model.MasterCategory;
import com.retailstreet.mobilepos.Model.MasterCustomerType;
import com.retailstreet.mobilepos.Model.MasterState;
import com.retailstreet.mobilepos.Model.MasterSubcategory;
import com.retailstreet.mobilepos.Model.MasterUOM;
import com.retailstreet.mobilepos.Model.PaymentModeMaster;
import com.retailstreet.mobilepos.Model.ProductMaster;
import com.retailstreet.mobilepos.Model.RetailStore;
import com.retailstreet.mobilepos.Model.SMS_SYNC;
import com.retailstreet.mobilepos.Model.ShiftMaster;
import com.retailstreet.mobilepos.Model.ShiftTrans;
import com.retailstreet.mobilepos.Model.StockMaster;
import com.retailstreet.mobilepos.Model.StockRegister;
import com.retailstreet.mobilepos.Model.StoreConfiguration;
import com.retailstreet.mobilepos.Model.StoreParameter;
import com.retailstreet.mobilepos.Model.SyncResponse;
import com.retailstreet.mobilepos.Model.TerminalConfiguration;
import com.retailstreet.mobilepos.Model.TerminalUserAllocation;
import com.retailstreet.mobilepos.Model.VendorDetailReturn;
import com.retailstreet.mobilepos.Model.VendorMaster;
import com.retailstreet.mobilepos.Model.VendorMasterReturn;
import com.retailstreet.mobilepos.Model.VendorPayDetail;
import com.retailstreet.mobilepos.Model.VendorPayMaster;
import com.retailstreet.mobilepos.Model.VendorRejectReason;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

public interface ApiInterface {

    @GET("/APIMANAGER/api/CloudtoDevice/GetRetailStore/{StoreId}/{TerminalName}")//1
    Call<List<RetailStore>> loadRetailStore(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId, @Path("TerminalName") String TerminalName);

    @GET("/APIMANAGER/api/CloudtoDevice/GetStockRegister/{StoreId}/{TerminalName}")//1
    Call<List<StockRegister>> loadStockRegister(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId, @Path("TerminalName") String TerminalName);

    @GET("/APIMANAGER/api/CloudtoDevice/GetBankDetails/{StoreId}/{TerminalName}")//1
    Call<List<BankDetails>> loadBankMaster(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId,@Path("TerminalName") String TerminalName);

    @GET("/APIMANAGER/api/CloudtoDevice/GetBillMaster/{StoreId}/{TerminalName}")//2
    Call<List<BillMaster>> loadBillMaster(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId, @Path("TerminalName") String TerminalName);

    @GET("/APIMANAGER/api/CloudtoDevice/GetBillDetails/{StoreId}/{TerminalName}")//3
    Call<List<BillDetail>> loadBillDetail(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId, @Path("TerminalName") String TerminalName);

    @GET("/APIMANAGER/api/CloudtoDevice/GetBillPayDetail/{StoreId}/{TerminalName}")//4
    Call<List<BillPayDetail>> loadBillPayDetail(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId, @Path("TerminalName") String TerminalName);

    /*@GET("/APIMANAGER/api/CloudtoDevice/GetBillTaxDetail/{StoreId}")//5
    Call<List<BillTaxDetail>> loadBillTaxDetail(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId);*/


    @GET("/APIMANAGER/api/CloudtoDevice/GetCustomerLedger/{StoreId}/{TerminalName}")//8
    Call<List<CustomerLedger>> loadCustomerLedger(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId, @Path("TerminalName") String TerminalName);


    @GET("/APIMANAGER/api/CloudtoDevice/GetCustomerReturnDetail/{StoreId}/{TerminalName}")//9
    Call<List<CustomerReturnDetails>> loadCustomerReturnDetail(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId, @Path("TerminalName") String TerminalName);


    @GET("/APIMANAGER/api/CloudtoDevice/GetCustomerReturnMaster/{StoreId}/{TerminalName}")//10
    Call<List<CustomerReturnMaster>> loadCustomerReturnMaster(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId, @Path("TerminalName") String TerminalName);

    /*@GET("/APIMANAGER/api/CloudtoDevice/GetDrDiscription/{StoreId}")//11
    Call<List<DrDiscription>> loadDrDiscription(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId);*/

    /*@GET("/APIMANAGER/api/CloudtoDevice/GetDrSpeciality/{StoreId}")//12
    Call<List<DrSpeciality>> loadDrSpeciality(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId);*/

    @GET("/APIMANAGER/api/CloudtoDevice/GetGrnDetail/{StoreId}/{TerminalName}")//13
    Call<List<GRNDetails>> loadGrnDetail(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId, @Path("TerminalName") String TerminalName);


    @GET("/APIMANAGER/api/CloudtoDevice/GetGrnMaster/{StoreId}/{TerminalName}")//14
    Call<List<GRNMaster>> loadGrnMaster(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId, @Path("TerminalName") String TerminalName);

    @GET("/APIMANAGER/api/CloudtoDevice/GetGroupUserMaster/{StoreId}/{TerminalName}")//15
    Call<List<GroupUserMaster>> loadGroupUserMaster(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId, @Path("TerminalName") String TerminalName);

    @GET("/APIMANAGER/api/CloudtoDevice/GetHSNMaster/{StoreId}/{TerminalName}")//16
    Call<List<HSNMaster>> loadHsnMaster(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId, @Path("TerminalName") String TerminalName);

    @GET("/APIMANAGER/api/CloudtoDevice/GetMasterCategory/{StoreId}/{TerminalName}")//17
    Call<List<MasterCategory>> loadMasterCategory(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId, @Path("TerminalName") String TerminalName);

    /*@GET("/APIMANAGER/api/CloudtoDevice/GetMasterCountry/{StoreId}")//18
    Call<List<MasterCountry>> loadMasterCountry(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId);*/

    @GET("/APIMANAGER/api/CloudtoDevice/GetMasterCustomer/{StoreId}/{TerminalName}")//19
    Call<List<CustomerMaster>> loadMasterCustomer(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId, @Path("TerminalName") String TerminalName);

    @GET("/APIMANAGER/api/CloudtoDevice/GetMasterCustomerAddress/{StoreId}/{TerminalName}")//20
    Call<List<CustomerAddress>> loadMasterCustomerAddress(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId, @Path("TerminalName") String TerminalName);

    @GET("/APIMANAGER/api/CloudtoDevice/GetMasterCustomerType/{StoreId}")//21
    Call<List<MasterCustomerType>> loadMasterCustomerType(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId);

    @GET("/APIMANAGER/api/CloudtoDevice/GetMasterDeliveryType/{StoreId}")//22
    Call<List<DeliveryTypeMaster>> loadMasterDeliveryType(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId);

    @GET("/APIMANAGER/api/CloudtoDevice/GetMasterPayMode/{StoreId}")//23
    Call<List<PaymentModeMaster>> loadMasterPayMode(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId);


    @GET("/APIMANAGER/api/CloudtoDevice/GetMasterState/{StoreId}")//24
    Call<List<MasterState>> loadmasterstate(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId);
/*
    @GET("/APIMANAGER/api/CloudtoDevice/GetMasterStoreProductSpeciality/{StoreId}")//25
    Call<List<MasterStoreProductSpeciality>> loadMasterStoreProductSpeciality(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId);*/

   /* @GET("/APIMANAGER/api/CloudtoDevice/GetMasterStoreTopProductsMapping/{StoreId}")//26
    Call<List<MasterStoreTopProductsMapping>> loadMasterStoreTopProductsMapping(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId);*/

    @GET("/APIMANAGER/api/CloudtoDevice/GetMasterSubCategory/{StoreId}/{TerminalName}")//27
    Call<List<MasterSubcategory>> loadMasterSubCategory(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId, @Path("TerminalName") String TerminalName);

    /*@GET("/APIMANAGER/api/CloudtoDevice/GetMasterTable/{StoreId}")//28//pending
    Call<List<MasterTable>> loadMasterTable(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId);

    @GET("/APIMANAGER/api/CloudtoDevice/GetMasterTicket/{StoreId}")//29
    Call<List<MasterTicket>> loadMasterTicket(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId);
*/
    @GET("/APIMANAGER/api/CloudtoDevice/GetProductMaster/{StoreId}/{TerminalName}")//30
    Call<List<ProductMaster>> loadProductMaster(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId, @Path("TerminalName") String TerminalName);

    @GET("/APIMANAGER/api/CloudtoDevice/GetRetailCreditBillDetails/{StoreId}/{TerminalName}")//31
    Call<List<CreditBillDetails>> loadRetailCreditBillDetails(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId, @Path("TerminalName") String TerminalName);

    @GET("/APIMANAGER/api/CloudtoDevice/GetRetailCreditCust/{StoreId}/{TerminalName}")//32
    Call<List<CustomerCredit>> loadRetailCreditCust(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId, @Path("TerminalName") String TerminalName);

   /* @GET("/APIMANAGER/api/CloudtoDevice/GetEmployeeData/{StoreId}")//33
    Call<List<RetailEmployee>> loadRetailEmployee(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId);*/

    @GET("/APIMANAGER/api/CloudtoDevice/GetRetailStoreCustReject/{StoreId}/{TerminalName}")//34
    Call<List<CustomerReject>> loadRetailStoreCustReject(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId,@Path("TerminalName") String TerminalName);

    @GET("/APIMANAGER/api/CloudtoDevice/GetRetailStoreVendReject/{StoreId}/{TerminalName}")//35
    Call<List<VendorRejectReason>> loadRetailStoreVendReject(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId,@Path("TerminalName") String TerminalName);

  /*  @GET("/APIMANAGER/api/CloudtoDevice/GetRoleScreenMapping/{StoreId}")//36
    Call<List<>> loadRetailEmployee(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId);
*/

    @GET("/APIMANAGER/api/CloudtoDevice/GetShiftMaster/{StoreId}/{TerminalName}")//37
    Call<List<ShiftMaster>> loadShiftMaster(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId, @Path("TerminalName") String TerminalName);

    @GET("/APIMANAGER/api/CloudtoDevice/GetShiftTransactions/{StoreId}/{TerminalName}")//38
    Call<List<ShiftTrans>> loadShifttrans(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId,@Path("TerminalName") String TerminalName);

    @GET("/APIMANAGER/api/CloudtoDevice/GetStockMaster/{StoreId}/{TerminalName}")//39
    Call<List<StockMaster>> loadStockMaster(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId,@Path("TerminalName") String TerminalName);

    @GET("/APIMANAGER/api/CloudtoDevice/GetStoreConfiguration/{StoreId}/{TerminalName}")//40
    Call<List<StoreConfiguration>> loadStoreConfiguration(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId,@Path("TerminalName") String TerminalName);

    @GET("/APIMANAGER/api/CloudtoDevice/GetStoreParameter/{StoreId}/{TerminalName}")//41
    Call<List<StoreParameter>> loadStoreParameter(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId, @Path("TerminalName") String TerminalName);

   /* @GET("/APIMANAGER/api/CloudtoDevice/GetTaxDetail/{StoreId}")//42
    Call<List<TaxDetail>> loadTaxDetail(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId);

    @GET("/APIMANAGER/api/CloudtoDevice/GetTaxMaster/{StoreId}")//43
    Call<List<TaxMaster>> loadTaxMaster(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId);*/

    @GET("/APIMANAGER/api/CloudtoDevice/GetTerminalConfiguration/{StoreId}/{TerminalName}")//44
    Call<List<TerminalConfiguration>> loadTerminalConfiguration(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId, @Path("TerminalName") String TerminalName);


    @GET("/APIMANAGER/api/CloudtoDevice/GetTerminalUserAllocation/{StoreId}/{TerminalName}")//45
    Call<List<TerminalUserAllocation>> loadTerminalUserAllocationterminal(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId, @Path("TerminalName") String TerminalName);

    @GET("/APIMANAGER/api/CloudtoDevice/GetUoMMaster/{StoreId}")//46GetUoMMaster
    Call<List<MasterUOM>> loadUomMaster(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId);

    @GET("/APIMANAGER/api/CloudtoDevice/GetMasterVendor/{StoreId}/{TerminalName}")//47
    Call<List<VendorMaster>> loadVendorMaster(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId, @Path("TerminalName") String TerminalName);

    @GET("/APIMANAGER/api/CloudtoDevice/GetVendorPayDetail/{StoreId}/{TerminalName}")//48
    Call<List<VendorPayDetail>> loadVendorPayDetail(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId, @Path("TerminalName") String TerminalName);

    @GET("/APIMANAGER/api/CloudtoDevice/GetVendorPayMaster/{StoreId}/{TerminalName}")//49
    Call<List<VendorPayMaster>> loadVendorPayMaster(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId, @Path("TerminalName") String TerminalName);

    @GET("/APIMANAGER/api/CloudtoDevice/GetVendorReturnDetail/{StoreId}/{TerminalName}")//50
    Call<List<VendorDetailReturn>> loadVendorReturnDetail(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId, @Path("TerminalName") String TerminalName);

    @GET("/APIMANAGER/api/CloudtoDevice/GetVendorReturnMaster/{StoreId}/{TerminalName}")//51
    Call<List<VendorMasterReturn>> loadVendorReturnMaster(@Header("Authorization") String Authorization, @Path("StoreId") String StoreId, @Path("TerminalName") String TerminalName);



   /* @GET
    Call<List<GroupUserMaster>> getGroupUserMaster(@Url String url);
*/


    //***************Installation Validator************************

    @GET("APIMANAGER/api/GetAllMasterDataForSync/GetStoreLicenseandActiveData/{Storeguid}")
    Call<LicenceModulePojo> Store_Licence_Checked(@Header("Authorization")String Authorization, @Path("Storeguid") String Storeguid);
    @POST("APIMANAGER/api/PostStoreFinalStatus/PostRegistration")
    Call<DownloadcheckPojo> Check_Before_Download_Data(@Header("Authorization") String Authorization, @Body RequestBody body);

    //****************************Upload API*****************************

   @POST("APIMANAGER/api/PullPOSRegualarSync/PullBillHeaderDetails")
   Call<SyncResponse> UploadSaleRecords(@Header("Authorization")String Authorization, @Body RequestBody body);

    @POST("APIMANAGER/api/PullPOSRegualarSync/PushShiftTransactions")
    Call<SyncResponse> UploadShifttransRecords(@Header("Authorization")String Authorization, @Body RequestBody body);

    @POST("APIMANAGER/api/PullPOSRegualarSync/PullLocalProductDetails")
    Call<SyncResponse> UploadproductRecords(@Header("Authorization")String Authorization, @Body RequestBody body);

    @POST("APIMANAGER/api/PullPOSRegualarSync/PullStockonHandDetails")
    Call<SyncResponse> UploadStockRecords(@Header("Authorization")String Authorization, @Body RequestBody body);

    @POST("APIMANAGER/api/PullPOSRegualarSync/PushCustomerMasterDetails")
    Call<SyncResponse> UploadCustomerRecords(@Header("Authorization")String Authorization, @Body RequestBody body);

    @POST("ApiTest/Invoice")
    Call<SMS_SYNC> UploadSaleRecordsSMS(@Body RequestBody body);

    @POST("APIMANAGER/api/PullPOSRegualarSync/PushCustomerReturns")
    Call<SyncResponse> UploadSalesReturnRecords(@Header("Authorization")String Authorization, @Body RequestBody body);

    @POST("APIMANAGER/api/PullPOSRegualarSync/PushCustomerLedger")
    Call<SyncResponse> UploadcustomerladgersRecords(@Header("Authorization")String Authorization, @Body RequestBody body);

    @POST("APIMANAGER/api/PullPOSRegualarSync/PullGRNDetails")
    Call<SyncResponse> UploadGRNRecords(@Header("Authorization")String Authorization, @Body RequestBody body);

    @POST("APIMANAGER/api/PullPOSRegualarSync/PushInventoryLedger")
    Call<SyncResponse> UploadInventoryLedgerRecords(@Header("Authorization")String Authorization, @Body RequestBody body);

    @POST("APIMANAGER/api/PullPOSRegualarSync/PushVendorPayments")
    Call<SyncResponse> UploadvendorpaymentRecords(@Header("Authorization")String Authorization, @Body RequestBody body);

    @POST("APIMANAGER/api/PullPOSRegualarSync/PushVendorReturns")
    Call<SyncResponse> UploadVendor_Return_Records(@Header("Authorization") String Authorization, @Body RequestBody body);


    @POST("/APIMANAGER/api/PullPOSRegualarSync/PullVendorDetails")
    Call<SyncResponse> UploadVendorRecords(@Header("Authorization") String Authorization, @Body RequestBody body);


}
