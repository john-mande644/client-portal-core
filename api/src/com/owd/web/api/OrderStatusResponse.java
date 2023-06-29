package com.owd.web.api;





import com.owd.core.business.order.beans.lineItemPackageData;
import com.owd.core.business.order.beans.lotData;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.order.*;
import com.owd.core.OWDUtilities;
import com.owd.core.csXml.OrderRater;
import com.owd.core.business.order.OrderStatus;
import com.owd.core.business.order.Package;
import org.apache.commons.lang.StringEscapeUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.Iterator;
import java.util.Set;


public class OrderStatusResponse extends APIResponse

{
private final static Logger log =  LogManager.getLogger();



//XML root name

    public static final String kRootTag 	= "OWD_ORDER_STATUS_RESPONSE";



//XML attribute names

    public static final String kAttorderId 	= "orderId";

    public static final String kAttclientOrderId 	= "clientOrderId";

    public static final String kAttbackorderOrderId 	= "backorderOrderId";

    public static final String kAttbackorderedFromOrderId 	= "backorderedFromOrderId";

    public static final String kAttstatus 	= "status";

    public static final String kAttGroupName 	= "groupName";

    public static final String kAttstoppedAtStatus 	= "stoppedAtStatus";

    public static final String kWarehouseStatus 	= "warehouseStatus";

    public static final String kAttpaymentStatus 	= "paymentStatus";

    public static final String kAttshipDateExpected	= "shipDateExpected";

    public static final String kAttShipMethodSelected	= "shipMethod";


    public static final String kAttShipMethodCodeSelected	= "shipMethodCode";


//XML line item element name

    public static final String kTagLine 	= "LINE";

//XML line item attribute names

    public static final String kAttSku 	= "clientSKU";

    public static final String kAttRequested 	= "requested";

    public static final String kAttAssigned 	= "assigned";
    public static final String kAttCost         ="cost";
    public static final String kAttBackordered 	= "backordered";
    public static final String kAttClientLineRef 	= "line_number";



//XML shipment item element name

    public static final String kTagShipment 	= "SHIPMENT";

//XML shipment item attribute names

    public static final String kShipDate 	= "shipDate";

    public static final String kShipVia 	= "shipVia";

    public static final String kShipViaCode 	= "shipViaCode";

//XML package item element name

    public static final String kTagPackage 	= "PACKAGE";

//XML package item attribute names

    public static final String kAttPackageNum 	= "packageNumber";

    public static final String kAttTrackingNum 	= "trackingNumber";

    public static final String kAttRatedCost 	= "ratedCost";

    public static final String kAttWeightLbs 	= "weightLbs";



    Order order = null;

    OrderStatus os = null;
    OwdOrder owdorder = null;


    List orderIDs = null;

    boolean showOrderDetails = true;

    public OrderStatusResponse(double api_v)

    {


        super(api_v);

    }

    public void buildFromOrderStatus(OrderStatus ostatus)

    {



        os = ostatus;



    }

    public void buildFromOrder(Order o)

    {



        order = o;



    }

    public void buildFromOwdOrder(OwdOrder o)

    {



        owdorder = o;



    }


    public void buildFromOrderIDs(List ids)

    {



        orderIDs = ids;



    }


    public String getXMLFromOwdOrder()

