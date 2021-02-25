package com.example.databasestructure.Model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GroupUserMaster {

    @SerializedName("PASSWORD")
    @Expose
    private String pASSWORD;
    @SerializedName("ROLE_ID")
    @Expose
    private String rOLEID;
    @SerializedName("ROLE_NAME")
    @Expose
    private String rOLENAME;
    @SerializedName("USER_NAME")
    @Expose
    private String uSERNAME;
    @SerializedName("ISACTIVE")
    @Expose
    private String iSACTIVE;
    @SerializedName("EMPLOYEE_GUID")
    @Expose
    private String eMPLOYEEGUID;
    @SerializedName("ROLE_GUID")
    @Expose
    private String rOLEGUID;
    @SerializedName("USER_GUID")
    @Expose
    private String uSERGUID;

    public String getISSYNCED() {
        return ISSYNCED;
    }

    public void setISSYNCED(String ISSYNCED) {
        this.ISSYNCED = ISSYNCED;
    }

    @SerializedName("ISSYNCED")
    @Expose
    private String ISSYNCED;

    /**
     * No args constructor for use in serialization
     *
     */
    public GroupUserMaster() {
    }

    /**
     *
     * @param rOLENAME
     * @param uSERNAME
     * @param iSACTIVE
     * @param rOLEID
     * @param uSERGUID
     * @param pASSWORD
     * @param eMPLOYEEGUID
     * @param rOLEGUID
     */
    public GroupUserMaster(String pASSWORD, String rOLEID, String rOLENAME, String uSERNAME, String iSACTIVE, String eMPLOYEEGUID, String rOLEGUID, String uSERGUID) {
        super();
        this.pASSWORD = pASSWORD;
        this.rOLEID = rOLEID;
        this.rOLENAME = rOLENAME;
        this.uSERNAME = uSERNAME;
        this.iSACTIVE = iSACTIVE;
        this.eMPLOYEEGUID = eMPLOYEEGUID;
        this.rOLEGUID = rOLEGUID;
        this.uSERGUID = uSERGUID;
    }

    public String getPASSWORD() {
        return pASSWORD;
    }

    public void setPASSWORD(String pASSWORD) {
        this.pASSWORD = pASSWORD;
    }

    public String getROLEID() {
        return rOLEID;
    }

    public void setROLEID(String rOLEID) {
        this.rOLEID = rOLEID;
    }

    public String getROLENAME() {
        return rOLENAME;
    }

    public void setROLENAME(String rOLENAME) {
        this.rOLENAME = rOLENAME;
    }

    public String getUSERNAME() {
        return uSERNAME;
    }

    public void setUSERNAME(String uSERNAME) {
        this.uSERNAME = uSERNAME;
    }

    public String getISACTIVE() {
        return iSACTIVE;
    }

    public void setISACTIVE(String iSACTIVE) {
        this.iSACTIVE = iSACTIVE;
    }

    public String getEMPLOYEEGUID() {
        return eMPLOYEEGUID;
    }

    public void setEMPLOYEEGUID(String eMPLOYEEGUID) {
        this.eMPLOYEEGUID = eMPLOYEEGUID;
    }

    public String getROLEGUID() {
        return rOLEGUID;
    }

    public void setROLEGUID(String rOLEGUID) {
        this.rOLEGUID = rOLEGUID;
    }

    public String getUSERGUID() {
        return uSERGUID;
    }

    public void setUSERGUID(String uSERGUID) {
        this.uSERGUID = uSERGUID;
    }

}