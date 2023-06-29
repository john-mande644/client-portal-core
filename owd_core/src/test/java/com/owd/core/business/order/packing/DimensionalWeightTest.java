package com.owd.core.business.order.packing;

import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdBoxtypes;
import org.hibernate.Criteria;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DimensionalWeightTest {

    @Test
    public void checkForBalloonRateTest(){
        assertFalse("Check for Balloon Rate failed", DimensionalWeight.checkForBalloonRate(77,10,10, 2));
        assertTrue("Check for Balloon Rate failed", DimensionalWeight.checkForBalloonRate(67,10, 10, 2));
        assertFalse("Check for Balloon Rate failed", DimensionalWeight.checkForBalloonRate(27, 10, 10, 2));
    }

    @Test
    public void testReturnValues(){
        ArrayList<DimTest> tests = new ArrayList<>();
        tests.add(new DimTest(12,20,6,8,11,9));
        tests.add(new DimTest(12,19,6,8,10,9));
        tests.add(new DimTest(12,18,6,7,10,8));
        tests.add(new DimTest(12,17,6,7,9,8));
        tests.add(new DimTest(12,16,6,6,9,7));
        tests.add(new DimTest(12,15,6,6,8,7));
        tests.add(new DimTest(12,14,6,6,8,7));
        tests.add(new DimTest(12,13,6,5,7,6));
        tests.add(new DimTest(12,13,6,5,7,6));
        tests.add(new DimTest(12,11,6,5,6,5));
        tests.add(new DimTest(12,10,6,4,6,5));
        tests.add(new DimTest(12,9,6,4,5,4));
        tests.add(new DimTest(12,8,6,3,5,4));
        tests.add(new DimTest(12,7,6,3,4,4));
        tests.add(new DimTest(12,6,6,3,4,3));
        tests.add(new DimTest(12,20,6,8,11,9));
        tests.add(new DimTest(12,20,1,2,2,2));
        tests.add(new DimTest(12,20,2,3,4,3));
        tests.add(new DimTest(19,16,1,2,3,2));
        tests.add(new DimTest(24,19,1,3,4,3));

        for(int x = 0; x < tests.size(); x++) {
            DimTest test = tests.get(x);
            assertEquals("Test " + x + " UPS value failed", test.uspsWeight, DimensionalWeight.getDimensionalWeight(
                    "123",
                    new BigDecimal(test.depth),
                    new BigDecimal(test.width),
                    new BigDecimal(test.height),
                    "CONNECTSHIP_UPS.UPS.GND"
            ));
            assertEquals("Test " + x + " DHL value failed", test.ecommWeight, DimensionalWeight.getDimensionalWeight(
                    "123",
                    new BigDecimal(test.depth),
                    new BigDecimal(test.width),
                    new BigDecimal(test.height),
                    "CONNECTSHIP_DHLGLOBALMAIL.DHL.PP_EXP"
            ));
            assertEquals("Test " + x + " Fedex value failed", test.fedexWieght, DimensionalWeight.getDimensionalWeight(
                    "123",
                    new BigDecimal(test.depth),
                    new BigDecimal(test.width),
                    new BigDecimal(test.height),
                    "TANDATA_FEDEXFSMS.FEDEX.STD"
            ));
        }
    }

    @Test
    public void AllBoxes(){
        try{
            Criteria crit = HibernateSession.currentSession().createCriteria(OwdBoxtypes.class);
            List<OwdBoxtypes> boxes = crit.list();
            for(OwdBoxtypes box: boxes){
                System.out.println("Box:" + box.getHeight() + " X " + box.getWidth() + " X " + box.getDepth());
                System.out.println(DimensionalWeight.getDimensionalWeight("123",box.getHeight(),box.getWidth(),box.getDepth(),"CONNECTSHIP_UPS.UPS.GND"));
                System.out.println(DimensionalWeight.getDimensionalWeight("123",box.getHeight(),box.getWidth(),box.getDepth(),"CONNECTSHIP_DHLGLOBALMAIL.DHL.PP_EXP"));
                System.out.println(DimensionalWeight.getDimensionalWeight("123",box.getHeight(),box.getWidth(),box.getDepth(),"TANDATA_FEDEXFSMS.FEDEX.STD"));
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private class DimTest {
        public int height;
        public int width;
        public int depth;
        public int uspsWeight;
        public int fedexWieght;
        public int ecommWeight;

        public DimTest(int height, int width, int depth, int uspsWeight, int fedexWieght, int ecommWeight) {
            this.height = height;
            this.width = width;
            this.depth = depth;
            this.uspsWeight = uspsWeight;
            this.fedexWieght = fedexWieght;
            this.ecommWeight = ecommWeight;
        }
    }
}
