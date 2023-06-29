package com.owd.dc.warehouse.cycleCountSheets;

import com.owd.WMSUtils;
import com.owd.core.managers.FacilitiesManager;
import com.owd.core.managers.InventoryManager;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
import org.hibernate.Query;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by danny on 9/2/2015.
 */
public class createFileForExternalSourceCount {


    public static void main(String[] args){

        createTheFile(489,"DC6","Lagence.csv","G_lagenceV2244");
       /* Map<String,Integer> m = new TreeMap<String,Integer>();
        m.put("P477729",1);
        m.put("P494889",1);
        m.put("P537157",1);
        m.put("P486600",1);
        m.put("P478944",1);
        m.put("P486564",1);

        m.put("P486592",1);
        m.put("P486645",1);
        m.put("P470828",1);
        m.put("P479428",1);
        m.put("P486609",1);
        m.put("P479406",1);
        m.put("P494739",1);
        m.put("P470835",1);
        m.put("P486654",1);
        m.put("P477661",1);
        m.put("P486683",1);
        m.put("P486595",1);
        m.put("P493229",1);
        m.put("P494144",1);
        m.put("P493244",1);
        m.put("P493192",1);
        m.put("P481731",1);
        m.put("P486391",1);
        m.put("P589451",1);
        m.put("P481602",1);
        m.put("P537007",1);
        m.put("P488732",1);
        m.put("P481708",1);
        m.put("P493264",3);
        m.put("P646391",1);
        m.put("P619032",1);
        m.put("P493688",1);
        m.put("P494173",1);
        m.put("P493523",1);
        m.put("P493596",1);
        m.put("P478638",1);
        m.put("P494602",1);
        m.put("P493428",1);
        m.put("P493530",1);
        m.put("P486469",1);
        m.put("P493579",1);
        m.put("P481779",1);
        m.put("P589418",1);
        m.put("P493418",1);
        m.put("P470342",1);
        m.put("P481675",1);
        m.put("P531058",1);
        m.put("P493224",1);
        m.put("P486299",1);
        m.put("P486264",1);
        m.put("P488729",1);
        m.put("P472516",1);
        m.put("P486248",1);
        m.put("P478626",1);
        m.put("P472471",1);
        m.put("P472104",1);
        m.put("P470281",1);
        m.put("P472476",1);
        m.put("P472153",1);
        m.put("P471062",1);
        m.put("P472441",1);
        m.put("P471561",1);
        m.put("P472550",1);
        m.put("P470299",1);
        m.put("P470009",1);
        m.put("P470288",1);
        m.put("P472236",1);
        m.put("P477348",1);
        m.put("P470303",1);
        m.put("P472508",1);
        m.put("P486298",1);
        m.put("P486513",1);
        m.put("P478637",1);
        m.put("P471731",1);
        m.put("P478638",1);
        m.put("P589452",1);
        m.put("P493307",1);
        m.put("P486366",1);
        m.put("P493746",1);
        m.put("P488726",1);
        m.put("P536613",1);
        m.put("P493242",1);
        m.put("P486361",1);
        m.put("P472520",1);
        m.put("P536660",1);
        m.put("P486251",1);
        m.put("P486346",1);
        m.put("P493258",1);
        m.put("P471700",1);
        m.put("P481770",1);
        m.put("P551899",1);
        m.put("P551900",1);
        m.put("P472288",1);
        m.put("P481738",1);
        m.put("P486321",1);
        m.put("P493226",1);
        m.put("P494643",1);
        m.put("P477382",1);
        m.put("P477324",1);
        m.put("P493413",1);
        m.put("P481090",1);
        m.put("P470284",1);
        m.put("P470938",1);
        m.put("P472510",1);
        m.put("P470285",1);
        m.put("P486319",1);
        m.put("P471733",1);
        m.put("P478637",1);
        m.put("P472235",1);
        m.put("P471613",1);
        m.put("P471757",1);
        m.put("P471876",1);
        m.put("P472479",1);
        m.put("P494739",1);
        m.put("P470626",1);
        m.put("P471668",1);
        m.put("P477319",1);
        m.put("P494146",1);
        m.put("P471186",1);

        m.put("P471735",1);
        m.put("P470257",1);
        m.put("P481673",1);
        m.put("P471021",1);
        m.put("P477274",1);
        m.put("P471407",1);
        m.put("P472402",1);
        m.put("P494335",1);
        m.put("P478638",1);
        m.put("P471039",1);
        m.put("P493266",1);
        m.put("P481682",1);
        m.put("P497138",1);
        m.put("P619209",1);
        m.put("P472239",1);
        m.put("P536505",1);
        m.put("P493607",1);
        m.put("P486319",1);
        m.put("P472243",1);
        m.put("P470276",1);
        m.put("P536630",1);
        m.put("P590368",1);
        m.put("P493319",1);
        m.put("P488797",1);
        m.put("P477460",1);
        m.put("P618910",1);
        m.put("P486288",1);
        m.put("P536705",1);
        m.put("P536583",1);
        m.put("P486711",1);
        m.put("P590355",1);
        m.put("P646394",1);
        m.put("P494066",1);
        m.put("P494080",1);
        m.put("P619528",1);
        m.put("P619065",1);
        m.put("P589601",1);
        m.put("P589561",1);
        m.put("P589562",1);
        m.put("P493390",2);
        m.put("P618885",2);
        m.put("P590187",1);
        m.put("P536506",1);
        m.put("P493667",1);
        m.put("P590105",1);
        m.put("P481569",1);
        m.put("P619484",1);
        m.put("P619425",1);
        m.put("P493276",1);
        m.put("P589606",1);
        m.put("P494379",1);
        m.put("P470288",1);
        m.put("P493666",1);
        m.put("P618941",1);
        m.put("P536831",1);
        m.put("P589778",1);
        m.put("P493748",1);
        m.put("P537006",1);
        m.put("P590313",1);
        m.put("P619400",1);
        m.put("P589821",1);
        m.put("P619360",1);
        m.put("P494337",1);
        m.put("P494113",1);
        m.put("P590355",1);
        m.put("P471759",1);
        m.put("P589688",1);
        m.put("P536686",1);
        m.put("P589710",1);
        m.put("P589291",1);
        m.put("P589408",1);
        m.put("P589862",1);
        m.put("P618849",1);
        m.put("P493442",1);
        m.put("P590026",1);
        m.put("P589705",1);
        m.put("P589709",1);
        m.put("P488842",1);
        m.put("P589977",1);
        m.put("P493728",1);
        m.put("P477459",1);
        m.put("P619387",1);
        m.put("P493706",1);
        m.put("P589685",1);
        m.put("P619040",1);
        m.put("P589755",1);
        m.put("P536749",1);
        m.put("P619310",1);
        m.put("P589717",1);
        m.put("P618888",1);
        m.put("P619042",1);
        m.put("P590373",1);
        m.put("P486252",1);
        m.put("P493260",1);
        m.put("P589298",1);
        m.put("P493268",1);
        m.put("P589704",1);
        m.put("P471966",1);
        m.put("P472464",1);
        m.put("P536899",1);
        m.put("P493462",1);
        m.put("P477322",1);
        m.put("P478641",1);
        m.put("P493306",1);
        m.put("P471735",1);
        m.put("P471759",1);
        m.put("P493591",1);
        m.put("P589910",1);
        m.put("P493428",1);
        m.put("P493201",1);
        m.put("P536566",1);
        m.put("P589292",1);
        m.put("P589658",1);
        m.put("P536717",1);
        m.put("P590216",1);
        m.put("P488788",1);
        m.put("P589265",1);
        m.put("P619008",1);
        m.put("P493547",1);
        m.put("P590354",1);
        m.put("P488789",1);
        m.put("P536951",1);
        m.put("P590106",1);
        m.put("P493620",1);
        m.put("P589271",1);
        m.put("P589425",1);
        m.put("P493708",1);
        m.put("P619041",1);
        m.put("P590024",1);
        m.put("P618948",1);
        m.put("P536553",1);
        m.put("P589579",1);
        m.put("P618997",1);
        m.put("P589419",1);
        m.put("P589527",1);
        m.put("P589270",1);
        m.put("P536626",1);
        m.put("P589569",1);
        m.put("P589416",1);
        m.put("P536719",1);
        m.put("P619316",1);
        m.put("P589607",1);
        m.put("P494604",1);
        m.put("P537079",1);
        m.put("P619021",1);
        m.put("P618829",1);
        m.put("P590367",1);
        m.put("P619031",1);
        m.put("P618828",1);
        m.put("P486288",1);
        m.put("P589352",1);
        m.put("P537006",1);
        m.put("P618862",1);
        m.put("P493208",1);
        m.put("P477482",1);
        m.put("P493205",1);
        m.put("P589761",1);
        m.put("P472468",1);
        m.put("P493498",1);
        m.put("P493491",1);
        m.put("P470283",1);
        m.put("P589434",1);

        createDamagedFile(m,489,"DamagedCount-Halston.csv");*/




    }

