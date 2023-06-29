package com.owd.dc.manifest.Manifestxml;

import org.apache.axis.message.MessageElement;

import javax.xml.namespace.QName;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Apr 22, 2010
 * Time: 11:49:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class Manifest {
    private DispatchXml Dispatch;
    private GSSPackage Package;
    private String xmlns="mailerdataformatf07.xsd";
    public DispatchXml getDispatch() {
        return Dispatch;
    }

    public void setDispatch(DispatchXml dispatch) {
        Dispatch = dispatch;
    }

    public GSSPackage getPackage() {
        return Package;
    }

    public void setPackage(GSSPackage aGSSPackage) {
        Package = aGSSPackage;
    }

    public MessageElement getXml(){
            MessageElement root = new MessageElement(new QName("Manifest"));
        try{
             root.addAttribute("","xmlns","mailerdataformatf07.xsd");
             root.addChild(Dispatch.getXml());
            System.out.println("Loading PackageXML");
            root.addChild(Package.getXml());
        } catch(Exception e){
            e.printStackTrace();
        }

         return root;
    }
}
