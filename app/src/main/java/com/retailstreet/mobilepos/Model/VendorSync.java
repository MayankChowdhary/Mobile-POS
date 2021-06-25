package com.retailstreet.mobilepos.Model;

import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by Mayank Choudhary on 11-06-2021.
 * mayankchoudhary00@gmail.com
 */

public class VendorSync implements Serializable {
    public String VENDORID ;
    public String VENDOR_GUID ;
    public String ORG_GUID ;
    public String STORE_GUID ;
    public String VENDOR_CATEGORY ;
    public String VENDOR_NAME ;
    public String VENDOR_STREET ;
    public String VENDOR_ADDRESS ;
    public String CITY ;
    public String CONTACT_PERSON ;
    public String MOBILE ;
    public String EMAIL ;
    public String GST ;
    public String PAN ;
    public String CREATEDBYUSERID ;
    public String ISACTIVE ;
    public String PINCODE;
    public String TELEPHONENO;
    public String MASTERCOUNTRYID;
    public String ISINVENTORY;
    public String VENDORSTATE;
    public String PAYMENTTERMS;
    String REGULAR_VENDOR;
    String MASTER_TERMINAL_ID;

    transient
    public String ISSYNCED;

    public VendorSync() {

    }

    public VendorSync(String VENDORID, String VENDOR_GUID, String ORG_GUID, String STORE_GUID, String VENDOR_CATEGORY, String VENDOR_NAME, String VENDOR_STREET, String VENDOR_ADDRESS, String CITY, String CONTACT_PERSON, String MOBILE, String EMAIL, String GST, String PAN, String CREATEDBYUSERID, String ISACTIVE, String PINCODE, String TELEPHONENO, String MASTERCOUNTRYID, String ISINVENTORY, String VENDORSTATE, String PAYMENTTERMS, String REGULAR_VENDOR, String MASTER_TERMINAL_ID, String ISSYNCED) {
        this.VENDORID = VENDORID;
        this.VENDOR_GUID = VENDOR_GUID;
        this.ORG_GUID = ORG_GUID;
        this.STORE_GUID = STORE_GUID;
        this.VENDOR_CATEGORY = VENDOR_CATEGORY;
        this.VENDOR_NAME = VENDOR_NAME;
        this.VENDOR_STREET = VENDOR_STREET;
        this.VENDOR_ADDRESS = VENDOR_ADDRESS;
        this.CITY = CITY;
        this.CONTACT_PERSON = CONTACT_PERSON;
        this.MOBILE = MOBILE;
        this.EMAIL = EMAIL;
        this.GST = GST;
        this.PAN = PAN;
        this.CREATEDBYUSERID = CREATEDBYUSERID;
        this.ISACTIVE = ISACTIVE;
        this.PINCODE = PINCODE;
        this.TELEPHONENO = TELEPHONENO;
        this.MASTERCOUNTRYID = MASTERCOUNTRYID;
        this.ISINVENTORY = ISINVENTORY;
        this.VENDORSTATE = VENDORSTATE;
        this.PAYMENTTERMS = PAYMENTTERMS;
        this.REGULAR_VENDOR = REGULAR_VENDOR;
        this.MASTER_TERMINAL_ID = MASTER_TERMINAL_ID;
        this.ISSYNCED = ISSYNCED;
    }


    public String getREGULAR_VENDOR() {
        return REGULAR_VENDOR;
    }

    public void setREGULAR_VENDOR(String REGULAR_VENDOR) {
        this.REGULAR_VENDOR = REGULAR_VENDOR;
    }

    public String getMASTER_TERMINAL_ID() {
        return MASTER_TERMINAL_ID;
    }

    public void setMASTER_TERMINAL_ID(String MASTER_TERMINAL_ID) {
        this.MASTER_TERMINAL_ID = MASTER_TERMINAL_ID;
    }

    public String getVENDORID() {
        return VENDORID;
    }

    public void setVENDORID(String VENDORID) {
        this.VENDORID = VENDORID;
    }

    public String getVENDOR_GUID() {
        return VENDOR_GUID;
    }

    public void setVENDOR_GUID(String VENDOR_GUID) {
        this.VENDOR_GUID = VENDOR_GUID;
    }

    public String getORG_GUID() {
        return ORG_GUID;
    }

    public void setORG_GUID(String ORG_GUID) {
        this.ORG_GUID = ORG_GUID;
    }

    public String getSTORE_GUID() {
        return STORE_GUID;
    }

