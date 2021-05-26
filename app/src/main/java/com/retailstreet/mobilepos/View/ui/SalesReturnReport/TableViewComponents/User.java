package com.retailstreet.mobilepos.View.ui.SalesReturnReport.TableViewComponents;

/**
 * Created by evrencoskun on 1.12.2017.
 */
public class User {
    public String CASHIER;
    public String TRANS_ID;
    public String RETURN_DATE;
    public String RETURN_MODE;
    public String TOTAL;
    public String MASTER_ID;


    public User() {
    }

    public User(String CASHIER, String TRANS_ID, String RETURN_DATE, String RETURN_MODE, String TOTAL, String MASTER_ID) {
        this.CASHIER = CASHIER;
        this.TRANS_ID = TRANS_ID;
        this.RETURN_DATE = RETURN_DATE;
        if(RETURN_MODE !=null && RETURN_MODE.trim().isEmpty()){
            this.RETURN_MODE = "CASH";
        }else {
            this.RETURN_MODE = "CREDIT NOTE";
        }
        this.TOTAL = TOTAL;
        this.MASTER_ID = MASTER_ID;
    }
}
