package com.owd.fedEx;


import com.owd.core.TagUtilities;
import com.owd.core.business.order.Order;
import com.owd.fedEx.ShipService.ProcessShipmentReply;
import com.owd.fedEx.ShipService.ProcessShipmentRequest;
import com.owd.fedEx.ShipService.WebAuthenticationDetail;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OrderShipInfo2;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.generated.OwdOrderShipInfo;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import static java.awt.SystemColor.info;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by danny on 11/11/2018.
 */
public class SmartPostReturnTest {



    @Test
    public void generateAndSaveLabelTest(){


        try{
            OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, 16815052);
            SmartPostReturn.generateAndSaveLabel(order,false,false);
        }catch (Exception e){
            e.printStackTrace();
            fail();
        }




    }
    @Test
    public void addReturnTrackingToOrderTest(){
        try {
            OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, 16815051);

           SmartPostReturn.addReturnTrackingToOrder(order,"12345678");
            Criteria crit = HibernateSession.currentSession().createCriteria(OrderShipInfo2.class);

            crit.add(Restrictions.eq("orderFkey", order.getOrderId()));
            List<OrderShipInfo2> l = crit.list();

            OrderShipInfo2 info = l.get(0);
            assertEquals("12345678",info.getReturnTracking());
            info.setReturnTracking("");
            HibernateSession.currentSession().saveOrUpdate(info);
            HibUtils.commit(HibernateSession.currentSession());


        }catch (Exception e){
            e.printStackTrace();
            fail();
        }

    }
    @Test
    public void doesOrderNeedReturnLabelTest(){

        try {
            OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, 16815051);

            assertEquals(1, SmartPostReturn.doesOrderNeedReturnLabel(order));

            order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, 16815060);
            assertEquals(0, SmartPostReturn.doesOrderNeedReturnLabel(order));

        }catch (Exception e){
            e.printStackTrace();
            fail();
        }



    }
    @Test
    public void getReturnLabelFromAmazonTest(){
        try{
            OwdOrder order= (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,16815060);
        byte[] image = SmartPostReturn.getLabelFromAmazonBucket(order);
            assertTrue(image.length>0);


        }catch (Exception e){
            e.printStackTrace();
            fail();
        }

    }
    @Test
    public void addLabelToAmazonImageBucket(){

        try{
            OwdOrder order= (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,16815060);
            File label = new File("src\\test\\java\\com\\owd\\core\\managers\\helperFiles\\SmartReturnTestLabel.png");
            byte[] image = Files.readAllBytes(label.toPath());
            boolean success = SmartPostReturn.addLabelToAmazonBucket("123456789",order,image);
            assertTrue(success);


        }catch (Exception e){
            fail();
        }

    }
    @Test
    public void setSmartPostFlagViaOrder(){
        Order order = new Order("55");
        try {
            order.setSmartPostReturnFlag();
            assertEquals(order.getTagMap().size(),1);
            assertEquals(order.getTagMap().get(TagUtilities.kSPReturn),"1");

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void martOrderForSmartPostShipmentViaOrderIdTest(){

        try {
           Map<String,String> tags =  TagUtilities.getTagMap("ORDER",16815060);
            assertTrue(tags.size() == 0);
            SmartPostReturn.martOrderForSmartPostShipmentViaOrderId(16815060);
            tags =  TagUtilities.getTagMap("ORDER",16815060);
            assertTrue(tags.size() == 1);
            //Cleanup
            TagUtilities.deleteTag(16815060,"ORDER",TagUtilities.kSPReturn);





        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Test
    public void getSmartPostReturnLabelForOwdOrderDC1Live(){
        try{
            OwdOrder order= (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,20381468);
            ProcessShipmentReply reply = SmartPostReturn.getSmartPostReturnLabelForOwdOrder(order,true);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void getSmartPostReturnLabelForOwdOrderDC6Live(){
        try{
            OwdOrder order= (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,16815060);
            ProcessShipmentReply reply = SmartPostReturn.getSmartPostReturnLabelForOwdOrder(order,false);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void getSmartPostReturnLabelForOwdOrderDC6Test(){
        try{
            OwdOrder order= (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,16815060);
            ProcessShipmentReply reply = SmartPostReturn.getSmartPostReturnLabelForOwdOrder(order,true,false);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void loadSmartPostReturnShipmentFromOwdOrderTest(){
        try{
            OwdOrder order= (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,16815060);


            ProcessShipmentRequest request = SmartPostReturn.loadSmartPostReturnShipmentFromOwdOrder(order,true);
            assertEquals(request.getRequestedShipment().getSmartPostDetail().getHubId(),"5531");
            assertEquals(request.getClientDetail().getAccountNumber(),"612041369");




        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void isSmartPostReturnItemTest(){
        try{

            OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, 19669517);
            assertEquals(true, SmartPostReturn.isSmartPostReturnItem(order));

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}