package com.owd.core.business.order.distributedOrderManagement;

import com.owd.core.business.order.Order;
import com.owd.core.business.order.ShippingInfo;
import com.owd.core.business.order.distributedOrderManagement.Model.domFillable;
import com.owd.core.business.order.distributedOrderManagement.Model.domModel;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class domUtilitiesTest {





    @Test
    public void getClosestAndFillableTestINTL(){
        Order order = new Order("55");
        ShippingInfo info = new ShippingInfo();
        info.shipAddress.zip = "ABE 123";
        order.setShippingInfo(info);
        try {
            order.addLineItem("Ebay Items", 3, 0.0f, 0.0f, "Test", "", "");
            //  domModel dom = DOMUtilities.loadDomModelFromOrder(order);
            List<domFillable> facilities = DOMUtilities.getClosestAndFillablePercentList(order);
            assertEquals(new Integer(2),new Integer(facilities.size()));

        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void getClosestAndFillableTest(){
        Order order = new Order("55");
        ShippingInfo info = new ShippingInfo();
        info.shipAddress.zip = "57601";
        order.setShippingInfo(info);
        try {
            order.addLineItem("Ebay Items", 3, 0.0f, 0.0f, "Test", "", "");
          //  domModel dom = DOMUtilities.loadDomModelFromOrder(order);
            List<domFillable> facilities = DOMUtilities.getClosestAndFillablePercentList(order);
            assertEquals(new Integer(2),new Integer(facilities.size()));

        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void loadJsonFromDomModelTest(){
        Order order = new Order("55");
        ShippingInfo info = new ShippingInfo();
        info.shipAddress.zip = "57601";
        order.setShippingInfo(info);
        try {
            order.addLineItem("Ebay Items", 3, 0.0f, 0.0f, "Test", "", "");
            domModel dom = DOMUtilities.loadDomModelFromOrder(order);
           // System.out.println(DOMUtilities.getDomModelAsJson(dom));
            assertEquals("{\"zip\":\"576\",\"client_fkey\":55,\"skuCount\":1,\"skus\":[{\"inventory_num\":\"Ebay Items\",\"qty\":3}]}",DOMUtilities.getDomModelAsJson(dom));

        }catch (Exception e){
            e.printStackTrace();
            fail();
        }


    }

    @Test
    public void loadDomModelFromOrderTest(){

        Order order = new Order("55");
        ShippingInfo info = new ShippingInfo();
        info.shipAddress.zip = "57601";
        order.setShippingInfo(info);
        try {
            order.addLineItem("Ebay Items", 3, 0.0f, 0.0f, "Test", "", "");
            domModel dom = DOMUtilities.loadDomModelFromOrder(order);

         assertEquals(1+"",dom.getSkuCount()+"");
         assertEquals(new Integer(55),dom.getClient_fkey());
         assertEquals("576", dom.getZip());


         /// use no zip should be blank
            info = new ShippingInfo();
            info.shipAddress.zip = "";
            order.setShippingInfo(info);
             dom = DOMUtilities.loadDomModelFromOrder(order);
            assertEquals("", dom.getZip());

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
