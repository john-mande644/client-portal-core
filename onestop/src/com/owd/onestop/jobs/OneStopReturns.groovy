package com.owd.onestop.jobs

import com.owd.hibernate.HibUtils
import com.owd.hibernate.HibernateSession
import com.owd.onestop.IOrderProcessing
import com.owd.onestop.ReturnDetails
import com.owd.onestop.ReturnOperationResponse
import com.owd.onestop.test.DateToXsdDatetimeFormatter
import org.hibernate.SQLQuery

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 3/29/12
 * Time: 3:02 PM
 * To change this template use File | Settings | File Templates.
 */
class OneStopReturns {

    public static void main(String[] args)
    {
       OneStopReturns.reportPendingReturns()

    }


    public static void reportPendingReturns()
    {
       //vw_babeland_onestopreturns
        Map<String,OneStopReturn> returnMap = new TreeMap<String, OneStopReturn>();

        try {

            List<Object[]> data = HibernateSession.currentSession().createSQLQuery("select receive_id,order_refnum,notes,sku,unstockqty,restockqty from vw_babeland_onestopreturns order by order_refnum asc").list();

            //  List<OneStopShipment> shipments = new ArrayList<OneStopShipment>();
            for(Object[] returndata:data)
            {

                if(!(returnMap.containsKey(""+returndata[0])))

                {
                    OneStopReturn ret = new OneStopReturn();
                    ret.rid = ""+returndata[0]

                    ret.reference = returndata[1]
                    ret.notes = returndata[2]
                    returnMap.put(ret.rid,ret);

                }
               
                OneStopReturn ret = returnMap.get(""+returndata[0])
                String sku = ""+returndata[3]
                Integer unstockable = Integer.parseInt(""+returndata[4])
                Integer restockable = Integer.parseInt(""+returndata[5])
                
                if(unstockable!=0)
                {
                    if(ret.unstockableItems.containsKey(sku))
                    {
                        ret.unstockableItems.put(sku,""+(Integer.parseInt(ret.unstockableItems.get(sku))+unstockable))
                    }   else
                    {
                        ret.unstockableItems.put(sku,""+unstockable)
                    }
                }
                if(restockable!=0)
                {
                    if(ret.restockedItems.containsKey(sku))
                    {
                        ret.restockedItems.put(sku,""+(Integer.parseInt(ret.restockedItems.get(sku))+restockable))
                    }   else
                    {
                        ret.restockedItems.put(sku,""+restockable)
                    }
                }
                    
                
               
            }

           for(OneStopReturn ret:returnMap.values())
           {
               try{
                sendReturnReport(ret)
                markReturnAsReported(ret)
               }catch(Exception ex)
               {
                   ex.printStackTrace();
               }
           }

        }catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }


    public static class OneStopReturn
    {
        String rid
        String reference
        String notes =""
        Map<String,String> unstockableItems = new TreeMap<String, String>()
        Map<String,String> restockedItems = new TreeMap<String, String>()


        public String toString ( ) {
            return "OneStopReturn{" +
                    "rid='" + rid + '\'' +
                    ", reference='" + reference + '\'' +
                    ", notes='" + notes + '\'' +
                    ", unstockableItems=" + unstockableItems +
                    ", restockedItems=" + restockedItems +
                    '}' ;
        }}

    public static void sendReturnReport(OneStopReturn returnData) throws Exception {


        IOrderProcessing caller = OneStopService.service.getBasicHttpBinding_IOrderProcessing();
        ReturnDetails sd = new ReturnDetails();
        sd.setClientDetails(OneStopService.cd)

        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'

        def shipxml =
            {
                mkp.xmlDeclaration()
                OrderDetails() {
                    OrderInfo() {
                        OrderID(returnData.reference)
                        ReturnDetails() {
                            if(returnData.restockedItems.size()>0)
                            {    
                            Restockable() {
                                returnData.restockedItems.keySet().each {
                                    String qty = (String)(returnData.restockedItems.get(it))
                                    String sku = it+""
                                        ReturnItem() {
                                            UPC(sku.replaceAll("/KIT",""))
                                            ReasonDesc(returnData.notes)
                                            CreateDate(DateToXsdDatetimeFormatter.format(Calendar.getInstance().getTime()))
                                            Quantity(qty)
                                        }
                                    
                                }


                            }
                        }
                            if(returnData.unstockableItems.size()>0)
                            {
                                NotRestockable() {
                                    returnData.unstockableItems.keySet().each {
                                        String qty = (String)(returnData.unstockableItems.get(it))
                                        String sku = it+""
                                        ReturnItem() {
                                            UPC(sku.replaceAll("/KIT",""))
                                            ReasonDesc(returnData.notes)
                                            CreateDate(DateToXsdDatetimeFormatter.format(Calendar.getInstance().getTime()))
                                            Quantity(qty)
                                        }
                                    
                                } }


                            }
                        }
                    }
                }



            }
        String xmlSent = builder.bind(shipxml).toString()
        print xmlSent

        sd.setReturnDataXml(xmlSent)

        ReturnOperationResponse response = caller.postReturns(sd);

        println "ReturnOperationResponse:"
        println response.operationResponseStatus.processStatusCode
        println response.operationResponseStatus.processStatusMessage
        println response.operationResponseStatus.responseStatusCode
        println response.operationResponseStatus.responseStatusMessage

        if(!("0".equals(response.operationResponseStatus.processStatusCode+"")))
        {
            throw new Exception("Return report failed : "+(response.operationResponseStatus.processStatusMessage+":"+response.operationResponseStatus.responseStatusMessage+""));
        }
    }

        public static void markReturnAsReported(OneStopReturn returnData) {

            SQLQuery query = HibernateSession.currentSession().createSQLQuery("update owd_receive_item set sys_reported=1 where receive_fkey="+returnData.rid+";");
            query.executeUpdate();
            HibUtils.commit(HibernateSession.currentSession());
        }

}