    {

        StringBuffer sb = new StringBuffer();

        String wstatus = OrderUtilities.getWarehouseStatusString(owdorder.getOrderId());
        //checking for flatrate method and reporting usedMethod
        if(owdorder.getShipinfo().getCarrServiceRefNum().contains("COM_OWD_FLATRATE")){
            log.debug("We need to decode flat rate shipping");
            try{
                Criteria crit = HibernateSession.currentSession().createCriteria(OwdOrderTrack.class);
                crit.add(Restrictions.eq("orderFkey", owdorder.getOrderId()));
                crit.add(Restrictions.eq("isVoid",0));
                List<OwdOrderTrack> owdOrderTracks = crit.list();
                if(owdOrderTracks.size()>0){
                    if(null!=owdOrderTracks.get(0).getServiceCode()) {
                        owdorder.getShipinfo().setCarrServiceRefNum(owdOrderTracks.get(0).getServiceCode());
                        Criteria crit2 = HibernateSession.currentSession().createCriteria(OwdShipMethod.class);
                        crit2.add(Restrictions.eq("methodCode", owdorder.getShipinfo().getCarrServiceRefNum()));
                        List<OwdShipMethod> methods = crit2.list();
                        owdorder.getShipinfo().setCarrService(methods.get(0).getMethodName());
                        log.debug(owdorder.getShipinfo().getCarrService());
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }


        }

        sb.append("<"+kRootTag+" "

                +API.buildAttribute(kAttorderId,owdorder.getOrderNum())+" "

                +API.buildAttribute(kAttclientOrderId,owdorder.getOrderRefnum())+" ");



        if(owdorder.getBackorderOrderNum()!= null)

            sb.append(API.buildAttribute(kAttbackorderOrderId,owdorder.getBackorderOrderNum())+" ");

        if(!("".equals(owdorder.getOriginalOrderNum())))
            sb.append(API.buildAttribute(kAttbackorderedFromOrderId,owdorder.getOriginalOrderNum())+" ");


        sb.append(API.buildAttribute(kAttGroupName,owdorder.getGroupName()==null?"":owdorder.getGroupName())+" ");

        if(api_vers>=2.0)
        {

            sb.append(API.buildAttribute("shipFromPolicy",owdorder.getFacilityPolicy()==null?"":owdorder.getFacilityPolicy())+" ");

        }

        if(owdorder.getIsVoid()==1)

        {



            sb.append(API.buildAttribute(kAttstatus,"VOID")+" ");

        }else

        {

            if(owdorder.getIsFutureShip()==1)

            {

                sb.append(API.buildAttribute(kAttstatus,"STOPPED")+" ");
                if(owdorder.isIsBackorder())
                {
                    sb.append(API.buildAttribute(kAttstoppedAtStatus,"BACKORDER")+" ");

                }else
                    sb.append(API.buildAttribute(kAttstoppedAtStatus,"RECEIVED")+" ");

            }else{

                if(owdorder.getPostDate()!=null)

                {

                    if(owdorder.getShippedDate()!=null) {
                        sb.append(API.buildAttribute(kAttstatus, "SHIPPED") + " ");
                    }
                    else {
                        sb.append(API.buildAttribute(kAttstatus, "WAREHOUSE") + " ");
                    }
                    if(api_vers>=2.0)
                    {

                       // String whstatus =  owdorder.getPickStatus()==0?"PENDING":owdorder.getPickStatus()==1?"PICKING":"PICKED";

                        sb.append(API.buildAttribute(kWarehouseStatus,wstatus)+" ");

                    }

                }else

                {
                    if(owdorder.isIsBackorder())
                    {
                        sb.append(API.buildAttribute(kAttstatus,"BACKORDER")+" ");

                    }else {
                        sb.append(API.buildAttribute(kAttstatus, "RECEIVED") + " ");
                    }
                }

            }

        }

        if(owdorder.getCcType().equals("CC")||owdorder.getCcType().equals("AMEX")
                ||owdorder.getCcType().equals("DISC")
                ||owdorder.getCcType().equals("ENRO")
                ||owdorder.getCcType().equals("JCB ")
                ||owdorder.getCcType().equals("MAST")
                ||owdorder.getCcType().equals("VISA")
                ||owdorder.getCcType().equals("DCCB") )
        {
            if(owdorder.getOrderBalance().floatValue() > 1)
            {
                sb.append(API.buildAttribute(kAttpaymentStatus,"CCREJECTED")+" ");
            }else
            {
                sb.append(API.buildAttribute(kAttpaymentStatus,"PAID")+" ");
            }

        }else if(owdorder.getCcType().equals("CK") )
        {
            if(owdorder.getOrderBalance().floatValue() > 1)
            {
                sb.append(API.buildAttribute(kAttpaymentStatus,"WAITINGCK")+" ");
            }else
            {
                sb.append(API.buildAttribute(kAttpaymentStatus,"PAID")+" ");
            }
        }else
        {
            sb.append(API.buildAttribute(kAttpaymentStatus,"CLIENTMANAGED")+" ");
        }

        if(!(owdorder.getShippedDate()!=null) && !(owdorder.getIsFutureShip()==1) && (owdorder.getPostDate()!=null))
            sb.append(API.buildAttribute(kAttshipDateExpected,OWDUtilities.getYYYYMMDDDateForToday())+" ");


        sb.append(API.buildAttribute(kAttShipMethodSelected,owdorder.getShipinfo().getCarrService())+" ");
        sb.append(API.buildAttribute(kAttShipMethodCodeSelected,OrderRater.getSafeServiceCode(owdorder.getShipinfo().getCarrServiceRefNum()))+" ");



        sb.append(">"	);

        for(OwdLineItem line:owdorder.getLineitems())

        {

            sb.append("<"+kTagLine+" "

                    +API.buildAttribute(kAttSku,(owdorder.getClientFkey()==489||owdorder.getClientFkey()==491)?line.getInventoryNum().startsWith("DS-")?line.getInventoryNum().replaceAll("DS-", ""):line.getInventoryNum():line.getInventoryNum())+" "

                    +API.buildAttribute(kAttRequested,line.getQuantityRequest()+"")+" "

                    +API.buildAttribute(kAttAssigned,line.getQuantityActual()+"")+" "

                    +API.buildAttribute(kAttBackordered,line.getQuantityBack()+"")+" "
                    +API.buildAttribute(kAttClientLineRef,line.getCustRefnum()+"")+" "
                    +API.buildAttribute(kAttCost,OWDUtilities.roundFloat(line.getPrice().floatValue()) + "")
                    +" >");

            try
            {
                log.debug("checking serials");

                        Set<OwdInventorySerial> serials = line.getSerials();

                        for(OwdInventorySerial serial:serials)
                        {
                            log.debug("appending serial");
                            sb.append(API.buildTextElement("SERIAL",serial.getSerialNumber()));

                        }


            }catch(Exception ex)
            {
                ex.printStackTrace();
            }
            sb.append("</"+kTagLine+">");
        }




        if(owdorder.getShippedDate()!=null)

        {

            sb.append("<"+kTagShipment+" ");
            OrderStatus tempos = new OrderStatus(""+owdorder.getOrderId());





            sb.append(API.buildAttribute(kShipDate,OWDUtilities.getYYYYMMDDFromSQLDate(((com.owd.core.business.order.Package)tempos.packages.elementAt(0)).ship_date))+" ");

            sb.append(API.buildAttribute(kShipVia,owdorder.getShipinfo().getCarrService())+" ");
            if(api_vers>=2.0)
            {

                sb.append(API.buildAttribute("shippedFromFacilityCode",(owdorder.getFacilityCode()==null?"":owdorder.getFacilityCode()))+" ");

            }
            if(api_vers >= 1.2)

            {

                sb.append(" "+API.buildAttribute(kShipViaCode,OrderRater.getSafeServiceCode(owdorder.getShipinfo().getCarrServiceRefNum()))+" >");

            }else

            {

                sb.append(" >");

            }



            for(int i=0;i<tempos.packages.size();i++)

            {

                if(((com.owd.core.business.order.Package)tempos.packages.elementAt(i)).is_void.equals("0"))

                {

                    sb.append("<"+kTagPackage+" "

                            +API.buildAttribute(kAttPackageNum,((com.owd.core.business.order.Package)tempos.packages.elementAt(i)).line_index)+" ");

                    if(((com.owd.core.business.order.Package)tempos.packages.elementAt(i)).tracking_no.length()>=8)

                    {

                        sb.append(API.buildAttribute(kAttTrackingNum,((com.owd.core.business.order.Package)tempos.packages.elementAt(i)).tracking_no+""));

                    }

                    if(api_vers >= 1.2)

                    {

                        sb.append(" "+API.buildAttribute(kAttRatedCost,((com.owd.core.business.order.Package)tempos.packages.elementAt(i)).total_billed+""));

                        sb.append(" "+API.buildAttribute(kAttWeightLbs,((com.owd.core.business.order.Package)tempos.packages.elementAt(i)).weight+""));

                        List<Integer> dims = Package.getHwdDimensionsOfPackage(new Integer(((com.owd.core.business.order.Package)tempos.packages.elementAt(i)).order_track_id).intValue());

                        sb.append(" "+API.buildAttribute("height_in",dims.get(0)+""));
                        sb.append(" "+API.buildAttribute("width_in",dims.get(1)+""));
                        sb.append(" "+API.buildAttribute("depth_in",dims.get(2)+""));

                        sb.append(" >");

                        /*      if(i==0)
             {
                for (int ix=0;ix<os.items.size();ix++)
                    {
                        LineItem item = (LineItem) os.items.get(ix);

                            if(item.quantity_actual>0)

                            {

                             sb.append("<"+OrderSummaryResponse.kTagItem+" "
        +API.buildAttribute(OrderSummaryResponse.kAttClientSKU,item.client_part_no)+" "
        +API.buildAttribute(OrderSummaryResponse.kAttQuantity,item.quantity_actual+"")+" ");



                sb.append(" />");

                            }
                    }


                        }*/

                        //removed pending pack-line item foreign key fix in place

                       List<lineItemPackageData> lines = Package.getLineItemDataMapforPackage(new Integer(((com.owd.core.business.order.Package)tempos.packages.elementAt(i)).order_track_id).intValue());

                        for (lineItemPackageData data:lines)
                        {
                            String sku =  data.getSku();

                            sb.append("<"+OrderSummaryResponse.kTagItem+" "
                                    +API.buildAttribute(OrderSummaryResponse.kAttClientSKU,("489".equals(tempos.client_id)||"491".equals(tempos.client_id))?sku.startsWith("DS-")?sku.replaceAll("DS-", ""):sku:sku)+" "
                                    +API.buildAttribute(OrderSummaryResponse.kAttQuantity,data.getPackageQty()+"")+" ");

                            if(data.getLots().size()>0&&api_vers>=2.2){
                               //do the lots for the item
                                sb.append(">");
                                sb.append("<"+OrderSummaryResponse.kTagItemLots +">");
                                for(lotData ld:data.getLots()){
                                    sb.append("<"+OrderSummaryResponse.kTagLot+">");
                                    sb.append("<"+OrderSummaryResponse.kTagLotValue+">");
                                    sb.append(ld.getLotValue());
                                    sb.append("</"+OrderSummaryResponse.kTagLotValue+">");
                                    sb.append("<"+OrderSummaryResponse.kTagQty+">");
                                    sb.append(ld.getLotQty());
                                    sb.append("</"+OrderSummaryResponse.kTagQty+">");
                                    sb.append("</"+OrderSummaryResponse.kTagLot+">");
                                }
                                sb.append("</"+OrderSummaryResponse.kTagItemLots+">");
                                sb.append("</ITEM>");
                            }else {

                                sb.append(" />");
                            }



                        }
                        sb.append("</PACKAGE>");

                    }else

                    {

                        sb.append(" />");

                    }

                }

            }



            sb.append("</"+kTagShipment+">");

        }


        if(showOrderDetails)
        {
            sb.append("<FIELDS>");

            sb.append(API.buildTextElement("BILL_ADDRESS_ONE",owdorder.getBillAddressOne()));
            sb.append(API.buildTextElement("BILL_ADDRESS_TWO",owdorder.getBillAddressTwo()));
            sb.append(API.buildTextElement("BILL_CITY",owdorder.getBillCity()));
            sb.append(API.buildTextElement("BILL_COMPANY",owdorder.getBillCompanyName()));
            sb.append(API.buildTextElement("BILL_COUNTRY",owdorder.getBillCountry()));
            sb.append(API.buildTextElement("BILL_EMAIL",owdorder.getBillEmailAddress()));
            sb.append(API.buildTextElement("BILL_NAME",owdorder.getBillFirstName()+" "+owdorder.getBillLastName()));
            sb.append(API.buildTextElement("BILL_PHONE",owdorder.getBillPhoneNum()));
            sb.append(API.buildTextElement("BILL_PO",owdorder.getPoNum()));
            sb.append(API.buildTextElement("BILL_POSTCODE",owdorder.getBillZip()));
            sb.append(API.buildTextElement("BILL_STATE",owdorder.getBillState()));
            sb.append(API.buildTextElement("FACILITY_RULE",api_vers>=2.0?"":""));
            sb.append(API.buildTextElement("GROUP_NAME",owdorder.getGroupName()));
            sb.append(API.buildTextElement("ORDER_COMMENT",owdorder.getShipinfo().getComments()));
            sb.append(API.buildTextElement("ORDER_WAREHOUSENOTES",owdorder.getShipinfo().getWhseNotes()));
            sb.append(API.buildTextElement("SHIP_ADDRESS_ONE",owdorder.getShipinfo().getShipAddressOne()));
            sb.append(API.buildTextElement("SHIP_ADDRESS_TWO",owdorder.getShipinfo().getShipAddressTwo()));
            sb.append(API.buildTextElement("SHIP_CITY",owdorder.getShipinfo().getShipCity()));
            sb.append(API.buildTextElement("SHIP_COMPANY",owdorder.getShipinfo().getShipCompanyName()));
            sb.append(API.buildTextElement("SHIP_COUNTRY",owdorder.getShipinfo().getShipCountry()));
            sb.append(API.buildTextElement("SHIP_EMAIL",owdorder.getShipinfo().getShipEmailAddress()));
            sb.append(API.buildTextElement("SHIP_NAME",owdorder.getShipinfo().getShipFirstName()+" "+owdorder.getShipinfo().getShipLastName()));
            sb.append(API.buildTextElement("SHIP_PHONE",owdorder.getShipinfo().getShipPhoneNum()));
            sb.append(API.buildTextElement("SHIP_POSTCODE",owdorder.getShipinfo().getShipZip()));
            sb.append(API.buildTextElement("SHIP_STATE",owdorder.getShipinfo().getShipState()));
            sb.append(API.buildTextElement("SHIPPING_METHOD",owdorder.getShipinfo().getCarrService()));
            sb.append(API.buildTextElement("TOTAL_DISCOUNT",""+ OWDUtilities.roundFloat(owdorder.getDiscount().floatValue())));
            sb.append(API.buildTextElement("TOTAL_SHIPHANDLING",""+OWDUtilities.roundFloat(owdorder.getShipHandlingFee().floatValue())));
            sb.append(API.buildTextElement("TOTAL_TAX",""+OWDUtilities.roundFloat(owdorder.getTaxAmount().floatValue())));
            sb.append(API.buildTextElement("TOTAL_COST",""+OWDUtilities.roundFloat(owdorder.getOrderTotal().floatValue())));
            sb.append(API.buildTextElement("PACKING_INSTRUCTIONS",owdorder.getPackingInstructions()==null?"":owdorder.getPackingInstructions().toString()));
            sb.append("<ORDER_VIEW_URL><![CDATA[https://service.owd.com/webapps/extranet/extranet.jsp?act=cngMgr&mgr=Orders&ordermgr=edit&oid="+owdorder.getOrderId()+"]]></ORDER_VIEW_URL>");

            sb.append("</FIELDS>");
        }

        if(api_vers>=2.1) {

            for(OrderTag tag:owdorder.getTags())
            {
                sb.append("<CUSTOM_VALUE "+API.buildAttribute("name",tag.getTagName())+">"+ StringEscapeUtils.escapeXml(tag.getTagValue())+"</CUSTOM_VALUE>");
            }
        }

        sb.append("</"+kRootTag+">");



        return sb.toString();	}

    public String getXMLFromOrder()

    {



        StringBuffer sb = new StringBuffer();

        String wstatus = OrderUtilities.getWarehouseStatusString(Integer.parseInt(order.orderID));

        if(order.getShippingInfo().carr_service_ref_num.contains("COM_OWD_FLATRATE")){
            log.debug("We need to decode flat rate shipping");
            try{
                Criteria crit = HibernateSession.currentSession().createCriteria(OwdOrderTrack.class);
                crit.add(Restrictions.eq("orderFkey", Integer.parseInt(order.orderID)));
                crit.add(Restrictions.eq("isVoid",0));
                List<OwdOrderTrack> owdOrderTracks = crit.list();
                if(owdOrderTracks.size()>0){
                    if(null!=owdOrderTracks.get(0).getServiceCode()) {
                        order.getShippingInfo().carr_service_ref_num = (owdOrderTracks.get(0).getServiceCode());
                        Criteria crit2 = HibernateSession.currentSession().createCriteria(OwdShipMethod.class);
                        crit2.add(Restrictions.eq("methodCode", order.getShippingInfo().carr_service_ref_num));
                        List<OwdShipMethod> methods = crit2.list();
                        order.getShippingInfo().carr_service = (methods.get(0).getMethodName());
                        log.debug(order.getShippingInfo().carr_service);
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }


        }



        if("117".equals(order.clientID) && order.order_refnum.startsWith("DDS"))
        {
            order.order_refnum = order.order_refnum.substring(3);
        }
        sb.append("<"+kRootTag+" "

                +API.buildAttribute(kAttorderId,order.orderNum)+" "

                +API.buildAttribute(kAttclientOrderId,order.order_refnum)+" ");





        if(order.backorder_order_num != null)

            sb.append(API.buildAttribute(kAttbackorderOrderId,order.backorder_order_num)+" ");



        if(order.is_future_ship == 1)

        {

            sb.append(API.buildAttribute(kAttstatus,"STOPPED")+" ");

            if(order.is_backorder==1)
            {
                sb.append(API.buildAttribute(kAttstoppedAtStatus,"BACKORDER")+" ");

            }else
            {       sb.append(API.buildAttribute(kAttstoppedAtStatus,"RECEIVED")+" ");
        }
        }else{

            if(order.post_date != null)

            {


                if(order.post_date.length() > 6)
                {
                    sb.append(API.buildAttribute(kAttstatus,"WAREHOUSE")+" ");
                    if(api_vers>=2.0)
                    {
                        sb.append(API.buildAttribute(kWarehouseStatus,wstatus)+" ");

                    }
                }
                else
                {
                    sb.append(API.buildAttribute(kAttstatus,"RECEIVED")+" ");

                }



            }else

            {
                if(order.is_backorder==1)
                {
                    sb.append(API.buildAttribute(kAttstatus,"BACKORDER")+" ");
                }else
                    sb.append(API.buildAttribute(kAttstatus,"RECEIVED")+" ");

            }

        }


    if(order.bill_cc_type.equals("CC")||order.bill_cc_type.equals("AMEX")
            ||order.bill_cc_type.equals("DISC")
            ||order.bill_cc_type.equals("ENRO")
            ||order.bill_cc_type.equals("JCB ")
            ||order.bill_cc_type.equals("MAST")
            ||order.bill_cc_type.equals("VISA")
            ||order.bill_cc_type.equals("DCCB") )
        {
            if(order.order_balance > 1)
            {
                sb.append(API.buildAttribute(kAttpaymentStatus,"CCREJECTED")+" ");
            }else
            {
                sb.append(API.buildAttribute(kAttpaymentStatus,"PAID")+" ");
            }

        }else if(order.bill_cc_type.equals("CK") )
        {
            if(order.order_balance > 1)
            {
                sb.append(API.buildAttribute(kAttpaymentStatus,"WAITINGCK")+" ");
            }else
            {
                sb.append(API.buildAttribute(kAttpaymentStatus,"PAID")+" ");
            }
        }else
        {
            sb.append(API.buildAttribute(kAttpaymentStatus,"CLIENTMANAGED")+" ");
        }


        sb.append(API.buildAttribute(kAttshipDateExpected,OWDUtilities.getYYYYMMDDDateForToday())+" ");

        sb.append(API.buildAttribute(kAttShipMethodSelected,order.getShippingInfo().carr_service)+" ");

        sb.append(API.buildAttribute(kAttShipMethodCodeSelected,OrderRater.getSafeServiceCode(order.getShippingInfo().carr_service_ref_num))+" ");
        sb.append(API.buildAttribute(kAttGroupName,order.group_name==null?"":order.group_name)+" ");

        if(api_vers>=2.0)
        {

            sb.append(API.buildAttribute("shipFromPolicy",order.getFacilityPolicy()==null?"":order.getFacilityPolicy())+" ");

        }

        sb.append(">"	);

        for(int i=0;i<order.skuList.size();i++)

        {

            sb.append("<"+kTagLine+" "

                +API.buildAttribute(kAttSku,("489".equals(order.clientID)||"491".equals(order.clientID))?((LineItem)order.skuList.elementAt(i)).client_part_no.startsWith("DS-")?((LineItem)order.skuList.elementAt(i)).client_part_no.replaceAll("DS-", ""):((LineItem)order.skuList.elementAt(i)).client_part_no:((LineItem)order.skuList.elementAt(i)).client_part_no)+" "

                +API.buildAttribute(kAttRequested,((LineItem)order.skuList.elementAt(i)).quantity_request+"")+" "

                +API.buildAttribute(kAttAssigned,((LineItem)order.skuList.elementAt(i)).quantity_actual+"")+" "

                    +API.buildAttribute(kAttBackordered,((LineItem)order.skuList.elementAt(i)).quantity_backordered+"")+" "

                +API.buildAttribute(kAttClientLineRef,((LineItem)order.skuList.elementAt(i)).client_ref_num+"")+" "
                    +API.buildAttribute(kAttCost,OWDUtilities.roundFloat(((LineItem)order.skuList.elementAt(i)).sku_price)+"")
                    +" />");

        }


        if(showOrderDetails)
        {
            sb.append("<FIELDS>");

            sb.append(API.buildTextElement("BILL_ADDRESS_ONE",order.getBillingAddress().address_one));
            sb.append(API.buildTextElement("BILL_ADDRESS_TWO",order.getBillingAddress().address_two));
            sb.append(API.buildTextElement("BILL_CITY",order.getBillingAddress().city));
            sb.append(API.buildTextElement("BILL_COMPANY",order.getBillingAddress().company_name));
            sb.append(API.buildTextElement("BILL_COUNTRY",order.getBillingAddress().country));
            sb.append(API.buildTextElement("BILL_EMAIL",order.getBillingContact().getEmail()));
            sb.append(API.buildTextElement("BILL_NAME",order.getBillingContact().getName()));
            sb.append(API.buildTextElement("BILL_PHONE",order.getBillingContact().getPhone()));
            sb.append(API.buildTextElement("BILL_PO",order.po_num));
            sb.append(API.buildTextElement("BILL_POSTCODE",order.getBillingAddress().zip));
            sb.append(API.buildTextElement("BILL_STATE",order.getBillingAddress().state));
            sb.append(API.buildTextElement("FACILITY_RULE",api_vers>=2.0?"":""));
            sb.append(API.buildTextElement("GROUP_NAME",order.group_name));
            sb.append(API.buildTextElement("ORDER_COMMENT",order.getShippingInfo().comments));
            sb.append(API.buildTextElement("ORDER_WAREHOUSENOTES",order.getShippingInfo().whse_notes));
            sb.append(API.buildTextElement("SHIP_ADDRESS_ONE",order.getShippingInfo().shipAddress.address_one));
            sb.append(API.buildTextElement("SHIP_ADDRESS_TWO",order.getShippingInfo().shipAddress.address_two));
            sb.append(API.buildTextElement("SHIP_CITY",order.getShippingInfo().shipAddress.city));
            sb.append(API.buildTextElement("SHIP_COMPANY",order.getShippingInfo().shipAddress.company_name));
            sb.append(API.buildTextElement("SHIP_COUNTRY",order.getShippingInfo().shipAddress.country));
            sb.append(API.buildTextElement("SHIP_EMAIL",order.getShippingInfo().shipContact.getEmail()));
            sb.append(API.buildTextElement("SHIP_NAME",order.getShippingInfo().shipContact.getName()));
            sb.append(API.buildTextElement("SHIP_PHONE",order.getShippingInfo().shipContact.getPhone()));
            sb.append(API.buildTextElement("SHIP_POSTCODE",order.getShippingInfo().shipAddress.zip));
            sb.append(API.buildTextElement("SHIP_STATE",order.getShippingInfo().shipAddress.state));
            sb.append(API.buildTextElement("SHIPPING_METHOD",order.getShippingInfo().carr_service));
            sb.append(API.buildTextElement("TOTAL_DISCOUNT",""+ OWDUtilities.roundFloat(order.discount)));
            sb.append(API.buildTextElement("TOTAL_SHIPHANDLING",""+OWDUtilities.roundFloat(order.total_shipping_cost)));
            sb.append(API.buildTextElement("TOTAL_TAX",""+OWDUtilities.roundFloat(order.total_tax_cost)));
            sb.append(API.buildTextElement("TOTAL_COST",""+OWDUtilities.roundFloat(order.total_order_cost)));
            sb.append(API.buildTextElement("PACKING_INSTRUCTIONS",order.packInstructions));
            sb.append("<ORDER_VIEW_URL><![CDATA[https://service.owd.com/webapps/extranet/extranet.jsp?act=cngMgr&mgr=Orders&ordermgr=edit&oid="+order.orderID+"]]></ORDER_VIEW_URL>");


            sb.append("</FIELDS>");
        }


        if(api_vers>=2.1) {

            for(String tag:order.getTagMap().keySet())
            {
                sb.append("<CUSTOM_VALUE "+API.buildAttribute("name",tag)+">"+ StringEscapeUtils.escapeXml(order.getTagMap().get(tag))+"</CUSTOM_VALUE>");
            }
        }

        sb.append("</"+kRootTag+">");

        return sb.toString();

    }



    public String getXMLFromStatus()

    {

StringBuffer sb = new StringBuffer();

        String wstatus = OrderUtilities.getWarehouseStatusString(Integer.parseInt(os.order_id));
        if(os.shipping.carr_service_ref_num.contains("COM_OWD_FLATRATE")){
            log.debug("We need to decode flat rate shipping");
            try{
                Criteria crit = HibernateSession.currentSession().createCriteria(OwdOrderTrack.class);
                crit.add(Restrictions.eq("orderFkey", Integer.parseInt(os.order_id)));
                crit.add(Restrictions.eq("isVoid",0));
                List<OwdOrderTrack> owdOrderTracks = crit.list();
                if(owdOrderTracks.size()>0){
                    if(null!=owdOrderTracks.get(0).getServiceCode()) {
                        os.shipping.carr_service_ref_num = (owdOrderTracks.get(0).getServiceCode());
                        Criteria crit2 = HibernateSession.currentSession().createCriteria(OwdShipMethod.class);
                        crit2.add(Restrictions.eq("methodCode", os.shipping.carr_service_ref_num));
                        List<OwdShipMethod> methods = crit2.list();
                        os.shipping.carr_service = (methods.get(0).getMethodName());
                        log.debug(os.shipping.carr_service);
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }


        }


        sb.append("<"+kRootTag+" "

                +API.buildAttribute(kAttorderId,os.OWDorderReference)+" "

                +API.buildAttribute(kAttclientOrderId,os.orderReference)+" ");



        if(os.hasBackorder)

            sb.append(API.buildAttribute(kAttbackorderOrderId,os.backorderNum)+" ");

        if(!("".equals(os.original_ordernum.trim())))
            sb.append(API.buildAttribute(kAttbackorderedFromOrderId,os.original_ordernum)+" ");



        if(os.is_void)

        {



            sb.append(API.buildAttribute(kAttstatus,"VOID")+" ");

        }else

        {

        if(os.is_on_hold)

        {

            sb.append(API.buildAttribute(kAttstatus,"STOPPED")+" ");
            if(os.isBackorder)
            {
                sb.append(API.buildAttribute(kAttstoppedAtStatus,"BACKORDER")+" ");

            }else
            sb.append(API.buildAttribute(kAttstoppedAtStatus,"RECEIVED")+" ");

        }else{

            if(os.is_posted)

            {

                if(os.is_shipped) {
                    sb.append(API.buildAttribute(kAttstatus, "SHIPPED") + " ");
                }
                else {
                    sb.append(API.buildAttribute(kAttstatus, "WAREHOUSE") + " ");
                    if(api_vers>=2.0)
                    {
                        sb.append(API.buildAttribute(kWarehouseStatus,wstatus)+" ");

                    }
                }
            }else

            {
                if(os.isBackorder)
                {
                    sb.append(API.buildAttribute(kAttstatus,"BACKORDER")+" ");

                }else
                    sb.append(API.buildAttribute(kAttstatus,"RECEIVED")+" ");

            }

        }

        }

        if(os.bill_cc_type.equals("CC")||os.bill_cc_type.equals("AMEX")
            ||os.bill_cc_type.equals("DISC")
            ||os.bill_cc_type.equals("ENRO")
            ||os.bill_cc_type.equals("JCB ")
            ||os.bill_cc_type.equals("MAST")
            ||os.bill_cc_type.equals("VISA")
            ||os.bill_cc_type.equals("DCCB") )
        {
            if(os.balance > 1)
            {
                sb.append(API.buildAttribute(kAttpaymentStatus,"CCREJECTED")+" ");
            }else
            {
                sb.append(API.buildAttribute(kAttpaymentStatus,"PAID")+" ");
            }

        }else if(os.bill_cc_type.equals("CK") )
        {
            if(os.balance > 1)
            {
                sb.append(API.buildAttribute(kAttpaymentStatus,"WAITINGCK")+" ");
            }else
            {
                sb.append(API.buildAttribute(kAttpaymentStatus,"PAID")+" ");
            }
        }else
        {
            sb.append(API.buildAttribute(kAttpaymentStatus,"CLIENTMANAGED")+" ");
        }

        if(!(os.is_shipped) && !(os.is_on_hold) && os.is_posted)
            sb.append(API.buildAttribute(kAttshipDateExpected,OWDUtilities.getYYYYMMDDDateForToday())+" ");


        sb.append(API.buildAttribute(kAttShipMethodSelected,os.shipping.carr_service)+" ");
        sb.append(API.buildAttribute(kAttShipMethodCodeSelected,OrderRater.getSafeServiceCode(os.shipping.carr_service_ref_num))+" ");
        sb.append(API.buildAttribute(kAttGroupName,os.group_name==null?"":os.group_name)+" ");
        if(api_vers>=2.3){
            sb.append(API.buildAttribute("packslip_template",os.template)+" ");
        }

        if(api_vers>=2.0)
        {

            sb.append(API.buildAttribute("shipFromPolicy",os.getShipPolicy()==null?"":os.getShipPolicy())+" ");

        }

        sb.append(">"	);

        for(int i=0;i<os.items.size();i++)

        {

            sb.append("<"+kTagLine+" "

                +API.buildAttribute(kAttSku,("489".equals(os.client_id)||"491".equals(os.client_id))?((LineItem)os.items.elementAt(i)).client_part_no.startsWith("DS-")?((LineItem)os.items.elementAt(i)).client_part_no.replaceAll("DS-", ""):((LineItem)os.items.elementAt(i)).client_part_no:((LineItem)os.items.elementAt(i)).client_part_no)+" "

                +API.buildAttribute(kAttRequested,((LineItem)os.items.elementAt(i)).quantity_request+"")+" "

                +API.buildAttribute(kAttAssigned,((LineItem)os.items.elementAt(i)).quantity_actual+"")+" "


                    +API.buildAttribute(kAttBackordered,((LineItem)os.items.elementAt(i)).quantity_backordered+"")+" "
                    +API.buildAttribute(kAttClientLineRef,((LineItem)os.items.elementAt(i)).client_ref_num+"")+" "
                    +API.buildAttribute(kAttCost,OWDUtilities.roundFloat(((LineItem)os.items.elementAt(i)).sku_price)+"")
                    +" >");

            try
            {
                log.debug("checking serials");
            if(os.serialItemMap != null)
            {
                log.debug("got serials");
                for (Object key:os.serialItemMap.keySet())
                {
                    log.debug("map:"+key+":"+os.serialItemMap.get(key));
                }
            if (os.serialItemMap.containsKey(new Integer(((LineItem)os.items.elementAt(i)).line_item_id)))
            {
                log.debug("got key");
                Iterator it = ((List)(os.serialItemMap.get(new Integer(((LineItem)os.items.elementAt(i)).line_item_id)))).iterator();

                while(it.hasNext())
                {
                    log.debug("appending serial");
                    sb.append(API.buildTextElement("SERIAL",(String) it.next()));

                }
            }
            }
            }catch(Exception ex)
            {
                ex.printStackTrace();
            }
        sb.append("</"+kTagLine+">");
        }




        if(os.is_shipped)

        {

            sb.append("<"+kTagShipment+" ");




            if(os.packages != null)
            {
                sb.append(API.buildAttribute(kShipDate,OWDUtilities.getYYYYMMDDFromSQLDate(((com.owd.core.business.order.Package)os.packages.elementAt(0)).ship_date))+" ");

            sb.append(API.buildAttribute(kShipVia,os.shipping.carr_service)+" ");

                if(api_vers>=2.0)
                {

                    sb.append(API.buildAttribute("shippedFromFacilityCode",(os.shipLocation==null?"":os.shipLocation))+" ");

                }

            if(api_vers >= 1.2)

            {

                sb.append(" "+API.buildAttribute(kShipViaCode,OrderRater.getSafeServiceCode(os.shipping.carr_service_ref_num))+" >");

            }else

            {

                sb.append(" >");

            }



            for(int i=0;i<os.packages.size();i++)

            {

                if(((com.owd.core.business.order.Package)os.packages.elementAt(i)).is_void.equals("0"))

                {

                sb.append("<"+kTagPackage+" "

                +API.buildAttribute(kAttPackageNum,((com.owd.core.business.order.Package)os.packages.elementAt(i)).line_index)+" ");

                if(((com.owd.core.business.order.Package)os.packages.elementAt(i)).tracking_no.length()>=8)

                {

                    sb.append(API.buildAttribute(kAttTrackingNum,((com.owd.core.business.order.Package)os.packages.elementAt(i)).tracking_no+""));

                }

                    if(api_vers >= 1.2)

                    {

                        sb.append(" "+API.buildAttribute(kAttRatedCost,((com.owd.core.business.order.Package)os.packages.elementAt(i)).total_billed+""));

                        sb.append(" "+API.buildAttribute(kAttWeightLbs,((com.owd.core.business.order.Package)os.packages.elementAt(i)).weight+""));

                        List<Integer> dims = Package.getHwdDimensionsOfPackage(new Integer(((com.owd.core.business.order.Package)os.packages.elementAt(i)).order_track_id).intValue());

                        sb.append(" "+API.buildAttribute("height_in",dims.get(0)+""));
                        sb.append(" "+API.buildAttribute("width_in",dims.get(1)+""));
                        sb.append(" "+API.buildAttribute("depth_in",dims.get(2)+""));

                        String sscc = Package.getSSCCCodeForPackage(((com.owd.core.business.order.Package)os.packages.elementAt(i)).order_track_id);
                        if (sscc.length()>0){
                            sb.append(" "+API.buildAttribute("sscc",sscc));
                        }
                                sb.append(" >");

                      //IF this is the first package, include virtual items in to report shipped. Fix added to support dropstream reporting all sku's back to shopping carts

                        List<lineItemPackageData> lines = Package.getLineItemDataMapforPackage(new Integer(((com.owd.core.business.order.Package)os.packages.elementAt(i)).order_track_id).intValue(),i==0);

                        for (lineItemPackageData data:lines)
                        {
                            String sku =  data.getSku();

                            sb.append("<"+OrderSummaryResponse.kTagItem+" "
                                    +API.buildAttribute(OrderSummaryResponse.kAttClientSKU,("489".equals(os.client_id)||"491".equals(os.client_id))?sku.startsWith("DS-")?sku.replaceAll("DS-", ""):sku:sku)+" "
                                    +API.buildAttribute(OrderSummaryResponse.kAttQuantity,data.getPackageQty()+"")+" ");

                            if(data.getLots().size()>0&&api_vers>=2.2){
                                //do the lots for the item
                                sb.append(">");
                                sb.append("<"+OrderSummaryResponse.kTagItemLots +">");
                                for(lotData ld:data.getLots()){
                                    sb.append("<"+OrderSummaryResponse.kTagLot+">");
                                    sb.append("<"+OrderSummaryResponse.kTagLotValue+">");
                                    sb.append(ld.getLotValue());
                                    sb.append("</"+OrderSummaryResponse.kTagLotValue+">");
                                    sb.append("<"+OrderSummaryResponse.kTagQty+">");
                                    sb.append(ld.getLotQty());
                                    sb.append("</"+OrderSummaryResponse.kTagQty+">");
                                    sb.append("</"+OrderSummaryResponse.kTagLot+">");
                                }
                                sb.append("</"+OrderSummaryResponse.kTagItemLots+">");
                                sb.append("</ITEM>");
                            }else {

                                sb.append(" />");
                            }



                        }
             		sb.append("</PACKAGE>");

                    }else

                    {

                        sb.append(" />");

                    }

                }

            }

            }

            sb.append("</"+kTagShipment+">");

        }


        if(showOrderDetails)
        {
           sb.append("<FIELDS>");

            sb.append(API.buildTextElement("BILL_ADDRESS_ONE",os.billAddress.address_one));
            sb.append(API.buildTextElement("BILL_ADDRESS_TWO",os.billAddress.address_two));
            sb.append(API.buildTextElement("BILL_CITY",os.billAddress.city));
            sb.append(API.buildTextElement("BILL_COMPANY",os.billAddress.company_name));
            sb.append(API.buildTextElement("BILL_COUNTRY",os.billAddress.country));
            sb.append(API.buildTextElement("BILL_EMAIL",os.billContact.getEmail()));
            sb.append(API.buildTextElement("BILL_NAME",os.billContact.getName()));
            sb.append(API.buildTextElement("BILL_PHONE",os.billContact.getPhone()));
            sb.append(API.buildTextElement("BILL_PO",os.po_num));
            sb.append(API.buildTextElement("BILL_POSTCODE",os.billAddress.zip));
            sb.append(API.buildTextElement("BILL_STATE",os.billAddress.state));
            sb.append(API.buildTextElement("FACILITY_RULE",api_vers>=2.0?"":""));
            sb.append(API.buildTextElement("GROUP_NAME",os.group_name));
            sb.append(API.buildTextElement("ORDER_COMMENT",os.shipping.comments));
            sb.append(API.buildTextElement("ORDER_WAREHOUSENOTES",os.shipping.whse_notes));
            sb.append(API.buildTextElement("SHIP_ADDRESS_ONE",os.shipping.shipAddress.address_one));
            sb.append(API.buildTextElement("SHIP_ADDRESS_TWO",os.shipping.shipAddress.address_two));
            sb.append(API.buildTextElement("SHIP_CITY",os.shipping.shipAddress.city));
            sb.append(API.buildTextElement("SHIP_COMPANY",os.shipping.shipAddress.company_name));
            sb.append(API.buildTextElement("SHIP_COUNTRY",os.shipping.shipAddress.country));
            sb.append(API.buildTextElement("SHIP_EMAIL",os.shipping.shipContact.getEmail()));
            sb.append(API.buildTextElement("SHIP_NAME",os.shipping.shipContact.getName()));
            sb.append(API.buildTextElement("SHIP_PHONE",os.shipping.shipContact.getPhone()));
            sb.append(API.buildTextElement("SHIP_POSTCODE",os.shipping.shipAddress.zip));
            sb.append(API.buildTextElement("SHIP_STATE",os.shipping.shipAddress.state));
            sb.append(API.buildTextElement("SHIPPING_METHOD",os.shipping.carr_service));
            sb.append(API.buildTextElement("TOTAL_DISCOUNT",""+ OWDUtilities.roundFloat(os.discount)));
            sb.append(API.buildTextElement("TOTAL_SHIPHANDLING",""+OWDUtilities.roundFloat(os.ship_handling_fee)));
            sb.append(API.buildTextElement("TOTAL_TAX",""+OWDUtilities.roundFloat(os.tax_amount)));
            sb.append(API.buildTextElement("TOTAL_COST",""+OWDUtilities.roundFloat(os.order_total)));
            sb.append(API.buildTextElement("PACKING_INSTRUCTIONS",os.packingInstructions));
            sb.append("<ORDER_VIEW_URL><![CDATA[https://service.owd.com/webapps/extranet/extranet.jsp?act=cngMgr&mgr=Orders&ordermgr=edit&oid="+os.order_id+"]]></ORDER_VIEW_URL>");
            sb.append("</FIELDS>");
        }


        if(api_vers>=2.1) {

            for(String tag:os.getTagMap().keySet())
            {
                sb.append("<CUSTOM_VALUE "+API.buildAttribute("name",tag)+">"+ StringEscapeUtils.escapeXml(os.getTagMap().get(tag))+"</CUSTOM_VALUE>");
            }
        }

        sb.append("</"+kRootTag+">");



        return sb.toString();	}

    protected String getXMLFromOrderIDs()
    {
        StringBuffer responseBuffer = new StringBuffer();

        for(int i=0;i<orderIDs.size();i++)
        {
            os = new OrderStatus( (String) orderIDs.get(i));
            responseBuffer.append(getXMLFromStatus());
        }

        os = null;
        return responseBuffer.toString();


    }

    public String getXML()

    {

        String rr = "";

         if(owdorder==null)
         {
        if(order == null)
{
        if(os == null)
        {
            rr= getXMLFromOrderIDs();
        }else
        {
            rr=  getXMLFromStatus();
            }
}
        else
{
    rr=  getXMLFromOrder();
}
         }else
         {
             rr=  getXMLFromOwdOrder();
         }

        log.debug(rr);
        return rr;
    }



}
