package com.retailstreet.mobilepos.Model;

public class StockMasterSync {

        private  String STOCKID;
        private String STORE_GUID;
        private  String ITEM_GUID;
        private String QTY;
        private  String SALE_UOM_GUID;
        private String BATCHNO;
        private  String BARCODE;
        private String COST_PRICE;
        private String MRP;
        private String SALEPRICE;
        private String INTERNETPRICE;
        private String MIN_QUANTITY;
        private String MAX_QUANTITY;
        private String WHOLESALEPRICE;
        private  String SPECIALPRICE;
        private   String EXPIRYDATE;
        private  String ITEM_NAME;
        private String ITEM_CODE;
        private String CREATEDBYGUID;
        private String UPDATEDBYGUID;
        private String CREATEDON;
        private String UPDATEDON;
        private String SALESDISCOUNTBYPERCENTAGE;
        private String SALESDISCOUNTBYAMOUNT;
        private String GRN_MASTER_GUID;
        private String MASTER_VENDOR_GUID;
        private  String GRN_DETAIL_GUID;
        private String ORG_GUID;
        private String CATEGORY_GUID;
        private String SUBCATEGORY_GUID;
        private String PUR_UOM_GUID;

    public String getPUR_UOM_GUID() {
        return PUR_UOM_GUID;
    }

    public void setPUR_UOM_GUID(String PUR_UOM_GUID) {
        this.PUR_UOM_GUID = PUR_UOM_GUID;
    }

    public String getSUBCATEGORY_GUID() {
        return SUBCATEGORY_GUID;
    }

    public void setSUBCATEGORY_GUID(String SUBCATEGORY_GUID) {
        this.SUBCATEGORY_GUID = SUBCATEGORY_GUID;
    }

    public String getCATEGORY_GUID() {
        return CATEGORY_GUID;
    }

    public void setCATEGORY_GUID(String CATEGORY_GUID) {
        this.CATEGORY_GUID = CATEGORY_GUID;
    }

    public String getGRN_DETAIL_GUID() {
            return GRN_DETAIL_GUID;
        }

        public void setGRN_DETAIL_GUID(String GRN_DETAIL_GUID) {
            this.GRN_DETAIL_GUID = GRN_DETAIL_GUID;
        }

        public StockMasterSync() {
        }

        public StockMasterSync(String STOCKID, String STORE_GUID, String ITEM_GUID, String QTY, String SALE_UOM_GUID, String UOM, String BATCHNO, String BARCODE, String COST_PRICE, String MRP, String SALEPRICE, String INTERNETPRICE, String MIN_QUANTITY, String MAX_QUANTITY, String WHOLESALEPRICE, String SPECIALPRICE, String GENERIC_NAME, String EXTERNALPRODUCTID, String GST, String SGST, String CGST, String IGST, String CESS1, String CESS2, String EXPIRYDATE, String ITEM_NAME, String ITEM_CODE, String CREATEDBYGUID, String UPDATEDBYGUID, String CREATEDON, String UPDATEDON, String SALESDISCOUNTBYPERCENTAGE, String SALESDISCOUNTBYAMOUNT, String GRN_MASTER_GUID, String GRNNO, String MASTER_VENDOR_GUID, String VENDOR_NAME, String issynceed, String grndetail_id) {
            this.STOCKID = STOCKID;
            this.STORE_GUID = STORE_GUID;
            this.ITEM_GUID = ITEM_GUID;
            this.QTY = QTY;
            this.SALE_UOM_GUID = SALE_UOM_GUID;
            this.BATCHNO = BATCHNO;
            this.BARCODE = BARCODE;
            this.COST_PRICE = COST_PRICE;
            this.MRP = MRP;
            this.SALEPRICE = SALEPRICE;
            this.INTERNETPRICE = INTERNETPRICE;
            this.MIN_QUANTITY = MIN_QUANTITY;
            this.MAX_QUANTITY = MAX_QUANTITY;
            this.WHOLESALEPRICE = WHOLESALEPRICE;
            this.SPECIALPRICE = SPECIALPRICE;
            this.EXPIRYDATE = EXPIRYDATE;
            this.ITEM_NAME = ITEM_NAME;
            this.ITEM_CODE = ITEM_CODE;
            this.CREATEDBYGUID = CREATEDBYGUID;
            this.UPDATEDBYGUID = UPDATEDBYGUID;
            this.CREATEDON = CREATEDON;
            this.UPDATEDON = UPDATEDON;
            this.SALESDISCOUNTBYPERCENTAGE = SALESDISCOUNTBYPERCENTAGE;
            this.SALESDISCOUNTBYAMOUNT = SALESDISCOUNTBYAMOUNT;
            this.GRN_MASTER_GUID = GRN_MASTER_GUID;
            this.MASTER_VENDOR_GUID = MASTER_VENDOR_GUID;
            this.GRN_DETAIL_GUID =grndetail_id;
        }

        public String getORG_GUID() {
            return ORG_GUID;
        }

        public void setORG_GUID(String ORG_GUID) {
            this.ORG_GUID = ORG_GUID;
        }
        public String getSTOCKID() {
            return STOCKID;
        }

        public void setSTOCKID(String STOCKID) {
            this.STOCKID = STOCKID;
        }

