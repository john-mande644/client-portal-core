package com.owd.dc.inventory.labels.ASNItemLabels;

import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.owd.dc.inventory.labels.inventoryLabelUtilities.printSmallLabelMapById;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 10/20/14
 * Time: 2:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class ItemLabelUtils {
    public static void main(String[] args) {
        try {
            List<ASNItemLabelsBean> ab = loadAsnItemsToPrint("54728", true, false);

            for (ASNItemLabelsBean lb : ab) {
                System.out.println(lb.getAsnItemId());
                System.out.println(lb.getInventoryId());
                System.out.println(lb.getCountToPrint());

            }
            printLabels(ab, "zebra12.dc1.owd.com");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void printLabels(List<ASNItemLabelsBean> ab, String printer) throws Exception {
        Map<Integer, String> items = new HashMap<Integer, String>();
        for (ASNItemLabelsBean lb : ab) {
            items.put(Integer.parseInt(lb.getInventoryId()), lb.getCountToPrint());
        }
        printSmallLabelMapById(items, printer);

    }

    public static List<ASNItemLabelsBean> loadAsnItemsToPrint(String asnId, boolean UPC, boolean onlyWhatsLeft) throws Exception {
        StringBuffer sb = new StringBuffer();
        List<ASNItemLabelsBean> items = new ArrayList<ASNItemLabelsBean>();
        sb.append("SELECT\n" +
                "    dbo.asn_items.id,\n" +
                "    dbo.asn_items.inventory_fkey,\n" +
                "    dbo.asn_items.inventory_num,\n" +
                "    dbo.asn_items.expected,\n" +
                "    expected - received,\n" +
                "    dbo.owd_inventory.description,\n" +
                "    dbo.owd_inventory.upc_code,\n" +
                "    dbo.owd_inventory.isbn_code\n" +
                "FROM\n" +
                "    dbo.asn_items\n" +
                "INNER JOIN dbo.owd_inventory\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.asn_items.inventory_fkey = dbo.owd_inventory.inventory_id\n" +
                "    )\n" +
                "WHERE\n" +
                "    dbo.asn_items.asn_fkey = :asnId ");
        if (!UPC) {


            sb.append("and upc_code = '' and isbn_code = ''");
        }
        if (onlyWhatsLeft) {
            sb.append("and expected - received > 0");
        }
        Query q = HibernateSession.currentSession().createSQLQuery(sb.toString());
        q.setParameter("asnId", asnId);
        List results = q.list();

        if (results.size() > 0) {
            for (Object row : results) {
                Object[] data = (Object[]) row;
                ASNItemLabelsBean ab = new ASNItemLabelsBean();
                ab.setAsnItemId(data[0].toString());
                ab.setInventoryId(data[1].toString());
                ab.setInventoryNum(data[2].toString());
                if (onlyWhatsLeft) {
                    ab.setCountToPrint(data[4].toString());
                } else {
                    ab.setCountToPrint(data[3].toString());

                }
                ab.setDescription(data[5].toString());
                items.add(ab);
            }


        }


        return items;

    }
}
