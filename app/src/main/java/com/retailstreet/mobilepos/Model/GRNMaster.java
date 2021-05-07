package com.retailstreet.mobilepos.Model;


/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */


public class GRNMaster {

    String GRN_GUID;
    String GRNNO;
    String GRNDate;
    String GRANDAMOUNT;
    String INVOICENO;
    String INVOICEDATE;
    String INVOICEDISCOUNT;
    String GRNPRINT;
    String GRNRECON;
    String GRN_STATUS;
    String CREATEDON;
    String GRNTYPE;
    String USER_GUID;
    String VENDOR_GUID;
    String STORE_GUID;
    String ISSYNCED;

    public GRNMaster() {

    }

    public GRNMaster(String GRN_GUID, String GRNNO, String GRNDate, String GRANDAMOUNT, String INVOICENO, String INVOICEDATE, String INVOICEDISCOUNT, String GRNPRINT, String GRNRECON, String GRN_STATUS, String CREATEDON, String GRNTYPE, String USER_GUID, String VENDOR_GUID, String STORE_GUID, String ISSYNCED) {
        this.GRN_GUID = GRN_GUID;
        this.GRNNO = GRNNO;
        this.GRNDate = GRNDate;
        this.GRANDAMOUNT = GRANDAMOUNT;
        this.INVOICENO = INVOICENO;
        this.INVOICEDATE = INVOICEDATE;
        this.INVOICEDISCOUNT = INVOICEDISCOUNT;
        this.GRNPRINT = GRNPRINT;
        this.GRNRECON = GRNRECON;
        this.GRN_STATUS = GRN_STATUS;
        this.CREATEDON = CREATEDON;
        this.GRNTYPE = GRNTYPE;
        this.USER_GUID = USER_GUID;
        this.VENDOR_GUID = VENDOR_GUID;
        this.STORE_GUID = STORE_GUID;
        this.ISSYNCED = ISSYNCED;
    }

    public String getGRN_GUID() {
        return GRN_GUID;
    }

    public void setGRN_GUID(String GRN_GUID) {
        this.GRN_GUID = GRN_GUID;
    }

    public String getGRNNO() {
        return GRNNO;
    }

    public void setGRNNO(String GRNNO) {
        this.GRNNO = GRNNO;
    }

    public String getGRNDate() {
        return GRNDate;
    }

    public void setGRNDate(String GRNDate) {
        this.GRNDate = GRNDate;
    }

    public String getGRANDAMOUNT() {
        return GRANDAMOUNT;
    }

    public void setGRANDAMOUNT(String GRANDAMOUNT) {
        this.GRANDAMOUNT = GRANDAMOUNT;
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

    public String getINVOICEDISCOUNT() {
        return INVOICEDISCOUNT;
    }

    public void setINVOICEDISCOUNT(String INVOICEDISCOUNT) {
        this.INVOICEDISCOUNT = INVOICEDISCOUNT;
    }

    public String getGRNPRINT() {
        return GRNPRINT;
    }

    public void setGRNPRINT(String GRNPRINT) {
        this.GRNPRINT = GRNPRINT;
    }

    public String getGRNRECON() {
        return GRNRECON;
    }

    public void setGRNRECON(String GRNRECON) {
        this.GRNRECON = GRNRECON;
    }

    public String getGRN_STATUS() {
        return GRN_STATUS;
    }

    public void setGRN_STATUS(String GRN_STATUS) {
        this.GRN_STATUS = GRN_STATUS;
    }

    public String getCREATEDON() {
        return CREATEDON;
    }

    public void setCREATEDON(String CREATEDON) {
        this.CREATEDON = CREATEDON;
    }

    public String getGRNTYPE() {
        return GRNTYPE;
    }

    public void setGRNTYPE(String GRNTYPE) {
        this.GRNTYPE = GRNTYPE;
    }

    public String getUSER_GUID() {
        return USER_GUID;
    }

    public void setUSER_GUID(String USER_GUID) {
        this.USER_GUID = USER_GUID;
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

    public String getISSYNCED() {
        return ISSYNCED;
    }

    public void setISSYNCED(String ISSYNCED) {
        this.ISSYNCED = ISSYNCED;
    }
}
