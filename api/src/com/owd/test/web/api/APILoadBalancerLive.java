package com.owd.test.web.api;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by danny on 3/27/2017.
 */
public class APILoadBalancerLive extends ApiTestSuiteHarness {



    public APILoadBalancerLive(String test) {
        super(test);
    }


    public void testMultiThreadHold() throws Exception{
        String xmlCount1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><OWD_API_REQUEST api_version=\"2.2\" client_authorization=\"RiFbAUV9sGMyxS9qpMmPmgM=\" client_id=\"489\"><OWD_INVENTORY_COUNT_REQUEST part_reference=\"P204077\" show_backorders=\"1\"/></OWD_API_REQUEST>";
        String xmlStatus2 = "<OWD_API_REQUEST api_version=\"2.1\" client_authorization=\"BP4XJ3OA248L1AF+dId0dwM=\" client_id=\"507\"" +
                " testing=\"false\"> <OWD_ORDER_STATUS_REQUEST clientOrderId=\"1832435\" prefixSearch=\"false\" summary=\"false\"/></OWD_API_REQUEST>";
        String xmlString = "<OWD_API_REQUEST api_version=\"2.1\" client_authorization=\"sUrciMxQPuI7t46I5JDMtwM=\" client_id=\"529\"" +
                " testing=\"false\"> <OWD_ORDER_STATUS_REQUEST clientOrderId=\"AUTOSHIP-118880271\" prefixSearch=\"false\" summary=\"false\"/></OWD_API_REQUEST>";


        APIThreadingTest t1 = new APIThreadingTest(50,"ONE",xmlCount1);
        APIThreadingTest t2 = new APIThreadingTest(50,"TWO",xmlStatus2);
        APIThreadingTest t3 = new APIThreadingTest(50,"three",xmlString);
      /*  APIThreadingTest t4 = new APIThreadingTest(50,"four");
        APIThreadingTest t5 = new APIThreadingTest(50,"ONE");
        APIThreadingTest t6 = new APIThreadingTest(50,"TWO");
        APIThreadingTest t7 = new APIThreadingTest(50,"three");
        APIThreadingTest t8 = new APIThreadingTest(50,"four");*/
        t1.start();
        t2.start();
       t3.start();
       /* t4.start();
        t5.start();
        t6.start();
        t7.start();
        t8.start();*/
    }



    public void testThreadingTask() throws Exception{
        ExecutorService exec = Executors.newFixedThreadPool(10);
        int i = 0;
        String xmlCount1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><OWD_API_REQUEST api_version=\"2.2\" client_authorization=\"RiFbAUV9sGMyxS9qpMmPmgM=\" client_id=\"489\"><OWD_INVENTORY_COUNT_REQUEST part_reference=\"P204077\" show_backorders=\"1\"/></OWD_API_REQUEST>";
        String xmlStatus2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><OWD_API_REQUEST api_version=\"2.1\" client_authorization=\"BP4XJ3OA248L1AF+dId0dwM=\" client_id=\"507\"" +
                " testing=\"false\"> <OWD_ORDER_STATUS_REQUEST clientOrderId=\"1832435\" prefixSearch=\"false\" summary=\"false\"/></OWD_API_REQUEST>";
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><OWD_API_REQUEST api_version=\"2.1\" client_authorization=\"sUrciMxQPuI7t46I5JDMtwM=\" client_id=\"529\"" +
                " testing=\"false\"> <OWD_ORDER_STATUS_REQUEST clientOrderId=\"AUTOSHIP-118880271\" prefixSearch=\"false\" summary=\"false\"/></OWD_API_REQUEST>";


        while(i<10000){
            exec.submit(new apiThreadingTask(xmlCount1));
            exec.submit(new apiThreadingTask( xmlStatus2));
            exec.submit(new apiThreadingTask(xmlString));
         System.out.println(i);
            i++;
        }
        System.out.println("hellO");

        exec.shutdown();
        System.out.println("waiting executor");
        exec.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        System.out.println("done");
    }
}
