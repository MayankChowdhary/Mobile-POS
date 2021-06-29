package com.retailstreet.mobilepos.View.SalesRecyclerView;

import android.database.Cursor;

import com.retailstreet.mobilepos.Utils.DecimalRounder;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

public class SalesListItem {
    private String name;
    private String product_detail_2;
    private String product_detail_3;
    private String product_detail_4;
    private String primary;
    private String gst;
    private String sgst;
    private String cgst;
    private String product_detail_v;
    private String Qty;
    private String itemGuid;
    private String vendorNM;

    public String getVendorNM() {
        return vendorNM;
    }

    public void setVendorNM(String vendorNM) {
        this.vendorNM = vendorNM;
    }

    public String getItemGuid() {
        return itemGuid;
    }

    public void setItemGuid(String itemGuid) {
        this.itemGuid = itemGuid;
    }

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


    public String getProduct_detail_v() {
        return product_detail_v;
    }

    public void setProduct_detail_v(String product_detail_v) {
        this.product_detail_v = product_detail_v;
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

    public static SalesListItem fromCursor(Cursor cursor) {
       // String exp = cursor.getString(24);
       // String[] splitStr = exp.split("\\s+");

        SalesListItem salesListItem = new SalesListItem();
        salesListItem.setName(cursor.getString(25));
        salesListItem.setProduct_detail_2(cursor.getString(9));
        salesListItem.setProduct_detail_3(cursor.getString(31));
        salesListItem.setProduct_detail_4(cursor.getString(10));
        salesListItem.setPrimary(cursor.getString(0));
        salesListItem.setProduct_detail_v("GST: "+cursor.getString(18)+"  SGST: "+cursor.getString(19)+"  CGST: "+cursor.getString(20));
        salesListItem.setGst(cursor.getString(18));
        salesListItem.setSgst(cursor.getString(19));
        salesListItem.setCgst(cursor.getString(20));
        salesListItem.setQty(cursor.getString(3));
        salesListItem.setItemGuid(cursor.getString(3));
        salesListItem.setVendorNM(cursor.getString(36));
        return salesListItem;

    }
}