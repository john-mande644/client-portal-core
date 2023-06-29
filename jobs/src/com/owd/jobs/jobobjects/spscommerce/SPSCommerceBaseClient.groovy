package com.owd.jobs.jobobjects.spscommerce

import com.owd.LogableException
import com.owd.core.Mailer
import com.owd.core.TagUtilities
import com.owd.core.business.order.*
import com.owd.core.business.order.beans.lineItemExemptions
import com.owd.core.managers.ConnectionManager
import com.owd.core.managers.FacilitiesManager
import com.owd.core.xml.OrderXMLDoc
import com.owd.hibernate.HibUtils
import com.owd.hibernate.HibernateSession
import com.owd.hibernate.generated.*
import com.owd.jobs.jobobjects.spscommerce.beans.AddressInfo
import com.owd.jobs.jobobjects.spscommerce.beans.SPSCommerceRemoteFtpResponse
import com.owd.jobs.jobobjects.spscommerce.beans.SPSReference
import com.owd.jobs.jobobjects.spscommerce.clients.*
import com.owd.jobs.jobobjects.symphony.SymphonyAPI
import com.owd.jobs.jobobjects.vendorCompliance.addressUtils
import org.hibernate.Criteria
import org.hibernate.Query

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
/**
 * Created by danny on 10/11/2018.
 */
class SPSCommerceBaseClient {
    public receiveDirPath = "/in"
    public sendDirPath = "/out"

    static final Map shipMap = [UPSGround: 'UPS Ground']

    def   Map<String, String> outboundShipMap
    def Map<String,String> spsCodeToOWDMethodMap
    def boolean dropShipOrder = false;
    def boolean buyerPartNumIsSku = false;
    def boolean vendorPartNumIsSku = false;
    def boolean includeVendorPartNumInAck = false;
    def boolean useMappingForASN = false;
    def boolean checkForFutureShip = false;
    def boolean isFutureShip = false;
    def String futureShipDate = "";
    def int daysForFutureShip = 7;
    def boolean useAlternateNameForAddress = false;
    def boolean includeBillOfLadingInHeader = false;
    def boolean includeOrderHeaderDepartment = false;
    def boolean includeAllPhysicalDetails = false;
    def boolean useGTIN = false;
    def boolean forceSkuMapUsage = false;
    def boolean includeOWDDunsLocationNumberASN = false;
    def boolean useUPCLookupForProductOrItemDescription = false;
    def boolean useShipToAddressAlternateName = false;
    def boolean includeOrderQtyUOMShipmentLine = false;
    def boolean includeDepositorOrderNumber = false
    def boolean includeApplicationId = false
    def boolean useEAN = false;
    def boolean includePurchasePrice = false;
    def String warehouseNotes = ""


    SPSCommerceBaseClient() {
        outboundShipMap = new TreeMap<String, String>()
        spsCodeToOWDMethodMap = new TreeMap<String,String>()

        outboundShipMap.put("FedEx 2Day", "FX2D");
        outboundShipMap.put("FedEx Express Saver", "FEDX");
        outboundShipMap.put("FedEx Ground", "FDXG");
        outboundShipMap.put("FedEx Home Delivery", "FEDH");
        outboundShipMap.put("FedEx Standard Overnight", "FXND");
        outboundShipMap.put("FedEx Priority Overnight", "FEDX_NM");
        outboundShipMap.put("FedEx SmartPost", "FXSP");
        outboundShipMap.put("LTL", "LTL");
        outboundShipMap.put("UPS 2nd Day Air", "UPSN_SE");
        outboundShipMap.put("UPS 3 Day Select", "UPSN");
        outboundShipMap.put("UPS Ground", "UPSG");
        outboundShipMap.put("UPS Next Day Air", "UPND");
        outboundShipMap.put("UPS Next Day Air Saver", "UPSN_PM");
        outboundShipMap.put("USPS Priority Mail", "USPS");
        outboundShipMap.put("USPS Parcel Select Nonpresort", "USPS");

        //replace any Unspecified (Map in setup) in the vendor class if needed
        spsCodeToOWDMethodMap.put("UNSP_SE", "2nd day - (Map in setup)");
        spsCodeToOWDMethodMap.put("FEDX_SE", "FedEx 2Day");
        spsCodeToOWDMethodMap.put("FDEG_SE", "FedEx 2Day");
        spsCodeToOWDMethodMap.put("FDE_SC", "FedEx 2Day");
        spsCodeToOWDMethodMap.put("FX2D", "FedEx 2Day");
        spsCodeToOWDMethodMap.put("FEDX_SE_ET", "FedEx 2Day");
        spsCodeToOWDMethodMap.put("FEDX_SE_AC", "FedEx 2Day");
        spsCodeToOWDMethodMap.put("FEDX_SE_IA", "FedEx 2Day");
        spsCodeToOWDMethodMap.put("FEDX_CG", "FedEx Ground");
        spsCodeToOWDMethodMap.put("FDEG_CG", "FedEx Ground");
        spsCodeToOWDMethodMap.put("FDEG", "FedEx Ground");
        spsCodeToOWDMethodMap.put("FEDEXG", "FedEx Ground");
        spsCodeToOWDMethodMap.put("FDXG", "FedEx Ground");
        spsCodeToOWDMethodMap.put("FEDX_CG_ET", "FedEx Ground");
        spsCodeToOWDMethodMap.put("FEDX_09", "FedEx Home Delivery");
        spsCodeToOWDMethodMap.put("FEDH", "FedEx Home Delivery");
        spsCodeToOWDMethodMap.put("FEDX_ND", "FedEx Standard Overnight");
        spsCodeToOWDMethodMap.put("FDEG_ND", "FedEx Standard Overnight");
        spsCodeToOWDMethodMap.put("FXND", "FedEx Standard Overnight");
        spsCodeToOWDMethodMap.put("FDEX", "FedEx Standard Overnight");
        spsCodeToOWDMethodMap.put("FEDX_ND_ET", "FedEx Standard Overnight");
        spsCodeToOWDMethodMap.put("FXND_AC", "FedEx Standard Overnight");
        spsCodeToOWDMethodMap.put("FXND_IA", "FedEx Standard Overnight");
        spsCodeToOWDMethodMap.put("FEDX_NM", "FedEx Priority Overnight");
        spsCodeToOWDMethodMap.put("FEDX_NM_ET", "FedEx Priority Overnight");
        spsCodeToOWDMethodMap.put("FEDX_NM_AC", "FedEx Priority Overnight");
        spsCodeToOWDMethodMap.put("FEDX_NM_IA", "FedEx Priority Overnight");
        spsCodeToOWDMethodMap.put("UNSP_CG", "Ground (Map in Setup)");
        spsCodeToOWDMethodMap.put("LTL_G2", "LTL");
        spsCodeToOWDMethodMap.put("LTL_R1", "LTL");
        spsCodeToOWDMethodMap.put("LTL_09", "LTL");
        spsCodeToOWDMethodMap.put("LTL_DS", "LTL");
        spsCodeToOWDMethodMap.put("UNSP_ND", "Overnight - (Map in Setup)");
        spsCodeToOWDMethodMap.put("UPSN_SE", "UPS 2nd Day Air");
        spsCodeToOWDMethodMap.put("UB", "UPS 2nd Day Air");
        spsCodeToOWDMethodMap.put("UPSN_SC", "UPS 2nd Day Air");
        spsCodeToOWDMethodMap.put("UPSET_SE", "UPS 2nd Day Air");
        spsCodeToOWDMethodMap.put("UY", "UPS 2nd Day Air");
        spsCodeToOWDMethodMap.put("UPSN_CG", "UPS Ground");
        spsCodeToOWDMethodMap.put("UG", "UPS Ground");
        spsCodeToOWDMethodMap.put("UPSG", "UPS Ground");
        spsCodeToOWDMethodMap.put("UPSET_CG", "UPS Ground");
        spsCodeToOWDMethodMap.put("UX", "UPS Ground");
        spsCodeToOWDMethodMap.put("UPSN_ND", "UPS Next Day Air");
        spsCodeToOWDMethodMap.put("UPND", "UPS Next Day Air");
        spsCodeToOWDMethodMap.put("UR", "UPS Next Day Air");
        spsCodeToOWDMethodMap.put("UPS1", "UPS Next Day Air");
        spsCodeToOWDMethodMap.put("UPSN_NM", "UPS Next Day Air");
        spsCodeToOWDMethodMap.put("UPSN_PM", "UPS Next Day Air Saver");
        spsCodeToOWDMethodMap.put("UPSET_ND", "UPS Next Day Air");
        spsCodeToOWDMethodMap.put("UZ", "UPS Next Day Air");
        spsCodeToOWDMethodMap.put("UPSN_ST", "UPS SurePost 1 lb or Greater");
        spsCodeToOWDMethodMap.put("USPS_BC", "USPS Parcel Select Nonpresort");
        spsCodeToOWDMethodMap.put("USPS_PB", "USPS Priority Mail");
        spsCodeToOWDMethodMap.put("UPSN","UPS Ground");
    }

    def boolean ignoreTheUOM(){
        return false;
    }

    def boolean includeOuterpackShipmentLine(){
        return false;
    }
    def boolean includeInnerpackShipmentLine(){
        return false;
    }
    def boolean includeProductOrItemDescriptionFromFile(){
        return false;
    }

    // receiveDirTestPath("/testin"),
    //  sendDirTestPath("/testout");

    def static int saveIncomingEdiDocForTesting(String docContent, String fileName, String account) {

        return saveIncomingEdiDoc(docContent, fileName, true,account)
    }

    def static int saveIncomingEdiDoc(String docContent, String fileName,String account) {
        return saveIncomingEdiDoc(docContent, fileName, false,account)

    }

    def static int saveIncomingEdiDoc(String docContent, String fileName, boolean testing,String account) {

        println(docContent)
        println(fileName)
        println(testing)
        PreparedStatement ps = HibernateSession.getPreparedStatement("insert into edi_docs (docData,sourceFile,docStatus,account) values (?,?,?,?);SELECT @@IDENTITY")
        ps.setString(1, docContent)
        ps.setString(2, fileName)
        ps.setInt(3, (testing ? 2 : 0))
        ps.setString(4,account);

        ps.executeUpdate()

        long newId = 0;




        int iUpdCount = ps.getUpdateCount();
        boolean bMoreResults = true;
        ResultSet rs = null;

        //While there are still more results or update counts
        //available, continue processing resultsets
        while (bMoreResults || iUpdCount != -1) {
            //NOTE: in order for output parameters to be available,
            //all resultsets must be processed

            rs = ps.getResultSet();

            //if rs is not null, we know we can get the results from the SELECT @@IDENTITY
            if (rs != null) {
                rs.next();
                newId = rs.getInt(1);
            }

            //Do something with the results here (not shown)

            //get the next resultset, if there is one
            //this call also implicitly closes the previously obtained ResultSet
            bMoreResults = ps.getMoreResults();
            iUpdCount = ps.getUpdateCount();
        }

        ps.close()
        HibUtils.commit(HibernateSession.currentSession())

        return newId


    }

    def static List processRemotePOs(SPSCommerceRemoteFTP ftp, String path) {

        List orders = new ArrayList();


        ftp.listFiles(path).each { filename ->
            if (filename?.startsWith(SPSCommerceRemoteFTP.fileType.PO.toString())) {
                println "loading filename " + filename
                def podata = new XmlParser().parseText(new String(ftp.getDataFile(filename, path)))

                //  Mailer.sendMail("PO received from SPS",new String(SPSCommerceRemoteFTP.getDataFile(filename, path)),"owditadmin@owd.com","edi@owd.com");


                if (podata?.name().toString().equals(("{http://www.spscommerce.com/RSX}Orders"))) {
                    podata.Order.each { po ->

                        StringWriter sw = new StringWriter()
                        new XmlNodePrinter(new PrintWriter(sw)).print(po)
                        println sw.toString()

                        String account = po.Header.OrderHeader.TradingPartnerId.text()

                        println saveIncomingEdiDoc(sw.toString(), filename,account)

                        HibUtils.commit(HibernateSession.currentSession())

                        //  orders.add(importPo(po, clientId))
                    }

                    //

                } else if (podata?.name() == "Order") {

                    StringWriter sw = new StringWriter()
                    new XmlNodePrinter(new PrintWriter(sw)).print(podata)
                    println sw.toString()
                    String account = podata.Header.OrderHeader.TradingPartnerId.text()
                    println saveIncomingEdiDoc(sw.toString(), filename,account)

                    HibUtils.commit(HibernateSession.currentSession())
                    //   ftp.deleteDataFile(filename, path)
                } else {
                    println "Unrecognized root element name " + (podata?.name())
                    Mailer.sendMail("Problem importing SPS PO file", new String(ftp.getDataFile(filename, path)), "owditadmin@owd.com", "edi@owd.com");

                    //ignore for now
                }
                ftp.deleteDataFile(filename, path)
            }
        }

        return orders;

    }



