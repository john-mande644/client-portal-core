package com.owd.jobs.jobobjects.commercehub.ClientVendorSetups

import com.owd.core.business.order.Order
import com.owd.core.business.order.OrderStatus
import com.owd.core.xml.OrderXMLDoc
import com.owd.jobs.jobobjects.commercehub.CommerceHubApiBase
import com.owd.jobs.jobobjects.commercehub.CommerceHubXmlFileFormatter
import com.owd.jobs.jobobjects.commercehub.LowesCommerceHubXmlFileFormatter

class JustBrandsLowesCommerceHubAPI extends CommerceHubApiBase{

    //Override any fields needed for setup in initializer block
    {
        setUseUPCLookup(false)
        setUseVendorSku(true)
        setGroupName("Lowes");
        setPackingSlipTemplate("lowes");
        setSendRejectOrder(true);
        setSendAcknowledgement(true);
        setBackOrderRule(OrderXMLDoc.kBackOrderAll);
    }

    @Override
    public String confirmOrderShipment(OrderStatus os) {

        LowesCommerceHubXmlFileFormatter confirmFileSource = new LowesCommerceHubXmlFileFormatter(sftpUser, sFtpRemoteFolder, LowesCommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED)
        confirmFileSource.setkIncludeShipFrom(true)
        confirmFileSource.setCurrentOrderStatus(os)
        confirmFileSource.setClientId(getClientId())
        confirmFileSource.setIncludeSsccOnDropship(true);

        return sendFileToCommerceHub(confirmFileSource)

    }

    @Override
    public String getInventoryFile(){
        // Override to add UseUPCLookup
        LowesCommerceHubXmlFileFormatter invFileSource = new LowesCommerceHubXmlFileFormatter(sftpUser, sFtpRemoteFolder, LowesCommerceHubXmlFileFormatter.CommerceHubXmlFileType.INVENTORY)

        invFileSource.setIncludeVendorHeader(true)
        invFileSource.setVendorID("justbrand")
        invFileSource.setIncludeUPCValueInInventory(true)
        invFileSource.setStockPercentage(1.00)
        invFileSource.setClientId(getClientId())
        invFileSource.setIncludeWarehouseBreakout(true)

        return invFileSource.getFileData();
    }
    @Override
    public String pushInventoryFile() {

        LowesCommerceHubXmlFileFormatter invFileSource = new LowesCommerceHubXmlFileFormatter(sftpUser, sFtpRemoteFolder, LowesCommerceHubXmlFileFormatter.CommerceHubXmlFileType.INVENTORY)

        invFileSource.setIncludeVendorHeader(true)
        invFileSource.setVendorID("justbrand")
        invFileSource.setIncludeUPCValueInInventory(true)
        invFileSource.setStockPercentage(1.00)
        invFileSource.setClientId(getClientId())
        invFileSource.setIncludeWarehouseBreakout(true)
        return sendFileToCommerceHub(invFileSource)

    }

    @Override
    String getShipMethod(String shipMethod) {
        Map<String, String> privateShipMethods = new HashMap();

        privateShipMethods.put("UNSP", "FedEx Ground");
        privateShipMethods.put("UPSN","FedEx Ground");
        privateShipMethods.put("MCC","FedEx Ground");
        privateShipMethods.put("UNSP_CG","FedEx Ground");
        privateShipMethods.put("UNSG", "FedEx Ground");
        privateShipMethods.put("UG", "FedEx Ground");

        if (privateShipMethods.containsKey(shipMethod)) {
            return privateShipMethods.get(shipMethod);
        }

        return "FedEx Ground";
    }

    @Override
    void loadThirdPartyBillingInfo(Order order){
        if (order.getShipMethodName().contains("FedEx")) {
            order.setThirdPartyBilling("214586223");
            order.setThirdPartyBillingContact(" Transactional Accounting - FAS99","Lowe's Companies Inc.","1000 Lowe's Blvd","","Mooresville","NC","28117","6058457172");
        }
    }

    @Override
    void doFinalStuffBeforeSavingOrder(Order order, def orderSrc){
    }

    @Override
    String acknowledgeOrder(OrderStatus os,String batchRef) {

        String orderXml = os.getTagMap().get("COMMERCEHUB_PO_XML");
        Object order = new XmlSlurper().parseText(orderXml);

        String expectedShipDate = "";

        order.lineItem.each() { item ->
            if(expectedShipDate.length() == 0)
                expectedShipDate = item.expectedShipDate;
        }

        LowesCommerceHubXmlFileFormatter confirmFileSource = new LowesCommerceHubXmlFileFormatter(sftpUser, sFtpRemoteFolder, LowesCommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_RECEIVED)
        confirmFileSource.setBatchRef(batchRef)
        confirmFileSource.setUseUPCLookup(useUPCLookup);
        confirmFileSource.setCurrentOrderStatus(os)
        confirmFileSource.setClientId(getClientId())
        confirmFileSource.setExpectedShipDate(expectedShipDate)

        //return confirmFileSource.getFileData()

        return sendFileToCommerceHub(confirmFileSource)

    }

    @Override
    public String rejectOrder(orderSrc, String BatchRef){
        CommerceHubXmlFileFormatter confirmFileSource =  new CommerceHubXmlFileFormatter(sftpUser, sFtpRemoteFolder, CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_REJECTED);
        //OrderStatus os = new OrderStatus(orderId+"");
        confirmFileSource.setBatchRef(BatchRef);
        confirmFileSource.setOrderSrc(orderSrc);
        confirmFileSource.setClientId(getClientId());
        //return confirmFileSource.getFileData();
        return sendFileToCommerceHub(confirmFileSource);

    }


}
