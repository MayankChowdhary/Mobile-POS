package com.retailstreet.mobilepos.Model;

import androidx.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

@Keep
public class PaymentModeMaster {
    @SerializedName("PAYMODE_GUID")
    @Expose
    String  PAYMODE_GUID;

    @SerializedName("PAYMODE")
    @Expose
    String  PAYMODE;

    @SerializedName("LEGEND")
    @Expose
    String  LEGEND;
    
    public PaymentModeMaster( String  PAYMODE_GUID, String  PAYMODE, String  LEGEND){
        this.PAYMODE_GUID = PAYMODE_GUID;
        this.PAYMODE = PAYMODE;
        this.LEGEND = LEGEND;
    }
    
    public String getPAYMODE_GUID() {
        return PAYMODE_GUID;
    }

    public void setPAYMODE_GUID(String PAYMODE_GUID) {
        this.PAYMODE_GUID = PAYMODE_GUID;
    }

    public String getPAYMODE() {
        return PAYMODE;
    }

    public void setPAYMODE(String PAYMODE) {
        this.PAYMODE = PAYMODE;
    }

    public String getLEGEND() {
        return LEGEND;
    }

    public void setLEGEND(String LEGEND) {
        this.LEGEND = LEGEND;
    }

   
}
