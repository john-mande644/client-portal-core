package com.owd.jobs.jobobjects.spscommerce

import com.owd.LogableException
import com.owd.core.Mailer
import com.owd.core.TagUtilities
import com.owd.core.business.order.Inventory
import com.owd.core.business.order.Order
import com.owd.core.business.order.OrderFactory
import com.owd.core.business.order.OrderUtilities
import com.owd.core.business.order.beans.lineItemExemptions
import com.owd.core.managers.ConnectionManager
import com.owd.core.managers.FacilitiesManager
import com.owd.core.managers.ManifestingManager
import com.owd.core.xml.OrderXMLDoc
import com.owd.hibernate.HibUtils
import com.owd.hibernate.HibernateSession
import com.owd.hibernate.generated.*
import com.owd.jobs.jobobjects.symphony.SymphonyAPI
import com.owd.jobs.jobobjects.vendorCompliance.addressUtils
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.hibernate.Criteria
import org.hibernate.Query

import java.sql.PreparedStatement
import java.sql.ResultSet

/**
 * Created by IntelliJ IDEA.
 * User: Stewart Buskirk
 * Date: May 3, 2010
 * Time: 11:24:45 AM
 * To change this template use File | Settings | File Templates.
 */

class SpsCommerceDicksSportingGoodsUtilities extends SpsCommerceUtilities{


    static receiveDirPath = "/in"
    static sendDirPath = "/out"
    private final static Logger log = LogManager.getLogger();



    private static Map<String, String> outboundShipMap

    static {
        outboundShipMap = new TreeMap<String, String>()

        outboundShipMap.put("FedEx 2Day","FDEG_SE");
        /*outboundShipMap.put("FedEx 2Day","FDE_SC");
        outboundShipMap.put("FedEx 2Day","FX2D");
        outboundShipMap.put("FedEx 2Day","FEDX_SE_ET");
        outboundShipMap.put("FedEx 2Day","FEDX_SE_AC");
        outboundShipMap.put("FedEx 2Day","FEDX_SE_IA");*/
        outboundShipMap.put("FedEx Ground","FEDX_CG");
        outboundShipMap.put("FedEx International Economy","FEDX_CG");
        /*outboundShipMap.put("FedEx Ground","FDEG_CG");
        outboundShipMap.put("FedEx Ground","FDEG");
        outboundShipMap.put("FedEx Ground","FEDEXG");
        outboundShipMap.put("FedEx Ground","FDXG");
        outboundShipMap.put("FedEx Ground","FEDX_CG_ET");*/
        outboundShipMap.put("FedEx Home Delivery","FEDX_09");
        /* outboundShipMap.put("FedEx Home Delivery","FEDH"); */
        // mrhoades - change per SPS guidelines
        outboundShipMap.put("FedEx Home Delivery","FDEG");

        outboundShipMap.put("FedEx Standard Overnight","FEDX_ND");
        outboundShipMap.put("FedEx Standard Overnight","FDEG_ND");
        /*outboundShipMap.put("FedEx Standard Overnight","FXND");
        outboundShipMap.put("FedEx Standard Overnight","FDEX");
        outboundShipMap.put("FedEx Standard Overnight","FEDX_ND_ET");
        outboundShipMap.put("FedEx Standard Overnight","FXND_AC");
        outboundShipMap.put("FedEx Standard Overnight","FXND_IA");*/
        outboundShipMap.put("FedEx Priority Overnight","FEDX_NM");
      /*  outboundShipMap.put("FedEx Priority Overnight","FEDX_NM_ET");
        outboundShipMap.put("FedEx Priority Overnight","FEDX_NM_AC");
        outboundShipMap.put("FedEx Priority Overnight","FEDX_NM_IA");*/
        outboundShipMap.put("LTL","LTL_G2");
        outboundShipMap.put("LTL","LTL_R1");
        outboundShipMap.put("LTL","LTL_09");
        outboundShipMap.put("LTL","LTL_DS");
        outboundShipMap.put("UPS 2nd Day Air","UPSN_SE");
        outboundShipMap.put("UPS 2nd Day Air","UB");
        outboundShipMap.put("UPS 2nd Day Air","UPSN_SC");
        outboundShipMap.put("UPS 2nd Day Air","UPSET_SE");
        outboundShipMap.put("UPS 2nd Day Air","UY");
        outboundShipMap.put("UPS Ground","UPSN_CG");
        outboundShipMap.put("UPS Ground","UG");
        outboundShipMap.put("UPS Ground","UPSG");
        outboundShipMap.put("UPS Ground","UPSET_CG");
        outboundShipMap.put("UPS Ground","UX");
        outboundShipMap.put("UPS Next Day Air","UPSN_ND");
        outboundShipMap.put("UPS Next Day Air","UPND");
        outboundShipMap.put("UPS Next Day Air","UR");
        outboundShipMap.put("UPS Next Day Air","UPS1");
        outboundShipMap.put("UPS Next Day Air","UPSN_NM");
        outboundShipMap.put("UPS Next Day Air Saver","UPSN_PM");
        outboundShipMap.put("UPS Next Day Air","UPSET_ND");
        outboundShipMap.put("UPS Next Day Air","UZ");
        outboundShipMap.put("UPS SurePost","UPSN_ST");
        outboundShipMap.put("USPS Parcel Select Nonpresort","USPS_BC");
        outboundShipMap.put("USPS Priority Mail","USPS_PB");

    }

