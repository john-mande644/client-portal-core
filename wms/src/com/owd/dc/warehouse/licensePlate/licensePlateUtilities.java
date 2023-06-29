package com.owd.dc.warehouse.licensePlate;

import com.owd.core.dbUtilities;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateAdHocSession;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

import java.util.List;

/**
 * Created by danny on 12/3/2016.
 */
public class licensePlateUtilities {
    public static final String kPackagingIdPrefix = "PPID";


    public static void main(String args[]){
        try {
          //  HibernateSession.currentSession();
          //  System.out.println(getBoxIdFromLicensePlate("PP:48:00003"));
         //   System.out.println(getBoxIdFromLicensePlate("PP:4800003"));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
       // System.out.println("hello");
        System.out.println(canThisPackageLicenseBeUsed("//tote-1"));

    }

    //extract box id which will be in between 2 colons example PP:48:000023
    public static String getBoxIdFromLicensePlate(String license)throws Exception{
        String[] parts = license.split(":");
       // System.out.println(parts);
        if(parts.length!=3){
            throw new Exception("Unable to extract Box Id");
        }
        return parts[1];




    }
    public static String getNextPackagingId() throws Exception{

        return dbUtilities.getNextIDSession(kPackagingIdPrefix);

    }
    public static boolean canThisPackageLicenseBeUsed(String lp){
        boolean canUse = false;
        try {
            String sql = "execute selectOrderIdFromLicensePlate :lp";
            SQLQuery q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setString("lp", lp);
            List l = q.list();
            System.out.println(l.size());
            if(l.size()==0){



                canUse = true;

            }else{
                if(lp.startsWith("//tote-")) {
                    System.out.println(l.get(0).toString());
                    //check for already shipped order and clear
                    OwdOrder o = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, Integer.parseInt(l.get(0).toString()));
                    if (o.getOrderStatus().equals("Shipped")) {
                        String s = "update owd_order set license_plate = '' where order_id = :id";
                        Query qq = HibernateSession.currentSession().createSQLQuery(s);
                        qq.setParameter("id",l.get(0).toString());
                        int i = qq.executeUpdate();
                        if(i>0){
                            HibUtils.commit(HibernateSession.currentSession());
                            canUse = true;

                        }

                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }



        return canUse;
    }
    public static String getPackagingLicensePlateBarcode(String prefix,int boxId,String packagingId){

        return getPackagingLicensePlateBarcode(prefix,boxId+"",packagingId);
    }
    public static String getPackagingLicensePlateBarcode(String prefix,String boxId,String packagingId){

        return prefix + ":"+ boxId + ":" + packagingId;
    }


    public static String getPackagingLicensePlateLabel(String barcode, String id, String name,boolean lastLabel){
      StringBuilder sb = new StringBuilder();
        sb.append("^XA~TA000~JSN^LT0^MNW^MTD^PON^PMN^LH0,0^JMA^PR6,6~SD15^JUS^LRN^CI0^XZ");
        sb.append("^XA");
        sb.append("^MMT");
                sb.append("^PW406");
                        sb.append("^LL0203");
                                sb.append("^LS0");

        sb.append("^BY2,3,58^FT12,96^BCN,,N,N");
        sb.append("^FD>:");
        sb.append(barcode);


        sb.append("^FS");
        sb.append("^FT122,180^A0N,23,24^FH\\^FD");
        sb.append(name);
        sb.append("^FS");
        sb.append("^FT135,127^A0N,23,24^FH\\^FD");
        sb.append(barcode);
        sb.append("^FS");
        if(!lastLabel){
            sb.append("^XB");
        }
        sb.append("^PQ1,0,1,Y^XZ");

        return sb.toString();

    }
}
