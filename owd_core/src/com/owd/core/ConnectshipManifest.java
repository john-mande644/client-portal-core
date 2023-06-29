package com.owd.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by stewartbuskirk1 on 1/29/16.
 */
public class ConnectshipManifest {
    private final static Logger log =  LogManager.getLogger();

    String carrierName;
    String carrierCode;
    String dateLabel;
    String manifestCode;

    public ConnectshipManifest(String carrierName, String carrierCode, String dateLabel, String manifestCode) {
        this.carrierName = carrierName;
        this.carrierCode = carrierCode;
        this.dateLabel = dateLabel;
        this.manifestCode = manifestCode;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    public String getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

    public String getDateLabel() {
        return dateLabel;
    }

    public void setDateLabel(String dateLabel) {
        this.dateLabel = dateLabel;
    }

    public String getManifestCode() {
        return manifestCode;
    }

    public void setManifestCode(String manifestCode) {
        this.manifestCode = manifestCode;
    }

    @Override
    public String toString() {
        return "ConnectshipManifest{" +
                "carrierName='" + carrierName + '\'' +
                ", carrierCode='" + carrierCode + '\'' +
                ", dateLabel='" + dateLabel + '\'' +
                ", manifestCode='" + manifestCode + '\'' +
                '}';
    }
}
