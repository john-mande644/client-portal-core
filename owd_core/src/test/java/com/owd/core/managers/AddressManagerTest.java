package com.owd.core.managers;

import com.owd.core.business.Address;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.generated.OwdOrderShipInfo;
import org.junit.Test;

import static org.junit.Assert.*;
/**
 * Created by danny on 12/26/2018.
 */
public class AddressManagerTest {


    /**
     * Test to make sure each part of the address gets checked for invalid characters.
     * Setting ?? in one field at a time and test. Method throws Exception when invalid characters are found
     * Test a good address should not throw Exception
     */
    @Test
    public void invalidCharacterCheckForAddressTest(){
        Address address = new Address();

        address.setCompany_name("AA??");
        String error = "";
        try{
            AddressManager.invalidCharacterCheckForAddress(address);
        }catch (Exception e){
            error = e.getMessage();
        }
        assertTrue(error.contains("Invalid Company Name"));

        address.setCompany_name("One World Direct");
        address.setAddress_one("10 1??  ??");
        error = "";
        try{
            AddressManager.invalidCharacterCheckForAddress(address);
        }catch (Exception e){
            error = e.getMessage();
        }
        assertTrue(error.contains("Invalid Address Line One"));

        address.setAddress_one("10 1st Ave E");
        address.setAddress_two(" box ?? 234");

        error = "";
        try{
            AddressManager.invalidCharacterCheckForAddress(address);
        }catch (Exception e){
            error = e.getMessage();
        }
        assertTrue(error.contains("Invalid Address Line Two"));

        address.setAddress_two("");
        address.setCity("Mobridg??");
        error = "";

        try{
            AddressManager.invalidCharacterCheckForAddress(address);
        }catch (Exception e){
            error = e.getMessage();
        }

        assertTrue(error.contains("Invalid City"));

        address.setCity("Mobridge");
        address.setState("SD??");

        error = "";

        try{
            AddressManager.invalidCharacterCheckForAddress(address);
        }catch (Exception e){
            error = e.getMessage();
        }

        assertTrue(error.contains("Invalid State"));

        address.setState("SD");

        try{
            AddressManager.invalidCharacterCheckForAddress(address);
        }catch (Exception e){
            fail("Address should have passed and no exception throws, instead: "+e.getMessage());
        }


    }

    @Test
    public void checkAndCorrectAddressForLocation() {
        Address address = new Address();

        // order 26656416
        /*address.setAddress_one("Anna Maria Casone");
        address.setAddress_two("11");
        address.setCity("Ornavasso ");
        address.setState("Italy");
        address.setZip("28877");
        address.setCountry("USA");*/

        // order 26785162
        address.setAddress_one("Via Per Trezzo 39M");
        address.setAddress_two("First floor ");
        address.setCity("Vaprio DAdda");
        address.setState("Italy");
        address.setZip("20069 ");
        address.setCountry("USA");

//        address.setState("New Hampshire");
//        address.setState("WASHINGTON ");
//        address.setState("maine");
//        address.setState("Alabama");
//        address.setState("alabama");
//        address.setState("NH");
//        address.setState("KS ");


        /*address.setAddress_one("1730 OLIVE AVE");
//        address.setAddress_two("");
        address.setCity("SANTA BARBARA");
        address.setState("CA");
        address.setZip("93101-1021");
        address.setCountry("USA");*/



        String err = "";

        try {
//            AddressManager.checkAndCorrectAddressForLocation(address, "CONNECTSHIP_UPS.UPS", "DC6");
            //AddressManager.checkAndCorrectAddressForLocation(address, "TANDATA_USPS.USPS", "DC6");
            AddressManager.checkAndCorrectAddressForLocation(address, "TANDATA_USPS.USPS", "DC6");


        }catch (Exception e){
            err = e.getMessage();
        }
//        assertTrue(err.contains("Invalid State"));
//        assertTrue(err.contains("Invalid Zip Code"));

//        System.out.println(err);

    }

    @Test
    public void isAddressValid() {
        Address address = new Address();

        address.setAddress_one("1730 OLIVE AVE");
//        address.setAddress_two("");
        address.setCity("SANTA BARBARA");
        address.setState("CA");
        address.setZip("93101-1021");
        address.setCountry("USA");

        String err = "";

        try {
            AddressManager.isAddressValid(address);
        }catch (Exception e){
            err = e.getMessage();
        }

    }


