package com.retailstreet.mobilepos.View.ui.VendorPayReport.TableViewComponents;

/**
 * Created by evrencoskun on 1.12.2017.
 */
public class User {
    public String USERNAME;
    public String VENDOR_NM;
    public String INVOICE_NO;
    public String PAYMODE;
    public String PAY_TYPE;
    public String PAY_AMOUNT;
    public String DUE_AMOUNT;
    public String INVOICE_DATE;
    public String PAID_FOR;
    public String CHEQUE_NO;
    public String MASTER_ID;


    public User() {
    }

    public User(String USERNAME, String VENDOR_NM, String INVOICE_NO, String PAYMODE, String PAY_TYPE, String PAY_AMOUNT, String DUE_AMOUNT, String INVOICE_DATE, String PAID_FOR, String CHEQUE_NO, String MASTER_ID) {
        this.USERNAME = USERNAME;
        this.VENDOR_NM = VENDOR_NM;
        this.INVOICE_NO = INVOICE_NO;
        if(PAYMODE.trim().equals("CA")){
            this.PAYMODE = "CASH";
        }else if(PAYMODE.trim().equals("CX")){
            this.PAYMODE = "CHEQUE";
        }else {
            this.PAYMODE = PAYMODE;
        }
        this.PAY_TYPE = PAY_TYPE;
        this.PAY_AMOUNT = PAY_AMOUNT;
        this.DUE_AMOUNT = DUE_AMOUNT;
        this.INVOICE_DATE = INVOICE_DATE;

        if(PAID_FOR==null || PAID_FOR.isEmpty()){
            this.PAID_FOR = "N/A";
        }else {
            this.PAID_FOR = PAID_FOR;
        }
        if(CHEQUE_NO==null || CHEQUE_NO.isEmpty()){
            this.CHEQUE_NO = "N/A";
        }else {
            this.CHEQUE_NO = CHEQUE_NO;
        }

        this.MASTER_ID = MASTER_ID;
    }
}
