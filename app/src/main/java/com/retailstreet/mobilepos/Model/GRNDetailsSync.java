package com.retailstreet.mobilepos.Model;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */


public class GRNDetailsSync<GRN_DETAIL_STATUS> {

    String GRNID;
    String GRN_QTY;
    String BATCHNO;
    String EXP_DATE;
    String PUR_PRICE;
    String TAX_AMOUNT;
    String GRN_VALUE;
    String MRP;
    String ISFREEGOODS;
    String FREE_QUANTITY;
    String PURCHASEDISCOUNTPERCENTAGE;
    String PURCHASEDISCOUNTBYAMOUNT;
    String GRN_GUID;
    String ITEM_GUID;
    String STORE_GUID;
    String UOM_GUID;
    String GRNNO;
    String GRNDETAILGUID;
    String GRN_DETAIL_STATUS;
    String CAT_GUID;



    public GRNDetailsSync() {
    }

    public GRNDetailsSync(String GRNID, String GRN_QTY, String BATCHNO, String EXP_DATE, String PUR_PRICE, String TAX_AMOUNT, String GRN_VALUE, String MRP, String ISFREEGOODS, String FREE_QUANTITY, String PURCHASEDISCOUNTPERCENTAGE, String PURCHASEDISCOUNTBYAMOUNT, String GRN_GUID, String ITEM_GUID, String STORE_GUID, String UOM_GUID, String GRNNO, String GRNDETAILGUID, String GRN_DETAIL_STATUS, String CAT_GUID) {
        this.GRNID = GRNID;
        this.GRN_QTY = GRN_QTY;
        this.BATCHNO = BATCHNO;
        this.EXP_DATE = EXP_DATE;
        this.PUR_PRICE = PUR_PRICE;
        this.TAX_AMOUNT = TAX_AMOUNT;
        this.GRN_VALUE = GRN_VALUE;
        this.MRP = MRP;
        this.ISFREEGOODS = ISFREEGOODS;
        this.FREE_QUANTITY = FREE_QUANTITY;
        this.PURCHASEDISCOUNTPERCENTAGE = PURCHASEDISCOUNTPERCENTAGE;
        this.PURCHASEDISCOUNTBYAMOUNT = PURCHASEDISCOUNTBYAMOUNT;
        this.GRN_GUID = GRN_GUID;
        this.ITEM_GUID = ITEM_GUID;
        this.STORE_GUID = STORE_GUID;
        this.UOM_GUID = UOM_GUID;
        this.GRNNO = GRNNO;
        this.GRNDETAILGUID = GRNDETAILGUID;
        this.GRN_DETAIL_STATUS = GRN_DETAIL_STATUS;
        this.CAT_GUID = CAT_GUID;
    }

    public String getGRNID() {
        return GRNID;
    }

    public void setGRNID(String GRNID) {
        this.GRNID = GRNID;
    }

    public String getGRN_QTY() {
        return GRN_QTY;
    }

    public void setGRN_QTY(String GRN_QTY) {
        this.GRN_QTY = GRN_QTY;
    }

    public String getBATCHNO() {
        return BATCHNO;
    }

    public void setBATCHNO(String BATCHNO) {
        this.BATCHNO = BATCHNO;
    }

    public String getEXP_DATE() {
        return EXP_DATE;
    }

    public void setEXP_DATE(String EXP_DATE) {
        this.EXP_DATE = EXP_DATE;
    }

    public String getPUR_PRICE() {
        return PUR_PRICE;
    }

    public void setPUR_PRICE(String PUR_PRICE) {
        this.PUR_PRICE = PUR_PRICE;
    }

    public String getTAX_AMOUNT() {
        return TAX_AMOUNT;
    }

    public void setTAX_AMOUNT(String TAX_AMOUNT) {
        this.TAX_AMOUNT = TAX_AMOUNT;
    }

    public String getGRN_VALUE() {
        return GRN_VALUE;
    }

    public void setGRN_VALUE(String GRN_VALUE) {
        this.GRN_VALUE = GRN_VALUE;
    }

    public String getMRP() {
        return MRP;
    }

    public void setMRP(String MRP) {
        this.MRP = MRP;
    }

    public String getISFREEGOODS() {
        return ISFREEGOODS;
    }

    public void setISFREEGOODS(String ISFREEGOODS) {
        this.ISFREEGOODS = ISFREEGOODS;
    }

    public String getFREE_QUANTITY() {
        return FREE_QUANTITY;
    }

    public void setFREE_QUANTITY(String FREE_QUANTITY) {
        this.FREE_QUANTITY = FREE_QUANTITY;
    }

    public String getPURCHASEDISCOUNTPERCENTAGE() {
        return PURCHASEDISCOUNTPERCENTAGE;
    }

    public void setPURCHASEDISCOUNTPERCENTAGE(String PURCHASEDISCOUNTPERCENTAGE) {
        this.PURCHASEDISCOUNTPERCENTAGE = PURCHASEDISCOUNTPERCENTAGE;
    }

    public String getPURCHASEDISCOUNTBYAMOUNT() {
        return PURCHASEDISCOUNTBYAMOUNT;
    }

    public void setPURCHASEDISCOUNTBYAMOUNT(String PURCHASEDISCOUNTBYAMOUNT) {
        this.PURCHASEDISCOUNTBYAMOUNT = PURCHASEDISCOUNTBYAMOUNT;
    }

    public String getGRN_GUID() {
        return GRN_GUID;
    }

    public void setGRN_GUID(String GRN_GUID) {
        this.GRN_GUID = GRN_GUID;
    }

    public String getITEM_GUID() {
        return ITEM_GUID;
    }

    public void setITEM_GUID(String ITEM_GUID) {
        this.ITEM_GUID = ITEM_GUID;
    }

    public String getSTORE_GUID() {
        return STORE_GUID;
    }

    public void setSTORE_GUID(String STORE_GUID) {
        this.STORE_GUID = STORE_GUID;
    }

    public String getUOM_GUID() {
        return UOM_GUID;
    }

    public void setUOM_GUID(String UOM_GUID) {
        this.UOM_GUID = UOM_GUID;
    }

    public String getGRNDETAILGUID() {
        return GRNDETAILGUID;
    }

    public void setGRNDETAILGUID(String GRNDETAILGUID) {
        this.GRNDETAILGUID = GRNDETAILGUID;
    }

    public String getGRNNO() {
        return GRNNO;
    }

    public void setGRNNO(String GRNNO) {
        this.GRNNO = GRNNO;
    }

    public String getGRN_DETAIL_STATUS() {
        return GRN_DETAIL_STATUS;
    }

    public void setGRN_DETAIL_STATUS(String GRN_DETAIL_STATUS) {
        this.GRN_DETAIL_STATUS = GRN_DETAIL_STATUS;
    }

    public String getCAT_GUID() {
        return CAT_GUID;
    }

    public void setCAT_GUID(String CAT_GUID) {
        this.CAT_GUID = CAT_GUID;
    }
}