    /**
     * Tests wiht invalid Staates, PO Box, oversea territory addresses, lowercase/ uppercase
     */
    @Test
    public void isAddressStateValid() {
        Address address = new Address();

        String err = "";

        try {
            // Test false
            address.setState("Italy");
            assertFalse(AddressManager.isAddressStateValid(address.state));

            address.setState("USA");
            assertFalse(AddressManager.isAddressStateValid(address.state));

            address.setState("90065");
            assertFalse(AddressManager.isAddressStateValid(address.state));

            address.setState("");
            assertFalse(AddressManager.isAddressStateValid(address.state));

            //============================================================
            // Test true
            address.setState("CA");
            assertTrue(AddressManager.isAddressStateValid(address.state));

            address.setState("California");
            assertTrue(AddressManager.isAddressStateValid(address.state));

            address.setState("Ca");
            assertTrue(AddressManager.isAddressStateValid(address.state));

            address.setState("ca");
            assertTrue(AddressManager.isAddressStateValid(address.state));

            address.setState("AA");
            assertTrue(AddressManager.isAddressStateValid(address.state));

            address.setState("AE");
            assertTrue(AddressManager.isAddressStateValid(address.state));

        }catch (Exception e){
            err = e.getMessage();
        }

    }

    @Test
    public void checkAndCorrectAddressForLocationtest(){

        Address add = new Address();
        add.setAddress_one("10 1st Ave E");
        add.setCity("Mobridge");
        add.setState("SD");
        add.setZip("57601");
        add.setCountry("USA");


        try{
            AddressManager.checkAndCorrectAddressForLocation(add,"CONNECTSHIP_UPS.UPS.GND","DC1",55);
            assertEquals("57601-2603",add.getZip());
            assertFalse("Residential flag is true",add.isResidential);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void checkAndCorrectAddressForLocationtestBad(){

        Address add = new Address();
        add.setAddress_one("220 6th St");
        add.setCity("Mobridge");
        add.setState("SD");
        add.setZip("57601");
        add.setCountry("USA");


        try{
            AddressManager.checkAndCorrectAddressForLocation(add,"CONNECTSHIP_UPS.UPS.GND","DC1",55);

        }catch (Exception e){

            System.out.println(e.getMessage());
            assertTrue(e.getMessage().contains("ERROR: 416: Postdirectional required to choose from multiple possible matches"));
            e.printStackTrace();
        }
    }


    @Test
    public void updateCompleteAddress() {

        Address address = new Address();

        // Address on the order
        address.setAddress_one("Anna Maria Casone");
        address.setAddress_two("11");
        address.setCity("Ornavasso ");
        address.setState("Italy");
        address.setZip("28877");
        address.setCountry("USA");

        String err = "";

        try {
            AddressManager.updateCompleteAddress(address);

        }catch (Exception e){
            err = e.getMessage();
        }


    }


    @Test
    public void checkFlatRateDomesticVsInternational(){
        try{
            //Load order
            OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,18750886);
            OwdOrderShipInfo info = order.getShipinfo();
            info.setCarrService("Ground");
            info.setCarrServiceRefNum("COM_OWD_FLATRATE_GROUND");
            assertEquals("COM_OWD_FLATRATE_GROUND",info.getCarrServiceRefNum());

            AddressManager.checkFlatRateDomesticVsInternational(info);
            HibernateSession.currentSession().refresh(info);
            assertEquals("COM_OWD_FLATRATE_INTL_STND",info.getCarrServiceRefNum());

            OwdOrder order2 = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,13430962);
            OwdOrderShipInfo info2 = order2.getShipinfo();

            assertEquals("USPS First-Class Mail",info2.getCarrService());

            AddressManager.checkFlatRateDomesticVsInternational(info2);
            HibernateSession.currentSession().refresh(info2);
            assertEquals("USPS First-Class Mail",info2.getCarrService());


            OwdOrder order3 = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,19135736);
            OwdOrderShipInfo info3 = order3.getShipinfo();
            info3.setCarrService("Ground");
            info3.setCarrServiceRefNum("COM_OWD_FLATRATE_GROUND");
            assertEquals("COM_OWD_FLATRATE_GROUND",info3.getCarrServiceRefNum());

            AddressManager.checkFlatRateDomesticVsInternational(info3);
            HibernateSession.currentSession().refresh(info3);
            assertEquals("UPS.GND",info3.getCarrServiceRefNum());


        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }


    @Test
    public void checkFlatRateInternationalVsDomestic(){
        System.setProperty("com.owd.environment", "dev");
        try{
            //Load order
            OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,20799010);
            OwdOrderShipInfo info = order.getShipinfo();
            info.setCarrService("International Economy");
            info.setCarrServiceRefNum("COM_OWD_FLATRATE_INTL_ECONOMY");
            assertEquals("COM_OWD_FLATRATE_INTL_ECONOMY",info.getCarrServiceRefNum());

            AddressManager.checkFlatRateDomesticVsInternational(info);
            HibernateSession.currentSession().refresh(info);
            assertEquals("Economy",info.getCarrService());
            assertEquals("COM_OWD_FLATRATE_ECONOMY",info.getCarrServiceRefNum());


            OwdOrder order2 = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,20799010);
            OwdOrderShipInfo info2 = order2.getShipinfo();

