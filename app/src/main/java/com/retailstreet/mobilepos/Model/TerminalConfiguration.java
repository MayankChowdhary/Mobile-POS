package com.retailstreet.mobilepos.Model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

    /**
     * No args constructor for use in serialization
     *
     */
    public TerminalConfiguration() {
    }

    /**
     *
     * @param sTOREGUID
     * @param tERMINALGUID
     * @param tERMINALNAME
     * @param tERMINALCONFIGGUID
     */
    public TerminalConfiguration(String tERMINALGUID, String tERMINALNAME, String sTOREGUID, String tERMINALCONFIGGUID) {
        super();
        this.tERMINALGUID = tERMINALGUID;
        this.tERMINALNAME = tERMINALNAME;
        this.sTOREGUID = sTOREGUID;
        this.tERMINALCONFIGGUID = tERMINALCONFIGGUID;
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