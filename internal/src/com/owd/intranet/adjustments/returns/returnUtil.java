package com.owd.intranet.adjustments.returns;

import com.owd.core.managers.LotManager;
import com.owd.hibernate.generated.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import com.owd.core.business.order.Event;
import com.owd.core.dbUtilities;
import com.owd.core.managers.FacilitiesManager;
import com.owd.hibernate.*;
import com.owd.intranet.forms.returnForm;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionImplementor;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.util.Calendar;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 7, 2006
 * Time: 4:05:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class returnUtil {
private final static Logger log =  LogManager.getLogger();

    public static returnForm saveReturn(returnForm rf, Session sess) throws Exception {
            log.debug("Doing a save return");
        log.debug("time in varirable");
        log.debug(rf.getTimeIn());
        if(rf.getOrderId().length()==0||rf.getRefNum().length()==0){
            throw new Exception("Please load an order before saving receive.");
        }
         ((SessionImplementor)sess).getJdbcConnectionAccess().obtainConnection().setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        OwdReceive rcv = new OwdReceive();
        try {
            String DATE_FORMAT = "MM/dd/yy HH:mm:ss";
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);

             Calendar cal = Calendar.getInstance();

            rcv.setBlNum(rf.getBlNum());

            rcv.setCarrier(rf.getCarrier());
            rcv.setCreatedBy(rf.getCreatedBy());
            rcv.setCreatedDate(cal.getTime());

            try
            {
               float floater = Float.parseFloat(rf.getDriver());
                if(floater>50.01){
                    throw new Exception("Postage due cannot be over $50. Talk to supervisor if the postage due is actually this much");
                }

            }catch(NumberFormatException nfe)
                {
                    throw new Exception("Postage Due value must be a decimal number like 1.45 or 0.00");
                }


            rcv.setDriver(OWDUtilities.roundFloat(Float.parseFloat(rf.getDriver()))+"");
            rcv.setIsVoid(false);
            rcv.setNotes(rf.getNotes());
            rcv.setOwdClient((OwdClient) sess.load(OwdClient.class, Integer.valueOf(rf.getClientFkey())));
            rcv.setReceiveDate(cal.getTime());
            rcv.setReceiveUser(rf.getReceiveUser());
            rcv.setRefNum(rf.getRefNum());
            rcv.setTimeIn(sdf.parse(rf.getTimeIn()));
            rcv.setTimeOut(sdf.parse(rf.getTimeIn()));
            rcv.setType("Return");
            rcv.setFacilityCode(rf.getCurrentLocation());
            sess.save(rcv);

           log.debug("ReceiveId " + rcv.getReceiveId());
            for (int i = 0; i < rf.getFormItems().length; i++) {
                OwdReceiveItem ri = new OwdReceiveItem();
                ri.setCreatedBy(rf.getFormItems()[i].getCreatedBy());
                ri.setCreatedDate(sdf.parse(rf.getReceiveDate()));
                log.debug("1");
                ri.setDescription(rf.getFormItems()[i].getDescription());
                log.debug("2");
                ri.setInventoryNum(rf.getFormItems()[i].getInventoryNum());
                log.debug("3");
                /*if(rf.getFormItems()[i].getUnusable().equals("yes")){
                    ri.setIsUnusable(true);
                }*/
                ri.setIsVoid(0);
                log.debug("4");
                ri.setItemStatus(rf.getFormItems()[i].getItemStatus());
                log.debug("5");
                ri.setOwdInventory((OwdInventory) sess.load(OwdInventory.class, Integer.valueOf(rf.getFormItems()[i].getInventoryId())));
                log.debug("6");
                  if(ri.getOwdInventory().getRequireSerialNumbers()==1)
                {
                    rf.getFormItems()[i].setSerialRequired(true);
                    String serial = rf.getFormItems()[i].getSerial();
                    if(serial==null){ serial = "";}

                    List<OwdOrder> orders =  HibernateSession.currentSession()
                                            .createSQLQuery("select distinct o.* from owd_inventory_serial ois join owd_line_serial_link join owd_line_item join owd_order o on order_id=order_fkey on line_fkey=line_item_id" +
                                                    " on serial_fkey=ois.id" +
                                                    " where shipped_on is not null and serial_number=? and order_num=?")
                                            .addEntity(OwdOrder.class)
                                            .setString(0,serial)
                                            .setString(1,rf.getRefNum())
                                            .list();
                    if(orders.size()<1)
                    {
                        throw new Exception("Serial number \""+serial+"\" does not match serials associated with order "+rf.getOwdOrderRef());
                    }
                    ri.setLocation(rf.getFormItems()[i].getSerial());
                }
                
                if (rf.getFormItems()[i].getQuantity().equals("")) {
                    throw new Exception("You must have a quantity for sku " + rf.getFormItems()[i].getInventoryNum());
                }
                ri.setQuantity(Integer.valueOf(rf.getFormItems()[i].getQuantity().trim()));
                //log.debug("7");
                ri.setReturnReason(rf.getFormItems()[i].getReturnReason());
                //log.debug("8");


                ri.setOwdReceive(rcv);
                sess.save(ri);

                //log.debug("9");
                // rcv.getOwdReceiveItems().add(ri);

                    OwdInventoryHistory invOh = new OwdInventoryHistory();
                if(null!=rf.getFormItems()[i].getLotValue() && rf.getFormItems()[i].getLotValue().length()>0){

                    OwdLotOwdReceiveItem lotItem = new OwdLotOwdReceiveItem();
                    OwdLotValue lv = LotManager.getExistingOwdLotValueForString(rf.getFormItems()[i].getLotValue(), ri.getOwdInventory().getInventoryId());
                    lotItem.setLotValue(lv);

                    lotItem.setQuantityChange(ri.getQuantity());


                    sess.save(ri);
                    lotItem.setOwdReceiveItem(ri);
                    ri.getLots().add(lotItem);
                    sess.save(lotItem);
                    invOh.setLot(lv);

                }
                    invOh.setInventoryFkey(ri.getOwdInventory().getInventoryId());
                    invOh.setQtyChange(ri.getQuantity());
                    invOh.setReceiveItemFkey(ri.getReceiveItemId());
                    invOh.setNote("returnUtil.saveReturn");
                invOh.setFacility(FacilitiesManager.getFacilityForCode(rf.getCurrentLocation()));
                    sess.save(invOh);
            }
            rcv.setTransactionNum("OWDRTN-" + dbUtilities.getNextID( ((SessionImplementor)sess).getJdbcConnectionAccess().obtainConnection(), "Receive"));
            sess.save(rcv);
            //log.debug("10");
            sess.flush();
            //log.debug("11");
            HibUtils.commit(sess);;
            try {

                
                if (rf.getOrderNum() != null && rf.getNotes() != null) {
                    StringBuffer sb = new StringBuffer();
                    sb.append("<b>Return id:</b> " +rcv.getTransactionNum());


                    sb.append("<br><b>  Skus Returned:</b><br>");
                    sb.append("<table border=\"1\" cellpadding=\"5\">");
                    sb.append("<tr><th>Sku</th><th>Description</th><th>Status</th><th>Quantity</th><th>Reason for Return</th></tr>");
                    //Map items = new TreeMap();
                    for (int i = 0; i < rf.getFormItems().length; i++) {
                       /* if (!items.containsKey(rf.getFormItems()[i].getInventoryNum())) {
                            items.put(rf.getFormItems()[i].getInventoryNum(), rf.getFormItems()[i].getInventoryNum());
                            //sb.append(rf.getFormItems()[i].getInventoryNum());
                            //sb.append(" | ");
                        }*/
                      sb.append("<tr>");
                      sb.append(tdWrap(rf.getFormItems()[i].getInventoryNum()));
                      sb.append(tdWrap(rf.getFormItems()[i].getDescription()));
                        sb.append(tdWrap(rf.getFormItems()[i].getItemStatus()));
                        sb.append(tdWrap(rf.getFormItems()[i].getQuantity()));
                        sb.append(tdWrap(rf.getFormItems()[i].getReturnReason()));
                        sb.append("</tr>");
                    }

                    sb.append("</table><br><b>Notes:</b>");
                    sb.append(rf.getNotes());

                    PreparedStatement stmt =  ((SessionImplementor)sess).getJdbcConnectionAccess().obtainConnection().prepareStatement("insert into order_events (order_fkey,event_type,message,creator) values (?,?,?,?)");
                    stmt.setInt(1, Integer.parseInt(rf.getOrderId()));
                    stmt.setInt(2, Event.kEventTypeComment);
                    stmt.setString(3, sb.toString());
                    stmt.setString(4, rf.getReceiveUser());

                    int rows = stmt.executeUpdate();
                    //log.debug("rows " + rows);
                    HibUtils.commit(sess);
                    stmt.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw(new Exception("Unable to save return (" + e.getMessage() + ")"));

        }
        rf.setReceiveId(rcv.getReceiveId() + "");
        rf.setTransactionNum(rcv.getTransactionNum());

        return rf;
    }
    private static String tdWrap(String s){
       return "<td>"+s+"</td>";
    }
}
