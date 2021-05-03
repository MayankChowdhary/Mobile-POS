package com.retailstreet.mobilepos.View.ui.PurchaseInvoice;

public interface TableViewInterface {
    void launchDetailsFragment(String id, String masterID);
    void cellToRowSelector(int rowId, String cellId,String masterID);
    void refreshTableView();
    void openDialogInterface(int rowId, String cellId,String masterID,String prodNAme);
}
