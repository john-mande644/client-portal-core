package com.owd.dc.manifest;

import GSSMailer.CommonResult;
import GSSMailer.ProcessPackageLocator;
import GSSMailer.ProcessPackageSoap_PortType;
import com.owd.core.OWDUtilities;
import com.owd.core.business.order.Package;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: May 19, 2010
 * Time: 4:18:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class gssVoidShipment {
      private final String MailingAgentID = "ONEWORLDRUSM";
    private gssAuthentication authentication;


    public void voidPostedShipment(String barcode, Session sess) throws Exception{
        //todo get package id from barcode, and otehr needed variables, then void gss then the rest
        String packageOrderId;
        String packageId;
        String orderFkey;
        String trackFkey;
        String facility;

        String variablelookupsql = "SELECT\n" +
                " dbo.package_order.id,\n" +
                "    dbo.package.order_track_fkey,\n" +
                "   \n" +
                "    dbo.package_order.owd_order_fkey," +
                "package.id as package_id,dbo.package_order.facility\n" +
                "FROM\n" +
                "    dbo.package\n" +
                "INNER JOIN dbo.package_order\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.package.package_order_fkey = dbo.package_order.id\n" +
                "    )\n" +
                "WHERE\n" +
                "    dbo.package.pack_barcode = :barcode ;";
        Query q = sess.createSQLQuery(variablelookupsql);
        q.setString("barcode",barcode);
        List l = q.list();
        if (l.size()>0){
              Object data = l.get(0);
                   Object[] result = (Object[])data;

              packageOrderId = result[0].toString();
            trackFkey = result[1].toString();
            orderFkey = result[2].toString();
            packageId = result[3].toString();
            facility = result[4].toString();
            System.out.println(packageId);


        }else{
            throw new Exception("Unable to load info needed, please try again, if you get this repeatedly, contact IT");
        }

       Package.voidPackageShipment(Integer.parseInt(trackFkey),"GssVoidShipment");

        //void the shipment on the USPS GSS Server
        
        voidShipment(packageId,"The server",facility);


    }


    public void voidShipment(String packageId, gssAuthentication a) throws Exception{
        authentication = a;
        doVoid(packageId);
    }
    public void voidShipment(String packageId,String workstationid,String location) throws Exception{
        authentication = new gssAuthentication(workstationid,location);

        doVoid(packageId);
    }

    private void doVoid(String packageId) throws Exception {
        ProcessPackageLocator locator = new ProcessPackageLocator();
        ProcessPackageSoap_PortType service = locator.getProcessPackageSoap();

        CommonResult result = service.removePackageFromOpenDispatch(packageId,MailingAgentID,1,authentication.getAccessToken());

        if (result.getResponseCode()!=0) throw new Exception(result.getMessage());
        System.out.println("We just voided "+packageId);
    }

    public static void main(String[] args){
        try{
        gssVoidShipment v = new gssVoidShipment();
        v.voidPostedShipment("p1590828*7812720R1*b1", HibernateSession.currentSession());
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
