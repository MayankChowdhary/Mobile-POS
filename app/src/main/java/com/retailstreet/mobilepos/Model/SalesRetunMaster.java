package com.retailstreet.mobilepos.Model;


/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */


public class SalesRetunMaster {

    String ID;
    String STORE_ID;
    String TRI_ID;
    String GRAND_TOTAL;
    String REASON_OF_RETURN;
    String BUSINESS_DATE;
    String SALE_DATE;
    String SALE_TIME;
    String CARD_NO;
    String TOTAL_POINTS;
    String SCHEME_POINTS;
    String FLAG;
    String CREATED_BY;
    String CREATED_ON;
    String LAST_MODIFIED;
    String S_FLAG;
    String POS_USER;
    String M_FLAG;
    String EX_TRI_ID;
    String ORDER_TYPE;
    String PICK_UP_LOCATION;
    String DISCOUNT_PERCENT;
    String LINE_ITEM_DISC;
    String LINE_DISC;
    String SLAVE_FLAG;


    public SalesRetunMaster() {
    }

    public SalesRetunMaster(String ID, String STORE_ID, String TRI_ID, String GRAND_TOTAL, String REASON_OF_RETURN, String BUSINESS_DATE, String SALE_DATE, String SALE_TIME, String CARD_NO, String TOTAL_POINTS, String SCHEME_POINTS, String FLAG, String CREATED_BY, String CREATED_ON, String LAST_MODIFIED, String s_FLAG, String POS_USER, String m_FLAG, String EX_TRI_ID, String ORDER_TYPE, String PICK_UP_LOCATION, String DISCOUNT_PERCENT, String LINE_ITEM_DISC, String LINE_DISC, String SLAVE_FLAG) {
        this.ID = ID;
        this.STORE_ID = STORE_ID;
        this.TRI_ID = TRI_ID;
        this.GRAND_TOTAL = GRAND_TOTAL;
        this.REASON_OF_RETURN = REASON_OF_RETURN;
        this.BUSINESS_DATE = BUSINESS_DATE;
        this.SALE_DATE = SALE_DATE;
        this.SALE_TIME = SALE_TIME;
        this.CARD_NO = CARD_NO;
        this.TOTAL_POINTS = TOTAL_POINTS;
        this.SCHEME_POINTS = SCHEME_POINTS;
        this.FLAG = FLAG;
        this.CREATED_BY = CREATED_BY;
        this.CREATED_ON = CREATED_ON;
        this.LAST_MODIFIED = LAST_MODIFIED;
        S_FLAG = s_FLAG;
        this.POS_USER = POS_USER;
        M_FLAG = m_FLAG;
        this.EX_TRI_ID = EX_TRI_ID;
        this.ORDER_TYPE = ORDER_TYPE;
        this.PICK_UP_LOCATION = PICK_UP_LOCATION;
        this.DISCOUNT_PERCENT = DISCOUNT_PERCENT;
        this.LINE_ITEM_DISC = LINE_ITEM_DISC;
        this.LINE_DISC = LINE_DISC;
        this.SLAVE_FLAG = SLAVE_FLAG;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSTORE_ID() {
        return STORE_ID;
    }

    public void setSTORE_ID(String STORE_ID) {
        this.STORE_ID = STORE_ID;
    }

    public String getTRI_ID() {
        return TRI_ID;
    }

    public void setTRI_ID(String TRI_ID) {
        this.TRI_ID = TRI_ID;
    }

    public String getGRAND_TOTAL() {
        return GRAND_TOTAL;
    }

    public void setGRAND_TOTAL(String GRAND_TOTAL) {
        this.GRAND_TOTAL = GRAND_TOTAL;
    }

    public String getREASON_OF_RETURN() {
        return REASON_OF_RETURN;
    }

    public void setREASON_OF_RETURN(String REASON_OF_RETURN) {
        this.REASON_OF_RETURN = REASON_OF_RETURN;
    }

    public String getBUSINESS_DATE() {
        return BUSINESS_DATE;
    }

    public void setBUSINESS_DATE(String BUSINESS_DATE) {
        this.BUSINESS_DATE = BUSINESS_DATE;
    }

    public String getSALE_DATE() {
        return SALE_DATE;
    }

    public void setSALE_DATE(String SALE_DATE) {
        this.SALE_DATE = SALE_DATE;
    }

    public String getSALE_TIME() {
        return SALE_TIME;
    }

    public void setSALE_TIME(String SALE_TIME) {
        this.SALE_TIME = SALE_TIME;
    }

    public String getCARD_NO() {
        return CARD_NO;
    }

    public void setCARD_NO(String CARD_NO) {
        this.CARD_NO = CARD_NO;
    }

    public String getTOTAL_POINTS() {
        return TOTAL_POINTS;
    }

    public void setTOTAL_POINTS(String TOTAL_POINTS) {
        this.TOTAL_POINTS = TOTAL_POINTS;
    }

    public String getSCHEME_POINTS() {
        return SCHEME_POINTS;
    }

    public void setSCHEME_POINTS(String SCHEME_POINTS) {
        this.SCHEME_POINTS = SCHEME_POINTS;
    }

    public String getFLAG() {
        return FLAG;
    }

    public void setFLAG(String FLAG) {
        this.FLAG = FLAG;
    }

    public String getCREATED_BY() {
        return CREATED_BY;
    }

    public void setCREATED_BY(String CREATED_BY) {
        this.CREATED_BY = CREATED_BY;
    }

    public String getCREATED_ON() {
        return CREATED_ON;
    }

    public void setCREATED_ON(String CREATED_ON) {
        this.CREATED_ON = CREATED_ON;
    }

    public String getLAST_MODIFIED() {
        return LAST_MODIFIED;
    }

    public void setLAST_MODIFIED(String LAST_MODIFIED) {
        this.LAST_MODIFIED = LAST_MODIFIED;
    }

    public String getS_FLAG() {
        return S_FLAG;
    }

    public void setS_FLAG(String s_FLAG) {
        S_FLAG = s_FLAG;
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
}
