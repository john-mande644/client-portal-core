package com.owd.jobs.jobobjects.RetailPro.models

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.HibUtils
import com.owd.hibernate.HibernateSession
import com.owd.jobs.jobobjects.RetailPro.TransferUtilities
import org.hibernate.SQLQuery

import java.sql.ResultSet
import java.text.DateFormat
import java.text.SimpleDateFormat

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 2/22/12
 * Time: 12:42 PM
 * To change this template use File | Settings | File Templates.
 */
class Adjustment {

    static DateFormat printDf = new SimpleDateFormat("yyyyMMdd");

    public static String renderFormattedXml(String xml) {
        if (xml) {
            def stringWriter = new StringWriter()
            def node = new XmlParser().parseText(xml.toString());
            new XmlNodePrinter(new PrintWriter(stringWriter)).print(node)
            return stringWriter.toString()
        }
        else {
            return ""
        }
    }

    public static void main(String[] args) {
        List<String> items = new ArrayList<String>()
        //items.add("12 307 00")
        //items.add("09 442 00")
      //    reportAdjustments(items)
       // markAsReported("175428")
        // reportReceives()
    }


    public static void reportAdjustments(List<String> rproSkuList) {
        Map<String, Voucher> voucherMap = new TreeMap<String, Voucher>();

        try {

            ResultSet rs = HibernateSession.getResultSet("select ref_num,notes,receive_id,sku,sum(qty) as adj from \n" +
                    "(SELECT\n" +
                    "    r.inventory_num AS sku,\n" +
                    "    CASE\n" +
                    "        WHEN r.is_void=1\n" +
                    "        THEN -1*quantity\n" +
                    "        ELSE quantity\n" +
                    "    END AS qty,\n" +
                    "    ref_num,\n" +
                    "    owd_receive.notes,\n" +
                    "    receive_id,\n" +
                    "    type\n" +
                    "FROM\n" +
                    "    owd_inventory i\n" +
                    "JOIN\n" +
                    "    owd_inventory_oh oh\n" +
                    "ON\n" +
                    "    oh.inventory_fkey=i.inventory_id\n" +
                    "JOIN\n" +
                    "    owd_receive_item r\n" +
                    "JOIN\n" +
                    "    owd_receive\n" +
                    "LEFT OUTER JOIN\n" +
                    "    owd_order\n" +
                    "ON\n" +
                    "    ref_num=order_num\n" +
                    "ON\n" +
                    "    receive_fkey=receive_id\n" +
                    "ON\n" +
                    "    i.inventory_id=r.inventory_id\n" +
                    "AND r.reported=0\n" +
                    "AND NOT (\n" +
                    "        PATINDEX('OWDRCV%',ISNULL(transaction_num,''))=1\n" +
                    "    AND item_status='Damaged'\n" +
                    "    AND quantity>0)\n" +
                    "WHERE\n" +
                    "    i.client_fkey=482) as stuff where type<>'Receive'\n" +
                    "group by ref_num,notes,receive_id,sku\n" +
                    "order by sum(qty) ");
            List<String> allRids = new ArrayList<String>();

            while (rs.next()) {

                String rid = rs.getString("receive_id");
                println rid
                allRids.add(rid);
                if(rproSkuList.contains(rs.getString("sku").replaceAll("/KIT", "").trim()))
                {
                int adj = rs.getInt("adj");
                if (adj != 0) {
                    String type = (adj < 0 ? Voucher.kVoucherReturnType : Voucher.kVoucherReceiveType)
                    if (voucherMap.containsKey(rid + type)) {
                        voucherMap.get(rid + type).addVoucherItem(rs.getString("sku").replaceAll("/KIT", ""), adj)
                    } else {
                        Voucher vou = new Voucher();
                        vou.id = rid
                        vou.notes = rs.getString("notes")
                        vou.type = type
                        vou.addVoucherItem(rs.getString("sku").replaceAll("/KIT", ""), adj)
                        voucherMap.put(rid + type, vou)

                    }


                }
                }
            }

            rs.close();

            for (Voucher vou : voucherMap.values()) {

                String fileData = vou.getVoucherXML()
                println renderFormattedXml(fileData)
                TransferUtilities.putRetailProFile("VOU_" + Calendar.getInstance().getTimeInMillis() + ".xml", fileData, true)
                markAsReported(vou.id);
                allRids.remove(vou.id)

            }

            for(String rid:allRids)
            {
                markAsReported(rid);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            HibernateSession.closeSession();
        }

    }

    private static markAsReported(String vouId) {
        SQLQuery query = HibernateSession.currentSession().createSQLQuery("update owd_receive_item set reported=1 where receive_fkey=" + vouId + ";");
        query.executeUpdate();
        HibUtils.commit(HibernateSession.currentSession())
    }


}

