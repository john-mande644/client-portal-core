package com.owd.jobs.jobobjects.commercehub.ClientVendorSetups

import com.owd.core.business.order.Order
import com.owd.core.business.order.OrderStatus
import com.owd.core.xml.OrderXMLDoc
import com.owd.jobs.jobobjects.commercehub.CommerceHubApiBase
import com.owd.jobs.jobobjects.commercehub.CommerceHubXmlFileFormatter

/**
 * Created by danny on 6/24/2019.
 */
class SokoMacysCommerceHubAPI extends CommerceHubApiBase {
    {
        setUseUPCLookup(true);
        setGroupName("macys");
        setPackingSlipTemplate("macys");
        setSendRejectOrder(true);
        setBackOrderRule(OrderXMLDoc.kRejectBackOrder);

        inboundShipMap.put("UNSP","UPS Ground");
        inboundShipMap.put("UPSN_CG","UPS Ground");
        inboundShipMap.put("UPSN_FC","UPS Ground");
        inboundShipMap.put("FEDX_NM_R5", "FedEx Priority Overnight");
    }

    @Override
    public String confirmOrderShipment(OrderStatus os) {

        CommerceHubXmlFileFormatter confirmFileSource = new CommerceHubXmlFileFormatter(sftpUser, sFtpRemoteFolder, CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED)
        confirmFileSource.setkIncludeShipFrom(true)
        confirmFileSource.setCurrentOrderStatus(os)
        confirmFileSource.setClientId(getClientId())

        return sendFileToCommerceHub(confirmFileSource)
    }

    @Override
    public String getInventoryFile() {
        CommerceHubXmlFileFormatter invFileSource = new CommerceHubXmlFileFormatter(sftpUser, sFtpRemoteFolder, CommerceHubXmlFileFormatter.CommerceHubXmlFileType.INVENTORY)

        invFileSource.setUseUPCLookup(false)
        invFileSource.setIncludeUPCValueInInventory(true)
        invFileSource.setIncludeWarehouseBreakout(true)
        invFileSource.setWarehouseName("OWD-DC6")
        invFileSource.setStockPercentage(1.00)
        invFileSource.setClientId(getClientId())

        return invFileSource.getFileData();
    }

    @Override
    public String pushInventoryFile() {
        CommerceHubXmlFileFormatter invFileSource = new CommerceHubXmlFileFormatter(sftpUser, sFtpRemoteFolder, CommerceHubXmlFileFormatter.CommerceHubXmlFileType.INVENTORY)

        invFileSource.setUseUPCLookup(false)
        invFileSource.setIncludeUPCValueInInventory(true)
        invFileSource.setIncludeWarehouseBreakout(true)
        invFileSource.setWarehouseName("OWD DC6")
        invFileSource.setStockPercentage(1.00)
        invFileSource.setClientId(getClientId())

        System.out.print(invFileSource.getFileData())
        return sendFileToCommerceHub(invFileSource)
    }

    @Override
    public String getPackingSlipFromTemplateFields(def orderSrc) {
        return "macys";
    }

    @Override
    void loadThirdPartyBillingInfo(Order order) {
        if (order.getShipMethodName().contains("UPS")) {
            order.setThirdPartyBilling("98X8V6");
            order.setThirdPartyBillingContact("Soko, Inc.", "", "1819 Polk Street #242", "", "San Francisco", "CA", "94109", "");
        }
    }

    @Override
    void doFinalStuffBeforeSavingOrder(Order order, def orderSrc) {
    }

    @Override
    void saveOrderTagsAsNeeded(Order order, def orderSrc) {
        String shiptoId = orderSrc.shipTo.@personPlaceID
        println 'stid:' + shiptoId
        def shipto = orderSrc.depthFirst().find {
            it.name() == 'personPlace' && it.@personPlaceID == shiptoId
        }

        String shipFromId = orderSrc.shipFrom.@vendorShipID
        def vendorShipInfo = orderSrc.depthFirst().find {
            it.name() == 'vendorShipInfo' && it.@vendorShipID == shipFromId
        }

        String receiptId = shipto.personPlaceData.ReceiptID.text();

        order.addTag("receiptId", receiptId);
        String salesDivision = orderSrc.salesDivision.text();
        String returnCode = orderSrc.vendorShipInfo.partnerLocationId.text();
        String returnData = "";

        //
        if (salesDivision.equalsIgnoreCase("21") || salesDivision.equalsIgnoreCase("23") || salesDivision.equalsIgnoreCase("25") || salesDivision.equalsIgnoreCase("26")) {
            if (returnCode.startsWith("S") || (returnCode.startsWith("N") && returnCode != "NX" )) {
                returnData = "Free Returns by Mail\n\n" +
                        "Not happy with your purchase? Ship it back to us for free! \n\n" +
                        "1) Find your order\n" +
                        "Go to bloomingdales.com/easyreturns, search your Order History or\n" +
                        "lookup by order number, and start your return.\n\n" +
                        "2) Select and print\n" +
                        "Select items to return, print your return confirmation and return label.\n\n" +
                        "3) Pack and ship\n" +
                        "Pack items in original packaging along with the confirmation page,\n" +
                        "attach return label, and ship from any UPS location.\n\n" +
                        "*Free Returns exclude Gift Cards,food,gourmet gifts,Beauty Boxes,lamps,wall art,mirrors,furniture,mattresses & area rugs.\n" +
                        "Free Returns do not apply to international orders. There is a charge for scheduled return pickup in areas offering the service."
            } else if (returnCode.equalsIgnoreCase("NX")) {
                returnData = "Easy Returns - Follow these easy steps to return your purchase\n\n" +
                        "- If you have any questions or would like assistance with a return,\n" +
                        "please refer to the CONTACT US information at the top of this page."
            }
        } else if (salesDivision.equalsIgnoreCase("11") || salesDivision.equalsIgnoreCase("13") || salesDivision.equalsIgnoreCase("15") || salesDivision.equalsIgnoreCase("16")) {
            if (returnCode.startsWith("S") || (returnCode.startsWith("N") && returnCode != "NX" )) {
                returnData = "Free Returns by Mail\n\n" +
                        "Not happy with your purchase? Ship it back to us for free! \n\n" +
                        "1) Find your order\n" +
                        "Go to macys.com/easyreturns, search your Order History or\n" +
                        "lookup by order number, and start your return.\n\n" +
                        "2) Select and print\n" +
                        "Select items to return, print your return confirmation and return label.\n\n" +
                        "3) Pack and ship\n" +
                        "Pack items in original packaging along with the confirmation page,\n" +
                        "attach return label, and ship from any UPS location.\n\n" +
                        "*Free Returns exclude Gift Cards,food,gourmet gifts,Beauty Boxes,lamps,wall art,mirrors,furniture,mattresses & area rugs.\n" +
                        "Free Returns do not apply to international orders. There is a charge for scheduled return pickup in areas offering the service."
            } else if (returnCode.equalsIgnoreCase("NX")) {
                returnData = "Easy Returns - Folow these easy steps to return your purchase"
            } else {
                returnData = "- If you have any questions or would like assistance with a \n" +
                        "return, please refer to the CONTACT US information at the top \n" +
                        "of this page."
            }
        } else {
                returnData = "- If you have any questions or would like assistance with a return, \n" +
                        "please refer to the CONTACT US information at the top of this page. "
        }

        order.addTag("returnData", returnData);
        order.addTag("salesDivision", salesDivision);
    }
}
