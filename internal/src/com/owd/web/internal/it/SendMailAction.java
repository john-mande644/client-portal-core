package com.owd.web.internal.it;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.opensymphony.xwork2.ActionSupport;
import com.owd.hibernate.HibernateFogbugzSession;
import com.owd.hibernate.HibUtils;
import com.owd.core.Mailer;

import java.util.*;
import java.sql.ResultSet;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jul 2, 2008
 * Time: 2:02:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class SendMailAction extends ActionSupport {
private final static Logger log =  LogManager.getLogger();

   String toAddress = "owditadmin@owd.com";
    String subject = "subject";
    String body="";
    String instructions="Write your report. Remember that this is being sent directly to the client!";
    String title="Form Title";

    public String send()
        {

           try
           {
          Mailer.sendMail(subject,body,toAddress,"casetracker@owd.com");
           }catch(Exception ex)
           {
               ex.printStackTrace();
               addActionError("Unable to send message: "+ex.getMessage());
               return INPUT;
           }
          return SUCCESS;
        }


       public String input()
        {
            return INPUT;

        }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
