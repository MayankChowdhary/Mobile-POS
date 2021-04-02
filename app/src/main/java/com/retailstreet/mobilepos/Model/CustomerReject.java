package com.retailstreet.mobilepos.Model;

public class CustomerReject {
    String ID;
    String REASONGUID;
    String REASONFOR;
    String REASON_FOR_REJECTION;
    String ACTIVE;
    String STORE_ID;
    String CREATEDBY;
    String LAST_MODIFIED;

    public CustomerReject() {

    }

    public CustomerReject(String ID, String REASONGUID, String REASONFOR, String REASON_FOR_REJECTION, String ACTIVE, String STORE_ID, String CREATEDBY, String LAST_MODIFIED) {
        this.ID =ID;
        this.REASONGUID = REASONGUID;
        this.REASONFOR = REASONFOR;
        this.REASON_FOR_REJECTION = REASON_FOR_REJECTION;
        this.ACTIVE = ACTIVE;
        this.STORE_ID = STORE_ID;
        this.CREATEDBY = CREATEDBY;
        this.LAST_MODIFIED = LAST_MODIFIED;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getREASONGUID() {
        return REASONGUID;
    }

    public void setREASONGUID(String REASONGUID) {
        this.REASONGUID = REASONGUID;
    }

    public String getREASONFOR() {
        return REASONFOR;
    }

    public void setREASONFOR(String REASONFOR) {
        this.REASONFOR = REASONFOR;
    }

    public String getREASON_FOR_REJECTION() {
        return REASON_FOR_REJECTION;
    }

    public void setREASON_FOR_REJECTION(String REASON_FOR_REJECTION) {
        this.REASON_FOR_REJECTION = REASON_FOR_REJECTION;
    }

    public String getACTIVE() {
        return ACTIVE;
    }

    public void setACTIVE(String ACTIVE) {
        this.ACTIVE = ACTIVE;
    }

    public String getSTORE_ID() {
        return STORE_ID;
    }

    public void setSTORE_ID(String STORE_ID) {
        this.STORE_ID = STORE_ID;
    }

    public String getCREATEDBY() {
        return CREATEDBY;
    }

    public void setCREATEDBY(String CREATEDBY) {
        this.CREATEDBY = CREATEDBY;
    }

    public String getLAST_MODIFIED() {
        return LAST_MODIFIED;
    }

    public void setLAST_MODIFIED(String LAST_MODIFIED) {
        this.LAST_MODIFIED = LAST_MODIFIED;
    }
}
