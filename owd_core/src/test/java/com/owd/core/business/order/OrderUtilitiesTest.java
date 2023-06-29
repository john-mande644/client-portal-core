package com.owd.core.business.order;

import com.owd.core.business.owdChoiceList;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.generated.OwdOrderShipInfo;
import org.junit.Test;
import static org.junit.Assert.*;

public class OrderUtilitiesTest {


    @Test
    public void isPOTest() {
        try{

            OwdOrderShipInfo info = (OwdOrderShipInfo) HibernateSession.currentSession().load(OwdOrderShipInfo.class,17361601);
            assertFalse("This should be falst",OrderUtilities.isPO(info));

            OwdOrderShipInfo addressOne = (OwdOrderShipInfo) HibernateSession.currentSession().load(OwdOrderShipInfo.class, 17361607);
            assertTrue("Address one should be true",OrderUtilities.isPO(addressOne));

            OwdOrderShipInfo addressTwo = (OwdOrderShipInfo) HibernateSession.currentSession().load(OwdOrderShipInfo.class,17362500);
            System.out.println(addressTwo.getShipAddressTwo());
            assertTrue("Address two should be True", OrderUtilities.isPO(addressTwo));

        }catch (Exception e){
            fail();
        }

    }

    @Test
    public void isAPOTest(){
        try {
            OwdOrderShipInfo info = (OwdOrderShipInfo) HibernateSession.currentSession().load(OwdOrderShipInfo.class, 17361601);
            assertFalse("This should be falst", OrderUtilities.isApo(info));

            OwdOrderShipInfo city = (OwdOrderShipInfo) HibernateSession.currentSession().load(OwdOrderShipInfo.class, 17362848);
            assertTrue("This should be True city", OrderUtilities.isApo(city));

            OwdOrderShipInfo state = (OwdOrderShipInfo) HibernateSession.currentSession().load(OwdOrderShipInfo.class, 17305853);
            assertTrue("This should be True state", OrderUtilities.isApo(state));


        }catch (Exception e){
            fail();
        }
    }

    @Test
    public void isAPOPOTest(){
        try {
            OwdOrderShipInfo info = (OwdOrderShipInfo) HibernateSession.currentSession().load(OwdOrderShipInfo.class, 17361601);
            assertFalse("This should be falst", OrderUtilities.isAPOOrPO(info));

            OwdOrderShipInfo city = (OwdOrderShipInfo) HibernateSession.currentSession().load(OwdOrderShipInfo.class, 17362848);
            assertTrue("This should be True city", OrderUtilities.isAPOOrPO(city));

            OwdOrderShipInfo state = (OwdOrderShipInfo) HibernateSession.currentSession().load(OwdOrderShipInfo.class, 17305853);
            assertTrue("This should be True state", OrderUtilities.isAPOOrPO(state));

            OwdOrderShipInfo addressOne = (OwdOrderShipInfo) HibernateSession.currentSession().load(OwdOrderShipInfo.class, 17361607);
            assertTrue("Address one should be true",OrderUtilities.isAPOOrPO(addressOne));

            OwdOrderShipInfo addressTwo = (OwdOrderShipInfo) HibernateSession.currentSession().load(OwdOrderShipInfo.class,17362500);
            System.out.println(addressTwo.getShipAddressTwo());
            assertTrue("Address two should be True", OrderUtilities.isAPOOrPO(addressTwo));

        }catch (Exception e){
            fail();
        }
    }


    @Test
    public void When_request_international_tax_and_duty_options_then_ddu_ddp_should_be_returned() throws Exception {
        String dduValue = "DDU";
        String ddpValue = "DDP";

        owdChoiceList choiseList = OrderUtilities.getIntlTaxAndDutyList();

        assertEquals(2, choiseList.getValues().size());
        assertTrue(choiseList.getValues().contains(dduValue));
        assertTrue(choiseList.getValues().contains(ddpValue));
    }

}
