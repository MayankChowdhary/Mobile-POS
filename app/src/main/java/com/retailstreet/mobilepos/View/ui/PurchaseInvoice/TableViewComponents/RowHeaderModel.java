package com.retailstreet.mobilepos.View.ui.PurchaseInvoice.TableViewComponents;

/**
 * Created by evrencoskun on 27.11.2017.
 */

public class RowHeaderModel {
    private String mData;
    private String myId;

    public RowHeaderModel(String mData, String myId) {
        this.mData = mData;
        this.myId = myId;
    }

    public String getData() {
        return mData;
    }
    public String getId() {
        return myId;
    }
}
