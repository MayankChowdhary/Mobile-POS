package com.retailstreet.mobilepos.Model;

import androidx.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */


@Keep
public class StockMaster {
    @SerializedName("STOCK_ID")
    @Expose
    private  String STOCK_ID;
    @SerializedName("STORE_GUID")
    @Expose
    private String STORE_GUID;
    @SerializedName("ITEM_GUID")
    @Expose
    private  String ITEM_GUID;
    @SerializedName("QTY")
    @Expose
    private String QTY;
    @SerializedName("SALE_UOMID")
    @Expose
    private  String SALE_UOMID;
    @SerializedName("UOM")
    @Expose
    private String UOM;
    @SerializedName("BATCH_NO")
    @Expose
    private String BATCH_NO;
    @SerializedName("BARCODE")
    @Expose
    private  String BARCODE;
    @SerializedName("P_PRICE")
    @Expose
    private String P_PRICE;
    @SerializedName("MRP")
    @Expose
    private String MRP;
    @SerializedName("S_PRICE")
    @Expose
    private String S_PRICE;
    @SerializedName("INTERNET_PRICE")
    @Expose
    private String INTERNET_PRICE;
    @SerializedName("MIN_QUANTITY")
    @Expose
    private String MIN_QUANTITY;
    @SerializedName("MAX_QUANTITY")
    @Expose
    private String MAX_QUANTITY;
    @SerializedName("WHOLE_SPRICE")
    @Expose
    private String WHOLE_SPRICE;
    @SerializedName("SPEC_PRICE")
    @Expose
    private  String SPEC_PRICE;
    @SerializedName("GENERIC_NAME")
    @Expose
    private String GENERIC_NAME;
    @SerializedName("EXTERNALPRODUCTID")
    @Expose
    private  String EXTERNALPRODUCTID;
    @SerializedName("GST")
    @Expose
    private String GST;
    @SerializedName("SGST")
    @Expose
    private  String SGST;
    @SerializedName("CGST")
    @Expose
    private String CGST;
    @SerializedName("IGST")
    @Expose
    private  String IGST;
    @SerializedName("CESS1")
    @Expose
    private String CESS1;
    @SerializedName("CESS2")
    @Expose
    private String CESS2;
    @SerializedName("EXP_DATE")
    @Expose
    private   String EXP_DATE;
    @SerializedName("PROD_NM")
    @Expose
    private  String PROD_NM;
    @SerializedName("ITEM_CODE")
    @Expose
    private String ITEM_CODE;
    @SerializedName("CREATED_BY")
    @Expose
    private String CREATED_BY;
    @SerializedName("UPDATEDBY")
    @Expose
    private String UPDATEDBY;
    @SerializedName("CREATED_ON")
    @Expose
    private String CREATED_ON;
    @SerializedName("UPDATEDON")
    @Expose
    private String UPDATEDON;
    @SerializedName("SALESDISCOUNTBYPERCENTAGE")
    @Expose
    private String SALESDISCOUNTBYPERCENTAGE;
    @SerializedName("SALESDISCOUNTBYAMOUNT")
    @Expose
    private String SALESDISCOUNTBYAMOUNT;
    @SerializedName("GRN_GUID")
    @Expose
    private String GRN_GUID;
    @SerializedName("GRNNO")
    @Expose
    private String GRNNO;
    @SerializedName("VENDOR_GUID")
    @Expose
    private String VENDOR_GUID;
    @SerializedName("VENDOR_NAME")
    @Expose
    private  String VENDOR_NAME;
    @SerializedName("GRNDETAILGUID")
    @Expose
    private  String GRNDETAILGUID;
    @SerializedName("ISSYNCED")
    @Expose
    private String ISSYNCED;
    String MASTER_TERMINAL_ID;

    public String getGRNDETAILGUID() {
        return GRNDETAILGUID;
    }

    public void setGRNDETAILGUID(String GRNDETAILGUID) {
        this.GRNDETAILGUID = GRNDETAILGUID;
    }

    public String getISSYNCED() {
        return ISSYNCED;
    }

    public void setISSYNCED(String ISSYNCED) {
        this.ISSYNCED = ISSYNCED;
    }



    public StockMaster() {
    }

