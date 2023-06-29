package com.owd.jobs.jobobjects.RetailPro.models

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities
import com.owd.core.WebResource
import com.owd.core.business.order.LineItem
import com.owd.core.managers.ConnectionManager
import com.owd.hibernate.HibUtils
import com.owd.hibernate.HibernateSession
import com.owd.jobs.jobobjects.RetailPro.TransferUtilities
import com.owd.jobs.jobobjects.RetailPro.models.Voucher.VouItem
import org.apache.xpath.XPathAPI
import org.hibernate.SQLQuery
import org.w3c.dom.Document

import javax.xml.parsers.DocumentBuilderFactory
import java.sql.Connection
import java.sql.ResultSet
import java.text.DateFormat
import java.text.SimpleDateFormat

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 2/22/12
 * Time: 12:42 PM
 * To change this template use File | Settings | File Templates.
 */
class PurchaseOrder {
    private final static Logger log =  LogManager.getLogger();

    static DateFormat printDf = new SimpleDateFormat("yyyyMMdd");

    public static String renderFormattedXml(String xml) {
        if (xml) {
            def stringWriter = new StringWriter()
            def node = new XmlParser().parseText(xml.toString());
            new XmlNodePrinter(new PrintWriter(stringWriter)).print(node)
            return stringWriter.toString()
        }
        else {
            return ""
        }
    }

    public static void main(String[] args) {
            println "run"

processPurchaseOrders()
    }


