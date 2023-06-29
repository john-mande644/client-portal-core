package com.owd.core.xml;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class XMLUtils {
private final static Logger log =  LogManager.getLogger();
    public static Element getXMLCDATANode(Document doc, String elementName, String elementText) {
        Element e = doc.createElement(elementName);

        if (elementText != null)
            e.appendChild(doc.createCDATASection(elementText));
        //else
        // OWDUtilities.debugApp("null value for XML element "+elementName);
        return e;

    }

    public static Element getXMLTextNode(Document doc, String elementName, String elementText) {
        Element e = doc.createElement(elementName);

        if (elementText != null)
            e.appendChild(doc.createTextNode(elementText));
        //else
        // OWDUtilities.debugApp("null value for XML element "+elementName);
        return e;

    }

    public static String getNodeValue(Document doc, String nodeName) {
        // OWDUtilities.debugApp("getting node value for "+nodeName);
        NodeList nodes = ((Document) doc).getElementsByTagName(nodeName);
        // OWDUtilities.debugApp("got node list for "+nodeName);
        if (nodes == null)
            return "NULL";

        if (nodes.getLength() < 1)
            return "NULL";

        // OWDUtilities.debugApp("getting node for "+nodeName);
        Node theNode = nodes.item(0);

        if (theNode.getFirstChild() == null)
            return "NULL";

        // OWDUtilities.debugApp("getting node first child for "+nodeName);
        String returnString = theNode.getFirstChild().getNodeValue();
        if (returnString == null)
            return "NULL";

        return returnString;

    }

    public static String getNodeValue(Node node, String nodeName) {

       // //log.debug("checking Node "+node+" for child "+nodeName);
        if(node==null) return "";
        NodeList nodes = node.getChildNodes();
        if (nodes == null) {
            // OWDUtilities.debugApp("null nodes in getNodeValue for "+nodeName);
            return "NULL";
        }

        if (nodes.getLength() < 1) {
            // //log.debug("no nodes in getNodeValue for "+nodeName);
            return "NULL";
        }

        // //log.debug("checking nodelist:"+nodes);
        for (int i = 0; i < nodes.getLength(); i++) {
           //  //log.debug("checking in getNodeValue for "+nodeName+", checking "+i);
            if (nodes.item(i).getNodeName().equals(nodeName)) {
               // //log.debug("matched name");
                String value=null;
                try{
                value = nodes.item(i).getFirstChild().getNodeValue();
                }catch(Exception ex){}
                if (value == null)
                    return "";
                else
                    return value;
            }

        }

        return "NULL";

    }

    public static String getQuotedNodeValue(Document doc, String nodeName) {

        String returnStr = getNodeValue(doc, nodeName);
        if (returnStr.equals("NULL"))
            return returnStr;

        return "\"" + returnStr + "\"";
    }

    public static String getQuotedNodeValue(Node doc, String nodeName) {

        String returnStr = getNodeValue(doc, nodeName);
        if (returnStr.equals("NULL"))
            return returnStr;

        return "\"" + returnStr + "\"";
    }

    public static void getTextNodeValue(Node parentNode, String nodeName) {


    }


}
