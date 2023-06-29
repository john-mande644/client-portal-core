package com.owd.dc.manifest.api;

import com.owd.connectship.xml.api.OWDShippingRequest.*;
import com.owd.connectship.xml.api.OWDShippingResponse.*;
import com.owd.connectship.services.ConnectShipCommunications;
import com.owd.core.Mailer;
import com.owd.core.business.order.LineItem;
import com.owd.core.business.order.OrderStatus;
import com.owd.core.managers.ManifestingManager;
import com.owd.dc.packing.singleBox;
import com.owd.hibernate.HibernateSession;
import com.owd.dc.manifest.api.internal.ConnectShipShipment;
import com.owd.dc.manifest.api.internal.ConnectShipWebServices;
import com.owd.dc.manifest.api.internal.ShipConfig;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.commons.codec.binary.Base64;
import org.hibernate.Session;

import java.awt.*;
import java.util.List;
import java.util.Vector;


import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.Marshaller;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: May 7, 2008
 * Time: 11:05:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class ShippingAPITest extends TestCase {
    Session sess = null;

    public ShippingAPITest(String test) {
        super(test);
    }

    /**
     * The fixture set up called before every test method.
     */
    protected void setUp() throws Exception {

        sess = HibernateSession.currentSession();
    }

    /**
     * The fixture clean up called after every test method.
     */
    protected void tearDown() throws Exception {

        // HibernateSession.closeSession();
    }

    public static TestSuite suite() {
        TestSuite suite = new TestSuite(ShippingAPITest.class);
        return suite;
    }


    public void testLISTREQUEST() throws Exception {


        try {
            LISTREQUEST lr = new LISTREQUEST();


            lr.setList_Name("xxx");

            try {
                LISTRESPONSE lrx = ListRequest.handle(lr, false, 1.0);
            } catch (Exception ex) {
                assertTrue("Count of list names incorrect", ex.getMessage().indexOf("LISTNAMES") >= 0);
            }

            lr.setList_Name("LISTNAMES");

            LISTRESPONSE lrx = ListRequest.handle(lr, false, 1.0);

            assertEquals("Count of list names incorrect", lrx.getDATALIST().getLISTVALUE().size(), 3);

            for (LISTVALUE listChoice : lrx.getDATALIST().getLISTVALUE()) {
                //System.out.println("testing " + listChoice.getAPIVALUE());
                lr.setList_Name(listChoice.getAPIVALUE());
                Marshaller m = ConnectShipCommunications.getMarshaller("com.owd.connectship.xml.api.OWDShippingRequest");
                                     m.marshal(lr,System.out);

                LISTRESPONSE lrxt = ListRequest.handle(lr, false, 1.0);


                assertTrue("No values returned for list name " + listChoice.getAPIVALUE(), lrxt.getDATALIST().getLISTVALUE().size() > 0);

                assertTrue("Empty code value returned for " + listChoice.getAPIVALUE(), ((String) lrxt.getDATALIST().getLISTVALUE().get(0).getAPIVALUE()).length() > 0);
                assertTrue("Empty display value returned for " + listChoice.getAPIVALUE(), ((String) lrxt.getDATALIST().getLISTVALUE().get(0).getDISPLAYNAME()).length() > 0);

            }


        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            HibernateSession.closeSession();
        }

    }

    public static void main(String[] args) throws Exception {
        ShippingAPITest test = new ShippingAPITest("hello");
        test.testShipOnePackageAndVoidShipment(new OrderStatus("9765403"),2.0f);
    }
    public void testShipOnePackageAndVoidShipment(OrderStatus order, float weightlbs) throws Exception
    {

        try {
            SHIPREQUEST lr = new SHIPREQUEST();

            lr.setLOCATIONCODE(order.getLocation());
            lr.setSHIPPER(ManifestingManager.getLiveShipperForLocation(order.getLocation()));
            lr.setLABELPRINTER("Zebra.ZebraZ4Mplus");
            PACKAGE pack = new PACKAGE();

            pack.setBARCODE(order.orderBarcode.replaceAll("\\*", ""));
            pack.setWEIGHT_LBS(""+weightlbs);
            lr.setPACKAGELIST(new PACKAGELIST());

            //System.out.println(":"+lr.getPACKAGELIST());
            lr.getPACKAGELIST().getPACKAGE().add(pack);

             StringBuffer sb = new StringBuffer();

            for(LineItem line:(Vector<LineItem>)order.items) {
                sb.append( "<line>" +
                        "<fkey>"+line.line_item_id+"</fkey>" +
                        "<qty>1</qty>" +
                        "</line>");
            }
                String s = "<OWDPack>" +
                        "<order>" +
                        "<orderfkey>" + order.order_id + "</orderfkey>" +
                        "<packer>51</packer>" +
                        "<start>8/24/2007 10:45:50 AM</start>" +
                        "<stop>8/24/2007 10:46:45 AM</stop>" +
                        "<barcode>" + order.orderBarcode + "</barcode>" +
                        "<facility>DC1</facility>" +
                        "<box>" +
                        "<fkey>0</fkey>" +
                        "<cost>0</cost>" +
                        "<depth>1</depth>" +
                        "<width>2</width>" +
                        "<height>3</height>" +
                        "<cost>0.00</cost>" +
                        "<weight>"+weightlbs+"</weight>" +
                        "</box>" +
                        "<items>" +
                        sb.toString()+
                        "</items>" +
                        "</order>" +
                        "</OWDPack>";

            String boxBarcode =    singleBox.insertSingleBox(s);
            boxBarcode = boxBarcode.substring(boxBarcode.indexOf(":")+1);

            SHIPRESPONSE lrx = ShipRequest.handle(lr, false, 1.0,"TEST");


            Marshaller m = ConnectShipCommunications.getMarshaller("com.owd.connectship.xml.api.OWDShippingResponse");
            m.marshal(lrx,System.out);

            String data = lrx.getSHIPMENT().get(0).getMSN();
            System.out.println("got MSN = "+data);
            String rawLabel = lrx.getSHIPMENT().get(0).getLABELDATA().get(0).getValue();
            System.out.println(rawLabel);
            String labelData = new String(Base64.decodeBase64(rawLabel));

            mailLabel(order,labelData,"owditadmin@owd.com");
            System.out.println("****");
            System.out.print(labelData);
            System.out.println("");
            System.out.println("****");
            System.out.println("Voiding shipment");
            VOIDREQUEST lrv = new VOIDREQUEST();

            lrv.setLOCATIONCODE(order.getLocation());
            lrv.setSHIPPER(order.getLocation());
            lrv.getBARCODE().add(boxBarcode);


            VOIDRESPONSE lrxv = VoidRequest.handle(lrv, false, 1.0);

            m.marshal(lrxv,System.out);
            assertTrue("Void shipment response contained error "+lrxv.getVOIDRESULT().get(0).getResults(),lrxv.getVOIDRESULT().get(0).getResults().equals("SUCCESS"));

          //  com.owd.core.business.order.Package.voidOrderPackage(,order.order_id);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            HibernateSession.closeSession();
        }
    }

    public void testShipOnePackageAndVoidShipment() throws Exception
    {

            try {
              SHIPREQUEST lr = new SHIPREQUEST();

              lr.setLOCATIONCODE("DC4");
              lr.setSHIPPER("LA1");
              lr.setLABELPRINTER("PDF:Zebra.ZebraS600");
                PACKAGE pack = new PACKAGE();

                pack.setBARCODE("10677470R2");
                pack.setWEIGHT_LBS("2.0");
                lr.setPACKAGELIST(new PACKAGELIST());

                //System.out.println(":"+lr.getPACKAGELIST());
                 lr.getPACKAGELIST().getPACKAGE().add(pack);
                SHIPRESPONSE lrx = ShipRequest.handle(lr, false, 1.0,"TEST");


              Marshaller m = ConnectShipCommunications.getMarshaller("com.owd.connectship.xml.api.OWDShippingResponse");
               m.marshal(lrx,System.out);

                String data = lrx.getSHIPMENT().get(0).getMSN();
                System.out.println("got MSN = "+data);
                System.out.println("Voiding shipment");
                VOIDREQUEST lrv = new VOIDREQUEST();

                   lrv.setLOCATIONCODE("DC4");
                             lrv.setSHIPPER("LA1");
                             lrv.getBARCODE().add("10677470R2");


                                 VOIDRESPONSE lrxv = VoidRequest.handle(lrv, false, 1.0);

                              m.marshal(lrxv,System.out);
                            assertTrue("Void shipment response contained error "+lrxv.getVOIDRESULT().get(0).getResults(),lrxv.getVOIDRESULT().get(0).getResults().equals("SUCCESS"));



          } catch (Exception ex) {
              ex.printStackTrace();
              throw ex;
          } finally {
              HibernateSession.closeSession();
          }
    }
    public void testUSPSCustomsPDF() throws Exception
    {
         ConnectShipShipment shipment = new ConnectShipShipment();
           shipment.loadOrderFromOrderReference("6570328R2",true,"DC1");
        shipment.setValue("CURRENT_FACILITY","DC1");

        shipment.setValue(ShipConfig.WEIGHT, 2.0);

      /* // List<byte[]> pdfdata = ConnectShipWebServices.printIntlDocPDF(shipment);
        assertTrue("No PDF docs returned from intl test!",pdfdata.size()>0);
        for(byte[] pdfpage:pdfdata)
        {

        assertTrue("PDF file not decoded properly",pdfpage[0]==37);
        }*/
    }
      public void testDOCREQUEST() throws Exception {


          try {
              DOCREQUEST lr = new DOCREQUEST();

              lr.setLOCATIONCODE("DC4");

              lr.setSHIPPER("OWD");
              lr.setBARCODE("6221722");
              lr.setDOCTYPE("INTLDOC");


                  DOCRESPONSE lrx = DocRequest.handle(lr, false, 1.0);

             //  Marshaller m = ConnectShipCommunications.getMarshaller("com.owd.connectship.xml.api.OWDShippingResponse");
             //  m.marshal(lrx,System.out);

                assertTrue("No PDF docs returned",lrx.getINTLDOCDATA().size()>0);
        for(INTLDOCDATA pdfpageStr:lrx.getINTLDOCDATA())
        {
            
            byte[] pdfpage = Base64.decodeBase64(pdfpageStr.getValue());
        assertTrue("PDF file not decoded properly",pdfpage[0]==37);
              assertTrue("PDF file not decoded properly",pdfpage[1]==80);
              assertTrue("PDF file not decoded properly",pdfpage[2]==68);
              assertTrue("PDF file not decoded properly",pdfpage[3]==70);
        }






          } catch (Exception ex) {
              ex.printStackTrace();
              throw ex;
          } finally {
              HibernateSession.closeSession();
          }

      }


    public void mailLabel(OrderStatus order, String zplString, String toMail) throws Exception
    {

        Client client = ClientBuilder.newClient();
        // adjust print density (8dpmm), label width (4 inches), label height (6 inches), and label index (0) as necessary
        WebTarget target = client.target("http://api.labelary.com/v1/printers/8dpmm/labels/4x6/0/");
        Invocation.Builder request = target.request();
       // request.accept("application/pdf"); // omit this line to get PNG images back
        Response response = request.post(Entity.entity(zplString, MediaType.APPLICATION_FORM_URLENCODED));

        if (response.getStatus() == 200) {
            byte[] body = response.readEntity(byte[].class);
           // File file = new File("label.pdf"); // change file name for PNG images
           // Files.write(file.toPath(), body);

            Mailer.sendMailWithAttachment("Label for "+order.OWDorderReference,"",toMail,body,order.OWDorderReference+"_label.png","image/png") ;
        } else {
            String body = response.readEntity(String.class);
            System.out.println("Error: " + body);
        }



    }
}
