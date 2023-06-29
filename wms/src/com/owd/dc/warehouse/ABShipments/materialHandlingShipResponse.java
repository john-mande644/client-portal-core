package com.owd.dc.warehouse.ABShipments;

public class materialHandlingShipResponse {

    private String message="";
    private String label;
    private String tracking;
    private boolean success = false;
    private boolean forceHospital = false;
    private String lpn;


    public String getLpn() {
        return lpn;
    }

    public void setLpn(String lpn) {
        this.lpn = lpn;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTracking() {
        return tracking;
    }

    public void setTracking(String tracking) {
        this.tracking = tracking;
    }

    public boolean isForceHospital() {
        return forceHospital;
    }

    public void setForceHospital(boolean forceHospital) {
        this.forceHospital = forceHospital;
    }
}
