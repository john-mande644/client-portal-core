package com.owd.core.business.order.shippingAdjustments.clientInterfaces

import com.owd.core.OWDUtilities
import com.owd.hibernate.HibernateSession
import com.owd.hibernate.generated.OwdLineItem
import com.owd.hibernate.generated.OwdOrder
import groovy.xml.XmlUtil
import groovyx.net.http.RESTClient
import org.apache.commons.codec.binary.Base64
import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.entity.BufferedHttpEntity
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.util.EntityUtils
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

/**
 * Created by IntelliJ IDEA.
 * User: StewartBuskirk
 * Date: Aug 16, 2010
 * Time: 1:41:43 PM
 * To change this template use File | Settings | File Templates.
 */
class OrderReleaseAPI
{
    private final static Logger log =  LogManager.getLogger();

    def public static byte[] getOrderReleaseApprovalAndPackingSlip(String serviceUrl, int orderid, boolean packSlipOnly) throws Exception
    {
        return getOrderReleaseApprovalAndPackingSlip(serviceUrl,orderid,packSlipOnly,null);

    }


    def public static byte[] getSymphonyPackingSlip(int orderid) throws Exception{


        byte[] pdfdata = null;

        /*
        <?xml version="1.0" encoding="utf8"
?>
<SymphonyApiRequest apiKey="bb39126d34a3" clientHandle="famous_client" >
<Service name="fulfillment" method="getPackingSlip">
<Order symphonyOrderId="1000948">
<ShippingAddress>
<AddressLine1>363 Clementina St</AddressLine1>
<AddressLine2></AddressLine2>
<City>San Francisco</City>
<Region>California</Region>
<Country>USA</Country>
<PostalCode>94103</PostalCode>
</ShippingAddress>
<PackageItems>
<Item sku="P74915">
<Quantity>1</Quantity>
</Item>
</PackageItems>
</Order>
</Service>
</SymphonyApiRequest>
         */
        OwdOrder order = HibernateSession.currentSession().load(OwdOrder.class, orderid);

        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def slipRequest =
                {
                    mkp.xmlDeclaration()
                    SymphonyApiRequest(apiKey: 'f3735208ac53488f3038344809466144', clientHandle:'owd') {
                        Service(name:'fulfillment', method:'getPackingSlip') {
                           Shipment(id:order.getOrderRefnum())
                                   {
                               ShippingAddress() {
                                   AddressLine1(order.getShipinfo().getShipAddressOne())
                                   AddresssLine2(order.getShipinfo().getShipAddressTwo())
                                   City(order.getShipinfo().getShipCity().trim().length()<1?".":order.getShipinfo().getShipCity())
                                   Region(order.getShipinfo().getShipState().trim().length()<1?".":order.getShipinfo().getShipState())
                                   Country(order.getShipinfo().getShipCountry())
                                   PostalCode(order.getShipinfo().getShipZip().trim().length()<1?".":order.getShipinfo().getShipZip())
                               }
                                       Barcode(order.getOrderNumBarcode().replaceAll("\\*", ""))
                                    /*   PackageItems(){
                                           for(OwdLineItem li:order.getLineitems())
                                           {
                                               Item(sku:li.getInventoryNum()){
                                                Quantity(li.getQuantityActual())
                                               }
                                           }*/
                                       }
                                   }
                        }
                    }

        println "sending"
        println builder.bind(slipRequest).toString()

        def final endpoint = new RESTClient("https://manage.symphonycommerce.com")

        endpoint.post(path: '/api/symphony/',
                requestContentType: 'text/xml',
                contentType:'text/xml',
                body: builder.bind(slipRequest).toString()) { resp, reader ->


            println XmlUtil.serialize(reader)
            if(reader?.Service?.Shipment?.PackingSlip?.text()?.length()>4)
            {
                try
                {
                 //   println reader?.Service?.Shipment?.PackingSlip?.text()
                 //   println new String(reader?.Service?.Shipment?.PackingSlip?.text().getBytes(),"ISO-8859-1")
                    pdfdata = Base64.decodeBase64(reader?.Service?.Shipment?.PackingSlip?.text())
                  //  println     "PDF:"+pdfdata
                    //    pdfdata = DatatypeConverter.parseBase64Binary(new String(reader?.Service?.Shipment?.PackingSlip?.text().getBytes(),'UTF-8'))
                }catch(Exception expdf)
                {
                    expdf.printStackTrace();
                    pdfdata = null;
                }
            } else
            {

            }


        }

        if(pdfdata?.length>4)
        {
            return pdfdata;
        } else {
            return null;
        }

    }
    def public static byte[] getMarineEssentialsPackingSlip(String serviceUrl, Map<String, String> paramMap) throws Exception
    {

      //  WebResource server = new WebResource(serviceUrl, WebResource.kGETMethod);

        String url = serviceUrl
        int index=0;
        for(String key:paramMap.keySet())
        {
            if (index ==0 )
            {
               url = url+"?" +key+"="+ paramMap.get(key)
            }        else{
                url = url+"&" +URLEncoder.encode(key)+"="+ URLEncoder.encode(paramMap.get(key))

            }
            index++;
           // server.addParameter(key,paramMap.get(key))
        }
        println url


        HttpGet req = new HttpGet(url);
        HttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(req);
// validate response code, etc.

        HttpEntity entity = (HttpEntity) response.getEntity();
        if (entity != null) {
            entity = new BufferedHttpEntity(entity);
        }

        byte[] pdfdata = null;

        pdfdata = EntityUtils.toByteArray(entity)
        println               "Bytes:"+pdfdata.size()

        //   InputStream is = server.getResourceAsInputStream(false)
      //  println OWDUtilities.parseISToString(is)
      //  pdfdata = getByteArrayFromIS(is)

        if(pdfdata?.length>4096 && response.getStatusLine().getStatusCode()<300)
        {
            return pdfdata;
        } else {
            return null;
        }
        

    }
    