    public StockMaster(String STOCK_ID, String STORE_GUID, String ITEM_GUID, String QTY, String SALE_UOMID, String UOM, String BATCH_NO, String BARCODE, String p_PRICE, String MRP, String s_PRICE, String INTERNET_PRICE, String MIN_QUANTITY, String MAX_QUANTITY, String WHOLE_SPRICE, String SPEC_PRICE, String GENERIC_NAME, String EXTERNALPRODUCTID, String GST, String SGST, String CGST, String IGST, String CESS1, String CESS2, String EXP_DATE, String PROD_NM, String ITEM_CODE, String CREATED_BY, String UPDATEDBY, String CREATED_ON, String UPDATEDON, String SALESDISCOUNTBYPERCENTAGE, String SALESDISCOUNTBYAMOUNT, String GRN_GUID, String GRNNO, String VENDOR_GUID, String VENDOR_NAME, String GRNDETAILGUID, String ISSYNCED, String MASTER_TERMINAL_ID) {
        this.STOCK_ID = STOCK_ID;
        this.STORE_GUID = STORE_GUID;
        this.ITEM_GUID = ITEM_GUID;
        this.QTY = QTY;
        this.SALE_UOMID = SALE_UOMID;
        this.UOM = UOM;
        this.BATCH_NO = BATCH_NO;
        this.BARCODE = BARCODE;
        P_PRICE = p_PRICE;
        this.MRP = MRP;
        S_PRICE = s_PRICE;
        this.INTERNET_PRICE = INTERNET_PRICE;
        this.MIN_QUANTITY = MIN_QUANTITY;
        this.MAX_QUANTITY = MAX_QUANTITY;
        this.WHOLE_SPRICE = WHOLE_SPRICE;
        this.SPEC_PRICE = SPEC_PRICE;
        this.GENERIC_NAME = GENERIC_NAME;
        this.EXTERNALPRODUCTID = EXTERNALPRODUCTID;
        this.GST = GST;
        this.SGST = SGST;
        this.CGST = CGST;
        this.IGST = IGST;
        this.CESS1 = CESS1;
        this.CESS2 = CESS2;
        this.EXP_DATE = EXP_DATE;
        this.PROD_NM = PROD_NM;
        this.ITEM_CODE = ITEM_CODE;
        this.CREATED_BY = CREATED_BY;
        this.UPDATEDBY = UPDATEDBY;
        this.CREATED_ON = CREATED_ON;
        this.UPDATEDON = UPDATEDON;
        this.SALESDISCOUNTBYPERCENTAGE = SALESDISCOUNTBYPERCENTAGE;
        this.SALESDISCOUNTBYAMOUNT = SALESDISCOUNTBYAMOUNT;
        this.GRN_GUID = GRN_GUID;
        this.GRNNO = GRNNO;
        this.VENDOR_GUID = VENDOR_GUID;
        this.VENDOR_NAME = VENDOR_NAME;
        this.GRNDETAILGUID = GRNDETAILGUID;
        this.ISSYNCED = ISSYNCED;
        this.MASTER_TERMINAL_ID = MASTER_TERMINAL_ID;
    }


    public String getMASTER_TERMINAL_ID() {
        return MASTER_TERMINAL_ID;
    }

    public void setMASTER_TERMINAL_ID(String MASTER_TERMINAL_ID) {
        this.MASTER_TERMINAL_ID = MASTER_TERMINAL_ID;
    }

    public String getSTOCK_ID() {
        return STOCK_ID;
    }

    public void setSTOCK_ID(String STOCK_ID) {
        this.STOCK_ID = STOCK_ID;
    }

    public String getSTORE_GUID() {
        return STORE_GUID;
    }

    public void setSTORE_GUID(String STORE_GUID) {
        this.STORE_GUID = STORE_GUID;
    }

    public String getITEM_GUID() {
        return ITEM_GUID;
    }

    public void setITEM_GUID(String ITEM_GUID) {
        this.ITEM_GUID = ITEM_GUID;
    }

    public String getQTY() {
        return QTY;
    }

    public void setQTY(String QTY) {
        this.QTY = QTY;
    }

    public String getSALE_UOMID() {
        return SALE_UOMID;
    }

    public void setSALE_UOMID(String SALE_UOMID) {
        this.SALE_UOMID = SALE_UOMID;
    }

    public String getUOM() {
        return UOM;
    }

    public void setUOM(String UOM) {
        this.UOM = UOM;
    }

    public String getBATCH_NO() {
        return BATCH_NO;
    }

    public void setBATCH_NO(String BATCH_NO) {
        this.BATCH_NO = BATCH_NO;
    }

    public String getBARCODE() {
        return BARCODE;
    }

    public void setBARCODE(String BARCODE) {
        this.BARCODE = BARCODE;
    }

    public String getP_PRICE() {
        return P_PRICE;
    }

    public void setP_PRICE(String p_PRICE) {
        P_PRICE = p_PRICE;
    }

    public String getMRP() {
        return MRP;
    }

    public void setMRP(String MRP) {
        this.MRP = MRP;
    }

    public String getS_PRICE() {
        return S_PRICE;
    }

    public void setS_PRICE(String s_PRICE) {
        S_PRICE = s_PRICE;
    }

    public String getINTERNET_PRICE() {
        return INTERNET_PRICE;
    }

    public void setINTERNET_PRICE(String INTERNET_PRICE) {
        this.INTERNET_PRICE = INTERNET_PRICE;
    }

