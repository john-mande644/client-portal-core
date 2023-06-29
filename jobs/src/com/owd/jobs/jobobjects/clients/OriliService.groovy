package com.owd.jobs.jobobjects.clients

import org.apache.commons.io.IOUtils
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.Mailer
import com.owd.core.OWDUtilities
import com.owd.hibernate.HibernateSession
import com.owd.hibernate.generated.OwdLineItem
import com.owd.hibernate.generated.OwdOrder
import groovyx.net.http.ContentType
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.text.SimpleDateFormat

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 6/1/11
 * Time: 9:41 AM
 * To change this template use File | Settings | File Templates.
 */

class OriliService {

    static Logger log = LogManager.getLogger(this.name)

    OriliService() {
         log.debug("Creating OriliService")
    }

    private TreeMap<Integer, List<Map<String, Integer>>> getAsnMap() {
        if (AsnMap == null) {

            String sql = """SELECT
    inventory_fkey          AS 'iid',
    client_ref              AS 'ASN Reference',
    qty_receive,
    post_date

FROM
   receive
join receive_item on receive.id=receive_item.receive_fkey
join asn on asn_fkey=asn.id
where qty_receive>0 and receive.client_fkey=477
order by inventory_fkey,post_date,receive.id""";

            ResultSet rs = HibernateSession.getResultSet(HibernateSession.currentSession(), sql);
            while (rs.next()) {
                if (AsnMap == null) {
                    AsnMap = new TreeMap<Integer, List<Map<String, Integer>>>();
                }
                if (AsnMap.get(rs.getInt(1)) == null) {
                    AsnMap.put(rs.getInt(1), new ArrayList<Map<String, Integer>>())
                }
                if (rs.getInt(3) > 0) {
                    Map<String, Integer> itemMap = new TreeMap<String, Integer>();
                    itemMap.put(rs.getString(2), rs.getInt(3));
                    AsnMap.get(rs.getInt(1)).add(itemMap);

                }

            }
            rs.close();
            println(AsnMap)
        }

        return AsnMap
    }

    private Map<String, Integer> getLastItemShippedBySkuMap() {
        if (ItemBySkuMap == null) {

            String sql = """SELECT
    * from vw_orili_lastitembysku order by inventory_num""";
            ItemBySkuMap = new TreeMap<String, Integer>();
            ResultSet rs = HibernateSession.getResultSet(HibernateSession.currentSession(), sql);
            while (rs.next()) {

                ItemBySkuMap.put(rs.getString(1), rs.getInt(2));
            }


            rs.close();
            println(ItemBySkuMap)
        }

        return ItemBySkuMap
    }

    def void sendCurrentASNReferenceBySKUEmail() throws Exception {

        StringBuilder str = new StringBuilder();
        StringBuilder bodyStr = new StringBuilder();

        Calendar cal = GregorianCalendar.newInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        bodyStr.append("""
Current ASN/Shipment Reference By SKU

SKU | Previously Shipped | Remaining in Current ASN | ASN Reference | Next ASN Reference

""")

        Map<String, Integer> itemMap = getLastItemShippedBySkuMap();

        str.append("OWD ASN Ref Inventory Statement for Orili " + df.format(cal.getTime()) + "\r")
        str.append("\"\"\r")
        str.append("\"SKU\",\"Previously Shipped\",\"Remaining in Current ASN\",\"ASN Reference\",\"Next ASN Reference\"\r")
        for (String item : itemMap.keySet()) {
            OwdLineItem line = (OwdLineItem) HibernateSession.currentSession().load(OwdLineItem.class, itemMap.get(item));
            log.debug("checking SKU " + line.inventoryNum);

            if (line != null) {
                Map<String, String> dataMap = getASNInfoForLineItem(line);
                log.debug("" + dataMap);

                if (!(dataMap.get("ref")).equals("")) {

                    bodyStr.append(OWDUtilities.padRight(item, 16)
                            + " | " + OWDUtilities.padRight(dataMap.get("previousqty"), 8)
                            + " | " + OWDUtilities.padRight(dataMap.get("remaining"), 8)
                            + " | " + dataMap.get("ref")
                            + " | " + dataMap.get("nextref") + "\r\n")
                    str.append("\"" + item
                            + "\",\"" + dataMap.get("previousqty")
                            + "\",\"" + dataMap.get("remaining")
                            + "\",\"" + dataMap.get("ref")
                            + "\",\"" + dataMap.get("nextref") + "\"\r")
                }
            }
        }
        Mailer.sendMailWithAttachment("OWD ASN Ref Inventory Statement for Orili " + df.format(cal.getTime()), bodyStr.toString(), "hello@spiffygear.com", str.toString().getBytes(), "ASNRef_" + df.format(cal.getTime()) + ".csv", "text/csv");
        // Mailer.sendMailWithAttachment("OWD ASN Ref Inventory Statement for Orili " + df.format(cal.getTime()), bodyStr.toString(), "owditadmin@owd.com", str.toString().getBytes(), "ASNRef_"+df.format(cal.getTime()) + ".csv", "text/csv");

        log.debug("DONE");
        println(bodyStr)

    }

