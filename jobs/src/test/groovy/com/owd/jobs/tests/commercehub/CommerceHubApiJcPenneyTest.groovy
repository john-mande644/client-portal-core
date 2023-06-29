package com.owd.jobs.tests.commercehub

import com.owd.core.business.order.LineItem
import com.owd.core.business.order.Order
import com.owd.jobs.jobobjects.commercehub.CommerceHubApi
import org.junit.Before
import org.junit.Test

/**
 * Created by stewartbuskirk1 on 10/30/15.
 */
class CommerceHubApiJcPenneyTest extends GroovyTestCase {

    @Before
    void setup() {
        //  System.setProperty('com.owd.environment','test')
        def things = ['thing', 'thing2']
    }

    @Test
    void testAcknowledgmentData() {

        URL url = CommerceHubApiJcPenneyTest.class.getResource("/305642484.neworders")
        String  testData = new File(url.getFile()).getText('UTF-8');
     //   println testData
        CommerceHubApi s = new CommerceHubApi("gildanusa.sftp-test.commercehub.com", "gildanusa", "Lead7Wrap:Enough","jcpenney","Gildan USA Inc.","JC Penney Company, Inc.",112);


        String ack = s.getFunctionalAcknowledgment(testData)
        println ack


    }

    @Test
    void testOrderImportedData() {

        CommerceHubApi s = new CommerceHubApi("gildanusa.sftp-test.commercehub.com", "gildanusa", "Lead7Wrap:Enough","jcpenney","Gildan USA Inc.","JC Penney Company, Inc.",112);


        Order order = new Order("112")
        order.orderNum = "123456789"
        order.order_refnum = "123456789:123456789"
        order.po_num = "PO12345"
        LineItem li = new LineItem();
        li.client_ref_num="1"
        li.quantity_request = 2

        String ack = s.getOrderImportedXml(order,'batch','control')
        println ack


    }
    @Test
    void testOrderRejectedData() {

        CommerceHubApi s = new CommerceHubApi("gildanusa.sftp-test.commercehub.com", "gildanusa", "Lead7Wrap:Enough","jcpenney","Gildan USA Inc.","JC Penney Company, Inc.",112);

        Order order = new Order("112")
        order.orderNum = "123456789"
        order.order_refnum = "123456789:123456789"
        order.po_num = "PO12345"
        LineItem li = new LineItem();
        li.client_ref_num="1"
        li.quantity_request = 2


        String ack = s.getOrderRejectedXml(order,'batch','control')
        println ack


    }




}
