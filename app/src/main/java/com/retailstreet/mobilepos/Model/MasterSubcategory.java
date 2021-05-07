package com.retailstreet.mobilepos.Model;

import androidx.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */


@Keep
public class MasterSubcategory {

    @SerializedName("SUB_CATEGORYID")
    @Expose
    String SUB_CATEGORYID;
    @SerializedName("SUB_CATEGORYGUID")
    @Expose
    String SUB_CATEGORYGUID;
    @SerializedName("SUBCATEGORY_DESCRIPTION")
    @Expose
    String SUBCATEGORY_DESCRIPTION;
    @SerializedName("CATEGORY_GUID")
    @Expose
    String CATEGORY_GUID;

    public MasterSubcategory() {


    }

    public MasterSubcategory(String SUB_CATEGORYID, String SUB_CATEGORYGUID, String SUBCATEGORY_DESCRIPTION, String CATEGORY_GUID) {
        this.SUB_CATEGORYID = SUB_CATEGORYID;
        this.SUB_CATEGORYGUID = SUB_CATEGORYGUID;
        this.SUBCATEGORY_DESCRIPTION = SUBCATEGORY_DESCRIPTION;
        this.CATEGORY_GUID = CATEGORY_GUID;
    }

    public String getSUB_CATEGORYID() {
        return SUB_CATEGORYID;
    }

    public void setSUB_CATEGORYID(String SUB_CATEGORYID) {
        this.SUB_CATEGORYID = SUB_CATEGORYID;
    }

    public String getSUB_CATEGORYGUID() {
        return SUB_CATEGORYGUID;
    }

    public void setSUB_CATEGORYGUID(String SUB_CATEGORYGUID) {
        this.SUB_CATEGORYGUID = SUB_CATEGORYGUID;
    }

    public String getSUBCATEGORY_DESCRIPTION() {
        return SUBCATEGORY_DESCRIPTION;
    }

    public void setSUBCATEGORY_DESCRIPTION(String SUBCATEGORY_DESCRIPTION) {
        this.SUBCATEGORY_DESCRIPTION = SUBCATEGORY_DESCRIPTION;
    }

    public String getCATEGORY_GUID() {
        return CATEGORY_GUID;
    }

    public void setCATEGORY_GUID(String CATEGORY_GUID) {
        this.CATEGORY_GUID = CATEGORY_GUID;
    }





}
