package com.owd.dc.beans;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Apr 17, 2009
 * Time: 4:22:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class locAttributeBean {
    private String id;
    private String value;
    private String name;

    public String printableValues() {
        StringBuffer sb = new StringBuffer();
        sb.append("Id: ");
        sb.append(id);
        sb.append("; Value: ");
        sb.append(value);
        sb.append("; Name: ");
        sb.append(name);

        return sb.toString();
    }

    public locAttributeBean() {

    }

    public locAttributeBean(String Id, String Value, String Name) {
        id = Id;
        value = Value;
        name = Name;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