            info2.setCarrService("International Standard");
            info2.setCarrServiceRefNum("COM_OWD_FLATRATE_INTL_STND");
            assertEquals("International Standard",info2.getCarrService());

            AddressManager.checkFlatRateDomesticVsInternational(info2);
            HibernateSession.currentSession().refresh(info2);
            assertEquals("Ground",info2.getCarrService());
            assertEquals("COM_OWD_FLATRATE_GROUND",info2.getCarrServiceRefNum());


            OwdOrder order3 = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,20799010);
            OwdOrderShipInfo info3 = order3.getShipinfo();
            info3.setCarrService("International Expedited");
            info3.setCarrServiceRefNum("COM_OWD_FLATRATE_INTL_EXPD");
            assertEquals("COM_OWD_FLATRATE_INTL_EXPD",info3.getCarrServiceRefNum());

            AddressManager.checkFlatRateDomesticVsInternational(info3);
            HibernateSession.currentSession().refresh(info3);
            assertEquals("Standard Priority",info3.getCarrService());
            assertEquals("COM_OWD_FLATRATE_STANDARD_GROUND",info3.getCarrServiceRefNum());

            OwdOrder order4 = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,20799010);
            OwdOrderShipInfo info4 = order4.getShipinfo();
            info4.setCarrService("International Priority DDP");
            info4.setCarrServiceRefNum("COM_OWD_FLATRATE_INTL_PRIDDP");
            assertEquals("COM_OWD_FLATRATE_INTL_PRIDDP",info4.getCarrServiceRefNum());

            OwdOrder order5 = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,20799010);
            OwdOrderShipInfo info5 = order5.getShipinfo();
            info5.setCarrService("International Priority DDU");
            info5.setCarrServiceRefNum("COM_OWD_FLATRATE_INTL_PRIDDU");
            assertEquals("COM_OWD_FLATRATE_INTL_PRIDDU",info5.getCarrServiceRefNum());

            AddressManager.checkFlatRateDomesticVsInternational(info4);
            HibernateSession.currentSession().refresh(info4);
            assertEquals("Ground",info4.getCarrService());
            assertEquals("COM_OWD_FLATRATE_GROUND",info4.getCarrServiceRefNum());

        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public  void  When_Check_Aes_Then_Orders_With_More_Than_2500_Total_Price_Require_It() throws  Exception
    {
        int usaMore2500_order_id = 20758825;
        int usaLess2500_order_id = 20753900;
        int internationalMore2500_order_id = 20784772;
        int internationaLess2500_order_id = 20795568;
        OwdOrder usaMore2500_order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, usaMore2500_order_id);
        OwdOrder usaLess2500_order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, usaLess2500_order_id);
        OwdOrder internationalMored2500_order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, internationalMore2500_order_id);
        OwdOrder internationalLess2500_order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, internationaLess2500_order_id);

        Boolean shouldBeUsaMore2500Notified = AddressManager.doesAESRequired(usaMore2500_order);
        Boolean shouldBeUsaLess2500Notified = AddressManager.doesAESRequired(usaLess2500_order);
        Boolean shouldBeInternationalMore2500Notified = AddressManager.doesAESRequired(internationalMored2500_order);
        Boolean shouldBeInternationaLess2500Notified = AddressManager.doesAESRequired(internationalLess2500_order);

        assertFalse(shouldBeUsaMore2500Notified);
        assertFalse(shouldBeUsaLess2500Notified);
        assertTrue(shouldBeInternationalMore2500Notified);
        assertFalse(shouldBeInternationaLess2500Notified);
    }

    @Test
    public  void  When_Check_Order_Phone_Number_Then_Invalid_Should_Be_Cleared() throws  Exception {
        System.setProperty("com.owd.environment", "dev");
        int domesticOrderId = 20758825;
        int intlOrderId = 20784772;
        String correctPhoneNumber1 = "732-679-7777";
        String correctPhoneNUmber2 = "4445807285";
        String correctPhoneNumber3 = "(202)555-0283";
        String correctPhoneNumber4 = "(204) 555-0283";
        String correctPhoneNumber5 = "(313)-444-0283";
        String correctPhoneNumber6 = "1-803-481-4665";
        String correctPhoneNumber7 = "[313]-444-0283";
        String correctPhoneNumber8 = "011499512975368";
        String correctPhoneNumber9 = "011 52 (686) 5 82 36 63";
        String incorrectPhoneNumber1 = "311499512975368";
        String incorrectPhoneNumber2 = "N4445807285";
        String incorrectPhoneNumber3 = "215-483-8900 X 310";
        String incorrectPhoneNumber4 = "965-0368";

        checkOrderIsValidAndNotUpdated(domesticOrderId, correctPhoneNumber1);
        checkOrderIsValidAndNotUpdated(intlOrderId, correctPhoneNumber1);
        checkOrderIsValidAndNotUpdated(domesticOrderId, correctPhoneNUmber2);
        checkOrderIsValidAndNotUpdated(intlOrderId, correctPhoneNUmber2);
        checkOrderIsValidAndNotUpdated(domesticOrderId, correctPhoneNumber3);
        checkOrderIsValidAndNotUpdated(intlOrderId, correctPhoneNumber3);
        checkOrderIsValidAndNotUpdated(domesticOrderId, correctPhoneNumber4);
        checkOrderIsValidAndNotUpdated(intlOrderId, correctPhoneNumber4);
        checkOrderIsValidAndNotUpdated(domesticOrderId, correctPhoneNumber5);
        checkOrderIsValidAndNotUpdated(intlOrderId, correctPhoneNumber5);
        checkOrderIsValidAndNotUpdated(domesticOrderId, correctPhoneNumber6);
        checkOrderIsValidAndNotUpdated(intlOrderId, correctPhoneNumber6);
        checkOrderIsValidAndNotUpdated(domesticOrderId, correctPhoneNumber7);
        checkOrderIsValidAndNotUpdated(intlOrderId, correctPhoneNumber7);
//        checkOrderIsValidAndNotUpdated(domesticOrderId, correctPhoneNumber8);
//        checkOrderIsValidAndNotUpdated(intlOrderId, correctPhoneNumber8);
//        checkOrderIsValidAndNotUpdated(domesticOrderId, correctPhoneNumber9);
//        checkOrderIsValidAndNotUpdated(intlOrderId, correctPhoneNumber9);
        checkOrderIsInvalidAndPhoneUpdated(domesticOrderId, incorrectPhoneNumber1);
        checkOrderIsValidAndNotUpdated(intlOrderId, incorrectPhoneNumber1);
        checkOrderIsInvalidAndPhoneUpdated(domesticOrderId, incorrectPhoneNumber2);
        checkOrderIsValidAndNotUpdated(intlOrderId, incorrectPhoneNumber2);
        checkOrderIsInvalidAndPhoneUpdated(domesticOrderId, incorrectPhoneNumber3);
        checkOrderIsValidAndNotUpdated(intlOrderId, incorrectPhoneNumber3);
        checkOrderIsInvalidAndPhoneUpdated(domesticOrderId, incorrectPhoneNumber4);
        checkOrderIsValidAndNotUpdated(intlOrderId, incorrectPhoneNumber4);
    }


    @Test
    public  void  When_Check_Order_Phone_Number_Then_Invalid_Should_Be_Cleared_In_DB() throws  Exception {
        System.setProperty("com.owd.environment", "dev");
        int domesticOrderId = 20758825;
        String incorrectPhoneNumber1 = "311499512975368";// original one - 703-599-4034

        checkOrderIsInvalidAndPhoneUpdated(domesticOrderId, incorrectPhoneNumber1);

        HibernateSession.closeSession();
        OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, domesticOrderId);
        OwdOrderShipInfo shipInfo = order.getShipinfo();

        assertEquals("", shipInfo.getShipPhoneNum());
    }

    private void checkOrderIsInvalidAndPhoneUpdated(int orderId, String incorrectPhoneNumber) throws Exception {
        OwdOrder order = getOrderWithPhoneNumber(orderId, incorrectPhoneNumber);
        Boolean isOrderCorrect = AddressManager.checkAndClearIncorrectShippingPhoneNumber(order);
        assertFalse(isOrderCorrect);
        assertEquals("", order.getShipinfo().getShipPhoneNum());
    }

    private void checkOrderIsValidAndNotUpdated(int orderId, String correctPhoneNumber1) throws Exception {
        OwdOrder order = getOrderWithPhoneNumber(orderId, correctPhoneNumber1);
        Boolean isOrderCorrect = AddressManager.checkAndClearIncorrectShippingPhoneNumber(order);
        assertTrue(isOrderCorrect);
        assertEquals(correctPhoneNumber1, order.getShipinfo().getShipPhoneNum());
    }

    private OwdOrder getOrderWithPhoneNumber(int orderId, String phoneNumber) throws Exception {
        OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, orderId);
        OwdOrderShipInfo shipInfo = order.getShipinfo();
        shipInfo.setShipPhoneNum(phoneNumber);

        return order;
    }
}