package com.owd.core.business;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.StringTokenizer;

public class Contact {
private final static Logger log =  LogManager.getLogger();


    public Contact() {


    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = (""+name).trim().replaceAll("  "," ");
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Contact(String aname,

                   String aphone,

                   String afax,

                   String aemail,

                   String aurl) {

        if (aname == null) aname = "";

        if (aphone == null) aphone = "";

        if (afax == null) afax = "";

        if (aemail == null) aemail = "";

        if (aurl == null) aurl = "";


        if (aname.equals("null null")) {

            aname = "";

        }


        setName(aname != null ? aname.replace('\'', ' ') : "");

        setPhone(aphone != null ? aphone.replace('\'', ' ') : "");

        setFax(afax != null ? afax.replace('\'', ' ') : "");

        setEmail(aemail != null ? aemail.replace('\'', ' ') : "");

        setUrl(aurl != null ? aurl.replace('\'', ' ') : "");

    }


    public String name = "";

    public String phone = "";

    public String fax = "";

    public String email = "";

    public String url = "";


    public void upperCase() {
        name = name.toUpperCase();
        phone = phone.toUpperCase();
        fax = fax.toUpperCase();
    }

    public String toStorableString() {

        StringBuffer sb = new StringBuffer();

        sb.append(name);

        sb.append(" ~");

        sb.append(phone);

        sb.append(" ~");

        sb.append(fax);

        sb.append(" ~");

        sb.append(email);

        sb.append(" ~");

        sb.append(url);


        return sb.toString();


    }


    public static Contact createFromStorableString(String stored) {

        StringTokenizer tokens = new StringTokenizer(stored, "~");

        Contact contact = new Contact();


        if (tokens.hasMoreTokens())

            contact.name = tokens.nextToken().trim();

        if (tokens.hasMoreTokens())

            contact.phone = tokens.nextToken().trim();

        if (tokens.hasMoreTokens())

            contact.fax = tokens.nextToken().trim();

        if (tokens.hasMoreTokens())

            contact.email = tokens.nextToken().trim();

        if (tokens.hasMoreTokens())

            contact.url = tokens.nextToken().trim();


        return contact;


    }


}
