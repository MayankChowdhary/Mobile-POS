package com.retailstreet.mobilepos.Model;
import androidx.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */


@Keep
public class RetailStore {

    @SerializedName("ADD_1")
    @Expose
    private String aDD1;
    @SerializedName("ZIP")
    @Expose
    private String zIP;
    @SerializedName("STR_CNTCT_NM")
    @Expose
    private String sTRCNTCTNM;
    @SerializedName("STORE_NUMBER")
    @Expose
    private String sTORENUMBER;
    @SerializedName("CULTUREINFO")
    @Expose
    private String cULTUREINFO;
    @SerializedName("GSTIN_NUMBER")
    @Expose
    private String gSTINNUMBER;
    @SerializedName("VERSION_BUILD")
    @Expose
    private String vERSIONBUILD;
    @SerializedName("STR_NM")
    @Expose
    private String sTRNM;
    @SerializedName("ASSEMBLYINFO")
    @Expose
    private String aSSEMBLYINFO;
    @SerializedName("SALESPERSON_ID")
    @Expose
    private String sALESPERSONID;
    @SerializedName("CTY")
    @Expose
    private String cTY;
    @SerializedName("FLAG")
    @Expose
    private String fLAG;
    @SerializedName("TELE")
    @Expose
    private String tELE;
    @SerializedName("FSSAINUMBER")
    @Expose
    private String fSSAINUMBER;
    @SerializedName("MASTERORG_GUID")
    @Expose
    private String mASTERORGGUID;
    @SerializedName("STATUS")
    @Expose
    private String sTATUS;
    @SerializedName("STORE_STATE")
    @Expose
    private String sTORESTATE;
    @SerializedName("DESCRIPTION")
    @Expose
    private String dESCRIPTION;
    @SerializedName("IS_STOREENABLEDFORECOMMERCE")
    @Expose
    private String iSSTOREENABLEDFORECOMMERCE;
    @SerializedName("ENTID")
    @Expose
    private String eNTID;
    @SerializedName("VERSIONINDENTITY")
    @Expose
    private String vERSIONINDENTITY;
    @SerializedName("LastUpdatedOn")
    @Expose
    private String lastUpdatedOn;
    @SerializedName("STORE_ID")
    @Expose
    private String sTOREID;
    @SerializedName("POS_USER")
    @Expose
    private String pOSUSER;
    @SerializedName("TELE_1")
    @Expose
    private String tELE1;
    @SerializedName("NoOfRegisters")
    @Expose
    private String noOfRegisters;
    @SerializedName("STORE_GUID")
    @Expose
    private String sTOREGUID;
    @SerializedName("ORGID")
    @Expose
    private String oRGID;
    @SerializedName("LastUpdatedBy")
    @Expose
    private String lastUpdatedBy;
    @SerializedName("StoreTaxID")
    @Expose
    private String storeTaxID;
    @SerializedName("CREATION_DATE")
    @Expose
    private String cREATIONDATE;
    @SerializedName("FOOTER")
    @Expose
    private String fOOTER;
    @SerializedName("E_MAIL")
    @Expose
    private String eMAIL;
    @SerializedName("StoreStateID")
    @Expose
    private String storeStateID;
    @SerializedName("Store_Street")
    @Expose
    private String storeStreet;
    @SerializedName("BUSINESSTYPE")
    @Expose
    private String bUSINESSTYPE;
    @SerializedName("PANCARD_NUMBER")
    @Expose
    private String pANCARDNUMBER;
    @SerializedName("VERSION_NAME")
    @Expose
    private String vERSIONNAME;
    @SerializedName("STORE_SECONDARYEMAIL")
    @Expose
    private String sTORESECONDARYEMAIL;
    @SerializedName("ECOMMERCESTOREID")
    @Expose
    private String eCOMMERCESTOREID;
    @SerializedName("STR_CTR")
    @Expose
    private String sTRCTR;

    public String getISSYNCED() {
        return ISSYNCED;
    }

    public void setISSYNCED(String ISSYNCED) {
        this.ISSYNCED = ISSYNCED;
    }

