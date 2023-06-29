package com.owd.dc.manifest.OSMWorldwide;

import com.owd.core.Mailer;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;


import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 12/4/14
 * Time: 2:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class OSMEndOFDayUtils {

      public static String OSMDomestic = "DM";
    public static String OSMInternational = "IN";

    public static void main(String[] args){
        try{
         //  System.out.println(createEndOfDayRecordOSM(OSMInternational));
           updatePackageInfoForCloseOutCode("IN20141204-1");
         //createFileForCloseOutCodeInternational("IN20141204-1");
         //   sendEndOfDayEmail("IN20141204-1",OSMInternational);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String createEndOfDayRecordOSM(String type) throws Exception{
       //check for file already created for today
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.US);
        String dayPart = formatter.format(Calendar.getInstance().getTime());

        String checkForFile = "select OSMCloseOutCode from closeout_OSM where OSMCloseOutCode like :code order by id";
        Query q = HibernateSession.currentSession().createSQLQuery(checkForFile);
        q.setParameter("code",dayPart+"%");
        List results = q.list();
        int ending = 1;
        if (results.size()>0){
            //todo grab number and add to it
        }
        String code = type+dayPart+"-"+ending;
        String insert = "insert into closeout_OSM(OSMCloseOutCode, shipment_type) values(:code,:type)";
        Query update = HibernateSession.currentSession().createSQLQuery(insert);
        update.setParameter("code",code);
        update.setParameter("type",type);
        int ii = update.executeUpdate();
        if (ii==1){
            HibUtils.commit(HibernateSession.currentSession());
            return code;
        }
      throw new Exception("Unknown error creating new record for closeout");

    }


    public static void markShippedPackagesOnCloseOut(String closeoutCode,String type) throws Exception{
        String sql = "update package_order \n" +
                "set OSMCloseOutCode = 'IN20141204-1'\n" +
                "FROM\n" +
                "dbo.package_order\n" +
                "    \n" +
                "INNER JOIN dbo.owd_order_ship_info\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order_ship_info.order_fkey = dbo.package_order.owd_order_fkey\n" +
                "    )\n" +
                "WHERE\n" +
                "    dbo.package_order.OSMCloseOutCode IS NULL\n" +
                "AND dbo.package_order.is_void = 0\n" +
                "AND dbo.owd_order_ship_info.carr_service ='OSM Worldwide'\n" +
                "AND dbo.package_order.packs_shipped > 0 ;";





    }

    public static void updatePackageInfoForCloseOutCode(String closeoutCode) throws Exception{
        String sql = "SELECT\n" +
                "    COUNT(dbo.package.id) AS totalPacks,\n" +
                "    SUM(\n" +
                "        CASE\n" +
                "            WHEN weight_lbs<1\n" +
                "            THEN 1\n" +
                "            ELSE 0\n" +
                "        END) AS UnderPound,\n" +
                "    SUM(\n" +
                "        CASE\n" +
                "            WHEN weight_lbs>=1\n" +
                "            THEN 1\n" +
                "            ELSE 0\n" +
                "        END) AS OverPound,\n" +
                "    SUM(\n" +
                "        CASE\n" +
                "            WHEN ship_country = 'CANADA'\n" +
                "            THEN 1\n" +
                "            ELSE 0\n" +
                "        END)                    AS Canada,\n" +
                "    SUM(dbo.package.weight_lbs) AS Total_Weight\n" +
                "FROM\n" +
                "    dbo.package_order\n" +
                "INNER JOIN dbo.package\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.package_order.id = dbo.package.package_order_fkey\n" +
                "    )\n" +
                "INNER JOIN dbo.owd_order_ship_info\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.package_order.owd_order_fkey = dbo.owd_order_ship_info.order_fkey\n" +
                "    )\n" +
                "WHERE\n" +
                "    dbo.package_order.OSMCloseOutCode = :code ;";

        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("code",closeoutCode);
        List results = q.list();
        if (results.size()>0){
            Object[] data = (Object[]) results.get(0);
            System.out.println("total packs");
            System.out.println(data[0].toString());
            String update = "update closeout_OSM set number_of_packages = :number, pkgs_under_pound = :under, pkgs_over_pound = :over, pkgs_canada = :canada, total_weight = :weight where OSMCloseOutCode = :code";
            Query qq = HibernateSession.currentSession().createSQLQuery(update);
            qq.setParameter("number",data[0].toString());
            qq.setParameter("under",data[1].toString());
            qq.setParameter("over",data[2].toString());
            qq.setParameter("canada",data[3].toString());
            qq.setParameter("weight",data[4].toString());
            qq.setParameter("code",closeoutCode);
             int updated = qq.executeUpdate();
            if (updated ==0){
                throw new Exception("UPdate of package data failed");
            }
            HibUtils.commit(HibernateSession.currentSession());
        } else{
            throw new Exception("Could not find info on the code");
        }

    }

    public static void createFileForCloseOutCodeInternational(String code) throws Exception{
        DateFormat formatter = new SimpleDateFormat("MMddyyHHmm", Locale.US);
        String dayPart = formatter.format(Calendar.getInstance().getTime());

        String fileName = "INTLPHOTOJOJO"+dayPart;
        StringBuffer sb = new StringBuffer();
        sb.append("Package Id,Company,Full Name,Address 1,Address 2,City,State,Zip,Country,Cost Center Id,Reference 1,Reference 2,Reference 3,Reference 4,Total package weight,Item Id,Content Type,Item description,Item quantity,Unit Weight,Unit Value,Country of Origin\n");

        //query the data
        String sql = "execute endOfDayOSMInternational :code ;";
               Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        q.setParameter("code",code);
        List results = q.list();
        System.out.println("11");
        for (Object row : results){
            Map data = (Map)row;
            System.out.println(data.get("name"));

            //Package Id
            sb.append("\"");
            sb.append(data.get("pack_barcode").toString().replaceAll("\\*", "-"));
            sb.append("\"");
            sb.append(",");
//Company
            sb.append("\"");
            sb.append(data.get("ship_company_name"));
            sb.append("\"");
            sb.append(",");

//Full Name
            sb.append("\"");
            sb.append(data.get("name"));
            sb.append("\"");
            sb.append(",");

//Address 1
            sb.append("\"");
            sb.append(data.get("ship_address_one"));
            sb.append("\"");
            sb.append(",");
//Address 2
            sb.append("\"");
            sb.append(data.get("ship_address_two"));
            sb.append("\"");
            sb.append(",");
//City
            sb.append("\"");
            sb.append(data.get("ship_city"));
            sb.append("\"");
            sb.append(",");

//State
            sb.append("\"");
            sb.append(data.get("ship_state"));
            sb.append("\"");
            sb.append(",");

//Zip
            sb.append("\"");
            sb.append(data.get("ship_zip"));
            sb.append("\"");
            sb.append(",");
//Country
            sb.append("\"");
            sb.append(data.get("ship_country"));
            sb.append("\"");
            sb.append(",");
//Cost Center Id

            sb.append(",");
//Reference 1
            sb.append("\"");
             sb.append(data.get("order_id"));
            sb.append("\"");
            sb.append(",");

//Reference 2
            sb.append("\"");
            sb.append(data.get("order_num"));
            sb.append("\"");
            sb.append(",");
//Reference 3
            sb.append(",");
//Reference 4
            sb.append(",");
//Total package weight
            sb.append("\"");
            sb.append(data.get("pkgweight"));
            sb.append("\"");
            sb.append(",");
//Item Id
            sb.append("\"");
            sb.append(data.get("ItemId"));
            sb.append("\"");
            sb.append(",");
//Content Type
            sb.append("\"");
            sb.append("OTHER");
            sb.append("\"");
            sb.append(",");
//Item description
         //   Clob desc = (Clob) data.get("customs_desc");
            sb.append("\"");
            sb.append(data.get("customs_desc"));
            sb.append("\"");
            sb.append(",");
//Item quantity
            sb.append("\"");

            sb.append(data.get("quantity_actual"));
            sb.append("\"");
            sb.append(",");

//Unit Weight
            sb.append("\"");
            sb.append(data.get("weight_lbs"));
            sb.append("\"");
            sb.append(",");
//Unit Value
            sb.append("\"");
            sb.append(data.get("declared_value"));
            sb.append("\"");
            sb.append(",");
//Country of Origin
            sb.append("\"");
            sb.append("US");
            sb.append("\"");

            sb.append("\n");


        }
       System.out.println("We are done building file");
        System.out.println(sb.toString());




    }
  /*  public static String convertClobToString(Clob clob) throws IOException, SQLException {
        Reader reader = clob.getCharacterStream();
        int c = -1;
        StringBuilder sb = new StringBuilder();
        while((c = reader.read()) != -1) {
            sb.append(((char)c));
        }

        return sb.toString();
    }
*/
    public static boolean sendEndOfDayEmail(String todayCode, String shipmentType) {
       boolean complete = false;
           try{
           //pull data for today and any non closed out shipments.
           reportDataBean today = new reportDataBean();
               List<reportDataBean> others = new ArrayList<reportDataBean>();
            String sql = "select number_of_packages, total_weight from closeout_OSM where OSMCloseOutCode = :code";
               Query q = HibernateSession.currentSession().createSQLQuery(sql);
               q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
               q.setParameter("code",todayCode);
               List results = q.list();
               if(results.size()==0) throw new Exception("Unable to find data for CloseOUtCode "+todayCode);
               Map data = (Map) results.get(0);
               today.setCloseOutCode(todayCode);
               today.setPackages(data.get("number_of_packages").toString());
               today.setWeight(data.get("total_weight").toString());


               //Load rest of unshipped data
               sql = "select OSMCloseOutCode, number_of_packages, total_weight from closeout_OSM where bol_created = 0";
               q = HibernateSession.currentSession().createSQLQuery(sql);
               q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
               results = q.list();
               int totalPackages = 0;
               double totalWeight = 0.0;

               for(Object row: results){
                   data = (Map)row;
                   reportDataBean bean = new reportDataBean();
                   bean.setCloseOutCode(data.get("OSMCloseOutCode").toString());
                   bean.setPackages(data.get("number_of_packages").toString());
                   totalPackages = totalPackages + Integer.parseInt(data.get("number_of_packages").toString());
                   totalWeight = totalWeight + Double.parseDouble(data.get("number_of_packages").toString());
                   bean.setWeight(data.get("total_weight").toString());
                   others.add(bean);

               }




           //format and send out email
             StringBuffer body = new StringBuffer();
               body.append("Todays Shipment Information:\r\n\r\n");
               body.append("Close Out Code: ");
               body.append(today.getCloseOutCode());
               body.append("\t\t");
               body.append("Number of packages: ");
               body.append(today.getPackages());
               body.append("\t\t");
               body.append("Total Weight: ");
               body.append(today.getWeight());
               body.append("\r\n\r\n");
               body.append("------------------------------------------------------------------------------------------");
               body.append("\r\n\r\n");
               body.append("All Open Shipments still in Warehouse\r\n\r\n");
               body.append("Close Out Code");
               body.append("\t\t");
               body.append("Number of packages");
               body.append("\t\t");
               body.append("Weight");
               body.append("\r\n");
               for(reportDataBean bean:others){
                   body.append(bean.getCloseOutCode());
                   body.append("\t\t\t");
                   body.append(bean.getPackages());
                   body.append("\t\t\t\t\t");
                   body.append(bean.getWeight());
                   body.append("\r\n");
               }
               body.append("------------------------------------------------------------------------------------------");
               body.append("\r\n\r\nTotal Summary in Warehouse:\r\n\r\n");
               body.append("Packages: ");
               body.append(totalPackages);
               body.append("\r\n");
               body.append("Weight: ");
               body.append(totalWeight);

               System.out.println(body.toString());
               String Subject = "OSM Daily Report";
               Vector<String> toAddress = new Vector<String>();
              // toAddress.add("dnickels@owd.com");
               //toAddress.add("glindskov@owd.com");

               //Mailer.postMailMessage(Subject,body.toString(),toAddress,"NoReply@owd.com");
               complete = true;
           } catch (Exception e){
               e.printStackTrace();
           }



       return complete;
    }

}
