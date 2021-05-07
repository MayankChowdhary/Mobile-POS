package com.retailstreet.mobilepos.Model;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */


public class MasterState {

    String STATE_GUID;
    String STATE;
    String ISSYNCED;

    public MasterState() {
    }

    public MasterState(String STATE_GUID, String STATE, String ISSYNCED) {
        this.STATE_GUID = STATE_GUID;
        this.STATE = STATE;
        this.ISSYNCED = ISSYNCED;
    }

    public String getSTATE_GUID() {
        return STATE_GUID;
    }

    public void setSTATE_GUID(String STATE_GUID) {
        this.STATE_GUID = STATE_GUID;
    }

    public String getSTATE() {
        return STATE;
    }

    public void setSTATE(String STATE) {
        this.STATE = STATE;
    }

    public String getISSYNCED() {
        return ISSYNCED;
    }

    public void setISSYNCED(String ISSYNCED) {
        this.ISSYNCED = ISSYNCED;
    }
}

