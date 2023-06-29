package com.owd.jobs.jobobjects.spscommerce

import com.owd.hibernate.HibernateSession
import com.owd.hibernate.generated.EdiDocs
import com.owd.hibernate.generated.EdiSpsConfigdata
import org.hibernate.Query

import java.nio.file.Files
import java.nio.file.Paths

/**
 * Created by danny on 11/16/2016.
 */
class checkForNonMappedSkus {

    static List<String> bad = new ArrayList<>()
    static List<String> pos = new ArrayList<>()

    public static void main(String[] args){


        List<EdiDocs> docsToProcess = HibernateSession.currentSession().createQuery("from EdiDocs where id=26281").list()

        for(EdiDocs d:docsToProcess){


            checkDoc(d.getDocData())
            getPoAndConsumer(d.getDocData(),d.getSourceFile());


        }

     //   String poData = new String(Files.readAllBytes(Paths.get("C:\\Users\\danny\\Documents\\GitHub\\core\\jobs\\src\\com\\owd\\jobs\\jobobjects\\spscommerce", "testorder.xml")))


        for(String s:pos){
            System.out.println(s);
        }
       println(bad)

    }


    public static void getPoAndConsumer(String s,String fileName){
        StringBuilder sb = new StringBuilder();
        sb.append(fileName);
        sb.append(",");

        XmlParser parser = new XmlParser()
        parser.setTrimWhitespace(true)
        Node po =       parser.parseText(s)
        EdiSpsConfigdata config = SPSCommerceBaseClient.getEdiConfigData(po)
        sb.append(po.Header.OrderHeader.PurchaseOrderNumber.text());
        sb.append(",")
        sb.append(po.Header.OrderHeader.CustomerOrderNumber.text());
        sb.append(",")
        sb.append(config.getName());

        pos.add(sb.toString());




    }


    public static void checkDoc(String s){

        try {


            XmlParser parser = new XmlParser()
            parser.setTrimWhitespace(true)
            Node po =       parser.parseText(s)
            EdiSpsConfigdata config = SPSCommerceBaseClient.getEdiConfigData(po)

            po.LineItems.LineItem.OrderLine.each {

                println(it.ConsumerPackageCode.text())
                try{

                   String ss = SpsCommerceUtilities.getOwdSkuFromUpc(config,it.ConsumerPackageCode.text())
                    if(null==ss) {
                        if (!bad.contains(it.ConsumerPackageCode.text()))
                        {

                        bad.add(it.ConsumerPackageCode.text())
                    }
                    }

                }catch(Exception e){
                    bad.add(it.ConsumerPackageCode.text())

                }

            }


        }catch(Exception e){
            e.printStackTrace();

        }




    }






}
