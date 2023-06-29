package com.owd.dc.warehouse.statusScroller;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 5/27/14
 * Time: 5:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class scrollerMessageBean {
    private String DT_RowId;
    private String id;
    private String facility;
    private String start;
    private String end;
    private String message;
    private int priority;


    public String getDT_RowId() {
        return DT_RowId;
    }

    public void setDT_RowId(String DT_RowId) {
        this.DT_RowId = DT_RowId;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.DT_RowId = id;
        this.id = id;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
