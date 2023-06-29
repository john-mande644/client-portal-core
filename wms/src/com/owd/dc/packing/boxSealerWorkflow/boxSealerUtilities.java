package com.owd.dc.packing.boxSealerWorkflow;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.owd.dc.packing.beans.boxBean;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.*;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * Created by danny on 7/29/2019.
 */
public class boxSealerUtilities {


    public static void main(String[] args){
       // System.out.println(getCleanOrderNum("P13389522*27597285*b1"));
       // System.out.println(getCleanOrderNum("27597285R1"));
       // System.out.println(getCleanOrderNum("27597285r2"));
       // System.out.println(getAvailableBoxes("DC6"));
        System.out.println(getCleanOrderNum("p13621298*28009947R1*b1"));
    }






    public static String getCleanOrderNum(String barcode){
        String ordernum = barcode;

        if(barcode.contains("p")||barcode.contains("P")){
            ordernum = barcode.substring(barcode.indexOf("*")+1, barcode.lastIndexOf("*"));
            if(ordernum.contains("r")){
                ordernum = ordernum.substring(0, ordernum.indexOf("r"));

            }
            if(ordernum.contains("R")){
                ordernum = ordernum.substring(0,ordernum.indexOf("R"));

            }

            return ordernum;
        }
        if(barcode.contains("r")){
            ordernum = barcode.substring(0,barcode.indexOf("r"));

        }
        if(barcode.contains("R")){
            ordernum = barcode.substring(0,barcode.indexOf("R"));
        }



        return ordernum;
    }

    public static String updateBox(String packageBarcode, String data){

        GsonBuilder builder = new GsonBuilder();
        // builder.setPrettyPrinting();
        Gson gson = builder.create();
        boxSealerBoxes boxes = new boxSealerBoxes();

        boxSealerBean box = gson.fromJson(data,boxSealerBean.class);
        try{
            Criteria crit = HibernateSession.currentSession().createCriteria(OWDPackage.class);
            crit.add(Restrictions.eq("packBarcode",packageBarcode));
            List<OWDPackage> owdPackages = crit.list();

            if(owdPackages.size()>0){
                OWDPackage owdPackage = owdPackages.get(0);
                owdPackage.setOwdBoxTypesFkey(Integer.parseInt(box.getId()));
                owdPackage.setCurrCost(new BigDecimal(box.getCost()));
                owdPackage.setWidth(new BigDecimal(box.getWidth()));
                owdPackage.setHeight(new BigDecimal(box.getHeight()));
                owdPackage.setDepth(new BigDecimal(box.getDepth()));
                HibernateSession.currentSession().saveOrUpdate(owdPackage);
                HibUtils.commit(HibernateSession.currentSession());
                boxes.setStatus("Success");
            }else{
                throw new Exception("Unable to find package");
            }



        }catch (Exception e){
            e.printStackTrace();
            boxes.setStatus("Error");
            boxes.setError(e.getMessage());
        }





        return gson.toJson(boxes);
    }


