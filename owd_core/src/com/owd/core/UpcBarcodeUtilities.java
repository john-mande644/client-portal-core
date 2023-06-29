package com.owd.core;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;

import java.sql.ResultSet;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpcBarcodeUtilities {
    protected final static Logger log = LogManager.getLogger();

    static Map<String, String> tonicBadBarcodeMap = null;

    public static Map<String, String> getTonicBadBarcodeMap() {

        if (tonicBadBarcodeMap == null) {
            tonicBadBarcodeMap = new HashMap<String, String>();
            tonicBadBarcodeMap.put("119593001BLUW-S", "119903");
            tonicBadBarcodeMap.put("119594001BLUW-M", "119904");
            tonicBadBarcodeMap.put("119595001BLUW-L", "119905");
            tonicBadBarcodeMap.put("119596001BLUW-XL", "119906");
            tonicBadBarcodeMap.put("119597002BLKW-S", "119907");
            tonicBadBarcodeMap.put("119598002BLKW-M", "119908");
            tonicBadBarcodeMap.put("119599002BLKW-L", "119909");
            tonicBadBarcodeMap.put("119560002BLKW-XL", "119910");
            tonicBadBarcodeMap.put("119601003NATW-S", "119911");
            tonicBadBarcodeMap.put("119602003NATW-M", "119912");
            tonicBadBarcodeMap.put("119603003NATW-L", "119913");
            tonicBadBarcodeMap.put("119604003NATW-XL", "119914");
            tonicBadBarcodeMap.put("119605004GRNW-S", "119915");
            tonicBadBarcodeMap.put("119606004GRNW-M", "119916");
            tonicBadBarcodeMap.put("119607004GRNW-L", "119917");
            tonicBadBarcodeMap.put("119608004GRNW-XL", "119918");
            tonicBadBarcodeMap.put("119638001BLUM-M", "119919");
            tonicBadBarcodeMap.put("119639001BLUM-L", "119920");
            tonicBadBarcodeMap.put("119640001BLUM-XL", "119921");
            tonicBadBarcodeMap.put("119641002BLKM-M", "119922");
            tonicBadBarcodeMap.put("119642002BLKM-L", "119923");
            tonicBadBarcodeMap.put("119643002BLKM-XL", "119924");
            tonicBadBarcodeMap.put("119644003NATM-M", "119925");
            tonicBadBarcodeMap.put("119645003NATM-L", "119926");
            tonicBadBarcodeMap.put("119646003NATM-XL", "119927");
            tonicBadBarcodeMap.put("119647004GRNM-M", "119928");
            tonicBadBarcodeMap.put("119648004GRNM-L", "119929");
            tonicBadBarcodeMap.put("119649004GRNM-XL", "119930");
        }
        return tonicBadBarcodeMap;
    }


    public static String getTranslatedBarcode(String barcode) {
        log.debug("testing barcode " + barcode);
        if (getTonicBadBarcodeMap().containsKey(barcode)) {
            log.debug("got translation:" + barcode);
            return getTonicBadBarcodeMap().get(barcode);
        } else

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

    public static String decodeClientSku(String barcode) {

        try {
            Pattern pat = Pattern.compile("^@@(\\d*):([.[^:]]*)");
            Matcher match = pat.matcher(barcode);
            //   log.debug("Matches: "+match.matches());
            //log.debug(match.lookingAt());

            if (match.matches()) {
                log.debug("match");
                log.debug(match.group(0));
                log.debug(match.group(1));
                log.debug(match.group(2));
                String clientId = match.group(1);
                String sku = match.group(2);

                String sql = "select inventory_id from owd_inventory where client_fkey = :clientId and inventory_num = :sku";
                Query q = HibernateSession.currentSession().createSQLQuery(sql);
                q.setParameter("clientId", clientId);
                q.setParameter("sku", sku);
                List results = q.list();
                if (results.size() == 1) {
                    return results.get(0).toString();
                } else {
                    return barcode;
                }


            } else {
                log.debug("No matchie");
            }


        } catch (Exception e) {
            e.printStackTrace();

        }


        return barcode;
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

    public static void calculate13DigitAndUpadateMap(Map<String, String> theList, int clientId) {
        try {
            Map<String, String> newList = new TreeMap<String, String>();
            for (String s : theList.keySet()) {
                String upc = isbncheckdigit(theList.get(s));
                if (barcodeCheck(upc).equals("UPC")) {
                    String sql = "update owd_inventory set upc_code = :upc where inventory_num = :inventory and client_fkey = :clientId";
                    Query q = HibernateSession.currentSession().createSQLQuery(sql);
                    q.setParameter("upc", upc);
                    q.setParameter("inventory", s);
                    q.setParameter("clientId", clientId);
                    int i = q.executeUpdate();
                    if (i > 0) {
                        HibUtils.commit(HibernateSession.currentSession());
                    } else {
                        log.debug("Failsed: " + s);
                    }
                    newList.put(s, upc);
                } else {
                    throw new Exception("Bad Calk");
                }


            }

            for (String s : newList.keySet()) {
                log.debug(s + "\t" + newList.get(s));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {

        System.out.println(barcodeCheck("123").equals("UPC"));


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

    public static HashMap checkMultiAgainstList(String barcode, int clientFkey, ArrayList skuList) throws Exception {
        //barcode = getTranslatedBarcode(barcode);
        String barcodeType = UpcBarcodeUtilities.barcodeCheck(barcode);
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
            if (skuList.contains(rs.getString(1)) == true) {
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

