package com.retailstreet.mobilepos.View.ui.SalesReport.TableViewComponents;

/**
 * Created by evrencoskun on 1.12.2017.
 */
public class User {
    public String CASHIER;
    public String BILL_NO;
    public String DATE;
    public String DISCOUNT;
    public String TOTAL;
    public String GST;
    public String MASTER_ID;


    public User() {
    }

    public User(String CASHIER, String BILL_NO, String DATE, String DISCOUNT, String TOTAL, String GST,String MasterID) {
        this.CASHIER = CASHIER;
        this.BILL_NO = BILL_NO;
        this.DATE = DATE;
        this.DISCOUNT = DISCOUNT;
        this.TOTAL = TOTAL;
        this.GST = GST;
        this.MASTER_ID= MasterID;
    }


}