    def public static byte[] getOrderReleaseApprovalAndPackingSlip(String serviceUrl, int orderid, boolean packSlipOnly, LineItemVerifier lineChecker) throws Exception
    {


        OwdOrder order = HibernateSession.currentSession().load(OwdOrder.class, orderid);

       // println "tax:"+order.getTaxAmount()

        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        println "sending photojojo request for id "+order.getOrderRefnum()

        URL testUrl = new URL(serviceUrl);

        URLConnection testConnection = (URLConnection) testUrl.openConnection();
        testConnection.setReadTimeout(120000);
        testConnection.setConnectTimeout(120000);
        testConnection.setRequestProperty("Content-Type", "text/xml");
        testConnection.setRequestProperty("User-Agent", "OWD");
        testConnection.setRequestMethod("POST");
        testConnection.setDoOutput(true);


   /*     PrintWriter p_out2 = new PrintWriter(System.out);
       p_out2 << builder.bind(request)
        p_out2.close();*/

builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'

        Closure request = getRequestForOwdOrder(order, packSlipOnly, lineChecker)
        PrintWriter p_out = new PrintWriter(testConnection.getOutputStream());
        p_out << builder.bind(request)
        p_out.close();

        log.debug("getting response");
        String response = OWDUtilities.parseISToString(testConnection.getInputStream());
        println ("RESPONSE: "+response);

      System.out.flush();

        Node resp = new XmlParser().parse(new BufferedReader(new InputStreamReader(OWDUtilities.parseStringToIS(response,"UTF-8"))))

        println "response OK:"+ resp.OK_TO_SHIP.text();
        if((!(packSlipOnly)) && (!(resp.OK_TO_SHIP.text().equals("1"))))
        {
            throw new OrderInvalidatedException("Response did not approve order release");
        }
      //  println "packing slip response:"+resp.PACKING_SLIP.text();



        byte[] pdfdata = null;
        if(resp.PACKING_SLIP.text().length()>4)
        {
            try
            {
                pdfdata = org.apache.commons.codec.binary.Base64.decodeBase64(""+resp.PACKING_SLIP.text())
            }catch(Exception expdf)
            {
                expdf.printStackTrace();
                pdfdata = null;
            }
        }

        if(pdfdata?.length>4)
        {
            return pdfdata;
        } else {
            return null;
        }

       // File temp = File.createTempFile("jojo",".pdf");
      //  temp.setWritable(true);
      //  temp.append(pdfdata);

      //  println temp.canonicalPath
      //  println System.getProperty("java.io.tmpdir");

    }


