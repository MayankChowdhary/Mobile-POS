package com.retailstreet.mobilepos.Model;

import androidx.annotation.NonNull;

/**
 * Created by rspl-rahul on 9/10/20.
 */
public class ProductMaster {
    String ACTIVE;
    String BARCODE;
    String CATEGORY;
    String CATEGORY_GUID;
    String CESS1;
    String CESS2;
    String CGST;
    String EXTERNALPRODUCTID;
    String GENERIC_NAME;
    String GST;
    String HSN;
    String IGST;
    String ITEM_CODE;
    String ITEM_GUID;
    String Item_Type;
    String MASTERBRAND;
    String MASTERCATEGORY_id;
    String POS_USER;
    String PRINT_NAME;
    String PROD_ID;
    String PROD_NM;
    String PRODUCTRELEVANCE;
    String SGST;
    String STORE_ID;
    String STORE_NUMBER;
    String SUB_CATEGORYGUID;
    String SUBCATEGORY_DESCRIPTION;
    String SUBCATEGORY_ID;
    String UOM;
    String UOM_GUID;
    String UoMID;
    String ISSYNCED;

    public ProductMaster() {
    }

    public ProductMaster(String ACTIVE, String BARCODE, String CATEGORY, String CATEGORY_GUID, String CESS1, String CESS2, String CGST, String EXTERNALPRODUCTID, String GENERIC_NAME, String GST, String HSN, String IGST, String ITEM_CODE, String ITEM_GUID, String item_Type, String MASTERBRAND, String MASTERCATEGORY_id, String POS_USER, String PRINT_NAME, String PROD_ID, String PROD_NM, String PRODUCTRELEVANCE, String SGST, String STORE_ID, String STORE_NUMBER, String SUB_CATEGORYGUID, String SUBCATEGORY_DESCRIPTION, String SUBCATEGORY_ID, String UOM, String UOM_GUID, String uoMID) {
        this.ACTIVE = ACTIVE;
        this.BARCODE = BARCODE;
        this.CATEGORY = CATEGORY;
        this.CATEGORY_GUID = CATEGORY_GUID;
        this.CESS1 = CESS1;
        this.CESS2 = CESS2;
        this.CGST = CGST;
        this.EXTERNALPRODUCTID = EXTERNALPRODUCTID;
        this.GENERIC_NAME = GENERIC_NAME;
        this.GST = GST;
        this.HSN = HSN;
        this.IGST = IGST;
        this.ITEM_CODE = ITEM_CODE;
        this.ITEM_GUID = ITEM_GUID;
        Item_Type = item_Type;
        this.MASTERBRAND = MASTERBRAND;
        this.MASTERCATEGORY_id = MASTERCATEGORY_id;
        this.POS_USER = POS_USER;
        this.PRINT_NAME = PRINT_NAME;
        this.PROD_ID = PROD_ID;
        this.PROD_NM = PROD_NM;
        this.PRODUCTRELEVANCE = PRODUCTRELEVANCE;
        this.SGST = SGST;
        this.STORE_ID = STORE_ID;
        this.STORE_NUMBER = STORE_NUMBER;
        this.SUB_CATEGORYGUID = SUB_CATEGORYGUID;
        this.SUBCATEGORY_DESCRIPTION = SUBCATEGORY_DESCRIPTION;
        this.SUBCATEGORY_ID = SUBCATEGORY_ID;
        this.UOM = UOM;
        this.UOM_GUID = UOM_GUID;
        UoMID = uoMID;
    }

    public String getACTIVE() {
        return ACTIVE;
    }

    public void setACTIVE(String ACTIVE) {
        this.ACTIVE = ACTIVE;
    }

    public String getBARCODE() {
        return BARCODE;
    }

    public void setBARCODE(String BARCODE) {
        this.BARCODE = BARCODE;
    }

    public String getCATEGORY() {
        return CATEGORY;
    }

    public void setCATEGORY(String CATEGORY) {
        this.CATEGORY = CATEGORY;
    }

    public String getCATEGORY_GUID() {
        return CATEGORY_GUID;
    }

