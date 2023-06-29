package com.owd.dc.packing;

import com.owd.WMSUtils;
import com.owd.core.business.Address;
import com.owd.dc.warehouse.licensePlate.licensePlateUtilities;
import org.hibernate.Session;

import java.sql.ResultSet;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: May 3, 2007
 * Time: 1:49:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class xmlCreate {
    public static String xmlHead = "<?xml version=\"1.0\"?>\r\n";

    public static String beginOrder(ResultSet rs) throws Exception {
        return xmlCreate.xmlHead + "<OWDOrder>\r\n" +
                "<order>\r\n" +
                "<id>" + xmlCreate.xmlEncode(rs.getString("order_id")) + "</id>\r\n" +
                "<orderNum>" + xmlCreate.xmlEncode(rs.getString("order_num")) + "</orderNum>\r\n" +
                "<name><![CDATA[" + xmlCreate.xmlEncode(rs.getString("ship_first_name")) + " " + xmlCreate.xmlEncode(rs.getString("ship_last_name")) + "]]></name>\r\n" +
                "<city><![CDATA[" + xmlCreate.xmlEncode(rs.getString("ship_city")) + "]]></city>\r\n" +
                "<state>" + xmlCreate.xmlEncode(rs.getString("ship_state")) + "</state>\r\n" +
                "<zip>" + xmlCreate.xmlEncode(rs.getString("ship_zip")) + "</zip>\r\n" +
                "<client>" + xmlCreate.xmlEncode(rs.getString("cname")) + "</client>\r\n" +
                "<shipMethod>" + xmlCreate.xmlEncode(rs.getString("carr_service")) + "</shipMethod>\r\n" +
                "<client_pack_notes><![CDATA[" + xmlCreate.xmlEncode(rs.getString("client_pack_notes")) + "]]></client_pack_notes>\r\n" +
                "<client_fkey>" + xmlCreate.xmlEncode(rs.getString("client_fkey")) + "</client_fkey>\r\n" +
                xmlCustomsForm(new Address(null, null, null, null, rs.getString("ship_state"), null, rs.getString("ship_country"))) + "\r\n" +
                "<fingerprint><![CDATA[" + xmlCreate.xmlEncode(rs.getString("fingerprint")) + "]]></fingerprint>" +
                "<fingerprintNoship><![CDATA[" + xmlCreate.xmlEncode(rs.getString("fingerprint").replaceAll(" \\| .*\\Z", "")) + "]]></fingerprintNoship>" +
                "<packInstructions><![CDATA[" + xmlCreate.xmlEncode(rs.getString("packing_instructions")) + "]]></packInstructions>\r\n" +
                "<is_personalized>" + xmlCreate.xmlEncode(rs.getString("is_personalized")) + "</is_personalized>\r\n" +
                "<lineItems>\r\n";

    }

    public static String lineItem(ResultSet rs, Session sess) throws Exception {

        StringBuffer sb = new StringBuffer();
        sb.append("<item>\r\n");
        sb.append("<id>" + xmlCreate.xmlEncode(rs.getString("inventory_id")) + "</id>\r\n");
        sb.append("<sku><![CDATA[" + xmlCreate.xmlEncode(rs.getString("inventory_num")) + "]]></sku>\r\n");
        sb.append("<qty>" + xmlCreate.xmlEncode(rs.getString("quantity_actual")) + "</qty>\r\n");
        sb.append("<desc><![CDATA[" + xmlCreate.xmlEncode2(rs.getString("description")) + "]]></desc>\r\n");
        sb.append("<upc>" + xmlCreate.xmlEncode(rs.getString("upc_code")) + "</upc>\r\n");
        sb.append("<isbn>" + xmlCreate.xmlEncode(rs.getString("isbn_code")) + "</isbn>\r\n");
        sb.append("<lineFkey>" + xmlCreate.xmlEncode(rs.getString("line_item_id")) + "</lineFkey>\r\n");
        sb.append("<item_pack_notes><![CDATA[" + xmlCreate.xmlEncode(rs.getString("item_pack_notes")) + "]]></item_pack_notes>\r\n");
        sb.append("<noScan>" + xmlCreate.xmlEncode(rs.getString("no_scan")) + "</noScan>\r\n");
        sb.append("<item_pack_url><![CDATA[" + xmlCreate.xmlEncode(rs.getString("item_pack_url")) + "]]></item_pack_url>\r\n");
        sb.append("<masterCaseQty>" + xmlEncode(rs.getInt("master_case_qty") + "") + "</masterCaseQty>\r\n");
        sb.append("<caseQty>" + xmlEncode(rs.getInt("case_qty") + "") + "</caseQty>\r\n");
        sb.append("<lotTracking>" + xmlEncode(rs.getInt("lot_tracking") + "") + "</lotTracking>\r\n");
        sb.append("<lotPattern>" + xmlEncode(rs.getString("lot_pattern")) + "</lotPattern>\r\n");
        sb.append("<itemWeight>" + xmlEncode(rs.getString("weight_lbs")) + "</itemWeight>\r\n");
        sb.append("<shipSystemWeight>" + xmlEncode(rs.getString("ship_system_weight")) + "</shipSystemWeight>\r\n");
        if (rs.getInt("is_serialized") == 2||rs.getInt("is_serialized") ==1) {
            sb.append(Util.getLineItemAdditionalData(sess, rs.getString("inventory_id"),rs.getString("is_serialized")));

        }
        sb.append(Util.getAdditionalIds(rs.getString("inventory_id")) + "\r\n");
        sb.append("<cardIslePickupCode>" + xmlEncode(rs.getString("card_isle_pickup_code")) + "</cardIslePickupCode>\r\n");

        sb.append("</item>\r\n");
        return sb.toString();


    }

    public static String finishOrder() {
        return "</lineItems>\r\n" +
                "</order>\r\n";
    }

    public static String getStartDate() {
        String s = "lala";
        try {
            s = "<orderStart>" + WMSUtils.getDate() + "</orderStart>";
        } catch (Exception e) {

        }
        return s;
    }

    public static String endOrder() {
        return "</OWDOrder>\r\n";
    }

    public static String verifiedShippingInfo(String weight, String boxId) {
        return "<verifiedShippingInfo>\n" +
                "<weight>" + xmlEncode(weight) + "</weight>\n" +
                "<boxId>" + xmlEncode(boxId) + "</boxId>\n" +
                "</verifiedShippingInfo>";
    }

    public static String licencePlateInfo(String license, String orderBarcode) throws Exception {
        return "<licensePlateInfo>\n" +
                "<licensePlate>" + xmlEncode(license) + "</licensePlate>\n" +
                "<boxId>" + xmlEncode(licensePlateUtilities.getBoxIdFromLicensePlate(license)) + "</boxId>\n" +
                "<orderBarcode>" + xmlEncode(orderBarcode) + "</orderBarcode>\n" +
                "</licensePlateInfo>";


    }

    public static String orderSortControl(String color, String sound, String type, String text, String image) {
        return "<orderSortControl>\r\n" +
                "<backgroundColor>" + color + "</backgroundColor>\n" +
                "<sortSound>" + sound + "</sortSound>\n" +
                "<sortType>" + type + "</sortType>\n" +
                "<sortText>" + text + "</sortText>\n" +
                "<sortImageUrl>" + image + "</sortImageUrl>\n" +
                "</orderSortControl>\n";
    }

    public static String box(String fkey, String name, String depth, String width, String height
            , String boxweight, String cost, String size, String volume, String desc, String invFkey, String barcode, String packaging_type) {
        char[] ca = desc.toCharArray();


        if (null == barcode) barcode = "";

        return "<box>\r\n" +
                "<fkey>" + fkey + "</fkey>\r\n" +
                "<name>" + name + "</name>\r\n" +
                "<depth>" + depth + "</depth>\r\n" +
                "<width>" + width + "</width>\r\n" +
                "<height>" + height + "</height>\r\n" +
                "<boxweight>" + boxweight + "</boxweight>\r\n" +
                "<cost>" + cost + "</cost>\r\n" +
                "<size>" + size + "</size>\r\n" +
                "<volume>" + volume + "</volume>\r\n" +
                "<desc>" + desc.replaceAll("'\\\r\\\n'", ", ") + "</desc>\r\n" +
                "<inventoryFkey>" + invFkey + "</inventoryFkey>\r\n" +
                "<barcode>" + barcode + "</barcode>\r\n" +
                "<packagingType>" + packaging_type + "</packagingType>\r\n" +
                "</box>\r\n";


    }

    public static String boxesPrefered(ResultSet rs) {
        StringBuffer bx = new StringBuffer();
        try {
            while (rs.next()) {
                bx.append(xmlCreate.box(rs.getString("id"), rs.getString("name"), rs.getString("dim_depth"), rs.getString("dim_width"), rs.getString("dim_height"),
                        rs.getString("weight_lbs"), rs.getString("cost"), rs.getString("size"), rs.getString("volume"), rs.getString("description"), rs.getString("inventory_fkey"), rs.getString("barcode"), rs.getString("packaging_type")));


            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "<boxesPrefered></boxesPrefered>";

        }


        return "<boxesPrefered>" + bx.toString() +
                "</boxesPrefered>\r\n";
    }

    public static String boxesPrimary(ResultSet rs) {
        StringBuffer bx = new StringBuffer();
        try {
            while (rs.next()) {
                bx.append(xmlCreate.box(rs.getString("id"), rs.getString("name"), rs.getString("dim_depth"), rs.getString("dim_width"), rs.getString("dim_height"),
                        rs.getString("weight_lbs"), rs.getString("cost"), rs.getString("size"), rs.getString("volume"), rs.getString("description"), rs.getString("inventory_fkey"), rs.getString("barcode"), rs.getString("packaging_type")));


            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "<boxesPrimary></boxesPrimary>";

        }

        /*String boxesS = "<boxesSecondary>\r\n" +
        "<box>\r\n"+
        "<fkey>35</fkey>\r\n" +
        "<name>A-12</name>\r\n" +
        "<depth>12.0</depth>\r\n" +
        "<width>12.0</width>\r\n" +
        "<height>3.00</height>\r\n" +
        "<boxweight>0.00</boxweight>\r\n" +
        "<cost>0.00</cost>\r\n" +
        "</box>\r\n"+
          "<box>\r\n"+
        "<fkey>48</fkey>\r\n" +
        "<name>S-1</name>\r\n" +
        "<depth>7.5</depth>\r\n" +
        "<width>12.0</width>\r\n" +
        "<height>0</height>\r\n" +
        "<boxweight>0.00</boxweight>\r\n" +
        "<cost>0.19</cost>\r\n" +
        "</box>\r\n"+
        "</boxesSecondary>\r\n";*/


        return "<boxesPrimary>" + bx.toString() +
                "</boxesPrimary>\r\n";
    }

    public static String loginResponce(String error, String id, String name) {

        return xmlCreate.xmlHead + "<response>\r\n" +
                "<error>" + error + "</error>\r\n" +
                "<id>" + id + "</id>\r\n" +
                "<name>" + name + "</name>\r\n" +
                "</response>";
    }

    public static String xmlEncode(String s) {
        if (null == s) return "";
        s = s.replaceAll("®", "&reg;");
        s = s.replaceAll("™", "&#8482;");
        return s.replaceAll("\\&", "&amp;");
    }

    public static String xmlEncode2(String s) {
        if (null == s) return "";
        s = s.replaceAll("\\&", "&amp;");
        s = s.replaceAll("</*\\w+>", "");
        s = s.replaceAll("<*>*", "");
        return s;

    }

    public static String xmlCustomsForm(Address a) {
        if (a.isInternational() || a.isAPO()) {
            return "<customs>yes</customs>";
        }
        return "<customs>no</customs>";
    }

}
