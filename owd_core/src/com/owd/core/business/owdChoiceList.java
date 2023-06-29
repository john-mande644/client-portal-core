package com.owd.core.business;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Vector;


public class owdChoiceList {
private final static Logger log =  LogManager.getLogger();
    String listName = "";
    Vector values = new Vector();
    Vector refNos = new Vector();
    String defaultRef = "";

    public owdChoiceList(String name) {
        listName = name;
        reset();
    }

    public void reset() {
        values = new Vector();
        refNos = new Vector();
        defaultRef = "";
    }

    public int size() {
        return values.size();
    }

    public void addElement(String value, String ref, boolean isDefault) {
        if (value != null & ref != null) {
            value = value.trim();
            ref = ref.trim();
            if (value.length() > 0) {
                values.addElement(value);
                refNos.addElement(ref);
                if (isDefault == true)
                    defaultRef = ref;
            }
        }

    }

    public Vector getValues() {
        return values;
    }

    public String getRefForValue(String value) {
        if (values.indexOf(value) >= 0)
            return (String) refNos.elementAt(values.indexOf(value));
        else {
            value = ChoiceListManager.getTranslatedString(value);
            if (values.indexOf(value) >= 0)
                return (String) refNos.elementAt(values.indexOf(value));
        }

        return null;
    }

    public String getValueForRef(String ref) {
        if (refNos.indexOf(ref) >= 0)
            return (String) values.elementAt(refNos.indexOf(ref));
        else {
            ref = ChoiceListManager.getTranslatedString(ref);
            if (refNos.indexOf(ref) >= 0)
                return (String) values.elementAt(refNos.indexOf(ref));
        }
        return null;
    }

    public String toString() {
        StringBuffer desc = new StringBuffer();

        desc.append("owdChoiceList:" + listName + "\n");
        desc.append("Default ref = " + defaultRef + "\n");
        for (int i = 0; i < values.size(); i++) {
            desc.append(values.elementAt(i).toString() + ":" + refNos.elementAt(i).toString() + "\n");
        }

        return desc.toString();
    }

    public String getHTMLSelect(String defaultValue, String selectName) {
        StringBuffer sb = new StringBuffer();

        sb.append("<SELECT NAME=\"" + selectName + "\" ID=\"" + selectName + "\">");

        for (int i = 0; i < values.size(); i++) {
            String value = (String) values.elementAt(i);
            if (value.equals(defaultValue)) {
                sb.append("<OPTION VALUE=\"" + value + "\" SELECTED>" + value);
            } else {
                sb.append("<OPTION VALUE=\"" + value + "\">" + value);
            }
        }

        sb.append("</SELECT>");
        return sb.toString();

    }


}
