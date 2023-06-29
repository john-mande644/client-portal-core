package com.owd.core.business;

import com.owd.core.OWDUtilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 2/9/13
 * Time: 1:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class PackageRate {
private final static Logger log =  LogManager.getLogger();

    public String methodName;
    public String methodCode;
    public double finalRate=0.00;
    public double originalDiscount=0.00;
    public double finalDiscount=0.00;
    public double fuelSurcharge=0.00;
    public double residentialCharge=0.00;
    public double otherCharge=0.00;
    public double originalRate=0.00;
    public double baseCharge=0.00;
    public int errorCode = 0;
    public String errorMessage = "";

    @Override
    public String toString() {
        return "PackageRate{" +
                "methodName='" + methodName + '\'' +
                ", methodCode='" + methodCode + '\'' +
                ", finalRate=" + finalRate +
                ", originalDiscount=" + originalDiscount +
                ", finalDiscount=" + finalDiscount +
                ", fuelSurcharge=" + fuelSurcharge +
                ", residentialCharge=" + residentialCharge +
                ", otherCharge=" + otherCharge +
                ", originalRate=" + originalRate +
                ", baseCharge=" + baseCharge +
                ", errorCode=" + errorCode +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


    public double getOriginalRate() {
        return originalRate;
    }

    public void setOriginalRate(double originalRate) {
        this.originalRate = OWDUtilities.roundDouble(originalRate);
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodCode() {
        return methodCode;
    }

    public void setMethodCode(String methodCode) {
        this.methodCode = methodCode;
    }

    public double getFinalRate() {
        return finalRate;
    }

    public void setFinalRate(double finalRate) {
        this.finalRate = OWDUtilities.roundDouble(finalRate);
    }

    public double getOriginalDiscount() {
        return originalDiscount;
    }

    public void setOriginalDiscount(double originalDiscount) {
        this.originalDiscount = OWDUtilities.roundDouble(originalDiscount);
    }

    public double getFinalDiscount() {
        return finalDiscount;
    }

    public void setFinalDiscount(double finalDiscount) {
        this.finalDiscount = OWDUtilities.roundDouble(finalDiscount);
    }

    public double getFuelSurcharge() {
        return fuelSurcharge;
    }

    public void setFuelSurcharge(double fuelSurcharge) {
        this.fuelSurcharge = OWDUtilities.roundDouble(fuelSurcharge);
    }

    public double getResidentialCharge() {
        return residentialCharge;
    }

    public void setResidentialCharge(double residentialCharge) {
        this.residentialCharge = OWDUtilities.roundDouble(residentialCharge);
    }

    public double getOtherCharge() {
        return otherCharge;
    }

    public void setOtherCharge(double otherCharge) {
        this.otherCharge = OWDUtilities.roundDouble(otherCharge);
    }

    public double getBaseCharge() {
        return baseCharge;
    }

    public void setBaseCharge(double baseCharge) {
        this.baseCharge = OWDUtilities.roundDouble(baseCharge);
    }
}
