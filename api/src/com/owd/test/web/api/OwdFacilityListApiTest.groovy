package com.owd.test.web.api

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities
import com.owd.hibernate.HibUtils
import com.owd.hibernate.HibernateSession
import com.owd.web.api.InvoiceDataRequest
import org.w3c.dom.Document

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 6/28/11
 * Time: 9:33 PM
 * To change this template use File | Settings | File Templates.
 */
class OwdFacilityListApiTest extends ApiTestSuiteHarness {

    public OwdFacilityListApiTest(String test) {
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



    def void testFacilityList() throws Exception {

        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def oneAsnStatusRequest =
        {
            mkp.xmlDeclaration()
            OWD_API_REQUEST(api_version: '1.0', client_authorization: OWDUtilities.encryptData("489"), client_id: '489') {
                OWD_FACILITY_LIST_REQUEST() {

                }
            }
        }

        println "sending"
        println builder.bind(oneAsnStatusRequest).toString()

        Document response = getTestAPIResponse(builder.bind(oneAsnStatusRequest).toString());
        verifyAPIResponse((Document) response);

        prettyPrint(response, System.out)


    }

}
