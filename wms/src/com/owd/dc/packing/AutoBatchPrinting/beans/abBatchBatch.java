package com.owd.dc.packing.AutoBatchPrinting.beans;

import com.thoughtworks.xstream.XStream;

import java.util.List;

/**
 * Created by danny on 2/6/2017.
 */
public class abBatchBatch {

    private String client;
    private String fingerprint;
    private String qty;
    private String sla;
    private String WorkingDirectory;
    private String fileName;
    private String fileSplit;


    public String getWorkingDirectory() {
        return WorkingDirectory;
    }

    public void setWorkingDirectory(String workingDirectory) {
        WorkingDirectory = workingDirectory;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSplit() {
        return fileSplit;
    }

    public void setFileSplit(String fileSplit) {
        this.fileSplit = fileSplit;
    }

    public String getSla() {
        return sla;
    }

    public void setSla(String sla) {
        this.sla = sla;
    }

    private List<abLabelData> labels;

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

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public List<abLabelData> getLabels() {
        return labels;
    }

    public void setLabels(List<abLabelData> labels) {
        this.labels = labels;
    }


    public XStream getXStream(){
        XStream x = new XStream();
        x.alias("abLabelData", abLabelData.class);
        x.alias("abBatch",abBatchBatch.class);


        return x;
    }

    public String getXML(){
        return getXStream().toXML(this);

    }
}
