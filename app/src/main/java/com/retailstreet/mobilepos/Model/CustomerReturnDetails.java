package com.retailstreet.mobilepos.Model;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

public class CustomerReturnDetails {
    String CUSTOMER_RETURNS_DETAILID;
    String CUSTOMER_RETURNS_MASTER_ID;
    String MASTER_PRODUCT_ITEM_ID;
    String BATCHNO;
    String RETURNQUANTITY;
    String EXPIRYDATE;
    String CUSTOMER_RETURN_DETAIL_STATUS;
    String MASTER_TERMINAL_ID;

    public CustomerReturnDetails() {

    }

    public CustomerReturnDetails(String CUSTOMER_RETURNS_DETAILID, String CUSTOMER_RETURNS_MASTER_ID, String MASTER_PRODUCT_ITEM_ID, String BATCHNO, String RETURNQUANTITY, String EXPIRYDATE, String CUSTOMER_RETURN_DETAIL_STATUS, String MASTER_TERMINAL_ID) {
        this.CUSTOMER_RETURNS_DETAILID = CUSTOMER_RETURNS_DETAILID;
        this.CUSTOMER_RETURNS_MASTER_ID = CUSTOMER_RETURNS_MASTER_ID;
        this.MASTER_PRODUCT_ITEM_ID = MASTER_PRODUCT_ITEM_ID;
        this.BATCHNO = BATCHNO;
        this.RETURNQUANTITY = RETURNQUANTITY;
        this.EXPIRYDATE = EXPIRYDATE;
        this.CUSTOMER_RETURN_DETAIL_STATUS = CUSTOMER_RETURN_DETAIL_STATUS;
        this.MASTER_TERMINAL_ID = MASTER_TERMINAL_ID;
    }

    public String getMASTER_TERMINAL_ID() {
        return MASTER_TERMINAL_ID;
    }

    public void setMASTER_TERMINAL_ID(String MASTER_TERMINAL_ID) {
        this.MASTER_TERMINAL_ID = MASTER_TERMINAL_ID;
    }

    public String getCUSTOMER_RETURNS_DETAILID() {
        return CUSTOMER_RETURNS_DETAILID;
    }

    public void setCUSTOMER_RETURNS_DETAILID(String CUSTOMER_RETURNS_DETAILID) {
        this.CUSTOMER_RETURNS_DETAILID = CUSTOMER_RETURNS_DETAILID;
    }

    public String getCUSTOMER_RETURNS_MASTER_ID() {
        return CUSTOMER_RETURNS_MASTER_ID;
    }

    public void setCUSTOMER_RETURNS_MASTER_ID(String CUSTOMER_RETURNS_MASTER_ID) {
        this.CUSTOMER_RETURNS_MASTER_ID = CUSTOMER_RETURNS_MASTER_ID;
    }

    public String getMASTER_PRODUCT_ITEM_ID() {
        return MASTER_PRODUCT_ITEM_ID;
    }

    public void setMASTER_PRODUCT_ITEM_ID(String MASTER_PRODUCT_ITEM_ID) {
        this.MASTER_PRODUCT_ITEM_ID = MASTER_PRODUCT_ITEM_ID;
    }

    public String getBATCHNO() {
        return BATCHNO;
    }

    public void setBATCHNO(String BATCHNO) {
        this.BATCHNO = BATCHNO;
    }

    public String getRETURNQUANTITY() {
        return RETURNQUANTITY;
    }

    public void setRETURNQUANTITY(String RETURNQUANTITY) {
        this.RETURNQUANTITY = RETURNQUANTITY;
    }

    public String getEXPIRYDATE() {
        return EXPIRYDATE;
    }

    public void setEXPIRYDATE(String EXPIRYDATE) {
        this.EXPIRYDATE = EXPIRYDATE;
    }

    public String getCUSTOMER_RETURN_DETAIL_STATUS() {
        return CUSTOMER_RETURN_DETAIL_STATUS;
    }

    public void setCUSTOMER_RETURN_DETAIL_STATUS(String CUSTOMER_RETURN_DETAIL_STATUS) {
        this.CUSTOMER_RETURN_DETAIL_STATUS = CUSTOMER_RETURN_DETAIL_STATUS;
    }
}
