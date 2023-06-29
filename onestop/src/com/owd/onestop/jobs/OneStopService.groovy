package com.owd.onestop.jobs

import com.owd.onestop.OrderProcessingLocator
import com.owd.onestop.OrderProcessing
import com.owd.onestop.ClientDetails
import org.apache.axis.EngineConfiguration
import org.apache.axis.configuration.EngineConfigurationFactoryFinder
import org.apache.axis.configuration.SimpleProvider
import org.apache.axis.transport.http.CommonsHTTPSender

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 3/14/12
 * Time: 12:48 AM
 * To change this template use File | Settings | File Templates.
 */
class OneStopService {
    static ClientDetails cd;
    static OrderProcessing service;

       static {
           cd = new ClientDetails();
           cd.setClientId("91");
           cd.setClinetPassword("Alpha123");
           cd.setClinetUsername("owd@onestop.com");

    EngineConfiguration engine = EngineConfigurationFactoryFinder.newFactory().getClientEngineConfig();
    def mike = new SimpleProvider(engine);

           CommonsHTTPSender sender = new CommonsHTTPSender();
           sender.httpChunkStream = false;
           mike.deployTransport("http", sender);

    service = new OrderProcessingLocator(mike);

}
}
