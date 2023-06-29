package com.owd.web.api;





import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.*;
import com.owd.core.business.order.*;
import com.owd.core.OWDUtilities;
import com.owd.core.business.order.Package;
import com.owd.core.csXml.OrderRater;

import java.util.ArrayList;
import java.util.List;





public class OrderSummaryResponse extends APIResponse

{
private final static Logger log =  LogManager.getLogger();



//XML root name

	public static final String kRootTag 	= "OWD_ORDER_SUMMARY_RESPONSE";

	

//XML attribute names


	public static final String kAttClientOrderId 	= "clientOrderId";

	public static final String kAttActive 	= "active";

	

//XML line summary element name

	public static final String kTagLine 	= "LINE_SUMMARY";

//XML line summary attribute names

	public static final String kAttSku 	= "clientSKU";

	public static final String kAttRequested 	= "requested";

	public static final String kAttShipped 	= "shipped";

	public static final String kAttBackordered 	= "backordered";
	public static final String kAttCancelled 	= "cancelled";

	public static final String kAttHeld 	= "held";



//XML shipment element name

	public static final String kTagShipment 	= "SHIPMENT";

//XML shipment attribute names

	public static final String kShipDate 	= "shipDate";

	public static final String kShipVia 	= "shipVia";

	public static final String kShipViaCode 	= "shipViaCode";

//XML package element name

	public static final String kTagPackage 	= "PACKAGE";

//XML package attribute names

	public static final String kAttPackageNum 	= "packageNumber";

	public static final String kAttTrackingNum 	= "trackingNumber";

	public static final String kAttRatedCost 	= "ratedCost";

	public static final String kAttWeightLbs 	= "weightLbs";

//XML item element name

	public static final String kTagItem 	= "ITEM";

//XML item attribute names

	public static final String kAttClientSKU 	= "clientSKU";

	public static final String kAttQuantity 	= "quantity";
	public static final String kTagItemLots = "ITEM_LOTS";
	public static final String kTagLot = "LOT";
	public static final String kTagLotValue = "LOT_VALUE";
	public static final String kTagQty = "QTY";

	OrderStatus osx = null;
	
	List orderIDs = null;

	public OrderSummaryResponse(double api_v)

	{

		super(api_v);

	}	

		public void buildFromOrderStatus(OrderStatus ostatus)

	{

		

		osx = ostatus;

	

	}

List summaryList = new ArrayList();

	LineSummary getLineSummaryForItem(String inventoryNum)
	{
		if (inventoryNum==null) inventoryNum = "";
		
		for (int i=0;i<summaryList.size();i++)
		{
			if(((LineSummary)summaryList.get(i)).clientSKU.equals(inventoryNum))
			{
				return (LineSummary)summaryList.get(i);
			}
		}
		
		LineSummary summary = new LineSummary();
		summary.clientSKU=inventoryNum;
		summaryList.add(summary);
		return summary;
	
	}
	public String getXMLFromStatus()

