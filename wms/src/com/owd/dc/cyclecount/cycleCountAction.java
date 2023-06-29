package com.owd.dc.cyclecount;

import com.opensymphony.xwork2.ActionSupport;
import com.owd.dc.checkAuthentication;
import com.owd.dc.inventory.beans.invBean;
import com.owd.dc.inventory.upcBarcodeUtilities;
import com.owd.dc.inventorycount.wInventoryUtilities;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.hibernate.generated.WCycleCount;
import com.owd.hibernate.generated.WCycleCountLocation;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: 12/16/11
 * Time: 11:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class cycleCountAction extends ActionSupport {

   private String skuScan;
   private String qty;
   private String locScan;
   private invBean invInfo;
   private List<String> verifySkuList;
   private boolean canVerify;
    private int cycleCountId;
    private int cycleCountLocationId;
    private List<WCycleCountLocation> wccl = new ArrayList<WCycleCountLocation>();
    private List<WCycleCount> availableCounts = new ArrayList<WCycleCount>();

    public String execute(){
          //check for existing pick status..
            HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
       try{
        checkAuthentication.check(request,response);
       }catch (Exception e){
           e.printStackTrace();
           addActionError(e.getMessage());
           return "success";
       }
            int userId = wInventoryUtilities.getIdFromCookies(request);
              String user = (String) request.getAttribute("loginName");
        System.out.println(user);
        try{
           canVerify = wInventoryUtilities.isInventoryAdmin(userId,HibernateSession.currentSession());
           List<WCycleCount> countsForUser = CycleCountUtilities.getCurrentCycleCountsForUser(user,canVerify);
            if(countsForUser.size()>0){
               System.out.println("We have " + countsForUser.size() +" cycle counts available for " + user);
                for (WCycleCount count : countsForUser){
                    if(count.getAssignedTo().equals(user)){
                        System.out.println("WE found an assigned one");
                        loadCountInfo(count,HibernateSession.currentSession());
                        return "assignedCount";
                    }
                }
                availableCounts = countsForUser;
            }
        }catch(Exception e){
            addActionError(e.getMessage());
        }
        //load any verify available for inventory Admin


        return "success";
    }
    public static void main(String[] args){
         try{

           WCycleCount wcc = (WCycleCount) HibernateSession.currentSession().load(WCycleCount.class,1l);
             System.out.println(wcc.getwCycleCountLocations().size());
             System.out.println(CycleCountUtilities.getUncountedLocationsForCycleCount(wcc));
             System.out.println(wcc.getVerifyStatus());
             System.out.println(wcc.getVerifyStatus().equals(CycleCountUtilities.kStatusAssigned));
             for (WCycleCountLocation wcl:wcc.getwCycleCountLocations()){
                System.out.println(wcl.getScannedOn());
                 System.out.println(wcl.getScannedOn()==null);
             }

         } catch (Exception e){
             e.printStackTrace();
         }
    }
    private void loadCountInfo(long countId,Session sess) throws Exception{
            WCycleCount wcc = new WCycleCount();


        sess.load(wcc, countId);
    }

    private void loadCountInfo(WCycleCount wcc,Session sess) throws Exception{

                       OwdInventory inv = new OwdInventory();
                        sess.load(inv,(int) wcc.getInventoryId());
                        if (null == invInfo){
                invInfo = new invBean();
            }
            System.out.println(inv.getInventoryNum());
            invInfo.setDescription(inv.getDescription());
            invInfo.setInventoryId(inv.getInventoryId()+"");
            invInfo.setInventoryNum(inv.getInventoryNum());
             wccl.addAll(CycleCountUtilities.getUncountedLocationsForCycleCount(wcc));
        System.out.println(wccl.size()+"   this is the uncountd locations size");

    }
    public String countThisSku(){
        //check for barcode
          String sku = upcBarcodeUtilities.getSku(skuScan, 0);
         if(sku.equals("MULTI")){
             addActionError("Multiple barcode found. You must use OWD id number instead");
             return "error";
         }

        //create w_cycle_count records

           cycleCountId = 1;
         try{
           loadCountInfo(cycleCountId, HibernateSession.currentSession());

        }catch(Exception e){
            e.printStackTrace();
            addActionError("hi" + e.getMessage());
            return "error";
        }
       return "success";
    }

    public String countThisLocation(){
          //verify this location is in this request or else create this location for this request

        try{


          System.out.println(invInfo.getInventoryNum());
        }catch (Exception e){
            e.printStackTrace();

        }
        //pull location info and go on

        return "success";
    }


    public String countQty(){
        //insert qty for this location


        return "success";
    }



    public String getSkuScan() {
        return skuScan;
    }

    public void setSkuScan(String skuScan) {
        this.skuScan = skuScan;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getLocScan() {
        return locScan;
    }

    public void setLocScan(String locScan) {
        this.locScan = locScan;
    }

    public invBean getInvInfo() {
        return invInfo;
    }

    public void setInvInfo(invBean invInfo) {
        this.invInfo = invInfo;
    }

    public List<WCycleCountLocation> getWccl() {
        return wccl;
    }

    public void setWccl(List<WCycleCountLocation> wccl) {
        this.wccl = wccl;
    }

    public List<String> getVerifySkuList() {
        return verifySkuList;
    }

    public void setVerifySkuList(List<String> verifySkuList) {
        this.verifySkuList = verifySkuList;
    }

    public int getCycleCountId() {
        return cycleCountId;
    }

    public void setCycleCountId(int cycleCountId) {
        this.cycleCountId = cycleCountId;
    }

    public int getCycleCountLocationId() {
        return cycleCountLocationId;
    }

    public void setCycleCountLocationId(int cycleCountLocationId) {
        this.cycleCountLocationId = cycleCountLocationId;
    }
}
