package com.owd.alittlePlaying.orderthings.ratingThings;

import com.owd.core.CountryNames;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.ShippingInfo;
import com.owd.core.csXml.OrderRater;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danny on 8/22/2019.
 */
public class rerateOrders {




    public static void main(String args[]){


        List<Integer> l = new ArrayList<>();
        l.add(17798505);
        l.add(17856029);
        l.add(17856030);
        l.add(17891695);
        l.add(17997845);
        l.add(18001015);
        l.add(18009833);
        l.add(18034520);
        l.add(18034702);
        l.add(18032793);
        l.add(18078989);
        l.add(18003458);
        l.add(18105075);
        l.add(17996515);
        l.add(17952207);
        l.add(18127738);
        l.add(18127902);
        l.add(18127928);
        l.add(18139588);
        l.add(18202354);
        l.add(18203752);
        l.add(18204082);
        l.add(18204749);
        l.add(18240429);
        l.add(18269744);
        l.add(18280459);
        l.add(18303069);
        l.add(18303070);
        l.add(18308034);
        l.add(18318292);
        l.add(17952205);
        l.add(18338080);
        l.add(17929037);
        l.add(18338953);
        l.add(18341734);
        l.add(18360734);
        l.add(18362194);
        l.add(18366658);
        l.add(17902645);
        l.add(18372395);
        l.add(18376867);
        l.add(17895269);
        l.add(18425745);
        l.add(18446021);
        l.add(18465960);
        l.add(18468793);
        l.add(17893638);
        l.add(18481779);
        l.add(17893283);
        l.add(18493863);
        l.add(18494382);
        l.add(17887955);
        l.add(17858562);
        l.add(18503655);
        l.add(18516559);
        l.add(18521200);
        l.add(18526031);
        l.add(18547891);
        l.add(17838638);
        l.add(18551836);
        l.add(18553448);
        l.add(18558975);
        l.add(18569583);
        l.add(18574601);
        l.add(18586760);
        l.add(18588141);
        l.add(18590358);
        l.add(17824043);
        l.add(18606506);
        l.add(18607803);
        l.add(18623578);
        l.add(17822873);
        l.add(17822133);
        l.add(17780225);
        l.add(18055110);
        l.add(18114616);
        l.add(18117767);
        l.add(17960107);
        l.add(17959686);
        l.add(18338785);
        l.add(18369042);
        l.add(18478522);
        l.add(18492188);
        l.add(18495091);
        l.add(18497907);
        l.add(17874411);
        l.add(17873790);
        l.add(18551568);
        l.add(18598194);
        l.add(17832308);
        l.add(17832307);
        l.add(18088504);
        l.add(17968400);
        l.add(18333892);
        l.add(18379403);


        StringBuilder sb = new StringBuilder();
        sb.append("orderNum,Orignial Cost,New Cost\n");
        for(Integer i:l){
            sb.append(getRerate(i,"UPS.2DA"));
        }

        System.out.println(sb.toString());

    }

    public static String getRerate(Integer orderId, String methodCode){
        String s = "";

        try{
            OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,orderId);
            String oCost = getOriginalCost(order);
            String newCost = getNewRate(order,methodCode);

            System.out.println(oCost);
            System.out.println(newCost);
            s = order.getOrderNum() + ","+ oCost + "," + newCost+"\n";
        }catch (Exception e){
            e.printStackTrace();

        }


        return s;
    }

    public static String getNewRate(OwdOrder oorder, String method) throws Exception{
        Order order = new Order(oorder.getClientFkey()+"");
        //order.setSignatureRequired(true);
        ShippingInfo shi = new ShippingInfo();

        order.getShippingAddress().address_one = oorder.getShipinfo().getShipAddressOne();
        order.getShippingAddress().city=oorder.getShipinfo().getShipCity();
        order.getShippingAddress().zip=oorder.getShipinfo().getShipZip();
        order.getShippingAddress().state=oorder.getShipinfo().getShipState();
        order.getShippingAddress().country=oorder.getShipinfo().getShipCountry();

       /* order.getBillingAddress().address_one = "10 1st aVe E";
        order.getBillingAddress().city="Mobridge";
        order.getBillingAddress().zip="57601";
        order.getBillingAddress().state="SD";
        order.getBillingAddress().country=CountryNames.getCountryName("United States");
*/



       // order.addLineItem("Ebay Items", 1, 0.00f, 0.00f, "test", "", "");

        List<String> codes = new ArrayList<>();
        codes.add(method);
        OrderRater rater = new OrderRater(order);
        rater.setForcePackagingWeight(oorder.getShippedWeight().floatValue());
        rater.setForcePackageZeroWeight(true);
        rater.setOriginCode("DC1");
        rater.rate(codes);
        System.out.println(rater.theResponse);
        return rater.theResponse.get(0).finalRate + "";
    }





    public static String getOriginalCost(OwdOrder order){

        String sql = "select amount from sp_bill_shipping where order_id = :orderId and amount > 0 and activity = 'Carrier Adjustment'";
        try{
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("orderId",order.getOrderId());

            return q.list().get(0).toString();
        }catch (Exception e){
            e.printStackTrace();
        }


        return "";
    }
}
