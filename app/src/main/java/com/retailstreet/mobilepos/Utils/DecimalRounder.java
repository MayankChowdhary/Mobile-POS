package com.retailstreet.mobilepos.Utils;

import com.retailstreet.mobilepos.Controller.ControllerStoreConfig;

import java.text.DecimalFormat;

/**
 * Created by Mayank Choudhary on 15-06-2021.
 * mayankchoudhary00@gmail.com
 */
public class  DecimalRounder {

    static ControllerStoreConfig config = new ControllerStoreConfig();
    private   static boolean grandRoundoff = true;


    public static String  roundPPrice(String pprice) {

        try {
            double number = Double.parseDouble(pprice);
            return roundPPrice(number);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return pprice;
    }
    public static String  roundMRP(String mrp) {

        try {
            double number = Double.parseDouble(mrp);
           return roundMRP(number);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return mrp;
    }

    public static String  roundSPrice(String sprice) {
        try {
            double number = Double.parseDouble(sprice);
            return roundSPrice(number);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return sprice;
    }


    public static String  roundSPrice(double sprice){

        int decimalPoint = config.getSPriceDecimal();
        return roundDecimal(decimalPoint,sprice);
    }

    public static String  roundPPrice(double pprice){

        int decimalPoint = config.getPPriceDecimal();
        return roundDecimal(decimalPoint,pprice);
    }


    public static String  roundMRP(double mrp){

       int decimalPoint = config.getMrpDecimal();
       return roundDecimal(decimalPoint,mrp);
   }


    public static String roundDecimal(int zeroes, String value){
        try {
            double number = Double.parseDouble(value);
            return roundDecimal(zeroes,number);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return value;
    }

   public  static String roundDecimal(int zeroes, double value){

       try {
           switch (zeroes){
               case 1:
                   return new DecimalFormat("#0.0").format(value);
               case 2:
                   return new DecimalFormat("#0.00").format(value);
               case 3:
                   return new DecimalFormat("#0.000").format(value);
               case 4:
                   return new DecimalFormat("#0.0000").format(value);
               case 5:
                   return new DecimalFormat("#0.00000").format(value);
               case 6:
                   return new DecimalFormat("#0.000000").format(value);
               case 7:
                   return new DecimalFormat("#0.0000000").format(value);
               case 8:
                   return new DecimalFormat("#0.00000000").format(value);
               default:
                   return new DecimalFormat("#0.00").format(value);
           }
       } catch (Exception e) {
           e.printStackTrace();
       }

       return String.valueOf(value);
   }

    public static String grandRoundOff(String  value){

        try {
            double valuex = Double.parseDouble(value);
            return  grandRoundOff(valuex);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return value;
    }


   public static String grandRoundOff(double value){
       grandRoundoff = config.getIsRoundOff();
       if(grandRoundoff) {
         return new DecimalFormat("#0.##").format(value);
       }else {
           return new DecimalFormat("#0.00").format(value);
       }

   }

}
