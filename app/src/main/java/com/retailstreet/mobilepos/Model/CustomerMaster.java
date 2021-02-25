package com.retailstreet.mobilepos.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerMaster {

    @SerializedName("CUSTOMERTYPE")
    @Expose
    private String CUSTOMERTYPE;

    @SerializedName("CUSTOMERCATEGORY")
    @Expose
    private String CUSTOMERCATEGORY;

    @SerializedName("CUST_ID")
    @Expose
    private String CUST_ID;

    @SerializedName("CUSTOMERGUID")
    @Expose
    private String CUSTOMERGUID;

    @SerializedName("NAME")
    @Expose
    private String NAME;

    @SerializedName("E_MAIL")
    @Expose
    private String E_MAIL;

    @SerializedName("GENDER")
    @Expose
    private String GENDER;

    @SerializedName("AGE")
    @Expose
    private String AGE;

    @SerializedName("ISEMAILVALIDATED")
    @Expose
    private String ISEMAILVALIDATED;

    @SerializedName("MOBILE_NO")
    @Expose
    private String MOBILE_NO;

    @SerializedName("ISMOBILEVALIDATED")
    @Expose
    private String ISMOBILEVALIDATED;

    @SerializedName("SECONDARYEMAIL")
    @Expose
    private String SECONDARYEMAIL;

    @SerializedName("SECONDARYMOBILE")
    @Expose
    private String SECONDARYMOBILE;

    @SerializedName("CUSTOMERDISCOUNTPERCENTAGE")
    @Expose
    private String CUSTOMERDISCOUNTPERCENTAGE;

    @SerializedName("LASTOTP")
    @Expose
    private String LASTOTP;

    @SerializedName("OTPVALIDATEDDATETIME")
    @Expose
    private String OTPVALIDATEDDATETIME;

    @SerializedName("EMAILVALIDATEDDATETIME")
    @Expose
    private String EMAILVALIDATEDDATETIME;

    @SerializedName("UPDATEDBY")
    @Expose
    private String UPDATEDBY;

    @SerializedName("LAST_MODIFIED")
    @Expose
    private String LAST_MODIFIED;

    @SerializedName("TOTALORDERS")
    @Expose
    private String TOTALORDERS;

    @SerializedName("CREDIT_CUST")
    @Expose
    private String CREDIT_CUST;

    @SerializedName("REGISTEREDFROM")
    @Expose
    private String REGISTEREDFROM;

    @SerializedName("REGISTEREDFROMSTOREID")
    @Expose
    private String REGISTEREDFROMSTOREID;

    @SerializedName("PANNO")
    @Expose
    private String PANNO;

    @SerializedName("GSTNO")
    @Expose
    private String GSTNO;

    @SerializedName("CPASSWORD")
    @Expose
    private String CPASSWORD;

    @SerializedName("CUSTOMERSTATUS")
    @Expose
    private String CUSTOMERSTATUS;

    @SerializedName("MASTER_CUSTOMER_TYPE")
    @Expose
    private String MASTER_CUSTOMER_TYPE;

    @SerializedName("MASTER_CUSTOMERCATEGORY")
    @Expose
    private String MASTER_CUSTOMERCATEGORY;

    @SerializedName("ADVANCE_AMOUNT")
    @Expose
    private String ADVANCE_AMOUNT;

    @SerializedName("BALANCE_AMOUNT")
    @Expose
    private String BALANCE_AMOUNT;

    @SerializedName("CUSTOMERSTOREKEY")
    @Expose
    private String CUSTOMERSTOREKEY;

    @SerializedName("STORE_ID")
    @Expose
    private String STORE_ID;

    @SerializedName("MASTER_CUSTOMERCATEGORYID")
    @Expose
    private String MASTER_CUSTOMERCATEGORYID;

    @SerializedName("PERCENTAGE")
    @Expose
    private String PERCENTAGE;

    @SerializedName("CREATEDBY")
    @Expose
    private String CREATEDBY;

    @SerializedName("ISSYNCED")
    @Expose
    private String ISSYNCED;


    public CustomerMaster(String CUSTOMERTYPE, String CUSTOMERCATEGORY, String CUST_ID, String CUSTOMERGUID, String NAME, String E_MAIL, String GENDER, String AGE, String ISEMAILVALIDATED, String MOBILE_NO, String ISMOBILEVALIDATED, String SECONDARYEMAIL, String SECONDARYMOBILE, String CUSTOMERDISCOUNTPERCENTAGE, String LASTOTP, String OTPVALIDATEDDATETIME, String EMAILVALIDATEDDATETIME, String UPDATEDBY, String  LAST_MODIFIED, String TOTALORDERS, String CREDIT_CUST, String REGISTEREDFROM, String  REGISTEREDFROMSTOREID, String  PANNO, String GSTNO, String CPASSWORD, String CUSTOMERSTATUS, String MASTER_CUSTOMER_TYPE, String MASTER_CUSTOMERCATEGORY, String ADVANCE_AMOUNT, String BALANCE_AMOUNT, String CUSTOMERSTOREKEY, String  STORE_ID, String MASTER_CUSTOMERCATEGORYID, String PERCENTAGE, String CREATEDBY, String ISSYNCED ){
        super();

        this.CUSTOMERTYPE = CUSTOMERTYPE;
        this.CUSTOMERCATEGORY = CUSTOMERCATEGORY;
        this.CUST_ID = CUST_ID;
        this.CUSTOMERGUID = CUSTOMERGUID;
        this.NAME= NAME;
        this.E_MAIL = E_MAIL;
        this.GENDER = GENDER;
        this.AGE = AGE;
        this.ISEMAILVALIDATED = ISEMAILVALIDATED;
        this.MOBILE_NO = MOBILE_NO;
        this.ISMOBILEVALIDATED = ISMOBILEVALIDATED;
        this.SECONDARYEMAIL = SECONDARYEMAIL;
        this.SECONDARYMOBILE = SECONDARYMOBILE;
        this.CUSTOMERDISCOUNTPERCENTAGE = CUSTOMERDISCOUNTPERCENTAGE;
        this.LASTOTP =LASTOTP;
        this.OTPVALIDATEDDATETIME = OTPVALIDATEDDATETIME;
        this.EMAILVALIDATEDDATETIME = EMAILVALIDATEDDATETIME;
        this.UPDATEDBY = UPDATEDBY;
        this.LAST_MODIFIED = LAST_MODIFIED;
        this.TOTALORDERS =TOTALORDERS;
        this.CREDIT_CUST = CREDIT_CUST;
        this.REGISTEREDFROM = REGISTEREDFROM;
        this.REGISTEREDFROMSTOREID = REGISTEREDFROMSTOREID;
        this.PANNO= PANNO;
        this.GSTNO = GSTNO;
        this.CPASSWORD = CPASSWORD;
        this.CUSTOMERSTATUS = CUSTOMERSTATUS;
        this.MASTER_CUSTOMER_TYPE = MASTER_CUSTOMER_TYPE;
        this.MASTER_CUSTOMERCATEGORY = MASTER_CUSTOMERCATEGORY;
        this.ADVANCE_AMOUNT = ADVANCE_AMOUNT;
        this.BALANCE_AMOUNT = BALANCE_AMOUNT;
        this.CUSTOMERSTOREKEY = CUSTOMERSTOREKEY;
        this.STORE_ID=STORE_ID;
        this.MASTER_CUSTOMERCATEGORYID= MASTER_CUSTOMERCATEGORYID;
        this.PERCENTAGE = PERCENTAGE;
        this.CREATEDBY = CREATEDBY;
        this.ISSYNCED = ISSYNCED;

    }


    public String getCUSTOMERTYPE() {
        return CUSTOMERTYPE;
    }

    public void setCUSTOMERTYPE(String CUSTOMERTYPE) {
        this.CUSTOMERTYPE = CUSTOMERTYPE;
    }

    public String getCUSTOMERCATEGORY() {
        return CUSTOMERCATEGORY;
    }

    public void setCUSTOMERCATEGORY(String CUSTOMERCATEGORY) {
        this.CUSTOMERCATEGORY = CUSTOMERCATEGORY;
    }

    public String getCUST_ID() {
        return CUST_ID;
    }

    public void setCUST_ID(String CUST_ID) {
        this.CUST_ID = CUST_ID;
    }

    public String getCUSTOMERGUID() {
        return CUSTOMERGUID;
    }

    public void setCUSTOMERGUID(String CUSTOMERGUID) {
        this.CUSTOMERGUID = CUSTOMERGUID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getE_MAIL() {
        return E_MAIL;
    }

    public void setE_MAIL(String e_MAIL) {
        E_MAIL = e_MAIL;
    }

    public String getGENDER() {
        return GENDER;
    }

    public void setGENDER(String GENDER) {
        this.GENDER = GENDER;
    }

    public String getAGE() {
        return AGE;
    }

    public void setAGE(String AGE) {
        this.AGE = AGE;
    }

    public String getISEMAILVALIDATED() {
        return ISEMAILVALIDATED;
    }

    public void setISEMAILVALIDATED(String ISEMAILVALIDATED) {
        this.ISEMAILVALIDATED = ISEMAILVALIDATED;
    }

    public String getMOBILE_NO() {
        return MOBILE_NO;
    }

    public void setMOBILE_NO(String MOBILE_NO) {
        this.MOBILE_NO = MOBILE_NO;
    }

    public String getISMOBILEVALIDATED() {
        return ISMOBILEVALIDATED;
    }

    public void setISMOBILEVALIDATED(String ISMOBILEVALIDATED) {
        this.ISMOBILEVALIDATED = ISMOBILEVALIDATED;
    }

    public String getSECONDARYEMAIL() {
        return SECONDARYEMAIL;
    }

    public void setSECONDARYEMAIL(String SECONDARYEMAIL) {
        this.SECONDARYEMAIL = SECONDARYEMAIL;
    }

    public String getSECONDARYMOBILE() {
        return SECONDARYMOBILE;
    }

    public void setSECONDARYMOBILE(String SECONDARYMOBILE) {
        this.SECONDARYMOBILE = SECONDARYMOBILE;
    }

    public String getCUSTOMERDISCOUNTPERCENTAGE() {
        return CUSTOMERDISCOUNTPERCENTAGE;
    }

    public void setCUSTOMERDISCOUNTPERCENTAGE(String CUSTOMERDISCOUNTPERCENTAGE) {
        this.CUSTOMERDISCOUNTPERCENTAGE = CUSTOMERDISCOUNTPERCENTAGE;
    }

    public String getLASTOTP() {
        return LASTOTP;
    }

    public void setLASTOTP(String LASTOTP) {
        this.LASTOTP = LASTOTP;
    }

    public String getOTPVALIDATEDDATETIME() {
        return OTPVALIDATEDDATETIME;
    }

    public void setOTPVALIDATEDDATETIME(String OTPVALIDATEDDATETIME) {
        this.OTPVALIDATEDDATETIME = OTPVALIDATEDDATETIME;
    }

    public String getEMAILVALIDATEDDATETIME() {
        return EMAILVALIDATEDDATETIME;
    }

    public void setEMAILVALIDATEDDATETIME(String EMAILVALIDATEDDATETIME) {
        this.EMAILVALIDATEDDATETIME = EMAILVALIDATEDDATETIME;
    }

    public String getUPDATEDBY() {
        return UPDATEDBY;
    }

    public void setUPDATEDBY(String UPDATEDBY) {
        this.UPDATEDBY = UPDATEDBY;
    }

    public String getLAST_MODIFIED() {
        return LAST_MODIFIED;
    }

    public void setLAST_MODIFIED(String LAST_MODIFIED) {
        this.LAST_MODIFIED = LAST_MODIFIED;
    }

    public String getTOTALORDERS() {
        return TOTALORDERS;
    }

    public void setTOTALORDERS(String TOTALORDERS) {
        this.TOTALORDERS = TOTALORDERS;
    }

    public String getCREDIT_CUST() {
        return CREDIT_CUST;
    }

    public void setCREDIT_CUST(String CREDIT_CUST) {
        this.CREDIT_CUST = CREDIT_CUST;
    }

    public String getREGISTEREDFROM() {
        return REGISTEREDFROM;
    }

    public void setREGISTEREDFROM(String REGISTEREDFROM) {
        this.REGISTEREDFROM = REGISTEREDFROM;
    }

    public String getREGISTEREDFROMSTOREID() {
        return REGISTEREDFROMSTOREID;
    }

    public void setREGISTEREDFROMSTOREID(String REGISTEREDFROMSTOREID) {
        this.REGISTEREDFROMSTOREID = REGISTEREDFROMSTOREID;
    }

    public String getPANNO() {
        return PANNO;
    }

    public void setPANNO(String PANNO) {
        this.PANNO = PANNO;
    }

    public String getGSTNO() {
        return GSTNO;
    }

    public void setGSTNO(String GSTNO) {
        this.GSTNO = GSTNO;
    }

    public String getCPASSWORD() {
        return CPASSWORD;
    }

    public void setCPASSWORD(String CPASSWORD) {
        this.CPASSWORD = CPASSWORD;
    }

    public String getCUSTOMERSTATUS() {
        return CUSTOMERSTATUS;
    }

    public void setCUSTOMERSTATUS(String CUSTOMERSTATUS) {
        this.CUSTOMERSTATUS = CUSTOMERSTATUS;
    }

    public String getMASTER_CUSTOMER_TYPE() {
        return MASTER_CUSTOMER_TYPE;
    }

    public void setMASTER_CUSTOMER_TYPE(String MASTER_CUSTOMER_TYPE) {
        this.MASTER_CUSTOMER_TYPE = MASTER_CUSTOMER_TYPE;
    }

    public String getMASTER_CUSTOMERCATEGORY() {
        return MASTER_CUSTOMERCATEGORY;
    }

    public void setMASTER_CUSTOMERCATEGORY(String MASTER_CUSTOMERCATEGORY) {
        this.MASTER_CUSTOMERCATEGORY = MASTER_CUSTOMERCATEGORY;
    }

    public String getADVANCE_AMOUNT() {
        return ADVANCE_AMOUNT;
    }

    public void setADVANCE_AMOUNT(String ADVANCE_AMOUNT) {
        this.ADVANCE_AMOUNT = ADVANCE_AMOUNT;
    }

    public String getBALANCE_AMOUNT() {
        return BALANCE_AMOUNT;
    }

    public void setBALANCE_AMOUNT(String BALANCE_AMOUNT) {
        this.BALANCE_AMOUNT = BALANCE_AMOUNT;
    }

    public String getCUSTOMERSTOREKEY() {
        return CUSTOMERSTOREKEY;
    }

    public void setCUSTOMERSTOREKEY(String CUSTOMERSTOREKEY) {
        this.CUSTOMERSTOREKEY = CUSTOMERSTOREKEY;
    }

    public String getSTORE_ID() {
        return STORE_ID;
    }

    public void setSTORE_ID(String STORE_ID) {
        this.STORE_ID = STORE_ID;
    }

    public String getMASTER_CUSTOMERCATEGORYID() {
        return MASTER_CUSTOMERCATEGORYID;
    }

    public void setMASTER_CUSTOMERCATEGORYID(String MASTER_CUSTOMERCATEGORYID) {
        this.MASTER_CUSTOMERCATEGORYID = MASTER_CUSTOMERCATEGORYID;
    }

    public String getPERCENTAGE() {
        return PERCENTAGE;
    }

    public void setPERCENTAGE(String PERCENTAGE) {
        this.PERCENTAGE = PERCENTAGE;
    }

    public String getCREATEDBY() {
        return CREATEDBY;
    }

    public void setCREATEDBY(String CREATEDBY) {
        this.CREATEDBY = CREATEDBY;
    }

    public String getISSYNCED() {
        return ISSYNCED;
    }

    public void setISSYNCED(String ISSYNCED) {
        this.ISSYNCED = ISSYNCED;
    }

}
