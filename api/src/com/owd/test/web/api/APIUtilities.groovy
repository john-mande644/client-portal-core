package com.owd.test.web.api

import com.owd.core.OWDUtilities
import org.w3c.dom.Document

/**
 * Created with IntelliJ IDEA.
 * User: stewartbuskirk1
 * Date: 8/15/13
 * Time: 2:41 PM
 * To change this template use File | Settings | File Templates.
 */
class APIUtilities extends ApiTestSuiteHarness {

    public APIUtilities(String test)
    {
        super(test)
    }

    public static void main(String[] args)
    {
        createBasicVirtualSKU("382","dchead2","Choppahead Volume 2 (Full Movie Download)");
        createBasicVirtualSKU("382","dchead3","Choppahead Volume 3 (Full Movie Download) ");
        createBasicVirtualSKU("382","dhrp1","Hot Rod Pinups (Full Movie Download)");
    }

    public static void createBasicVirtualSKU(String clientId, String sku, String description)
    {

        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def itemCreateRequest =
            {
                mkp.xmlDeclaration()
                OWD_API_REQUEST(api_version:'1.0', client_authorization:OWDUtilities.encryptData(clientId), client_id:clientId, testing:'FALSE') {
                    OWD_INVENTORY_CREATE_REQUEST() {
                        SKU(sku)
                        TYPE('VIRTUAL')
                        TITLE(description)

                    }
                }
            }

        println "sending"
        println builder.bind(itemCreateRequest).toString()

        Document  response = getInternalAPIResponse(builder.bind(itemCreateRequest).toString());
        verifyAPIResponse((Document)response);

    }


}
