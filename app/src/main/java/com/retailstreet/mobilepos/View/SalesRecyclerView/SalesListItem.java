package com.retailstreet.mobilepos.View.SalesRecyclerView;

import android.database.Cursor;

public class SalesListItem {
    private String name;
    private String product_detail_2;
    private String product_detail_3;
    private String product_detail_4;
    private String primary;


    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }



    public String getProduct_detail_4() {
        return product_detail_4;
    }

    public void setProduct_detail_4(String product_detail_4) {
        this.product_detail_4 = product_detail_4;
    }

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
       // String exp = cursor.getString(24);
       // String[] splitStr = exp.split("\\s+");

        SalesListItem salesListItem = new SalesListItem();
        salesListItem.setName(cursor.getString(25));
        salesListItem.setProduct_detail_2(cursor.getString(9));
        salesListItem.setProduct_detail_3(cursor.getString(32));
        salesListItem.setProduct_detail_4(cursor.getString(10));
        salesListItem.setPrimary(cursor.getString(0));
        return salesListItem;
    }
}