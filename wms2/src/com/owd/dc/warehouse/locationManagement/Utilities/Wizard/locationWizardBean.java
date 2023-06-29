package com.owd.dc.warehouse.locationManagement.Utilities.Wizard;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: 8/17/11
 * Time: 1:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class locationWizardBean {

    int type;
    int numberToDo;
    int startNumber;
    boolean doSequence = false;
    String nameList;
    String user;
    String ipAddress;
    String typeString;
    List<String> locationsToCreateList;

    public List<String> getLocationsToCreateList() {
        List<String> l = new ArrayList<String>();
        if (doSequence) {
            int i = 0;
            int number = startNumber;
            while (i < numberToDo) {
                l.add(number + "");
                i = i + 1;
                number = number + 1;
            }


        } else {
            if (nameList.contains(",")) {

                for (String s : nameList.split(",")) {
                    l.add(s);
                }


            } else {
                for (String s : nameList.split("\r\n")) {
                    l.add(s);

                }


            }


        }


        return l;

    }

    public String getTypeString() {
        return typeString;
    }

    public void setTypeString(String typeString) {
        this.typeString = typeString;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
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

    public String getNameList() {
        return nameList;
    }

    public void setNameList(String nameList) {
        this.nameList = nameList;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
