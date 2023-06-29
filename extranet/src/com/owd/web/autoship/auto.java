package com.owd.web.autoship;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import org.apache.struts2.interceptor.PrincipalAware;
import org.apache.struts2.interceptor.PrincipalProxy;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Jan 24, 2008
 * Time: 10:19:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class auto extends ActionSupport implements Preparable, SessionAware, PrincipalAware {
private final static Logger log =  LogManager.getLogger();
    private PrincipalProxy principal;
    public void setPrincipalProxy(PrincipalProxy proxy){
        principal = proxy;
    }
    public PrincipalProxy getPrincipalProxy(){
        return principal;
    }
    private Map _session ;
   public void setSession(Map session){
_session =session;
   }
    public Map getSession() {
		return _session;
	}
  
     public static final String MESSAGE = "Struts is up and running ...";

    public void prepare(){

        boolean authenticate = true;
        log.debug("In Prepareing auto");
       Map<String,String> parameters = this.getSession();
        PrincipalProxy princ = this.getPrincipalProxy();

    if(parameters.size()>0) {
        log.debug("session size"+parameters.size());
      for(Map.Entry<String,String> e : parameters.entrySet())

    log.debug(e.getKey()+": "+e.getValue());
        try{
            if(parameters.get(autoUtil.kExtranetClientID).equals(oldClientId)){
                log.debug("Authenticating good");
                authenticate=false;
            }
        } catch(Exception e){

        }

    }
     if(authenticate){
         
         autoUtil.authenticateUser(parameters,princ.getRemoteUser());
     }
         log.debug(princ.getRemoteUser());
         
    }


    public String execute() throws Exception {
        setMessage(MESSAGE);
        return SUCCESS;
    }
    private String oldClientId;
    private String clientName;

    private String message;


    public String getOldClientId() {
        return oldClientId;
    }

    public void setOldClientId(String oldClientId) {
        this.oldClientId = oldClientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    
}
