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
class TransferOrderShipment {



    List<TordItem> tordItems = new ArrayList<TordItem>()
   String toNumber = ""
    String orderId = "";
   String tracking="";
    String store="";


   public static void main(String[] args) throws Exception {
       new TransferOrderShipment().reportTransferOrderShipments();

   }

   public void addTransferOrderItem(String sku, int qty) {
       TordItem item = new TordItem()
       item.setSku(sku)
       item.setQuantity(qty)
       tordItems.add(item)
   }

   public String getTransferOrderXML() {
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
           TORDS()
                   {
                       TORD(from_store: 'OAK', to_no: toNumber, to_store: store, notes2: tracking, document_date: DateToXsdDatetimeFormatter.format(Calendar.getInstance().getTime()))
                               {
                                   TORD_ITEMS()
                                           {
                                               for (TordItem item: tordItems) {
                                                   TORD_ITEM(ECommId: item.sku.replaceAll("/KIT",""), ItemNumber:item.externalSku, qty: item.quantity)
                                               }
                                           }
                               }
                   }

       }


       println "sending"
       String xml = builder.bind(createAsnRequest).toString()
       println xml
       return xml

   }



   public class TordItem {
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

    public void reportTransferOrderShipments() {



        try {

            SQLQuery query = HibernateSession.currentSession().createSQLQuery("update owd_order_track set reported=1 from owd_order_track t join owd_order o on order_id=order_fkey and t.is_void=0 and o.order_status='Shipped'  and order_type='RetailPro' and order_refnum like 'RTO_%' and client_fkey=482 and t.reported=0;");
            query.executeUpdate();
            HibUtils.commit(HibernateSession.currentSession());

            List<Object[]> data = HibernateSession.currentSession().createSQLQuery("""select order_id,order_refnum,tracker,inventory_num,quantity_actual,po_num from owd_order
join owd_line_item l on l.order_fkey=order_id and quantity_actual>0
join (select order_fkey,min(tracking_no) as tracker from owd_order_track t where t.is_void=0 and t.reported=1 group by t.order_fkey)
as tracks on order_id=tracks.order_fkey
 where client_fkey=482 and order_status='Shipped' and isnull(is_shipping,0)=0 and order_refnum like 'RTO_%'
                     and order_type='RetailPro' order by order_refnum,inventory_num,po_num """).list();
              print data
             if(data.size()>0)
             {
                 TransferOrderShipment tos = new TransferOrderShipment();

                 for(Object[] lineData:data)
                 {

                     toNumber=lineData[kOrderRefnum].toString().replaceAll("RTO_","")
                     tracking= lineData[kTrack].toString()
                     orderId = lineData[kOrderId].toString()
                     store = lineData[kPoNum].toString()

                     if(!(tos.toNumber.equals(toNumber)))
                     {
                         if(!(tos.toNumber.equals("")))
                         {
                             String fileData = tos.getTransferOrderXML()
                          //   println fileData
                             TransferUtilities.putRetailProFile("TO_"+tos.toNumber+"_"+Calendar.getInstance().getTimeInMillis()+".xml",fileData, true)
                             query = HibernateSession.currentSession().createSQLQuery("update owd_order_track set reported=2 where order_fkey="+tos.orderId+" and reported=1;");
                             query.executeUpdate();
                             HibUtils.commit(HibernateSession.currentSession());
                         }
                         tos.toNumber = toNumber
                         tos.tracking = tracking
                         tos.orderId = orderId
                         tos.store = store
                         tos.tordItems=new ArrayList<TordItem>()
                     }
                     tos.addTransferOrderItem(lineData[kItemSku].toString(),Integer.parseInt(lineData[kItemQty].toString()))
                 }

                  if(!(tos.toNumber.equals("")))
                         {
                             String fileData = tos.getTransferOrderXML()
                             TransferUtilities.putRetailProFile("TO_"+tos.toNumber+"_"+Calendar.getInstance().getTimeInMillis()+".xml",fileData, true)
                             query = HibernateSession.currentSession().createSQLQuery("update owd_order_track set reported=2 where order_fkey="+tos.orderId+" and reported=1;");
                             query.executeUpdate();
                             HibUtils.commit(HibernateSession.currentSession());
                         }
             }


/*

           query = HibernateSession.currentSession().createSQLQuery("update owd_order_track set reported=2 from owd_order_track t join owd_order o on order_id=order_fkey and t.is_void=0 and o.order_status='Shipped'  and order_type='RetailPro' and order_refnum like 'RTO_%' and client_fkey=482 and t.reported=1;");
            query.executeUpdate();
            HibUtils.commit(HibernateSession.currentSession());*/

        }catch(Exception ex)
        {
           ex.printStackTrace();
        }
    }



}

