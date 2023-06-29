package com.owd.jobs.jobobjects.apparelmagic

import com.owd.core.managers.InventoryManager
import com.owd.hibernate.HibUtils
import com.owd.hibernate.HibernateSession
import com.owd.hibernate.generated.OwdClientInv
import com.owd.hibernate.generated.OwdInventory

/**
 * Created by danny on 8/25/2016.
 */
class skuCheckTask implements Runnable{



    private def it;
    private ApparelMagicApi amaAPI;
    private String clientId;
    private boolean originOnly;
    skuCheckTask(Object reqest,ApparelMagicApi api, String clientId,Boolean origin){
        this.amaAPI = api;
        this.it = reqest;
        this.clientId = clientId;
        this.originOnly = origin;

    }


    public void run(){
        try {
            println("Updating Sku " + it.sku_alt);
            def product = amaAPI.getProductJson(Integer.parseInt(it.product_id));
            def vendorName;
            if (product.response.vendor_id[0] != null) {
                try {
                    def vendor = amaAPI.getVendorJson(Integer.parseInt(product.response.vendor_id[0]))
                    vendorName = vendor.response.vendor_name[0]
                    if (vendorName.size() > 50) {
                        vendorName = vendorName.substring(0, 49);
                    }
                } catch (Exception ex) {

                }
            }
            OwdInventory inv = InventoryManager.getOwdInventoryForClientAndSku(clientId + "", it.sku_alt);
           if(!originOnly) {
               inv.setDescription(product.response.description[0] + '-' + it.attr_2 + '-' + it.size);
               inv.setPrice(Float.parseFloat(product.response.retail_price[0].toString()));
               inv.setMfrPartNum(vendorName);
               //inv.setWeightLbs(Float.parseFloat(product.response.weight[0].toString()));
               inv.setCustomsDesc(product.response.tariff_code[0] + '/Made in ' + getOriginCountry(product.response.origin[0].toString()) + '/Apparel/' + product.response.content[0]);
               inv.setCustomsValue(Float.parseFloat(it.cost));

               inv.setKeyword(product.response.category[0].toString());
               inv.setItemColor(it.attr_2_name);

           }

            if(null == inv.getOwdClientInv()){
                OwdClientInv oci = new OwdClientInv();
                oci.setClientFkey(inv.getOwdClient().getClientId());
                oci.setOwdInventory(inv);
                oci.setOriginCountry(product.response.origin[0].toString());
                oci.setForceShipment(false);
                oci.setIsDocuments(false);
                oci.setForceMethodUs("")
                oci.setForceMethodCan("");
                oci.setForceMethodPo("");
                oci.setForceMethodInt("");

                HibernateSession.currentSession().save(oci);
                inv.setOwdClientInv(oci);

            }else{
                inv.getOwdClientInv().setOriginCountry(product.response.origin[0].toString());
            }




            HibernateSession.currentSession().save(inv);
            HibernateSession.currentSession().saveOrUpdate(inv.getOwdClientInv());
            HibUtils.commit(HibernateSession.currentSession());
        }catch(Exception e){
            println e.message;
        }
    }

    private String getOriginCountry(String country){
        if(country.equals("CN")) return "China";
        if(country.equals("IN")) return "India";

        return "USA";
    }
}