    private static Map<String,String[]> inboundShipMap

    static{
        inboundShipMap = new TreeMap<String, String[]>()
        inboundShipMap.put("UNSP_SE",["FedEx 2Day","0"]);
        inboundShipMap.put("UNSP_ND",["FedEx Standard Overnight","0"]);
        inboundShipMap.put("UNSP_CG",["FedEx Home Delivery","0"]);
        inboundShipMap.put("FDEG_SE",["FedEx 2Day","0"]);
        inboundShipMap.put("FDE_SC",["FedEx 2Day","0"]);
        inboundShipMap.put("FX2D",["FedEx 2Day","0"]);
        inboundShipMap.put("FEDX_SE_ET",["FedEx 2Day","1"]);
        inboundShipMap.put("FEDX_SE_AC",["FedEx 2Day","1"]);
        inboundShipMap.put("FEDX_SE_IA",["FedEx 2Day","1"]);
        inboundShipMap.put("FEDX_CG",["FedEx Ground","0"]);
        inboundShipMap.put("FDEG_CG",["FedEx Ground","0"]);
        inboundShipMap.put("FDEG",["FedEx Ground","0"]);
        inboundShipMap.put("FEDEXG",["FedEx Ground","0"]);
        inboundShipMap.put("FDXG",["FedEx Ground","0"]);
        inboundShipMap.put("FEDX_CG_ET",["FedEx Ground","1"]);
        inboundShipMap.put("FEDX_09",["FedEx Home Delivery","0"]);
        inboundShipMap.put("FEDH",["FedEx Home Delivery","0"]);
        inboundShipMap.put("FEDX_ND",["FedEx Standard Overnight","1"]);
        inboundShipMap.put("FDEG_ND",["FedEx Standard Overnight","1"]);
        inboundShipMap.put("FXND",["FedEx Standard Overnight","1"]);
        inboundShipMap.put("FDEX",["FedEx Standard Overnight","1"]);
        inboundShipMap.put("FEDX_ND_ET",["FedEx Standard Overnight","1"]);
        inboundShipMap.put("FXND_AC",["FedEx Standard Overnight","1"]);
        inboundShipMap.put("FXND_IA",["FedEx Standard Overnight","1"]);
        inboundShipMap.put("FEDX_NM",["FedEx Priority Overnight","1"]);
        inboundShipMap.put("FEDX_NM_ET",["FedEx Priority Overnight","1"]);
        inboundShipMap.put("FEDX_NM_AC",["FedEx Priority Overnight","1"]);
        inboundShipMap.put("FEDX_NM_IA",["FedEx Priority Overnight","0"]);
        inboundShipMap.put("LTL_G2",["LTL","0"]);
        inboundShipMap.put("LTL_R1",["LTL","0"]);
        inboundShipMap.put("LTL_09",["LTL","0"]);
        inboundShipMap.put("LTL_DS",["LTL","0"]);
        inboundShipMap.put("UPSN_SE",["UPS 2nd Day Air","0"]);
        inboundShipMap.put("UB",["UPS 2nd Day Air","0"]);
        inboundShipMap.put("UPSN_SC",["UPS 2nd Day Air","0"]);
        inboundShipMap.put("UPSET_SE",["UPS 2nd Day Air","1"]);
        inboundShipMap.put("UY",["UPS 2nd Day Air","1"]);
        inboundShipMap.put("UPSN_CG",["UPS Ground","0"]);
        inboundShipMap.put("UG",["UPS Ground","0"]);
        inboundShipMap.put("UPSG",["UPS Ground","0"]);
        inboundShipMap.put("UPSET_CG",["UPS Ground","1"]);
        inboundShipMap.put("UX",["UPS Ground","1"]);
        inboundShipMap.put("UPSN_ND",["UPS Next Day Air","0"]);
        inboundShipMap.put("UPND",["UPS Next Day Air","0"]);
        inboundShipMap.put("UR",["UPS Next Day Air","0"]);
        inboundShipMap.put("UPS1",["UPS Next Day Air","0"]);
        inboundShipMap.put("UPSN_NM",["UPS Next Day Air","0"]);
        inboundShipMap.put("UPSN_PM",["UPS Next Day Air Saver","0"]);
        inboundShipMap.put("UPSET_ND",["UPS Next Day Air","0"]);
        inboundShipMap.put("UZ",["UPS Next Day Air","0"]);
        inboundShipMap.put("UPSN_ST",["UPS SurePost","0"]);
        inboundShipMap.put("USPS_BC",["USPS Parcel Select Nonpresort","0"]);
        inboundShipMap.put("USPS_PB",["USPS Priority Mail","0"]);

    }

    // receiveDirTestPath("/testin"),
    //  sendDirTestPath("/testout");





