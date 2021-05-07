package com.retailstreet.mobilepos.Model;

import androidx.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

@Keep
public class VendorMaster {

    @SerializedName("DSTR_ID")
    @Expose
      private  String DSTR_ID;
    @SerializedName("VENDOR_GUID")
    @Expose
      private  String VENDOR_GUID;
    @SerializedName("MASTERORGID")
    @Expose
      private  String MASTERORGID;
    @SerializedName("STORE_ID")
    @Expose
      private  String STORE_ID;
    @SerializedName("VENDOR_CATEGORY")
    @Expose
      private  String VENDOR_CATEGORY;
    @SerializedName("DSTR_NM")
    @Expose
      private  String DSTR_NM;
    @SerializedName("VENDOR_STREET")
    @Expose
      private  String VENDOR_STREET;
    @SerializedName("ADD_1")
    @Expose
      private  String ADD_1;
    @SerializedName("CITY")
    @Expose
      private  String CITY;
    @SerializedName("DSTR_CNTCT_NM")
    @Expose
      private  String DSTR_CNTCT_NM;
    @SerializedName("MOBILE")
    @Expose
      private  String MOBILE;
    @SerializedName("EMAIL")
    @Expose
      private  String EMAIL;
    @SerializedName("GST")
    @Expose
      private  String GST;
    @SerializedName("PAN")
    @Expose
      private  String PAN;
    @SerializedName("ZIP")
    @Expose
      private  String ZIP;
    @SerializedName("TELE")
    @Expose
      private  String TELE;
    @SerializedName("VENDOR_STATUS")
    @Expose
      private  String VENDOR_STATUS;
    @SerializedName("POS_USER")
    @Expose
      private  String POS_USER;
    @SerializedName("CREATEDON")
    @Expose
      private  String CREATEDON;
    @SerializedName("MASTERCOUNTRYID")
    @Expose
      private  String MASTERCOUNTRYID;
    @SerializedName("DSTR_INV")
    @Expose
      private  String DSTR_INV;
    @SerializedName("VENDORSTATE")
    @Expose
      private  String VENDORSTATE;
    @SerializedName("PAYMENTTERMS")
    @Expose
      private  String PAYMENTTERMS;
    @SerializedName("ISSYNCED")
    @Expose
      private  String ISSYNCED;


    public VendorMaster() {
    }

    public VendorMaster(String DSTR_ID, String VENDOR_GUID, String MASTERORGID, String STORE_ID, String VENDOR_CATEGORY, String DSTR_NM, String VENDOR_STREET, String ADD_1, String CITY, String DSTR_CNTCT_NM, String MOBILE, String EMAIL, String GST, String PAN, String ZIP, String TELE, String VENDOR_STATUS, String POS_USER, String CREATEDON, String MASTERCOUNTRYID, String DSTR_INV, String VENDORSTATE, String PAYMENTTERMS, String ISSYNCED) {
        this.DSTR_ID = DSTR_ID;
        this.VENDOR_GUID = VENDOR_GUID;
        this.MASTERORGID = MASTERORGID;
        this.STORE_ID = STORE_ID;
        this.VENDOR_CATEGORY = VENDOR_CATEGORY;
        this.DSTR_NM = DSTR_NM;
        this.VENDOR_STREET = VENDOR_STREET;
        this.ADD_1 = ADD_1;
        this.CITY = CITY;
        this.DSTR_CNTCT_NM = DSTR_CNTCT_NM;
        this.MOBILE = MOBILE;
        this.EMAIL = EMAIL;
        this.GST = GST;
        this.PAN = PAN;
        this.ZIP = ZIP;
        this.TELE = TELE;
        this.VENDOR_STATUS = VENDOR_STATUS;
        this.POS_USER = POS_USER;
        this.CREATEDON = CREATEDON;
        this.MASTERCOUNTRYID = MASTERCOUNTRYID;
        this.DSTR_INV = DSTR_INV;
        this.VENDORSTATE = VENDORSTATE;
        this.PAYMENTTERMS = PAYMENTTERMS;
        this.ISSYNCED = ISSYNCED;
    }

    public String getDSTR_ID() {
        return DSTR_ID;
    }

