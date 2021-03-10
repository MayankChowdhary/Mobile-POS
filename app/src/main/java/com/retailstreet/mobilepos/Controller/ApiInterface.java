package com.retailstreet.mobilepos.Controller;



import com.retailstreet.mobilepos.Model.BillDetail;
import com.retailstreet.mobilepos.Model.BillMaster;
import com.retailstreet.mobilepos.Model.BillPayDetail;
import com.retailstreet.mobilepos.Model.DeliveryTypeMaster;
import com.retailstreet.mobilepos.Model.GroupUserMaster;
import com.retailstreet.mobilepos.Model.CustomerMaster;
import com.retailstreet.mobilepos.Model.PaymentModeMaster;
import com.retailstreet.mobilepos.Model.ProductMaster;
import com.retailstreet.mobilepos.Model.RetailStore;
import com.retailstreet.mobilepos.Model.ShiftMaster;
import com.retailstreet.mobilepos.Model.StockMaster;
import com.retailstreet.mobilepos.Model.TerminalConfiguration;
import com.retailstreet.mobilepos.Model.TerminalUserAllocation;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
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

    /*  @GET("ApiTest/BillMaster?STORE_ID="+storeid)
    Call<List<BillMaster>> getBill_Mater();*/

}
