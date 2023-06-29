package com.owd.web.returns;

import com.opensymphony.xwork2.ActionSupport;
import com.owd.core.TagUtilities;
import com.owd.core.business.order.OrderManager;
import com.owd.fedEx.SmartPostReturn;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;

import java.util.Map;

/**
 * Created by danny on 11/23/2019.
 */
public class ReturnLabelAction extends ActionSupport {


    private String orderId;
    private String imageUrl="";
    private OwdOrder order;



    public String create(){

        try {
            if(null==orderId || orderId.length()==0) throw new Exception("Invalid order");

            order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,Integer.parseInt(orderId));

            SmartPostReturn.generateAndSaveLabel(order);

            Map<String,String> tags = TagUtilities.getTagMap("ORDER",order.getOrderId());

            if(tags.containsKey("COM.OWD.RETURN.LABEL")){
                imageUrl = tags.get("COM.OWD.RETURN.LABEL");
            }else{
                throw new Exception("Unable to load label, please return to extranet and reload to view label");
            }

        }catch (Exception e){
            e.printStackTrace();
            addActionError("Unable to Generate Label: "+e.getMessage());
        }



        return "success";
    }
    public String view(){

        try {
            if(null==orderId || orderId.length()==0) throw new Exception("Invalid order");

            order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,Integer.parseInt(orderId));



            Map<String,String> tags = TagUtilities.getTagMap("ORDER",order.getOrderId());

            if(tags.containsKey("COM.OWD.RETURN.LABEL")){
                imageUrl = tags.get("COM.OWD.RETURN.LABEL");
            }else{
                throw new Exception("Unable to load label, please return to extranet and reload to view label");
            }

        }catch (Exception e){
            e.printStackTrace();
            addActionError("Unable to Generate Label: "+e.getMessage());
        }



        return "success";
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public OwdOrder getOrder() {
        return order;
    }

    public void setOrder(OwdOrder order) {
        this.order = order;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
