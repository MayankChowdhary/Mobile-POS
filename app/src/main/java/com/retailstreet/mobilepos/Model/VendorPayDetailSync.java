package com.retailstreet.mobilepos.Model;

/**
 * Created by Mayank Choudhary on 04-05-2021.
 * mayankchoudhary00@gmail.com
 */
public class VendorPayDetailSync {

    String VENDOR_PAYDETAIL_ID;
    String VENDOR_PAYGUID;
    String BANK_GUID;
    String AMOUNTPAID;
    String PAYMENTDATE;
    String PAYMODE;
    String CHEQUENO;
    String CHEQUEDATE;

    public VendorPayDetailSync() {
    }

    public VendorPayDetailSync(String VENDOR_PAYDETAIL_ID, String VENDOR_PAYGUID, String BANK_GUID, String AMOUNTPAID, String PAYMENTDATE, String PAYMODE, String CHEQUENO, String CHEQUEDATE) {
        this.VENDOR_PAYDETAIL_ID = VENDOR_PAYDETAIL_ID;
        this.VENDOR_PAYGUID = VENDOR_PAYGUID;
        this.BANK_GUID = BANK_GUID;
        this.AMOUNTPAID = AMOUNTPAID;
        this.PAYMENTDATE = PAYMENTDATE;
        this.PAYMODE = PAYMODE;
        this.CHEQUENO = CHEQUENO;
        this.CHEQUEDATE = CHEQUEDATE;
    }

    public String getVENDOR_PAYDETAIL_ID() {
        return VENDOR_PAYDETAIL_ID;
    }

    public void setVENDOR_PAYDETAIL_ID(String VENDOR_PAYDETAIL_ID) {
        this.VENDOR_PAYDETAIL_ID = VENDOR_PAYDETAIL_ID;
    }

    public String getVENDOR_PAYGUID() {
        return VENDOR_PAYGUID;
    }

    public void setVENDOR_PAYGUID(String VENDOR_PAYGUID) {
        this.VENDOR_PAYGUID = VENDOR_PAYGUID;
    }

    public String getBANK_GUID() {
        return BANK_GUID;
    }

    public void setBANK_GUID(String BANK_GUID) {
        this.BANK_GUID = BANK_GUID;
    }

    public String getAMOUNTPAID() {
        return AMOUNTPAID;
    }

    public void setAMOUNTPAID(String AMOUNTPAID) {
        this.AMOUNTPAID = AMOUNTPAID;
    }

    public String getPAYMENTDATE() {
        return PAYMENTDATE;
    }

    public void setPAYMENTDATE(String PAYMENTDATE) {
        this.PAYMENTDATE = PAYMENTDATE;
    }

    public String getPAYMODE() {
        return PAYMODE;
    }

    public void setPAYMODE(String PAYMODE) {
        this.PAYMODE = PAYMODE;
    }

    public String getCHEQUENO() {
        return CHEQUENO;
    }

    public void setCHEQUENO(String CHEQUENO) {
        this.CHEQUENO = CHEQUENO;
    }

    public String getCHEQUEDATE() {
        return CHEQUEDATE;
    }

    public void setCHEQUEDATE(String CHEQUEDATE) {
        this.CHEQUEDATE = CHEQUEDATE;
    }
}
