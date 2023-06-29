package com.owd.core.business.order;
import com.owd.core.business.Address;
import  com.owd.core.tests.BaseTest;
import com.owd.core.managers.AddressManagerTest;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OrderShipInfo2;
import com.owd.hibernate.generated.OwdClient;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.generated.OwdOrderShipInfo;
import org.hibernate.Query;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;



public class OwdOrderTests extends  BaseTest {

    @Override
    public void setUp() {
        super.setUp();
    }


    @Test
    public  void  When_Create_Owd_Order_Than_Order_Should_Created() throws Exception {
        String facilityName = "DC1";
        String clientName = "ACME BAR-B-Q";
        OwdClient client = loadClient(clientName);

        Address billingAddress = createDefaultBillingAddress();
        OwdOrderShipInfo shipInfo = createDefaultShipInfo();
        OrderShipInfo2 shipInfo2 = createDefaultShipInfo2();

        OwdOrder order = createOrder(client, facilityName, billingAddress, shipInfo,shipInfo2);


        if (order != null) {
            deleteOrder(order.getOrderId());
        }
    }

    private OrderShipInfo2 createDefaultShipInfo2() {
        OrderShipInfo2 shipInfo2 = new OrderShipInfo2();
        shipInfo2.setAesItn("");
        shipInfo2.setBwayText("");
        shipInfo2.setBwayType(0);
        shipInfo2.setPackageRef("");

        shipInfo2.setShipperAddressOne("Shipper address 1");
        shipInfo2.setShipperAddressTwo("Shipper address 2");
        shipInfo2.setShipperCity("Shipper City");
        shipInfo2.setShipperCompany("Shipper Company");
        shipInfo2.setShipperName("Shipper Name");
        shipInfo2.setShipperPhone("33-33-33");
        shipInfo2.setShipperState("Shipper State");
        shipInfo2.setShipperZip("shipper_zip");

        shipInfo2.setReturnAddressOne("Return address 1");
        shipInfo2.setReturnAddressTwo("Return address 2");
        shipInfo2.setReturnCity("Return city");
        shipInfo2.setReturnCountry("USA");
        shipInfo2.setReturnCountryRef("UNITED_STATES");
        shipInfo2.setReturnTracking("");
        shipInfo2.setReturnName("");
        shipInfo2.setReturnPhone("44-44-44");
        shipInfo2.setReturnState("return_state");
        shipInfo2.setReturnZip("return_zip");
        return shipInfo2;
    }

    private OwdOrderShipInfo createDefaultShipInfo() {
        OwdOrderShipInfo shipInfo = new OwdOrderShipInfo();

        shipInfo.setShipCompanyName("Ship to Company");
        shipInfo.setShipAddressOne("Ship To Address1");
        shipInfo.setShipAddressTwo("Ship to Address2");
        shipInfo.setShipCity("Ship to city");
        shipInfo.setShipState("Ship to state");
        shipInfo.setShipZip("shipping_zip");
        shipInfo.setShipCountry("USA");
        shipInfo.setAvsOverRide(false);
        shipInfo.setCallTag("");
        shipInfo.setCarrFreightTerms("Prepaid");
        shipInfo.setCarrFreightTermsRefNum("SHIPPER");
        shipInfo.setCarrService("Ground");
        shipInfo.setCarrServiceRefNum("COM_OWD_FLATRATE_GROUND");
        shipInfo.setCodCharge(new BigDecimal(0));
        shipInfo.setComments("");
        shipInfo.setCustomsDesc("");
        shipInfo.setStatus(0);
        shipInfo.setCustomsValue(new BigDecimal(0));
        shipInfo.setDeclaredValue(new BigDecimal(0));
        shipInfo.setShipCountryRefNum("UNITED_STATES");
        shipInfo.setShipEmailAddress("ship@test.test");
        shipInfo.setShipFirstName("Shiply");
        shipInfo.setShipLastName("Shiplian");
        shipInfo.setShipFaxNum("22-22-22");
        shipInfo.setShipPhoneNum("33-33-33");
        shipInfo.setScheduledShipDate(new Date());
        shipInfo.setShipTitle("");
        shipInfo.setThirdPartyRefnum("");
        shipInfo.setWhseNotes("");
        return shipInfo;
    }

    private Address createDefaultBillingAddress() {
        Address billingAddress = new Address();

        billingAddress.setCompany_name("Test Company");
        billingAddress.setAddress_one("Bill To Address1");
        billingAddress.setAddress_two("bill to Address2");
        billingAddress.setCity("bill to city");
        billingAddress.setState("bill to state");
        billingAddress.setZip("billing_zip");
        billingAddress.setCountry("USA");
        return billingAddress;
    }

