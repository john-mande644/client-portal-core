package com.owd.alittlePlaying.fileStuff;

import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by danny on 3/21/2017.
 */
public class lookupPackagesFromFile {


    public static void main(String[] args){
        doFile();


    }



    public static void doFile(){
        Map<String,String> m = new LinkedHashMap<String, String>();
        try{
            FileReader fileReader = new FileReader(new File("D:\\Symphony.txt"));

            BufferedReader br = new BufferedReader(fileReader);

            String line = null;
            // if no more lines the readLine() returns null
            while ((line = br.readLine()) != null) {
                // reading lines until the end of the file
                    System.out.println(line);
                String packages = getPackages(line);
                m.put(line,packages);
            }


            File f = new File("issues.csv");
            System.out.println(f.getAbsolutePath());
            BufferedWriter writer = null;
            try{
                writer = new BufferedWriter(new FileWriter(f));

                for(String key:m.keySet()){
                    writer.append(key+","+m.get(key)+"\r\n");


                }
                writer.flush();
                writer.close();

            }catch (Exception e){
                e.printStackTrace();
            }



        }catch (Exception e){
            e.printStackTrace();

        }



    }

    public static String getPackages(String orderRefNum){
        String s = "";
        try{

            String sql = "execute sp_getPackageDimsFromRefNum 489, :orderRefNum";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("orderRefNum",orderRefNum);
            List l = q.list();
            for(Object data:l){
                if(s.length()>0){
                    s = s+ "; ";
                }
                s = s+ data.toString();
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return s;
    }
}
