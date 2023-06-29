package com.owd.jobs.jobobjects.RetailPro.models

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities
import com.owd.hibernate.HibernateSession
import com.owd.jobs.jobobjects.RetailPro.TransferUtilities

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 1/20/12
 * Time: 1:08 PM
 * To change this template use File | Settings | File Templates.
 */
class MemoAdjustment {
    private final static Logger log =  LogManager.getLogger();

    List<AdjItem> adjustItems = new ArrayList<AdjItem>()
    String adjustReason = ""


    public static void main(String[] args) throws Exception {

        println OWDUtilities.encryptData("500")
    }

    public void addAdjustItem(String sku, int qty) {
        AdjItem item = new AdjItem()
        item.setSku(sku)
        item.setQuantity(qty)
        adjustItems.add(item)
    }

    public String getMemoAdjustmentXML(List<String> rproSkus) {
        /*
        <xsd:schema xmlns:xsd="http://www.w3.org/2001/XMLSchema">
  <xsd:element name="ADJUSTMENTS">
    <xsd:complexType>
      <xsd:sequence>
        <xsd:element name="ADJUSTMENT" type="ADJUSTMENT" minOccurs="0" maxOccurs="unbounded"/>
      </xsd:sequence>
    </xsd:complexType>
  </xsd:element>
  <xsd:complexType name="ADJUSTMENT">
    <xsd:sequence>
      <xsd:element name="ADJ_ITEMS">
        <xsd:complexType>
          <xsd:sequence>
            <xsd:element name="ADJ_ITEM" minOccurs="0" maxOccurs="unbounded">
              <xsd:complexType>
                <xsd:attribute name="ECommId" type="xsd:string"/>
                <xsd:attribute name="ItemNum" type="integerNullable"/>
                <xsd:attribute name="qty" type="xsd:double"/>
              </xsd:complexType>
            </xsd:element>
          </xsd:sequence>
        </xsd:complexType>
      </xsd:element>
    </xsd:sequence>
    <xsd:attribute name="store_no" type="integerNullable"/>
    <xsd:attribute name="adj_no" type="integerNullable"/>
    <xsd:attribute name="status" type="integerNullable"/>
    <xsd:attribute name="adj_reason" type="xsd:string"/>
    <xsd:attribute name="created_date" type="dateTimeNullable" use="required"/>
  </xsd:complexType>
  <xsd:simpleType name="integerNullable">
    <xsd:union memberTypes="xsd:integer emptyType"/>
  </xsd:simpleType>
  <xsd:simpleType name="doubleNullable">
    <xsd:union memberTypes="xsd:double emptyType"/>
  </xsd:simpleType>
  <xsd:simpleType name="dateNullable">
    <xsd:union memberTypes="xsd:date emptyType"/>
  </xsd:simpleType>
  <xsd:simpleType name="dateTimeNullable">
    <xsd:union memberTypes="xsd:dateTime emptyType"/>
  </xsd:simpleType>
  <xsd:simpleType name="hexBinaryNullable">
    <xsd:union memberTypes="xsd:hexBinary emptyType"/>
  </xsd:simpleType>
  <xsd:simpleType name="emptyType">
    <xsd:restriction base="xsd:string">
      <xsd:enumeration value=""/>
    </xsd:restriction>
  </xsd:simpleType>
</xsd:schema>
         */
        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def createAsnRequest =
        {
            mkp.xmlDeclaration()
            ADJUSTMENTS()
                    {
                        ADJUSTMENT(store_no: 'OAK', adj_no: '', status: '', adj_reason: adjustReason, created_date: '')
                                {
                                    ADJ_ITEMS()
                                            {
                                                for (AdjItem item: adjustItems) {
                                                    if(rproSkus.contains(item.sku))
                                                    {
                                                        ADJ_ITEM(ECommId: item.sku, ItemNum: '', qty: item.quantity)
                                                    }
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



    public class AdjItem {
private final static Logger log =  LogManager.getLogger();
        String sku
        String externalSku
        int quantity

    }

public static void exportCurrentInventoryOnHand(List<String> rproSkus) throws Exception
    {
        MemoAdjustment ma = new MemoAdjustment();
               ma.setAdjustReason("sync");
          List<Object[]> items = HibernateSession.currentSession().createSQLQuery("\n" +
                "(select case when inventory_num like '%/KIT' then substring(inventory_num,1,len(inventory_num)-4) else inventory_num end as sku,\n" +
                "sum(ISNULL(qty_on_hand,0)+ISNULL(unshipped,0)) as onhand\n" +
                "from owd_inventory i join owd_inventory_oh h on h.inventory_fkey=i.inventory_id left outer join \n" +
                "(select inventory_id, sum(quantity_actual) as unshipped from owd_line_item l join owd_order o \n" +
                "on order_id=order_fkey and order_status='At Warehouse' and client_fkey=482 group by inventory_id)\n" +
                "as pending on pending.inventory_id=i.inventory_id\n" +
                "where client_fkey=482 and is_auto_inventory=0\n" +
                "group by case when inventory_num like '%/KIT' then substring(inventory_num,1,len(inventory_num)-4) else inventory_num end\n" +
                ") union (\n" +
                "select inventory_num as sku,0 as onhand\n" +
                "from owd_inventory i join owd_inventory_oh h on h.inventory_fkey=i.inventory_id left outer join \n" +
                "(select inventory_id, sum(quantity_actual) as unshipped from owd_line_item l join owd_order o \n" +
                "on order_id=order_fkey and order_status='At Warehouse' and client_fkey=482 group by inventory_id)\n" +
                "as pending on pending.inventory_id=i.inventory_id\n" +
                "where client_fkey=482 and is_auto_inventory=1 and inventory_num+'/KIT' not in (select inventory_num from owd_inventory where client_fkey=482 and inventory_num like '%/KIT')\n" +
                "group by inventory_num\n" +
                ")").list();

       for(Object[] item:items)
       {
           log.debug(item[0]);
           ma.addAdjustItem(""+item[0], Integer.parseInt(""+item[1]));
       }
              //  ma.addAdjustItem("01 946 00", 2);
              //  ma.addAdjustItem("01 945 00",0);

                TransferUtilities.putRetailProFile("MEMO_"+ Calendar.getInstance().getTime().getTime()+".xml",ma.getMemoAdjustmentXML(rproSkus),true);




    }

}
