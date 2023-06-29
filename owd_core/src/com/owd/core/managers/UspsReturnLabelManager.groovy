package com.owd.core.managers

import com.owd.hibernate.generated.OwdOrder

/**
 * Created with IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 5/6/13
 * Time: 3:28 PM
 * To change this template use File | Settings | File Templates.
 */
class UspsReturnLabelManager {

    public static void main(String[] args) throws Exception
    {


        println getLabelRequestXml(null)

    }

    static String getLabelRequestXml(OwdOrder order, String serviceType, boolean delConf)
    {

        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def createAsnRequest =
            {
                mkp.xmlDeclaration()
                'EMRSV4.0CertifyRequest'(USERID:'815OWD001839', PASSWORD:'')
                        {
                           // Option()
                           // Revision()
                            CustomerName(order.getShipinfo().getShipFirstName()+' '+order.getShipinfo().getShipLastName())
                            CustomerAddress1(order.getShipinfo().getShipAddressOne())
                            CustomerAddress2(order.getShipinfo().getShipAddressTwo())
                            CustomerCity(order.getShipinfo().getShipCity())
                            CustomerState(order.getShipinfo().getShipState())
                            CustomerZip5(order.getShipinfo().getShipZip())
                            CustomerZip4()
                            RetailerName(order.getClient().getCompanyName())
                            RetailerAddress("10 1st Ave E") //facility address
                            PermitNumber("2-000")
                            PermitIssuingPOCity("Mobridge")
                            PermitIssuingPOState("SD")
                            PermitIssuingPOZip5("57601")
                           // PDUFirmName("One World Direct")
                            PDUPOBox("404 North Main Street")
                            PDUCity("Mobridge")
                            PDUState("SD")
                            PDUZip5("57601")
                            PDUZip4()
                            ServiceType(serviceType)   //Priority, First Class, Standard Post
                            DeliveryConfirmation(delConf)
                            InsuranceValue('')   //don't use 0.00
                          //  MailingAckPackageID()
                            WeightInPounds('1')
                            WeightInOUnces('16')
                            RMA('123456')   //2-7 decimal digits
                            RMAPICFlag('true')
                            ImageType('PDF')
                            SenderName()
                            SenderEmail()
                            ReceipientName()
                            ReceipientEmail()
                            RMABarcode('true')

                        }

            }


      //  println "sending"
        String xml = builder.bind(createAsnRequest).toString()
       // println xml
        return xml


    }


}
