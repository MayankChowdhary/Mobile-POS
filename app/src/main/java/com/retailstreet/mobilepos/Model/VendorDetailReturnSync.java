package com.retailstreet.mobilepos.Model;

/**
 * Created by Mayank Choudhary on 11-05-2021.
 * mayankchoudhary00@gmail.com
 */
public class VendorDetailReturnSync {

   String VENDOR_RETURN_MASTER_GUID;
   String MASTER_PRODUCT_ITEM_GUID;
   String BATCHNO;
   String QTY;
   String MASTER_UOM_GUID;

    public VendorDetailReturnSync() {


    }

    public VendorDetailReturnSync(String VENDOR_RETURN_MASTER_GUID, String MASTER_PRODUCT_ITEM_GUID, String BATCHNO, String QTY, String MASTER_UOM_GUID) {
        this.VENDOR_RETURN_MASTER_GUID = VENDOR_RETURN_MASTER_GUID;
        this.MASTER_PRODUCT_ITEM_GUID = MASTER_PRODUCT_ITEM_GUID;
        this.BATCHNO = BATCHNO;
        this.QTY = QTY;
        this.MASTER_UOM_GUID = MASTER_UOM_GUID;
    }

    public String getVENDOR_RETURN_MASTER_GUID() {
        return VENDOR_RETURN_MASTER_GUID;
    }

    public void setVENDOR_RETURN_MASTER_GUID(String VENDOR_RETURN_MASTER_GUID) {
        this.VENDOR_RETURN_MASTER_GUID = VENDOR_RETURN_MASTER_GUID;
    }

    public String getMASTER_PRODUCT_ITEM_GUID() {
        return MASTER_PRODUCT_ITEM_GUID;
    }

    public void setMASTER_PRODUCT_ITEM_GUID(String MASTER_PRODUCT_ITEM_GUID) {
        this.MASTER_PRODUCT_ITEM_GUID = MASTER_PRODUCT_ITEM_GUID;
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

    public String getMASTER_UOM_GUID() {
        return MASTER_UOM_GUID;
    }

    public void setMASTER_UOM_GUID(String MASTER_UOM_GUID) {
        this.MASTER_UOM_GUID = MASTER_UOM_GUID;
    }
}
