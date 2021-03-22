package com.retailstreet.mobilepos.Model;

import androidx.annotation.Keep;

@Keep
public class BillMasterUpload {
    
    String BID;
    String ORG_GUID;
    String STORE_GUID;
    String TERMINAL_GUID;
    String SHIFTID;
    String BILLNO;
    String SALEDATE;
    String SALETIME;
    String USER_GUID;
    String CUST_MOBILENO;
    String NETVALUE;
    String TAXVALUE;
    String TOTAL_AMOUNT;
    String DEL_TYPE;
    String BILL_PRINT;
    String TOTAL_BILL_AMOUNT;
    String NO_OF_ITEMS;
    String BILL_STATUS;
    String MASTERCUSTOMER_GUID;
    String RECEIVED_CASH;
    String BALANCE_CASH;
    String ROUND_OFF;
    String NETDISCOUNT;

    public BillMasterUpload(){

    }
    public BillMasterUpload(String BID, String ORG_GUID , String STORE_GUID,String TERMINAL_GUID, String SHIFTID, String BILLNO, String SALEDATE, String SALETIME, String USER_GUID, String CUST_MOBILENO, String NETVALUE, String TAXVALUE, String TOTAL_AMOUNT, String DEL_TYPE, String BILL_PRINT, String TOTAL_BILL_AMOUNT, String NO_OF_ITEMS, String BILL_STATUS, String MASTERCUSTOMER_GUID, String RECEIVED_CASH, String BALANCE_CASH, String ROUND_OFF, String NETDISCOUNT){
        this.BID =BID;
        this.ORG_GUID=ORG_GUID;
        this.STORE_GUID = STORE_GUID;
        this.TERMINAL_GUID =TERMINAL_GUID;
        this.SHIFTID= SHIFTID;
        this.BILLNO=BILLNO;
        this.SALEDATE = SALEDATE;
        this.SALETIME = SALETIME;
        this.USER_GUID = USER_GUID;
        this.CUST_MOBILENO = CUST_MOBILENO;
        this.NETVALUE = NETVALUE;
        this.TAXVALUE = TAXVALUE;
        this.TOTAL_AMOUNT = TOTAL_AMOUNT;
        this.DEL_TYPE = DEL_TYPE;
        this.BILL_PRINT = BILL_PRINT;
        this.TOTAL_BILL_AMOUNT = TOTAL_BILL_AMOUNT;
        this.NO_OF_ITEMS = NO_OF_ITEMS;
        this.BILL_STATUS = BILL_STATUS;
        this.MASTERCUSTOMER_GUID = MASTERCUSTOMER_GUID;
        this.RECEIVED_CASH = RECEIVED_CASH;
        this.BALANCE_CASH = BALANCE_CASH;
        this.ROUND_OFF = ROUND_OFF;
        this.NETDISCOUNT = NETDISCOUNT;
    }

    public String getBID() {
        return BID;
    }

    public void setBID(String BID) {
        this.BID = BID;
    }

    public String getORG_GUID() {
        return ORG_GUID;
    }

    public void setORG_GUID(String ORG_GUID) {
        this.ORG_GUID = ORG_GUID;
    }

    public String getSTORE_GUID() {
        return STORE_GUID;
    }

    public void setSTORE_GUID(String STORE_GUID) {
        this.STORE_GUID = STORE_GUID;
    }

    public String getTERMINAL_GUID() {
        return TERMINAL_GUID;
    }

    public void setTERMINAL_GUID(String TERMINAL_GUID) {
        this.TERMINAL_GUID = TERMINAL_GUID;
    }

    public String getSHIFTID() {
        return SHIFTID;
    }

    public void setSHIFTID(String SHIFTID) {
        this.SHIFTID = SHIFTID;
    }

    public String getBILLNO() {
        return BILLNO;
    }

    public void setBILLNO(String BILLNO) {
        this.BILLNO = BILLNO;
    }

    public String getSALEDATE() {
        return SALEDATE;
    }

    public void setSALEDATE(String SALEDATE) {
        this.SALEDATE = SALEDATE;
    }

    public String getSALETIME() {
        return SALETIME;
    }

    public void setSALETIME(String SALETIME) {
        this.SALETIME = SALETIME;
    }

    public String getUSER_GUID() {
        return USER_GUID;
    }

    public void setUSER_GUID(String USER_GUID) {
        this.USER_GUID = USER_GUID;
    }

    public String getCUST_MOBILENO() {
        return CUST_MOBILENO;
    }

    public void setCUST_MOBILENO(String CUST_MOBILENO) {
        this.CUST_MOBILENO = CUST_MOBILENO;
    }

    public String getNETVALUE() {
        return NETVALUE;
    }

    public void setNETVALUE(String NETVALUE) {
        this.NETVALUE = NETVALUE;
    }

    public String getTAXVALUE() {
        return TAXVALUE;
    }

    public void setTAXVALUE(String TAXVALUE) {
        this.TAXVALUE = TAXVALUE;
    }

    public String getTOTAL_AMOUNT() {
        return TOTAL_AMOUNT;
    }

    public void setTOTAL_AMOUNT(String TOTAL_AMOUNT) {
        this.TOTAL_AMOUNT = TOTAL_AMOUNT;
    }

    public String getDEL_TYPE() {
        return DEL_TYPE;
    }

    public void setDEL_TYPE(String DEL_TYPE) {
        this.DEL_TYPE = DEL_TYPE;
    }

    public String getBILL_PRINT() {
        return BILL_PRINT;
    }

    public void setBILL_PRINT(String BILL_PRINT) {
        this.BILL_PRINT = BILL_PRINT;
    }

    public String getTOTAL_BILL_AMOUNT() {
        return TOTAL_BILL_AMOUNT;
    }

    public void setTOTAL_BILL_AMOUNT(String TOTAL_BILL_AMOUNT) {
        this.TOTAL_BILL_AMOUNT = TOTAL_BILL_AMOUNT;
    }

    public String getNO_OF_ITEMS() {
        return NO_OF_ITEMS;
    }

    public void setNO_OF_ITEMS(String NO_OF_ITEMS) {
        this.NO_OF_ITEMS = NO_OF_ITEMS;
    }

    public String getBILL_STATUS() {
        return BILL_STATUS;
    }

    public void setBILL_STATUS(String BILL_STATUS) {
        this.BILL_STATUS = BILL_STATUS;
    }

    public String getMASTERCUSTOMER_GUID() {
        return MASTERCUSTOMER_GUID;
    }

    public void setMASTERCUSTOMER_GUID(String MASTERCUSTOMER_GUID) {
        this.MASTERCUSTOMER_GUID = MASTERCUSTOMER_GUID;
    }

    public String getRECEIVED_CASH() {
        return RECEIVED_CASH;
    }

    public void setRECEIVED_CASH(String RECEIVED_CASH) {
        this.RECEIVED_CASH = RECEIVED_CASH;
    }

    public String getBALANCE_CASH() {
        return BALANCE_CASH;
    }

    public void setBALANCE_CASH(String BALANCE_CASH) {
        this.BALANCE_CASH = BALANCE_CASH;
    }

    public String getROUND_OFF() {
        return ROUND_OFF;
    }

    public void setROUND_OFF(String ROUND_OFF) {
        this.ROUND_OFF = ROUND_OFF;
    }

    public String getNETDISCOUNT() {
        return NETDISCOUNT;
    }

    public void setNETDISCOUNT(String NETDISCOUNT) {
        this.NETDISCOUNT = NETDISCOUNT;
    }
}
