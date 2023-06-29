package com.owd.core.business.intacct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jan 31, 2006
 * Time: 9:58:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class IntacctRequest {
private final static Logger log =  LogManager.getLogger();


    List functionList = new ArrayList();
    boolean useControlID=false;
    String controlID="";

    public boolean isUseControlID() {
        return useControlID;
    }

    public void setUseControlID(boolean useControlID) {
        this.useControlID = useControlID;
    }

    public String getControlID() {
        return controlID;
    }

    public void setControlID(String controlID) {
        this.controlID = controlID;
    }



public void addFunction(String fxnXML)
    {

        functionList.add(fxnXML);
    }
    public List getFunctionList() {
        return (functionList);
    }


    public void setFunctionList(List functionList) {
        this.functionList = functionList;
    }


}
