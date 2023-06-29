package com.owd.dc.beans;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: May 4, 2009
 * Time: 3:46:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class jsonNewLocationBean {
    private String error = "";
    private String message = "";
    private String newId = "";
    private String newName = "";


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNewId() {
        return newId;
    }

    public void setNewId(String newId) {
        this.newId = newId;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }
}
