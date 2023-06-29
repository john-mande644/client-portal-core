package com.owd.dc.packing;

import com.owd.dc.packing.AutoBatchPrinting.PackingABUtils;
import com.owd.dc.packing.beans.Lyft.LookupUtils;
import com.owd.dc.packing.boxSealerWorkflow.boxSealerUtilities;
import com.owd.dc.packing.dcholds.holdUtils;
import com.owd.dc.packing.sortation.sortationUtilities;
import com.owd.dc.packing.specialFunctions.relabelingUtils;
import com.owd.dc.packing.vendorCompliance.vendorComplianceUtils;
import com.owd.hibernate.HibernateSession;

import javax.servlet.ServletException;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: May 1, 2007
 * Time: 4:07:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class packapp extends HttpServlet {
  public void init(ServletConfig config)
            throws ServletException {
        super.init(config);

    }

    public void destroy() {
        super.destroy();

    }

    //GET requests not supported
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {

        doPost(req, resp);
    }
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {
       try{
          String action = req.getParameter("action");
        if(null == action) throw new Exception("Invalid request");
          String r = new String();

        if(action.equals("test")){
            r="Success";
        }
        if(action.equals("login")){
            if(null == req.getParameter("version")){
                r = Util.errorResponse("Please update your version of the packapp");
            }else {
                r = Util.login((String) req.getParameter("id"), req.getRemoteAddr(), req.getParameter("version"));
            }
        }
        if(action.equals("loadOrder")){
          if(null == req.getParameter("orderNum")){
              r = Util.errorResponse("Invalid order Number");
          } else{
              String facility = "DC1";
              if(null != req.getParameter("facility")){
                  facility = req.getParameter("facility");
                       System.out.println(facility);
              }
              r = Util.loadOrder(req.getParameter("orderNum"),facility,req.getParameter("batch"));
          }
        }
           if(action.equals("updatePackageWeight")){
              r = Util.updatePackageWeight(req.getParameter("barcode"),req.getParameter("weight"));
           }
           if(action.equals("singleBox")){
               if(null == req.getParameter("data")){
                   r = Util.errorResponse("No data found for Single box creation");
               }else{
                   r = singleBox.insertSingleBox(req.getParameter("data"));
               }
           }
           if(action.equals("packedLabels")){
               if(null == req.getParameter("data")){
                   r = Util.errorResponse("No data found for Getting Packed Labels");
               }else{
                   r = packageBarcodeLookup.getPackedBarcodes(req.getParameter("data"));
               }
           }
           if(action.equals("multiFirstBox")){
              if(null == req.getParameter("data")){
                   r = Util.errorResponse("No data found for Multi box creation");
               }else{
                   r = multiBox.insertMultiBox(req.getParameter("data"),true);
               }
           }
            if(action.equals("multiNextBox")){
              if(null == req.getParameter("data")){
                   r = Util.errorResponse("No data found for Multi Next creation");
               }else{
                   r = multiBox.insertMultiBox(req.getParameter("data"),false);
               }
           }
           if(action.equals("loadBoxes")){

                   r = boxUtil.getAllBoxes(req.getParameter("facility"));

           }
            if(action.equals("batchBox")){
               if(null == req.getParameter("data")){
                   r = Util.errorResponse("No data found for batch box creation");
               }else{
                   r = batchBox.insertSingleBox(req.getParameter("data"));
               }
           }
            if(action.equals("cancelPack")){
               if(null == req.getParameter("id")){
                   r = "No data found for Cancel id";
               }else if(null == req.getParameter("time")){
                   r= "Bad time data";

               }else if(null == req.getParameter("who")){
                  r= "Bad who data";
               }else{
                   r = multiBox.cancelPack(req.getParameter("id"),req.getParameter("time"),req.getParameter("who"));
               }
           }
           if(action.equals("voidShipment")){
               r = voidUtil.getOrderPackagesXml(HibernateSession.currentSession(),req.getParameter("orderNum"));
               
           }
           if(action.equals("clientPrintables")){
               r = Util.getAllPrintableSkus(HibernateSession.currentSession());
           }
           if(action.equals("loadPackages")){
               r = getPackagesForShipRetry.getPackagesXml(req.getParameter("orderNum"),HibernateSession.currentSession());
           }
           if(action.equals("swapPackages")){
               r = relabelingUtils.doRelabel(req.getParameter("tracking"), req.getParameter("swapType"));
           }
           if (action.equals("gsscloseout")){
               String dispatchId = "";
               if (null != req.getParameter("dispatchId")){
                   dispatchId = req.getParameter("dispatchId");
               }
               r = gssCloseOut.getXml(req.getParameter("gssAction"),dispatchId,req.getParameter("facility"));
           }
           if (action.equals("putAway")){
               if(null== req.getParameter("orderFkey")){
                 r = "No Valid orderFkey supplied";
               }
               if(null== req.getParameter("packer")){
                                r = "No Valid packer supplied";
                              }
               if(null== req.getParameter("inventoryId")){
                                r = "No Valid Inventory Id supplied";
                              }
               if(null== req.getParameter("inventoryNum")){
                                r = "No Valid Inventory Number supplied";
                              }
               if(null== req.getParameter("qty")){
                                r = "No Valid qty supplied";
                              }

               if(r.length()==0){
                   pickErrorRecord.recordError(req.getParameter("orderFkey"),req.getParameter("packer"),req.getParameter("inventoryId"),req.getParameter("inventoryNum"),req.getParameter("qty"));
               }
           }
           if(action.equals("shipHold")){
               if(null==req.getParameter("orderNum")||req.getParameter("orderNum").length()==0){
                   r="No Valid order Num";
               }
               if(null==req.getParameter("name")||req.getParameter("name").length()==0){
                   r="No Name supplied";
               }
               if(null==req.getParameter("error")||req.getParameter("error").length()==0){
                   r="No error Message";
               }
               if(r.length()==0){
                   r = holdUtils.setShippingHoldForOrderNum(req.getParameter("orderNum"),req.getParameter("name"),req.getParameter("error"));
               }
           }
           if(action.equals("checkSerial")){
               if(null==req.getParameter("serial")||req.getParameter("serial").length()==0){
                   r = "No serial number found";
               }
               if(null==req.getParameter("inventoryFkey")||req.getParameter("inventoryFkey").length()==0){
                   r="No inventory Fkey found";

               }
               if(r.length()==0){

                   boolean found = Util.checkSerialIsAlreadyUsed(req.getParameter("serial"),req.getParameter("inventoryFkey"));
                   if(found){
                       r="used";
                   }else{
                       r="not found";
                   }


               }
           }
           if(action.equals("clearTote")){
               if(null==req.getParameter("tote")||req.getParameter("tote").length()==0){
                   r = "please provide tote";
               }
               r = Util.clearTote(req.getParameter("tote"));
           }
           if(action.equals("orderStatus")){
               if(null==req.getParameter("orderId")||req.getParameter("orderId").length()==0){
                   r="Please submit with a proper order Id";
               }
               r = orderStatusUtils.getOrderStatusXML(req.getParameter("orderId"));

           }
           if(action.equals("getLots")){
               if(null==req.getParameter("inventoryId")||req.getParameter("inventoryId").length()==0){
                   r = "Error, please set InventoryId";
               }
               if(null==req.getParameter("facility")||req.getParameter("facility").length()==0){
                   r = "Error, please set facility";
               }
               r = lotUtils.getLotValuesForSkuXML(req.getParameter("inventoryId"),req.getParameter("facility"));
           }
           if(action.equals("vendorCompliance")){
               System.out.println("In the vendor stuff xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
               if(null==req.getParameter("orderNum")||req.getParameter("orderNum").length()==0){
                   r = "Error, please set orderNum";
               }else {
                   r = vendorComplianceUtils.getVendorComplianceXmlResponseForOrderNum(req.getParameter("orderNum"));
               }
           }
           if(action.equals("batchSummary")){
               System.out.println("Batch xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
               if(null==req.getParameter("facility")){
                   r = "We have some issues";
               }else {
                   r = PackingABUtils.getJsonSummaryBatchInfo(req.getParameter("facility"));
               }
           }
           if(action.equals("sortationPull")){
               r = sortationUtilities.getTrackingXmlForFacility(req.getParameter("trackingId"),req.getParameter("facility"));
           }
           if(action.equals("LyftLookup")){
               r = LookupUtils.getLabelFromOrderId(req.getParameter("orderId"));
           }
           if(action.equals("ClientUnits")){
               r = Util.getClientAndUnitsString(req.getParameter("orderId"));
           }
           if(action.equals("loadBatchFile")){
               System.out.println("Batch file");
               if(null==req.getParameter("facility")){
                   r = "We have some issues";
               }else if(null==req.getParameter("client")) {
                   r = "We have some issues";
               }else if(null==req.getParameter("sla")) {
                   r = "We have some issues";
               }else if(null==req.getParameter("fingerprint")) {
                   r = "We have some issues";
               }
               else{
                   r = PackingABUtils.getXmlAbBatchForFingerprintFacility(req.getParameter("fingerprint"),req.getParameter("facility"),req.getParameter("sla"), req.getParameter("client"));
               }
           }
           if(action.equals("markPackageTransferred")){

                   r = PackingABUtils.getXmlmarkPackageTransferred(req.getParameter("packageId"));


           }
           if(action.equals("getBoxSealerBoxes")){

               r = boxSealerUtilities.getAvailableBoxes(req.getParameter("facility"));


           }
           if(action.equals("addDunnageToPackage")){
               r = boxSealerUtilities.addDunnageToPackage(req.getParameter("barcode"),req.getParameter("dunnage"));
           }

           if(action.equals("orderLookupForReprint")){
               r = boxSealerUtilities.reprintPackageData(req.getParameter("barcode"));
           }
           if(action.equals("updateBox")){
               r = boxSealerUtilities.updateBox(req.getParameter("barcode"),req.getParameter("data"));
           }


          System.out.println("Sending Responce for"+action);
          resp.getWriter().print(r);
         // resp.getOutputStream().print(r);
        }catch (Exception ex){
            ex.printStackTrace();

        }
    }
    }
