package com.retailstreet.mobilepos.View.ui.PurchaseInvoice;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

public interface TableViewInterface {
    void launchDetailsFragment(String id, String masterID);
    void cellToRowSelector(int rowId, String cellId,String masterID);
    void refreshTableView();
    void openDialogInterface(int rowId, String cellId,String masterID,String prodNAme);
}
