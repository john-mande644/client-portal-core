package com.owd.dc.locations.locationManagement;

import com.owd.dc.locations.locationInfoBean;
import com.owd.dc.locations.newLocationUtilities;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.WLocation;
import org.hibernate.Query;
import org.hibernate.Session;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 10/7/13
 * Time: 10:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class locateupdatestuff {
    public static void main(String[] args) {
    List<String> good = new ArrayList<String>();
        List<String> error = new ArrayList<String>();
        List<String> bad = new ArrayList<String>();
        try {
            String lookupsql = "SELECT top 2000\n" +
                    "    dbo.w_location.ixNode\n" +
                    "    \n" +
                    "FROM\n" +
                    "    dbo.w_location\n" +
                    "INNER JOIN\n" +
                    "    dbo.w_location_tree\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.w_location.ixNode = dbo.w_location_tree.ixNode)\n" +
                    "WHERE\n" +
                    "    dbo.w_location_tree.ixParent = 94101\n" +
                    "AND dbo.w_location.ixLocType = 10 \n" +
                    "and dbo.w_location.ixNode >104960\n" +
                    "order by ixNode;";
            Query q = HibernateSession.currentSession().createSQLQuery(lookupsql);

            List locations = q.list();


            for (Object o : locations) {
                String s = o.toString();
                try {
                    boolean b = checkParentEqualsPickString(s);
                    if (b) {
                        good.add(s);
                    } else {
                        bad.add(s);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    if(e.getMessage().contains("range")==false){
                        error.add(s);
                    }

                }

                Thread.sleep(200);
            }

            System.out.println("Good Ones");
            for (String s : good) {
                System.out.println(s);
            }
            System.out.println("Fixxed");
            for (String s : bad) {
                System.out.println(s);
            }
            System.out.println("Errors");

            File file = new File("testingdc7.txt");
            if(!file.exists()){
                file.createNewFile();
            }

            FileWriter fileWritter = new FileWriter(file.getName(),true);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            for (String s : error) {
                bufferWritter.write(s+"\r\n");
                System.out.println(s);
            }
            bufferWritter.close();

            System.out.println("Last: " + locations.get(locations.size() - 1));
            System.out.println(file.getAbsolutePath());
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static boolean checkParentEqualsPickString(String id)throws Exception{
        int falseCount = 0;
        String zoneId;
        String aisleId;
        String rackId;
        String shelfId;
        String sectionId;

        WLocation theLocation = (WLocation) HibernateSession.currentSession().load(WLocation.class,Integer.parseInt(id));
        if(null==theLocation.getPickString()) throw new Exception("range");
        System.out.println("This is the location String "+ theLocation.getPickString());
        String pickString = theLocation.getPickString().substring(0,theLocation.getPickString().indexOf("<"));
        System.out.println(pickString);
        String[] splitLocations = pickString.split("-");
        System.out.println(splitLocations.length);
        if(splitLocations.length<4 || splitLocations.length >5){
            throw new Exception("Not within range");

        }


        //get zone of pick String
        String zonesql = "SELECT\n" +
                "    dbo.w_location.ixNode\n" +

                "FROM\n" +
                "    dbo.w_location\n" +
                "WHERE\n" +
                "    dbo.w_location.ixParent = 94101\n" +
                "AND dbo.w_location.locname = :locname\n" +
                "And ixLocType = 6\n" +
                " ;";
        Query zoneq = HibernateSession.currentSession().createSQLQuery(zonesql);
        zoneq.setParameter("locname","Zone "+splitLocations[0]);
        List zoneresults = zoneq.list();
        if (zoneresults.size()==0 || zoneresults.size()>1){
            throw new Exception("Bad zone lookup");

        }
        zoneId = zoneresults.get(0).toString();
        System.out.println("Zone id: " + zoneId );
        String aislesql =  "SELECT\n" +
                "    dbo.w_location.ixNode\n" +

                "FROM\n" +
                "    dbo.w_location\n" +
                "WHERE\n" +
                "    dbo.w_location.ixParent = :zoneId\n" +
                "AND dbo.w_location.locname = :locname\n" +
                "And ixLocType = 7\n" +
                " ;";
        Query Aisleq = HibernateSession.currentSession().createSQLQuery(aislesql);
        Aisleq.setParameter("locname","Aisle "+splitLocations[1]);
        Aisleq.setParameter("zoneId",zoneId);
        List AisleResults = Aisleq.list();
        if(AisleResults.size()==0 || AisleResults.size()>1){
            throw new Exception("Unable to get aisle");
        }
        aisleId = AisleResults.get(0).toString();
        System.out.println("Aisle ID: "+aisleId);

        String Racksql =  "SELECT\n" +
                "    dbo.w_location.ixNode\n" +

                "FROM\n" +
                "    dbo.w_location\n" +
                "WHERE\n" +
                "    dbo.w_location.ixParent = :aisleId\n" +
                "AND dbo.w_location.locname = :locname\n" +
                "And ixLocType = 8\n" +
                " ;";
        Query Rackeq = HibernateSession.currentSession().createSQLQuery(Racksql);
        Rackeq.setParameter("locname","Rack "+splitLocations[2].replace("R",""));
        Rackeq.setParameter("aisleId",aisleId);
        List RackResults = Rackeq.list();
        if(RackResults.size()==0 || RackResults.size()>1){
            throw new Exception("Unable to get Rack");
        }
        rackId = RackResults.get(0).toString();
        System.out.println("Rack ID: "+rackId);


        String Shelfsql =  "SELECT\n" +
                "    dbo.w_location.ixNode\n" +

                "FROM\n" +
                "    dbo.w_location\n" +
                "WHERE\n" +
                "    dbo.w_location.ixParent = :rackId\n" +
                "AND dbo.w_location.locname = :locname\n" +
                "And ixLocType = 9\n" +
                " ;";
        Query Shelfq = HibernateSession.currentSession().createSQLQuery(Shelfsql);
        Shelfq.setParameter("locname","Shelf "+splitLocations[3].replace("S",""));
        Shelfq.setParameter("rackId",rackId);
        List ShelfResults = Shelfq.list();
        if(ShelfResults.size()==0 || ShelfResults.size()>1){
            throw new Exception("Unable to get Shelf");
        }
        shelfId = ShelfResults.get(0).toString();
        System.out.println("Shelf ID: "+shelfId);

        String parentIdFromPickString="";
        if(splitLocations.length ==4){
            parentIdFromPickString = shelfId;
        }
        if(splitLocations.length ==5){
            String Sectionsql =  "SELECT\n" +
                    "    dbo.w_location.ixNode\n" +

                    "FROM\n" +
                    "    dbo.w_location\n" +
                    "WHERE\n" +
                    "    dbo.w_location.ixParent = :shelfId\n" +
                    "AND dbo.w_location.locname = :locname\n" +
                    "" +
                    " ;";
            Query sectionq = HibernateSession.currentSession().createSQLQuery(Sectionsql);
            sectionq.setParameter("locname","Section "+splitLocations[4]);
            sectionq.setParameter("shelfId",shelfId);
            List SectionResults = sectionq.list();
            if(SectionResults.size()==0 || SectionResults.size()>1){
                throw new Exception("Unable to get section or pallet");
            }
            sectionId = SectionResults.get(0).toString();
            System.out.println("Section ID: "+sectionId);
            parentIdFromPickString = sectionId;
        }
        System.out.println("This is what the pickString id is: "+ parentIdFromPickString);
        System.out.println("This is what the parent id is now: "+ theLocation.getParentId());


if(theLocation.getParentId()== Integer.parseInt(parentIdFromPickString)){
    return true;
}
        System.out.println("We need to so a fix");
        theLocation.setParentId(Integer.parseInt(parentIdFromPickString));
        HibernateSession.currentSession().save(theLocation);
        HibUtils.commit(HibernateSession.currentSession());

return false;

    }


    public static void otherstuff() {
        //   locationInfoBean lib = new locationInfoBean("61147",HibernateSession.currentSession());
        //  System.out.println(lib.getId());

        //  String coutnsql = "select count(ixNode) from w_location where pickString is null";

      /*  Session sess = HibernateSession.currentSession();
        //  Query q = sess.createSQLQuery(coutnsql);
        //  System.out.println(q.list().get(0).toString());
        int i = 500;
        while (i > 0){

            String sql = "select ixNode from w_location where ixLocType = 11 and pickString like 'S-R%' order by ixNode DESC";
            Query qq = sess.createSQLQuery(sql);
            List results = qq.list();

            for (Object data : results){
                System.out.println("Doing "+data.toString() +"XXXXXXXXXXXXXXXXXXXXXXXXXXX");
                newLocationUtilities.updatePickStrings(sess, Integer.parseInt(data.toString()));
                i--;

                Thread.sleep(1500);
            }*/

    }
}


