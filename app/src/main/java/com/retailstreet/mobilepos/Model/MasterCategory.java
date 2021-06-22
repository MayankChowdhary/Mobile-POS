package com.retailstreet.mobilepos.Model;

import androidx.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */


@Keep
public class MasterCategory {

    @SerializedName("CATEGORYID")
    @Expose
    private String CATEGORYID;
    @SerializedName("CATEGORY_GUID")
    @Expose
    private String CATEGORY_GUID;

    @SerializedName("CATEGORY")
    @Expose
    private String CATEGORY;

    String MASTER_TERMINAL_ID;

    public MasterCategory(){

    }

    public MasterCategory(String CATEGORYID, String CATEGORY_GUID, String CATEGORY, String MASTER_TERMINAL_ID) {
        this.CATEGORYID = CATEGORYID;
        this.CATEGORY_GUID = CATEGORY_GUID;
        this.CATEGORY = CATEGORY;
        this.MASTER_TERMINAL_ID = MASTER_TERMINAL_ID;
    }

    public String getMASTER_TERMINAL_ID() {
        return MASTER_TERMINAL_ID;
    }

    public void setMASTER_TERMINAL_ID(String MASTER_TERMINAL_ID) {
        this.MASTER_TERMINAL_ID = MASTER_TERMINAL_ID;
    }

    public String getCATEGORYID() {
        return CATEGORYID;
    }

    public void setCATEGORYID(String CATEGORYID) {
        this.CATEGORYID = CATEGORYID;
    }

    public String getCATEGORY_GUID() {
        return CATEGORY_GUID;
    }

    public void setCATEGORY_GUID(String CATEGORY_GUID) {
        this.CATEGORY_GUID = CATEGORY_GUID;
    }

    public String getCATEGORY() {
        return CATEGORY;
    }

    public void setCATEGORY(String CATEGORY) {
        this.CATEGORY = CATEGORY;
    }

}


