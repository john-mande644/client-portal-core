package com.owd.dc.warehouse.locationManagement;

import com.opensymphony.xwork2.ActionSupport;
import com.owd.dc.warehouse.locationManagement.Utilities.Wizard.locationWizardBean;
import com.owd.dc.warehouse.locationManagement.Utilities.Wizard.locationWizardLoop;
import com.owd.dc.warehouse.locationManagement.Utilities.locationInfoLookupUtil;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.apache.struts2.interceptor.SessionAware;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: 8/19/11
 * Time: 10:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class locationWizardAction extends ActionSupport implements SessionAware {
    private List<locationWizardBean> locationBeansList = new ArrayList<locationWizardBean>();
    private String StartParentId;

    private String OriginalType;
    private String StartName;
    private String type;
    private int numberToDo;
    private int startNumber;
    private boolean doSequence = false;
    private String nameList;
    private TreeMap<String, String> allowedChildren;
    private Map session;
    private String typeString;
    private boolean canProcess = false;
    private int removeIndex;

    private void pullSession() {
        System.out.println("in pull session size: " + session.size());
        if (session.containsKey("beans")) {
            locationBeansList = (ArrayList<locationWizardBean>) session.get("beans");
        }
        if (session.containsKey("allowed")) {
            allowedChildren = (TreeMap<String, String>) session.get("allowed");
        }
        if (session.containsKey("StartParent")) {
            StartParentId = session.get("StartParent").toString();
            System.out.println("We are in the session stuff: here is the start id " + StartParentId);
        } else {
            System.out.println("negatory on the StartParent key");
        }
        if (session.containsKey("OriginalType")) {
            OriginalType = getSession().get("OriginalType").toString();
        }
    }

    public String createLocations() {
        try {
            pullSession();
            if (locationBeansList.size() < 1) {
                throw new Exception("Something is not right. Please start over");
            }
            locationWizardLoop loop = new locationWizardLoop(0, locationBeansList, Integer.parseInt(StartParentId), HibernateSession.currentSession());
            System.out.println("Doing loop now");
            loop.doLoop();
            HibUtils.commit(HibernateSession.currentSession());


        } catch (Exception e) {
            e.printStackTrace();
            addActionError(e.getMessage());
            return "error";
        }


        return "success";
    }

    public String removeFromBean() {
        pullSession();
        System.out.println("This is what we are removing: " + removeIndex);
        int removeStart = locationBeansList.size() - 1;
        System.out.println("This is the starting spot: " + removeStart);
        while (removeStart >= removeIndex) {
            System.out.println("Removing: " + removeStart);
            locationBeansList.remove(removeStart);
            removeStart = removeStart - 1;
        }
        System.out.println("size of bean: " + locationBeansList.size());
        getSession().remove("beans");
        getSession().put("beans", locationBeansList);

        try {
            String ThisType = OriginalType;
            if (locationBeansList.size() > 0) {
                ThisType = locationBeansList.get(locationBeansList.size() - 1).getType() + "";
            }
            allowedChildren = new TreeMap<String, String>();
            locationInfoLookupUtil.loadAllowedChildren(HibernateSession.currentSession(), allowedChildren, ThisType + "");
            getSession().put("allowed", allowedChildren);
        } catch (Exception e) {
            e.printStackTrace();
            addActionError(e.getMessage());
        }
        if (locationBeansList.size() > 0) {
            canProcess = true;
        }
        return "success";
    }

    public String addToBean() {
        System.out.println("session size: " + session.size());
        pullSession();
        System.out.println("This is how big the bean list is: " + locationBeansList.size());
        locationWizardBean lwb = new locationWizardBean();
        lwb.setDoSequence(doSequence);
        lwb.setNameList(nameList);
        lwb.setNumberToDo(numberToDo);
        lwb.setStartNumber(startNumber);
        lwb.setType(Integer.parseInt(type));
        lwb.setTypeString(typeString);
        System.out.println("This is the type we are adding" + type);
        System.out.println("This is what the dosequence is" + doSequence);
        System.out.println("Adding bean to list");
        locationBeansList.add(lwb);
        getSession().remove("beans");
        getSession().put("beans", locationBeansList);
        System.out.println("Done Adding");
        try {
            allowedChildren = new TreeMap<String, String>();
            locationInfoLookupUtil.loadAllowedChildren(HibernateSession.currentSession(), allowedChildren, type + "");
            getSession().put("allowed", allowedChildren);
        } catch (Exception e) {
            e.printStackTrace();
            addActionError(e.getMessage());
        }
        System.out.println("This is how big the bean list is now: " + locationBeansList.size());
        if (locationBeansList.size() > 0) {
            canProcess = true;
        }
        return "success";
    }

    public String execute() {
        session.clear();

        if (StartParentId == null || OriginalType == null) {
            addActionError("Defaults are not set, please go back and resubmit request");
        }
        //load the allowed children for Start parent Id and original type
        try {
            allowedChildren = new TreeMap<String, String>();
            locationInfoLookupUtil.loadAllowedChildren(HibernateSession.currentSession(), allowedChildren, OriginalType);
            session.put("allowed", allowedChildren);
            session.put("StartParent", StartParentId);

            System.out.println("Checking partent store: " + session.get("StartParent"));
        } catch (Exception e) {
            e.printStackTrace();
            addActionError(e.getMessage());
        }

        return "success";
    }


    public List<locationWizardBean> getLocationBeansList() {
        return locationBeansList;
    }

    public void setLocationBeansList(List<locationWizardBean> locationBeansList) {
        this.locationBeansList = locationBeansList;
    }

    public String getStartParentId() {
        return StartParentId;
    }

    public void setStartParentId(String startParentId) {
        StartParentId = startParentId;
    }

    public String getOriginalType() {
        return OriginalType;
    }

    public void setOriginalType(String originalType) {
        OriginalType = originalType;
    }

    public String getStartName() {
        return StartName;
    }

    public void setStartName(String startName) {
        StartName = startName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNumberToDo() {
        return numberToDo;
    }

    public void setNumberToDo(int numberToDo) {
        this.numberToDo = numberToDo;
    }

    public int getStartNumber() {
        return startNumber;
    }

    public void setStartNumber(int startNumber) {
        this.startNumber = startNumber;
    }

    public boolean isDoSequence() {
        return doSequence;
    }

    public void setDoSequence(boolean doSequence) {
        this.doSequence = doSequence;
    }

    public boolean isCanProcess() {
        return canProcess;
    }

    public void setCanProcess(boolean canProcess) {
        this.canProcess = canProcess;
    }

    public String getNameList() {
        return nameList;
    }

    public void setNameList(String nameList) {
        this.nameList = nameList;
    }

    public TreeMap<String, String> getAllowedChildren() {
        return allowedChildren;
    }

    public void setAllowedChildren(TreeMap<String, String> allowedChildren) {
        this.allowedChildren = allowedChildren;
    }

    public void setSession(Map session) {

        this.session = session;

    }


    public Map getSession() {

        return this.session;

    }

    public String getTypeString() {
        return typeString;
    }

    public void setTypeString(String typeString) {
        this.typeString = typeString;
    }

    public int getRemoveIndex() {
        return removeIndex;
    }

    public void setRemoveIndex(int removeIndex) {
        this.removeIndex = removeIndex;
    }
}
