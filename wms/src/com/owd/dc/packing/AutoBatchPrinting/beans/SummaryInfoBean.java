package com.owd.dc.packing.AutoBatchPrinting.beans;

/**
 * Created by danny on 2/4/2017.
 */
public class SummaryInfoBean {

    private String client;
    private String fingerprint;
    private String sla;
    private String qty;

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public String getSla() {
        return sla;
    }

    public void setSla(String sla) {
        this.sla = sla;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