    public static String reprintPackageData(String barcode){
        GsonBuilder builder = new GsonBuilder();
        // builder.setPrettyPrinting();
        Gson gson = builder.create();
       boxSealerReprint reprint = new boxSealerReprint();

        try{
            String orderNum = getCleanOrderNum(barcode);
            Criteria crit = HibernateSession.currentSession().createCriteria(OwdOrder.class);
            crit.add(Restrictions.eq("orderNum",orderNum));
            List<OwdOrder> orders = crit.list();
            if(orders.size()>0){
                OwdOrder order = orders.get(0);
                if(!order.getOrderStatus().equalsIgnoreCase("At Warehouse")){
                    throw new Exception("Order is in the wrong state for Reprint: "+order.getOrderStatus());
                }
                Criteria packageCrit = HibernateSession.currentSession().createCriteria(PackageOrder.class);
                packageCrit.add(Restrictions.eq("owdOrderFkey",order.getOrderId()));
                packageCrit.add(Restrictions.eq("isVoid",0));

                List<PackageOrder> packageOrders = packageCrit.list();

                if(packageOrders.size()==0){
                    throw new Exception("No valid Packs found");
                }
                PackageOrder packageOrder = packageOrders.get(0);
                Set<OWDPackage> owdPackages = packageOrder.getPackages();

                OWDPackage owdPackage = (OWDPackage) owdPackages.toArray()[0];

                reprint.setStatus("Success");
                reprint.setMessage("Success");
                reprint.setBarcode( owdPackage.getPackBarcode());
                reprint.setClient(order.getClient().getCompanyName());
                reprint.setShipMethod(order.getShipinfo().getCarrService());
                reprint.setOrderNum(order.getOrderNum());
                OwdBoxtypes box = (OwdBoxtypes) HibernateSession.currentSession().load(OwdBoxtypes.class,owdPackage.getOwdBoxTypesFkey());
                reprint.setBoxName(box.getName());


            }else{
                throw new Exception("Unable to lookup order with: "+barcode);
            }

        }catch (Exception e){
           reprint.setStatus("Error");
            reprint.setMessage(e.getMessage());
        }

        return gson.toJson(reprint);

    }
    public static String addDunnageToPackage(String barcode,String dunnage){
        GsonBuilder builder = new GsonBuilder();
        // builder.setPrettyPrinting();
        Gson gson = builder.create();
        boxSealerBoxes boxes = new boxSealerBoxes();

        try{
            Criteria crit = HibernateSession.currentSession().createCriteria(OWDPackage.class);
            crit.add(Restrictions.eq("packBarcode",barcode));
            List<OWDPackage> packages = crit.list();
            if(packages.size()>0) {

                OWDPackage pack = packages.get(0);
                try {
                    pack.setDunnageFactor(Integer.parseInt(dunnage));
                }catch (Exception e){
                    throw new Exception("Dunnage must be numeric");
                }
                HibernateSession.currentSession().saveOrUpdate(pack);
                HibUtils.commit(HibernateSession.currentSession());
                boxes.setStatus("Success");

            }else{
                throw new Exception("Unable to find package");
            }



        }catch (Exception e){
            boxes.setStatus("Error");
            boxes.setError(e.getMessage());
        }



        return gson.toJson(boxes);

    }


    public static String getAvailableBoxes(String facility){


        GsonBuilder builder = new GsonBuilder();
        // builder.setPrettyPrinting();
        Gson gson = builder.create();
        boxSealerBoxes boxes = new boxSealerBoxes();
        try{

            Criteria crit = HibernateSession.currentSession().createCriteria(OwdBoxtypes.class);


            crit.add(Expression.eq("location", facility));
            crit.add(Expression.eq("showOnBoxSealerStation",true));
            crit.addOrder(Order.asc("depth"));

            List<OwdBoxtypes> b = crit.list();

            for(OwdBoxtypes box:b){
                boxSealerBean bb = new boxSealerBean();
                bb.setId(box.getId()+"");
                bb.setDesc(box.getDescription());
                bb.setName(box.getName());
                bb.setBarcode(box.getBarcode());
                bb.setDepth(box.getDepth()+"");
                bb.setWidth(box.getWidth()+"");
                bb.setHeight(box.getHeight()+"");
                bb.setBoxweight(box.getWeight()+"");
                bb.setCost(box.getCost().toString());
                bb.setSize(box.getDepth()+"x"+box.getWidth()+"x"+box.getHeight());
                bb.setVolume((box.getDepth().multiply(box.getWidth()).multiply(box.getHeight())).toString());
                try {
                    bb.setInventoryfkey(box.getOwdInventory().getInventoryId() + "");
                }catch (Exception e){
                    bb.setInventoryfkey("");
                    e.printStackTrace();
                }
                bb.setPackageType(box.getPackagingType());

                boxes.getBoxes().add(bb);
            }
            boxes.setStatus("Success");



        }catch (Exception e){
            boxes.setStatus("Error");
            boxes.setError(e.getMessage());
        }


        return gson.toJson(boxes);
    }
}
