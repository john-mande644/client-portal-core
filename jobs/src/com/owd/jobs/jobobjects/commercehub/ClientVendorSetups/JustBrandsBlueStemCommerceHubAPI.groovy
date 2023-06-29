package com.owd.jobs.jobobjects.commercehub.ClientVendorSetups

import com.owd.core.business.order.Order
import com.owd.core.business.order.OrderStatus
import com.owd.core.xml.OrderXMLDoc
import com.owd.jobs.jobobjects.commercehub.CommerceHubApiBase
import com.owd.jobs.jobobjects.commercehub.CommerceHubXmlFileFormatter

/**
 * Created by danny on 6/24/2019.
 */
class JustBrandsBlueStemCommerceHubAPI extends CommerceHubApiBase {
    {
        setUseUPCLookup(true)
        setGroupName("bluestem");
        setSendRejectOrder(true);
        inboundShipMap.put("UNSP", "UPS Ground");
        inboundShipMap.put("UPSN_CG", "UPS Ground");
        setBackOrderRule(OrderXMLDoc.kBackOrderAll);

        setTrimUPC(true);
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
        // invFileSource.setUseUPCLookup(true)
        invFileSource.setIncludeWarehouseBreakout(true)
        invFileSource.setIncludeVendorHeader(true);
        invFileSource.setVendorID("justbrand")
        invFileSource.setIncludeUnitCost(true)
        return invFileSource.getFileData();


    }

    @Override
    public String pushInventoryFile() {

        CommerceHubXmlFileFormatter invFileSource = new CommerceHubXmlFileFormatter(sftpUser, sFtpRemoteFolder, CommerceHubXmlFileFormatter.CommerceHubXmlFileType.INVENTORY)

        invFileSource.setIncludeUPCValueInInventory(true)
        invFileSource.setStockPercentage(1.00)
        invFileSource.setClientId(getClientId())
        // invFileSource.setUseUPCLookup(true)
        invFileSource.setIncludeWarehouseBreakout(true)
        invFileSource.setIncludeVendorHeader(true);
        invFileSource.setVendorID("justbrand")
        invFileSource.setIncludeUnitCost(true)

        // return invFileSource.getFileData()
        return sendFileToCommerceHub(invFileSource)

    }

    @Override
    public String getPackingSlipFromTemplateFields(def orderSrc) {

        String salesDivision = orderSrc.salesDivision.text();

        System.out.println(salesDivision);

        if (salesDivision.equalsIgnoreCase("Gettington")) {
            return "gettington";
        }
        if (salesDivision.equalsIgnoreCase("Fingerhut")) {
            return "fingerhut";
        }


        return "";
    }

    @Override
    void loadThirdPartyBillingInfo(Order order) {
        if (order.getShipMethodName().contains("UPS")) {
            order.setThirdPartyBilling("5551A8");
            order.setThirdPartyBillingContact("Blue Brands", "", "6250 RidgeWood Rd", "", "St Cloud", "MN", "56303", "3206543825");
        }

    }

    // case 888283 Inserts sku
    @Override
    void doFinalStuffBeforeSavingOrder(Order order, def orderSrc) {
        order.addInsertItemIfAvailable("ah-dropship-insert", 1)
    }

}
