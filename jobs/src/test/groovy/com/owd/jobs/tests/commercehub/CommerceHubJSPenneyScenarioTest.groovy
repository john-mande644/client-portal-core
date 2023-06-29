package com.owd.jobs.tests.commercehub

import com.owd.core.business.order.OrderStatus
import com.owd.jobs.jobobjects.commercehub.CommerceHubApi
import com.owd.jobs.jobobjects.commercehub.CommerceHubSftpClient
import groovy.xml.XmlUtil

/**
 * Created by stewartbuskirk1 on 10/30/15.
 */
class CommerceHubJSPenneyScenarioTest {

    static boolean doUploads = false;
    public static void main(String[] args) {
      //OK! //  testInventory()

        testCaseOne()
        testCaseTwo()
        testCaseThree()
      //  testCaseFour()
      //  testCaseFive()
      //  testCaseSix()
    }
    public static void scenario() {
        CommerceHubSftpClient sftp = new CommerceHubSftpClient("161.170.240.133","gildan","je8z2PSMwj","jcpenney");

        URL url = CommerceHubApiJcPenneyTest.class.getResource("/305642484.neworders")
        String  testData = new File(url.getFile()).getText('UTF-8');
        //   println testData
        CommerceHubApi s = new CommerceHubApi("gildanusa.sftp-test.commercehub.com", "gildanusa", "Lead7Wrap:Enough","jcpenney","Gildan USA Inc.","JC Penney Company, Inc.",112);
        s.setUpsAcct("XXXX")
        s.setFedexAcct("XXXX")
        s.importOrderBatchFile(testData)
        String xmlFuncAck = s.getFunctionalAcknowledgment(testData)
        //send to acknowledgment directory  (.ack)
    //   if(doUploads){ sftp.writeOrderAck(String batchRef, String data) }
        println (xmlFuncAck)

       /* NO ACKNOWLEDGMENTS FOR JCPENNEY
       //for each order, report accepted
        String orderPoAckData = s.getOrderImportedXml(OrderStatus, batchref,controlnumber)
       // sftp.writeOrderPOAck(batchref, orderPoAckData)
*/
        //for each order, report shipped
        //TEST #1
        OrderStatus orderstatus = new OrderStatus("")
        String orderPoAckData = s.getOrderShippedXml(orderstatus)
        // sftp.writeOrderPOAck(batchref, orderPoAckData)
        println XmlUtil.serialize(orderPoAckData)

        /*

        Sequence:

        -- import batch order file
        -- send fa for order batch file
        ---for each order
        ---send PO ack or reject for each order to fa directory (.fa)
        -----for accepted order
        -----send ship confirm file

        -- possible - send return info

        -- send inventory file

         */


    }

    public static testCaseOne()
    {
        //_111
        CommerceHubApi s = new CommerceHubApi("gildanusa.sftp-test.commercehub.com", "gildanusa", "Lead7Wrap:Enough","jcpenney","Gildan USA Inc.","JC Penney Company, Inc.",112);
        CommerceHubSftpClient sftp = new CommerceHubSftpClient("gildanusa.sftp-test.commercehub.com","gildanusa","Lead7Wrap:Enough","jcpenney");
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone('GMT'))


