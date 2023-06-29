package com.owd.dc.packing.beans.Lyft;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.List;

/**
 * Created by danny on 6/15/2017.
 */
public class LookupUtils {


    public static void main(String[] args){



        System.out.println(getLabelFromOrderId(12339679+""));


    }




    public static String getLabelFromOrderId(String orderId){
        ObjectFactory of = new ObjectFactory();
        LyftReturnLookupType lrt = of.createLyftReturnLookupType();
        try{
            String sql = ("SELECT\n" +
                    "    Convert(varchar(250),dbo.zzLyftDuplicates.labelString) as label,\n" +
                    "    replace(dbo.owd_order.order_num_barcode,'*','') as barcode,\n" +
                    "    isnull(dbo.zzLyftDuplicates.printed,0)as printed\n" +
                    "FROM\n" +
                    "    dbo.zzLyftDuplicates\n" +
                    "INNER JOIN\n" +
                    "    dbo.owd_order\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.zzLyftDuplicates.newOrderId = dbo.owd_order.order_id)\n" +
                    "WHERE\n" +
                    "    dbo.zzLyftDuplicates.orderFkey = :orderId ;");
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("orderId",orderId);
            List l = q.list();

            if(l.size()==1){
                Object data = l.get(0);
                Object[] row = (Object[]) data;
                if(row[2].toString().equals("true")){
                    lrt.setError("This order has already been printed");
                }else{
                    lrt.setBarcode(row[1].toString());
                    lrt.setLabelUrl(row[0].toString());
                    lrt.setError("");
                    String sqlq = "update zzLyftDuplicates set printed = :printed where orderFkey = :orderId";
                    Query qq = HibernateSession.currentSession().createSQLQuery(sqlq);
                    qq.setParameter("printed",1);
                    qq.setParameter("orderId",orderId);

                    int i = qq.executeUpdate();
                    if(i>0){
                        HibUtils.commit(HibernateSession.currentSession());
                    }else{
                        //bad.add(orderId);
                    }
                }


            }else if(l.size()>1){
                lrt.setError("Too many lookups. Contact IT");

            }else{
                lrt.setError("Not Found");
            }



        }catch (Exception e){
            e.printStackTrace();
            lrt.setError(e.getMessage());


        }


        try{
            JAXBContext jaxbContext = JAXBContext.newInstance(LyftReturnLookupType.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal( of.createLyftReturnLookup(lrt),sw);
            return sw.toString();
        }catch(Exception e){
            return e.getMessage();
        }



    }



}