        public String getSTORE_GUID() {
            return STORE_GUID;
        }

        public void setSTORE_GUID(String STORE_GUID) {
            this.STORE_GUID = STORE_GUID;
        }

        public String getITEM_GUID() {
            return ITEM_GUID;
        }

        public void setITEM_GUID(String ITEM_GUID) {
            this.ITEM_GUID = ITEM_GUID;
        }

        public String getQTY() {
            return QTY;
        }

        public void setQTY(String QTY) {
            this.QTY = QTY;
        }

        public String getSALE_UOM_GUID() {
            return SALE_UOM_GUID;
        }

        public void setSALE_UOM_GUID(String SALE_UOM_GUID) {
            this.SALE_UOM_GUID = SALE_UOM_GUID;
        }

        public String getBATCHNO() {
            return BATCHNO;
        }

        public void setBATCHNO(String BATCHNO) {
            this.BATCHNO = BATCHNO;
        }

        public String getBARCODE() {
            return BARCODE;
        }

        public void setBARCODE(String BARCODE) {
            this.BARCODE = BARCODE;
        }

        public String getCOST_PRICE() {
            return COST_PRICE;
        }

        public void setCOST_PRICE(String COST_PRICE) {
            this.COST_PRICE = COST_PRICE;
        }

        public String getMRP() {
            return MRP;
        }

        public void setMRP(String MRP) {
            this.MRP = MRP;
        }

        public String getSALEPRICE() {
            return SALEPRICE;
        }

        public void setSALEPRICE(String SALEPRICE) {
            this.SALEPRICE = SALEPRICE;
        }

        public String getINTERNETPRICE() {
            return INTERNETPRICE;
        }

        public void setINTERNETPRICE(String INTERNETPRICE) {
            this.INTERNETPRICE = INTERNETPRICE;
        }

        public String getMIN_QUANTITY() {
            return MIN_QUANTITY;
        }

        public void setMIN_QUANTITY(String MIN_QUANTITY) {
            this.MIN_QUANTITY = MIN_QUANTITY;
        }

        public String getMAX_QUANTITY() {
            return MAX_QUANTITY;
        }

        public void setMAX_QUANTITY(String MAX_QUANTITY) {
            this.MAX_QUANTITY = MAX_QUANTITY;
        }

        public String getWHOLESALEPRICE() {
            return WHOLESALEPRICE;
        }

        public void setWHOLESALEPRICE(String WHOLESALEPRICE) {
            this.WHOLESALEPRICE = WHOLESALEPRICE;
        }

        public String getSPECIALPRICE() {
            return SPECIALPRICE;
        }

        public void setSPECIALPRICE(String SPECIALPRICE) {
            this.SPECIALPRICE = SPECIALPRICE;
        }


        public String getEXPIRYDATE() {
            return EXPIRYDATE;
        }

        public void setEXPIRYDATE(String EXPIRYDATE) {
            this.EXPIRYDATE = EXPIRYDATE;
        }

        public String getITEM_NAME() {
            return ITEM_NAME;
        }

        public void setITEM_NAME(String ITEM_NAME) {
            this.ITEM_NAME = ITEM_NAME;
        }

        public String getITEM_CODE() {
            return ITEM_CODE;
        }

        public void setITEM_CODE(String ITEM_CODE) {
            this.ITEM_CODE = ITEM_CODE;
        }

        public String getCREATEDBYGUID() {
            return CREATEDBYGUID;
        }

        public void setCREATEDBYGUID(String CREATEDBYGUID) {
            this.CREATEDBYGUID = CREATEDBYGUID;
        }

        public String getUPDATEDBYGUID() {
            return UPDATEDBYGUID;
        }

        public void setUPDATEDBYGUID(String UPDATEDBYGUID) {
            this.UPDATEDBYGUID = UPDATEDBYGUID;
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

        public String getSALESDISCOUNTBYPERCENTAGE() {
            return SALESDISCOUNTBYPERCENTAGE;
        }

        public void setSALESDISCOUNTBYPERCENTAGE(String SALESDISCOUNTBYPERCENTAGE) {
            this.SALESDISCOUNTBYPERCENTAGE = SALESDISCOUNTBYPERCENTAGE;
        }

        public String getSALESDISCOUNTBYAMOUNT() {
            return SALESDISCOUNTBYAMOUNT;
        }

        public void setSALESDISCOUNTBYAMOUNT(String SALESDISCOUNTBYAMOUNT) {
            this.SALESDISCOUNTBYAMOUNT = SALESDISCOUNTBYAMOUNT;
        }

        public String getGRN_MASTER_GUID() {
            return GRN_MASTER_GUID;
        }

        public void setGRN_MASTER_GUID(String GRN_MASTER_GUID) {
            this.GRN_MASTER_GUID = GRN_MASTER_GUID;
        }


        public String getMASTER_VENDOR_GUID() {
            return MASTER_VENDOR_GUID;
        }

        public void setMASTER_VENDOR_GUID(String MASTER_VENDOR_GUID) {
            this.MASTER_VENDOR_GUID = MASTER_VENDOR_GUID;
        }

        @Override
        public String toString() {
            return STOCKID;
        }
    }

