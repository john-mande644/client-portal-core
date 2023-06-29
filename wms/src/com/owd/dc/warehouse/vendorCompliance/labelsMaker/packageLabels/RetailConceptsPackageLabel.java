package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels;

import com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels.spscommerce.*;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RetailConceptsPackageLabel extends vendorCompliancePackageLabelBase {
    String buyerPartNumber = "";
    String productSizeCode = "";
    String productColorCode = "";
    String merchantTypeCode = "";
    String addressLocationNumber = "";
    String department = "";
    String addressAlternateName = "";
    String consumerPackageCode = "";
    String vendorPartNumber = "";
    String locationCodeQualifier = "";

    public static void main(String[] args) {
        RetailConceptsPackageLabel label = new RetailConceptsPackageLabel();
        String orderId = "25412018";
        boolean saveToDisk = true;

        try {
            label.loadFromOrderId(orderId);

            List<String> sLabelData = label.getMultiLabelZPL(label.labelData);
            System.out.println("-------------------------------------------------");
            System.out.println("ZPL DATA");
            PrintWriter pw = new PrintWriter("c:\\labels\\" + orderId + ".txt");
            for(String lbl:sLabelData) {
                System.out.println("-------------------------------------------------");
                System.out.println(lbl);
                pw.println(lbl);
            }
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RetailConceptsPackageLabel()
    {
        supportsMultiLabel = true;

        itemsSQL = "SELECT\n" +
                "    dbo.package_line.pack_quantity,\n" +
                "    dbo.owd_inventory.upc_code as upc,\n" +
                "    dbo.owd_inventory.inventory_num as buyerPartNumber,\n" +
                "    dbo.owd_inventory.description as description\n" +
                "FROM\n" +
                "    dbo.package_line\n" +
                "INNER JOIN\n" +
                "    dbo.owd_line_item\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.package_line.owd_line_item_fkey = dbo.owd_line_item.line_item_id)\n" +
                "INNER JOIN\n" +
                "    dbo.owd_inventory\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_line_item.inventory_id = dbo.owd_inventory.inventory_id)\n" +
                "WHERE\n" +
                "    dbo.package_line.package_fkey = :packageFkey ;";
    }

    public void loadDataFrom850(packageLabelDataBean labelData) {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;

        try {
            builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(new ByteArrayInputStream(edi850.getBytes()));
            XPath xPath = XPathFactory.newInstance().newXPath();

            String vendorPartNumberEXP = "/Order/LineItems/LineItem/OrderLine[contains(ConsumerPackageCode,'"+labelData.getUpc()+"')]/VendorPartNumber";
            String buyerPartNumberEXP = "/Order/LineItems/LineItem/OrderLine[contains(ConsumerPackageCode,'"+labelData.getUpc()+"')]/BuyerPartNumber";
            String productSizeCodeEXP = "/Order/LineItems/LineItem/OrderLine[contains(ConsumerPackageCode,'"+labelData.getUpc()+"')]/ProductSizeCode";
            String productColorCodeEXP = "/Order/LineItems/LineItem/OrderLine[contains(ConsumerPackageCode,'"+labelData.getUpc()+"')]/ProductColorCode";
            String merchantTypeCodeEXP = "/Order/Header/Reference[contains(ReferenceQual,'MR')]/ReferenceID";
            String addressLocationNumberEXP = "/Order/Header/Address[contains(AddressTypeCode, 'ST')]/AddressLocationNumber";
            String addressAlternateNameEXP = "/Order/Header/Address[contains(AddressTypeCode, 'ST')]/AddressAlternateName";
            String departmentEXP = "/Order/Header/OrderHeader/Department";
            String locationCodeExp = "/Order/Header/Address[contains(AddressTypeCode, 'ST')]/LocationCodeQualifier";

            vendorPartNumber = xPath.compile(vendorPartNumberEXP).evaluate(document).trim();
            buyerPartNumber = xPath.compile(buyerPartNumberEXP).evaluate(document).trim();
            productSizeCode = xPath.compile(productSizeCodeEXP).evaluate(document).trim();
            productColorCode = xPath.compile(productColorCodeEXP).evaluate(document).trim();
            merchantTypeCode = xPath.compile(merchantTypeCodeEXP).evaluate(document).trim();
            addressLocationNumber = xPath.compile(addressLocationNumberEXP).evaluate(document).trim();
            department = xPath.compile(departmentEXP).evaluate(document).trim();
            addressAlternateName = xPath.compile(addressAlternateNameEXP).evaluate(document).trim();
            consumerPackageCode = labelData.getUpc();
            locationCodeQualifier = xPath.compile(locationCodeExp).evaluate(document).trim();

            if(merchantTypeCode == null || merchantTypeCode.length() == 0)
                merchantTypeCode = ".";

            if(department == null || department.length() == 0)
                department = ".";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getMultiLabelZPL(List<packageLabelDataBean> labelData)throws Exception{
        List<String> labels = new ArrayList();

        String appId = "x0lmo8Im1mk5Z7FKC2FY5OOE45ifRNmY";
        String appSecret = "_lfmwAaA9gihdwlWDsG17vrDRIHhC8-A5grWTYJiVibu-Xy9cgNjHi8cIJE-cOGS";

        SPSCommerceLabelApiClient sps = new SPSCommerceLabelApiClient(appId, appSecret);
        packageLabelDataBean firstLabel = labelData.get(0);
        loadDataFrom850(firstLabel);

        // header
        SPSCommerceHeader header = new SPSCommerceHeader();
        header.addresses = new ArrayList();
        header.carrierInformations = new ArrayList();

        SPSCommerceAddress shipTo = new SPSCommerceAddress();
        header.addresses.add(shipTo);
        shipTo.address1 = firstLabel.getShipToAddress();
        shipTo.address2 = firstLabel.getShipToAddress2();
        if(shipTo.address2.length() == 0)
            shipTo.address2 = ".";
        shipTo.addressName = firstLabel.getShipToName();
        shipTo.addressAlternateName = addressAlternateName;
        shipTo.addressTypeCode = SPSCommerceAddress.AddressTypeCode_ShipTo;
        shipTo.addressLocationNumber = addressLocationNumber;
        shipTo.city = firstLabel.getShipToCity();
        shipTo.postalCode = firstLabel.getShipToZip();
        shipTo.state = firstLabel.getShipToState();
        shipTo.locationCodeQualifier = locationCodeQualifier;
        //shipTo.country = "":

        SPSCommerceAddress shipFrom = new SPSCommerceAddress();
        header.addresses.add(shipFrom);
        shipFrom.address1 = firstLabel.getShipFromAddress();
        shipFrom.address2 = ".";
        shipFrom.addressName = firstLabel.getShipFromName();
        shipFrom.addressAlternateName = ""; TODO:
        shipFrom.addressTypeCode = SPSCommerceAddress.AddressTypeCode_ShipFrom;
        shipFrom.addressLocationNumber = "..";
        shipFrom.city = firstLabel.getShipFromCity();
        shipFrom.postalCode = firstLabel.getShipFromZip();
        shipFrom.state = firstLabel.getShipFromState();
        //shipFrom.country = "";

        SPSCommerceCarrierInformation carrier = new SPSCommerceCarrierInformation();
        header.carrierInformations.add(carrier);
        carrier.carrierAlphaCode = getScacCode(firstLabel.getCarrierName());
        if(firstLabel.getCarrierName().equals("LTL")) {
            carrier.carrierRouting = "CALL FOR ROUTING";
        } else {
            carrier.carrierRouting = ".";
        }


        //header.shipmentIdentification = labelData.;
        header.billOfLadingNumber = firstLabel.getPurchaseOrder();

        header.carrierProNumber = firstLabel.getTrackingNumber();
        if(header.carrierProNumber == null || header.carrierProNumber.length() == 0)
            header.carrierProNumber = ".";

        header.purchaseOrderNumber = firstLabel.getPurchaseOrder();
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        header.shipDate = dateFormat.format(now);
        header.department = department;
        header.references = new ArrayList();
        SPSCommerceReference reference = new SPSCommerceReference();
        header.references.add(reference);
        reference.referenceQualifier = SPSCommerceReference.ReferenceQualifier_MerchandiseTypeCode;
        reference.referenceId = merchantTypeCode;


        // packs

        List<SPSCommercePack> packs = new ArrayList();

        for(packageLabelDataBean ld:labelData) {

            loadDataFrom850(ld);

            SPSCommercePack pack1 = new SPSCommercePack();
            packs.add(pack1);

            pack1.packLevelType = SPSCommercePack.PackLevelType_Pack;
            pack1.marksAndNumbersCollections = new ArrayList();
            SPSCommerceMarksAndNumbersCollection marksAndNumbers = new SPSCommerceMarksAndNumbersCollection();
            pack1.marksAndNumbersCollections.add(marksAndNumbers);
            marksAndNumbers.marksAndNumbersQualifier1 = SPSCommerceMarksAndNumbersCollection.MarksAndNumbersQualifier_UPCShippingContainerCode;
            marksAndNumbers.marksAndNumbers1 = ld.getCartonNum();

            pack1.shippingSerialId = String.format("%1$20s", ld.getSSCC()).replace(' ', '0');

            pack1.items = new ArrayList();

            SPSCommerceItem item1 = new SPSCommerceItem();
            pack1.items.add(item1);

            item1.buyerPartNumber = buyerPartNumber;
            item1.shipQuantity = Double.parseDouble(ld.getQty());
            item1.productSizeDescription = productSizeCode;
            item1.productColorDescription = productColorCode;
            item1.consumerPackageCode = consumerPackageCode;
            item1.vendorPartNumber = vendorPartNumber;
        }

        SPSCommerceZPLData zpl = sps.renderZPL(SPSCommerceLabelApiClient.LabelId_RetailConcepts, header, packs);

        for(String label: zpl.zplData)
            labels.add(label);

        return labels;
    }

    @Override
    public String getLabelZPL(packageLabelDataBean labelData) throws Exception {

        return "";

    }

    private String getScacCode(String shipMethod) {
        switch (shipMethod)
        {
            case "UPS Ground":
                return "UPSC";
        }

        return shipMethod;
    }
}
