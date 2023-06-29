package com.owd.core.business.order.distributedOrderManagement;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.owd.core.business.order.LineItem;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderStatus;
import com.owd.core.business.order.distributedOrderManagement.Model.domFillable;
import com.owd.core.business.order.distributedOrderManagement.Model.domModel;
import com.owd.core.business.order.distributedOrderManagement.Model.domSku;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.List;

public class DOMUtilities {

    public static String _domClosest = "CLOSEST";

    public static List<String> getAvailableDomMethods(){
        List<String> l = new ArrayList<>();

        l.add("CLOSEST");
        return l;

    }


    public static List<domFillable> getClosestAndFillablePercentList(OrderStatus status) throws Exception{
        List<domFillable> facilities = new ArrayList<>();
        domModel dom = loadDomModelFromOrderStatus(status);
        String jsonModel = getDomModelAsJson(dom);
        Query q = HibernateSession.currentSession().createSQLQuery("execute DOM_Closest_Fillable :model");
        q.setParameter("model",jsonModel);
        List l = q.list();
        for(Object row:l){
            Object[] data = (Object[]) row;
            domFillable fill = new domFillable();
            fill.setLocCode(data[0].toString());
            fill.setFillable(Double.parseDouble(data[1].toString()));
            facilities.add(fill);
        }


        return facilities;
    }
    public static List<domFillable> getClosestAndFillablePercentList(Order order) throws Exception{
        List<domFillable> facilities = new ArrayList<>();
        domModel dom = loadDomModelFromOrder(order);
        String jsonModel = getDomModelAsJson(dom);
        Query q = HibernateSession.currentSession().createSQLQuery("execute DOM_Closest_Fillable :model");
        q.setParameter("model",jsonModel);
        List l = q.list();
        for(Object row:l){
            Object[] data = (Object[]) row;
            domFillable fill = new domFillable();
            fill.setLocCode(data[0].toString());
            fill.setFillable(Double.parseDouble(data[1].toString()));
            facilities.add(fill);
        }


        return facilities;
    }

    public static domModel loadDomModelFromOrderStatus(OrderStatus status) throws Exception{
        domModel dom = new domModel();
        dom.setClient_fkey(Integer.parseInt(status.client_id));
        if(status.shipping.shipAddress.zip.length()>3){
            dom.setZip(status.shipping.shipAddress.zip.substring(0,3));
        }else{
            dom.setZip(status.shipping.shipAddress.zip);
        }
        dom.setSkuCount(status.items.size());
        List<domSku> skus = new ArrayList<>();
        for (int i = 0; i < status.items.size(); i++) {
            //log.debug("checking for kit");
            domSku sku  = new domSku();

            LineItem item = (LineItem) status.items.elementAt(i);
            sku.setInventory_num(item.client_part_no);
            sku.setQty(item.getQuantityRequest());
            skus.add(sku);
        }
        dom.setSkus(skus);




        return dom;
    }


    public static domModel loadDomModelFromOrder(Order order) throws Exception{
        domModel dom = new domModel();
        dom.setClient_fkey(Integer.parseInt(order.getClientID()));
        if(order.getShippingAddress().zip.length()>3){
            dom.setZip(order.getShippingAddress().zip.substring(0,3));
        }else{
            dom.setZip(order.getShippingAddress().zip);
        }
        dom.setSkuCount(order.skuList.size());
       List<domSku> skus = new ArrayList<>();
        for (int i = 0; i < order.skuList.size(); i++) {
            //log.debug("checking for kit");
            domSku sku  = new domSku();
            LineItem item = (LineItem) order.skuList.elementAt(i);
            sku.setInventory_num(item.client_part_no);
            sku.setQty(item.getQuantityRequest());
            skus.add(sku);
        }
        dom.setSkus(skus);




        return dom;
    }

    public static String getDomModelAsJson(domModel dom){
        GsonBuilder builder = new GsonBuilder();

        Gson gson = builder.create();
        return gson.toJson(dom);
    }
}
