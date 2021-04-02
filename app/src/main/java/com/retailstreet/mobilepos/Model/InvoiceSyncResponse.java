package com.retailstreet.mobilepos.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InvoiceSyncResponse {


    @SerializedName("Message")
    @Expose
    private String message;

    /**
     * No args constructor for use in serialization
     *
     */
    public InvoiceSyncResponse() {
    }

    /**
     *
     * @param message
     */
    public InvoiceSyncResponse(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @NonNull
    @Override
    public String toString() {
        return message;
    }

}

