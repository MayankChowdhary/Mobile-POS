package com.retailstreet.mobilepos.Model;

import androidx.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@Keep
public class MasterUOM {

    @SerializedName("ISACTIVE")
    @Expose
    String ISACTIVE;
    @SerializedName("UoM")
    @Expose
    String UoM;
    @SerializedName("UOM_GUID")
    @Expose
    String UOM_GUID;
    @SerializedName("UoMID")
    @Expose
    String UoMID;


    public MasterUOM() {
    }

    public MasterUOM(String ISACTIVE, String uoM, String UOM_GUID, String uoMID) {
        this.ISACTIVE = ISACTIVE;
        UoM = uoM;
        this.UOM_GUID = UOM_GUID;
        UoMID = uoMID;
    }

    public String getISACTIVE() {
        return ISACTIVE;
    }

    public void setISACTIVE(String ISACTIVE) {
        this.ISACTIVE = ISACTIVE;
    }

    public String getUoM() {
        return UoM;
    }

    public void setUoM(String uoM) {
        UoM = uoM;
    }

    public String getUOM_GUID() {
        return UOM_GUID;
    }

    public void setUOM_GUID(String UOM_GUID) {
        this.UOM_GUID = UOM_GUID;
    }

    public String getUoMID() {
        return UoMID;
    }

    public void setUoMID(String uoMID) {
        UoMID = uoMID;
    }
}