    private static void createDamagedFile(Map<String,Integer> m, int clientId, String fileName){
        try{



            System.out.println("creating the sheet");
            StringBuffer s = new StringBuffer();
            s.append("OWD ID,UPC,ISBN,SKU,QTY,Description,Locations\r\n");

        for(String sku: m.keySet()){
            try {
                OwdInventory inv = InventoryManager.getOwdInventoryForClientAndSku(clientId + "", sku);
                s.append(inv.getInventoryId());
                s.append(",");
                s.append(inv.getUpcCode());
                s.append(",");
                s.append(inv.getIsbnCode());
                s.append(",");
                s.append(sku);
                s.append(",");
                s.append(m.get(sku));
                s.append(",");
                s.append(inv.getDescription());
                s.append(",Damaged Pallet\r\n");

            }catch (Exception e){
                e.printStackTrace();
            }

        }



            File file = new File(fileName);
            if(!file.exists()){
                file.createNewFile();
            }

            FileWriter fileWritter = new FileWriter(file.getName(),true);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);


                bufferWritter.write(s.toString());


            bufferWritter.close();


            System.out.println(file.getAbsolutePath());





        }catch(Exception e){
            e.printStackTrace();
        }

    }



    private static void createTheFile(Integer clientId, String facility,String fileName,String groupName){
        try{



                System.out.println("creating the sheet");


                cycleCountSheetData data = new cycleCountSheetData();
                String sql2 = "SELECT\n" +
                        "dbo.totalInventoryOhFacility(dbo.owd_inventory.inventory_id,:facility), \n" +
                        "    dbo.owd_inventory.inventory_id as theId,\n" +
                        "    dbo.owd_inventory.inventory_num,\n" +
                        "    dbo.owd_inventory.description,\n" +
                        "dbo.owd_inventory.harm_code, upc_code, isbn_code"+
                        "\n" +
                        "\n" +
                        "FROM\n" +
                        "    dbo.owd_inventory\n" +

                        "WHERE\n";

                sql2 = sql2+"    client_fkey = :id and is_active = 1 ";

                if(groupName.length()>0){
                    sql2 = sql2+" and group_name = :groupname ";
                }


                sql2 = sql2 +          "ORDER BY\n" +
                        "    dbo.owd_inventory.inventory_num ASC";

                Query qq = HibernateSession.currentSession().createSQLQuery(sql2);
                qq.setParameter("id",clientId);
                qq.setParameter("facility", facility);
            if(groupName.length()>0){
                qq.setParameter("groupname",groupName);
            }


                List theresults = qq.list();
                System.out.println("We got this many: "+theresults.size());
                if(theresults.size()>0){

                    loadResultsIntoCycleCountSheetData(data,theresults,facility);


                } else{
                    throw new Exception("No sku's found to count");
                }


            File file = new File(fileName);
            if(!file.exists()){
                file.createNewFile();
            }

            FileWriter fileWritter = new FileWriter(file.getName(),true);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write("OWD ID,UPC,ISBN,SKU,QTY,Description,Locations,QTY OH\r\n");
            for ( cycleCountInv cInv:data.getInventory().values()) {
               StringBuffer s = new StringBuffer();
                s.append(cInv.getId());
                s.append(",");
                s.append(cInv.getUpc());
                s.append(",");
                s.append(cInv.getIsbn());
                s.append(",");
                s.append(cInv.getInvNumber());
                s.append(",");
                s.append(cInv.getQty());
                s.append(",");
                s.append(cInv.getDesc());
                s.append(",");
                s.append(cInv.getLocation().toString().replace("[","").replace("]",""));
                s.append(",");
                OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, Integer.parseInt(cInv.getId()));
                s.append(inv.getOwdInventoryOh().getQtyOnHand());
                bufferWritter.write(s.toString() + "\r\n");

            }
            bufferWritter.close();


            System.out.println(file.getAbsolutePath());





        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public static void loadResultsIntoCycleCountSheetData(cycleCountSheetData data, List theresults,String facilityCode){
        for(Object row:theresults){
            Object[] inv = (Object[]) row;
            System.out.println("Start");
            System.out.println(inv[2].toString());




            cycleCountInv cInv = new cycleCountInv();

            cInv.setId(inv[1].toString());

            cInv.setInvNumber(inv[2].toString());
            System.out.println("3");
            cInv.setDesc(inv[3].toString());
            System.out.println("4");
            cInv.setSupplier(inv[4].toString());
            System.out.println("5");
            cInv.setUpc((inv[5].toString()));
            cInv.setIsbn(inv[6].toString());


            try{
                System.out.println("getting locations");
                List<String> locs = getLocationPickStringsForInventoryIdAndFacility(cInv.getId(),facilityCode);
                cInv.getLocation().addAll(locs);
            }catch(Exception e){
                e.printStackTrace();
                cInv.getLocation().add("No Known Locations");
            }

            System.out.println("6");
            cInv.setQty(inv[0].toString());
            System.out.println("7");
            data.getInventory().put(cInv.getInvNumber(),cInv);




        }
    }
    public static List<String> getLocationPickStringsForInventoryIdAndFacility(String inventoryId,String facilityCode) throws Exception{
        List<String> locs = new ArrayList<String>();
        String sql = "SELECT\n" +
                "    dbo.w_location.pickString\n" +
                "FROM\n" +
                "    dbo.owd_inventory_locations\n" +
                "LEFT OUTER JOIN dbo.w_location\n" +
                "ON\n" +
                "    (\n" +
                "        CASE\n" +
                "\n" +
                "                    when location = 'UNKNOWN' then 1\n" +
                "                          else CAST(Replace(dbo.owd_inventory_locations.location,'//','') as int)\n" +
                "                    end = dbo.w_location.ixNode\n" +
                "    )\n" +
                "INNER JOIN dbo.w_location_tree\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.w_location.ixNode = dbo.w_location_tree.ixNode\n" +
                "    )\n" +
                "WHERE\n" +
                "    dbo.w_location_tree.ixParent = :locNode\n" +
                "AND dbo.owd_inventory_locations.inventory_fkey = :inventoryId  order by sortString ;";
        String locNode = FacilitiesManager.getFacilityForCode(facilityCode).getWlocNodeFkey()+"";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("locNode",locNode);
        q.setParameter("inventoryId",inventoryId);
        List results = q.list();
        for(Object row:results){
            locs.add(row.toString().replace("<br>"," "));
        }
        if(locs.size()==0){
            locs.add("No Locations Found");
        }
        return locs;
    }
}
