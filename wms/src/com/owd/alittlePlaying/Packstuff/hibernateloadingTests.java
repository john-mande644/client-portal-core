package com.owd.alittlePlaying.Packstuff;

import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OWDPackage;
import com.owd.hibernate.generated.OwdClientBillTypes;
import com.owd.hibernate.generated.PackageLine;
import com.owd.hibernate.generated.PackageOrder;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by danny on 6/6/2017.
 */
public class hibernateloadingTests {


    public static void main(String[] args){

        getPackageOrder(12003205);

    }


    public static PackageOrder getPackageOrder(int orderId){
        PackageOrder porder = new PackageOrder();
        try{
            System.out.println("Lets test");
            Criteria criteria = HibernateSession.currentSession().createCriteria(PackageOrder.class);
            criteria.add(Restrictions.eq("owdOrderFkey",orderId));
            criteria.add(Restrictions.eq("isVoid",0));

            List<PackageOrder> btype = criteria.list();
            System.out.println(btype.size());
            for(PackageOrder bt:btype){

                System.out.println(bt.getPackerRef());
                System.out.println(bt.getFacility());
                System.out.println(bt.getVoidBy());
                for(OWDPackage pack:bt.getPackages()){
                    System.out.println(pack.getPackBarcode());
                    System.out.println(pack.getId());

                   System.out.println(pack.getPackageLines().size());
                  for(PackageLine line: pack.getPackageLines()){
                      System.out.println(line.getId());
                  }

                }

                porder = bt;
            }
        } catch (Exception e){
            e.printStackTrace();
        }



        return porder;
    }
}
