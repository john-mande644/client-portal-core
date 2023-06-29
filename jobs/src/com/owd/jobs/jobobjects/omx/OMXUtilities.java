package com.owd.jobs.jobobjects.omx;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.WebResource;
import org.apache.xpath.XPathAPI;
import org.apache.commons.lang.StringEscapeUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Stewart Buskirk
 * Date: Jun 18, 2009
 * Time: 1:34:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class OMXUtilities {
private final static Logger log =  LogManager.getLogger();


    public static void logYahooItemFeed() throws Exception
    {

         WebResource catalog = new WebResource("http://store.razorama.com/razorama/objinfo.xml",WebResource.kGETMethod);

                // StringBuffer buffer = new StringBuffer();

                                          InputStream in = catalog.getResourceAsInputStream(false);


         //   log.debug(buffer);
                   DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                 factory.setNamespaceAware(false);
                 factory.setValidating(false);
                 javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();



                 Document doc = builder.parse(in,"UTF-8");
                 doc.getDocumentElement().normalize();


            Transformer serializer =
          TransformerFactory.newInstance().newTransformer();
      serializer.setOutputProperty(
            OutputKeys.OMIT_XML_DECLARATION, "yes");


                          NodeIterator nlstatus = org.apache.xpath.XPathAPI.selectNodeIterator(doc, "/StoreExport/Products/Product");
                  Node ns;
           while ((ns = nlstatus.nextNode()) != null) {
             // Serialize the found nodes to System.out


              serializer.transform(
            new DOMSource(ns),
            new StreamResult(System.out));

           }



    }
    public static void main(String[] args) throws Exception
    {
        //razorama ID
        String bizid="svSYjSNlfGxaZOLGPaEUUOaaqcQRKGUghhfoTIOUuBGjOjEMtyACrBQJlghHGLfvuXQgfJtPhJoIevvjBIwuCCcMcPhwOsDOjefPdXGmdDZIiZiYnaSlaQYygsfaPLAlPtIsYunIVPZTfERHWoiUEogleDUloKMiikKJajpfvRLfxtctwWygNfOpJTBSjqbWKhIqfqIbeiGVQviDfpEwhpCeMNDAHODEsPelWTeNFydnQLAregMgGVwwyQrcLWg";

          logYahooItemFeed();

      //    getInventoryInfoForSKU("svSYjSNlfGxaZOLGPaEUUOaaqcQRKGUghhfoTIOUuBGjOjEMtyACrBQJlghHGLfvuXQgfJtPhJoIevvjBIwuCCcMcPhwOsDOjefPdXGmdDZIiZiYnaSlaQYygsfaPLAlPtIsYunIVPZTfERHWoiUEogleDUloKMiikKJajpfvRLfxtctwWygNfOpJTBSjqbWKhIqfqIbeiGVQviDfpEwhpCeMNDAHODEsPelWTeNFydnQLAregMgGVwwyQrcLWg",
      //                "W13112099015" +
      //                        "");




    }

    public static void updateItemDefaultPrice(String bizid, String code, float unitPrice) throws Exception {



             String xxx = "<PricePoints priceType=\"Unit\" >" +
              "<PricePoint>" +
                           "<Quantity>1</Quantity><Price>"+unitPrice+"</Price><ShippingHandling>0.00</ShippingHandling>" +
                           "</PricePoint>" +
                           "</PricePoints>";

        Map<String,String> attMap = new HashMap<String,String>();
        attMap.put("ItemCode",code);
        attMap.put("Type","DefaultPrice");

            Document doc =  getOMXGenericRequest(bizid,"ItemPriceUpdateRequest","1.00",xxx,attMap);
         }
    

public static void updateCustomItemAttributes(String bizid, String code, Map<String,String> attMap) throws Exception {


     
         String xxx = "<Items>" +
                 "<Item itemCode=\"" + code +"\">";
        for(String key: attMap.keySet())
        {
            xxx=xxx+"<Attribute attributeID=\""+key+"\">"+attMap.get(key)+"</Attribute>";
        }
               xxx=xxx+  "</Item></Items>";

        Document doc =  getOMXGenericRequest(bizid,"CustomItemAttributeUpdateRequest","1.00",xxx,null);
     }


     public static void updateOMXSkuFromYahoo(String bizid, Node ns, boolean isNew) throws Exception {


       //    Document doc = getInventoryInfoForSKU("svSYjSNlfGxaZOLGPaEUUOaaqcQRKGUghhfoTIOUuBGjOjEMtyACrBQJlghHGLfvuXQgfJtPhJoIevvjBIwuCCcMcPhwOsDOjefPdXGmdDZIiZiYnaSlaQYygsfaPLAlPtIsYunIVPZTfERHWoiUEogleDUloKMiikKJajpfvRLfxtctwWygNfOpJTBSjqbWKhIqfqIbeiGVQviDfpEwhpCeMNDAHODEsPelWTeNFydnQLAregMgGVwwyQrcLWg",
       //               code);

         DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
          OMXUtilities.SubItemList subList =null;
         String code = org.apache.xpath.XPathAPI.eval(ns,"./Code").toString().toUpperCase();
     //    log.debug("code1="+code);
         if(code.lastIndexOf(9218)>0)
                   {
                   //    //log.debug("Hi!");
                     NodeList optionNodes = org.apache.xpath.XPathAPI.selectNodeList(ns, "./OptionLists/OptionList");

        if(optionNodes.getLength()>0)
        {

            //got sub items
            subList = new OMXUtilities.SubItemList();
            subList.parentItemCode = code;
            Node optionNode = optionNodes.item(0);

        String optionName  = XPathAPI.selectSingleNode(optionNode,"./@name").getNodeValue().toString();
           // log.debug("optionname="+optionName);
            subList.optionName=optionName.replaceAll("\\<.*?>","");


        }

                      while(code.length()>0)
                   {
                       String optionCode=code.substring(code.lastIndexOf(9218)+1);
                       if(subList==null) {return;}
                       code = code.substring(0,code.lastIndexOf(9218));

                       ////log.debug("1");
                       String subcode = code.substring(code.lastIndexOf(9217)+1);

                       code = code.substring(0,code.lastIndexOf(9217));
                       ////log.debug("2");
                       String type= code.substring(code.lastIndexOf(9218)+1);
                      //     //log.debug("3");
                       type=type.substring(type.lastIndexOf(" ")+1);
                      //    //log.debug("4");
                       if(code.lastIndexOf(9218)>0)
                       {
                        code = code.substring(0,code.lastIndexOf(9218));
                       }else
                       {
                         //  code = new code.().toString();
                           log.debug(""+code+":"+code.length());
                           int stop = -1;
                           String sku = "";
                           while(code.getBytes()[++stop] != 63)
                           {
                                 sku = sku + (char) code.getBytes()[stop];
                           }
                           log.debug(""+sku);
                           log.debug("code:"+code);
                           code=sku;
                           subList.parentItemCode = code;
                                    subList.subitemMap.put(subcode,optionCode);
                           break;
                       }
                      // //log.debug("new code in loop:"+code);

                 //  log.debug("\r>>"+optionCode+" ("+subcode+") - "+ code);
                         subList.subitemMap.put(subcode,optionCode);

                   }
                   }else
                   {
                       //normal code value
            //    log.debug("\r>>"+XPathAPI.eval(ns,"./Code")+" - "+XPathAPI.eval(ns,"./Description").toString().replaceAll("<.*?>",""));
                    //   processYahooSKU(ns, sku,color,size);

                   }
       //     log.debug("processing Yahoo ID "+ns.getAttributes().getNamedItem("Id"));


             log.debug("OMX/OWD SKU = "+ code);


         if(subList!=null)
         {
             log.debug(""+subList);
         }
         log.debug("submap***");

             String title = org.apache.xpath.XPathAPI.eval(ns,"./Description").toString().replaceAll("<.*?>","");
             String longDesc = org.apache.xpath.XPathAPI.eval(ns,"./Caption").toString().replaceAll("<.*?>","");
             String thumb = org.apache.xpath.XPathAPI.eval(ns,"./Thumb").toString();
         if(thumb!=null && thumb.indexOf("http")>=0)
         {
             thumb = thumb.substring(thumb.indexOf("http"));
      //   log.debug(""+thumb);
                if(thumb.indexOf(">")>=0)
         {
            thumb = thumb.substring(0,thumb.indexOf(">"));
         }
         }
         String picture = org.apache.xpath.XPathAPI.eval(ns,"./Picture").toString();
           if(picture!=null && picture.indexOf("http")>=0)
         {
         picture = picture.substring(picture.indexOf("http"));
       //  log.debug(""+picture);
                           if(picture.indexOf(">")>=0)
         {
             picture = picture.substring(0,picture.indexOf(">"));
         }
         }
             String url = org.apache.xpath.XPathAPI.eval(ns,"./Url").toString();
             String unitPrice = org.apache.xpath.XPathAPI.eval(ns,"./Pricing/SalePrice").toString().replaceAll("<.*?>","");

         if("".equals(unitPrice))
         {

              unitPrice = org.apache.xpath.XPathAPI.eval(ns,"./Pricing/BasePrice").toString().replaceAll("<.*?>","");
         }
             String weight = org.apache.xpath.XPathAPI.eval(ns,"./Weight").toString().replaceAll("<.*?>","");
             String yahooID = ns.getAttributes().getNamedItem("Id").toString().replaceAll("<.*?>","").replaceAll("\"","");
             if(yahooID.startsWith("Id=")) { yahooID = yahooID.substring(3);}
         String inventoryType = "1";
         String fileSubCode = "0";
         boolean notDropship = true;
         if(code.toUpperCase().endsWith("-DS"))
         {
             notDropship=false;
             if(title.toUpperCase().indexOf("TRIKKE")>=0)
             {
                 fileSubCode="2";
             }                   else
             {
                 fileSubCode="1";
             }
         }


             inventoryType="1";


             if(!notDropship)
         {
           //  dropship yes
             inventoryType="2";

         }

         String xxx = getItemUpdateXML(df, code, title, longDesc, thumb, picture, weight, inventoryType,fileSubCode,(subList==null?null:subList.optionName),isNew);

         log.debug(""+xxx);
           Document doc = getOMXGenericRequest(bizid,"ItemUpdateRequest","2.00",xxx,null);
         Map<String,String> attMap = new HashMap<String,String>();
         attMap.put("1",yahooID);
        updateCustomItemAttributes(bizid,code,attMap);
         try
         {
             float afloat = Float.parseFloat(unitPrice);
         }                                              catch (Exception ex)
         {
                                                                            unitPrice="";
         }
        if(!("".equals(unitPrice)))
        {

            updateItemDefaultPrice(bizid,code,Float.parseFloat(unitPrice));
        }

         if(subList!=null )
         {
             if(subList.subitemMap.size()>0)
             {
                if(notDropship)
         {
             inventoryType="1";
         }                     else
         {
             //sublist null, dropship yes
             inventoryType="2";

         }

                 for(String key:subList.subitemMap.keySet())
                 {
              String subitem="<Items>" +
                      "<Item itemCode=\""+subList.subitemMap.get(key)+"\" parentItemCode=\""+code+"\">" +
                      "<Dimension dimension=\"X\">" +
                      "<Value>" +
                      "<Description>"+StringEscapeUtils.escapeXml(key)+"</Description>" +
                      "<Surcharge>0</Surcharge>" +
                      "</Value>" +
                      "</Dimension>" +
                      "</Item>" +
                      "</Items>";
           doc = getOMXGenericRequest(bizid,"SubItemUpdateRequest","1.00",subitem,null);
                     log.debug(""+subitem);
                 }

             }
         }


     }

    private static String getItemUpdateXML(DateFormat df, String code, String title, String longDesc, String thumb, String picture, String weight, String inventoryType, String fileSubCode, String dimName,boolean isNew) {
      return "<Items>" +
                "<Item>" +
                "<ItemCode>"+code+"</ItemCode>" +
                "<ProductStatus>True</ProductStatus>" +
                "<ProductName>"+StringEscapeUtils.escapeXml(title)+"</ProductName>" +
                "<Weight>"+weight+"</Weight>" +
                (isNew?"<LaunchDate>"+df.format(Calendar.getInstance().getTime())+"</LaunchDate>":"") +
                "<InfoText>"+StringEscapeUtils.escapeXml(longDesc.replaceAll("\\<.*?>",""))+"</InfoText>" +
                "<InfoTextHTML>"+StringEscapeUtils.escapeXml(longDesc)+"</InfoTextHTML>" +
                (isNew?"<ShipAloneFlag>False</ShipAloneFlag>":"") +
                (isNew?"<InventoryProductFlag>True</InventoryProductFlag>":"") +
                (isNew?"<InventoryType>"+inventoryType+"</InventoryType>":"") +
                (isNew?"<TaxCode>TC1</TaxCode>":"") +
               // "<InventoryManager>inventory manager</InventoryManager>" +
                (isNew?"<FileSubCode>"+fileSubCode+"</FileSubCode>":"") +
                ((inventoryType.equals("2") && isNew)?"<DropShipFileSubCode>"+fileSubCode+"</DropShipFileSubCode>":"") +
                (isNew?"<ProductGroup>1</ProductGroup>":"") +
                "<ImageURL>"+StringEscapeUtils.escapeXml((picture==null?"":picture))+"</ImageURL>" +
               "<ImageURLThumbnail>"+StringEscapeUtils.escapeXml((thumb==null?"":thumb))+"</ImageURLThumbnail>" +
                (isNew?"<DiscountPercent>0</DiscountPercent>":"") +
              (isNew?(dimName==null?"":"<DimensionX>"+ StringEscapeUtils.escapeXml(dimName)+"</DimensionX>"):"")+
                "</Item>" +
                "</Items>";
    }

    public static List<String> getItemCodeList(String httpBizID) throws Exception
    {
                       HashMap<String,String> pmap = new HashMap<String,String>();

          Document doc = getOMXInformationRequest("svSYjSNlfGxaZOLGPaEUUOaaqcQRKGUghhfoTIOUuBGjOjEMtyACrBQJlghHGLfvuXQgfJtPhJoIevvjBIwuCCcMcPhwOsDOjefPdXGmdDZIiZiYnaSlaQYygsfaPLAlPtIsYunIVPZTfERHWoiUEogleDUloKMiikKJajpfvRLfxtctwWygNfOpJTBSjqbWKhIqfqIbeiGVQviDfpEwhpCeMNDAHODEsPelWTeNFydnQLAregMgGVwwyQrcLWg",
                      "ItemListRequest","1.00",pmap);

        List<String> items = new ArrayList<String>();

        NodeList nlist = XPathAPI.selectNodeList(doc,"//Item/@itemcode") ;
        for (int i=0;i<nlist.getLength();i++)
        {
            items.add(nlist.item(i).getNodeValue());
        }
        return items;

        
    }


      public static List<String> getAttributeListForSKU(String httpBizID, String sku) throws Exception
    {
                         HashMap<String,String> pmap = new HashMap<String,String>();
                         pmap.put("ItemCode",sku);
          Document doc = getOMXInformationRequest("svSYjSNlfGxaZOLGPaEUUOaaqcQRKGUghhfoTIOUuBGjOjEMtyACrBQJlghHGLfvuXQgfJtPhJoIevvjBIwuCCcMcPhwOsDOjefPdXGmdDZIiZiYnaSlaQYygsfaPLAlPtIsYunIVPZTfERHWoiUEogleDUloKMiikKJajpfvRLfxtctwWygNfOpJTBSjqbWKhIqfqIbeiGVQviDfpEwhpCeMNDAHODEsPelWTeNFydnQLAregMgGVwwyQrcLWg",
                      "CustomItemAttributeInformationRequest","1.00",pmap);

        List<String> items = new ArrayList<String>();

        NodeList nlist = XPathAPI.selectNodeList(doc,"//Item/@attributeID") ;
        for (int i=0;i<nlist.getLength();i++)
        {
            items.add(nlist.item(i).getNodeValue());
        }
        return items;
    }

       public static Document getInventoryInfoForSKU(String httpBizID, String sku) throws Exception
    {
                       HashMap<String,String> pmap = new HashMap<String,String>();
          pmap.put("ItemCode",sku);
          return getOMXInformationRequest("svSYjSNlfGxaZOLGPaEUUOaaqcQRKGUghhfoTIOUuBGjOjEMtyACrBQJlghHGLfvuXQgfJtPhJoIevvjBIwuCCcMcPhwOsDOjefPdXGmdDZIiZiYnaSlaQYygsfaPLAlPtIsYunIVPZTfERHWoiUEogleDUloKMiikKJajpfvRLfxtctwWygNfOpJTBSjqbWKhIqfqIbeiGVQviDfpEwhpCeMNDAHODEsPelWTeNFydnQLAregMgGVwwyQrcLWg",
                      "ItemInformationRequest","1.00",pmap);
    }

    public static Document getShipmentStatus(String httpBizID,String OMXShipmentReference) throws Exception
    {
            String[] refsplit = OMXShipmentReference.split("-");

          if (refsplit.length == 3 && refsplit[2].length()>0) {

                       HashMap<String,String> pmap = new HashMap<String,String>();
          pmap.put("ShipmentNumber",refsplit[0]+"-"+refsplit[1]);
           return OMXUtilities.getOMXInformationRequest(httpBizID,
                      "ShipmentInformationRequest","1.00",pmap);


    } else
          {
            throw new Exception("Invalid OMX order/shipment reference");
          }
    }
    private static Document getOMXInformationRequest(String httpBizID, String requestName, String version, Map<String,String> params) throws Exception
    {
          StringBuffer sb = new StringBuffer();

 
            sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><"+requestName+" version=\""+version+"\"><UDIParameter>");
            sb.append("<Parameter key=\"HTTPBizID\">" + httpBizID + "</Parameter>");
        for(String key:params.keySet())
        {
           sb.append("<Parameter key=\""+key+"\">" + params.get(key) + "</Parameter>");
        }

            sb.append("</UDIParameter></"+requestName+">");

           // log.debug("itemInformationRequest:" + sb);

                try {
                    WebResource server = new WebResource("https://api.omx.ordermotion.com/hdde/xml/udi.asp?ContentType=UTF-8", WebResource.kPOSTMethod);
                    server.setContent("text/xml");

                    server.setParameters(sb.toString());


                    log.debug(":::API REQUEST BEGIN:::");

                    log.debug(sb.toString());

                    log.debug(":::API REQUEST END:::");


                    server.contentType = "text/xml";


                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                             factory.setNamespaceAware(false);
                             factory.setValidating(false);
                             javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();


                             InputStream is = server.getResourceAsInputStream(false);

                             log.debug("got OMX API response");
                             Document doc = builder.parse(is);
                             doc.getDocumentElement().normalize();


                                   if (sb.toString().toUpperCase().indexOf("ERROR") >= 0) {
                        log.debug(":::API ERROR RESPONSE BEGIN:::");
                        log.debug(sb.toString());
                        log.debug(":::API ERROR RESPONSE END:::");
                        throw new Exception("OMX API ERROR RESPONSE");

                    }   else
                    {
                       log.debug(":::API RESPONSE BEGIN:::");

            Transformer serializer =
          TransformerFactory.newInstance().newTransformer();
      serializer.setOutputProperty(
            OutputKeys.OMIT_XML_DECLARATION, "yes");


              serializer.transform(
            new DOMSource(doc),
            new StreamResult(System.out));
                                     log.debug(":::API RESPONSE END:::");
                        return doc;

                    }

                    

                } catch (IOException ex) {

                    throw new Exception("Unable to communicate with OM server: IO Exception " + ex.getMessage());
                } catch (Exception ex) {

                    throw ex;
                }




    }

      private static Document getOMXGenericRequest(String httpBizID, String requestName, String version, String requestXML, Map<String,String> attMap) throws Exception
    {
          StringBuffer sb = new StringBuffer();


            sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><"+requestName+" version=\""+version+"\"><UDIParameter>");
            sb.append("<Parameter key=\"HTTPBizID\">" + httpBizID + "</Parameter>");
        if(attMap!=null)
        {
            for(String key:attMap.keySet())
            {
                sb.append("<Parameter key=\""+key+"\">" + attMap.get(key) + "</Parameter>");
            }
        }
            sb.append("</UDIParameter>"+requestXML+"</"+requestName+">");

           // log.debug("itemInformationRequest:" + sb);

                try {
                    WebResource server = new WebResource("https://api.omx.ordermotion.com/hdde/xml/udi.asp", WebResource.kPOSTMethod);
               //     server.setContent("text/xml");

                    server.setParameters(sb.toString());


                    log.debug(":::API REQUEST BEGIN:::");

                    log.debug(sb.toString());

                    log.debug(":::API REQUEST END:::");


                    server.contentType = "application/x-www-form-urlencoded";


                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                             factory.setNamespaceAware(false);
                             factory.setValidating(false);
                             javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();


                             InputStream is = server.getResourceAsInputStream(false);

                             log.debug("got OMX API response");
                             Document doc = builder.parse(is);
                             doc.getDocumentElement().normalize();


                                   if (sb.toString().toUpperCase().indexOf("ERROR") >= 0) {
                        log.debug(":::API ERROR RESPONSE BEGIN:::");
                        log.debug(sb.toString());
                        log.debug(":::API ERROR RESPONSE END:::");
                        throw new Exception("OMX API ERROR RESPONSE");

                    }   else
                    {
                        log.debug(":::API RESPONSE BEGIN:::");

            Transformer serializer =
          TransformerFactory.newInstance().newTransformer();
      serializer.setOutputProperty(
            OutputKeys.OMIT_XML_DECLARATION, "yes");


              serializer.transform(
            new DOMSource(doc),
            new StreamResult(System.out));
                                     log.debug(":::API RESPONSE END:::");
                        return doc;

                    }



                } catch (IOException ex) {

                    throw new Exception("Unable to communicate with OM server: IO Exception " + ex.getMessage());
                } catch (Exception ex) {

                    throw ex;
                }




    }

    public static class SubItemList
    {
        String parentItemCode;
        String optionName;
        Map<String,String> subitemMap = new HashMap<String,String>();

        @Override
        public String toString() {
            return "SubItemList{" +
                    "parentItemCode='" + parentItemCode + '\'' +
                    ", optionName='" + optionName + '\'' +
                    ", subitemMap=" + subitemMap +
                    '}';
        }
    }
}
