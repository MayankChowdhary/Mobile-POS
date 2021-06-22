package com.retailstreet.mobilepos.Model;

/**
 * Created by Mayank Choudhary on 06-05-2021.
 * mayankchoudhary00@gmail.com
 */
public class VendorRejectReason {

    String ID;
    String REASONFOR;
    String ACTIVE;
    String REASONGUID;
    String STORE_ID;
    String LAST_MODIFIED;
    String REASON_FOR_REJECTION;
    String CREATEDBY;
    String MASTER_TERMINAL_ID;

    public VendorRejectReason() {
    }

    public VendorRejectReason(String ID, String REASONFOR, String ACTIVE, String REASONGUID, String STORE_ID, String LAST_MODIFIED, String REASON_FOR_REJECTION, String CREATEDBY, String MASTER_TERMINAL_ID) {
        this.ID = ID;
        this.REASONFOR = REASONFOR;
        this.ACTIVE = ACTIVE;
        this.REASONGUID = REASONGUID;
        this.STORE_ID = STORE_ID;
        this.LAST_MODIFIED = LAST_MODIFIED;
        this.REASON_FOR_REJECTION = REASON_FOR_REJECTION;
        this.CREATEDBY = CREATEDBY;
        this.MASTER_TERMINAL_ID = MASTER_TERMINAL_ID;
    }


    public String getMASTER_TERMINAL_ID() {
        return MASTER_TERMINAL_ID;
    }

    public void setMASTER_TERMINAL_ID(String MASTER_TERMINAL_ID) {
        this.MASTER_TERMINAL_ID = MASTER_TERMINAL_ID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getREASONFOR() {
        return REASONFOR;
    }

    public void setREASONFOR(String REASONFOR) {
        this.REASONFOR = REASONFOR;
    }

    public String getACTIVE() {
        return ACTIVE;
    }

    public void setACTIVE(String ACTIVE) {
        this.ACTIVE = ACTIVE;
    }

    public String getREASONGUID() {
        return REASONGUID;
    }

    public void setREASONGUID(String REASONGUID) {
        this.REASONGUID = REASONGUID;
    }

    public String getSTORE_ID() {
        return STORE_ID;
    }

    public void setSTORE_ID(String STORE_ID) {
        this.STORE_ID = STORE_ID;
    }

    public String getLAST_MODIFIED() {
        return LAST_MODIFIED;
    }

    public void setLAST_MODIFIED(String LAST_MODIFIED) {
        this.LAST_MODIFIED = LAST_MODIFIED;
    }

    public String getREASON_FOR_REJECTION() {
        return REASON_FOR_REJECTION;
    }

    public void setREASON_FOR_REJECTION(String REASON_FOR_REJECTION) {
        this.REASON_FOR_REJECTION = REASON_FOR_REJECTION;
    }

    public String getCREATEDBY() {
        return CREATEDBY;
    }

    public void setCREATEDBY(String CREATEDBY) {
        this.CREATEDBY = CREATEDBY;
    }
}
