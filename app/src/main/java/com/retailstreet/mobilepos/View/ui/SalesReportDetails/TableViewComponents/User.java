package com.retailstreet.mobilepos.View.ui.SalesReportDetails.TableViewComponents;

/**
 * Created by evrencoskun on 1.12.2017.
 */
public class User {
    public String PROD_NM;
    public String S_PRICE;
    public String QTY;
    public String TOTAL;
    public String DISCOUNT;
    public String SGST;
    public String CGST;
    public User() {
    }

    public User(String PROD_NM, String s_PRICE, String QTY, String TOTAL, String DISCOUNT, String SGST, String CGST) {
        this.PROD_NM = PROD_NM;
        S_PRICE = s_PRICE;
        this.QTY = QTY;
        this.TOTAL = TOTAL;
        this.DISCOUNT = DISCOUNT;
        this.SGST = SGST;
        this.CGST = CGST;
    }
}
