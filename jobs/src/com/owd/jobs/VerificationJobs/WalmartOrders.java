package com.owd.jobs.VerificationJobs;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;

import org.apache.xpath.XPathAPI;

import org.hibernate.Query;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by danny on 10/3/2017.
 */
public class WalmartOrders extends OWDStatefulJob{




    public static void main(String[] args){

        run();

    }
    public  void internalExecute(){

        String sql = "select id, data from walmart_edi_incoming where created > '2017-10-01' and source = 'walmartdsv471' and verified = 0";
        try{
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            List results = q.list();
            for(Object row: results){
                Object[] data = (Object[]) row;

                List<String> bad = parseFile(convertClobToString((Clob) data[1]));
                if(bad.size()>0){
                    System.out.println("WE have a bad: "+data[0].toString());
                    System.out.println(bad);
                }else{
                    Query qq = HibernateSession.currentSession().createSQLQuery("update walmart_edi_incoming set verified = 1 where id = :id");
                    qq.setParameter("id",data[0].toString());
                    qq.executeUpdate();
                    HibUtils.commit(HibernateSession.currentSession());
                }


            }
        }catch (Exception e){
            e.printStackTrace();
        }





    }


    public List<String> parseFile(String s){

        List<String> bad = new ArrayList<>();
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(false);
            dbf.setValidating(false);
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document dom = db.parse(new ByteArrayInputStream(s.getBytes()));
            // System.out.println(dom.getNodeValue());
            NodeIterator pack = XPathAPI.selectNodeIterator(dom, "./WMI/WMIORDERREQUEST/OR_ORDER");

            Node ns;

            while((ns = pack.nextNode())!=null){
                System.out.println(ns.getAttributes().getNamedItem("ORDERNUMBER").getNodeValue());

                String sql = "select order_id from owd_order where client_fkey = :fkey and order_refnum = :ref";
                Query q = HibernateSession.currentSession().createSQLQuery(sql);
                q.setParameter("fkey",471);
                q.setParameter("ref",ns.getAttributes().getNamedItem("ORDERNUMBER").getNodeValue());
                List results = q.list();

                if(results.size()==0){
                    bad.add(ns.getAttributes().getNamedItem("ORDERNUMBER").getNodeValue());
                }

            }
            System.out.println("Number of bad: "+bad.size());


        }catch (Exception e){
            e.printStackTrace();
        }
        return bad;
    }
    public static String convertClobToString(Clob clob) throws IOException, SQLException {
        Reader reader = clob.getCharacterStream();
        int c = -1;
        StringBuilder sb = new StringBuilder();
        while((c = reader.read()) != -1) {
            sb.append(((char)c));
        }

        return sb.toString();
    }


}
