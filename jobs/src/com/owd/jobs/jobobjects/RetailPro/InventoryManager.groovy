package com.owd.jobs.jobobjects.RetailPro

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.DelimitedReader
import com.owd.hibernate.HibUtils
import com.owd.hibernate.HibernateSession
import com.owd.hibernate.generated.OwdClient
import com.owd.hibernate.generated.OwdInventory

import java.sql.ResultSet

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 2/9/12
 * Time: 11:51 AM
 * To change this template use File | Settings | File Templates.
 */
class InventoryManager {
    public static int cid = 482;
    private final static Logger log =  LogManager.getLogger();


    // ECommID|DeptCode|DeptName|VendCode|VendName|Descrip1|Descrip2|Descrip3|Descrip4|Attribute|Size|UPC|Price1|Price2|Price3|Cost|UDFName|UDF1|UDF2|UDF3|UDF4|Aux1|Aux2|Aux3|Aux4|Aux5|Aux6|Aux7|Aux8
   /*

60915|017171|MARKETING PROMOCODE|||Sliquid Postcard Bounceback|Promo 15% off Sliquid Products|6/1/11||12/31/11|stores||0.00|0.00|0.00|0.00|||||||||||||
05 722 04|005051|COCK TOYS COCK RINGS|ENT001|ENTRENUE|A00837|Laid Cock Ring P3|Stretch Blue||||||31.00|31.00|0.00|14.73|||||||||||||
1805|008081|SAFER SEX CONDOM|BIL001||01016 10pk Ex Lub rdisdisbdis|BillyBoy X Lubricated Condom|||||0081613000052|1.00|1.00|0.00|0.57||All|||||||||||
01 476 01|001014|VIBRATORS RECHARGE|FUN001|FUN FACTORY USA, INC|4500031|Yooo Vibe | Pink||||||99.00|99.00|0.00|47.92|||||||||||||
01 476 02|001014|VIBRATORS RECHARGE|FUN001|FUN FACTORY USA, INC|4500051|Yooo Vibe | Blue||||||99.00|99.00|0.00|47.92|||||||||||||
01 944 00|001014|VIBRATORS RECHARGE|SUK001|SUKI LLC|BKLS01|Kandi Kisses||||||59.00|59.00|0.00|30.00|||||||||||||
09 275 00|002024|DILDOS    DIL OTHER|SUK001|SUKI LLC|BKKB01|Hold On To Me||||||39.00|39.00|0.00|20.00|||||||||||||
05 942 02|005052|COCK TOYS COCK OTHER|ONE001|ONEUP INNOVATIONS LLC|LOVERS|Tenga Egg | Lovers||||||8.50|8.50|0.00|4.22|||||||||||||
03 423 00|003096|PREPKG KITSUNDRIES|BLO002|Blossom Organics|00272-00|Blossom Organics Deluxe Duo|||||0859602002727|10.00|10.00|0.00|5.00|||||||||||||
09 440 02|009097|SUNDRIES  EDIBLES|JTS001|JT'S STOCKROOM|KL915|Jawbreaker Gag | Pink||||||13.00|13.00|0.00|3.88|||||||||||||
1836|009098|SUNDRIES  LINGERIE|LOV001|LOVELY PLANET|605866 36C|Suite Fatale Bodice | 36C||||||80.00|80.00|0.00|49.90||M Only|||||||||||
1837|009098|SUNDRIES  LINGERIE|LOV001|LOVELY PLANET|605808-Medium|Suite Fatale Thong | Medium||||||30.00|0.00|0.00|17.90||M Only|||||||||||
1838|009098|SUNDRIES  LINGERIE|LOV001|LOVELY PLANET|605808-Large|Suite Fatale Thong | Large||||||30.00|30.00|0.00|17.90||M Only|||||||||||
1839|009098|SUNDRIES  LINGERIE|LOV001|LOVELY PLANET|605808-extra large|Suite Fatale Thong | XL||||||30.00|30.00|0.00|17.90||M Only|||||||||||
35945|017171|MARKETING PROMOCODE|||We-Vibe 3 GWP|Promo We-Vibe 3 GWP|1/17/12||2/17/12|STORES||0.00|0.00|0.00|0.00|||||||||||||
35946|017171|MARKETING PROMOCODE|||Form Line GWP|Promo Form Line GWP|1/17/12||2/17/12|STORES||0.00|0.00|0.00|0.00|||||||||||||
35947|017171|MARKETING PROMOCODE|||Body Chocolate w/ $75+ Purch|Promo Body Chocolate GWP $75+|2/1/12||2/15/12|R/S/B||0.00|0.00|0.00|0.00|||||||||||||
35948|017171|MARKETING PROMOCODE|||Body Chocolate w/ $100+ Purch|Promo Body Chocolate GWP $100+|2/1/12||2/15/12|Mer||0.00|0.00|0.00|0.00|||||||||||||
01 979 00|001012|VIBRATORS BATTERY|PAR001|PARADISE MARKETING SERVIC|multiposition 5.3|Trojan Multiposition Vibrator|||||0022600906667|89.00|89.00|0.00|41.50|||||||||||||
1850|009097|SUNDRIES  EDIBLES|THE002|Theo Chocolates|Pink Salted Caramels 4pc.|Pink Salted Caramels 4 pack||||||9.00|0.00|0.00|4.99|||||||||||||
40534|017171|MARKETING PROMOCODE|||GO NYC Print Ad|Promo Go NYC 15% Off|2.10.12||1.31.13|ALL||0.00|0.00|0.00|0.00|||||||||||||
15 200 03|009024|SUNDRIES  OTHER|||Charger - G-Ki|Charger | Je Joue G-Ki||||||0.00|0.00|0.00|0.00||Stores|||||||||||
13 092 00|013132|BOOKS     GUIDES|ENT001|ENTRENUE|3901|Ultimate Guide To Anal Sex For||||||14.95|14.95|0.00|9.00||Stores|||||||||||
10 700 00|010110|SENSATION BONDAGE|INC001|Incoqnito|IN40NT-RG|Incoqnito Lambskin Tassels||||||35.00|35.00|0.00|17.50||All|||||||||||
06 311 11|006063|HARNESS   HAR OTHER|ROD001|RodeoH LLC|36-38 Black/Red|RodeoH Black/Red | 2X|||2XL|||45.00|45.00|0.00|20.50|||||||||||||
07 144 99|007071|LUBE      WATER|SLI001|SLIQUID LLC|89414700003|Sliquid H20 | lubette|||LUBETTE|||1.00|1.00|0.00|0.43||Stores|||||||||||
2414|001012|VIBRATORS BATTERY|BIG002|BIG TEAZE TOYS|10502|Retro Pocket Rocket||||||20.00|20.00|0.00|7.50|||||||||||||
    */
   static int kEcometryItemNumber=0;
   static int kDeptCode=1;
   static int kDeptName=2;
   static int kVendCode=3;
   static int kVendName=4;
   static int kDescrip1=5;
   static int kDescrip2=6;
   static int kDescrip3=7;
   static int kDescrip4=8;
   static int kAttribute=9;
   static int kSize=10;
   static int kUPC=11;
   static int kPrice1=12;
   static int kPrice2=13;
   static int kPrice3=14;
   static int kCost=15;
   static int kUDFName=16;
   static int kUDF1=17;
   static int kUDF2=18;
   static int kUDF3=19;
   static int kUDF4=20;
   static int kAux1=21;
   static int kAux2=22;
   static int kAux3=23;
   static int kAux4=24;
   static int kAux5=25;
   static int kAux6=26;
   static int kAux7=27;
   static int kAux8=28;
                               /*

                                */

