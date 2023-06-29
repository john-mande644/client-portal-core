package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels;

import com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels.spscommerce.*;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ZapposPackageLabel  extends vendorCompliancePackageLabelBase {

    public static void main(String[] args) {
        System.setProperty("com.owd.environment", "test");
        ZapposPackageLabel label = new ZapposPackageLabel();

        try {
            label.loadFromOrderId("22094846");
            List<String> sLabelData = label.getMultiLabelZPL(label.labelData);
            System.out.println("-------------------------------------------------");
            System.out.println("ZPL DATA");
            for(String lbl:sLabelData) {
                System.out.println("-------------------------------------------------");
                System.out.println(lbl);
            }

            System.out.println();
            System.out.println();
            System.out.println("-------------------------------------------------");
            System.out.println("XML DATA");
            System.out.println("-------------------------------------------------");
            String xml = label.getXml();
            System.out.println(xml);
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ZapposPackageLabel()
    {
        supportsMultiLabel = true;

        itemsSQL = "SELECT\n" +
                "    dbo.package_line.pack_quantity,\n" +
                "    dbo.owd_inventory.upc_code as upc,\n" +
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

    @Override
    public List<String> getMultiLabelZPL(List<packageLabelDataBean> labelData)throws Exception{

        List<String> labels = new ArrayList();

        /*String appId = "bPbvj76FSbGPfpFG5KO8L2TU95HyURyt"; // TODO: remove
        String appSecret = "i4mn8AueMHvEwemf2jP189RBUw12Ay1S3oMI9KD1x2kZRhUMC1AKfCf4HCkI5by8";*/

        String appId = "x0lmo8Im1mk5Z7FKC2FY5OOE45ifRNmY";
        String appSecret = "_lfmwAaA9gihdwlWDsG17vrDRIHhC8-A5grWTYJiVibu-Xy9cgNjHi8cIJE-cOGS";

        SPSCommerceLabelApiClient sps = new SPSCommerceLabelApiClient(appId, appSecret);

        packageLabelDataBean firstLabel = labelData.get(0);

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
        shipTo.addressAlternateName = ".";
        shipTo.addressTypeCode = SPSCommerceAddress.AddressTypeCode_ShipTo;
        shipTo.city = firstLabel.getShipToCity();
        shipTo.postalCode = firstLabel.getShipToZip();
        shipTo.state = firstLabel.getShipToState();
        //shipTo.country = "":

        SPSCommerceAddress shipFrom = new SPSCommerceAddress();
        header.addresses.add(shipFrom);
        shipFrom.address1 = firstLabel.getShipFromAddress();
        shipFrom.address2 = ".";
        shipFrom.addressName = firstLabel.getShipFromName();
        shipFrom.addressAlternateName = ""; TODO:
        shipFrom.addressTypeCode = SPSCommerceAddress.AddressTypeCode_ShipFrom;
        shipFrom.city = firstLabel.getShipFromCity();
        shipFrom.postalCode = firstLabel.getShipFromZip();
        shipFrom.state = firstLabel.getShipFromState();
        //shipFrom.country = "";

        SPSCommerceCarrierInformation carrier = new SPSCommerceCarrierInformation();
        header.carrierInformations.add(carrier);
        carrier.carrierAlphaCode = String.format("%1$4s", firstLabel.getCarrierName()); //SPSCommerceCarrierInformation.CarrierTransMethodCodes_FedExGround;
        if(firstLabel.getCarrierName().equals("LTL"))
            carrier.carrierRouting = "CALL FOR ROUTING";

        //header.shipmentIdentification = labelData.;
        //header.billOfLadingNumber = "123456";

        //header.carrierProNumber = "abc123";
        header.purchaseOrderNumber = firstLabel.getPurchaseOrder();
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        header.shipDate = dateFormat.format(now);
        header.department = ".";

        // packs

        List<SPSCommercePack> packs = new ArrayList();

        for(packageLabelDataBean ld:labelData) {


            SPSCommercePack pack1 = new SPSCommercePack();
            packs.add(pack1);

            pack1.shippingSerialId = String.format("%1$20s", ld.getSSCC()).replace(' ', '0');

            pack1.items = new ArrayList();

            SPSCommerceItem item1 = new SPSCommerceItem();
            pack1.items.add(item1);

            item1.productOrItemDescriptions = new ArrayList();

            SPSCommerceProductOrItemDescription pd1 = new SPSCommerceProductOrItemDescription();
            item1.productOrItemDescriptions.add(pd1);
            pd1.productCharacteristicCode = SPSCommerceProductOrItemDescription.ProductCharacteristicCode_ProductDescription;
            pd1.productDescription = ld.getDescription();
            if(pd1.productDescription.length() == 0)
                pd1.productDescription = ".";

            if (ld.getMultiSku())
                item1.consumerPackageCode = "MULTI SKU";
            else
                item1.consumerPackageCode = ld.getUpc();

            item1.shipQuantity = Double.parseDouble(ld.getQty());

            //item1.productSizeCode =
            //item1.productSizeDescription = "Very large item.";
            //item1.productColorDescription = "Reddish in color";
        }

        SPSCommerceZPLData zpl = sps.renderZPL(SPSCommerceLabelApiClient.LabelId_Zappos, header, packs);

        for(String label: zpl.zplData)
            labels.add(label);

        return labels;
    }

    @Override
    public String getLabelZPL(packageLabelDataBean labelData) throws Exception {

        return "";

    }

}
