package com.owd.web.internal.warehouse;

import com.opensymphony.xwork2.ActionSupport;
import com.owd.core.MailAddressValidator;
import com.owd.core.Mailer;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;

import java.util.*;

/**
 * Created by danny on 8/12/2016.
 */
public class markShippedBatchAction extends ActionSupport{

    private int counter = 0;
    private int numLeft = 0;
    private int total = 0;
    private int processed = 0;
    private String addToTracking = "";
    private String clientId = "";
    private String theList = "";
    private String theListContains = "";
    private List<String> theProcessList;
    private String lastProcessed = "";
    private List<String> containsList ;
    private List<String> actionList;
    private List<String> errorOrderList = new ArrayList<String>();
    private String email="";
    public Map<String,String> clients = new TreeMap<String,String>();
    public String groupName ="";
    public String theAction = "";
    public String starting(){
        try{
          String sql = "select client_id,company_name from owd_client where is_active = 1 order by company_name";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            List l = q.list();
            if(l.size()>0){
                for(Object row: l){
                    Object[] data = (Object[]) row;
                    clients.put(data[0].toString(),data[1].toString());
                }
            }else{
                throw new Exception("Unable to load clients ");
            }
        }catch(Exception e){
            addActionError(e.getMessage());
            e.printStackTrace();
        }

        return SUCCESS;
    }



    public String processBatch() throws Exception{
        int cid ;
        try{
            cid = Integer.parseInt(clientId);
        }catch (NumberFormatException e){
            throw new Exception("Please enter a valid client Id");

        }
    System.out.println("hello");
        System.out.println(theList.length());
       if(theList.length()>0){
           theProcessList = splitStringIntoList(theList);
           total = theProcessList.size();
           numLeft = total;

           for(String s : theProcessList){
               System.out.println(s);
               numLeft --;
               processed++;
               lastProcessed=s;
               counter = 0;

                    if(theAction.equals("Set Group Name")|| theAction.equals("Set Group and Mark Shipped")){
                        if(theListContains.equals("Client Ref")) {
                            boolean good = false;
                            if(groupName.length()>0) {
                                good = batchManualShipUtilities.addGroupName(cid, s, groupName);
                            }else{
                                addActionError("Group Name is blank");
                                return ERROR;
                            }
                            if(!good){
                                errorOrderList.add(s);
                            }

                        }


                    }

                    if(theAction.equals("Mark Shipped")||theAction.equals("Set Group and Mark Shipped")){
                        if(theListContains.equals("Client Ref")) {
                            boolean good = batchManualShipUtilities.markAsShipped(cid,s,addToTracking);
                            if(!good){
                                errorOrderList.add(s);
                            }

                        }
                    }


           }
           System.out.println(total);



            System.out.println(errorOrderList);
           if(email.length()>0 & MailAddressValidator.isValid(email)){
               StringBuilder sb = new StringBuilder();
               sb.append("We Processed ");
               sb.append(total);
               sb.append(" orders. We had issues with ");
               sb.append(errorOrderList.size());
               sb.append("\r\n Any problem order are listed below: \r\n");
               for(String s: errorOrderList){
                   sb.append(s);
                   sb.append("\r\n");
               }
               Mailer.sendMail("Mark Shipped Batch Summary",sb.toString(),email,"no-reply@owd.com");
           }
       }else{
           addActionError("You Must have soemthing in the list to mark as shipped");
       }


            if(hasActionErrors()){
                return ERROR;
            }
        return SUCCESS;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getAddToTracking() {
        return addToTracking;
    }

    public void setAddToTracking(String addToTracking) {
        this.addToTracking = addToTracking;
    }

    public int getNumLeft() {
        return numLeft;
    }

    public void setNumLeft(int numLeft) {
        this.numLeft = numLeft;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getProcessed() {
        return processed;
    }

    public void setProcessed(int processed) {
        this.processed = processed;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getTheList() {
        return theList;
    }

    public void setTheList(String theList) {
        this.theList = theList;
    }

    public String getTheListContains() {
        return theListContains;
    }

    public void setTheListContains(String theListContains) {
        this.theListContains = theListContains;
    }

    public List<String> getTheProcessList() {
        return theProcessList;
    }

    public void setTheProcessList(List<String> theProcessList) {
        this.theProcessList = theProcessList;
    }
    public  List<String> splitStringIntoList(String s){
        s= s.replaceAll("\n",",");

        return Arrays.asList(s.split("\\s*,\\s*"));
    }

    public String getLastProcessed() {
        return lastProcessed;
    }

    public void setLastProcessed(String lastProcessed) {
        this.lastProcessed = lastProcessed;
    }

    public List<String> getContainsList() {
        if(null == containsList){
            containsList = new ArrayList<String>();
            containsList.add("Client Ref");
            containsList.add("OWD Ref");
            containsList.add("OWD Order Id");
        }
        return containsList;
    }

    public void setContainsList(List<String> containsList) {
        this.containsList = containsList;
    }

    public List<String> getErrorOrderList() {
        return errorOrderList;
    }

    public void setErrorOrderList(List<String> errorOrderList) {
        this.errorOrderList = errorOrderList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, String> getClients() {
        return clients;
    }

    public void setClients(Map<String, String> clients) {
        this.clients = clients;
    }

    public List<String> getActionList() {
        if(null == actionList){
            actionList = new ArrayList<>();
            actionList.add("Mark Shipped");
            actionList.add("Set Group Name");
            actionList.add("Set Group and Mark Shipped");

        }
        return actionList;
    }

    public void setActionList(List<String> actionList) {
        this.actionList = actionList;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