    public void setSTORE_GUID(String STORE_GUID) {
        this.STORE_GUID = STORE_GUID;
    }

    public String getVENDOR_CATEGORY() {
        return VENDOR_CATEGORY;
    }

    public void setVENDOR_CATEGORY(String VENDOR_CATEGORY) {
        this.VENDOR_CATEGORY = VENDOR_CATEGORY;
    }

    public String getVENDOR_NAME() {
        return VENDOR_NAME;
    }

    public void setVENDOR_NAME(String VENDOR_NAME) {
        this.VENDOR_NAME = VENDOR_NAME;
    }

    public String getVENDOR_STREET() {
        return VENDOR_STREET;
    }

    public void setVENDOR_STREET(String VENDOR_STREET) {
        this.VENDOR_STREET = VENDOR_STREET;
    }

    public String getVENDOR_ADDRESS() {
        return VENDOR_ADDRESS;
    }

    public void setVENDOR_ADDRESS(String VENDOR_ADDRESS) {
        this.VENDOR_ADDRESS = VENDOR_ADDRESS;
    }

    public String getCITY() {
        return CITY;
    }

    public void setCITY(String CITY) {
        this.CITY = CITY;
    }

    public String getCONTACT_PERSON() {
        return CONTACT_PERSON;
    }

    public void setCONTACT_PERSON(String CONTACT_PERSON) {
        this.CONTACT_PERSON = CONTACT_PERSON;
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

    public String getCREATEDBYUSERID() {
        return CREATEDBYUSERID;
    }

    public void setCREATEDBYUSERID(String CREATEDBYUSERID) {
        this.CREATEDBYUSERID = CREATEDBYUSERID;
    }

    public String getISACTIVE() {
        return ISACTIVE;
    }

    public void setISACTIVE(String ISACTIVE) {
        this.ISACTIVE = ISACTIVE;
    }

    public String getPINCODE() {
        return PINCODE;
    }

    public void setPINCODE(String PINCODE) {
        this.PINCODE = PINCODE;
    }

    public String getTELEPHONENO() {
        return TELEPHONENO;
    }

    public void setTELEPHONENO(String TELEPHONENO) {
        this.TELEPHONENO = TELEPHONENO;
    }

    public String getMASTERCOUNTRYID() {
        return MASTERCOUNTRYID;
    }

    public void setMASTERCOUNTRYID(String MASTERCOUNTRYID) {
        this.MASTERCOUNTRYID = MASTERCOUNTRYID;
    }

    public String getISINVENTORY() {
        return ISINVENTORY;
    }

    public void setISINVENTORY(String ISINVENTORY) {
        this.ISINVENTORY = ISINVENTORY;
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

    @NonNull
    @Override
    public String toString() {
        return "VendorSync{" +
                "VENDORID='" + VENDORID + '\'' +
                ", VENDOR_GUID='" + VENDOR_GUID + '\'' +
                ", ORG_GUID='" + ORG_GUID + '\'' +
                ", STORE_GUID='" + STORE_GUID + '\'' +
                ", VENDOR_CATEGORY='" + VENDOR_CATEGORY + '\'' +
                ", VENDOR_NAME='" + VENDOR_NAME + '\'' +
                ", VENDOR_STREET='" + VENDOR_STREET + '\'' +
                ", VENDOR_ADDRESS='" + VENDOR_ADDRESS + '\'' +
                ", CITY='" + CITY + '\'' +
                ", CONTACT_PERSON='" + CONTACT_PERSON + '\'' +
                ", MOBILE='" + MOBILE + '\'' +
                ", EMAIL='" + EMAIL + '\'' +
                ", GST='" + GST + '\'' +
                ", PAN='" + PAN + '\'' +
                ", CREATEDBYUSERID='" + CREATEDBYUSERID + '\'' +
                ", ISACTIVE='" + ISACTIVE + '\'' +
                ", PINCODE='" + PINCODE + '\'' +
                ", TELEPHONENO='" + TELEPHONENO + '\'' +
                ", MASTERCOUNTRYID='" + MASTERCOUNTRYID + '\'' +
                ", ISINVENTORY='" + ISINVENTORY + '\'' +
                ", VENDORSTATE='" + VENDORSTATE + '\'' +
                ", PAYMENTTERMS='" + PAYMENTTERMS + '\'' +
                ", ISSYNCED='" + ISSYNCED + '\'' +
                '}';
    }
}
