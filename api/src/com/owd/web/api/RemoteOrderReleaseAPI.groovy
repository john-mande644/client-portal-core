package com.owd.web.api

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities
import com.owd.core.business.order.LineItem
import com.owd.hibernate.HibernateSession
import com.owd.hibernate.generated.OwdLineItem
import com.owd.hibernate.generated.OwdOrder
import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.entity.BufferedHttpEntity
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.util.EntityUtils
import org.castor.core.util.Base64Decoder

/**
 * Created by IntelliJ IDEA.
 * User: StewartBuskirk
 * Date: Aug 16, 2010
 * Time: 1:41:43 PM
 * To change this template use File | Settings | File Templates.
 */

class RemoteOrderReleaseAPI
{
    private final static Logger log =  LogManager.getLogger();

    def public static byte[] getOrderReleaseApprovalAndPackingSlip(String serviceUrl, int orderid, boolean packSlipOnly) throws Exception
    {
        return getOrderReleaseApprovalAndPackingSlip(serviceUrl,orderid,packSlipOnly,null);

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

        if(pdfdata?.length>4096)
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
        log.debug("RESPONSE: "+response);

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
                pdfdata = Base64Decoder.decode(resp.PACKING_SLIP.text())
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
                        if(lineChecker==null || lineChecker.includeLineItemInRequest(line))
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
                            }
                        }
                    }
                }


            }
        }
        return request
    }



    public static void main(String[] args) throws Exception
    {

        //
        OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, 5890732);

        Map<String, String> mapper =new TreeMap<String,String>();
        mapper.put("orderId","251921");//order.getOrderRefnum())
        mapper.put("barcode",order.getOrderNumBarcode().replaceAll("\\*", ""))


        //    saveFileFromUrlWithJavaIO("/tmp/test.pdf","https://www.marineessentials.com/thinvoice/generate_invoice/?barcode=10097648&orderId=224723")
        // byte[] pdfdata = RemoteOrderReleaseAPI.getMarineEssentialsPackingSlip("https://www.marineessentials.com/thinvoice/generate_invoice/",mapper);

        byte[] pdfdata = RemoteOrderReleaseAPI.getOrderReleaseApprovalAndPackingSlip("http://ws.getkarma.com/shipment/approve", 6182134, true);


        if (pdfdata != null) //if no exception thrown but no data received, then proceed but punt to normal template
        {
            //      println(pdfdata)

            BufferedOutputStream bs = null;
            File temp = File.createTempFile("marine",".pdf");
            try {

                FileOutputStream fs = new FileOutputStream(temp);
                bs = new BufferedOutputStream(fs);
                bs.write(pdfdata);
                bs.close();
                bs = null;

            } catch (Exception e) {
                e.printStackTrace()
            }

            if (bs != null) try { bs.close(); } catch (Exception e) {}

            println temp.canonicalPath
            println System.getProperty("java.io.tmpdir");

        }   else
        {
            println "No pdf data found!"
        }
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

public abstract class LineItemVerifier {
private final static Logger log =  LogManager.getLogger();

    abstract public boolean includeLineItemInRequest(LineItem line);
}

public class OrderInvalidatedException extends Exception
{
    public OrderInvalidatedException()
    {
        super();
    }
    public OrderInvalidatedException(String message)
    {
        super(message);
    }
}
