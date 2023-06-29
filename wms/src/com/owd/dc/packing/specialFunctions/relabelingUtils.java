package com.owd.dc.packing.specialFunctions;

import com.owd.core.ExcelUtils;
import com.owd.core.business.order.*;
import com.owd.dc.packing.beans.OrderPackagesBean;
import com.owd.dc.packing.beans.PackageBoxBean;
import com.owd.dc.packing.getPackagesForShipRetry;
import com.owd.dc.packing.voidUtil;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;

import com.thoughtworks.xstream.XStream;
import org.eclipse.jdt.internal.core.hierarchy.HierarchyBuilder;
import org.hibernate.Query;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * Created by danny on 11/29/2016.
 */
public class relabelingUtils {





    public static void main(String[] args){
        //System.out.println(voidAndReshipFedextoFedex("1001901774520005760100678920034568"));

        try {
            getOwdOrderFromTrackingNumber(parseOutTrackingFromLabelScanFedex("1001901774520005760100678920034568"));
        }catch (Exception e){
            e.printStackTrace();
        }




    }

    public static String doRelabel(String tracking, String swapType){
        String s = "";
        System.out.println(swapType);

        switch(Integer.parseInt(swapType)){
            case 0: s = voidAndReshipFedextoFedex(tracking);
            case 1: s = voidAndReshipFedextoFedexNoUpdate(tracking);
        }


        return s;
    }





    public static String voidAndReshipFedextoFedex(String tracking){

            String result = "";
        OrderPackagesBean opb = new OrderPackagesBean();
            try{
                tracking = parseOutTrackingFromLabelScanFedex(tracking);
                System.out.println(tracking);
                OwdOrder order = getOwdOrderFromTrackingNumber(tracking);
                System.out.println(order.getPackagesShipped());
                System.out.println(order.getClientFkey());
                List<PackageBoxBean> packages = voidUtil.getPackagesListOrderId(HibernateSession.currentSession(),order.getOrderId()+"");

                if(packages.size()==1){

                    System.out.println(packages.get(0).getBarcode());
                    System.out.println(packages.get(0).getPackageOrderFkey());
                    updatePackTypeForRecording(packages.get(0).getPackageOrderFkey(),"10");
                    voidUtil.voidOrderPackage(packages.get(0),order.getOrderId()+"");
                    if(order.getShipinfo().getCarrService().equals("FedEx 2Day")){
                        order.getShipinfo().setCarrService("FedEx Standard Overnight");
                        order.getShipinfo().setCarrServiceRefNum("FDX.STD");
                        HibUtils.commit(HibernateSession.currentSession());
                    }


                    result = getPackagesForShipRetry.getPackagesXml(order.getOrderNum(), HibernateSession.currentSession());


                }else{
                    if(packages.size()>1){
                        throw new Exception("To many packages found for tracking number");
                    }
                    throw new Exception("unable to find package for order");
                }





            }catch(Exception e){
                e.printStackTrace();
               opb.getResults().setMessage("Error");
                opb.getResults().setError(e.getMessage());
                opb.getResults().setMessage("Success");

                XStream x = new XStream();

                x.alias("OrderPackages",opb.getClass());
                x.alias("Box",PackageBoxBean.class);

                return x.toXML(opb);
            }


            return result;
    }
    public static String voidAndReshipFedextoFedexNoUpdate(String tracking){

        String result = "";
        OrderPackagesBean opb = new OrderPackagesBean();
        try{
            tracking = parseOutTrackingFromLabelScanFedex(tracking);
            System.out.println(tracking);
            OwdOrder order = getOwdOrderFromTrackingNumber(tracking);
            System.out.println(order.getPackagesShipped());
            System.out.println(order.getClientFkey());
            List<PackageBoxBean> packages = voidUtil.getPackagesListOrderId(HibernateSession.currentSession(),order.getOrderId()+"");

            if(packages.size()==1){

                System.out.println(packages.get(0).getBarcode());
                System.out.println(packages.get(0).getPackageOrderFkey());
                updatePackTypeForRecording(packages.get(0).getPackageOrderFkey(),"10");
                voidUtil.voidOrderPackage(packages.get(0),order.getOrderId()+"");



                result = getPackagesForShipRetry.getPackagesXml(order.getOrderNum(), HibernateSession.currentSession());


            }else{
                if(packages.size()>1){
                    throw new Exception("To many packages found for tracking number");
                }
                throw new Exception("unable to find package for order");
            }





        }catch(Exception e){
            e.printStackTrace();
            opb.getResults().setMessage("Error");
            opb.getResults().setError(e.getMessage());
            opb.getResults().setMessage("Success");

            XStream x = new XStream();

            x.alias("OrderPackages",opb.getClass());
            x.alias("Box",PackageBoxBean.class);

            return x.toXML(opb);
        }


        return result;
    }


    public static void updatePackTypeForRecording(String packageOrderId, String type) throws Exception{

        String sql = "update package_order set packType = :thetype where id = :id";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("thetype", type);
        q.setParameter("id",packageOrderId);
        int i = q.executeUpdate();
        if(i>0){
            HibUtils.commit(HibernateSession.currentSession());
        }else{
            throw new Exception("Unable to update package_order");
        }

    }




    public static OwdOrder getOwdOrderFromTrackingNumber(String tracking) throws Exception{
        OwdOrder order;
        System.out.println(tracking);
        int orderId = com.owd.core.business.order.Package.getOrderIdFromTrackingNumber(tracking);

        if(orderId>0){
            order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, orderId);
            if(!order.getTrackingNums().contains(tracking)){
                throw new Exception("Old tracking number. No longer attached");
            }
            return order;
        }

        throw new Exception("Unable to lookup order from tracking");


    }

    public static String parseOutTrackingFromLabelScanFedex(String labelScan)throws Exception{

        if(labelScan.length()==34 && labelScan.startsWith("1")){
            return labelScan.substring(22);


        }else{
            throw new Exception("Does not appear to be a valid FedEx Label: "+ labelScan);
        }


    }
}
