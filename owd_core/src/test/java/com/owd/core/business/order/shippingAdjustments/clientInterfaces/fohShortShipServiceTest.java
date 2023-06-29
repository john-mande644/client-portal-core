package com.owd.core.business.order.shippingAdjustments.clientInterfaces;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.owd.core.business.order.beans.shortShipBean;
import com.owd.core.business.order.shippingAdjustments.clientInterfaces.dataBeans.FOHShortShipData;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by danny on 10/8/2019.
 */
public class fohShortShipServiceTest {

    @Test
    public void getJsonBodyTest(){
        try{
            Integer orderId = 19142716;
            Integer clientId = 640;
            List<shortShipBean> items = new ArrayList<>();
            shortShipBean ss = new shortShipBean();
            ss.setLineItemId(40331363);
            ss.setQtyToAdjust(1);
            ss.setReason("stockout");
            items.add(ss);

            String s = fohShortShipService.getJsonBody(items,clientId,orderId);
            GsonBuilder builder = new GsonBuilder();

            Gson gson = builder.create();
            FOHShortShipData data = gson.fromJson(s,FOHShortShipData.class);
            System.out.println(s);
            assertTrue(data.getLine_items().size()==1);
            assertEquals("19499909",data.getOrder_reference());



        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }
}
