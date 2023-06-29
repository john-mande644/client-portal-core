package com.owd.core.business.order.zoneUtilities;

import com.owd.hibernate.generated.OwdShipMethod;
import junit.framework.TestCase;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.validation.constraints.AssertTrue;
import java.util.Calendar;
import java.util.List;

/**
 * Created by danny on 9/11/2018.
 */
public class FlatRateShippingTest extends TestCase{

    @Override
    protected void setUp() throws Exception {
        //  System.setProperty("com.owd.environment", "test");
        super.setUp();
    }



    @Rule
    public ExpectedException exception = ExpectedException.none();

    public void testGroupRoleup(){
        try{
            shipMethodUpdate smu = FlatRateShipping.getShipMethodToUse("634","frederickss","DC6",4,2,2,false);
            System.out.println(smu.getMethod());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void testOvernightUnderPoundFOH(){
        try{
            shipMethodUpdate smu = FlatRateShipping.getShipMethodToUse("489", "fredericks", "DC6", 8, 1, 5,true);
            assertTrue("Should be USPS Priority Mail Express, We have " + smu.getMethod(), smu.getMethod().equals("USPS Priority Mail Express"));


             smu = FlatRateShipping.getShipMethodToUse("489", "fredericks", "DC6", 4, 1, 5,false);
            assertTrue("Should be FedEx Standard Overnight, We have " + smu.getMethod(), smu.getMethod().equals("FedEx Standard Overnight"));

            smu = FlatRateShipping.getShipMethodToUse("489", "fredericks", "DC6", 8, 1, 5,true);
            assertTrue("Should be USPS Priority Mail Express, We have " + smu.getMethod(), smu.getMethod().equals("USPS Priority Mail Express"));


            smu = FlatRateShipping.getShipMethodToUse("489", "fredericks", "DC6", 4, 1, 5,false);
            assertTrue("Should be FedEx Standard Overnight, We have " + smu.getMethod(), smu.getMethod().equals("FedEx Standard Overnight"));

        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }
    @Test
    public void testFOHMapping(){
        //This will need to be updated if the table changes.
        try {
            shipMethodUpdate smu = FlatRateShipping.getShipMethodToUse("489", "fredericks", "DC6", 1, 6, 2,false);
            assertTrue("Should be USPS Priority Mail, We have " + smu.getMethod(), smu.getMethod().equals("USPS Priority Mail"));

            smu = FlatRateShipping.getShipMethodToUse("489", "fredericks", "DC6", 8, 50, 2,false);
            assertTrue("Should be UPS Ground, We have " + smu.getMethod(), smu.getMethod().equals("UPS Ground"));
            smu = FlatRateShipping.getShipMethodToUse("489", "fredericks", "DC6", 8, 50, 3,false);
            assertTrue("Should be DHL Express Worldwide nondoc, We have " + smu.getMethod(), smu.getMethod().equals("DHL Express Worldwide nondoc"));


            smu = FlatRateShipping.getShipMethodToUse("489", "fredericks", "DC6", 8, 0, 2,false);
            assertTrue("Should be DHL SmartMail Parcel Expedited, We have " + smu.getMethod(), smu.getMethod().equals("DHL SmartMail Parcel Expedited"));

        }catch (Exception e){
            e.printStackTrace();
            fail();
        }

    }

    @Test
    public void testSPOSelectionOfProperMethod(){
        try {

            shipMethodUpdate smu = FlatRateShipping.getShipMethodToUse("489", "fredericks", "DC6", 6, 9, 2, true);
            assertTrue("Should be USPS Priority Mail, We have " + smu.getMethod(), smu.getMethod().equals("USPS Priority Mail"));
            smu = FlatRateShipping.getShipMethodToUse("489", "fredericks", "DC6", 6, 8, 2, true);
            assertTrue("Should be DHL SmartMail Parcel Plus Expedited, We have " + smu.getMethod(), smu.getMethod().equals("DHL SmartMail Parcel Plus Expedited"));

            smu = FlatRateShipping.getShipMethodToUse("489", "fredericks", "DC6", 6, 9, 2, false);
            assertTrue("Should be UPS Ground, We have " + smu.getMethod(), smu.getMethod().equals("UPS Ground"));

            smu = FlatRateShipping.getShipMethodToUse("489", "fredericks", "DC6", 8, 5, 2, true);
            assertTrue("Should be DHL SmartMail Parcel Plus Expedited, We have " + smu.getMethod(), smu.getMethod().equals("DHL SmartMail Parcel Plus Expedited"));


        }catch (Exception e){
            e.printStackTrace();
            fail();

        }
    }
    @Test
    public void testOversizeMethodLookup(){
        shipMethodUpdate smu = FlatRateShipping.getOversizeShipMethod(1,5,false);
            assertTrue("Should be FedEx SmartPost Parcel Select, We have " + smu.getMethod(), smu.getMethod().equals("FedEx SmartPost Parcel Select"));

        smu = FlatRateShipping.getOversizeShipMethod(4,5, false);
        assertTrue("Should beUSPS Priority Mail, We have " + smu.getMethod(), smu.getMethod().equals("USPS Priority Mail"));

        smu = FlatRateShipping.getOversizeShipMethod(8,5, false);
        assertTrue("Should be UPS Ground, We have " + smu.getMethod(), smu.getMethod().equals("UPS Ground"));


    }
    @Test
    public void testLoadInitialDataset(){

        try {
            shipMethodUpdate smu = FlatRateShipping.getShipMethodToUse("0", "", "DC6", 1, 6, 2,false);
            assertTrue(FlatRateShipping.getShippingMapSize() > 0);
            int size = FlatRateShipping.getShippingMapSize();
            assertTrue("Should be USPS Priority Mail, We have " + smu.getMethod(), smu.getMethod().equals("USPS Priority Mail"));
            smu = FlatRateShipping.getShipMethodToUse("0", "", "DC6", 4, 6, 2,false);
            assertTrue("Should be DHL SmartMail Parcel Expedited, We have " + smu.getMethod(), smu.getMethod().equals("DHL SmartMail Parcel Expedited"));
            assertTrue("Map should be same size since it's the same client ", FlatRateShipping.getShippingMapSize() == size);

            smu = FlatRateShipping.getShipMethodToUse("1", "", "DC6", 4, 6, 2,false);
            fail("Exception should have been thrown");

        }catch (Exception e){
            assertTrue(e.getMessage().contains("Error loading service map"));
          //  e.printStackTrace();
        }




    }

    @Test
    public void testGettingServiceLevelId(){
        int id = FlatRateShipping.getServiceLevelIdFromCode("COM_OWD_FLATRATE_GROUND");
        assertTrue("Should be 2: we got "+id,id==2);
    }

    @Test
    public void testResetOfMaps(){
        try {
            shipMethodUpdate smu = FlatRateShipping.getShipMethodToUse("0", "", "DC6", 1, 6, 2,false);
            assertTrue(FlatRateShipping.getShippingMapSize() > 0);
            assertTrue(FlatRateShipping.getMethodMapSize() > 0);
            FlatRateShipping.resetMaps();
            assertTrue(FlatRateShipping.getShippingMapSize() == 0);
            assertTrue(FlatRateShipping.getMethodMapSize() == 0);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @Test
    public void testGetHoursUntilTarget() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        assertEquals(24, FlatRateShipping.getHoursUntilTarget(hour));
        assertEquals(1, FlatRateShipping.getHoursUntilTarget(hour + 1));
        assertEquals(23, FlatRateShipping.getHoursUntilTarget(hour - 1));
    }

    @Test
    public void testgetFlatRateShipCodesForClien(){
        try {
            List<OwdShipMethod> shipMethods = FlatRateShipping.getFlatRateShipMethodCodesForClient(0);
            System.out.println("shipMethods");
            for(OwdShipMethod method:shipMethods) {
                org.junit.Assert.assertTrue("string emtpy",0 < method.getMethodCode().length());
                System.out.println(method.getMethodCode() + " :: " + method.getDivisor());
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

}
