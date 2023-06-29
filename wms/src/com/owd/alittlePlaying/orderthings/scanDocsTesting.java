package com.owd.alittlePlaying.orderthings;

import com.owd.core.OWDUtilities;
import com.owd.core.managers.ScanManager;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.generated.ScanOrder;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by danny on 10/8/2019.
 */
public class scanDocsTesting {

    public static void main(String[] args){
        try{

            OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,19142235);
            Collection<ScanOrder> scans = order.getScanDocs();

            System.out.println(scans);

            for(ScanOrder s:scans){
                System.out.println("https://service.owd.com/webapps/extranet/extranet.jsp?ordermgr=get-scan&oid="+19142235+"&auth="+ OWDUtilities.encryptData(19142235+"/"+s.getName()+"/"+640));
            }



        }catch (Exception e){
            e.printStackTrace();
        }



    }
}