    @SerializedName("ISSYNCED")
    @Expose
    private String ISSYNCED;


    @SerializedName("ISINTRAIL")
    @Expose
    private String ISINTRAIL;

    public String getISINTRAIL() {
        return ISINTRAIL;
    }

    public void setISINTRAIL(String ISINTRAIL) {
        this.ISINTRAIL = ISINTRAIL;
    }

    private String CREDIT_NOTE_VALIDITY;
    private String RETURN_FOOTER;

    /**
     * No args constructor for use in serialization
     *
     */
    public RetailStore() {
    }

    public RetailStore(String aDD1, String zIP, String sTRCNTCTNM, String sTORENUMBER, String cULTUREINFO, String gSTINNUMBER, String vERSIONBUILD, String sTRNM, String aSSEMBLYINFO, String sALESPERSONID, String cTY, String fLAG, String tELE, String fSSAINUMBER, String mASTERORGGUID, String sTATUS, String sTORESTATE, String dESCRIPTION, String iSSTOREENABLEDFORECOMMERCE, String eNTID, String vERSIONINDENTITY, String lastUpdatedOn, String sTOREID, String pOSUSER, String tELE1, String noOfRegisters, String sTOREGUID, String oRGID, String lastUpdatedBy, String storeTaxID, String cREATIONDATE, String fOOTER, String eMAIL, String storeStateID, String storeStreet, String bUSINESSTYPE, String pANCARDNUMBER, String vERSIONNAME, String sTORESECONDARYEMAIL, String eCOMMERCESTOREID, String sTRCTR, String ISSYNCED, String ISINTRAIL, String CREDIT_NOTE_VALIDITY, String RETURN_FOOTER) {
        this.aDD1 = aDD1;
        this.zIP = zIP;
        this.sTRCNTCTNM = sTRCNTCTNM;
        this.sTORENUMBER = sTORENUMBER;
        this.cULTUREINFO = cULTUREINFO;
        this.gSTINNUMBER = gSTINNUMBER;
        this.vERSIONBUILD = vERSIONBUILD;
        this.sTRNM = sTRNM;
        this.aSSEMBLYINFO = aSSEMBLYINFO;
        this.sALESPERSONID = sALESPERSONID;
        this.cTY = cTY;
        this.fLAG = fLAG;
        this.tELE = tELE;
        this.fSSAINUMBER = fSSAINUMBER;
        this.mASTERORGGUID = mASTERORGGUID;
        this.sTATUS = sTATUS;
        this.sTORESTATE = sTORESTATE;
        this.dESCRIPTION = dESCRIPTION;
        this.iSSTOREENABLEDFORECOMMERCE = iSSTOREENABLEDFORECOMMERCE;
        this.eNTID = eNTID;
        this.vERSIONINDENTITY = vERSIONINDENTITY;
        this.lastUpdatedOn = lastUpdatedOn;
        this.sTOREID = sTOREID;
        this.pOSUSER = pOSUSER;
        this.tELE1 = tELE1;
        this.noOfRegisters = noOfRegisters;
        this.sTOREGUID = sTOREGUID;
        this.oRGID = oRGID;
        this.lastUpdatedBy = lastUpdatedBy;
        this.storeTaxID = storeTaxID;
        this.cREATIONDATE = cREATIONDATE;
        this.fOOTER = fOOTER;
        this.eMAIL = eMAIL;
        this.storeStateID = storeStateID;
        this.storeStreet = storeStreet;
        this.bUSINESSTYPE = bUSINESSTYPE;
        this.pANCARDNUMBER = pANCARDNUMBER;
        this.vERSIONNAME = vERSIONNAME;
        this.sTORESECONDARYEMAIL = sTORESECONDARYEMAIL;
        this.eCOMMERCESTOREID = eCOMMERCESTOREID;
        this.sTRCTR = sTRCTR;
        this.ISSYNCED = ISSYNCED;
        this.ISINTRAIL = ISINTRAIL;
        this.CREDIT_NOTE_VALIDITY = CREDIT_NOTE_VALIDITY;
        this.RETURN_FOOTER = RETURN_FOOTER;
    }


