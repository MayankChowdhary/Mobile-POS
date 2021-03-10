package com.retailstreet.mobilepos.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BillMaster {

    @SerializedName("BILLMASTERID")
    @Expose
    private String BILLMASTERID;

    @SerializedName("BILLNO")
    @Expose
    private String BILLNO;

    @SerializedName("SALEDATETIME")
    @Expose
    private String SALEDATETIME;

    @SerializedName("SALEDATE")
    @Expose
    private String SALEDATE;

    @SerializedName("SALETIME")
    @Expose
    private String SALETIME;

    @SerializedName("MASTERCUSTOMERGUID")
    @Expose
    private String MASTERCUSTOMERGUID;

    @SerializedName("MASTERSTOREGUID")
    @Expose
    private String MASTERSTOREGUID;

    @SerializedName("MASTERTERMINALGUID")
    @Expose
    private String MASTERTERMINALGUID;

    @SerializedName("MASTERSHIFTGUID")
    @Expose
    private String MASTERSHIFTGUID;

    @SerializedName("USER_GUID")
    @Expose
    private String USER_GUID;

    @SerializedName("CUST_MOBILENO")
    @Expose
    private String CUST_MOBILENO;

    @SerializedName("NETVALUE")
    @Expose
    private String NETVALUE;

    @SerializedName("TAXVALUE")
    @Expose
    private String TAXVALUE;

    @SerializedName("TOTAL_AMOUNT")
    @Expose
    private String TOTAL_AMOUNT;

    @SerializedName("DELIVERY_TYPE_GUID")
    @Expose
    private String DELIVERY_TYPE_GUID;

    @SerializedName("BILL_PRINT")
    @Expose
    private String BILL_PRINT;

    @SerializedName("TOTAL_BILL_AMOUNT")
    @Expose
    private String TOTAL_BILL_AMOUNT;

    @SerializedName("NO_OF_ITEMS")
    @Expose
    private String NO_OF_ITEMS;

    @SerializedName("BILLSTATUS")
    @Expose
    private String BILLSTATUS;

    @SerializedName("ISSYNCED")
    @Expose
    private String ISSYNCED;

    @SerializedName("RECEIVED_CASH")
    @Expose
    private String RECEIVED_CASH;

    @SerializedName("BALANCE_CASH")
    @Expose
    private String BALANCE_CASH;

    @SerializedName("ROUND_OFF")
    @Expose
    private String ROUND_OFF;

    @SerializedName("NETDISCOUNT")
    @Expose
    private String NETDISCOUNT;
    

    
    public  BillMaster(String BILLMASTERID,String BILLNO,String SALEDATETIME,String SALEDATE,String SALETIME,String MASTERCUSTOMERGUID,String MASTERSTOREGUID,String MASTERTERMINALGUID,String MASTERSHIFTGUID,String USER_GUID,String CUST_MOBILENO,String NETVALUE,String TAXVALUE,String TOTAL_AMOUNT,String DELIVERY_TYPE_GUID,String BILL_PRINT,String TOTAL_BILL_AMOUNT,String NO_OF_ITEMS,String BILLSTATUS,String ISSYNCED,String RECEIVED_CASH,String BALANCE_CASH,String ROUND_OFF,String NETDISCOUNT){

        this.BILLMASTERID =BILLMASTERID;
        this.BILLNO = BILLNO;
        this.SALEDATETIME = SALEDATETIME;
        this.SALEDATE = SALEDATE;
        this.SALETIME = SALETIME;
        this.MASTERCUSTOMERGUID = MASTERCUSTOMERGUID;
        this.MASTERSTOREGUID = MASTERSTOREGUID;
        this.MASTERTERMINALGUID = MASTERTERMINALGUID;
        this.MASTERSHIFTGUID = MASTERSHIFTGUID;
        this.USER_GUID = USER_GUID;
        this.CUST_MOBILENO = CUST_MOBILENO;
        this.NETVALUE = NETVALUE;
        this.TAXVALUE = TAXVALUE;
        this.TOTAL_AMOUNT =TOTAL_AMOUNT;
        this.DELIVERY_TYPE_GUID = DELIVERY_TYPE_GUID;
        this.BILL_PRINT = BILL_PRINT;
        this.TOTAL_BILL_AMOUNT = TOTAL_BILL_AMOUNT;
        this.NO_OF_ITEMS = NO_OF_ITEMS;
        this.BILLSTATUS = BILLSTATUS;
        this.ISSYNCED = ISSYNCED;
        this.RECEIVED_CASH = RECEIVED_CASH;
        this.BALANCE_CASH = BALANCE_CASH;
        this.ROUND_OFF = ROUND_OFF;
        this.NETDISCOUNT = NETDISCOUNT;
    }
    
    
    

    public String getBILLMASTERID() {
        return BILLMASTERID;
    }

    public void setBILLMASTERID(String BILLMASTERID) {
        this.BILLMASTERID = BILLMASTERID;
    }

    public String getBILLNO() {
        return BILLNO;
    }

    public void setBILLNO(String BILLNO) {
        this.BILLNO = BILLNO;
    }

    public String getSALEDATETIME() {
        return SALEDATETIME;
    }

    public void setSALEDATETIME(String SALEDATETIME) {
        this.SALEDATETIME = SALEDATETIME;
    }

    public String getSALEDATE() {
        return SALEDATE;
    }

    public void setSALEDATE(String SALEDATE) {
        this.SALEDATE = SALEDATE;
    }

    public String getSALETIME() {
        return SALETIME;
    }

    public void setSALETIME(String SALETIME) {
        this.SALETIME = SALETIME;
    }

    public String getMASTERCUSTOMERGUID() {
        return MASTERCUSTOMERGUID;
    }

    public void setMASTERCUSTOMERGUID(String MASTERCUSTOMERGUID) {
        this.MASTERCUSTOMERGUID = MASTERCUSTOMERGUID;
    }

    public String getMASTERSTOREGUID() {
        return MASTERSTOREGUID;
    }

    public void setMASTERSTOREGUID(String MASTERSTOREGUID) {
        this.MASTERSTOREGUID = MASTERSTOREGUID;
    }

    public String getMASTERTERMINALGUID() {
        return MASTERTERMINALGUID;
    }

    public void setMASTERTERMINALGUID(String MASTERTERMINALGUID) {
        this.MASTERTERMINALGUID = MASTERTERMINALGUID;
    }

    public String getMASTERSHIFTGUID() {
        return MASTERSHIFTGUID;
    }

    public void setMASTERSHIFTGUID(String MASTERSHIFTGUID) {
        this.MASTERSHIFTGUID = MASTERSHIFTGUID;
    }

    public String getUSER_GUID() {
        return USER_GUID;
    }

    public void setUSER_GUID(String USER_GUID) {
        this.USER_GUID = USER_GUID;
    }

    public String getCUST_MOBILENO() {
        return CUST_MOBILENO;
    }

    public void setCUST_MOBILENO(String CUST_MOBILENO) {
        this.CUST_MOBILENO = CUST_MOBILENO;
    }

    public String getNETVALUE() {
        return NETVALUE;
    }

    public void setNETVALUE(String NETVALUE) {
        this.NETVALUE = NETVALUE;
    }

    public String getTAXVALUE() {
        return TAXVALUE;
    }

    public void setTAXVALUE(String TAXVALUE) {
        this.TAXVALUE = TAXVALUE;
    }

    public String getTOTAL_AMOUNT() {
        return TOTAL_AMOUNT;
    }

    public void setTOTAL_AMOUNT(String TOTAL_AMOUNT) {
        this.TOTAL_AMOUNT = TOTAL_AMOUNT;
    }

    public String getDELIVERY_TYPE_GUID() {
        return DELIVERY_TYPE_GUID;
    }

    public void setDELIVERY_TYPE_GUID(String DELIVERY_TYPE_GUID) {
        this.DELIVERY_TYPE_GUID = DELIVERY_TYPE_GUID;
    }

    public String getBILL_PRINT() {
        return BILL_PRINT;
    }

    public void setBILL_PRINT(String BILL_PRINT) {
        this.BILL_PRINT = BILL_PRINT;
    }

    public String getTOTAL_BILL_AMOUNT() {
        return TOTAL_BILL_AMOUNT;
    }

    public void setTOTAL_BILL_AMOUNT(String TOTAL_BILL_AMOUNT) {
        this.TOTAL_BILL_AMOUNT = TOTAL_BILL_AMOUNT;
    }

    public String getNO_OF_ITEMS() {
        return NO_OF_ITEMS;
    }

    public void setNO_OF_ITEMS(String NO_OF_ITEMS) {
        this.NO_OF_ITEMS = NO_OF_ITEMS;
    }

    public String getBILLSTATUS() {
        return BILLSTATUS;
    }

    public void setBILLSTATUS(String BILLSTATUS) {
        this.BILLSTATUS = BILLSTATUS;
    }

    public String getISSYNCED() {
        return ISSYNCED;
    }

    public void setISSYNCED(String ISSYNCED) {
        this.ISSYNCED = ISSYNCED;
    }

    public String getRECEIVED_CASH() {
        return RECEIVED_CASH;
    }

    public void setRECEIVED_CASH(String RECEIVED_CASH) {
        this.RECEIVED_CASH = RECEIVED_CASH;
    }

    public String getBALANCE_CASH() {
        return BALANCE_CASH;
    }

    public void setBALANCE_CASH(String BALANCE_CASH) {
        this.BALANCE_CASH = BALANCE_CASH;
    }

    public String getROUND_OFF() {
        return ROUND_OFF;
    }

    public void setROUND_OFF(String ROUND_OFF) {
        this.ROUND_OFF = ROUND_OFF;
    }

    public String getNETDISCOUNT() {
        return NETDISCOUNT;
    }

    public void setNETDISCOUNT(String NETDISCOUNT) {
        this.NETDISCOUNT = NETDISCOUNT;
    }


    @NonNull
    @Override
    public String toString() {
        return ( BILLMASTERID + BILLNO + SALEDATETIME + SALEDATE + SALETIME + MASTERCUSTOMERGUID + MASTERSTOREGUID + MASTERTERMINALGUID + MASTERSHIFTGUID + USER_GUID + CUST_MOBILENO + NETVALUE + TAXVALUE + TOTAL_AMOUNT + DELIVERY_TYPE_GUID + BILL_PRINT + TOTAL_BILL_AMOUNT + NO_OF_ITEMS + BILLSTATUS + ISSYNCED + RECEIVED_CASH + BALANCE_CASH + ROUND_OFF + NETDISCOUNT);
    }
}
