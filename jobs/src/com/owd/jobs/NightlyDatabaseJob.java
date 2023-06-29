package com.owd.jobs;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;

import java.util.List;

/**
 * Created by danny on 5/17/2017.
 */
public class NightlyDatabaseJob extends  OWDStatefulJob {

    public static void main(String[] args){
        run();

    }

    public void internalExecute(){

        try{

            String sql = "select value, display from app_data where project = 'jobs' and description = 'nightly' and variable = 'sql'";

            Query q = HibernateSession.currentSession().createSQLQuery(sql);

            List l = q.list();

            if(l.size()>0){

                for(Object data : l){
                    Object[] info = (Object[]) data;
                    System.out.println("doing: " + info[0].toString());

                    Query qq = HibernateSession.currentSession().createSQLQuery(info[1].toString());

                   int i =  qq.executeUpdate();

                    System.out.println(i);
                    if(i>0){
                        HibUtils.commit(HibernateSession.currentSession());
                    }


                }


            }



        }catch (Exception e){
            e.printStackTrace();
        }




    }
}
