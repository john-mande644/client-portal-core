package com.owd.jobs.jobobjects.commercehub.ClientVendorSetups

import com.owd.core.business.order.Order
import com.owd.core.xml.OrderXMLDoc
import com.owd.jobs.jobobjects.commercehub.CommerceHubApiBase
import com.owd.jobs.jobobjects.commercehub.CommerceHubXmlFileFormatter

/**
 * Created by danny on 6/24/2019.
 */
class JustBrandsQVCCommerceHubAPI extends CommerceHubApiBase{

    //Override any fields needed for setup in initializer block
    {
        setUseVendorSku(true)
        setGroupName("qvc");
        setPackingSlipTemplate("qvc");
        setSendRejectOrder(true);
        setBackOrderRule(OrderXMLDoc.kBackOrderAll);
        inboundShipMap.put("UPSN_CG", "UPS Ground");

    }

    @Override
    public String getInventoryFile(){
        // Override to add UseUPCLookup
        CommerceHubXmlFileFormatter invFileSource = new CommerceHubXmlFileFormatter(sftpUser, sFtpRemoteFolder, CommerceHubXmlFileFormatter.CommerceHubXmlFileType.INVENTORY)

        invFileSource.setStockPercentage(1.00)
        invFileSource.setClientId(getClientId())
       // invFileSource.setUseUPCLookup(true)
        invFileSource.setIncludeWarehouseBreakout(true)
        return invFileSource.getFileData();


    }

    @Override
    public String pushInventoryFile() {

        CommerceHubXmlFileFormatter invFileSource = new CommerceHubXmlFileFormatter(sftpUser, sFtpRemoteFolder, CommerceHubXmlFileFormatter.CommerceHubXmlFileType.INVENTORY)

        invFileSource.setStockPercentage(1.00)
        invFileSource.setClientId(getClientId())
       // invFileSource.setUseUPCLookup(true)
        invFileSource.setIncludeWarehouseBreakout(true)

        // return invFileSource.getFileData()
        return sendFileToCommerceHub(invFileSource)

    }
    @Override
    void doFinalStuffBeforeSavingOrder(Order order, def orderSrc){
        order.companyOverride = "QVC Inc";
        order.nameOverride = "QVC Inc";
        order.address1Override="1000 Stony Battery Road";
        order.cityOverride="Lancaseter";
        order.stateOverride = "PA";
        order.zipOverride = "17699";
        order.phoneOverride = "888-345-5788";
        order.getShippingContact().phone = "(000)-000-0000"

        // case 888283 Inserts sku
        order.addInsertItemIfAvailable("ah-dropship-insert", 1)
    }



}
