package jobobjects.billing.FlatRateShipping;

import com.owd.jobs.jobobjects.billing.FlatRateShipping.FlatRateShippingBillingUtilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by danny on 2/15/2019.
 */
public class FlatRateShippingBillingUtilitiesTests {
    private final static Logger log =  LogManager.getLogger();



    @Test
    public  void  getDutyAndTaxesRecordsForDateTest(){

        try {
            List l = FlatRateShippingBillingUtilities.getDutyAndTaxesRecordsForDate("2019-02-09");
            assertTrue("This is the size "+l.size(),l.size()==11);

            l = FlatRateShippingBillingUtilities.getDutyAndTaxesRecordsForDate("2019-02-08");
            assertTrue(l.size()==0);

        }catch (Exception e){
            e.printStackTrace();
            fail();
        }

    }
}
