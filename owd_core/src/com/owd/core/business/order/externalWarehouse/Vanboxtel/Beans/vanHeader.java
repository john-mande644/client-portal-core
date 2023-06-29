package com.owd.core.business.order.externalWarehouse.Vanboxtel.Beans;

/**
 * Created by danny on 4/24/2017.
 */
public class vanHeader {
    private String TIMESTAMP;
    private int CLIENT;
    private String DELCODECL;
    private String DELNAME;
    private String DELADDRESS;
    private String DELADDRESS2;
    private String DELZIP;
    private String DELCITY;
    private String COUNTRY;
    private String REFERENCE1;
    private String REFERENCE2;
    private String DELDATE;
    private String COLLECT;
    private String REMARK;
    private String EMAIL;
    private String PHONE;
    private String URLINVOICE;

    public String getDELNAME() {
        return DELNAME;
    }

    public void setDELNAME(String DELNAME) {
        this.DELNAME = DELNAME;
    }

    public String getTIMESTAMP() {
        return TIMESTAMP;
    }

    public void setTIMESTAMP(String TIMESTAMP) {
        this.TIMESTAMP = TIMESTAMP;
    }

    public int getCLIENT() {
        return CLIENT;
    }

    public void setCLIENT(int CLIENT) {
        this.CLIENT = CLIENT;
    }

    public String getDELCODECL() {
        return DELCODECL;
    }

    public void setDELCODECL(String DELCODECL) {
        this.DELCODECL = DELCODECL;
    }

    public String getDELADDRESS() {
        return DELADDRESS;
    }

    public void setDELADDRESS(String DELADDRESS) {
        this.DELADDRESS = DELADDRESS;
    }

    public String getDELADDRESS2() {
        return DELADDRESS2;
    }

    public void setDELADDRESS2(String DELADDRESS2) {
        this.DELADDRESS2 = DELADDRESS2;
    }

    public String getDELZIP() {
        return DELZIP;
    }

    public void setDELZIP(String DELZIP) {
        this.DELZIP = DELZIP;
    }

    public String getDELCITY() {
        return DELCITY;
    }

    public void setDELCITY(String DELCITY) {
        this.DELCITY = DELCITY;
    }

    public String getCOUNTRY() {
        return COUNTRY;
    }

    public void setCOUNTRY(String COUNTRY) {
        this.COUNTRY = COUNTRY;
    }

    public String getREFERENCE1() {
        return REFERENCE1;
    }

    public void setREFERENCE1(String REFERENCE1) {
        this.REFERENCE1 = REFERENCE1;
    }

    public String getREFERENCE2() {
        return REFERENCE2;
    }

    public void setREFERENCE2(String REFERENCE2) {
        this.REFERENCE2 = REFERENCE2;
    }

    public String getDELDATE() {
        return DELDATE;
    }

    public void setDELDATE(String DELDATE) {
        this.DELDATE = DELDATE;
    }

    public String getCOLLECT() {
        return COLLECT;
    }

    public void setCOLLECT(String COLLECT) {
        this.COLLECT = COLLECT;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public String getURLINVOICE() {
        return URLINVOICE;
    }

    public void setURLINVOICE(String URLINVOICE) {
        this.URLINVOICE = URLINVOICE;
    }
}
