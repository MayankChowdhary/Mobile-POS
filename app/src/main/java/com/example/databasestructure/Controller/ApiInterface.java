package com.example.databasestructure.Controller;



import com.example.databasestructure.Model.BillDetail;
import com.example.databasestructure.Model.BillMaster;
import com.example.databasestructure.Model.GroupUserMaster;
import com.example.databasestructure.Model.CustomerMaster;
import com.example.databasestructure.Model.ProductMaster;
import com.example.databasestructure.Model.StockMaster;

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

    /*  @GET("ApiTest/BillMaster?STORE_ID="+storeid)
    Call<List<BillMaster>> getBill_Mater();*/

}
