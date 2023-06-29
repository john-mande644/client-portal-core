package com.owd.dc.inventory;

import com.owd.dc.inventory.beans.inventoryBarcodeQtyBean;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.metamodel.source.hbm.HierarchyBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Apr 18, 2005
 * Time: 3:49:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class upcBarcodeUtilities {
    protected final static Logger log = LogManager.getLogger();

    static  Map<String,String> tonicBadBarcodeMap = null;

    public static Map<String, String> getTonicBadBarcodeMap() {

        if(tonicBadBarcodeMap==null)
        {
          tonicBadBarcodeMap = new HashMap<String,String>();
               tonicBadBarcodeMap.put("119593001BLUW-S","119903");
tonicBadBarcodeMap.put("119594001BLUW-M","119904");
tonicBadBarcodeMap.put("119595001BLUW-L","119905");
tonicBadBarcodeMap.put("119596001BLUW-XL","119906");
tonicBadBarcodeMap.put("119597002BLKW-S","119907");
tonicBadBarcodeMap.put("119598002BLKW-M","119908");
tonicBadBarcodeMap.put("119599002BLKW-L","119909");
tonicBadBarcodeMap.put("119560002BLKW-XL","119910");
tonicBadBarcodeMap.put("119601003NATW-S","119911");
tonicBadBarcodeMap.put("119602003NATW-M","119912");
tonicBadBarcodeMap.put("119603003NATW-L","119913");
tonicBadBarcodeMap.put("119604003NATW-XL","119914");
tonicBadBarcodeMap.put("119605004GRNW-S","119915");
tonicBadBarcodeMap.put("119606004GRNW-M","119916");
tonicBadBarcodeMap.put("119607004GRNW-L","119917");
tonicBadBarcodeMap.put("119608004GRNW-XL","119918");
tonicBadBarcodeMap.put("119638001BLUM-M","119919");
tonicBadBarcodeMap.put("119639001BLUM-L","119920");
tonicBadBarcodeMap.put("119640001BLUM-XL","119921");
tonicBadBarcodeMap.put("119641002BLKM-M","119922");
tonicBadBarcodeMap.put("119642002BLKM-L","119923");
tonicBadBarcodeMap.put("119643002BLKM-XL","119924");
tonicBadBarcodeMap.put("119644003NATM-M","119925");
tonicBadBarcodeMap.put("119645003NATM-L","119926");
tonicBadBarcodeMap.put("119646003NATM-XL","119927");
tonicBadBarcodeMap.put("119647004GRNM-M","119928");
tonicBadBarcodeMap.put("119648004GRNM-L","119929");
tonicBadBarcodeMap.put("119649004GRNM-XL","119930");
        }
        return tonicBadBarcodeMap;
    }


    public static String getTranslatedBarcode(String barcode)
    {
        log.debug("testing barcode " + barcode);
        if(getTonicBadBarcodeMap().containsKey(barcode))
        {
            log.debug("got translation:" + barcode);
            return getTonicBadBarcodeMap().get(barcode);
        }else

        return barcode;
    }
    public static String barcodeCheck(String barcode) {
        log.debug("Chekcing: " + barcode);
        //checks length.  If 13, and starts with 978 or 979 returns isbn, otherwise retruns upc.
        //if 12, it returns upc
        // checks to make sure it is a valid barcode
       // barcode = getTranslatedBarcode(barcode);
        barcode = barcode.trim();
        if (barcode.length() == 13) {
            if (barcode.equals(isbncheckdigit(barcode))) {
                if (barcode.startsWith("978") || barcode.startsWith("979")) {
                    return "ISBN";
                } else {
                    return "UPC";
                }
            } else {
                return "Not a valid UPC or ISBN code";
            }
        }

        if (barcode.length() == 12 || barcode.length() == 14) {
            if (barcode.equals(upccheckdigit(barcode))) {
                return "UPC";
            } else {
                return "Not a valid UPC or ISBN code";
            }
        }
        if (barcode.equals("ZERO")) {
            return "ZERO";
        }
        return "Not a valid UPC or ISBN code";


    }
public static String decodeClientSku(String barcode){

    try{
        Pattern pat = Pattern.compile("^@@(\\d*):([.[^:]]*)");
        Matcher match = pat.matcher(barcode);
        //   log.debug("Matches: "+match.matches());
        //log.debug(match.lookingAt());

        if(match.matches()){
            log.debug("match");
            log.debug(match.group(0));
            log.debug(match.group(1));
            log.debug(match.group(2));
            String clientId = match.group(1);
            String sku = match.group(2);

            String sql = "select inventory_id from owd_inventory where client_fkey = :clientId and inventory_num = :sku";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("clientId",clientId);
            q.setParameter("sku",sku);
            List results = q.list();
            if(results.size()==1){
                return results.get(0).toString();
            }else{
                return barcode;
            }





        }else{
            log.debug("No matchie");
        }


    }catch (Exception e){
        e.printStackTrace();

    }


    return barcode;
}

    public static String getSku(String barcode, int clientFkey) {
        inventoryBarcodeQtyBean  ib = getInventoryBarcodeQty(barcode,clientFkey);

        return ib.getInventoryId();
    }
    public static inventoryBarcodeQtyBean getInventoryBarcodeQty(String barcode, int clientFkey) {
        inventoryBarcodeQtyBean ib = new inventoryBarcodeQtyBean();
        try {

if(barcode.startsWith("@@")){
    ib.setInventoryId(decodeClientSku(barcode));
    ib.setQty(1);
    return ib;

}
           String sql = "execute sp_getInventoryIdFromIdentifier '" + barcode + "'," + clientFkey;

            ResultSet rs = HibernateSession.getResultSet(HibernateSession.currentSession(),sql);

            log.debug("before rs.next if");
            int rows = 0;

            while (rs.next()) {
                ib.setInventoryId(rs.getString(1));
                ib.setQty(rs.getInt(2));
                rows++;

            }


            rs.close();
            if (rows == 1) {

                return ib;
            } else if (rows > 1) {
                log.debug("retrunign multi");
                ib.setInventoryId("Multi");
                ib.setQty(1);
                return ib;


            } else {
                //return barcocode not assigned to anything
                ib.setInventoryId("NA");
                return ib;
            }
        } catch(SQLException se){
            log.debug(se.getMessage());
         if(se.getMessage().equals("No current row in the ResultSet.")){

             ib.setInventoryId("NA");
             return ib;
         }
        } catch (Exception ex) {

            ex.printStackTrace();
            log.debug(ex.getMessage());
        }

        ib.setInventoryId(barcode);
        ib.setQty(1);
        return ib;
    }

    public static String upccheckdigit(String code) {

        int odds[] = new int[7];
        int evens[] = new int[6];
        int odd = 0;
        int even = 0;
        if (code.length() <= 12) {
            code = code.substring(0, 11);
            int o = 0;
            for (int i = 0; i < 6; i++) {
                odds[i] = code.charAt(o) - 48;
                o = o + 2;
            }
            // odds[0] = code.charAt(0) - 48;
            //  odds[1] = code.charAt(2) - 48;
            //  odds[2] = code.charAt(4) - 48;
            //  odds[3] = code.charAt(6) - 48;
            //  odds[4] = code.charAt(8) - 48;
            //  odds[5] = code.charAt(10) - 48;
            o = 1;
            for (int i = 0; i < 5; i++) {
                evens[i] = code.charAt(o) - 48;
                o = o + 2;
            }
            /* evens[0] = code.charAt(1) - 48;
             evens[1] = code.charAt(3) - 48;
             evens[2] = code.charAt(5) - 48;
             evens[3] = code.charAt(7) - 48;
             evens[4] = code.charAt(9) - 48;*/

            odd = (odds[0] + odds[1] + odds[2] + odds[3] + odds[4] + odds[5]) * 3;
            even = (evens[0] + evens[1] + evens[2] + evens[3] + evens[4]);
        } else {
            int o = 0;
            code = code.substring(0, 13);
            for (int i = 0; i < 7; i++) {
                odds[i] = code.charAt(o) - 48;
                o = o + 2;
            }
            /*odds[0] = code.charAt(0) - 48;
            odds[1] = code.charAt(2) - 48;
            odds[2] = code.charAt(4) - 48;
            odds[3] = code.charAt(6) - 48;
            odds[4] = code.charAt(8) - 48;
            odds[5] = code.charAt(10) - 48;
            odds[6] = code.charAt(12) - 48;*/
            o = 1;
            for (int i = 0; i < 6; i++) {
                evens[i] = code.charAt(o) - 48;
                o = o + 2;
            }
            /* evens[0] = code.charAt(1) - 48;
             evens[1] = code.charAt(3) - 48;
             evens[2] = code.charAt(5) - 48;
             evens[3] = code.charAt(7) - 48;
             evens[4] = code.charAt(9) - 48;
             evens[5] = code.charAt(11) - 48;*/

            odd = (odds[0] + odds[1] + odds[2] + odds[3] + odds[4] + odds[5] + odds[6]) * 3;
            even = (evens[0] + evens[1] + evens[2] + evens[3] + evens[4] + evens[5]);
        }

        int total = odd + even;
        String totalstring = Integer.toString(total);
        int last = totalstring.charAt(totalstring.length() - 1) - 48;
        int checkdigit = 10 - last;
        if (checkdigit == 10) checkdigit = 0;
        return code + checkdigit;
    }

    public static String isbncheckdigit(String code) {
        code = code.substring(0, 12);
        int odds[] = new int[6];
        int evens[] = new int[6];
        int o = 11;
        for (int i = 0; i < 6; i++) {
            odds[i] = code.charAt(o) - 48;
            o = o - 2;
        }
        o = 10;
        for (int i = 0; i < 6; i++) {
            evens[i] = code.charAt(o) - 48;
            o = o - 2;
        }

        /* odds[0] = code.charAt(11) - 48;
         odds[1] = code.charAt(9) - 48;
         odds[2] = code.charAt(7) - 48;
         odds[3] = code.charAt(5) - 48;
         odds[4] = code.charAt(3) - 48;
         odds[5] = code.charAt(1) - 48;
         evens[0] = code.charAt(10) - 48;
         evens[1] = code.charAt(8) - 48;
         evens[2] = code.charAt(6) - 48;
         evens[3] = code.charAt(4) - 48;
         evens[4] = code.charAt(2) - 48;
         evens[5] = code.charAt(0) - 48;*/

        int odd = (odds[0] + odds[1] + odds[2] + odds[3] + odds[4] + odds[5]) * 3;
        int even = (evens[0] + evens[1] + evens[2] + evens[3] + evens[4] + evens[5]);
        int total = odd + even;
        String totalstring = Integer.toString(total);
        int last = totalstring.charAt(totalstring.length() - 1) - 48;
        int checkdigit = 10 - last;
        if (checkdigit == 10) checkdigit = 0;
        return code + checkdigit;
    }

    public static String RPad(String str, Integer length, char car) {
        return str
                 +
                 String.format("%" + (length - str.length()) + "s", "")
                             .replace(" ", String.valueOf(car));
        }

    public static void calculate13DigitAndUpadateMap(Map<String,String> theList, int clientId){
        try{
            Map<String,String> newList = new TreeMap<String,String>();
             for(String s : theList.keySet()){
               String upc = isbncheckdigit(theList.get(s));
                 if(barcodeCheck(upc).equals("UPC")){
                        String sql = "update owd_inventory set upc_code = :upc where inventory_num = :inventory and client_fkey = :clientId";
                     Query q = HibernateSession.currentSession().createSQLQuery(sql);
                     q.setParameter("upc",upc);
                     q.setParameter("inventory",s);
                     q.setParameter("clientId",clientId);
                     int i = q.executeUpdate();
                     if(i>0){
                         HibUtils.commit(HibernateSession.currentSession());
                     }   else{
                         log.debug("Failsed: " + s);
                     }
                     newList.put(s,upc);
                 }   else{
                     throw new Exception("Bad Calk");
                 }



             }

            for(String s:newList.keySet()){
                log.debug(s + "\t" + newList.get(s));
            }
        }   catch(Exception e){
            e.printStackTrace();
        }



    }
    public static void main(String[] args) {


       /* try{

            String sql = "select id, upc from owd_inventory_sku_maps WHERE client_fkey = 471 and type = 'Amazon' and id > 3102";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            List l = q.list();

            for(Object row: l){
                Object[] data = (Object[]) row;
                log.debug(data[1].toString());
                String newUpc = upccheckdigit(data[1].toString());
                System.out.print(newUpc);
                Query qq = HibernateSession.currentSession().createSQLQuery("update owd_inventory_sku_maps set upc = :upc where id = :id");
                qq.setParameter("upc",newUpc);
                qq.setParameter("id",data[0]);
                qq.executeUpdate();

            }
            HibUtils.commit(HibernateSession.currentSession());

        }catch(Exception e){
            e.printStackTrace();
        }*/

       // log.debug(getSku("426243",0));
       // log.debug(getSku("426243",55));
        try {
            getMultiBarcodeList("  ", 0);
        }catch (Exception e){
            e.printStackTrace();
        }


//log.debug(getSku("55:MAIL:",0));
        // log.debug(upccheckdigit("141133290104"));
       // String id = "36351";
       // log.debug(RPad("4",11-id.length(),'0')+id);
       // log.debug(addcheckdigit(RPad("4",11-id.length(),'0')+id));
       // log.debug(addcheckdigit("40000036351"));
       // log.debug(upcBarcodeUtilities.barcodeCheck("141133290104"));
        /*   java.sql.Connection cxn = null;
           java.sql.PreparedStatement stmt = null;
           java.sql.ResultSet rs = null;
           try {
               cxn = ConnectionManager.getConnection();
               String getinventorynum = "select inventory_num from owd_inventory"
                       + " where client_fkey = '111' and upc_code = '' and inventory_num not like 'L%'";

               stmt = cxn.prepareStatement(getinventorynum);
               stmt.executeQuery();
               rs = stmt.getResultSet();

               while (rs.next()) {
                   String code = rs.getString(1);

                   String upc = getisbncheckdigit(code);
                   if (upc.equals("Not UPC") == false) {

                       String sql = "update owd_inventory set upc_code = ?"
                               + " where client_fkey = '111' and inventory_num = ?";

                       stmt = cxn.prepareStatement(sql);
                       stmt.setString(1, upc);
                       stmt.setString(2, code);
                       int rowsUpdated = stmt.executeUpdate();
                       log.debug("Updated " + code);
                       cxn.commit();
                   }
               }
           } catch (Exception e) {
               log.debug(e.getStackTrace());

           }
          */
    }

    public static String addcheckdigit(String code) {
        if (code.length() == 12) {
            code = code.substring(0, 11);
        }

        if (code.length() != 11) {
            return "Not UPC";
        }
        int odds[] = new int[6];
        int evens[] = new int[5];
        odds[0] = code.charAt(0) - 48;
        odds[1] = code.charAt(2) - 48;
        odds[2] = code.charAt(4) - 48;
        odds[3] = code.charAt(6) - 48;
        odds[4] = code.charAt(8) - 48;
        odds[5] = code.charAt(10) - 48;
        evens[0] = code.charAt(1) - 48;
        evens[1] = code.charAt(3) - 48;
        evens[2] = code.charAt(5) - 48;
        evens[3] = code.charAt(7) - 48;
        evens[4] = code.charAt(9) - 48;

        int odd = (odds[0] + odds[1] + odds[2] + odds[3] + odds[4] + odds[5]) * 3;
        int even = (evens[0] + evens[1] + evens[2] + evens[3] + evens[4]);
        int total = odd + even;
        String totalstring = Integer.toString(total);
        int last = totalstring.charAt(totalstring.length() - 1) - 48;
        int checkdigit = 10 - last;
        if (checkdigit == 10) checkdigit = 0;
        return code + checkdigit;
    }

    public static String getisbncheckdigit(String code) {
        if (code.length() == 13) {
            code = code.substring(0, 12);
        } else {
            return ("Not UPC");
        }
        int odds[] = new int[6];
        int evens[] = new int[6];
        odds[0] = code.charAt(11) - 48;
        odds[1] = code.charAt(9) - 48;
        odds[2] = code.charAt(7) - 48;
        odds[3] = code.charAt(5) - 48;
        odds[4] = code.charAt(3) - 48;
        odds[5] = code.charAt(1) - 48;
        evens[0] = code.charAt(10) - 48;
        evens[1] = code.charAt(8) - 48;
        evens[2] = code.charAt(6) - 48;
        evens[3] = code.charAt(4) - 48;
        evens[4] = code.charAt(2) - 48;
        evens[5] = code.charAt(0) - 48;

        int odd = (odds[0] + odds[1] + odds[2] + odds[3] + odds[4] + odds[5]) * 3;
        int even = (evens[0] + evens[1] + evens[2] + evens[3] + evens[4] + evens[5]);
        int total = odd + even;
        String totalstring = Integer.toString(total);
        int last = totalstring.charAt(totalstring.length() - 1) - 48;
        int checkdigit = 10 - last;
        if (checkdigit == 10) checkdigit = 0;
        return code + checkdigit;
    }

    public static HashMap getMultiBarcodeList(String barcode, int clientFkey) throws Exception {

        barcode = barcode.trim();
        if(barcode.length()==0||barcode.equals(" ")) throw new Exception("Barcode needs to not be blank");
        //retruns a HashMap of Sku's with the same upc/isbn in MultiSkuform
        try {
            // barcode = getTranslatedBarcode(barcode);
            //get barcode type returns upc or isbn if valid, error message if not
            /*String barcodeType = upcBarcodeUtilities.barcodeCheck(barcode);
            int sq = 0;
            if (barcodeType.equals("UPC"))
                sq = 0;
            else if (barcodeType.equals("ISBN"))
                sq = 1;

            int c = 0;
            if (clientFkey != 0) {
                c = 1;
            }
            String[][] sqlgetid = new String[2][2];
            sqlgetid[0][0] = "select inventory_id, inventory_num, company_name, description, client_fkey from dbo.owd_inventory i (NOLOCK) , \n" +
                    "dbo.owd_client c  (NOLOCK) where i.client_fkey = c.client_id and upc_code = '" + barcode + "'";
            sqlgetid[1][0] = "select inventory_id, inventory_num, company_name, description, client_fkey from dbo.owd_inventory i (NOLOCK) , \n" +
                    "dbo.owd_client c  (NOLOCK) where i.client_fkey = c.client_id and isbn_code = '" + barcode + "'";
            sqlgetid[0][1] = "select inventory_id, inventory_num, company_name, description, client_fkey from dbo.owd_inventory i (NOLOCK) , \n" +
                    "dbo.owd_client c  (NOLOCK) where i.client_fkey = c.client_id and upc_code = '" + barcode + "'and client_fkey= " + clientFkey;
            sqlgetid[1][1] = "select inventory_id, inventory_num, company_name, description, client_fkey from dbo.owd_inventory i (NOLOCK) , \n" +
                    "dbo.owd_client c  (NOLOCK) where i.client_fkey = c.client_id and isbn_code = '" + barcode + "' and client_fkey=" + clientFkey;
*/
            String sql = "execute sp_getInventoryMultiIdentifierInfo '"+barcode+"',"+clientFkey;
            ResultSet rs = HibernateSession.getResultSet(HibernateSession.currentSession(),sql);
            HashMap skuList = new HashMap();
            int i = 1;
            while (rs.next()) {
                log.debug("in rs.next");
                multiSkuForm info = new multiSkuForm();
                info.setInventoryId(rs.getString(1));
                info.setInventoryNum(rs.getString(2));
                info.setClientName(rs.getString(3));
                info.setDescription(rs.getString(4));
                info.setClientFkey(rs.getString(5));
                skuList.put("" + i, info);
                i++;
            }
            rs.close();
            log.debug("return");
            return skuList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Unable to find data");

        }

    }

    public static HashMap getMultiBarcodeListLimited(ArrayList skuList) throws Exception {
        Iterator it = skuList.iterator();
        HashMap skusList = new HashMap();
        int i = 1;
        while (it.hasNext()) {
            OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, (new Integer(it.next().toString())));
            log.debug("in rs.next Limited");
            multiSkuForm info = new multiSkuForm();
            info.setInventoryId(inv.getInventoryId().toString());
            info.setInventoryNum(inv.getInventoryNum());
            info.setDescription(inv.getDescription());
            skusList.put("" + i, info);
            i++;

        }
        return skusList;
    }


    public static HashMap checkMultiAgainstList(String barcode, int clientFkey, ArrayList skuList) throws Exception {
         //barcode = getTranslatedBarcode(barcode);
        String barcodeType = upcBarcodeUtilities.barcodeCheck(barcode);
        int sq = 0;
        if (barcodeType.equals("UPC"))
            sq = 0;
        else if (barcodeType.equals("ISBN"))
            sq = 1;
        String[] sqlgetid = new String[2];
        sqlgetid[0] = "select inventory_id from dbo.owd_inventory i  (NOLOCK) \n" +
                " where upc_code = '" + barcode + "'and client_fkey= " + clientFkey;
        sqlgetid[1] = "select inventory_id from dbo.owd_inventory i  (NOLOCK) \n" +
                " where isbn_code = '" + barcode + "' and client_fkey=" + clientFkey;
        log.debug("before get RS");
        log.debug(sqlgetid[sq]);
        ResultSet rs = HibernateSession.getResultSet(sqlgetid[sq]);
        log.debug("afterRS");
        List skusInOrder = new ArrayList();
        HashMap info = new HashMap();
        while (rs.next()) {

               log.debug(rs.getString(1));
         log.debug("in while");
         log.debug(skuList);
            if (skuList.contains(rs.getString(1))==true) {
                log.debug("in if");

                skusInOrder.add(rs.getString(1));
                log.debug("add to akusinorder");
            }
        }
        if (skusInOrder.size() == 0) {
            throw new Exception("Item not found in current order");
        }
        if (skusInOrder.size() == 1) {
            log.debug("in = 1");
            info.put("outcome", "single");
            info.put("sku", skusInOrder.get(0).toString());
        }
        if (skusInOrder.size() > 1) {
            info.put("outcome", "multi");
            info.put("skus", skusInOrder);
        }
        return info;

    }
}
