package com.owd.dc.warehouse.misc;

import com.opensymphony.xwork2.ActionSupport;
import com.owd.WMSUtils;
import com.owd.core.business.order.OrderFactory;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.generated.OwdOrderShipHold;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 10/3/13
 * Time: 11:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class checkDCHoldAction extends ActionSupport{
    private String orderNum;
    private Boolean onHold;

    public String execute(){

        return "success";
    }

    public String check(){
        try{
          orderNum = WMSUtils.getOrderNumberFromString(orderNum);

        OwdOrder order = OrderFactory.getOwdOrderFromOwdReference(orderNum);
        OwdOrderShipHold holder = order.getHoldinfo();
        if (holder.getIsOnWhHold().intValue() == 1) {
            onHold = true;
        } else {
           onHold = false;
        }
        }catch(Exception e){
            e.printStackTrace();

        }
        return "success";

    }


    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Boolean getOnHold() {
        return onHold;
    }

    public void setOnHold(Boolean onHold) {
        this.onHold = onHold;
    }
}
