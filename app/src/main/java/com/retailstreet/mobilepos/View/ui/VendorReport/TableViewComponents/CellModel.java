package com.retailstreet.mobilepos.View.ui.VendorReport.TableViewComponents;

import com.evrencoskun.tableview.sort.ISortableModel;

/**
 * Created by evrencoskun on 27.11.2017.
 */

public class CellModel implements ISortableModel {
    private String mId;
    private Object mData;
    private String masterID;

    public CellModel(String pId, Object mData, String MasterID) {
        this.mId = pId;
        this.mData = mData;
        this.masterID = MasterID;
    }

    public Object getData() {
        return mData;
    }

    @Override
    public String getId() {
        return mId;
    }

    @Override
    public Object getContent() {
        return mData;
    }

    public String getMasterID(){
        return masterID;
    }


}
