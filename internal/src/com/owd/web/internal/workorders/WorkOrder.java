package com.owd.web.internal.workorders;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Sep 21, 2004
 * Time: 1:26:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class WorkOrder {
private final static Logger log =  LogManager.getLogger();

    int ageInDays = 0;
    Date created;
    int priorityLevel = 0;
    String assignedTo = "";
    String title = "";
    int bugID = 0;
    String status = "";

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    String category = "";
    String area = "";

    public float getEstimateHours() {
        return estimateHours;
    }

    public void setEstimateHours(float estimateHours) {
        this.estimateHours = estimateHours;
    }

    public float getElapsedHours() {
        return elapsedHours;
    }

    public void setElapsedHours(float elapsedHours) {
        this.elapsedHours = elapsedHours;
    }

    public float getBillableHours() {
        return billableHours;
    }

    public void setBillableHours(float billableHours) {
        this.billableHours = billableHours;
    }

    float estimateHours = 0.00f;
    float elapsedHours = 0.00f;
    float billableHours = 0.00f;


    public int getAgeInDays() {
        return ageInDays;
    }

    public void setAgeInDays(int ageInDays) {
        this.ageInDays = ageInDays;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBugID() {
        return bugID;
    }

    public void setBugID(int bugID) {
        this.bugID = bugID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    String project = "";
    String clientName = "";
    int clientID = 0;


}