    def static void main(String[] args) {
       // processPendingPos()
        //generateInvoice("hello", 1)
        // println storeMap

        //    processPendingPos();
        /* XmlParser parser = new XmlParser()
         parser.setTrimWhitespace(true)
         OwdOrder order = OrderFactory.getOwdOrderFromOrderID(11851041, 489, true)



         OwdFacility shipFrom = FacilitiesManager.getFacilityForCode(order.getFacilityCode())
         Map<String, String> tagMap = TagUtilities.getTagMap("ORDER", order.getOrderId())

         println "asn tagmap: " + tagMap
         String poNode = tagMap.get(TagUtilities.kEDIPurchaseOrder.toUpperCase())
         def poData = parser.parseText(poNode)
         EdiSpsConfigdata configdata = getEdiConfigData(poData)*/
        /* String ack = SpsCommerceUtilities.generateACK(configdata,poData,471,order)
         println ack*/
        // System.out.println(getUPCFromOwdSku(configdata, "P222189"))


       /* submitASN(21970386,626);
        submitASN(21970405,626);
        submitASN(21970411,626);
        submitASN(21972775,626);*/



        // submitASN(21731904,626);

       // submitASN(20319131,651);
      //  submitASN(20119956,575);
       // submitASN(20119957,575);


    }

    public static boolean submitASN(Integer orderId, int clientId){
        boolean success = false;
        try {

            SPSCommerceRemoteFTP ftp;
            OrderUtilities.checkLTLAndUpdateTrackingAndPackages(orderId+"");

            OwdOrder order = OrderFactory.getOwdOrderFromOrderID(orderId, clientId, true)
            Map<String, String> tagMap = TagUtilities.getTagMap("ORDER", order.getOrderId())
            println "asn tagmap: " + tagMap
            String poNode = tagMap.get(TagUtilities.kEDIPurchaseOrder.toUpperCase())
            XmlParser parser = new XmlParser()
            parser.setTrimWhitespace(true)


            def poData = parser.parseText(poNode)

            // SPSCommerceRemoteFTP

            EdiSpsConfigdata configdata = getEdiConfigData(poData);

            //Some drop shippers still require sscc even through it's not on package. This is set in config
            if(configdata.isSsccOnDropship()){
                OrderUtilities.addSSCCToPackages(orderId);
            }
            String asnData = "";
            if (configdata.getSpsaccount().equals("093ALLKADENASPO")) {
                JustBrandsCabelasEDI edi = new JustBrandsCabelasEDI();
                ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");
                asnData = edi.generateASN(orderId, clientId);

            }
            if (configdata.getSpsaccount().equals("0XRALLJUSTBRAND")) {
                JustBrandsDicksEDI edi = new JustBrandsDicksEDI();
                ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");
                asnData = edi.generateASN(orderId, clientId);

            }
            if (configdata.getSpsaccount().equals("9J6ALLJUSTBRAND")) {
                JustBrandsHomeDepotEDI edi = new JustBrandsHomeDepotEDI();
                ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");
                asnData = edi.generateASN(orderId, clientId);

            }
            if (configdata.getSpsaccount().equals("BKDALLJUSTBRAND")) {
                JustBrandsBedBathBeyondEDI edi = new JustBrandsBedBathBeyondEDI();
                ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");
                asnData = edi.generateASN(orderId, clientId);

            }
            if (configdata.getSpsaccount().equals("501ALLJUSTBRAND")) {
                JustBrandsDicksWarehouseEDI edi = new JustBrandsDicksWarehouseEDI();
                ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");
                asnData = edi.generateASN(orderId, clientId);

            }
            if (configdata.getSpsaccount().equals("95OALLJUSTBRAND")) {
                JustBrandsTargetEDI edi = new JustBrandsTargetEDI();
                ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");
                asnData = edi.generateASN(orderId, clientId);

            }
            if (configdata.getSpsaccount().equals("563ALLJUSTBRAND")) {
                JustBrandsBassProEDI edi = new JustBrandsBassProEDI();
                ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");
                asnData = edi.generateASN(orderId, clientId);

            }
            if (configdata.getSpsaccount().equals("BPRALLJUSTBRAND")) {
                JustBrandsScheelsEDI edi = new JustBrandsScheelsEDI();
                ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");
                asnData = edi.generateASN(orderId, clientId);

            }
            if (configdata.getSpsaccount().equals("CQKALLJUSTBRAND")) {
                JustBrandsOnyxEDI edi = new JustBrandsOnyxEDI();
                ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");
                asnData = edi.generateASN(orderId, clientId);

            }
            if (configdata.getSpsaccount().equals("61YFWDBUMBLONEW")) {
                BumbleRideWilliamsSanomaEDI edi = new BumbleRideWilliamsSanomaEDI();
                ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");
                asnData = edi.generateASN(orderId, clientId);
            }


            if (configdata.getSpsaccount().equals("APOFWDBUMBLERID")) {
                BumbleRideMagicBeansEDI edi = new BumbleRideMagicBeansEDI();
                ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");
                asnData = edi.generateASN(orderId, clientId);
            }

            if (configdata.getSpsaccount().equals("77WALLLAWLESSBE")) {
                LawlessBeautySephoraEDI edi = new  LawlessBeautySephoraEDI();
                ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");
                asnData = edi.generateASN(orderId, clientId);

            }
            if (configdata.getSpsaccount().equals("009FWDTUSHBABYI")) {
                TushBabyTargetStockingEDI edi = new  TushBabyTargetStockingEDI();
                ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");
                asnData = edi.generateASN(orderId, clientId);

            }
            if (configdata.getSpsaccount().equals("832FWDTUSHBABYI")) {
                TushBabyBedBathBeyondEDI edi = new  TushBabyBedBathBeyondEDI();
                ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");
                asnData = edi.generateASN(orderId, clientId);

            }
            if (configdata.getSpsaccount().equals("089ALLJUSTBRAND")) {
                JustBrandsCampingWorldStockingEDI edi = new JustBrandsCampingWorldStockingEDI();
                ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");
                asnData = edi.generateASN(orderId, clientId);

            }
            if (configdata.getSpsaccount().equals("5P1ALLJUSTBRAND")) {
                JustBrandsRetailConceptsEDI edi = new  JustBrandsRetailConceptsEDI();
                ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");
                asnData = edi.generateASN(orderId, clientId);

            }
            if (configdata.getSpsaccount().equals("3ANALLJUSTBRAND")) {
                JustBrandsRetailConceptsDropShipEDI edi = new   JustBrandsRetailConceptsDropShipEDI();
                ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");
                asnData = edi.generateASN(orderId, clientId);

            }
            if (configdata.getSpsaccount().equals("525ALLJUSTBRAND")) {
                JustBrandsAceHardwareEDI edi = new  JustBrandsAceHardwareEDI();
                ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");
                asnData = edi.generateASN(orderId, clientId);

            }
            if (configdata.getSpsaccount().equals("031ALLROVRPRODU")) {
                RovrREIEDI edi = new  RovrREIEDI();
                ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");
                asnData = edi.generateASN(orderId, clientId);

            }

            if (configdata.getSpsaccount().equals("61FALLDOGEAREDJ")) {
                DogEaredZapposEDI edi = new  DogEaredZapposEDI();
                ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");
                asnData = edi.generateASN(orderId, clientId);
            }
            if (configdata.getSpsaccount().equals("61FFWDDOGEAREDJ")) {
                DogEaredZapposEDI edi = new  DogEaredZapposEDI();
                ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");
                asnData = edi.generateASN(orderId, clientId);
            }
            if (configdata.getSpsaccount().equals("501FWDBLUETEESG")) {
                BlueTeesGolfDicksWarehouseEDI edi = new  BlueTeesGolfDicksWarehouseEDI();
                ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");
                asnData = edi.generateASN(orderId, clientId);

            }

            if (configdata.getSpsaccount().equals("BPRFWDBLUETEESG")) {
                BlueTeesGolfScheelsEDI edi = new BlueTeesGolfScheelsEDI();
                ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");
                asnData = edi.generateASN(orderId, clientId);
            }

            if (configdata.getSpsaccount().equals("563ALLROVRPRODU")) {
                RovrBassProEDI edi = new  RovrBassProEDI();
                ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");
                asnData = edi.generateASN(orderId, clientId);

            }
            if (configdata.getSpsaccount().equals("BRQALLROVRPRODU")) {
                ROVRBackCountryEDI edi = new   ROVRBackCountryEDI();

                ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");
                asnData = edi.generateASN(orderId, clientId);


            }

            if (configdata.getSpsaccount().equals("089ALLROVRPRODU")) {
                ROVRCampingWorldStockingEDI edi = new   ROVRCampingWorldStockingEDI();

                ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");
                asnData = edi.generateASN(orderId, clientId);

            }

            if (configdata.getSpsaccount().equals("529ALLJUSTBRAND")) {
                JustBrandsWalmartEDI edi = new   JustBrandsWalmartEDI();

                ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");
                asnData = edi.generateASN(orderId, clientId);

            }

            if (configdata.getSpsaccount().equals("DGTALLROVRPRODU")) {
                ROVRMooseJawEDI edi = new   ROVRMooseJawEDI();

                ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");
                asnData = edi.generateASN(orderId, clientId);

            }

            if (configdata.getSpsaccount().equals("BAGALLALLBLUETE")) {
                BlueTeesGolfGolfWarehouseEDI edi = new   BlueTeesGolfGolfWarehouseEDI();

                ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");
                asnData = edi.generateASN(orderId, clientId);
            }

            if (configdata.getSpsaccount().equals("DWHALLBLUETEESG")) {
                BlueTeesGolfAcademySportRadialEDI edi = new   BlueTeesGolfAcademySportRadialEDI();

                ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");
                asnData = edi.generateASN(orderId, clientId);
            }

            if (configdata.getSpsaccount().equals("E90ALLJUSTBRAND")) {
                JustBrandsModellsEDI edi = new JustBrandsModellsEDI();

                ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");
                asnData = edi.generateASN(orderId, clientId);
            }

            if (configdata.getSpsaccount().equalsIgnoreCase("FYASOLDTHROUGH0")) {
                SoldThroughNetsuiteEDI edi = new SoldThroughNetsuiteEDI();

                ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");
                asnData = edi.generateASN(orderId, clientId);
            }

            if (configdata.getSpsaccount().equalsIgnoreCase("A6LALLSOLDTHROU")) {
                SoldThroughLordAndTaylorEDI edi = new SoldThroughLordAndTaylorEDI();

                ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");
                asnData = edi.generateASN(orderId, clientId);
            }

            if (configdata.getSpsaccount().equalsIgnoreCase("080ALLPOPULARBO")) {
                PopularHoldingsAmazonEDI edi = new PopularHoldingsAmazonEDI();

                ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");
                asnData = edi.generateASN(orderId, clientId);
            }

            if (configdata.getSpsaccount().equalsIgnoreCase("DNTALLSOLDTHROU")) {
                SoldThroughBelkEDI edi = new SoldThroughBelkEDI();

                ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");
                asnData = edi.generateASN(orderId, clientId);
            }

            if (configdata.getSpsaccount().equalsIgnoreCase("DJNALLJUSTBRAND")) {
                JustBrandsTractorSupplyCoEDI edi = new JustBrandsTractorSupplyCoEDI();

                ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");
                asnData = edi.generateASN(orderId, clientId);
            }

            if (configdata.getSpsaccount().equalsIgnoreCase("BPRALLROVRPRODU")) {
                ROVRScheelsEDI edi = new ROVRScheelsEDI();

                ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");
                asnData = edi.generateASN(orderId, clientId);
            }

            if (configdata.getSpsaccount().equalsIgnoreCase("502FWDBLUETEES1")) {
                BlueTeesGolfAcademySportRadialStockingEDI edi = new BlueTeesGolfAcademySportRadialStockingEDI();

                ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");
                asnData = edi.generateASN(orderId, clientId);
            }


            if (asnData.length() > 0) {
                println(asnData);
                TagUtilities.setOrderTag(orderId,TagUtilities.kEDIAsn,asnData);
                SPSCommerceRemoteFtpResponse response = ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, asnData.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirPath)

                success = (response.getResponseCode() >= 200 && response.getResponseCode() < 300);

                if (!success) {
                    new LogableException(response.getResponseMessage() , orderId+"", clientId+"","Submit EDI SPS ASN",LogableException.errorTypes.SHIPMENT_NOTIFICATION)
                }
            }

        }catch (Exception e){
            e.printStackTrace()
            new LogableException(e.getMessage(),orderId+"",clientId+"","Submit EDI SPS ASN",LogableException.errorTypes.SHIPMENT_NOTIFICATION)
        }

