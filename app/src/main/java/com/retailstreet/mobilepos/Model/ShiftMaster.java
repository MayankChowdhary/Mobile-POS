package com.retailstreet.mobilepos.Model;

import androidx.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */


@Keep
public class ShiftMaster {

    @SerializedName("SHIFTGUID")
    @Expose
    String  SHIFTGUID;
    @SerializedName("SHIFT_DESCRIPTION")
    @Expose
    String  SHIFT_DESCRIPTION;
    @SerializedName("START_TIME")
    @Expose
    String  START_TIME;
    @SerializedName("END_TIME")
    @Expose
    String  END_TIME;
    @SerializedName("MASTERORG_GUID")
    @Expose
    String  MASTERORG_GUID;
    @SerializedName("STORE_GUID")
    @Expose
    String  STORE_GUID;
    
    public ShiftMaster(String  SHIFTGUID, String  SHIFT_DESCRIPTION, String  START_TIME, String  END_TIME, String  MASTERORG_GUID, String  STORE_GUID){
        this.SHIFTGUID =SHIFTGUID;
        this.SHIFT_DESCRIPTION =SHIFT_DESCRIPTION;
        this.START_TIME=START_TIME;
        this.END_TIME = END_TIME;
        this.MASTERORG_GUID = MASTERORG_GUID;
        this.STORE_GUID = STORE_GUID;
    }
    
    public String getSHIFTGUID() {
        return SHIFTGUID;
    }

    public void setSHIFTGUID(String SHIFTGUID) {
        this.SHIFTGUID = SHIFTGUID;
    }

    public String getSHIFT_DESCRIPTION() {
        return SHIFT_DESCRIPTION;
    }

    public void setSHIFT_DESCRIPTION(String SHIFT_DESCRIPTION) {
        this.SHIFT_DESCRIPTION = SHIFT_DESCRIPTION;
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

    public String getMASTERORG_GUID() {
        return MASTERORG_GUID;
    }

    public void setMASTERORG_GUID(String MASTERORG_GUID) {
        this.MASTERORG_GUID = MASTERORG_GUID;
    }

    public String getSTORE_GUID() {
        return STORE_GUID;
    }

    public void setSTORE_GUID(String STORE_GUID) {
        this.STORE_GUID = STORE_GUID;
    }

    
    
    
}
