package com.retailstreet.mobilepos.Model;

/**
 * Created by Mayank Choudhary on 04-05-2021.
 * mayankchoudhary00@gmail.com
 */
public class VendorPayMaster {

    String VENDOR_PAYGUID;
    String VENDOR_PAYID;
    String VENDOR_GUID;
    String STORE_GUID;
    String INVOICENO;
    String INVOICEDATE;
    String INVOICEAMOUNT;
    String CREATEDBY;
    String UPDATEDBY;
    String CREATEDON;
    String UPDATEDON;
    String DUEAMOUNT;
    String PAIDFOR;
    String TYPEOFINVOICE;
    String ISYNCED;
    String VENDORPAY_STATUS;


    public VendorPayMaster() {
    }

    public VendorPayMaster(String VENDOR_PAYGUID, String VENDOR_PAYID, String VENDOR_GUID, String STORE_GUID, String INVOICENO, String INVOICEDATE, String INVOICEAMOUNT, String CREATEDBY, String UPDATEDBY, String CREATEDON, String UPDATEDON, String DUEAMOUNT, String PAIDFOR, String TYPEOFINVOICE, String ISYNCED, String VENDORPAY_STATUS) {
        this.VENDOR_PAYGUID = VENDOR_PAYGUID;
        this.VENDOR_PAYID = VENDOR_PAYID;
        this.VENDOR_GUID = VENDOR_GUID;
        this.STORE_GUID = STORE_GUID;
        this.INVOICENO = INVOICENO;
        this.INVOICEDATE = INVOICEDATE;
        this.INVOICEAMOUNT = INVOICEAMOUNT;
        this.CREATEDBY = CREATEDBY;
        this.UPDATEDBY = UPDATEDBY;
        this.CREATEDON = CREATEDON;
        this.UPDATEDON = UPDATEDON;
        this.DUEAMOUNT = DUEAMOUNT;
        this.PAIDFOR = PAIDFOR;
        this.TYPEOFINVOICE = TYPEOFINVOICE;
        this.ISYNCED = ISYNCED;
        this.VENDORPAY_STATUS = VENDORPAY_STATUS;
    }

    public String getVENDOR_PAYGUID() {
        return VENDOR_PAYGUID;
    }

    public void setVENDOR_PAYGUID(String VENDOR_PAYGUID) {
        this.VENDOR_PAYGUID = VENDOR_PAYGUID;
    }

    public String getVENDOR_PAYID() {
        return VENDOR_PAYID;
    }

    public void setVENDOR_PAYID(String VENDOR_PAYID) {
        this.VENDOR_PAYID = VENDOR_PAYID;
    }

    public String getVENDOR_GUID() {
        return VENDOR_GUID;
    }

    public void setVENDOR_GUID(String VENDOR_GUID) {
        this.VENDOR_GUID = VENDOR_GUID;
    }

    public String getSTORE_GUID() {
        return STORE_GUID;
    }

    public void setSTORE_GUID(String STORE_GUID) {
        this.STORE_GUID = STORE_GUID;
    }

    public String getINVOICENO() {
        return INVOICENO;
    }

    public void setINVOICENO(String INVOICENO) {
        this.INVOICENO = INVOICENO;
    }

    public String getINVOICEDATE() {
        return INVOICEDATE;
    }

    public void setINVOICEDATE(String INVOICEDATE) {
        this.INVOICEDATE = INVOICEDATE;
    }

    public String getINVOICEAMOUNT() {
        return INVOICEAMOUNT;
    }

    public void setINVOICEAMOUNT(String INVOICEAMOUNT) {
        this.INVOICEAMOUNT = INVOICEAMOUNT;
    }

    public String getCREATEDBY() {
        return CREATEDBY;
    }

    public void setCREATEDBY(String CREATEDBY) {
        this.CREATEDBY = CREATEDBY;
    }

    public String getUPDATEDBY() {
        return UPDATEDBY;
    }

    public void setUPDATEDBY(String UPDATEDBY) {
        this.UPDATEDBY = UPDATEDBY;
    }

    public String getCREATEDON() {
        return CREATEDON;
    }

    public void setCREATEDON(String CREATEDON) {
        this.CREATEDON = CREATEDON;
    }

    public String getUPDATEDON() {
        return UPDATEDON;
    }

    public void setUPDATEDON(String UPDATEDON) {
        this.UPDATEDON = UPDATEDON;
    }

    public String getDUEAMOUNT() {
        return DUEAMOUNT;
    }

    public void setDUEAMOUNT(String DUEAMOUNT) {
        this.DUEAMOUNT = DUEAMOUNT;
    }

    public String getPAIDFOR() {
        return PAIDFOR;
    }

    public void setPAIDFOR(String PAIDFOR) {
        this.PAIDFOR = PAIDFOR;
    }

    public String getTYPEOFINVOICE() {
        return TYPEOFINVOICE;
    }

    public void setTYPEOFINVOICE(String TYPEOFINVOICE) {
        this.TYPEOFINVOICE = TYPEOFINVOICE;
    }

    public String getISYNCED() {
        return ISYNCED;
    }

    public void setISYNCED(String ISYNCED) {
        this.ISYNCED = ISYNCED;
    }

    public String getVENDORPAY_STATUS() {
        return VENDORPAY_STATUS;
    }

    public void setVENDORPAY_STATUS(String VENDORPAY_STATUS) {
        this.VENDORPAY_STATUS = VENDORPAY_STATUS;
    }
}
