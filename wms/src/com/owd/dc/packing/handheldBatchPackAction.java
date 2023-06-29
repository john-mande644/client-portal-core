package com.owd.dc.packing;

import com.owd.dc.actions.OWDLookupDispatchAction;
import com.owd.dc.checkAuthentication;
import com.owd.dc.inventory.LabelUtilities;
import com.owd.dc.setup.selectList;
import org.apache.xpath.XPathAPI;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Calendar;
import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Feb 9, 2008
 * Time: 8:27:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class handheldBatchPackAction extends OWDLookupDispatchAction {
    private String  name="name";
     private String  width="width";
     private String  depth="depth";
     private String  height="height";
     private String  cost="cost";
    protected Map getKeyMethodMap() {
            System.out.println("getKeyMethodMap invoked..");
            Map map = new HashMap();
            map.put("button.selectBox", "selectBox");
            map.put("button.pack", "packOrder");
             map.put("button.newBox", "unspecified");
            return map;
        }
   //action for edit quanity
   public ActionForward unspecified(ActionMapping mapping,
                                    ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception{

    try {
            //Checking for login
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }
        System.out.println("hello");
        batchPackForm bp = (batchPackForm) form;

         clearBoxInfo(bp);
   String boxsxml = boxUtil.getAllBoxes();
           //System.out.println(boxsxml);

        String barcode = new String();
       packageBean pb = new packageBean();
        // Create a factory object for creating DOM parsers
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(false);
        factory.setValidating(false);

// Now use the factory to create a DOM parser (a.k.a. a DocumentBuilder)
DocumentBuilder parser = factory.newDocumentBuilder();
// Parse the file and build a Document tree to represent its content
Document document = parser.parse(new ByteArrayInputStream(boxsxml.getBytes()));
        NodeIterator pack = XPathAPI.selectNodeIterator(document,"./boxes/box");
               Node ns;
           Map<String,Map> boxs = new HashMap();

           while((ns = pack.nextNode())!=null){


          Map box = new HashMap();
          box.put(name,XPathAPI.eval(ns,"./name").toString());
         box.put(width,XPathAPI.eval(ns,"./width").toString());
          box.put(height,XPathAPI.eval(ns,"./height").toString());
         box.put(depth,XPathAPI.eval(ns,"./depth").toString());
         box.put(cost,XPathAPI.eval(ns,"./cost").toString());
       //add box hashmap to the boxs map keyed by box fkey
        boxs.put(XPathAPI.eval(ns,"./fkey").toString(),box);
               selectList sl = new selectList();
               sl.setAction(XPathAPI.eval(ns,"./fkey").toString());
               sl.setDisplay(XPathAPI.eval(ns,"./name").toString());
               bp.getBoxes().add(sl);

           }
           bp.setBoxInfo(boxs);



            return (mapping.findForward("success"));

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }
   public ActionForward selectBox(ActionMapping mapping,
                                    ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception{

    try {
            //Checking for login
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }
        System.out.println("settingBox");
        batchPackForm bp = (batchPackForm) form;
          //get box from loaded map
          Map box = bp.getBoxInfo().get(bp.getBoxId());

        bp.setBoxDescription(box.get(name).toString());


        bp.setWidth(box.get(width).toString());
        bp.setHeight(box.get(height).toString());
        bp.setDepth(box.get(depth).toString());
        bp.setCost(box.get(cost).toString());

            return (mapping.findForward("success"));

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }
    public ActionForward packOrder(ActionMapping mapping,
                                    ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception{

    try {
            //Checking for login
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }

        batchPackForm bp = (batchPackForm) form;
          //get box from loaded map
         if(bp.getOrderNum().length()<5){
             throw new Exception("Please scan valid order number");
         }
      if(bp.getDoneOrders().contains(bp.getOrderNum())){
       // order is done and in current list reprint barcode.  this is for bad barcode printing
         LabelUtilities.printPackageBarcode(bp.getOrderNum());
         return mapping.findForward("success");

      }
      //load package bean
        String tcid = "";
       packageBean pb = new packageBean();
       for(Cookie c:request.getCookies()){
           if(c.getName().equals("tc__id")){
             tcid = c.getValue();
               System.out.println("Timeclockid" + tcid);
           }
       }
        pb.setPacker(tcid);
    pb.setStart(getMMDDYYSlashedTime());
    pb.setStop(getMMDDYYSlashedTime());
    pb.setScannedBarcode("*"+bp.getOrderNum()+"*");


   pb.setBoxFkey(bp.getBoxId());
    pb.setBoxCost(bp.getCost());
    pb.setDepth(bp.getDepth());
    pb.setHeight(bp.getHeight());
    pb.setWidth(bp.getWidth());
           batchBox.insertRecord(pb);
        bp.setLastOrder(bp.getOrderNum());
        bp.getDoneOrders().add(bp.getOrderNum());
          LabelUtilities.printPackageBarcode(bp.getOrderNum());

            return (mapping.findForward("success"));

        } catch (Exception ex) {
            ex.printStackTrace();
         if(ex.getMessage().contains("Violation of UNIQUE KEY constraint")){
                 request.setAttribute("error", "Order has Already been Packed");
              }
         else{   request.setAttribute("error", ex.getMessage());
         }
            return mapping.findForward("error");

        }
    }


  static String getMMDDYYSlashedTime() throws Exception{
     SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
     try{

     return df.format(Calendar.getInstance().getTime());

     }catch (Exception e){
      throw new Exception("Unparseable Date");
     }

 }
    private void clearBoxInfo(batchPackForm bp){
        bp.setBoxId("");
        bp.setCost("");
        bp.setDoneOrders(new ArrayList());
        bp.setHeight("");
        bp.setLastOrder("");
        bp.setDepth("");
        bp.setBoxes(new ArrayList());
    }
}
