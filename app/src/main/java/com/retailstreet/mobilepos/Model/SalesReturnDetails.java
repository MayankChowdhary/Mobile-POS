package com.retailstreet.mobilepos.Model;

public class SalesReturnDetails {
    String ID;
    String TRI_ID;
    String STORE_ID;
    String PROD_NM;
    String BATCH_NO;
    String EXP_DATE;
    String S_PRICE;
    String QTY;
    String MRP;
    String AMOUNT;
    String UOM;
    String TOTAL;
    String STATUS;
    String CARD_NO;
    String ITEM_ID;
    String DISC_PERC;
    String DISC_VAL;
    String TAX_PER;
    String TAX_VALUE;
    String TAX_PER1;
    String TAX_VALUE1;
    String TAX_PER2;
    String TAX_VALUE2;
    String TAX_PER3;
    String S_FLAG;
    String FLAG;
    String LAST_MODIFIED;
    String SALE_DATE;
    String POS_USER;
    String M_FLAG;
    String EX_TRI_ID;
    String ORDER_TYPE;
    String PICK_UP_LOCATION;
    String DISCOUNT_PERCENT;
    String LINE_ITEM_DISC;
    String LINE_DISC;
    String SLAVE_FLAG;
    String BARCODE;

    public SalesReturnDetails() {
    }

