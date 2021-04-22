package com.retailstreet.mobilepos.Model;

public class LicenceModulePojo {
    private String StoreNumber;
    private String LicenseStatus;
    private String ActiveStatus;
    private String Message;
    private String Severity;

    public String getStoreNumber() {
        return StoreNumber;
    }

    public void setStoreNumber(String StoreNumber) {
        this.StoreNumber = StoreNumber;
    }

    public String getLicenseStatus() {
        return LicenseStatus;
    }

    public void setLicenseStatus(String licenseStatus) {
        LicenseStatus = licenseStatus;
    }

    public String getActiveStatus() {
        return ActiveStatus;
    }

    public void setActiveStatus(String activeStatus) {
        ActiveStatus = activeStatus;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getSeverity() {
        return Severity;
    }

    public void setSeverity(String severity) {
        Severity = severity;
    }

    public LicenceModulePojo() {
    }

    @Override
    public String toString() {
        return "LicenceModulePojo{" +
                "StoreNumber='" + StoreNumber + '\'' +
                ", LicenseStatus='" + LicenseStatus + '\'' +
                ", ActiveStatus='" + ActiveStatus + '\'' +
                ", Message='" + Message + '\'' +
                ", Severity='" + Severity + '\'' +
                '}';
    }
    /* "StoreNumber": "8739404167",
            "LicenseStatus": "False",
            "ActiveStatus": "True",
            "Message": "REDIRECT TO LOGIN PAGE",
            "Severity": "SEVERITY-S4"*/
}
