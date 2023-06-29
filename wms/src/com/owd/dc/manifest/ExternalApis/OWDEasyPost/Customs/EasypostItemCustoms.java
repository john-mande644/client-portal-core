package com.owd.dc.manifest.ExternalApis.OWDEasyPost.Customs;

import com.easypost.model.CustomsInfo;
import com.easypost.model.CustomsItem;
import com.owd.dc.manifest.api.internal.AMPConnectShipShipment;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by danny on 9/23/2017.
 */
public class EasypostItemCustoms {



    public static CustomsInfo loadCustomsInfo(AMPConnectShipShipment ship,int clientId) throws Exception{
        Map<String, Object> customsInfoMap = new HashMap<String, Object>();
       // customsInfoMap.put("integrated_form_type", "form_2976");
        customsInfoMap.put("customs_certify", true);
        customsInfoMap.put("customs_signer", "Kyle Bauman");
        customsInfoMap.put("contents_type", "merchandise");
        customsInfoMap.put("eel_pfc", "NOEEI 30.37(a)");
        customsInfoMap.put("non_delivery_option", "return");
        customsInfoMap.put("restriction_type", "none");

        List<CustomsItem> customsItemsList = new ArrayList<CustomsItem>();

        for (int i = 0; i < ship.getItemData()[0].length; i++) {
            System.out.println("Doing item #" + i);

            Map<String, Object> customsItemMap = new HashMap<String, Object>();

            customsItemMap.put("code", ship.getItemData()[AMPConnectShipShipment.kTDItemSKU][i]);
           customsItemMap.put("description",ship.getItemData()[AMPConnectShipShipment.kTDItemDesc][i]);
            int qty = Integer.parseInt(ship.getItemData()[AMPConnectShipShipment.kTDItemQty][i]);
            float price = Float.parseFloat(ship.getItemData()[AMPConnectShipShipment.kTDItemPrice][i]);
            customsItemMap.put("quantity",qty);
            customsItemMap.put("value",price * qty );
            customsItemMap.put("origin_country","US");
            CustomsItem customsItem = CustomsItem.create(customsItemMap);
            customsItemsList.add(customsItem);


        }
        customsInfoMap.put("customs_items",customsItemsList);


        return CustomsInfo.create(customsInfoMap);
    }
}
