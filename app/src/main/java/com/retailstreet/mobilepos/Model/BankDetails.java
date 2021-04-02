package com.retailstreet.mobilepos.Model;

public class BankDetails {
   String BANK_ID;
   String BANK_GUID;
   String BANK_NAME;
   String BANK_ADDRESS;
   String BANK_LOCATION;
   String BANK_CITY;
   String COUNTRY_CODE;
   String BANK_STATUS;
   String STORE_ID;
   String POS_USER;
   String LAST_MODIFIED;
   String STORE_GUID;

    public BankDetails() {

    }

    public BankDetails(String BANK_ID, String BANK_GUID, String BANK_NAME, String BANK_ADDRESS, String BANK_LOCATION, String BANK_CITY, String COUNTRY_CODE, String BANK_STATUS, String STORE_ID, String POS_USER, String LAST_MODIFIED, String STORE_GUID) {
        this.BANK_ID = BANK_ID;
        this.BANK_GUID = BANK_GUID;
        this.BANK_NAME = BANK_NAME;
        this.BANK_ADDRESS = BANK_ADDRESS;
        this.BANK_LOCATION = BANK_LOCATION;
        this.BANK_CITY = BANK_CITY;
        this.COUNTRY_CODE = COUNTRY_CODE;
        this.BANK_STATUS = BANK_STATUS;
        this.STORE_ID = STORE_ID;
        this.POS_USER = POS_USER;
        this.LAST_MODIFIED = LAST_MODIFIED;
        this.STORE_GUID = STORE_GUID;
    }

    public String getBANK_ID() {
        return BANK_ID;
    }

    public void setBANK_ID(String BANK_ID) {
        this.BANK_ID = BANK_ID;
    }

    public String getBANK_GUID() {
        return BANK_GUID;
    }

    public void setBANK_GUID(String BANK_GUID) {
        this.BANK_GUID = BANK_GUID;
    }

    public String getBANK_NAME() {
        return BANK_NAME;
    }

    public void setBANK_NAME(String BANK_NAME) {
        this.BANK_NAME = BANK_NAME;
    }

    public String getBANK_ADDRESS() {
        return BANK_ADDRESS;
    }

    public void setBANK_ADDRESS(String BANK_ADDRESS) {
        this.BANK_ADDRESS = BANK_ADDRESS;
    }

    public String getBANK_LOCATION() {
        return BANK_LOCATION;
    }

    public void setBANK_LOCATION(String BANK_LOCATION) {
        this.BANK_LOCATION = BANK_LOCATION;
    }

    public String getBANK_CITY() {
        return BANK_CITY;
    }

    public void setBANK_CITY(String BANK_CITY) {
        this.BANK_CITY = BANK_CITY;
    }

    public String getCOUNTRY_CODE() {
        return COUNTRY_CODE;
    }

    public void setCOUNTRY_CODE(String COUNTRY_CODE) {
        this.COUNTRY_CODE = COUNTRY_CODE;
    }

    public String getBANK_STATUS() {
        return BANK_STATUS;
    }

    public void setBANK_STATUS(String BANK_STATUS) {
        this.BANK_STATUS = BANK_STATUS;
    }

    public String getSTORE_ID() {
        return STORE_ID;
    }

    public void setSTORE_ID(String STORE_ID) {
        this.STORE_ID = STORE_ID;
    }

    public String getPOS_USER() {
        return POS_USER;
    }

    public void setPOS_USER(String POS_USER) {
        this.POS_USER = POS_USER;
    }

    public String getLAST_MODIFIED() {
        return LAST_MODIFIED;
    }

    public void setLAST_MODIFIED(String LAST_MODIFIED) {
        this.LAST_MODIFIED = LAST_MODIFIED;
    }

    public String getSTORE_GUID() {
        return STORE_GUID;
    }

    public void setSTORE_GUID(String STORE_GUID) {
        this.STORE_GUID = STORE_GUID;
    }
}
