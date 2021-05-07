package com.retailstreet.mobilepos.Model;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

public class SalesReturnManage {
        public Double Saleqty;
        float Salesellingprice, GrandTotal, Saletotal, Salestockqty;
        String Salebatchno, Barcode, Salemrp, SaleDate, Reasons, SaleProdid, Saleuom,
                Discount, Saleproductname, Saleexpiry, Salediscoumt,customerguid,reasonguid;
        float salereturn_sprice,GST,SGST,CGST,BALANCE_CASH;

        public float getBALANCE_CASH() {
            return BALANCE_CASH;
        }

        public void setBALANCE_CASH(float BALANCE_CASH) {
            this.BALANCE_CASH = BALANCE_CASH;
        }

        public float getGST() {
            return GST;
        }

        public void setGST(float GST) {
            this.GST = GST;
        }

        public float getSGST() {
            return SGST;
        }

        public void setSGST(float SGST) {
            this.SGST = SGST;
        }

        public float getCGST() {
            return CGST;
        }

        public void setCGST(float CGST) {
            this.CGST = CGST;
        }

        public String getExp_Date() {
            return Exp_Date;
        }

        public void setExp_Date(String exp_Date) {
            Exp_Date = exp_Date;
        }

        String Exp_Date;
        float salereturn_qty;
        float salereturn_total;
        float salereturn_Linelevel_discount;
        public SalesReturnManage() {
            this.Saleqty = 1.0;
            this.Salebatchno = "";
            this.Salediscoumt = "0";
            this.Salesellingprice = 0.00f;
            salereturn_sprice=0.0f;
            salereturn_qty=0.0f;
            salereturn_total=0.0f;
            salereturn_Linelevel_discount =0.0f;
        }

        public String getBarcode() {
            return Barcode;
        }

        public void setBarcode(String barcode) {
            Barcode = barcode;
        }

        public String getSalebatchno() {
            return Salebatchno;
        }

        public void setSalebatchno(String salebatchno) {
            Salebatchno = salebatchno;
        }

        public String getReasonguid() {
            return reasonguid;
        }

        public void setReasonguid(String reasonguid) {
            this.reasonguid = reasonguid;
        }

        public String getCustomerguid() {
            return customerguid;
        }

        public void setCustomerguid(String customerguid) {
            this.customerguid = customerguid;
        }

        public String getSalediscoumt() {
            return Salediscoumt;
        }

        public void setSalediscoumt(String salediscoumt) {
            Salediscoumt = salediscoumt;
//        Salesellingprice=(float) ((Salesellingprice+Float.parseFloat(Salediscoumt))/ Saleqty);
            //   Saletotal = (float) ((Salesellingprice - Double.parseDouble(Salediscoumt)) * Saleqty);
        }

        public String getDiscount() {
            return Discount;
        }

        public void setDiscount(String discount) {
            Discount = discount;
        }

        public String getSaleProdid() {
            return SaleProdid;
        }

        public void setSaleProdid(String saleProdid) {
            SaleProdid = saleProdid;
        }

        public String getSaleDate() {
            return SaleDate;
        }

        public void setSaleDate(String saleDate) {
            SaleDate = saleDate;
        }

        public float getSalestockqty() {
            return Salestockqty;
        }

        public void setSalestockqty(float salestockqty) {
            Salestockqty = salestockqty;
        }

        public String getReasons() {
            return Reasons;
        }

        public void setReasons(String reasons) {
            Reasons = reasons;
        }

        public float getGrandTotal() {
            return GrandTotal;
        }

        public void setGrandTotal(float grandTotal) {
            GrandTotal = grandTotal;
        }

        public String getSalemrp() {
            return Salemrp;
        }

        public void setSalemrp(String salemrp) {
            Salemrp = salemrp;
        }

        public Double getSaleqty() {
            return Saleqty;
        }

        public void setSaleqty(Double saleqty) {
            Saleqty = saleqty;
//        try {
//            // salesellingprice=(float) ((Salesellingprice+Float.parseFloat(Salediscoumt))/ Saleqty);
//            Saletotal = (float) ((Salesellingprice - Double.parseDouble(Salediscoumt)) * Saleqty);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        }

        public float getSaletotal() {
            return Saletotal;
        }

        public void setSaletotal(float saletotal) {
            Saletotal = saletotal;
//        try {
//            Saletotal = (float) ((Salesellingprice - Double.parseDouble(Salediscoumt)) * Saleqty);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        }

        public String getSaleuom() {
            return Saleuom;
        }

        public void setSaleuom(String saleuom) {
            Saleuom = saleuom;
        }

        public float getSalesellingprice() {
            return Salesellingprice;
        }

        public void setSalesellingprice(float salesellingprice) {
            Salesellingprice = salesellingprice;
            try {
                // salesellingprice=(float) ((Salesellingprice+Float.parseFloat(Salediscoumt))/ Saleqty);
                //   Saletotal = (float) ((Salesellingprice - Double.parseDouble(Salediscoumt)) * Saleqty);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public String getSaleproductname() {
            return Saleproductname;
        }

        public void setSaleproductname(String saleproductname) {
            Saleproductname = saleproductname;
        }

        public String getSaleexpiry() {
            return Saleexpiry;
        }

        public void setSaleexpiry(String saleexpiry) {
            Saleexpiry = saleexpiry;
        }
        public float getSalereturn_sprice() {
            return salereturn_sprice;
        }

        public void setSalereturn_sprice(float salereturn_sprice) {
            this.salereturn_sprice = salereturn_sprice;
        }

        public float getSalereturn_qty() {
            return salereturn_qty;
        }

        public void setSalereturn_qty(float salereturn_qty) {
            this.salereturn_qty = salereturn_qty;
        }

        public float getSalereturn_total() {
            return salereturn_total;
        }

        public void setSalereturn_total(float salereturn_total) {
            this.salereturn_total = salereturn_total;
        }

        public float getSalereturn_Linelevel_discount() {
            return salereturn_Linelevel_discount;
        }

        public void setSalereturn_Linelevel_discount(float salereturn_Linelevel_discount) {
            this.salereturn_Linelevel_discount = salereturn_Linelevel_discount;
        }

        @Override
        public String toString() {
            return Reasons;
        }

}
