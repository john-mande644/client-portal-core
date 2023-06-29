package com.owd.jobs.jobobjects.commercehub.ClientVendorSetups

import com.owd.core.business.order.Order
import com.owd.core.business.order.OrderStatus
import com.owd.core.xml.OrderXMLDoc
import com.owd.jobs.jobobjects.commercehub.CommerceHubApiBase
import com.owd.jobs.jobobjects.commercehub.CommerceHubXmlFileFormatter

/**
 * Created by danny on 6/24/2019.
 */
class JustBrandsMacysCommerceHubAPI extends CommerceHubApiBase {
    {
        setUseUPCLookup(true)
        setGroupName("macys");
        setPackingSlipTemplate("macys");
        setSendRejectOrder(true);
        setBackOrderRule(OrderXMLDoc.kBackOrderAll);
        inboundShipMap.put("UPSN_CG", "UPS Ground")

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

        invFileSource.setIncludeUPCValueInInventory(true)
        invFileSource.setStockPercentage(1.00)
        invFileSource.setClientId(getClientId())
        invFileSource.setUseUPCLookup(true)
        invFileSource.setIncludeWarehouseBreakout(true)

        return invFileSource.getFileData();
    }

    @Override
    public String pushInventoryFile() {
        CommerceHubXmlFileFormatter invFileSource = new CommerceHubXmlFileFormatter(sftpUser, sFtpRemoteFolder, CommerceHubXmlFileFormatter.CommerceHubXmlFileType.INVENTORY)

        invFileSource.setIncludeUPCValueInInventory(true)
        invFileSource.setStockPercentage(1.00)
        invFileSource.setClientId(getClientId())
        invFileSource.setUseUPCLookup(true)
        invFileSource.setIncludeWarehouseBreakout(true)

        return sendFileToCommerceHub(invFileSource)
    }