    def static endpoint = new RESTClient("https://www.diyphotography.net/")

    def void sendOrderShipmentNotification(int orderId) throws Exception {


        OwdOrder orderx = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, orderId)

        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def reportRequest =
            {
                mkp.xmlDeclaration()
                export() {
                    order() {
                        ship_to_country(orderx.getShipinfo().getShipCountry())
                        order_id("" + orderx.getOrderRefnum())
                        ship_to_first_name(orderx.getShipinfo().getShipFirstName())
                        ship_to_last_name(orderx.getShipinfo().getShipLastName())
                        special_instructions(orderx.getShipinfo().getComments())
                        ship_to_address1(orderx.getShipinfo().getShipAddressOne())
                        ship_to_address2(orderx.getShipinfo().getShipAddressTwo())
                        ship_to_city(orderx.getShipinfo().getShipCity())
                        ship_to_state(orderx.getShipinfo().getShipState())
                        ship_to_zip(orderx.getShipinfo().getShipZip())
                        shipping_handling_total(orderx.getShipHandlingFee())
                        bill_to_first_name(orderx.getBillFirstName())
                        bill_to_last_name(orderx.getBillLastName())
                        bill_to_address1(orderx.getBillAddressOne())
                        bill_to_address2(orderx.getBillAddressTwo())
                        bill_to_city(orderx.getBillCity())
                        bill_to_state(orderx.getBillState())
                        bill_to_zip(orderx.getBillZip())
                        tax(orderx.getTaxAmount())
                        subtotal(orderx.getOrderSubTotal())
                        ordertotal(orderx.getOrderTotal())
                        subtotal_discount(orderx.getDiscount())
                        shipping_handling_total_discount(0)


                        for (OwdLineItem itemx : orderx.getLineitems()) {
                            item() {
                                item_id(itemx.getInventoryNum())
                                quantity(itemx.getQuantityActual())
                                description(itemx.getDescription())
                                asn_ref(getASNReferenceForLineItem(itemx))
                                unit_cost_with_discount(itemx.getPrice())
                                cost(itemx.getPrice())
                            }
                        }
                    }
                }

            }

        def xml = builder.bind(reportRequest).toString()
        println xml



