package com.owd.dc.warehouse.misc;

import com.opensymphony.xwork2.ActionSupport;
import com.owd.core.business.order.WarehouseHold.holdUtilities;
import com.owd.dc.inventorycount.wInventoryUtilities;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 10/6/13
 * Time: 9:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class workOrderHoldAction extends ActionSupport{

    private String workOrder;
    private String orderNum;


    public String execute(){

        return "success";
    }

    public String set(){
         if(orderNum.length()==0){
             addActionError("Plese enter order Num");

         }
        if(workOrder.length()==0){
            addActionError("Please enter Work Order");


        }

        if(getActionErrors().size()>0){
            return "success";
        }
        HttpServletRequest request = ServletActionContext.getRequest();
        String name = wInventoryUtilities.getNameFromCookies(request);
         System.out.println(orderNum);
        System.out.println(name);
        System.out.println(workOrder);

        String result =holdUtilities.setWorkOrderDCHold(orderNum,name,workOrder);

        if(result.equals("Good")){
          addActionMessage(orderNum + " has been set on Work Order Hold");

        }else{
            addActionError(result);
        }



        return "success";
    }


    public String getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(String workOrder) {
        this.workOrder = workOrder;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
}
