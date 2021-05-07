package com.retailstreet.mobilepos.Model;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

public class CustomerAddress {

    String CUSTOMERADDRESSID;
    String MASTERCUSTOMERID;
    String ADDRESSTYPE;
    String CONTACTPERSONNAME;
    String ADDRESSLINE1;
    String ADDRESSLINE2;
    String STREET_AREA;
    String PINCODE;
    String CITY;
    String MASTERSTATEID;
    String ADDRESSSTATUS;
    String CREATEDBY;
    String CREATEDDATETIME;

    public CustomerAddress() {
    }

    public CustomerAddress(String CUSTOMERADDRESSID, String MASTERCUSTOMERID, String ADDRESSTYPE, String CONTACTPERSONNAME, String ADDRESSLINE1, String ADDRESSLINE2, String STREET_AREA, String PINCODE, String CITY, String MASTERSTATEID, String ADDRESSSTATUS, String CREATEDBY, String CREATEDDATETIME) {
        this.CUSTOMERADDRESSID = CUSTOMERADDRESSID;
        this.MASTERCUSTOMERID = MASTERCUSTOMERID;
        this.ADDRESSTYPE = ADDRESSTYPE;
        this.CONTACTPERSONNAME = CONTACTPERSONNAME;
        this.ADDRESSLINE1 = ADDRESSLINE1;
        this.ADDRESSLINE2 = ADDRESSLINE2;
        this.STREET_AREA = STREET_AREA;
        this.PINCODE = PINCODE;
        this.CITY = CITY;
        this.MASTERSTATEID = MASTERSTATEID;
        this.ADDRESSSTATUS = ADDRESSSTATUS;
        this.CREATEDBY = CREATEDBY;
        this.CREATEDDATETIME = CREATEDDATETIME;
    }

    public String getCUSTOMERADDRESSID() {
        return CUSTOMERADDRESSID;
    }

    public void setCUSTOMERADDRESSID(String CUSTOMERADDRESSID) {
        this.CUSTOMERADDRESSID = CUSTOMERADDRESSID;
    }

    public String getMASTERCUSTOMERID() {
        return MASTERCUSTOMERID;
    }

    public void setMASTERCUSTOMERID(String MASTERCUSTOMERID) {
        this.MASTERCUSTOMERID = MASTERCUSTOMERID;
    }

    public String getADDRESSTYPE() {
        return ADDRESSTYPE;
    }

    public void setADDRESSTYPE(String ADDRESSTYPE) {
        this.ADDRESSTYPE = ADDRESSTYPE;
    }

    public String getCONTACTPERSONNAME() {
        return CONTACTPERSONNAME;
    }

    public void setCONTACTPERSONNAME(String CONTACTPERSONNAME) {
        this.CONTACTPERSONNAME = CONTACTPERSONNAME;
    }

    public String getADDRESSLINE1() {
        return ADDRESSLINE1;
    }

    public void setADDRESSLINE1(String ADDRESSLINE1) {
        this.ADDRESSLINE1 = ADDRESSLINE1;
    }

    public String getADDRESSLINE2() {
        return ADDRESSLINE2;
    }

    public void setADDRESSLINE2(String ADDRESSLINE2) {
        this.ADDRESSLINE2 = ADDRESSLINE2;
    }

    public String getSTREET_AREA() {
        return STREET_AREA;
    }

    public void setSTREET_AREA(String STREET_AREA) {
        this.STREET_AREA = STREET_AREA;
    }

    public String getPINCODE() {
        return PINCODE;
    }

    public void setPINCODE(String PINCODE) {
        this.PINCODE = PINCODE;
    }

    public String getCITY() {
        return CITY;
    }

    public void setCITY(String CITY) {
        this.CITY = CITY;
    }

    public String getMASTERSTATEID() {
        return MASTERSTATEID;
    }

    public void setMASTERSTATEID(String MASTERSTATEID) {
        this.MASTERSTATEID = MASTERSTATEID;
    }

    public String getADDRESSSTATUS() {
        return ADDRESSSTATUS;
    }

    public void setADDRESSSTATUS(String ADDRESSSTATUS) {
        this.ADDRESSSTATUS = ADDRESSSTATUS;
    }

    public String getCREATEDBY() {
        return CREATEDBY;
    }

    public void setCREATEDBY(String CREATEDBY) {
        this.CREATEDBY = CREATEDBY;
    }

    public String getCREATEDDATETIME() {
        return CREATEDDATETIME;
    }

    public void setCREATEDDATETIME(String CREATEDDATETIME) {
        this.CREATEDDATETIME = CREATEDDATETIME;
    }
}