    public static void processPurchaseOrders() {

                          println "start"
        Map<String, byte[]> toMap = TransferUtilities.getRetailProFiles("PO_")

        for (String filename: toMap.keySet()) {

            try
            {

            def purchaseOrder = new XmlSlurper().parseText(new String(toMap.get(filename)))
            int duplicateCount = 0
            int poCount = 0
            // def xmlString = new StreamingMarkupBuilder().bindNode(item).toString()
           //    println  renderFormattedXml(new String(toMap.get(filename)))
            purchaseOrder.PO.each { it ->
                List<POItem> items = new ArrayList<POItem>();
                poCount++
                String poId = it.@po_no

                println "read PO id "+poId
                String vendor = it.@vend_code
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 1);
                String dateStr = printDf.format(cal.getTime());//parseDf.parse(rs.getString("ETA").trim()));
                String notes = it.@instruction1



                it.PO_ITEMS.PO_ITEM.each { item ->


                    String currentSku = (""+item.@ECommID.text())
                    currentSku = currentSku.trim()

                    if (LineItem.SKUExists("482", currentSku + "/KIT")) {
                        currentSku = currentSku + "/KIT";
                    }

                    if (!(LineItem.SKUExists("482", currentSku))) {
                        throw new Exception("Unable to import purchase order " + poId + " from "+filename+" due to invalid SKU value " + currentSku+"/"+item.@ECommID.text())
                    }


                    if (LineItem.SKUExists("482", currentSku + "/KIT")) {
                        currentSku = currentSku + "/KIT";
                    }

                    if (Integer.parseInt(item.PO_QTYS.PO_QTY.@ord_qty.text()) > 0) {
                        POItem poitem = new POItem();

                        poitem.sku = currentSku;
                        poitem.qty = Integer.parseInt(item.PO_QTYS.PO_QTY.@ord_qty.text());
                        poitem.desc = "";

                        items.add(poitem);
                    }

                }

                if (items.size() > 0) {
                    List oldies = HibernateSession.currentSession().createSQLQuery("select count(*) from asn where client_fkey=482 and client_ref='" + poId + "'").list();
                    if (((Integer) (oldies.get(0))) == 0) {
                        createAsn(poId, vendor, notes, dateStr, items);

                    } else {
                        //duplicate!
                        List updates = HibernateSession.currentSession().createSQLQuery("select max(id) from asn where client_fkey=482 and client_ref='" + poId + "'").list();
                        if (((Integer) (updates.get(0))) > 0) {
                            updateAsn(((Integer) (updates.get(0))), poId,vendor, notes, dateStr, items) ;


                        }
                    }
                    duplicateCount++

                }


                println "id:" + it.@po_no

            }

            if(poCount == duplicateCount)
            {
                 TransferUtilities.moveRetailProFileToDone(filename)
            }
        }catch(Exception ex)
            {
                ex.printStackTrace();
            }

        }
    }

    public static void createAsn(String po, String vendor, String notes, String dateStr, List<POItem> items) throws Exception {

        String cid="482"
        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def createAsnRequest =
        {
            mkp.xmlDeclaration()
            OWD_API_REQUEST(api_version: '1.0', client_authorization: OWDUtilities.encryptData("" + cid), client_id: cid, testing: 'FALSE') {
                OWD_ASN_CREATE_REQUEST() {
                    REFERENCE(po)
                    PO_REFERENCE(po)
                    SHIPPER(vendor)
                    EXPECTED_DATE(dateStr)
                    CARRIER('Unknown')
                    AUTORELEASE('1')
                    CARTONS('0')
                    PALLETS('0')
                    NOTES(notes)
                    ASNITEMS()
                            {
                                for (POItem item: items) {
                                    ASNITEM()
                                            {
                                                ASNITEM_SKU(item.sku)
                                                ASNITEM_EXPECTED(item.qty)
                                                ASNITEM_DESCRIPTION("")
                                            }
                                }


                            }
                }
            }

        }


        println "sending"
        println builder.bind(createAsnRequest).toString()

        Document response = getServerAPIResponse(builder.bind(createAsnRequest).toString(), "http://service2.owd.com:8080/api/api.jsp");
     //   verifyAPIResponse((Document) response, response.getDocumentElement());

        //   prettyPrint(response.getDocumentElement(), System.out);

    }

    public static void updateAsn(Integer OwdAsnId, String po, String vendor, String notes, String dateStr, List<POItem> items) throws Exception {

        String cid="482"
        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def updateAsnRequest =
            {
                mkp.xmlDeclaration()
                OWD_API_REQUEST(api_version: '1.0', client_authorization: OWDUtilities.encryptData("" + cid), client_id: cid, testing: 'FALSE') {
                    OWD_ASN_UPDATE_REQUEST(id:OwdAsnId) {
                        REFERENCE(po)
                        PO_REFERENCE(po)
                        SHIPPER(vendor)
                        EXPECTED_DATE(dateStr)
                        NOTES(notes)
                        ASNITEMS()
                                {
                                    for (POItem item: items) {
                                        ASNITEM()
                                                {
                                                    ASNITEM_SKU(item.sku)
                                                    ASNITEM_EXPECTED(item.qty)
                                                    ASNITEM_DESCRIPTION("")
                                                }
                                    }


                                }
                    }
                }

            }


        println "sending"
        println builder.bind(updateAsnRequest).toString()

        Document response = getServerAPIResponse(builder.bind(updateAsnRequest).toString(), "http://service2.owd.com:8080/api/api.jsp");
        //   verifyAPIResponse((Document) response, response.getDocumentElement());

        //   prettyPrint(response.getDocumentElement(), System.out);

    }

    static Document getServerAPIResponse(String apiRequest, String url) throws Exception {
        WebResource catalog = new WebResource(url, WebResource.kPOSTMethod);
        catalog.setContent(apiRequest);
        catalog.contentType = "text/xml";

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(false);
        factory.setValidating(false);
        javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();




        URL testUrl = new URL(url);
        HttpURLConnection testConnection = (HttpURLConnection) testUrl.openConnection();

        testConnection.setRequestProperty("Content-Type", "text/xml");
        testConnection.setRequestMethod("POST");
        testConnection.setDoOutput(true);
        testConnection.getOutputStream().write(apiRequest.getBytes("UTF-8"));
        testConnection.getOutputStream().close();


        Document doc = builder.parse(testConnection.getInputStream());
        doc.getDocumentElement().normalize();


        return doc;

    }

    static public void verifyAPIResponse(Document response) throws Exception {


        if (!(XPathAPI.selectSingleNode(response, "OWD_API_RESPONSE").getAttributes().getNamedItem("results").getNodeValue().equals("SUCCESS"))) {
            throw new Exception("failed ASN creation - bad API response")
        }

    }


    public static void reportReceives()
    {
        String vid="";
          Connection cxn = null;
        try {
            cxn = ConnectionManager.getConnection();

            ResultSet rs = HibernateSession.getResultSet("select * from vw_babeland_radj where type='Receive' order by receive_id asc ");

              Voucher vou = new Voucher();
              vou.type= Voucher.kVoucherReceiveType
                 while(rs.next())
                 {

                     vid=rs.getString("receive_id")
                     log.debug("reading line for "+vid);

                     if(!(vou.id.equals(vid)))
                     {
                         if(vou.id != null)
                         {
                             String fileData = vou.getVoucherXML()
                             println fileData
                             TransferUtilities.putRetailProFile("VOU_"+Calendar.getInstance().getTimeInMillis()+".xml",fileData, true)
                             SQLQuery query = HibernateSession.currentSession().createSQLQuery("update owd_receive_item set reported=1 where receive_fkey="+vou.id+";");
                             query.executeUpdate();
                             HibUtils.commit(HibernateSession.currentSession());
                         }
                         vou.id=vid
                         vou.poNumber = rs.getString("ref_num")
                         vou.notes=rs.getString("notes")
                         vou.voucherItems=new ArrayList<VouItem>()
                     }
                     vou.addVoucherItem(rs.getString("sku").replaceAll("/KIT",""),rs.getInt("qty"))
                 }

if((vou.id.equals(vid)))
{
                             String fileData = vou.getVoucherXML()
    println fileData

    TransferUtilities.putRetailProFile("VOU_"+Calendar.getInstance().getTimeInMillis()+".xml",fileData, true)
                            SQLQuery query = HibernateSession.currentSession().createSQLQuery("update owd_receive_item set reported=1 where receive_fkey="+vou.id+";");
                             query.executeUpdate();
                             HibUtils.commit(HibernateSession.currentSession());
                         }




        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            HibernateSession.closeSession();
            ConnectionManager.freeConnection(cxn);
        }

    }


}

public class POItem {
private final static Logger log =  LogManager.getLogger();
    String sku;
    int qty;
    String desc;
}
