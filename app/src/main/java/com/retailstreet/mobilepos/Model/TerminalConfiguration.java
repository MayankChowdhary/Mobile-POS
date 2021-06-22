package com.retailstreet.mobilepos.Model;
import androidx.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */


@Keep
public class TerminalConfiguration {
    @SerializedName("TERMINAL_GUID")
    @Expose
    private String tERMINALGUID;
    @SerializedName("TERMINAL_NAME")
    @Expose
    private String tERMINALNAME;
    @SerializedName("STORE_GUID")
    @Expose
    private String sTOREGUID;
    @SerializedName("TERMINALCONFIG_GUID")
    @Expose
    private String tERMINALCONFIGGUID;

    private String MASTER_TERMINAL_ID;

    /**
     * No args constructor for use in serialization
     *
     */
    public TerminalConfiguration() {
    }



    public TerminalConfiguration(String tERMINALGUID, String tERMINALNAME, String sTOREGUID, String tERMINALCONFIGGUID, String MASTER_TERMINAL_ID) {
        this.tERMINALGUID = tERMINALGUID;
        this.tERMINALNAME = tERMINALNAME;
        this.sTOREGUID = sTOREGUID;
        this.tERMINALCONFIGGUID = tERMINALCONFIGGUID;
        this.MASTER_TERMINAL_ID = MASTER_TERMINAL_ID;
    }



    public String gettERMINALGUID() {
        return tERMINALGUID;
    }

    public void settERMINALGUID(String tERMINALGUID) {
        this.tERMINALGUID = tERMINALGUID;
    }

    public String gettERMINALNAME() {
        return tERMINALNAME;
    }

    public void settERMINALNAME(String tERMINALNAME) {
        this.tERMINALNAME = tERMINALNAME;
    }

    public String getsTOREGUID() {
        return sTOREGUID;
    }

    public void setsTOREGUID(String sTOREGUID) {
        this.sTOREGUID = sTOREGUID;
    }

    public String gettERMINALCONFIGGUID() {
        return tERMINALCONFIGGUID;
    }

    public void settERMINALCONFIGGUID(String tERMINALCONFIGGUID) {
        this.tERMINALCONFIGGUID = tERMINALCONFIGGUID;
    }

    public String getMASTER_TERMINAL_ID() {
        return MASTER_TERMINAL_ID;
    }

    public void setMASTER_TERMINAL_ID(String MASTER_TERMINAL_ID) {
        this.MASTER_TERMINAL_ID = MASTER_TERMINAL_ID;
    }

    public String getTERMINALGUID() {
        return tERMINALGUID;
    }

    public void setTERMINALGUID(String tERMINALGUID) {
        this.tERMINALGUID = tERMINALGUID;
    }

    public String getTERMINALNAME() {
        return tERMINALNAME;
    }

    public void setTERMINALNAME(String tERMINALNAME) {
        this.tERMINALNAME = tERMINALNAME;
    }

    public String getSTOREGUID() {
        return sTOREGUID;
    }

    public void setSTOREGUID(String sTOREGUID) {
        this.sTOREGUID = sTOREGUID;
    }

    public String getTERMINALCONFIGGUID() {
        return tERMINALCONFIGGUID;
    }

    public void setTERMINALCONFIGGUID(String tERMINALCONFIGGUID) {
        this.tERMINALCONFIGGUID = tERMINALCONFIGGUID;
    }

    @Override
    public String toString() {
        return tERMINALNAME;
    }
}