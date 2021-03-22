package com.retailstreet.mobilepos.Utils;

import java.util.Date;
import java.util.UUID;

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
}
