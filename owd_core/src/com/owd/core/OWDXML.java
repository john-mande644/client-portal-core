/**
 * OWDXML.java
 * Contains generic methods to construct tags & attributes in an XML document
 */

package com.owd.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Liheng Qiao July 18, 2002
 */


public class OWDXML {
private final static Logger log =  LogManager.getLogger();

    private static final String kTagXMLStart = "<?csXml version=\"1.0\" encoding=\"iso-8859-1\"?>";

    public static String getXMLTag(String tagName, Hashtable attributes) {
        StringBuffer request = new StringBuffer();
        Enumeration attNames;

        request.append("<" + tagName + " ");
        if (attributes != null) {
            attNames = attributes.keys();
            while (attNames.hasMoreElements()) {
                String attName = (String) attNames.nextElement();
                String attValue = (String) attributes.get(attName);
                request.append(" " + attName + "=" + attValue + " ");
            }
        }

        request.append(">\n");
        return request.toString();
    }

    public static String getXMLTag(String tagName, Vector attributes) {
        StringBuffer request = new StringBuffer();
        if (attributes != null) {
            Enumeration attribute = attributes.elements();

            while (attribute.hasMoreElements()) {
                Hashtable attrib = (Hashtable) attribute.nextElement();
                request.append(getXMLTag(tagName, attrib));
            }
        } else
            request.append("<" + tagName + ">\n>");
        return request.toString();
    }

    public static String getXMLStartTag() {
        return kTagXMLStart;
    }

    public static String getXMLEndTag(String tagName) {
        StringBuffer end_tag = new StringBuffer();
        end_tag.append("</" + tagName + ">\n");
        return end_tag.toString();
    }


}
