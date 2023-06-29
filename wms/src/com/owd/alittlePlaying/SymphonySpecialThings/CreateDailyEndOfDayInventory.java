package com.owd.alittlePlaying.SymphonySpecialThings;

import com.owd.connectship.soap.ObjectFactory;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Criteria;
import org.hibernate.Query;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by danny on 2/16/2017.
 */
public class CreateDailyEndOfDayInventory {



    public static void main(String[] args){

      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
String dateString = "2016-09-30";
       try {
           Date date = sdf.parse(dateString);

           Calendar c = Calendar.getInstance();
           c.setTime(date);

           while (c.before(Calendar.getInstance())){
               CreateEODFile(sdf.format(c.getTime()));
               c.add(Calendar.DATE,1);

           }
       }catch (Exception e){
           e.printStackTrace();

       }




    }

    public static void CreateEODFile(String date){
           try{
               String sql = "execute sp_getinventorymovementsummarybygroupfacility :Date1,:date2,489,'G_halstonV2224','DC6'";
               Query q = HibernateSession.currentSession().createSQLQuery(sql);
               q.setParameter("Date1",date);
               q.setParameter("date2",date);
               q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

               List l = q.list();
               if(l.size()>0){
                   File f = new File("halston/"+ date + ".csv");
                   System.out.println(f.getAbsolutePath());
                   BufferedWriter writer = null;
                   writer = new BufferedWriter(new FileWriter(f));
                   writer.append("Barcode,SKU,UPC,Title,Item Group,Facility,Starting Quantity,Received,Adjusted,Returned,Shipped,Damaged,Ending Qantity\r\n");






                   for(Object row:l){
                       Map info = (Map) row;
                       writer.append(info.get("Barcode").toString());
                       writer.append(",");
                       writer.append(info.get("SKU").toString());
                       writer.append(",");
                       writer.append(info.get("UPC").toString());
                       writer.append(",");
                       writer.append(info.get("Title").toString());
                       writer.append(",");
                       writer.append(info.get("Item Group").toString());
                       writer.append(",");
                       writer.append(info.get("Facility").toString());
                       writer.append(",");
                       writer.append(info.get("Starting Quantity").toString());
                       writer.append(",");
                       writer.append(info.get("Received").toString());
                       writer.append(",");
                       writer.append(info.get("Adjusted").toString());
                       writer.append(",");
                       writer.append(info.get("Returned").toString());
                       writer.append(",");
                       writer.append(info.get("Shipped").toString());
                       writer.append(",");
                       writer.append(info.get("Damaged").toString());
                       writer.append(",");
                       writer.append(info.get("Ending Quantity").toString());
                       writer.append("\r\n");

                   }
                   writer.flush();
                   writer.close();


               }


           } catch (Exception e){

              e.printStackTrace();
           }




    }

}
