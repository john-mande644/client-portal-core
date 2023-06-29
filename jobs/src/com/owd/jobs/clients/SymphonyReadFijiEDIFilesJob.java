package com.owd.jobs.clients;

import com.owd.hibernate.generated.OwdLineItem;
import com.owd.hibernate.generated.OwdOrder;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.managers.InventoryManager;
import com.owd.core.managers.PackingManager;
import com.owd.hibernate.*;
import com.owd.jobs.OWDStatefulJob;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;

/**
 * Created by stewartbuskirk1 on 4/6/15.
 */
public class SymphonyReadFijiEDIFilesJob   extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();

    public void internalExecute() {

  /*      try {
            OwdEdiComRemoteFTP ftp = new OwdEdiComRemoteFTP();
            List<String> files = ftp.listIncomingFiles("DSWater");
            for (String file : files) {
                log.debug(file);
                String data = new String(ftp.getDataFile(file, "DSWater"));
                  log.debug(data);
                String type = OwdEdiComRemoteFTP.getIncomingFileDocType(data);

                if ("997".equals(type)) {

                    ftp.archiveIncomingDataFile("DSWater", file, type);
                } else  if("945".equals(type)) {

                        com.altova.io.StringInput Name945Source = new com.altova.io.StringInput(data);

                        MappingMapTo945_Shipped_Data MappingMapTo945_Shipped_DataObject = new MappingMapTo945_Shipped_Data();

                        com.altova.io.StringOutput Processed945 = new com.altova.io.StringOutput();


                        MappingMapTo945_Shipped_DataObject.run(
                                Name945Source,
                                Processed945);

                        log.debug("945:"+Processed945.getString());
                    Mailer.sendMail("DS Waters EDI Order Shipped Check Email", "received 945\r\n\r\n"+data+"\r\n\r\n"+Processed945.getString(),
                            "owditadmin@owd.com",
                            "donotreply@owd.com");
                        DelimitedReader dr = new TabReader(new BufferedReader(new StringReader(Processed945.getString().toString())),false);
                        log.debug("ROWS:"+dr.getRowCount());
                        for(int i=0;i<dr.getRowCount();i++)
                        {
                               String clientOrderRef = dr.getStrValue(1,i,"????");
                               shipDSWaterOrder(clientOrderRef);

                        }


                    ftp.archiveIncomingDataFile("DSWater", file, type);
                    } else {
                    log.debug("UNKNOWN Type");
                }

                }


          *//*      com.altova.io.StringOutput Processed997 = new com.altova.io.StringOutput();
                com.altova.io.StringInput Named997Source = new com.altova.io.StringInput(data);


                MappingMapTo945_997 MappingMapTo945_997Object = new MappingMapTo945_997();


                MappingMapTo945_997Object.run(
                        Named997Source,
                        Processed997);
                log.debug("997:"+Processed997.getString());

                ftp.putOutboundFile("DSWater",Processed997.getString().toString().getBytes(), Calendar.getInstance().getTimeInMillis()+".997");

                ftp.archiveIncomingDataFile("DSWater", file, type);*//*




        }catch(Exception ex)
        {
            LogableException le = new LogableException(ex, "Error reading DS Water 945", "", "489", "EDI", LogableException.errorTypes.ORDER_IMPORT);

        }*/
    }

    private void shipDSWaterOrder(String clientOrderRef) throws Exception {
        PreparedStatement pss = HibernateSession.getPreparedStatement("select top 1 order_id from owd_order join edi_queue edi on order_id=order_fkey and edi.status=2\n" +
                "where order_status not in ('at warehouse','shipped','void') and type='DSWater' and doctype='940' and order_refnum=?");
        pss.setString(1,clientOrderRef);
        ResultSet rs = pss.executeQuery();
        int oid = 0;
        if(rs.next())
        {
            oid = rs.getInt(1);
        }
        pss.close();
        if(oid>0) {
            OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,oid);
            float ttlWeight = 0.00f;
             for(OwdLineItem line:order.getLineitems())
             {
                 if(line.getInventoryNum().startsWith("DS-")) {
                     InventoryManager.postInventoryAdjustmentAsAdjustmentToCurrentValue(line.getOwdInventory().getInventoryId(), order.getClientFkey(), line.getQuantityRequest(),  OWDUtilities.getSQLDateNoTimeForToday(), "DSWATER"+Calendar.getInstance().getTimeInMillis(), order.getFacilityCode(), HibernateSession.currentSession());
                     ttlWeight+=(((float)line.getQuantityRequest())*line.getOwdInventory().getWeightLbs());
                 }else{
                     throw new Exception("Non DSWater SKU found for order "+order.getOrderId());
                 }
             }

            OrderUtilities.shipExistingHibernateOrder(order);
            PackingManager.packAndShip(oid, ttlWeight, 0.00, "DS Waters Route Truck");

            HibUtils.commit(HibernateSession.currentSession());
            HibernateSession.currentSession().clear();

            pss = HibernateSession.getPreparedStatement("update owd_order set not_billed=1 where order_id=?");
            pss.setInt(1, oid);
            pss.executeUpdate();
            HibUtils.commit(HibernateSession.currentSession());
            HibernateSession.currentSession().clear();
        }
    }

    public static void main(String[] args) throws Exception {
        run();
    }
}
