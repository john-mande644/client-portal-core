package com.owd.dc.manifest.ExternalApis.PurolatorUtils;

import com.owd.core.business.order.OrderFactory;
import com.owd.core.managers.FacilitiesManager;
import com.owd.dc.manifest.ExternalApis.PurolatorUtils.Livingston.ObjectFactory;
import com.owd.dc.manifest.ExternalApis.PurolatorUtils.Livingston.SHIPMENTS;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by danny on 6/5/2017.
 */
public class Utilities {
    private final static Logger log =  LogManager.getLogger();



   /* User: Bumbleride
    Pass: BumbleRIDE0623
    Home folder: /Bumbleride
    hostname: ftpCA.livingstonintl.com
    Port: 21
    Protocol: FTP*/


    public static void main(String[] args){

        try{
            List<Integer> l = new ArrayList<Integer>();

         //   l.add(13562601);

           // l.add(13562567);

          //  l.add(13551997);

          //  l.add(13551380);

            l.add(15282911);
            l.add(15282910);
            l.add(15274268);
            l.add(15318874);
            l.add(15322112);
            l.add(15322111);
            l.add(15274290);
            l.add(15274366);
            l.add(15310244);
            l.add(15274214);
            l.add(15283396);
            l.add(15291846);
            l.add(15282506);
            l.add(15291950);
            l.add(15289953);
            l.add(15283174);



            createAndSendFileForShipments(l, 575, "20VF046688");


        }catch (Exception e){
            e.printStackTrace();
        }



    }