    protected static Closure getRequestForOwdOrder(OwdOrder order, boolean packSlipOnly,  LineItemVerifier lineChecker)
    {
        def request = {
            mkp.xmlDeclaration()
            OWD_ORDER_APPROVAL_REQUEST {
                PACK_SLIP_ONLY(packSlipOnly ? "1" : "0")
                OWD_ORDER_REF(order.getOrderNum())
                CLIENT_ORDER_REF(order.getOrderRefnum())
                ORDER_BARCODE(order.getOrderNumBarcode().replaceAll("\\*", ""))
                SHIP_METHOD(order.getShipinfo().getCarrService())
                DISCOUNT_AMOUNT(String.format('%.2f', Math.abs(order.getDiscount().doubleValue())))
                TAX_AMOUNT(order.getTaxAmount() == 0.00 ? "0.00" : String.format('%.2f', order.getTaxAmount().doubleValue()))
                SHIPPING_AMOUNT(String.format('%.2f', order.getShipHandlingFee().doubleValue()))
                BILLING_ADDRESS {
                    NAME(order.getBillFirstName() + " " + order.getBillLastName())
                    COMPANY(order.getBillCompanyName())
                    ADDRESS_1(order.getBillAddressOne())
                    ADDRESS_2(order.getBillAddressTwo())
                    CITY(order.getBillCity())
                    REGION(order.getBillState())
                    POSTAL_CODE(order.getBillZip())
                    COUNTRY(order.getBillCountry())
                    PHONE(order.getBillPhoneNum())
                    EMAIL(order.getBillEmailAddress())
                }
                SHIPPING_ADDRESS {
                    NAME(order.getShipinfo().getShipFirstName() + " " + order.getShipinfo().getShipLastName())
                    COMPANY(order.getShipinfo().getShipCompanyName())
                    ADDRESS_1(order.getShipinfo().getShipAddressOne())
                    ADDRESS_2(order.getShipinfo().getShipAddressTwo())
                    CITY(order.getShipinfo().getShipCity())
                    REGION(order.getShipinfo().getShipState())
                    POSTAL_CODE(order.getShipinfo().getShipZip())
                    COUNTRY(order.getShipinfo().getShipCountry())
                    PHONE(order.getShipinfo().getShipPhoneNum())
                    EMAIL(order.getShipinfo().getShipEmailAddress())
                }
                LINE_ITEMS {
                    // <!--1 or more repetitions:-->
                    for (OwdLineItem line: order.getLineitems())
                    {
                        if((lineChecker==null || lineChecker.includeLineItemInRequest(line))&&line.getQuantityActual()>0)
                        {
                        LINE {
                            SKU(line.getInventoryNum())
                            QUANTITY_SHIPPED(line.getQuantityActual())
                            QUANTITY_BACKORDERED(line.getQuantityBack())
                            TITLE(line.getDescription())
                            UNIT_PRICE(line.getPrice())
                            COLOR(line.getItemColor())
                            SIZE(line.getItemSize())
                            LINE_NUMBER(line.getCustRefnum())
                            println "LINE"
                        }


                        }
                    }
                }


            }
        }
        def builder = new groovy.xml.StreamingMarkupBuilder()

        println builder.bind(request).toString()


        return request
    }



    public static void main(String[] args) throws Exception
    {

                                                                                                                                                              //
      //  OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, 6430311);


    //    saveFileFromUrlWithJavaIO("/tmp/test.pdf","https://www.marineessentials.com/thinvoice/generate_invoice/?barcode=10097648&orderId=224723")
       // byte[] pdfdata = RemoteOrderReleaseAPI.getMarineEssentialsPackingSlip("https://www.marineessentials.com/thinvoice/generate_invoice/",mapper);

        byte[] pdfdata = OrderReleaseAPI.getSymphonyPackingSlip( 9170108);
        log.debug( "got data length="+pdfdata?.length );
        File temp = File.createTempFile("jojox",".pdf");
          temp.setWritable(true);
          temp.append(pdfdata);

        log.debug( temp.canonicalPath  );
                log.debug( System.getProperty("java.io.tmpdir"));
         }


// Using Java IO
        public static void saveFileFromUrlWithJavaIO(String fileName, String fileUrl)
        throws MalformedURLException, IOException {

            BufferedInputStream inx = null

            FileOutputStream fout = null;
            try {
                inx = new BufferedInputStream(new URL(fileUrl).openStream());
                fout = new FileOutputStream(fileName);

                byte[] data = new byte[1024];
                int count;
                while ((count = inx.read(data, 0, 1024)) != -1) {
                    fout.write(data, 0, count);
                }
            } finally {
                if (inx != null)
                inx.close();
                if (fout != null)
                    fout.close();
            }
        }



}
