package com.retailstreet.mobilepos.View.ui.PurchaseInvoice.TableViewComponents;

import android.view.Gravity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by evrencoskun on 4.02.2018.
 */

public class MyTableViewModel {
    // View Types
    public static final int GENDER_TYPE = 1;
    public static final int MONEY_TYPE = 2;

    private List<ColumnHeaderModel> mColumnHeaderModelList;
    private List<RowHeaderModel> mRowHeaderModelList;
    private List<List<CellModel>> mCellModelList;

    public int getCellItemViewType(int column) {

        switch (column) {
            case 12:
                // 5. column header is gender.
                return GENDER_TYPE;

            default:
                return 0;
        }
    }

     /*
       - Each of Column Header -
            "Id"
            "Name"
            "Nickname"
            "Email"
            "Birthday"
            "Gender"
            "Age"
            "Job"
            "Salary"
            "CreatedAt"
            "UpdatedAt"
            "Address"
            "Zip Code"
            "Phone"
            "Fax"
     */

    public int getColumnTextAlign(int column) {
        switch (column) {
            // Id
            case 0:
                return Gravity.CENTER;
            // Name
            case 1:
                return Gravity.CENTER;
            // Nickname
            case 2:
                return Gravity.CENTER;
            // Email
            case 3:
                return Gravity.CENTER;
            // BirthDay
            case 4:
                return Gravity.CENTER;
            // Gender (Sex)
            case 5:
                return Gravity.CENTER;
            // Age
            case 6:
                return Gravity.CENTER;
            // Job
            case 7:
                return Gravity.CENTER;
            // Salary
            case 8:
                return Gravity.CENTER;
            // CreatedAt
            case 9:
                return Gravity.CENTER;
            // UpdatedAt
            case 10:
                return Gravity.CENTER;
            // Address
            case 11:
                return Gravity.CENTER;
            // Zip Code
            case 12:
                return Gravity.CENTER;
            default:
                return Gravity.LEFT;
        }

    }


    private List<ColumnHeaderModel> createColumnHeaderModelList() {
        List<ColumnHeaderModel> list = new ArrayList<>();

        // Create Column Headers
        list.add(new ColumnHeaderModel("PRODUCT NAME"));
        list.add(new ColumnHeaderModel("EXTERNAL ID"));
        list.add(new ColumnHeaderModel("BARCODE"));
        list.add(new ColumnHeaderModel("EXPIRY DATE"));
        list.add(new ColumnHeaderModel("MRP"));
        list.add(new ColumnHeaderModel("S.PRICE"));
        list.add(new ColumnHeaderModel("P.PRICE"));
        list.add(new ColumnHeaderModel("QTY"));
        list.add(new ColumnHeaderModel("F.QTY"));
        list.add(new ColumnHeaderModel("DISC"));
        list.add(new ColumnHeaderModel("UOM"));
        list.add(new ColumnHeaderModel("TOTAL"));
        list.add(new ColumnHeaderModel("  "));

        return list;
    }

    private List<List<CellModel>> createCellModelList(List<User> userList) {
        List<List<CellModel>> lists = new ArrayList<>();

        // Creating cell model list from User list for Cell Items
        // In this example, User list is populated from web service

        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);

            List<CellModel> list = new ArrayList<>();

            // The order should be same with column header list;
            list.add(new CellModel(user.ITEM_GUID , user.P_NAME));
            list.add(new CellModel(user.ITEM_GUID , user.EXT_ID));
            list.add(new CellModel(user.ITEM_GUID , user.BARCODE));
            list.add(new CellModel(user.ITEM_GUID , user.EXPIRY));
            list.add(new CellModel(user.ITEM_GUID , user.MRP));
            list.add(new CellModel(user.ITEM_GUID , user.S_PRICE));
            list.add(new CellModel(user.ITEM_GUID , user.P_PRICE));
            list.add(new CellModel(user.ITEM_GUID , user.QTY));
            list.add(new CellModel(user.ITEM_GUID , user.F_QTY));
            list.add(new CellModel(user.ITEM_GUID , user.DISC));
            list.add(new CellModel(user.ITEM_GUID , user.UOM));
            list.add(new CellModel(user.ITEM_GUID , user.TOTAL));
            list.add(new CellModel(user.ITEM_GUID , ""));

            // Add
            lists.add(list);
        }

        return lists;
    }

    private List<RowHeaderModel> createRowHeaderList(int size, List<User> users) {
        List<RowHeaderModel> list = new ArrayList<>();
        int i = 0;
        for (User user:users) {
            // In this example, Row headers just shows the index of the TableView List.
            list.add(new RowHeaderModel(String.valueOf(i + 1),user.ITEM_GUID));
            i++;
        }
        return list;
    }


    public List<ColumnHeaderModel> getColumHeaderModeList() {
        return mColumnHeaderModelList;
    }

    public List<RowHeaderModel> getRowHeaderModelList() {
        return mRowHeaderModelList;
    }

    public List<List<CellModel>> getCellModelList() {
        return mCellModelList;
    }


    public void generateListForTableView(List<User> users) {
        mColumnHeaderModelList = createColumnHeaderModelList();
        mCellModelList = createCellModelList(users);
        mRowHeaderModelList = createRowHeaderList(users.size(), users);
    }

}



