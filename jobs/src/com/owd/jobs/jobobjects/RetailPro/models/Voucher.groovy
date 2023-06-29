package com.owd.jobs.jobobjects.RetailPro.models

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.jobs.jobobjects.RetailPro.DateToXsdDatetimeFormatter

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 1/20/12
 * Time: 1:47 PM
 * To change this template use File | Settings | File Templates.
 */
class Voucher {
      List<VouItem> voucherItems = new ArrayList<VouItem>()
    String poNumber = ""
    String type = ""
    String id = null
    String notes = ""

    public static String kVoucherReturnType = "RETURN"
    public static String kVoucherReceiveType = "RECEIVE"



    public static void main(String[] args) throws Exception {

    }

    public void addVoucherItem(String sku, int qty) {
        VouItem item = new VouItem()
        item.setSku(sku)
        item.setQuantity(Math.abs(qty))
        voucherItems.add(item)
    }

    public String getVoucherXML() {
 /*
    <VOUCHERS>
  <VOUCHER document_date="2011-12-04T14:14:59" po_no="1" store_no="000" >
    <VOU_ITEMS>
      <VOU_ITEM ECommId="1234" ItemNumber="13" qty="2" />
    </VOU_ITEMS>
  </VOUCHER>
  <VOUCHER document_date="2011-12-04T14:14:59" po_no="3" store_no="000" >
    <VOU_ITEMS>
      <VOU_ITEM ECommId="1234" ItemNumber="1" qty="2" />
    </VOU_ITEMS>
  </VOUCHER>
</VOUCHERS>
     */
        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def createAsnRequest =
        {
            mkp.xmlDeclaration()
            VOUCHERS()
                    {
                        VOUCHER(store_no: 'OAK', voucher_type:type, po_no: poNumber, notes:notes, document_date: DateToXsdDatetimeFormatter.format(Calendar.getInstance().getTime()))
                                {
                                    VOU_ITEMS()
                                            {
                                                for (VouItem item: voucherItems) {
                                                    VOU_ITEM(ECommId: item.sku, ItemNumber: '', qty: item.quantity)
                                                }
                                            }
                                }
                    }

        }


        println "sending"
        String xml = builder.bind(createAsnRequest).toString()
      //  println xml
        return xml

    }



    public class VouItem {
private final static Logger log =  LogManager.getLogger();
        String sku
        String externalSku
        int quantity

    }

}
