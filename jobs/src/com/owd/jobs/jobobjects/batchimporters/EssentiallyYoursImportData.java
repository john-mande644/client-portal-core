package com.owd.jobs.jobobjects.batchimporters;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Apr 10, 2008
 * Time: 3:00:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class EssentiallyYoursImportData extends OWDUploadOrdersData_1{


    public String translateShipMethod(String oldMethod) {

        if(oldMethod.equalsIgnoreCase("FedEx 3-Day Select")) oldMethod="FedEx Express Saver";
        if(oldMethod.equalsIgnoreCase("FedEx 2Day Air")) oldMethod="FedEx 2Day";

       return oldMethod;
         }
}
