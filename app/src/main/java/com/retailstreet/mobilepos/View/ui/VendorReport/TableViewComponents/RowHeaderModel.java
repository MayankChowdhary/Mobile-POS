package com.retailstreet.mobilepos.View.ui.VendorReport.TableViewComponents;

/**
 * Created by evrencoskun on 27.11.2017.
 */

public class RowHeaderModel {
    private String mData;
    private String myId;
    private String masterId;

    public RowHeaderModel(String mData, String myId,String masterId) {
        this.mData = mData;
        this.myId = myId;
        this.masterId = masterId;
    }

    public String getData() {
        return mData;
    }
    public String getId() {
        return myId;
    }
    public String getMasterId() {
        return masterId;
    }
}
