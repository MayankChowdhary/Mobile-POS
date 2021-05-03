package com.retailstreet.mobilepos.Model;

public class StockRegisterSync {

    String REGISTERGUID;
    String MASTERORG_GUID;
    String STORE_GUID;
    String VENDOR_GUID;
    String LINETYPE;
    String TRANSACTIONTYPE;
    String TRANSACTIONNUMBER;
    String TRANSACTIONDATE;
    String ITEM_GUID;
    String UOM_GUID;
    String QUANTITY;
    String BATCHNO;
    String Barcode;
    String SALESPRICE;
    String WHOLESALEPRICE;
    String INTERNETPRICE;
    String SPECIALPRICE;



    public StockRegisterSync() {

    }

    public StockRegisterSync(String REGISTERGUID, String MASTERORG_GUID, String STORE_GUID, String VENDOR_GUID, String LINETYPE, String TRANSACTIONTYPE, String TRANSACTIONNUMBER, String TRANSACTIONDATE, String ITEM_GUID, String UOM_GUID, String QUANTITY, String BATCHNO, String Barcode, String SALESPRICE, String WHOLESALEPRICE, String INTERNETPRICE, String SPECIALPRICE, String ISSYNCED) {
        this.REGISTERGUID = REGISTERGUID;
        this.MASTERORG_GUID = MASTERORG_GUID;
        this.STORE_GUID = STORE_GUID;
        this.VENDOR_GUID = VENDOR_GUID;
        this.LINETYPE = LINETYPE;
        this.TRANSACTIONTYPE = TRANSACTIONTYPE;
        this.TRANSACTIONNUMBER = TRANSACTIONNUMBER;
        this.TRANSACTIONDATE = TRANSACTIONDATE;
        this.ITEM_GUID = ITEM_GUID;
        this.UOM_GUID = UOM_GUID;
        this.QUANTITY = QUANTITY;
        this.BATCHNO = BATCHNO;
        this.Barcode = Barcode;
        this.SALESPRICE = SALESPRICE;
        this.WHOLESALEPRICE = WHOLESALEPRICE;
        this.INTERNETPRICE = INTERNETPRICE;
        this.SPECIALPRICE = SPECIALPRICE;
    }

    public String getREGISTERGUID() {
        return REGISTERGUID;
    }

    public void setREGISTERGUID(String REGISTERGUID) {
        this.REGISTERGUID = REGISTERGUID;
    }

    public String getMASTERORG_GUID() {
        return MASTERORG_GUID;
    }

    public void setMASTERORG_GUID(String MASTERORG_GUID) {
        this.MASTERORG_GUID = MASTERORG_GUID;
    }

    public String getSTORE_GUID() {
        return STORE_GUID;
    }

    public void setSTORE_GUID(String STORE_GUID) {
        this.STORE_GUID = STORE_GUID;
    }

    public String getVENDOR_GUID() {
        return VENDOR_GUID;
    }

    public void setVENDOR_GUID(String VENDOR_GUID) {
        this.VENDOR_GUID = VENDOR_GUID;
    }

    public String getLINETYPE() {
        return LINETYPE;
    }

    public void setLINETYPE(String LINETYPE) {
        this.LINETYPE = LINETYPE;
    }

    public String getTRANSACTIONTYPE() {
        return TRANSACTIONTYPE;
    }

    public void setTRANSACTIONTYPE(String TRANSACTIONTYPE) {
        this.TRANSACTIONTYPE = TRANSACTIONTYPE;
    }

    public String getTRANSACTIONNUMBER() {
        return TRANSACTIONNUMBER;
    }

    public void setTRANSACTIONNUMBER(String TRANSACTIONNUMBER) {
        this.TRANSACTIONNUMBER = TRANSACTIONNUMBER;
    }

    public String getTRANSACTIONDATE() {
        return TRANSACTIONDATE;
    }

    public void setTRANSACTIONDATE(String TRANSACTIONDATE) {
        this.TRANSACTIONDATE = TRANSACTIONDATE;
    }

    public String getITEM_GUID() {
        return ITEM_GUID;
    }

    public void setITEM_GUID(String ITEM_GUID) {
        this.ITEM_GUID = ITEM_GUID;
    }

    public String getUOM_GUID() {
        return UOM_GUID;
    }

    public void setUOM_GUID(String UOM_GUID) {
        this.UOM_GUID = UOM_GUID;
    }

    public String getQUANTITY() {
        return QUANTITY;
    }

    public void setQUANTITY(String QUANTITY) {
        this.QUANTITY = QUANTITY;
    }

    public String getBATCHNO() {
        return BATCHNO;
    }

    public void setBATCHNO(String BATCHNO) {
        this.BATCHNO = BATCHNO;
    }

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        this.Barcode = barcode;
    }

    public String getSALESPRICE() {
        return SALESPRICE;
    }

    public void setSALESPRICE(String SALESPRICE) {
        this.SALESPRICE = SALESPRICE;
    }

    public String getWHOLESALEPRICE() {
        return WHOLESALEPRICE;
    }

    public void setWHOLESALEPRICE(String WHOLESALEPRICE) {
        this.WHOLESALEPRICE = WHOLESALEPRICE;
    }

    public String getINTERNETPRICE() {
        return INTERNETPRICE;
    }

    public void setINTERNETPRICE(String INTERNETPRICE) {
        this.INTERNETPRICE = INTERNETPRICE;
    }

    public String getSPECIALPRICE() {
        return SPECIALPRICE;
    }

    public void setSPECIALPRICE(String SPECIALPRICE) {
        this.SPECIALPRICE = SPECIALPRICE;
    }


}

