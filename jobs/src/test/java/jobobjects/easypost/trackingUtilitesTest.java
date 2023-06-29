package jobobjects.easypost;


import com.owd.hibernate.generated.OwdOrderTrackCurrentStatus;
import com.owd.hibernate.generated.OwdOrderTrackEvents;
import com.owd.jobs.jobobjects.easypost.trackingUtilites;
import org.junit.Test;

import javax.validation.constraints.AssertTrue;

import static org.junit.Assert.*;


/**
 * Created by danny on 5/24/2019.
 */
public class trackingUtilitesTest {




    @Test
    public void getLatestTrackEventFromTrackingIdTest(){
        try{

            OwdOrderTrackEvents event = trackingUtilites.getLatestTrackEventFromTrackingId("trk_38181b5863c54c188ca8002f91100982");
            assertTrue(event.getCarrierCode().equals("AR"));
            assertTrue(event.getMessage().equals("At U.S. Postal Service facility (Accepted by U.S. Postal Service - Tracking ID 9261290980617732182900)"));


        }catch (Exception ex){
            ex.printStackTrace();
            fail();
        }



    }

    @Test
    public void getTrackCurrentStatusFromTrackingIdTest(){
        try{
            OwdOrderTrackCurrentStatus status = trackingUtilites.getTrackCurrentStatusFromTrackingId("trk_38181b5863c54c188ca8002f91100982");
            assertTrue(status.getId()==117771);

        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }
}