        try {


            HttpResponseDecorator resp = endpoint.post(
                    path: 'scripts/orili-owd2i4u.php',
                    body: xml,
                    requestContentType: ContentType.XML,
                    contentType: ContentType.TEXT
            ) as HttpResponseDecorator

            //    println("Location:"+resp.getAllHeaders())
            println("Status:" + resp.status)
            //   println("Type:"+resp.contentType)

            if (resp.status != 200) {
                throw new Exception("Unable to report shipment")
            }
            Mailer.sendMailWithAttachment("OWD Shipment Notification   " + orderx.getOrderRefnum(), "XML file attached", "hello@spiffygear.com", xml.getBytes(), orderx.getOrderRefnum() + ".xml", "text/xml");

            println IOUtils.toString(resp.data)
        }catch(HttpResponseException ex)
        {
            println "orili-reportshipment:"+ex.getMessage()
            if (!(ex.getMessage().contains("not processed")) )
            {
                throw ex
            }
        }

    }

    public static void main(String[] args) throws Exception {

        OriliService job = new OriliService()
        log.debug("starting OriliService");
        List<Integer> l = new ArrayList<>();
        l.add(16024541);



       //   job.sendCurrentASNReferenceBySKUEmail()
        //println(getPreviousShippedQuantity(10157322))
for(int i:l) {
    try {
        job.sendOrderShipmentNotification(i)
    }catch (Exception e ){
    e.printStackTrace();}
}



    }


    private Map<Integer, List<Map<String, Integer>>> AsnMap = null;
    private Map<String, Integer> ItemBySkuMap = null;


    private String getASNReferenceForLineItem(OwdLineItem line) {
        String ref = "";
        long previousQty = getPreviousShippedQuantity(line.lineItemId)
        log.debug("SKU:" + line.getOwdInventory().getInventoryNum() + ":::" + line.getOwdInventory().getInventoryId());
        log.debug("previouslyShipped:" + previousQty);
        int shippedQty = line.getQuantityActual();

        log.debug("keysize:" + (getAsnMap().keySet().size() > 0));
        if (getAsnMap() ? getAsnMap().keySet().size() > 0 : false) {
            List<Map<String, Integer>> receiveList = getAsnMap().get(line.getOwdInventory().getInventoryId());
            log.debug("ReceiveList=" + receiveList);
            if (receiveList?.size() > 0) {
                //        ref  = receiveList.get(0).keySet().toArray()[0]
                //    log.debug("Ref:"+ref);
                int rQty = 0;
                for (Map<String, Integer> receive : receiveList) {
                    ref = receive.keySet().toArray()[0]
                    log.debug("Ref:" + ref);
                    rQty = (Integer) receive.values().toArray()[0]
                    log.debug("rQty:" + rQty);
                    if (previousQty >= rQty) {
                        previousQty -= rQty

                    } else {
                        log.debug("Refx:" + ref);
                        return ref
                    }
                    log.debug("pqty:" + previousQty);
                    if (previousQty <= 0) {
                        log.debug("Refx:" + ref);
                        return ref
                    }
                }
            }
        }


        log.debug("Refx:" + ref);

        return ref;
    }

    static private long getPreviousShippedQuantity(int lineItemID) {
        long qty = 0;
        String sql = """select
(ISNULL(
((select sum(quantity_actual) from owd_line_item l2 join owd_order t2 join
(select min(order_track_id) as reftid2,order_fkey as refoid2 from owd_order_track t3 where t3.is_void=0 group by order_fkey) as tids2
on t2.order_id=refoid2 and reftid2<reftid
on t2.order_id=l2.order_fkey where l2.inventory_id=l.inventory_id )),0) +ISNULL(
(select sum(-1*quantity) from owd_receive_item ri join owd_receive r on r.receive_id=receive_fkey
where ri.inventory_id=l.inventory_id and type<>'Receive' and r.is_void=0 and r.created_date<tids.created_date),0)) as shipped_before
,

quantity_actual
 from owd_line_item l join owd_order t join
(select min(order_track_id) as reftid,order_fkey as refoid,min(created_date) as created_date from owd_order_track t3 where t3.is_void=0 group by order_fkey) as tids on refoid=t.order_id
on t.order_id=l.order_fkey and
line_item_id=?""";

        PreparedStatement ps = HibernateSession.getPreparedStatement(sql);
        ps.setInt(1, lineItemID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            qty = rs.getLong(1);
        }
        ps.close();
        rs.close();

        return qty;

    }


    private sendRecentLineItemDataByEmail() {

        ResultSet rs = HibernateSession.getResultSet("""select line_item_id,min(order_track_id)  from owd_line_item l join owd_order join owd_order_track t on order_id=t.order_fkey and t.is_void=0
                                                        on order_id=l.order_fkey and client_fkey=477  and inventory_num='BMK-ME'
                                                        and shipped_on>='2011-11-18' group by line_item_id order by min(order_track_id)""")
        List<Integer> ids = new ArrayList<Integer>();

        while (rs.next()) {
            ids.add(rs.getInt(1))
        }

        StringBuffer buf = new StringBuffer();

        for (int id : ids) {

            buf.append(getASNInfoForLineItem((OwdLineItem) HibernateSession.currentSession().load(OwdLineItem.class, id)) + "\r\n");
        }
        log.debug(buf.toString());
    }



    private Map<String, String> getASNInfoForLineItem(OwdLineItem line) {
        String ref = "";
        Map<String, String> dataMap = new TreeMap<String, String>()
        long previousQty = getPreviousShippedQuantity(line.lineItemId)
        SimpleDateFormat df = new SimpleDateFormat("yyy-MM-dd")
        dataMap.put("order", (line.getOrder().getOrderRefnum()));
        dataMap.put("shipped", df.format(line.getOrder().getShippedDate()));
        dataMap.put("lineid", "" + line.getLineItemId());
        dataMap.put("sku", line.getOwdInventory().getInventoryNum());
        dataMap.put("shipqty", "" + line.getQuantityActual());
        dataMap.put("previousqty", "" + (previousQty + (1 - 1)));

        log.debug("SKU:" + line.getOwdInventory().getInventoryNum() + ":::" + line.getOwdInventory().getInventoryId());
        //   log.debug("previouslyShipped:"+previousQty);
        int shippedQty = line.getQuantityActual();
        long totalQtyReceived = 0;

        // log.debug("keysize:"+(getAsnMap().keySet().size()>0));
        if (getAsnMap() ? getAsnMap().keySet().size() > 0 : false) {
            List<Map<String, Integer>> receiveList = getAsnMap().get(line.getOwdInventory().getInventoryId());
            //     log.debug("ReceiveList="+receiveList);
            if (receiveList?.size() > 0) {
                //multiple receives
                int rQty = 0;

                //calc total received to date
                for (Map<String, Integer> receive : receiveList) {
                    totalQtyReceived += (Integer) receive.values().toArray()[0]


                }
                dataMap.put("totalreceived", "" + totalQtyReceived)

                boolean foundRef = false;
                dataMap.put("nextref", "")
                int lineQty = line.getQuantityActual()
                //loop through receives
                for (Map<String, Integer> receive : receiveList) {
                    ref = receive.keySet().toArray()[0]
                    log.debug("Ref:" + ref);
                    rQty = (Integer) receive.values().toArray()[0]
                    log.debug("rQty:" + rQty);
                    log.debug("pQty:" + previousQty);
                    //check for split line
                    if (((previousQty+lineQty)>rQty) && (rQty>previousQty)) {
                               println "split"

                        println "rQty:" + rQty
                        println "pQty:" + previousQty
                        println "line:" + lineQty
                        int newLineQty = lineQty - (rQty - previousQty)
                        dataMap.put("previousqty", ""+(Integer.parseInt((String)(dataMap.get("previousqty")))+(lineQty-newLineQty)))

                        previousQty = previousQty+(lineQty-newLineQty)
                        lineQty = newLineQty

                        println "corrected"

                        println "rQty:" + rQty
                        println "pQty:" + previousQty
                        println "line:" + lineQty

                    }
                    if (foundRef) {
                        dataMap.put("nextref", "" + receive.keySet().toArray()[0])
                        return dataMap;
                    } else {

                        if (previousQty == 0) {
                            println "rQty:" + rQty
                            println "pQty:" + previousQty
                            println "line:" + lineQty

                            dataMap.put("remaining", "" + (rQty - previousQty - lineQty))

                            dataMap.put("ref", ref)
                            foundRef = true
                        } else {

                            if (previousQty >= rQty) {
                                previousQty -= rQty

                            } else {
                                //        log.debug("Refx:"+ref);
                                println "rQty:" + rQty
                                println "pQty:" + previousQty
                                println "line:" + lineQty

                                dataMap.put("remaining", "" + (rQty - previousQty - lineQty))
                                dataMap.put("ref", ref)
                                foundRef = true

                            }
                            //      log.debug("pqty:"+previousQty);
                            if (previousQty <= 0 && lineQty==0) {
                                //log.debug("Refx:"+ref);
                                dataMap.put("remaining", "0")
                                dataMap.put("ref", ref)
                                foundRef = true
                            }
                        }
                    }

                }
            }
        }

        if (!(dataMap.containsKey("ref"))) {
            dataMap.put("ref", ref)
        }
        if (!(dataMap.containsKey("remaining"))) {
            dataMap.put("remaining", "0")
        }

        //  log.debug("Refx:"+ref);
        return dataMap;
    }
}
