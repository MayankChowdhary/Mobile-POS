package com.retailstreet.mobilepos.Model;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

public class CustomerMasterUpload {

        private String CUSTOMERTYPE;
        private String CUSTOMERCATEGORY;
        private String CUSTOMERID;
        private String CUSTOMERGUID;
        private String NAME;
        private String E_MAIL;
        private String GENDER;
        private String AGE;
        private String ISEMAILVALIDATED;
        private String MOBILE_NO;
        private String ISMOBILEVALIDATED;
        private String SECONDARYEMAIL;
        private String SECONDARYMOBILE;
        private String CUSTOMERDISCOUNTPERCENTAGE;
        private String LASTOTP;
        private String OTPVALIDATEDDATETIME;
        private String EMAILVALIDATEDDATETIME;
        private String UPDATEDBY;
        private String LAST_MODIFIED;
        private String TOTALORDERS;
        private String CREDIT_CUST;
        private String REGISTEREDFROM;
        private String REGISTEREDFROMSTOREGUID;
        private String PANNO;
        private String GSTNO;
        private String CPASSWORD;
        private String CUSTOMERSTATUS;
        private String MASTER_CUSTOMER_TYPE;
        private String MASTER_CUSTOMERCATEGORY;
        private String ADVANCE_AMOUNT;
        private String BALANCE_AMOUNT;
        private String CUSTOMERSTOREKEY;
        private String STORE_ID;
        private String MASTER_CUSTOMERCATEGORYID;
        private String PERCENTAGE;
        private String CREATEDBYGUID;
        private String ISSYNCED;
        private String CREDITLIMIT;


    public CustomerMasterUpload() {
    }

    public CustomerMasterUpload(String CUSTOMERTYPE, String CUSTOMERCATEGORY, String CUSTOMERID, String CUSTOMERGUID, String NAME, String e_MAIL, String GENDER, String AGE, String ISEMAILVALIDATED, String MOBILE_NO, String ISMOBILEVALIDATED, String SECONDARYEMAIL, String SECONDARYMOBILE, String CUSTOMERDISCOUNTPERCENTAGE, String LASTOTP, String OTPVALIDATEDDATETIME, String EMAILVALIDATEDDATETIME, String UPDATEDBY, String LAST_MODIFIED, String TOTALORDERS, String CREDIT_CUST, String REGISTEREDFROM, String REGISTEREDFROMSTOREGUID, String PANNO, String GSTNO, String CPASSWORD, String CUSTOMERSTATUS, String MASTER_CUSTOMER_TYPE, String MASTER_CUSTOMERCATEGORY, String ADVANCE_AMOUNT, String BALANCE_AMOUNT, String CUSTOMERSTOREKEY, String STORE_ID, String MASTER_CUSTOMERCATEGORYID, String PERCENTAGE, String CREATEDBYGUID, String ISSYNCED, String CREDITLIMIT) {
        this.CUSTOMERTYPE = CUSTOMERTYPE;
        this.CUSTOMERCATEGORY = CUSTOMERCATEGORY;
        this.CUSTOMERID = CUSTOMERID;
        this.CUSTOMERGUID = CUSTOMERGUID;
        this.NAME = NAME;
        E_MAIL = e_MAIL;
        this.GENDER = GENDER;
        this.AGE = AGE;
        this.ISEMAILVALIDATED = ISEMAILVALIDATED;
        this.MOBILE_NO = MOBILE_NO;
        this.ISMOBILEVALIDATED = ISMOBILEVALIDATED;
        this.SECONDARYEMAIL = SECONDARYEMAIL;
        this.SECONDARYMOBILE = SECONDARYMOBILE;
        this.CUSTOMERDISCOUNTPERCENTAGE = CUSTOMERDISCOUNTPERCENTAGE;
        this.LASTOTP = LASTOTP;
        this.OTPVALIDATEDDATETIME = OTPVALIDATEDDATETIME;
        this.EMAILVALIDATEDDATETIME = EMAILVALIDATEDDATETIME;
        this.UPDATEDBY = UPDATEDBY;
        this.LAST_MODIFIED = LAST_MODIFIED;
        this.TOTALORDERS = TOTALORDERS;
        this.CREDIT_CUST = CREDIT_CUST;
        this.REGISTEREDFROM = REGISTEREDFROM;
        this.REGISTEREDFROMSTOREGUID = REGISTEREDFROMSTOREGUID;
        this.PANNO = PANNO;
        this.GSTNO = GSTNO;
        this.CPASSWORD = CPASSWORD;
        this.CUSTOMERSTATUS = CUSTOMERSTATUS;
        this.MASTER_CUSTOMER_TYPE = MASTER_CUSTOMER_TYPE;
        this.MASTER_CUSTOMERCATEGORY = MASTER_CUSTOMERCATEGORY;
        this.ADVANCE_AMOUNT = ADVANCE_AMOUNT;
        this.BALANCE_AMOUNT = BALANCE_AMOUNT;
        this.CUSTOMERSTOREKEY = CUSTOMERSTOREKEY;
        this.STORE_ID = STORE_ID;
        this.MASTER_CUSTOMERCATEGORYID = MASTER_CUSTOMERCATEGORYID;
        this.PERCENTAGE = PERCENTAGE;
        this.CREATEDBYGUID = CREATEDBYGUID;
        this.ISSYNCED = ISSYNCED;
        this.CREDITLIMIT = CREDITLIMIT;
    }

    public String getCREDITLIMIT() {
        return CREDITLIMIT;
    }

    public void setCREDITLIMIT(String CREDITLIMIT) {
        this.CREDITLIMIT = CREDITLIMIT;
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

        public String getCUSTOMERID() {
            return CUSTOMERID;
        }

        public void setCUSTOMERID(String CUSTOMERID) {
            this.CUSTOMERID = CUSTOMERID;
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

        public String getREGISTEREDFROMSTOREGUID() {
            return REGISTEREDFROMSTOREGUID;
        }

        public void setREGISTEREDFROMSTOREGUID(String REGISTEREDFROMSTOREGUID) {
            this.REGISTEREDFROMSTOREGUID = REGISTEREDFROMSTOREGUID;
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
            return CREATEDBYGUID;
        }

        public void setCREATEDBY(String CREATEDBY) {
            this.CREATEDBYGUID = CREATEDBY;
        }

        public String getISSYNCED() {
            return ISSYNCED;
        }

        public void setISSYNCED(String ISSYNCED) {
            this.ISSYNCED = ISSYNCED;
        }

    }


