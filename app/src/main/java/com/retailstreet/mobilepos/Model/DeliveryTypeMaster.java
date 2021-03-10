package com.retailstreet.mobilepos.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeliveryTypeMaster {
    @SerializedName("DELIVERYTYPE_GUID")
    @Expose
    String  DELIVERYTYPE_GUID;
    @SerializedName("DELIVERYTYPE")
    @Expose
    String  DELIVERYTYPE;
    @SerializedName("LEGEND")
    @Expose
    String  LEGEND;
    
    
    public DeliveryTypeMaster(String  DELIVERYTYPE_GUID, String  DELIVERYTYPE, String  LEGEND){

        this.DELIVERYTYPE_GUID = DELIVERYTYPE_GUID;
        this.DELIVERYTYPE = DELIVERYTYPE;
        this.LEGEND = LEGEND;
        
    }

    public String getDELIVERYTYPE_GUID() {
        return DELIVERYTYPE_GUID;
    }

    public void setDELIVERYTYPE_GUID(String DELIVERYTYPE_GUID) {
        this.DELIVERYTYPE_GUID = DELIVERYTYPE_GUID;
    }

    public String getDELIVERYTYPE() {
        return DELIVERYTYPE;
    }

    public void setDELIVERYTYPE(String DELIVERYTYPE) {
        this.DELIVERYTYPE = DELIVERYTYPE;
    }

    public String getLEGEND() {
        return LEGEND;
    }

    public void setLEGEND(String LEGEND) {
        this.LEGEND = LEGEND;
    }
}
