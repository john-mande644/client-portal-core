package com.owd.alittlePlaying.edi;


import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class findtheIssue {


    public static void main(String[] args){

        String a = "563ALLJUSTBRAND";
        List<String> l = new ArrayList<>();
        l.add("8223928");
        l.add("8576194");
        l.add("8577149");
        l.add("8581948");
        l.add("8633510");
        l.add("8633375");
        l.add("8633332");
        l.add("8633759");
        l.add("8634002");
        l.add("8634215");
        l.add("8634661");
        l.add("8634486");
        l.add("8634509");
        l.add("8634930");
        l.add("8634897");
        l.add("8635225");
        l.add("8635375");
        l.add("8635746");
        l.add("8635969");
        l.add("8636491");
        l.add("8636469");
        l.add("8636653");
        l.add("8636946");
        l.add("8636956");
        l.add("8672313");
        l.add("8672308");
        l.add("8672311");
        l.add("8672307");
        l.add("8672310");
        l.add("8672313");

        Map<String,String> m = new HashMap<>();
        for(String s: l){

            m.put(s,whatsTheIssue(s,a));


        }


        System.out.print(m);
    }


    private static String whatsTheIssue(String docData, String account){
        String sql = "select docStatus from edi_docs where account = :account and docData like :data";
        docData = "%"+docData+"%";
        try{
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("account",account);
            q.setParameter("data",docData);
            List l = q.list();
            if(l.size()>0){
                return l.get(0).toString();
            }else{
                return "Nope";
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return "Error";
    }


}
