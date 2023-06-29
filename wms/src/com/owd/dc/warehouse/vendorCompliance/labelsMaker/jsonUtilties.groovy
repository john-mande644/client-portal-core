package com.owd.dc.warehouse.vendorCompliance.labelsMaker

import groovy.json.JsonOutput

/**
 * Created by danny on 10/19/2016.
 */
class jsonUtilties {

    public static void main(String[] args){
        List<skuBean> l = new ArrayList<>();

        def s = new skuBean();
        s.setOwdId("12345")
        s.setOwdSku("hellO");
        s.setVendorDesc("desc")
        s.setVendorSku("aaaa");
        l.add(s);
        def b = new skuBean();
        b.setOwdId("123451")
        b.setOwdSku("hellO1");
        b.setVendorDesc("desc1")
        b.setVendorSku("aaa1a");
        l.add(b);
        String sss = getJsonFromObject(l);
        System.out.println(sss);
        JsonOutput.prettyPrint(getJsonFromObject(l))

    }

    public static String getJsonFromObject(Object data){


        return JsonOutput.toJson(data);



    }
}
