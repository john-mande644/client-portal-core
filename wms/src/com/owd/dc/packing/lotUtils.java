package com.owd.dc.packing;

import com.owd.core.managers.LotManager;
import com.owd.dc.packing.beans.lotValuesBean;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdLotPackageLine;
import com.owd.hibernate.generated.OwdLotValue;
import com.owd.hibernate.generated.PackageLine;
import com.thoughtworks.xstream.XStream;
import org.apache.xpath.XPathAPI;
import org.hibernate.Query;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * Created by danny on 4/19/2016.
 */
public class lotUtils {


    public static void main(String[] args){
        /* String xml = "<OWDPack>" +
                 "<order>" +
                 "<orderfkey>10064416</orderfkey>" +
                 "<packer>51</packer>" +
                 "<start>4/19/2016 10:56:43 PM</start>" +
                 "<stop>4/19/2016 10:57:07 PM</stop>" +
                 "<barcode>*17331861*</barcode>" +
                 "<facility>DC1</facility>" +
                 "<box>" +
                 "<fkey>48</fkey>" +
                 "<cost>0.1500</cost>" +
                 "<depth>7.5000</depth>" +
                 "<width>12.0000</width>" +
                 "<height>0.8000</height>" +
                 "<number>1</number>" +
                 "<weight>1</weight>" +
                 "</box>" +
                 "<items>" +
                 "<line>" +
                 "<fkey>24263402</fkey>" +
                 "<qty>5</qty>" +
                 "</line>" +
                 "</items>" +
                 "</order>" +
                 "<last>False</last>" +
                 "<AdditionalInfo>" +
                 "</AdditionalInfo>" +
                 "<LotInfo>" +
                 "<LotInfoItem>" +
                 "<lineFkey>24263402</lineFkey>" +
                 "<values>" +
                 "<lot>" +
                 "<lotValue>123456</lotValue>" +
                 "<qty>5</qty>" +
                 "<inventoryFkey>" +
                 "121672"+
                 "</inventoryFkey>" +
                 "</lot>" +
                 "</values>" +
                 "</LotInfoItem>" +
                 "</LotInfo>" +
                 "</OWDPack>";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(false);
        factory.setValidating(false);
            try {
// Now use the factory to create a DOM parser (a.k.a. a DocumentBuilder)
                DocumentBuilder parser = factory.newDocumentBuilder();
// Parse the file and build a Document tree to represent its content
                Document document = parser.parse(new ByteArrayInputStream(xml.getBytes()));
//get the order node
                Node owdPack = XPathAPI.selectSingleNode(document, "/OWDPack");
                NodeIterator lotInfoItems = XPathAPI.selectNodeIterator(owdPack,"./LotInfo/LotInfoItem");
                System.out.println(lotInfoItems);
                Node lotNode;
                while((lotNode = lotInfoItems.nextNode())!= null) {
                    String lineFkey = XPathAPI.eval(lotNode,"lineFkey").toString();
                    System.out.println( XPathAPI.eval(lotNode,"lineFkey").toString());
                    NodeList nl = XPathAPI.selectNodeList(lotNode,"./values/lot");

                    for (int i = 0; i< nl.getLength();i++){
                        Node valueNode = nl.item(i);
                        Integer inventoryFkey = Integer.parseInt(XPathAPI.eval(valueNode,"inventoryFkey").toString());
                        String lotValue = XPathAPI.eval(valueNode,"lotValue").toString();
                        System.out.printf("Line Fkey for lots: %s%n",lineFkey);
                        System.out.println(lotValue);
                    }

                }


            }catch (Exception e){
                e.printStackTrace();
            }
*/
        try {
            System.out.println(getLotValuesForSkuXML("306834", "DC6"));
        }catch (Exception e){
            e.printStackTrace();
        }

    }



    public static void insertOwdLotPackageLine( NodeIterator lotInfoItems, String packageId) throws Exception{
          Node lotNode;
        while((lotNode = lotInfoItems.nextNode())!= null){
            String lineFkey = XPathAPI.eval(lotNode,"lineFkey").toString();
            NodeList nl = XPathAPI.selectNodeList(lotNode,"./values/lot");

            for (int i = 0; i< nl.getLength();i++){
                Node valueNode = nl.item(i);
                Integer inventoryFkey = Integer.parseInt(XPathAPI.eval(valueNode,"inventoryFkey").toString());
                 String lotValue = XPathAPI.eval(valueNode,"lotValue").toString();
                System.out.printf("Line Fkey for lots: %s%n",lineFkey);
                System.out.println(lotValue);
                if(LotManager.isLotValueValidForInventoryId(lotValue,inventoryFkey)) {
                    String packageLineId = getPackageLineId(packageId,lineFkey);

                    OwdLotValue owdLot = LotManager.getExistingOwdLotValueForString(lotValue,inventoryFkey);
                    PackageLine pLine =  getPackageLineFromString(packageId,lineFkey);
                            OwdLotPackageLine owdlotPackage = new OwdLotPackageLine();
                    owdlotPackage.setLotValue(owdLot);
                    owdlotPackage.setPackageLine(pLine);
                    owdlotPackage.setQty(Integer.parseInt(XPathAPI.eval(valueNode,"qty").toString()));

                    try {
                        HibernateSession.currentSession().save(owdlotPackage);
                    }catch (org.hibernate.exception.ConstraintViolationException e){
                        throw new Exception("Attempting to pack more of Lot: "+lotValue + " then is available. Check with Inventory Specialist to resolve.");
                    }
                } else{
                    throw new Exception("Invalid lot Value: " + lotValue + " for inventoryId: "+inventoryFkey);
                }




            }

        }

    }

    public static PackageLine getPackageLineFromString(String packageId,String OwdLineItemFkey) throws Exception{

        return (PackageLine) HibernateSession.currentSession().load(PackageLine.class, Integer.parseInt(getPackageLineId(packageId,OwdLineItemFkey)));

    }
    public static String getPackageLineId(String packageId,String OwdLineItemFkey) throws Exception{

        String sql = "select id from package_line where package_fkey = :packageId and owd_line_item_fkey = :OwdLineItemFkey";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("packageId",packageId);
        q.setParameter("OwdLineItemFkey",OwdLineItemFkey);
        List l = q.list();
        if(l.size()==1){
            return  l.get(0).toString();
        }

        throw new Exception("unable to lookup the package line Id");

    }

    public static String getLotValuesForSkuXML(String inventoryId, String facility) throws Exception{

        lotValuesBean lvb = new lotValuesBean();
       // lvb.setLotValues(LotManager.getLotValuesWithInventoryForInventoryIdSorted(Integer.parseInt(inventoryId), facility));
        lvb.setLotValueAndQty(LotManager.getLotValuesWithInventoryForInventoryIdSortedWithQty(Integer.parseInt(inventoryId),facility));
        lvb.setInventoryId(inventoryId);
        lvb.setFacility(facility);

        return "<?xml version=\"1.0\"?>"+lvb.getXML();


    }
}