    public void setCATEGORY_GUID(String CATEGORY_GUID) {
        this.CATEGORY_GUID = CATEGORY_GUID;
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

    public String getCGST() {
        return CGST;
    }

    public void setCGST(String CGST) {
        this.CGST = CGST;
    }

    public String getEXTERNALPRODUCTID() {
        return EXTERNALPRODUCTID;
    }

    public void setEXTERNALPRODUCTID(String EXTERNALPRODUCTID) {
        this.EXTERNALPRODUCTID = EXTERNALPRODUCTID;
    }

    public String getGENERIC_NAME() {
        return GENERIC_NAME;
    }

    public void setGENERIC_NAME(String GENERIC_NAME) {
        this.GENERIC_NAME = GENERIC_NAME;
    }

    public String getGST() {
        return GST;
    }

    public void setGST(String GST) {
        this.GST = GST;
    }

    public String getHSN() {
        return HSN;
    }

    public void setHSN(String HSN) {
        this.HSN = HSN;
    }

    public String getIGST() {
        return IGST;
    }

    public void setIGST(String IGST) {
        this.IGST = IGST;
    }

    public String getITEM_CODE() {
        return ITEM_CODE;
    }

    public void setITEM_CODE(String ITEM_CODE) {
        this.ITEM_CODE = ITEM_CODE;
    }

    public String getITEM_GUID() {
        return ITEM_GUID;
    }

    public void setITEM_GUID(String ITEM_GUID) {
        this.ITEM_GUID = ITEM_GUID;
    }

    public String getItem_Type() {
        return Item_Type;
    }

    public void setItem_Type(String item_Type) {
        Item_Type = item_Type;
    }

    public String getMASTERBRAND() {
        return MASTERBRAND;
    }

    public void setMASTERBRAND(String MASTERBRAND) {
        this.MASTERBRAND = MASTERBRAND;
    }

    public String getMASTERCATEGORY_id() {
        return MASTERCATEGORY_id;
    }

    public void setMASTERCATEGORY_id(String MASTERCATEGORY_id) {
        this.MASTERCATEGORY_id = MASTERCATEGORY_id;
    }

    public String getPOS_USER() {
        return POS_USER;
    }

    public void setPOS_USER(String POS_USER) {
        this.POS_USER = POS_USER;
    }

    public String getPRINT_NAME() {
        return PRINT_NAME;
    }

    public void setPRINT_NAME(String PRINT_NAME) {
        this.PRINT_NAME = PRINT_NAME;
    }

    public String getPROD_ID() {
        return PROD_ID;
    }

    public void setPROD_ID(String PROD_ID) {
        this.PROD_ID = PROD_ID;
    }

    public String getPROD_NM() {
        return PROD_NM;
    }

    public void setPROD_NM(String PROD_NM) {
        this.PROD_NM = PROD_NM;
    }

    public String getPRODUCTRELEVANCE() {
        return PRODUCTRELEVANCE;
    }

    public void setPRODUCTRELEVANCE(String PRODUCTRELEVANCE) {
        this.PRODUCTRELEVANCE = PRODUCTRELEVANCE;
    }

    public String getSGST() {
        return SGST;
    }

    public void setSGST(String SGST) {
        this.SGST = SGST;
    }

    public String getSTORE_ID() {
        return STORE_ID;
    }

    public void setSTORE_ID(String STORE_ID) {
        this.STORE_ID = STORE_ID;
    }

    public String getSTORE_NUMBER() {
        return STORE_NUMBER;
    }

    public void setSTORE_NUMBER(String STORE_NUMBER) {
        this.STORE_NUMBER = STORE_NUMBER;
    }

    public String getSUB_CATEGORYGUID() {
        return SUB_CATEGORYGUID;
    }

    public void setSUB_CATEGORYGUID(String SUB_CATEGORYGUID) {
        this.SUB_CATEGORYGUID = SUB_CATEGORYGUID;
    }

    public String getSUBCATEGORY_DESCRIPTION() {
        return SUBCATEGORY_DESCRIPTION;
    }

    public void setSUBCATEGORY_DESCRIPTION(String SUBCATEGORY_DESCRIPTION) {
        this.SUBCATEGORY_DESCRIPTION = SUBCATEGORY_DESCRIPTION;
    }

    public String getSUBCATEGORY_ID() {
        return SUBCATEGORY_ID;
    }

    public void setSUBCATEGORY_ID(String SUBCATEGORY_ID) {
        this.SUBCATEGORY_ID = SUBCATEGORY_ID;
    }

    public String getUOM() {
        return UOM;
    }

    public void setUOM(String UOM) {
        this.UOM = UOM;
    }

    public String getUOM_GUID() {
        return UOM_GUID;
    }

    public void setUOM_GUID(String UOM_GUID) {
        this.UOM_GUID = UOM_GUID;
    }

    public String getUoMID() {
        return UoMID;
    }

    public void setUoMID(String uoMID) {
        UoMID = uoMID;
    }

    public String getISSYNCED() {
        return ISSYNCED;
    }

    public void setISSYNCED(String ISSYNCED) {
        this.ISSYNCED = ISSYNCED;
    }

    @NonNull
    @Override
    public String toString() {
        return ITEM_GUID;
    }
}
