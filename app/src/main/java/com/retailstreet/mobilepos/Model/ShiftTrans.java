package com.retailstreet.mobilepos.Model;

import androidx.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

@Keep
public class ShiftTrans {
    @SerializedName("SHIFT_TRANS_ID")
    @Expose
    String SHIFT_TRANS_ID;
    @SerializedName("SHIFT_TRANSACTIONGUID")
    @Expose
    String SHIFT_TRANSACTIONGUID;
    @SerializedName("ORG_GUID")
    @Expose
    String ORG_GUID;
    @SerializedName("STORE_GUID")
    @Expose
    String STORE_GUID;
    @SerializedName("SHIFT_GUID")
    @Expose
    String SHIFT_GUID;
    @SerializedName("SHIFT_DATE")
    @Expose
    String SHIFT_DATE;
    @SerializedName("START_TIME")
    @Expose
    String START_TIME;
    @SerializedName("END_TIME")
    @Expose
    String END_TIME;
    @SerializedName("IS_SHIFT_STARTED")
    @Expose
    String IS_SHIFT_STARTED;
    @SerializedName("IS_SHIFT_ENDED")
    @Expose
    String IS_SHIFT_ENDED;
    @SerializedName("USER_GUID")
    @Expose
    String USER_GUID;
    @SerializedName("CASH_OPENED")
    @Expose
    String CASH_OPENED;
    @SerializedName("CASH_CLOSED")
    @Expose
    String CASH_CLOSED;
    @SerializedName("ISACTIVE")
    @Expose
    String ISACTIVE;
    @SerializedName("ISSYNCED")
    @Expose
    String ISSYNCED;

    String MASTER_TERMINAL_ID;

    public ShiftTrans(){

    }


    public ShiftTrans(String SHIFT_TRANS_ID, String SHIFT_TRANSACTIONGUID, String ORG_GUID, String STORE_GUID, String SHIFT_GUID, String SHIFT_DATE, String START_TIME, String END_TIME, String IS_SHIFT_STARTED, String IS_SHIFT_ENDED, String USER_GUID, String CASH_OPENED, String CASH_CLOSED, String ISACTIVE, String ISSYNCED, String MASTER_TERMINAL_ID) {
        this.SHIFT_TRANS_ID = SHIFT_TRANS_ID;
        this.SHIFT_TRANSACTIONGUID = SHIFT_TRANSACTIONGUID;
        this.ORG_GUID = ORG_GUID;
        this.STORE_GUID = STORE_GUID;
        this.SHIFT_GUID = SHIFT_GUID;
        this.SHIFT_DATE = SHIFT_DATE;
        this.START_TIME = START_TIME;
        this.END_TIME = END_TIME;
        this.IS_SHIFT_STARTED = IS_SHIFT_STARTED;
        this.IS_SHIFT_ENDED = IS_SHIFT_ENDED;
        this.USER_GUID = USER_GUID;
        this.CASH_OPENED = CASH_OPENED;
        this.CASH_CLOSED = CASH_CLOSED;
        this.ISACTIVE = ISACTIVE;
        this.ISSYNCED = ISSYNCED;
        this.MASTER_TERMINAL_ID = MASTER_TERMINAL_ID;
    }


    public String getMASTER_TERMINAL_ID() {
        return MASTER_TERMINAL_ID;
    }

    public void setMASTER_TERMINAL_ID(String MASTER_TERMINAL_ID) {
        this.MASTER_TERMINAL_ID = MASTER_TERMINAL_ID;
    }

    public String getSHIFT_TRANS_ID() {
        return SHIFT_TRANS_ID;
    }

    public void setSHIFT_TRANS_ID(String SHIFT_TRANS_ID) {
        this.SHIFT_TRANS_ID = SHIFT_TRANS_ID;
    }

    public String getSHIFT_TRANSACTIONGUID() {
        return SHIFT_TRANSACTIONGUID;
    }

    public void setSHIFT_TRANSACTIONGUID(String SHIFT_TRANSACTIONGUID) {
        this.SHIFT_TRANSACTIONGUID = SHIFT_TRANSACTIONGUID;
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

    public String getSHIFT_GUID() {
        return SHIFT_GUID;
    }

    public void setSHIFT_GUID(String SHIFT_GUID) {
        this.SHIFT_GUID = SHIFT_GUID;
    }

    public String getSHIFT_DATE() {
        return SHIFT_DATE;
    }

    public void setSHIFT_DATE(String SHIFT_DATE) {
        this.SHIFT_DATE = SHIFT_DATE;
    }

    public String getSTART_TIME() {
        return START_TIME;
    }

    public void setSTART_TIME(String START_TIME) {
        this.START_TIME = START_TIME;
    }

    public String getEND_TIME() {
        return END_TIME;
    }

    public void setEND_TIME(String END_TIME) {
        this.END_TIME = END_TIME;
    }

    public String getIS_SHIFT_STARTED() {
        return IS_SHIFT_STARTED;
    }

    public void setIS_SHIFT_STARTED(String IS_SHIFT_STARTED) {
        this.IS_SHIFT_STARTED = IS_SHIFT_STARTED;
    }

    public String getIS_SHIFT_ENDED() {
        return IS_SHIFT_ENDED;
    }

    public void setIS_SHIFT_ENDED(String IS_SHIFT_ENDED) {
        this.IS_SHIFT_ENDED = IS_SHIFT_ENDED;
    }

    public String getUSER_GUID() {
        return USER_GUID;
    }

    public void setUSER_GUID(String USER_GUID) {
        this.USER_GUID = USER_GUID;
    }

    public String getCASH_OPENED() {
        return CASH_OPENED;
    }

    public void setCASH_OPENED(String CASH_OPENED) {
        this.CASH_OPENED = CASH_OPENED;
    }

    public String getCASH_CLOSED() {
        return CASH_CLOSED;
    }

    public void setCASH_CLOSED(String CASH_CLOSED) {
        this.CASH_CLOSED = CASH_CLOSED;
    }

    public String getISACTIVE() {
        return ISACTIVE;
    }

    public void setISACTIVE(String ISACTIVE) {
        this.ISACTIVE = ISACTIVE;
    }

    public String getISSYNCED() {
        return ISSYNCED;
    }

    public void setISSYNCED(String ISSYNCED) {
        this.ISSYNCED = ISSYNCED;
    }


}
