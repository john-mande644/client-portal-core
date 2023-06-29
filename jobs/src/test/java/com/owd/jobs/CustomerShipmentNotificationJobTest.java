package com.owd.jobs;

import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerShipmentNotificationJobTest {

    @Test
    public void sendHotTacoShipmentConfirmationEmail() {
        CustomerShipmentNotificationJob.sendHotTacoShipmentConfirmationEmail(21093390, "sean.chen@owd.com", "aa");

    }
}