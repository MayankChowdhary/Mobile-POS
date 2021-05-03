package com.retailstreet.mobilepos.View.ui.PurchaseInvoice.TableViewComponents;

/**
 * Created by evrencoskun on 1.12.2017.
 */
public class User {
    public String P_NAME;
    public String EXT_ID;
    public String BARCODE;
    public String EXPIRY;
    public String MRP;
    public String S_PRICE;
    public String P_PRICE;
    public String QTY;
    public String F_QTY;
    public String DISC;
    public String UOM;
    public String TOTAL;
    public String ITEM_GUID;

    public User() {
    }

    public User(String p_NAME, String EXT_ID, String BARCODE, String EXPIRY, String MRP, String s_PRICE, String p_PRICE, String QTY, String f_QTY, String DISC, String UOM, String TOTAL, String ITEM_GUID) {
        P_NAME = p_NAME;
        this.EXT_ID = EXT_ID;
        this.BARCODE = BARCODE;
        this.EXPIRY = EXPIRY;
        this.MRP = MRP;
        S_PRICE = s_PRICE;
        P_PRICE = p_PRICE;
        this.QTY = QTY;
        F_QTY = f_QTY;
        this.DISC = DISC;
        this.UOM = UOM;
        this.TOTAL = TOTAL;
        this.ITEM_GUID = ITEM_GUID;
    }
}
