package com.owd.jobs.jobobjects.commercehub.ClientVendorSetups

import com.owd.core.business.order.Order
import com.owd.core.business.order.OrderStatus
import com.owd.core.xml.OrderXMLDoc
import com.owd.jobs.jobobjects.commercehub.CommerceHubApiBase
import com.owd.jobs.jobobjects.commercehub.CommerceHubXmlFileFormatter

/**
 * Created by danny on 6/24/2019.
 */
class JustBrandsDicksCommerceHubAPI extends CommerceHubApiBase{

    //Override any fields needed for setup in initializer block
    {
        setUseUPCLookup(true)
        setGroupName("Dicks");
        setPackingSlipTemplate("dickschub");
        setSendRejectOrder(true);
        inboundShipMap.put("UNSP_SE","FedEx 2Day")
        inboundShipMap.put("UNSP_ND","FedEx Standard Overnight")
        inboundShipMap.put("UNSP_CG","FedEx Home Delivery")

        setBackOrderRule(OrderXMLDoc.kBackOrderAll)

    }
    @Override
    void loadThirdPartyBillingInfo(Order order){
        order.setThirdPartyBilling("716047598");
        order.setThirdPartyBillingContact("DICK'S Sporting Goods","DICK'S Sporting Goods","345 Court Street","","Coraopolis","PA","15108","6058457172");


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
        invFileSource.setUseUPCLookup(true)
      //  invFileSource.setIncludeWarehouseBreakout(true)
        return invFileSource.getFileData();


    }
    @Override
    public String pushInventoryFile() {

        CommerceHubXmlFileFormatter invFileSource = new CommerceHubXmlFileFormatter(sftpUser, sFtpRemoteFolder, CommerceHubXmlFileFormatter.CommerceHubXmlFileType.INVENTORY)

        invFileSource.setIncludeUPCValueInInventory(true)
        invFileSource.setStockPercentage(1.00)
        invFileSource.setClientId(getClientId())
        invFileSource.setUseUPCLookup(true)
        // DSG does not accept warehouse breakouts
      //  invFileSource.setIncludeWarehouseBreakout(true)

        // return invFileSource.getFileData()
        return sendFileToCommerceHub(invFileSource)

    }

    // case 888283 Inserts sku
    @Override
    void doFinalStuffBeforeSavingOrder(Order order, def orderSrc){
        order.addInsertItemIfAvailable("ah-dropship-insert", 1)
    }

}