    public static boolean createAndSendFileForShipments(List<Integer> orders,int clientId, String pars) throws Exception{

        ObjectFactory shipFactory = new ObjectFactory();
        SHIPMENTS  shipments = shipFactory.createSHIPMENTS();

        //loop through each order id and mark with pars and load shipment info
        for(Integer id:orders){


            SHIPMENTS.SHIPMENT  shipment = getShipmentForOrderId(id, clientId,pars);
            shipments.getSHIPMENT().add(shipment) ;

        }




        System.out.println("Number of Shipments");
        System.out.println(shipments.getSHIPMENT().size());

        for(int i = 0; i<shipments.getSHIPMENT().size();i++){
            System.out.println(shipments.getSHIPMENT().get(i).getSHIPMENTHEADER().getBOL());

            shipments.getSHIPMENT().get(i).getSHIPMENTHEADER().setInvQty(shipments.getSHIPMENT().size());


        }

        JAXBContext jaxbContext = JAXBContext.newInstance(SHIPMENTS.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        ByteArrayOutputStream b = new ByteArrayOutputStream();
        jaxbMarshaller.marshal(shipments,b);

        System.out.println(b.toString("UTF-8"));



        return true;
    }

    public static SHIPMENTS.SHIPMENT getShipmentForOrderId(int orderId, int clientId,String pars) throws Exception{
        ObjectFactory shipFactory = new ObjectFactory();
        SHIPMENTS.SHIPMENT  shipment = shipFactory.createSHIPMENTSSHIPMENT();
        try{

            OwdOrder order = OrderFactory.getOwdOrderFromOrderID(orderId,clientId,true);
            OwdFacility shipFrom = FacilitiesManager.getFacilityForCode(order.getFacilityCode());


            SHIPMENTS.SHIPMENT.SHIPMENTADDRESS address = shipFactory.createSHIPMENTSSHIPMENTSHIPMENTADDRESS();
            SHIPMENTS.SHIPMENT.SHIPMENTADDRESS.VENDORADDRESS vendoraddress = shipFactory.createSHIPMENTSSHIPMENTSHIPMENTADDRESSVENDORADDRESS();

            vendoraddress.setVendName("BumbleRide");
            vendoraddress.setVendAddress1("2345 Kettner Blvd");
            vendoraddress.setVendCity("San Diego");
            vendoraddress.setVendState("CA");
            vendoraddress.setVendCntry("US");
            vendoraddress.setVendZip("92101");
            address.setVENDORADDRESS(vendoraddress);


            SHIPMENTS.SHIPMENT.SHIPMENTADDRESS.EXPORTERADDRESS exporteraddress = shipFactory.createSHIPMENTSSHIPMENTSHIPMENTADDRESSEXPORTERADDRESS();
            exporteraddress.setExpName("One World Direct");
            exporteraddress.setExpAddress1(shipFrom.getAddress());
            exporteraddress.setExpCity(shipFrom.getCity());
            exporteraddress.setExpState(shipFrom.getState());
            exporteraddress.setExpCntry("US");
            exporteraddress.setExpZip(shipFrom.getZip());
            address.setEXPORTERADDRESS(exporteraddress);

            SHIPMENTS.SHIPMENT.SHIPMENTADDRESS.PURCHASERADDRESS purchaseraddress = shipFactory.createSHIPMENTSSHIPMENTSHIPMENTADDRESSPURCHASERADDRESS();
            OwdOrderShipInfo shipInfo = order.getShipinfo();
            System.out.println(shipInfo.getShipFirstName());
            purchaseraddress.setPurchName(shipInfo.getShipFirstName()+ " " + shipInfo.getShipLastName());
            purchaseraddress.setPurchAddress1(shipInfo.getShipAddressOne());
            purchaseraddress.setPurchAddress2(shipInfo.getShipAddressTwo());
            purchaseraddress.setPurchCity(shipInfo.getShipCity());
            purchaseraddress.setPurchState(shipInfo.getShipState());
            purchaseraddress.setPurchZip(shipInfo.getShipZip());
            purchaseraddress.setPurchCntry(shipInfo.getShipCountry());
            address.setPURCHASERADDRESS(purchaseraddress);

            SHIPMENTS.SHIPMENT.SHIPMENTADDRESS.CONSIGNEEADDRESS consigneeaddress = shipFactory.createSHIPMENTSSHIPMENTSHIPMENTADDRESSCONSIGNEEADDRESS();
            consigneeaddress.setConsName(shipInfo.getShipFirstName()+ " " + shipInfo.getShipLastName());
            consigneeaddress.setConsAddress1(shipInfo.getShipAddressOne());
            consigneeaddress.setConsAddress2(shipInfo.getShipAddressTwo());
            consigneeaddress.setConsCity(shipInfo.getShipCity());
            consigneeaddress.setConsState(shipInfo.getShipState());
            consigneeaddress.setConsZip(shipInfo.getShipZip());
            consigneeaddress.setConsCntry(shipInfo.getShipCountry());
            address.setCONSIGNEEADDRESS(consigneeaddress);




//loop through packages and get details and add up values

            BigDecimal netWeight = new BigDecimal(0);
            int TotalUnits = 0;
            BigDecimal invTotalValue = new BigDecimal(0);

            PackageOrder porder = getPackageOrder(order.getOrderId());
            updateParsOnPackageOrder(porder.getId(),pars);
            SHIPMENTS.SHIPMENT.SHIPMENTDETAIL shipmentdetail = shipFactory.createSHIPMENTSSHIPMENTSHIPMENTDETAIL();

            for(OWDPackage pack : porder.getPackages()){
                for(PackageLine line:pack.getPackageLines()){
                //Add the package weight to total
                    netWeight =  netWeight.add(pack.getWeightLbs());

                SHIPMENTS.SHIPMENT.SHIPMENTDETAIL.ORDER sorder = shipFactory.createSHIPMENTSSHIPMENTSHIPMENTDETAILORDER();
                    sorder.setRefPONo(order.getPoNum());
                   OwdLineItem lineItem = line.getOwdLineItem();
                    String s = lineItem.getDescription();
                  //  String partNum = s.substring(s.indexOf("SupplierSKU:"),s.lastIndexOf("|")).trim().replace("SupplierSKU: ","");

                   /* if(partNum.length()>0){
                        sorder.setPartNumber(partNum);
                    }else{*/
                        sorder.setPartNumber(lineItem.getInventoryNum());
                   // }

                    sorder.setContainerNo(pack.getSSCC());




                    sorder.setPartName(lineItem.getDescription().replaceAll("\\|","").trim());
                    sorder.setUnitPrice(lineItem.getPrice());
                    sorder.setQty(line.getPackQuantity());
                    sorder.setUom("EA");
                    sorder.setExtendPrice(lineItem.getPrice().multiply(BigDecimal.valueOf(line.getPackQuantity())));
                    sorder.setCountryOrigin("TW");

                    //add units
                    TotalUnits = TotalUnits + lineItem.getQuantityActual();

                    // add value
                    invTotalValue =  invTotalValue.add(sorder.getExtendPrice());


                    shipmentdetail.getORDER().add(sorder);



                }
                shipment.setSHIPMENTDETAIL(shipmentdetail);




            }



            shipment.setSHIPMENTADDRESS(address);

            SHIPMENTS.SHIPMENT.SHIPMENTHEADER header = shipFactory.createSHIPMENTSSHIPMENTSHIPMENTHEADER();
            header.setCartonCount(porder.getNumPacks());
            header.setPARS(pars);
            header.setCCINo(order.getOrderId());
            header.setAltShpmntNo(order.getOrderRefnum());
            header.setInvoiceNumber(Integer.parseInt(order.getOrderNum()));
            header.setUnitOfMeasure("LBS");
            header.setNetWeight(netWeight);
            header.setPayTerms1("Net 30");
            header.setCurrency("USD");
            header.setInvTotal(invTotalValue);
            header.setInvQty(TotalUnits);
            header.setRefPO1(order.getPoNum());
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
            header.setDirectShipDt(format.format(order.getShippedDate()));

            header.setBOL(order.getTrackingNums());
            header.setPort(813);

            shipment.setSHIPMENTHEADER(header);












        }catch (Exception e){

            e.printStackTrace();


        }



        return shipment;


    }

    public static PackageOrder getPackageOrder(int orderId){
        PackageOrder porder = new PackageOrder();
        try{
            System.out.println("Lets test");
            Criteria criteria = HibernateSession.currentSession().createCriteria(PackageOrder.class);
            criteria.add(Restrictions.eq("owdOrderFkey", orderId));
            criteria.add(Restrictions.eq("isVoid",0));

            List<PackageOrder> btype = criteria.list();
            System.out.println(btype.size());
            for(PackageOrder bt:btype){

                System.out.println(bt.getPackerRef());



                porder = bt;
            }
        } catch (Exception e){
            e.printStackTrace();
        }



        return porder;
    }

    private static void updateParsOnPackageOrder(Integer id, String pars) throws Exception{
        Query q = HibernateSession.currentSession().createSQLQuery("update package_order set PurolatorPARS = :pars where id = :id");
        q.setParameter("id",id);
        q.setParameter("pars",pars);
        q.executeUpdate();
        HibUtils.commit(HibernateSession.currentSession());
    }
}
