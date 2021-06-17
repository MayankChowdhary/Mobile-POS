package com.retailstreet.mobilepos.Model;

/**
 * Created by Mayank Choudhary on 14-06-2021.
 * mayankchoudhary00@gmail.com
 */


public class StoreParameter {

    String STORE_ID;
    String STORE_GUID;
    String Vendoraddition;
    String Productaddition;
    String StockEntry;
    String Stockadjustments;
    String Vendorpayment;
    String Vendorreturns;
    String Creditsales;
    String Estimates;
    String Systemsetting;
    String Storeaddress;
    String Reports;
    String Printersetting;
    String Additionalparam1;
    String Looseitem;
    String GST_Price;
    String Cash_drawer;
    String Printer;
    String Printer_brand;


    public StoreParameter() {

    }

    public StoreParameter(String STORE_ID, String STORE_GUID, String vendoraddition, String productaddition, String stockEntry, String stockadjustments, String vendorpayment, String vendorreturns, String creditsales, String estimates, String systemsetting, String storeaddress, String reports, String printersetting, String additionalparam1, String looseitem, String GST_Price, String cash_drawer, String printer, String printer_brand) {
        this.STORE_ID = STORE_ID;
        this.STORE_GUID = STORE_GUID;
        Vendoraddition = vendoraddition;
        Productaddition = productaddition;
        StockEntry = stockEntry;
        Stockadjustments = stockadjustments;
        Vendorpayment = vendorpayment;
        Vendorreturns = vendorreturns;
        Creditsales = creditsales;
        Estimates = estimates;
        Systemsetting = systemsetting;
        Storeaddress = storeaddress;
        Reports = reports;
        Printersetting = printersetting;
        Additionalparam1 = additionalparam1;
        Looseitem = looseitem;
        this.GST_Price = GST_Price;
        Cash_drawer = cash_drawer;
        Printer = printer;
        Printer_brand = printer_brand;
    }


    public String getSTORE_ID() {
        return STORE_ID;
    }

    public void setSTORE_ID(String STORE_ID) {
        this.STORE_ID = STORE_ID;
    }

    public String getSTORE_GUID() {
        return STORE_GUID;
    }

    public void setSTORE_GUID(String STORE_GUID) {
        this.STORE_GUID = STORE_GUID;
    }

    public String getVendoraddition() {
        return Vendoraddition;
    }

    public void setVendoraddition(String vendoraddition) {
        Vendoraddition = vendoraddition;
    }

    public String getProductaddition() {
        return Productaddition;
    }

    public void setProductaddition(String productaddition) {
        Productaddition = productaddition;
    }

    public String getStockEntry() {
        return StockEntry;
    }

    public void setStockEntry(String stockEntry) {
        StockEntry = stockEntry;
    }

    public String getStockadjustments() {
        return Stockadjustments;
    }

    public void setStockadjustments(String stockadjustments) {
        Stockadjustments = stockadjustments;
    }

    public String getVendorpayment() {
        return Vendorpayment;
    }

    public void setVendorpayment(String vendorpayment) {
        Vendorpayment = vendorpayment;
    }

    public String getVendorreturns() {
        return Vendorreturns;
    }

    public void setVendorreturns(String vendorreturns) {
        Vendorreturns = vendorreturns;
    }

    public String getCreditsales() {
        return Creditsales;
    }

    public void setCreditsales(String creditsales) {
        Creditsales = creditsales;
    }

    public String getEstimates() {
        return Estimates;
    }

    public void setEstimates(String estimates) {
        Estimates = estimates;
    }

    public String getSystemsetting() {
        return Systemsetting;
    }

    public void setSystemsetting(String systemsetting) {
        Systemsetting = systemsetting;
    }

    public String getStoreaddress() {
        return Storeaddress;
    }

    public void setStoreaddress(String storeaddress) {
        Storeaddress = storeaddress;
    }

    public String getReports() {
        return Reports;
    }

    public void setReports(String reports) {
        Reports = reports;
    }

    public String getPrintersetting() {
        return Printersetting;
    }

    public void setPrintersetting(String printersetting) {
        Printersetting = printersetting;
    }

    public String getAdditionalparam1() {
        return Additionalparam1;
    }

    public void setAdditionalparam1(String additionalparam1) {
        Additionalparam1 = additionalparam1;
    }

    public String getLooseitem() {
        return Looseitem;
    }

    public void setLooseitem(String looseitem) {
        Looseitem = looseitem;
    }

    public String getGST_Price() {
        return GST_Price;
    }

    public void setGST_Price(String GST_Price) {
        this.GST_Price = GST_Price;
    }

    public String getCash_drawer() {
        return Cash_drawer;
    }

    public void setCash_drawer(String cash_drawer) {
        Cash_drawer = cash_drawer;
    }

    public String getPrinter() {
        return Printer;
    }

    public void setPrinter(String printer) {
        Printer = printer;
    }

    public String getPrinter_brand() {
        return Printer_brand;
    }

    public void setPrinter_brand(String printer_brand) {
        Printer_brand = printer_brand;
    }
}
