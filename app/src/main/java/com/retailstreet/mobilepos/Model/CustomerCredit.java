package com.retailstreet.mobilepos.Model;

public class CustomerCredit {
    String PAYMENTID;
    String STORE_GUID;
    String CUSTOMERNAME;
    String CUSTOMERMOBILENO;
    String GRANDTOTAL;
    String CUSTOMERSTATUS;
    String RECEIVEAMOUNT;
    String DUEAMOUNT;
    String CUSTOMERGUID;
    String TOTALGST;

    public CustomerCredit() {
    }

    public CustomerCredit(String PAYMENTID, String STORE_GUID, String CUSTOMERNAME, String CUSTOMERMOBILENO, String GRANDTOTAL, String CUSTOMERSTATUS, String RECEIVEAMOUNT, String DUEAMOUNT, String CUSTOMERGUID, String TOTALGST) {
        this.PAYMENTID = PAYMENTID;
        this.STORE_GUID = STORE_GUID;
        this.CUSTOMERNAME = CUSTOMERNAME;
        this.CUSTOMERMOBILENO = CUSTOMERMOBILENO;
        this.GRANDTOTAL = GRANDTOTAL;
        this.CUSTOMERSTATUS = CUSTOMERSTATUS;
        this.RECEIVEAMOUNT = RECEIVEAMOUNT;
        this.DUEAMOUNT = DUEAMOUNT;
        this.CUSTOMERGUID = CUSTOMERGUID;
        this.TOTALGST = TOTALGST;
    }

    public String getPAYMENTID() {
        return PAYMENTID;
    }

    public void setPAYMENTID(String PAYMENTID) {
        this.PAYMENTID = PAYMENTID;
    }

    public String getSTORE_GUID() {
        return STORE_GUID;
    }

    public void setSTORE_GUID(String STORE_GUID) {
        this.STORE_GUID = STORE_GUID;
    }

    public String getCUSTOMERNAME() {
        return CUSTOMERNAME;
    }

    public void setCUSTOMERNAME(String CUSTOMERNAME) {
        this.CUSTOMERNAME = CUSTOMERNAME;
    }

    public String getCUSTOMERMOBILENO() {
        return CUSTOMERMOBILENO;
    }

    public void setCUSTOMERMOBILENO(String CUSTOMERMOBILENO) {
        this.CUSTOMERMOBILENO = CUSTOMERMOBILENO;
    }

    public String getGRANDTOTAL() {
        return GRANDTOTAL;
    }

    public void setGRANDTOTAL(String GRANDTOTAL) {
        this.GRANDTOTAL = GRANDTOTAL;
    }

    public String getCUSTOMERSTATUS() {
        return CUSTOMERSTATUS;
    }

    public void setCUSTOMERSTATUS(String CUSTOMERSTATUS) {
        this.CUSTOMERSTATUS = CUSTOMERSTATUS;
    }

    public String getRECEIVEAMOUNT() {
        return RECEIVEAMOUNT;
    }

    public void setRECEIVEAMOUNT(String RECEIVEAMOUNT) {
        this.RECEIVEAMOUNT = RECEIVEAMOUNT;
    }

    public String getDUEAMOUNT() {
        return DUEAMOUNT;
    }

    public void setDUEAMOUNT(String DUEAMOUNT) {
        this.DUEAMOUNT = DUEAMOUNT;
    }

    public String getCUSTOMERGUID() {
        return CUSTOMERGUID;
    }

    public void setCUSTOMERGUID(String CUSTOMERGUID) {
        this.CUSTOMERGUID = CUSTOMERGUID;
    }

    public String getTOTALGST() {
        return TOTALGST;
    }

    public void setTOTALGST(String TOTALGST) {
        this.TOTALGST = TOTALGST;
    }
}