       static Map<String, String> customsDescMap = new TreeMap<String, String>();

       static {

           customsDescMap.put("01", "Plastic Toy");
           customsDescMap.put("02", "Rubber Toy, Glass, Wood, Metal Sculpture");
           customsDescMap.put("03", "Bath Kit");
           customsDescMap.put("04", "Rubber Toy");
           customsDescMap.put("05", "Plastic Toy, Rubber Accessory, Ring");
           customsDescMap.put("06", "Fabric, Leather Bbelt");
           customsDescMap.put("07", "Lotion");
           customsDescMap.put("08", "Rubber Accessory");
           customsDescMap.put("09", "Lotion");
           customsDescMap.put("10", "Metal, Plastic, Leather Accessory ");
           customsDescMap.put("11", "Metal, Plastic, Rubber Accessory ");
           customsDescMap.put("12", "DVD");
           customsDescMap.put("13", "Book");
           customsDescMap.put("14", "Bath Kit");
           customsDescMap.put("15", "Packaging");
           customsDescMap.put("16", "Virtual Item");
           customsDescMap.put("25", "Packaging");




       }

     public static List<String> updateOWDInventoryFromRetailPro()
     {
         Map<String,byte[]> kitMap =  com.owd.jobs.jobobjects.RetailPro.TransferUtilities.getRetailProFiles("Kits_")
           Map<String,byte[]> invMap =  com.owd.jobs.jobobjects.RetailPro.TransferUtilities.getRetailProFiles("Inv_")

         //determine max shared file ID
         int maxID = 0;
         for (String kitFileName:kitMap.keySet())
         {
             log.debug("testing "+kitFileName);
             try{
                 String numTest = kitFileName.substring(kitFileName.indexOf("_")+1);
                 numTest = numTest.substring(0,numTest.indexOf("."))
                int currID = Integer.parseInt(numTest)
                 println "currID:"+currID
                if(currID>maxID  && null!=invMap.get("Inv_"+currID+".txt"))
                {

                         if(null!=invMap.get("Inv_"+maxID+".txt"))
                     {
                    TransferUtilities.moveRetailProFileToDone("Inv_"+maxID+".txt")
                     }
                         if(null!=kitMap.get("Kits_"+maxID+".xml"))
                     {
                    TransferUtilities.moveRetailProFileToDone("Kits_"+maxID+".xml")

                     }

                    maxID = currID
                }
             }   catch(Exception ex)
             {
                 ex.printStackTrace();
             }
         }

         println "Max ID="+maxID

         return  processInventoryUpdate(invMap.get("Inv_"+maxID+".txt"),kitMap.get("Kits_"+maxID+".xml"))
     }
     public static void main(String[] args) throws Exception {

         println updateOWDInventoryFromRetailPro()
      /*   def kitDoc = new XmlSlurper().parse(new ByteArrayInputStream(kitMap.values().toArray()[0]))

                kitDoc.Kit.each {
                             println "Kit:"+it.ECommID+":"+it.ItemType;

                }*/

    }




