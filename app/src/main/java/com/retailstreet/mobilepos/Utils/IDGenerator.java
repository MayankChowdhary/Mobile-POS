package com.retailstreet.mobilepos.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */


public class IDGenerator {

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        System.out.println("UUID=" + uuid.toString());
        return uuid.toString();
    }

    public static String getTimeStamp() {
        Date date = new Date();
        //This method returns the time in millis
        long timeMilli = date.getTime();
        return String.valueOf(timeMilli);
    }

    public static String getDateAndTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return formatter.format(date);
    }
}