        return success;
    }

    public static void processPendingPos(){
        try {
            List<EdiDocs> docsToProcess = HibernateSession.currentSession().createQuery("from EdiDocs where docStatus=0 and account<>''").list()
            for (EdiDocs doc : docsToProcess) {

                if (doc.getAccount().equalsIgnoreCase("093ALLKADENASPO")) {
                    JustBrandsCabelasEDI edi = new JustBrandsCabelasEDI();
                    SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");

                    edi.processSinglePo(doc, ftp);

                }
                if (doc.getAccount().equalsIgnoreCase("0XRALLJUSTBRAND")) {
                    JustBrandsDicksEDI edi = new JustBrandsDicksEDI();
                    SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");

                    edi.processSinglePo(doc, ftp);

                }
                if (doc.getAccount().equalsIgnoreCase("9J6ALLJUSTBRAND")) {
                    JustBrandsHomeDepotEDI edi = new JustBrandsHomeDepotEDI();
                    SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");

                    edi.processSinglePo(doc, ftp);

                }
                if (doc.getAccount().equalsIgnoreCase("BKDALLJUSTBRAND")) {
                    JustBrandsBedBathBeyondEDI edi = new JustBrandsBedBathBeyondEDI();
                    SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");

                    edi.processSinglePo(doc, ftp);

                }
                if (doc.getAccount().equalsIgnoreCase("501ALLJUSTBRAND")) {
                    JustBrandsDicksWarehouseEDI edi = new JustBrandsDicksWarehouseEDI();
                    SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");

                    edi.processSinglePo(doc, ftp);

                }
                if (doc.getAccount().equalsIgnoreCase("95OALLJUSTBRAND")) {
                    JustBrandsTargetEDI edi = new JustBrandsTargetEDI();
                    SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");

                    edi.processSinglePo(doc, ftp);

                }
                if (doc.getAccount().equalsIgnoreCase("563ALLJUSTBRAND")) {
                    JustBrandsBassProEDI edi = new JustBrandsBassProEDI();
                    SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");

                    edi.processSinglePo(doc, ftp);

                }
                if (doc.getAccount().equalsIgnoreCase("BPRALLJUSTBRAND")) {
                    JustBrandsScheelsEDI edi = new JustBrandsScheelsEDI();
                    SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");

                    edi.processSinglePo(doc, ftp);

                }
                if (doc.getAccount().equalsIgnoreCase("CQKALLJUSTBRAND")) {
                    JustBrandsOnyxEDI edi = new JustBrandsOnyxEDI();
                    SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");

                    edi.processSinglePo(doc, ftp);

                }
                if (doc.getAccount().equalsIgnoreCase("61YFWDBUMBLONEW")) {
                    BumbleRideWilliamsSanomaEDI edi = new BumbleRideWilliamsSanomaEDI();
                    SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");
                    edi.processSinglePo(doc, ftp);
                }

                if (doc.getAccount().equalsIgnoreCase("APOFWDBUMBLERID")) {
                    BumbleRideMagicBeansEDI edi = new BumbleRideMagicBeansEDI();
                    SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");
                    edi.processSinglePo(doc, ftp);
                }

                if (doc.getAccount().equalsIgnoreCase("77WALLLAWLESSBE")) {
                    LawlessBeautySephoraEDI edi = new LawlessBeautySephoraEDI();
                    SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");

                    edi.processSinglePo(doc, ftp);

                }
                if (doc.getAccount().equalsIgnoreCase("009FWDTUSHBABYI")) {
                    TushBabyTargetStockingEDI edi = new TushBabyTargetStockingEDI();
                    SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");

                    edi.processSinglePo(doc, ftp);

                }
                if (doc.getAccount().equalsIgnoreCase("832FWDTUSHBABYI")) {
                    TushBabyBedBathBeyondEDI edi = new TushBabyBedBathBeyondEDI();
                    SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");

                    edi.processSinglePo(doc, ftp);

                }
                if (doc.getAccount().equalsIgnoreCase("089ALLJUSTBRAND")) {
                    JustBrandsCampingWorldStockingEDI edi = new JustBrandsCampingWorldStockingEDI();
                    SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");

                    edi.processSinglePo(doc, ftp);


                }
                if (doc.getAccount().equalsIgnoreCase("5P1ALLJUSTBRAND")) {
                    JustBrandsRetailConceptsEDI edi = new JustBrandsRetailConceptsEDI();
                    SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");

                    edi.processSinglePo(doc, ftp);


                }
                if (doc.getAccount().equalsIgnoreCase("3ANALLJUSTBRAND")) {
                    JustBrandsRetailConceptsDropShipEDI edi = new JustBrandsRetailConceptsDropShipEDI();
                    SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");

                    edi.processSinglePo(doc, ftp);


                }
                if (doc.getAccount().equalsIgnoreCase("525ALLJUSTBRAND")) {
                    JustBrandsAceHardwareEDI edi = new JustBrandsAceHardwareEDI();
                    SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");

                    edi.processSinglePo(doc, ftp);


                }
                if (doc.getAccount().equalsIgnoreCase("031ALLROVRPRODU")) {
                    RovrREIEDI edi = new RovrREIEDI();
                    SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");

                    edi.processSinglePo(doc, ftp);


                }
                if (doc.getAccount().equalsIgnoreCase("61FALLDOGEAREDJ")) {
                    DogEaredZapposEDI edi = new DogEaredZapposEDI();
                    SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");

                    edi.processSinglePo(doc, ftp);
                }

                if (doc.getAccount().equalsIgnoreCase("61FFWDDOGEAREDJ")) {
                    DogEaredZapposEDI edi = new DogEaredZapposEDI();
                    SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");

                    edi.processSinglePo(doc, ftp);
                }

                if (doc.getAccount().equalsIgnoreCase("501FWDBLUETEESG")) {
                    BlueTeesGolfDicksWarehouseEDI edi = new BlueTeesGolfDicksWarehouseEDI();
                    SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");

                    edi.processSinglePo(doc, ftp);


                }

                if (doc.getAccount().equalsIgnoreCase("BPRFWDBLUETEESG")) {
                    BlueTeesGolfScheelsEDI edi = new BlueTeesGolfScheelsEDI();
                    SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");

                    edi.processSinglePo(doc, ftp);
                }

                if (doc.getAccount().equalsIgnoreCase("563ALLROVRPRODU")) {
                    RovrBassProEDI edi = new RovrBassProEDI();
                    SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");

                    edi.processSinglePo(doc, ftp);
                }

                if (doc.getAccount().equalsIgnoreCase("BRQALLROVRPRODU")) {
                    ROVRBackCountryEDI edi = new ROVRBackCountryEDI();
                    SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");

                    edi.processSinglePo(doc, ftp);
                }

                // ROVR CampingWorld
                if (doc.getAccount().equalsIgnoreCase("089ALLROVRPRODU")) {
                    ROVRCampingWorldStockingEDI edi = new ROVRCampingWorldStockingEDI();
                    SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");

                    edi.processSinglePo(doc, ftp);
                }

                if (doc.getAccount().equalsIgnoreCase("529ALLJUSTBRAND")) {
                    JustBrandsWalmartEDI edi = new JustBrandsWalmartEDI();

                    SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");

                    edi.processSinglePo(doc, ftp);
                }

                if (doc.getAccount().equalsIgnoreCase("DGTALLROVRPRODU")) {
                    ROVRMooseJawEDI edi = new ROVRMooseJawEDI();
                    SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");

                    edi.processSinglePo(doc, ftp);
                }

                if (doc.getAccount().equalsIgnoreCase("BAGALLALLBLUETE")) {
                    BlueTeesGolfGolfWarehouseEDI edi = new BlueTeesGolfGolfWarehouseEDI();
                    SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");

                    edi.processSinglePo(doc, ftp);
                }

                if (doc.getAccount().equalsIgnoreCase("DWHALLBLUETEESG")) {
                    BlueTeesGolfAcademySportRadialEDI edi = new BlueTeesGolfAcademySportRadialEDI();
                    SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");

                    edi.processSinglePo(doc, ftp);
                }

                if (doc.getAccount().equalsIgnoreCase("E90ALLJUSTBRAND")) {
                    JustBrandsModellsEDI edi = new JustBrandsModellsEDI();
                    SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");

                    edi.processSinglePo(doc, ftp);
                }

                if (doc.getAccount().equalsIgnoreCase("FYASOLDTHROUGH0")) {
                    SoldThroughNetsuiteEDI edi = new SoldThroughNetsuiteEDI();
                    SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");

                    edi.processSinglePo(doc, ftp);
                }

                if (doc.getAccount().equalsIgnoreCase("A6LALLSOLDTHROU")) {
                    SoldThroughLordAndTaylorEDI edi = new SoldThroughLordAndTaylorEDI();
                    SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");

                    edi.processSinglePo(doc, ftp);
                }

                if (doc.getAccount().equalsIgnoreCase("080ALLPOPULARBO")) {
                    PopularHoldingsAmazonEDI edi = new PopularHoldingsAmazonEDI();
                    SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");

                    edi.processSinglePo(doc, ftp);
                }

                if (doc.getAccount().equalsIgnoreCase("DNTALLSOLDTHROU")) {
                    SoldThroughBelkEDI edi = new SoldThroughBelkEDI();
                    SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");

                    edi.processSinglePo(doc, ftp);
                }

                if (doc.getAccount().equalsIgnoreCase("DJNALLJUSTBRAND")) {
                    JustBrandsTractorSupplyCoEDI edi = new JustBrandsTractorSupplyCoEDI();
                    SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");

                    edi.processSinglePo(doc, ftp);
                }

                if (doc.getAccount().equalsIgnoreCase("BPRALLROVRPRODU")) {
                    ROVRScheelsEDI edi = new ROVRScheelsEDI();
                    SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");

                    edi.processSinglePo(doc, ftp);
                }

                if (doc.getAccount().equalsIgnoreCase("502FWDBLUETEES1")) {
                    BlueTeesGolfAcademySportRadialStockingEDI edi = new BlueTeesGolfAcademySportRadialStockingEDI();
                    SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");

                    edi.processSinglePo(doc, ftp);
                }
            }
        }catch (Exception e) {
            e.printStackTrace()
        }
    }
    void loadOrderTemplate(Order order){
        //overload in custom class for vendor as needed
    }
    void doFinalStuffBeforeOrderSave(Order order){
        //overload in custom class for vendor as needed
    }
    void  loadBusinessOrConsumerOrder(Order order){

    }
     void loadThirdPartyBillingInfo(Order order){
         //overload in custom class for vendor as needed
    }
    def String loadShippingMethod(Order order, String method, Object carrierInformation) throws Exception{
        //overload in custom class for vendor as needed
    }
    void loadLabelInfo(Order order){
        //overload in custom class for vendor as needed
    }
    public void processSinglePo(EdiDocs doc,SPSCommerceRemoteFTP ftp) {
        //todo create base process file. make this a generic template put all actions into overridable methods
         try {

                String notificationEmails = "";
                String errorPO = "";
                String errorCustomerNo="";
                String errorVendor = "";
                try {
                    println doc.getDocData()
                    XmlParser parser = new XmlParser()
                    parser.setTrimWhitespace(true)
                    Node po = parser.parseText(doc.getDocData())

                    doc.setDocStatus(1)
                    // doc.setSourceFile(doc.getSourceFile()+'.dtd.')
                    //   HibernateSession.currentSession().evict(doc);
                    // HibernateSession.currentSession.update(object);
                    HibernateSession.currentSession().saveOrUpdate(doc)
                    HibernateSession.currentSession().flush()
                    HibUtils.commit(HibernateSession.currentSession())
                    //   HibernateSession.currentSession().flush()
                    EdiSpsConfigdata config = SpsCommerceUtilities.getEdiConfigData(po)
                    notificationEmails = config.getErrorNotification();
                    int clientId = config.getClientFkey();
                    Order order;

                        order = importPo(po, clientId)


                    errorPO = order.po_num;
                    errorVendor = config.getName();
                    errorCustomerNo = po.Header.OrderHeader.CustomerOrderNumber.text();

                    boolean canceledOrder = false;
                    if (clientId == 489 || clientId == 491) {  //Symphony
                        SymphonyAPI.postOrderToSymphony(order, doc.getId(), po, false)

                    } else {
                        //set dropship flag for use in vendorId lookup
                        setDropShipFlag(po);

                        //next three lines normally happen in API when Symphony posts order
                        order.addTag(TagUtilities.kEDIPurchaseOrder, doc.getDocData());
                        order.addTag(TagUtilities.kVendorComplianceReference, config.getName());
                        try {
                            int vComplainceId = getVendorComplianceId(config,po);
                            if (vComplainceId > 0) {
                                order.addTag(TagUtilities.kVendorComplianceIDReference, vComplainceId + "");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        order.order_type = order.order_type + ":EDI:";
                        order.forcePayment = false
                        order.is_paid = 1
                        order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;

                        //this must match the facility that your test SKU has inventory in
                        //DOES NOT HANDLE MULTIPLE FACILITY CLIENTS YET

                         //   order.facilityCode = FacilitiesManager.getLocationCodeForClient(clientId)
                         //   order.facilityPolicy = FacilitiesManager.getLocationCodeForClient(clientId)
                        //todo pull proper ship methods from mapping
                        order.backorder_rule = loadBackorderRule();
                        order.setShippingMethodName("FedEx Ground");
                        order.group_name = config.getName();
                        order.bill_cc_type = "CK";
                        order.is_paid = 1

                        String method = po.LineItems.LineItem[0].CarrierInformation.CarrierRouting.text();
                        if(null == method || method.length()==0){
                           method =  po.Header.CarrierInformation.CarrierRouting.text();
                        }

                        order.setShippingMethodName(loadShippingMethod(order, method, po.Header.CarrierInformation))
                        loadThirdPartyBillingInfo(order);
                        loadOrderTemplate(order);
                        loadBusinessOrConsumerOrder(order);
                        loadGiftMessageIfIncluded(order,po);
                        //Overide in client if needing to be different than config
                        setGroupName(order,po)
                        if(isFutureShip){
                            order.holdNewOrder();
                            order.hold_reason = "Future Ship for EDI Order";
                            order.setBackorderRule(OrderXMLDoc.kHoldBackOrder);
                        }
                        doFinalStuffBeforeOrderSave(order);





                        //Amazon
                        if (config.getSpsaccount().equals("080ALLGILDANUSA")||config.spsaccount.equals("FYAALLHVMNINC00")) {
                            order.backorder_rule = OrderXMLDoc.kRejectBackOrder
                            order.setShippingMethodName("LTL");
                            order.group_name = "Amazon";
                            order.bill_cc_type = "CK";
                            order.is_paid = 1


                        }
                        //Dicks Sporting Goods Gildan
                        if(config.spsaccount.equals("0XRALLGILDANUSA")){
                            order.backorder_rule = OrderXMLDoc.kRejectBackOrder

                            order.group_name = "Dicks";
                            order.bill_cc_type = "CK";
                            order.is_paid = 1
                            order.companyOverride = "DICK'S Sporting Goods";
                            order.nameOverride = "Returns Department";
                            order.address1Override = "7603 Trade Port Drive";
                            order.cityOverride = "Louisville";
                            order.stateOverride = "KY";
                            order.zipOverride = "40258";

                            order.setThirdPartyBilling("9V3W92");
                            order.setThirdPartyBillingContact("DICK'S Sporting Goods","DICK'S Sporting Goods","7603 Trade Port Drive","","Louisville","KY","40258","6058457172");
                        }

                        String reference = order.saveNewOrder(OrderUtilities.kRequirePayment, false);

                        if (reference == null) {
                            if(order.last_error.contains("No valid line items in order - order could not be saved")){
                                if(config.spsaccount.equals("0XRALLGILDANUSA")||config.spsaccount.equals("080ALLGILDANUSA")){
                                    SpsCommerceDicksSportingGoodsUtilities.deliverCanceledASN(po,471);
                                    canceledOrder = true;
                                }
                            }else {
                                throw new Exception("Error saving order: " + order.last_error);
                            }
                        }

                        injectWorkOrder(order, po);
                        //inject work order for gildan Amazon
                        /*if (config.getSpsaccount().equals("080ALLGILDANUSA")&& !canceledOrder) {
                            order.injectWorkOrder("Gildan/Amzazon work order. OrderNum: " + order.orderNum, "Please poly bag all items according to specs. Then pack out using pack stations. Apply vendor compliance labels to middle long side of box. Assign to Rachel to get routing");
                        }
                        if(config.spsaccount.equals("FYAALLHVMNINC00")){
                            order.injectWorkOrder("HVMN/Amzazon work order. OrderNum: " + order.orderNum, "Please fulfill this order. \n" +
                                    "1.  Pull the requested items\n" +
                                    "2.  Overbox each case (if more than 6 cases place on a pallet and DO NOT overbox).\n" +
                                    "3.  After order is picked, pack out using pack stations. \n" +
                                    "4.  Apply vendor compliance labels to middle long side of box.\n" +
                                    "5.  Currently, the shipping method is set to LTL. Assign the work order back Gail to get routing");

                        }*/

                    }
                    if(canceledOrder){
                        doc.setDocStatus(7)
                    }else{
                        doc.setDocStatus(2)
                    }

                    //  HibernateSession.currentSession().evict(doc);

                    HibernateSession.currentSession().saveOrUpdate(doc)
                    HibernateSession.currentSession().flush()

                    //  HibernateSession.currentSession().getTransaction().commit()
                    HibUtils.commit(HibernateSession.currentSession())
                    try {
                        if (config.acknowledgmentRequired == 1 && !canceledOrder) {
                            OwdOrder oorder = OrderFactory.getOwdOrderFromOrderID(Integer.parseInt(order.orderID), Integer.parseInt(order.getClientID()), true)
                            println "This is the aknowledgmentXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
                            String ack ="";

                                ack = generateACK(config, po, Integer.parseInt(order.clientID), oorder)


                            println ack



                            ftp.putDataFile(SPSCommerceRemoteFTP.fileType.PR, ack.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirPath)
                            addTagToOrder(order.orderID, TagUtilities.kEDIPurchaseOrderAcknowledgement, ack);


                        }

                    } catch (Exception e) {
                        throw new LogableException("Unable to properly Acknowledge: " + e.getMessage(), order.orderNum, clientId + "", "EDI ACK", LogableException.errorTypes.ASN_IMPORT);
                    }
                } catch (LogableException lex) {
                    println "We have an error for processing orders that is logged to slack"

                    println lex.errorMessage

                    if(lex.getMessage().contains("Duplicate PO")){
                        doc.setDocStatus(6)
                    }else {
                        doc.setDocStatus(0)
                    }
                    //  HibernateSession.currentSession().evict(doc);

                    HibernateSession.currentSession().saveOrUpdate(doc)
                    HibernateSession.currentSession().flush()
                    //  HibernateSession.currentSession().getTransaction().commit()
                    HibUtils.commit(HibernateSession.currentSession())


                }

                catch (Exception ex) {
                    if(ex.getMessage().contains("Duplicate PO")){
                        doc.setDocStatus(6)
                    }else {
                        doc.setDocStatus(0)
                    }
                    //  HibernateSession.currentSession().evict(doc);

                    HibernateSession.currentSession().saveOrUpdate(doc)
                    HibernateSession.currentSession().flush()
                    //  HibernateSession.currentSession().getTransaction().commit()
                    HibUtils.commit(HibernateSession.currentSession())

                    String message = ex.getMessage()
                    if (message == null) {
                        message = "" + ex.getCause().getMessage()
                    }
                    if (message.contains("Insufficient inventory")) {

                        if(null!= notificationEmails && notificationEmails.length() >3){
                            message = message + "\r\nVendor : " + errorVendor +" \r\n CustomerOrderNumber: "+errorCustomerNo;
                            Vector v = new Vector();
                            v.addAll(notificationEmails.split(","));
                            Mailer.postMailMessage("Inventory Error for PO: "+errorPO+", "+errorVendor+", CO: "+errorCustomerNo,message,v,"edi@owd.com");
                            doc.setDocStatus(3)
                            HibernateSession.currentSession().saveOrUpdate(doc)
                            HibernateSession.currentSession().flush()
                            //  HibernateSession.currentSession().getTransaction().commit()
                            HibUtils.commit(HibernateSession.currentSession())
                        }

                        println message
                    } else if (message.contains("Duplicate:")) {
                        doc.setDocStatus(2)
                        //  HibernateSession.currentSession().evict(doc);

                        HibernateSession.currentSession().saveOrUpdate(doc)
                        HibernateSession.currentSession().flush()
                        //  HibernateSession.currentSession().getTransaction().commit()
                        HibUtils.commit(HibernateSession.currentSession())

                    } else {
                        ex.printStackTrace()

                        if(!ex.getMessage().contains("Duplicate PO")) {
                            Exception exl = new LogableException(ex, "Generic order import error from SPSCommerce:" + ex.getMessage(), "TS:" + Calendar.getInstance().getTimeInMillis(), "489", "EDI order import", LogableException.errorTypes.ORDER_IMPORT);
                        }
                    }
                }



        } catch (Exception ex) {
            ex.printStackTrace()
        }
    }
    def static String getOwdSkuFromForeinSku(EdiSpsConfigdata config, String skuValue){
        String sku = null;
        ResultSet rs;
       // println("select owd_sku from owd_inventory_sku_maps  where client_fkey=" + config.getClientFkey() + " and foreign_sku='" + skuValue.trim() + "'" + " and vendor_compliance_fkey='" + config.getVendorComplianceFkey() + "'")
        if(null == config.getVendorComplianceFkey()){
            rs = HibernateSession.getResultSet("select owd_sku from owd_inventory_sku_maps  where client_fkey=" + config.getClientFkey() + " and foreign_sku ='" + skuValue.trim() + "'" + " and type='" + config.getName() + "'")

        }else{
            rs = HibernateSession.getResultSet("select owd_sku from owd_inventory_sku_maps  where client_fkey=" + config.getClientFkey() + " and foreign_sku ='" + skuValue.trim() + "'" + " and vendor_compliance_fkey='" + config.getVendorComplianceFkey() + "'")

        }

        if (rs.next()) {
            sku = rs.getString(1)
        }
        rs.close()

        return sku;
    }
    def static String getOwdSkuFromUpc(EdiSpsConfigdata config, String upcValue) {
        if (upcValue == null || upcValue.length() < 8) {
            return null;
        }
        if( upcValue.length()==14&&upcValue.startsWith("00")){
            upcValue = upcValue.substring(2,upcValue.length());
        }
        String sku = null;
        println("Verify Price: " + config.verifyPrice)
        println()
        if (config.verifyPrice == 0) {
            println("We are doing normal upc lookup")

            ResultSet rs = HibernateSession.getResultSet("select inventory_num from owd_inventory where client_fkey=" + config.getClientFkey() + " and upc_code='" + upcValue.trim() + "'")

            if (rs.next()) {
                sku = rs.getString(1)
            } else {
                println("We did not find it")
            }
            rs.close()
        }
        if (null == sku && config.vendorComplianceFkey > 0) {
            println("in the verify price")
            println(config.spsaccount)
            println(config.acknowledgmentRequired);
            println(config.verifyPrice);
            println(config.vendorComplianceFkey);

            println("select owd_sku from owd_inventory_sku_maps  where client_fkey=" + config.getClientFkey() + " and upc='" + upcValue.trim() + "'" + " and vendor_compliance_fkey='" + config.getVendorComplianceFkey() + "'")
            ResultSet rs = HibernateSession.getResultSet("select owd_sku from owd_inventory_sku_maps  where client_fkey=" + config.getClientFkey() + " and upc='" + upcValue.trim() + "'" + " and vendor_compliance_fkey='" + config.getVendorComplianceFkey() + "'")
            if (rs.next()) {
                sku = rs.getString(1)
            }
            rs.close()
        }
        return sku
    }

    def static Boolean verifyPrice(EdiSpsConfigdata config, String owdSku, String price) throws Exception {
        boolean good = false;
        if (owdSku.length() > 0) {
            ResultSet rs = HibernateSession.getResultSet("select verification_price from owd_inventory_sku_maps where client_fkey=" + config.getClientFkey() + " and owd_sku='" + owdSku.trim() + "' and vendor_compliance_fkey = " + config.vendorComplianceFkey)
            if (rs.next()) {
                String vPrice = rs.getString(1)
                println(price)
                println(vPrice)
                if (Float.parseFloat(vPrice) == (Float.parseFloat(price))) good = true;

            } else {
                rs.close()
                throw new Exception("Unable to lookup price for verification")
            }
            rs.close();

        }


        return good;
    }

    def static String getUPCFromOwdSku(EdiSpsConfigdata config, String skuValue) {
        if (skuValue == null || skuValue.length() < 1) {
            return null;
        }
        String upc = null;
        if (config.verifyPrice == 0) {

            println "select upc_code from owd_inventory where client_fkey=" + config.getClientFkey() + " and inventory_num='" + skuValue.trim() + "'"
            ResultSet rs = HibernateSession.getResultSet("select upc_code from owd_inventory where client_fkey=" + config.getClientFkey() + " and inventory_num='" + skuValue.trim() + "'")
            if (rs.next()) {
                upc = rs.getString(1)
            }
            rs.close()
        }

        if ((null == upc || upc.length() < 5) && config.vendorComplianceFkey > 0) {

            ResultSet rs = HibernateSession.getResultSet("select upc from owd_inventory_sku_maps where client_fkey=" + config.getClientFkey() + " and owd_sku='" + skuValue.trim() + "'" + " and vendor_compliance_fkey='" + config.getVendorComplianceFkey() + "'")
            if (rs.next()) {
                upc = rs.getString(1)
            }
            rs.close()
        }
        return upc
    }

    def static Map<String, String> makeVendorToOwdSkuMap(EdiSpsConfigdata config) {
        Map<String, String> skuMap = new HashMap<String, String>();
        Query q ;
        if(null == config.getVendorComplianceFkey()){
            q = HibernateSession.currentSession().createSQLQuery("select owd_sku, foreign_sku from owd_inventory_sku_maps  where client_fkey=" + config.getClientFkey()  + " and type='" + config.getName() + "'")

        }else{
            q = HibernateSession.currentSession().createSQLQuery("select owd_sku, foreign_sku from owd_inventory_sku_maps  where client_fkey=" + config.getClientFkey() + " and vendor_compliance_fkey ='"  + config.getVendorComplianceFkey() + "'")

        }
        List results = q.list();
        for (Object row:results) {
            Object[] data = (Object[]) row;
            skuMap.put(data[1].toString(),data[0].toString());
        }

        return skuMap
    }

    def static Map<String, String> makeOwdToVendorSkuMap(EdiSpsConfigdata config) {
        Map<String, String> skuMap = new HashMap<String, String>();
        Query q ;
        if(null == config.getVendorComplianceFkey()){
            q = HibernateSession.currentSession().createSQLQuery("select owd_sku, foreign_sku from owd_inventory_sku_maps  where client_fkey=" + config.getClientFkey()  + " and type='" + config.getName() + "'")

        }else{
            q = HibernateSession.currentSession().createSQLQuery("select owd_sku, foreign_sku from owd_inventory_sku_maps  where client_fkey=" + config.getClientFkey() + " and foreign_sku ='"  + config.getVendorComplianceFkey() + "'")

        }
        List results = q.list();
        for (Object row:results) {
            Object[] data = (Object[]) row;
            skuMap.put(data[0].toString(),data[1].toString());
        }

        return skuMap
    }


    def  Order importPo(Node podoc, int clientID) throws Exception {


        def order = new Order("" + clientID) //ballpark classics, for now
        setFacilityCode(order);

        EdiSpsConfigdata configdata = getEdiConfigData(podoc)
        Map<String, String> fromVendorSkuMap = makeVendorToOwdSkuMap(configdata)

        if(checkForFutureShip){
            checkForFutureShipDate(podoc);
        }


        order.po_num = podoc.Header.OrderHeader.PurchaseOrderNumber.text()
        String refNum = "";
        refNum = podoc.Header.OrderHeader.CustomerOrderNumber.text()
        if(refNum.length()>0){
            order.order_refnum = order.po_num+"-"+ refNum

        }


        def shipto = podoc.Header.Address.find { it.AddressTypeCode.text() == 'ST' }
        println(shipto)
        println("This ship")
        println(shipto.AddressLocationNumber.text)
        println(podoc.Header.Address.AddressLocationNumber.text())

        if (shipto != null) {

            if (shipto.AddressLocationNumber.text().length() > 0 && !(shipto.Address1.text().length() > 0)&!useAlternateNameForAddress) {
                println("We are loading the address")

                addressUtils.loadShippingAddressFromReference(order, shipto.AddressLocationNumber.text(), configdata.name)
                println(order.getShippingAddress().address_one)
                println("That was the one")

            } else {


                order.getShippingContact().with {
                    name = "" + shipto.AddressName.text()
                    email = "" + shipto.Contact?.PrimaryEmail?.text()
                    phone = "" + shipto.Contact?.PrimaryPhone?.text()
                }
                order.getShippingAddress().with {
                    if(useAlternateNameForAddress){
                        address_one = shipto.AddressAlternateName.text()
                    }else{
                        address_one = shipto.Address1.text()
                    }

                    address_two = shipto.Address2?.text()
                    city = shipto.City.text()
                    state = shipto.State.text()
                    zip = shipto.PostalCode.text()
                    country = shipto.Country.text()
                }
            }
        } else {

            throw new Exception("PO contains no ship to information!")
        }
        def billto = podoc.Header.Address.find { it.AddressTypeCode.text() == 'BT' }

        if (billto != null) {
            order.getBillingContact().with {
                name = billto.AddressName.text()
                email = billto.Contact?.PrimaryEmail?.text()
                phone = billto.Contact?.PrimaryPhone?.text()
            }
            order.getBillingAddress().with {
                address_one = billto.Address1.text()
                address_two = billto.Address2?.text()
                city = billto.City.text()
                state = billto.State.text()
                zip = billto.PostalCode.text()
                country = billto.Country.text()
            }
        } else {
            order.setBillingContact(order.getShippingContact())
            order.setBillingAddress(order.getShippingAddress())
        }

        def lines = podoc.LineItems.LineItem.OrderLine.each {

            int orderedQty = Integer.parseInt(it.OrderQty.text());
            if(orderedQty>0) {
                boolean caseQty = false;



                if (!ignoreTheUOM()) {

                    if (null != it.OrderQtyUOM?.text() && !("EA".equals("" + it.OrderQtyUOM.text()))) {
                        if ("CA".equals("" + it.OrderQtyUOM.text())) {
                            caseQty = true;

                        } else if ("BG".equals("" + it.OrderQtyUOM.text()) || "PR".equals("" + it.OrderQtyUOM.text()) || "BX".equals("" + it.OrderQtyUOM.text())) {

                        } else {


                            throw new Exception("unrecognized UOM of " + it.OrderQtyUOM.text());


                        }
                    }
                }


                String internalSku;
                if(useGTIN){
                    internalSku = getOwdSkuFromUpc(configdata, "" + it.GTIN.text())
                }else{
                    if(!forceSkuMapUsage) {
                        internalSku = getOwdSkuFromUpc(configdata, "" + it.ConsumerPackageCode.text())
                    }
                }

                if (useEAN) {
                    internalSku = getOwdSkuFromUpc(configdata, "" + it.EAN.text())
                }


                if (buyerPartNumIsSku) {
                    internalSku = it.BuyerPartNumber.text()
                }
                if (vendorPartNumIsSku) {
                    internalSku = it.VendorPartNumber.text()
                }

                if (internalSku == null || "null".equals(internalSku) || internalSku.length() < 1) {
                    internalSku = fromVendorSkuMap.get("" + it.VendorPartNumber.text())
                    if (internalSku == null || "null".equals(internalSku) || internalSku.length() < 1) {

                        internalSku = fromVendorSkuMap.get("" + it.ProductID.PartNumber.text())
                        if (internalSku == null || "null".equals(internalSku) || internalSku.length() < 1) {
                            throw new Exception("unrecognized SKU/VendorPartNumber of " + it.VendorPartNumber.text());
                        }
                    }
                }
                println("Found sku: " + internalSku)
                if (!(ConnectionManager.InventoryExists(internalSku, configdata.getClientFkey() + ""))) {
                    throw new Exception("unrecognized SKU of " + internalSku);

                }

                String itemDescription = it.PartDescription1?.text()
                if (itemDescription == null) {

                    it.find { it.ProductOrItemDescription?.ItemDescriptionCode?.text() == '08' }.with
                            { desc ->
                                itemDescription = desc
                            }
                }
                if (itemDescription == null) {
                    itemDescription = ""
                }
                //   Do we need to Verify price??
                if (configdata.verifyPrice == 1) {
                    String price = it.PurchasePrice?.text();
                    //if price does not match add line to exemptions list
                    if (!verifyPrice(configdata, internalSku, price)) {

                        def lie = new lineItemExemptions()

                        lie.setVendor_sku(it.ConsumerPackageCode.text())
                        lie.setInventory_num(internalSku);
                        lie.setExemptionCode("price");
                        lie.setExemptionValue("no match");
                        lie.setQty(it.OrderQty.text() + "")
                        lie.setMerchant_line_number(it.LineSequenceNumber.text())

                        order.lineExemptions.add(lie);
                    } else {
                        //check for stock and only ship what we have
                        if (configdata.spsaccount.equals("080ALLGILDANUSA")) {
                            OwdInventory owdInv = com.owd.core.managers.InventoryManager.getOwdInventoryForClientAndSku(order.clientID, internalSku)


                            int stock = order.getStockLevelForInventoryId(owdInv.getInventoryId(), order.facilityCode)
                            if (stock == 0) {
                                println "WE have 0 in stock exempt the whole line"
                                def lie = new lineItemExemptions()

                                lie.setVendor_sku(it.ConsumerPackageCode.text())
                                lie.setInventory_num(internalSku);
                                lie.setExemptionCode("quantity");
                                lie.setExemptionValue("out of stock");
                                lie.setQty(it.OrderQty.text() + "")
                                lie.setMerchant_line_number(it.LineSequenceNumber.text())

                                order.lineExemptions.add(lie);

                            } else {
                                int orderQty = Integer.parseInt(it.OrderQty.text());
                                if (stock >= orderQty) {
                                    println "We can fullfill";
                                    order.addLineItem(internalSku, "" + it.OrderQty.text(), "0" + ((it.PurchasePrice?.text()) == null ? "" : it.PurchasePrice?.text()), "0.00", itemDescription, "", "")
                                    ((LineItem) (order.skuList.get(order.skuList.size() - 1))).client_ref_num = it.LineSequenceNumber.text()
                                } else {
                                    println "We can partial Ship";
                                    order.addLineItem(internalSku, "" + stock, "0" + ((it.PurchasePrice?.text()) == null ? "" : it.PurchasePrice?.text()), "0.00", itemDescription, "", "")

                                    ((LineItem) (order.skuList.get(order.skuList.size() - 1))).client_ref_num = it.LineSequenceNumber.text()

                                    def lie = new lineItemExemptions()

                                    lie.setVendor_sku(it.ConsumerPackageCode.text())
                                    lie.setInventory_num(internalSku);
                                    lie.setExemptionCode("quantity");
                                    lie.setExemptionValue("short ship");
                                    lie.setQty((orderQty - stock) + "")
                                    lie.setMerchant_line_number(it.LineSequenceNumber.text())

                                    order.lineExemptions.add(lie);


                                }


                            }


                        } else {
                            if (caseQty) {
                                println("We have case qty")
                                OwdInventory owdInv = com.owd.core.managers.InventoryManager.getOwdInventoryForClientAndSku(order.clientID, internalSku)
                                int qty = it.OrderQty.text()
                                if (owdInv.caseQty > 0) {
                                    order.addLineItem(internalSku, "" + qty * owdInv.caseQty, "0" + ((it.PurchasePrice?.text()) == null ? "" : it.PurchasePrice?.text()), "0.00", itemDescription, "", "")
                                    ((LineItem) (order.skuList.get(order.skuList.size() - 1))).client_ref_num = it.LineSequenceNumber.text()
                                } else {
                                    throw new Exception("No case Qty set for: " + internalSku)
                                }

                            } else {
                                order.addLineItem(internalSku, "" + it.OrderQty.text(), "0" + ((it.PurchasePrice?.text()) == null ? "" : it.PurchasePrice?.text()), "0.00", itemDescription, "", "")
                                ((LineItem) (order.skuList.get(order.skuList.size() - 1))).client_ref_num = it.LineSequenceNumber.text()

                            }


                        }

                    }


                } else {
                    if (configdata.spsaccount.equals("501ALLJUSTBRAND") && !isFutureShip ) {
                        OwdInventory owdInv = com.owd.core.managers.InventoryManager.getOwdInventoryForClientAndSku(order.clientID, internalSku)


                        int stock = order.getStockLevelForInventoryId(owdInv.getInventoryId(), order.facilityCode)
                        if (stock == 0) {
                            println "WE have 0 in stock exempt the whole line"
                            def lie = new lineItemExemptions()

                            lie.setVendor_sku(it.ConsumerPackageCode.text())
                            lie.setInventory_num(internalSku);
                            lie.setExemptionCode("quantity");
                            lie.setExemptionValue("out of stock");
                            lie.setQty(it.OrderQty.text() + "")
                            lie.setMerchant_line_number(it.LineSequenceNumber.text())

                            order.lineExemptions.add(lie);

                        } else {
                            int orderQty = Integer.parseInt(it.OrderQty.text());
                            if (stock >= orderQty) {
                                println "We can fullfill";
                                order.addLineItem(internalSku, "" + it.OrderQty.text(), "0" + ((it.PurchasePrice?.text()) == null ? "" : it.PurchasePrice?.text()), "0.00", itemDescription, "", "")
                                ((LineItem) (order.skuList.get(order.skuList.size() - 1))).client_ref_num = it.LineSequenceNumber.text()
                            } else {
                                println "We can partial Ship";
                                order.addLineItem(internalSku, "" + stock, "0" + ((it.PurchasePrice?.text()) == null ? "" : it.PurchasePrice?.text()), "0.00", itemDescription, "", "")
                                ((LineItem) (order.skuList.get(order.skuList.size() - 1))).client_ref_num = it.LineSequenceNumber.text()

                                def lie = new lineItemExemptions()

                                lie.setVendor_sku(it.ConsumerPackageCode.text())
                                lie.setInventory_num(internalSku);
                                lie.setExemptionCode("quantity");
                                lie.setExemptionValue("short ship");
                                lie.setQty((orderQty - stock) + "")
                                lie.setMerchant_line_number(it.LineSequenceNumber.text())

                                order.lineExemptions.add(lie);
                            }
                        }
                    } else {

                        order.addLineItem(internalSku, "" + it.OrderQty.text(), "0" + ((it.PurchasePrice?.text()) == null ? "" : it.PurchasePrice?.text()), "0.00", itemDescription, "", "")
                        ((LineItem) (order.skuList.get(order.skuList.size() - 1))).client_ref_num = it.LineSequenceNumber.text()
                    }
                }

/*
            def upc = it.ConsumerPackageCode.text().trim();

            if (upc.trim().length() < 10) {
                upc = LineItem.getBarcodeForSKU(it.VendorPartNumber.text(), clientID + "");

                while (upc.length() > 12) {
                    upc = upc.substring(1)
                }
                while (upc.length() < 13) {
                    upc = '0' + upc
                }
            }*/

                order.recalculateBalance()
            }
        }


        return order;
    }

    public static EdiSpsConfigdata getEdiConfigData(Node poDoc) {

        String partner = poDoc.Header.OrderHeader.TradingPartnerId.text()
        String vendor = poDoc.Header.OrderHeader.Vendor.text()

        println "SPSPartner:" + partner
        println "SPSVendor:" + vendor

        return getEdiConfigDataSPSAccount(partner)
    }

    public static EdiSpsConfigdata getEdiConfigDataSPSAccount(String partner){
        Query q = HibernateSession.currentSession().createQuery("from EdiSpsConfigdata where Spsaccount=? ")
        q.setParameter(0, partner)

        List<EdiSpsConfigdata> configs = (List<EdiSpsConfigdata>) q.list()
        if (configs.size() != 1) {
            throw new Exception("SPS EDI import error - wrong number of configs found for " + partner + "/" )
        }

        EdiSpsConfigdata configdata = configs.get(0)
        return configdata
    }

    def static String generateInvoice(int orderId, int clientId) {

        def writer = new StringWriter()
        def builder = new groovy.xml.MarkupBuilder(writer)

        OwdOrder order = OrderFactory.getOwdOrderFromOrderID(orderId, clientId, true)
        assert (order != null)
        println order.poNum
        def ediMap = Eval.me(OrderUtilities.getPrintTemplateRefData(orderId))
        def today = new Date();

        int totalLines = 0;
        double totalValue = 0.00;


        def invoice = builder.Invoice {

            Header {
                InvoiceHeader {
                    TradingPartnerId(ediMap?.get('TradingPartnerId'))
                    InvoiceNumber(order.orderNum)
                    InvoiceDate(String.format('%1$tY-%<tm-%<td', today))
                    PurchaseOrderDate(String.format('%1$tY-%<tm-%<td', order.createdDate))
                    PurchaseOrderNumber(order.poNum)
                    Vendor(ediMap?.get('Vendor'))
                    FOBPayCode('PP')
                    ShipDate(String.format('%1$tY-%<tm-%<td', order.createdDate))
                }
                PaymentTerms {
                    TermsNetDueDate(String.format('%1$tY-%<tm-%<td', today + 45))
                    TermsDescription('Net45 Days')
                }
                Address {
                    AddressTypeCode('ST')
                    AddressName(order.shipinfo.shipFirstName + ' ' + order.shipinfo.shipLastName)
                    Address1(order.shipinfo.shipAddressOne)
                    Address2(order.shipinfo.shipAddressTwo)
                    City(order.shipinfo.shipCity)
                    State(order.shipinfo.shipState)
                    PostalCode(order.shipinfo.shipZip)
                    Country('US')
                }
                /* Reference {
                    ReferenceQual('IA')
                    ReferenceID('24696')
                }
                ChargesAllowances {
                    AllowChrgIndicator('C')
                    AllowChrgCode('G830')
                    AllowChrgAmt('15.00')
                    AllowChrgHandlingDescription('DROP SHIP FEE')
                }
                Miscellaneous {
                    Description1('INTERCHANGE')
                    Description2('021292000')
                }
                Miscellaneous {
                    Description1('FUNCTIONALGROUP')
                    Description2('21292100')
                }
                Miscellaneous {
                    Description1('TRANSACTION')
                    Description2('21292154')
                }*/
            }
            LineItems {

                Set<OwdLineItem> lines = order.getLineitems()
                lines.each { line ->
                    if (!("".equals(line.custRefnum)) && line.getQuantityActual() > 0) {
                        double unitValue = 105.00;
                        if (line.getInventoryNum().equalsIgnoreCase("BPC-GU-YANKEE")
                                || line.getInventoryNum().equalsIgnoreCase("BPC-GU-REDSOX")) {
                            unitValue = 105.00;
                        }
                        totalLines++;
                        totalValue += (line.quantityActual * unitValue);
                        LineItem {
                            InvoiceLine {
                                BuyerPartNumber(line.longDesc.tokenize(":").get(1))
                                //   VendorPartNumber('00812030010047')
                                ConsumerPackageCode(line.longDesc.tokenize(":").get(0))
                                // PartDescription1(line.description)
                                UnitPrice(('' + unitValue))
                                ShipQty(line.quantityActual)
                                ShipQtyUOM('EA')
                            }
                        }
                    }
                }

            }
            Summary {
                Totals {
                    TotalAmount(String.format('%.2f', totalValue))
                    TotalNetSalesAmount(String.format('%.2f', totalValue))
                    TotalLineItemNumber(totalLines)
                }
            }
        }



        return writer.toString()


    }

    def static String generateStockLevels(int clientId) {
        def writer = new StringWriter()
        def builder = new groovy.xml.MarkupBuilder(writer)

        int totalItems = 0;
        def stocklevels = builder.InventoryInquiriesAndAdvice
                {
                    Header {
                        HeaderReport {
                            TradingPartnerId('5S2ALLBALLPARKC')
                            Vendor('26634')
                            DocumentId('1106')
                            TsetHandlingCode('00')
                            ReportTypeCode('PI')
                            Date1(String.format('%1$tY-%<tm-%<td', new Date()))

                        }
                        Address {
                            AddressTypeCode('VN')
                            AddressName('Ballpark Classics')
                        }

                        /* Miscellaneous {
                            Qualifier1('INTERCHANGE')
                            Description1('000006963')
                        }
                        Miscellaneous {
                            Qualifier1('FUNCTIONALGROUP')
                            Description1('1135')
                        }
                        Miscellaneous {
                            Qualifier1('TRANSACTION')
                            Description1('0001')
                        }*/
                    }
                    LineItems {

                        def ArrayList fields = ['qty_on_hand', 'upc_code', 'inventory_num', 'is_active']
                        Inventory.getItemsForClientIDx(clientId + '', fields, true).each { item ->
                            def upc = item.get('upc_code')
                            println upc + "," + item.get('qty_on_hand') + "," + item.get('is_active')
                            if (item.get('upc_code')?.length() >= 12) {
                                totalItems++
                                LineItem {
                                    InventoryLine {
                                        //fix for bad left-side padding of upc with zeroes
                                        while (upc.length() > 12) {
                                            upc = upc.substring(1)
                                        }
                                        while (upc.length() < 13) {
                                            upc = '0' + upc
                                        }
                                        VendorPartNumber(item.get('inventory_num'))
                                        ConsumerPackageCode(upc)
                                    }
                                    /* Pricing {
                                        PriceTypeIDCode('DSP')
                                        UnitPrice('180')
                                    }*/
                                    InventoryLineQuantities {
                                        InventoryLineQuantity {
                                            QuantityLine {
                                                QuantityQualifier('33')
                                                Quantity1((item.get('is_active') == "1" ? item.get('qty_on_hand') : 0))
                                                QtyUOM('EA')
                                            }

                                        }

                                    }
                                }
                            }
                        }
                    }

                    Summary {
                        TotalLineItemNumber(totalItems)
                    }
                }
        return writer.toString()
    }

    def  String generateASN(int orderId, int clientId) throws Exception{
        def writer = new StringWriter()
        def builder = new groovy.xml.MarkupBuilder(writer)

        OwdOrder order = OrderFactory.getOwdOrderFromOrderID(orderId, clientId, true)


        assert (order != null)
        assert ('Shipped'.equalsIgnoreCase(order.getOrderStatus()))


        OwdFacility shipFrom = FacilitiesManager.getFacilityForCode(order.getFacilityCode())
        Map<String, String> tagMap = TagUtilities.getTagMap("ORDER", order.getOrderId())



        println "asn tagmap: " + tagMap
        String poNode = tagMap.get(TagUtilities.kEDIPurchaseOrder.toUpperCase())
        XmlParser parser = new XmlParser()
        parser.setTrimWhitespace(true)


        def poData = parser.parseText(poNode)


        String partner = poData.Header.OrderHeader.TradingPartnerId.text()
        String vendor = poData.Header.OrderHeader.Vendor.text()


        println "SPSPartner:" + partner
        println "SPSVendor:" + vendor


        EdiSpsConfigdata configdata = getEdiConfigData(poData)
        Map<String, String> fromVendorSkuMap = makeOwdToVendorSkuMap(configdata)


        println order.poNum
        def today = new Date();

        int totalLines = 0;
        int totalQuantity = 0;
        String purposeCode = getASNPurposeCode(poData.Header.OrderHeader.PurchaseOrderTypeCode.text().trim());


        def asn = builder.Shipments(xmlns: 'http://www.spscommerce.com/RSX')
                {

                    Shipment {


                        Header {
                            ShipmentHeader {
                                TradingPartnerId(poData.Header.OrderHeader.TradingPartnerId.text().trim()) //from order?
                                ShipmentIdentification(order.orderNum)
                                ShipDate(String.format('%1$tY-%<tm-%<td', order.shippedDate))
                                 TsetPurposeCode(purposeCode) //OK
                                ShipNoticeDate(String.format('%1$tY-%<tm-%<td', today))
                                ShipNoticeTime(String.format('%1$tH:%<tM:%<tS', today))
                                ASNStructureCode('0001')
                                AppointmentNumber(poData.Header.OrderHeader.DepartmentDescription.text().trim())
                                String tracking = com.owd.core.business.order.Package.getPackagesForOrderId(orderId).get(0).tracking_no;
                                if(tracking.contains(":")){
                                    tracking = tracking.substring(tracking.indexOf(":")+1);
                                    tracking = tracking.replaceAll(" ","");
                                    tracking = tracking.replaceAll("-","");
                                }

                                    if((configdata.getVendorComplianceFkey()==2 && order.shipinfo.carrService.equals("LTL"))
                                            || configdata.getVendorComplianceFkey()==4 ){
                                        BillOfLadingNumber(tracking);
                                        if(configdata.getVendorComplianceFkey()==4){
                                            CarrierProNumber(tracking)
                                        }
                                    } else{
                                        if(includeBillOfLadingInHeader){
                                            BillOfLadingNumber(tracking)
                                        }else{
                                            BillOfLadingNumber('')
                                            CarrierProNumber(tracking)
                                        }

                                    }



                                CurrentScheduledDeliveryDate(String.format('%1$tY-%<tm-%<td', today + 3))
                                if(includeVendorInShipmentHeader()){

                                    Vendor(poData?.Header?.OrderHeader?.Vendor?.text()?.trim())
                                }





                            }

                            if (configdata.vendorComplianceFkey == 1) {


                                def refs = poData.Header.Reference?.each { ref ->

                                    Reference {
                                        ReferenceQual(ref.ReferenceQual?.text().trim())
                                        ReferenceID(ref.ReferenceID?.text().trim())
                                        //  Description(ref.Description?.text())
                                    }
                                }

                                if (clientId == 471 || clientId == 576 || clientId == 710) {

                                    Reference {
                                        ReferenceQual('BX')

                                        ReferenceID(getAmazonARNFromTags(order.getTags()))


                                    }
                                }
                            }
                            else {
                                def snr = getVendorComplianceShipmentNumberReference(order.getTags());
                                if (snr != null) {
                                    if (snr.referenceID.length() > 0) {
                                        Reference {
                                            ReferenceQual(snr.getReferenceQual());
                                            ReferenceID(snr.getReferenceID());
                                        }
                                    }
                                }
                            }

                            Address {
                                AddressTypeCode('ST')
                                AddressName(order.shipinfo.shipFirstName + ' ' + order.shipinfo.shipLastName)
                                def shipto = poData.Header.Address.find { it.AddressTypeCode.text().equals('ST') }

                                if(useShipToAddressAlternateName == true && shipto != null) {
                                    String altName = shipto.AddressAlternateName?.text();
                                    if(altName != null && altName.isEmpty() == false) {
                                        AddressAlternateName(altName)
                                    }
                                    else {
                                        AddressAlternateName(order.shipinfo.shipCompanyName)
                                    }
                                }
                                else {
                                    AddressAlternateName(order.shipinfo.shipCompanyName)
                                }
                                Address1(order.shipinfo.shipAddressOne)
                                Address2(order.shipinfo.shipAddressTwo)
                                City(order.shipinfo.shipCity)
                                State(order.shipinfo.shipState)
                                PostalCode(order.shipinfo.shipZip)
                                Country(order.shipinfo.shipCountry)


                                if (shipto != null) {

                                    LocationCodeQualifier("" + shipto.LocationCodeQualifier?.text())
                                    AddressLocationNumber("" + shipto.AddressLocationNumber?.text())

                                }

                            }
                            Address {
                                AddressTypeCode('SF')
                                AddressName("Fulfillment Operations")
                                AddressAlternateName("One World Direct")

                                Address1(shipFrom.address)
                                Address2("")
                                City(shipFrom.city)
                                State(shipFrom.state)
                                PostalCode(shipFrom.zip)
                                Country("US")
                                if(includeOWDDunsLocationNumberASN){
                                    AddressLocationNumber("929243491");
                                }
                                if (configdata.getVendorComplianceFkey() == 1) {
                                    //chilitech - Amazon DC shipment

                                    LocationCodeQualifier("ZZ")
                                    if (poData.Header.OrderHeader.Vendor.text().length() > 0) {
                                        AddressLocationNumber("" + poData.Header.OrderHeader.Vendor.text().trim())
                                    } else {
                                        AddressLocationNumber(order.getFacilityCode())
                                    }
                                }
                                if(configdata.getVendorComplianceFkey()==2){
                                    LocationCodeQualifier("92")
                                    AddressLocationNumber(configdata.getVendorid())
                                }
                                AddressInfo info = getShipFromAddressInfo();

                                if(info.getLocationCodeQualifier().length()>0){
                                    LocationCodeQualifier(info.getLocationCodeQualifier())
                                }
                                if(info.getAddressLocationNumber().length()>0){


                                    AddressLocationNumber(info.getAddressLocationNumber())
                                }
                            }


                            String scacCode = getScacCode(poData, order.shipinfo.carrService);

                            if (scacCode.length() < 2) {
                                scacCode = outboundShipMap.get(order.shipinfo.carrService)
                                if (scacCode == null) {
                                    scacCode = ""
                                }
                            }
                            CarrierInformation {
                                StatusCode(getStatusCode(order.shipinfo.carrService))
                                if ( configdata.getVendorComplianceFkey() == 1) {
                                    //BumbleRide Amazon DC
                                    CarrierTransMethodCode(order.shipinfo.carrService.equals('LTL') ? 'LT' : 'ZZ')
                                } else {
                                    CarrierTransMethodCode(getCarrierTransMethodCode(order))
                                }

                                CarrierAlphaCode(scacCode)

                                if (configdata.getId() == 37 || configdata.getId() == 15) {
                                    String carrierRouting = getCarrierRouting(poData);

                                    CarrierRouting(carrierRouting)
                                } else {
                                    CarrierRouting(scacCode)
                                }

                                String routingSequence = getRoutingSequenceCode();
                                if(routingSequence.length()>0){
                                    RoutingSequenceCode(routingSequence);
                                }
                            }

                            QuantityAndWeight {
                                if (configdata.getVendorComplianceFkey() == 1) {
                                    //chilitech - Amazon DC shipment
                                    PackingMedium('CTN')
                                } else {
                                    PackingMedium(getPackingMedium())
                                }
                                LadingQuantity(order.packagesShipped)
                                WeightQualifier('G')
                                Weight(order.shippedWeight)
                                WeightUOM('LB')

                            }

                            FOBRelatedInstruction {


                                    FOBPayCode(getFOBPayCode())

                            }

                            SPSReference shipId = getShipmentId(order.orderNum);
                            if(shipId.referenceID.length()>0){
                                Reference{
                                    ReferenceQual(shipId.getReferenceQual());
                                    ReferenceID(shipId.getReferenceID());
                                }
                            }
                        }
                        OrderLevel {
                            OrderHeader {
                                InternalOrderNumber(order.orderRefnum)
                                InvoiceNumber(poData.Header.OrderHeader.InvoiceNumber ? poData.Header.OrderHeader.InvoiceNumber?.text().trim() : order.orderNum)
                                PurchaseOrderNumber(poData.Header.OrderHeader.PurchaseOrderNumber?.text().trim())
                                ReleaseNumber(poData.Header.OrderHeader.ReleaseNumber?.text().trim())
                                PurchaseOrderDate(poData.Header.OrderHeader.PurchaseOrderDate?.text().trim())
                                Vendor(poData.Header.OrderHeader.Vendor?.text().trim())
                                Division(poData.Header.OrderHeader.Division?.text().trim())
                                CustomerOrderNumber(poData.Header.OrderHeader.CustomerOrderNumber?.text().trim())
                                if(includeOrderHeaderDepartment){
                                    Department(poData.Header.OrderHeader.Department.text().trim())
                                }
                                if (includeDepositorOrderNumber) {
                                    DepositorOrderNumber(poData.Header.OrderHeader.DepositorOrderNumber?.text())
                                }
                            }
                            QuantityAndWeight {
                                if (configdata.getVendorComplianceFkey() == 1) {
                                    //toysrus dropship
                                    PackingMedium('CTN')
                                } else {
                                    PackingMedium(getPackingMedium())
                                }
                                LadingQuantity(order.packagesShipped)
                                WeightQualifier('G')
                                Weight(order.shippedWeight)
                                WeightUOM('LB')
                            }


                            def refs = poData.Header.Reference?.each { ref ->
                                Reference {
                                    ReferenceQual(ref.ReferenceQual?.text().trim())
                                    ReferenceID(ref.ReferenceID?.text().trim())
                                    //  Description(ref.Description?.text())
                                }
                            }


                            def adds = poData.Header.Address?.each { add ->
                                Address {
                                    AddressTypeCode(add.AddressTypeCode?.text().trim())
                                    AddressName(add.AddressName?.text().trim())
                                    AddressAlternateName(add.AddressAlternateName?.text().trim())
                                    Address1(add.Address1?.text().trim())
                                    Address2(add.Address2?.text().trim())
                                    City(add.City?.text().trim())
                                    State(add.State?.text().trim())
                                    PostalCode(add.PostalCode?.text().trim())
                                    Country(add.Country?.text().trim())
                                    AddressLocationNumber(add.AddressLocationNumber?.text().trim())

                                }
                            }
                            AddressInfo buyerAddress = getBuyingAddressInfo(poData);
                            if(buyerAddress.getAddressLocationNumber().length()>0){
                                Address{
                                    AddressTypeCode(buyerAddress.getAddressTypeCode());
                                    LocationCodeQualifier(buyerAddress.getLocationCodeQualifier());
                                    AddressLocationNumber(buyerAddress.getAddressLocationNumber());
                                }
                            }

                            def cons = poData.Header.Contact?.each { con ->
                                Contact {
                                    ContactName(con.ContactName?.text().trim())
                                    PrimaryPhone(con.PrimaryPhone?.text().trim())
                                    PrimaryFax(con.PrimaryFax?.text().trim())
                                    PrimaryEmail(con.PrimaryEmail?.text().trim())
                                }
                            }


                            com.owd.core.business.order.Package.getPackagesForOrderId(orderId).each { pack ->

                                PackLevel {
                                    Pack {
                                        PackLevelType('P')

                                        String SSCC = com.owd.core.business.order.Package.getSSCCCodeForPackage(pack.order_track_id);
                                        if (SSCC.length()>0) {
                                            ShippingSerialID("00" + SSCC);

                                        }
                                        String tracking = pack.tracking_no;
                                        if(tracking.contains(":")){
                                            tracking = tracking.substring(tracking.indexOf(":")+1);
                                        }
                                        CarrierPackageID(tracking)
                                    }
                                    PhysicalDetails {
                                        if (configdata.getVendorComplianceFkey() == 1) {

                                            PackingMedium('CTN')
                                        } else {
                                            PackingMedium(getPackingMedium())
                                        }
                                        WeightQualifier('G')
                                        PackWeight(pack.weight)
                                        PackWeightUOM('LB')


                                            PackUOM('EA')
                                            PackSize('1')

                                    }





                                        println "Getting line items for order_track_id " + pack.order_track_id

                                        List<Map> items = com.owd.core.business.order.Package.getEDILineItemsForPackage(pack.order_track_id,true)


                                        println "Got " + items.size() + " lines for package"
                                        if (items.size()==0){
                                            throw new LogableException("Unable to load sku's from tracking id: "+ pack.order_track_id,order.orderNum,clientId+"","Shipment confirmation",LogableException.errorTypes.SHIPMENT_NOTIFICATION);
                                        }
                                        items.each { itemMap ->
                                            ItemLevel {
                                                println itemMap

                                                def poLineItem
                                               // String vendorSku = fromVendorSkuMap.get(itemMap.get('SKU'))
                                                String UPC
                                                if(useMappingForASN){
                                                    System.out.println(itemMap.get("SKU"));
                                                    System.out.println(fromVendorSkuMap.containsKey(itemMap.get("SKU")));
                                                    String sku = fromVendorSkuMap.get(itemMap.get("SKU"));
                                                    poLineItem = poData.LineItems.LineItem.OrderLine.find {

                                                        it.VendorPartNumber.text().trim().equals(sku)


                                                    }
                                                }else if(buyerPartNumIsSku){
                                                   //buyerpart owd sku lookup
                                                    UPC = itemMap.get("UPC");
                                                   println UPC

                                                    poLineItem = poData.LineItems.LineItem.OrderLine.find {

                                                       it.BuyerPartNumber.text().trim().equals(itemMap.get("SKU"))


                                                   }
                                               }else if(vendorPartNumIsSku){
                                                   //buyerpart owd sku lookup
                                                   UPC = itemMap.get("UPC");
                                                   println UPC

                                                   poLineItem = poData.LineItems.LineItem.OrderLine.find {

                                                       if(itemMap.get("PARENT").toString().equals("0")) {
                                                           it.VendorPartNumber.text().trim().equals(itemMap.get("SKU"))
                                                       }else{
                                                           //This item is mapped. We need to get the parent sku to get correct line from SPS
                                                           OwdLineItem owdItem = HibernateSession.currentSession().load(OwdLineItem.class, Integer.parseInt(itemMap.get("PARENT").toString()))
                                                           it.VendorPartNumber.text().trim().equals(owdItem.getInventoryNum())
                                                       }


                                                   }
                                               }else{
                                                   //do upc lookup
                                                   UPC = itemMap.get("UPC");
                                                   println UPC

                                                    poLineItem = poData.LineItems.LineItem.OrderLine.find {
                                                        if(useGTIN) {
                                                            it.GTIN.text().trim().equals("00" + UPC)

                                                        } else if (useEAN) {
                                                            it.EAN.text().trim().equals(UPC)
                                                        }else{
                                                            it.ConsumerPackageCode.text().trim().equals(UPC)
                                                        }
                                                   }
                                               }




                                                    if (poLineItem) {

                                                        totalLines++;
                                                        ShipmentLine {
                                                            LineSequenceNumber(poLineItem.LineSequenceNumber.text().trim())
                                                            if (useEAN) {
                                                                EAN(poLineItem.EAN.text().trim())
                                                            } else {
                                                                BuyerPartNumber(poLineItem.BuyerPartNumber ? poLineItem.BuyerPartNumber.text().trim() : '')
                                                                VendorPartNumber(poLineItem.VendorPartNumber? poLineItem.VendorPartNumber.text().trim():'')
                                                                ConsumerPackageCode(poLineItem.ConsumerPackageCode ? poLineItem.ConsumerPackageCode.text().trim() : itemMap.get('BARCODE'))
                                                            }

                                                            //  PartDescription1('Boston Red Sox Fenway Park MLB Baseball Game	3')
                                                            ShipQty(itemMap.get('QTY') + '.0')
                                                            totalQuantity += Integer.parseInt('' + itemMap.get('QTY'))
                                                            ShipQtyUOM(getShipQtyUOM(poLineItem))
                                                            if(getItemStatusCode(poLineItem).length()>0){
                                                                ItemStatusCode(getItemStatusCode(poLineItem))
                                                            }
                                                            if(getOrderQty(poLineItem).length()>0){
                                                                OrderQty(getOrderQty(poLineItem))
                                                            }
                                                            if(includeOrderQtyUOMShipmentLine){
                                                                OrderQtyUOM(getOrderQtyUOM(poLineItem))
                                                            }

                                                            if (includeApplicationId) {
                                                                ApplicationId(poLineItem.ApplicationId ? poLineItem.ApplicationId.text().trim() :  '')
                                                            }

                                                            if(includePurchasePrice) {
                                                                PurchasePrice(poLineItem.PurchasePrice ? poLineItem.PurchasePrice.text().trim() :  '')
                                                            }
                                                        }
                                                        if(includeInnerpackShipmentLine()){
                                                            PhysicalDetails{
                                                                PackQualifier("IN")
                                                                PackValue(itemMap.get('QTY'))
                                                            }
                                                        }

                                                        if(includeProductOrItemDescriptionFromFile()){
                                                            def LineItem = poData.LineItems.LineItem.find {
                                                                if(useGTIN){
                                                                    it.OrderLine.GTIN.text().trim().equals("00"+UPC)

                                                                }else{
                                                                    if(useUPCLookupForProductOrItemDescription){
                                                                        it.OrderLine.ConsumerProductCode.text().trim().equals(UPC)
                                                                    }else{
                                                                        it.OrderLine.BuyerPartNumber.text().trim().equals(poLineItem.BuyerPartNumber.text())
                                                                    }

                                                                }




                                                            }
                                                            ProductOrItemDescription{
                                                                ItemDescriptionType(LineItem.ProductOrItemDescription.ItemDescriptionType.text())
                                                                ProductDescription(LineItem.ProductOrItemDescription.ProductDescription.text())
                                                            }

                                                        }

                                                        if(includeOuterpackShipmentLine()){
                                                            def LineItem = poData.LineItems.LineItem.find {

                                                                it.OrderLine.ConsumerPackageCode.text().trim().equals(UPC)


                                                            }
                                                            PhysicalDetails{
                                                                PackQualifier(LineItem.PhysicalDetails.PackQualifier.text())
                                                                PackValue(LineItem.PhysicalDetails.PackValue.text())
                                                            }
                                                        }
                                                        if(includeAllPhysicalDetails){
                                                            def LineItem = poData.LineItems.LineItem.find {

                                                                it.OrderLine.ConsumerPackageCode.text().trim().equals(UPC)
                                                            }

                                                            LineItem.PhysicalDetails.each{ detail ->
                                                                PhysicalDetails{
                                                                    PackQualifier(detail.PackQualifier.text())
                                                                    PackValue(detail.PackValue.text())
                                                                }
                                                            }
                                                        }
                                                    }


                                            }

                                        }





                                }


                            }


                        }
                        Summary {
                           if(totalLines == 0 ) throw new Exception("Lines Must be Greater than 0 to send")
                           if( totalQuantity==0) throw new Exception("Quantity must be Greater than 0 to send")
                            TotalLineItems(totalLines)
                            TotalQuantity(totalQuantity)
                        }

                    }

                }

        // throw new Exception("fix line number lookup from PO!!!")
        return writer.toString()

    }

    public static void addTagToOrder(String id, String name, String value) {
        try {
            String sql = "insert into owd_tags (external_id, type, name, [value]) values(:externalId, :type, :name, :thevalue)"
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("externalId", id);
            q.setParameter("type", "ORDER");
            q.setParameter("name", name);
            q.setParameter("thevalue", value);
            q.executeUpdate();
            HibUtils.commit(HibernateSession.currentSession());


        } catch (Exception e) {

            e.printStackTrace();

        }


    }

    def  String generateACK(EdiSpsConfigdata configdata, Node poData, int clientId, OwdOrder order) {
        def writer = new StringWriter()
        def builder = new groovy.xml.MarkupBuilder(writer)





        XmlParser parser = new XmlParser()
        parser.setTrimWhitespace(true)






        String partner = poData.Header.OrderHeader.TradingPartnerId.text()
        // String vendor = poData.Header.OrderHeader.Vendor.text()

        println "SPSPartner:" + partner
        // println "SPSVendor:"+vendor


        Map<String, String> fromVendorSkuMap = makeOwdToVendorSkuMap(configdata)
        Map<String, lineItemExemptions> exemptLines = loadExemptionFromOrderId(order.getOrderId()+"")

        //println order.poNum
        def today = new Date();

        int totalLines = 0;
        int totalQuantity = 0;

        def asn = builder.OrderAcks(xmlns: 'http://www.spscommerce.com/RSX')
                {

                    OrderAck {


                        Header {
                            OrderHeader {
                                TradingPartnerId(poData.Header.OrderHeader.TradingPartnerId.text().trim()) //from order?
                                PurchaseOrderNumber(poData.Header.OrderHeader.PurchaseOrderNumber.text())
                                Vendor(poData.Header.OrderHeader.Vendor.text())
                                Division(poData.Header.OrderHeader.Division.text())
                                TsetPurposeCode('00') //OK
                                PurchaseOrderDate(String.format('%1$tY-%<tm-%<td', today))


                                AcknowledgementType(getAcknowledgementType(exemptLines.size()))
                                if(getAcknowledgementType(exemptLines.size()).equals("AK")||getAcknowledgementType(exemptLines.size()).equals("AE")){
                                   AcknowledgementDate(String.format('%1$tY-%<tm-%<td', order.createdDate))
                                }
                               CustomerOrderNumber(poData.Header.OrderHeader.CustomerOrderNumber.text())


                            }


                        }

                        LineItems {
                            poData.LineItems.LineItem.each { line ->
                                println(line)
                                // println(it.get("LineSequenceNumber").text())
                                LineItem {
                                    OrderLine {

                                        LineSequenceNumber(line.OrderLine.LineSequenceNumber.text())
                                        BuyerPartNumber(line.OrderLine.BuyerPartNumber.text())
                                        ConsumerPackageCode(line.OrderLine.ConsumerPackageCode.text())
                                        if(includeVendorPartNumInAck){
                                            VendorPartNumber(line.OrderLine.VendorPartNumber.text())
                                        }
                                        if (useEAN) {
                                            EAN(line.OrderLine.EAN.text())
                                        }
                                        OrderQty(line.OrderLine.OrderQty.text())
                                        OrderQtyUOM(line.OrderLine.OrderQtyUOM.text())
                                        PurchasePrice(line.OrderLine.PurchasePrice.text())
                                        PurchasePriceBasis("NT")


                                    }

                                    LineItemAcknowledgement {
                                        //check for exempt lines
                                        if(exemptLines.containsKey(line.OrderLine.ConsumerPackageCode.text())){
                                            def lie = exemptLines.get(line.OrderLine.ConsumerPackageCode.text())
                                            //determine type of exemption
                                            if(lie.exemptionCode.equals("quantity")&&lie.exemptionValue.equals("short ship")){
                                                ItemStatusCode("IQ")
                                                ItemScheduleQty(Integer.parseInt(line.OrderLine.OrderQty.text())-Integer.parseInt(lie.qty))
                                                ItemScheduleQualifier("068")
                                                ItemScheduleUOM("EA")
                                                ItemScheduleDate(String.format('%1$tY-%<tm-%<td', order.shipinfo.getScheduledShipDate()))

                                            }else{
                                                ItemStatusCode("IR")
                                                ItemScheduleQty(line.OrderLine.OrderQty.text())

                                                ItemScheduleUOM("EA")

                                            }


                                        }else{


                                            ItemStatusCode("IA")
                                            ItemScheduleQty(line.OrderLine.OrderQty.text())
                                            ItemScheduleQualifier("068")
                                            ItemScheduleUOM("EA")
                                            ItemScheduleDate(String.format('%1$tY-%<tm-%<td', order.shipinfo.getScheduledShipDate()))
                                        }

                                    }
                                    Date {
                                        DateTimeQualifier1("067")
                                        def earlyDate = poData.Header.Date.find {
                                            it.DateTimeQualifier1.text() == '064'
                                        }
                                        if(null == earlyDate){
                                            earlyDate = poData.Header.Date.find {
                                                it.DateTimeQualifier1.text() == '010'
                                            }
                                        }
                                        if(null == earlyDate){
                                            earlyDate = poData.Header.Date.find {
                                                it.DateTimeQualifier1.text() == '017'
                                            }
                                        }
                                        Date1(earlyDate.Date1.text())
                                    }
                                    ProductOrItemDescription{
                                        ProductDescription(line.ProductOrItemDescription.ProductDescription.text())
                                        ItemDescriptionType("F")
                                        ProductCharacteristicCode("08")

                                    }
                                }


                            }
                        }
                        Summary {
                            TotalLineItemNumber(poData.Summary.TotalLineItemNumber.text())
                            int total = 0;
                            for (OwdLineItem item : order.lineitems) {
                                total += item.getQuantityActual();
                            }
                            TotalQuantity(total)


                        }

                    }

                }

        // throw new Exception("fix line number lookup from PO!!!")
        return writer.toString()

    }

    def static Map<String,lineItemExemptions> loadExemptionFromOrderId(String orderId){
        Map<String,lineItemExemptions> lines = new TreeMap<>()
        String sql = "select * from owd_line_item_exemptions where order_fkey = :orderId"
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        q.setParameter("orderId",orderId);
        List l = q.list();
        if(l.size()>0) {

            for (Object row : l) {
                Map data = (Map) row;
                def lie = new lineItemExemptions()
                lie.setOrder_fkey(data.get("order_fkey").toString())
                lie.setInventory_num(data.get("inventory_num").toString())
                lie.setVendor_sku(data.get("vendor_sku").toString());
                lie.setExemptionCode(data.get("exemptionCode").toString())
                lie.setExemptionValue(data.get("exemptionValue").toString())
                lie.setQty(data.get("qty").toString())
                lie.setMerchant_line_number(data.get("merchant_line_number").toString())
                lines.put(lie.getVendor_sku(), lie);


            }

        }
        return lines;
    }


    def   String getASNPurposeCode(String OrderType ){
        return "00";
    }

    def String getShipQtyUOM(Object poLineItem){
        return poLineItem.OrderQtyUOM?.text().trim();
    }
    def String getOrderQtyUOM(Object poLineItem){
        return poLineItem.OrderQtyUOM?.text().trim();
    }
    def String getItemStatusCode(Object poLineItem){
        return "";
    }
    def String getOrderQty(Object poLineItem){
        return "";
    }
    def String getCarrierTransMethodCode(OwdOrder order){
        return order.shipinfo.carrService.equals('LTL') ? 'L' : 'M';
    }
    def String getRoutingSequenceCode(){
        return "";
    }
    def String getStatusCode(String shipMethod){
        return "CL";
    }
    def String getScacCode(Object poData, String shipMethod){
       return poData.Header.CarrierInformation.CarrierAlphaCode.text().trim()
    }
    def String getCarrierRouting(Object poData) {
        return poData.Header.CarrierInformation.CarrierRouting.text().trim();
    }
    def String getAcknowledgementType(int size){
        if(size>0){
           return "AC"
        }else {
            return "AD"
        }
    }

    def AddressInfo getShipFromAddressInfo(){
        return new AddressInfo();
    }

    def AddressInfo getBuyingAddressInfo(Object poData){
        return new AddressInfo();
    }

    def SPSReference getShipmentId(String orderNum){
        return new SPSReference();
    }

    def String getFOBPayCode(){
        return 'TP';
    }
    def String getPackingMedium(){
        return 'BOX';
    }

    protected def SPSReference getVendorComplianceShipmentNumberReference(Collection<OrderTag> tags) {
        return null;
    }

    protected def String getVendorComplianceShipmentNumber(Collection<OrderTag> tags, String kTag){
        if(kTag == null || kTag.length() == 0)
            return null;

        for(OrderTag tag:tags){
            if(tag.getTagName().equals(kTag)){
                return tag.getTagValue();

            }
        }
        return null;
    }



    def static String getAmazonARNFromTags(Collection<OrderTag> tags){

        for(OrderTag tag:tags){
            if(tag.getTagName().equals(TagUtilities.kEDIAmazonARN)){
                return tag.getTagValue();

            }

        }
        return ""
    }

    def static String getZapposDNFromTags(Collection<OrderTag> tags){

        for(OrderTag tag:tags){
            if(tag.getTagName().equals(TagUtilities.kEDIZapposDN)){
                return tag.getTagValue();

            }

        }
        return ""
    }

    def static String getDicksASIDNFromTags(Collection<OrderTag> tags){

        for(OrderTag tag:tags){
            if(tag.getTagName().equals(TagUtilities.kEDIDicksASIDN)){
                return tag.getTagValue();

            }

        }
        return ""
    }

    def boolean includeVendorInShipmentHeader(){
        return false;
    }

    def int getVendorComplianceId(EdiSpsConfigdata config, Object po){
            return config.getVendorComplianceFkey();
    }

    void setDropShipFlag(Object po){

    }

    void checkForFutureShipDate(Object po){
        po.Header.Date.each {date ->
            System.out.println(date.Date1.text());
            if(date.DateTimeQualifier1.text().equals("010")){
              LocalDate now = LocalDate.now();
                LocalDate future = LocalDate.parse(date.Date1.text(), DateTimeFormatter.ISO_LOCAL_DATE);
               long days = ChronoUnit.DAYS.between(now,future);
                System.out.println(days);
                if(days>daysForFutureShip){
                    isFutureShip = true;
                    futureShipDate = date.Date1.text();
                }
            }
            if(date.DateTimeQualifier1.text().equals("037")&& !isFutureShip){
                LocalDate now = LocalDate.now();
                LocalDate future = LocalDate.parse(date.Date1.text(), DateTimeFormatter.ISO_LOCAL_DATE);
                long days = ChronoUnit.DAYS.between(now,future);
                System.out.println(days);
                if(days>daysForFutureShip){
                    isFutureShip = true;
                    futureShipDate = date.Date1.text();
                }
            }

        }
    }

    void injectWorkOrder(Order order, Object po){

    }

    void loadGiftMessageIfIncluded(Order order,Object po){
        po.Header.Notes.each { note ->
            println (note.NoteCode.text());
            if(note.NoteCode.text().contains("GFT")){
                order.gift_message = note.NoteInformationField.text();


            }
        }
    }

    void setGroupName(Order order,Object po){

    }
    def void setFacilityCode(Order order){

    }
    def String loadBackorderRule(){
        return OrderXMLDoc.kRejectBackOrder;
    }

    def static String generate846Inventory(EdiSpsConfigdata configdata, int clientId) {
        assert (clientId != null)
        def writer = new StringWriter()
        def builder = new groovy.xml.MarkupBuilder(writer)
        def today = new Date();
        List l = getOhInventoryMap(clientId+"",configdata.name)
        int counter = 1;

        def inv = builder.ItemRegistry(xmlns: 'http://www.spscommerce.com/RSX')
                {




                    Header {
                        HeaderReport{
                            TradingPartnerId(configdata.spsaccount)
                            DocumentId(today.getTime())
                            TsetPurposeCode('00')
                            ReportTypeCode('MB')
                            InventoryDate(String.format('%1$tY-%<tm-%<td', today))
                        }


                    }

                    Structure{



                        for(Object row : l) {
                            Map data = (Map) row;
                            LineItem {
                                InventoryLine {

                                    LineSequenceNumber(counter)

                                    VendorPartNumber(data.get("foreign_sku"))
                                    ConsumerPackageCode(data.get("upc"))




                                }
                                QuantitiesSchedulesLocations{
                                    QuantityQualifier('33')
                                    TotalQty(data.get("qty_on_hand"))
                                    TotalQtyUOM("EA")

                                }
                            }
                            counter++;

                        }



                    }


                }




        return writer.toString()

    }



    public static List getOhInventoryMap(String clientID,String type) throws Exception{

        String sql = "SELECT\n" +
                "    dbo.owd_inventory_sku_maps.owd_sku,\n" +
                "    dbo.owd_inventory_sku_maps.foreign_sku,\n" +
                "    dbo.owd_inventory_sku_maps.upc,\n" +
                "    dbo.owd_inventory_oh.qty_on_hand\n" +
                "FROM\n" +
                "    dbo.owd_inventory\n" +
                "LEFT OUTER JOIN\n" +
                "    dbo.owd_inventory_sku_maps\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_inventory.inventory_num = dbo.owd_inventory_sku_maps.owd_sku)\n" +
                "INNER JOIN\n" +
                "    dbo.owd_inventory_oh\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_inventory.inventory_id = dbo.owd_inventory_oh.inventory_fkey)\n" +
                "WHERE\n" +
                "    dbo.owd_inventory_sku_maps.type = :type\n" +
                "AND dbo.owd_inventory.client_fkey = :clientID ;"
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        q.setParameter("clientID",clientID);
        q.setParameter("type",type);

        return  q.list();
    }
}