        OrderStatus orderstatus = new OrderStatus("9755388")
        String orderPoAckData = s.getOrderShippedXml(orderstatus)
        if(doUploads){ sftp.writeOrderShipped("HUB"+""+cal.getTimeInMillis(), orderPoAckData)  }
        println orderPoAckData
        String orderReturnData = s.getReturnEventXml(orderstatus,null)
            if(doUploads){ sftp.writeReturnEvent("HUB"+""+cal.getTimeInMillis(), orderReturnData)  }
        println orderReturnData
    }

    public static testCaseTwo()
    {
        //_222
        CommerceHubApi s = new CommerceHubApi("gildanusa.sftp-test.commercehub.com", "gildanusa", "Lead7Wrap:Enough","jcpenney","Gildan USA Inc.","JC Penney Company, Inc.",112);
        CommerceHubSftpClient sftp = new CommerceHubSftpClient("gildanusa.sftp-test.commercehub.com","gildanusa","Lead7Wrap:Enough","jcpenney");
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone('GMT'))


        OrderStatus orderstatus = new OrderStatus("9755395")
        String orderPoAckData = s.getOrderRejectedXml(orderstatus)
        println (orderPoAckData)
        if(doUploads){  sftp.writeOrderShipped("HUB"+""+cal.getTimeInMillis(), orderPoAckData)   }

    }

    public static testCaseThree()
    {
        //_333
        CommerceHubApi s = new CommerceHubApi("gildanusa.sftp-test.commercehub.com", "gildanusa", "Lead7Wrap:Enough","jcpenney","Gildan USA Inc.","JC Penney Company, Inc.",112);
        CommerceHubSftpClient sftp = new CommerceHubSftpClient("gildanusa.sftp-test.commercehub.com","gildanusa","Lead7Wrap:Enough","jcpenney");
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone('GMT'))


        OrderStatus orderstatus = new OrderStatus("9755389")
        String orderPoAckData = s.getOrderShippedXml(orderstatus)
        println (orderPoAckData)
        if(doUploads){  sftp.writeOrderShipped("HUB"+""+cal.getTimeInMillis(), orderPoAckData)  }

    }

    public static testCaseFour()
    {
        CommerceHubApi s = new CommerceHubApi("gildanusa.sftp-test.commercehub.com", "gildanusa", "Lead7Wrap:Enough","jcpenney","Gildan USA Inc.","JC Penney Company, Inc.",112);
        CommerceHubSftpClient sftp = new CommerceHubSftpClient("gildanusa.sftp-test.commercehub.com","gildanusa","Lead7Wrap:Enough","jcpenney");
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone('GMT'))


        OrderStatus orderstatus = new OrderStatus("9755390")
        String orderPoAckData = s.getOrderRejectedXml(orderstatus)
        println (orderPoAckData)
        if(doUploads){  sftp.writeOrderShipped("HUB"+""+cal.getTimeInMillis(), orderPoAckData)   }

    }

    public static testCaseFive()
    {
        CommerceHubApi s = new CommerceHubApi("gildanusa.sftp-test.commercehub.com", "gildanusa", "Lead7Wrap:Enough","jcpenney","Gildan USA Inc.","JC Penney Company, Inc.",112);
        CommerceHubSftpClient sftp = new CommerceHubSftpClient("gildanusa.sftp-test.commercehub.com","gildanusa","Lead7Wrap:Enough","jcpenney");
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone('GMT'))


        OrderStatus orderstatus = new OrderStatus("9755391")
        String orderPoAckData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<ConfirmMessageBatch batchNumber=\"305642484\">\n" +
                " <partnerID name=\"gildanusa\" roleType=\"vendor\"/>\n" +
                " <hubConfirm transactionID=\"9755391\">\n" +
                "  <participatingParty name=\"jcpenney\" roleType=\"merchant\" participationCode=\"To:\">jcpenney</participatingParty>\n" +
                "  <partnerTrxID>16948593</partnerTrxID>\n" +
                "  <partnerTrxDate>20151110</partnerTrxDate>\n" +
                "  <poNumber>116468011_555</poNumber>\n" +
                "  <hubAction transactionItemID=\"23518562\">\n" +
                "   <action>v_cancel</action>\n" +
                "   <actionCode>merchant_request</actionCode>\n" +
                "   <merchantLineNumber>4</merchantLineNumber>\n" +
                "   <trxQty>2</trxQty>\n" +
                "  </hubAction>\n" +
                " </hubConfirm>\n" +
                " <messageCount>1</messageCount>\n" +
                "</ConfirmMessageBatch>"
        println (orderPoAckData)
        if(doUploads){  sftp.writeOrderShipped("HUB"+""+cal.getTimeInMillis(), orderPoAckData)   }
        orderPoAckData = s.getOrderShippedXml(orderstatus)
        println (orderPoAckData)
         cal = Calendar.getInstance(TimeZone.getTimeZone('GMT'))

            if(doUploads){  sftp.writeOrderShipped("HUB"+""+cal.getTimeInMillis(), orderPoAckData)  }
    }

    public static testCaseSix()
    {
        CommerceHubApi s = new CommerceHubApi("gildanusa.sftp-test.commercehub.com", "gildanusa", "Lead7Wrap:Enough","jcpenney","Gildan USA Inc.","JC Penney Company, Inc.",112);
        CommerceHubSftpClient sftp = new CommerceHubSftpClient("gildanusa.sftp-test.commercehub.com","gildanusa","Lead7Wrap:Enough","jcpenney");
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone('GMT'))


        OrderStatus orderstatus = new OrderStatus("9755393")
        String orderPoAckData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<ConfirmMessageBatch batchNumber=\"305642484\">\n" +
                " <partnerID name=\"gildanusa\" roleType=\"vendor\"/>\n" +
                " <hubConfirm transactionID=\"9755393\">\n" +
                "  <participatingParty name=\"jcpenney\" roleType=\"merchant\" participationCode=\"To:\">jcpenney</participatingParty>\n" +
                "  <partnerTrxID>16948597</partnerTrxID>\n" +
                "  <partnerTrxDate>20151110</partnerTrxDate>\n" +
                "  <poNumber>116468013_999</poNumber>\n" +
                "  <hubAction transactionItemID=\"23518578\">\n" +
                "   <action>v_cancel</action>\n" +
                "   <actionCode>merchant_request</actionCode>\n" +
                "   <merchantLineNumber>10</merchantLineNumber>\n" +
                "   <trxQty>1</trxQty>\n" +
                "  </hubAction>\n" +
                " </hubConfirm>\n" +
                " <messageCount>1</messageCount>\n" +
                "</ConfirmMessageBatch>"
         println (orderPoAckData)
        if(doUploads){   sftp.writeOrderShipped("HUB"+""+cal.getTimeInMillis(), orderPoAckData)  }
        orderPoAckData = s.getOrderShippedXml(orderstatus)
        println (orderPoAckData)
        cal = Calendar.getInstance(TimeZone.getTimeZone('GMT'))
            if(doUploads){  sftp.writeOrderShipped("HUB"+""+cal.getTimeInMillis(), orderPoAckData)    }
    }

    public static testInventory()
    {
        CommerceHubApi s = new CommerceHubApi("gildanusa.sftp-test.commercehub.com", "gildanusa", "Lead7Wrap:Enough","jcpenney","Gildan USA Inc.","JC Penney Company, Inc.",112);
        CommerceHubSftpClient sftp = new CommerceHubSftpClient("gildanusa.sftp-test.commercehub.com","gildanusa","Lead7Wrap:Enough","jcpenney");
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone('GMT'))

        List<List<String>> itemList = new ArrayList<List<String>>();
        itemList.add(Arrays.asList('12345678912','AVENDORSKU','INTERNALSKU','upc','100','A SHORT DESCRIPTION'));
        itemList.add(Arrays.asList('12345678913','AVENDORSKU1','INTERNALSKU3','upc','25','A SHORT DESCRIPTION 1'));
        itemList.add(Arrays.asList('12345678914','AVENDORSKU2','INTERNALSKU4','upc','0','A SHORT DESCRIPTION 2'));
        String orderPoAckData = s.getInventoryLevelFile(itemList)
        println (orderPoAckData)
        cal = Calendar.getInstance(TimeZone.getTimeZone('GMT'))
        if(doUploads){  sftp.writeInventoryAdvice("HUB"+""+cal.getTimeInMillis(), orderPoAckData)    }


    }

}