    public String getMIN_QUANTITY() {
        return MIN_QUANTITY;
    }

    public void setMIN_QUANTITY(String MIN_QUANTITY) {
        this.MIN_QUANTITY = MIN_QUANTITY;
    }

    public String getMAX_QUANTITY() {
        return MAX_QUANTITY;
    }

    public void setMAX_QUANTITY(String MAX_QUANTITY) {
        this.MAX_QUANTITY = MAX_QUANTITY;
    }

    public String getWHOLE_SPRICE() {
        return WHOLE_SPRICE;
    }

    public void setWHOLE_SPRICE(String WHOLE_SPRICE) {
        this.WHOLE_SPRICE = WHOLE_SPRICE;
    }

    public String getSPEC_PRICE() {
        return SPEC_PRICE;
    }

    public void setSPEC_PRICE(String SPEC_PRICE) {
        this.SPEC_PRICE = SPEC_PRICE;
    }

    public String getGENERIC_NAME() {
        return GENERIC_NAME;
    }

    public void setGENERIC_NAME(String GENERIC_NAME) {
        this.GENERIC_NAME = GENERIC_NAME;
    }

    public String getEXTERNALPRODUCTID() {
        return EXTERNALPRODUCTID;
    }

    public void setEXTERNALPRODUCTID(String EXTERNALPRODUCTID) {
        this.EXTERNALPRODUCTID = EXTERNALPRODUCTID;
    }

    public String getGST() {
        return GST;
    }

    public void setGST(String GST) {
        this.GST = GST;
    }

    public String getSGST() {
        return SGST;
    }

    public void setSGST(String SGST) {
        this.SGST = SGST;
    }

    public String getCGST() {
        return CGST;
    }

    public void setCGST(String CGST) {
        this.CGST = CGST;
    }

    public String getIGST() {
        return IGST;
    }

    public void setIGST(String IGST) {
        this.IGST = IGST;
    }

    public String getCESS1() {
        return CESS1;
    }

    public void setCESS1(String CESS1) {
        this.CESS1 = CESS1;
    }

    public String getCESS2() {
        return CESS2;
    }

    public void setCESS2(String CESS2) {
        this.CESS2 = CESS2;
    }

    public String getEXP_DATE() {
        return EXP_DATE;
    }

    public void setEXP_DATE(String EXP_DATE) {
        this.EXP_DATE = EXP_DATE;
    }

    public String getPROD_NM() {
        return PROD_NM;
    }

    public void setPROD_NM(String PROD_NM) {
        this.PROD_NM = PROD_NM;
    }

    public String getITEM_CODE() {
        return ITEM_CODE;
    }

    public void setITEM_CODE(String ITEM_CODE) {
        this.ITEM_CODE = ITEM_CODE;
    }

    public String getCREATED_BY() {
        return CREATED_BY;
    }

    public void setCREATED_BY(String CREATED_BY) {
        this.CREATED_BY = CREATED_BY;
    }

    public String getUPDATEDBY() {
        return UPDATEDBY;
    }

    public void setUPDATEDBY(String UPDATEDBY) {
        this.UPDATEDBY = UPDATEDBY;
    }

    public String getCREATED_ON() {
        return CREATED_ON;
    }

    public void setCREATED_ON(String CREATED_ON) {
        this.CREATED_ON = CREATED_ON;
    }

    public String getUPDATEDON() {
        return UPDATEDON;
    }

    public void setUPDATEDON(String UPDATEDON) {
        this.UPDATEDON = UPDATEDON;
    }

    public String getSALESDISCOUNTBYPERCENTAGE() {
        return SALESDISCOUNTBYPERCENTAGE;
    }

    public void setSALESDISCOUNTBYPERCENTAGE(String SALESDISCOUNTBYPERCENTAGE) {
        this.SALESDISCOUNTBYPERCENTAGE = SALESDISCOUNTBYPERCENTAGE;
    }

    public String getSALESDISCOUNTBYAMOUNT() {
        return SALESDISCOUNTBYAMOUNT;
    }

    public void setSALESDISCOUNTBYAMOUNT(String SALESDISCOUNTBYAMOUNT) {
        this.SALESDISCOUNTBYAMOUNT = SALESDISCOUNTBYAMOUNT;
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

    public String getVENDOR_GUID() {
        return VENDOR_GUID;
    }

    public void setVENDOR_GUID(String VENDOR_GUID) {
        this.VENDOR_GUID = VENDOR_GUID;
    }

    public String getVENDOR_NAME() {
        return VENDOR_NAME;
    }

    public void setVENDOR_NAME(String VENDOR_NAME) {
        this.VENDOR_NAME = VENDOR_NAME;
    }

    @Override
    public String toString() {
        return STOCK_ID;
    }
}

