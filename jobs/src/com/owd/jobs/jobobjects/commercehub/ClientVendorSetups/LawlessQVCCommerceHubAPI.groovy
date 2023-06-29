package com.owd.jobs.jobobjects.commercehub.ClientVendorSetups

import com.owd.core.business.order.Order
import com.owd.core.xml.OrderXMLDoc
import com.owd.jobs.jobobjects.commercehub.CommerceHubApiBase
import com.owd.jobs.jobobjects.commercehub.CommerceHubXmlFileFormatter

class LawlessQVCCommerceHubAPI extends CommerceHubApiBase{

    //Override any fields needed for setup in initializer block
    {
        setUseUPCLookup(true)
        //setUseVendorSku(true)
        setGroupName("iqvc");
        setPackingSlipTemplate("qvc");
        setSendRejectOrder(true);
        //  setBackOrderRule(OrderXMLDoc.kPartialShip);
        inboundShipMap.put("UPSN_CG", "UPS Ground");
        inboundShipMap.put("USPSB_FC", "USPS First-Class Mail")

    }

    @Override
    protected String getFacilityCode(int clientId) {
        return "DC1";
    }

    @Override
    public String getInventoryFile(){
        // Override to add UseUPCLookup
        CommerceHubXmlFileFormatter invFileSource = new CommerceHubXmlFileFormatter(sftpUser, sFtpRemoteFolder, CommerceHubXmlFileFormatter.CommerceHubXmlFileType.INVENTORY)

        invFileSource.setStockPercentage(1.00)
        invFileSource.setClientId(getClientId())
        // invFileSource.setUseUPCLookup(true)
        invFileSource.setIncludeUPCValueInInventory(true)
        invFileSource.setIncludeWarehouseBreakout(true)
        return invFileSource.getFileData();


    }

    @Override
    public String pushInventoryFile() {

        CommerceHubXmlFileFormatter invFileSource = new CommerceHubXmlFileFormatter(sftpUser, sFtpRemoteFolder, CommerceHubXmlFileFormatter.CommerceHubXmlFileType.INVENTORY)

        invFileSource.setStockPercentage(1.00)
        invFileSource.setClientId(getClientId())
        // invFileSource.setUseUPCLookup(true)
        invFileSource.setIncludeUPCValueInInventory(true)
        invFileSource.setIncludeWarehouseBreakout(true)

        // return invFileSource.getFileData()
        return sendFileToCommerceHub(invFileSource)

    }
    @Override
    void doFinalStuffBeforeSavingOrder(Order order, def orderSrc){
        order.companyOverride = "QVC";
        order.nameOverride = "QVC";
        order.address1Override="1000 Stony Battery Road";
        order.cityOverride="Lancaseter";
        order.stateOverride = "PA";
        order.zipOverride = "17699";
        order.phoneOverride = "888-345-5788";
        order.getShippingContact().phone = "(000)-000-0000"

        // case 888283 Inserts sku
        //order.addInsertItemIfAvailable("ah-dropship-insert", 1)
    }

    @Override
    void loadThirdPartyBillingInfo(Order order){
        order.setThirdPartyBilling("055865");
        order.setThirdPartyBillingContact(" QVC Inc.","","1200 Wilson Drive","","West Chester","PA","19380","4847012002");


    }



}
