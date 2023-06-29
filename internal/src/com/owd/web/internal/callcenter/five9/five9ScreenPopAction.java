package com.owd.web.internal.callcenter.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.owd.core.Mailer;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdClientCallcenter;
import com.owd.intranet.beans.selectList;


import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Jul 8, 2009
 * Time: 10:26:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class five9ScreenPopAction extends ActionSupport {
private final static Logger log =  LogManager.getLogger();
    private String id;
    private OwdClientCallcenter ccClient;
    private List<selectList> clientList;
    private String orderRef;
    private List<screenPopBean> pageList;
    private String agentfname;
    private String deploy;
    private String eaddress;
    private String body;
    private String send;
    private String five9Scrpt;





    public String email(){
        if(null != send){
        if(send.equals("yes")){
          if(eaddress.length()<2){
          addActionError("Please enter a valid address");
             return Action.SUCCESS;
          }
        try{
            Mailer.postMailMessage("Callcenter Quick form",body, screenPopUtils.getEmailTo(HibernateSession.currentSession()),eaddress);
           addActionMessage("Form Sent!!");
        } catch(Exception e){
            e.printStackTrace();
            addActionError(e.getMessage());

        }

        }
        }
        return Action.SUCCESS;
    }
    public String delete(){
                     try{

                         OwdClientCallcenter cc = screenPopUtils.loadBeanFromId(id,HibernateSession.currentSession());
                        HibernateSession.currentSession().delete(cc);
                         HibUtils.commit(HibernateSession.currentSession());
                         addActionMessage("Removed the page");
                          pageList = screenPopUtils.loadPageList(HibernateSession.currentSession());
                     } catch(Exception e){
                         e.printStackTrace();

                     }
        return Action.SUCCESS;
    }
    public String list(){

                try{
                 pageList = screenPopUtils.loadPageList(HibernateSession.currentSession());
                if(deploy != null){
                 if(deploy.equals("all")){
                 screenPopUtils.createAllFiles(HibernateSession.currentSession());
                 screenPopUtils.createScreenPopIndex(HibernateSession.currentSession());
                 }   }
                } catch(Exception e){
                    e.printStackTrace();

                }
        

        return Action.SUCCESS;
    }
    public String update(){
        log.debug("here we are update");

        log.debug(id);
       // log.debug(ccClient.getTicketbox());
        log.debug(ccClient.getAnnounceScript());
        log.debug(ccClient.getMlogCampaignName());
         clientList = screenPopUtils.getClientList();
        ccClient.setDoEntry(1);
        ccClient.setDoService(1);
        ccClient.setDoStatus(1);
        ccClient.setUrlEntryPop(1);
        ccClient.setUrlKbBasePop(1);
        ccClient.setUrlServicePop(1);
        ccClient.setUrlStatusPop(1);

        if(null == ccClient.getCallThreshold()){
            ccClient.setCallThreshold(150);
        }
        if (orderRef.equals("1")) {
                                     ccClient.setDoOrderRefEntry(1);

                                 } else{
                                     ccClient.setDoOrderRefEntry(0);
                                 }
        try{
        
           HibernateSession.currentSession().saveOrUpdate(ccClient);
            HibUtils.commit(HibernateSession.currentSession());
            addActionMessage(ccClient.getMlogCampaignName()+" saved Successfully");
            if (deploy.equals("1")){
                screenPopUtils.createSingleFile(ccClient.getId(),HibernateSession.currentSession());
                addActionMessage(ccClient.getMlogCampaignName()+" deployed Successfully");
                screenPopUtils.createScreenPopIndex(HibernateSession.currentSession());
            }
             pageList = screenPopUtils.loadPageList(HibernateSession.currentSession());
        } catch(Exception e){
            e.printStackTrace();
             addActionError(e.getMessage());
        }
        return Action.SUCCESS;
    }
    public String test() {
        try{
              ccClient = screenPopUtils.loadBeanFromId(id, HibernateSession.currentSession());
            log.debug(ccClient.getAnnounceScript());
            ccClient.setAnnounceScript(ccClient.getAnnounceScript().replace("<$AGENT_NAME>",agentfname));
               ccClient.setAnnounceScript(ccClient.getAnnounceScript().replace("&lt;$AGENT_NAME&gt;",agentfname));
             if (ccClient.getFrameUrl().length()==0) ccClient.setFrameUrl("http://internal.owd.com");
        } catch(Exception e){
            e.printStackTrace();

        }

        return Action.SUCCESS;
    }
    public String five9Script() {
        try{
              ccClient = screenPopUtils.loadBeanFromId(id, HibernateSession.currentSession());
          //ccClient.setAnnounceScript("<html><head>hello</head><body>hi bye <a ></body></html>");
            five9Scrpt=screenPopUtils.getFive9Script(ccClient);
        /*    log.debug(ccClient.getAnnounceScript());
            ccClient.setAnnounceScript(ccClient.getAnnounceScript().replace("<$AGENT_NAME>",agentfname));
               ccClient.setAnnounceScript(ccClient.getAnnounceScript().replace("&lt;$AGENT_NAME&gt;",agentfname));
             if (ccClient.getFrameUrl().length()==0) ccClient.setFrameUrl("http://internal.owd.com");*/
        } catch(Exception e){
            e.printStackTrace();

        }

        return Action.SUCCESS;
    }
    public String admin(){
       
        try{
            if(id == null){
                log.debug("Doing new one");
                ccClient = new OwdClientCallcenter();
            } else{
              ccClient = screenPopUtils.loadBeanFromId(id, HibernateSession.currentSession());
            }

            log.debug(ccClient.getAnnounceScript());
            clientList = screenPopUtils.getClientList();
           if (ccClient.getFrameUrl().length()==0) ccClient.setFrameUrl("http://internal.owd.com");

        } catch(Exception e){
            e.printStackTrace();

        }


        return Action.SUCCESS;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OwdClientCallcenter getCcClient() {
        return ccClient;
    }

    public void setCcClient(OwdClientCallcenter ccClient) {
        this.ccClient = ccClient;
    }

    public List<selectList> getClientList() {
        return clientList;
    }

    public void setClientList(List<selectList> clientList) {
        this.clientList = clientList;
    }

    public String getOrderRef() {
        return orderRef;
    }

    public void setOrderRef(String orderRef) {
        this.orderRef = orderRef;
    }

    public List<screenPopBean> getPageList() {
        return pageList;
    }

    public void setPageList(List<screenPopBean> pageList) {
        this.pageList = pageList;
}

    public String getAgentfname() {
        return agentfname;
    }

    public void setAgentfname(String agentfname) {
        this.agentfname = agentfname;
    }

    public String getDeploy() {
        return deploy;
    }

    public void setDeploy(String deploy) {
        this.deploy = deploy;
    }

    public String getEaddress() {
        return eaddress;
    }

    public void setEaddress(String eaddress) {
        this.eaddress = eaddress;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSend() {
        return send;
    }

    public void setSend(String send) {
        this.send = send;
    }

    public String getFive9Scrpt() {
        return five9Scrpt;
    }

    public void setFive9Scrpt(String five9Scrpt) {
        this.five9Scrpt = five9Scrpt;
    }
}
