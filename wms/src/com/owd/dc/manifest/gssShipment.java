package com.owd.dc.manifest;


import GSSMailer.*;

import GSSMailer.LabelResult;
import com.owd.connectship.soap.NameAddress;
import com.owd.connectship.xml.api.OWDShippingResponse.INTLDOCDATA;
import com.owd.connectship.xml.api.OWDShippingResponse.LABELDATA;
import com.owd.connectship.xml.api.OWDShippingResponse.SHIPMENT;
import com.owd.core.Mailer;
import com.owd.core.OWDUtilities;
import com.owd.dc.manifest.Manifestxml.DispatchXml;
import com.owd.dc.manifest.Manifestxml.GSSPackage;
import com.owd.dc.manifest.Manifestxml.Item;
import com.owd.dc.manifest.Manifestxml.Manifest;
import com.owd.dc.manifest.Manifestxml.Responses.PackageSuccessfulResponse;
import com.owd.dc.manifest.Manifestxml.Responses.PackageUnsuccessfulResponse;
import com.owd.dc.manifest.api.internal.AMPConnectShipShipment;
import com.owd.dc.manifest.api.internal.ConnectShipShipment;
import com.owd.dc.manifest.api.internal.ShipConfig;
import com.owd.dc.warehouse.ABShipments.ABUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdClient;
import org.apache.axis.message.MessageElement;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import org.apache.commons.codec.binary.Base64;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;

