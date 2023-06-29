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
class OwdInventoryReportApiTest extends ApiTestSuiteHarness {
    private final static Logger log =  LogManager.getLogger();

    public OwdInventoryReportApiTest(String test) {
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



    //<?xml version="1.0"?>
    //<OWD_API_REQUEST api_version="1.2" client_authorization="RiFbAUV9sGMyxS9qpMmPmgM=" client_id="489"><OWD_INVENTORY_REPORT_REQUEST><FILTER><TYPE>startDate</TYPE><VALUE>20130917</VALUE></FILTER><FILTER><TYPE>endDate</TYPE>
    // <VALUE>20130921</VALUE></FILTER></OWD_INVENTORY_REPORT_REQUEST></OWD_API_REQUEST>

    def void testInventoryReportRequestDates() throws Exception {

        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def oneAsnStatusRequest =
        {
            mkp.xmlDeclaration()
            OWD_API_REQUEST(api_version: '2.0', client_authorization: OWDUtilities.encryptData("489"), client_id: '489', testing: 'FALSE') {
                OWD_INVENTORY_REPORT_REQUEST() {
                    /*     FILTER()
                                 {
                                     TYPE("startDate")
                                     VALUE("20150114")
                                 }
                       FILTER()
                                 {
                                     TYPE("type")
                                     VALUE("RETURN")
                               }
                         /*  FILTER()
                                 {
                                     TYPE("endDate")
                                     VALUE("20131215")
                                 }*/
                     FILTER()
                            {
                                TYPE("containsSku")
                                VALUE("P395181")
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

    def void testInventoryReportReturnsRecent() throws Exception {

          def builder = new groovy.xml.StreamingMarkupBuilder()
          builder.encoding = 'UTF-8'
          def oneAsnStatusRequest =
          {
              mkp.xmlDeclaration()
              OWD_API_REQUEST(api_version: '2.0', client_authorization: OWDUtilities.encryptData("489"), client_id: '489', testing: 'FALSE') {
                  OWD_INVENTORY_REPORT_REQUEST() {
                      FILTER()
                              {
                                  TYPE("startDate")
                                  VALUE("20141031")


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


    def void testInventoryReportReceivesRecent() throws Exception {

             def builder = new groovy.xml.StreamingMarkupBuilder()
             builder.encoding = 'UTF-8'
             def oneAsnStatusRequest =
             {
                 mkp.xmlDeclaration()
                 OWD_API_REQUEST(api_version: '2.0', client_authorization: OWDUtilities.encryptData("489"), client_id: '489', testing: 'FALSE') {
                     OWD_INVENTORY_REPORT_REQUEST() {
                         FILTER()
                                 {
                                     TYPE("startDate")
                                     VALUE("20141115")
                                 }
                         FILTER()
                                 {
                                     TYPE("type")
                                     VALUE("RECEIVE")
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




}