    public SalesReturnDetails(String ID, String TRI_ID, String STORE_ID, String PROD_NM, String BATCH_NO, String EXP_DATE, String s_PRICE, String QTY, String MRP, String AMOUNT, String UOM, String TOTAL, String STATUS, String CARD_NO, String ITEM_ID, String DISC_PERC, String DISC_VAL, String TAX_PER, String TAX_VALUE, String TAX_PER1, String TAX_VALUE1, String TAX_PER2, String TAX_VALUE2, String TAX_PER3, String s_FLAG, String FLAG, String LAST_MODIFIED, String SALE_DATE, String POS_USER, String m_FLAG, String EX_TRI_ID, String ORDER_TYPE, String PICK_UP_LOCATION, String DISCOUNT_PERCENT, String LINE_ITEM_DISC, String LINE_DISC, String SLAVE_FLAG, String BARCODE) {
        this.ID = ID;
        this.TRI_ID = TRI_ID;
        this.STORE_ID = STORE_ID;
        this.PROD_NM = PROD_NM;
        this.BATCH_NO = BATCH_NO;
        this.EXP_DATE = EXP_DATE;
        S_PRICE = s_PRICE;
        this.QTY = QTY;
        this.MRP = MRP;
        this.AMOUNT = AMOUNT;
        this.UOM = UOM;
        this.TOTAL = TOTAL;
        this.STATUS = STATUS;
        this.CARD_NO = CARD_NO;
        this.ITEM_ID = ITEM_ID;
        this.DISC_PERC = DISC_PERC;
        this.DISC_VAL = DISC_VAL;
        this.TAX_PER = TAX_PER;
        this.TAX_VALUE = TAX_VALUE;
        this.TAX_PER1 = TAX_PER1;
        this.TAX_VALUE1 = TAX_VALUE1;
        this.TAX_PER2 = TAX_PER2;
        this.TAX_VALUE2 = TAX_VALUE2;
        this.TAX_PER3 = TAX_PER3;
        S_FLAG = s_FLAG;
        this.FLAG = FLAG;
        this.LAST_MODIFIED = LAST_MODIFIED;
        this.SALE_DATE = SALE_DATE;
        this.POS_USER = POS_USER;
        M_FLAG = m_FLAG;
        this.EX_TRI_ID = EX_TRI_ID;
        this.ORDER_TYPE = ORDER_TYPE;
        this.PICK_UP_LOCATION = PICK_UP_LOCATION;
        this.DISCOUNT_PERCENT = DISCOUNT_PERCENT;
        this.LINE_ITEM_DISC = LINE_ITEM_DISC;
        this.LINE_DISC = LINE_DISC;
        this.SLAVE_FLAG = SLAVE_FLAG;
        this.BARCODE = BARCODE;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTRI_ID() {
        return TRI_ID;
    }

    public void setTRI_ID(String TRI_ID) {
        this.TRI_ID = TRI_ID;
    }

    public String getSTORE_ID() {
        return STORE_ID;
    }

    public void setSTORE_ID(String STORE_ID) {
        this.STORE_ID = STORE_ID;
    }

    public String getPROD_NM() {
        return PROD_NM;
    }

    public void setPROD_NM(String PROD_NM) {
        this.PROD_NM = PROD_NM;
    }

    public String getBATCH_NO() {
        return BATCH_NO;
    }

    public void setBATCH_NO(String BATCH_NO) {
        this.BATCH_NO = BATCH_NO;
    }

    public String getEXP_DATE() {
        return EXP_DATE;
    }

    public void setEXP_DATE(String EXP_DATE) {
        this.EXP_DATE = EXP_DATE;
    }

    public String getS_PRICE() {
        return S_PRICE;
    }

    public void setS_PRICE(String s_PRICE) {
        S_PRICE = s_PRICE;
    }

    public String getQTY() {
        return QTY;
    }

    public void setQTY(String QTY) {
        this.QTY = QTY;
    }

    public String getMRP() {
        return MRP;
    }

    public void setMRP(String MRP) {
        this.MRP = MRP;
    }

    public String getAMOUNT() {
        return AMOUNT;
    }

    public void setAMOUNT(String AMOUNT) {
        this.AMOUNT = AMOUNT;
    }

    public String getUOM() {
        return UOM;
    }

    public void setUOM(String UOM) {
        this.UOM = UOM;
    }

    public String getTOTAL() {
        return TOTAL;
    }

    public void setTOTAL(String TOTAL) {
        this.TOTAL = TOTAL;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getCARD_NO() {
        return CARD_NO;
    }

    public void setCARD_NO(String CARD_NO) {
        this.CARD_NO = CARD_NO;
    }

    public String getITEM_ID() {
        return ITEM_ID;
    }

    public void setITEM_ID(String ITEM_ID) {
        this.ITEM_ID = ITEM_ID;
    }

    public String getDISC_PERC() {
        return DISC_PERC;
    }

    public void setDISC_PERC(String DISC_PERC) {
        this.DISC_PERC = DISC_PERC;
    }

    public String getDISC_VAL() {
        return DISC_VAL;
    }

    public void setDISC_VAL(String DISC_VAL) {
        this.DISC_VAL = DISC_VAL;
    }

    public String getTAX_PER() {
        return TAX_PER;
    }

    public void setTAX_PER(String TAX_PER) {
        this.TAX_PER = TAX_PER;
    }

    public String getTAX_VALUE() {
        return TAX_VALUE;
    }

    public void setTAX_VALUE(String TAX_VALUE) {
        this.TAX_VALUE = TAX_VALUE;
    }

    public String getTAX_PER1() {
        return TAX_PER1;
    }

    public void setTAX_PER1(String TAX_PER1) {
        this.TAX_PER1 = TAX_PER1;
    }

    public String getTAX_VALUE1() {
        return TAX_VALUE1;
    }

    public void setTAX_VALUE1(String TAX_VALUE1) {
        this.TAX_VALUE1 = TAX_VALUE1;
    }

    public String getTAX_PER2() {
        return TAX_PER2;
    }

    public void setTAX_PER2(String TAX_PER2) {
        this.TAX_PER2 = TAX_PER2;
    }

    public String getTAX_VALUE2() {
        return TAX_VALUE2;
    }

    public void setTAX_VALUE2(String TAX_VALUE2) {
        this.TAX_VALUE2 = TAX_VALUE2;
    }

    public String getTAX_PER3() {
        return TAX_PER3;
    }

    public void setTAX_PER3(String TAX_PER3) {
        this.TAX_PER3 = TAX_PER3;
    }

    public String getS_FLAG() {
        return S_FLAG;
    }

    public void setS_FLAG(String s_FLAG) {
        S_FLAG = s_FLAG;
    }

    public String getFLAG() {
        return FLAG;
    }

    public void setFLAG(String FLAG) {
        this.FLAG = FLAG;
    }

    public String getLAST_MODIFIED() {
        return LAST_MODIFIED;
    }

    public void setLAST_MODIFIED(String LAST_MODIFIED) {
        this.LAST_MODIFIED = LAST_MODIFIED;
    }

    public String getSALE_DATE() {
        return SALE_DATE;
    }

    public void setSALE_DATE(String SALE_DATE) {
        this.SALE_DATE = SALE_DATE;
    }

    public String getPOS_USER() {
        return POS_USER;
    }

    public void setPOS_USER(String POS_USER) {
        this.POS_USER = POS_USER;
    }

    public String getM_FLAG() {
        return M_FLAG;
    }

    public void setM_FLAG(String m_FLAG) {
        M_FLAG = m_FLAG;
    }

    public String getEX_TRI_ID() {
        return EX_TRI_ID;
    }

    public void setEX_TRI_ID(String EX_TRI_ID) {
        this.EX_TRI_ID = EX_TRI_ID;
    }

    public String getORDER_TYPE() {
        return ORDER_TYPE;
    }

    public void setORDER_TYPE(String ORDER_TYPE) {
        this.ORDER_TYPE = ORDER_TYPE;
    }

    public String getPICK_UP_LOCATION() {
        return PICK_UP_LOCATION;
    }

    public void setPICK_UP_LOCATION(String PICK_UP_LOCATION) {
        this.PICK_UP_LOCATION = PICK_UP_LOCATION;
    }

    public String getDISCOUNT_PERCENT() {
        return DISCOUNT_PERCENT;
    }

    public void setDISCOUNT_PERCENT(String DISCOUNT_PERCENT) {
        this.DISCOUNT_PERCENT = DISCOUNT_PERCENT;
    }

    public String getLINE_ITEM_DISC() {
        return LINE_ITEM_DISC;
    }

    public void setLINE_ITEM_DISC(String LINE_ITEM_DISC) {
        this.LINE_ITEM_DISC = LINE_ITEM_DISC;
    }

    public String getLINE_DISC() {
        return LINE_DISC;
    }

    public void setLINE_DISC(String LINE_DISC) {
        this.LINE_DISC = LINE_DISC;
    }

    public String getSLAVE_FLAG() {
        return SLAVE_FLAG;
    }

    public void setSLAVE_FLAG(String SLAVE_FLAG) {
        this.SLAVE_FLAG = SLAVE_FLAG;
    }

    public String getBARCODE() {
        return BARCODE;
    }

    public void setBARCODE(String BARCODE) {
        this.BARCODE = BARCODE;
    }
}

