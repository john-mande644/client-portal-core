package com.owd.web.internal.orderhistory;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.ParameterNameAware;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Aug 19, 2008
 * Time: 9:03:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class LookupAction  extends ActionSupport implements ParameterNameAware {
private final static Logger log =  LogManager.getLogger();


     public boolean acceptableParameterName(String parameterName) {
        boolean retVal = true;
        if (parameterName != null && parameterName.startsWith("d-"))
            if (parameterName.length() > 2) {
                String thirdCharacter = parameterName.substring(2, 3);
                if (StringUtils.isNumeric(thirdCharacter)) {
                    retVal = false;
                }
            }
        return retVal;
    }

    String owdOrderReference;


    public String doFind()
    {
        String response = SUCCESS;

        List pickHistory;


        return response;
        
    }

     public String doInput() {
        return INPUT;
    }

    public String getOwdOrderReference() {
        return owdOrderReference;
    }

    public void setOwdOrderReference(String owdOrderReference) {
        this.owdOrderReference = owdOrderReference;
    }

    public class orderHistory
    {

    }

    public class itemPickHistory
    {

    }

    public class boxPackHistory
    {

    }
}
