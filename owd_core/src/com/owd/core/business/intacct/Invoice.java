package com.owd.core.business.intacct;

import com.owd.core.OWDUtilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Mar 16, 2006
 * Time: 10:53:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class Invoice {
private final static Logger log =  LogManager.getLogger();

    public static final String kDeptIDShipping="SH";
    public static final String kDeptIDContactCenter="CCC";
    public static final String kDeptIDFulfillment="FF";
    public static final String kDeptIDAccountMgmt="AM";
    public static final String kDeptIDInfoTech="IT";


    String customerid="";
    Date datecreated= null;
    Date datedue=null;
    String invoiceno = "";
    String description="";

    List lineitems = new ArrayList();

    public List getLineitems() {
        return lineitems;
    }

    public void setLineitems(List lineitems) {
        this.lineitems = lineitems;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public Date getDatedue() {
        return datedue;
    }

    public void setDatedue(Date datedue) {
        this.datedue = datedue;
    }

    public String getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public class InvoiceItem
    {
        String glaccountno = "";
        float amount=0.00f;
               String memo = "";
               String locationid="DC1";
               String departmentid;


        public String getGlaccountno() {
            return glaccountno;
        }

        public void setGlaccountno(String glaccountno) {
            this.glaccountno = glaccountno;
        }

        public float getAmount() {
            return amount;
        }

        public void setAmount(float amount) {
            this.amount = amount;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getLocationid() {
            return locationid;
        }

        public void setLocationid(String locationid) {
            this.locationid = locationid;
        }

        public String getDepartmentid() {
            return departmentid;
        }

        public void setDepartmentid(String departmentid) {
            this.departmentid = departmentid;
        }




    }

    static public DateFormat dfIntacct = new SimpleDateFormat("'<year>'yyyy'</year><month>'MM'</month><day>'dd'</day>'");

    static public DateFormat dfInvoiceNo = new SimpleDateFormat("yyyyMMdd");

    public String getCreateInvoiceXML()
    {


        StringBuffer xml = new StringBuffer();

        xml.append("<create_invoice>");
        xml.append("<customerid>"+getCustomerid()+"</customerid>");
        xml.append("<datecreated>"+dfIntacct.format(getDatecreated())+"</datecreated>");
        xml.append("<datedue>"+dfIntacct.format(getDatedue())+"</datedue>");
        xml.append("<invoiceno>"+getInvoiceno()+"</invoiceno>");
        xml.append("<description>"+getDescription()+"</description>");
        xml.append("<invoiceitems>");

         Iterator it = getLineitems().iterator();
while(it.hasNext())
{
    Invoice.InvoiceItem item = (Invoice.InvoiceItem) it.next();
      xml.append("<lineitem>");
    //    xml.append("<glaccountno>"+item.getGlaccountno()+"</glaccountno>");
    xml.append("<accountlabel>4005N Shipping Fees Collected</accountlabel>");
        xml.append("<amount>"+item.getAmount()+"</amount>");
        xml.append("<memo>"+item.getMemo()+"</memo>");
        xml.append("<locationid>"+item.getLocationid()+"</locationid>");
        xml.append("<departmentid>"+item.getDepartmentid()+"</departmentid>");

        xml.append("</lineitem>");

}



        xml.append("</invoiceitems>");
        xml.append("</create_invoice>");



        return xml.toString();


    }

    public static void main (String[] args)
    {
        try
        {

            Invoice inv = new Invoice();
                inv.setCustomerid("INSTACHARGE_1");
                inv.setDatecreated(Calendar.getInstance().getTime());
                inv.setDatedue(Calendar.getInstance().getTime());
                inv.setDescription("Test");
                inv.setInvoiceno("SH_" + Invoice.dfInvoiceNo.format(inv.getDatecreated()));

                 Invoice.InvoiceItem item = inv.new InvoiceItem();

                item.setAmount(OWDUtilities.roundFloat(1.11f));
                item.setDepartmentid(Invoice.kDeptIDShipping);
                item.setGlaccountno("4005");
                item.setLocationid("DC1");
                item.setMemo("memo");

                inv.getLineitems().add(item);
        IntacctRequest req = new IntacctRequest();
        req.addFunction(inv.getCreateInvoiceXML());
        IntacctResponse resp = IntacctUtilities.intacctAPI(req);
        Document doc = resp.getRawResponse();
/*
        Transformer serializer =
                TransformerFactory.newInstance().newTransformer();
        serializer.setOutputProperty(
                OutputKeys.OMIT_XML_DECLARATION, "yes");

        serializer.transform(
                new DOMSource(doc),
                new StreamResult(System.out));*/

                 NodeIterator nlstatus = XPathAPI.selectNodeIterator(doc, "/response/operation/result//key");
             Node ns;
      while ((ns = nlstatus.nextNode()) != null) {
        // Serialize the found nodes to System.out

          //log.debug("Key="+ XPathAPI.eval(ns, ".").toString());


      }
        }catch(Exception ex)
        {

        }
    }
}
