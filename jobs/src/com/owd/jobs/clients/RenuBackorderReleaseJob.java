package com.owd.jobs.clients;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.Mailer;
import com.owd.core.OWDUtilities;
import com.owd.core.business.Client;
import com.owd.core.business.order.*;
import com.owd.core.managers.ConnectionManager;
import com.owd.core.managers.FacilitiesManager;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.jobs.OWDStatefulJob;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Feb 17, 2006
 * Time: 12:58:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class RenuBackorderReleaseJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    static String sweepSQL = "select order_id, order_num, c.client_id from vw_back_sweep v, owd_client c (NOLOCK) where v.client_id = c.client_id and is_backship=1  " +
            "and c.client_id=369 order by ISNULL(actual_order_date, created_date) asc ";

        //static String sweepSQL = "select order_id, order_num, c.client_id from vw_back_sweep v, owd_client c where v.client_id = c.client_id and v.client_id=160 and min_avail >= 0 order by order_id asc ";
	   public static void main(String[] args) {

        run();
    }



     public void internalExecute() {
            Connection cxn= null;
            Statement stmt = null;
            ResultSet rs = null;
                //log.debug("start sweep");
            try
            {
                cxn = ConnectionManager.getConnection();
                Vector oids = new Vector();
                Vector onums = new Vector();
                Vector cids = new Vector();
                stmt = cxn.createStatement();

         //    sweepSQL = "select order_id, order_num, c.client_id from vw_back_sweep v, owd_client c (NOLOCK) where v.client_id = c.client_id and c.client_id=160 and min_avail >= 0 order by order_id asc ";

                stmt.execute(sweepSQL);
                rs = stmt.getResultSet();
                if(rs != null)
                {
                    while(rs.next())
                    {
                        oids.addElement(rs.getString(1));
                        onums.addElement(rs.getString(2));
                        cids.addElement(rs.getString(3));
                    }
                }else
                {
                //log.debug("rs null");
                }

                rs.close();
                stmt.close();

                //log.debug("found "+oids.size()+" candidates to release...");
                for (int i=0;i<oids.size();i++)
                {

                    try
                    {
                        //get order status
                        //log.debug("getting OS for "+(String)oids.elementAt(i));
                        OrderStatus status = new OrderStatus((String)oids.elementAt(i),
                                            (String)cids.elementAt(i));
                        Client client = Client.getClientForID((String)cids.elementAt(i));
                        //log.debug("got OS for "+(String)oids.elementAt(i));
                        int units = 0;


                        OrderUtilities.updateLineItemsForAvailability(cxn,status.items, OrderXMLDoc.kPartialShip,true, FacilitiesManager.getFacilityForCode(status.shipLocation).getId());

                        boolean isOK = false;
                      //  isOK = true;
                        for(int k=0;k<status.items.size();k++)
                        {
                            //check line item quantities
                            LineItem item = (LineItem)status.items.elementAt(k);
                            if(item.inventory_fkey.equals("120084") && item.client_part_no.equals("4007HP-03"))
                            {
                                isOK=true;
                            }
                            
                            if(item.quantity_backordered>0)
                            {
                                //oops - still have a backorder situation...
                             //   throw new Exception("backorder found when attempting to autoship invID "+item.inventory_fkey+" ("+OrderUtilities.getAvailableInventory(cxn,item.inventory_fkey)+") orderID "+oids.elementAt(i));
                            } 
                        }

                            if(!isOK)
                            {
                                //oops - still have a backorder situation...
                                throw new Exception("backorder found ");
                            }

                        //what kind of order is it?
                        if (status.original_ordernum.length() > 4)
                        { //partial ship
                            if (client.partial_ship_type.equals("1"))
                            {
                                ShippingInfo sinfo = status.shipping;

                                if(client.partial_ship_carrier.startsWith("FedEx") && client.fedex_acct.length() > 0)
                                {
                                    sinfo.setShipOptions(client.partial_ship_carrier,"Third Party Billing",client.fedex_acct);

                                }else if (client.partial_ship_carrier.startsWith("UPS") && client.ups_acct.length() > 0)
                                {
                                    sinfo.setShipOptions(client.partial_ship_carrier,"Third Party Billing",client.ups_acct);

                                }else{
                                    sinfo.setShipOptions(client.partial_ship_carrier,"Prepaid","");
                                }

                                sinfo.dbupdate(cxn);
                            }
                        }else
                        { //original order
                            if (client.original_ship_type.equals("1"))
                            {
                                ShippingInfo sinfo = status.shipping;

                                if(client.original_ship_carrier.startsWith("FedEx") && client.fedex_acct.length() > 0)
                                {
                                    sinfo.setShipOptions(client.original_ship_carrier,"Third Party Billing",client.fedex_acct);

                                }else if (client.original_ship_carrier.startsWith("UPS") && client.ups_acct.length() > 0)
                                {
                                    sinfo.setShipOptions(client.original_ship_carrier,"Third Party Billing",client.ups_acct);

                                }else{
                                    sinfo.setShipOptions(client.original_ship_carrier,"Prepaid","");
                                }

                                sinfo.dbupdate(cxn);
                            }
                        }

                        //apply special rules
                        if( ((String)cids.elementAt(i)).equals("117") )//av
                        {
                            int shipunits = 0;
                            boolean forceGround = false;

                            for(int skuIndex=0;skuIndex<status.items.size();skuIndex++)
                            {
                                LineItem theItem = (LineItem)status.items.elementAt(skuIndex);

                                    if(theItem.client_part_no.equals("VV:GT 08-01707") || theItem.client_part_no.equals("VV:GT 08-01707-FAN")
                            ||theItem.client_part_no.equals("VV:GT 08-83062") || theItem.client_part_no.equals("VV:GT 08-83061")
                            ||theItem.client_part_no.equals("VV:GT 08-02780") || theItem.client_part_no.equals("VV:GT 08-02779")
                            )
                            {
                                forceGround=true;
                            }
                                    int realqty = (int)theItem.quantity_actual;
                                    int packIndex = theItem.description.indexOf("-Pack");

                                    if ((packIndex > 0))
                                    {
                                        String lastToken = "1";
                                        StringTokenizer st = new StringTokenizer(theItem.description.substring(0,packIndex), " ");
                                        while(st.hasMoreTokens())
                                            lastToken = st.nextToken();
                                        try{
                                            realqty = realqty*(new Integer(lastToken).intValue());
                                        }catch(Exception ex)
                                        {

                                        }
                                    }
                                    shipunits = (int)(shipunits+ realqty);

                            }

                            if(status.shipping.carr_service.indexOf("First Class")>=0 || status.shipping.carr_service.indexOf("First-Class")>=0 || status.shipping.carr_service.indexOf("Priority Mail")>=0)
                            {

                                if(shipunits < 2 && status.shipping.carr_service.indexOf("Priority Mail")<0)
                                {
                                    status.shipping.setShipOptions("USPS First-Class Mail","Prepaid","");
                                    status.shipping.dbupdate(cxn);
                                }else{
                                    status.shipping.setShipOptions("USPS Priority Mail","Prepaid","");
                                    status.shipping.dbupdate(cxn);
                                }
                            }

                            if(forceGround && (status.shipping.carr_service.indexOf("First Class")>=0 || status.shipping.carr_service.indexOf("First-Class")>=0 || status.shipping.carr_service.indexOf("Ground")>=0 || status.shipping.carr_service.indexOf("Priority Mail")>=0)
                            )
                            {
                                status.shipping.setShipOptions("UPS Ground","Prepaid","");
                                status.shipping.dbupdate(cxn);
                            }



                        }


                             //log.debug("**||**Shipping backorder..."+status.orderReference);
                       String backorderRef = OrderUtilities.shipExistingOrder(cxn,status);
                             //log.debug("Shipped backorder...");

                        Event.addOrderEvent(cxn,new Integer(status.order_id).intValue(),Event.kEventTypeHandling,"Backorder cleared",null);

                        cxn.commit();

                    }catch(Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }

            }catch(Throwable ex)
            {
                ex.printStackTrace();
                Mailer.postMailMessage("Error during backorder sweep",ex.getMessage()+"\n"+ OWDUtilities.getStackTraceAsString(ex),"owditadmin@owd.com","owditadmin@owd.com");
            }finally{
                try{ rs.close();}catch(Exception ex){}
                try{ stmt.close();}catch(Exception ex){}
                try{ cxn.close();}catch(Exception ex){}
            }

        }



}