    @Override
    void saveOrderTagsAsNeeded(Order order, def orderSrc) {
        String shiptoId = orderSrc.shipTo.@personPlaceID
        println 'stid:' + shiptoId
        def shipto = orderSrc.depthFirst().find {
            it.name() == 'personPlace' && it.@personPlaceID == shiptoId
        }
        String receiptId = shipto.personPlaceData.ReceiptID.text();
        order.addTag("receiptId", receiptId);
        String salesDivision = orderSrc.salesDivision.text();
        String returnCode = orderSrc.vendorShipInfo.partnerLoctionId.text();
        String returnData = "";

        if (salesDivision.equalsIgnoreCase("21") || salesDivision.equalsIgnoreCase("23") || salesDivision.equalsIgnoreCase("26")) {
            if (returnCode.startsWith("S")) {
                returnData = "BY MAIL Follow these easy steps to return your purchase:\n" +
                        "- Visit www.bloomingdales.com/easyreturn to create and print your \n" +
                        "FREE return label.\n" +
                        "IN STORE Most purchases can be returned to your local Bloomingdale�s \n" +
                        "store:\n" +
                        "- Take your merchandise and this invoice (make sure the barcode is \n" +
                        "attached) to your local store.\n" +
                        "- Any sales associate can process your return. "
            } else if (returnCode.equalsIgnoreCase("NX")) {
                returnData = "- If you have any questions or would like assistance with a return, \n" +
                        "please refer to the CONTACT US information at the top of this page. "
            } else if (returnCode.startsWith("N")) {
                returnData = "BY MAIL Follow these easy steps to return your purchase:\n" +
                        "- Visit www.bloomingdales.com/easyreturn to create and print your \n" +
                        "FREE return label.\n" +
                        "- This package was carefully packed for you directly by our vendor. If \n" +
                        "you wish to return it, kindly mail it back to the vendor using the FREE\n" +
                        "return label provided via the link above. "
            } else {
                returnData = "- If you have any questions or would like assistance with a return, \n" +
                        "please refer to the CONTACT US information at the top of this page. "
            }
        } else if (salesDivision.equalsIgnoreCase("25")) {
            if (returnCode.startsWith("N") && !returnCode.equalsIgnoreCase("NX")) {
                returnData = "BY MAIL Follow these easy steps to return your purchase:\n" +
                        "- Visit www.bloomingdales.com/easyreturn to create and print your \n" +
                        "FREE return label.\n" +
                        "- This package was carefully packed for you directly by our vendor. If \n" +
                        "you wish to return it, kindly mail it back to the vendor using the FREE\n" +
                        "return label provided via the link above. "
            } else {
                returnData = "- If you have any questions or would like assistance with a return, \n" +
                        "please refer to the CONTACT US information at the top of this page. "
            }
        } else if (salesDivision.equalsIgnoreCase("11") || salesDivision.equalsIgnoreCase("13") || salesDivision.equalsIgnoreCase("16")) {
            if (returnCode.startsWith("S")) {
                returnData = "BY MAIL Follow these easy steps to return your purchase:\n" +
                        "- Visit www.macys.com/easyreturn to create and print your FREE \n" +
                        "return label.\n" +
                        "IN STORE Most purchases can be returned to your local Macy's store:\n" +
                        "- Take your merchandise and this invoice (make sure the barcode is \n" +
                        "attached) to your local store.\n" +
                        "- Any sales associate can process your return. "
            } else if (returnCode.equalsIgnoreCase("NX")) {
                returnData = "- If you have any questions or would like assistance with a return, \n" +
                        "please refer to the CONTACT US information at the top of this page. "
            } else if (returnCode.startsWith("N")) {
                returnData = "BY MAIL Follow these easy steps to return your purchase:\n" +
                        "- Visit www.macys.com/easyreturn to create and print your FREE \n" +
                        "return label.\n" +
                        "- This package was carefully packed for you directly by our vendor. If \n" +
                        "you wish to return it, kindly mail it back to the vendor using the FREE\n" +
                        "return label provided via the link above. "
            } else {
                returnData = "- If you have any questions or would like assistance with a return,\n" +
                        "please refer to the CONTACT US information at the top of this page."
            }
        } else if (salesDivision.equalsIgnoreCase("15")) {
            if (returnCode.equalsIgnoreCase("NX") && !returnCode.equalsIgnoreCase("NX")) {
                returnData = "- If you have any questions or would like assistance with a return, \n" +
                        "please refer to the CONTACT US information at the top of this pa"
            } else if (returnCode.startsWith("N")) {
                returnData = "BY MAIL Follow these easy steps to return your purchase:\n" +
                        "- Visit www.macys.com/easyreturn to create and print your FREE \n" +
                        "return label.\n" +
                        "- This package was carefully packed for you directly by our vendor. If \n" +
                        "you wish to return it, kindly mail it back to the vendor using the FREE\n" +
                        "return label provided via the link above. "
            } else {
                returnData = "- If you have any questions or would like assistance with a return, \n" +
                        "please refer to the CONTACT US information at the top of this page. "
            }
        }
        order.addTag("returnData", returnData);

        if (returnCode.equalsIgnoreCase("SM") || returnCode.equalsIgnoreCase("SN") || returnCode.equalsIgnoreCase("SV")) {
            order.addTag("returnBarcode", "1")
        }
    }

    @Override
    public String getPackingSlipFromTemplateFields(def orderSrc) {
        String salesDivision = orderSrc.salesDivision.text();

        System.out.println(salesDivision);

        if (salesDivision.equalsIgnoreCase("11") || salesDivision.equalsIgnoreCase("13") || salesDivision.equalsIgnoreCase("15") || salesDivision.equalsIgnoreCase("16")) {
            return "macys";
        }
        if (salesDivision.equalsIgnoreCase("21") || salesDivision.equalsIgnoreCase("23") || salesDivision.equalsIgnoreCase("25") || salesDivision.equalsIgnoreCase("26")) {
            return "bloomingdales";
        }

        return "";
    }

    @Override
    void doFinalStuffBeforeSavingOrder(Order order, def orderSrc) {
        order.phoneOverride = "000-000-0000"
        order.getShippingContact().phone = "(000)-000-0000"

        // case 888283 Inserts sku
        order.addInsertItemIfAvailable("ah-dropship-insert", 1)
    }

    // case 1098407 add third party shipping information
    @Override
    void loadThirdPartyBillingInfo(Order order) {
        if (order.getShipMethodName().contains("UPS")) {
            order.setThirdPartyBilling("70XW38");
            order.setThirdPartyBillingContact("Macy's Accounts Payable", "Attn: Freight Payment Department", "2101 E Kemper Rd.", "", "Cincinnati", "OH", "45241", "");
        }
    }
}