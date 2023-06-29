package TestGSSMailer;


import com.owd.dc.manifest.Manifestxml.Responses.PackageSuccessfulResponse;
import com.owd.dc.manifest.Manifestxml.Responses.PackageUnsuccessfulResponse;
import com.owd.dc.manifest.Manifestxml.dispatchIdList;
import com.owd.hibernate.HibernateSession;
import com.thoughtworks.xstream.XStream;
import org.apache.axis.message.MessageElement;
import org.apache.axis.types.Name;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.xml.namespace.QName;
import java.util.*;
import java.util.ArrayList;
import java.util.jar.Attributes;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: May 12, 2010
 * Time: 3:36:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class testing {
          private static String MailingAgentID = "ONEWORLDRUSM";
    private static final String DefaultEEL = "NOEEI 30.37(a)";
    private static final String locationId = "MOBRIDGEONEWORLDRUSM";
    private static final String password = "gss0460demo";
    private static final String userID = "OWDEMO";
    public static void main(String[] args){
                               try{
                                   dispatchIdList id = new dispatchIdList();

                                   dispatchIdList.dispatch d = new dispatchIdList.dispatch();
                                   d.setCreatedDate("hellodate");
                                   d.setDispatchId("000wwwww");
                                   id.getDispatchList().add(d);
                                   dispatchIdList.dispatch dd = new dispatchIdList.dispatch();
                                   dd.setCreatedDate("date 3");;
                                   dd.setDispatchId("id 2");
                                   id.getDispatchList().add(dd);
                                    System.out.println(id.getDispatchList().size());

                                                  XStream x = new XStream();
                                              x.alias("Dispatch",dispatchIdList.dispatch.class);
                                             x.alias("OWD Dispatch List",id.getClass());
                                                                             System.out.println(x.toXML(id));

 /*                                  Session sess = HibernateSession.currentSession();
String  createTrackingRecord = "insert into owd_order_track (order_fkey, line_index,tracking_no,weight,total_billed,cost_of_goods,ship_date,created_date,created_by,modified_date,modified_by,"
                            + "msn,is_void,reported,email_sent) VALUES ( :order_fkey, :line_index, :tracking_no, :weight, :total_billed , 0 ,getdate(),getdate(),\'Manual\',getdate(),\'Manual\',0,0, 0,0)";
                               Query q = sess.createSQLQuery(createTrackingRecord);
              q.setString("order_fkey", "11");
              q.setInteger("line_index",1);
              q.setString("tracking_no","123456654321");
              q.setString("weight","2.3");
              q.setDouble("total_billed",Double.valueOf(12.12));

              int results = q.executeUpdate();
               System.out.println("Done creating TRacking record");

              if (results <1 ) throw new Exception("Insert returned 0");

               String sql = "select order_track_id from owd_order_track where order_fkey = :fkey and tracking_no = :track";
              q = sess.createSQLQuery(sql);
              q.setString("fkey", "11");
              q.setString("track", "123456654321");
              List l = q.list();
                                   System.out.println(l.size());
                                   System.out.println(l);*/
/*
                                   String xml = "<Manifest xsi:schemaLocation=\"mailerdataformatf06.xsd\" xmlns=\"mailerdataformatf06.xsd\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><Dispatch_Confirmation><DispatchID/><Reject_Package_Count>0</Reject_Package_Count><Version_Number>6</Version_Number></Dispatch_Confirmation><Package PackageID=\"1610519\"><Postage_Paid>25.18</Postage_Paid><Postage_Paid_Currency_Type>USD</Postage_Paid_Currency_Type><Package_Identifier><PackageID>CG052109621US</PackageID><PackageID_Type>IDENTIFIER</PackageID_Type><AgentName>USPS</AgentName></Package_Identifier></Package></Manifest>";

                                   PackageSuccessfulResponse p =  (PackageSuccessfulResponse) PackageSuccessfulResponse.getXstream().fromXML(xml);
                                   System.out.println(p.getPackage().getPackage_Identifier().getPackageID());
                                   System.out.println(p.getPackage().getPostage_Paid());*/
                                  /* PackageSuccessfulResponse.dispatchConfirmation dispatch = new PackageSuccessfulResponse.dispatchConfirmation();
                                   dispatch.setDispatchID("1234");
                                   dispatch.setReject_Package_Count("0");
                                   dispatch.setVersion_Number("6");
                                   PackageSuccessfulResponse.packageIdentifier id = new PackageSuccessfulResponse.packageIdentifier();
                                                      id.setPackageID("eeeeeeeeeee");
                                   id.setPackageID_Type("Type");
                                   PackageSuccessfulResponse.uspspackage uspsp = new PackageSuccessfulResponse.uspspackage();
                                   uspsp.setPackage_Identifier(id);
                                   uspsp.setPackageID("ID");
                                   uspsp.setPostage_Paid("25.23");
                                   uspsp.setPostage_Paid_Currency_Type("USD");
                                   p.setDispatch_Confirmation(dispatch);
                                   p.setPackage(uspsp);*/
                                 /*  XStream x = new XStream();
                                  x.alias("Manifest",PackageSuccessfulResponse.class);
                                  x.useAttributeFor(PackageSuccessfulResponse.uspspackage.class,"PackageID");
                                   System.out.println(x.toXML(p));
                                   PackageSuccessfulResponse pp = new PackageSuccessfulResponse();
                                     pp = (PackageSuccessfulResponse)  PackageSuccessfulResponse.getXstream().fromXML(xml);
                                         System.out.println(pp.getPackage().getPackageID());*/

               /*                   String xml = "<Manifest xsi:schemaLocation=\"mailerdataformatf06.xsd\" xmlns=\"mailerdataformatf06.xsd\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                                          "<Dispatch_Confirmation>\n" +
                                          "<DispatchID/><Reject_Package_Count>1</Reject_Package_Count>\n" +
                                          "<Version_Number>N/A</Version_Number></Dispatch_Confirmation>\n" +
                                          "<Package_Hold>\n" +
                                          "<PackageID>1610519</PackageID>\n" +
                                          "<Line_Number>0</Line_Number>\n" +
                                          "<Reason_Code/><Reason_Description/>\n" +
                                          "</Package_Hold>\n" +
                                          "\n" +
                                          "<Package_Error PackageID=\"1610519\">\n" +
                                          "<Error_Description>Unable to validate the maximum allowed weight for the submitted shipping agent ID, service type, rate type, and recipient country code.  Please verify if these fields are correct and not missing.</Error_Description>\n" +
                                          
                                          "</Package_Error>\n" +
                                          "\n" +
                                          "<Package_Warning PackageID=\"1610519\"/>\n" +
                                          "<Package_Message PackageID=\"1610519\"/>\n" +
                                          "</Manifest>";
                                      PackageUnsuccessfulResponse manifest = new PackageUnsuccessfulResponse();

                                   PackageUnsuccessfulResponse.dispatchConfirmation dispatch = new PackageUnsuccessfulResponse.dispatchConfirmation();
                                   PackageUnsuccessfulResponse.packageError errr = new PackageUnsuccessfulResponse.packageError();
                                   dispatch.setDispatchID("123");
                                   dispatch.setReject_Package_Count("1");
                                   dispatch.setVersion_Number("7");
                                   manifest.setDispatch_Confirmation(dispatch);
                                List l = new ArrayList();
                                   l.add("hello");
                                   l.add("goodbye");
                                   errr.setErrors(l);
                                   manifest.setPackage_Error(errr);
                                   XStream xx = new XStream();
                                    // xx.aliasField("string",PackageUnsuccessfulResponse.packageError.class,"Error_Description");
                                    xx.addImplicitCollection(PackageUnsuccessfulResponse.packageError.class,"errors");
                                   xx.alias("Error_Description",String.class);
                                  // xx.addImplicitCollection(PackageUnsuccessfulResponse.packageError.class,"err");
                                   System.out.println(xx.toXML(manifest));
                                     xx.alias("Manifest",PackageUnsuccessfulResponse.class);
                                   manifest = (PackageUnsuccessfulResponse) xx.fromXML(xml);
                                   
                                   System.out.println(manifest.getPackage_Error().getErrors().size());
                                               String s = manifest.getPackage_Error().getErrors().get(0).toString();*/

   /*   ProcessPackageLocator locator = new ProcessPackageLocator();
        ProcessPackageSoap_PortType service = locator.getProcessPackageSoap();

          AuthenticateResult ar =                 service.authenticateUser(userID,password,locationId,"testing");
                                 System.out.println(ar.getAccessToken());

                           LoadAndProcessPackageDataXmlDoc doc = new LoadAndProcessPackageDataXmlDoc();
                                   MessageElement[] e = new MessageElement[1];
                                       MessageElement man = new MessageElement(new QName("Manifest"));

                                                      man.addChildElement("Dispatch").addTextNode("test");
                                   
                                  


                                           e[0] = man;
                                   doc.set_any(e);
                   LoadAndProcessPackageDataResponseLoadAndProcessPackageDataResult result =           service.loadAndProcessPackageData(doc,ar.getAccessToken());
                                  System.out.println(result.get_any());
                                  System.out.println(result.get_any().length);
                                   for(MessageElement mm:result.get_any()){
                                          System.out.println(mm.getAsString());

                                   }*/

                               }catch (Exception e){
                                   e.printStackTrace();
                               }

    }
}
