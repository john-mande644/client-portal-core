package com.owd.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;

public class OWDStringTokenizer extends StringTokenizer {
private final static Logger log =  LogManager.getLogger();
    private String str;
    private String delim;
    private boolean returnDelims;
    private Vector strings;
    private boolean incomplete = false;

    public OWDStringTokenizer(String str, String delim, boolean returnDelims) {
        super(str, delim, returnDelims);
        this.str = str;
        this.delim = delim;
        this.returnDelims = returnDelims;
        strings = new Vector();
        while (this.hasMoreTokens()) {
            strings.addElement(this.nextToken());
        }
        strings = correct();
    }

    private Vector correct() {
        String s = null;
        Vector v = new Vector();
        Enumeration e = strings.elements();
        while (e.hasMoreElements()) {
            String s1 = "";
            s = (String) e.nextElement();
            while (s.startsWith("\"") && (!(s.endsWith("\""))) && e.hasMoreElements()) {
                if (e.hasMoreElements())
                    s1 = (String) e.nextElement();
                if (s1.length() > 0)
                    s = s + delim + s1;
            }
            v.addElement(s);
        }
        return v;
    }


    public int getRepeatTimes(String s) {
        int count = 0;
        if (s == null)
            s = "";
        Enumeration e = strings.elements();
        while (e.hasMoreElements()) {
            if (s.equals(e.nextElement()))
                count++;
        }
        return count;
    }

    public Vector getUniqueStrings() {
        Vector unique = new Vector();
        Enumeration e = strings.elements();
        while (e.hasMoreElements()) {
            String token = (String) e.nextElement();
            if (!(unique.contains(token))) {
                unique.addElement(token);
            }
        }
        return unique;

    }

    public Vector getStrings() {
        return strings;
    }

    public static void main(String[] args) {
        //test
        String teststring = "c-mb:mb-3:mb-3";
        //////log.debug("Tokenizing string "+teststring);
        OWDStringTokenizer toker = new OWDStringTokenizer(teststring, ":", false);
        Vector strings = toker.getUniqueStrings();
        for (int i = 0; i < strings.size(); i++) {
            String tok = (String) strings.elementAt(i);
            //log.debug("Got token " + tok + " (" + toker.getRepeatTimes(tok) + ")");
        }


    }
}
