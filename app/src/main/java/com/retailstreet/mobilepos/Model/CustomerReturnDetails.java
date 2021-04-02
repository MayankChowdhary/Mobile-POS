package com.retailstreet.mobilepos.Model;

public class CustomerReturnDetails {
    String CUSTOMER_RETURNS_DETAILID;
    String CUSTOMER_RETURNS_MASTER_ID;
    String MASTER_PRODUCT_ITEM_ID;
    String BATCHNO;
    String RETURNQUANTITY;
    String EXPIRYDATE;
    String CUSTOMER_RETURN_DETAIL_STATUS;


    public CustomerReturnDetails() {
    }

    public CustomerReturnDetails(String CUSTOMER_RETURNS_DETAILID, String CUSTOMER_RETURNS_MASTER_ID, String MASTER_PRODUCT_ITEM_ID, String BATCHNO, String RETURNQUANTITY, String EXPIRYDATE, String CUSTOMER_RETURN_DETAIL_STATUS) {
        this.CUSTOMER_RETURNS_DETAILID = CUSTOMER_RETURNS_DETAILID;
        this.CUSTOMER_RETURNS_MASTER_ID = CUSTOMER_RETURNS_MASTER_ID;
        this.MASTER_PRODUCT_ITEM_ID = MASTER_PRODUCT_ITEM_ID;
        this.BATCHNO = BATCHNO;
        this.RETURNQUANTITY = RETURNQUANTITY;
        this.EXPIRYDATE = EXPIRYDATE;
        this.CUSTOMER_RETURN_DETAIL_STATUS = CUSTOMER_RETURN_DETAIL_STATUS;
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
