package com.owd.web.api;

import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.hibernate.generated.OwdInventoryAdditionalIds;
import com.owd.hibernate.generated.OwdInventoryRequiredSku;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 6/28/11
 * Time: 10:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class InventoryStatusResponse extends APIResponse

{
private final static Logger log =  LogManager.getLogger();



//XML root name

	public static final String kTagInventoryStatusResponse 	= "OWD_INVENTORY_STATUS_RESPONSE";

    protected static DateFormat df = new SimpleDateFormat("yyyyMMdd");

    private List<OwdInventory> itemList = new ArrayList<OwdInventory>();

    	public InventoryStatusResponse(double api_v)

	{

		super(api_v);

	}

     public void setInventoryList(List<OwdInventory> items)

    {
        if (items != null) {
            itemList = items;

        }


    }
    public String getXML()

       {

           StringBuffer responseBuffer = new StringBuffer();

           responseBuffer.append("<"+kTagInventoryStatusResponse+">");
           responseBuffer.append("<COUNT>" + itemList.size() + "</COUNT>");
           for (OwdInventory item : itemList) {
               responseBuffer.append(getXMLFromItem(item));
           }
           responseBuffer.append("</"+kTagInventoryStatusResponse+">");
           return responseBuffer.toString();


       }


   protected String getXMLFromItem(OwdInventory item) {

       //Take reported Percent from the OwdInventory item and report that as quantity_available
       Integer totalQuantity = item.getOwdInventoryOh().getQtyOnHand();
       Integer reportedQuantity = (int) (totalQuantity * (item.getReportedPercent()/100d));
       if( totalQuantity < 5 && item.getReportedPercent()>0) reportedQuantity = totalQuantity;

        StringBuffer sb = new StringBuffer();
        sb.append("<OWD_INVENTORY_STATUS id=\""+item.getInventoryId()
                +"\" created=\""+df.format(item.getCreatedDate())+"\""
                +" "+API.buildAttribute("createdBy",item.getCreatedBy())
                +" "+API.buildAttribute("sku",item.getInventoryNum())
       +(api_vers<2.0?" "+API.buildAttribute("default_facility_code",item.getDefaultFacilityCode()==null?"DC1":item.getDefaultFacilityCode()):"")
                +" "+API.buildAttribute("quantity_available",""+reportedQuantity)
                +" "+API.buildAttribute("type",(item.getIsAutoInventory()==1?(item.getRequiredSkus().size()>0?("KIT"):("VIRTUAL")):(item.getRequireSerialNumbers()==1?("SERIALIZED"):("PHYSICAL"))))
                + (api_vers>1.9?" "+API.buildAttribute("total_quantity",""+totalQuantity):"")
                +" >");


        sb.append(API.buildTextElement("SUPPLIER",""+item.getMfrPartNum()));
        sb.append(API.buildTextElement("SUPPLIER_SKU",item.getHarmCode()));
        sb.append(API.buildTextElement("KEYWORD",item.getKeyword()));
        sb.append(API.buildTextElement("UNIT_PRICE", "" + OWDUtilities.roundFloat(item.getPrice() == null ? 0.00f : item.getPrice().floatValue())));
     //  log.debug("SUPP:"+item.getSuppCost());
        sb.append(API.buildTextElement("UNIT_COST",""+OWDUtilities.roundFloat(item.getSuppCost()==null?0.00f:item.getSuppCost().floatValue())));
    //   log.debug("DESC"+item.getDescription());
        sb.append(API.buildTextElement("TITLE", item.getDescription()));
    //   log.debug("DESC2"+item.getLongDesc());
        sb.append(API.buildTextElement("DESCRIPTION",item.getLongDesc()));
        sb.append(API.buildTextElement("COLOR",item.getItemColor()));
        sb.append(API.buildTextElement("SIZE",item.getItemSize()));
        sb.append(API.buildTextElement("REORDER_ALERT_QTY",""+item.getQtyReorder()));
       sb.append(API.buildTextElement("WEIGHT",""+OWDUtilities.roundFloat(item.getWeightLbs()==null?0.00f:item.getWeightLbs().floatValue())));
       if(api_vers>2.6) {
           sb.append(API.buildTextElement("LENGTH", "" + OWDUtilities.roundFloat(item.getLength() == null ? 0.00f : item.getLength().floatValue())));
           sb.append(API.buildTextElement("WIDTH", "" + OWDUtilities.roundFloat(item.getWidth() == null ? 0.00f : item.getWidth().floatValue())));
           sb.append(API.buildTextElement("HEIGHT", "" + OWDUtilities.roundFloat(item.getHeight() == null ? 0.00f : item.getHeight().floatValue())));
           sb.append(API.buildTextElement("CUBIC_FEET", "" + OWDUtilities.roundFloat(item.getCubicFeet() == null ? 0.00f : item.getCubicFeet().floatValue())));

           StringBuffer inventory_barcodes = new StringBuffer();
           String upc = item.getUpcCode();
           if(upc != null && !upc.equals("")){
               inventory_barcodes.append(barcodeElement("UPC",upc));
           }
           String isbn = item.getIsbnCode();
           if(isbn != null && !isbn.equals("")){
               inventory_barcodes.append(barcodeElement("ISBN",isbn));
           }
           try {
               Criteria crit = HibernateSession.currentSession().createCriteria(OwdInventoryAdditionalIds.class);
               //          crit.add(Restrictions.eq("name",bc_type));
               crit.add(Restrictions.eq("clientFkey", item.getOwdClient().getClientId()));
               crit.add(Restrictions.eq("inventoryFkey", item.getInventoryId()));
                            /* barcode wuantity not yet enabled  if enabled before 7/17/2021 DNickels Owes EJackman a flat of beer after that date vice versa
                            crit.add(Restrictions.eq("quantity",bc_quantity));
                            */
               List<OwdInventoryAdditionalIds> existing_records = crit.list();
               for(OwdInventoryAdditionalIds barcode:existing_records){
                   inventory_barcodes.append(barcodeElement(barcode.getName(),barcode.getValue()));
               }
           }catch(Exception e){
               e.printStackTrace();
           }
           sb.append("<INVENTORY_BARCODES>"+inventory_barcodes.toString() + "</INVENTORY_BARCODES>");
       } 
        sb.append(API.buildTextElement("CUSTOMS_DESC",""+item.getCustomsDesc()));
        sb.append(API.buildTextElement("CUSTOMS_VALUE",""+ OWDUtilities.roundFloat(item.getCustomsValue())));
        sb.append(API.buildTextElement("ACTIVE",""+(item.isIsActive()?"1":"0")));
       sb.append(API.buildTextElement("NOTES", item.getNotes()));
       sb.append(API.buildTextElement("IMAGE_URL", item.getImageUrl()));
       sb.append(API.buildTextElement("THUMB_URL", item.getImageThumbUrl()));
       sb.append(API.buildTextElement("WEB_URL", item.getCatalogUrl()));
       sb.append(API.buildTextElement("GROUP_NAME", item.getGroupName()==null?"":item.getGroupName()));

       if(api_vers>=2.0)
       {

           sb.append(API.buildTextElement("CASE_QTY",item.getCaseQty()==null?"0":item.getCaseQty()+""));

           sb.append(API.buildTextElement("MASTER_CASE_QTY",item.getMasterCaseQty()==null?"0":item.getMasterCaseQty()+""));
       }



       sb.append(API.buildTextElement("PACKING_INSTRUCTIONS",item.getPackingInstructions()==null?"":item.getPackingInstructions().getInstructions()));

       if(api_vers>=2.0) {
        sb.append(API.buildTextElement("REPORTED_PERCENT",item.getReportedPercent()+""));

       }

    //items
       if(item.getRequiredSkus().size()>0)
       {
           sb.append("<COMPONENTS>");
        for(OwdInventoryRequiredSku reqSku:item.getRequiredSkus())
        {
            sb.append("<COMPONENT>");
          sb.append(API.buildTextElement("COMPONENT_SKU",reqSku.getOwdInventory().getInventoryNum()));
          sb.append(API.buildTextElement("COMPONENT_QTY",reqSku.getQty()+""));
            sb.append("</COMPONENT>");
        }
           sb.append("</COMPONENTS>");
       }

        sb.append("</OWD_INVENTORY_STATUS>");

       log.debug(sb.toString());
        return sb.toString();
    }



    /*

    <OWD_INVENTORY_STATUS_RESPONSE>
	<COUNT>
		1
	</COUNT>
	<OWD_INVENTORY_STATUS       id="8828837" created="20110402" createdBy="bgentle"
								sku="1234A"  quantity_available="3" quantity_unshipped="2"
								quantity_backordered="0" quantity_onhold="0" type="PHYSICAL" >
    <SUPPLIER>Wackenhut</SUPPLIER >
    <SUPPLIER_SKU>XT-KKAIS</SUPPLIER_SKU >
    <KEYWORD></KEYWORD >
    <UNIT_PRICE>47.99</UNIT_PRICE >
    <UNIT_COST>24.00</UNIT_COST >
    <TITLE>Rock, Paper, Scissors, Lizard, Spock - A Strategy Guide</TITLE >
    <DESCRIPTION>Blue jacketed hardcover book with gold lettering</DESCRIPTION >
    <COLOR>Maroon</COLOR >
    <SIZE>XL</SIZE >
    <REORDER_ALERT_QTY>20</REORDER_ALERT_QTY >
    <WEIGHT>1.24</WEIGHT >
    <CUSTOMS_DESC></CUSTOMS_DESC >
    <CUSTOMS_VALUE></CUSTOMS_VALUE >
    <PACKING_INSTRUCTIONS></PACKING_INSTRUCTIONS >
    <HARMONIZED_CODE></HARMONIZED_CODE >
	</OWD_INVENTORY_STATUS>
</OWD_INVENTORY_STATUS_RESPONSE>
     */

    private String barcodeElement(String type, String value){
        return "<BARCODE>" +
                API.buildTextElement("TYPE", type) +
                API.buildTextElement("VALUE",value) +
                "</BARCODE>";
    }
}
