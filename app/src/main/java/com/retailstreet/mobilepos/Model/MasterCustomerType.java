package com.retailstreet.mobilepos.Model;


/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */


public class MasterCustomerType {

    String MASTER_CUSTOMERTYPEID;
    String CUSTOMERTYPE;
    String MASTER_CUSTOMERTYPESTATUS;


    public MasterCustomerType() {

    }

    public MasterCustomerType(String MASTER_CUSTOMERTYPEID, String CUSTOMERTYPE, String MASTER_CUSTOMERTYPESTATUS) {
        this.MASTER_CUSTOMERTYPEID = MASTER_CUSTOMERTYPEID;
        this.CUSTOMERTYPE = CUSTOMERTYPE;
        this.MASTER_CUSTOMERTYPESTATUS = MASTER_CUSTOMERTYPESTATUS;
    }

    public String getMASTER_CUSTOMERTYPEID() {
        return MASTER_CUSTOMERTYPEID;
    }

    public void setMASTER_CUSTOMERTYPEID(String MASTER_CUSTOMERTYPEID) {
        this.MASTER_CUSTOMERTYPEID = MASTER_CUSTOMERTYPEID;
    }

    public String getCUSTOMERTYPE() {
        return CUSTOMERTYPE;
    }

    public void setCUSTOMERTYPE(String CUSTOMERTYPE) {
        this.CUSTOMERTYPE = CUSTOMERTYPE;
    }

    public String getMASTER_CUSTOMERTYPESTATUS() {
        return MASTER_CUSTOMERTYPESTATUS;
    }

    public void setMASTER_CUSTOMERTYPESTATUS(String MASTER_CUSTOMERTYPESTATUS) {
        this.MASTER_CUSTOMERTYPESTATUS = MASTER_CUSTOMERTYPESTATUS;
    }
}
