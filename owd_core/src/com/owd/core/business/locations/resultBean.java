package com.owd.core.business.locations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Jun 23, 2009
 * Time: 2:46:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class resultBean {
private final static Logger log =  LogManager.getLogger();
    private boolean success = false;
    private List<String> message = new ArrayList<String>();
    private List<String> error = new ArrayList<String>();

    


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void addMessage(String s){
        message.add(s);
    }
    public void addError(String s){
        error.add(s);
    }
    public String getMessages(){
            StringBuffer sb = new StringBuffer();
        for(String s:message){
             sb.append(s);
            if (message.indexOf(s)!= message.size()-1){
              sb.append(" | ");
        }

        }
        return sb.toString();
    }
     public String getErrors(){
            StringBuffer sb = new StringBuffer();
        for(String s:error){
             sb.append(s);
            if (error.indexOf(s)!= error.size()-1){
              sb.append(" | ");
        }

        }
        return sb.toString();
    }
}
