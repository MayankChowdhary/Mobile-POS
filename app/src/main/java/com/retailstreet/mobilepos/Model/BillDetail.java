package com.retailstreet.mobilepos.Model;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

@Keep
public class BillDetail {

    @SerializedName("BILLDETAILID")
    @Expose
    private String BILLDETAILID;

    @SerializedName("BILLMASTERID")
    @Expose
    private String BILLMASTERID;

    @SerializedName("BILLNO")
    @Expose
    private String BILLNO;

    @SerializedName("CATEGORY_GUID")
    @Expose
    private String CATEGORY_GUID;

    @SerializedName("SUBCAT_GUID")
    @Expose
    private String SUBCAT_GUID;

    @SerializedName("ITEM_GUID")
    @Expose
    private String ITEM_GUID;

    @SerializedName("ITEM_CODE")
    @Expose
    private String ITEM_CODE;

    @SerializedName("QTY")
    @Expose
    private String QTY;

    @SerializedName("UOM_GUID")
    @Expose
    private String UOM_GUID;

    @SerializedName("BATCHNO")
    @Expose
    private String BATCHNO;

    @SerializedName("COST_PRICE")
    @Expose
    private  String COST_PRICE;

    @SerializedName("NETVALUE")
    @Expose
    private String NETVALUE;
    @SerializedName("DISCOUNT_PERC")
    @Expose
    private String DISCOUNT_PERC;

    @SerializedName("DISCOUNT_VALUE")
    @Expose
    private  String DISCOUNT_VALUE;

    @SerializedName("TOTALVALUE")
    @Expose
    private String TOTALVALUE;

    @SerializedName("MRP")
    @Expose
    private  String MRP;

    @SerializedName("BILLDETAILSTATUS")
    @Expose
    private  String BILLDETAILSTATUS;

    @SerializedName("HSN")
    @Expose
    private String HSN;

    @SerializedName("CGSTPERCENTAGE")
    @Expose
    private String CGSTPERCENTAGE;

    @SerializedName("SGSTPERCENTAGE")
    @Expose
    private String SGSTPERCENTAGE;

    @SerializedName("IGSTPERCENTAGE")
    @Expose
    private String IGSTPERCENTAGE;

    @SerializedName("ADDITIONALPERCENTAGE")
    @Expose
    private String ADDITIONALPERCENTAGE;

    @SerializedName("CGST")
    @Expose
    private String CGST;

    @SerializedName("SGST")
    @Expose
    private String SGST;

    @SerializedName("IGST")
    @Expose
    private String IGST;

    @SerializedName("ADDITIONALCESS")
    @Expose
    private String ADDITIONALCESS;

    @SerializedName("TOTALGST")
    @Expose
    private String TOTALGST;

    public BillDetail(){

    }
    public BillDetail(String BILLDETAILID, String BILLMASTERID, String BILLNO, String CATEGORY_GUID, String SUBCAT_GUID, String ITEM_GUID, String ITEM_CODE, String QTY, String UOM_GUID, String BATCHNO, String COST_PRICE, String NETVALUE, String DISCOUNT_PERC, String DISCOUNT_VALUE, String TOTALVALUE, String MRP, String BILLDETAILSTATUS, String HSN, String CGSTPERCENTAGE, String SGSTPERCENTAGE, String IGSTPERCENTAGE, String ADDITIONALPERCENTAGE, String CGST, String SGST, String IGST, String ADDITIONALCESS, String TOTALGST){
        super();
        this.BILLDETAILID = BILLDETAILID;
        this.BILLMASTERID  = BILLMASTERID;
        this.BILLNO = BILLNO;
        this.CATEGORY_GUID = CATEGORY_GUID;
        this.SUBCAT_GUID = SUBCAT_GUID;
        this.ITEM_GUID = ITEM_GUID;
        this.ITEM_CODE = ITEM_CODE;
        this.QTY = QTY;
        this.UOM_GUID = UOM_GUID;
        this.BATCHNO = BATCHNO;
        this.COST_PRICE  = COST_PRICE;
        this.NETVALUE  = NETVALUE;
        this.DISCOUNT_PERC = DISCOUNT_PERC;
        this.DISCOUNT_VALUE = DISCOUNT_VALUE;
        this.TOTALVALUE = TOTALVALUE;
        this.MRP  = MRP;
        this.BILLDETAILSTATUS  = BILLDETAILSTATUS;
        this.HSN  = HSN;
        this.CGSTPERCENTAGE  = CGSTPERCENTAGE;
        this.SGSTPERCENTAGE = SGSTPERCENTAGE;
        this.IGSTPERCENTAGE  = IGSTPERCENTAGE;
        this.ADDITIONALPERCENTAGE  = ADDITIONALPERCENTAGE;
        this.CGST  = CGST;
        this.SGST  = SGST;
        this.IGST  = IGST;
        this.ADDITIONALCESS  = ADDITIONALCESS;
        this.TOTALGST  = TOTALGST;
    }


