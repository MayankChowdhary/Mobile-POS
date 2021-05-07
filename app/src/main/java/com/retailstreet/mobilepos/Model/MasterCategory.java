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

    public MasterCategory(){

    }

    public MasterCategory(String CATEGORYID, String CATEGORY_GUID, String CATEGORY) {
        this.CATEGORYID = CATEGORYID;
        this.CATEGORY_GUID = CATEGORY_GUID;
        this.CATEGORY = CATEGORY;
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


