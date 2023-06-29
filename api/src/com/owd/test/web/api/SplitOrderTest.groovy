package com.owd.test.web.api

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities
import com.owd.hibernate.HibUtils
import com.owd.hibernate.HibernateSession
import org.w3c.dom.Document

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 12/6/11
 * Time: 9:19 AM
 * To change this template use File | Settings | File Templates.
 */
class SplitOrderTest extends ApiTestSuiteHarness {

      public SplitOrderTest(String test) {
        super(test);
    }



        @Override
        protected void setUp() {
            log.debug("setup");
            super.setUp()    //To change body of overridden methods use File | Settings | File Templates.

            HibUtils.commit(HibernateSession.currentSession());
        }

        @Override
        protected void tearDown() {

            //  clearSKU('112',testKitSKU)
            HibUtils.commit(HibernateSession.currentSession());

            super.tearDown()    //To change body of overridden methods use File | Settings | File Templates.
        }


    def void testOrderUpdate() throws Exception {


        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def getOrderUpdateRequest =
        {
            mkp.xmlDeclaration()
            OWD_API_REQUEST(api_version: '1.9', client_authorization: OWDUtilities.encryptData("112"), client_id: '112', testing: 'FALSE') {
                OWD_ORDER_UPDATE_REQUEST(clientOrderId:'9173672') {
                    ADD_NOTE('Hello world!')
                    SHIPPING_METHOD('UPS.GND')
                    SHIP_NAME('Joe Despacio')
                    SHIP_ADDRESS_ONE('123 Main Street')
                    SHIP_CITY('Mobridge')
                    SHIP_STATE('SD')
                    SHIP_POSTCODE('57601')
                    SHIP_COUNTRY('US')
                    TOTAL_TAX('1.23')
                    TOTAL_DISCOUNT('3.21')
                    TOTAL_SHIPHANDLING('4.56')
                    LINE_ITEMS{
                        LINE_ITEM(part_reference:'SKUTest1',description:'Some stuff that sounds good',requested:'3',cost:'4.99',line_number:'88934')
                    }




                }
            }
        }


        println "sending"
        println builder.bind(getOrderUpdateRequest).toString()

        Document response = getLiveAPIResponse(builder.bind(getOrderUpdateRequest).toString());
       // verifyAPIResponse((Document) response);

        prettyPrint(response.getDocumentElement(), System.out);

    }

}
