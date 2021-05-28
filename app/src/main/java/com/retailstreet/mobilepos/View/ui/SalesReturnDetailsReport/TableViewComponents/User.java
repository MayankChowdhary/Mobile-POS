package com.retailstreet.mobilepos.View.ui.SalesReturnDetailsReport.TableViewComponents;

import java.text.DecimalFormat;

/**
 * Created by evrencoskun on 1.12.2017.
 */
public class User {
    public String PROD_NM;
    public String BARCODE;
    public String S_PRICE;
    public String QTY;
    public String TOTAL;


    public User() {

    }

    public User(String PROD_NM, String BARCODE, String s_PRICE, String QTY) {
        this.PROD_NM = PROD_NM;
        this.BARCODE = BARCODE;
        S_PRICE = s_PRICE;
        this.QTY = QTY;
        this.TOTAL = new DecimalFormat("#0.00").format(Double.parseDouble(QTY) * Double.parseDouble(S_PRICE));
    }
}
