package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels;

import com.owd.core.business.Address;
import com.owd.core.business.Contact;
import com.owd.core.business.order.OrderStatus;
import com.owd.core.business.order.ShippingInfo;
import com.owd.dc.manifest.api.internal.returnAddressBean;
import com.owd.dc.packing.vendorCompliance.vendorComplianceUtils;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Criteria;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by danny on 10/20/2016.
 */
public class vendorCompliancePackageLabelBase  implements vendorComplaincePackageLabelInterface{

    List<packageLabelDataBean> labelData = new ArrayList<packageLabelDataBean>();
    String edi850 = "";
    String commereHubData = "";

    public Boolean supportsMultiLabel = false;


    public String getLabelZPL(packageLabelDataBean labelData)throws Exception{
        throw new Exception("You need to overide this mehtod for you particular labels");

    }

    public List<String> getMultiLabelZPL(List<packageLabelDataBean> labelData)throws Exception{
        return new ArrayList();
    }


    public String getXml() throws Exception{
        StringBuilder sb = new StringBuilder();

        if(supportsMultiLabel){
            return getMultiLabelXml();
        }

        sb.append("<packageLabels><labels>");
        for(packageLabelDataBean ld:labelData){
            sb.append("<label>");
            sb.append(org.apache.commons.codec.binary.Base64.encodeBase64String(getLabelZPL(ld).getBytes()));
            sb.append("</label>");

        }
        sb.append("</labels>");
        sb.append("</packageLabels>");

        return sb.toString();

    }

    public String getMultiLabelXml() throws Exception{
        StringBuilder sb = new StringBuilder();

        List<String> labels = getMultiLabelZPL(labelData);

        sb.append("<packageLabels><labels>");
        for(String ld:labels){
            sb.append("<label>");
            sb.append(org.apache.commons.codec.binary.Base64.encodeBase64String(ld.getBytes()));
            sb.append("</label>");
        }
        sb.append("</labels>");
        sb.append("</packageLabels>");

        return sb.toString();

    }

