package com.owd.dc.packing.vendorCompliance;

import com.owd.dc.manifest.api.internal.returnAddressBean;
import com.owd.dc.packing.AutoBatchPrinting.PackingABUtils;
import com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels.*;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Criteria;
import org.hibernate.Query;

import java.sql.Clob;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by danny on 10/20/2016.
 */
public class vendorComplianceUtils {


    public static void main(String[] args) {
    }

    public static String getVendorComplianceXmlResponseForOrderNum(String orderNum) {
        System.out.println("Here is the order num: " + orderNum);
        String sql = "execute sp_getOrderIdFromOrderNum  :orderNum";
        String s = "";
        try {
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("orderNum", orderNum);
            List l = q.list();
            s = l.get(0).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return getVendorComplianceXmlResponseForOrder(s);
    }

    public static String getVendorComplianceXmlResponseForOrder(String orderId) {
        System.out.println("Doing VC for id: " + orderId);
        StringBuilder sb = new StringBuilder();
        sb.append("<vendorComplianceInfo>");
        String error = "";
        try {
            if (isVendorComplianceOrder(orderId)) {
                String vcId = getVCIdForOrderId(orderId);
                String vcName = getVendorComplainceNameFromId(vcId);
                sb.append("<name>");
                sb.append(vcName);
                sb.append("</name>");

                String packagelabelName = getPackageLabelTemplateName(vcId);

                if (packagelabelName.length() > 0) {
                    vendorCompliancePackageLabelBase label;
                    if (packagelabelName.equals("amazonSSCCPackage")) {
                        System.out.println("Doing amazon label");
                        label = new AmazonPackageLabel();
                    } else if (packagelabelName.equals("jcpenneySSCCPackage")) {
                        label = new JCPennyDirectToStorePackageLabel();
                    } else if (packagelabelName.equalsIgnoreCase("dicksWarehouseSSCCPackage")) {
                        label = new DickWarehousePackageLabel();
                    } else if (packagelabelName.equalsIgnoreCase("walmartS2SSSCCPackage")) {
                        label = new WalmartS2SPackageLabel();
                    } else if (packagelabelName.equalsIgnoreCase("bassProSSCCPackage")) {
                        label = new BassProWarehousePackageLabel();
                    } else if (packagelabelName.equalsIgnoreCase("sephoraSSCCPackage")) {
                        label = new SephoraPackageLabel();
                    } else if (packagelabelName.equalsIgnoreCase("walmartD2SSSCCPackage")) {
                        label = new WalmartD2SPackageLabel();
                    } else if (packagelabelName.equalsIgnoreCase("scheelsSSCCPackage")) {
                        label = new ScheelsAllSportsPackageLabel();
                    } else if (packagelabelName.equalsIgnoreCase("targetSSCCPackage")) {
                        label = new TushBabyTargetLabel();
                    } else if (packagelabelName.equalsIgnoreCase("bedBathSSCCPackage")) {
                        label = new TushBabyBedBathBeyondLabel();
                    } else if (packagelabelName.equalsIgnoreCase("campingWorldSSCCPackage")) {
                        label = new CampingWorldPackageLabel();
                    } else if (packagelabelName.equalsIgnoreCase("aceHardwareSSCCPackage")) {
                        label = new AceHardwarePackageLabel();
                    } else if (packagelabelName.equalsIgnoreCase("REISSCCPackage")) {
                        label = new REIPackageLabel();
                    } else if (packagelabelName.equalsIgnoreCase("zapposSSCCPackage")) {
                        label = new ZapposPackageLabel();
                    } else if (packagelabelName.equalsIgnoreCase("backcountrySSCCPackage")) {
                        label = new BackCountryPackageLabel();
                    } else if (packagelabelName.equalsIgnoreCase("walmartSSCC14Package")) {
                        label = new WalmartSSCC14PackageLabel();
                    } else if (packagelabelName.equalsIgnoreCase("moosejawSSCCPackage")) {
                        label = new MoosejawPackageLabel();
                    } else if (packagelabelName.equalsIgnoreCase("theGolfWarehouseSSCCPackage")) {
                        label = new TheGolfWarehousePackageLabel();
                    } else if (packagelabelName.equalsIgnoreCase("retailConceptsSSCCPackage")) {
                        label = new RetailConceptsPackageLabel();
                    } else {
                        throw new Exception("unable to find a proper VC mapping");
                    }

                    System.out.println("Loading label");
                    label.loadFromOrderId(orderId);
                    System.out.println("Label loaded");

                    String lblXml = label.getXml();
                    System.out.println(lblXml);

                    if (lblXml.length() > 0) {
                        sb.append(lblXml);
                    }
                }

            } else {
                error = "This is not a vendor Compliance Order";
            }

        } catch (Exception e) {
            e.printStackTrace();
            error = e.getMessage();
        }

        if (error.length() > 0) {
            sb.append("<error>");
            sb.append(error);
            sb.append("</error>");
        }

        sb.append("</vendorComplianceInfo>");
        return sb.toString();
    }

    public static String getPackageLabelTemplateName(String id) throws Exception {

        String s = "";

        try {
            String sql = "select package_label_template from owd_order_vendor_compliance where id = :id";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("id", id);
            List l = q.list();
            if (l.size() > 0) {
                return l.get(0).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;


    }

    public static Boolean isVendorComplianceOrder(int orderId) {
        return isVendorComplianceOrder(orderId + "");
    }


    public static List getItemInfoForPackageId(String packageId, String vendorComplainceId) throws Exception {
        List items = new ArrayList();
        try {
            String sql = "";

            if (vendorComplainceId.equals("3")) {

                sql = "SELECT\n" +
                        "\n" +
                        "                        dbo.package_line.pack_quantity,\n" +
                        "                        dbo.jcpenney_itemreference.upc\n" +
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


            } else if (vendorComplainceId.equals("4") || vendorComplainceId.equals("5") || vendorComplainceId.equals("6") || vendorComplainceId.equals("7")) {
                sql = "SELECT\n" +
                        "    dbo.package_line.pack_quantity,\n" +
                        "    dbo.owd_inventory.upc_code as upc\n" +
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
            } else {
                sql = "SELECT\n" +
                        "\n" +
                        "                            dbo.package_line.pack_quantity,\n" +
                        "                            isnull(dbo.owd_inventory_sku_maps.upc,owd_inventory.upc_code) as upc\n" +
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
            }
            System.out.println(sql);

            Query q = HibernateSession.currentSession().createSQLQuery(sql);

            q.setParameter("packageFkey", packageId);
            if (sql.contains(":vcid")) {
                q.setParameter("vcid", vendorComplainceId);
            }

            q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            items = q.list();
            if (items.size() <= 0) {
                throw new Exception("Didn't load anything. Empty results");
            }


        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("unable to load Item info for package id: " + packageId);
        }
        return items;
    }

    public static List getPackageInfoForLabelId(String orderId) throws Exception {
        List packages = new ArrayList();
        try {
            String sql = "SELECT\n" +
                    "    dbo.package_order.num_packs,\n" +
                    "    dbo.package.id,\n" +
                    "    dbo.package.weight_lbs,\n" +
                    "    dbo.owd_order_track.tracking_no,\n" +
                    "    dbo.package.SSCC, " +
                    "convert(varchar,convert(DATE,pack_end),1) as processDate\n" +
                    "FROM\n" +
                    "    dbo.package_order\n" +
                    "LEFT OUTER JOIN\n" +
                    "    dbo.package\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.package_order.id = dbo.package.package_order_fkey)\n" +
                    "LEFT OUTER JOIN\n" +
                    "    dbo.owd_order_track\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.package.order_track_fkey = dbo.owd_order_track.order_track_id)\n" +
                    "WHERE\n" +
                    "    dbo.package_order.owd_order_fkey = :orderId\n" +
                    "AND dbo.package_order.is_void = 0\n" +
                    "ORDER BY\n" +
                    "    dbo.package.id ASC ;";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setInteger("orderId", Integer.parseInt(orderId));
            q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            packages = q.list();
            if (packages.size() <= 0) {
                throw new Exception("Didn't load anything. Empty results");
            }


        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Unable to load package Info for Vendor Compliance Label Generation");
        }
        return packages;
    }

    public static Boolean isVendorComplianceOrder(String orderId) {
        Boolean vc = false;
        try {

            String sql = "select value from owd_tags where external_id = :orderId and type = 'ORDER' and name = 'COM.OWD.COMPLIANCE.ID'";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("orderId", orderId);
            if (q.list().size() > 0) {
                vc = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return vc;
    }

    public static String getVendorComplainceNameFromId(String id) {
        String s = "";

        try {
            String sql = "select vc_name from owd_order_vendor_compliance where id = :id";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("id", id);
            List l = q.list();
            if (l.size() > 0) {
                return l.get(0).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public static String getVCIdForOrderId(String orderId) throws Exception {

        try {
            String sql = "select convert(varchar,value) from owd_tags where external_id = :orderId and name = 'COM.OWD.COMPLIANCE.ID' ";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("orderId", orderId);
            List l = q.list();
            if (l.size() > 0) {
                return l.get(0).toString();
            } else {
                throw new Exception("nothing");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("unable to load Vendor Complaince Id for order");
        }

    }

    public static String getXMLForPackingFromOrderID(String orderId) {
        System.out.println("Getting VC for : " + orderId);

        try {

            String sql = "select convert(varchar,value) from owd_tags where external_id = :orderId and name = 'COM.OWD.COMPLIANCE.ID' ";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("orderId", orderId);
            List l = q.list();
            if (l.size() == 1) {
                StringBuilder sb = new StringBuilder();
                sb.append("<vendorCompliance>");
                sb.append("<vcid>");
                sb.append(l.get(0).toString());
                sb.append("</vcid>");
                sb.append("<vcname>");
                sb.append(getVendorComplainceNameFromId(l.get(0).toString()));
                sb.append("</vcname>");
                sb.append("</vendorCompliance>");
                return sb.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static returnAddressBean getDefaultReturnAddress(String shipper) throws Exception {
        returnAddressBean rb = new returnAddressBean();
        String sql = "select address, city,state,zip from owd_facilities where loc_code = :loc";
        try {
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("loc", shipper);
            List results = q.list();
            if (results.size() > 0) {
                Object row = results.get(0);
                Object[] data = (Object[]) row;
                rb.setAddress1(data[0].toString());
                rb.setCity(data[1].toString());
                rb.setState(data[2].toString());
                rb.setZip(data[3].toString());
                rb.setPhone("6058457172");

            } else {
                throw new Exception("No records for return");
            }


        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("unable to fill default return address");
        }


        return rb;
    }

    public static String edi850FromOrderID(String orderId) {
        System.out.println("Getting VC for : " + orderId);

        try {

            String sql = "select convert(varchar(max),value) from owd_tags where external_id = :orderId and name = 'COM.OWD.EDI.850' ";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("orderId", orderId);
            List l = q.list();
            if (l.size() == 1) {
                Clob c = (Clob) l.get(0);
                return PackingABUtils.convertClobToString(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String CommereHubDataFromOrderID(String orderId) {
        System.out.println("Getting VC for : " + orderId);

        try {

            String sql = "select convert(varchar(max),value) from owd_tags where external_id = :orderId and name = 'COMMERCEHUB_PO_XML' ";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("orderId", orderId);
            List l = q.list();
            if (l.size() == 1) {
                Clob c = (Clob) l.get(0);
                return PackingABUtils.convertClobToString(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
