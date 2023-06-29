package com.owd.dc.inventory;

import com.owd.core.dbUtilities;
import com.owd.dc.HandheldUtilities;
import com.owd.dc.inventory.forms.skuForm;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.hibernate.generated.OwdInventoryHistory;
import com.owd.hibernate.generated.OwdReceive;
import com.owd.hibernate.generated.OwdReceiveItem;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionImplementor;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Sep 20, 2006
 * Time: 1:40:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class supplyUtil {
    public static void saveAdjust(skuForm sf, Session sess, HttpServletRequest req) throws Exception{
        OwdReceive rcv = new OwdReceive();
        try{
            String user = (String) req.getAttribute("loginName");
            String DATE_FORMAT = "MM/dd/yy HH:mm:ss";
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
              OwdInventory inv = HandheldUtilities.loadInv(Integer.valueOf(sf.getSku()));
            rcv.setBlNum("");

            rcv.setCarrier("");
            rcv.setCreatedBy(user);
            rcv.setCreatedDate((Calendar.getInstance().getTime()));
            rcv.setDriver("");
            rcv.setIsVoid(false);
            rcv.setNotes("Adjustment for Supplies used.");
            rcv.setOwdClient(inv.getOwdClient());
            rcv.setReceiveDate(Calendar.getInstance().getTime());
            rcv.setReceiveUser(user);
            rcv.setRefNum(sf.getSku()+"/"+Calendar.getInstance().getTime());
            rcv.setTimeIn(Calendar.getInstance().getTime());
            rcv.setType("Adjustment");
            sess.save(rcv);

            System.out.println("ReceiveId "+rcv.getReceiveId());

              OwdReceiveItem ri = new OwdReceiveItem();
                ri.setCreatedBy(user);
                ri.setCreatedDate(Calendar.getInstance().getTime());
                System.out.println("1");
                ri.setDescription(inv.getDescription());
                System.out.println("2");
                ri.setInventoryNum(inv.getInventoryNum());
                System.out.println("3");
              /* if(af.getFormItems()[i].getUnusable().equals("yes")){
                   ri.setIsUnusable(true);

                ri.setIsVoid(0);*/
                System.out.println("4");
                ri.setItemStatus("New");
                System.out.println("5");
                ri.setOwdInventory((OwdInventory) sess.load(OwdInventory.class, inv.getInventoryId()));
                System.out.println("6");
                if(sf.getNumlabels().equals("")){
                    throw new Exception("You must have a quanity for sku " + inv.getInventoryNum());
                }
                ri.setQuantity(new Integer(0-Integer.parseInt(sf.getNumlabels())));
                System.out.println("7");
               /* ri.setReturnReason(af.getFormItems()[i].getReturnReason());*/
                System.out.println("8");
                ri.setOwdReceive(rcv);
                sess.save(ri);
                System.out.println("9");
               // rcv.getOwdReceiveItems().add(ri);
              OwdInventoryHistory invOh = new OwdInventoryHistory();
                                invOh.setInventoryFkey(ri.getOwdInventory().getInventoryId());
                                invOh.setQtyChange(ri.getQuantity()*-1);
                                invOh.setReceiveItemFkey(ri.getReceiveItemId());
                                invOh.setNote("supplyUtil.saveAdjust");
                                sess.save(invOh);

            rcv.setTransactionNum("OWDADJ-"+ dbUtilities.getNextID(((SessionImplementor)sess).getJdbcConnectionAccess().obtainConnection(),"Receive"));
            sess.save(rcv);
            System.out.println("10");
           sess.flush();
            System.out.println("11");
            com.owd.hibernate.HibUtils.commit(sess);


        } catch(Exception e){
             e.printStackTrace();

            throw (new Exception("Unable to save Adjustment ("+e.getMessage()+")"));

        }
       
    }
}