/**
 *
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Apr 30, 2010
 * Time: 11:18:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class gssShipment {
    private final String MailingAgentID = "ONEWORLDRUSM";
    private final String DefaultEEL = "NOEEI 30.37(a)";
   // private final String locationId = "MOBRIDGEONEWORLDRUSM";
   // private final String password = "gss0460demo";
   // private final String userID = "OWDEMO";
    private String AccessToken;
    private String dcFacility;
    private long authenticationTime = 0;
    private gssAuthentication authentication;
    private boolean isAPO = false;

    private List<Manifest> GssShipments = new ArrayList<Manifest>();

    private List<Manifest> getGssShipments() {
        return GssShipments;
    }
    public List<INTLDOCDATA> getAPOCustomsForms(String workstationId,String location) throws Exception {
        isAPO = true;

          authentication = new gssAuthentication(workstationId,location);
        System.out.println("Attemping shiping");
        List<SHIPMENT> shippedPackages = new ArrayList<SHIPMENT>();
        List<Manifest> goodShippedPackages = new ArrayList<Manifest>();
        List<INTLDOCDATA> labels = new ArrayList<INTLDOCDATA>();
       if (GssShipments.size() == 0)
            throw new Exception("Please load packages first.  The Manifest is currently zero");

        try{
          goodShippedPackages =  sendShipmentToUSPS();

         for(Manifest gssPackage: goodShippedPackages) {
             labels.addAll(gssGetApoCustoms(gssPackage.getPackage().getPackageID()));
         }
        }catch (Exception e){
            //something happened.  Check for already shipped packges and void
        if (goodShippedPackages.size()>0){
         try{
          for(Manifest gssPackage: goodShippedPackages){
            gssVoidShipment voidship = new gssVoidShipment();
              voidship.voidShipment(gssPackage.getPackage().getPackageID(),authentication);

            }
         }catch (Exception ex){
             //something bad happened.  Not good
             ex.printStackTrace();
             throw new Exception("Unable to void packages properly please contact IT.  Fist error:" +e.getMessage() +"\r\n Second Error: "+ ex.getMessage());
         }
        }
        }


        return labels;
    }
    public List<SHIPMENT> shipGSSPackages(Session sess, String workstationId) throws Exception {
        authentication = new gssAuthentication(workstationId,dcFacility);
        System.out.println("Attemping shiping");
        List<SHIPMENT> shippedPackages = new ArrayList<SHIPMENT>();
        List<Manifest> goodShippedPackages = new ArrayList<Manifest>();

        if (GssShipments.size() == 0)
            throw new Exception("Please load packages first.  The Manifest is currently zero");
        try{
          goodShippedPackages =  sendShipmentToUSPS();

            System.out.println("Posting good packages");
            //loop through packages and post them to our system.
            for(Manifest gssPackage: goodShippedPackages){


               postTrackingINFO(gssPackage,sess);

            }
            System.out.println("doing label stuff");
            //loop through packages and get the labels into a shipment object to be returned by api
            for(Manifest gssPackage: goodShippedPackages){
                System.out.println("ID" + gssPackage.getPackage().getPackageID() );

              shippedPackages.add(gssGetLabelReturnSHIPMENT(gssPackage.getPackage().getPackageID(),workstationId));

            }
        }catch (Exception e){
            //something happened.  Check for already shipped packges and void
        if (goodShippedPackages.size()>0){
         try{
          for(Manifest gssPackage: goodShippedPackages){
            gssVoidShipment voidship = new gssVoidShipment();
              voidship.voidShipment(gssPackage.getPackage().getPackageID(),authentication);

            }
         }catch (Exception ex){
             //something bad happened.  Not good
             ex.printStackTrace();
             throw new Exception("Unable to void packages properly please contact IT.  Fist error:" +e.getMessage() +"\r\n Second Error: "+ ex.getMessage());
         }
        }

            throw new Exception("Problem Shipping :  " + e.getMessage());
        }
        return shippedPackages;
    }

    private List<Manifest> sendShipmentToUSPS() throws Exception {
        List<Manifest> goodShippedPackages = new ArrayList<Manifest>();
        for (Manifest gssManifest : GssShipments) {

            ProcessPackageLocator locator = new ProcessPackageLocator();
            ProcessPackageSoap_PortType service = locator.getProcessPackageSoap();
            LoadAndProcessPackageDataXmlDoc doc = new LoadAndProcessPackageDataXmlDoc();
            MessageElement[] ee = new MessageElement[1];
            ee[0] = gssManifest.getXml();
            System.out.println(gssManifest.getXml().getAsString());
            doc.set_any(ee);
            LoadAndProcessPackageDataResponseLoadAndProcessPackageDataResult result = service.loadAndProcessPackageData(doc, authentication.getAccessToken());

            System.out.println(result.get_any());
            System.out.println(result.get_any().length);
            int index = 0;
            for (MessageElement mm : result.get_any()) {
                index = index +1;
                System.out.println(mm.getAsString());
                if (mm.getAsString().contains("Package_Error")) {
                    try {
                        PackageUnsuccessfulResponse manifest = new PackageUnsuccessfulResponse();
                        manifest = (PackageUnsuccessfulResponse) manifest.getXstream().fromXML(mm.getAsString());
                        StringBuffer error = new StringBuffer();
                        for (Object s : manifest.getPackage_Error().getErrors()) {
                            String ss = (String) s;
                            error.append(ss);
                            error.append("\r\n");
                        }
                        throw new Exception(error.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                        StringBuffer sb = new StringBuffer();
                        sb.append(e.getMessage());
                        sb.append("\r\n");
                        sb.append(doc.get_any()[0].getAsString());

                       // Mailer.postMailMessage("USPS GSS Error",sb.toString(),"dnickels@owd.com","dnickels@owd.com");
                        throw new Exception("Error shipping Package: " +e.getMessage());
                    }
                }  else{

                    PackageSuccessfulResponse goodresult = new PackageSuccessfulResponse();
                                    goodresult = (PackageSuccessfulResponse)  PackageSuccessfulResponse.getXstream().fromXML(mm.getAsString());

                    gssManifest.getPackage().setResultPostagePaid(goodresult.getPackage().getPostage_Paid());
                    gssManifest.getPackage().setResultTrackingNum(goodresult.getPackage().getPackage_Identifier().getPackageID());
                    gssManifest.getPackage().setResultIndex(index);

                   goodShippedPackages.add(gssManifest);
                }


            }
        }
        return goodShippedPackages;
    }

    private void postTrackingINFO(Manifest gssPackage,Session sess)throws Exception{

        String updatePackageSql = "update package set gss_shipment = 1, customs_docs = :customs, ship_time=getdate(),order_track_fkey=:trackid,owd_insurance_cost=:cost, insured_amount=:insured where id=:id";
          String updatePackageOrdersql = "update package_order set packs_shipped=(packs_shipped+1) where owd_order_fkey=:orderfkey and is_void=0";
         String updateOrdersql   ="exec update_order_shipment_info :orderfkey";
         String  createTrackingRecord = "insert into owd_order_track (order_fkey, line_index,tracking_no,weight,total_billed,cost_of_goods,ship_date,created_date,created_by,modified_date,modified_by,"
                            + "msn,is_void,reported,email_sent,service_code) VALUES ( :order_fkey, :line_index, :tracking_no, :weight, :total_billed , 0 ,convert(datetime,convert(varchar,getdate(),101)),getdate(),:location,getdate(),:location,0,0, 0,0,:serviceCode)";

          try{
              Query q = sess.createSQLQuery(createTrackingRecord);
              q.setString("order_fkey", gssPackage.getPackage().getOrderFkey());
              System.out.println(gssPackage.getPackage().getOrderFkey());
              q.setInteger("line_index",gssPackage.getPackage().getResultIndex());
              q.setString("tracking_no",gssPackage.getPackage().getResultTrackingNum());
              System.out.println(gssPackage.getPackage().getResultTrackingNum());
              q.setString("weight",gssPackage.getPackage().getPackageWeight());
               boolean doUSPSRateStuff = true;
                            if(gssPackage.getPackage().getUSPSAddToPercent().compareTo(BigDecimal.ZERO)==1){
                                doUSPSRateStuff = false;
                                Double fakePrice = gssPackage.getPackage().getShipPriceToRecord();
                                Double realPrice =  Double.valueOf(gssPackage.getPackage().getResultPostagePaid());
                                BigDecimal percent = gssPackage.getPackage().getUSPSAddToPercent();
                                System.out.println("The percent: "+percent );

                                Double price = fakePrice + (fakePrice * percent.doubleValue());
                                System.out.println("The Real price: "+realPrice);

                                System.out.println("This is what we are recording for the add to pct :" + OWDUtilities.roundFloat(price.floatValue()) );

                                q.setDouble("total_billed",Double.valueOf(gssPackage.getPackage().getOWDInsuranceCost())+Double.valueOf(OWDUtilities.roundFloat(price.floatValue())));
                            }
              if(doUSPSRateStuff){
              if(gssPackage.getPackage().getShipPriceToRecord()>0){
                  BigDecimal percent =     gssPackage.getPackage().getShipDiscountShare();
                  Double realPrice =  Double.valueOf(gssPackage.getPackage().getResultPostagePaid());
                  Double fakePrice =   Double.valueOf(gssPackage.getPackage().getShipPriceToRecord());
                  if(fakePrice>realPrice & (percent.compareTo(BigDecimal.ZERO)==1)){
                      System.out.println("The percentage of Discount Share is " +percent );
                      System.out.println("Real Ship Price: "+ realPrice);
                      System.out.println("Fake ship price" + fakePrice);
                      Double price = fakePrice-((fakePrice-realPrice)*percent.doubleValue());
                      System.out.println("This is our new price not rounded: "+ price );
                      System.out.println("This is what we will record: "+OWDUtilities.roundFloat(price.floatValue()));
                       q.setDouble("total_billed",Double.valueOf(gssPackage.getPackage().getOWDInsuranceCost())+Double.valueOf(OWDUtilities.roundFloat(price.floatValue())));

                  } else{

                 q.setDouble("total_billed",Double.valueOf(gssPackage.getPackage().getOWDInsuranceCost())+Double.valueOf(gssPackage.getPackage().getShipPriceToRecord()));
                  }

              } else{
                 q.setDouble("total_billed",Double.valueOf(gssPackage.getPackage().getOWDInsuranceCost())+Double.valueOf(gssPackage.getPackage().getResultPostagePaid()));
              }
              }
              q.setString("location",gssPackage.getPackage().getLocation());
              q.setString("serviceCode",getTandataCodeFromRateType(gssPackage.getPackage().getRateType()));
              System.out.println("hello");
              System.out.println(q.getNamedParameters());

              int results = q.executeUpdate();
              System.out.println(results);
              
               System.out.println("Done creating TRacking record");

              if (results <1 ) throw new Exception("Insert returned 0");

               String sql = "select order_track_id from owd_order_track where order_fkey = :fkey and tracking_no = :track";
            Query  qq = sess.createSQLQuery(sql);
              qq.setString("fkey", gssPackage.getPackage().getOrderFkey());
              System.out.println(gssPackage.getPackage().getOrderFkey());
              qq.setString("track", gssPackage.getPackage().getResultTrackingNum());
               System.out.println(gssPackage.getPackage().getResultTrackingNum());
              List l = qq.list();
              System.out.println(l);
                System.out.println("Got order track Id");
           String trackId = l.get(0).toString();
                   System.out.println("hhhhhhhhh");

              q = sess.createSQLQuery(updatePackageSql);
              q.setString("trackid",trackId);
              q.setString("id",gssPackage.getPackage().getPackageID());
              q.setString("cost",gssPackage.getPackage().getOWDInsuranceCost());
              q.setString("insured", gssPackage.getPackage().getInsuredAmount());
              if(gssPackage.getPackage().getRateType().equals("EM")){
                  q.setParameter("customs",0);
              }else{
                  q.setParameter("customs",1);
              }

              System.out.println("Right before updateing package");
              results = q.executeUpdate();
                System.out.println("Done updateing pacakge with trackid");
              if (results < 1) throw new Exception("update for Package returned 0");
              q = sess.createSQLQuery(updatePackageOrdersql);
              q.setString("orderfkey",gssPackage.getPackage().getOrderFkey());
              results = q.executeUpdate();
                System.out.println("Done updateing package order ");
              if (results < 1) throw new Exception("update for Package ORder returned 0");
              if(!ABUtils.isWeightVerifiedPacked(gssPackage.getPackage().getOrderFkey())) {
              q = sess.createSQLQuery(updateOrdersql);
                  q.setString("orderfkey", gssPackage.getPackage().getOrderFkey());
              results = q.executeUpdate();
                System.out.println("Done updateting order sql");
              if (results < 1) throw new Exception("update Order returned 0");
              }


              
          } catch (Exception e){
              e.printStackTrace();
              throw new Exception("Unable to post tracking info: "+e.getMessage());
          }

    }
    public static List<INTLDOCDATA> reprintAPOLabel(String packageId) throws Exception{
      List<INTLDOCDATA> labels = new ArrayList<INTLDOCDATA>();
           String sql ="SELECT\n" +
                   "    dbo.package.id,\n" +
                   "    dbo.package_order.facility\n" +
                   "FROM\n" +
                   "    dbo.package_order\n" +
                   "INNER JOIN dbo.package\n" +
                   "ON\n" +
                   "    (\n" +
                   "        dbo.package_order.id = dbo.package.package_order_fkey\n" +
                   "    )\n" +
                   "WHERE\n" +
                   "    dbo.package.pack_barcode = :barcode ;";
       Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setString("barcode",packageId);
        List results = q.list();
        if( results.size()==1){
         Object row = results.get(0);


           System.out.println(row.toString());
              Object[] data = (Object[]) row;
            System.out.println(data);
        String id = data[0].toString();
            String facility= data[1].toString();
            System.out.println(id);
          ProcessPackageLocator locator = new ProcessPackageLocator();
            ProcessPackageSoap_PortType service = locator.getProcessPackageSoap();
          System.out.println("getting the actual label");
        LabelResult response = service.getImageLabelsForPackage(id,"ONEWORLDRUSM",1,"PNG",new gssAuthentication("server",facility).getAccessToken());
        System.out.println("got label");
        if (response.getResponseCode()!=0){
        throw new Exception(response.getMessage());
        }
         for(byte[] ld:response.getLabel()){
             INTLDOCDATA doc = new INTLDOCDATA();
             doc.setCopies_Needed("1");
             doc.setValue(Base64.encodeBase64String(ld));
              labels.add(doc);
         }
        }
        return labels;
    }
    public List<INTLDOCDATA> gssGetApoCustoms(String packageId) throws Exception{
        List<INTLDOCDATA> labels = new ArrayList<INTLDOCDATA>();

          ProcessPackageLocator locator = new ProcessPackageLocator();
            ProcessPackageSoap_PortType service = locator.getProcessPackageSoap();
          System.out.println("getting the actual label");
        LabelResult response = service.getImageLabelsForPackage(packageId,MailingAgentID,1,"PNG",authentication.getAccessToken());
        System.out.println("got label");
        if (response.getResponseCode()!=0){
        throw new Exception(response.getMessage());
        }
         for(byte[] ld:response.getLabel()){
             INTLDOCDATA doc = new INTLDOCDATA();
             doc.setCopies_Needed("1");
             doc.setValue(Base64.encodeBase64String(ld));
              labels.add(doc);
         }

        return labels;
    }
    public  SHIPMENT gssGetLabelReturnSHIPMENT(String packageId,String workStationID) throws Exception{

        SHIPMENT shipment = new SHIPMENT();
        System.out.println("getting locator");
         ProcessPackageLocator locator = new ProcessPackageLocator();
            ProcessPackageSoap_PortType service = locator.getProcessPackageSoap();
          System.out.println("getting the actual label");
        System.out.println("This is the packageID we are using:"+packageId );
        LabelResult response = service.getImageLabelsForPackage(packageId,MailingAgentID,1,"PNG",authentication.getAccessToken());
        System.out.println("got label");
        System.out.println(response.getMessage());

        System.out.println(response.getResponseCode());
        if (response.getResponseCode()!=0){
        throw new Exception(response.getMessage());
        }

        LABELDATA ld = new LABELDATA();
        ld.setCopies_Needed("1");
       System.out.println(response.getLabel().length);

        ld.setValue(org.apache.commons.codec.binary.Base64.encodeBase64String(response.getLabel()[0]));
        shipment.getLABELDATA().add(ld);



        for(int i=1;i<response.getLabel().length;i++){

            INTLDOCDATA doc = new INTLDOCDATA();
            doc.setCopies_Needed("1");
            doc.setValue(org.apache.commons.codec.binary.Base64.encodeBase64String(response.getLabel()[i]));
          shipment.getINTLDOCDATA().add(doc);


        }


          return shipment;
    }

    public static List<LABELDATA> reprintLabelFromBarcode(String barcode) throws Exception{
         String facility;
        List<LABELDATA> labels = new ArrayList<LABELDATA>();
        String sql ="SELECT\n" +
                "    dbo.package.id,\n" +
                "    dbo.package_order.facility\n" +
                "FROM\n" +
                "    dbo.package_order\n" +
                "INNER JOIN dbo.package\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.package_order.id = dbo.package.package_order_fkey\n" +
                "    )\n" +
                "WHERE\n" +
                "    dbo.package.pack_barcode = :barcode ;";
       Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setString("barcode",barcode);
        List results = q.list();
        if( results.size()==1){
         Object row = results.get(0);
         Object[] data = (Object[]) row;
           // System.out.println(row.toString());

        String id = data[0].toString();
          facility = data[1].toString();
            System.out.println(id);

            ProcessPackageLocator locator = new ProcessPackageLocator();
            ProcessPackageSoap_PortType service = locator.getProcessPackageSoap();
            System.out.println("getting the actual label");
                  LabelResult response = service.getImageLabelsForPackage(id,"ONEWORLDRUSM",1,"PNG",new gssAuthentication("server",facility).getAccessToken());
                  System.out.println("got label");
                  if (response.getResponseCode()!=0){
                  throw new Exception(response.getMessage());
                  }

             for(int i=0;i<response.getLabel().length;i++){
               LABELDATA ld = new LABELDATA();
        ld.setCopies_Needed("1");
            ld.setValue(org.apache.commons.codec.binary.Base64.encodeBase64String(response.getLabel()[i]));
                 labels.add(ld);
             }

        }else{
            throw new Exception("could not find barcode");

        }




     return labels;
    }
    public  static SHIPMENT gssGetLabelReturnSHIPMENT2(String packageId,String workStationID,String location) throws Exception{

        SHIPMENT shipment = new SHIPMENT();
        System.out.println("getting locator");
         ProcessPackageLocator locator = new ProcessPackageLocator();
            ProcessPackageSoap_PortType service = locator.getProcessPackageSoap();
          System.out.println("getting the actual label");
        LabelResult response = service.getImageLabelsForPackage(packageId,"ONEWORLDRUSM",1,"PNG",new gssAuthentication(workStationID,location).getAccessToken());
        System.out.println("got label");
        if (response.getResponseCode()!=0){
        throw new Exception(response.getMessage());
        }

        LABELDATA ld = new LABELDATA();
        ld.setCopies_Needed("1");
       System.out.println(response.getLabel().length);

        ld.setValue(org.apache.commons.codec.binary.Base64.encodeBase64String(response.getLabel()[0]));
        shipment.getLABELDATA().add(ld);



        for(int i=1;i<response.getLabel().length;i++){

            INTLDOCDATA doc = new INTLDOCDATA();
            doc.setCopies_Needed("1");
            doc.setValue(org.apache.commons.codec.binary.Base64.encodeBase64String(response.getLabel()[i]));
          shipment.getINTLDOCDATA().add(doc);


        }


          return shipment;
    }


    public gssShipment(List<AMPConnectShipShipment> packlist, Session sess,OwdClient client,String facility,boolean USCustoms) throws Exception {
          dcFacility = facility;
        System.out.println("going to load gsspakcages from connectshipshipments");
        try {
            for (AMPConnectShipShipment ship : packlist) {
                Manifest man = new Manifest();

                GSSPackage pkg = new GSSPackage();
                List<Item> Itemlist = new ArrayList<Item>();
                pkg.setOrderID(ship.getValueAsString("SHIPPER_REFERENCE"));
                pkg.setItemValueCurrencyType("USD");
                pkg.setShipDiscountShare(client.getUspsIntlDiscountSharePct());
                pkg.setUSPSAddToPercent(client.getUspsAddedMarginPct());

                 if(!ship.getServiceCode().equals("TANDATA_USPS.USPS.I_EXP_DMND")&&!ship.getServiceCode().contains("FSMS")){
                System.out.println("This we need record false shipping prices I guess");
              if(!ship.isAPOFPO()){
              pkg.setShipPriceToRecord(ship.getTTEmp(facility));
              }

              System.out.println("Here is our faked price" + pkg.getShipPriceToRecord());
            }else{
                  pkg.setShipPriceToRecord(0.0);
                 }
                //Insurance stuff
                if(((String) ship.getValueAsString("OWDINSURANCEVALUE")).length()==0){
           System.out.println("Setting defualt insurance Value");
               pkg.setInsuredAmount("0.0");
            }else{
                pkg.setInsuredAmount(ship.getValueAsString("OWDINSURANCEVALUE"));
            }

            System.out.println("INSurance value: "+pkg.getInsuredAmount());
            pkg.setOWDInsuranceCost(ship.getValueAsString("OWDINSURANCECOST"));
                System.out.println(pkg.getOWDInsuranceCost());
                 if(pkg.getOWDInsuranceCost().length()==0){
                     pkg.setOWDInsuranceCost("0.0");
                 }
                System.out.println("REprint cost" +pkg.getOWDInsuranceCost());


                //end insurance stuff
                //CONSIGNEE client = ship.getClientNA();
                NameAddress clientnew = ship.getReturnNA() ;

                System.out.println(clientnew.getAddress1());
                System.out.println(ship.getValueAsString("SHIPPER"));
                pkg.setOrderFkey(ship.getValueAsString("OWDORDERID"));
                pkg.setLocation(ship.getValueAsString("CURRENT_FACILITY"));
                if(clientnew.getCompany().length()>1){
                    pkg.setSenderName(clientnew.getCompany());
                    pkg.setSenderBusinessName(clientnew.getCompany());
                }else{
                    pkg.setSenderName(client.getCompanyName());
                    pkg.setSenderBusinessName(client.getCompanyName());
                }


                 ship.printDictionaryValues();
                pkg.setSenderAddress_Line_1(clientnew.getAddress1());
                pkg.setSenderAddress_Line_2(clientnew.getAddress2());
                pkg.setSenderAddress_Line_3(clientnew.getAddress3());
                pkg.setSenderCity(clientnew.getCity());
                pkg.setSenderProvince(clientnew.getStateProvince());
                pkg.setSenderPostal_Code(clientnew.getPostalCode());
                pkg.setSenderCountry_Code(getIANACountry(clientnew.getCountrySymbol(),clientnew.getCity(),sess));
                pkg.setSenderPhone(clientnew.getPhone());
                pkg.setSenderEmail("");
                NameAddress customer = ship.getConsigneeNA();
                pkg.setRecipientName(customer.getContact());
                System.out.println(customer.getContact());
                if(customer.getContact().length()>0){
                   if(customer.getContact().contains(" ")){

                       pkg.setRecipientFirstName(customer.getContact().substring(0,customer.getContact().indexOf(" ")));
                     pkg.setRecipientLastName(customer.getContact().substring(customer.getContact().indexOf(" ")+1));

                   }   else{
                       pkg.setRecipientFirstName(customer.getContact());
                       pkg.setRecipientLastName(customer.getContact());
                   }

                }

                //removed because of GSS changes on 1/26/2013

               /* if(pkg.getRecipientLastName().length()==1){
                    pkg.setRecipientFirstName(pkg.getRecipientFirstName()+pkg.getRecipientLastName());
                    pkg.setRecipientLastName("");
                }*/
                if(customer.getCompany().length()>1){
               pkg.setRecipientBusinessName(customer.getCompany());
                };
                pkg.setRecipientAddress_Line_1(customer.getAddress1());
                pkg.setRecipientAddress_Line_2(customer.getAddress2());
                pkg.setRecipientAddress_Line_3(customer.getAddress3());
                pkg.setRecipientCity(customer.getCity());
                pkg.setRecipientProvince(customer.getStateProvince());
                pkg.setRecipientPostal_Code(customer.getPostalCode().toUpperCase());
                if(USCustoms){
                    pkg.setRecipientCountry_Code("US");
                } else{
                    pkg.setRecipientCountry_Code(getIANACountry(customer.getCountrySymbol(),customer.getCity(),sess));

                    if(pkg.getRecipientCountry_Code().equalsIgnoreCase("VI")){
                        if(ship.getAssignedServiceCode().equalsIgnoreCase("TANDATA_USPS.USPS.EXPR")){
                            pkg.setRecipientCountry_Code("US");
                        }
                    }
                }

                pkg.setRecipientPhone(customer.getPhone());
                pkg.setRecipientEmail("");

                //todo figure this out, gift sample or merchendise

                pkg.setPackageType("M");
                pkg.setShippingCurrencyType("USD");
                try {
                    Query q = sess.createSQLQuery("execute sp_SelectPackageIdFromPackBarcode :barcode");
                    q.setParameter("barcode", ship.getValueAsString("PACKAGE_BARCODE"));
                    List results = q.list();
                    Object data = results.get(0);
                    Object[] row = (Object[]) data;

                    pkg.setPackageID(row[0].toString());
                    pkg.setBoxTypeFkey(row[1].toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("Unable to get Id for package: "+ ship.getValueAsString("PACKAGE_BARCODE") );

                }

                pkg.setPackageWeight(ship.getValueAsString("WEIGHT"));
                pkg.setWeightUnit("LB");
                Dimension dim = new Dimension(ship.getValueAsString("DIMENSION"));

                pkg.setPackageWidth(dim.getWidth());
                pkg.setPackageHeight(dim.getHeight());
                pkg.setPackageLength(dim.getLength());
                pkg.setUnitOfMeasurement("IN");
                //todo rate flat type lookup
                pkg.setRateType(getRateTyepFromTandataCode(ship.getAssignedServiceCode(),pkg.getBoxTypeFkey(),Float.parseFloat(ship.getValueAsString("WEIGHT"))));
               // pkg.setRateType("PMI");
                if(pkg.getRateType().equals("EM")){
                    pkg.setServiceType("MLO");
                    if(pkg.getRecipientCountry_Code().equals("PR")){
                        pkg.setRecipientCountry_Code("US");
                    }
                }else {


                pkg.setServiceType("LBL");
                }

                System.out.println("Dimmensions are now set");


                if(pkg.getRateType().equals("LBO")){
                    pkg.setPackageID(ship.getValue(ShipConfig.TRACKING_NUMBER)+"");
                }
                pkg.setPackagePhysicalCount("1");
                pkg.setMailingAgentID(MailingAgentID);
                pkg.setValueLoaded("N");
                pkg.setPFCorEEL(DefaultEEL);
               // pkg.setPFCorEEL("X20140226052702");
                System.out.println("Going to be looping through items");
                System.out.println("number of items " + ship.getItemData()[0].length);
                for (int i = 0; i < ship.getItemData()[0].length; i++) {
                    System.out.println("Doing item #" + i);
                    Item itm = new Item();
                    itm.setItemID(ship.getItemData()[ConnectShipShipment.kTDItemSKU][i]);
                    itm.setItemDescription(ship.getItemData()[ConnectShipShipment.kTDItemDesc][i]);
                  if(isAPO){
                      Query q = HibernateSession.currentSession().createSQLQuery("execute sp_getCustomsDescriptionForClientAndSku :clientId, :sku ");
                      q.setParameter("clientId",client.getClientId());
                      q.setParameter("sku",itm.getItemID());
                      String desc = q.list().get(0).toString();
                      itm.setCustomsDescription(StringUtils.left(desc,50));
                  }   else{
                      itm.setCustomsDescription(StringUtils.left(itm.getItemDescription(),50));
                  }

                    itm.setUnitValue(ship.getItemData()[ConnectShipShipment.kTDItemPrice][i]);
                    itm.setQuantity(ship.getItemData()[ConnectShipShipment.kTDItemQty][i]);
                    Itemlist.add(itm);


                }
                System.out.println("Done looping Items");

                pkg.setItems(Itemlist);

                man.setPackage(pkg);
                DispatchXml dis = new DispatchXml();
                dis.setDispatchDateTime();
                man.setDispatch(dis);
                GssShipments.add(man);

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }


    }

    public String getIANACountry(String country,String city,Session sess) throws Exception{
        System.out.println("getting countires For "+country);
        //fix funky country stuff
        if(country.equals("KYRGHYZSTAN")) country = "KYRGYZSTAN";
         if(country.equalsIgnoreCase("NETHERLANDS_ANTILLES")){
             if(city.equalsIgnoreCase("Curacao")){
                 return "CW";
             }
         }

            String sql = " select country_code from countries where connectship = :name";
        try{

            Query q = sess.createSQLQuery(sql);
            q.setString("name",country.toUpperCase());
            List results = q.list();
            if (results.size()>0){
                
               country =  (String) results.get(0);

            }

        }catch (Exception e){
            e.printStackTrace();
        }
          if (country.length()>2) throw new Exception("Unable to find 2 digit country code please contact IT");

        return country;
        
    }
    public static void main(String[] args) {
        try {

          //  reprintLabelFromBarcode("p1666029*7916342R1*b1");

            ConnectShipShipment ship = new ConnectShipShipment();
            ship.loadOrderFromOrderReference("8834408", true,"DC1");

            System.out.println("hello          ");
            System.out.println(ship.getValueAsString("SHIPPER_REFERENCE"));

           // XStream x = GSSPackage.getManifestXstream();
            ship.setValue("CURRENT_FACILITY", "DC1");
            ship.setValue(ShipConfig.CURRENT_SHIPPER, "OWDTESTING");
            ship.setValue(ShipConfig.SHIPPER, "OWDTESTER");



            ship.setValue(ShipConfig.WEIGHT, new Double("1"));
            List<ConnectShipShipment> l = new ArrayList();
            l.add(ship);
            Session sess = HibernateSession.currentSession();
            OwdClient cl = (OwdClient) sess.load(OwdClient.class,486);
            //gssShipment gss = new gssShipment(l, sess,cl,"DC1",false);
           // System.out.println(gss.getGssShipments().get(0).getPackage().getSenderAddress_Line_1());
            // System.out.println(gss.getGssShipments().get(0).getPackage().getSenderName());
           /* SHIPMENT s = gssGetLabelReturnSHIPMENT2("1610519","Testing");
              System.out.println(s.getLABELDATA().get(0).getValue());*/
       /*  List<SHIPMENT> result =  gss.shipGSSPackages(sess,"Testing");
            System.out.println(result.size()+"  the result size");
            for(SHIPMENT ss:result){

                System.out.println(ss.getLABELDATA().size());

            }*/

         //          gss.getGssShipments().get(0).getPackage().setResultIndex(1);
          //  gss.getGssShipments().get(0).getPackage().setResultTrackingNum("1zeeeeeeeeeeeeeeeee");

            //        gss.getGssShipments().get(0).getPackage().setResultPostagePaid("34.34");
       //    gss.postTrackingINFO(gss.getGssShipments().get(0),sess);
            
            System.out.println();
            //SHIPMENT shipment =  gss.gssGetLabelReturnSHIPMENT("CG052109621US","TEstSTation1");
            //System.out.println(shipment.getINTLDOCDATA().size());
            //System.out.println(shipment.getLABELDATA().get(0).getValue());

          //  gss.shipGSSPackages(sess, "Teststation1");
            /*for (Manifest m : gss.getGssShipments()){
                System.out.println(m.getXml());
            }*/

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private String getTandataCodeFromRateType(String rateType) throws Exception{

        if(rateType.contains("PMI")){
            return "TANDATA_USPS.USPS.I_PRIORITY";
        }
        if(rateType.equalsIgnoreCase("EMS")){
            return "TANDATA_USPS.USPS.I_EXP_DMND";
        }
        if(rateType.equalsIgnoreCase("FCM")){
            return "TANDATA_USPS.USPS.I_FIRST";
        }
        if(rateType.equalsIgnoreCase("EM")){
            return "TANDATA_USPS.USPS.EXPR";
        }


        throw new Exception("Unable to map this rate type: "+rateType);
    }

    private String getRateTyepFromTandataCode(String shipmethod, String boxTypeFkey,float weight) throws Exception {
        System.out.println("This is our weight"+weight);
        if (boxTypeFkey.equals("149")||boxTypeFkey.equals("100")||boxTypeFkey.equals("99")) {
           if (shipmethod.equalsIgnoreCase("TANDATA_USPS.USPS.I_PRIORITY")) return "PMI-FRX";
       }
       if (boxTypeFkey.equals("101")) {
           if (shipmethod.equalsIgnoreCase("TANDATA_USPS.USPS.I_PRIORITY")) return "PMI-FRE";
       }
        if (shipmethod.equalsIgnoreCase("TANDATA_USPS.USPS.I_EXP_DMND")) return "EMS";

        if (shipmethod.equalsIgnoreCase("TANDATA_USPS.USPS.I_FIRST")) {
            if(weight>4.0f) return "PMI";

            return "FCM";
        }
        if(shipmethod.equalsIgnoreCase("TANDATA_USPS.USPS.EXPR")){
            return "EM";
        }


        if (shipmethod.equalsIgnoreCase("TANDATA_USPS.USPS.I_PRIORITY")) return "PMI";
        return "LBO";

       // throw new Exception("Unable to find proper shipmethod Rate Type Conversion");

    }

}
