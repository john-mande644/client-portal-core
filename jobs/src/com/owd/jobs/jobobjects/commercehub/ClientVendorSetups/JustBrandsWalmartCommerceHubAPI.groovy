package com.owd.jobs.jobobjects.commercehub.ClientVendorSetups

import com.owd.core.TagUtilities
import com.owd.core.business.order.Order
import com.owd.core.business.order.OrderStatus
import com.owd.core.managers.ManifestingManager
import com.owd.core.xml.OrderXMLDoc
import com.owd.jobs.jobobjects.commercehub.CommerceHubApiBase
import com.owd.jobs.jobobjects.commercehub.CommerceHubXmlFileFormatter

/**
 * Created by danny on 6/24/2019.
 */
class JustBrandsWalmartCommerceHubAPI extends CommerceHubApiBase{

    //Override any fields needed for setup in initializer block
    {
        setUseUPCLookup(true)
        setSendAcknowledgement(true)
        setGroupName("walmart");

        setSendRejectOrder(true);
        inboundShipMap.put("UNSP","UPS Ground");
        inboundShipMap.put("UPSN_CG","UPS Ground");
        inboundShipMap.put("UPSN_FC","UPS Ground");
        inboundShipMap.put("FEDX_NM_R5", "FedEx Priority Overnight");

        setTrimUPC(true);
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
        invFileSource.setIncludeWarehouseBreakout(true)
        invFileSource.setIncludeVendorHeader(true);
        invFileSource.setVendorID("walmart")
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
        invFileSource.setVendorID("walmart")
        invFileSource.setIncludeUnitCost(true)

        // return invFileSource.getFileData()
        return sendFileToCommerceHub(invFileSource)

    }

    @Override
    public String getPackingSlipFromTemplateFields(def orderSrc){

        String merchType = orderSrc.poHdrData?.merchandiseTypeCode;



        if(merchType.equals("D2S")){
            return "walmartd2s";
        }else{
            return "walmart"
        }

    }

    @Override
    void loadThirdPartyBillingInfo(Order order){
        if(order.getShipMethodName().contains("UPS")) {
            order.setThirdPartyBilling("3Y372R");
        }
        if(order.getShipMethodName().contains("FedEx")) {
            order.setThirdPartyBilling("794146896");
        }
    }

    @Override
    void doFinalStuffBeforeSavingOrder(Order order, def orderSrc){
        order.companyOverride = "Walmart.com/ Jet.com";
        order.nameOverride = "Walmart.com/ Jet.com";
        order.address1Override="2301 Corporation Parkway";
        order.cityOverride="Waco";
        order.stateOverride = "TX";
        order.zipOverride = "76712";
        order.phoneOverride = "800-966-6546";
        order.getShippingContact().phone = "(000)-000-0000"

        String sscc = String.format("%019d",new BigInteger("68904"+order.po_num));
        sscc= sscc+ ManifestingManager.getSSCCCheckDigit(sscc);

        order.addTag("SSCC",sscc);

        String merchType = orderSrc.poHdrData?.merchandiseTypeCode;



        if(merchType.equals("D2S")){
            order.setGroupName("walmartd2s");
            order.addTag(TagUtilities.kVendorComplianceIDReference, "8");
        }

        // case 888283 Inserts sku
        order.addInsertItemIfAvailable("ah-dropship-insert", 1)
    }
}
