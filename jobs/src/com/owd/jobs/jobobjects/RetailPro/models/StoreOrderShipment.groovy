package com.owd.jobs.jobobjects.RetailPro.models

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.HibUtils
import com.owd.hibernate.HibernateSession
import com.owd.jobs.jobobjects.RetailPro.DateToXsdDatetimeFormatter
import com.owd.jobs.jobobjects.RetailPro.TransferUtilities
import org.hibernate.SQLQuery

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 1/20/12
 * Time: 3:05 PM
 * To change this template use File | Settings | File Templates.
 *
 *

 */
class StoreOrderShipment {



    List<SoItem> soItems = new ArrayList<SoItem>()
   String soNumber = ""
    String orderId = "";
   String tracking="";


   public static void main(String[] args) throws Exception {
          StoreOrderShipment sos = new StoreOrderShipment()
         sos.reportStoreOrderShipments()
   }

   public void addStoreOrderItem(String sku, int qty) {
       SoItem item = new SoItem()
       item.setSku(sku)
       item.setQuantity(qty)
       soItems.add(item)
   }

   public String getStoreOrderXML() {
/*
  <TORDS>
<TORD to_no="4" po_no="" document_date="2011-12-28T12:07:14" from_store="OAK" to_store="SEA" notes2="">
<TORD_ITEMS>
<TORD_ITEM ECommID="01 561 00" qty="1"/>
<TORD_ITEM ECommID="09 295 13" qty="1"/>
<TORD_ITEM ECommID="09 444 00" qty="1"/>
</TORD_ITEMS>
</TORD>
</TORDS>
    */
       def builder = new groovy.xml.StreamingMarkupBuilder()
       builder.encoding = 'UTF-8'
       def createAsnRequest =
       {
           mkp.xmlDeclaration()
           SOS()
                   {
                       SO(store_no: 'OAK', so_no: soNumber, notes2: tracking, document_date: DateToXsdDatetimeFormatter.format(Calendar.getInstance().getTime()))
                               {
                                   SO_ITEMS()
                                           {
                                               for (SoItem item: soItems) {
                                                   SO_ITEM(ECommId: item.sku.replaceAll("/KIT",""), ItemNumber:item.externalSku, qty: item.quantity)
                                               }
                                           }
                               }
                   }

       }


     //  println "sending"
       String xml = builder.bind(createAsnRequest).toString()
    //   println xml
       return xml

   }



   public class SoItem {
private final static Logger log =  LogManager.getLogger();
       String sku
       String externalSku
       int quantity

   }

    static final int kOrderId = 0;
   static final int kOrderRefnum = 1;
   static final int kTrack = 2;
    static final int kItemSku = 3;
    static final int kItemQty = 4;
    static final int kPoNum = 5;

    public void reportStoreOrderShipments() {



        try {

            SQLQuery query = HibernateSession.currentSession().createSQLQuery("update owd_order_track set reported=1 from owd_order_track t join owd_order o on order_id=order_fkey and t.is_void=0 and o.order_status='Shipped'  and order_type='RetailPro' and order_refnum like 'RSO_%' and client_fkey=482 and t.reported=0;");
            query.executeUpdate();
            HibUtils.commit(HibernateSession.currentSession());

            List<Object[]> data = HibernateSession.currentSession().createSQLQuery("""select order_id,order_refnum,tracker,inventory_num,quantity_actual,po_num from owd_order
join owd_line_item l on l.order_fkey=order_id and quantity_actual>0
join (select order_fkey,min(tracking_no) as tracker from owd_order_track t where t.is_void=0 and t.reported=1 group by t.order_fkey)
as tracks on order_id=tracks.order_fkey
 where client_fkey=482 and order_status='Shipped' and isnull(is_shipping,0)=0 and order_refnum like 'RSO_%'
                     and order_type='RetailPro' order by order_refnum,inventory_num,po_num """).list();
              print data
             if(data.size()>0)
             {
                 StoreOrderShipment so = new StoreOrderShipment();

                 for(Object[] lineData:data)
                 {

                     soNumber=lineData[kOrderRefnum].toString().replaceAll("RSO_","")
                     tracking= lineData[kTrack].toString()
                     orderId = lineData[kOrderId].toString()

                     if(!(so.soNumber.equals(soNumber)))
                     {
                         if(!(so.soNumber.equals("")))
                         {
                             String fileData = so.getStoreOrderXML()
                             println fileData
                             TransferUtilities.putRetailProFile("SO_"+so.soNumber+"_"+Calendar.getInstance().getTimeInMillis()+".xml",fileData, true)
                             query = HibernateSession.currentSession().createSQLQuery("update owd_order_track set reported=2 where order_fkey="+so.orderId+" and reported=1;");
                             query.executeUpdate();
                             HibUtils.commit(HibernateSession.currentSession());
                         }
                         so.soNumber = soNumber
                         so.tracking = tracking
                         so.orderId = orderId
                         so.soItems=new ArrayList<SoItem>()
                     }
                     so.addStoreOrderItem(lineData[kItemSku].toString(),Integer.parseInt(lineData[kItemQty].toString()))
                 }

                  if(!(so.soNumber.equals("")))
                         {
                             String fileData = so.getStoreOrderXML()
                             println fileData

                             TransferUtilities.putRetailProFile("SO_"+so.soNumber+"_"+Calendar.getInstance().getTimeInMillis()+".xml",fileData, true)
                             query = HibernateSession.currentSession().createSQLQuery("update owd_order_track set reported=2 where order_fkey="+so.orderId+" and reported=1;");
                             query.executeUpdate();
                             HibUtils.commit(HibernateSession.currentSession());
                         }
             }

        }catch(Exception ex)
        {
           ex.printStackTrace();
        }
    }



}

