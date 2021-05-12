package com.retailstreet.mobilepos.Model;

/**
 * Created by Mayank Choudhary on 11-05-2021.
 * mayankchoudhary00@gmail.com
 */
public class VendorReturnMasterSync {
    String VENDOR_RETURN_MASTERID;
    String VENDOR_RETURNGUID;
    String MASTER_STORE_GUID;
    String MASTER_VENDOR_GUID;
    String REASON;
    String RETURN_DATE;
    String GRNNO;
    String GRNDATE;
    String INVOICENO;
    String INVOICEDATE;
    String VENDOR_RETURN_STATUS;
    String CREATEDBYGUID;
    String CREATEDON;
    String UPDATEDBYGUID;
    String UPDATEDON;

    public VendorReturnMasterSync() {

    }

    public VendorReturnMasterSync(String VENDOR_RETURN_MASTERID, String VENDOR_RETURNGUID, String MASTER_STORE_GUID, String MASTER_VENDOR_GUID, String REASON, String RETURN_DATE, String GRNNO, String GRNDATE, String INVOICENO, String INVOICEDATE, String VENDOR_RETURN_STATUS, String CREATEDBYGUID, String CREATEDON, String UPDATEDBYGUID, String UPDATEDON) {
        this.VENDOR_RETURN_MASTERID = VENDOR_RETURN_MASTERID;
        this.VENDOR_RETURNGUID = VENDOR_RETURNGUID;
        this.MASTER_STORE_GUID = MASTER_STORE_GUID;
        this.MASTER_VENDOR_GUID = MASTER_VENDOR_GUID;
        this.REASON = REASON;
        this.RETURN_DATE = RETURN_DATE;
        this.GRNNO = GRNNO;
        this.GRNDATE = GRNDATE;
        this.INVOICENO = INVOICENO;
        this.INVOICEDATE = INVOICEDATE;
        this.VENDOR_RETURN_STATUS = VENDOR_RETURN_STATUS;
        this.CREATEDBYGUID = CREATEDBYGUID;
        this.CREATEDON = CREATEDON;
        this.UPDATEDBYGUID = UPDATEDBYGUID;
        this.UPDATEDON = UPDATEDON;
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

    public String getMASTER_STORE_GUID() {
        return MASTER_STORE_GUID;
    }

    public void setMASTER_STORE_GUID(String MASTER_STORE_GUID) {
        this.MASTER_STORE_GUID = MASTER_STORE_GUID;
    }

    public String getMASTER_VENDOR_GUID() {
        return MASTER_VENDOR_GUID;
    }

    public void setMASTER_VENDOR_GUID(String MASTER_VENDOR_GUID) {
        this.MASTER_VENDOR_GUID = MASTER_VENDOR_GUID;
    }

    public String getREASON() {
        return REASON;
    }

    public void setREASON(String REASON) {
        this.REASON = REASON;
    }

    public String getRETURN_DATE() {
        return RETURN_DATE;
    }

    public void setRETURN_DATE(String RETURN_DATE) {
        this.RETURN_DATE = RETURN_DATE;
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

    public String getVENDOR_RETURN_STATUS() {
        return VENDOR_RETURN_STATUS;
    }

    public void setVENDOR_RETURN_STATUS(String VENDOR_RETURN_STATUS) {
        this.VENDOR_RETURN_STATUS = VENDOR_RETURN_STATUS;
    }

    public String getCREATEDBYGUID() {
        return CREATEDBYGUID;
    }

    public void setCREATEDBYGUID(String CREATEDBYGUID) {
        this.CREATEDBYGUID = CREATEDBYGUID;
    }

    public String getCREATEDON() {
        return CREATEDON;
    }

    public void setCREATEDON(String CREATEDON) {
        this.CREATEDON = CREATEDON;
    }

    public String getUPDATEDBYGUID() {
        return UPDATEDBYGUID;
    }

    public void setUPDATEDBYGUID(String UPDATEDBYGUID) {
        this.UPDATEDBYGUID = UPDATEDBYGUID;
    }

    public String getUPDATEDON() {
        return UPDATEDON;
    }

    public void setUPDATEDON(String UPDATEDON) {
        this.UPDATEDON = UPDATEDON;
    }
}
