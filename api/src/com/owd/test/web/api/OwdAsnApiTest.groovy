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
 * Date: 6/28/11
 * Time: 9:33 PM
 * To change this template use File | Settings | File Templates.
 */
class OwdAsnApiTest extends ApiTestSuiteHarness {

    public OwdAsnApiTest(String test) {
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

    def void testCreateAsn() throws Exception {

        println "517:"+OWDUtilities.encryptData("517")
        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def createAsnRequest =
        {
            mkp.xmlDeclaration()
            OWD_API_REQUEST(api_version: '2.1', client_authorization: OWDUtilities.encryptData("112"), client_id: '112', testing: 'FALSE') {
                OWD_ASN_CREATE_REQUEST() {
                    REFERENCE('My ASN Reference')
                    PO_REFERENCE('Supplier PO <123>')
                    SHIPPER('MY SUPPLIER')
                    EXPECTED_DATE('20150801')
                    CARRIER('USPS')
                    AUTORELEASE('1')
                    CARTONS('2')
                    PALLETS('0')
                    FACILITY_CODE('DC6')
                    NOTES('Check all items for Ebola')
                    ASNITEMS()
                            {
                                ASNITEM()
                                        {
                                            ASNITEM_SKU("SKUTest1")
                                            ASNITEM_EXPECTED("1")
                                            ASNITEM_DESCRIPTION("SkuTest1 Desc")
                                        }
                                ASNITEM()
                                        {
                                            ASNITEM_SKU("SKUTest2")
                                            ASNITEM_EXPECTED("200")
                                            ASNITEM_DESCRIPTION("SkuTest2 Desc")
                                        }
                            }
                    CUSTOM_VALUE(name:'TRACKING','MYTRACKINGNUMBER1,MYTRACKINGNUMBER2')

                }
            }

        }

        //    println "sending"
        //    println builder.bind(createAsnRequest).toString()

        Document response = getInternalAPIResponse(builder.bind(createAsnRequest).toString());
        verifyAPIResponse(response);

          prettyPrint(response.getDocumentElement(), System.out);

    }

    def void testGetAsnByReceivedSince() throws Exception {


        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def oneAsnStatusRequest =
        {
            mkp.xmlDeclaration()
            OWD_API_REQUEST(api_version: '1.0', client_authorization: OWDUtilities.encryptData("387"), client_id: '387', testing: 'FALSE') {
                OWD_ASN_STATUS_REQUEST() {
                    FILTER()
                            {
                                TYPE("receivedSince")
                                VALUE("20110815")
                            }
                }
            }
        }

        println "sending"
        println builder.bind(oneAsnStatusRequest).toString()

        Document response = getLiveAPIResponse(builder.bind(oneAsnStatusRequest).toString());

        prettyPrint(response.getDocumentElement(), System.out);
        verifyAPIResponse((Document) response);


    }

    def void testGetAsnByContainsSku() throws Exception {
        createTestAsn()

        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def oneAsnStatusRequest =
        {
            mkp.xmlDeclaration()
            OWD_API_REQUEST(api_version: '1.0', client_authorization: OWDUtilities.encryptData("112"), client_id: '112', testing: 'FALSE') {
                OWD_ASN_STATUS_REQUEST() {
                    FILTER()
                            {
                                TYPE("containsSku")
                                VALUE("SkuTest1")
                            }
                    FILTER()
                            {
                                TYPE("hasUnreleasedReceives")
                                VALUE("0")
                            }

                    FILTER()
                            {
                                TYPE("status")
                                VALUE("UNRECEIVED")
                            }
                }
            }
        }

        println "sending"
        //  println builder.bind(oneAsnStatusRequest).toString()

        Document response = getLiveAPIResponse(builder.bind(oneAsnStatusRequest).toString());
        verifyAPIResponse((Document) response);

        prettyPrint(response.getDocumentElement(), System.out);

    }

    def void testGetAsnByReference() throws Exception {
        createTestAsn()

        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def oneAsnStatusRequest =
        {
            mkp.xmlDeclaration()
            OWD_API_REQUEST(api_version: '1.0', client_authorization: OWDUtilities.encryptData("112"), client_id: '112', testing: 'FALSE') {
                OWD_ASN_STATUS_REQUEST() {
                    FILTER()
                            {
                                TYPE("reference")
                                VALUE("My ASN Reference")
                            }
                }
            }
        }

        println "sending"
        println builder.bind(oneAsnStatusRequest).toString()

        Document response = getInternalAPIResponse(builder.bind(oneAsnStatusRequest).toString());
        verifyAPIResponse((Document) response);

        prettyPrint(response.getDocumentElement(), System.out);

    }

    def void testGetAsnByReferenceCheck() throws Exception {
      //  createTestAsn()

        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def oneAsnStatusRequest =
        {
            mkp.xmlDeclaration()
            OWD_API_REQUEST(api_version: '1.9', client_authorization: OWDUtilities.encryptData("489"), client_id: '489', testing: 'FALSE') {
                OWD_ASN_STATUS_REQUEST() {
                    FILTER()
                            {
                                TYPE("reference")
                                VALUE("692")
                            }
                }
            }
        }

        println "sending"
        println builder.bind(oneAsnStatusRequest).toString()

        Document response = getLiveAPIResponse(builder.bind(oneAsnStatusRequest).toString());
        verifyAPIResponse((Document) response);

        prettyPrint(response.getDocumentElement(), System.out);

    }

    def void testAsnUpdate() throws Exception {

        int id = createTestAsn()

        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def updateAsnRequest =
        {
            mkp.xmlDeclaration()
            OWD_API_REQUEST(api_version: '1.0', client_authorization: OWDUtilities.encryptData("112"), client_id: '112', testing: 'FALSE') {
                OWD_ASN_UPDATE_REQUEST(id: id) {
                    REFERENCE('My ASN Referencex')
                    PO_REFERENCE('Supplier PO <123>x"')
                    SHIPPER('MY SUPPLIERx')
                    EXPECTED_DATE('20200102')
                    CARRIER('FedEx')
                    AUTORELEASE('0')
                    CARTONS('0')
                    PALLETS('1')
                    NOTES('Delicate')
                    ASNITEMS()
                            {
                                ASNITEM()
                                        {
                                            ASNITEM_SKU("SKUTest2")
                                            ASNITEM_EXPECTED("10")
                                            ASNITEM_DESCRIPTION("SkuTest2 updated Desc")
                                        }
                            }
                }
            }
        }

        println "sending"
        println builder.bind(updateAsnRequest).toString()

        Document response = getLiveAPIResponse(builder.bind(updateAsnRequest).toString());
        prettyPrint(response.getDocumentElement(), System.out);
        verifyAPIResponse((Document) response);

        def resp = new XmlParser().parseText(printNodeToString(response.getDocumentElement()));

        /*  assertTrue("Returned Count <> 1",1==new Integer(resp.OWD_INVENTORY_STATUS_RESPONSE.COUNT.text()))
              assertTrue("Update not verified","NEWTITLE".equals(resp.OWD_INVENTORY_STATUS_RESPONSE.OWD_INVENTORY_STATUS[0].TITLE.text()))
              assertTrue("Update not verified","NEWSUPPLIER".equals(resp.OWD_INVENTORY_STATUS_RESPONSE.OWD_INVENTORY_STATUS[0].SUPPLIER.text()))
              assertTrue("Update not verified","NEWSUPPLIER_SKU".equals(resp.OWD_INVENTORY_STATUS_RESPONSE.OWD_INVENTORY_STATUS[0].SUPPLIER_SKU.text()))
              assertTrue("Update not verified","9.99".equals(resp.OWD_INVENTORY_STATUS_RESPONSE.OWD_INVENTORY_STATUS[0].UNIT_PRICE.text()))
              assertTrue("Update not verified","9.99".equals(resp.OWD_INVENTORY_STATUS_RESPONSE.OWD_INVENTORY_STATUS[0].UNIT_COST.text()))
              assertTrue("Update not verified","NEWDESCRIPTION".equals(resp.OWD_INVENTORY_STATUS_RESPONSE.OWD_INVENTORY_STATUS[0].DESCRIPTION.text()))
              assertTrue("Update not verified","NEWCOLOR".equals(resp.OWD_INVENTORY_STATUS_RESPONSE.OWD_INVENTORY_STATUS[0].COLOR.text()))
              assertTrue("Update not verified","NEWSIZE".equals(resp.OWD_INVENTORY_STATUS_RESPONSE.OWD_INVENTORY_STATUS[0].SIZE.text()))
              assertTrue("Update not verified","9".equals(resp.OWD_INVENTORY_STATUS_RESPONSE.OWD_INVENTORY_STATUS[0].REORDER_ALERT_QTY.text()))
              assertTrue("Update not verified","9.99".equals(resp.OWD_INVENTORY_STATUS_RESPONSE.OWD_INVENTORY_STATUS[0].WEIGHT.text()))
              assertTrue("Update not verified","NEWCUSTOMS_DESC".equals(resp.OWD_INVENTORY_STATUS_RESPONSE.OWD_INVENTORY_STATUS[0].CUSTOMS_DESC.text()))
              assertTrue("Update not verified","NEWTAG STUFF".equals(resp.OWD_INVENTORY_STATUS_RESPONSE.OWD_INVENTORY_STATUS[0].KEYWORD.text()))
              assertTrue("Update not verified","9.99".equals(resp.OWD_INVENTORY_STATUS_RESPONSE.OWD_INVENTORY_STATUS[0].CUSTOMS_VALUE.text()))
              assertTrue("Update not verified","NEWPACKING_INSTRUCTIONS".equals(resp.OWD_INVENTORY_STATUS_RESPONSE.OWD_INVENTORY_STATUS[0].PACKING_INSTRUCTIONS.text()))
              assertTrue("Update not verified","NEWNOTES".equals(resp.OWD_INVENTORY_STATUS_RESPONSE.OWD_INVENTORY_STATUS[0].NOTES.text()))
              assertTrue("Update not verified","0".equals(resp.OWD_INVENTORY_STATUS_RESPONSE.OWD_INVENTORY_STATUS[0].ACTIVE.text()))

              assertEquals("Update not verified",1,(resp.OWD_INVENTORY_STATUS_RESPONSE.OWD_INVENTORY_STATUS[0].COMPONENTS.COMPONENT.findAll{ it.COMPONENT_SKU.text().equals('TestItemOWD0001') }.size()))
              assertEquals("Update not verified",1,(resp.OWD_INVENTORY_STATUS_RESPONSE.OWD_INVENTORY_STATUS[0].COMPONENTS.COMPONENT.findAll{ it.COMPONENT_SKU.text().equals('TestItemOWD0002') }.size()))
              assertEquals("Update not verified","2",(resp.OWD_INVENTORY_STATUS_RESPONSE.OWD_INVENTORY_STATUS[0].COMPONENTS.COMPONENT.findAll{ it.COMPONENT_SKU.text().equals('TestItemOWD0001') }[0].COMPONENT_QTY.text()))
              assertEquals("Update not verified","4",(resp.OWD_INVENTORY_STATUS_RESPONSE.OWD_INVENTORY_STATUS[0].COMPONENTS.COMPONENT.findAll{ it.COMPONENT_SKU.text().equals('TestItemOWD0002') }[0].COMPONENT_QTY.text()))
        */
    }



    def void testAsnDelete() throws Exception {

        int id = createTestAsn()

        Document response = getInternalAPIResponse(getDeleteAsnRequestXml(id));
        prettyPrint(response.getDocumentElement(), System.out);
        verifyAPIResponse((Document) response);

    }


    def int createTestAsn() {
        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def createAsnRequest =
        {
            mkp.xmlDeclaration()
            OWD_API_REQUEST(api_version: '1.0', client_authorization: OWDUtilities.encryptData("112"), client_id: '112', testing: 'FALSE') {
                OWD_ASN_CREATE_REQUEST() {
                    REFERENCE('My ASN Reference')
                    PO_REFERENCE('Supplier PO <123>')
                    SHIPPER('MY SUPPLIER')
                    EXPECTED_DATE('20220102')
                    CARRIER('USPS')
                    AUTORELEASE('1')
                    CARTONS('2')
                    PALLETS('0')
                    NOTES('Rock, Paper, Scissors, Lizard, Spock: A Strategy Guide\r that\'s the way I like it')
                    ASNITEMS()
                            {
                                ASNITEM()
                                        {
                                            ASNITEM_SKU("SKUTest1")
                                            ASNITEM_EXPECTED("1")
                                            ASNITEM_DESCRIPTION("SkuTest1 Desc")
                                        }
                                ASNITEM()
                                        {
                                            ASNITEM_SKU("SKUTest2")
                                            ASNITEM_EXPECTED("200")
                                            ASNITEM_DESCRIPTION("SkuTest2 Desc")
                                        }
                            }
                }
            }

        }


        println "sending"
        println builder.bind(createAsnRequest).toString()

        Document response = getInternalAPIResponse(builder.bind(createAsnRequest).toString());
        verifyAPIResponse((Document) response);

        prettyPrint(response.getDocumentElement(), System.out);
        def resp = new XmlParser().parseText(printNodeToString(response.getDocumentElement()));

        return Integer.parseInt(resp.OWD_ASN_STATUS_RESPONSE.OWD_ASN_STATUS[0].@id)
    }

    def String getDeleteAsnRequestXml(int asnId) {
        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def updateAsnRequest =
        {
            mkp.xmlDeclaration()
            OWD_API_REQUEST(api_version: '1.0', client_authorization: OWDUtilities.encryptData("112"), client_id: '112', testing: 'FALSE') {
                OWD_ASN_DELETE_REQUEST(id: asnId) {

                }
            }
        }

        println "sending"
        println builder.bind(updateAsnRequest).toString()
        return builder.bind(updateAsnRequest).toString()
    }
}