    public static void main (String[] arg){
        try{
            vendorCompliancePackageLabelBase vbase = new vendorCompliancePackageLabelBase();
            vbase.loadFromOrderId("11192238");
            System.out.println(vbase.labelData.size());
            System.out.println(vbase.labelData.get(0).getShipToAddress());
            System.out.println(vbase.labelData.get(0).getShipToName());
            System.out.println(vbase.labelData.get(0).getUpc());
            System.out.println(vbase.labelData.get(0).getSSCC());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void loadFromOrderId(String orderId) throws Exception{

        if(orderId.length()<2) throw new Exception("Please provide a vaild order Id");
        try{
            OrderStatus ostatus = new OrderStatus(orderId);
            returnAddressBean returnAddress = vendorComplianceUtils.getDefaultReturnAddress(ostatus.getLocation());
            System.out.println(ostatus.getStatusString());
            List packages = vendorComplianceUtils.getPackageInfoForLabelId(orderId);
            System.out.println("Packages: "+ packages.size());
            if(packages.size()==0){
                throw new Exception("Unable to lookup packages for order Id");
            }

            int i =1 ;
           edi850 = vendorComplianceUtils.edi850FromOrderID(orderId);
           commereHubData = vendorComplianceUtils.CommereHubDataFromOrderID(orderId);
            for (Object row : packages) {

                System.out.println("9");
                Map data = (Map) row;
                packageLabelDataBean ldb = new packageLabelDataBean();
                ldb.setShipFromName("OWD");
                ldb.setShipFromAddress(returnAddress.getAddress1());

                ldb.setShipFromCity(returnAddress.getCity());
                ldb.setShipFromState(returnAddress.getState());
                ldb.setShipFromZip(returnAddress.getZip());
                ldb.setOrderReference(ostatus.orderReference);
                ldb.setClientId(Integer.parseInt(ostatus.client_id));
                ldb.setWeight(data.get("weight_lbs").toString());

               ShippingInfo shipping = ostatus.shipping;
                System.out.println("10");
                ldb.setCarrierName(shipping.carr_service);
                System.out.println("11");
                Contact contact = ostatus.shipping.shipContact;
                System.out.println("12");
                Address address = ostatus.shipping.shipAddress;

                if(address.getCompany_name().equals(".")){
                    ldb.setShipToName(contact.getName());
                }else{
                    ldb.setShipToName(address.getCompany_name());
                }

                ldb.setShipToAddress(address.getAddress_one());
                ldb.setShipToAddress2(address.getAddress_two());
                ldb.setShipToCity(address.getCity());
                ldb.setShipToState(address.getState());
                ldb.setShipToZip(address.getZip().substring(0,5));
                System.out.println("12");
                ldb.setPurchaseOrder(ostatus.po_num);
                ldb.setCartonNum(i+"");
                System.out.println("13");
                ldb.setCartonOf(data.get("num_packs").toString());
                ldb.setSSCC(data.get("SSCC").toString());
                ldb.setProcessingDate(data.get("processDate").toString());
                System.out.println("14");
                System.out.println(ostatus.warehouse_status);
                if(ostatus.getStatusString().contains("Warehouse")){
                    System.out.println("ww");
                }else{
                    try {
                        ldb.setTrackingNumber(data.get("tracking_no").toString());
                    }catch(Exception e){
                        System.out.println("Null tracking: ignore");
                    }
                }
                System.out.println("15");

                String vcid = vendorComplianceUtils.getVCIdForOrderId(orderId);

                System.out.println(data.get("id").toString());
                List items = getItemInfoForPackageId(data.get("id").toString(),vcid);


                Map m = (Map) items.get(0);
                ldb.setUpc(m.get("upc").toString());
                try {
                    ldb.setBuyerPartNumber(m.get("buyerPartNumber").toString());
                } catch (NullPointerException ne) {
                    // swallow expected exception
                    System.out.println("Integration does not use buyer part number.");
                }
                ldb.setQty(m.get("pack_quantity").toString());
                ldb.setDescription(m.get("description").toString());
                System.out.println("Item size: "+items.size());

                if(items.size()>1){
                    int packQty = 0;
                    for(Object theItem : items){
                     Map mm = (Map) theItem;
                        packQty += Integer.parseInt(mm.get("pack_quantity").toString());
                    }
                    ldb.setQty(packQty+"");
                    ldb.setMultiSku(true);
                }
                System.out.println("here is the qty");
                System.out.println(ldb.getQty());

                labelData.add(ldb);

                i++;
            }


        }catch (Exception e){
            e.printStackTrace();
        }





    }


    String itemsSQL = "SELECT\n" +
            "\n" +
            "                            dbo.package_line.pack_quantity,\n" +
            "                            isnull(dbo.owd_inventory_sku_maps.upc,owd_inventory.upc_code) as upc,\n" +
            "                            isnull(dbo.owd_inventory_sku_maps.foreign_desc,owd_inventory.description) as description\n" +
            "                        FROM\n" +
            "                            dbo.package_line\n" +
            "                        INNER JOIN\n" +
            "                            dbo.owd_line_item\n" +
            "                        ON\n" +
            "                            (\n" +
            "                                dbo.package_line.owd_line_item_fkey = dbo.owd_line_item.line_item_id)\n" +
            "                        INNER JOIN\n" +
            "                            dbo.owd_order\n" +
            "                        ON\n" +
            "                            (\n" +
            "                                dbo.owd_line_item.order_fkey = dbo.owd_order.order_id)\n" +
            "                        INNER JOIN\n" +
            "                            dbo.owd_inventory_sku_maps\n" +
            "                        ON\n" +
            "                            (\n" +
            "                                dbo.owd_line_item.inventory_num = dbo.owd_inventory_sku_maps.owd_sku)\n" +
            "                        Inner join \n" +
            "                                owd_inventory \n" +
            "                                on\n" +
            "                                (owd_line_item.inventory_id = owd_inventory.inventory_id)        \n" +
            "                        WHERE\n" +
            "                            dbo.owd_inventory_sku_maps.client_fkey = dbo.owd_order.client_fkey\n" +
            "AND dbo.package_line.package_fkey = :packageFkey\n" +
            "AND dbo.owd_inventory_sku_maps.vendor_compliance_fkey = :vcid ;";
   //Todo move these out to override for each label
    public  List getItemInfoForPackageId(String packageId, String vendorComplainceId) throws Exception{
        List items = new ArrayList();
        try{



            if(vendorComplainceId.equals("3")){

                itemsSQL = "SELECT\n" +
                        "\n" +
                        "                        dbo.package_line.pack_quantity,\n" +
                        "                        dbo.jcpenney_itemreference.upc,\n" +
                        "                        dbo.jcpenney_itemreference.description,\n" +
                        "                    FROM\n" +
                        "                       dbo.package_line\n" +
                        "                    INNER JOIN\n" +
                        "                        dbo.owd_line_item\n" +
                        "                    ON\n" +
                        "                        (\n" +
                        "                            dbo.package_line.owd_line_item_fkey = dbo.owd_line_item.line_item_id)\n" +
                        "                    INNER JOIN\n" +
                        "                        dbo.owd_order\n" +
                        "                    ON\n" +
                        "                        (\n" +
                        "                            dbo.owd_line_item.order_fkey = dbo.owd_order.order_id)\n" +
                        "                    INNER JOIN\n" +
                        "                        dbo.jcpenney_itemreference\n" +
                        "                    ON\n" +
                        "                        (\n" +
                        "                            dbo.owd_line_item.inventory_num = dbo.jcpenney_itemreference.owdsku)\n" +
                        "                    WHERE\n" +
                        "                        dbo.jcpenney_itemreference.jcp_client_fkey = dbo.owd_order.client_fkey\n" +
                        "                    AND dbo.package_line.package_fkey = :packageFkey ;";


            }else if(vendorComplainceId.equals("4")||vendorComplainceId.equals("5")||vendorComplainceId.equals("6")||vendorComplainceId.equals("7")){
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
            System.out.println(itemsSQL);

            Query q = HibernateSession.currentSession().createSQLQuery(itemsSQL);

            q.setParameter("packageFkey", packageId);
            if(itemsSQL.contains(":vcid")){
                q.setParameter("vcid",vendorComplainceId);
            }

            q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            items = q.list();
            if (items.size()<=0){
                throw new Exception("Didn't load anything. Empty results");
            }


        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("unable to load Item info for package id: " + packageId);
        }
        return items;
    }
}