    public void setDSTR_ID(String DSTR_ID) {
        this.DSTR_ID = DSTR_ID;
    }

    public String getVENDOR_GUID() {
        return VENDOR_GUID;
    }

    public void setVENDOR_GUID(String VENDOR_GUID) {
        this.VENDOR_GUID = VENDOR_GUID;
    }

    public String getMASTERORGID() {
        return MASTERORGID;
    }

    public void setMASTERORGID(String MASTERORGID) {
        this.MASTERORGID = MASTERORGID;
    }

    public String getSTORE_ID() {
        return STORE_ID;
    }

    public void setSTORE_ID(String STORE_ID) {
        this.STORE_ID = STORE_ID;
    }

    public String getVENDOR_CATEGORY() {
        return VENDOR_CATEGORY;
    }

    public void setVENDOR_CATEGORY(String VENDOR_CATEGORY) {
        this.VENDOR_CATEGORY = VENDOR_CATEGORY;
    }

    public String getDSTR_NM() {
        return DSTR_NM;
    }

    public void setDSTR_NM(String DSTR_NM) {
        this.DSTR_NM = DSTR_NM;
    }

    public String getVENDOR_STREET() {
        return VENDOR_STREET;
    }

    public void setVENDOR_STREET(String VENDOR_STREET) {
        this.VENDOR_STREET = VENDOR_STREET;
    }

    public String getADD_1() {
        return ADD_1;
    }

    public void setADD_1(String ADD_1) {
        this.ADD_1 = ADD_1;
    }

    public String getCITY() {
        return CITY;
    }

    public void setCITY(String CITY) {
        this.CITY = CITY;
    }

    public String getDSTR_CNTCT_NM() {
        return DSTR_CNTCT_NM;
    }

    public void setDSTR_CNTCT_NM(String DSTR_CNTCT_NM) {
        this.DSTR_CNTCT_NM = DSTR_CNTCT_NM;
    }

    public String getMOBILE() {
        return MOBILE;
    }

    public void setMOBILE(String MOBILE) {
        this.MOBILE = MOBILE;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getGST() {
        return GST;
    }

    public void setGST(String GST) {
        this.GST = GST;
    }

    public String getPAN() {
        return PAN;
    }

    public void setPAN(String PAN) {
        this.PAN = PAN;
    }

    public String getZIP() {
        return ZIP;
    }

    public void setZIP(String ZIP) {
        this.ZIP = ZIP;
    }

    public String getTELE() {
        return TELE;
    }

    public void setTELE(String TELE) {
        this.TELE = TELE;
    }

    public String getVENDOR_STATUS() {
        return VENDOR_STATUS;
    }

    public void setVENDOR_STATUS(String VENDOR_STATUS) {
        this.VENDOR_STATUS = VENDOR_STATUS;
    }

    public String getPOS_USER() {
        return POS_USER;
    }

    public void setPOS_USER(String POS_USER) {
        this.POS_USER = POS_USER;
    }

    public String getCREATEDON() {
        return CREATEDON;
    }

    public void setCREATEDON(String CREATEDON) {
        this.CREATEDON = CREATEDON;
    }

    public String getMASTERCOUNTRYID() {
        return MASTERCOUNTRYID;
    }

    public void setMASTERCOUNTRYID(String MASTERCOUNTRYID) {
        this.MASTERCOUNTRYID = MASTERCOUNTRYID;
    }

    public String getDSTR_INV() {
        return DSTR_INV;
    }

    public void setDSTR_INV(String DSTR_INV) {
        this.DSTR_INV = DSTR_INV;
    }

    public String getVENDORSTATE() {
        return VENDORSTATE;
    }

    public void setVENDORSTATE(String VENDORSTATE) {
        this.VENDORSTATE = VENDORSTATE;
    }

    public String getPAYMENTTERMS() {
        return PAYMENTTERMS;
    }

    public void setPAYMENTTERMS(String PAYMENTTERMS) {
        this.PAYMENTTERMS = PAYMENTTERMS;
    }

    public String getISSYNCED() {
        return ISSYNCED;
    }

    public void setISSYNCED(String ISSYNCED) {
        this.ISSYNCED = ISSYNCED;
    }
}
