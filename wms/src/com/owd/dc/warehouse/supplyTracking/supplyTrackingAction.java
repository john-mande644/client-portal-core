package com.owd.dc.warehouse.supplyTracking;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.owd.WMSUtils;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 9/17/12
 * Time: 3:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class supplyTrackingAction extends ActionSupport {
    private Object jsonModel;
    private List<String> countTypes;
    private List<String> groupTypes;
    private List<String> emails;
    private List<String> facilities;
    private String invId;
    private supplyTrackingBean stb;
    private String input;
    private int qty;
    private String selectedGroup;
    private List<supplyTrackingBean> trackedItems;
    private boolean getQty = false;
    private String theError;
    private String name;
    private String userId;
    private String currentFacility="";

    public String getQtyHH(){
          try{
            if(qty==0){
                theError = "Please enter a valid qty";
                return "error";
            }
              supplyTrackingUtils.saveAdjust(name,invId,qty,HibernateSession.currentSession(),currentFacility);
                  System.out.println("llslslsllslslsslslslslslslslsl");

                  supplyTrackingBean sbb = new supplyTrackingBean();
                  sbb.load(Integer.parseInt(invId),HibernateSession.currentSession());
              addActionMessage("Used "+qty +" of " +sbb.getInventoryNum());
                  supplyTrackingUtils.sendThresholdEmailsifNeeded(sbb,HibernateSession.currentSession(),currentFacility);

          }catch(Exception e){
              theError = e.getMessage();
              return "error";
          }

        return "success";
    }

    public String getSku(){
        try{
           if(invId==null){
               theError = "You must supply and Id";
               return "error";
           }
            if(currentFacility.equals("")){
                currentFacility = WMSUtils.getDefaultFacilityFromContext(ActionContext.getContext());
            }
         stb = new supplyTrackingBean();
            stb.load(invId,HibernateSession.currentSession());

        } catch (Exception e){
            theError = e.getMessage();
            return "error";
        }

        return "success";
    }

    public String endRecord(){
         if(invId!=null&&qty>0&&!currentFacility.equals("")){
             try{
                 if(name.length()==0) name="Unknown";
               supplyTrackingUtils.saveAdjust(name,invId,qty,HibernateSession.currentSession(),currentFacility);
                 System.out.println("llslslsllslslsslslslslslslslsl");
                 addActionMessage("Success");
                 supplyTrackingBean sbb = new supplyTrackingBean();
                 sbb.load(Integer.parseInt(invId),HibernateSession.currentSession());
                 supplyTrackingUtils.sendThresholdEmailsifNeeded(sbb,HibernateSession.currentSession(),currentFacility);
                 groupTypes = supplyTrackingUtils.loadGroupTypes(HibernateSession.currentSession(),currentFacility);
             }catch (Exception e){

                 theError = e.getMessage();

                 try{
                 HibUtils.rollback(HibernateSession.currentSession());
                 System.out.println("erroring" + e.getMessage());

                 stb = new supplyTrackingBean();

                   System.out.println("12");
                          stb.load(Integer.parseInt(invId),HibernateSession.currentSession());
                     System.out.println("13");
                 }catch (Exception ex){
                      ex.printStackTrace();
                 }
                 getQty = true;
                 System.out.println("14");

             }
         }
        System.out.println(invId);
        System.out.println(qty);

        return "success";
    }
    public String selectItem(){
        if(invId==null){
           addActionError("error");
        }else{
            try{
                stb = new supplyTrackingBean();
            stb.load(Integer.parseInt(invId),HibernateSession.currentSession());
            getQty = true;
            }catch(Exception e){
                addActionError(e.getMessage());
            }
        }

        return "success";
    }
    public String selectGroup(){
                if(selectedGroup!=null){
                    try{
                        trackedItems = supplyTrackingUtils.loadItemsForGroup(selectedGroup, ActionContext.getContext().getSession().get("owd_default_location").toString(),HibernateSession.currentSession());


                    } catch (Exception e){
                        addActionError(e.getMessage());
                    }
                }

        return "success";
    }
    public String startRecord(){
            try{
               if(currentFacility.equals("")){
                   currentFacility = WMSUtils.getDefaultFacilityFromContext(ActionContext.getContext());
               }


                Session sess = HibernateSession.currentSession();
                if(userId!=null){
               name=supplyTrackingUtils.getNameforId(userId,sess);
                }
             groupTypes = supplyTrackingUtils.loadGroupTypes(sess,currentFacility);
            }catch(Exception e){
                addActionError(e.getMessage());
            }

        return "success";
    }

    public String execute() {
        try {
            if(currentFacility.equals("")){
                currentFacility = WMSUtils.getDefaultFacilityFromContext(ActionContext.getContext());
            }
            Session sess = HibernateSession.currentSession();
            countTypes = supplyTrackingUtils.loadCountType(sess);
            groupTypes = supplyTrackingUtils.loadGroupTypes(sess,currentFacility);
            emails = supplyTrackingUtils.loadEmails(sess);
            facilities = supplyTrackingUtils.loadFacilities(sess);
            if(userId!=null){
                name= supplyTrackingUtils.getNameforId(userId,sess);
            } else{
                name="System";
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return "success";
    }

    public String getItems() {
        JSONsupplyTrackingbean JSTB = new JSONsupplyTrackingbean();
        try {

            JSTB = supplyTrackingUtils.getTrackedItems(HibernateSession.currentSession());
            setJsonModel(JSTB);

        } catch (Exception e) {
            JSTB.setError(e.getMessage());
            e.printStackTrace();
        }

        return Action.SUCCESS;
    }

    public String checkId() {
        JSONcheckIdBean JSCIB = new JSONcheckIdBean();
        if (invId.length() > 3) {
            try {
                JSCIB = supplyTrackingUtils.checkIdToAdd(invId, HibernateSession.currentSession());
            } catch (Exception e) {
                JSCIB.setError(e.getMessage());
            }
        } else {
            JSCIB.setError("it appears you have not entered anything good to find");
        }
        setJsonModel(JSCIB);
        return Action.SUCCESS;
    }

    public String save() {
        JSONcheckIdBean JSCIB = new JSONcheckIdBean();
        try {
            Session sess = HibernateSession.currentSession();

            System.out.println(stb.getGroupType());
            stb.createNew(sess);
            countTypes = supplyTrackingUtils.loadCountType(sess);
            groupTypes = supplyTrackingUtils.loadGroupTypes(sess,currentFacility);
            emails = supplyTrackingUtils.loadEmails(sess);
            facilities = supplyTrackingUtils.loadFacilities(sess);
            addActionMessage("Saved: " + stb.getInventoryNum());
        } catch (Exception e) {
            addActionError(e.getMessage());
            JSCIB.setError(e.getMessage());
        }
        setJsonModel(JSCIB);
        return "success";
    }

    public String update(){
      System.out.println(stb.getInventoryNum());
      JSONcheckIdBean cidb = new JSONcheckIdBean();

        try{
           stb.update(HibernateSession.currentSession());
        }catch(Exception e){
            cidb.setError(e.getMessage());
        }
        setJsonModel(cidb);
        return Action.SUCCESS;
    }


    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public Object getJsonModel() {
        return jsonModel;
    }

    public void setJsonModel(Object jsonModel) {
        this.jsonModel = jsonModel;
    }

    public List<String> getCountTypes() {
        return countTypes;
    }

    public void setCountTypes(List<String> countTypes) {
        this.countTypes = countTypes;
    }

    public List<String> getGroupTypes() {
        return groupTypes;
    }

    public void setGroupTypes(List<String> groupTypes) {
        this.groupTypes = groupTypes;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public String getInvId() {
        return invId;
    }

    public void setInvId(String invId) {
        this.invId = invId;
    }

    public supplyTrackingBean getStb() {
        return stb;
    }

    public void setStb(supplyTrackingBean stb) {
        this.stb = stb;
    }

    public List<String> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<String> facilities) {
        this.facilities = facilities;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getSelectedGroup() {
        return selectedGroup;
    }

    public void setSelectedGroup(String selectedGroup) {
        this.selectedGroup = selectedGroup;
    }

    public List<supplyTrackingBean> getTrackedItems() {
        return trackedItems;
    }

    public void setTrackedItems(List<supplyTrackingBean> trackedItems) {
        this.trackedItems = trackedItems;
    }

    public boolean isGetQty() {
        return getQty;
    }

    public void setGetQty(boolean getQty) {
        this.getQty = getQty;
    }

    public String getTheError() {
        return theError;
    }

    public void setTheError(String theError) {
        this.theError = theError;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrentFacility() {
        return currentFacility;
    }

    public void setCurrentFacility(String currentFacility) {
        this.currentFacility = currentFacility;
    }
}
