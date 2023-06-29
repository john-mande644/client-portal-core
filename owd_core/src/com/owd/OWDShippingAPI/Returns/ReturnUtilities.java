package com.owd.OWDShippingAPI.Returns;

import com.owd.OWDShippingAPI.Models.*;
import com.owd.OWDShippingAPI.Models.Package;
import com.owd.OWDShippingAPI.ShippingUtilities;
import com.owd.core.TagUtilities;
import com.owd.core.business.order.Order;
import com.owd.core.managers.ScanManager;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by danny on 3/10/2020.
 */
public class ReturnUtilities {

    protected final static Logger log = LogManager.getLogger();

    public static void main(String[] args) {

        // point to dev shipping api
        /*
        System.setProperty("com.owd.shippingapi", "development");
        try {
            OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, 24647345);
            generateAndMISaveLabel(order);
        } catch (Exception e) {
        }
        */

    }



    public static void generateAndMISaveLabel(OwdOrder order){

        try {
            //Generate the label
            //todo deal with errors.
            ReturnResponse rr = getMailInnovationsReturnLabelFromOrder(order);
            //get Label data and save
            String tracking = rr.getTracking();


            addLabelToAmazonBucket(tracking, order, rr.getLabel());
            //Save tracking
            addReturnTrackingToOrder(order,tracking);
            //add url to order
            addUrlToOrder(order,tracking);

        }catch (Exception e){
            e.printStackTrace();

        }

    }



    public static void addReturnTrackingToOrder(OwdOrder order,String tracking) throws Exception{



        try {
            
            OrderShipInfo2 info = order.getShipInfo2();
            info.setReturnTracking(tracking);

            HibernateSession.currentSession().saveOrUpdate(info);
            HibernateSession.currentSession().flush();
            log.debug("Committing Update");
            HibUtils.commit(HibernateSession.currentSession());
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("Unable to save return tracking to order");
        }



    }

    /**
     *
     * @param order OWDOrder to check
     * @return returns int. 0 for no return needed. 1 for label to be saved and printed on packing slip
     */
    public static int doesOrderNeedMIReturnLabel(OwdOrder order){

        Map<String,String> tags = TagUtilities.getTagMap("ORDER", order.getOrderId());
        if(tags.size()>0&&tags.containsKey(TagUtilities.kMIReturn)){

            return Integer.parseInt(tags.get(TagUtilities.kMIReturn));

        }
        return 0 ;

    }

    public static byte[] getLabelFromAmazonBucket(OwdOrder order){

        String filename = "";
        try {

            Criteria crit = HibernateSession.currentSession().createCriteria(ScanReturnLabel.class);
            crit.add(Restrictions.eq("scan_id", order.getOrderId()));



            List<ScanReturnLabel> scandocs = crit.list();
            if(scandocs.size()>0){
                filename = scandocs.get(0).getName();
                return ScanManager.getReturnLabelFromOwdOrder(order, filename);
            }else{
                return new byte[0];
            }




        }catch (Exception e){
            e.printStackTrace();
        }
        return new byte[0];
    }

    public static boolean addUrlToOrder(OwdOrder order, String tracking){
        boolean success = false;

        try{
            String url = "https://s3-us-west-2.amazonaws.com/owd.s3.returnlabels/returnlabel/"+order.getClient().getClientId()+"/"+order.getOrderId() + "_" + tracking + ".png";
            TagUtilities.setOrderTagForOwdOrder(order,TagUtilities.kReturnLabel,url);
            success = true;

        }catch (Exception e){
            e.printStackTrace();
        }



        return success;
    }

    public static boolean addLabelToAmazonBucket(String tracking, OwdOrder order, byte[] data){
        boolean success = false;
        try {
            ScanManager.addReturnLabelToOwdOrder(order, data, order.getOrderId() + "_" + tracking + ".png", "");

            success = true;
        }catch (Exception e){
            e.printStackTrace();
        }

        return success;
    }
    public static void markOrderForMailInnovationsShipmentViaOrder(Order order) throws Exception{
        order.addTag(TagUtilities.kMIReturn,"1");
    }
    public static void martOrderForMailInnovationsShipmentViaOrderId(int orderId){
        TagUtilities.setOrderTag(orderId,TagUtilities.kMIReturn,"1");
    }


    public static ReturnResponse getMailInnovationsReturnLabelFromOrder(OwdOrder order) throws Exception{

        if(order.getFacilityCode().equals("DC1")){
            throw new Exception("UPS Mail Innovations Returns not supported for facility");
        }

        ShippingUtilities su = new ShippingUtilities();

        ShipShipment shipment = loadMIReturnInfoFromOrder(order);
        ShipShipmentResponse response = su.processShipmentAndAttemptFixes(shipment);
        ReturnResponse returnResponse = loadReturnResponseFromShipShipmentResponse(response);


        return returnResponse;
    }


    private static ReturnResponse loadReturnResponseFromShipShipmentResponse(ShipShipmentResponse response){
        ReturnResponse rr = new ReturnResponse();
        rr.setTracking(response.getPackages().get(0).getTrackingNumber());
        rr.setLabel(org.apache.commons.codec.binary.Base64.decodeBase64(response.getPackages().get(0).getLabel().get(0)));
        return rr;
    }


    public static ShipShipment loadMIReturnInfoFromOrder(OwdOrder order) throws Exception{

        ShipShipment shipment = new ShipShipment();

        if (order.getFacilityCode().equalsIgnoreCase("DC7")){
            shipment.setShippingAccountName("OWD_DC7");
            shipment.setFacility("DC7");

        }else if (order.getFacilityCode().equalsIgnoreCase("DC6") ){
            shipment.setShippingAccountName("OWD_DC6");
            shipment.setFacility("DC6");
        }

        // enable when DC1 UPS MI return is ready
        /*else if (order.getFacilityCode().equalsIgnoreCase("DC1") ) {
            shipment.setShippingAccountName("OWD_DC1");
            shipment.setFacility("DC1");

        }*/

        shipment.setShipMethod("UPS Mail Innovations Returns");
        shipment.setShipService("BWTI_UPS.UPS.MIR");
        shipment.setOrderId(order.getOrderId()+"");
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        shipment.setShipTerms("SHIPPER");
        shipment.setShipDate(format.format(Calendar.getInstance().getTime()));
        shipment.setShipperReference(order.getOrderNum());
        shipment.setClientReference(order.getOrderRefnum());
        OwdOrderShipInfo info = order.getShipinfo();
        shipment.setShipToContact(info.getShipFirstName()+ " "+ info.getShipLastName());
        shipment.setShipToAddress1(info.getShipAddressOne());
        shipment.setShipToAddress2(info.getShipAddressTwo());
        shipment.setShipToCity(info.getShipCity());
        shipment.setShipToState(info.getShipState());
        shipment.setShipToPostalCode(info.getShipZip());
        shipment.setShipToCountry("US");
        shipment.setShipmentDescription("Returned Goods");
        shipment.setLabelFormat("PNG_IMAGE");
        Package p = new Package();
        p.setWeight(1d);
        p.setPackagingType("CUSTOM");
        p.setPackageReference(order.getOrderNum());
        shipment.setPackages(new ArrayList<Package>());
        shipment.getPackages().add(p);


        return shipment;

    }




}