    public static boolean isGoodRow(DelimitedReader items, int currRow)
    {
        boolean isGood = true;

        if(items.getStrValue(kDescrip1, currRow, "").trim().startsWith("PARENT-")) { println "IS PARENT";isGood = false;}
        if(items.getStrValue(kEcometryItemNumber, currRow, "").trim().length()<2) {println "Bad SKU"; isGood = false;}
        if(items.getStrValue(kDeptName, currRow, "").trim().length()<1) {println "NO DEPT NAME"; isGood = false;}
        if(items.getStrValue(kDeptName, currRow, "").trim().contains("PROMO")) { println "PROMO DEPT";isGood = false;}
        if(items.getStrValue(kDeptName, currRow, "").trim().contains("WORKSHOP")) { println "WORKSHOP DEPT";isGood = false;}




        return isGood;


    }

    public static List<String> processInventoryUpdate(byte[] inventoryFileData, byte[] kitFileData)
    {


        def kitDoc = new XmlSlurper().parse(new ByteArrayInputStream(kitFileData))

        List<String> kitSKUs = new ArrayList<String>()
        List<String> bundleSKUs = new ArrayList<String>()

        kitDoc.Kit.each {
            if(it.ItemType.text().equals("Kit"))
            {
               kitSKUs.add(it.ECommID.text().trim())
            }   else
            {
               bundleSKUs.add(it.ECommID.text().trim())

            }


        }


        List<String> oldSkus = new ArrayList<String>();

                ResultSet rs = HibernateSession.getResultSet("select inventory_num from owd_inventory where client_fkey="+cid)
        while(rs.next())
        {
            oldSkus.add(rs.getString(1))
        }
        rs.close();

      String invData = new String(inventoryFileData)
      invData = invData.replace("\"","")
      DelimitedReader items = new DelimitedReader(((char)'|'),new BufferedReader(new StringReader(invData)), true);
      List<String> rproSkuList =new ArrayList<String>()


                for (int row = 0; row < items.getRowCount(); row++)
                {
                    log.debug("row:"+row);
                //   if(row>150) throw new Exception("STOP");

            String newSku = items.getStrValue(kEcometryItemNumber, row, "xxx").trim();
                    println newSku
                    rproSkuList.add(newSku)
            if(isGoodRow(items,row))
            {
            if (oldSkus.contains(newSku)) {
                println "SKU exists"
                boolean skipit = false;
                if(kitSKUs.contains(newSku))
                {
                 if (oldSkus.contains(newSku + "/KIT")) {
                            newSku = newSku + "/KIT";
                        } else {
                     println "making kit..."
                            OwdInventory oldItem = com.owd.core.managers.InventoryManager.getOwdInventoryForClientAndSku("482", newSku);

                            OwdInventory itemx = com.owd.core.managers.InventoryManager.getInitializedOwdInventory(482);
                            itemx.setInventoryNum(newSku+"/KIT");
                            itemx.setDescription(oldItem.getDescription());
                            itemx.setHarmCode(oldItem.getHarmCode());
                            itemx.setMfrPartNum(oldItem.getMfrPartNum());
                            itemx.setPrice(oldItem.getPrice());
                            itemx.setWeightLbs(oldItem.getWeightLbs());

                            itemx.setNotes(oldItem.getNotes());
                            itemx.setCustomsDesc(oldItem.getCustomsDesc());

                            itemx.setLotTrackingRequired(0);
                     itemx.setLotPattern("");
                            itemx.setModifiedBy("RetailPro Importer");
                            itemx.setOwdClient((OwdClient) HibernateSession.currentSession().load(OwdClient.class, 482));
                            HibernateSession.currentSession().save(itemx);
                            HibernateSession.currentSession().save(itemx.getOwdInventoryOh());
                            HibernateSession.currentSession().save(itemx.getPackingInstructions());
                            HibernateSession.currentSession().flush();
                            //commit?
                            HibUtils.commit(HibernateSession.currentSession());
                            newSku = newSku + "/KIT";

                        }
                }else if(bundleSKUs.contains(newSku))
                {
                    //todo
                    skipit = true;
                    //throw new Exception("Bundle SKUs not yet handled");
                }

                if (!skipit)
                {
                //update SKU
      /*          log.debug("Already got SKU ");
                OwdInventory item = (OwdInventory) (HibernateSession.currentSession().load(OwdInventory.class, Inventory.dbloadByPart(newSku, "" + cid).inventory_id));

                //    item.setDescription(rs.getString("Description"));
                item.setHarmCode(items.getStrValue(kVendCode, row, "xxx").trim());
                item.setMfrPartNum(items.getStrValue(kVendName, row, "xxx").trim());
                item.setPrice(new BigDecimal("0.00"));


                if (customsDescMap.get(item.getInventoryNum().trim().substring(0, 2)) != null) {
                    item.setCustomsDesc(customsDescMap.get(item.getInventoryNum().substring(0, 2)));
                } else {
                    log.debug("UNKNOWN CATEGORY : [" + item.getInventoryNum().substring(0, 2) + "]");
                    item.setCustomsDesc("Accessories");
                }
                HibernateSession.currentSession().save(item);
                HibernateSession.currentSession().save(item.getOwdInventoryOh());
                HibernateSession.currentSession().save(item.getPackingInstructions());
                HibernateSession.currentSession().flush();
                //commit?
                HibUtils.commit(HibernateSession.currentSession());*/

                }


            } else {
                //create SKU

                OwdInventory item = com.owd.core.managers.InventoryManager.getInitializedOwdInventory(cid);
                item.setInventoryNum(newSku);
                item.setDescription(items.getStrValue(kDescrip1, row, "").trim()+items.getStrValue(kDescrip2, row, "").trim()+items.getStrValue(kDescrip3, row, "").trim());
                //item.setHarmCode(items.getStrValue(kVend, row, "xxx").trim());
                item.setMfrPartNum(items.getStrValue(kVendName, row, "xxx").trim());
                item.setPrice(new BigDecimal("0.00"));
                item.setWeightLbs(0.00f);

                item.setLotTrackingRequired(0);
                item.setLotPattern("");
               // item.setNotes(items.getStrValue(kStatusDescription, row, "xxx").trim());
                if (customsDescMap.get(item.getInventoryNum().substring(0, 2)) != null) {
                    item.setCustomsDesc(customsDescMap.get(item.getInventoryNum().substring(0, 2)));
                }  else {
                    log.debug("UNKNOWN CATEGORY : [" + item.getInventoryNum().substring(0, 2) + "]");
                    item.setCustomsDesc("Accessories");
                }
             //   if (items.getStrValue(kStatusId, row, "xxx").trim().startsWith("K") || items.getStrValue(kStatusId, row, "xxx").trim().startsWith("G")) {
             //       item.setIsAutoInventory(1);
             //   }

                if(kitSKUs.contains(newSku))
                {
                    item.setIsAutoInventory(1);

                }


                item.setModifiedBy("RetailPro Importer");
                item.setOwdClient((OwdClient) HibernateSession.currentSession().load(OwdClient.class, cid));

                HibernateSession.currentSession().save(item);
                HibernateSession.currentSession().save(item.getOwdInventoryOh());
                HibernateSession.currentSession().save(item.getPackingInstructions());
                HibernateSession.currentSession().flush();
                //commit?
                HibUtils.commit(HibernateSession.currentSession());
            }
                }  else
            {
                println "BAD"
            }
        }


      return rproSkuList
    }



}
