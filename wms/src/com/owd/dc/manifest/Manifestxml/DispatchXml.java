package com.owd.dc.manifest.Manifestxml;

import org.apache.axis.message.MessageElement;

import javax.xml.namespace.QName;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Apr 22, 2010
 * Time: 4:59:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class DispatchXml {
     private String xmlns ="mailerdataformatf07.xsd";
    private String ShippingAgentID ="ONEWORLDRUSM";
    private String ReceivingAgentID = "USPSERVICUSD";

    private String DispatchID = "";
    private String DispatchDateTime;
    private String TimeZone ="CST";
    private String FileFormatVersion="7";

    public String getXmlns() {
        return xmlns;
    }

    public String getShippingAgentID() {
        return ShippingAgentID;
    }

    public String getReceivingAgentID() {
        return ReceivingAgentID;
    }

    public String getDispatchID() {
        return DispatchID;
    }

    public void setDispatchDateTime() {
         SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
     try{

    DispatchDateTime =  df.format(Calendar.getInstance().getTime());

     }catch (Exception e){
     
     }

    }

    public String getTimeZone() {
        return TimeZone;
    }

    public String getFileFormatVersion() {
        return FileFormatVersion;
    }

    public MessageElement getXml() throws Exception{
        MessageElement root = new MessageElement(new QName("Dispatch"));
        try{

            root.addAttribute("","xmlns","mailerdataformatf07.xsd");
          root.addChildElement("ShippingAgentID").addTextNode(ShippingAgentID);
          root.addChildElement("ReceivingAgentID").addTextNode(ReceivingAgentID);
          root.addChildElement("DispatchID").addTextNode(DispatchID);
            root.addChildElement("DispatchDateTime").addTextNode(DispatchDateTime);
            root.addChildElement("TimeZone").addTextNode(TimeZone);
            root.addChildElement("FileFormatVersion").addTextNode(FileFormatVersion);
            


        } catch(Exception e){


        }

       return root;

    }
}
