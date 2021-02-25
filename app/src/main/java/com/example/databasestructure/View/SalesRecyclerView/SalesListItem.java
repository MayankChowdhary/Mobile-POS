package com.example.databasestructure.View.SalesRecyclerView;

import android.database.Cursor;

import com.example.databasestructure.Controller.ControllerStockMaster;
import com.example.databasestructure.Model.StockMaster;
import com.example.databasestructure.View.ApplicationContextProvider;

import java.util.LinkedList;
import java.util.List;

public class SalesListItem {
    private String name;
    private String product_detail_2;
    private String product_detail_3;

    public String getProduct_detail_2() {
        return product_detail_2;
    }

    public void setProduct_detail_2(String product_detail_2) {
        this.product_detail_2 = product_detail_2;
    }

    public String getProduct_detail_3() {
        return product_detail_3;
    }

    public void setProduct_detail_3(String product_detail_3) {
        this.product_detail_3 = product_detail_3;
    }



    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return name;
    }

    public static SalesListItem fromCursor(Cursor cursor) {
        //TODO return your MyListItem from cursor.
        String exp = cursor.getString(24);
        String[] splitStr = exp.split("\\s+");

        SalesListItem salesListItem = new SalesListItem();
        salesListItem.setName(cursor.getString(25));
        salesListItem.setProduct_detail_2("Expiry: "+splitStr[0]);
        salesListItem.setProduct_detail_3("MRP: "+cursor.getString(9));
        return salesListItem;
    }
}