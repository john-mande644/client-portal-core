package com.owd.jobs.jobobjects.commercehub.ClientVendorSetups

import com.owd.core.business.order.Order
import com.owd.core.business.order.OrderStatus
import com.owd.core.xml.OrderXMLDoc
import com.owd.jobs.jobobjects.commercehub.CommerceHubApiBase
import com.owd.jobs.jobobjects.commercehub.CommerceHubXmlFileFormatter

/**
 * Created by danny on 6/24/2019.
 */
class JustBrandsHomeDepotCommerceHubAPI extends CommerceHubApiBase{

    //Override any fields needed for setup in initializer block
    {
        setUseUPCLookup(true)
        setGroupName("HomeDepot");
        setPackingSlipTemplate("homedepotchub");
        setSendRejectOrder(true);
        inboundShipMap.put("UPSN","UPS Ground");
        inboundShipMap.put("MCC","UPS Ground");
        inboundShipMap.put("UNSP_CG","UPS Ground");
        inboundShipMap.put("UNSP_ND","UPS Next Day Air");
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
    public String getInventoryFile(){
        // Override to add UseUPCLookup
        CommerceHubXmlFileFormatter invFileSource = new CommerceHubXmlFileFormatter(sftpUser, sFtpRemoteFolder, CommerceHubXmlFileFormatter.CommerceHubXmlFileType.INVENTORY)

        invFileSource.setIncludeUPCValueInInventory(true)
        invFileSource.setStockPercentage(1.00)
        invFileSource.setClientId(getClientId())
       // invFileSource.setUseUPCLookup(true)
      //  invFileSource.setIncludeWarehouseBreakout(true)
        return invFileSource.getFileData();


    }
    @Override
    public String pushInventoryFile() {

        CommerceHubXmlFileFormatter invFileSource = new CommerceHubXmlFileFormatter(sftpUser, sFtpRemoteFolder, CommerceHubXmlFileFormatter.CommerceHubXmlFileType.INVENTORY)

        invFileSource.setIncludeUPCValueInInventory(true)
        invFileSource.setStockPercentage(1.00)
        invFileSource.setClientId(getClientId())
       // invFileSource.setUseUPCLookup(true)
      //  invFileSource.setIncludeWarehouseBreakout(true)

        // return invFileSource.getFileData()
        return sendFileToCommerceHub(invFileSource)

    }

    @Override
    void loadThirdPartyBillingInfo(Order order){
        order.setThirdPartyBilling("975W8X");
        order.setThirdPartyBillingContact(" Home Depot Headquarters","","2455 Paces Ferry Rd.","","Atlanta","GA","30339","6058457172");


    }

    // case 888283 Inserts sku
    @Override
    void doFinalStuffBeforeSavingOrder(Order order, def orderSrc){
        order.addInsertItemIfAvailable("ah-dropship-insert", 1)
    }


}
