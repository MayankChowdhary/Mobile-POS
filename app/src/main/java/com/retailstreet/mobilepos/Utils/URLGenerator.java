package com.retailstreet.mobilepos.Utils;

/**
 * Created by Mayank Choudhary on 19-05-2021.
 * mayankchoudhary00@gmail.com
 */
public class URLGenerator {

    public static String generateTableUrl(String tablename , String storeid){

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
