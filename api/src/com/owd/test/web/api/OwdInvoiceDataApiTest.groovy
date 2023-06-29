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
class OwdInvoiceDataApiTest extends ApiTestSuiteHarness {

    public OwdInvoiceDataApiTest(String test) {
        super(test);
    }




    @Override
    protected void setUp() {
      //  log.debug("setup");
        super.setUp()    //To change body of overridden methods use File | Settings | File Templates.

        HibUtils.commit(HibernateSession.currentSession());
    }

    @Override
    protected void tearDown() {

        //  clearSKU('112',testKitSKU)
        HibUtils.commit(HibernateSession.currentSession());

        super.tearDown()    //To change body of overridden methods use File | Settings | File Templates.
    }



    def void testAllOctober2014() throws Exception {

        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def oneAsnStatusRequest =
        {
            mkp.xmlDeclaration()
            OWD_API_REQUEST(api_version: '2.8', client_authorization: OWDUtilities.encryptData("622"), client_id: '622') {
                OWD_INVOICE_DATA_REQUEST(type: InvoiceDataRequest.kEnumTypeReceives,startDate:'20180515',endDate:'20180801',ids_after: '0') {

                }
            }
        }

        println "sending"
        println builder.bind(oneAsnStatusRequest).toString()

      // Document response = getLiveAPIResponse(builder.bind(oneAsnStatusRequest).toString());
        Document response = getInternalAPIResponse(builder.bind(oneAsnStatusRequest).toString())
        verifyAPIResponse((Document) response);

        prettyPrint(response.getDocumentElement(), System.out);

    }
    def void testPickPack() throws Exception {

        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def oneAsnStatusRequest =
                {
                    mkp.xmlDeclaration()
                    OWD_API_REQUEST(api_version: '2.1', client_authorization: OWDUtilities.encryptData("622"), client_id: '622') {
                        OWD_INVOICE_DATA_REQUEST(type: InvoiceDataRequest.kEnumTypePickPack,startDate:'20180715',endDate:'20180801',ids_after: '0') {

                        }
                    }
                }

        println "sending"
        println builder.bind(oneAsnStatusRequest).toString()

        // Document response = getLiveAPIResponse(builder.bind(oneAsnStatusRequest).toString());
        Document response = getInternalAPIResponse(builder.bind(oneAsnStatusRequest).toString())
        verifyAPIResponse((Document) response);

        prettyPrint(response.getDocumentElement(), System.out);

    }


}
