package com.retailstreet.mobilepos.Model;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */
public class VendorMasterReturn {

    String VENDOR_RETURN_MASTERID;
    String VENDOR_RETURNGUID;
    String VENDOR_GUID;
    String STORE_GUID;
    String INVOICENO;
    String INVOICEDATE;
    String RETURN_DATE;
    String REASON;
    String GRNNO;
    String GRNDATE;
    String CREATEDBY;
    String UPDATEDBY;
    String CREATEDON;
    String UPDATEDON;
    String ISSYNCED;


    public VendorMasterReturn() {
    }

    public VendorMasterReturn(String VENDOR_RETURN_MASTERID, String VENDOR_RETURNGUID, String VENDOR_GUID, String STORE_GUID, String INVOICENO, String INVOICEDATE, String RETURN_DATE, String REASON, String GRNNO, String GRNDATE, String CREATEDBY, String UPDATEDBY, String CREATEDON, String UPDATEDON, String ISSYNCED) {
        this.VENDOR_RETURN_MASTERID = VENDOR_RETURN_MASTERID;
        this.VENDOR_RETURNGUID = VENDOR_RETURNGUID;
        this.VENDOR_GUID = VENDOR_GUID;
        this.STORE_GUID = STORE_GUID;
        this.INVOICENO = INVOICENO;
        this.INVOICEDATE = INVOICEDATE;
        this.RETURN_DATE = RETURN_DATE;
        this.REASON = REASON;
        this.GRNNO = GRNNO;
        this.GRNDATE = GRNDATE;
        this.CREATEDBY = CREATEDBY;
        this.UPDATEDBY = UPDATEDBY;
        this.CREATEDON = CREATEDON;
        this.UPDATEDON = UPDATEDON;
        this.ISSYNCED = ISSYNCED;
    }

    public String getVENDOR_RETURN_MASTERID() {
        return VENDOR_RETURN_MASTERID;
    }

    public void setVENDOR_RETURN_MASTERID(String VENDOR_RETURN_MASTERID) {
        this.VENDOR_RETURN_MASTERID = VENDOR_RETURN_MASTERID;
    }

    public String getVENDOR_RETURNGUID() {
        return VENDOR_RETURNGUID;
    }

    public void setVENDOR_RETURNGUID(String VENDOR_RETURNGUID) {
        this.VENDOR_RETURNGUID = VENDOR_RETURNGUID;
    }

    public String getVENDOR_GUID() {
        return VENDOR_GUID;
    }

    public void setVENDOR_GUID(String VENDOR_GUID) {
        this.VENDOR_GUID = VENDOR_GUID;
    }

    public String getSTORE_GUID() {
        return STORE_GUID;
    }

    public void setSTORE_GUID(String STORE_GUID) {
        this.STORE_GUID = STORE_GUID;
    }

    public String getINVOICENO() {
        return INVOICENO;
    }

    public void setINVOICENO(String INVOICENO) {
        this.INVOICENO = INVOICENO;
    }

    public String getINVOICEDATE() {
        return INVOICEDATE;
    }

    public void setINVOICEDATE(String INVOICEDATE) {
        this.INVOICEDATE = INVOICEDATE;
    }

    public String getRETURN_DATE() {
        return RETURN_DATE;
    }

    public void setRETURN_DATE(String RETURN_DATE) {
        this.RETURN_DATE = RETURN_DATE;
    }

    public String getREASON() {
        return REASON;
    }

    public void setREASON(String REASON) {
        this.REASON = REASON;
    }

    public String getGRNNO() {
        return GRNNO;
    }

    public void setGRNNO(String GRNNO) {
        this.GRNNO = GRNNO;
    }

    public String getGRNDATE() {
        return GRNDATE;
    }

    public void setGRNDATE(String GRNDATE) {
        this.GRNDATE = GRNDATE;
    }

    public String getCREATEDBY() {
        return CREATEDBY;
    }

    public void setCREATEDBY(String CREATEDBY) {
        this.CREATEDBY = CREATEDBY;
    }

    public String getUPDATEDBY() {
        return UPDATEDBY;
    }

    public void setUPDATEDBY(String UPDATEDBY) {
        this.UPDATEDBY = UPDATEDBY;
    }

    public String getCREATEDON() {
        return CREATEDON;
    }

    public void setCREATEDON(String CREATEDON) {
        this.CREATEDON = CREATEDON;
    }

    public String getUPDATEDON() {
        return UPDATEDON;
    }

    public void setUPDATEDON(String UPDATEDON) {
        this.UPDATEDON = UPDATEDON;
    }

    public String getISSYNCED() {
        return ISSYNCED;
    }

    public void setISSYNCED(String ISSYNCED) {
        this.ISSYNCED = ISSYNCED;
    }
}
