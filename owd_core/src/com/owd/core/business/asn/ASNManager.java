package com.owd.core.business.asn;

import com.owd.core.*;
import com.owd.core.business.client.ClientManager;
import com.owd.core.managers.FacilitiesManager;
import com.owd.core.managers.LotManager;
import com.owd.core.managers.ScanManager;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.spi.SessionImplementor;

import javax.mail.internet.AddressException;
import java.io.BufferedReader;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 13, 2004
 * Time: 3:41:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class ASNManager {
private final static Logger log =  LogManager.getLogger();

    public static int asnTypeConforming = 0;
    public static int asnTypeNonconforming = 1;
    public static int asnTypeBlind = 2;


    public static Map asnStatusMap = new TreeMap();


    static {
        asnStatusMap.put("0", "Pending");
        asnStatusMap.put("1", "Received");
        asnStatusMap.put("2", "Partial Receipt");
        asnStatusMap.put("3", "Cancelled");

    }

    public static String getAsnStatus(String integerKeyString) {
        return (String) asnStatusMap.get(integerKeyString);
    }


    public static void setASNComplete(Asn asn) throws Exception {
        //log.debug("in set asn complete");
       // if (asn.getStatus()  2) {
            //log.debug("setting asn status to 1");
            asn.setStatus(1);
       // }
    }

    public static void saveOrUpdateASN(Asn asn) throws Exception {

        if (asn.getAsnItems().size() < 1) {
            throw new Exception("At least one item must be added to an ASN before saving!");
        }
        boolean isNew = false;
        if (asn.getId() == null) {
            isNew = true;

        }

        log.debug("ASNLOC3:"+asn.getFacilityCode());

        Session sess = HibernateSession.currentSession();
        sess.saveOrUpdate(asn);
        log.debug("ASNLOC4:"+asn.getFacilityCode());

        Iterator it = asn.getAsnItems().iterator();
        //get list of asnitem id's that are currentlly in database
        ResultSet items = HibernateSession.getResultSet("select id from asn_items where asn_fkey = " + asn.getId());
        List itemid = new ArrayList();
        while (items.next()) {
            itemid.add(items.getString(1));
        }
        //log.debug("XXXXXXXXXXX" + itemid);
        while (it.hasNext()) {
            AsnItem item = (AsnItem) it.next();

            //if not yet received
            if (asn.getReceiveCount() == 0) {
                //...and the expected count has been set to zero
                if (item.getExpected() == 0) {
                    //...remove from asn session
                    it.remove();
                    asn.getAsnItems().remove(item);
                    ////log.debug("YYYYYYYYYYYYYYYY "+item.getId());
                    String id = new String();
                    try {
                        id = item.getId().toString();
                    } catch (NullPointerException ex) {
                        id = "no";
                    }
                    //...if in database delete from there
                    if (itemid.contains(id)) {
                        //log.debug("in if");
                        sess.delete(item);
                    }

                } else {
                    sess.saveOrUpdate(item);

                }
            } else {
                sess.saveOrUpdate(item);
            }
        }

        log.debug("ASNLOC5:"+asn.getFacilityCode());

        sess.flush();
        HibUtils.commit(sess);


        if (isNew && asn.getCarrier().equals("Kitting")) {

            String mailbox = ClientManager.getClientSupportBoxEmail(sess,asn.getClientFkey());
            OwdClient oc = (OwdClient) sess.load(OwdClient.class,new Integer(asn.getClientFkey()));


            StringBuffer sb = new StringBuffer();
            sb.append("[CSAs, assign this work order to the DC ASAP]\n\nNOX ASN-based kitting request! Please follow instructions below carefully\n\n");
            sb.append("This kitting request is to create the following SKUs and quantities:\n\n");
            sb.append("OWD ASN Reference: "+asn.getId()+"\n");
            sb.append("Client: "+oc.getCompanyName()+"\n\n");
            for (AsnItem item:asn.getAsnItems())
            {
                sb.append("SKU: "+item.getInventoryNum()+" (ID:"+item.getInventoryFkey()+") QUANTITY: "+item.getExpected()+"\n");
            }

            sb.append("This kits should be built from the sources described in the following instructions - if they are unclear, assign this case to the appropriate Fulfillment Specialist to follow up with the client:\n\n");

            sb.append("[INSTRUCTIONS BEGIN]\n\n"+asn.getNotes()+"\n\n[INSTRUCTIONS END]\n\n");
            sb.append("To process this work order, follow these steps:\n\n");
            sb.append("1. Calculate and adjust inventory downward to subtract source units from inventory");
            sb.append("\n2. Physically collect source units, construct kitted units per instructions, and physically move/relabel the kitted items. Confirm all quantities are as expected.");
            sb.append("\n3. Go to the referenced ASN above in the ASN Manager, and enter the kitted quantities normally as you would for a normal receive EXCEPT be sure to enter zero in the hours worked field.");
            sb.append("\n4. Return to this case, enter the appropriate number of hours as you would for any work order, and resolve the case.");

            //todo return address is AM email address
            Mailer.sendMail("[DC KITTING] ASN #"+asn.getId()+" for "+oc.getCompanyName(),sb.toString(),mailbox,oc.getAmEmail());




        }
    }

    private static void sendRowdysNotificationOfCreation(Asn asn) throws Exception {
        StringBuffer sb = new StringBuffer();
        //  //log.debug("Sending Rowdy's notification");
        OwdClient client = ((OwdClient) HibernateSession.currentSession().load(OwdClient.class, new Integer(asn.getClientFkey())));

        sb.append("ASN Creation Report - " + client.getCompanyName() + "\r\n\r\n");
        sb.append("For ASN :" + asn.getClientRef() + "\r\nOWD ASN ID : " + asn.getId() + "\r\n");
        sb.append("ASN Status : " + ASNManager.getAsnStatus("" + asn.getStatus()));
        sb.append("Created By : " + asn.getCreatedBy() + " on " + asn.getCreatedDate() + "\r\n");

        sb.append("\r\nLink to ASN Detail: http://service.owd.com/webapps/warehouse/asn/edit?act=edit-old&asn_id=" + asn.getId());

        // //log.debug("getting email addresses");

        String amEmail = "owditadmin@owd.com";
        if (client != null) {
            if (client.getAmEmail().length() > 5) {
                amEmail = client.getAmEmail();

            }
        }

        String clientAddresses = client.getAsnRcvEmail();

        Vector ccs = new Vector();


        if (clientAddresses.indexOf("@") > 0) {

            StringTokenizer tokens = new StringTokenizer(clientAddresses, ",");

            while (tokens.hasMoreTokens()) {

                String addr = tokens.nextToken();

                try {
                    MailAddressValidator.validate(addr);
                } catch (AddressException ea) {
                    addr = null;
                }

                if (addr != null)

                    ccs.addElement(addr);

            }

        }

        if (ccs.size() < 1) ccs = null;


        // //log.debug("sending email to " + amEmail);

        Mailer.sendMailWithAttachment("OWD Created ASN for " + (asn.getClientRef().length() > 0 ? asn.getClientRef() : "OWD" + asn.getId() + "") + " (" + asn.getShipperName() + ") - " + client.getCompanyName(),
                sb.toString(), amEmail, (ccs == null ? null : (ccs.toArray())),
                getASNAsExcelSheet(asn), (asn.getClientRef().length() > 0 ? asn.getClientRef() : asn.getId() + "") + ".csv", "application/octet-stream");
    }


    public static void releasePendingReceive(Receive rcv, String creator, boolean commitInternally) throws Exception {
        rcv.setCreatedBy(creator);
        saveOrUpdateReceive(rcv, commitInternally);

    }


    public static void savePendingReceive(Session sess, Receive rcv) throws Exception {

        if (rcv.getReceiveItems().size() < 1) {
            throw new Exception("At least one item must be entered for a receive before saving!");
        }
        // rcv.setAsn((Asn)sess.load(Asn.class,rcv.getAsn().getId()));

        sess.saveOrUpdate(rcv);

        sess.flush();

        Iterator it = rcv.getReceiveItems().iterator();

        while (it.hasNext()) {
            ReceiveItem receiveItem = (ReceiveItem) it.next();
            receiveItem.setReceive(rcv);
            sess.saveOrUpdate(receiveItem);

        }
        sess.save(((Asn) rcv.getAsn()));
        if (((Asn) rcv.getAsn()).getReceives() == null) ((Asn) rcv.getAsn()).setReceives(new TreeSet());

        sess.save(((Asn) rcv.getAsn()));

        //log.debug("saved asn2");

        ((Asn) rcv.getAsn()).getReceives().add(rcv);

        sess.save(((Asn) rcv.getAsn()));


    }

    public static void updateAsnGroupNameValueFromReceiveItems(Receive rcv) throws Exception{


        String oldgroup = rcv.getAsn().getGroupName();
        if(oldgroup==null) { oldgroup = "";}
        if(oldgroup.length()<1)
        {


        Iterator it = rcv.getReceiveItems().iterator();
        //log.debug("rcvitems before save iteration=" + rcv.getReceiveItems().size());

        String itemGroup = null;

        while (it.hasNext() && oldgroup!=null) {

            ReceiveItem rcvitem = ((ReceiveItem) it.next());
            Hibernate.initialize(rcvitem.getAsnItem());
            AsnItem asnitem = rcvitem.getAsnItem();

            OwdInventory inventory = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, asnitem.getInventoryFkey());
            log.debug(inventory.getInventoryNum()+":"+inventory.getGroupName());
            String itemGroupTest = inventory.getGroupName();
            if(itemGroup==null && itemGroupTest!=null)
            {
               if(itemGroupTest.length()>0)
               {
                   itemGroup = itemGroupTest;
               }
            }else
            {
                if(itemGroupTest!=null)
                {
                    if(itemGroupTest.length()>0)
                    {
                       if(!(itemGroup.equals(itemGroupTest)))
                       {
                         oldgroup = null;
                       }
                    }
                }
            }

        }

            if(oldgroup!=null)
            {
                rcv.getAsn().setGroupName(itemGroup);
            }


        }
    }


    public static void saveOrUpdateReceive(Receive rcv, boolean commitInternally) throws Exception {

        if (rcv.getCreatedBy().equals("PENDING")) {
            throw new Exception("Use savePendingReceive method for saving pending receives to database, or releasePendingReceive to release a pending receive as complete");
        }
        Session sess = HibernateSession.currentSession();

        if (rcv.getReceiveItems().size() < 1) {
            throw new Exception("At least one item must be entered for a receive before saving!");
        }
        // rcv.setAsn((Asn)sess.load(Asn.class,rcv.getAsn().getId()));

        sess.saveOrUpdate(rcv);
        //mark
        sess.flush();

        updateAsnGroupNameValueFromReceiveItems(rcv);

        Iterator it = rcv.getReceiveItems().iterator();
        log.debug("rcvitems before save iteration=" + rcv.getReceiveItems().size());


        while (it.hasNext()) {
            ReceiveItem receiveItem = (ReceiveItem) it.next();
            receiveItem.setReceive(rcv);
            sess.saveOrUpdate(receiveItem);
            sess.saveOrUpdate(((AsnItem) receiveItem.getAsnItem()));
            if (((AsnItem) receiveItem.getAsnItem()).getReceiveItems() == null) {
                ((AsnItem) receiveItem.getAsnItem()).setReceiveItems(new TreeSet());
            }


            ((AsnItem) receiveItem.getAsnItem()).getReceiveItems().add(receiveItem);


            sess.save(((AsnItem) receiveItem.getAsnItem()));
        }


        sess.save(((Asn) rcv.getAsn()));
        if (((Asn) rcv.getAsn()).getReceives() == null) ((Asn) rcv.getAsn()).setReceives(new TreeSet());

        sess.save(((Asn) rcv.getAsn()));

        //log.debug("saved asn2");

        ((Asn) rcv.getAsn()).getReceives().add(rcv);
        ((Asn) rcv.getAsn()).setReceiveCount(((Asn) rcv.getAsn()).getReceives().size());
        ((Asn) rcv.getAsn()).setLastReceiveDate(Calendar.getInstance().getTime());
        int tstatus = 1;
        Iterator iter = ((Asn) rcv.getAsn()).getAsnItems().iterator();
        while (iter.hasNext()) {
            AsnItem aitem = ((AsnItem) iter.next());
            if (aitem.getReceived() < aitem.getExpected()) {
                tstatus = 2;
            }
        }

        if (((Asn) rcv.getAsn()).getReceiveCount() < 1) {
            tstatus = 0;
        }

        ((Asn) rcv.getAsn()).setStatus(tstatus);

        //   sess.saveOrUpdate(((Asn) rcv.getAsn()));


       try{
        if (((Asn) rcv.getAsn()).getIsAutorelease() == 1) {

            postReceiveToInventory(rcv);


        }

       } catch (Exception e){
           e.printStackTrace();
         Iterator it2 = rcv.getReceiveItems().iterator();
        //log.debug("settingback received qty");

        while (it2.hasNext()) {
            ReceiveItem receiveItem = (ReceiveItem) it2.next();

            ((AsnItem) receiveItem.getAsnItem()).setReceived(((AsnItem) receiveItem.getAsnItem()).getReceived()-receiveItem.getQtyReceive());


            sess.save(((AsnItem) receiveItem.getAsnItem()));
        }
         throw new Exception(e.getMessage());
       }

        sess.flush();
        if (commitInternally) {

          HibUtils.commit(sess);
        }

        updateASNItemsReceivedCounts(rcv.getAsn(),rcv,true);


    }


    public static void postReceiveToInventory(Receive rcv) throws Exception {
        Session sess = HibernateSession.currentSession();

        ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        //delete any existing non-void owd_receive records for this receive - just in case we have an old dupe hanging around.
        if (rcv.getCreatedBy().equals("PENDING")) {
            throw new Exception("Cannot post a pending receive!");
        }
        cancelPostReceiveToInventory(rcv);


        //TODO - exclusive lock for inventory on hand quantity during transaction...

      //  long hours = (rcv.getEndTimestamp().getTime() - rcv.getStartTimestamp().getTime()) / (60 * 60 * 1000);
        long minutes = (rcv.getEndTimestamp().getTime() - rcv.getStartTimestamp().getTime()) / (60 * 1000);

        double qhours = 0.25;
        while (minutes > 15) {
            qhours = qhours + 0.25;
            minutes = minutes - 15;
        }

        //log.debug("h:" + hours + ",m:" + minutes + ",qh:" + qhours);
        OwdReceive oldrcv = new OwdReceive();
        oldrcv.setBlNum("");
        oldrcv.setCarrier("");
        oldrcv.setCreatedBy(rcv.getReceiveBy());
        oldrcv.setCreatedDate(Calendar.getInstance().getTime());
        oldrcv.setDriver("" + (qhours));
        oldrcv.setExpectedDate(rcv.getAsn().getExpectDate());
        oldrcv.setIsVoid(false);
        oldrcv.setModifiedBy(rcv.getReceiveBy());
        oldrcv.setModifiedDate(Calendar.getInstance().getTime());
        oldrcv.setNotes(rcv.getNotes().substring(0,rcv.getNotes().length()>254?254:rcv.getNotes().length()));
        oldrcv.setNumEmployees(Byte.decode("0x00"));
        oldrcv.setOwdClient((OwdClient) sess.load(OwdClient.class, new Integer(rcv.getAsn().getClientFkey())));
        oldrcv.setReceiveDate(rcv.getEndTimestamp());
        oldrcv.setReceiveUser(rcv.getReceiveBy());
        oldrcv.setRefNum(rcv.getAsn().getClientRef().length() > 0 ? rcv.getAsn().getClientRef() : "ASNOWD" + rcv.getAsn().getId());
        oldrcv.setRowIsLocked(false);
        oldrcv.setShipperRef(rcv.getAsn().getShipperName());
        oldrcv.setTimeIn(rcv.getStartTimestamp());
        oldrcv.setTimeOut(rcv.getEndTimestamp());
        oldrcv.setTotalTime(new BigDecimal((rcv.getEndTimestamp().getTime() - rcv.getStartTimestamp().getTime()) / (60 * 60 * 1000)));
        oldrcv.setTransactionNum("OWDRCV-" + rcv.getId());
        oldrcv.setType("Receive");
        oldrcv.setFacilityCode(rcv.getAsn().getFacilityCode());

        //log.debug("Total Time:" + new BigDecimal((rcv.getEndTimestamp().getTime() - rcv.getStartTimestamp().getTime()) / (60 * 60 * 1000)));

        sess.save(oldrcv);
        if (oldrcv.getOwdReceiveItems() == null) {
            oldrcv.setOwdReceiveItems(new ArrayList());
        }

        sess.save(oldrcv);
        sess.flush();
        Iterator it = rcv.getReceiveItems().iterator();
        while (it.hasNext()) {
            ReceiveItem ritem = (ReceiveItem) it.next();
            OwdInventory inv = (OwdInventory) sess.load(OwdInventory.class, new Integer(ritem.getAsnItem().getInventoryFkey()));


            if (ritem.getQtyReceive() > 0) {

                //log.debug("creating receive item");
                OwdReceiveItem ori = new OwdReceiveItem();
                ori.setIsVoid(0);
                ori.setOwdReceive(oldrcv);
                ori.setCreatedBy("testing");
                ori.setCreatedDate(Calendar.getInstance().getTime());
                // OwdInventoryOh invOH = inv.getOwdInventoryOh();
                //  invOH.setQtyOnHand(new Integer(invOH.getQtyOnHand().intValue() + ritem.getQtyReceive()));

                ori.setOwdInventory(inv);
                ori.setDescription(inv.getDescription());
                ori.setInventoryNum(inv.getInventoryNum());
                ori.setIsUnusable(false);
                ori.setItemStatus("New");
                ori.setLocation("");
                ori.setModifiedBy("");
                ori.setModifiedDate(Calendar.getInstance().getTime());
                ori.setQuantity(new Integer(ritem.getQtyReceive()));
                ori.setReported(false);
                ori.setReturnReason("");

                sess.save(ori);

                if (!(ori.getItemStatus().equalsIgnoreCase("DAMAGED"))) {

                    if (ritem.getLots().size() == 0) {
                        OwdInventoryHistory invOh = new OwdInventoryHistory();
                        invOh.setInventoryFkey(ori.getOwdInventory().getInventoryId());
                        invOh.setQtyChange(ori.getQuantity());
                        invOh.setReceiveItemFkey(ori.getReceiveItemId());
                        invOh.setNote("ASNManager.postReceiveToInventory");
                        invOh.setFacility(FacilitiesManager.getFacilityForCode(oldrcv.getFacilityCode()));
                        sess.save(invOh);
                    } else {
                         int totalLotQty = 0;
                        for (OwdLotReceiveItem lotData : ritem.getLots()) {

                            OwdLotOwdReceiveItem lotRcvItem = new OwdLotOwdReceiveItem();
                            lotRcvItem.setOwdReceiveItem(ori);
                            lotRcvItem.setLotValue(LotManager.getOwdLotValueForString(lotData.getLotValue(), ori.getOwdInventory().getInventoryId()));
                            lotRcvItem.setQuantityChange(lotData.getQuantityChange());
                            ori.getLots().add(lotRcvItem);
                            lotRcvItem.setOwdReceiveItem(ori);

                            sess.save(lotRcvItem);

                            OwdInventoryHistory invOh = new OwdInventoryHistory();
                            invOh.setInventoryFkey(ori.getOwdInventory().getInventoryId());
                            invOh.setQtyChange(lotRcvItem.getQuantityChange());
                            invOh.setLot(lotRcvItem.getLotValue());
                            invOh.setReceiveItemFkey(ori.getReceiveItemId());
                            invOh.setNote("ASNManager.postReceiveToInventoryLot");
                            invOh.setFacility(FacilitiesManager.getFacilityForCode(oldrcv.getFacilityCode()));
                            sess.save(invOh);
                            totalLotQty +=  lotRcvItem.getQuantityChange();
                        }
                        if(totalLotQty!=ritem.getQtyReceive()) {
                            throw new Exception("Total Lot quantities received ("+totalLotQty+") not equal to total units received ("+ritem.getQtyReceive()+")");
                        }
                    }

                    oldrcv.getOwdReceiveItems().add(ori);
                }
                //log.debug("cking for damage count");
                if (ritem.getQtyDamage() > 0) {
                    //log.debug("creating damage item");
                    OwdReceiveItem ori2 = new OwdReceiveItem();
                    ori2.setIsVoid(0);
                    ori2.setOwdReceive(oldrcv);
                    ori2.setCreatedBy("testing");
                    ori2.setCreatedDate(Calendar.getInstance().getTime());
                    ori2.setOwdInventory(inv);
                    ori2.setDescription(inv.getDescription());
                    ori2.setInventoryNum(inv.getInventoryNum());
                    ori2.setIsUnusable(false);
                    ori2.setItemStatus("Damaged");
                    ori2.setLocation("");
                    ori2.setModifiedBy("");
                    ori2.setModifiedDate(Calendar.getInstance().getTime());
                    ori2.setQuantity(ritem.getQtyDamage());
                    ori2.setReported(true); //don't report damaged counts due to receives - they don't need to be reported as adjustments
                    ori2.setReturnReason("");

                    //log.debug("saving damage item");
                    sess.save(ori2);

                    //log.debug("adding damage item to set");
                    oldrcv.getOwdReceiveItems().add(ori2);


                }
            }
            rcv.setIsPosted(1);
            rcv.setPostDate(Calendar.getInstance().getTime());
            sess.save(rcv);
        }

        //log.debug(oldrcv.getOwdReceiveItems().size());

        //log.debug("saving old receive");
        sess.save(oldrcv);
        //log.debug("done posting inventory for receive");
    }

    public static void cancelPostReceiveToInventory(Receive rcv) throws Exception {
        Session sess = HibernateSession.currentSession();

        ((SessionImplementor)sess).getJdbcConnectionAccess().obtainConnection().setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        if (!rcv.getCreatedBy().equals("PENDING")) {
            Criteria crit = sess.createCriteria(OwdReceive.class);
            crit.add(Restrictions.eq("transactionNum", "OWDRCV-" + rcv.getId()))
                    .add(Restrictions.eq("isVoid", new Boolean(false)));
            List oldReceives = crit.list();

            if (oldReceives.size() > 0) {
                Iterator it = oldReceives.iterator();
                while (it.hasNext()) {
                    OwdReceive oldrcv = (OwdReceive) it.next();
                    oldrcv.setIsVoid(true);
                    sess.saveOrUpdate(oldrcv);
                    Iterator itr = oldrcv.getOwdReceiveItems().iterator();
                    while (itr.hasNext()) {
                        OwdReceiveItem ritem = (OwdReceiveItem) itr.next();


                        if (!(ritem.getItemStatus().equalsIgnoreCase("DAMAGED"))) {

                            if(ritem.getLots().size()==0) {
                                OwdInventoryHistory invOh = new OwdInventoryHistory();
                                invOh.setInventoryFkey(ritem.getOwdInventory().getInventoryId());
                                invOh.setQtyChange(ritem.getQuantity() * -1);
                                invOh.setReceiveItemFkey(ritem.getReceiveItemId());
                                invOh.setNote("ASNManager.cancelPostReceiveToInventoryByLot");
                                invOh.setFacility(FacilitiesManager.getFacilityForCode(ritem.getOwdReceive().getFacilityCode()));
                                HibernateSession.currentSession().save(invOh);
                            }
                            else {
                                for(OwdLotOwdReceiveItem lotData:ritem.getLots()) {
                                    OwdInventoryHistory invOh = new OwdInventoryHistory();
                                    invOh.setInventoryFkey(ritem.getOwdInventory().getInventoryId());
                                    invOh.setQtyChange(lotData.getQuantityChange() * -1);
                                    invOh.setReceiveItemFkey(ritem.getReceiveItemId());
                                    invOh.setNote("ASNManager.cancelPostReceiveToInventory");
                                    invOh.setFacility(FacilitiesManager.getFacilityForCode(ritem.getOwdReceive().getFacilityCode()));
                                    invOh.setLot(lotData.getLotValue());
                                    HibernateSession.currentSession().save(invOh);
                                }
                            }

                            if (ritem.isReported() && ritem.getIsVoid() == 0) {
                                //to keep track of inventory change events due to voided receives, the reporting flag needs
                                //to be reversed for previously reported items and set for unreported items when voiding the parent receive record.

                                ritem.setReported(false);
                            }
                        } else {
                            //unposting damaged counts from receives not needed as they aren/t reflected in inventory counts in the first place
                            ritem.setReported(true);
                        }

                        ritem.setIsVoid(1);
                        HibernateSession.currentSession().saveOrUpdate(ritem);
                        //sess.saveOrUpdate(invOH);


                    }

                }
            }

            rcv.setPostDate(null);
            rcv.setIsPosted(0);

            sess.saveOrUpdate(rcv);
        }

    }

    private static void cancelPostReceiveItem(OwdReceiveItem ritem, Integer lotFkey, int lotQuantity) throws Exception {



    }

    public static void deleteReceive(Receive rcv) throws Exception {
        Session sess = HibernateSession.currentSession();
        Asn asn = rcv.getAsn();
        if (!rcv.getCreatedBy().equals("PENDING")) {
            cancelPostReceiveToInventory(rcv);


            asn.setReceiveCount(asn.getReceiveCount() - 1);
            asn.getReceives().remove(rcv);
            rcv.setAsn(null);


            if (asn.getReceives().size() > 0) {
                asn.setLastReceiveDate(((Receive) (asn.getReceives().toArray()[asn.getReceives().size() - 1])).getCreatedOn());
            } else {
                asn.setLastReceiveDate(null);
            }


            sess.save(asn);

            Iterator it = rcv.getReceiveItems().iterator();
            while (it.hasNext()) {
                ReceiveItem ritem = (ReceiveItem) it.next();

                ritem.getAsnItem().getReceiveItems().remove(ritem);

                sess.save(ritem.getAsnItem());
                    ritem.setAsnItem(null);
                sess.save(ritem);
            }
        }

            for(ReceiveItem rm: rcv.getReceiveItems())
        {
            HibernateSession.currentSession().delete(rm);
            rm.setReceive(null);

        }
        rcv.getReceiveItems().clear();

      //  sess.delete("select receiveItem FROM ReceiveItem as receiveItem where receive_fkey = " + rcv.getId());

        sess.delete(rcv);
        sess.flush();
        updateASNItemsReceivedCounts(asn,rcv,false);

    }

    public static void deleteASN(Asn asnObj) throws Exception {

        Asn asn = (Asn) HibernateSession.currentSession().load(Asn.class, asnObj.getId());
        if(asn.getReceives().size()>0)
        {
            throw new Exception("Cannot delete ASNs with existing receive records");
        }



   //     HibernateSession.currentSession().delete("select asnItem FROM AsnItem as asnItem where asn_fkey = " + asn.getId());
        for(AsnItem rm: asn.getAsnItems())
           {
               HibernateSession.currentSession().delete(rm);

           }
           asn.getAsnItems().clear();

        HibernateSession.currentSession().delete(asn);


    }

     public static void updateASNItemsReceivedCounts(Asn asn, Receive rcv, boolean updateAsn) throws Exception {

      //     Asn asn = (Asn) HibernateSession.currentSession().load(Asn.class, asnObj.getId());
        boolean gotAll = true;
        boolean gotAny = false;
           for(AsnItem rm: asn.getAsnItems())
              {
                  int received=0;
                  for(ReceiveItem ri:rm.getReceiveItems())
                  {
                      received += ri.getQtyReceive();
                      gotAny = true;
                  }
                  rm.setReceived(received);
                  if(rm.getReceived()<rm.getExpected())
                  {
                     gotAll = false;
                  }
                  HibernateSession.currentSession().update(rm);
              }
        if(gotAll)
        {
            asn.setStatus(1);
        } else if(gotAny)
        {
            asn.setStatus(2);
        }      else
        {
            asn.setStatus(0);
        }

         if (((Asn) asn).getReceives() == null) ((Asn) asn).setReceives(new TreeSet());

         if(updateAsn)
         {
        //log.debug("saved asn2");

        asn.getReceives().add(rcv);
        asn.setReceiveCount(asn.getReceives().size());
        asn.setLastReceiveDate(Calendar.getInstance().getTime());
        int tstatus = 1;
        Iterator iter = asn.getAsnItems().iterator();
        while (iter.hasNext()) {
            AsnItem aitem = ((AsnItem) iter.next());
            if (aitem.getReceived() < aitem.getExpected()) {
                tstatus = 2;
            }
        }
         }

        HibernateSession.currentSession().save(asn);
              HibernateSession.currentSession().flush();
              HibUtils.commit(HibernateSession.currentSession());

       }

    public static void updateASNItemsReceivedCounts(Receive rcv) throws Exception {

      updateASNItemsReceivedCounts(rcv.getAsn(), rcv, true);
       }


    public static void main(String[] args) {
        try {


            Receive rcv = (Receive) HibernateSession.currentSession().load(Receive.class,72771);
            Hibernate.initialize(rcv);
            log.debug("ASN before: "+rcv.getAsn().getGroupName());
            updateAsnGroupNameValueFromReceiveItems(rcv);
            log.debug("ASN after: "+rcv.getAsn().getGroupName());
        } catch (Throwable ex) {
            ex.printStackTrace();
        } finally {
      HibernateSession.closeSession();
        }
    }

    public static void sendAMNotificationOfReceipt(Receive rcv) throws Exception {

        sendHTMLAMNotificationOfReceipt(rcv);
 /*       if (rcv.getCreatedBy().equals("PENDING")) {
            throw new Exception("Cannot notify AMof pending receive!");
        }
        StringBuffer sb = new StringBuffer();
        OwdClient client = ((OwdClient) HibernateSession.currentSession().load(OwdClient.class, new Integer(rcv.getAsn().getClientFkey())));

        sb.append("Completed Inventory Receipt Report - " + (rcv.getIsPosted() == 1 ? "POSTED" : "NOT POSTED") + " - " + client.getCompanyName() + "\r\n\r\n");
        sb.append("For ASN :" + rcv.getAsn().getClientRef() + "\r\nOWD ASN ID : " + rcv.getAsn().getId() + "\r\n");
        sb.append("Receive Start : " + rcv.getStartTimestamp() + "\r\nReceive End : " + rcv.getEndTimestamp() + "\r\n");
        sb.append("ASN Status : " + ASNManager.getAsnStatus("" + rcv.getAsn().getStatus()));
        sb.append("\r\nLink to ASN Detail: http://service.owd.com/webapps/warehouse/asn/edit?act=edit-old&asn_id=" + rcv.getAsn().getId());
        sb.append("\r\nInventory Status : " + (rcv.getIsPosted() == 1 ? "Posted to inventory" : "Not posted - requires manual release to inventory") + "\r\n");

        sb.append("\r\nSKU ... Received ... Damaged\r\n======================================================\r\n");

        //log.debug("email iterating items");

        Iterator it = rcv.getReceiveItems().iterator();
        //log.debug("Sending AM notification with receive item count=" + rcv.getReceiveItems().size());
        while (it.hasNext()) {
            ReceiveItem ri = (ReceiveItem) it.next();
            sb.append(ri.getAsnItem().getInventoryNum() + "/" + ri.getAsnItem().getTitle() + " ... " + ri.getQtyReceive() + " ... " + ri.getQtyDamage() + "   \r\n");
        }


        //log.debug("getting email addresses");

        String amEmail = "owditadmin@owd.com";
        if (client != null) {
            if (client.getAmEmail().length() > 5) {
                amEmail = client.getAmEmail();

            }
        }

        String clientAddresses = client.getAsnRcvEmail();

        Vector ccs = new Vector();


        if (clientAddresses.indexOf("@") > 0) {

            StringTokenizer tokens = new StringTokenizer(clientAddresses, ",");

            while (tokens.hasMoreTokens()) {

                String addr = tokens.nextToken();

                try {
                    MailAddressValidator.validate(addr);
                } catch (AddressException ea) {
                    addr = null;
                }

                if (addr != null)

                    ccs.addElement(addr);

            }

        }

        if (ccs.size() < 1) ccs = null;


        //log.debug("sending email to " + amEmail);
        byte[][] files = null;
        String[] names = null;
        boolean hasFiles = false;
        try {
            Map scans = getScanData(rcv);
            files = (byte[][]) scans.get("file");
            names = (String[]) scans.get("names");
            hasFiles = true;
        } catch (Exception e) {
            Mailer.sendMailWithAttachment("OWD ASN Receipt for " + (rcv.getAsn().getClientRef().length() > 0 ? rcv.getAsn().getClientRef() : "OWD" + rcv.getAsn().getId() + "") + " (" + rcv.getAsn().getShipperName() + ") - " + (rcv.getIsPosted() == 1 ? "POSTED" : "NOT POSTED") + " - " + client.getCompanyName(),
                    sb.toString(), amEmail, (ccs == null ? null : (ccs.toArray())), getASNAsExcelSheet(rcv.getAsn()), (rcv.getAsn().getClientRef().length() > 0 ? rcv.getAsn().getClientRef() : rcv.getAsn().getId() + "") + ".csv", "application/octet-stream");
        }      //todo
        if (hasFiles) {
            Mailer.sendMailWithAttachments("OWD ASN Receipt for " + (rcv.getAsn().getClientRef().length() > 0 ? rcv.getAsn().getClientRef() : "OWD" + rcv.getAsn().getId() + "") + " (" + rcv.getAsn().getShipperName() + ") - " + (rcv.getIsPosted() == 1 ? "POSTED" : "NOT POSTED") + " - " + client.getCompanyName(),
                    sb.toString(), amEmail, (ccs == null ? null : (ccs.toArray())), getASNAsExcelSheet(rcv.getAsn()), (rcv.getAsn().getClientRef().length() > 0 ? rcv.getAsn().getClientRef() : rcv.getAsn().getId() + "") + ".csv", files, names, "application/octet-stream");
            // Asn asn = (Asn) sess.load(Asn.class, new Integer("1657"));
            //  sendRowdysNotificationOfCreation(asn);
        }
*/
    }

