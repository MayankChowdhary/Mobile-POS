package com.retailstreet.mobilepos.View.ui.VendorReportDetails.TableViewComponents;

/**
 * Created by evrencoskun on 1.12.2017.
 */
public class User {
    public String PROD_NM;
    public String BARCODE;
    public String PUR_PRICE;
    public String GRN_QTY;
    public String FREE_QTY;
    public String TOTAL;
    public User() {

    }

    public User(String PROD_NM, String BARCODE, String PUR_PRICE, String GRN_QTY, String FREE_QTY, String TOTAL) {
        this.PROD_NM = PROD_NM;
        this.BARCODE = BARCODE;
        this.PUR_PRICE = PUR_PRICE;
        this.GRN_QTY = GRN_QTY;
        this.FREE_QTY = FREE_QTY;
        this.TOTAL = TOTAL;
    }
}