    def static deliverAsn(SPSCommerceRemoteFTP ftp, int orderId, int clientId) {

        String asnData = SpsCommerceDicksSportingGoodsUtilities.generateASN(orderId, clientId)
        println asnData
        ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, asnData.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirPath)
    }
    def static deliverCanceledASN( Object poData, int clientId) {
        SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP();
        String asnData =generateCancelAsn(poData, clientId)
        println asnData
        ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, asnData.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirPath)
    }

    def static void main(String[] args) {
        //generateInvoice("hello", 1)
        // println storeMap

        //processPendingPos(489);
       /* XmlParser parser = new XmlParser()
        parser.setTrimWhitespace(true)
        OwdOrder order = OrderFactory.getOwdOrderFromOrderID(11222171, 489, true)



        OwdFacility shipFrom = FacilitiesManager.getFacilityForCode(order.getFacilityCode())
        Map<String, String> tagMap = TagUtilities.getTagMap("ORDER", order.getOrderId())

        println "asn tagmap: " + tagMap
        String poNode = tagMap.get(TagUtilities.kEDIPurchaseOrder.toUpperCase())
        def poData = parser.parseText(poNode)
        EdiSpsConfigdata configdata = getEdiConfigData(poData)*/
        /* String ack = SpsCommerceUtilities.generateACK(configdata,poData,471,order)
         println ack*/
       // System.out.println(generate846Inventory(getEdiConfigDataSPSAccount("0XRALLGILDANUSA"),471))


      //  String inv = generate846Inventory(getEdiConfigDataSPSAccount("0XRALLGILDANUSA"),471)
        String asnData = SpsCommerceUtilities.generateASN(12052071 ,471);
        println asnData;
       SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP();

        //uncomment when you're ready to deliver the test ASN to SPSCommmerce test folder
        ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, asnData.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirPath)



    }



    def static String getOwdSkuFromUpc(EdiSpsConfigdata config, String upcValue) {
        if (upcValue == null || upcValue.length() < 8) {
            return null;
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
        if (null == sku) {
            println("in inventory_sku_map lookup")
            println(config.spsaccount)
            println(config.acknowledgmentRequired);
            println(config.verifyPrice);
            println(config.vendorComplianceFkey);

           // println("select owd_sku from owd_inventory_sku_maps  where client_fkey=" + config.getClientFkey() + " and upc='" + upcValue.trim() + "'" + " and vendor_compliance_fkey='" + config.getVendorComplianceFkey() + "'")
            ResultSet rs = HibernateSession.getResultSet("select owd_sku from owd_inventory_sku_maps  where client_fkey=" + config.getClientFkey() + " and upc='" + upcValue.trim() + "'" + " and type='DSG'")
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

        if (null == upc  ) {

            ResultSet rs = HibernateSession.getResultSet("select upc from owd_inventory_sku_maps where client_fkey=" + config.getClientFkey() + " and owd_sku='" + skuValue.trim() + "'" + " and type='DSG'")
            if (rs.next()) {
                upc = rs.getString(1)
            }
            rs.close()
        }

        if (null == upc) {

            println "select upc_code from owd_inventory where client_fkey=" + config.getClientFkey() + " and inventory_num='" + skuValue.trim() + "'"
            ResultSet rs = HibernateSession.getResultSet("select upc_code from owd_inventory where client_fkey=" + config.getClientFkey() + " and inventory_num='" + skuValue.trim() + "'")
            if (rs.next()) {
                upc = rs.getString(1)
            }
            rs.close()
        }

        return upc
    }

    def static Map<String, String> makeVendorToOwdSkuMap(EdiSpsConfigdata config) {
        Map<String, String> skuMap = new HashMap<String, String>();
        for (EdiSpsConfigdataSkus mapper : config.getSkus()) {
            skuMap.put(mapper.getVendorSku(), mapper.getOwdSku())
        }

        return skuMap
    }

    def static Map<String, String> makeOwdToVendorSkuMap(EdiSpsConfigdata config) {
        Map<String, String> skuMap = new HashMap<String, String>();
        for (EdiSpsConfigdataSkus mapper : config.getSkus()) {
            skuMap.put(mapper.getOwdSku(), mapper.getVendorSku())
        }

        return skuMap
    }


    def static Order importPo(Node podoc, int clientID) throws Exception {


        def order = new Order("" + clientID) //ballpark classics, for now


        EdiSpsConfigdata configdata = getEdiConfigData(podoc)
        Map<String, String> fromVendorSkuMap = makeVendorToOwdSkuMap(configdata)

        //  order.getShippingInfo().carr_service = podoc.Header.OrderHeader.CarrierRouting.text()

        // order.getShippingInfo().setShipOptions(order.getShippingInfo().carr_service,"Third Party Billing","Y2X172");

        //  order.shippercontact = "GSI Commerce";
        //                     order.shippercompany = "GSI Commerce";
        //                     order.shipperaddress_one = "935 First Avenue";
        //                     order.shipperaddress_two = "";
        //                     order.shippercity = "King of Prussia";
        //                    order.shipperstate = "PA";
        //                   order.shipperzip = "19406";
        //                    order.shipperphone = "6104917000";

        order.po_num = podoc.Header.OrderHeader.PurchaseOrderNumber.text()
        if(isDuplicatePO(order.po_num,clientID+"")){
            throw new Exception("Duplicate PO");
        }

        if(podoc.Header.OrderHeader.Division.text().equals("DSG_ECOM")){
            order.template = "dsg"
        }
        if(podoc.Header.OrderHeader.Division.text().equals("FS_ECOM")){
            order.template = "fs"
        }
        if(podoc.Header.OrderHeader.Division.text().equals("GG_ECOM")){
            order.template = "gg"
        }




        def notes = podoc.Header.Notes;
        if(notes!=null){
            if (notes.NodeCode.text().equals("GFT")){
                order.is_gift = "1";
                order.gift_message(notes.NoteInformationField.text())

            }
        }


        def shipto = podoc.Header.Address.find { it.AddressTypeCode.text() == 'ST' }
        println(shipto)
       println("The ship")
        println(shipto.AddressLocationNumber.text)
        println(podoc.Header.Address.AddressLocationNumber.text())

        if (shipto != null) {

            if (shipto.AddressLocationNumber.text().length() > 0 && !(shipto.Address1.text().length() > 0)) {
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
                    address_one = shipto.Address1.text()
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
         try{
             println("Lookin up carrier")
             println(podoc.LineItems.LineItem[0].CarrierInformation.CarrierRouting.text())
             String[] methodArray = inboundShipMap.get(podoc.LineItems.LineItem[0].CarrierInformation.CarrierRouting.text().trim());

              order.setShippingMethodName(methodArray[0])
             order.setSignatureRequired(methodArray[1].equals("1"))

             println("We set the ship Method")
         }catch (Exception e){
             e.printStackTrace();
         }
        def lines = podoc.LineItems.LineItem.OrderLine.each {


           /* if (null != it.OrderQtyUOM?.text() && !("EA".equals("" + it.OrderQtyUOM.text()))) {
                throw new Exception("unrecognized UOM of " + it.OrderQtyUOM.text());
            }*/

            String internalSku = getOwdSkuFromUpc(configdata, "" + it.ConsumerPackageCode.text())
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

               // String price = it.PurchasePrice?.text();
                //if price does not match add line to exemptions list
              /*  if (!verifyPrice(configdata, internalSku, price)) {

                    def lie = new lineItemExemptions()

                    lie.setVendor_sku(it.ConsumerPackageCode.text())
                    lie.setInventory_num(internalSku);
                    lie.setExemptionCode("price");
                    lie.setExemptionValue("no match");
                    lie.setQty(it.OrderQty.text() + "")

                    order.lineExemptions.add(lie);*/

                    //check for stock and only ship what we have
                    println(configdata.spsaccount);
                    println("befor the if for account xxxx");
                    if (configdata.spsaccount.equals("0XRALLGILDANUSA")) {
                        OwdInventory owdInv = com.owd.core.managers.InventoryManager.getOwdInventoryForClientAndSku(order.clientID,internalSku)


                        int stock = order.getStockLevelForInventoryId(owdInv.getInventoryId(),order.facilityCode)
                        if(stock == 0){
                            println "WE have 0 in stock exempt the whole line"
                            def lie = new lineItemExemptions()

                            lie.setVendor_sku(it.ConsumerPackageCode.text())
                            lie.setInventory_num(internalSku);
                            lie.setExemptionCode("quantity");
                            lie.setExemptionValue("out of stock");
                            lie.setQty(it.OrderQty.text() + "")
                            lie.setMerchant_line_number(it.LineSequenceNumber.text())

                            order.lineExemptions.add(lie);

                        }else{
                            int orderQty = Integer.parseInt(it.OrderQty.text());
                            if(stock>= orderQty){
                                println "We can fullfill";
                                order.addLineItem(internalSku, "" + it.OrderQty.text(), "0" + ((it.PurchasePrice?.text()) == null ? "" : it.PurchasePrice?.text()), "0.00", itemDescription, "", "")

                            } else{
                                println "We can partial Ship";
                                order.addLineItem(internalSku, "" + stock, "0" + ((it.PurchasePrice?.text()) == null ? "" : it.PurchasePrice?.text()), "0.00", itemDescription, "", "")

                                def lie = new lineItemExemptions()

                                lie.setVendor_sku(it.ConsumerPackageCode.text())
                                lie.setInventory_num(internalSku);
                                lie.setExemptionCode("quantity");
                                lie.setExemptionValue("short ship");
                                lie.setQty((orderQty-stock)+"")
                                lie.setMerchant_line_number(it.LineSequenceNumber.text())

                                order.lineExemptions.add(lie);


                            }


                        }



                   /* } else {
                        order.addLineItem(internalSku, "" + it.OrderQty.text(), "0" + ((it.PurchasePrice?.text()) == null ? "" : it.PurchasePrice?.text()), "0.00", itemDescription, "", "")


                    }*/

                }






            order.recalculateBalance()
        }


        return order;
    }

    public static EdiSpsConfigdata getEdiConfigData(Node poDoc) {

        String partner = poDoc.Header.OrderHeader.TradingPartnerId.text()
        String vendor = poDoc.Header.OrderHeader.Vendor.text()

        println "SPSPartner:" + partner
        println "SPSVendor:" + vendor

        Query q = HibernateSession.currentSession().createQuery("from EdiSpsConfigdata where Spsaccount=? ")
        q.setParameter(0, partner)

        List<EdiSpsConfigdata> configs = (List<EdiSpsConfigdata>) q.list()
        if (configs.size() != 1) {
            throw new Exception("SPS EDI import error - wrong number of configs found for " + partner + "/" + vendor)
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

    def static String generateASN(int orderId, int clientId) {
        println("Doing Dicks");
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

        /*   Query q = HibernateSession.currentSession().createQuery("from EdiSpsConfigdata where Spsaccount='"+partner.trim()+"' and Vendorid='"+vendor.trim()+"'")
           //  q.setString(0,partner)
           //   q.setString(1,vendor)
           List<EdiSpsConfigdata> configs = (List<EdiSpsConfigdata> ) q.list()
           println configs.size()+" configs found!"
           if(configs.size()!=1)
           {
               throw new Exception("SPS EDI import error - wrong number  of configs found for "+partner+"/"+vendor)
           }

           EdiSpsConfigdata configdata = configs.get(0)*/
        EdiSpsConfigdata configdata = getEdiConfigData(poData)
        Map<String, String> fromVendorSkuMap = makeOwdToVendorSkuMap(configdata)


        println order.poNum
        def today = new Date();

        int totalLines = 0;
        int totalQuantity = 0;

        def asn = builder.Shipments(xmlns: 'http://www.spscommerce.com/RSX')
                {

                    Shipment {


                        Header {
                            ShipmentHeader {
                                TradingPartnerId(poData.Header.OrderHeader.TradingPartnerId.text().trim()) //from order?
                                ShipmentIdentification(order.orderNum)
                                ShipDate(String.format('%1$tY-%<tm-%<td', order.shippedDate))
                                TsetPurposeCode('00') //OK
                                ShipNoticeDate(String.format('%1$tY-%<tm-%<td', today))
                                ShipNoticeTime(String.format('%1$tH:%<tM:%<tS', today))
                                ASNStructureCode('0001')
                                if (configdata.spsaccount.equals("0XRALLGILDANUSA")) {
                                    BillOfLadingNumber(com.owd.core.business.order.Package.getPackagesForOrderId(orderId).get(0).tracking_no);
                                } else {
                                    if(configdata.getVendorComplianceFkey()==2 && order.shipinfo.carrService.equals("LTL")){
                                        BillOfLadingNumber(com.owd.core.business.order.Package.getPackagesForOrderId(orderId).get(0).tracking_no);
                                    } else{
                                        BillOfLadingNumber('')
                                    }

                                }
                                CarrierProNumber('')
                                CurrentScheduledDeliveryDate(String.format('%1$tY-%<tm-%<td', today + 3))

                                // ShipmentQtyPackingCode('CTN25')
                                // ShipmentLadingQuantity("" + order.packagesShipped) // divide qty by 3 plus 1
                                // CarrierAlphaCode(order.shipinfo.carrService.contains("USPS") ? 'USPS' : 'UPSN')  //lookup
                            }

                            if (configdata.spsaccount.equals("0XRALLGILDANUSA")) {


                                def refs = poData.Header.Reference?.each { ref ->

                                    Reference {
                                        ReferenceQual(ref.ReferenceQual?.text().trim())
                                        ReferenceID(ref.ReferenceID?.text().trim())
                                        //  Description(ref.Description?.text())
                                    }
                                }




                            }
                            Address {
                                AddressTypeCode('ST')
                                AddressName(order.shipinfo.shipFirstName + ' ' + order.shipinfo.shipLastName)
                                AddressAlternateName(order.shipinfo.shipCompanyName)
                                Address1(order.shipinfo.shipAddressOne)
                                Address2(order.shipinfo.shipAddressTwo)
                                City(order.shipinfo.shipCity)
                                State(order.shipinfo.shipState)
                                PostalCode(order.shipinfo.shipZip)
                                Country(order.shipinfo.shipCountry)

                                def shipto = poData.Header.Address.find { it.AddressTypeCode.text().equals('ST') }
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

                                    LocationCodeQualifier("ZZ")
                                    if (poData.Header.OrderHeader.Vendor.text().length() > 0) {
                                        AddressLocationNumber("" + poData.Header.OrderHeader.Vendor.text().trim())
                                    } else {
                                        AddressLocationNumber(order.getFacilityCode())
                                    }


                            }
                            /*  Miscellaneous {
                                                Qualifier1('TD502')
                                                Description1('2')
                                                Qualifier2('1')
                                            }
                            */
                            String scacCode = poData.Header.CarrierInformation.CarrierAlphaCode.text().trim()
                            if (scacCode.length() < 2) {
                                scacCode = outboundShipMap.get(order.shipinfo.carrService)
                                if (scacCode == null) {
                                    scacCode = ""
                                }
                            }
                            CarrierInformation {
                                StatusCode('CL')

                                    CarrierTransMethodCode(order.shipinfo.carrService.equals('LTL') ? 'LT' : 'ZZ')


                                CarrierAlphaCode(scacCode)
                                RoutingSequenceCode("B")
                               // CarrierRouting(order.shipinfo.carrService)
                                CarrierRouting(scacCode)




                            }

                            QuantityAndWeight {
                                   PackingMedium('CTN')

                                LadingQuantity(order.packagesShipped)
                                WeightQualifier('G')
                                Weight(order.shippedWeight)
                                WeightUOM('LB')

                            }

                            FOBRelatedInstruction {

                                if (configdata.getSpsaccount().equals("0XRALLGILDANUSA")) {
                                    //set PP for Gildan Amazon orders
                                    FOBPayCode('CC')
                                } else {
                                    FOBPayCode('TP')
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
                                CustomerOrderNumber(poData.Header.OrderHeader.CustomerOrderNumber?.text().trim())
                            }
                            QuantityAndWeight {

                                    PackingMedium('CTN')

                                LadingQuantity(order.packagesShipped)
                                WeightQualifier('G')
                                Weight(order.shippedWeight)
                                WeightUOM('LB')
                            }
                            /*CarrierInformation {
                                StatusCode('CL')
                                CarrierTransMethodCode(order.shipinfo.carrService.equals('LTL')?'L':'M')
                                CarrierAlphaCode(podoc.Header.CarrierInformation.CarrierAlphaCode.text())
                            }*/

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

                                        CarrierPackageID(pack.tracking_no)
                                    }
                                    PhysicalDetails {
                                          PackingMedium('CTN')

                                        WeightQualifier('G')
                                        PackWeight(pack.weight)
                                        PackWeightUOM('LB')
                                        if (!("TRZL".equalsIgnoreCase(vendor))) { //if not toysrus dropship

                                            PackUOM('CTN')
                                            PackSize('1')
                                        }
                                    }

                                    /* if("BUMAQ".equalsIgnoreCase(vendor)){
                                         //amazon need to get SSCC in Marks and Numbers Section
                                         MarksAndNumbersCollection{
                                             MarksAndNumbersQualifier1('UC');
                                             MarksAndNumbers1(com.owd.core.business.order.Package.getSSCCCodeForPackage(pack.order_track_id));
                                             MarksAndNumbersQualifier2('CA');
                                             MarksAndNumbers2(pack.tracking_no);
                                         }

                                     }*/



                                            println "Getting line items for order_track_id " + pack.order_track_id

                                            List<Map> items = com.owd.core.business.order.Package.getEDILineItemsForPackage(pack.order_track_id)


                                            println "Got " + items.size() + " lines for package"
                                            items.each { itemMap ->
                                                ItemLevel {
                                                println itemMap
                                                String vendorSku = fromVendorSkuMap.get(itemMap.get('SKU'))
                                                println vendorSku
                                                if (vendorSku == null || vendorSku.length() < 1) {
                                                    String upc = SpsCommerceDicksSportingGoodsUtilities.getUPCFromOwdSku(configdata, "" + itemMap.get('SKU'))
                                                    println "got upc " + upc + " for " + itemMap.get('SKU')
                                                    def poLineItem = poData.LineItems.LineItem.OrderLine.find {
                                                        println "checking CPC " + it.ConsumerPackageCode.text()
                                                        it.ConsumerPackageCode.text().trim().equals(upc)
                                                    }

                                                    if (poLineItem) {

                                                        totalLines++;
                                                        ShipmentLine {
                                                            LineSequenceNumber(poLineItem.LineSequenceNumber.text().trim())
                                                            BuyerPartNumber(poLineItem.BuyerPartNumber ? poLineItem.BuyerPartNumber.text().trim() : '')
                                                            VendorPartNumber(poLineItem.VendorPartNumber ? poLineItem.VendorPartNumber.text().trim() : poLineItem.BuyerPartNumber.text().trim())
                                                            ConsumerPackageCode(poLineItem.ConsumerPackageCode ? poLineItem.ConsumerPackageCode.text().trim() : upc)
                                                            //  PartDescription1('Boston Red Sox Fenway Park MLB Baseball Game	3')
                                                            ShipQty(itemMap.get('QTY') + '.0')
                                                            totalQuantity += Integer.parseInt('' + itemMap.get('QTY'))
                                                            ShipQtyUOM('EA')

                                                            ItemStatusCode('AC')

                                                            // ProductColorCode('75')
                                                            // ProductColorDescription('One Color')
                                                        }
                                                    }

                                                } else {
                                                    def poLineItem = poData.LineItems.LineItem.OrderLine.find {
                                                        if ("TRZL".equalsIgnoreCase(vendor)) {
                                                            it.ProductID.PartNumber.text().trim().equals(vendorSku)
                                                        } else {
                                                            it.VendorPartNumber.text().trim().equals(vendorSku)
                                                        }

                                                    }


                                                    if (poLineItem) {

                                                        totalLines++;
                                                        ShipmentLine {
                                                            LineSequenceNumber(poLineItem.LineSequenceNumber.text().trim())
                                                            BuyerPartNumber(poLineItem.BuyerPartNumber ? poLineItem.BuyerPartNumber.text().trim() : '')
                                                            VendorPartNumber((vendorSku))
                                                            ConsumerPackageCode(poLineItem.ConsumerPackageCode ? poLineItem.ConsumerPackageCode.text().trim() : itemMap.get('BARCODE'))
                                                            //  PartDescription1('Boston Red Sox Fenway Park MLB Baseball Game	3')
                                                            ShipQty(itemMap.get('QTY') + '.0')
                                                            totalQuantity += Integer.parseInt('' + itemMap.get('QTY'))
                                                            ShipQtyUOM(poLineItem.OrderQtyUOM?.text().trim())
                                                            if ("TRZL".equalsIgnoreCase(vendor)) { //toysrus dropship
                                                                ItemStatusCode('AC')
                                                            }
                                                            // ProductColorCode('75')
                                                            // ProductColorDescription('One Color')
                                                        }
                                                    }
                                                }


                                            }

                                        }
                                    }
                                }


                            }
                        Summary {
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

    def static String generateACK(EdiSpsConfigdata configdata, Node poData, int clientId, OwdOrder order) {
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
                                TsetPurposeCode('00') //OK
                                PurchaseOrderDate(String.format('%1$tY-%<tm-%<td', today))

                                if(exemptLines.size()>0){
                                    AcknowledgementType("AC")
                                }else {
                                    AcknowledgementType("AD")
                                }
                                // ShipmentQtyPackingCode('CTN25')
                                // ShipmentLadingQuantity("" + order.packagesShipped) // divide qty by 3 plus 1
                                // CarrierAlphaCode(order.shipinfo.carrService.contains("USPS") ? 'USPS' : 'UPSN')  //lookup
                            }


                        }

                        LineItems {
                            poData.LineItems.LineItem.OrderLine.each { line ->
                                println(line)
                                // println(it.get("LineSequenceNumber").text())
                                LineItem {
                                    OrderLine {

                                        LineSequenceNumber(line.LineSequenceNumber.text())
                                        BuyerPartNumber(line.BuyerPartNumber.text())
                                        ConsumerPackageCode(line.ConsumerPackageCode.text())
                                        OrderQty(line.OrderQty.text())
                                        OrderQtyUOM(line.OrderQtyUOM.text())
                                        PurchasePrice(line.PurchasePrice.text())
                                        PurchasePriceBasis("NT")

                                    }
                                    LineItemAcknowledgement {
                                        //check for exempt lines
                                        if(exemptLines.containsKey(line.ConsumerPackageCode.text())){
                                           def lie = exemptLines.get(line.ConsumerPackageCode.text())
                                            //determine type of exemption
                                            if(lie.exemptionCode.equals("quantity")&&lie.exemptionValue.equals("short ship")){
                                                ItemStatusCode("IQ")
                                                ItemScheduleQty(Integer.parseInt(line.OrderQty.text())-Integer.parseInt(lie.qty))
                                                ItemScheduleQualifier("068")
                                                ItemScheduleUOM("EA")
                                                ItemScheduleDate(String.format('%1$tY-%<tm-%<td', order.shipinfo.getScheduledShipDate()))

                                            }else{
                                                ItemStatusCode("IR")
                                                ItemScheduleQty(line.OrderQty.text())

                                                ItemScheduleUOM("EA")

                                            }


                                        }else{


                                        ItemStatusCode("IA")
                                        ItemScheduleQty(line.OrderQty.text())
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
                                        Date1(earlyDate.Date1.text())
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
                lines.put(lie.getVendor_sku(), lie);


            }

        }
        return lines;
    }



    def static String getAmazonARNFromTags(Collection<OrderTag> tags){

        for(OrderTag tag:tags){
            if(tag.getTagName().equals(TagUtilities.kEDIAmazonARN)){
                return tag.getTagValue();

            }

        }
        return ""
    }




    def static String generateCancelAsn(def poData, int clientId) {
        def writer = new StringWriter()
        def builder = new groovy.xml.MarkupBuilder(writer)

        String partner = poData.Header.OrderHeader.TradingPartnerId.text()
        String vendor = poData.Header.OrderHeader.Vendor.text()

        println "SPSPartner:" + partner
        println "SPSVendor:" + vendor

        /*   Query q = HibernateSession.currentSession().createQuery("from EdiSpsConfigdata where Spsaccount='"+partner.trim()+"' and Vendorid='"+vendor.trim()+"'")
           //  q.setString(0,partner)
           //   q.setString(1,vendor)
           List<EdiSpsConfigdata> configs = (List<EdiSpsConfigdata> ) q.list()
           println configs.size()+" configs found!"
           if(configs.size()!=1)
           {
               throw new Exception("SPS EDI import error - wrong number  of configs found for "+partner+"/"+vendor)
           }

           EdiSpsConfigdata configdata = configs.get(0)*/
        EdiSpsConfigdata configdata = getEdiConfigData(poData)
       // Map<String, String> fromVendorSkuMap = makeOwdToVendorSkuMap(configdata)

        def today = new Date();

        int totalLines = 0;
        int totalQuantity = 0;

        def asn = builder.Shipments(xmlns: 'http://www.spscommerce.com/RSX')
                {

                    Shipment {


                        Header {
                            ShipmentHeader {
                                TradingPartnerId(poData.Header.OrderHeader.TradingPartnerId.text().trim()) //from order?
                                ShipmentIdentification(poData.Header.OrderHeader.PurchaseOrderNumber.text())
                               // ShipDate(String.format('%1$tY-%<tm-%<td', order.shippedDate))
                                TsetPurposeCode('00') //OK
                                ShipNoticeDate(String.format('%1$tY-%<tm-%<td', today))
                                ShipNoticeTime(String.format('%1$tH:%<tM:%<tS', today))
                                ASNStructureCode('0001')

                                CurrentScheduledDeliveryDate(String.format('%1$tY-%<tm-%<td', today + 3))

                                // ShipmentQtyPackingCode('CTN25')
                                // ShipmentLadingQuantity("" + order.packagesShipped) // divide qty by 3 plus 1
                                // CarrierAlphaCode(order.shipinfo.carrService.contains("USPS") ? 'USPS' : 'UPSN')  //lookup
                            }

                            if (configdata.spsaccount.equals("0XRALLGILDANUSA")||configdata.spsaccount.equals("080ALLGILDANUSA")) {


                                def refs = poData.Header.Reference?.each { ref ->

                                    Reference {
                                        ReferenceQual(ref.ReferenceQual?.text().trim())
                                        ReferenceID(ref.ReferenceID?.text().trim())
                                        //  Description(ref.Description?.text())
                                    }
                                }


                            }


                        }
                        OrderLevel {
                            OrderHeader {

                                InvoiceNumber(poData.Header.OrderHeader.InvoiceNumber?.text().trim() )
                                PurchaseOrderNumber(poData.Header.OrderHeader.PurchaseOrderNumber?.text().trim())
                                ReleaseNumber(poData.Header.OrderHeader.ReleaseNumber?.text().trim())
                                PurchaseOrderDate(poData.Header.OrderHeader.PurchaseOrderDate?.text().trim())
                                Vendor(poData.Header.OrderHeader.Vendor?.text().trim())
                                CustomerOrderNumber(poData.Header.OrderHeader.CustomerOrderNumber?.text().trim())
                            }
                            QuantityAndWeight {

                               // PackingMedium('CTN')

                                LadingQuantity(0)
                               // WeightQualifier('G')
                               // Weight(order.shippedWeight)
                               // WeightUOM('LB')
                            }
                            /*CarrierInformation {
                                StatusCode('CL')
                                CarrierTransMethodCode(order.shipinfo.carrService.equals('LTL')?'L':'M')
                                CarrierAlphaCode(podoc.Header.CarrierInformation.CarrierAlphaCode.text())
                            }*/

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

                            def cons = poData.Header.Contact?.each { con ->
                                Contact {
                                    ContactName(con.ContactName?.text().trim())
                                    PrimaryPhone(con.PrimaryPhone?.text().trim())
                                    PrimaryFax(con.PrimaryFax?.text().trim())
                                    PrimaryEmail(con.PrimaryEmail?.text().trim())
                                }
                            }
                            PackLevel{
                                Pack{
                                    PackLevelType('P')
                                    ShippingSerialID('0000000000000000')
                                }


                                    def lines = poData.LineItems.LineItem.OrderLine.each { line ->
                                        println(line)
                                        ItemLevel{
                                         ShipmentLine{
                                             LineSequenceNumber(line.LineSequenceNumber.text())
                                             BuyerPartNumber(line.BuyerPartNumber.text())
                                             VendorPartNumber(line.BuyerPartNumber.text())
                                             ConsumerPackageCode(line.ConsumerPackageCode.text())
                                             ShipQty(0)
                                             ShipQtyUOM('EA')
                                             ItemStatusCode('ID')
                                             OrderQty(line.OrderQty.text())
                                             OrderQtyUOM('EA')




                                         }
                                        Reference{
                                            ReferenceQual('TD')
                                            ReferenceID('out_of_stock')
                                        }


                                    }

                                }
                            }


                            //todo items
                        }
                    }
                }



        // throw new Exception("fix line number lookup from PO!!!")
        return writer.toString()

    }


    def static String generate846Inventory(EdiSpsConfigdata configdata, int clientId) {
        assert (clientId != null)
        def writer = new StringWriter()
        def builder = new groovy.xml.MarkupBuilder(writer)
        def today = new Date();
         List l = getOhInventoryMap(clientId+"")
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


    public static boolean isDuplicatePO(String poNum, String clientId){
        boolean duplicate = false;
        try{
            String sql = "select order_id from owd_order where client_fkey = :clientId and po_num = :poNum ";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("clientId", clientId);
            q.setParameter("poNum", poNum);
            if(q.list().size()>0){
                duplicate = true;
            }

        }catch (Exception e){
            e.printStackTrace()
        }

        return duplicate;

    }




    public static List getOhInventoryMap(String clientID) throws Exception{

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
                "    dbo.owd_inventory_sku_maps.type = 'DSG'\n" +
                "AND dbo.owd_inventory.client_fkey = :clientID ;"
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        q.setParameter("clientID",clientID);
        return  q.list();







    }





}
