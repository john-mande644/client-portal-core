package com.owd.jobs.jobobjects.commercehub.ClientVendorSetups

import com.owd.core.business.order.Order
import com.owd.core.business.order.OrderStatus
import com.owd.core.xml.OrderXMLDoc
import com.owd.jobs.jobobjects.commercehub.CommerceHubApiBase
import com.owd.jobs.jobobjects.commercehub.CommerceHubXmlFileFormatter

/**
 * Created by danny on 6/24/2019.
 */
class BeautyCropMacysCommerceHubAPI extends CommerceHubApiBase {
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
        invFileSource.setStockPercentage(1.00)
        invFileSource.setClientId(getClientId())

        return sendFileToCommerceHub(invFileSource)
    }

    @Override
    public String getPackingSlipFromTemplateFields(def orderSrc) {
        return "macys";
    }

    @Override
    void loadThirdPartyBillingInfo(Order order) {
        if (order.getShipMethodName().contains("UPS")) {
            order.setThirdPartyBilling("V9E679");
            order.setThirdPartyBillingContact("Macy's Accounts Payable", "", "145 Progress Place", "", "Springdale", "OH", "45246", "");
        }
    }

    @Override
    void doFinalStuffBeforeSavingOrder(Order order, def orderSrc) {
    }
}
