package com.retailstreet.mobilepos.View.ui.VendorReport.TableViewComponents;

/**
 * Created by evrencoskun on 1.12.2017.
 */
public class User {
    public String CASHIER;
    public String VENDOR_NM;
    public String INVOICE_NO;
    public String INVOICE_DATE;
    public String GRAND_AMOUNT;
    public String GRN_DATE;
    public String MASTER_ID;


    public User() {
    }

    public User(String CASHIER, String VENDOR_NM, String INVOICE_NO, String INVOICE_DATE, String GRAND_AMOUNT, String GRN_DATE, String MASTER_ID) {
        this.CASHIER = CASHIER;
        this.VENDOR_NM = VENDOR_NM;
        this.INVOICE_NO = INVOICE_NO;
        this.INVOICE_DATE = INVOICE_DATE;
        this.GRAND_AMOUNT = GRAND_AMOUNT;
        this.GRN_DATE = GRN_DATE;
        this.MASTER_ID = MASTER_ID;
    }
}
