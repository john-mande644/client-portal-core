package com.owd.intranet.adjustments;

import com.owd.core.managers.LotManager;
import com.owd.hibernate.generated.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.managers.FacilitiesManager;
import com.owd.intranet.forms.adjustForm;
import com.owd.hibernate.*;
import com.owd.core.dbUtilities;
import com.owd.core.managers.InventoryManager;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionImplementor;

import java.sql.Connection;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 7, 2006
 * Time: 4:05:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class adjustUtil {
private final static Logger log =  LogManager.getLogger();

    public static adjustForm saveAdjust(adjustForm af, Session sess) throws Exception{

         ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        OwdReceive rcv = new OwdReceive();
        try{
            String DATE_FORMAT = "MM/dd/yy HH:mm:ss";
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);

            rcv.setBlNum(af.getBlNum());

            rcv.setCarrier(af.getCarrier());
            rcv.setCreatedBy(af.getCreatedBy());
            rcv.setCreatedDate(sdf.parse(af.getReceiveDate()));
            rcv.setDriver(af.getDriver());
            rcv.setIsVoid(false);

            // Sean 02/10/2020 745174 check notes char > 2 char
            if(af.getNotes().length()<= 2){
                throw new Exception("You must enter adjustment notes for return.");
            }

            rcv.setNotes(af.getNotes());
            rcv.setOwdClient((OwdClient) sess.load(OwdClient.class,Integer.valueOf(af.getClientFkey())));
            rcv.setReceiveDate(sdf.parse(af.getReceiveDate()));
            rcv.setReceiveUser(af.getReceiveUser());
            rcv.setRefNum(af.getRefNum());
            rcv.setTimeIn(sdf.parse(af.getTimeIn()));
            rcv.setTimeOut(sdf.parse(af.getTimeIn()));
            rcv.setType("Adjustment");
            rcv.setFacilityCode(af.getCurrentLocation());

            sess.save(rcv);

            //log.debug("ReceiveId "+rcv.getReceiveId());
            for(int i =0;i<af.getFormItems().length;i++){
              OwdReceiveItem ri = new OwdReceiveItem();
                ri.setCreatedBy(af.getFormItems()[i].getCreatedBy());
                ri.setCreatedDate(sdf.parse(af.getReceiveDate()));
                //log.debug("1");
                ri.setDescription(af.getFormItems()[i].getDescription());
                //log.debug("2");
                ri.setInventoryNum(af.getFormItems()[i].getInventoryNum());
                //log.debug("3");
              /* if(af.getFormItems()[i].getUnusable().equals("yes")){
                   ri.setIsUnusable(true);
               }*/
                ri.setIsVoid(0);
                //log.debug("4");
                ri.setItemStatus(af.getFormItems()[i].getItemStatus());
                //log.debug("5");
                ri.setOwdInventory((OwdInventory) sess.load(OwdInventory.class, Integer.valueOf(af.getFormItems()[i].getInventoryId())));
                //log.debug("6");
                if(af.getFormItems()[i].getQuantity().equals("")||af.getFormItems()[i].getQuantity().equals("0")){
                    throw new Exception("You must have a quanity for sku " + af.getFormItems()[i].getInventoryNum());
                }
                ri.setQuantity(Integer.valueOf(af.getFormItems()[i].getQuantity().trim()));
                //log.debug("7");
               /* ri.setReturnReason(af.getFormItems()[i].getReturnReason());*/
                //log.debug("8");
                ri.setOwdReceive(rcv);
                sess.save(ri);
                //log.debug("9");
               // rcv.getOwdReceiveItems().add(ri);
                OwdInventoryHistory invOh = new OwdInventoryHistory();
                if(LotManager.isInventoryIdLotControlled(ri.getOwdInventory().getInventoryId())){
                    if(null!=af.getFormItems()[i].getLotValue() && af.getFormItems()[i].getLotValue().length()>0){

                        OwdLotOwdReceiveItem lotItem = new OwdLotOwdReceiveItem();
                        OwdLotValue lv = LotManager.getExistingOwdLotValueForString(af.getFormItems()[i].getLotValue(),ri.getOwdInventory().getInventoryId());
                        lotItem.setLotValue(lv);

                        lotItem.setQuantityChange(ri.getQuantity());


                        sess.save(ri);
                        lotItem.setOwdReceiveItem(ri);
                        ri.getLots().add(lotItem);
                        sess.save(lotItem);
                        invOh.setLot(lv);

                    }else{
                        throw new Exception("No lot data for "+ ri.getOwdInventory().getInventoryNum()+". Please restart, if problem persists contact IT");

                    }

                }


                    invOh.setInventoryFkey(ri.getOwdInventory().getInventoryId());
                    invOh.setQtyChange(ri.getQuantity());

                invOh.setReceiveItemFkey(ri.getReceiveItemId());
                invOh.setNote("adjustUtil.saveAdjust");
                invOh.setFacility(FacilitiesManager.getFacilityForCode(af.getCurrentLocation()));

                sess.save(invOh);
            }
            rcv.setTransactionNum("OWDADJ-"+ dbUtilities.getNextID( ((SessionImplementor)sess).getJdbcConnectionAccess().obtainConnection(),"Receive"));
            sess.save(rcv);
            //log.debug("10");
            sess.flush();
            //log.debug("11");
            HibUtils.commit(sess);


        } catch(Exception e){
             e.printStackTrace();

            throw (new Exception("Unable to save Adjustment ("+e.getMessage()+")"));

        }
         af.setReceiveId(rcv.getReceiveId()+"");
        af.setTransactionNum(rcv.getTransactionNum());

        return af;
    }

    public static OwdReceive voidAdjustment(Session sess, String recId, String user) throws Exception{


         ((SessionImplementor)sess).getJdbcConnectionAccess().obtainConnection().setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        OwdReceive rec = (OwdReceive) sess.load((OwdReceive.class), Integer.valueOf(recId));

        if(!rec.getTransactionNum().startsWith("OWDRCV-")){
      if(InventoryManager.countInProgress(HibernateSession.currentSession(), rec.getOwdClient().getClientId().intValue())){
            throw new Exception("Inventory Count in progress, you cannot adjust inventory");
        }
       Iterator it = rec.getOwdReceiveItems().iterator();

        while(it.hasNext()){
            OwdReceiveItem ri = (OwdReceiveItem) it.next();

                    OwdInventoryHistory invOh = new OwdInventoryHistory();
                    invOh.setInventoryFkey(ri.getOwdInventory().getInventoryId());
                    invOh.setQtyChange(ri.getQuantity()*-1);
                    invOh.setReceiveItemFkey(ri.getReceiveItemId());
                    invOh.setFacility(FacilitiesManager.getFacilityForCode(rec.getFacilityCode()));
                    invOh.setNote("adjustUtil.voidAdjustment");
                    sess.save(invOh);
            ri.setIsVoid(1);
            sess.save(ri);

                }
          rec.setIsVoid(true);
          rec.setModifiedBy(user);
            rec.setCreatedBy(user);
        sess.save(rec);
        sess.flush();
        HibUtils.commit(sess);;
        }else{
            throw new Exception("Unable to void Receive Created through ASN Manager");
        }
        sess.refresh(rec);
        return rec;
        }

    }


