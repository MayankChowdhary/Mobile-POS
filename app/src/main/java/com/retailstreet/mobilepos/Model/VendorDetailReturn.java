package com.retailstreet.mobilepos.Model;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */
public class VendorDetailReturn {

    String VENDOR_RETURN_DETAILID;
    String VENDOR_RETURNGUID;
    String ITEM_GUID;
    String BATCHNO;
    String QTY;
    String UOM_GUID;

    public VendorDetailReturn() {

    }

    public VendorDetailReturn(String VENDOR_RETURN_DETAILID, String VENDOR_RETURNGUID, String ITEM_GUID, String BATCHNO, String QTY, String UOM_GUID) {
        this.VENDOR_RETURN_DETAILID = VENDOR_RETURN_DETAILID;
        this.VENDOR_RETURNGUID = VENDOR_RETURNGUID;
        this.ITEM_GUID = ITEM_GUID;
        this.BATCHNO = BATCHNO;
        this.QTY = QTY;
        this.UOM_GUID = UOM_GUID;
    }

    public String getVENDOR_RETURN_DETAILID() {
        return VENDOR_RETURN_DETAILID;
    }

    public void setVENDOR_RETURN_DETAILID(String VENDOR_RETURN_DETAILID) {
        this.VENDOR_RETURN_DETAILID = VENDOR_RETURN_DETAILID;
    }

    public String getVENDOR_RETURNGUID() {
        return VENDOR_RETURNGUID;
    }

    public void setVENDOR_RETURNGUID(String VENDOR_RETURNGUID) {
        this.VENDOR_RETURNGUID = VENDOR_RETURNGUID;
    }

    public String getITEM_GUID() {
        return ITEM_GUID;
    }

    public void setITEM_GUID(String ITEM_GUID) {
        this.ITEM_GUID = ITEM_GUID;
    }

    public String getBATCHNO() {
        return BATCHNO;
    }

    public void setBATCHNO(String BATCHNO) {
        this.BATCHNO = BATCHNO;
    }

    public String getQTY() {
        return QTY;
    }

    public void setQTY(String QTY) {
        this.QTY = QTY;
    }

    public String getUOM_GUID() {
        return UOM_GUID;
    }

    public void setUOM_GUID(String UOM_GUID) {
        this.UOM_GUID = UOM_GUID;
    }
}
