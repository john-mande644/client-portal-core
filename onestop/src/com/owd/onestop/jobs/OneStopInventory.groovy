package com.owd.onestop.jobs

import com.owd.onestop.InventoryOperationResponse
import com.owd.onestop.IOrderProcessing
import com.owd.onestop.InventoryDetails
import com.owd.hibernate.generated.OwdInventory
import com.owd.hibernate.HibernateSession
import org.hibernate.criterion.Expression
import com.owd.core.Mailer

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 3/14/12
 * Time: 12:47 AM
 * To change this template use File | Settings | File Templates.
 */
class OneStopInventory {

    static List<String> alwaysInStockSkus = new ArrayList<String>();

         static {

  alwaysInStockSkus.add("01 490 02");
  alwaysInStockSkus.add("01 625 00");
  alwaysInStockSkus.add("01 626 01");
  alwaysInStockSkus.add("01 626 02");
  alwaysInStockSkus.add("01 627 02");
  alwaysInStockSkus.add("01 627 01");
  alwaysInStockSkus.add("02 042 02");
         }


    public static void runInventoryUpdate() {
        IOrderProcessing caller = OneStopService.service.getBasicHttpBinding_IOrderProcessing();
        InventoryDetails sd = new InventoryDetails();
        sd.setClientDetails(OneStopService.cd)


        List<OwdInventory> items = HibernateSession.currentSession().createCriteria(OwdInventory.class).add(Expression.eq("owdClient.clientId",new Integer(482))).list()

        System.out.println(items);
        Map<String,String> testItemMap = new TreeMap<String, String>()
        int count = 0;
        items.each {  item ->
            println item.inventoryNum
            count++
         //   if(count<700)
        //    {
            if(item.isAutoInventory==0)
            {
                testItemMap.put(item.inventoryNum.endsWith("/KIT")?item.inventoryNum.substring(0,item.inventoryNum.indexOf("/KIT")):item.inventoryNum,(alwaysInStockSkus.contains(item.inventoryNum)?"1000":((item.getOwdInventoryOh().qtyOnHand).toString())))
            }
        //    }
        }
        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def itemxmlrequest =
        {
            /* <xs:element name="InventoryInfo" msdata:IsDataSet="false" msdata:UseCurrentLocale="false">
    <xs:complexType>
      <xs:choice minOccurs="0" maxOccurs="unbounded">
        <xs:element name="InventoryItems ">
          <xs:complexType>
            <xs:sequence>
              <xs:element name="UPC" type="xs:string" minOccurs="1" />
              <xs:element name="Quantity" type="xs:integer" minOccurs="1" />
              <xs:element name="UpdateDate" type="xs:dateTime" minOccurs="0" />
            </xs:sequence>
          </xs:complexType>
        </xs:element>*/

        InventoryInfo() {
            for(String sku:testItemMap.keySet())
            {
            InventoryItems()
                    {
                        UPC(sku)
                        Quantity(testItemMap.get(sku))
                    }
            }
        }


        }

        String itemupdatexml = builder.bind(itemxmlrequest).toString()
        println itemupdatexml

       sd.setInventoryDataXml(itemupdatexml)

        InventoryOperationResponse response = caller.inventoryIncrementAction(sd);

        println "processCode:" + response.operationResponseStatus.processStatusCode
        println "processMessage:" + response.operationResponseStatus.processStatusMessage
        println "responseCode:" + response.operationResponseStatus.responseStatusCode
        println "responseMessage:" + response.operationResponseStatus.responseStatusMessage
        /*   good results
        processCode:0
processMessage:None
responseCode:0
responseMessage:Passed
         */
    }

    public static void main(String[] args)
    {
          try
         {
             OneStopInventory.runInventoryUpdate();
         }catch(Exception ex)
             {

                 try{
                 Mailer.sendMail("Generic Import Orders Error for OneStop", ex.getMessage(), "owditadmin@owd.com");
                 }catch(Exception exm)
                 {

                 }
             }
    }
}
