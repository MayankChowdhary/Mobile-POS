package com.retailstreet.mobilepos.View.CartRecyclerView;

import android.database.Cursor;

import com.retailstreet.mobilepos.Utils.DecimalRounder;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

public class CartListItem {
    private String name;
    private String product_detail_2;
    private String product_detail_3;
    private String product_detail_4;
    private String product_detail_5;
    private String gst;
    private String sgst;
    private String cgst;
    private String primary;
    private String Qty;

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }


    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getSgst() {
        return sgst;
    }

    public void setSgst(String sgst) {
        this.sgst = sgst;
    }

    public String getCgst() {
        return cgst;
    }

    public void setCgst(String cgst) {
        this.cgst = cgst;
    }




    public String getProduct_detail_5() {
        return product_detail_5;
    }

    public void setProduct_detail_5(String product_detail_5) {
        this.product_detail_5 = product_detail_5;
    }


    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }



    public String getProduct_detail_4() {
        return DecimalRounder.roundSPrice(product_detail_4);
    }

    public void setProduct_detail_4(String product_detail_4) {
        this.product_detail_4 = product_detail_4;
    }

    public String getProduct_detail_2() {
        return DecimalRounder.roundMRP(product_detail_2);
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

    public static CartListItem fromCursor(Cursor cursor) {
       // String exp = cursor.getString(24);
       // String[] splitStr = exp.split("\\s+");

        CartListItem cartListItem = new CartListItem();
        cartListItem.setName(cursor.getString(1));
        cartListItem.setProduct_detail_2(cursor.getString(3));
       cartListItem.setProduct_detail_3(cursor.getString(5));
       cartListItem.setProduct_detail_4(cursor.getString(4));
        cartListItem.setPrimary(cursor.getString(0));
        cartListItem.setProduct_detail_5("GST: "+cursor.getString(6)+"  SGST: "+cursor.getString(7)+"  CGST: "+cursor.getString(8));
        cartListItem.setGst(cursor.getString(6));
        cartListItem.setSgst(cursor.getString(7));
        cartListItem.setCgst(cursor.getString(8));
        cartListItem.setQty(cursor.getString(9));
        return cartListItem;
    }
}