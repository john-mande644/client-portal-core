package com.owd.jobs.jobobjects.commercehub.ClientVendorSetups

import com.owd.core.business.order.Order
import com.owd.core.business.order.OrderStatus
import com.owd.core.xml.OrderXMLDoc
import com.owd.jobs.jobobjects.commercehub.CommerceHubApiBase
import com.owd.jobs.jobobjects.commercehub.CommerceHubXmlFileFormatter

/**
 * Created by danny on 6/24/2019.
 */
class JustBrandsBedBathCommerceHubAPI extends CommerceHubApiBase {
    {
        setUseUPCLookup(true)
        setGroupName("bedbathbeyond");
        setPackingSlipTemplate("bedbathbeyondchub");
        setSendRejectOrder(true);
        setBackOrderRule(OrderXMLDoc.kBackOrderAll);
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

        return invFileSource.getFileData();
    }

    @Override
    public String pushInventoryFile() {
        CommerceHubXmlFileFormatter invFileSource = new CommerceHubXmlFileFormatter(sftpUser, sFtpRemoteFolder, CommerceHubXmlFileFormatter.CommerceHubXmlFileType.INVENTORY)

        invFileSource.setIncludeUPCValueInInventory(true)
        invFileSource.setStockPercentage(1.00)
        invFileSource.setClientId(getClientId())

        return sendFileToCommerceHub(invFileSource)
    }

    @Override
    public String getPackingSlipFromTemplateFields(def orderSrc) {
        String salesDivision = orderSrc.salesDivision.text();

        System.out.println(salesDivision);

        if (salesDivision.equalsIgnoreCase("BBB")) {
            return "bedbathbeyondchub";
        }
        if (salesDivision.equalsIgnoreCase("BAB")) {
            return "buybuybabychub";
        }

        return "";
    }

    @Override
    void loadThirdPartyBillingInfo(Order order) {
        if (order.getShipMethodName().contains("FedEx")) {
            order.setThirdPartyBilling("910999869");
            order.setThirdPartyBillingContact("Bed Bath & Beyond", "", "1001 W. Middlesex Road", "", "Port Reading", "NJ", "07064", "6058457172");
        }
    }

    // case 888283 Inserts sku
    @Override
    void doFinalStuffBeforeSavingOrder(Order order, def orderSrc) {
        order.addInsertItemIfAvailable("ah-dropship-insert", 1)
    }
}
