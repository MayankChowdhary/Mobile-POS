package com.retailstreet.mobilepos.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TerminalUserAllocation {

    @SerializedName("TERMINAL_GUID")
    @Expose
    private String tERMINALGUID;
    @SerializedName("STORE_GUID")
    @Expose
    private String sTOREGUID;
    @SerializedName("USER_GUID")
    @Expose
    private String uSERGUID;
    @SerializedName("TERMINAL_USER_ALLOCATION_ID")
    @Expose
    private String TERMINAL_USER_ALLOCATION_ID;

    /**
     * No args constructor for use in serialization
     *
     */
    public TerminalUserAllocation() {
    }

    /**
     *
     * @param sTOREGUID
     * @param tERMINALGUID
     * @param uSERGUID
     * @param TERMINAL_USER_ALLOCATION_ID
     */
    public TerminalUserAllocation(String tERMINALGUID, String sTOREGUID, String uSERGUID, String TERMINAL_USER_ALLOCATION_ID) {
        super();
        this.tERMINALGUID = tERMINALGUID;
        this.sTOREGUID = sTOREGUID;
        this.uSERGUID = uSERGUID;
        this.TERMINAL_USER_ALLOCATION_ID = TERMINAL_USER_ALLOCATION_ID;
    }

    public String getTERMINALGUID() {
        return tERMINALGUID;
    }

    public void setTERMINALGUID(String tERMINALGUID) {
        this.tERMINALGUID = tERMINALGUID;
    }

    public String getSTOREGUID() {
        return sTOREGUID;
    }

    public void setSTOREGUID(String sTOREGUID) {
        this.sTOREGUID = sTOREGUID;
    }

    public String getUSERGUID() {
        return uSERGUID;
    }

    public void setUSERGUID(String uSERGUID) {
        this.uSERGUID = uSERGUID;
    }


    public String getTERMINAL_USER_ALLOCATION_ID() {
        return TERMINAL_USER_ALLOCATION_ID;
    }

    public void setTERMINAL_USER_ALLOCATION_ID(String TERMINAL_USER_ALLOCATION_ID) {
        this.TERMINAL_USER_ALLOCATION_ID = TERMINAL_USER_ALLOCATION_ID;
    }

    @Override
    public String toString() {
        return uSERGUID;
    }
}