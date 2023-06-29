package com.owd.web.callcenter;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.struts.action.ActionForm;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Dec 20, 2006
 * Time: 4:21:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class downloadCallForm extends ActionForm {
private final static Logger log =  LogManager.getLogger();
    private String id;
    private String doDown;
    private String oldId;

    public String getOldId() {
        return oldId;
    }

    public void setOldId(String oldId) {
        this.oldId = oldId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoDown() {
        try{
        if(null==doDown||"".equals(doDown)) setDoDown("0");
        }catch (NullPointerException e){
         setDoDown("0");
    }
        return doDown;
    }

    public void setDoDown(String doDown) {
        this.doDown = doDown;
    }
}