    public String getBILLDETAILID() {
        return BILLDETAILID;
    }

    public void setBILLDETAILID(String BILLDETAILID) {
        this.BILLDETAILID = BILLDETAILID;
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

    public String getCATEGORY_GUID() {
        return CATEGORY_GUID;
    }

    public void setCATEGORY_GUID(String CATEGORY_GUID) {
        this.CATEGORY_GUID = CATEGORY_GUID;
    }

    public String getSUBCAT_GUID() {
        return SUBCAT_GUID;
    }

    public void setSUBCAT_GUID(String SUBCAT_GUID) {
        this.SUBCAT_GUID = SUBCAT_GUID;
    }

    public String getITEM_GUID() {
        return ITEM_GUID;
    }

    public void setITEM_GUID(String ITEM_GUID) {
        this.ITEM_GUID = ITEM_GUID;
    }

    public String getITEM_CODE() {
        return ITEM_CODE;
    }

    public void setITEM_CODE(String ITEM_CODE) {
        this.ITEM_CODE = ITEM_CODE;
    }

    public String getQTY() {
        return QTY;
    }

    public void setQTY(String QTY) {
        this.QTY = QTY;
    }

    public String getUOM_GUID() {
        return UOM_GUID;
    }

    public void setUOM_GUID(String UOM_GUID) {
        this.UOM_GUID = UOM_GUID;
    }

    public String getBATCHNO() {
        return BATCHNO;
    }

    public void setBATCHNO(String BATCHNO) {
        this.BATCHNO = BATCHNO;
    }

    public String getCOST_PRICE() {
        return COST_PRICE;
    }

    public void setCOST_PRICE(String COST_PRICE) {
        this.COST_PRICE = COST_PRICE;
    }

    public String getNETVALUE() {
        return NETVALUE;
    }

    public void setNETVALUE(String NETVALUE) {
        this.NETVALUE = NETVALUE;
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

    public String getTOTALVALUE() {
        return TOTALVALUE;
    }

    public void setTOTALVALUE(String TOTALVALUE) {
        this.TOTALVALUE = TOTALVALUE;
    }

    public String getMRP() {
        return MRP;
    }

    public void setMRP(String MRP) {
        this.MRP = MRP;
    }

    public String getBILLDETAILSTATUS() {
        return BILLDETAILSTATUS;
    }

    public void setBILLDETAILSTATUS(String BILLDETAILSTATUS) {
        this.BILLDETAILSTATUS = BILLDETAILSTATUS;
    }

    public String getHSN() {
        return HSN;
    }

    public void setHSN(String HSN) {
        this.HSN = HSN;
    }

    public String getCGSTPERCENTAGE() {
        return CGSTPERCENTAGE;
    }

    public void setCGSTPERCENTAGE(String CGSTPERCENTAGE) {
        this.CGSTPERCENTAGE = CGSTPERCENTAGE;
    }

    public String getSGSTPERCENTAGE() {
        return SGSTPERCENTAGE;
    }

    public void setSGSTPERCENTAGE(String SGSTPERCENTAGE) {
        this.SGSTPERCENTAGE = SGSTPERCENTAGE;
    }

    public String getIGSTPERCENTAGE() {
        return IGSTPERCENTAGE;
    }

    public void setIGSTPERCENTAGE(String IGSTPERCENTAGE) {
        this.IGSTPERCENTAGE = IGSTPERCENTAGE;
    }

    public String getADDITIONALPERCENTAGE() {
        return ADDITIONALPERCENTAGE;
    }

    public void setADDITIONALPERCENTAGE(String ADDITIONALPERCENTAGE) {
        this.ADDITIONALPERCENTAGE = ADDITIONALPERCENTAGE;
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

    public String getADDITIONALCESS() {
        return ADDITIONALCESS;
    }

    public void setADDITIONALCESS(String ADDITIONALCESS) {
        this.ADDITIONALCESS = ADDITIONALCESS;
    }

    public String getTOTALGST() {
        return TOTALGST;
    }

    public void setTOTALGST(String TOTALGST) {
        this.TOTALGST = TOTALGST;
    }

    @NonNull
    @Override
    public String toString() {
        return ( BILLDETAILID + BILLMASTERID + BILLNO + CATEGORY_GUID + SUBCAT_GUID + ITEM_GUID + ITEM_CODE + QTY + UOM_GUID + BATCHNO + COST_PRICE + NETVALUE + DISCOUNT_PERC + DISCOUNT_VALUE + TOTALVALUE + MRP + BILLDETAILSTATUS + HSN + CGSTPERCENTAGE + SGSTPERCENTAGE + IGSTPERCENTAGE + ADDITIONALPERCENTAGE + CGST + SGST + IGST + ADDITIONALCESS + TOTALGST);
    }
}
