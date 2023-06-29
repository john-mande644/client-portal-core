package com.owd.dc.returns;

import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdLineItem;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.dc.inventory.multiSkuForm;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Oct 10, 2005
 * Time: 8:58:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class returnUtilities {
    public static HashMap getOrderItemList(String id,HttpServletRequest req, HttpServletResponse resp) throws Exception {
        try {
            OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, (new Integer(id)));
            //retruns a HashMap of Sku's with the order ID given
            Iterator it = order.getLineitems().iterator();
            HashMap skuList = new HashMap();
            List skus = new ArrayList();
            int i=1;
            while (it.hasNext()) {

                OwdLineItem line = (OwdLineItem) it.next();
                System.out.println("in it next");
                multiSkuForm info = new multiSkuForm();
                info.setInventoryId(line.getOwdInventory().getInventoryId().toString());
                info.setInventoryNum(line.getInventoryNum());
                info.setDescription(line.getDescription());
                skuList.put(""+i, info);
                skus.add(info.getInventoryId());
                i++;
            }

            System.out.println("return");
            System.out.println(skus);
            req.getSession(true).setAttribute("orderskulist",skus);
           Cookie cookie2 = new Cookie("returncid", order.getClientFkey()+"");

                        resp.addCookie(cookie2);
            return skuList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Unable to find data");

        }

    }
}
