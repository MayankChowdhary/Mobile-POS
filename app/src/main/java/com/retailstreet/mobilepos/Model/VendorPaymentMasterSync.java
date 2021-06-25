package com.retailstreet.mobilepos.Model;

/**
 * Created by Mayank Choudhary on 04-05-2021.
 * mayankchoudhary00@gmail.com
 */
public class VendorPaymentMasterSync {
    String VENDOR_PAYID;
    String VENDOR_PAYGUID;
    String MASTER_STOREGUID;
    String MASTER_VENDORGUID;
    String INVOICENO;
    String INVOICEDATE;
    String INVOICEAMOUNT;
    String VENDORPAY_STATUS;
    String CREATEDBYGUID;
    String CREATEDON;
    String UPDATEDBYGUID;
    String UPDATEDON;
    String DUEAMOUNT;
    String PAIDFOR;
    String TYPEOFINVOICE;
    String MASTER_TERMINAL_ID;


    public VendorPaymentMasterSync() {

    }

    public VendorPaymentMasterSync(String VENDOR_PAYID, String VENDOR_PAYGUID, String MASTER_STOREGUID, String MASTER_VENDORGUID, String INVOICENO, String INVOICEDATE, String INVOICEAMOUNT, String VENDORPAY_STATUS, String CREATEDBYGUID, String CREATEDON, String UPDATEDBYGUID, String UPDATEDON, String DUEAMOUNT, String PAIDFOR, String TYPEOFINVOICE, String MASTER_TERMINAL_ID) {
        this.VENDOR_PAYID = VENDOR_PAYID;
        this.VENDOR_PAYGUID = VENDOR_PAYGUID;
        this.MASTER_STOREGUID = MASTER_STOREGUID;
        this.MASTER_VENDORGUID = MASTER_VENDORGUID;
        this.INVOICENO = INVOICENO;
        this.INVOICEDATE = INVOICEDATE;
        this.INVOICEAMOUNT = INVOICEAMOUNT;
        this.VENDORPAY_STATUS = VENDORPAY_STATUS;
        this.CREATEDBYGUID = CREATEDBYGUID;
        this.CREATEDON = CREATEDON;
        this.UPDATEDBYGUID = UPDATEDBYGUID;
        this.UPDATEDON = UPDATEDON;
        this.DUEAMOUNT = DUEAMOUNT;
        this.PAIDFOR = PAIDFOR;
        this.TYPEOFINVOICE = TYPEOFINVOICE;
        this.MASTER_TERMINAL_ID = MASTER_TERMINAL_ID;
    }

    public String getMASTER_TERMINAL_ID() {
        return MASTER_TERMINAL_ID;
    }

    public void setMASTER_TERMINAL_ID(String MASTER_TERMINAL_ID) {
        this.MASTER_TERMINAL_ID = MASTER_TERMINAL_ID;
    }

    public String getVENDOR_PAYID() {
        return VENDOR_PAYID;
    }

    public void setVENDOR_PAYID(String VENDOR_PAYID) {
        this.VENDOR_PAYID = VENDOR_PAYID;
    }

    public String getVENDOR_PAYGUID() {
        return VENDOR_PAYGUID;
    }

    public void setVENDOR_PAYGUID(String VENDOR_PAYGUID) {
        this.VENDOR_PAYGUID = VENDOR_PAYGUID;
    }

    public String getMASTER_STOREGUID() {
        return MASTER_STOREGUID;
    }

    public void setMASTER_STOREGUID(String MASTER_STOREGUID) {
        this.MASTER_STOREGUID = MASTER_STOREGUID;
    }

    public String getMASTER_VENDORGUID() {
        return MASTER_VENDORGUID;
    }

    public void setMASTER_VENDORGUID(String MASTER_VENDORGUID) {
        this.MASTER_VENDORGUID = MASTER_VENDORGUID;
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

    public String getVENDORPAY_STATUS() {
        return VENDORPAY_STATUS;
    }

    public void setVENDORPAY_STATUS(String VENDORPAY_STATUS) {
        this.VENDORPAY_STATUS = VENDORPAY_STATUS;
    }

    public String getCREATEDBYGUID() {
        return CREATEDBYGUID;
    }

    public void setCREATEDBYGUID(String CREATEDBYGUID) {
        this.CREATEDBYGUID = CREATEDBYGUID;
    }

    public String getCREATEDON() {
        return CREATEDON;
    }

    public void setCREATEDON(String CREATEDON) {
        this.CREATEDON = CREATEDON;
    }

    public String getUPDATEDBYGUID() {
        return UPDATEDBYGUID;
    }

    public void setUPDATEDBYGUID(String UPDATEDBYGUID) {
        this.UPDATEDBYGUID = UPDATEDBYGUID;
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
}

