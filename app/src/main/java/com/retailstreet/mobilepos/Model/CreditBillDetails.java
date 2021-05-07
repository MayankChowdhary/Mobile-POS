package com.retailstreet.mobilepos.Model;
/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

public class CreditBillDetails {
    String STORE_GUID;
    String CUSTOMERMOBILENO;
    String CUSTOMERGUID;
    String BILLNO;
    String BILLDATE;
    String ITEM_GUID;
    String ITEM_NAME;
    String MRP;
    String SELLINGPRICE;
    String QTY;
    String TOTALVALUE;
    String TOTALGST;
    String CGST;
    String SGST;
    String IGST;
    String DISCOUNT_PERC;
    String DISCOUNT_VALUE;


    public CreditBillDetails() {
    }

    public CreditBillDetails(String STORE_GUID, String CUSTOMERMOBILENO, String CUSTOMERGUID, String BILLNO, String BILLDATE, String ITEM_GUID, String ITEM_NAME, String MRP, String SELLINGPRICE, String QTY, String TOTALVALUE, String TOTALGST, String CGST, String SGST, String IGST, String DISCOUNT_PERC, String DISCOUNT_VALUE) {
        this.STORE_GUID = STORE_GUID;
        this.CUSTOMERMOBILENO = CUSTOMERMOBILENO;
        this.CUSTOMERGUID = CUSTOMERGUID;
        this.BILLNO = BILLNO;
        this.BILLDATE = BILLDATE;
        this.ITEM_GUID = ITEM_GUID;
        this.ITEM_NAME = ITEM_NAME;
        this.MRP = MRP;
        this.SELLINGPRICE = SELLINGPRICE;
        this.QTY = QTY;
        this.TOTALVALUE = TOTALVALUE;
        this.TOTALGST = TOTALGST;
        this.CGST = CGST;
        this.SGST = SGST;
        this.IGST = IGST;
        this.DISCOUNT_PERC = DISCOUNT_PERC;
        this.DISCOUNT_VALUE = DISCOUNT_VALUE;
    }

    public String getSTORE_GUID() {
        return STORE_GUID;
    }

    public void setSTORE_GUID(String STORE_GUID) {
        this.STORE_GUID = STORE_GUID;
    }

    public String getCUSTOMERMOBILENO() {
        return CUSTOMERMOBILENO;
    }

    public void setCUSTOMERMOBILENO(String CUSTOMERMOBILENO) {
        this.CUSTOMERMOBILENO = CUSTOMERMOBILENO;
    }

    public String getCUSTOMERGUID() {
        return CUSTOMERGUID;
    }

    public void setCUSTOMERGUID(String CUSTOMERGUID) {
        this.CUSTOMERGUID = CUSTOMERGUID;
    }

    public String getBILLNO() {
        return BILLNO;
    }

    public void setBILLNO(String BILLNO) {
        this.BILLNO = BILLNO;
    }

    public String getBILLDATE() {
        return BILLDATE;
    }

    public void setBILLDATE(String BILLDATE) {
        this.BILLDATE = BILLDATE;
    }

    public String getITEM_GUID() {
        return ITEM_GUID;
    }

    public void setITEM_GUID(String ITEM_GUID) {
        this.ITEM_GUID = ITEM_GUID;
    }

    public String getITEM_NAME() {
        return ITEM_NAME;
    }

    public void setITEM_NAME(String ITEM_NAME) {
        this.ITEM_NAME = ITEM_NAME;
    }

    public String getMRP() {
        return MRP;
    }

    public void setMRP(String MRP) {
        this.MRP = MRP;
    }

    public String getSELLINGPRICE() {
        return SELLINGPRICE;
    }

    public void setSELLINGPRICE(String SELLINGPRICE) {
        this.SELLINGPRICE = SELLINGPRICE;
    }

    public String getQTY() {
        return QTY;
    }

    public void setQTY(String QTY) {
        this.QTY = QTY;
    }

    public String getTOTALVALUE() {
        return TOTALVALUE;
    }

    public void setTOTALVALUE(String TOTALVALUE) {
        this.TOTALVALUE = TOTALVALUE;
    }

    public String getTOTALGST() {
        return TOTALGST;
    }

    public void setTOTALGST(String TOTALGST) {
        this.TOTALGST = TOTALGST;
    }

    public String getCGST() {
        return CGST;
    }

    public void setCGST(String CGST) {
        this.CGST = CGST;
    }

    public String getSGST() {
        return SGST;
    }

    public void setSGST(String SGST) {
        this.SGST = SGST;
    }

    public String getIGST() {
        return IGST;
    }

    public void setIGST(String IGST) {
        this.IGST = IGST;
    }

    public String getDISCOUNT_PERC() {
        return DISCOUNT_PERC;
    }

    public void setDISCOUNT_PERC(String DISCOUNT_PERC) {
        this.DISCOUNT_PERC = DISCOUNT_PERC;
    }

    public String getDISCOUNT_VALUE() {
        return DISCOUNT_VALUE;
    }

    public void setDISCOUNT_VALUE(String DISCOUNT_VALUE) {
        this.DISCOUNT_VALUE = DISCOUNT_VALUE;
    }
}