    private OwdOrder createOrder(OwdClient client, String facilityName, Address billingAddress, OwdOrderShipInfo shippingInfo, OrderShipInfo2 shipInfo2) throws Exception {
        OwdOrder order = new OwdOrder();
        order.setClient(client);
        order.setClientFkey(client.getClientId());
        order.setFacilityCode(facilityName);

        order.setOrderNum("ORDERNUM");
        order.setIsFutureShip(0);
        order.setIsVoid(0);
        order.setBackorderLevel(0);
        order.setDiscountPct(new BigDecimal(0));
        order.setBusinessOrder(true);

        order.setBillCompanyName(billingAddress.company_name);
        order.setBillAddressOne(billingAddress.address_one);
        order.setBillAddressTwo(billingAddress.address_two);
        order.setBillCity(billingAddress.city);
        order.setBillState(billingAddress.state);
        order.setBillZip(billingAddress.zip);
        order.setBillCountry(billingAddress.country);
        order.setBillEmailAddress("bill@test.test");
        order.setBillFirstName("Billy");
        order.setBillLastName("Billian");
        order.setBillFaxNum("00-00-00");
        order.setBillPhoneNum("11-11-11");

        order.setShipinfo(shippingInfo);
        shippingInfo.setOrder(order);

        order.setShipInfo2(shipInfo2);

        updateOWDOrder(order);
        return order;
    }

    private OwdClient loadClient(String clientName) throws Exception {
        Query clientFilter = HibernateSession.currentSession().createSQLQuery(
                "select * from owd_client where company_name = '"+clientName+"'").addEntity(OwdClient.class);
        List list = clientFilter.list();
        OwdClient client = (OwdClient) list.get(0);
        return  client;
    }

    private void deleteOrder(int orderId) throws  Exception {
        OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, orderId);
        OwdOrderShipInfo shipInfo = order.getShipinfo();
        OrderShipInfo2 shipInfo2 = order.getShipInfo2();
        if (shipInfo != null) {
            HibernateSession.currentSession().delete(shipInfo);
        }
        if(shipInfo2!=null) {
            HibernateSession.currentSession().delete(shipInfo2);
        }
        HibernateSession.currentSession().delete(order);
        HibUtils.commit(HibernateSession.currentSession());
    }

    @Test
    public void When_load_owd_order_then_ship_info2_is_loaded_too() throws Exception {
        OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, 18012480);
        OrderShipInfo2 shipInfo2 = order.getShipInfo2();
        assertNotNull(shipInfo2);
    }

    @Test
    public void When_save_owd_order_than_ship_info2_aes_itn_should_be_saved() throws Exception {
        Integer orderId = 18012480;
        OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, orderId);
        OrderShipInfo2 shipInfo2 = order.getShipInfo2();
        String updatedAESITN = "AES_ITN_TEST_2";
        shipInfo2.setAesItn(updatedAESITN);
        AddressManagerTest test = null;
        updateOWDOrder(order);

        OwdOrder reloadedFomDbOrder = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, orderId);
        OrderShipInfo2 updatedShipInfo = reloadedFomDbOrder.getShipInfo2();
        assertEquals(updatedAESITN, updatedShipInfo.getAesItn());
    }

    @Test
    public void When_save_owd_order_than_updated_customs_account_should_be_saved_too() throws Exception {
        Integer orderId = 18012480;
        OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, orderId);
        Integer ddpCustomsAccountValue = 1;
        Integer dduCustomsAccountValue = 0;

        order.setNoCustomsAccount(ddpCustomsAccountValue);
        updateOWDOrder(order);

        OwdOrder reloadedFomDbOrder = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, orderId);
        assertEquals(ddpCustomsAccountValue, reloadedFomDbOrder.getNoCustomsAccount());


        order.setNoCustomsAccount(dduCustomsAccountValue);
        updateOWDOrder(order);

        reloadedFomDbOrder = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, orderId);
        assertEquals(dduCustomsAccountValue, reloadedFomDbOrder.getNoCustomsAccount());
    }

    private void updateOWDOrder(OwdOrder order) throws Exception {
        HibernateSession.currentSession().saveOrUpdate(order);
        HibernateSession.currentSession().saveOrUpdate(order.getShipinfo());
        HibernateSession.currentSession().saveOrUpdate(order.getShipInfo2());
//        HibernateSession.currentSession().flush();
        HibUtils.commit(HibernateSession.currentSession());
    }
}
