package com.retailstreet.mobilepos.Model;

import androidx.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@Keep
public class BillPayInvoice {
    @SerializedName("BILLPAYDETAILID")
    @Expose
    String BILLPAYDETAILID;
    @SerializedName("BILLMASTERID")
    @Expose
    String BILLMASTERID;
    @SerializedName("MASTERPAYMODEGUID")
    @Expose
    String MASTERPAYMODEGUID;
    @SerializedName("PAYAMOUNT")
    @Expose
    String PAYAMOUNT;
    @SerializedName("TRANSACTIONNUMBER")
    @Expose
    String TRANSACTIONNUMBER;
    @SerializedName("ADDITIONALPARAM1")
    @Expose
    String ADDITIONALPARAM1;
    @SerializedName("ADDITIONALPARAM2")
    @Expose
    String ADDITIONALPARAM2;
    @SerializedName("ADDITIONALPARAM3")
    @Expose
    String ADDITIONALPARAM3;
    @SerializedName("BILLPAYDETAIL_STATUS")
    @Expose
    String BILLPAYDETAIL_STATUS;

    String STORE_GUID;
    String TERMINAL_GUID;
    String BILLNO;


    public BillPayInvoice(){

    }

    public BillPayInvoice(String BILLPAYDETAILID, String BILLMASTERID, String MASTERPAYMODEGUID, String PAYAMOUNT, String TRANSACTIONNUMBER, String ADDITIONALPARAM1, String ADDITIONALPARAM2, String ADDITIONALPARAM3, String BILLPAYDETAIL_STATUS, String STORE_GUID, String TERMINAL_GUID, String BILLNO) {
        this.BILLPAYDETAILID = BILLPAYDETAILID;
        this.BILLMASTERID = BILLMASTERID;
        this.MASTERPAYMODEGUID = MASTERPAYMODEGUID;
        this.PAYAMOUNT = PAYAMOUNT;
        this.TRANSACTIONNUMBER = TRANSACTIONNUMBER;
        this.ADDITIONALPARAM1 = ADDITIONALPARAM1;
        this.ADDITIONALPARAM2 = ADDITIONALPARAM2;
        this.ADDITIONALPARAM3 = ADDITIONALPARAM3;
        this.BILLPAYDETAIL_STATUS = BILLPAYDETAIL_STATUS;
        this.STORE_GUID = STORE_GUID;
        this.TERMINAL_GUID = TERMINAL_GUID;
        this.BILLNO = BILLNO;
    }

    public String getSTORE_GUID() {
        return STORE_GUID;
    }

    public void setSTORE_GUID(String STORE_GUID) {
        this.STORE_GUID = STORE_GUID;
    }

    public String getTERMINAL_GUID() {
        return TERMINAL_GUID;
    }

    public void setTERMINAL_GUID(String TERMINAL_GUID) {
        this.TERMINAL_GUID = TERMINAL_GUID;
    }

    public String getBILLNO() {
        return BILLNO;
    }

    public void setBILLNO(String BILLNO) {
        this.BILLNO = BILLNO;
    }

    public String getBILLPAYDETAILID() {
        return BILLPAYDETAILID;
    }

    public void setBILLPAYDETAILID(String BILLPAYDETAILID) {
        this.BILLPAYDETAILID = BILLPAYDETAILID;
    }

    public String getBILLMASTERID() {
        return BILLMASTERID;
    }

    public void setBILLMASTERID(String BILLMASTERID) {
        this.BILLMASTERID = BILLMASTERID;
    }

    public String getMASTERPAYMODEGUID() {
        return MASTERPAYMODEGUID;
    }

    public void setMASTERPAYMODEGUID(String MASTERPAYMODEGUID) {
        this.MASTERPAYMODEGUID = MASTERPAYMODEGUID;
    }

    public String getPAYAMOUNT() {
        return PAYAMOUNT;
    }

    public void setPAYAMOUNT(String PAYAMOUNT) {
        this.PAYAMOUNT = PAYAMOUNT;
    }

    public String getTRANSACTIONNUMBER() {
        return TRANSACTIONNUMBER;
    }

    public void setTRANSACTIONNUMBER(String TRANSACTIONNUMBER) {
        this.TRANSACTIONNUMBER = TRANSACTIONNUMBER;
    }

    public String getADDITIONALPARAM1() {
        return ADDITIONALPARAM1;
    }

    public void setADDITIONALPARAM1(String ADDITIONALPARAM1) {
        this.ADDITIONALPARAM1 = ADDITIONALPARAM1;
    }

    public String getADDITIONALPARAM2() {
        return ADDITIONALPARAM2;
    }

    public void setADDITIONALPARAM2(String ADDITIONALPARAM2) {
        this.ADDITIONALPARAM2 = ADDITIONALPARAM2;
    }

    public String getADDITIONALPARAM3() {
        return ADDITIONALPARAM3;
    }

    public void setADDITIONALPARAM3(String ADDITIONALPARAM3) {
        this.ADDITIONALPARAM3 = ADDITIONALPARAM3;
    }

    public String getBILLPAYDETAIL_STATUS() {
        return BILLPAYDETAIL_STATUS;
    }

    public void setBILLPAYDETAIL_STATUS(String BILLPAYDETAIL_STATUS) {
        this.BILLPAYDETAIL_STATUS = BILLPAYDETAIL_STATUS;
    }


}


