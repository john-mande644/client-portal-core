package com.owd.dc.OnDemandPrinting.BollAndBranch;

import com.opensymphony.xwork2.ActionSupport;
import com.owd.dc.OnDemandPrinting.OnDemandInfoBean;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 7/13/14
 * Time: 3:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class bollBranchGiftPrintAction extends ActionSupport {
      private String orderNum;
    private String printer;
    private String orderId;

    public String exec(){

        return "success";
    }

    public String loadOrder(){
          try{
              orderNum =    orderNum.replaceAll("R\\d.","");
              OnDemandInfoBean ib = BollBanchUtils.isOnDemandOrderViaOrderNum(orderNum);
                  if (!ib.isOnDemand()){
                      addActionError("This order does not appear to be a valid gift message order");
                      return "error";
                  }
              orderId = ib.getOrderId();
          } catch(Exception e){
              e.printStackTrace();
              addActionError(e.getMessage());
              return "error";
          }


        return "success";
    }


    public String printMessage(){
         try{

             Boolean good = BollBanchUtils.printGiftCard(orderId,printer);
             if(!good){
                 addActionError("Something went wrong try again");

             }
             addActionMessage("It will print shortly. Please make sure it looks good.");
         }catch (Exception e){
             e.printStackTrace();
             addActionError(e.getMessage());
             addActionError("Please contact IT for help");
         }

        return "success";
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getPrinter() {
        return printer;
    }

    public void setPrinter(String printer) {
        this.printer = printer;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