    public String getADD1() {
        return aDD1;
    }

    public void setADD1(String aDD1) {
        this.aDD1 = aDD1;
    }

    public String getZIP() {
        return zIP;
    }

    public void setZIP(String zIP) {
        this.zIP = zIP;
    }

    public String getSTRCNTCTNM() {
        return sTRCNTCTNM;
    }

    public void setSTRCNTCTNM(String sTRCNTCTNM) {
        this.sTRCNTCTNM = sTRCNTCTNM;
    }

    public String getSTORENUMBER() {
        return sTORENUMBER;
    }

    public void setSTORENUMBER(String sTORENUMBER) {
        this.sTORENUMBER = sTORENUMBER;
    }

    public String getCULTUREINFO() {
        return cULTUREINFO;
    }

    public void setCULTUREINFO(String cULTUREINFO) {
        this.cULTUREINFO = cULTUREINFO;
    }

    public String getGSTINNUMBER() {
        return gSTINNUMBER;
    }

    public void setGSTINNUMBER(String gSTINNUMBER) {
        this.gSTINNUMBER = gSTINNUMBER;
    }

    public String getVERSIONBUILD() {
        return vERSIONBUILD;
    }

    public void setVERSIONBUILD(String vERSIONBUILD) {
        this.vERSIONBUILD = vERSIONBUILD;
    }

    public String getSTRNM() {
        return sTRNM;
    }

    public void setSTRNM(String sTRNM) {
        this.sTRNM = sTRNM;
    }

    public String getASSEMBLYINFO() {
        return aSSEMBLYINFO;
    }

    public void setASSEMBLYINFO(String aSSEMBLYINFO) {
        this.aSSEMBLYINFO = aSSEMBLYINFO;
    }

    public String getSALESPERSONID() {
        return sALESPERSONID;
    }

    public void setSALESPERSONID(String sALESPERSONID) {
        this.sALESPERSONID = sALESPERSONID;
    }

    public String getCTY() {
        return cTY;
    }

    public void setCTY(String cTY) {
        this.cTY = cTY;
    }

    public String getFLAG() {
        return fLAG;
    }

    public void setFLAG(String fLAG) {
        this.fLAG = fLAG;
    }

    public String getTELE() {
        return tELE;
    }

    public void setTELE(String tELE) {
        this.tELE = tELE;
    }

    public String getFSSAINUMBER() {
        return fSSAINUMBER;
    }

    public void setFSSAINUMBER(String fSSAINUMBER) {
        this.fSSAINUMBER = fSSAINUMBER;
    }

    public String getMASTERORGGUID() {
        return mASTERORGGUID;
    }

    public void setMASTERORGGUID(String mASTERORGGUID) {
        this.mASTERORGGUID = mASTERORGGUID;
    }

    public String getSTATUS() {
        return sTATUS;
    }

    public void setSTATUS(String sTATUS) {
        this.sTATUS = sTATUS;
    }

    public String getSTORESTATE() {
        return sTORESTATE;
    }

    public void setSTORESTATE(String sTORESTATE) {
        this.sTORESTATE = sTORESTATE;
    }

    public String getDESCRIPTION() {
        return dESCRIPTION;
    }

    public void setDESCRIPTION(String dESCRIPTION) {
        this.dESCRIPTION = dESCRIPTION;
    }

    public String getISSTOREENABLEDFORECOMMERCE() {
        return iSSTOREENABLEDFORECOMMERCE;
    }

    public void setISSTOREENABLEDFORECOMMERCE(String iSSTOREENABLEDFORECOMMERCE) {
        this.iSSTOREENABLEDFORECOMMERCE = iSSTOREENABLEDFORECOMMERCE;
    }

    public String getENTID() {
        return eNTID;
    }

    public void setENTID(String eNTID) {
        this.eNTID = eNTID;
    }

    public String getVERSIONINDENTITY() {
        return vERSIONINDENTITY;
    }

    public void setVERSIONINDENTITY(String vERSIONINDENTITY) {
        this.vERSIONINDENTITY = vERSIONINDENTITY;
    }

