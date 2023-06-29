package com.owd.dc.warehouse.vendorCompliance.labelsMaker.inventoryLabels;

import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;

import java.util.List;

/**
 * Created by danny on 10/23/2016.
 */
public class AmazonInventoryLabel extends vendorComplianceInventoryLabelBase{


    public static void main(String args[]){
            AmazonInventoryLabel label = new AmazonInventoryLabel();
        try {
            label.printInventoryLabels("228", "2", "10.17.29.157");
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public String getLabelString(String id) throws Exception{
        StringBuilder sb = new StringBuilder();
        String sku = "";
        String des = "";
        String sql = "select foreign_sku, foreign_desc from owd_inventory_sku_maps where id = :id";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("id",id);
        List l = q.list();
        if(l.size()>0){
            Object[] data = (Object[]) l.get(0);
            sku = data[0].toString();
            des = data[1].toString();


        }else{
            throw new Exception("Could not load label data");
        }
        System.out.println(sku);
        System.out.println(des);
        sb.append("^XA~TA000~JSN^LT0^MNW^MTD^PON^PMN^LH0,0^JMA^PR6,6~SD15^JUS^LRN^CI0^XZ");
        sb.append("^XA");
        sb.append("^MMT");
        sb.append("^PW406");
        sb.append("^LL0203");
        sb.append("^LS0");
        sb.append("^BY2,3,58^FT56,93^BCN,,N,N");
        sb.append("^FD>:");
        sb.append(sku);
                sb.append("^FS");
        sb.append("^FT52,177^A0N,23,24^FH\\^FD");
        sb.append(des);
        sb.append("^FS");
        sb.append("^FT51,146^A0N,23,24^FH\\^FD");
        sb.append(sku);
        sb.append("^FS");
        sb.append("^PQ1,0,1,Y^XZ");





       return sb.toString();
    }





}
