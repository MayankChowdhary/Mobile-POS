package com.retailstreet.mobilepos.View.SalesReturnRecyclerView;

import android.database.Cursor;

public class SalesReturnListItem {
    private String name;
    private String product_detail_2;
    private String product_detail_3;
    private String product_detail_4;
    private String product_detail_5;
    private String Qty;
    private String primary;
    private  String MaxQty;

    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }

    public String getMaxQty() {
        return MaxQty;
    }

    public void setMaxQty(String maxQty) {
        MaxQty = maxQty;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getProduct_detail_5() {
        return product_detail_5;
    }

    public void setProduct_detail_5(String product_detail_5) {
        this.product_detail_5 = product_detail_5;
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

    public static SalesReturnListItem fromCursor(Cursor cursor) {
        SalesReturnListItem salesReturnListItem = new SalesReturnListItem();
        salesReturnListItem.setName(cursor.getString(0));
        salesReturnListItem.setProduct_detail_2(cursor.getString(1).trim());
       salesReturnListItem.setProduct_detail_3(cursor.getString(2));
       salesReturnListItem.setProduct_detail_4(cursor.getString(3));
        salesReturnListItem.setProduct_detail_5("PRICE: "+cursor.getString(4)+"  TOTAL: "+cursor.getString(5));
        salesReturnListItem.setQty(cursor.getString(3));
       salesReturnListItem.setPrimary(cursor.getString(7));
        salesReturnListItem.setMaxQty(cursor.getString(8));
        return salesReturnListItem;
    }



}