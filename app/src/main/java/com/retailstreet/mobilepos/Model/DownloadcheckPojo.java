package com.retailstreet.mobilepos.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Modified by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

public class DownloadcheckPojo {

    @SerializedName("Message")
    @Expose
    private String message;

    /**
     * No args constructor for use in serialization
     *
     */
    public DownloadcheckPojo() {
    }

    /**
     *
     * @param message
     */
    public DownloadcheckPojo(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }

}