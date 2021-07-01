package com.retailstreet.mobilepos.Model;

/**
 * Created by Mayank Choudhary on 30-06-2021.
 * mayankchoudhary00@gmail.com
 */
public class SMS_SYNC {
    String Data;
    String ID;
    String Status;

    public SMS_SYNC(String data, String ID, String status) {
        Data = data;
        this.ID = ID;
        Status = status;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @Override
    public String toString() {
        return "Data ='" + Data +"'";
    }
}