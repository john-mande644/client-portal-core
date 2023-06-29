package com.owd.web.internal.callcenter.clients;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.opensymphony.xwork2.ActionSupport;
import com.owd.hibernate.generated.CcScoutsCanada;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.core.Mailer;
import com.owd.web.internal.callcenter.screenPopUtils;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Aug 11, 2009
 * Time: 9:00:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class scoutsCanadaAction extends ActionSupport {
private final static Logger log =  LogManager.getLogger();

    private String name;
    private String phone;
    private String email;
    private String zip;
    private String notes;
    private String callid;
    private CcScoutsCanada scouts;

    public String start(){
        return ActionSupport.SUCCESS;
    }
    public String execute(){
        CcScoutsCanada ccs = new CcScoutsCanada();
        if(name.length()<1){
            addActionError("Name cannot be blank");
            return ActionSupport.ERROR;
        }
        if(phone.length()<1 && email.length()<1){
            addActionError("Phone and email cannot be blank");
            return ActionSupport.ERROR;
        }
        ccs.setName(name);
        ccs.setPhone(phone);
        ccs.setEmail(email);
        ccs.setZip(zip);
        ccs.setNotes(notes);
        ccs.setCallid(callid);
        try{
              HibernateSession.currentSession().save(ccs);
            HibUtils.commit(HibernateSession.currentSession());
            scouts = ccs;

            StringBuffer sb = new StringBuffer();

             sb.append("Name: ");
             sb.append(scouts.getName());
            sb.append("\r\n");
            sb.append("Phone: ");
               sb.append(scouts.getPhone());
            sb.append("\r\n");
            sb.append("Email: ");
                 sb.append(scouts.getEmail());
           sb.append("\r\n");
            sb.append("Postal Code: ");
                 sb.append(scouts.getZip());
         sb.append("\r\n");
            sb.append("Notes: ");
                sb.append(scouts.getNotes());
            sb.append("\r\n");
            Mailer.postMailMessage("Additional Information Request: "+scouts.getName(),sb.toString(), screenPopUtils.getEmailToCanada(HibernateSession.currentSession()),"scoutscanada@owd.com");


        } catch(Exception e){
            e.printStackTrace();
            addActionError(e.getMessage());
            return ActionSupport.ERROR;
        }


        return ActionSupport.SUCCESS;
    }



    public CcScoutsCanada getScouts() {
        return scouts;
    }

    public void setScouts(CcScoutsCanada scouts) {
        this.scouts = scouts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCallid() {
        return callid;
    }

    public void setCallid(String callid) {
        this.callid = callid;
    }
}
