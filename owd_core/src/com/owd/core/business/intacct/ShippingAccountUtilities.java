package com.owd.core.business.intacct;

import com.owd.hibernate.HibernateSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jan 31, 2006
 * Time: 9:56:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class ShippingAccountUtilities {
private final static Logger log =  LogManager.getLogger();



    public static Map getARCustomerData()
    {
        Map map = new TreeMap();
              try {

               /*   Transformer serializer =
          TransformerFactory.newInstance().newTransformer();
      serializer.setOutputProperty(
            OutputKeys.OMIT_XML_DECLARATION, "yes");*/

            IntacctRequest req = new IntacctRequest();


            req.addFunction(IntacctUtilities.getARCustomerDataXML());


            IntacctResponse resp = IntacctUtilities.intacctAPI(req);


          for(int i=0;i<resp.getResponseData().size();i++) {

        Node n = (Node)resp.getResponseData().get(i);

              map.put((XPathAPI.eval(n, "customerid").toString()),XPathAPI.eval(n, "totaldue").toString());

              //log.debug((XPathAPI.eval(n, "customerid").toString())+"\t"+XPathAPI.eval(n, "name")+"\t"+XPathAPI.eval(n, "totaldue"));
          }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return map;

    }



     public static String getARAccountLabelXml()
    {
String xml = "";
        try {



            IntacctRequest req = new IntacctRequest();


            req.addFunction(IntacctUtilities.getARAccountDataXML());


            IntacctResponse resp = IntacctUtilities.intacctAPI(req);


               //   PreparedStatement ps = HibernateBillingSession.getPreparedStatement("delete from activity_code");
              //   ps.executeUpdate();

               //   ps.close();

               //   ps = HibernateBillingSession.getPreparedStatement("insert into activity_code (description) values (?)");



            //  map.put((XPathAPI.eval(n, "customerid").toString()),XPathAPI.eval(n, "totaldue").toString());
           //  ps.setString(1,XPathAPI.eval(n, "accountlabel").toString());
           //  ps.executeUpdate();
                 Transformer serializer =
                               TransformerFactory.newInstance().newTransformer();

            StringWriter stringWriter = new StringWriter();
            Result result = new StreamResult(stringWriter);
                       serializer.transform(
                               new DOMSource(resp.getRawResponse()),
                               result);

               xml = stringWriter.getBuffer().toString();
           // log.debug(xml);
                //  HibernateBillingSession.currentSession().flush();
               //   HibUtils.commit(HibernateBillingSession.currentSession());

        } catch (Exception ex) {
            ex.printStackTrace();
        }finally
              {
                  //HibernateBillingSession.closeSession();
              }

        return xml;

    }



    public static Map getARCustomerData(String clientIntacctID)
    {
        Map map = new TreeMap();
              try {

                 /* Transformer serializer =
          TransformerFactory.newInstance().newTransformer();
      serializer.setOutputProperty(
            OutputKeys.OMIT_XML_DECLARATION, "yes");
*/
            IntacctRequest req = new IntacctRequest();


            req.addFunction(IntacctUtilities.getARCustomerDataXML(clientIntacctID));


            IntacctResponse resp = IntacctUtilities.intacctAPI(req);
          for(int i=0;i<resp.getResponseData().size();i++) {

        Node n = (Node)resp.getResponseData().get(i);

              String limit= (XPathAPI.eval(n, "number(creditlimit)").toString());
              if(("NaN").equalsIgnoreCase(limit))
              {
                  limit="0.00";
              }
              map.put((XPathAPI.eval(n, "customerid").toString()),XPathAPI.eval(n, "totaldue").toString());
              map.put((XPathAPI.eval(n, "customerid").toString()+"limit"),limit);
          }
        } catch (Exception ex) {
                  //log.debug("retry");
                      try {

                 /*  Transformer serializer =
          TransformerFactory.newInstance().newTransformer();
      serializer.setOutputProperty(
            OutputKeys.OMIT_XML_DECLARATION, "yes");


              serializer.transform(
            new DOMSource(doc),
            new StreamResult(System.out));
*/
            IntacctRequest req = new IntacctRequest();


            req.addFunction(IntacctUtilities.getARCustomerDataXML(clientIntacctID));


            IntacctResponse resp = IntacctUtilities.intacctAPI(req);
          for(int i=0;i<resp.getResponseData().size();i++) {

        Node n = (Node)resp.getResponseData().get(i);

              map.put((XPathAPI.eval(n, "customerid").toString()),XPathAPI.eval(n, "totaldue").toString());
          }
        } catch (Exception ex2) {

            ex2.printStackTrace();
                  
        }

        }

        return map;



    }
    public static List getNewARLedgerData(String clientIntacctID) throws Exception
    {
        return getNewARLedgerData(clientIntacctID,false,0);
    }


    public static List getNewARLedgerData(String clientIntacctID,boolean forceAll, int keyDecrement) throws Exception
    {

        if(keyDecrement<0){
            throw new Exception("Key decrement value cannot be less than zero");
        }
        List transactionList = new ArrayList();
           try {

            IntacctRequest req = new IntacctRequest();

            ResultSet rs = HibernateSession.getResultSet("select account_type,transaction_type, "+(forceAll?"0":"max(intacct_id)-"+keyDecrement)+" from \n" +
                    "((select account_type, transaction_type, intacct_id from owd_client\n" +
                    "join owd_client_acct_transactions \n" +
                    "on client_id=client_fkey and shipper_symbol='"+clientIntacctID+"')\n" +
                    "union (select distinct account_type, transaction_type, 0 as intacct_id from owd_intacct_account_types cross join owd_intacct_trans_types ) ) as test\n" +
                    "group by account_type,transaction_type");

               while(rs.next())
               {
                   String type=rs.getString(1);
                   String transType = rs.getString(2);
                   log.debug("last intacct id:"+rs.getString(3));
                   log.debug("NEW TRANSACTION FOUND "+rs.getString(1)+":"+rs.getString(2));

                   if("Shipping".equalsIgnoreCase(type))
                   {
                       if("Payment".equalsIgnoreCase(transType))
                       {
                          req.addFunction(IntacctUtilities.getNewARShippingPaymentsRequestXML(clientIntacctID,
                    rs.getInt(3)));
                       }   else if ("Adjustment".equalsIgnoreCase(transType))
                       {
                               req.addFunction(IntacctUtilities.getNewARShippingAdjustmentsRequestXML(clientIntacctID,
                    rs.getInt(3)));
                       }   else
                       {
                                req.addFunction(IntacctUtilities.getNewARShippingInvoicesRequestXML(clientIntacctID,
                    rs.getInt(3)));
                       }
                   }   else
                   {
                      if("Payment".equalsIgnoreCase(transType))
                       {
                          req.addFunction(IntacctUtilities.getNewARActivityPaymentsRequestXML(clientIntacctID,
                    rs.getInt(3)));
                       }   else if ("Adjustment".equalsIgnoreCase(transType))
                       {
                               req.addFunction(IntacctUtilities.getNewARActivityAdjustmentsRequestXML(clientIntacctID,
                    rs.getInt(3)));
                       }   else
                       {
                                req.addFunction(IntacctUtilities.getNewARActivityInvoicesRequestXML(clientIntacctID,
                    rs.getInt(3)));
                       }
                   }
               }
               rs.close();
               
/*

               Transformer serializer =
           TransformerFactory.newInstance().newTransformer();
       serializer.setOutputProperty(
             OutputKeys.OMIT_XML_DECLARATION, "yes");
*/


               log.debug("getting intacct data for "+clientIntacctID+", forceAll="+forceAll);

            IntacctResponse resp = IntacctUtilities.intacctAPI(req);

           if(resp.isOk())
           {
               log.debug("success getting acct history for "+clientIntacctID);
           }   else
           {
               log.debug("Error getting intacct history for "+clientIntacctID+": "+resp.getErrorDescription());
           }

          /*    serializer.transform(
            new DOMSource(resp.getRawResponse()),
            new StreamResult(System.out));

*/
          for(int i=0;i<resp.getResponseData().size();i++) {

        Node n = (Node)resp.getResponseData().get(i);

        Map nodeMap = new TreeMap();
              log.debug("processing node type "+n.getNodeName());

              if(n.getNodeName().equals("invoice"))
        {

    

            nodeMap.put("due_date",XPathAPI.eval(n, "datedue/year").toString()+"-"+XPathAPI.eval(n, "datedue/month").toString()+"-"+XPathAPI.eval(n, "datedue/day").toString()+" 00:00:00.0");
            nodeMap.put("total_due",XPathAPI.eval(n, "totaldue").toString());

            nodeMap.put("intacct_id",XPathAPI.eval(n, "key").toString());
            nodeMap.put("transaction_type","Invoice");
            nodeMap.put("intacct_ref",XPathAPI.eval(n, "invoiceno").toString());
            nodeMap.put("description",XPathAPI.eval(n, "description").toString());
            nodeMap.put("total_amount",XPathAPI.eval(n, "totalamount").toString());
            nodeMap.put("applied_amount",XPathAPI.eval(n, "totalpaid").toString());
            nodeMap.put("transaction_date",XPathAPI.eval(n, "datecreated/year").toString()+"-"+XPathAPI.eval(n, "datecreated/month").toString()+"-"+XPathAPI.eval(n, "datecreated/day").toString()+" 00:00:00.0");
            nodeMap.put("notes","");
            if(XPathAPI.eval(n, "customerid").toString().endsWith("_1"))
            {
                nodeMap.put("account_type","Shipping");
            }else
            {
                nodeMap.put("account_type","Activity");
            }



/*
serializer.transform(
new DOMSource(n),
new StreamResult(System.out));*/
        }else if(n.getNodeName().equals("aradjustment"))
        {

            nodeMap.put("intacct_id",XPathAPI.eval(n, "key").toString());
            nodeMap.put("transaction_type","Adjustment");
            nodeMap.put("intacct_ref",XPathAPI.eval(n, "adjustmentno").toString());
            nodeMap.put("description",XPathAPI.eval(n, "description").toString());
            nodeMap.put("total_amount",XPathAPI.eval(n, "totalamount").toString());
            nodeMap.put("applied_amount",XPathAPI.eval(n, "totalpaid").toString());
            nodeMap.put("transaction_date",XPathAPI.eval(n, "datecreated/year").toString()+"-"+XPathAPI.eval(n, "datecreated/month").toString()+"-"+XPathAPI.eval(n, "datecreated/day").toString()+" 00:00:00.0");

            nodeMap.put("notes","");
            if(XPathAPI.eval(n, "customerid").toString().endsWith("_1"))
            {
                nodeMap.put("account_type","Shipping");
            }else
            {
                nodeMap.put("account_type","Activity");
            }

            //OWDUtilities.getDateForSQLDateString(XPathAPI.eval(n, "datecreated/year").toString()+"-"+XPathAPI.eval(n, "datecreated/month").toString()+"-"+XPathAPI.eval(n, "datecreated/day").toString()+" 00:00:00.0");

        }else if(n.getNodeName().equals("arpayment"))
        {


            nodeMap.put("intacct_id",XPathAPI.eval(n, "key").toString());
            nodeMap.put("transaction_type","Payment");
            nodeMap.put("intacct_ref",XPathAPI.eval(n, "refid").toString());
            nodeMap.put("description",XPathAPI.eval(n, "description").toString());
            nodeMap.put("total_amount",XPathAPI.eval(n, "paymentamount").toString());
            nodeMap.put("applied_amount",XPathAPI.eval(n, "paymentapplied").toString());
            nodeMap.put("transaction_date",XPathAPI.eval(n, "datereceived/year").toString()+"-"+XPathAPI.eval(n, "datereceived/month").toString()+"-"+XPathAPI.eval(n, "datereceived/day").toString()+" 00:00:00.0");

            nodeMap.put("notes",XPathAPI.eval(n, "paymentmethod").toString());
            if(XPathAPI.eval(n, "customerid").toString().endsWith("_1"))
            {
                nodeMap.put("account_type","Shipping");
            }else
            {
                nodeMap.put("account_type","Activity");
            }



        }

              transactionList.add(nodeMap);

              }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return transactionList;

    }



    public static List<Map> getDetailedActivityARLedgerData(String clientIntacctID,boolean forceAll, int keyDecrement) throws Exception
    {

        if(keyDecrement<0){
            throw new Exception("Key decrement value cannot be less than zero");
        }
        List transactionList = new ArrayList();
        try {

            IntacctRequest req = new IntacctRequest();

            ResultSet rs = HibernateSession.getResultSet("select account_type,transaction_type, "+(forceAll?"0":"max(intacct_id)-"+keyDecrement)+" from \n" +
                    "((select account_type, transaction_type, intacct_id from owd_client\n" +
                    "join owd_client_acct_transactions \n" +
                    "on client_id=client_fkey and shipper_symbol='"+clientIntacctID+"')\n" +
                    "union (select distinct account_type, transaction_type, 0 as intacct_id from owd_intacct_account_types cross join owd_intacct_trans_types ) ) as test\n" +
                    "group by account_type,transaction_type");

            while(rs.next())
            {
                String type=rs.getString(1);
                String transType = rs.getString(2);
                log.debug("last intacct id:"+rs.getString(3));
                log.debug("NEW TRANSACTION FOUND "+rs.getString(1)+":"+rs.getString(2));

                if("Shipping".equalsIgnoreCase(type))
                {
                    if("Payment".equalsIgnoreCase(transType))
                    {
                    //    req.addFunction(IntacctUtilities.getNewARShippingPaymentsRequestXML(clientIntacctID,
                    //            rs.getInt(3)));
                    }   else if ("Adjustment".equalsIgnoreCase(transType))
                    {
                    //    req.addFunction(IntacctUtilities.getNewARShippingAdjustmentsRequestXML(clientIntacctID,
                    //            rs.getInt(3)));
                    }   else
                    {
                    //    req.addFunction(IntacctUtilities.getNewARShippingInvoicesRequestXML(clientIntacctID,
                    //            rs.getInt(3)));
                    }
                }   else
                {
                    if("Payment".equalsIgnoreCase(transType))
                    {
                      //  req.addFunction(IntacctUtilities.getNewARActivityPaymentsRequestXML(clientIntacctID,
                      //          rs.getInt(3)));
                    }   else if ("Adjustment".equalsIgnoreCase(transType))
                    {
                    //    req.addFunction(IntacctUtilities.getNewARActivityAdjustmentsRequestXML(clientIntacctID,
                    //            rs.getInt(3)));
                    }   else
                    {
                        req.addFunction(IntacctUtilities.getNewARActivityInvoicesRequestXML(clientIntacctID,
                                rs.getInt(3)));
                    }
                }
            }
            rs.close();

/*

               Transformer serializer =
           TransformerFactory.newInstance().newTransformer();
       serializer.setOutputProperty(
             OutputKeys.OMIT_XML_DECLARATION, "yes");
*/


            log.debug("getting intacct data for "+clientIntacctID+", forceAll="+forceAll);

            IntacctResponse resp = IntacctUtilities.intacctAPI(req);

            if(resp.isOk())
            {
                log.debug("success getting acct history for "+clientIntacctID);
            }   else
            {
                log.debug("Error getting intacct history for "+clientIntacctID+": "+resp.getErrorDescription());
            }

            Transformer serializer =
                    TransformerFactory.newInstance().newTransformer();

            StringWriter stringWriter = new StringWriter();
            Result result = new StreamResult(stringWriter);
            serializer.transform(
                    new DOMSource(resp.getRawResponse()),
                    result);

            String xml = stringWriter.getBuffer().toString();
            log.debug(xml);


            for(int i=0;i<resp.getResponseData().size();i++) {

                Node n = (Node)resp.getResponseData().get(i);

                Map nodeMap = new TreeMap();
                log.debug("processing node type "+n.getNodeName());

                if(n.getNodeName().equals("invoice"))
                {



                    nodeMap.put("due_date",XPathAPI.eval(n, "datedue/year").toString()+"-"+XPathAPI.eval(n, "datedue/month").toString()+"-"+XPathAPI.eval(n, "datedue/day").toString()+" 00:00:00.0");
                    nodeMap.put("total_due",XPathAPI.eval(n, "totaldue").toString());

                    nodeMap.put("intacct_id",XPathAPI.eval(n, "key").toString());
                    nodeMap.put("transaction_type","Invoice");
                    nodeMap.put("intacct_ref",XPathAPI.eval(n, "invoiceno").toString());
                    nodeMap.put("description",XPathAPI.eval(n, "description").toString());
                    nodeMap.put("total_amount",XPathAPI.eval(n, "totalamount").toString());
                    nodeMap.put("applied_amount",XPathAPI.eval(n, "totalpaid").toString());
                    nodeMap.put("transaction_date",XPathAPI.eval(n, "datecreated/year").toString()+"-"+XPathAPI.eval(n, "datecreated/month").toString()+"-"+XPathAPI.eval(n, "datecreated/day").toString()+" 00:00:00.0");
                    nodeMap.put("notes","");
                    if(XPathAPI.eval(n, "customerid").toString().endsWith("_1"))
                    {
                        nodeMap.put("account_type","Shipping");
                    }else
                    {
                        nodeMap.put("account_type","Activity");
                    }
                    List<Map<String,String>> itemsList = new ArrayList<Map<String, String>>();
                    NodeList itemNodes = XPathAPI.selectNodeList(n, "./invoiceitems/lineitem");
                    log.debug("items:"+itemNodes.getLength());
                    for(int in=0;in<itemNodes.getLength();in++)
                    {
                        Node inode = itemNodes.item(in);
                        Map inodeMap = new TreeMap();
                        log.debug(XPathAPI.eval(inode, "accountlabel").toString());

                        inodeMap.put("araccountlabel",XPathAPI.eval(inode, "accountlabel").toString());
                        inodeMap.put("glaccountno",XPathAPI.eval(inode, "glaccountno").toString());
                        inodeMap.put("amount",XPathAPI.eval(inode, "amount").toString());
                        inodeMap.put("location",XPathAPI.eval(inode, "locationid").toString());
                        inodeMap.put("department",XPathAPI.eval(inode, "departmentid").toString());
                        itemsList.add(inodeMap);


                    }
                    nodeMap.put("lines",itemsList);



/*
serializer.transform(
new DOMSource(n),
new StreamResult(System.out));*/
                }else if(n.getNodeName().equals("aradjustment"))
                {

                    nodeMap.put("intacct_id",XPathAPI.eval(n, "key").toString());
                    nodeMap.put("transaction_type","Adjustment");
                    nodeMap.put("intacct_ref",XPathAPI.eval(n, "adjustmentno").toString());
                    nodeMap.put("description",XPathAPI.eval(n, "description").toString());
                    nodeMap.put("total_amount",XPathAPI.eval(n, "totalamount").toString());
                    nodeMap.put("applied_amount",XPathAPI.eval(n, "totalpaid").toString());
                    nodeMap.put("transaction_date",XPathAPI.eval(n, "datecreated/year").toString()+"-"+XPathAPI.eval(n, "datecreated/month").toString()+"-"+XPathAPI.eval(n, "datecreated/day").toString()+" 00:00:00.0");

                    nodeMap.put("notes","");
                    if(XPathAPI.eval(n, "customerid").toString().endsWith("_1"))
                    {
                        nodeMap.put("account_type","Shipping");
                    }else
                    {
                        nodeMap.put("account_type","Activity");
                    }

                    //OWDUtilities.getDateForSQLDateString(XPathAPI.eval(n, "datecreated/year").toString()+"-"+XPathAPI.eval(n, "datecreated/month").toString()+"-"+XPathAPI.eval(n, "datecreated/day").toString()+" 00:00:00.0");

                }else if(n.getNodeName().equals("arpayment"))
                {


                    nodeMap.put("intacct_id",XPathAPI.eval(n, "key").toString());
                    nodeMap.put("transaction_type","Payment");
                    nodeMap.put("intacct_ref",XPathAPI.eval(n, "refid").toString());
                    nodeMap.put("description",XPathAPI.eval(n, "description").toString());
                    nodeMap.put("total_amount",XPathAPI.eval(n, "paymentamount").toString());
                    nodeMap.put("applied_amount",XPathAPI.eval(n, "paymentapplied").toString());
                    nodeMap.put("transaction_date",XPathAPI.eval(n, "datereceived/year").toString()+"-"+XPathAPI.eval(n, "datereceived/month").toString()+"-"+XPathAPI.eval(n, "datereceived/day").toString()+" 00:00:00.0");

                    nodeMap.put("notes",XPathAPI.eval(n, "paymentmethod").toString());
                    if(XPathAPI.eval(n, "customerid").toString().endsWith("_1"))
                    {
                        nodeMap.put("account_type","Shipping");
                    }else
                    {
                        nodeMap.put("account_type","Activity");
                    }



                }

                transactionList.add(nodeMap);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return transactionList;

    }

    public static void main(String[] args) {

        try {
        //  getNewARLedgerData("AV",false,1000);
           List<Map> invoices =  getDetailedActivityARLedgerData("AV", false, 2000);
            for(Map invoice:invoices)
            {
                log.debug(invoice);
                log.debug(invoice.get("lines"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