    public String getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(String lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public String getSTOREID() {
        return sTOREID;
    }

    public void setSTOREID(String sTOREID) {
        this.sTOREID = sTOREID;
    }

    public String getPOSUSER() {
        return pOSUSER;
    }

    public void setPOSUSER(String pOSUSER) {
        this.pOSUSER = pOSUSER;
    }

    public String getTELE1() {
        return tELE1;
    }

    public void setTELE1(String tELE1) {
        this.tELE1 = tELE1;
    }

    public String getNoOfRegisters() {
        return noOfRegisters;
    }

    public void setNoOfRegisters(String noOfRegisters) {
        this.noOfRegisters = noOfRegisters;
    }

    public String getSTOREGUID() {
        return sTOREGUID;
    }

    public void setSTOREGUID(String sTOREGUID) {
        this.sTOREGUID = sTOREGUID;
    }

    public String getORGID() {
        return oRGID;
    }

    public void setORGID(String oRGID) {
        this.oRGID = oRGID;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getStoreTaxID() {
        return storeTaxID;
    }

    public void setStoreTaxID(String storeTaxID) {
        this.storeTaxID = storeTaxID;
    }

    public String getCREATIONDATE() {
        return cREATIONDATE;
    }

    public void setCREATIONDATE(String cREATIONDATE) {
        this.cREATIONDATE = cREATIONDATE;
    }

    public String getFOOTER() {
        return fOOTER;
    }

    public void setFOOTER(String fOOTER) {
        this.fOOTER = fOOTER;
    }

    public String getEMAIL() {
        return eMAIL;
    }

    public void setEMAIL(String eMAIL) {
        this.eMAIL = eMAIL;
    }

    public String getStoreStateID() {
        return storeStateID;
    }

    public void setStoreStateID(String storeStateID) {
        this.storeStateID = storeStateID;
    }

    public String getStoreStreet() {
        return storeStreet;
    }

    public void setStoreStreet(String storeStreet) {
        this.storeStreet = storeStreet;
    }

    public String getBUSINESSTYPE() {
        return bUSINESSTYPE;
    }

    public void setBUSINESSTYPE(String bUSINESSTYPE) {
        this.bUSINESSTYPE = bUSINESSTYPE;
    }

    public String getPANCARDNUMBER() {
        return pANCARDNUMBER;
    }

    public void setPANCARDNUMBER(String pANCARDNUMBER) {
        this.pANCARDNUMBER = pANCARDNUMBER;
    }

    public String getVERSIONNAME() {
        return vERSIONNAME;
    }

    public void setVERSIONNAME(String vERSIONNAME) {
        this.vERSIONNAME = vERSIONNAME;
    }

    public String getSTORESECONDARYEMAIL() {
        return sTORESECONDARYEMAIL;
    }

    public void setSTORESECONDARYEMAIL(String sTORESECONDARYEMAIL) {
        this.sTORESECONDARYEMAIL = sTORESECONDARYEMAIL;
    }

    public String getECOMMERCESTOREID() {
        return eCOMMERCESTOREID;
    }

    public void setECOMMERCESTOREID(String eCOMMERCESTOREID) {
        this.eCOMMERCESTOREID = eCOMMERCESTOREID;
    }

    public String getSTRCTR() {
        return sTRCTR;
    }

    public void setSTRCTR(String sTRCTR) {
        this.sTRCTR = sTRCTR;
    }

    @Override
    public String toString() {
        return sTRNM;
    }


    public String getCREDIT_NOTE_VALIDITY() {
        return CREDIT_NOTE_VALIDITY;
    }

    public void setCREDIT_NOTE_VALIDITY(String CREDIT_NOTE_VALIDITY) {
        this.CREDIT_NOTE_VALIDITY = CREDIT_NOTE_VALIDITY;
    }

    public String getRETURN_FOOTER() {
        return RETURN_FOOTER;
    }

    public void setRETURN_FOOTER(String RETURN_FOOTER) {
        this.RETURN_FOOTER = RETURN_FOOTER;
    }
}