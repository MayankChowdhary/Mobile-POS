package com.retailstreet.mobilepos.Model;

import androidx.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */


@Keep
public class HSNMaster {

    @SerializedName("HSN_ID")
    @Expose
   String HSN_ID;
    @SerializedName("HSN")
    @Expose
   String HSN;
    @SerializedName("GST")
    @Expose
   String GST;
    @SerializedName("SGST")
    @Expose
   String SGST;
    @SerializedName("CGST")
    @Expose
   String CGST;
    @SerializedName("IGST")
    @Expose
   String IGST;
    @SerializedName("CESS")
    @Expose
   String CESS;
    @SerializedName("HSN_STATUS")
    @Expose
   String HSN_STATUS;

    String MASTER_TERMINAL_ID;

    public HSNMaster() {
    }

    public HSNMaster(String HSN_ID, String HSN, String GST, String SGST, String CGST, String IGST, String CESS, String HSN_STATUS, String MASTER_TERMINAL_ID) {
        this.HSN_ID = HSN_ID;
        this.HSN = HSN;
        this.GST = GST;
        this.SGST = SGST;
        this.CGST = CGST;
        this.IGST = IGST;
        this.CESS = CESS;
        this.HSN_STATUS = HSN_STATUS;
        this.MASTER_TERMINAL_ID = MASTER_TERMINAL_ID;
    }

    public String getMASTER_TERMINAL_ID() {
        return MASTER_TERMINAL_ID;
    }

    public void setMASTER_TERMINAL_ID(String MASTER_TERMINAL_ID) {
        this.MASTER_TERMINAL_ID = MASTER_TERMINAL_ID;
    }

    public String getHSN_ID() {
        return HSN_ID;
    }

    public void setHSN_ID(String HSN_ID) {
        this.HSN_ID = HSN_ID;
    }

    public String getHSN() {
        return HSN;
    }

    public void setHSN(String HSN) {
        this.HSN = HSN;
    }

    public String getGST() {
        return GST;
    }

    public void setGST(String GST) {
        this.GST = GST;
    }

    public String getSGST() {
        return SGST;
    }

    public void setSGST(String SGST) {
        this.SGST = SGST;
    }

    public String getCGST() {
        return CGST;
    }

    public void setCGST(String CGST) {
        this.CGST = CGST;
    }

    public String getIGST() {
        return IGST;
    }

    public void setIGST(String IGST) {
        this.IGST = IGST;
    }

    public String getCESS() {
        return CESS;
    }

    public void setCESS(String CESS) {
        this.CESS = CESS;
    }

    public String getHSN_STATUS() {
        return HSN_STATUS;
    }

    public void setHSN_STATUS(String HSN_STATUS) {
        this.HSN_STATUS = HSN_STATUS;
    }
}