//    /

    private static void sendSymphonyNotificationOfReceipt(Receive rcv, List<String> emails) throws Exception {

        if (rcv.getCreatedBy().equals("PENDING")) {
            throw new Exception("Cannot notify AM of pending receive!");
        }
        OwdClient client = ((OwdClient) HibernateSession.currentSession().load(OwdClient.class, new Integer(rcv.getAsn().getClientFkey())));

        try {

            String authString = OWDUtilities.encryptData(client.getClientId() + "/" + rcv.getId());

            String text = "\r\nThis is an HTML or web-formatted email containing a report of an inventory receive at OWD. If you are seeing this message, you may be unable" +
                    " to view HTML mail. You can access your receive report at the URL below:" +
                    "\r\n\r\nhttp://service.owd.com/webapps/accounts/receivestatement.jsp?auth=" + URLEncoder.encode(authString,"UTF-8") + "\r\n\r\n" +
                    " If you continue to have problems, please contact your account manager for assistance.";


            WebResource server = new WebResource("http://service.owd.com/webapps/accounts/receivestatement.jsp", WebResource.kGETMethod);
            server.addParameter("auth", authString);

            StringBuffer buffer = new StringBuffer();

            BufferedReader in = server.getResource();
            String inputLine;


            while ((inputLine = in.readLine()) != null) {
                buffer.append(inputLine + "\r");
            }

            //log.debug(buffer);
            //log.debug("getting email addresses");

            String amEmail = null;
            Vector<String> ccs = new Vector<String>();
            for(String addy:emails){

                if(amEmail==null)
                {
                    amEmail = addy;
                }
                else
                {
                    ccs.add(addy);
                }
            }

            byte[][] files = null;
            String[] names = null;
            boolean hasFiles = false;
            try {
                Map scans = getScanData(rcv);
                files = (byte[][]) scans.get("file");
                names = (String[]) scans.get("names");
                hasFiles = true;
            } catch (Exception e) {
                e.printStackTrace();



                Mailer.sendMailWithAttachment("ASN Receipt for " + (rcv.getAsn().getClientRef().length() > 0 ? rcv.getAsn().getClientRef() : "OWD" + rcv.getAsn().getId() + "") + " (" + rcv.getAsn().getShipperName() + ") - " + (rcv.getIsPosted() == 1 ? "POSTED" : "NOT POSTED") ,
                        text, amEmail, ccs.toArray(), getASNAsExcelSheet(rcv.getAsn()), (rcv.getAsn().getClientRef().length() > 0 ? rcv.getAsn().getClientRef() : rcv.getAsn().getId() + "") + ".csv", "application/octet-stream",buffer.toString());

            }      //todo

            if (hasFiles) {

                Mailer.sendMailWithAttachments("ASN Receipt for " + (rcv.getAsn().getClientRef().length() > 0 ? rcv.getAsn().getClientRef() : "OWD" + rcv.getAsn().getId() + "") + " (" + rcv.getAsn().getShipperName() + ") - " + (rcv.getIsPosted() == 1 ? "POSTED" : "NOT POSTED") ,
                        text, amEmail, ccs.toArray(), getASNAsExcelSheet(rcv.getAsn()), (rcv.getAsn().getClientRef().length() > 0 ? rcv.getAsn().getClientRef() : rcv.getAsn().getId() + "") + ".csv", files, names, "application/octet-stream",buffer.toString());

                // Asn asn = (Asn) sess.load(Asn.class, new Integer("1657"));
                //  sendRowdysNotificationOfCreation(asn);
            }



        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    private static void sendRockFlowerPaperNotificationOfReceipt(Receive rcv, List<String> emails) throws Exception {

        if (rcv.getCreatedBy().equals("PENDING")) {
            throw new Exception("Cannot notify AM of pending receive!");
        }
        OwdClient client = ((OwdClient) HibernateSession.currentSession().load(OwdClient.class, new Integer(rcv.getAsn().getClientFkey())));

        try {

            String authString = OWDUtilities.encryptData(client.getClientId() + "/" + rcv.getId());

            String text = "\r\nThis is an HTML or web-formatted email containing a report of an inventory receive. If you are seeing this message, you may be unable" +
                    " to view HTML mail. You can access your receive report at the URL below:" +
                    "\r\n\r\nhttp://service.owd.com/webapps/accounts/rockflowerpaperreceivestatement.jsp?auth=" + URLEncoder.encode(authString,"UTF-8") + "\r\n\r\n" +
                    " If you continue to have problems viewing it, please contact your account manager for assistance.";


            WebResource server = new WebResource("http://service.owd.com/webapps/accounts/rockflowerpaperreceivestatement.jsp", WebResource.kGETMethod);
            server.addParameter("auth", authString);

            StringBuffer buffer = new StringBuffer();

            BufferedReader in = server.getResource();
            String inputLine;


            while ((inputLine = in.readLine()) != null) {
                buffer.append(inputLine + "\r");
            }

            //log.debug(buffer);
            //log.debug("getting email addresses");

            String amEmail = null;
            Vector<String> ccs = new Vector<String>();
            for(String addy:emails){

                if(amEmail==null)
                {
                    amEmail = addy;
                }
                else
                {
                    ccs.add(addy);
                }
            }

            byte[][] files = null;
            String[] names = null;
            boolean hasFiles = false;
            try {
                Map scans = getScanData(rcv);
                files = (byte[][]) scans.get("file");
                names = (String[]) scans.get("names");
                hasFiles = true;
            } catch (Exception e) {
                e.printStackTrace();



                Mailer.sendMailWithAttachment("ASN Receipt for " + (rcv.getAsn().getClientRef().length() > 0 ? rcv.getAsn().getClientRef() : "OWD" + rcv.getAsn().getId() + "") + " (" + rcv.getAsn().getShipperName() + ") - " + (rcv.getIsPosted() == 1 ? "POSTED" : "NOT POSTED") ,
                        text, amEmail, ccs.toArray(), getASNAsExcelSheet(rcv.getAsn()), (rcv.getAsn().getClientRef().length() > 0 ? rcv.getAsn().getClientRef() : rcv.getAsn().getId() + "") + ".csv", "application/octet-stream",buffer.toString());

            }      //todo

            if (hasFiles) {

                Mailer.sendMailWithAttachments("ASN Receipt for " + (rcv.getAsn().getClientRef().length() > 0 ? rcv.getAsn().getClientRef() : "OWD" + rcv.getAsn().getId() + "") + " (" + rcv.getAsn().getShipperName() + ") - " + (rcv.getIsPosted() == 1 ? "POSTED" : "NOT POSTED") ,
                        text, amEmail, ccs.toArray(), getASNAsExcelSheet(rcv.getAsn()), (rcv.getAsn().getClientRef().length() > 0 ? rcv.getAsn().getClientRef() : rcv.getAsn().getId() + "") + ".csv", files, names, "application/octet-stream",buffer.toString());

                // Asn asn = (Asn) sess.load(Asn.class, new Integer("1657"));
                //  sendRowdysNotificationOfCreation(asn);
            }



        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    private static void sendUpgradedSelfNotificationOfReceipt(Receive rcv, List<String> emails) throws Exception {

        if (rcv.getCreatedBy().equals("PENDING")) {
            throw new Exception("Cannot notify AM of pending receive!");
        }
        OwdClient client = ((OwdClient) HibernateSession.currentSession().load(OwdClient.class, new Integer(rcv.getAsn().getClientFkey())));

        try {

            String authString = OWDUtilities.encryptData(client.getClientId() + "/" + rcv.getId());

            String text = "\r\nThis is an HTML or web-formatted email containing a report of an inventory receive. If you are seeing this message, you may be unable" +
                    " to view HTML mail. You can access your receive report at the URL below:" +
                    "\r\n\r\nhttp://service.owd.com/webapps/accounts/upgradedselfreceivestatement.jsp?auth=" + URLEncoder.encode(authString,"UTF-8") + "\r\n\r\n" +
                    " If you continue to have problems viewing it, please contact your account manager for assistance.";


            WebResource server = new WebResource("http://service.owd.com/webapps/accounts/upgradedselfreceivestatement.jsp", WebResource.kGETMethod);
            server.addParameter("auth", authString);

            StringBuffer buffer = new StringBuffer();

            BufferedReader in = server.getResource();
            String inputLine;


            while ((inputLine = in.readLine()) != null) {
                buffer.append(inputLine + "\r");
            }

            //log.debug(buffer);
            //log.debug("getting email addresses");

            String amEmail = null;
            Vector<String> ccs = new Vector<String>();
            for(String addy:emails){

                if(amEmail==null)
                {
                   amEmail = addy;
                }
                else
                {
                    ccs.add(addy);
                }
            }

            byte[][] files = null;
            String[] names = null;
            boolean hasFiles = false;
            try {
                Map scans = getScanData(rcv);
                files = (byte[][]) scans.get("file");
                names = (String[]) scans.get("names");
                hasFiles = true;
            } catch (Exception e) {
                e.printStackTrace();



                    Mailer.sendMailWithAttachment("ASN Receipt for " + (rcv.getAsn().getClientRef().length() > 0 ? rcv.getAsn().getClientRef() : "OWD" + rcv.getAsn().getId() + "") + " (" + rcv.getAsn().getShipperName() + ") - " + (rcv.getIsPosted() == 1 ? "POSTED" : "NOT POSTED") ,
                        text, amEmail, ccs.toArray(), getASNAsExcelSheet(rcv.getAsn()), (rcv.getAsn().getClientRef().length() > 0 ? rcv.getAsn().getClientRef() : rcv.getAsn().getId() + "") + ".csv", "application/octet-stream",buffer.toString());

            }      //todo

            if (hasFiles) {

                    Mailer.sendMailWithAttachments("ASN Receipt for " + (rcv.getAsn().getClientRef().length() > 0 ? rcv.getAsn().getClientRef() : "OWD" + rcv.getAsn().getId() + "") + " (" + rcv.getAsn().getShipperName() + ") - " + (rcv.getIsPosted() == 1 ? "POSTED" : "NOT POSTED") ,
                        text, amEmail, ccs.toArray(), getASNAsExcelSheet(rcv.getAsn()), (rcv.getAsn().getClientRef().length() > 0 ? rcv.getAsn().getClientRef() : rcv.getAsn().getId() + "") + ".csv", files, names, "application/octet-stream",buffer.toString());

                 // Asn asn = (Asn) sess.load(Asn.class, new Integer("1657"));
                //  sendRowdysNotificationOfCreation(asn);
            }



        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

     private static void sendHTMLAMNotificationOfReceipt(Receive rcv) throws Exception {

        if (rcv.getCreatedBy().equals("PENDING")) {
            throw new Exception("Cannot notify AM of pending receive!");
        }
        OwdClient client = ((OwdClient) HibernateSession.currentSession().load(OwdClient.class, new Integer(rcv.getAsn().getClientFkey())));

         if("G298V2024".equals(rcv.getAsn().getGroupName()))
         {
             sendUpgradedSelfNotificationOfReceipt(rcv,new ArrayList<String>(
                     Arrays.asList("viet@symphonycommerce.com","warehouseASN@bulletproof.com")));
         }
         if("G_rockflowerpaperV2124".equals(rcv.getAsn().getGroupName()))
         {
             sendRockFlowerPaperNotificationOfReceipt(rcv, new ArrayList<String>(
                     Arrays.asList("rachel@rockflowerpaper.com",
                             "CustomerService@rockflowerpaper.com",
                             "katie@rockflowerpaper.com","suzanne@rockflowerpaper.com","customerservice2@rockflowerpaper.com")));
         }
         // Sean commented out on 2019-11-25 case 736503
         /*if("G_fredericksV2217".equals(rcv.getAsn().getGroupName()))
         {
             sendSymphonyNotificationOfReceipt(rcv, new ArrayList<String>(
                     Arrays.asList("marianne.johns@fredericks.com","sue.hancock@bendon.com","christian.tapili@bendon.com","rachelle.phillips@bendon.com","shivani.bhika@bendon.com")));
         }*/
         if("G_jbrandV2186".equals(rcv.getAsn().getGroupName())){
             sendSymphonyNotificationOfReceipt(rcv, new ArrayList<String>(
                     Arrays.asList("kristin.defino@jbrandjeans.com","Monica.Santos@jbrandjeans.com","Thai.Nguyen@jbrandjeans.com")));
         }
         if("G_bumblerideV2144".equals(rcv.getAsn().getGroupName())){
             sendSymphonyNotificationOfReceipt(rcv, new ArrayList<String>(
                     Arrays.asList("constance@bumbleride.com","isabel@bumbleride.com")));
         }
         if("G_halstonV2224".equals(rcv.getAsn().getGroupName())){
             sendSymphonyNotificationOfReceipt(rcv, new ArrayList<String>(
                     Arrays.asList("Roderick.Chua@halston.com","leo@jsgroup.com","melvin@jsgroup.com","amanda@jsgroup.com","annev@jsgroup.com")));
         }
         if("G324V2068".equals(rcv.getAsn().getGroupName())){
             sendSymphonyNotificationOfReceipt(rcv, new ArrayList<String>(
                     Arrays.asList("John@hillflint.com","lorne@hillflint.com","james@hillflint.com","woody@hillflint.com","greg@hillflint.com")));
         }
         if("G_drinkfinityV2227".equals(rcv.getAsn().getGroupName())){
             sendSymphonyNotificationOfReceipt(rcv, new ArrayList<String>(
                     Arrays.asList("David.Pedraja@pepsico.com","jimb@symphonycommerce.com","Benjamin.Bryan@pepsico.com","Delia.Moran@pepsico.com")));
         }

         if("G_nomadV2230".equals(rcv.getAsn().getGroupName())){
             sendSymphonyNotificationOfReceipt(rcv, new ArrayList<String>(
                     Arrays.asList("shipping@hellonomad.com")));
         }
         if("G_lagenceV2244".equals(rcv.getAsn().getGroupName())){
             sendSymphonyNotificationOfReceipt(rcv, new ArrayList<String>(
                     Arrays.asList("renee.washburn@lagencefashion.com","christopher.martinez@lagencefashion.com",
                             "internla@lagencefashion.com", "rockie.braun@lagence.com")));
         }


     try {

                        String authString = OWDUtilities.encryptData(client.getClientId() + "/" + rcv.getId());

                        String text = "\r\nThis is an HTML or web-formatted email containing a report of an inventory receive at OWD. If you are seeing this message, you may be unable" +
                                " to view HTML mail. You can access your receive report at the URL below:" +
                                "\r\n\r\nhttp://service.owd.com/webapps/accounts/receivestatement.jsp?auth=" + URLEncoder.encode(authString,"UTF-8") + "\r\n\r\n" +
                                " If you continue to have problems, please contact your account manager at OWD for assistance.";


                        WebResource server = new WebResource("http://service.owd.com/webapps/accounts/receivestatement.jsp", WebResource.kGETMethod);
                        server.addParameter("auth", authString);

                        StringBuffer buffer = new StringBuffer();

                        BufferedReader in = server.getResource();
                        String inputLine;


                        while ((inputLine = in.readLine()) != null) {
                            buffer.append(inputLine + "\r");
                        }

         //log.debug(buffer);
         //log.debug("getting email addresses");

               String amEmail = "owditadmin@owd.com";
               if (client != null) {
                   if (client.getAmEmail().length() > 5) {
                       amEmail = client.getAmEmail();

                   }
               }

               String clientAddresses = client.getAsnRcvEmail();

               Vector ccs = new Vector();


               if (clientAddresses.indexOf("@") > 0) {

                   StringTokenizer tokens = new StringTokenizer(clientAddresses, ",");

                   while (tokens.hasMoreTokens()) {

                       String addr = tokens.nextToken();

                       try {
                           MailAddressValidator.validate(addr);
                       } catch (Exception ea) {
                           addr = null;
                       }

                       if (addr != null)

                           ccs.addElement(addr);

                   }

               }

               if (ccs.size() < 1) ccs = null;


        // ccs=null;
        // amEmail = "owditadmin@owd.com";;

               //log.debug("sending email to " + amEmail);
               byte[][] files = null;
               String[] names = null;
               boolean hasFiles = false;
               try {
                   Map scans = getScanData(rcv);
                   files = (byte[][]) scans.get("file");
                   names = (String[]) scans.get("names");
                   hasFiles = true;
               } catch (Exception e) {
                   e.printStackTrace();
                   Mailer.sendMailWithAttachment("OWD ASN Receipt for " + (rcv.getAsn().getClientRef().length() > 0 ? rcv.getAsn().getClientRef() : "OWD" + rcv.getAsn().getId() + "") + " (" + rcv.getAsn().getShipperName() + ") - " + (rcv.getIsPosted() == 1 ? "POSTED" : "NOT POSTED") + " - " + client.getCompanyName(),
                           text, amEmail, (ccs == null ? null : (ccs.toArray())), getASNAsExcelSheet(rcv.getAsn()), (rcv.getAsn().getClientRef().length() > 0 ? rcv.getAsn().getClientRef() : rcv.getAsn().getId() + "") + ".csv", "application/octet-stream",buffer.toString());
               }      //todo
         
               if (hasFiles) {
                   Mailer.sendMailWithAttachments("OWD ASN Receipt for " + (rcv.getAsn().getClientRef().length() > 0 ? rcv.getAsn().getClientRef() : "OWD" + rcv.getAsn().getId() + "") + " (" + rcv.getAsn().getShipperName() + ") - " + (rcv.getIsPosted() == 1 ? "POSTED" : "NOT POSTED") + " - " + client.getCompanyName(),
                           text, amEmail, (ccs == null ? null : (ccs.toArray())), getASNAsExcelSheet(rcv.getAsn()), (rcv.getAsn().getClientRef().length() > 0 ? rcv.getAsn().getClientRef() : rcv.getAsn().getId() + "") + ".csv", files, names, "application/octet-stream",buffer.toString());
                   // Asn asn = (Asn) sess.load(Asn.class, new Integer("1657"));
                   //  sendRowdysNotificationOfCreation(asn);
               }



                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

    }

    public static byte[] getASNAsExcelSheet(Asn asn) throws Exception {


        try {
            DecimalFormat decform = new java.text.DecimalFormat("######0.00");

            byte[] stuff = null;
            StringBuffer sb = new StringBuffer();


            sb.append("ASN Receipt Report\n");
            sb.append("Client ASN Reference:," + ExcelUtils.getCellValue(asn.getClientRef()) + "\n");
            sb.append("OWD ASN Reference:," + ExcelUtils.getCellValue(asn.getId().intValue()) + "\n");
            sb.append("Client PO Reference:," + ExcelUtils.getCellValue(asn.getClientPo()) + "\n");
            sb.append("Shipper:," + ExcelUtils.getCellValue(asn.getShipperName()) + "\n");
            sb.append("Created:," + ExcelUtils.getCellValue(asn.getCreatedDate().toString()) + "\n");
            sb.append("Notes:," + ExcelUtils.getCellValue(asn.getNotes()) + "\n");
            sb.append("Expected Date:," + ExcelUtils.getCellValue(asn.getExpectDate().toString()) + "\n");
            sb.append("Blind ASN:," + ExcelUtils.getCellValue(asn.getHasBlind() == 1 ? "Yes" : "No") + "\n");

            List mapList = new ArrayList();

          //  Session sess = HibernateSession.currentSession();
            //sess.lock(asn, LockMode.READ);
            if (asn.getReceives() != null) {

                Iterator it = asn.getReceives().iterator();
                int indexNum = 1;
                while (it.hasNext()) {
                    Receive item = (Receive) it.next();
                    //sess.lock(item, LockMode.READ);
                    sb.append("\n\nRcv#,When,Minutes,Packing Slip?,Matches?,Count Method,Notes,Status\n");
                    sb.append(ExcelUtils.getCellValue((indexNum) + "."));
                    sb.append("," + ExcelUtils.getCellValue(item.getEndTimestamp().toString()));
                    sb.append("," + ExcelUtils.getCellValue(item.getBilledMinutes()));
                    sb.append("," + ExcelUtils.getCellValue(item.getIsPackSlipFound() == 1 ? "Yes" : "No"));
                    if (item.getIsPackSlipFound() == 1)
                        sb.append("," + ExcelUtils.getCellValue(item.getIsPackSlipMatch() == 1 ? "Yes" : "No"));
                    else
                        sb.append(",");
                    sb.append("," + ExcelUtils.getCellValue(item.getCountMethod()));
                    sb.append("," + ExcelUtils.getCellValue(item.getNotes()));
                    sb.append("," + ExcelUtils.getCellValue(item.getIsPosted() == 1 ? "Released" : "Waiting for manual release") + "\n\n\n");


                    Map itemMap = new TreeMap();
                    Iterator itm = item.getReceiveItems().iterator();
                    while (itm.hasNext()) {
                        ReceiveItem ri = (ReceiveItem) itm.next();
                        itemMap.put(new Integer(ri.getInventoryFkey()), ri);

                    }
                    mapList.add(itemMap);
                    indexNum++;
                }
            }
            sb.append("\nASN Item Status\nSKU,Title,Unit Cost,Expected");

            double[] rcvTotals = new double[mapList.size()];
            double itemTotals = 0.00;

            for (int i = 0; i < mapList.size(); i++) {
                sb.append(",Receive #" + (i + 1));
                sb.append(",Damaged #" + (i + 1));
                sb.append(",Cost ($)" + (i + 1));
                rcvTotals[i] = 0.00;
            }
            sb.append(",Difference From Expected,Total Received,Total Value\n");


            Iterator it2 = asn.getAsnItems().iterator();
            while (it2.hasNext()) {
                //  //log.debug("got a asnitem");
                AsnItem item = (AsnItem) it2.next();
                OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, new Integer(item.getInventoryFkey()));

                double itemCost = 0.00;
                try {
                    String strCost = inv.getSuppCost() == null ? "0.00" : inv.getSuppCost().toString();
                    itemCost = new Double(strCost).doubleValue();

                } catch (Exception ex) {
                    //numformatexception - ignore
                }
                // sess.lock(item,LockMode.READ);
                sb.append("\n" + ExcelUtils.getCellValue(item.getInventoryNum()));
                sb.append("," + ExcelUtils.getCellValue(item.getTitle() == null ? "" : item.getTitle()));
                sb.append("," + ExcelUtils.getCellValue(decform.format(itemCost)));
                sb.append("," + ExcelUtils.getCellValue(item.getExpected()));


                for (int i = 0; i < mapList.size(); i++) {
                    int receivedAmt = 0;
                    int damagedAmount = 0;
                    ReceiveItem currItem = ((ReceiveItem) ((Map) mapList.get(i)).get(new Integer(item.getInventoryFkey())));
                    if (currItem != null) {
                        receivedAmt = currItem.getQtyReceive();
                        damagedAmount = currItem.getQtyDamage();
                    }
                    sb.append("," + ExcelUtils.getCellValue(receivedAmt));

                    sb.append("," + ExcelUtils.getCellValue(damagedAmount));
                    sb.append("," + ExcelUtils.getCellValue(decform.format((((double) receivedAmt) * itemCost))));
                    rcvTotals[i] += (((double) receivedAmt) * itemCost);
                }

                sb.append("," + ExcelUtils.getCellValue(item.getReceived() - item.getExpected()));
                sb.append("," + ExcelUtils.getCellValue(item.getReceived()));
                sb.append("," + ExcelUtils.getCellValue(decform.format((((double) item.getReceived()) * itemCost))));
                itemTotals += (((double) item.getReceived()) * itemCost);


            }
            sb.append("\n,,,");


            for (int i = 0; i < mapList.size(); i++) {

                sb.append(",,," + ExcelUtils.getCellValue(decform.format(rcvTotals[i])));
            }

            sb.append(",,," + ExcelUtils.getCellValue(decform.format(itemTotals)));

            //  //log.debug("generated excel file len:"+sb.toString().length());
            return sb.toString().getBytes("UTF-8");
        } catch (Exception exe) {
            exe.printStackTrace();
            throw exe;
        }
    }

    public static Map getScanData(Receive rcv) throws Exception {
        Map data = new TreeMap();
        Collection scans = rcv.getScanDocs();

        if (scans.size() == 0) throw new Exception("No Files");
        String[] names = new String[scans.size()];
        byte[][] file = new byte[scans.size()][];


        for (int i = 0; i < scans.size(); i++) {
            ScanReceive sr = (ScanReceive) scans.toArray()[i];
            file[i] = (ScanManager.getScanFromReceive(rcv, sr.getName()));
            names[i] = sr.getName();
        }
        data.put("file", file);
        data.put("names", names);

        return data;
    }

}