	{

StringBuffer sb = new StringBuffer();

summaryList = new ArrayList();

StringBuffer shipmentData = new StringBuffer();

OrderStatus currentOS = osx;
boolean isActive = false;

while (currentOS != null)
{
	
		//update line summaries...
		
		for (int i=0;i<currentOS.items.size();i++)
		{
			LineItem item = (LineItem) currentOS.items.get(i);
			LineSummary summary = getLineSummaryForItem(item.client_part_no);
			if(currentOS.is_void)
			{

				summary.cancelled += item.quantity_request;
				summary.requested += item.quantity_request;

			}else if(currentOS.is_shipped)
			{

				summary.shipped += item.quantity_actual;
				summary.requested += item.quantity_actual;
				
		
			
			}else if(currentOS.is_on_hold)
			{

				summary.held += item.quantity_request;	
				
				summary.requested += item.quantity_request;	

			}else if(currentOS.is_posted)
			{
			
				summary.requested += item.quantity_request;
						
			}else if(currentOS.isBackorder)
			{
				summary.backordered += item.quantity_request;	
				
				summary.requested += item.quantity_request;	
			}
				
		}
		
		
		if(currentOS.is_void)
		{



		}else if(currentOS.is_shipped)
		{

					shipmentData.append("<"+kTagShipment+" ");

			

			

			shipmentData.append(API.buildAttribute(kShipDate,OWDUtilities.getYYYYMMDDFromSQLDate(((com.owd.core.business.order.Package)currentOS.packages.elementAt(0)).ship_date))+" ");

			shipmentData.append(API.buildAttribute(kShipVia,currentOS.shipping.carr_service));

		

				shipmentData.append(" "+API.buildAttribute(kShipViaCode,OrderRater.getSafeServiceCode(currentOS.shipping.carr_service_ref_num))+" >");

			

			for(int i=0;i<currentOS.packages.size();i++)

			{

				if(((com.owd.core.business.order.Package)currentOS.packages.elementAt(i)).is_void.equals("0"))

				{

				shipmentData.append("<"+kTagPackage+" "

				+API.buildAttribute(kAttPackageNum,((com.owd.core.business.order.Package)currentOS.packages.elementAt(i)).line_index)+" ");
				if(((com.owd.core.business.order.Package)currentOS.packages.elementAt(i)).tracking_no.length()>9)

				{

					shipmentData.append(API.buildAttribute(kAttTrackingNum,((com.owd.core.business.order.Package)currentOS.packages.elementAt(i)).tracking_no+""));

				}

						shipmentData.append(" "+API.buildAttribute(kAttRatedCost,((Package)currentOS.packages.elementAt(i)).total_billed+""));

                        List<Integer> dims = Package.getHwdDimensionsOfPackage(new Integer(((com.owd.core.business.order.Package) currentOS.packages.elementAt(i)).order_track_id).intValue());

                        shipmentData.append(" "+API.buildAttribute("height_in",dims.get(0)+""));
                        shipmentData.append(" "+API.buildAttribute("width_in",dims.get(1)+""));
                        shipmentData.append(" "+API.buildAttribute("depth_in",dims.get(2)+""));

						shipmentData.append(" "+API.buildAttribute(kAttWeightLbs,((com.owd.core.business.order.Package)currentOS.packages.elementAt(i)).weight+"")+" />");

					

				}
				}
				for (int i=0;i<currentOS.items.size();i++)
		{
			LineItem item = (LineItem) currentOS.items.get(i);

				if(item.quantity_actual>0)

				{
					String sku = item.client_part_no;
				shipmentData.append("<"+kTagItem+" "

				+API.buildAttribute(kAttClientSKU,("489".equals(currentOS.client_id)||"491".equals(currentOS.client_id))?sku.startsWith("DS-")?sku.replaceAll("DS-", ""):sku:sku)+" "
				+API.buildAttribute(kAttQuantity,item.quantity_actual+"")+" ");

			

						shipmentData.append(" />");

					}


			}
			
			

			shipmentData.append("</"+kTagShipment+">");


		
		}else if(currentOS.is_on_hold)
		{

		isActive = true;		

		}else if(currentOS.is_posted)
		{
		isActive = true;		
		}else if(currentOS.isBackorder)
		{
		isActive = true;		
		}
		
		
		if(currentOS.backorder != null)
		{
			currentOS = currentOS.backorder;
		}else
		{
			currentOS = null;
		}


}
		
          if("117".equals(osx.client_id) && osx.orderReference.startsWith("DDS"))
        {
            osx.orderReference = osx.orderReference.substring(3);
        }
		sb.append("<"+kRootTag+" "

				+API.buildAttribute(kAttClientOrderId,osx.orderReference)+" "
				+API.buildAttribute(kAttActive,(isActive?"TRUE":"FALSE")));
				


		

		sb.append(" >"	);			

		for(int i=0;i<summaryList.size();i++)

		{

			sb.append("<"+kTagLine+" "

				+API.buildAttribute(kAttSku,((LineSummary)summaryList.get(i)).clientSKU)+" "

				+API.buildAttribute(kAttRequested,((LineSummary)summaryList.get(i)).requested+"")+" "
				+API.buildAttribute(kAttShipped,((LineSummary)summaryList.get(i)).shipped+"")+" "
				+API.buildAttribute(kAttHeld,((LineSummary)summaryList.get(i)).held+"")+" "

				+API.buildAttribute(kAttBackordered,((LineSummary)summaryList.get(i)).backordered+"")+" "

				+API.buildAttribute(kAttCancelled,((LineSummary)summaryList.get(i)).cancelled+"")+" />");

		}

		

		sb.append(shipmentData.toString());
		

		sb.append("</"+kRootTag+">");

			

		return sb.toString();	}



	public String getXML()

	{


			return getXMLFromStatus();

	}
	
	
	public class LineSummary{
	
		String clientSKU = "";
		int requested = 0;
		int backordered = 0;
		int cancelled=0;
		int shipped=0;
		int held=0;
		
		
	
	}



}
